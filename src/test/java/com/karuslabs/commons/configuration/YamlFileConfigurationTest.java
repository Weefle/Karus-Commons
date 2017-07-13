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
package com.karuslabs.commons.configuration;

import com.karuslabs.commons.util.UncheckedIOException;

import java.io.*;

import junitparams.*;

import org.bukkit.configuration.InvalidConfigurationException;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;


@RunWith(JUnitParamsRunner.class)
public class YamlFileConfigurationTest {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private YamlFileConfiguration config;
    private File file;
    private File parent;
    
    
    public YamlFileConfigurationTest() {
        file = when(mock(File.class).getName()).thenReturn("name").getMock();
        config = spy(new YamlFileConfiguration(file));
        parent = when(mock(File.class).mkdirs()).thenReturn(true).getMock();
        when(file.getParentFile()).thenReturn(parent);
    }
    
    
    @Test
    @Parameters({"true, 0", "false, 1"})
    public void load(boolean exists, int times) throws IOException, InvalidConfigurationException {
        when(file.exists()).thenReturn(exists);
        
        doNothing().when(config).load(file);
        
        config.load();
        
        verify(parent, times(times)).mkdirs();
        verify(file, times(times)).createNewFile();
        
        verify(config).load(file);
    }
    
    
    @Test
    public void load_File_ThrowsException() throws IOException {
        exception.expect(UncheckedIOException.class);
        exception.expectMessage("Failed to load configuration file: " + file.getName());
        
        doThrow(IOException.class).when(file).createNewFile();
        
        config.load();
    }
    
    
    @Test
    public void load_Config_ThrowsException() throws InvalidConfigurationException, IOException {
        exception.expect(UncheckedIOException.class);
        exception.expectMessage("Failed to load configuration file: " + file.getName());
        
        doThrow(InvalidConfigurationException.class).when(config).load(file);
        
        config.load();
    }
    
    
    @Test
    @Parameters({"true, 0, 1", "false, 1, 0"})
    public void loadOrDefault(boolean exists, int saveTimes, int loadTimes) throws IOException, InvalidConfigurationException {
        when(file.exists()).thenReturn(exists);
        doNothing().when(config).load(any(InputStreamReader.class));
        doNothing().when(config).save(any(File.class));
        doNothing().when(config).load(any(File.class));
        
        config.loadOrDefault("configuration/config.yml");
        
        verify(config, times(saveTimes)).load(any(InputStreamReader.class));
        verify(config, times(saveTimes)).save(file);
        
        verify(config, times(loadTimes)).load(any(File.class));
    }
    
    
    @Test
    public void loadOrDefault_File_ThrowsException() throws IOException {
        exception.expect(UncheckedIOException.class);
        exception.expectMessage("Failed to load configuration file: " + file.getName());
        
        doThrow(IOException.class).when(config).save(file);
        
        config.loadOrDefault("configuration/config.yml");
    }
    
    
    @Test
    public void loadOrDefault_Config_ThrowsException() throws IOException, InvalidConfigurationException {
        when(file.exists()).thenReturn(true);
        
        exception.expect(UncheckedIOException.class);
        exception.expectMessage("Failed to load configuration file: " + file.getName());
        
        doThrow(InvalidConfigurationException.class).when(config).load(file);
        
        config.loadOrDefault("configuration/config.yml");
    }
    
    
    @Test
    public void save() throws IOException {   
        doNothing().when(config).save(file);
        
        config.save();
        
        verify(config).save(file);
    }
    
    
    @Test
    public void save_ThrowsException() throws IOException {
        exception.expect(UncheckedIOException.class);
        exception.expectMessage("Failed to save config: " + file.getName());
        
        doThrow(IOException.class).when(config).save(file);
        
        config.save();
    }
    
}