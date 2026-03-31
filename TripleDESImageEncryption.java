import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class TripleDESImageEncryption {

    public static void main(String[] args) {
        try {
            // Step 1: Read Image
            BufferedImage image = ImageIO.read(new File("input.jpg"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();

            // Step 2: Generate Triple DES Key
            KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
            keyGen.init(168);
            SecretKey key = keyGen.generateKey();

            // Step 3: Initialization Vector (IV)
            byte[] iv = new byte[8]; // DES block size

            // Step 4: Encrypt using Triple DES
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] encryptedBytes = cipher.doFinal(imageBytes);

            // 🔥 Save encrypted image (visual noise)
            FileOutputStream fosImg = new FileOutputStream("encrypted.jpg");
            fosImg.write(encryptedBytes);
            fosImg.close();

            // Save encrypted data file
            FileOutputStream fos = new FileOutputStream("encrypted.des");
            fos.write(encryptedBytes);
            fos.close();

            // Step 5: Decrypt using same key and IV
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Step 6: Save decrypted image
            FileOutputStream fos2 = new FileOutputStream("decrypted.jpg");
            fos2.write(decryptedBytes);
            fos2.close();

            System.out.println("✅ Encryption & Decryption Successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}