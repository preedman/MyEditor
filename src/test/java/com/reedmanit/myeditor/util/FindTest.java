/*
 * Copyright 2022 preed.
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
package com.reedmanit.myeditor.util;

import java.util.Arrays;
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
public class FindTest {
    
    public FindTest() {
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
     * Test of findAll method, of class Find.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        
        Find instance = new Find("I said goodbye and bye bye a number of times");
        int n = instance.findAll("bye");
        
        assertEquals(n, 3);  // 3 instances of string
        
        Integer[] pos = instance.getIndexes();
       
        assertEquals(pos[0], 11);
        assertEquals(pos[1], 19);
        assertEquals(pos[2], 23);
       
        int t = instance.findAll("1");
        
        assertEquals(t, 0);  // no string found
        
        int m = instance.findAll("I");
        
        assertEquals(m, 1); // string found once
        
        Integer[] one = instance.getIndexes();
        
        assertEquals(one[0], 0);  // found at beginning of string
        
    }

    
    
}
