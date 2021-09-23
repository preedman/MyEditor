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
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
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
    private MenuItem newMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private TextArea dataText;

    @FXML
    private MenuItem findMenuItem;

    @FXML
    private Label fileNameLabel;

    @FXML
    private TextField fileNameTF;

    private Output out;

    private File theDataFile;

    private Stage theStage;

    private String fontFamily = "Arial";
    private double fontSize = 13;

    private Font theFont;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        theFont = Font.font(fontFamily, fontSize);

        dataText.setFont(theFont);

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

        newMenuItem = new MenuItem();

        newMenuItem.setOnAction(e -> {

            newData();

        });

        exitMenuItem = new MenuItem();

        exitMenuItem.setOnAction(e -> {

            exit();

        });

        findMenuItem = new MenuItem();

        findMenuItem.setOnAction(e -> {

            findContent();

        });

    }

    public void newData() {

        System.out.println("New Selected");

        dataText.setText("");

        theStage.setTitle("My Text Editor");

    }

    public void saveAsData() throws IOException {
        System.out.println("Save As Selected");

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                                                 new FileChooser.ExtensionFilter("HTML Files", "*.htm"),
                                                 new FileChooser.ExtensionFilter("Java Files", "*.java")
        );
        
        fileChooser.setInitialFileName(theStage.getTitle());

        theDataFile = fileChooser.showSaveDialog(new Stage());

        if (theDataFile != null) {
            out = new Output(theDataFile);
            out.persist(dataText.getText());
            theStage.setTitle(theDataFile.getName());
        }

    }

    public void saveData() throws IOException {

        System.out.println("Save Selected");

        if (theDataFile != null) {

            Output saveFile = new Output(theDataFile);
            saveFile.persist(dataText.getText());
            theStage.setTitle(theDataFile.getName());

        }

    }

    public void openFile() throws IOException {

        System.out.println("Open Selected");

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                                                 new FileChooser.ExtensionFilter("HTML Files", "*.htm"),
                                                 new FileChooser.ExtensionFilter("Java Files", "*.java")
        );
      
        fileChooser.setInitialFileName("*.txt");

        theDataFile = fileChooser.showOpenDialog(new Stage());

        if (theDataFile != null) {

            Input in = new Input(theDataFile);
            dataText.setText(in.readContents());
            System.out.println(theDataFile.getName());

            theStage.setTitle(theDataFile.getName());

        }

    }

    public void exit() {
        theStage.close();
    }

    public void findContent() {

    }

    public void setStage(Stage aStage) {
        theStage = aStage;
    }

}
