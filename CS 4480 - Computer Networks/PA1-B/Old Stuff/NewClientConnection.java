package PA1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class handles the client request made to the proxy server. It interprets the data from the client and
 * ensures it is in the correct format. Then it forwards that cleint information to the desired remote server
 * and then returns that data to the client.
 * @author Robert Weischedel
 *
 */
public class NewClientConnection implements Runnable {

	/**
	 * This private member variable stores the client socket we are communicating on.
	 */
	private Socket acceptedSocket;
	
	/**
	 * This private member variable stores the desired port that the client wants to connect to the remote
	 * server too.
	 */
	private int userSpecifiedPort;

	/**
	 * This private member variable stores the string error message for a bad request.
	 */
	private String badReqMsg = "HTTP/1.0 400 Bad Request";

	/**
	 * This private member variable stores the string error message for a not implemented.
	 */
	private String notImpMsg = "HTTP/1.0 501 Not Implemented";

	/**
	 * This serves as the constructor for this class, it takes in the newly accepted socked and stores it as
	 * a private member variable.
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

			// Create PrintWriter and Buffered reader to handle the input/output of the proxy.
			PrintWriter out = new PrintWriter(acceptedSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));

			// Create an ArrayList to hold all the request lines from the client to proxy.
			ArrayList<String> clientRequest = new ArrayList<String>();

			// The desired port to send the client request to the server. Initalize it to 80.
			userSpecifiedPort = 80;

			// Retrieve all the request lines from the client to proxy.
			while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {

				System.out.println("Initial input line : " + inputLine);

				clientRequest.add(inputLine);
			}
			
			String firstLine[] = null;
			
			// Interpret the GET Line, if its empty then return a error message.
			if(clientRequest.size() > 0){
				firstLine = clientRequest.get(0).split(" ");
			}
			else{
				PrintErrorMsg(out, badReqMsg);
				return;
			}
			
			// Remove the GET line so it can be built later on.
			clientRequest.remove(0);

			// Ensure the GET line is in the correct format, if not return the approprate error message.
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
			if(getLineURL.contains("/")){
				domain = getLineURL.substring(0, getLineURL.indexOf("/"));
				directory = getLineURL.substring(getLineURL.indexOf("/"));
			}
			else{
				domain = getLineURL;
				directory = "/";
			}
			
			System.out.println("Domain Stripped : " + domain + " Directory Stripped: " + directory);

			// Ensure the last part of the GET message contains HTTP/1.0. If not, return a error message.
			if (!firstLine[2].equals("HTTP/1.0")) {
				PrintErrorMsg(out, badReqMsg);
			}

			// Create two strings so that the host and connection lines can be removed from the client request.
			String hostToRemove = "", connToRemove = "";
			
			// Loop through all the client headers requests and ensure they are in correct order.
			for (String req : clientRequest) {

				System.out.println("Loop : " + req);
				
				// Split up each header.
				String[] requestSplit = req.split(" ");

				// Ensure that the header title contains a :, if not return a error message.
				if (!requestSplit[0].contains(":")) {
					PrintErrorMsg(out, badReqMsg);
					return;
				}

				// If the Host header is found extract the information from it and set it to be removed.
				if (requestSplit[0].contains("Host:")) {
					System.out.println("The host splitting p1: " + requestSplit[0]);
					System.out.println("The host splitting p2 : " + requestSplit[1]);
					domain = requestSplit[1];
					hostToRemove = req;
				}

				// If the connection header is found set it to be removed.
				if (requestSplit[0].contains("Connection:")) {
					connToRemove = req;
				}

			}
			
			// Remove the connection and host if they were found in the client request headers.
			if(hostToRemove != ""){
				clientRequest.remove(hostToRemove);
			}
			
			if(connToRemove != ""){
				clientRequest.remove(connToRemove);
			}
			
			System.out.println("After Header Chk Dom: " + domain);
			System.out.println("After Header Chk Dir: " + directory);
			
			// Add the headers that were removed, but with the correct format and information.
			clientRequest.add(0, "GET " + directory + " HTTP/1.0");
			clientRequest.add(1, "Host: " + domain);
			clientRequest.add("Connection: close\r\n");
			
			System.out.println("Check port before making rServSock : " + userSpecifiedPort);
			
			// Try and connect to the remote server.
			try{
				
				// Create a socket to connect to the remote server from the domain name and port number.
				Socket remoteServer = new Socket(InetAddress.getByName(domain), userSpecifiedPort);
				
				// Create a new print writer to retrieve the output from the remote server.
				PrintWriter writer = new PrintWriter(remoteServer.getOutputStream());
				
				// Iterate through and send the modified client requests to the server.
				for(String req : clientRequest){
					writer.print(req + "\r\n");
					System.out.println("Each request to remote Serv " + req);
				}
				
				// Flush the writer.
				writer.flush();
				
				// Create a byte array so that we can receive the correct input from the server.
				byte[] outBytesFromServer = new byte[100000];
				
				int size = 0;
				
				// Create a InputStream to read information from the remote server.
				InputStream stream = remoteServer.getInputStream();
				
				// Read the bytes from ther server.
				while((size = stream.read(outBytesFromServer)) > -1) {
					acceptedSocket.getOutputStream().write(outBytesFromServer, 0, size);
				}
				
				// Close the writers, streams, and flush the writer.
				stream.close();
				writer.close();
				remoteServer.close();
				out.flush();
				
			}
			// If there is any issue, return an error message.
			catch(Exception e){
				PrintErrorMsg(out, badReqMsg);
				return;
			}
			
		} catch (IOException e) {

			// Overall try catch.
			System.out.println("Problem with getting input from client socket.");
			e.printStackTrace();
		}

	}
	
	/**
	 * This method strips down the URL and removes the http:// from the address and also ensures that if
	 * a user specified port is given, it is removed and stored.
	 * @param address
	 * @param out
	 * @return
	 */
	private String StripString(String address, PrintWriter out){
		
		// Check and remove if the address contains http:// or http:
		if (address.contains("http://")) {
			address = address.replace("http://", "");
		}

		if (address.contains("http:")) {
			address = address.replace("http:", "");
		}
		
		// Check and see if a user specified port was entered. If it was remove and store it.
		if (address.contains(":")) {

			try {
				userSpecifiedPort = Integer.parseInt(address.substring(address.indexOf(":") + 1));
				
				address = address.substring(0, address.indexOf(":"));
				
			} catch (Exception e) {
				PrintErrorMsg(out, badReqMsg);
			}

			System.out.println("User spec port " + userSpecifiedPort);
		}
		
		// Return the cleaned up address.
		return address;
	}
	
	/**
	 * This method prints out the various error messages that can occur during the process. It creates 
	 * a web page to display the type of error message to aid in user readability. 
	 * @param out
	 * @param errorType
	 */
	private void PrintErrorMsg(PrintWriter out, String errorType) {

		// Create the error message string including the error type and HTML for error page.
		String errorMsg = errorType + "\r\n\r\n"
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
