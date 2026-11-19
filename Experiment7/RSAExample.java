
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RSAExample {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        try {
            // Step 1: Generate RSA Key Pair
            System.out.println("STEP 1: Generating RSA 2048-bit Key Pair");

            KeyPairGenerator keyPairGen =
                    KeyPairGenerator.getInstance("RSA");

            keyPairGen.initialize(2048);

            KeyPair keyPair = keyPairGen.generateKeyPair();

            System.out.println("RSA keys generated successfully.\n");

            // Step 2: Original Message
            String originalMessage =
                    "Hello! This is an RSA encryption demo.";

            byte[] plaintext =
                    originalMessage.getBytes(StandardCharsets.UTF_8);

            System.out.println("STEP 2: Original Message");
            System.out.println(originalMessage + "\n");

            // Step 3: Configure RSA OAEP with SHA-256
            String transformation =
                    "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

            Cipher cipher = Cipher.getInstance(transformation);

            OAEPParameterSpec oaepSpec =
                    new OAEPParameterSpec(
                            "SHA-256",
                            "MGF1",
                            MGF1ParameterSpec.SHA256,
                            PSource.PSpecified.DEFAULT
                    );

            // Step 4: Encrypt using Public Key
            System.out.println("STEP 3: RSA Encryption");

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    keyPair.getPublic(),
                    oaepSpec
            );

            byte[] ciphertext = cipher.doFinal(plaintext);

            System.out.println("Encrypted message (Hex):");
            System.out.println(bytesToHex(ciphertext));

            System.out.println(
                    "Ciphertext length: " +
                    ciphertext.length + " bytes\n"
            );

            // Step 5: Decrypt using Private Key
            System.out.println("STEP 4: RSA Decryption");

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    keyPair.getPrivate(),
                    oaepSpec
            );

            byte[] decryptedBytes = cipher.doFinal(ciphertext);

            String decryptedMessage =
                    new String(decryptedBytes, StandardCharsets.UTF_8);

            System.out.println("Decrypted message:");
            System.out.println(decryptedMessage + "\n");

            // Step 6: Verify Original and Decrypted Messages
            System.out.println("STEP 5: Verification");

            boolean match = originalMessage.equals(decryptedMessage);

            System.out.println("Original and decrypted messages match: " + match);

            if (match) {
                System.out.println("RSA EXPERIMENT COMPLETED SUCCESSFULLY");
            } else {
                System.out.println("RSA EXPERIMENT FAILED");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
