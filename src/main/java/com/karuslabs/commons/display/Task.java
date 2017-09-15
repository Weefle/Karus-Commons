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
package com.karuslabs.commons.display;


import com.karuslabs.commons.locale.Translation;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public abstract class Task<GenericTask extends Task> extends BukkitRunnable {
    
    private Translation translation;
    private Consumer<GenericTask> prerender;
    private Consumer<GenericTask> cancellation;
    private long total;
    private long current;

    
    public Task(Translation translation, Consumer<GenericTask> prerender, Consumer<GenericTask> cancellation, long frames) {
        this.translation = translation;
        this.prerender = prerender;
        this.cancellation = cancellation;
        total = frames;
        current = 0;
    }
    
    
    @Override
    public void run() {
        if (current < total) {
            prerender.accept(getThis());
            render();
            current++;
            
        } else {
            cancel();
            cancellation.accept(getThis());
        }
    }
    
    protected abstract void render();
    
    protected abstract GenericTask getThis();
    
    protected abstract void cancel(Player player);
    
    
    public Translation getTranslation() {
        return translation;
    }
    
    public long getTotalFrames() {
        return total;
    }

    public long getCurrentFrame() {
        return current;
    }
    
}
