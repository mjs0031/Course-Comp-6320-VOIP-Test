//Package Declaration //
package ProductionCode;

//Java Package Support //
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.LineUnavailableException;

//Internal Package Support //
// { Not Applicable }



/**
 * 
 * ProductionCode/VOIP_Gui.java
 * 
 * @author(s)	: Ian Middleton, Zach Ogle, Matthew J Swann
 * @version  	: 1.0
 * Last Update	: 2013-03-01
 * Update By	: Matthew J Swann
 * 
 * Source code for Comp 6360: Wireless & Mobile Networks. This code
 * encompasses the graphical user interface and runs the transmission
 * sender and receiver for ad-hoc VOIP transmission.
 * 
 */

public class VOIP_Gui extends JFrame{
	
	// Static Variables
	private static final long serialVersionUID = 1L;

	// GUI Variables
	private JButton button_one, button_two;
	private JLabel label_one, label_two, label_three, label_four,
					label_five;
	private JPanel panel_one, primary;
	private JTextField text_field_one;
	
	private TransmitListener TransmitListener;
	private KillSwitch KillSwitch;
	
	// VOIP Variables
	//private SocketSender sender;
	//private SocketReceiver receiver;
	
	
	/**
     * Constructor for the GUI class.
     * 
	 * @param  args           : Not currently implemented
	 * @throws IOException    : General IOException for package functions.
	 * @throws LineUnavailable: General LineUnavailable for package
	 * 								functions. 
     */
	public VOIP_Gui() throws IOException, LineUnavailableException{
		
		// JButtons
		this.button_one = new JButton(" :: Connect");
		this.button_one.setFont(new Font("Helvetica", Font.BOLD, 12));
		this.button_one.setPreferredSize(new Dimension(150,50));
		TransmitListener = new TransmitListener();
		this.button_one.addActionListener(TransmitListener);
		
		this.button_two = new JButton("{ Disconnect }");
		KillSwitch      = new KillSwitch();
		this.button_two.addActionListener(KillSwitch);
						
		// JLabels
		this.label_one   = new JLabel("Enter IP Connection ::");
		this.label_two   = new JLabel("");
		this.label_three = new JLabel("");
		this.label_four  = new JLabel("");
		this.label_five  = new JLabel("");
		
		// JPanels
		this.panel_one = new JPanel();
		this.primary   = new JPanel();
				
		// JTextField
		this.text_field_one = new JTextField(15);
		
		// Control Block
		this.compile_components();
		
	} // end VOIP_Gui()
	
	
    /**
     * Places components on the appropriate panels.
     */	
	public void compile_components(){
		
		this.getContentPane().add(primary);
		
		// Panel One additions
		this.panel_one.setLayout(new GridLayout(4, 4));
		this.panel_one.add(this.label_one);	
		this.panel_one.add(this.label_two);
		this.panel_one.add(this.text_field_one);
		this.panel_one.add(this.button_one);
		this.panel_one.add(this.label_three);
		this.panel_one.add(this.label_four);
		this.panel_one.add(this.label_five);
		this.panel_one.add(this.button_two);

		
		// Primary Panel additions
		this.primary.setPreferredSize(new Dimension(4*100,3*100));
		this.primary.add(this.panel_one);
	} // end VOIP_Gui.compile_components()

	
	/**
	 * Action listener for the transmission and connect buttons.
	 * Attached to the connect button.
	 */
	private class TransmitListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			String ip_address = text_field_one.getText();
			
			try{
				//sender = new SocketSender(ip_address);
				//receiver = new SocketReceiver();
			}
			catch (Exception e){
				// skip it
			}	
			//sender.start();
			//receiver.start();
			
			text_field_one.setText("");
			
		} // end TransmitListener.actionPerformed()
	}// end TransmitListener()
	
	
	/**
	 * Action listener for the kill command. Attached to the
	 * Disconnect button.
	 */
	private class KillSwitch implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			
			// Kill command
			System.exit(0);
		}// end KillSwitch.actionPerformed()	
	} // end KillSwitch()
		
	
		/**
		 * Runs the Gui. Constructs the complementary variables, etc.
		 * Runs until the KillSwitch is engaged.
		 * 
		 * @param  args        : Not currently implemented
		 * @throws IOException : General IOException for package functions.
		 * 
		 */
	    public static void main(String[] args) throws IOException,
	    							IOException, LineUnavailableException{
			VOIP_Gui teh_gui = new VOIP_Gui();
			
			teh_gui.setTitle("VOIP to end all VOIP's");
			teh_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			teh_gui.pack();
			teh_gui.setVisible(true);
		} // end main()	
	    
	} // end VOIP_Gui class
