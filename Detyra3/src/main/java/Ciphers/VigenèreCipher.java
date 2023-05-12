package Ciphers;


public class VigenèreCipher {
private final static String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";


public static String encrypt(String original_msg, String key){
        // allchars is our keyspace
        int all_chars_length = allChars.length();

        StringBuilder encrypted_msg = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < original_msg.length(); i++) {
        char c = original_msg.charAt(i);
        int charIndex = allChars.indexOf(c);
        if (charIndex >= 0) {
        // Shift the character by the corresponding key character
        int keyCharIndex = allChars.indexOf(key.charAt(keyIndex));
        int shiftedIndex = (charIndex + keyCharIndex) % all_chars_length;
        if (shiftedIndex >= 0) {
        encrypted_msg.append(allChars.charAt(shiftedIndex));
        }

        // encrypted_msg.append(allChars.charAt(shiftedIndex));

        // Move to the next character in the key, wrapping around if necessary
        keyIndex = (keyIndex + 1) % key.length();
        } else {
        // Append non-alphabetic characters without modification
        encrypted_msg.append(c);
        }
        }

        return encrypted_msg.toString();
        }


public static String decrypt(String encrypted_msg, String key) {

        int all_chars_length = allChars.length();
        String decrypted_msg = "";


        int index = 0;
        // iterating over the encrypted message character by chracter and decrypting it and appending it to the new string
        for(int i=0; i< encrypted_msg.length(); i++){
        Character letter = encrypted_msg.charAt(i);
        if(allChars.indexOf(letter) >= 0) {
        int position = (allChars.indexOf(letter) - allChars.indexOf(key.charAt(index)) + all_chars_length) % all_chars_length;
        Character d_letter = allChars.charAt(position);
        decrypted_msg += d_letter;
        index++;
        if(index >= key.length()){
        index %= key.length();
        }
        }else{
        decrypted_msg += letter;
        index = 0;
        }
        }

        return decrypted_msg;
        }


public static String encrypt_file(String key, String encoded){
        String fullFile = "";
        try {
        fullFile = encrypt(encoded, key);
        }catch (Exception e){
        e.printStackTrace();
        }

        return fullFile;
        }

public static String decrypt_file(String key,String decoded){

        String fullFile = "";

        try {
        fullFile = decrypt(decoded, key);
        }catch (Exception e){
        e.printStackTrace();
        }

        return fullFile;
        }
        }