package Ciphers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CaesarCipher {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static void main(String[] args) {
        String filePath = "C:/Users/Admin/Desktop/MyCV_SHQIP.pdf";
        int shift = 3;

        try {
            String encodedFilePath = Base64UtilClass.encode(filePath);
            String encryptedFilePath = encrypt(encodedFilePath, shift, "C:\\Users\\Admin\\Desktop\\prova.txt");
            String decryptedFilePath = decrypt(encryptedFilePath, shift, "C:\\Users\\Admin\\Desktop\\prov.txt");
            Base64UtilClass.decode(decryptedFilePath,"C:\\Users\\Admin\\Desktop\\result.pdf" );
            System.out.println(encodedFilePath.equals(decryptedFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String encrypt(String filePath, int shift, String encryptedFilePath) throws IOException {
        String fileContents = filePath;
        String encryptedFileContents = caesarCipher_e(fileContents, shift);
        writeFile(encryptedFilePath, encryptedFileContents);
        System.out.println("File encrypted! Encrypted file saved at: " + encryptedFilePath);
        return encryptedFileContents;
    }

    public static String decrypt(String filePath, int shift, String decryptedFilePath) throws IOException {
        String fileContents = filePath;
        String decryptedFileContents = caesarCipher_d(fileContents, shift);
        writeFile(decryptedFilePath, decryptedFileContents);
        System.out.println("File decrypted! Decrypted file saved at: " + decryptedFilePath);
        return decryptedFileContents;
    }

    // create encryptData() method for encrypting user input string with given shift key
    public static String caesarCipher_e(String inputStr, int shiftKey) {


        // convert inputStr into lower case

        // encryptStr to store encrypted data
        String encryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {
            // get position of each character of inputStr in ALPHABET
            int pos = ALPHABET.indexOf(inputStr.charAt(i));

            // get encrypted char for each char of inputStr
            int encryptPos = (shiftKey + pos) % 64;
            char encryptChar = ALPHABET.charAt(encryptPos);

            // add encrypted char to encrypted string
            encryptStr += encryptChar;
        }

        // return encrypted string
        return encryptStr;

    }

    public static String caesarCipher_d(String inputStr, int shiftKey) {
        // convert inputStr into lower case


        // decryptStr to store decrypted data
        String decryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {

            // get position of each character of inputStr in ALPHABET
            int pos = ALPHABET.indexOf(inputStr.charAt(i));

            // get decrypted char for each char of inputStr
            int decryptPos = (pos - shiftKey) % 64;

            // if decryptPos is negative
            if (decryptPos < 0) {
                decryptPos = ALPHABET.length() + decryptPos;
            }
            char decryptChar = ALPHABET.charAt(decryptPos);

            // add decrypted char to decrypted string
            decryptStr += decryptChar;
        }
        // return decrypted string
        return decryptStr;

    }

    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
    public static String caesarCipher(String plaintext, int shift) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = 'a';
                if (Character.isUpperCase(c)) {
                    base = 'A';
                }
                int charValue = c - base;
                int shiftedValue = (charValue + shift + 26) % 26;
                char shiftedChar = (char) (shiftedValue + base);
                ciphertext.append(shiftedChar);
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }
}
