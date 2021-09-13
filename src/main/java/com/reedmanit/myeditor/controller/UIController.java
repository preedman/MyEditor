/*
 * Copyright 2021 paul.
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

import com.reedmanit.myeditor.data.Input;
import com.reedmanit.myeditor.data.Output;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author paul
 */
public class UIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;
    
    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private TextArea dataText;
    
    @FXML
    private Label fileNameLabel;

    private Output out;
    
    private File openFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fileNameLabel = new Label();
        
        fileNameLabel.setText("No File Open");
        
        saveAsMenuItem = new MenuItem();

        saveAsMenuItem.setOnAction(e -> {
            try {
                saveAsData();
            } catch (IOException ex) {
                Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        openMenuItem = new MenuItem();

        openMenuItem.setOnAction(e -> {
            try {
                openFile();
            } catch (IOException ex) {
                Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        saveMenuItem = new MenuItem();
        
        saveMenuItem.setOnAction(e -> {
            try {
                saveData();
            } catch (IOException ex) {
                Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        

    }

    public void saveAsData() throws IOException {
        System.out.println("Save As Selected");

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save As Text File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        fileChooser.setInitialFileName("*.txt");

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            out = new Output(file);
            out.persist(dataText.getText());
        }

    }
    
    public void saveData() throws IOException {
        
        System.out.println("Save Selected");
        
        if (openFile != null) {
            
            Output saveFile = new Output(openFile);
            saveFile.persist(dataText.getText());
            
        }
        
        
    }

    public void openFile() throws IOException {

        System.out.println("Open Selected");

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        fileChooser.setInitialFileName("*.txt");

        openFile = fileChooser.showOpenDialog(new Stage());

        if (openFile != null) {

            Input in = new Input(openFile);
            dataText.setText(in.readContents());
            fileNameLabel.setText(openFile.getName());  
        }

    }

}
