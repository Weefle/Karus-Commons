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
package com.karuslabs.commons.graphics;

import com.karuslabs.commons.graphics.windows.ShapelessWindow;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import org.junit.jupiter.api.Test;

import static com.karuslabs.commons.locale.MessageTranslation.NONE;
import static java.util.Collections.*;
import static org.bukkit.Material.AIR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DragEventTest {
    
    InventoryDragEvent drag = spy(new InventoryDragEvent(null, null, new ItemStack(AIR), false, EMPTY_MAP));
    DragEvent event = new DragEvent(new ShapelessWindow(null, NONE, false), drag);
    
    
    @Test
    void getDragged() {
        when(drag.getRawSlots()).thenReturn(singleton(15));
        
        Point[] points = event.getDragged();
        
        assertArrayEquals(new Point[] {new Point(15, 0)}, points);
        assertSame(points, event.getDragged());
    }
    
    
    @Test
    void get() {
        event.getNewItems();
        verify(drag).getNewItems();
        
        event.getRawSlots();
        verify(drag).getRawSlots();
        
        event.getInventorySlots();
        verify(drag).getInventorySlots();
        
        event.getCursor();
        verify(drag).getCursor();
        
        ItemStack item = new ItemStack(AIR);
        event.setCursor(item);
        verify(drag).setCursor(item);
        
        event.getType();
        verify(drag).getType();
    }
    
}
