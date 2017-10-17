package secureCommunication;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * This class provides a variety of static methods that help in the sending and receiving of the message. These
 * methods are used by both Alice and Bob. 
 * 
 * @author Robert
 *
 */
public class MessageTools {
	
	/**
	 * This method retrieves the Public Key from the desired file path to be used in both Alice's and Bob's
	 * message classes.
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey retrievePubKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		// Create a new File
		File keyFile = new File(filename);
		
		// Create Input and Data Stream to read the file information.
		FileInputStream fileInput = new FileInputStream(keyFile);
		DataInputStream payloadInput = new DataInputStream(fileInput);
		
		// Create a Byte array to store the Public Key Bytes in it.
		byte[] keyBytes = new byte[(int) keyFile.length()];
		payloadInput.readFully(keyBytes);
		payloadInput.close();

		// Generate the Public Key from the Data and the KeySpecs.
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory key = KeyFactory.getInstance("RSA");
		return key.generatePublic(keySpec);
	}

	/**
	 * This method retrieves the Private Key from the desired file path to be used in both Alice's and Bob's
	 * message classes.
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws Exception
	 */
	public static PrivateKey retrievePrivKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		// Create a new File
		File keyFile = new File(filename);
		
		// Create Input and Data Stream to read the file information.
		FileInputStream fileInput = new FileInputStream(keyFile);
		DataInputStream payloadInput = new DataInputStream(fileInput);
		
		// Create a Byte array to store the Private Key Bytes in it.
		byte[] keyBytes = new byte[(int) keyFile.length()];
		payloadInput.readFully(keyBytes);
		payloadInput.close();

		// Generate the Private Key from the Data and the KeySpecs.
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory key = KeyFactory.getInstance("RSA");
		return key.generatePrivate(keySpec);
	}
	
	/**
	 * This method takes in a String of information, and turns it into a SHA1 Hash.
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateSHA1Hash(String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
		sha1Digest.update(data.getBytes("UTF-8"));
		return sha1Digest.digest();
	}

	/**
	 * This method converted an inputed byte array into Hexadecimal format. It is primarily used in the VPrint
	 * methods in both the Bob and Alice classes.
	 * 
	 * @param bytes
	 * @return
	 */
	public static String generateHex(byte[] byteArray) {
		return new BigInteger(1, byteArray).toString(16);
	}

}
