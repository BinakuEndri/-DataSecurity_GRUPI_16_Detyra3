package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ChooseFileController {

    @FXML
    private Label chooseTextFile;

    private File file;

    private MainViewController mainViewController;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
    private boolean textFileChosen;

    @FXML
    void browseAllFiles(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        this.file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            textFileChosen = false;
            mainViewController.setTheFile(file);
            mainViewController.setTxtFileChosen(textFileChosen);
            closeStage();
            mainViewController.setTextToPlainText();
        }
        else {
            textFileChosen =false;
            mainViewController.setTxtFileChosen(textFileChosen);
        }
    }
    @FXML
    void browseTextFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        this.file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            textFileChosen = true;
            mainViewController.setTheFile(file);
            mainViewController.setTxtFileChosen(textFileChosen);
            closeStage();
            mainViewController.setTextToPlainText();
        }
        else {
            textFileChosen =false;
            mainViewController.setTxtFileChosen(textFileChosen);
        }
    }

    private void closeStage() {
        Stage stage = (Stage) chooseTextFile.getScene().getWindow();
        stage.close();
    }
}
