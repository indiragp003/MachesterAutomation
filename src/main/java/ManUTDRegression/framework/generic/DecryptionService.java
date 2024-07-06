package ManUTDRegression.framework.generic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecryptionService {

	final static Logger log = LogManager.getLogger(DecryptionService.class);
	private static final String encryptionKey           = "DXCIAS#256AES";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithm = "AES";
	private static final String encryptionPrefix = "{$encrypt$}";
    private static byte[] iv = new byte[16];
    
    
    public String checkEncryption(String inputValue) {
    	if(inputValue.contains(encryptionPrefix)) {
    		inputValue = decrypt(inputValue);
    	}
    	return inputValue;
    }
    
    public String decrypt(String encryptedText) {
        String decryptedText = "";
        try {
        	encryptedText = encryptedText.substring(encryptionPrefix.length());
            MessageDigest sha = null;
			Cipher cipher = Cipher.getInstance(cipherTransformation);
			byte[] key = encryptionKey.getBytes(characterEncoding);
			sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(key);
			SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithm);
			IvParameterSpec ivparameterspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            System.err.println("decrypt Exception : "+E.getMessage());
        }
        return decryptedText;
    }
}
