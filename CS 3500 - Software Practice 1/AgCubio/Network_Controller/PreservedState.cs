using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;

namespace AgCubio
{
    /// <summary>
    /// This class acts as a mediator between the Network and other projects using the Network_Controller by storing important data and methods to enable communication between the 
    /// two projects.
    /// </summary>
    public class PreservedState
    {
        /// <summary>
        /// This member variable stores the current socket we are using to connect between the network and server.
        /// </summary>
        public Socket socket { get; set; }

        /// <summary>
        /// This member variable acts as a buffer to store all the byte information brought in from the server that has yet to be decoded.
        /// </summary>
        public byte[] buffer { get; set; }

        /// <summary>
        /// This delegate serves as the requirements for the callback functions that can be used in the Network. It must take in a PreservedState obj as a parameter.
        /// </summary>
        /// <param name="state">The callback function will always take in a PreservedState obj in order </param>
        public delegate void CallBackFunction(PreservedState state);

        /// <summary>
        /// This member variable stores the callback function delegate in order to be called and used later.
        /// </summary>
        public CallBackFunction callBack;

        /// <summary>
        /// This member variable sets the size of the byte buffer. 
        /// </summary>
        public const int BUFFERSIZE = 1024;

        /// <summary>
        /// This member variable creates and stores a string builder that contains the decoded bytes from the server as the strings of cube data.
        /// </summary>
        public StringBuilder strBuilder = new StringBuilder();

        /// <summary>
        /// This is the constructor for the PreservedState class, it brings in a socket and callback function and also creates a new byte array. 
        /// </summary>
        /// <param name="socket">The socket that is currently being used to connect between the network and server.</param>
        /// <param name="fcn">The stored callback function that enables smooth transition and communication between the Network and the other project.</param>
        public PreservedState(Socket socket, CallBackFunction fcn)
        {
            // Store the socket and callback function.
            this.socket = socket;

            callBack = fcn;

            // Create and initialize the byte buffer. 
            this.buffer = new byte[BUFFERSIZE];
        }
    }
}
