/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.commons.util.concurrent;

import com.karuslabs.commons.annotation.Blocking;

import java.util.concurrent.*;

import org.bukkit.scheduler.BukkitRunnable;


public abstract class ResultTask<T> extends BukkitRunnable implements Result<T> {
    
    public static <T> ResultTask<T> of(Runnable runnable, T result) {
        return new PromiseRunnable<>(runnable, result);
    }
    
    public static <T> ResultTask<T> of(Callable<T> callable) {
        return new PromiseCallable<>(callable);
    }
    
    
    static final int NEW = 0;
    static final int RUNNING = 1;
    static final int COMPLETED = 2;
    static final int CANCELLED = 3;
    
    volatile int state;
    volatile Throwable thrown;
    CountDownLatch latch;
    
    
    public ResultTask() {
        state = NEW;
        thrown = null;
        latch = new CountDownLatch(1);
    }

    
    @Override
    public void run() {
        try {
            process();
            
        } catch (Throwable e) {
            thrown = e;
            
        } finally {
            done();
        }
    }
    
    protected abstract void process() throws Exception;        
    
    protected void done() {
        finish(COMPLETED);
    }
     
    @Override
    public void cancel() {
        cancel(true);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (state < COMPLETED && mayInterruptIfRunning) {
            finish(CANCELLED);
            return true;
            
        } else {
            return false;
        }
    }
    
    void finish(int updated) {
        cancelThis();
        latch.countDown();
        state = updated;
        callback();
    }
    
    protected void callback() {};
    
    void cancelThis() {
        try {
            super.cancel();
        } catch (NullPointerException | IllegalStateException e) {
            // Honestly doesn't really matter if the NPE is ignored as it means Bukkit.getScheduler() returned null
            // in which case the task wouldn't have been scheduled to begin with...
        }
    }

    
    @Override
    @Blocking
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        return handle();
    }

    @Override
    @Blocking
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return handle();
            
        } else {
            throw new TimeoutException();
        }
    }
    
    protected T handle() throws ExecutionException {
        if (thrown != null) {
            throw new ExecutionException(thrown);
            
        } else if (state == CANCELLED) {
            throw new CancellationException();
            
        } else {
            return value();
        }
    }
    
    protected abstract T value();
    
        
    @Override
    public boolean isCancelled() {
        return state == CANCELLED;
    }
    
    @Override
    public boolean isDone() {
        return RUNNING < state;
    }
    
    
    static class PromiseRunnable<T> extends ResultTask<T> {
        
        private final Runnable runnable;
        private final T value;
        
        PromiseRunnable(Runnable runnable, T value) {
            this.runnable = runnable;
            this.value = value;
        }
        
        @Override
        protected void process() {
            runnable.run();
        }

        @Override
        protected T value() {
            return value;
        }
        
    }
    
    static class PromiseCallable<T> extends ResultTask<T> {
        
        private final Callable<T> callable;
        private T value;
        
        PromiseCallable(Callable<T> callable) {
            this.callable = callable;
        }
        
        @Override
        protected void process() throws Exception {
            value = callable.call();
        }

        @Override
        protected T value() {
            return value;
        }
        
    }
    
}
