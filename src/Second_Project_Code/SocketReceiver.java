//Package Declaration //
package Second_Project_Code;

//Java Package Support //
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;

//Internal Package Support //
// { Not Applicable }


/**
* 
* Second_Project_Code/SocketReceiver.java
* 
* @author(s)	: Ian Middleton, Zach Ogle, Matthew J Swann
* @version  	: 1.0
* Last Update	: 2013-03-26
* Update By		: Ian Middleton
* 
* 
* Second_Project_Code PACKAGE :: Source code for Comp 6360: Wireless & Mobile Networks
* 	               Assignment 2 :: VOIP
* 
* This is source code for the SocketReceiver class. This class accepts a
* set of packets, unpacks the BYTES and plays back the sound.
* 
*/


public class SocketReceiver implements Runnable{
	
	// Audio Variables
	AudioFormat format;
	
	// Transmit Variables
	DatagramPacket dp;
	DatagramSocket s;
	SourceDataLine sLine;
	
	// Control Variables
	boolean running = true;
	byte[] buf;
	int number, port;
	String address;
	ArrayList<Node> nodes;
	ArrayList<int[]> cache = new ArrayList<int[]>();
	byte[] playbuf = new byte[120];
	
	/**
	 * Base constructor.
	 * 
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	public SocketReceiver(int nodeNum, String address, int port, ArrayList<Node> linkedNodes) throws IOException, LineUnavailableException{
		this.buf    = new byte[128];
		this.s      = new DatagramSocket(port);
		this.dp     = new DatagramPacket(buf, buf.length);
		this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100,
												16, 2, 4, 44100, false);		
		DataLine.Info sLineInfo = new DataLine.Info(SourceDataLine.class, this.format);
		this.sLine   = (SourceDataLine)AudioSystem.getLine(sLineInfo);
		this.nodes   = linkedNodes;
		this.number  = nodeNum;
		this.address = address;
		this.port    = port;
		
	} // end SocketReceiver()
	
	/**
	 * Terminate can be called to terminate execution of the thread. A join should be
	 * called afterward in order to wait for the thread to finish.
	 */
	public void terminate(){
		
		byte[] buffer = new byte[8];
		
		for(int i = 0; i < 8; i++){
			buffer[i] = -128;
		}
		
		try {
			SocketSender.forward(address, port, buffer);
		}
		
		catch (IOException e) {
			// Live on the edge.
		}
		
	} // end terminate()
	
	/**
	 * Run command called automatically by Thread.start().
	 * 
	 * @throws IOException			: General IOException for package functions.
	 * @throws LineUnavailable		: General LineUnavailable for package 
	 * 										functions.
	 */
	@Override
	public void run(){
		try {
			this.sLine.open(this.format);
		}
		
		catch (LineUnavailableException e){
			// empty sub-block	
		}
		
		this.sLine.start();
		
		// Continues until program is closed.
		while(running){
			
			try{
				this.s.receive(this.dp);
			}
			
			catch (IOException e){
				// empty sub-block		
			}
			
			int sequence    = ((dp.getData()[0] + 128) * 256) + dp.getData()[1] + 128;
			int source      = ((dp.getData()[2] + 128) * 256) + dp.getData()[3] + 128;
			int destination = ((dp.getData()[4] + 128) * 256) + dp.getData()[5] + 128;
			int prevHop     = ((dp.getData()[6] + 128) * 256) + dp.getData()[7] + 128;
			
			if (source == 0){
			
				running = false;
			}
			
			else if (destination == number){
				System.arraycopy(this.dp.getData(), 8, playbuf, 0, playbuf.length);
				System.out.println("Playing packet: " + (((this.dp.getData()[0] + 128) * 256) + this.dp.getData()[1] + 128) + "	" + (((this.dp.getData()[2] + 128) * 256) + this.dp.getData()[3] + 128) + "	" + (((this.dp.getData()[4] + 128) * 256) + this.dp.getData()[5] + 128) + "	" + (((this.dp.getData()[6] + 128) * 256) + this.dp.getData()[7] + 128));
				this.sLine.write(playbuf, 0, playbuf.length);
			}
			
			else if (source != number){
			
				int count;
				
				for (count = 0; count < cache.size(); count++){
				
					if (cache.get(count)[0] == source){
					
						if (cache.get(count)[1] < sequence){
							cache.get(count)[1] = sequence;
						}
						break;
					}
				}
				if (count == cache.size()){
					
					cache.add(new int[2]);
					cache.get(count)[0] = source;
					cache.get(count)[1] = sequence;
				}
				
				
				byte[] buffer = dp.getData();
				buffer[6] = (byte)((number / 256) - 128);
				buffer[7] = (byte)((number % 256) - 128);
				
				for (count = 0; count < nodes.size(); count++){
					
					if (nodes.get(count).getNumber() != prevHop){
						try {
							SocketSender.forward(nodes.get(count).getAddress(), nodes.get(count).getPort(), buffer);
						}
						
						catch (IOException e){
							//empty sub-block
						}
					}
				}
			}
		} // end while
		
		s.close();
	} // end SocketReceiver.run()

	
} // end SocketReceiver class
