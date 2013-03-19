// Package Declaration //
package Second_Project_Code;

import java.io.IOException;
// Java Package Support //
import java.util.ArrayList;

// Internal Package Support //
// { Not Applicable } 

/**
 * 
 * Second_Project_Code/Node.java
 * 
 * @author(s)	: Ian Middleton, Zach Ogle, Matthew J Swann
 * @version  	: 1.0
 * Last Update	: 2013-03-19
 * Update By	: Ian Middleton
 * 
 * 
 * Second_Project_Code PACKAGE :: Source code for Comp 6360: Wireless & Mobile Networks
 * 	               Assignment 2 :: VOIP
 * 
 * Source code for Comp 6360: Wireless & Mobile Networks. This code
 * encompasses the node object which will store all the information regarding
 * each node in the network.
 *  
 */

public class Node{
	int number, port, x, y;
	String address;
	ArrayList<Node> links = null;
	
	/**
     * Constructor for the Node class.
     * 
	 * @param  number		: Number of the node.
	 * @param  address      : IP address of the node.
	 * @param  port         : Port number of the node.
	 * @param  x           	: X coordinate of the node.
	 * @param  y           	: Y coordinate of the node.
	 * @param  links        : An array that contains all nodes that this node
	 * 							is linked to.
	 * @param numLinks		: The number of links in the array.
     */
	public Node(int number, String address, int port, int x, int y){
		this.number = number;
		this.address = address;
		this.port = port;
		this.x = x;
		this.y = y;
	} // end Node()
	
	/**
	 * Gets the number of the node.
	 * 
	 * @return 				: An int representing the node.
	 */
	public int getNumber(){
		return number;
	} // end getNumber()
	
	/**
	 * Gets the IP address of the node.
	 * 
	 * @return				: A string containing the IP address.
	 */
	public String getAddress(){
		return address;
	} // end getAddress()
	
	/**
	 * Gets the port of the node.
	 * 
	 * @return				: An int containing the port number.
	 */
	public int getPort(){
		return port;
	} // end getPort()
	
	/**
	 * Gets the X coordinate of the node.
	 * 
	 * @return				: An int containing the X coordinate.
	 */
	public int getX(){
		return x;
	} // end getX()
	
	/**
	 * Sets the X coordinate of the node.
	 * 
	 * @param x				: What the X coordinate is to be changed to.
	 */
	public void setX(int x){
		this.x = x;
	} // end setX()
	
	/**
	 * Gets the Y coordinate of the node.
	 * 
	 * @return				: An int containing the Y coordinate.
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Sets the Y coordinate of the node.
	 * 
	 * @param y				: What the Y coordinate is to be changed to.
	 */
	public void setY(int y){
		this.y = y;
	} // end setY()
	
	public static void setup() throws IOException{
		ArrayList<String> lines = ConfigReader.getLines("/home/ian/Documents/configuration");
		System.out.println(lines.get(0));
	}
	
} // end Node class

