package Ciphers;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64UtilClass {

    public static String encode(String filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        String encodedFileContents = Base64.getEncoder().encodeToString(fileBytes);
        return encodedFileContents;
    }

    public static void decode(String filePath, String where) throws IOException {
        String encodedFileContents = filePath;
        byte[] decodedImageBytes = Base64.getDecoder().decode(encodedFileContents);
        Files.write(Paths.get(where), decodedImageBytes);

    }
}
