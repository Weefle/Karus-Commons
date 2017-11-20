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
package com.karuslabs.commons.command.arguments;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Arrays.copyOfRange;


/**
 * Represents a matcher which provides facilities to match and test the arguments of this {@code Matcher} in a given range.
 */
public class Matcher {
    
    private String[] arguments;
    private int first, last;
    
    
    /**
     * Constructs a {@code Matcher} for the specified arguments.
     * 
     * @param arguments the arguments
     */
    public Matcher(String... arguments) {
        this.arguments = arguments;
        first = 0;
        last = arguments.length;
    }
  
    
    /**
     * Sets the range between 0 and the length of the arguments, exclusive.
     * 
     * @return this 
     */
    public Matcher all() {
        first = 0;
        last = arguments.length;
        return this;
    }
    
    
    /**
     * Sets the range between the specified first index, inclusive, and the length of the arguments, exclusive.
     * 
     * @param first the first index, inclusive
     * @return this
     */
    public Matcher starting(int first) {
        return between(first, arguments.length);
    }
    
    /**
     * Sets the range between the specified first index, inclusive, and the specified last index, exclusive.
     * 
     * @param first the first index, inclusive
     * @param last the final index, exclusive
     * @return this
     * @throws IllegalArgumentException if the specified first or last index is out of bounds, or the last index is less than the first index
     */
    public Matcher between(int first, int last) {
        if (0 <= first && first <= last && last <= arguments.length && (arguments.length == 0 || first < arguments.length)) {
            this.first = first;
            this.last = last;
            return this;
            
        } else {
            throw new IllegalArgumentException("Invalid bounds specified: " + first + ", " + last);
        }
    }

    
    /**
     * Returns a {@code Stream} of the arguments for the given range.
     * 
     * @return 
     */
    public Stream<String> stream() {
        return Stream.of(copyOfRange(arguments, first, last));
    }
    
    /**
     * Returns {@code true} if the arguments in the given range matches the specified {@code Predicate} sequence exactly; else {@code false}.
     * 
     * @param matches the Predicates to test the given arguments
     * @return true if the arguments in the given range each matches the specified Predicate; else false
     * @throws IllegalArgumentException if the range of arguments is not equal to the number of specified Predicates
     */
    public boolean exact(Predicate<String>... matches) {
        if (matches.length != length()) {
            throw new IllegalArgumentException("Invalid number of matches specified.");
        }
        
        for (int i = first; i < last; i++) {
            if (!matches[i].test(arguments[i])) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Returns {@code true} if the arguments in the given range contains a match for the specified {@code Predicate} sequence; else {@code false}.
     * 
     * @param matches the Predicates to test the given arguments
     * @return true if the arguments in the given range contains a match for the specified Predicate sequence; else false
     * @throws IllegalArgumentException if the range of arguments is less than the length of the specified sequence of Predicates
     */
    public boolean anySequence(Predicate<String>... matches) {
        if (matches.length > length()) {
            throw new IllegalArgumentException("Invalid number of matches specified.");
        }
        
        int i = 0;
        for (String argument : arguments) {
            if (matches[i].test(argument)) {
                i++;
                if (matches.length <= i) {
                    return true;
                }

            } else {
                i = 0;
            }
        }

        return false;
    }
    
    
    /**
     * Returns Returns {@code true} if the arguments in the given range matches the specified {@code Predicate}; else {@code false}.
     * 
     * @param matcher the Predicate to test the given arguments
     * @return true if the arguments in the given range matches the specified Predicate; else false
     */
    public boolean using(Predicate<String[]> matcher) {
        return matcher.test(copyOfRange(arguments, first, last));
    }
    
    
    /**
     * Returns the range of the arguments to match.
     * 
     * @return the range
     */
    public int length() {
        return last - first;
    }
    
    
    /**
     * Sets the arguments of this {@code Matcher}.
     * 
     * @param arguments the arguments
     */
    protected void set(String[] arguments) {
        this.arguments = arguments;
        all();
    }
    
    /**
     * Returns the first index, inclusive.
     * 
     * @return the first index
     */
    protected int getFirst() {
        return first;
    }
    
    /**
     * Returns the last index, exclusive.
     * 
     * @return the last index
     */
    protected int getLast() {
        return last;
    }
    
}
