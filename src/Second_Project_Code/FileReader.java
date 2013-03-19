// Package Declaration //
package Second_Project_Code;

// Java Package Support //
// { Not Applicable }

// Internal Package Support //
// { Not Applicable }

/**
 * 
 * Second_Project_Code/FileReader.java
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
 * encompasses the file reader which will read the configuration file 
 * for setting up the network.
 *  
 */

public class FileReader{
	
	/**
	 * Reads the lines from a file and returns them in an array.
	 * 
	 * @param fileLoc			: A string of the location of the file to be read.
	 * @return					: An array holding all the lines in the file.
	 */
	public string[] getLines(string fileLoc){
		File file = new File(fileLoc);
		string[] lines = new string[];
		string line;
		int lineCount = 0;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		while((line = reader.readLine()) != null){
			lines[lineCount] = line;
			lineCount++;
		}
		
		return lines;
	} // end getLines()
	
} // end FileReader class

