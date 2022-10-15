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
package com.reedmanit.myeditor.util;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author preed
 */
public class Find {
    
    private String data;
    
    private Integer[] indexes;
    
    public Find(String aValue) {
        data = aValue;
    }
    
    public int findAll(String value) {
        int n = StringUtils.countMatches(data, value);  // how many times does string appear
        int p = 0;  // ptr set to start of string
        
        if (n > 0) {     // some matches
            indexes = new Integer[n]; 
            for (int i = 0; i < n; i++) {   
                indexes[i] = StringUtils.indexOf(data, value, p);  // store the index of the search item
                p = indexes[i] + value.length();  // push the ptr forward to beyond the current length of the found string
            }
        }
        return n;   // return number of times string appears
        
    }
    
    public int positionOf(String value) {
        
        return StringUtils.indexOf(getData(), value);
        
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the indexes
     */
    public Integer[] getIndexes() {
        return indexes;
    }
    
}
