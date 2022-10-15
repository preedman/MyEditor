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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;

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

    // FXML for Search Dialog
    @FXML
    private TextField searchTF;

    @FXML
    private Button searchBT;

    @FXML
    private Button nextBT;

    private Stage searchScreen;

    //////////////////////////////////
    private Output out;

    private File theDataFile;

    private Stage theStage;

    private String fontFamily = "Arial";
    private double fontSize = 13;

    private Font theFont;

    private Text theContent;

    //  private TextInputDialog searchDialog;
    private static Properties prop;

    private TextInputDialog multiSearchDialog;

    protected static final org.apache.log4j.Logger uiControllerLogger = LogManager.getLogger(UIController.class.getName());

    private static org.apache.log4j.Logger logger = uiControllerLogger;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        logger.info("Enter UI Controller");

        readProperties();

        theFont = Font.font(fontFamily, fontSize);

        dataText.setFont(theFont);

        theContent = new Text();

        saveAsMenuItem = new MenuItem();

        saveAsMenuItem.setOnAction(e -> {
            try {
                saveAsData();
            } catch (IOException ex) {
                logger.error("Save as data failed " + ex.getMessage());
            }
        });

        openMenuItem = new MenuItem();

        openMenuItem.setOnAction(e -> {
            try {
                openFile();
            } catch (IOException ex) {
                logger.error("Open File Failed " + ex.getMessage());
            }
        });

        saveMenuItem = new MenuItem();

        saveMenuItem.setOnAction(e -> {
            try {
                saveData();
            } catch (IOException ex) {
                logger.error("Save Data Failed " + ex.getMessage());
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

            try {
                findContent();
            } catch (IOException ex) {
                logger.error("Find Content failed " + ex.getMessage());
            }

        });

        aboutMenuItem = new MenuItem();

        aboutMenuItem.setOnAction(e -> {

            showAbout();

        });

        logger.info("Exit UI Controller");

    }

    public void newData() {

        logger.info("New data enter");

        dataText.setText("");

        theStage.setTitle("My Text Editor");

        logger.info("New data exit");

    }

    public void saveAsData() throws IOException {
        logger.info("save as enter");

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

        logger.info("save as exit");

    }

    public void saveData() throws IOException {

        logger.info("Save enter");

        if (theDataFile != null) {

            Output saveFile = new Output(theDataFile);
            saveFile.persist(dataText.getText());
            theStage.setTitle(theDataFile.getName());

        }
        logger.info("Save exit");

    }

    public void openFile() throws IOException {

        logger.info("Open file enter");

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

            theStage.setTitle(theDataFile.getName());

        }

        logger.info("open file exit");

    }

    public void exit() {
        logger.info("exit enter");
        theStage.close();
        logger.info("exit exit");
    }

    public void searchText() {

        System.out.println("Search Button");

    }

    public void findContent() throws IOException {

        logger.info("find content enter");

        //Create Stage
        searchScreen = new Stage();
        searchScreen.setTitle("Search");

//Create view from FXML
        FXMLLoader loader = new FXMLLoader(UIController.class.getResource("find.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        SearchController theSearchController = loader.<SearchController>getController();

        theSearchController.setController(UIController.this);

        //  theSearchController.setTextOfFile(dataText.getText());
//Set view in window
        searchScreen.setScene(scene);

//Launch
        searchScreen.showAndWait();

        logger.info("Find content exit");

    }

    public String getText() {
        return dataText.getText();
    }

    public void highlightText(int textPosition) {

        logger.info("Highlight text enter");
        dataText.positionCaret(textPosition);
        dataText.selectNextWord(); // select the word
        dataText.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick;"); // highlight it.
        logger.info("Highlight text exit");
    }

    public void findAll() {

        //Creating a dialog
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title

        Label label1 = new Label("Find: ");
        TextField text1 = new TextField();
        //   Button closeButton = new Button();
        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        //   grid.add(closeButton, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setTitle("Dialog");
        ButtonType close = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
        ButtonType find = new ButtonType("Find", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(find);
        dialog.getDialogPane().getButtonTypes().add(close);
        dialog.initModality(Modality.APPLICATION_MODAL);
        //  closeButton.setCancelButton(true);
        //Setting the content of the dialog
        Button closeButton = (Button) dialog.getDialogPane().lookupButton(close);
        Button findButton = (Button) dialog.getDialogPane().lookupButton(find);
        dialog.setContentText("This is a sample dialog");

        dialog.show();

        closeButton.setOnAction((ActionEvent e) -> {
            //dial.close();

            System.out.println("close hit");
            dialog.close();
        });

        findButton.setOnAction((ActionEvent e) -> {
            //dial.close();

            System.out.println("find hit");
            Find f = new Find(dataText.getText());
            if (f.positionOf(text1.getText()) > 0) {   // some text was found

                System.out.println(text1.getText().length());
                System.out.println(f.positionOf(text1.getText()));

                dataText.positionCaret(f.positionOf(text1.getText()));  // put the cursor at the start of the text
                dataText.selectNextWord(); // select the word

                dataText.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick;"); // highlight it.
            } else {
                dataText.positionCaret(0);  // not found - position at beginning
            }

            dialog.show();

        });

    }

    public void setStage(Stage aStage) {
        theStage = aStage;
    }

    public void showAbout() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Simple Text Editor - JavaFX" + " version " + prop.getProperty("version"));
        alert.showAndWait();
    }

    private static void readProperties() {

        logger.info("Enter read properties");

        try ( InputStream input = UIController.class.getClassLoader().getResourceAsStream("editor.properties")) {

            prop = new Properties();

            if (input == null) {
                logger.error("Unable to find properties file");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            logger.info("Software version " + prop.getProperty("version"));

        } catch (IOException ex) {
            logger.error(SEVERE + ex.getMessage());
        }
    }

    private void loadFileToTextArea(File fileToLoad) {
        
        logger.info("Load File to Text Area");
        Task<String> loadTask = fileLoaderTask(fileToLoad);

        loadTask.run();
        
        logger.info("exit Load file to text area");

    }

    private Task<String> fileLoaderTask(File fileToLoad) {
        
        logger.info("Enter file loader task");
        //Create a task to load the file asynchronously
        Task<String> loadFileTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));
                //Use Files.lines() to calculate total lines - used for progress
                long lineCount;
                try ( Stream<String> stream = Files.lines(fileToLoad.toPath())) {
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
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Fatal error " + e.getMessage());
                dataText.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
            }

        });
        //If unsuccessful, set text area with error message and status message to failed
        loadFileTask.setOnFailed(workerStateEvent -> {
            logger.error("Could not load file from:\n " + fileToLoad.getAbsolutePath());
            dataText.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());

        });
        logger.info("Exit Load File task");
        return loadFileTask;
    }

}
