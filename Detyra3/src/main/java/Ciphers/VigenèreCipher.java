package Ciphers;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;

public class VigenèreCipher {
//    public static void main(String arg[]){
//        Scanner sc = new Scanner(System.in);

//
   //     System.out.print("Enter the Encryption key : ");
 //       String key = sc.nextLine();
        // decrypt_file(key);
 //       try{
   //         String encoded = Base64UtilClass.encode("C:/Users/Admin/Desktop/MyCV_SHQIP.pdf");
  //          String en = encrypt_file(key, encoded,"C:\\Users\\Admin\\Desktop\\t1.txt" );
  //          String de = decrypt_file(key, en);
  //          System.out.println(de.equals(encoded));
  //          Base64UtilClass.decode(de, "C:\\Users\\Admin\\Desktop\\vig.pdf");
 //       }catch (IOException e){
 //           e.printStackTrace();
 //       }



        //System.out.print("Enter the String to be encrypted : ");
        //String inString = sc.nextLine();
        //String finalString = encrypt(inString, key);
        //System.out.print("\nThe encrypted String is : " + finalString);


    // method that removes all the numbers, punctuations
    static String remove_ichars(String original_msg){
        original_msg = original_msg.replaceAll("[^A-Z\n\r]", "");
        return original_msg;
    }

    public static String encrypt(String original_msg, String key){
        // allchars is our keyspace
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        int all_chars_length = allChars.length();
//        String encrypted_msg = "";

        // converting our string to uppercase
        // original_msg = original_msg.toUpperCase();

        // removing all numbers, punctuations
        // original_msg = remove_ichars(original_msg);

        // converting key to uppercase
        // key = key.toUpperCase();

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

        // allchars is our keyspace
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        int all_chars_length = allChars.length();
        String decrypted_msg = "";

        // converting our string to uppercase
        //encrypted_msg = encrypted_msg.toUpperCase();

        // removing all numbers, punctuations
        //encrypted_msg = remove_ichars(encrypted_msg);

        // converting key to uppercase
        // key = key.toUpperCase();

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

            System.out.println("File read successfully!!");
            fullFile = encrypt(encoded, key);
            System.out.println("Data encrypted successfully!!");
            System.out.println(fullFile);
        }catch (Exception e){
            e.printStackTrace();
        }

      //  try {

        //    File newTextFile = new File(pathToSaveEncrypted);
         //   FileWriter f1 = new FileWriter(newTextFile);
        //    f1.write(fullFile);
        //    f1.close();
       //     System.out.println("Encrypted file stored on Desktop");
      //  } catch (IOException ex) {
      //      ex.printStackTrace();
      //  }
        return fullFile;
    }

    public static String decrypt_file(String key,String decoded){

        String fullFile = "";

        try {

            System.out.println("File read successfully!!");
            fullFile = decrypt(decoded, key);
            System.out.println("Data decrypted successfully!!");
            System.out.println(fullFile);
        }catch (Exception e){
            e.printStackTrace();
        }

//        try {
//
//            File newTextFile = new File(pathToSaveDecrypted);
//            FileWriter f1 = new FileWriter(newTextFile);
//            f1.write(fullFile);
//            f1.close();
//            System.out.println("Decrypted file stored on Desktop");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        return fullFile;
    }
}