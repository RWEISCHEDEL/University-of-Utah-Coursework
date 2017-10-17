using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Net.Sockets;
using System.Net;

namespace AgCubio
{
    /// <summary>
    /// This static class is filled with methods that enable communictaion from a server to a client using a socket. 
    /// </summary>
    public static class Network
    {
        /// <summary>
        /// This is a arbitrary object to act as a lock to ensure that cross-threading doesn't happen in some of the methods.
        /// </summary>
        private static object locker = new object();

        /// <summary>
        /// Declare and create the type of encoding need for communication between the server and client.
        /// </summary>
        private static System.Text.UTF8Encoding encoding = new System.Text.UTF8Encoding();

        /// <summary>
        /// This is a constant value that acts as the port number for all server and client communications.
        /// </summary>
        //private const int port = 11000;

        /// <summary>
        /// This method begins the connection process between the server and the client. It also creates and returns the socket to be used in other methods in this class. It takes
        /// in a callback function to be stored in the PreservedState obj and the hostname of the desired server you wish to connect to.
        /// </summary>
        /// <param name="callBack">A callback function to be stored and used later to help in exiting and processing the server data./param>
        /// <param name="hostname">A string containing the server you wish to connect to.</param>
        /// <returns>A socket that enables communication from the server to client.</returns>
        public static Socket Connect_to_Server(PreservedState.CallBackFunction callBack, String hostname, int port)
        {
            // Create a PreservedState object.
            PreservedState state = null;

            // Store the callback function in the PreservedState obj.
            PreservedState.CallBackFunction callBackFcn = callBack;

            // Create a new IPHostEntry based on the hostname.
            IPHostEntry ipHostInfo = Dns.GetHostEntry(hostname);

            // Set IPAddress equal to null
            IPAddress ipAddress = null;

            // Loop through all the ipaddresses in the ipHostInfo List and find the correct one. 
            foreach (IPAddress address in ipHostInfo.AddressList)
            {
                // If the address is of type InterNetwork, store this address as the ipAddress.
                if (address.AddressFamily == AddressFamily.InterNetworkV6)
                {
                    ipAddress = address;
                    break;
                }
            }

            // Create a IPEndPoint in order to begin connecting to the server.
            IPEndPoint remoteEP = new IPEndPoint(ipAddress, port);

            // Create a socket
            Socket socket = new Socket(AddressFamily.InterNetworkV6, SocketType.Stream, ProtocolType.Tcp);

            socket.SetSocketOption(SocketOptionLevel.IPv6, SocketOptionName.IPv6Only, false);

            // Create a PreservedState obj and store the callback function and socket so it can be used later.
            state = new PreservedState(socket, callBackFcn);

            // Begin connecting to the server by calling the Connected_To_Server callback.
            socket.BeginConnect(remoteEP, new AsyncCallback(Connected_to_Server), state);

            // Return the socket.
            return state.socket;
        }

        /// <summary>
        /// This method is a callback from the Connect_To_Server method, it calls the callback stored in the PreservedState obj and then returns control back to Connect_To_Server
        /// </summary>
        /// <param name="arObject"></param>
        public static void Connected_to_Server(IAsyncResult arObject)
        {
            // Pull the Preserved state obj out of the arObject.
            PreservedState state = (PreservedState)arObject.AsyncState;

            // Store the socket
            Socket socket = state.socket;

            // Call the callback function and pass in the state obj. 
            state.callBack(state);
        }

        /// <summary>
        /// This method is called when more data is wanted from the server. It is the callback of the I want more data. It requests more data from the server and if more information 
        /// was given it adds it to the PreservedState obj. If nothing is recieved from the server the socket is closed. 
        /// </summary>
        /// <param name="arObject"></param>
        public static void ReceiveCallBack(IAsyncResult arObject)
        {
            try
            {
                // Pull the Preserved state obj out of the arObject.
                PreservedState state = (PreservedState)arObject.AsyncState;

                // Store the socket
                Socket socket = state.socket;

                if (socket.Connected == false)
                    return;

                // Keep track of the number of bytes read from the server.
                int bytesRead = socket.EndReceive(arObject);

                // If at least one byte is read in, decode it and append it to the string builder in the PreservedState object.
                if (bytesRead > 0)
                {
                    // Store the decoded string bytes into the string builder
                    state.strBuilder.Append(Encoding.UTF8.GetString(state.buffer, 0, bytesRead));

                    // Call the callback to allow that function to know about the newly recieved information.
                    state.callBack(state);

                }
                // If no bytes were read in, close down the socket connection.
                else
                {
                    socket.Shutdown(SocketShutdown.Both);
                    socket.Close();
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }



        }

        /// <summary>
        /// This method calls the RecieveCallBack method exclusively, outside methods use this method to receive more information from the server.
        /// </summary>
        /// <param name="state">The PreservedState object containing the updated callback and the currently being used socket.</param>
        public static void i_want_more_data(PreservedState state)
        {
            // Began the process to recieve data from the server by calling ReceiveCallBAck
            state.socket.BeginReceive(state.buffer, 0, PreservedState.BUFFERSIZE, 0, ReceiveCallBack, state);
        }

        /// <summary>
        /// This method is called when the user wishes to send data to the server. The callBack parameter
        /// is optional. If a callback is provided, it will be called
        /// </summary>
        /// <param name="socket">The socket this connection is using to talk to the server</param>
        /// <param name="data">The information the user wishes to send to the server.</param>
        public static void Send(Socket socket, String data, PreservedState.CallBackFunction callBackFcn = null)
        {
            // Lock this method so multiple threads can be sending data at the same time. 
            lock (locker)
            {
                // Create a new PreservedState object to store the socket and buffer.
                PreservedState state = new PreservedState(socket, null);

                // If the optional callback parameter was provided, add it to the state
                // object so it can be called once the send is complete.
                state.callBack = callBackFcn;

                // Translate the string of data into bytes
                state.buffer = encoding.GetBytes(data);

                // Began sending the bytes of data to the server, and call the callback SendCallBack.
                if (socket.Connected)
                {
                    socket.BeginSend(state.buffer, 0, state.buffer.Length, SocketFlags.None, SendCallBack, state);
                }


            }

        }

        /// <summary>
        /// This method is a callback function to the Send method. It ensures all the data that needs to be sent is sent in the correct order. And it ends when there is no more data
        /// to me sent to the server.
        /// </summary>
        /// <param name="result"></param>
        public static void SendCallBack(IAsyncResult result)
        {
            // The number of bytes that were sent.
            int bytes;

            // Pull the Preserved state obj out of the arObject.
            PreservedState state = (PreservedState)result.AsyncState;

            // Store the socket
            Socket socket = state.socket;

            if (socket.Connected)
            {
                // Retrieve the number of bytes sent 
                try
                {
                    bytes = socket.EndSend(result);
                }
                catch (Exception e)
                {
                    socket.Close();
                    return;
                }

            }
            else
            {
                return;
            }

            // Lock this method so multiple threads can be sending data at the same time.
            lock (locker)
            {
                // store the PreservedState buffer into a outgoing buffer.
                byte[] outgoingBuffer = state.buffer;

                // Convert all the buffer data into a string.
                String data = encoding.GetString(outgoingBuffer, bytes, outgoingBuffer.Length - bytes);

                // Iif the string is empty end this send callback
                if (data == "")
                {
                    // If the optional callback was provided in Send(), run it. Else, just return
                    if (state.callBack != null)
                        state.callBack(null);
                    else
                        return;
                }

                // If there is more data to send call Send again.
                Send(socket, data);
            }

        }

        /// <summary>
        /// This method is the heart of the server code, it asks the OS to listen for new connections, and when a client 
        /// makes a connection request it calls the Accept_a_New_Client function. Note : This methos is an event loop, it
        /// continues to listen for new connections continually.
        /// </summary>
        /// <param name="callbackFcn">The desired function you wish to callback to when the Accept_a_New_Client method has finished.</param>
        public static void Server_Awaiting_Client(PreservedState.CallBackFunction callbackFcn, int port)
        {
            // Create a PreservedState object.
            PreservedState state = null;

            // Store the callback function in the PreservedState obj.
            PreservedState.CallBackFunction callBack = callbackFcn;

            // Create a new IPHostEntry based on the hostname.
            IPHostEntry ipHostInfo = Dns.GetHostEntry("localhost");

            // Set IPAddress equal to null
            IPAddress ipAddress = null;

            // Loop through all the ipaddresses in the ipHostInfo List and find the correct one. 
            foreach (IPAddress address in ipHostInfo.AddressList)
            {
                // If the address is of type InterNetwork, store this address as the ipAddress.
                if (address.AddressFamily == AddressFamily.InterNetworkV6)
                {
                    ipAddress = address;
                    break;
                }
            }

            // Extablish local end point for the socket.
            IPEndPoint localEP = new IPEndPoint(ipAddress, port);

            // Create a socket.
            Socket listener = new Socket(AddressFamily.InterNetworkV6, SocketType.Stream, ProtocolType.Tcp);

            listener.SetSocketOption(SocketOptionLevel.IPv6, SocketOptionName.IPv6Only, false);

            // Associate the listener socket to only this local end point.
            listener.Bind(localEP);

            // We will only allow up to 100 client connections to be backlogged.
            listener.Listen(100);

            // Create a new state object storing the listener socket and the callback function.
            state = new PreservedState(listener, callBack);

            // Print to the Server window, to allow users to see that the server is listening for more players.
            Console.WriteLine("Listening for new connections.....");

            // When a listener has detected a new client trying to add, call Accept_a_New_Client to accept the new cliet.
            listener.BeginAccept(Accept_a_New_Client, state);



        }

        /// <summary>
        /// This method is called by the Server_Awaiting_Client method when a new client connection needs to be made. Note : This method 
        /// is also part of the even loop described in the Server_Awating_Client method. 
        /// </summary>
        /// <param name="result">The IAsyncResult object that holds the state object that was passed from the previous method.</param>
        public static void Accept_a_New_Client(IAsyncResult result)
        {
            // Write to the window to show the player that a new client has been accepted.
            Console.WriteLine("Accepting a new Client....");

            // Convert the state object back into a PreservedState object.
            PreservedState state = (PreservedState)result.AsyncState;

            // Pull out the socket listener.
            Socket listener = state.socket;

            // Create a new socket for the client. 
            Socket clienthandler = listener.EndAccept(result);


            // We must pass a new state object into the Begin Accept...
            listener.BeginAccept(Accept_a_New_Client, new PreservedState(listener, state.callBack));
            // This fails:
            //listener.BeginAccept(Accept_a_New_Client, state);

            // Update the state object to hold the new client socket instead of the listener.
            state.socket = clienthandler;

            // Call the callback function stored in the state obj.
            state.callBack(state);
        }



    }
}







