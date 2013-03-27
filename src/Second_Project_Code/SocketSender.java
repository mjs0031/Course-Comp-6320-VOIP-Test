//Package Declaration //
package Second_Project_Code;

//Java Package Support //
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

//Internal Package Support //
// { Not Applicable }

/**
* 
* Second_Project_Code/SocketSender.java
* 
* @author(s)	: Ian Middleton, Zach Ogle, Matthew J Swann
* @version  	: 2.0
* Last Update	: 2013-03-26
* Update By		: Ian R Middleton
* 
* 
* Second_Project_Code PACKAGE :: Source code for Comp 6360: Wireless & Mobile Networks
* 	               Assignment 2 :: VOIP
* 
* This is source code for the SocketSender class. This class records
* 	sound input, packs the sound in BYTE packets, and forwards the data
* 	to the appropriate IP Address(es) based on the Nodes linked to the 
* 	Node calling this thread.
* 
*/


public class SocketSender implements Runnable{
	
	//static variables
	static InetAddress fwdAddress;
	static DatagramPacket fwdDp;
	static DatagramSocket fwdS;
	
	static{
		
		try {
			fwdS = new DatagramSocket();
		} 
		
		catch (SocketException e) {
			// bad
		}
	}
	
	// Audio Variables
	AudioFormat format;
	
	// Transmit Variables
	ArrayList<Node> linkedNodes;
	DatagramSocket s;
	DatagramPacket dp;
	TargetDataLine tLine;
	
	// Control Variables
	boolean running = true;
	byte[] buffer, packet;
	int numBytes;
	
	//Packet Header
	int sequenceNum, srcAddress, destAddress;
	
	/**
	 * Base constructor.
	 * 
	 * @param nodes					: ArrayList of Node objects that are linked to the sending Node.
	 * @param srcAddress			: Number designating the source Node of this message.
	 * @param destAddress			: Number designating the destination Node of this message.
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	public SocketSender(ArrayList<Node> nodes, int srcAddress, int destAddress) throws IOException, LineUnavailableException{
		linkedNodes = nodes;
		
		s       = new DatagramSocket();
		format  = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100,
												16, 2, 4, 44100, false);
		DataLine.Info tLineInfo = new DataLine.Info(TargetDataLine.class, format);
		tLine   = (TargetDataLine)AudioSystem.getLine(tLineInfo);
		tLine.open(this.format);
		tLine.start();
		packet  = new byte[128];
		buffer  = new byte[120];
		
		sequenceNum      = 0;
		this.srcAddress  = srcAddress;
		this.destAddress = destAddress;
		
	} // end SocketSender()
	
	
	/**
	 * Terminate can be called to terminate execution of the thread. A join should be
	 * called afterward in order to wait for the thread to finish.
	 */
	public void terminate(){
		running = false;
	} // end terminate()
	
	
	/**
	 * Static method to be used for forwarding packets.
	 * 
	 * @param address		: String of the address to be sent to.
	 * @param port			: Integer of the port number to be sent to.
	 * @param packet		: The packet to be sent.
	 * @throws IOException	: General IOException.
	 */
	public static void forward(String address, int port, byte[] packet) throws IOException{
		fwdAddress = InetAddress.getByName(address);
		fwdDp      = new DatagramPacket(packet, packet.length, fwdAddress, port);
		fwdS.send(fwdDp);
	} // end forward()
	
	
	/**
	 * Run command called automatically when the thread is started.
	 */
	@Override
	public void run(){	

		InetAddress nextAddress = null;
		int nextPort;
		
		while(running){
			
			// Add sequence number to the packet
			if(sequenceNum < 65536){
				
				packet[0] = (byte)((sequenceNum/256)-128);
				packet[1] = (byte)((sequenceNum%256)-128);
			} // end if
			
			else{
				
				sequenceNum = 0;
				packet[0] = (byte)((sequenceNum/256)-128);
				packet[1] = (byte)((sequenceNum%256)-128);
			} // end else
			
			// Add source address to the packet
			packet[2] = (byte)((srcAddress/256)-128);
			packet[3] = (byte)((srcAddress%256)-128);
			
			// Add destination address to the packet
			packet[4] = (byte)((destAddress/256)-128);
			packet[5] = (byte)((destAddress%256)-128);
			
			// Add previous hop to the packet
			packet[6] = (byte)((srcAddress/256)-128);
			packet[7] = (byte)((srcAddress%256)-128);
			
			numBytes = tLine.read(buffer, 0, buffer.length);
			System.arraycopy(buffer, 0, packet, 8, buffer.length);
			
			for(int i = 0; i < linkedNodes.size(); i++){
				
				try {
					nextAddress = InetAddress.getByName(linkedNodes.get(i).getAddress());
				}// end try
				
				catch (UnknownHostException e1) {
					// live on the edge
				}// end catch
				
				nextPort = linkedNodes.get(i).getPort();
				dp       = new DatagramPacket(packet, packet.length, nextAddress, nextPort);
				try{
					s.send(dp);
				}// end try
				
				catch (IOException e){
					// empty sub-block
				}// end catch
				
			}// end for
		}// end while
	} // end run()		
} // end SocketSender class
