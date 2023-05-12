package Controller;

import Ciphers.CaesarCipher;
import Ciphers.VigenèreCipher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainViewController implements Initializable {
    @FXML
    private ChoiceBox<String> choseDecryptionMethod;

    @FXML
    private ChoiceBox<String> choseEncryptionMethod;

    @FXML
    private TextArea decryptedText;

    @FXML
    private TextArea encryptedChoseFile;

    @FXML
    private TextArea encryptedText;

    @FXML
    private TextField keyLengthDecryption;

    @FXML
    private TextField keyLengthEncryption;

    @FXML
    private TextArea plainText;

    @FXML
    private Button saveDecryptedFile;

    @FXML
    private Button saveEncryptedFile;

    @FXML
    private File theFile;

    private boolean txtFileChosen;

    public void setTheFile(File theFile) {
        this.theFile = theFile;
    }

    public void setTxtFileChosen(boolean txtFileChosen) {
        this.txtFileChosen = txtFileChosen;
    }

    public void setValueToChoiceBoxes(){
        this.choseEncryptionMethod.getItems().addAll("Cesar","Vigenère");
        this.choseDecryptionMethod.getItems().addAll("Cesar","Vigenère");

    }

    @FXML
    public void browseFiles(){
        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("choseFile.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ChoseFileController choseFileController = fxmlLoader.getController();
            choseFileController.setMainViewController(this);
            Stage stage = new Stage();
            Scene scene = new Scene(root,500,300);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTextToPlainText(){
        if(txtFileChosen){
            FileInputStream fin;
            Scanner sc = new Scanner(System.in);

            String fullFile = "";
            try {
                fin = new FileInputStream(this.theFile);
                Scanner fp = new Scanner(fin);
                while (fp.hasNextLine()) {
                    String line = fp.nextLine();
                    fullFile += line;
                }
                this.plainText.wrapTextProperty().set(true);
                this.plainText.setText(fullFile);

            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
           // Write the base64 encoded text to Text Area          // base64
        }
    }
    public void setTextToEnryptedText(){
        String value = choseEncryptionMethod.getValue();
        String text = plainText.getText();
        if(txtFileChosen){
            if(value == "Cesar"){
                String encryptedText1 = CaesarCipher.caesarCipher(text,
                        Integer.parseInt(this.keyLengthEncryption.getText()));
                this.encryptedText.wrapTextProperty().set(true);
                this.encryptedText.setText(encryptedText1);
            } else {
                //Wirte to encryptedArea  Viginere
            }
        }else {
            if(value == "Cesar"){
                //Write to encryptedArea the  Cesar with Base64
            } else {
                //Wirte to encryptedArea the encryptet Viginere with Base64
            }
        }
    }

    public void setPromptToKey(){
        this.choseEncryptionMethod.setOnAction(actionEvent -> {
            this.keyLengthEncryption.editableProperty().set(true);

            if(choseEncryptionMethod.getValue() == "Cesar"){
                this.keyLengthEncryption.setPromptText("0-64");
                validateKeyLengthEncryption();

            }else {
                this.keyLengthEncryption.setPromptText("Write Letters only");
                validateKeyLengthEncryption();
            }
        });
    }
    @FXML
    public void saveEncryptedFile(){
        FileChooser fileChooser = new FileChooser();
        System.out.println(theFile.getAbsolutePath());
        theFile = fileChooser.showSaveDialog(new Stage());

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValueToChoiceBoxes();
        setPromptToKey();

    }

    public void validateKeyLengthEncryption(){
                if (choseEncryptionMethod.getValue() == "Cesar"){
                    this.keyLengthEncryption.setOnKeyPressed(e1-> {
                        if(!e1.getCode().isDigitKey() && e1.getCode() != KeyCode.BACK_SPACE){
                            e1.consume();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Key should be number!");
                            alert.show();
                        }
                    });
            }else {
                    this.keyLengthEncryption.setOnKeyPressed(e1 -> {
                        if (!e1.getCode().isLetterKey() && e1.getCode() != KeyCode.BACK_SPACE) {
                            e1.consume();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Key should be a letter(also not spaces)!");
                            alert.show();
                        }
                    });
                }
    }
}
