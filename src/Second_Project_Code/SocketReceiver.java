//Package Declaration //
package Second_Project_Code;

//Java Package Support //
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

//Internal Package Support //
// { Not Applicable }


/**
* 
* Second_Project_Code/SocketReceiver.java
* 
* @author(s)	: Ian Middleton, Zach Ogle, Matthew J Swann
* @version  	: 2.0
* Last Update	: 2013-03-18
* Update By		: Ian R Middleton
* 
* 
* Second_Project_Code PACKAGE :: Source code for Comp 6360: Wireless & Mobile Networks
* 	               Assignment 2 :: VOIP
* 
* This is source code for the SocketReceiver class. This class accepts a
* set of packets, unpacks the BYTES and plays back the sound.
* 
*/


public class SocketReceiver extends Thread{
	
	// Audio Variables
	AudioFormat format;
	
	// Transmit Variables
	InetAddress address;
	DatagramPacket dp;
	DatagramSocket s;
	SourceDataLine sLine;
	
	// Control Variables
	boolean is_true = true;
	byte[] buf;
	
	
	/**
	 * Base constructor.
	 * 
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	public SocketReceiver() throws IOException, LineUnavailableException{
		this.buf    = new byte[2048];
		this.s      = new DatagramSocket(10150);
		this.dp     = new DatagramPacket(buf, buf.length);
		this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100,
												16, 2, 4, 44100, false);		
		DataLine.Info sLineInfo = new DataLine.Info(SourceDataLine.class, this.format);
		this.sLine = (SourceDataLine)AudioSystem.getLine(sLineInfo);	
		
	} // end SocketReceiver()
	
	
	/**
	 * Run command called automatically by Thread.start().
	 * 
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	@Override
	public void run(){
		try{
			this.sLine.open(this.format);
		}
		catch (LineUnavailableException e){
			// empty sub-block	
		}
		this.sLine.start();
		
		// Continues until program is closed.		
		while(this.is_true){
			try{
				this.s.receive(this.dp);
			}
			catch (IOException e){
				// empty sub-block		
			}
			this.sLine.write(this.dp.getData(), 0, this.dp.getLength());
		} // end while
		
	} // end SocketReceiver.run()

	
} // end SocketReceiver class
