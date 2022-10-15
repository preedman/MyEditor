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
package com.reedmanit.myeditor.controller;

import com.reedmanit.myeditor.util.Find;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.log4j.LogManager;

/**
 *
 * @author preed
 */
public class SearchController implements Initializable {

    @FXML
    private TextField searchTF;

    @FXML
    private Button searchBT;

    @FXML
    private Button nextBT;

    

    private String textOfFile;

    private UIController mainUIController;

    private Integer[] instancesOf;

    private static int clickNextCount;
    
    protected static final org.apache.log4j.Logger searchControllerLogger = LogManager.getLogger(SearchController.class.getName());

    private static org.apache.log4j.Logger logger = searchControllerLogger;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logger.info("Enter Search Controller");

        nextBT.setDisable(true);
        clickNextCount = 0;
        
        logger.info("Exit Search Controller");

    }

    public void searchText() {
        logger.info("Enter Search Text");

        Find find = new Find(mainUIController.getText());

        int n = find.findAll(searchTF.getText());

        if (n > 0) {
            
            clickNextCount = 0;
            
            instancesOf = find.getIndexes();
            
            logger.info("number of " + this.searchTF.getText() + " " + instancesOf.length);

            

            mainUIController.highlightText(instancesOf[0]);

            nextBT.setDisable(false);

        }
        
        logger.info("Exit Search Text");

    }

    public void nextText() {
        
        logger.info("Enter next");

        clickNextCount++;

        if (clickNextCount < instancesOf.length) {
            

            mainUIController.highlightText(instancesOf[clickNextCount]);
        }
        
        logger.info("Exit Next");

    }

    public void setTextOfFile(String theText) {
        
        textOfFile = theText;
        
    }

    public void setController(UIController theMainController) {
        
        mainUIController = theMainController;
    }

}
