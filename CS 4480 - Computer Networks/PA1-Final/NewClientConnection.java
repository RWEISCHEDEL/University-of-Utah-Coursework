package PA1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * This class handles the client request made to the proxy server. It interprets
 * the data from the client and ensures it is in the correct format. Then it
 * forwards that client information to the desired remote server and then
 * returns that data to the client.
 * 
 * @author Robert Weischedel
 * 
 */
public class NewClientConnection implements Runnable {

	/**
	 * This private member variable stores the client socket we are
	 * communicating on.
	 */
	private Socket acceptedSocket;

	/**
	 * This private member variable stores the desired port that the client
	 * wants to connect to the remote server too.
	 */
	private int userSpecifiedPort;

	/**
	 * This private member variable stores the string error message for a bad
	 * request.
	 */
	private String badReqMsg = "HTTP/1.0 400 Bad Request";

	/**
	 * This private member variable stores the string error message for a not
	 * implemented.
	 */
	private String notImpMsg = "HTTP/1.0 501 Not Implemented";

	/**
	 * This private member variable stores the string error message for when the
	 * site requested contians malware.
	 */
	private String malwareMsg = "HTTP/1.0 200 OK";

	/**
	 * This serves as the constructor for this class, it takes in the newly
	 * accepted socked and stores it as a private member variable.
	 * 
	 * @param newSocket
	 */
	public NewClientConnection(Socket newSocket) {

		// Store the inputed client socket.
		acceptedSocket = newSocket;

	}

	@Override
	public void run() {

		try {

			String inputLine = "", getLineURL = "";

			// Create PrintWriter and Buffered reader to handle the input/output
			// of the proxy.
			PrintWriter out = new PrintWriter(acceptedSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					acceptedSocket.getInputStream()));

			// Create an ArrayList to hold all the request lines from the client
			// to proxy.
			ArrayList<String> clientRequest = new ArrayList<String>();

			// The desired port to send the client request to the server.
			// Initalize it to 80.
			userSpecifiedPort = 80;

			// Retrieve all the request lines from the client to proxy.
			while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {

				clientRequest.add(inputLine);
			}

			String firstLine[] = null;

			// Interpret the GET Line, if its empty then return a error message.
			if (clientRequest.size() > 0) {
				firstLine = clientRequest.get(0).split(" ");
			} else {
				PrintErrorMsg(out, badReqMsg);
				return;
			}

			// Remove the GET line so it can be built later on.
			clientRequest.remove(0);

			// Ensure the GET line is in the correct format, if not return the
			// approprate error message.
			if (firstLine.length != 3) {
				PrintErrorMsg(out, badReqMsg);
				return;
			}

			if (!firstLine[0].equals("GET")) {
				PrintErrorMsg(out, notImpMsg);
				return;
			}

			try {
				getLineURL = firstLine[1];
			} catch (Exception e) {
				PrintErrorMsg(out, badReqMsg);

				return;
			}

			// Strip out the http:// and the user specified port.
			getLineURL = StripString(getLineURL, out);

			// Separate and store the directory and domain.
			String directory = "", domain = "";
			if (getLineURL.contains("/")) {
				domain = getLineURL.substring(0, getLineURL.indexOf("/"));
				directory = getLineURL.substring(getLineURL.indexOf("/"));
			} else {
				domain = getLineURL;
				directory = "/";
			}

			// Ensure the last part of the GET message contains HTTP/1.0. If
			// not, return a error message.
			if (!firstLine[2].equals("HTTP/1.0")) {
				PrintErrorMsg(out, badReqMsg);
			}

			// Create two strings so that the host and connection lines can be
			// removed from the client request.
			String hostToRemove = "", connToRemove = "";

			// Loop through all the client headers requests and ensure they are
			// in correct order.
			for (String req : clientRequest) {

				// Split up each header.
				String[] requestSplit = req.split(" ");

				// Ensure that the header title contains a :, if not return a
				// error message.
				if (!requestSplit[0].contains(":")) {
					PrintErrorMsg(out, badReqMsg);
					return;
				}

				// If the Host header is found extract the information from it
				// and set it to be removed.
				if (requestSplit[0].contains("Host:")) {
					domain = requestSplit[1];
					hostToRemove = req;
				}

				// If the connection header is found set it to be removed.
				if (requestSplit[0].contains("Connection:")) {
					connToRemove = req;
				}

			}

			// Remove the connection and host if they were found in the client
			// request headers.
			if (hostToRemove != "") {
				clientRequest.remove(hostToRemove);
			}

			if (connToRemove != "") {
				clientRequest.remove(connToRemove);
			}

			// Add the headers that were removed, but with the correct format
			// and information.
			clientRequest.add(0, "GET " + directory + " HTTP/1.0");
			clientRequest.add(1, "Host: " + domain);
			clientRequest.add("Connection: close\r\n");

			// Try and connect to the remote server.
			try {

				// Create a socket to connect to the remote server from the
				// domain name and port number.
				Socket remoteServer = new Socket(InetAddress.getByName(domain),
						userSpecifiedPort);

				// Create a new print writer to retrieve the output from the
				// remote server.
				PrintWriter writer = new PrintWriter(
						remoteServer.getOutputStream());

				// Iterate through and send the modified client requests to the
				// server.
				for (String req : clientRequest) {
					writer.print(req + "\r\n");
				}

				// Flush the writer.
				writer.flush();

				// Create a InputStream to read information from the remote
				// server.
				InputStream stream = remoteServer.getInputStream();

				int byteBuffer;

				// Create a ArrayList to hold the remote servers message.
				ArrayList<Byte> bytesFromServer = new ArrayList<Byte>();

				// Read the message from the remote server and store it.
				while ((byteBuffer = stream.read()) > -1) {
					bytesFromServer.add((byte) byteBuffer);
				}

				// Call the sendToCymru method to send the response to be checked for malware.
				boolean siteSafe = false;

				try {
					siteSafe = sendToCymru(bytesFromServer);
				} catch (Exception e) {
					PrintErrorMsg(out, badReqMsg);
				}
				
				
				// According to the sendToCymru send the site or a warning to the client.
				OutputStream toClient = acceptedSocket.getOutputStream();

				if (siteSafe) {

					for (int i = 0; i < bytesFromServer.size(); i++) {
						toClient.write(bytesFromServer.get(i));
					}
					toClient.flush();

				} else {
					PrintErrorMsg(out, malwareMsg + ": This request contains Malware");
					remoteServer.close();
					return;

				}

				// Close the writers, streams, and flush the writer.
				stream.close();
				writer.close();
				remoteServer.close();
				out.flush();

			}
			// If there is any issue, return an error message.
			catch (Exception e) {
				PrintErrorMsg(out, badReqMsg);
				return;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method prepares the byte array information that was recieved from
	 * the remote server to be sent to the Team Cymru site in order to be
	 * checked for malware.
	 * 
	 * @param byteArray
	 * @return
	 * @throws Exception
	 */
	private boolean sendToCymru(ArrayList<Byte> byteArray) throws Exception {

		// Set a boolean to return in the site to be checked is safe or not.
		boolean siteSafe = false;

		try {

			// Create the MessageDigest that will convert the body into a md5
			// hash
			final MessageDigest md5 = MessageDigest.getInstance("MD5");

			md5.reset();

			byte carriage = '\r';
			byte newLine = '\n';

			// Set a boolean flag to know when we hit the body of the message.
			boolean inheader = false;
			
			// Iterate throught the byte array and retrieve the body of the HTTP response
			for (int i = 0; i < byteArray.size(); i++) {

				if (!inheader) {
					
					// Search through the header and find the \r\n\r\n
					if (byteArray.get(i).equals(carriage)
							&& byteArray.get(i + 1).equals(newLine)
							&& byteArray.get(i + 2).equals(carriage)
							&& byteArray.get(i + 3).equals(newLine)) {
						
						// Once we find it set the bool flag to true.
						inheader = true;
						i += 3;
						
						// Ensure we are not at the end of the byte array. If we are there is no body and thus no malware.
						if (i == byteArray.size() - 1) {
							return true;
						}
					}
				} else {
					// Add the body information so that it can be hashed.
					md5.update(byteArray.get(i));
				}
			}

			// Convert the body information into a md5 hash byte array.
			final byte[] resultByte = md5.digest();

			// Convert the md5 byte array hash into a string.
			BigInteger bigInt = new BigInteger(1, resultByte);

			String convertToText = bigInt.toString(16);

			while (convertToText.length() < 32) {

				convertToText = "0" + convertToText;

			}

			try {
				// Create a new socket to Team Cymru
				Socket cymruSocket = new Socket(
						InetAddress.getByName("hash.cymru.com"), 43);

				// Create a PrintWriter to send the information to Cymru
				PrintWriter writer = new PrintWriter(
						cymruSocket.getOutputStream());

				writer.println("Begin Transmission");
				writer.println(convertToText);
				writer.println("End Transmission");

				//Flush the PrintWriter
				writer.flush();

				// Retrieve the information from Team Cymru and read it.
				InputStreamReader stream = new InputStreamReader(
						cymruSocket.getInputStream());
				BufferedReader reader = new BufferedReader(stream);

				String inputLine = "";

				// If NO_DATA is found in the response the site is safe
				while ((inputLine = reader.readLine()) != null
						&& !inputLine.equals("")) {
					if (inputLine.contains("NO_DATA")) {
						siteSafe = true;
					}
				}

				// Clean up the sockets and close the writer.
				writer.close();
				cymruSocket.close();
			} catch (Exception e) {
				throw new Exception();
			}

		} catch (NoSuchAlgorithmException e) {
			throw new Exception();
		}

		return siteSafe;
	}

	/**
	 * This method strips down the URL and removes the http:// from the address
	 * and also ensures that if a user specified port is given, it is removed
	 * and stored.
	 * 
	 * @param address
	 * @param out
	 * @return
	 */
	private String StripString(String address, PrintWriter out) {

		// Check and remove if the address contains http:// or http:
		if (address.contains("http://")) {
			address = address.replace("http://", "");
		}

		if (address.contains("http:")) {
			address = address.replace("http:", "");
		}

		// Check and see if a user specified port was entered. If it was remove
		// and store it.
		if (address.contains(":")) {

			try {
				userSpecifiedPort = Integer.parseInt(address.substring(address
						.indexOf(":") + 1));

				address = address.substring(0, address.indexOf(":"));

			} catch (Exception e) {
				//PrintErrorMsg(out, badReqMsg);
			}

		}

		// Return the cleaned up address.
		return address;
	}

	/**
	 * This method prints out the various error messages that can occur during
	 * the process. It creates a web page to display the type of error message
	 * to aid in user readability.
	 * 
	 * @param out
	 * @param errorType
	 */
	private void PrintErrorMsg(PrintWriter out, String errorType) {

		// Create the error message string including the error type and HTML for
		// error page.
		String errorMsg = errorType
				+ "\r\n\r\n"
				+ "<!DOCTYPE html><html><head><title>ERROR PROCESSING REQUEST</title></head><body>"
				+ errorType + "</body></html>\r\n\r\n";

		// Send the error message to the client.
		out.print(errorMsg);

		// Flush the writer.
		out.flush();

		try {
			// Close the socket.
			acceptedSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
