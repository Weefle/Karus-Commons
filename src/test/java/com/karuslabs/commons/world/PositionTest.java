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
package com.karuslabs.commons.world;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class PositionTest {
    
    static Position position = new Position(1, 2, 3, 4, 5);
    
    
    @ParameterizedTest
    @MethodSource("parameters")
    void equals_Object(Object other, boolean expected) {
        assertEquals(expected, position.equals(other));
    }
    
    
    @ParameterizedTest
    @MethodSource("parameters")
    void hashcode(Object other, boolean expected) {
        assertEquals(expected, position.hashCode() == other.hashCode());
    }
    
    static Stream<Arguments> parameters() {
        return Stream.of(of(position, true),
            of(new Position(1, 2, 3, 4, 5), true),
            of(new Position(2, 1, 3, 5, 4), false),
            of(new Object(), false)
        );
    }
    
    @Test
    void tostring() {
        assertEquals("Position[" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ", " + position.getYaw() + ", " + position.getPitch() + "]", 
            position.toString()
        );
    }
    
}
