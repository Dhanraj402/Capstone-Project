import java.util.Base64;

public class Base64Decoder {

    public static String decodeBase64(String encodedString) {
        // Decode the Base64 encoded string
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static void main(String[] args) {
        // Example encoded string (Note: Replace with your actual Base64 encoded string)
        String encodedString = "$10$V/rxGRd4/31laKW6Z5Qdz.4MYSGEqHtZLCa9PWp/vIj0Nk9IA5Roa"; // "Hello, world!" encoded in Base64

        // Decode the Base64 encoded string
        String decodedString = decodeBase64(encodedString);
        System.out.println("Decoded string: " + decodedString);
    }
}