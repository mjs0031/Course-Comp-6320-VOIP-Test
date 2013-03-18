// Package Declaration //
package Second_Project_Code;

import java.io.IOException;

// Java Package Support //
// { Not Applicable }

// Internal Package Support //
// { Not Applicable }

/**
 * 
 * Second_Project_Code/Node.java
 * 
 * @author(s)	: Ian Middleton, Zach Ogle, Matthew J Swann
 * @version  	: 1.0
 * Last Update	: 2013-03-18
 * Update By	: Ian R Middleton
 * 
 * 
 * Second_Project_Code PACKAGE :: Source code for Comp 6360: Wireless & Mobile Networks
 * 	               Assignment 2 :: VOIP
 * 
 * Source code for Comp 6360: Wireless & Mobile Networks. This code
 * encompasses the node object which will store all the information regarding
 * each node in the network.
 *  
 *  ***INCOMPLETE***
 */

public class Node{
	int number, port, x, y;
	string address;
	Node[] links;
	
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
     */
	public Node(int number, string address, int port, int x, int y, Node[] links){
		this.number = number;
		this.address = address;
		this.port = port;
		this.x = x;
		this.y = y;
		this.links = links;
	} // end Node()
	
} // end Node class

