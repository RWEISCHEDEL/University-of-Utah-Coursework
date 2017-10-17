package secureCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * This class handles of the work in creating, encrypting, and sending a message to Bob.
 * @author Robert Weischedel
 * CS 4480
 */
public class AliceMessageTransmitter {
	
	// These member variables are used to specify which ports have been included.
	private static boolean vOptionEnabled = false;
	private static int port = 0;
	private static String ipAddress;
	
	// These member variables are used to keep track of the encryption keys we need.
	private static PrivateKey alicePrivKey;
	private static PublicKey bobPubKey;
	private static PublicKey CAPubKey;
	
	// These member variables are used to help in the encryption of the message.
	private static String message = "";
	private static byte[] symKey;
	private static byte[] messageByte;
	private static byte[] aliceSign;
	private static byte[] cipherText;
	private static byte[] payload;
	private static byte[] sha1MessageHash;
	private static byte[] combinedPayload;
	private static byte[] symKeyLengthBytes;
	private static byte[] CAPubKeyArray;
	
	// These member variables are used to help retrieve the encryption keys.
	private static final String keyPath = System.getProperty("user.dir") + "/" + "PA3Keys/";
	private static final String alicePrivKeyFilename = "alicePrivateKey.der";
	private static final String CAPubKeyFilename = "CAPublicKey.der";
	
	/**
	 * This method begins the process of sending an encrypted message to Bob.
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		// Print out the System File Path in case of confusion
		vPrint("The filepath the program is set to: " + keyPath);
		
		// Parse through the user specified tags and determine what the user specs are.
		for (int i = 0; i < args.length; i++){
			if(args[i].equals("-a") && i <= args.length){
				ipAddress = args[i+1];
			}
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
			if(args[i].equals("-m") && i <= args.length){
				message = args[i+1];
			}
		}

		vPrint("Preparing to Send Message....");
		
		messageByte = message.getBytes("UTF-8");
		
		// Try to retrieve Bob's Public key from Bob.
		try{
			askForKeys();
		}
		catch (Exception e){
			printError("Issue trying to retreive Bob's Public Key.");
		}
		
		
		// Try to create the SHA1 Digest and Alice's Signature, if an issue occurs print an error message.
		try {
			generateSHA1AndSign();
		} 
		catch (Exception e) {
			printError("Issue Trying to Create the SHA1 or Signing With Private Key");
		} 
		
		// Try to encrypt the message, if an issue occurs print an error message.
		try{
			encryptMessage();
		}
		catch(Exception e){
			printError("Issue trying to encrypt the message.");
		}
		
		// Try to generate the 3DES Symmetric Key, if an issue occurs print an error message.
		try{
			generate3DESKey();
		}
		catch(Exception e){
			printError("Issue generating the 3DES Key.");
		}
		
		// Try and send the encrypted message, if an issue occurs print an error message.
		try{
			sendMessage();
		}
		catch(Exception e){
			printError("Issue trying to send the message.");
		}
		
	}
	
	/**
	 * Creates SHA1 message digest and signs it with private key
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	private static void generateSHA1AndSign() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, SignatureException, InvalidKeyException{
		
		// Create the SHA1 Hash of The Message and Retrieve Alice's Private Key
		sha1MessageHash = MessageTools.generateSHA1Hash(message);
		vPrint("SHA1 Message Digest: " + MessageTools.generateHex(sha1MessageHash));
		alicePrivKey = MessageTools.retrievePrivKey(keyPath + alicePrivKeyFilename);
		
		System.out.println("Got Alice private key.");
		
		// Ensure that the Private Key Was Recieved Correctly.
		if(alicePrivKey == null){
			printError("Error With Alice's Private Key.");
		}
		else{
			vPrint("Alice's private key: " + MessageTools.generateHex(alicePrivKey.getEncoded()));
		}
			
		// Sign With Private Key
		vPrint("Sign Message Digest Using Alice's Private Key.");
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(alicePrivKey);
		signature.update(sha1MessageHash);
		aliceSign = signature.sign();
		vPrint("Digest Using Alice's Signature: " + MessageTools.generateHex(aliceSign));
	}
	
	/**
	 * This method encrypts the message using the provided keys and then combine the information into a single byte array. 
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void encryptMessage() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		// Retrieve the CA Public Key
		vPrint("Retrieve CA Public Key.");
	    CAPubKey = MessageTools.retrievePubKey(keyPath + CAPubKeyFilename);
		vPrint("CA public key: " + MessageTools.generateHex(CAPubKey.getEncoded()));
		
		//Verify the CA signature
		vPrint("Verifying the CA signature that is on Bob's public key");
		Signature signOfRSA = Signature.getInstance("SHA1withRSA");
		signOfRSA.initVerify(CAPubKey);
		signOfRSA.update(bobPubKey.getEncoded());
		
		if(!signOfRSA.verify(CAPubKeyArray)){
			printError("Not Able To Verify the CA Signature.");
		}
		else{
			vPrint("Able To Verify the CA Signature.");
		}
		
		// Create the payload by adding the SHA1 Digest, the desired message, and ALice's Signature into one.
		combinedPayload = new byte[sha1MessageHash.length + aliceSign.length + messageByte.length];
		System.arraycopy(sha1MessageHash, 0, combinedPayload, 0, sha1MessageHash.length);
		System.arraycopy(aliceSign, 0, combinedPayload, sha1MessageHash.length, aliceSign.length);
		System.arraycopy(messageByte, 0, combinedPayload, sha1MessageHash.length + aliceSign.length, messageByte.length);
		
	}
	
	/**
	 * This method creates the 3DES Symmetric Key in order to generate the encryption of the message.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 */
	private static void generate3DESKey() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		
		// Create the 3DES Key
		vPrint("Generate The 3DES Symmetric Key.");	
		KeyGenerator keyCreator = KeyGenerator.getInstance("DESede");
		SecretKey key3DES = keyCreator.generateKey();
		vPrint("The 3DES Key Before Encryption: " + MessageTools.generateHex(key3DES.getEncoded()));
		
		// Encrypt the Data and Signature
		vPrint("Encrypt the SHA1 Hash, Signature, and Message With The 3DES Key.");
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key3DES);
		cipherText = cipher.doFinal(combinedPayload);
		vPrint("The encrypted data, the 3DES encrypted Cipher text: " + MessageTools.generateHex(cipherText));
	
		// Encrypt the 3DES Key With Bob's Public Key
		vPrint("Encrypt 3DES key Using Bob's Public Key");
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, bobPubKey);
		symKey = cipher.doFinal(key3DES.getEncoded());
		vPrint("Encrypted 3DES key Encrypted Using Bob's Public Key: " + MessageTools.generateHex(symKey));
		
		// Prepare to add the Symmetric Key to the payload to Bob
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.putInt(symKey.length);
		vPrint("Symmetric Key Length: " + symKey.length);
		symKeyLengthBytes = byteBuffer.array();
		
		// Add the Symmetric Key and its's length to the payload to Bob
		payload = new byte[symKeyLengthBytes.length + symKey.length + cipherText.length];
		System.arraycopy(symKeyLengthBytes, 0, payload, 0, symKeyLengthBytes.length);
		System.arraycopy(symKey, 0, payload, symKeyLengthBytes.length, symKey.length);
		System.arraycopy(cipherText, 0, payload, symKeyLengthBytes.length + symKey.length, cipherText.length);
		vPrint("Cipher Text: " + MessageTools.generateHex(cipherText));
	}

	/**
	 * This method sends the encrypted message to Bob.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private static void sendMessage() throws UnknownHostException, IOException{
		
		// Create the Socket and Output Stream in order to send Bob the Message.
		vPrint("Sending the Cipher Text to Bob at IP Address: " + ipAddress);
		Socket bobSocket = new Socket(ipAddress, port);
		DataOutputStream outputToBob = new DataOutputStream(bobSocket.getOutputStream());
		
		// Send the payload to Bob
		outputToBob.writeInt(payload.length);
		outputToBob.write(payload);
		outputToBob.flush();
		bobSocket.close();
		outputToBob.close();
	}
	
	/**
	 * This method requests from Bob the key necessary for program decryption.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static void askForKeys() throws UnknownHostException, IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		
		// Create a socket to request bob's public key from. 
		vPrint("Create A Socket to Send to Bob.");
		Socket bobSocket = new Socket(ipAddress, port);	
		DataOutputStream outputToBob = new DataOutputStream(bobSocket.getOutputStream());
		
		// Send the Hello request message to Bob
		vPrint("Send the key request message to Bob.");
		String ask = "Hello";
		outputToBob.write(ask.getBytes());
		outputToBob.flush();
		
		// Retrieve Bob's Public Key and the CA Signature
		vPrint("Retreive Bob's Keys.");
		DataInputStream inputFromBob = new DataInputStream(bobSocket.getInputStream());
		int bobPubKeySize = inputFromBob.readInt();
		int bothKeySize = inputFromBob.readInt();
		
		byte[] totalKeys = new byte[bothKeySize];
		
		inputFromBob.readFully(totalKeys);
		
		byte[] bobPubKeyArray = new byte[bobPubKeySize];
		CAPubKeyArray = new byte[bothKeySize - bobPubKeySize];
		
		// Pull out each Key
		for(int i = 0; i < bobPubKeySize; i++){
			bobPubKeyArray[i] = totalKeys[i];
		}
		
		for(int j = bobPubKeySize, i = 0; j < bothKeySize; j++, i++){
			CAPubKeyArray[i] = totalKeys[j];
		}
		
		// Recreate Bob's Public Key
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bobPubKeyArray);
		KeyFactory key = KeyFactory.getInstance("RSA");
		bobPubKey = key.generatePublic(keySpec);
		
		vPrint("Bob's public key: " + MessageTools.generateHex(bobPubKey.getEncoded()));
		vPrint("Bob's CA Signed Public Key: " + MessageTools.generateHex(CAPubKeyArray));
		
		// Close the socket connection.
		bobSocket.close();
		outputToBob.close();
	}

	/** 
	 * This method prints a small help menu for the user to see what arguments can be entered.
	 */
	private static void printHelp() {
		System.out.println("\nHelp Menu\n"
				+ "Example: java -jar alicetalk.jar -a <address> -p <port> -m <message> -v ...\n\n"
				+ "Arguments:\n"
				+ "    -a <address>  The IP Address To Send TO.\n"
				+ "    -p <port>     The Port Number To Listen On.\n"
				+ "    -m <message>  The Message You Wish to Send.\n"
				+ "    -v            Display Verbose Output.\n"
				+ "    -h            Print This Menu.\n");
		System.exit(0);
	}
	
	/**
	 * This method is used when the user enters the -v tag, it will display all the information that is
	 * happening in the program.
	 * 
	 * @param message
	 */
	private static void vPrint(String text){
		if(vOptionEnabled){
			System.out.println(text);
		}
	}

	/**
	 * This method is used to print errors to the user, after the message is displayed the program quits.
	 * 
	 * @param errorMessage
	 */
	private static void printError(String errorMessage){
		System.out.println("\n" + "The Following Error Occured: " + errorMessage);
		System.exit(1);
	}
}
