package PA1;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This serves as the main method for the ProxyServer. It starts the proxy server socket and begins listening
 * to the requests.
 * @author Robert Weischedel
 *
 */
public class ProxyServer {

	/**
	 * This method begins the Proxy Server process.
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		// Ask the user for the desired port number.
		int port;
		
		// Create the server socket. 
		ServerSocket welcomeSocket;
		
		try{
			
			// Pull the port number from the command line.
			port = Integer.parseInt(args[0]);
			
			// Create the server welcome socket.
			welcomeSocket = new ServerSocket(port);
		}
		// If the port is already in use, return a error message.
		catch (BindException e){
			System.out.println("The specified port is in use, please enter a new one.");
			return;
		}
		// If the value entered for the port number isn't a valid int, return a error message.
		catch(Exception e){
			System.out.println("Please enter a vaild integer.");
			return;
		}

		// Continuously be accepting new sockets.
		while(true){
			try {
				
				// When a new client wants to connect, accept it and create a new socket.
				Socket connectionSocket = welcomeSocket.accept();
				
				// Put the newly accepted socket on a new thread and store it in the NewClientConnection class.
				(new Thread(new NewClientConnection(connectionSocket))).start();
				
			}
			// If there is an issue print an error message. 
			catch (IOException e) {
				System.out.println("Cannot create a client connection on this port.");
				e.printStackTrace();
				return;
			}
		}

	}

}
