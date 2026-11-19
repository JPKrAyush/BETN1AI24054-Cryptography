
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DiffieHellmanExample {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

    // HKDF Extract phase
    public static byte[] hkdfExtract(byte[] salt, byte[] ikm)
            throws Exception {

        Mac mac = Mac.getInstance("HmacSHA256");

        if (salt == null || salt.length == 0) {
            salt = new byte[32];
        }

        mac.init(new SecretKeySpec(salt, "HmacSHA256"));

        return mac.doFinal(ikm);
    }

    // HKDF Expand phase
    public static byte[] hkdfExpand(
            byte[] prk, byte[] info, int outputLengthBytes)
            throws Exception {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(prk, "HmacSHA256"));

        ByteArrayOutputStream okm = new ByteArrayOutputStream();

        byte[] t = new byte[0];
        int hashLen = 32;

        int iterations =
                (int) Math.ceil((double) outputLengthBytes / hashLen);

        for (int i = 1; i <= iterations; i++) {
            mac.reset();
            mac.update(t);

            if (info != null && info.length > 0) {
                mac.update(info);
            }

            mac.update((byte) i);
            t = mac.doFinal();
            okm.write(t);
        }

        byte[] result = okm.toByteArray();

        return Arrays.copyOfRange(result, 0, outputLengthBytes);
    }

    // Derive a 256-bit AES key
    public static byte[] derive256BitAesKey(
            byte[] rawSharedSecret, String contextInfo)
            throws Exception {

        byte[] info = contextInfo.getBytes(StandardCharsets.UTF_8);

        byte[] prk = hkdfExtract(null, rawSharedSecret);

        return hkdfExpand(prk, info, 32);
    }

    public static void main(String[] args) {

        try {
            // Step 1: Generate DH parameters
            System.out.println("STEP 1: Generating 2048-bit DH parameters");

            AlgorithmParameterGenerator paramGen =
                    AlgorithmParameterGenerator.getInstance("DH");

            paramGen.init(2048, new SecureRandom());

            AlgorithmParameters params = paramGen.generateParameters();

            DHParameterSpec dhSpec =
                    params.getParameterSpec(DHParameterSpec.class);

            System.out.println("DH Parameters generated successfully.");
            System.out.println(
                    "Prime modulus bit length: "
                    + dhSpec.getP().bitLength());

            // Step 2: Initialize key pair generator
            KeyPairGenerator dhKeyPairGen =
                    KeyPairGenerator.getInstance("DH");

            dhKeyPairGen.initialize(dhSpec);

            // Step 3: Alice generates her keys
            System.out.println("\nSTEP 2: Alice generates her key pair");

            KeyPair aliceKeyPair =
                    dhKeyPairGen.generateKeyPair();

            System.out.println("Alice's key pair generated.");

            // Step 4: Bob generates his keys
            System.out.println("\nSTEP 3: Bob generates his key pair");

            KeyPair bobKeyPair =
                    dhKeyPairGen.generateKeyPair();

            System.out.println("Bob's key pair generated.");

            // Step 5: Alice calculates shared secret
            System.out.println("\nSTEP 4: Computing shared secrets");

            KeyAgreement aliceAgreement =
                    KeyAgreement.getInstance("DH");

            aliceAgreement.init(aliceKeyPair.getPrivate());

            aliceAgreement.doPhase(bobKeyPair.getPublic(), true);

            byte[] aliceRawSecret =
                    aliceAgreement.generateSecret();

            System.out.println(
                    "Alice's shared secret generated: "
                    + aliceRawSecret.length + " bytes");

            // Step 6: Bob calculates shared secret
            KeyAgreement bobAgreement =
                    KeyAgreement.getInstance("DH");

            bobAgreement.init(bobKeyPair.getPrivate());

            bobAgreement.doPhase(aliceKeyPair.getPublic(), true);

            byte[] bobRawSecret =
                    bobAgreement.generateSecret();

            System.out.println(
                    "Bob's shared secret generated: "
                    + bobRawSecret.length + " bytes");

            // Step 7: Derive AES keys using HKDF-SHA256
            System.out.println(
                    "\nSTEP 5: Deriving 256-bit AES keys using HKDF-SHA256");

            String kdfContext =
                    "CSL0509-Lab-Exp07-DH-AES-KeyDerivation";

            byte[] aliceAesKey =
                    derive256BitAesKey(aliceRawSecret, kdfContext);

            byte[] bobAesKey =
                    derive256BitAesKey(bobRawSecret, kdfContext);

            System.out.println("\nAlice's Derived AES Key:");
            System.out.println(bytesToHex(aliceAesKey));

            System.out.println("\nBob's Derived AES Key:");
            System.out.println(bytesToHex(bobAesKey));

            // Step 8: Verify the derived keys
            System.out.println("\nSTEP 6: Verifying derived keys");

            boolean keysMatch =
                    Arrays.equals(aliceAesKey, bobAesKey);

            System.out.println("Shared keys match: " + keysMatch);

            if (keysMatch) {
                System.out.println(
                        "\nSUCCESS: Alice and Bob derived identical AES keys!");
                System.out.println(
                        "Secure key establishment completed.");
            } else {
                System.out.println(
                        "\nFAILURE: Derived keys do not match.");
            }

            System.out.println(
                    "\nDIFFIE-HELLMAN EXPERIMENT COMPLETED");

        } catch (Exception e) {
            System.err.println(
                    "Diffie-Hellman failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
