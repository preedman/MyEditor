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

/**
 *
 * @author preed
 */
public class Content {
    
    private String theContent;
    
    public Content ( String aContent) {
        theContent = aContent;
    }

    /**
     * @return the theContent
     */
    public String getTheContent() {
        return theContent;
    }

    /**
     * @param theContent the theContent to set
     */
    public void setTheContent(String theContent) {
        this.theContent = theContent;
    }
    
    
    
}
