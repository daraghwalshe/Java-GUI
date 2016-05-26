// -------------------------------------------------------------------------+
// GUI Programming                      			Assignment 2            |
// -------------------------------------------------------------------------+
// File: languageCount.java                                               	|
// -------------------------------------------------------------------------+
// Author:  Daragh Walshe                           Group: Group 1          |
// Student# B00064428                               Date:  Nov. 2013   		|
// -------------------------------------------------------------------------+
// DESCRIPTION:                                                             |
// A class to construct a JInternalFrame with a game where the user 		|
// selects one of three languages and then must attempt to count to ten		|
// in the chosen language by clicking on labels on the game board.			|
// If the wrong word is picked a message appears, used for mouseWords.java	|
// -------------------------------------------------------------------------+

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputListener;


//--------------------------------------------------------------------------
class languageCount extends JInternalFrame implements ActionListener, MouseListener{

	JLabel[] wordBox = new JLabel[30];
	JPanel wordPanel, outerPanel;
	JLabel answer, answerBottom, answerTop, flag;
	Border blankBorder;

	ImageIcon[] flags = {new ImageIcon("icons/portugal.png"),
						 new ImageIcon("icons/sweden.png"),
						 new ImageIcon("icons/germany.png")
						 };
	ImageIcon correct1 = new ImageIcon("icons/correct.png");
	ImageIcon correct = new ImageIcon("icons/correct4.png");
	ImageIcon wrong = new ImageIcon("icons/wrong1.png");

	//Portuguese, Swedish, German
	String[] words = {"Um","Dois","Tr&ecirc;","Quatro","Cinco","Ceis","Sete","Oito","Nove","Dez",
					  "En","Tv&aring;","Tre","Fyra","Fem","Sex","Sju","&Aring;tta","Nio","Tio",
					  "Eins","Zwei","Drei","Vier","F&uuml;nf","Sechs","Sieben","Acht","Neun","Zehn"};

	String[] nextNum = {"1","2","3","4","5","6","7","8","9","10",""};

	int[] tracker = new int[30];
	JComboBox languageChoice;
	JButton reset;

	int thisGame, endOfGame, nextNumber;

	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public languageCount(){

		//set up the window
		super("Language Count", true, true, true, true);
		//setSize(800,500);
		setLocation(25,25);

		//make the two main panels
		wordPanel = makeWordPanel();
		JPanel comboPanel = makeComboPanel();

		//arrange window layout
		outerPanel = new JPanel(new BorderLayout());
		outerPanel.add(wordPanel);
		outerPanel.add(comboPanel, BorderLayout.EAST);

		Container c = getContentPane();
		c.add(outerPanel);

		setVisible(true);

		}//end languageCount

	//-----------------------------------------------------------------------
	public JPanel makeWordPanel(){

		makeRandom();

		//make the word-board
		JPanel thisPanel = new JPanel( new GridLayout(6,5) );
		blankBorder = BorderFactory.createEmptyBorder(15,20,20,20);
		thisPanel.setBorder(blankBorder);

		//fill the JLabel[] with ?'s in random colours
		for(int i=0; i<30; i++){

			wordBox[i] = new JLabel("<html><font size=" + ( (int)(Math.random()*6 +2) ) + ">?</font></html>",JLabel.CENTER);
			wordBox[i].setForeground( getRandomColor(210,30) );
			wordBox[i].setBackground(getRandomColor(160,0));
			wordBox[i].setOpaque(true);
			wordBox[i].setPreferredSize(new Dimension(120,65));
			}

		//add the labels to game board in a random sequence
		for(int i=0; i<30; i++){
			thisPanel.add(wordBox[tracker[i]]);
			}

		return thisPanel;

		}//end makeWordPanel
	//-----------------------------------------------------------------------
	//method to hold the control and output for the game
	public JPanel makeComboPanel(){

		JPanel userPanel = new JPanel(new GridLayout(3,1));
		blankBorder = BorderFactory.createEmptyBorder(15,0,20,20);
		String[] language = {"Pick a Language", "Portuguese", "Swedish", "German"};

		reset = new JButton("Reset");
		reset.addActionListener(this);

		//new combo box to give choice in game
		languageChoice = new JComboBox<String>(language);
		languageChoice.addActionListener(this);

		flag = new JLabel("", JLabel.CENTER);
		answerTop = new JLabel("", JLabel.CENTER);
		answer = new JLabel("", JLabel.CENTER);
		answerBottom = new JLabel("", JLabel.CENTER);

		//keeps button and combo box a sane size
		JPanel flowCombo = new JPanel();
		JPanel flowButton = new JPanel();

		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel midPanel = new JPanel(new BorderLayout());
		JPanel answerPanel = new JPanel(new BorderLayout());

		flowCombo.add(languageChoice);
		flowButton.add(reset);

		topPanel.add(flowCombo, BorderLayout.NORTH);
		topPanel.add(answerTop, BorderLayout.SOUTH);

		//panel with reset button and label to hold flag
		midPanel.add(flowButton, BorderLayout.NORTH);
		midPanel.add(flag);

		answer.setPreferredSize(new Dimension(100,60));
		answerTop.setPreferredSize(new Dimension(100,60));

		answerPanel.add(answer);
		answerPanel.add(answerBottom, BorderLayout.SOUTH);
		answerPanel.setBorder(new LineBorder(Color.BLACK));

		//put it all together
		userPanel.add(topPanel);
		userPanel.add(midPanel);
		userPanel.add(answerPanel);
		userPanel.setBorder(blankBorder);

		setTop("");//
		setAnswer("Choose a<br>language<br>to play", null, "");

		return userPanel;

		}//end makeComboPanel
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	//use an array of 3 random numbers to make a colour
	public Color getRandomColor(int randomness, int sameness){

			int[] rgb = new int[3];

			for(int i=0; i<3; i++){
				rgb[i] = (int)(Math.random()*randomness) + sameness;
				}
			Color c = new Color(rgb[0],rgb[1],rgb[2]);

			return c;

		}//end makeRandom
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	//randomise an array of 30 numbers(to juggle the words)
	public void makeRandom(){
			for(int i=0; i<30; i++){
				//roll the digital 30 sided die
				tracker[i] = (int)(Math.random()*30);
				//check if we allready have number
				for(int j=0; j<i; j++){
					if(tracker[j] == tracker[i]){
						i--;
						}
					}
			}//end outer for-loop

		}//end makeRandom
	//-----------------------------------------------------------------------
	public void newGame(int choiceIn){

		//will control which third of words[] we use for a game
		thisGame = 0;
		nextNumber = 0;
		thisGame += choiceIn;
		endOfGame = thisGame + 10;

		//clear old game board and create new randomised board
		outerPanel.remove(wordPanel);
		wordPanel = makeWordPanel();

		setTop("Find number " + nextNum[nextNumber]);

		//put mouse listener on the labels
		listenUp();

		//add new game to the game window
		outerPanel.add(wordPanel);
		outerPanel.repaint();
		outerPanel.revalidate();

		}//end newGame
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	public void goDeaf(){

		for(int i=0; i<30; i++){
			wordBox[i].removeMouseListener(this);
			}

		}//end goDeaf
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	public void listenUp(){

		for(int i=0; i<30; i++){
			wordBox[i].addMouseListener(this);
			}

		}//end listenUp
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	public void actionPerformed(ActionEvent e){

		int langPicked = -1;

		//action handling on the comboPanel
		if(e.getSource() == languageChoice){
			langPicked = (languageChoice.getSelectedIndex() -1);

			if(langPicked < 0){
				return;
				}
			//setflag for language choice
			flag.setIcon(flags[langPicked]);
			setAnswer("", null, "");
			thisGame = (langPicked*10);
			newGame(thisGame);
			}

		//action handling on the reset button
		if(e.getSource() == reset){
			//reset combo box
			languageChoice.setSelectedIndex(0);

			setAnswer("Choose a<br>language<br>to play", null, "");
			flag.setIcon(null);

			newGame((languageChoice.getSelectedIndex() -1)* 10);
			setTop("");
			goDeaf();
			answer.setIcon(null);
			}

		}//end actionPerformed
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public void wonGame(){

		//disable action on labels
		goDeaf();

		//fancy dialog box
		ImageIcon winner = new ImageIcon("icons/balloon3.png");
		Object[] options = {"<html><font size=4 color=purple><i>Groovy !</i></font></html>",};
		JOptionPane.showOptionDialog(this,
			"<html><font size=5 color=purple>You did it!</font></html>",
			"We have a multi-linguist",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			winner,
			options,
			options);

			answer.setIcon(null);
		}//end wonGame
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public void mouseClicked(MouseEvent e){

		//if correct word selected
		if(e.getSource() == wordBox[thisGame]){

			setAnswer("", correct1, "Correct");
			nextNumber++;
			setTop("Find number " + nextNum[nextNumber]);

			wordBox[thisGame].removeMouseListener(this);//disable
			wordBox[thisGame].setIcon(correct);
			thisGame++;

			//if user gets to ten
			if(thisGame == endOfGame){//<---------------------------------------------------!!-8
				wonGame();
				}
			}
			else{
				setAnswer("", wrong, "Wrong answer");
				}//end if-else

		}//end mouseClicked
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public void mouseEntered(MouseEvent e){

		//find index
		int index = -1;
		for(int i=0; i<30; i++){
			if(e.getSource() == wordBox[i]){
				index = i;
				}
			}
		wordBox[index].setText("<html><font color=blue size=6>" + words[index] + "</font></html>");
		wordBox[index].setBackground(Color.yellow);
		}//end mouseEntered

	//-----------------------------------------------------------------------
	public void mouseExited(MouseEvent e){
		if( e.getSource() instanceof JLabel ){
			( (JLabel)e.getSource() ).setText("<html><font size=" + ( (int)(Math.random()*6 +3) ) + ">?</font></html>");
			( (JLabel)e.getSource() ).setForeground(getRandomColor(100,155));
			( (JLabel)e.getSource() ).setBackground(getRandomColor(155,0));
			}

		}//end mouseExited
	//-----------------------------------------------------------------------
	public void mousePressed(MouseEvent e){
		}
	//-----------------------------------------------------------------------
	public void mouseReleased(MouseEvent e){
		}
	//-----------------------------------------------------------------------

	//-----------------------------------------------------------------------
	public void setAnswer(String ans, ImageIcon pic, String ans2){

		Font answerFont = new Font("Meiryo", Font.BOLD, 14);//Batang

		ans = ("<html>" + ans + "</html>");
		ans2 = ("<html>" + ans2 + "</html>");

		answer.setText(ans);
		answer.setBackground(Color.GRAY);
		answer.setForeground(Color.WHITE);
		answer.setFont(answerFont);
		answer.setOpaque(true);
		answer.setIcon(pic);

		answerBottom.setText(ans2);
		answerBottom.setBackground(Color.GRAY);
		answerBottom.setForeground(Color.WHITE);
		answerBottom.setOpaque(true);
		answerBottom.setFont(answerFont);

		}//end setAnswer
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	public void setTop(String numToFind){

		Font answerFont = new Font("Meiryo", Font.BOLD, 12);//Batang

		answerTop.setText(numToFind);
		answerTop.setBackground(Color.GRAY);
		answerTop.setForeground(Color.WHITE);
		answerTop.setOpaque(true);
		answerTop.setFont(answerFont);

		}//end setTop
	//-----------------------------------------------------------------------

	}//end class languageCount
	//-----------------------------------------------------------------------




