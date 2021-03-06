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

import com.karuslabs.commons.effect.Effect;
import com.karuslabs.commons.effect.particles.Particles;

import java.util.stream.Stream;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static com.karuslabs.commons.effect.effects.Constants.NONE;
import static java.lang.Math.PI;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;


class EffectsTest extends EffectBase {
    
    @ParameterizedTest
    @MethodSource("parameters")
    void render(Effect effect, Vector expected) {
        effect.render(context, origin, target, offset);
        
        verify(context, atLeastOnce()).render(any(Particles.class), any(Location.class), any(Vector.class));
        
        assertVector(expected, context.offset);
    }
    
    static Stream<Arguments> parameters() {
        return Stream.of(
            of(new AnimatedBall(PARTICLES).get(), from(0, 2, 0)),
            of(new Arc(PARTICLES, 2, 1).get(), from(0, 0, 0)),          
            of(new Cone(PARTICLES, 0.5F, 0.006F, (float) (PI / 16), 180, 1, 0, false).get(), from(0, 0, 0)),
            of(new Donut(PARTICLES, 1, 1, 2, 0.5F, NONE).get(), from(2.5, 0, 0)),
            of(new Heart(PARTICLES).get(), from(0, 0, 0)),
            of(new Hill(PARTICLES).get(), from(0, 0, 0)),
            of(new Vortex(PARTICLES, 1, 2, 0.5f, PI / 16, 1, 1).get(), from(1.0000000000000002, 0, 1.7320508075688772)),
            of(new Warp(PARTICLES, 12, 1, 1, 0.2f).get(), from(1, 0, 0)),
            of(new Spiral(SINGLE, 1, 1, 10, 10, PI / 4).get(), from(7.071067811865563, 0.0, 7.071067811865387))
        );
    }
    
    
    @ParameterizedTest
    @MethodSource("random_parameters")
    void render_Random(Effect effect, int times) {
        effect.render(context, origin, target, offset);
        
        verify(context, times(times)).render(eq(PARTICLES), any(Location.class), any(Vector.class));
    }
    
    static Stream<Arguments> random_parameters() {
        return Stream.of(
            of(new Flame(PARTICLES).get(), 1),
            of(new Shield(PARTICLES).get(), 1),
            of(new Smoke(PARTICLES).get(), 1),
            of(new Sphere(PARTICLES).get(), 1)
        );
    }
    
}
