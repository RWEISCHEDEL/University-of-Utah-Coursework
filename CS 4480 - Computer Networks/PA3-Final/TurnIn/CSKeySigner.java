package secureCommunication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * This classes only purpose is to sign Bob's Public Key with the Certificate Authority Private Key and then generate that
 * resulting file. This method is only used once, just to generate the signature that will be used in the other communcation 
 * classes.
 * @author Robert Weischedel
 *
 */
public class CSKeySigner {
	
	private static String keyPath = "/Users/Robert/Documents/U of U/Courses/CS 4480/PA3 Keys/";

	/**
	 * This main method generates the signature of Bob's Public Key via the Certificate Authority Private Key.
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException,
			FileNotFoundException, InvalidKeySpecException, IOException,
			InvalidKeyException, SignatureException {
		
		// Retrieve the Public and Private Key
		PublicKey bobPubKey = MessageTools.retrievePubKey(keyPath + "bobPublicKey.der");
		PrivateKey CAPrivKey = MessageTools.retrievePrivKey(keyPath + "CAPrivateKey.der");

		// Create a signature of type SHA1 with RSA and initialize the newly created object.
		Signature rsaSig = Signature.getInstance("SHA1withRSA");
		rsaSig.initSign(CAPrivKey);
		
		// Update the signature with the encoding of the Public Key, and then sign it.
		rsaSig.update(bobPubKey.getEncoded());
		byte[] signature = rsaSig.sign();

		// Create the signed output file and close.
		FileOutputStream fileOutput = new FileOutputStream("BobCASignature");
		fileOutput.write(signature);
		fileOutput.close();
	}

}
