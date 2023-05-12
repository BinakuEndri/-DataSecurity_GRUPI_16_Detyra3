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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ChoiceBox<String> chooseCipher;

    @FXML
    private TextArea resultText;

    @FXML
    private TextField key;

    @FXML
    private TextArea plainText;

    @FXML
    private ChoiceBox<String> chooseAction;

    @FXML
    private ImageView image;
    @FXML
    private Button browseFilesBtn;
    @FXML
    private Button btnAction;
    @FXML
    private Button saveFiles;

    private File theFile;

    private boolean txtFileChosen;

    public void setTheFile(File theFile) {
        this.theFile = theFile;
    }

    public void setTxtFileChosen(boolean txtFileChosen) {
        this.txtFileChosen = txtFileChosen;
    }

    public void setValueToChoiceBoxes(){
        this.chooseCipher.getItems().addAll("Caesar","Vigenère");
        this.chooseAction.getItems().addAll("Encrypt","Decrypt");

    }



    @FXML
    public void browseFiles(){
        plainText.clear();
        resultText.clear();
        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("chooseFile.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ChooseFileController choseFileController = fxmlLoader.getController();
            choseFileController.setMainViewController(this);
            Stage stage = new Stage();
            Scene scene = new Scene(root,500,300);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.btnAction.setDisable(false);
    }

    public void setTextToPlainText(){
        if(txtFileChosen){
            FileInputStream fin;
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
                fp.close();
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
    void cipherAction(ActionEvent event) {

        if(!this.plainText.getText().isEmpty() && !this.key.getText().isEmpty() && this.chooseCipher.getValue() != null) {
            try {
                setTextToResultText();
                this.saveFiles.setDisable(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Fill all the fields!",ButtonType.CLOSE);
            alert.show();
        }
    }


    @FXML
    void saveFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("*","."+getFileExtension(theFile));
        fileChooser.getExtensionFilters().add(extensionFilter);
        File filedDest = fileChooser.showSaveDialog(new Stage());
        try {
        if(txtFileChosen){
            CaesarCipher.writeFile(filedDest.getAbsolutePath(),this.resultText.getText());
        }
        else {
            Base64UtilClass.decode(this.resultText.getText(),filedDest.getAbsolutePath());
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


// TTT
    public void setTextToResultText() throws IOException {
        String value = chooseCipher.getValue();
        String text = plainText.getText();
        if (chooseAction.getValue().equals("Encrypt")){
            if(txtFileChosen){
                if(value == "Caesar"){
                    String encryptedText1 = CaesarCipher.caesarCipher(text,
                            Integer.parseInt(this.key.getText()));
                    this.resultText.wrapTextProperty().set(true);
                    this.resultText.setText(encryptedText1);
                } else {
                    String encrypted = VigenèreCipher.encrypt(text, key.getText());
                    resultText.setText(encrypted);
                }
            }else {
                if(value == "Caesar"){
                    String encryptFileCaesar = CaesarCipher.encrypt(this.plainText.getText(),Integer.parseInt(key.getText()));
                    resultText.setText(encryptFileCaesar);
                } else {
                    String encryptFileVigenere = VigenèreCipher.encrypt_file(key.getText(),plainText.getText());
                    resultText.setText(encryptFileVigenere);
                }
            }
        }else{
            if(txtFileChosen){
                if(value == "Caesar"){
                    String decryptedText1 = CaesarCipher.caesarDecipher(text,
                            Integer.parseInt(this.key.getText()));
                    this.resultText.wrapTextProperty().set(true);
                    this.resultText.setText(decryptedText1);
                } else {
                    String decrypted = VigenèreCipher.decrypt(text, key.getText());
                    resultText.setText(decrypted);
                }
            }else {
                if(value == "Cesar"){
                    String decryptFileCaeasar = CaesarCipher.decrypt(text,Integer.parseInt(key.getText()));
                    resultText.setText(decryptFileCaeasar);
                } else {
                    String decryptFileVigenere = VigenèreCipher.decrypt_file(key.getText(),plainText.getText());
                    resultText.setText(decryptFileVigenere);
                }
            }
        }

    }

    public void setPromptToKey(ChoiceBox<String> choseMethod, TextField keyLength){
        choseMethod.setOnAction(actionEvent -> {
            keyLength.editableProperty().set(true);
            if(choseMethod.getValue() == "Caesar"){
                keyLength.setPromptText("Write Number");
                validateKeyLengthEncryption(this.chooseCipher,this.key);
                keyLength.clear();
                resultText.clear();
            }else {
                keyLength.setPromptText("Write Letters only");
                validateKeyLengthEncryption(this.chooseCipher,this.key);
                keyLength.clear();
                resultText.clear();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValueToChoiceBoxes();
        setPromptToKey(this.chooseCipher, this.key);
        update();
    }

    public void update(){
        this.chooseAction.setOnAction(e ->{
            this.chooseCipher.setDisable(false);
            this.browseFilesBtn.setDisable(false);
            if (this.chooseAction.getValue()!= null && this.chooseAction.getValue()== "Encrypt"){
                this.btnAction.setText("Encrypt");
                this.image.setImage(new
                        Image("C:\\Users\\Admin\\IdeaProjects\\DataSecurity_GRUPI_16_Detyra3\\Detyra3\\src\\main\\resources\\Images\\lock_480px.png"));
            }else if(this.chooseAction.getValue()!= null && this.chooseAction.getValue() =="Decrypt"){
                this.btnAction.setText("Decrypt");
                this.image.setImage(new
                        Image("C:\\Users\\Admin\\IdeaProjects\\DataSecurity_GRUPI_16_Detyra3\\Detyra3\\src\\main\\resources\\Images\\KeySecurity_480px.png"));
            }
        });
    }

    public void validateKeyLengthEncryption(ChoiceBox<String> choseMethod,TextField keyLength){
                if (choseMethod.getValue() == "Cesar"){
                    keyLength.setOnKeyPressed(e1-> {
                        if(!e1.getCode().isDigitKey() && e1.getCode() != KeyCode.BACK_SPACE){
                            e1.consume();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Only digits!");
                            alert.show();
                        }
                    });
            }else {
                    keyLength.setOnKeyPressed(e1 -> {
                        if (!e1.getCode().isLetterKey() && e1.getCode() != KeyCode.BACK_SPACE) {
                            e1.consume();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Only letters (also not spaces)!");
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
