import java.util.ArrayList;

public class StudentNetworkSimulator extends NetworkSimulator {
	/*
	 * Predefined Constants (static member variables):
	 * 
	 * int MAXDATASIZE : the maximum size of the Message data and Packet payload
	 * 
	 * int A : a predefined integer that represents entity A int B : a
	 * predefined integer that represents entity B
	 * 
	 * 
	 * Predefined Member Methods:
	 * 
	 * void stopTimer(int entity): Stops the timer running at "entity" [A or B]
	 * void startTimer(int entity, double increment): Starts a timer running at
	 * "entity" [A or B], which will expire in "increment" time units, causing
	 * the interrupt handler to be called. You should only call this with A.
	 * void toLayer3(int callingEntity, Packet p) Puts the packet "p" into the
	 * network from "callingEntity" [A or B] void toLayer5(int entity, String
	 * dataSent) Passes "dataSent" up to layer 5 from "entity" [A or B] double
	 * getTime() Returns the current time in the simulator. Might be useful for
	 * debugging. void printEventList() Prints the current event list to stdout.
	 * Might be useful for debugging, but probably not.
	 * 
	 * 
	 * Predefined Classes:
	 * 
	 * Message: Used to encapsulate a message coming from layer 5 Constructor:
	 * Message(String inputData): creates a new Message containing "inputData"
	 * Methods: boolean setData(String inputData): sets an existing Message's
	 * data to "inputData" returns true on success, false otherwise String
	 * getData(): returns the data contained in the message Packet: Used to
	 * encapsulate a packet Constructors: Packet (Packet p): creates a new
	 * Packet that is a copy of "p" Packet (int seq, int ack, int check, String
	 * newPayload) creates a new Packet with a sequence field of "seq", an ack
	 * field of "ack", a checksum field of "check", and a payload of
	 * "newPayload" Packet (int seq, int ack, int check) chreate a new Packet
	 * with a sequence field of "seq", an ack field of "ack", a checksum field
	 * of "check", and an empty payload Methods: boolean setSeqnum(int n) sets
	 * the Packet's sequence field to "n" returns true on success, false
	 * otherwise boolean setAcknum(int n) sets the Packet's ack field to "n"
	 * returns true on success, false otherwise boolean setChecksum(int n) sets
	 * the Packet's checksum to "n" returns true on success, false otherwise
	 * boolean setPayload(String newPayload) sets the Packet's payload to
	 * "newPayload" returns true on success, false otherwise int getSeqnum()
	 * returns the contents of the Packet's sequence field int getAcknum()
	 * returns the contents of the Packet's ack field int getChecksum() returns
	 * the checksum of the Packet int getPayload() returns the Packet's payload
	 */

	// Add any necessary class variables here. Remember, you cannot use
	// these variables to send messages error free! They can only hold
	// state information for A or B.
	// Also add any necessary methods (e.g. checksum of a String)

	// Alternating Bit Protocol Code - uncomment to use

	/*
	 * // This member variable is used to store the seq num of A. private int
	 * seqValA; // This member variable keeps track of what seq number B is
	 * expecting. private int bExpectedSeq; // This member variable is used to
	 * determine of the current message has been acked or not. private boolean
	 * acked;
	 */

	// This member variable is used to keep track of how many messages are sent
	// through layer 5 to layer 3 in part A.
	private int messagesCount;

	// This member variable is used to keep track of how many messages are
	// actually sent through layer 3 onto B.
	private int messagesSent;

	// This member variable is used to keep track of how many messages corrupted
	// in transit to B.
	private int corruptedCount;

	// This member variable is used to keep track of how many messages were
	// resent because they were lost or corrupted.
	private int resentCount;

	// This member variable keeps track of the last message sent to B, just in
	// case of time out.
	private Message timeOutMsg;

	// This member variable keeps track of the ack number.
	private int acksCount;

	// The following values were used in the Go-Back-N Protocol

	// This member variable keeps track of the
	private int baseVal;

	// This member variable keeps track of the current seq value.
	private int nextSeqVal;

	// This member variable keeps track of the sequence value that is expected
	// next.
	private int expectedSeqVal;

	// This member variable stores the maximum window size.
	private int windowSize;

	// This member variable stores the maximum buffer size.
	private int bufferSize;
	
	//This member variable stores the amount of dropped packets.
	private int droppedCount;

	// This ArrayList acts as the buffer, holding all the packets for the
	// Go-Back-N protocol.
	private ArrayList<Packet> buffer;

	// This is the constructor. Don't touch!
	public StudentNetworkSimulator(int numMessages, double loss,
			double corrupt, double avgDelay, int trace, long seed) {
		super(numMessages, loss, corrupt, avgDelay, trace, seed);
	}

	// This routine will be called whenever the upper layer at the sender [A]
	// has a message to send. It is the job of your protocol to insure that
	// the data in such a message is delivered in-order, and correctly, to
	// the receiving upper layer.
	protected void aOutput(Message message) {
		// Alternating Bit Protocol Code - uncomment to use

		/*
		 * // Increment the amount of total recieved so far. messagesCount++; //
		 * If the message has been acked then go ahead and send it. if (acked) {
		 * // Create the checksum value for the message about to be sent. String
		 * checksum = message.getData() + seqValA + 0; int checksumVal = 0;
		 * for(int i = 0; i < checksum.length(); i++){ checksumVal = checksumVal
		 * + (int)checksum.charAt(i); } // Create the packet to send to Layer 3.
		 * Packet toLayer3 = new Packet(seqValA, 0, checksumVal,
		 * message.getData()); // Save a copy of the message just in case of
		 * time out. timeOutMsg = message; // Increment the amount of messages
		 * sent so far. messagesSent++; // Set the ack value of the new message
		 * to false. acked = false; // Start the send timer. startTimer(0, 20);
		 * // Send the packet to Layer 3. toLayer3(0, toLayer3); } else{
		 * System.out.println("In A: Skipped Message due to another on wire.");
		 * }
		 */

		// This is for the Go-Back-N protocol
		
		// If the buffer size hasn't reached its max capacity, add a another packet.
		if (buffer.size() < windowSize + bufferSize + baseVal) {
			
			// Generate the checksum value and create a new packet and add it to the buffer.
			int checksumVal = (buffer.size() + buffer.size() + message.getData()).hashCode();
			
    		Packet p = new Packet(buffer.size(), buffer.size(), checksumVal, message.getData());
    		buffer.add(p);
    		
    		// I enclosed this into a try catch in the 
    		try {
    			while (nextSeqVal < windowSize + baseVal) {
    				
    				// Send the packet to layer 3 and start the timer if sequnce values match.
    				toLayer3(A, buffer.get(nextSeqVal));
    				
    				if (nextSeqVal == baseVal) {
    					startTimer(A, 30);
    				}
    				
    				messagesSent++;
    				nextSeqVal++;
    				
    			}
    		}
    		catch (IndexOutOfBoundsException e) {
    			System.out.println("Both buffer and window are empty.");
    		}
    	}
		
		// Increment the amount of messages sent.
    	messagesCount++;

	}

	// This routine will be called whenever a packet sent from the B-side
	// (i.e. as a result of a toLayer3() being done by a B-side procedure)
	// arrives at the A-side. "packet" is the (possibly corrupted) packet
	// sent from the B-side.
	protected void aInput(Packet packet) {
		// Alternating Bit Protocol Code - uncomment to use

		/*
		 * // Create the checksum value for the message about to be sent. String
		 * packetCheckSum = packet.getPayload() + packet.getSeqnum() +
		 * packet.getAcknum(); int packetCheckSumVal = 0; for(int i = 0; i <
		 * packetCheckSum.length(); i++){ packetCheckSumVal = packetCheckSumVal
		 * + (int)packetCheckSum.charAt(i); } // If the packet is the correct
		 * packet if (packetCheckSumVal == packet.getChecksum()) { if
		 * (packet.getAcknum() == seqValA) { stopTimer(0); acked = true;
		 * if(seqValA == 1){ seqValA = 0; } else{ seqValA = 1; } acksCount++; }
		 * } else { corruptedCount++; }
		 */
		
		//Go-Back-N Protocol
		
		// Recreate the checksum value.
		int checksumVal = (packet.getSeqnum() + packet.getAcknum() + packet.getPayload()).hashCode();
		 
		// If its not equal increment the corrupted count.
		if (checksumVal != packet.getChecksum()) {
			corruptedCount++;
    	}
		// If it is equal increment the base value and if they match stop the timer.
    	else {
    		baseVal = packet.getAcknum() + 1;
    		if (nextSeqVal == baseVal) {
    			stopTimer(A);
    		}
    	}
	}

	// This routine will be called when A's timer expires (thus generating a
	// timer interrupt). You'll probably want to use this routine to control
	// the retransmission of packets. See startTimer() and stopTimer(), above,
	// for how the timer is started and stopped.
	protected void aTimerInterrupt() {
		// Alternating Bit Protocol Code - uncomment to use

		/*
		 * // Create the checksum value for the message about to be sent. String
		 * checksum = timeOutMsg.getData() + seqValA + 0; int checksumVal = 0;
		 * for(int i = 0; i < checksum.length(); i++){ checksumVal = checksumVal
		 * + (int)checksum.charAt(i); } //Re-Create the message packet to be
		 * sent again. Packet toLayer3 = new Packet(seqValA, 0, checksumVal,
		 * timeOutMsg.getData()); // Start the transmission timer. startTimer(0,
		 * 20); // Increment the resent message count. resentCount++; // Re-Send
		 * the message to Layer 3. toLayer3(0, toLayer3);
		 */
		
		// Go-Back-In-Protocol
		
		// Restart the timer
		startTimer(A, 30);
		
		// Resend all packets after the packet that was lost. And increment the resent count.
    	for (int packet = baseVal; packet < nextSeqVal; packet++) {
    		Packet p = buffer.get(packet);
    		toLayer3(A, p);
    		resentCount++;
    	}

	}

	// This routine will be called once, before any of your other A-side
	// routines are called. It can be used to do any required
	// initialization (e.g. of member variables you add to control the state
	// of entity A).
	protected void aInit() {
		// Alternating Bit Protocol Code - uncomment to use
		/*
		 * acked = true; seqValA = 0;
		 */

		// Initialize all the values for the beginning of transporting all the desired messages.
		messagesCount = 0;
		messagesSent = 0;
		acksCount = 0;
		corruptedCount = 0;
		resentCount = 0;

		// Go-Back-N variables
		bufferSize = 50;
		windowSize = 8;
		nextSeqVal = 0;
		baseVal = 0;
		buffer = new ArrayList<Packet>();

	}

	// This routine will be called whenever a packet sent from the B-side
	// (i.e. as a result of a toLayer3() being done by an A-side procedure)
	// arrives at the B-side. "packet" is the (possibly corrupted) packet
	// sent from the A-side.
	protected void bInput(Packet packet) {
		// Alternating Bit Protocol Code - uncomment to use

		/*
		 * // Create the checksum value for the message that was sent. String
		 * packetCheckSum = packet.getPayload() + packet.getSeqnum() +
		 * packet.getAcknum(); int packetCheckSumVal = 0; for(int i = 0; i <
		 * packetCheckSum.length(); i++){ packetCheckSumVal = packetCheckSumVal
		 * + (int)packetCheckSum.charAt(i); } // If the packet checksum value is
		 * the correct one, then retrieve the message. if (packetCheckSumVal ==
		 * packet.getChecksum()) { // If the sequence num is the one B was
		 * expecting continue. if (packet.getSeqnum() == bExpectedSeq) { // Set
		 * the Seq number based on the current packet seq number.
		 * if(packet.getSeqnum() == 1){ bExpectedSeq = 0; } else{ bExpectedSeq =
		 * 1; }
		 * 
		 * if (!packet.getPayload().equals(timeOutMsg.getData())){
		 * System.out.println("At B: Payload mismatch."); } // Sent packet
		 * payload up to layer 5. toLayer5(1, packet.getPayload()); } // Create
		 * the checksum value for the message about to be sent. String
		 * toLayer3CheckSum = "Empty" + 0 + packet.getSeqnum(); int
		 * toLayer3CheckSumVal = 0; for(int i = 0; i <
		 * toLayer3CheckSum.length(); i++){ toLayer3CheckSumVal =
		 * toLayer3CheckSumVal + toLayer3CheckSum.charAt(i); } //Send the packet
		 * up to layer 3. Packet toLayer3 = new Packet(0, packet.getSeqnum(),
		 * toLayer3CheckSumVal, "Empty"); toLayer3(1, toLayer3); } // If the
		 * checksum value is incorrect. else { // Increment the corrupt message
		 * count. corruptedCount++; }
		 */
		
		//Go-Back-N Protocol
		
		// Re-Create the checksum value to ensure that it is correct. 
		int checksumVal = (packet.getSeqnum() + packet.getAcknum() + packet.getPayload()).hashCode();
		
		// If they dont match increment the corrupted count.
		if (checksumVal != packet.getChecksum()) {
			
			corruptedCount++;
			
    	}
		// If they do create a ack packet to send back to a.
    	else {
    			
            int newPacketChecksum = (packet.getSeqnum() + packet.getAcknum() + "Empty").hashCode();
			
    		Packet p = new Packet(packet.getSeqnum(), packet.getAcknum(), newPacketChecksum, "Empty");
    		
    		
    		if (packet.getSeqnum() == expectedSeqVal) {
    			toLayer5(B, packet.getPayload());
    			expectedSeqVal++;
    		}
    		
    		toLayer3(B, p);
    	}
    	acksCount++;
	}

	// This routine will be called once, before any of your other B-side
	// routines are called. It can be used to do any required
	// initialization (e.g. of member variables you add to control the state
	// of entity B).
	protected void bInit() {
		// Alternating Bit Protocol Code - uncomment to use

		/*
		 * // Initialize the sequence number value that B is waiting for to 0.
		 * bExpectedSeq = 0;
		 */

		// Used for Go-Back-N Protocol

		// Initialize the expected sequence number Value that B is waiting for
		// to 0.
		expectedSeqVal = 0;

	}

	/**
	 * This method is used for print out vital statistics about the packet
	 * transmission that just took place.
	 */
	protected void viewStatistics() {
		System.out.println("\n Transmission Statistics: \n");
		System.out.println("Messages sent from Layer 5: " + messagesCount);
		System.out.println("Packets sent by sender: " + messagesSent);
		System.out.println("Packets still on the wire: " + (messagesCount - messagesSent));
		System.out.println("Packets Resent: " + resentCount);
		System.out.println("Packets correctly ACKed: " + acksCount);
		System.out.println("Corrupted Packets: " + corruptedCount);
		System.out.println("Dropped Packets: " + droppedCount);
	}

}
