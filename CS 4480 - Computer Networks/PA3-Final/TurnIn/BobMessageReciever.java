package secureCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class acts as the listening portion of the secure message program. In this class Bob listens/waits 
 * for Alice message, decrpyts it, verifies its from her, and then displays it.
 * @author Robert Weischedel
 * CS 4480
 */
public class BobMessageReciever {
	
	// These member variables are used to specify which ports have been included.
	private static boolean vOptionEnabled = false;
	private static int port = -1;
	
	// These member variables are used to keep track of the encryption keys we need.
	private static PrivateKey bobPrivKey;
	private static PublicKey alicePubKey;
	private static PublicKey bobPubKey;
	
	// These member variables are used to help in the decryption of the message.
	private static byte[] payload;
	private static Cipher keyCipher;
	private static byte[] symKeyDecrpted;
	private static byte[] encryptedMessage;
	
	// These member variables are used to help retrieve the encryption keys.
	private static final String keyPath = System.getProperty("user.dir") + "/" + "PA3Keys/";
	private static String bobPrivKeyFilename = "bobPrivateKey.der";
	private static String bobPubKeyFilename = "bobPublicKey.der";
	private static final String bobCASignFilename = "BobCASignature";
	private static String alicePubKeyFilename = "alicePublicKey.der";
	
	/**
	 * This method starts the process of Bob Waiting for Alice's Message, all other methods are called from
	 * this method. It also checks the user input tags as well.
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		
		// Print out the System File Path in case of confusion
		vPrint("The filepath the program is set to: " + keyPath);
		
		// Parse through the user specified tags and determine what the user specs are.
		for (int i = 0; i < args.length; i++){
			if(args[i].equals("-p") && i <= args.length){
				try {
					port = Integer.parseInt(args[i+1]);
					} 
				catch(NumberFormatException e){
					printError("Not a Valid Port Number.");
					}
			}			
			if(args[i].equals("-v")){
				vOptionEnabled = true;
			}		
			if(args[i].equals("-h")){
				printHelp();
			}
				
		}
		
		// If no port -p tag was entered, print an error message.
		if(port == -1){
			printError("Please enter a port with -p flag.");
		}
		
		// Attempt to send the keys to Alice.
		try{
			giveKeysToAlice();
		}
		catch (Exception e){
			printError("Issue trying to send required keys to Alice.");
		}

		// Wait for the message to arrive.
		listenForMessage();
		
		// Attempt to decrypt the message, if an error occurs print an Error Message.
		try {
			decryptMessage();
		} 
		catch (Exception e) {
			printError("Issue decrypting message.");
		}
		
	}

	/**
	 * This method creates a ServerSocket to accept Alice's Message and then retrieves the message from it.
	 */
	private static void listenForMessage(){
		
		// Create Bob's Server Socket
		vPrint("Bob Waiting For Message...");
		ServerSocket bobServer = null;
		
		try {
			
			// Accept the Socket and set up the Input Stream
			bobServer = new ServerSocket(port);
			Socket aliceSocket = bobServer.accept();
			vPrint("Accepted Socket From Alice.");
			DataInputStream dataStream = new DataInputStream(aliceSocket.getInputStream());
            
			// Retrieve the Data From the Socket
			int payloadLength = dataStream.readInt();
			vPrint("Receiving message from Alice.");
			
			// If there is a payload, retrieve it. If not print an error message.
			if(payloadLength > 0){
				payload = new byte[payloadLength];
				dataStream.readFully(payload);
				vPrint("Payload From Alice: " + MessageTools.generateHex(payload));
			}
			else{
				printError("The Message From Alice Was Empty.");
			}
            
		} catch (IOException e) {
			printError("Bob's Server Socket Couldn't Be Created.");
		} finally {
			if(bobServer != null){
				try{
					bobServer.close();
					}
				catch(IOException e){
					printError("Failure Closing Bob's Server Socket");
					}
			}
		}
	}
	
	/**
	 * This method decrypts the message that Alice sent to us. Not only does it decrpyt the message, but also
	 * the keys we use for decryption as well. It is the heart of Bob's Message Reciever.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws SignatureException
	 */
	private static void decryptMessage() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException{
		
		// Get the Bytes of how long the symmetric key is.
		byte[] symKeyBytes = new byte[4]; 
		for(int i = 0; i < 4; i++){
			symKeyBytes[i] = payload[i];
		}
		
		// Retrieve the Actual Symmetric Key
		int symKeyLength = ByteBuffer.wrap(symKeyBytes).getInt();
		vPrint("Symmetric Key Length: " + symKeyLength);
		byte[] symKeyEncrypted = new byte[symKeyLength];
		for(int i = 4, j = 0; i < 4 + symKeyLength; i++, j++){
			symKeyEncrypted[j] = payload[i];
		}		
		vPrint("3DES/Symmetric Key: " + MessageTools.generateHex(symKeyEncrypted));
		
		// Now using Bobs Private Key Decrypt 3DES Key
		vPrint("Decrypt 3DES Key Using Bob's private key.");
		bobPrivKey = MessageTools.retrievePrivKey(keyPath + bobPrivKeyFilename);
		vPrint("Bob's private key: " + MessageTools.generateHex(bobPrivKey.getEncoded()));	
		keyCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		keyCipher.init(Cipher.DECRYPT_MODE, bobPrivKey);
		symKeyDecrpted = keyCipher.doFinal(symKeyEncrypted);		
		vPrint("3DES key decrypted: " + MessageTools.generateHex(symKeyDecrpted));
		
		// Decrypt the Cipher Text
		decryptCipherText();
		
		// Retrieve the Encrpyed Message From the Payload
		encryptedMessage = new byte[payload.length -  symKeyEncrypted.length - 4];
		for (int i = 4 + symKeyEncrypted.length, j = 0; i < payload.length; i++, j++) {
			encryptedMessage[j] = payload[i];
		}
		vPrint("Cipher Text: " + MessageTools.generateHex(encryptedMessage));

		// Preparing to Decrypt Actual Message.
		byte[] textToBeDecrypted = keyCipher.doFinal(encryptedMessage);

		// Decrypt The Text To The Message
		byte[] decryptedMessageDigest = new byte[textToBeDecrypted.length - 20 - 128];
		for(int i = 20 + 128, j = 0; i < textToBeDecrypted.length; i++, j++){
			decryptedMessageDigest[j] = textToBeDecrypted[i];
		}	
		
		// Store the actual decrpyted message.
		String messageFromAlice = new String(decryptedMessageDigest, "UTF-8");

		// Create the Message Hash and A Hash of the Actual Message From the Digest in order to verify signature.
		byte[] messageBytesDigest = messageFromAlice.getBytes("UTF-8");
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(messageBytesDigest);
		byte[] payloadHash = digest.digest();
		byte[] messageHash = new byte[20];
		for(int i = 0; i < 20; i++){
			messageHash[i] = textToBeDecrypted[i];
		}
		
		// Verify the Signature of the Message and Print the Message.
		verifySignature(textToBeDecrypted, messageHash, payloadHash, messageFromAlice);
			
	}
	
	/**
	 * This method decrypts the Cipher Text from the payload using a Cipher.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	private static void decryptCipherText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		vPrint("Time to decrypt the cipher text using the decrypted 3DES key above.");
		SecretKey des3Key = new SecretKeySpec(symKeyDecrpted, "DESede");
		keyCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		keyCipher.init(Cipher.DECRYPT_MODE, des3Key);
		
	}
	
	/**
	 * This method is the final step in the Message Receiving Process, it verifies if the signature of the
	 * message and then prints it if it matches.
	 * @param textToBeDecrypted
	 * @param messageHash
	 * @param payloadHash
	 * @param messageFromAlice
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	private static void verifySignature(byte[] textToBeDecrypted, byte[] messageHash, byte[] payloadHash, String messageFromAlice) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException{

		// Retrieve Alice's Public Key pull out the Signature From the Message.
		vPrint("Verify The Signature Of The Message.");
		alicePubKey = MessageTools.retrievePubKey(keyPath + alicePubKeyFilename);
		vPrint("Alice's Public Key: " + MessageTools.generateHex(alicePubKey.getEncoded()));	
		byte[] signatureBytes = new byte[128];
		for(int i = 20, j = 0; i < 148; i++, j++){
			signatureBytes[j] = textToBeDecrypted[i];
		}	
		vPrint("Alice's Signature On The Message Digest: " + MessageTools.generateHex(signatureBytes));
		
		// Generate the signature to test it.
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(alicePubKey);
		signature.update(messageHash);
		
		// Verify if the two signatures match.
		if(!signature.verify(signatureBytes)){
			vPrint("Unable To Verify Signature, this message is not from Alice.");
		}		
		else{
			vPrint("Verified Signature, this message is from Alice.");
		}	
		
		// Verify that the message wasn't altered or corrupted.
		vPrint("Verify Using The Message Digests.");
		if(!Arrays.equals(payloadHash, messageHash)){
			vPrint("Message Has Been Altered!");
		}
		else{
			vPrint("Message digest is equal.");
			System.out.println("************** Message From Alice **************\n"+ messageFromAlice);
		}
	}

	/** 
	 * This method prints a small help menu for the user to see what arguments can be entered.
	 */
	private static void printHelp() {
		System.out.println("\nHelp Menu\n"
				+ "Example: java -jar boblisten.jar -p <port> -v ...\n\n"
				+ "Arguments:\n"
				+ "    -p <port>     The Port Number To Listen On.\n"
				+ "    -v            Display Verbose Output.\n"
				+ "    -h            Print This Menu.\n");
		System.exit(0);
	}
	
	/**
	 * This method reteives the public key and CA signature of Bob and sends it to Alice.
	 * 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	private static void giveKeysToAlice() throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		// Create Bob's Server Socket
		vPrint("Bob Waiting To Send Key...");
		ServerSocket bobServer = null;
				
		try {		
			// Accept the Socket and set up the Output Stream
			bobServer = new ServerSocket(port);
			Socket aliceSocket = bobServer.accept();
			vPrint("Accepted Socket From Alice.");
			
			vPrint("Prepare To Send Public Key and CA Signature to Alice.");
			
			// Create the output stream, and bring in the Public and Bob CS Signature.
			DataOutputStream outputToAlice = new DataOutputStream(aliceSocket.getOutputStream());		
			bobPubKey = MessageTools.retrievePubKey(keyPath + bobPubKeyFilename);
			byte[] pubKeyArray = bobPubKey.getEncoded();
			FileInputStream bobCASign = new FileInputStream(keyPath + bobCASignFilename);
			byte[] signOfCA = new byte[bobCASign.available()];
			bobCASign.read(signOfCA);
			bobCASign.close();
			
			// Combine the two keys in one array.
			int pubLen = pubKeyArray.length;
			int caLen = signOfCA.length;
			byte[] combKeyArray = new byte[pubLen+caLen];
			System.arraycopy(pubKeyArray, 0, combKeyArray, 0, pubLen);
			System.arraycopy(signOfCA, 0, combKeyArray, pubLen, caLen);
			
			// Send the keys to Alice.
			vPrint("Send the keys to Alice.");
			outputToAlice.writeInt(pubKeyArray.length);
			outputToAlice.writeInt(combKeyArray.length);
			outputToAlice.write(combKeyArray);
			
			
		}
		catch (IOException e) {
					printError("Bob's Server Socket Couldn't Be Created.");
		}
		finally {
			if(bobServer != null){
				try{
					bobServer.close();
					}
				catch(IOException e){
					printError("Failure Closing Bob's Server Socket");
					}
				}
			}
		}
	
	/**
	 * This method is used when the user enters the -v tag, it will display all the information that is
	 * happening in the program.
	 * @param message
	 */
	private static void vPrint(String message){
		if(vOptionEnabled){
			System.out.println(message + "\n");
		}
	}
	
	/**
	 * This method is used to print errors to the user, after the message is displayed the program quits.
	 * @param errorMessage
	 */
	private static void printError(String errorMessage){
		System.out.println("\n" + "The Following Error Occured: " + errorMessage);
		System.exit(1);
	}
}
