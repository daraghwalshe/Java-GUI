// -------------------------------------------------------------------------+
// GUI Programming                      			Assignment 2            |
// -------------------------------------------------------------------------+
// File: cheesyWin.java                                               		|
// -------------------------------------------------------------------------+
// Author:  Daragh Walshe                           Group: Group 1          |
// Student# B00064428                               Date:  Nov. 2013   		|
// -------------------------------------------------------------------------+
// DESCRIPTION:                                                             |
// A class to construct a JInternalFrame with a JLabel displaying mouse		|
// events in the centre of the window, used for mouseWords.java				|
// -------------------------------------------------------------------------+

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputListener;


// MouseInputListener: implements the MouseListener and MouseMotionListener
//--------------------------------------------------------------------------
class cheesyWin extends JInternalFrame implements MouseInputListener{

	JLabel messageLabel, moveLabel;
	JPanel onlyPanel;
	String labelMessage, newText, messageText, movedText;
	String oldText = " Mouse events go here";
	int messageCounter = 0;
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public cheesyWin(){

		super("Labwork 8-2", true, true, true, true);
		setSize(600,500);

		//inherited from JInternalFrame
/*		setClosable(true);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);*/

		//set up main window
		onlyPanel = new JPanel(new GridLayout(3,3));
		onlyPanel.addMouseMotionListener(this);

		//label to display the mouse events
		messageLabel = new JLabel(" Mouse events go here");
		messageLabel.setBackground(Color.PINK);
		messageLabel.setOpaque(true);
		messageLabel.setBorder(new LineBorder(Color.BLACK));
		messageLabel.addMouseListener(this);

		//label which shows the x and y in the whole JPanel
		moveLabel = new JLabel("Move events go here");

		onlyPanel.add(new JLabel());
		onlyPanel.add(new JLabel());
		onlyPanel.add(new JLabel());
		onlyPanel.add(new JLabel());
		onlyPanel.add(messageLabel);
		onlyPanel.add(new JLabel());
		onlyPanel.add(new JLabel());
		onlyPanel.add(moveLabel);


		Container c = getContentPane();
		c.add(onlyPanel);

		//pack();

		setVisible(true);

		}//end cheesyWin
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public void mouseClicked(MouseEvent e){
		writeToLabel("<br> mouse clicked in label:<br>x = " + e.getX() + " y = " + e.getX() );
		}
	//-----------------------------------------------------------------------
	public void mouseEntered(MouseEvent e){
		writeToLabel("<br> mouse entered JLabel");
		}
	//-----------------------------------------------------------------------
	public void mouseExited(MouseEvent e){
		writeToLabel("<br> mouse exited JLabel");
		}
	//-----------------------------------------------------------------------
	public void mousePressed(MouseEvent e){
		writeToLabel("<br> mouse button pressed");
		}
	//-----------------------------------------------------------------------
	public void mouseReleased(MouseEvent e){
		writeToLabel("<br> mouse button released");
		}
	//-----------------------------------------------------------------------
	public void mouseDragged(MouseEvent e){
		writeToLabel("<br> mouse is dragging");
		}
	//-----------------------------------------------------------------------
	public void mouseMoved(MouseEvent e){

		movedText = "<html> Mouse location in JPanel:<br> x-coordinate = " + e.getX() + "<br>" + " y-coordinate = " + e.getY() + "</html>";
		moveLabel.setText(movedText);
		}
	//-----------------------------------------------------------------------
	//method to write multiple messages to the JLabel
	public void writeToLabel(String messageIn){

		//counter to wipe label after 10 messages
		messageCounter++;
		if(messageCounter == 10){
			oldText = "";
			messageCounter = 0;
			}

		//add new line to label
		newText = oldText + messageIn;

		//wrap in tags and display
		messageText = "<html>" + newText + "</html>";
		messageLabel.setText(messageText);

		//set oldText before next message arrives
		oldText = newText;

		}//end writeToLabel

	}//end class cheesyWin




