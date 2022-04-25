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
import com.reedmanit.myeditor.util.Find;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

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
    private TextFlow dataTextFlow;

    @FXML
    private MenuItem findMenuItem;

    @FXML
    private Label fileNameLabel;

    @FXML
    private TextField fileNameTF;

    @FXML
    private MenuItem aboutMenuItem;

    private Output out;

    private File theDataFile;

    private Stage theStage;

    private String fontFamily = "Arial";
    private double fontSize = 13;

    private Font theFont;

    private Text theContent;

    private TextInputDialog searchDialog;
    
    private static Properties prop;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        readProperties();

        theFont = Font.font(fontFamily, fontSize);

        dataText.setFont(theFont);

        theContent = new Text();

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

        aboutMenuItem = new MenuItem();

        aboutMenuItem.setOnAction(e -> {

            showAbout();

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
            
              loadFileToTextArea(theDataFile);

     
            System.out.println(theDataFile.getName());

            theStage.setTitle(theDataFile.getName());

        }

    }

    public void exit() {
        theStage.close();
    }

    public void findContent() {

        searchDialog = new TextInputDialog();
        searchDialog.setTitle("Search Dialog");
        searchDialog.setHeaderText("Find text");
        searchDialog.setContentText("Find ?");

        Optional<String> result = searchDialog.showAndWait();

        if (result.isPresent()) {
            Find f = new Find(dataText.getText());

            if (f.positionOf(result.get()) > 0) {   // some text was found
                
                System.out.println(result.get().length());
                System.out.println(f.positionOf(result.get()));
               
                dataText.positionCaret(f.positionOf(result.get()));  // put the cursor at the start of the text
                dataText.selectNextWord(); // select the word

                dataText.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick;"); // highlight it.
            } else {
                dataText.positionCaret(0);  // not found - position at beginning
            }
            

        }

    }

    public void setStage(Stage aStage) {
        theStage = aStage;
    }

    public void showAbout() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Simple Text Editor - JavaFX" + " version " + prop.getProperty("version") );
        alert.showAndWait();
    }
    
    private static void readProperties() {
        try (InputStream input = UIController.class.getClassLoader().getResourceAsStream("editor.properties")) {

            prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println(prop.getProperty("version"));
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadFileToTextArea(File fileToLoad) {
        Task<String> loadTask = fileLoaderTask(fileToLoad);
   
        loadTask.run();
        
    }
    
    private Task<String> fileLoaderTask(File fileToLoad) {
        //Create a task to load the file asynchronously
        Task<String> loadFileTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));
                //Use Files.lines() to calculate total lines - used for progress
                long lineCount;
                try (Stream<String> stream = Files.lines(fileToLoad.toPath())) {
                    lineCount = stream.count();
                }
                //Load in all lines one by one into a StringBuilder separated by "\n" - compatible with TextArea
                String line;
                StringBuilder totalFile = new StringBuilder();
                long linesLoaded = 0;
                while ((line = reader.readLine()) != null) {
                    totalFile.append(line);
                    totalFile.append("\n");
                    updateProgress(++linesLoaded, lineCount);
                }
                return totalFile.toString();
            }
        };
        //If successful, update the text area, display a success message and store the loaded file reference
        loadFileTask.setOnSucceeded(workerStateEvent -> {
            try {
                dataText.setText(loadFileTask.get());
             //   statusMessage.setText("File loaded: " + fileToLoad.getName());
            //    loadedFileReference = fileToLoad;
           //     lastModifiedTime = Files.readAttributes(fileToLoad.toPath(), BasicFileAttributes.class).lastModifiedTime();
            } catch (InterruptedException | ExecutionException  e) {
                Logger.getLogger(getClass().getName()).log(SEVERE, null, e);
                dataText.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
            }
          //  scheduleFileChecking(loadedFileReference);
        });
        //If unsuccessful, set text area with error message and status message to failed
        loadFileTask.setOnFailed(workerStateEvent -> {
            dataText.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
          //  statusMessage.setText("Failed to load file");
        });
        return loadFileTask;
    }

}
