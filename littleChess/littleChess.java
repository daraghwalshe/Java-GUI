// -------------------------------------------------------------------------+
// GUI Programming                      			Assignment 1            |
// -------------------------------------------------------------------------+
// File: littleChess.java                                                   |
// -------------------------------------------------------------------------+
// Author:  Daragh Walshe                           Group: Group 1          |
// Student# B00064428                               Date:  Oct.~ Nov. 2013  |
// -------------------------------------------------------------------------+
// DESCRIPTION:                                                             |
//	A mini chess game which has only four squares and one pawn piece which  |
//  can be moved around using control buttons on the bottom of the JFrame.  |
//  The main part of the JFrame should contain four squares. 				|
//  When the game begins the first square should hold a single pawn.		|
//	Provide directional buttons to the user to move the chess piece from 	|
//  its current location to the squares beside it.							|
//	When the user selects a valid move, e.g. move right by clicking the 	|
//  button the chess piece will APPEAR to move from one square to another.	|
// -------------------------------------------------------------------------+

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;


class littleChess extends JFrame implements ActionListener{


	public static void main(String args[]){

		//set boardsize and size of squares
		setBoardSize(2);
		setSquareSize(120);

		littleChess gameOn = new littleChess();

	}//end main


//-----------------
//global variables
//-----------------

//menu items
private JMenuItem  newGame, resetGame, exit, tempJMenuItem,
		   twoSize, threeSize, fourSize, fiveSize, sixSize, sevenSize, eightSize;

//board size
private static int boardSize;
private static int squareSize;

//the chess board
private JPanel arrayBoard;

//JLabel array to hold board
private JLabel[][] boardJLabelArray = new JLabel[boardSize][boardSize];

//movement buttons and variables
private JButton moveN, moveNE, moveE, moveSE, moveS, moveSW, moveW, moveNW, reset;

//location of the pawn
private int xLocation, yLocation;

private ImageIcon pawn = new ImageIcon("icons/pawn.png");//********************************************************
private ImageIcon blackBall = new ImageIcon("icons/blackBall.png");
private ImageIcon whiteBall = new ImageIcon("icons/whiteBall.png");

//===============================================================================

	//---------------------------------------------------------------------------
	//method to set size of board
	public static void setBoardSize(int sizeIn){

		boardSize = sizeIn;

		}//end setBoardSize

	//---------------------------------------------------------------------------
	//method to set size of square
	public static void setSquareSize(int squareSizeIn){

		squareSize = squareSizeIn;

		}//end setBoardSize
	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//constructor for game window
	public littleChess(){

		//name, size and location of the window
		super("Very light blue");

		//start location - bottom left
		xLocation = 0;
		yLocation = (boardSize -1);

		//create the menu bar
		JMenuBar chessMenuBar = buildMenuBar();

		//create chequered panel and some edge panels
		JPanel bigBoard = buildBoard();
		JPanel topEdge = buildTopEdge();
		JPanel bottomEdge = buildTopEdge();
		JPanel leftEdge = buildLeftEdge();
		JPanel rightEdge = buildLeftEdge();

		//new JPanel to hold the move buttons
		JPanel movementButtons = buildButtonPanel();

		//the chequered panel and edge panels fitted togther
		bigBoard.add(arrayBoard,BorderLayout.CENTER);
		bigBoard.add(topEdge,BorderLayout.NORTH);
		bigBoard.add(leftEdge,BorderLayout.WEST);
		bigBoard.add(bottomEdge,BorderLayout.SOUTH);
		bigBoard.add(rightEdge,BorderLayout.EAST);

		//used to center the movement buttons under the board
		JPanel outerButtonPanel = new JPanel();
		outerButtonPanel.setBackground(Color.lightGray);
		outerButtonPanel.add(movementButtons);

		//create the container for the main window
		Container gameWindow = getContentPane();
		gameWindow.add(bigBoard,BorderLayout.CENTER);
		gameWindow.add(outerButtonPanel,BorderLayout.SOUTH);

		//very usefull little gem
		//will resize stuff on its own, overrides setSize
		pack();

		setVisible(true);

		//go set the buttons for start position
		buttonChecker();

		}//end constructor

	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//method to make a coloured menu item
	//takes in four paramaters
	private JMenuItem setColouredItem(String stringIn, Color bGround, Color fGround, ImageIcon iconIn){
		JMenuItem tempMenuItem = new JMenuItem(stringIn);
		tempMenuItem.addActionListener(this);
		tempMenuItem.setBackground(bGround);
		tempMenuItem.setForeground(fGround);
		tempMenuItem.setIcon(iconIn);
		tempMenuItem.setOpaque(true);
		return tempMenuItem;
		}//end setColouredItem

	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//a method to build a menu bar
	private JMenuBar buildMenuBar(){
		JMenuBar chessMenuBar = new JMenuBar();
		setJMenuBar(chessMenuBar);

		chessMenuBar.setBackground(Color.blue);
		chessMenuBar.setOpaque(true);

		JMenu gameMenu = new JMenu(" Game ");
		gameMenu.setIcon(new ImageIcon("icons/game.png"));
		chessMenuBar.add(gameMenu);

		JMenu boardSizeMenu = new JMenu(" Board Size ");
		boardSizeMenu.setIcon(new ImageIcon("icons/board.png"));
		chessMenuBar.add(boardSizeMenu);

		//the menu items for the game menu
		newGame = setColouredItem(" New Game", Color.black, Color.white, new ImageIcon("icons/menuNew.png"));
		resetGame = setColouredItem(" Reset", Color.white, Color.black, new ImageIcon("icons/menuReset.png"));
		exit = setColouredItem(" Exit", Color.black, Color.white, new ImageIcon("icons/menuExit.png"));

		//the menu items for the board size menu
		twoSize = setColouredItem(" 2 x 2 Squares", Color.black, Color.white, whiteBall);
		threeSize = setColouredItem(" 3 x 3 Squares", Color.white, Color.black, blackBall);
		fourSize = setColouredItem(" 4 x 4 Squares", Color.black, Color.white, whiteBall);
		fiveSize = setColouredItem(" 5 x 5 Squares", Color.white, Color.black, blackBall);
		sixSize = setColouredItem(" 6 x 6 Squares", Color.black, Color.white, whiteBall);
		sevenSize = setColouredItem(" 7 x 7 Squares", Color.white, Color.black, blackBall);
		eightSize = setColouredItem(" 8 x 8 Squares", Color.black, Color.white, whiteBall);

		//add the menu items to the menus
		gameMenu.add(newGame);
		gameMenu.add(resetGame);
		gameMenu.add(exit);

		boardSizeMenu.add(twoSize);
		boardSizeMenu.add(threeSize);
		boardSizeMenu.add(fourSize);
		boardSizeMenu.add(fiveSize);
		boardSizeMenu.add(sixSize);
		boardSizeMenu.add(sevenSize);
		boardSizeMenu.add(eightSize);

		return chessMenuBar;

		}//end buildMenuBar

	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//method to build the game board
	private JPanel buildBoard(){

		JPanel buildBigBoard = new JPanel(new BorderLayout());

		//nested for-loop to create the black and white
		//JLabels which will be the squares on the board
		boolean blackSquare = true;

		for(int i=0; i<boardSize; i++){

			for(int j=0; j<boardSize; j++){

				if(blackSquare){
					boardJLabelArray[i][j] = new JLabel("", JLabel.CENTER);		//when the icon is placed later
					boardJLabelArray[i][j].setBackground(Color.darkGray);		//it will be in center
					boardJLabelArray[i][j].setOpaque(true);
					blackSquare = false;
					}
					else{
						boardJLabelArray[i][j] = new JLabel("", JLabel.CENTER);
						boardJLabelArray[i][j].setBackground(Color.white);
						boardJLabelArray[i][j].setOpaque(true);
						blackSquare = true;
						}
				}//end inner for-loop

				//if board an even size flip colours between rows
				//to keep the check pattern in order
				if( boardSize%2 == 0){
					blackSquare = !(blackSquare);
					}
			}//end outer for-loop


		//create the starting pawn, and set squaresize of board
		boardJLabelArray[yLocation][xLocation].setIcon(pawn);
		boardJLabelArray[yLocation][xLocation].setPreferredSize(new Dimension(squareSize,squareSize));

		//put the black and white squares and pawn onto the board
		arrayBoard = new JPanel(new GridLayout(boardSize,boardSize));

			for(int r=0; r<boardSize; r++){
				for(int c=0; c<boardSize; c++){
					arrayBoard.add(boardJLabelArray[r][c]);
					}
				}//end outer for-loop

		return buildBigBoard;

		}//end buildBoard

	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//method to make top edge of board
	private JPanel buildTopEdge(){

		JPanel topEdge = new JPanel(new GridLayout(1, boardSize+1));

		//fill edge panels a,b,c...
		String[] letters = {" A","B","C","D","E","F","G","H "};
		for(int i = 0; i<boardSize; i++){

			topEdge.add(new JLabel(letters[i], JLabel.CENTER));
			topEdge.setBackground(Color.lightGray);
			topEdge.setOpaque(true);
			topEdge.setPreferredSize(new Dimension (0,15));
			}

		return topEdge;

		}//end buildTopEdge
	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//method to
	private JPanel buildLeftEdge(){

		JPanel leftEdge = new JPanel(new GridLayout(boardSize, 1));

		//fill edge panels 1,2,3...
		String[] numbers = {"1","2","3","4","5","6","7","8"};
		for(int i = boardSize-1; i>=0; i--){
			leftEdge.add(new JLabel(numbers[i], JLabel.CENTER));
			leftEdge.setBackground(Color.lightGray);
			leftEdge.setOpaque(true);
			leftEdge.setPreferredSize(new Dimension (15,0));
			}

		return leftEdge;

		}//end buildLeftEdge
	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//a method to move the pawn icon
	private void movePiece(int oldYin, int oldXin){

		//remove pawn from location before move
		boardJLabelArray[oldYin][oldXin].setIcon(null);

		//place pawn in new position
		boardJLabelArray[yLocation][xLocation].setIcon(pawn);

		}//end movePiece

	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//method to listen for the buttons
	public void actionPerformed(ActionEvent e){

		//record location of pawn before move
		int oldX = xLocation;
		int oldY = yLocation;

		if(e.getSource() == moveNW){
				xLocation -= 1;
				yLocation -= 1;
			}
			else if(e.getSource() == moveN){
				yLocation -= 1;
			}
			else if(e.getSource() == moveNE){
				xLocation += 1;
				yLocation -= 1;
			}
			else if(e.getSource() == moveW){
				xLocation -= 1;
			}
			else if(e.getSource() == reset || e.getSource() ==  resetGame){
				xLocation = 0; yLocation = (boardSize -1);
			}
			else if(e.getSource() == moveE){
				xLocation += 1;
			}
			else if(e.getSource() == moveSW){
				xLocation -= 1;
				yLocation += 1;
			}
			else if(e.getSource() == moveS){
				yLocation += 1;
			}
			else if(e.getSource() == moveSE){
				xLocation += 1;
				yLocation += 1;
			}
			//---------------------------------------
			else if(e.getSource() == newGame){
			newGame();
			}
			else if(e.getSource() == exit){
			System.exit(0);
			}
			else if(e.getSource() == twoSize){
			newGame(120, 2);
			}
			else if(e.getSource() == threeSize){
			newGame(120, 3);
			}
			else if(e.getSource() == fourSize){
			newGame(120, 4);
			}
			else if(e.getSource() == fiveSize)
			{
			newGame(80, 5);
			}
			else if(e.getSource() == sixSize){
			newGame(80, 6);
			}
			else if(e.getSource() == sevenSize){
			newGame(80, 7);
			}
			else if(e.getSource() == eightSize){
			newGame(80, 8);
			}

		movePiece(oldY, oldX);//duh

		//go check and set which buttons are true/false for new x-y location
		buttonChecker();

		}//end actionPerformed

	//---------------------------------------------------------------------------
	//default method to make a new game board
	public void newGame(){

		//rub out the old window
		this.setVisible(false);

		//create new window
		littleChess gameOn = new littleChess();

		}//end newGame

	//-----------------------------------------------------
	//method to make a new game board with passed values
	public void newGame(int squareSizeIn, int boardSizeIn){

		//rub out the old window
		this.setVisible(false);

		setBoardSize(boardSizeIn);
		setSquareSize(squareSizeIn);

		//create new window
		littleChess gameOn = new littleChess();

		}//end newGame

	//---------------------------------------------------------------------------
	//method to build a panel for the movement buttons
	private JPanel buildButtonPanel(){

		//this will hold the nine buttons - movement and reset			//	\|/
		JPanel panelIn = new JPanel(new GridLayout(3,3));				//	-R-
																		//	/|\
		//creating the nine buttons and adding the listener to each
		moveNW = new JButton();
		moveNW.setPreferredSize(new Dimension(36,36));
		moveNW.setIcon(new ImageIcon("icons/northwest.png"));
		moveNW.addActionListener(this);

		moveN = new JButton();
		moveN.setIcon(new ImageIcon("icons/north.png"));
		moveN.addActionListener(this);

		moveNE = new JButton();
		moveNE.setIcon(new ImageIcon("icons/northeast.png"));
		moveNE.addActionListener(this);

		moveW = new JButton();
		moveW.setIcon(new ImageIcon("icons/west.png"));
		moveW.addActionListener(this);

		reset = new JButton();
		reset.setIcon(new ImageIcon("icons/reset.png"));
		reset.addActionListener(this);

		moveE = new JButton();
		moveE.setIcon(new ImageIcon("icons/east.png"));
		moveE.addActionListener(this);

		moveSW = new JButton();
		moveSW.setIcon(new ImageIcon("icons/southwest.png"));
		moveSW.addActionListener(this);

		moveS = new JButton();
		moveS.setIcon(new ImageIcon("icons/south.png"));
		moveS.addActionListener(this);

		moveSE = new JButton();
		moveSE.setIcon(new ImageIcon("icons/southeast.png"));
		moveSE.addActionListener(this);

		//add the movement buttons to the panel
		panelIn.add(moveNW);
		panelIn.add(moveN);
		panelIn.add(moveNE);
		panelIn.add(moveW);
		panelIn.add(reset);
		panelIn.add(moveE);
		panelIn.add(moveSW);
		panelIn.add(moveS);
		panelIn.add(moveSE);

		return panelIn;
		}//end buildButtonPanel
	//---------------------------------------------------------------------------

	//---------------------------------------------------------------------------
	//method to disable buttons for invalid moves
	private void buttonChecker(){

		//we know where we are on the array x and y so we can use this to disable
		//the movement buttons if we are on first or last index of a row (xLocation)
		//or if are on the first or last rows(yLocation)
		//check for north
		if(yLocation == 0){							//if at north edge of board:
			moveN.setEnabled(false);				//can't go N, NE or NW
			moveNE.setEnabled(false);
			moveNW.setEnabled(false);
			}
			else{									//else you're not at N edge
				moveN.setEnabled(true);				//you can go North
				if(xLocation != (boardSize-1)){		//or NE if not at E edge
					moveNE.setEnabled(true);
					}
				if(xLocation != 0){					//or NW if not at W edge
					moveNW.setEnabled(true);
					}
				}//end if-else

		//check for south
		if(yLocation == (boardSize-1)){				//if at South edge of the board...
			moveS.setEnabled(false);
			moveSE.setEnabled(false);
			moveSW.setEnabled(false);
			}
			else{
				moveS.setEnabled(true);
				if(xLocation != (boardSize-1)){
					moveSE.setEnabled(true);
					}
				if(xLocation != 0){
					moveSW.setEnabled(true);
					}
				}//end if-else

		//check for east							//final check for east and west
		if(xLocation == (boardSize-1)){
			moveE.setEnabled(false);
			moveNE.setEnabled(false);
			moveSE.setEnabled(false);
			}
			else{
				moveE.setEnabled(true);
				}//end if-else

		//check for west
		if(xLocation == 0){
			moveW.setEnabled(false);
			moveNW.setEnabled(false);
			moveSW.setEnabled(false);
			}
			else{
				moveW.setEnabled(true);
				}//end if-else

		}//end buttonChecker

//===========================================================================
	}//end class littleChess

//===========================================================================
//===========================================================================



