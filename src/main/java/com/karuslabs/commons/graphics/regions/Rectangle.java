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
package com.karuslabs.commons.graphics.regions;

import com.karuslabs.commons.graphics.buttons.Button;
import com.karuslabs.commons.graphics.*;

import java.util.*;


public class Rectangle<GenericButton extends Button> extends AbstractRegion<GenericButton> {
    
    private Point min;
    private Point max;
    private int minSlot;
    private int maxSlot;
    private int size;
    private int width;
        
    
    public Rectangle(Point min, int minSlot, Point max, int maxSlot) {
        this(new HashMap<>(), min, minSlot, max, maxSlot);
    }
    
    public Rectangle(Map<Integer, GenericButton> map, Point min, int minSlot, Point max, int maxSlot) {
        super(map);
        this.min = min;
        this.max = max;
        this.minSlot = minSlot;
        this.maxSlot = maxSlot;
        int width = (max.x - min.x) + 1;
        int height = (max.y - min.y) + 1;
        this.size = width * height;
        this.width = (maxSlot - minSlot - width + 1) / (height - 1);
    }
    
    
    @Override
    public boolean contains(int slot) {
        int x = slot % width;
        int y = slot / width;
        return (min.x <= x && x <= max.x) && (min.y <= y && y <= max.y);
    }
    
    @Override
    public int size() {
        return size;
    }
    
    
    public Point getMin() {
        return min;
    }
    
    public Point getMax() {
        return max;
    }
    
    public int getMinSlot() {
        return minSlot;
    }
    
    public int getMaxSlot() {
        return maxSlot;
    }
    
}
