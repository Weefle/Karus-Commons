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
package com.karuslabs.commons.menu.buttons;

import com.karuslabs.commons.menu.*;

import org.bukkit.event.inventory.*;


@FunctionalInterface
public interface Button {
    
    public static final Button CANCEL = (menu, event) -> event.setCancelled(true);
    
    public static final Button NONE = new Button() {
        
        @Override
        public void click(Menu menu, InventoryClickEvent event) {}
        
        @Override
        public void drag(Menu menu, InventoryDragEvent event) {}
        
    };
    
    
    public void click(Menu menu, InventoryClickEvent event);
    
    public default void drag(Menu menu, InventoryDragEvent event) {
        event.setCancelled(true);
    }
    
    public default void open(Menu menu, InventoryOpenEvent event, int slot) {}
    
    public default void close(Menu menu, InventoryCloseEvent event, int slot) {}
    
}
