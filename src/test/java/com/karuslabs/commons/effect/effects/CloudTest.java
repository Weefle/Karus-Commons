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
package com.karuslabs.commons.effect.effects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.Mockito.*;


class CloudTest extends EffectBase {

    Cloud cloud = spy(new Cloud(PARTICLES, COLOURED));
    
    
    @Test
    void render() {
        doNothing().when(cloud).renderCloud(context, origin, offset, RANDOM);
        doNothing().when(cloud).renderDroplets(context, origin, offset, RANDOM);
        
        cloud.render(context, origin, target, offset);
        
        verify(cloud).renderCloud(context, origin, offset, RANDOM);
        verify(cloud).renderDroplets(context, origin, offset, RANDOM);
    }
    
    
    @Test
    void renderCloud() {
        cloud.renderCloud(context, origin, offset, mockRandom);
        
        verify(context).render(PARTICLES, origin, offset);
    }
    
    
    @ParameterizedTest
    @CsvSource({"true, 2", "false, 0"})
    void renderDroplets(boolean render, int expected) {
        when(mockRandom.nextBoolean()).thenReturn(render);
        
        cloud.renderDroplets(context, origin, offset, mockRandom);
        
        verify(context, times(expected)).render(COLOURED, origin, offset);
    }
    
}
