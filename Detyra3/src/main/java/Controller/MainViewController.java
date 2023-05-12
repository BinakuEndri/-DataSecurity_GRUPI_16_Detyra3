package Controller;

import Ciphers.Base64UtilClass;
import Ciphers.CaesarCipher;
import Ciphers.VigenèreCipher;
import javafx.event.ActionEvent;
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
    private ChoiceBox<String> choseEncryptionMethod;

    @FXML
    private TextArea encryptedText;

    @FXML
    private TextField keyLengthEncryption;

    @FXML
    private TextArea plainText;

    @FXML
    private ChoiceBox<String> choseAction;

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
        this.choseAction.getItems().addAll("Encrypt","Decrypt");

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
            if(theFile != null) {
                String path = theFile.getAbsolutePath();
                try {
                    String encoded = Base64UtilClass.encode(path);
                    plainText.setText(encoded);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    void encryptText(ActionEvent event) {
        if(!this.plainText.getText().isEmpty() && !this.keyLengthEncryption.getText().isEmpty() && this.choseEncryptionMethod.getValue() != null) {
            try {
                setTextToEnryptedText();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Fill all the fields",ButtonType.CLOSE);
            alert.show();
        }
    }
    @FXML
    void decryptText(ActionEvent actionEvent){

    }

    @FXML
    void saveEncryptedFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("*","."+getFileExtension(theFile));
        fileChooser.getExtensionFilters().add(extensionFilter);
        File filedDest = fileChooser.showSaveDialog(new Stage());
        try {
        if(txtFileChosen){
            CaesarCipher.writeFile(filedDest.getAbsolutePath(),this.encryptedText.getText());
        }
        else {
            Base64UtilClass.decode(this.encryptedText.getText(),filedDest.getAbsolutePath());
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTextToEnryptedText() throws IOException {
        String value = choseEncryptionMethod.getValue();
        String text = plainText.getText();
        if(txtFileChosen){
            if(value == "Cesar"){
                String encryptedText1 = CaesarCipher.caesarCipher(text,
                        Integer.parseInt(this.keyLengthEncryption.getText()));
                this.encryptedText.wrapTextProperty().set(true);
                this.encryptedText.setText(encryptedText1);
            } else {
                String encrypted = VigenèreCipher.encrypt(text,keyLengthEncryption.getText());
                encryptedText.setText(encrypted);
            }
        }else {
            if(value == "Cesar"){
                String encryptFileCeasar = CaesarCipher.encrypt(this.plainText.getText(),Integer.parseInt(keyLengthEncryption.getText()));
                encryptedText.setText(encryptFileCeasar);
            } else {
                String encryptFileViginiere = VigenèreCipher.encrypt_file(keyLengthEncryption.getText(),plainText.getText());
                encryptedText.setText(encryptFileViginiere);
            }
        }
    }

    public void setPromptToKey(ChoiceBox<String> choseMethod, TextField keyLength){
        choseMethod.setOnAction(actionEvent -> {
            keyLength.editableProperty().set(true);

            if(choseMethod.getValue() == "Cesar"){
                keyLength.setPromptText("0-64");
                validateKeyLengthEncryption(this.choseEncryptionMethod,this.keyLengthEncryption);

            }else {
                keyLength.setPromptText("Write Letters only");
                validateKeyLengthEncryption(this.choseEncryptionMethod,this.keyLengthEncryption);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValueToChoiceBoxes();
        setPromptToKey(this.choseEncryptionMethod,this.keyLengthEncryption);
    }
    public void validateKeyLengthEncryption(ChoiceBox<String> choseMethod,TextField keyLength){
                if (choseMethod.getValue() == "Cesar"){
                    keyLength.setOnKeyPressed(e1-> {
                        if(!e1.getCode().isDigitKey() && e1.getCode() != KeyCode.BACK_SPACE){
                            e1.consume();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Key should be number!");
                            alert.show();
                        }
                    });
            }else {
                    keyLength.setOnKeyPressed(e1 -> {
                        if (!e1.getCode().isLetterKey() && e1.getCode() != KeyCode.BACK_SPACE) {
                            e1.consume();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Key should be a letter(also not spaces)!");
                            alert.show();
                        }
                    });
                }
    }

    String getFileExtension(File file) {
        if (file == null) {
            return "";
        }
        String name = file.getName();
        int i = name.lastIndexOf('.');
        String ext = i > 0 ? name.substring(i + 1) : "";
        return ext;
    }
}
