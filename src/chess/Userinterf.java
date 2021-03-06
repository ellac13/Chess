package chess;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.*;

import chess.pieces.*;
@SuppressWarnings("serial")
public class Userinterf extends JPanel implements MouseListener, MouseMotionListener {

	//Contains the graphical representation of the game.
	Graphics g;

	// Contains the virtual representation of the game
	Board playBoard;

	//TODO: Remove or implement properly
	private boolean mark;
	private int marked_x = 2;
	private int marked_y = 2;
	private int x = 120;
	private int y = 120;
	
	
	//Two states of the game. 0 indicates that no one is inMate/winner.
	// 1 is white and -1 is black.
	private int inMate = 0;
	private int winner = 0 ;
	
	// Gives the coordinates for the new game button.
	private int buttonPosX = 140 ;
	private int buttonPosY = 520;
	private int buttonSizeX = 180 ;
	private int buttonSizeY = 40;

	//Pictures used in the graphical representation
	private Image chessPieces;
	private Image marked;
	private Image markedRed ;
	private Image markedOrange;
	private Image markedGreen;
	private Image markedOrangeGreen;

	// TODO: Implement so that only player can only make moves if board.getPlayerTurn == playerColor
	// butt if player color is 0 keep the current implementation.
	private int playerColor;


	/**
	 * Constructor for userinterface used for local game.
	 */
	public Userinterf(){
		this.playBoard = new Board();
		playBoard.addPieces();
		importPictures();
		playerColor = 0;
	}

	/**
	 * Constructor for userinterface used for remote game.
	 */
	public Userinterf(Board board, int playerColor){
		this.playBoard = board;
		importPictures();

	}

	@Override 
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.g=g;
		setUp(g);

		if (mark){
			drawMarked(g);
		}


		if (playBoard.occupied(marked_x,marked_y)==playBoard.GetPlayerTurn()){
			drawPossible(g);
		}
		drawPieces(g);
		
		
		//TODO finish and clean up.
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawRoundRect(buttonPosX, buttonPosY, buttonSizeX, buttonSizeY, 2, 2);
		
		g.drawString("NEW GAME" , buttonPosX+35 , buttonPosY+27 );
		

		// Set color for texts.
		int rgb = 85;
		Color semiDarkGray = new Color(rgb, rgb, rgb);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		
		
		//print a message telling if player is in mate.
		String mateString;
		
		if (inMate == 1){
			mateString = "White in check";
		}else if(inMate == -1){
			mateString = "Black in check";
		}else {
			mateString = "";
		}
		g.setColor(Color.RED);
		g.drawString(mateString, 300, 510);
		
		
		//print a message telling whos turn it is.
		String[] pt= {"White", "", "Black"} ;
		g.setColor(semiDarkGray);
	
		if (winner==0){
			g.drawString(pt[1-playBoard.GetPlayerTurn()]+" players turn." , 20 , 510 );
		} else {
			g.setColor(Color.RED);
			g.drawString(pt[1-winner]+" has won." , 20 , 510 );
		}

	}
	@Override 
	public void mouseMoved(MouseEvent e){
	}
	@Override 
	public void mousePressed(MouseEvent e){
		int xTemp = e.getX();
		int yTemp = e.getY();
		
		if (!( xTemp/60==marked_x && yTemp/60 == marked_y) ){
			mark=true;
			if((xTemp/60<8 && yTemp/60<8)){
				clickOnBoard(xTemp/60, yTemp/60);
			} 
		}
		if (!( xTemp==x && yTemp== y) ){
				clickOffBoard(xTemp,yTemp);
			
		}
		
	}
	
	//TODO: fix temp variables and marked_x marked_y. Currently messy.
	@Override 
	public void mouseReleased(MouseEvent e){
		int xTemp = e.getX();
		int yTemp = e.getY();
		
		if (!( xTemp/60==marked_x && yTemp/60 == marked_y) ){
			mark=true;
			if((xTemp/60<8 && yTemp/60<8)){
				clickOnBoard(xTemp/60, yTemp/60);
			} 
		}
		
	}
	@Override 
	public void mouseClicked(MouseEvent e){

	}
	@Override 
	public void mouseExited(MouseEvent e){}
	@Override 
	public void mouseDragged(MouseEvent e){
	}
	@Override 
	public void mouseEntered(MouseEvent e){}


	/**
	 * Draw an 8x8 white and gray play board.
	 * @param g The Graphic object on which to draw the board.
	 */
	public void setUp(Graphics g){
		this.setBackground(Color.WHITE);
		g.setColor(Color.gray);
		for( int i = 0; i < 8; i++){
			for( int j = 0; j < 8; j++){
				if((i+j)%2 == 1){
					g.fillRect(i*60, j*60, 60, 60);
				}
			}
		}
	}
	/**
	 * Draw the pieces from this. playBoard on the 8x8 white and gray play board.
	 * @param g The Graphic object on which to draw the board.
	 */
	private void drawPieces(Graphics g){

		for (int r = 0; r<8; r++){
			for (int c = 0; c<8; c++){

				Piece temp = playBoard.getPiece(c, r);
				if (temp!=null){
					int tempInt = temp.picturePosition();
					int color=33;
					color=color+(color*temp.returnColor());
					g.drawImage(chessPieces, c*60, r*60, c*60+60, r*60+60, tempInt, color, tempInt+60, 60+color, this);
				}
			}
		}
	}


	/**
	 * If marked square contains an piece ( that is if corresponding place in playBoard does ) this draws orange marks on the squares
	 * that marked piece has as possible move.
	 * @param g The Graphic object on which to draw the board.
	 */

	private void drawPossible(Graphics g){
		HashSet<Integer[]> possible = playBoard.getPiece(marked_x, marked_y).getMoves();
		for (Iterator<Integer[]> i = possible.iterator(); i.hasNext(); ){
			Integer[] pos = i.next();
			g.drawImage(markedOrange, pos[0]*60, pos[1]*60, 60, 60, this);
		}
		if (playBoard.getPiece(marked_x, marked_y).king()){
			King k= (King) playBoard.getPiece(marked_x, marked_y);
			HashSet<Integer[]> castle = k.getCastlingMoves();
			for (Iterator<Integer[]> i = castle.iterator(); i.hasNext(); ){
				Integer[] pos = i.next();
				if (pos[0]==0){
					//TODO REMOVE CALLE
					g.drawImage(markedGreen, 2*60, pos[1]*60, 60, 60, this);
				}else if(pos[0]==7){
					g.drawImage(markedGreen, 6*60, pos[1]*60, 60, 60, this);
				}
				
				g.drawImage(markedOrangeGreen, pos[0]*60, pos[1]*60, 60, 60, this);
			}

		}
	}

	/**
	 * Draws an blue marker square on the marked square on the play board.
	 * @param g The Graphic object on which to draw the board.
	 */
	private void drawMarked(Graphics g){
		if (playBoard.occupied(marked_x,marked_y)!=playBoard.GetPlayerTurn()){
			g.drawImage(markedRed, marked_x*60, marked_y*60, 60, 60, this);
		} else {
			g.drawImage(marked, marked_x*60, marked_y*60, 60, 60, this);
		}
	}

	/*
	 * Imports the pictures needed for the game board and performs error handling.
	 */
	private void importPictures(){
		try{
			chessPieces = new ImageIcon("pictures/chessPieces.png").getImage();
			marked = new ImageIcon("pictures/transpBlue75.png").getImage();
			markedRed = new ImageIcon("pictures/transpRed75.png").getImage();
			markedOrange = new ImageIcon("pictures/transpOrange50.png").getImage();
			markedGreen = new ImageIcon("pictures/transpGreen50.png").getImage();
			markedOrangeGreen = new ImageIcon("pictures/transpOrangeGreen50.png").getImage();
			//markedOrangeGreen = new ImageIcon("pictures/transpGreenOrange50.png").getImage();
		} catch (Exception e){
			System.err.println("Problem occured loading game pictures.");
		}
	}
	
	/*
	 * Handles the actions to perform when clicking outside the game board.
	 * @param c
	 * @param r
	 */
	private void clickOffBoard(int x,int y){
	if (x<buttonPosX+buttonSizeX && x>buttonPosX && y<buttonPosY+buttonSizeY && y> buttonPosY ){
		newGameDialog();
	}
	winner=0;
	this.x=x;
	this.y=y;
	}




	/*
	 * Handles the actions to perform when clicking on the game board.
	 * @param c
	 * @param r
	 */
	private void clickOnBoard(int c,int r){
		int playerTurn = playBoard.GetPlayerTurn();
		if (playBoard.occupied(marked_x, marked_y) == playerTurn){
			if (playBoard.getPiece(marked_x, marked_y).moveIsAlowed(c, r)){
				Piece temp = playBoard.movePiece(marked_x, marked_y, c, r);

				//If player puts him self in mate, retract move.
				if (playBoard.isMate(playerTurn)){
					playBoard.movePiece( c, r, marked_x, marked_y);
					playBoard.putPiece(temp, c, r);
					//TODO: Print message saying that move can not be performed.
				} else {
					//check if pawn has reached last square on board, if so offer pawnPromotion.
					if (( r==7 || r == 0) && (playBoard.getPiece(c, r) instanceof Pawn)) {
						swapPawn( c, r );
					}
					playBoard.nextPlayer(); ;
					playBoard.getPiece(c, r).move();
					mark=false;
				}
			}

			// castling, checks for availble castling moves if marked  piece is a king that has not moved and that is not in mate.
			if (playBoard.getPiece(marked_x, marked_y) != null && playBoard.getPiece(marked_x, marked_y).king() && !playBoard.isMate(playerTurn)  ){
				King k  = (King) playBoard.getPiece(marked_x, marked_y);
				if (!k.hasMoved()){
					if (k.castlingAlowed(c, r)){
						int d=0;
						if (c==0){
							d=-1;
						} else {
							d=1;
						}
						playBoard.movePiece(marked_x, marked_y, marked_x+2*d, r);
						playBoard.movePiece(c, r, marked_x+1*d, r);

						if (playBoard.isMate(playerTurn)){
							playBoard.movePiece(marked_x+2*d, r, marked_x, marked_y);
							playBoard.movePiece(marked_x+1*d, r, c, r );
						} else {
							playBoard.nextPlayer(); ;
							playBoard.getPiece(marked_x+2*d, r).move();
							playBoard.getPiece(marked_x+1*d, r).move();
							mark=false;
						}
					}
				}
			}




		}

		marked_x=c;
		marked_y=r;

		playerTurn = playBoard.GetPlayerTurn();
		if (playBoard.isMate(playerTurn)){ 
			inMate = playerTurn;
			if (playBoard.isCheckMate(playerTurn)){
				String[] pt= {"White", "", "Black"} ;
				
				JOptionPane.showMessageDialog(this, ""+pt[1+playerTurn]+" has won", "Winner" , JOptionPane.INFORMATION_MESSAGE);
				
					this.playBoard = new Board();
					this.playBoard.addPieces();
					winner=0;
					inMate=0;
					repaint();
				}
		}else{
			inMate = 0;
		}
		repaint();

	}
	
	/**
	 * Gives the player options of pieces to swap their pawn for.
	 */
	private void swapPawn(int x, int y){
		Piece p = playBoard.getPiece(x, y);
		//TODO Remove
		//openChooserWindow(p.returnColor());
		Piece chosenPiece = pieceChooser(p.returnColor());
		playBoard.remove(x, y);
		playBoard.putPiece(chosenPiece, x, y);
		playBoard.getPiece(x, y).move();
	}
	
	/*
	 * Opens a dialog window which allows the user to choose a piece which they want returned.
	 * If no option is chosen, which should never happen, null is returned. 
	 * @param pieceColor The color of the returned piece, 1 = white, -1 = black.
	 * @return The chosen piece
	 */
	private Piece pieceChooser(int pieceColor) {
		//Custom button text
		Object[] options = {new Queen(pieceColor),
				new Bishop(pieceColor),
				new Knight(pieceColor),
				new Rook(pieceColor)};
		//Section below returns an int (0, 1, 2, 3) depending on chosen option.
		int n = 0;
		//Note that this returns -1 if you close the dialog without choosing an option.
		n = JOptionPane.showOptionDialog(this,
		    "Please choose what kind of piece you wish to turn your pawn into",
		    "A pawn got promoted!",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);
		
		if(n < 0 || n > options.length){
			return (Piece) options[0];
		}
		
		return (Piece) options[n];
	}
	
	/**
	 * Opens a dialog window which allows the user to chooseif they want to start a new game.
	 * 
	 */
	private void newGameDialog() {
		
		String message = " Are you sure you want to start a new game? This will end the current game. ";
		int n = JOptionPane.showConfirmDialog(this, message, "NEW GAME" , JOptionPane.YES_NO_OPTION);
		
		if (n==JOptionPane.YES_OPTION){
			this.playBoard = new Board();
			this.playBoard.addPieces();
			repaint();
		}
	}
	
	
	
	
	
	//TODO Consider removing as it is not used.
	/**
	 * Creates a pop-up containing the four pieces allowed to chose from when promoting a pawn.
	 * @param pieceColor Color of the pieces to be displayed, 1 = white, -1 = black.
	 */
	private void openChooserWindow(int pieceColor){
		JFrame f = new JFrame("Choose a piece");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PieceChooser pc = new PieceChooser(pieceColor);
		f.add(pc);
		// 246x88 window
		f.setSize ( 246 ,88);
		f.setResizable(false);
		Image icon = new ImageIcon("pictures/king_black.png").getImage();
		f.setIconImage(icon);
		f.setVisible(true);
	}
}




