// -------------------------------------------------------------------------+
// GUI Programming                      			Assignment_2            |
// -------------------------------------------------------------------------+
// File: mouseWords.java                                                 	|
// -------------------------------------------------------------------------+
// Author:  Daragh Walshe                           Group: Group 1          |
// Student# B00064428                               Date:  Nov. 2013   		|
// -------------------------------------------------------------------------+
// DESCRIPTION:                                                             |
// A program which creates a window with a menu. The two choices on the		|
// menu are both JInternelFrame applications: A mouse listening app. and    |
// a game to count to ten in one of three chosen languages.					|
// Required: cheesyWin.java, languageCount.java and icons(folder) 			|
// -------------------------------------------------------------------------+

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.UIManager.*;

class mouseWords extends JFrame implements ActionListener{

	//global variables
	JMenuItem mouseItem, gameItem, exitItem;
	JMenuBar myMenuBar;
	JDesktopPane desktop;
	JInternalFrame welcome;
	Border blankBorder;

	//-----------------------------------------------------------------------
	public static void main(String args[]){

		//set the look and feel to 'nimbus'
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e){
			}

		new mouseWords();

		}//end main
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public mouseWords(){
			//set size, location and title of window
			super("Assignment 2");
			setSize(900,600);
			setLocation(0,0);

			//create a new menu-bar
			setJMenuBar(makeMenuBar());

			//a many layered container - necessary for JInternalFrames
			desktop = new JDesktopPane();

			//makes a welcome window
			desktop.add(makeWelcome());

			setContentPane(desktop);
			setVisible(true);
		}//end public mouseWords
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public JInternalFrame makeWelcome(){

			//makes a welcome window
			welcome = new JInternalFrame("", false, false, false, false);
			blankBorder = BorderFactory.createEmptyBorder(25,20,35,20);
			Font welFont = new Font("Meiryo", Font.ITALIC, 22);//Batang

			JLabel welcomeLabel = new JLabel("Welcome Please choose from the menu above", JLabel.CENTER);

			welcomeLabel.setFont(welFont);
			welcomeLabel.setForeground(Color.yellow);
			welcomeLabel.setBackground(Color.black);
			welcomeLabel.setOpaque(true);
			welcomeLabel.setBorder(blankBorder);

			welcome.add(welcomeLabel);
			welcome.setLocation(200, 150);
			welcome.setVisible(true);
			welcome.pack();

			return welcome;

		}//end makeWelcome
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public void actionPerformed(ActionEvent e){

		//action handling on the menu bar
		if(e.getSource() == mouseItem){
			desktop.remove(welcome);
			cheesyWin aCheesyWin = new cheesyWin();
			desktop.add(aCheesyWin);
			desktop.repaint();
			}
		else if(e.getSource() == gameItem){
			desktop.remove(welcome);
			languageCount lc = new languageCount();
			lc.pack();
			desktop.add(lc);
			}
		else if(e.getSource() == exitItem){
			System.exit(0);
			}

		}//end actionPerformed
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public JMenuBar makeMenuBar(){

		ImageIcon selectIcon = new ImageIcon("icons/select.png");
		ImageIcon mouseIcon = new ImageIcon("icons/mouse.png");
		ImageIcon flagsIcon = new ImageIcon("icons/flags.png");
		ImageIcon exitIcon = new ImageIcon("icons/exit.png");

		//create a new menu-bar
		JMenuBar aMenuBar = new JMenuBar();

		//create a new menu and add it to the menuBar
		JMenu onlyMenu = new JMenu("  Select", true);
		onlyMenu.setIcon(selectIcon);
		aMenuBar.add(onlyMenu);

		//add menu items to the select menu
		mouseItem = new JMenuItem("  Mouse Listener Window");
		mouseItem.addActionListener(this);
		onlyMenu.add(mouseItem);
		mouseItem.setIcon(mouseIcon);

		gameItem = new JMenuItem("  Language Game");
		gameItem.addActionListener(this);
		onlyMenu.add(gameItem);
		gameItem.setIcon(flagsIcon);

		onlyMenu.addSeparator();

		exitItem = new JMenuItem("  Exit");
		exitItem.addActionListener(this);
		onlyMenu.add(exitItem);
		exitItem.setIcon(exitIcon);

		//sets a key mnemonic for these menu items(alt + key)
		onlyMenu.setMnemonic('S');
		mouseItem.setMnemonic('M');
		gameItem.setMnemonic('L');
		exitItem.setMnemonic('X');

		//sets a key accelerator for these menu items(ctrl + key)
		mouseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		gameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		return aMenuBar;

		}//end makeMenuBar
	//-----------------------------------------------------------------------



	}//end class mouseWords
