package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainViewController {
    @FXML
    private ChoiceBox<?> choseDecryptionMethod;

    @FXML
    private ChoiceBox<?> choseEncryptionMethod;

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
    private TextArea plainTextChoseFile;

    @FXML
    private Button saveDecryptedFile;

    @FXML
    private Button saveEncryptedFile;





}
