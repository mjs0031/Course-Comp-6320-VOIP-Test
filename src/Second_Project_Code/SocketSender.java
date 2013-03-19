//Package Declaration //
package Second_Project_Code;

//Java Package Support //
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
* Last Update	: 2013-03-19
* Update By		: Ian R Middleton
* 
* 
* Second_Project_Code PACKAGE :: Source code for Comp 6360: Wireless & Mobile Networks
* 	               Assignment 2 :: VOIP
* 
* This is source code for the SocketSender class. This class records
* sound input, packs the sound in BYTE packets, and forwards the data
* to the appropriate IP Address.
* 
* *** INCOMPLETE ***
* 
*/


public class SocketSender implements Runnable{

	static InetAddress fwdAddress;
	static DatagramPacket fwdDp;
	static DatagramSocket s;
	static Object lock;
	
	static{
		lock = new Object();
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// bad
		}
	}
	
	// Audio Variables
	AudioFormat format;
	
	// Transmit Variables
	InetAddress address;
	DatagramPacket dp;
	TargetDataLine tLine;
	
	// Control Variables
	boolean running = true;
	byte[] buffer;
	int numBytes;
	
	/**
	 * Base constructor.
	 * 
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	public SocketSender(String nextAddress, String destAddress) throws IOException, LineUnavailableException{
		address = InetAddress.getByName(nextAddress);
		format  = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100,
												16, 2, 4, 44100, false);
		DataLine.Info tLineInfo = new DataLine.Info(TargetDataLine.class, format);
		tLine   = (TargetDataLine)AudioSystem.getLine(tLineInfo);
		tLine.open(this.format);
		tLine.start();
		buffer = new byte[128];
		
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
		fwdDp = new DatagramPacket(packet, packet.length, fwdAddress, port);
		synchronized(lock){
			s.send(fwdDp);
		} // end synchronized
	} // end forward()
	
	/**
	 * Run command called automatically when the thread is started.
	 * 
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	@Override
	public void run(){	

		// Continues until program is closed.
		while(running){
			numBytes = tLine.read(buffer, 0, buffer.length);
			dp = new DatagramPacket(buffer, buffer.length, address, 10150);
			synchronized(lock){
				try{
					s.send(dp);
				}
				catch (IOException e){
					// empty sub-block
				}
			} // end synchronized
		}// end while
		
	} // end SocketSender.run()
		
		
} // end SocketSender class
