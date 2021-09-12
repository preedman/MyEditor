/*
 * Copyright 2021 preed.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reedmanit.myeditor.data;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author preed
 */
public class OutputTest {
    
    public OutputTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of persist method, of class Output.
     */
    @Test
    public void testPersist() throws Exception {
        System.out.println("persist");
        String out = "This is a test" + "\n";
        String out2 = "Another Test";
        
        String s = out + out2;
        
        File f = new File("C:\\SoftwareDevelopment\\test.txt");
        Output instance = new Output(f);
        try {
            instance.persist(s);
        } catch (IOException ioe) {
            fail("Exception " + ioe.toString());
        }
        
        
    }
    
}
