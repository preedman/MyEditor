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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author preed
 */
public class Input {
    
    private File inFile;
    
    public Input(File theFile) {
        inFile = theFile;
    }
    
    public String readContents() throws IOException {
        
        Path filePath = Paths.get(inFile.getAbsolutePath());
        String content = new String(Files.readAllBytes(filePath));
        return content;
    }
    
    
}
