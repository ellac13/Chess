package chess;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.*;

import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
@SuppressWarnings("serial")
public class Userinterf extends JPanel implements MouseListener, MouseMotionListener {

	//Contains the graphical representation of the game.
	Graphics g;

	// Contains the virtual representation of the game
	Board playBoard;

	//TODO: Remove or implement properly
	boolean mark;
	int marked_x = 2;
	int marked_y = 2;
	int winner = 0 ;

	//Pictures used in the graphical representation
	Image chessPieces;
	Image marked;
	Image markedRed ;
	Image markedOrange;

	// TODO: Implement so that only player can only make moves if board.getPlayerTurn == playerColor
	// butt if player color is 0 keep the current implementation.
	int playerColor;


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
		//TODO finish and clean up. Implement new game button.
		g.drawRoundRect(20, 520, 120, 40, 2, 2);
		String[] pt= {"White", "", "Black"} ;
		if (winner==0){
			g.drawString(pt[1-playBoard.GetPlayerTurn()]+" players turn." , 20 , 510 );
		} else {
			g.drawString(pt[1-winner]+" has won." , 20 , 510 );
		}

	}
	@Override 
	public void mouseMoved(MouseEvent e){
	}
	@Override 
	public void mousePressed(MouseEvent e){
	}
	@Override 
	public void mouseReleased(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		int xTemp=x/60;
		int yTemp=y/60;
		
		if (!( xTemp==marked_x && yTemp == marked_y)){
			mark=true;
			if((xTemp<8 && yTemp<8)){
				clickOnBoard(xTemp, yTemp);
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


	//imports the pictures needed for the game board and performs error handling.
	private void importPictures(){
		try{
			chessPieces = new ImageIcon("pictures/chessPieces.png").getImage();
			marked = new ImageIcon("pictures/transpBlue75.png").getImage();
			markedRed = new ImageIcon("pictures/transpRed75.png").getImage();
			markedOrange = new ImageIcon("pictures/transpOrange50.png").getImage();
		} catch (Exception e){
			System.err.println("Problem occured loading game pictures.");
		}
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
					} else {
						//check if pawn has reached last square on board, if so offer pawnPromotion.
						if (( r==7 || r == 0) && (playBoard.getPiece(c, r) instanceof Pawn)) {
							swapPiece( c, r );
						}
						playBoard.nextPlayer(); ;
						playBoard.getPiece(c, r).move();
						mark=false;
					}
				}
			}
			marked_x=c;
			marked_y=r;
		
		playerTurn = playBoard.GetPlayerTurn();
		if (playBoard.isMate(playerTurn)){ 
			if (playBoard.isCheckMate(playerTurn)){
				winner= -playerTurn;
				System.out.println("you won"+  (-playerTurn));
			}
		}
		repaint();

	}
	
	//TODO: Finish swap method.
	/**
	 * Gives the player options of pieces to swap their pawn for.
	 */
	private void swapPiece(int x, int y){
		Piece p = playBoard.getPiece(x, y);
		playBoard.remove(x, y);
		playBoard.putPiece(new Queen(p.returnColor()), x, y);
		playBoard.getPiece(x, y).move();
	}
}




