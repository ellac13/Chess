package chess;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.*;

import chess.pieces.Piece;
@SuppressWarnings("serial")
public class Userinterf extends JPanel implements MouseListener, MouseMotionListener {

	static int x= 0, y=0;
	Board playBoard;
	int king_x = 2;
	int king_y = 2;
	boolean mark;
	int marked_x = 2;
	int marked_y = 2;
	Graphics g;
	Image chessPieces;
	Image marked;
	Image markedRed ;
	Image markedOrange;

	int playerColor;


	public Userinterf(){
		this.playBoard = new Board();
		playBoard.addPieces();
		playBoard.isCheckMate(1);
		 importPictures();
		 playerColor = 0;
	}
	
	public Userinterf(Board board, int playerColor){
		this.playBoard = board;
		playBoard.addPieces();
		playBoard.isCheckMate(1);
		 importPictures();
	}

	@Override 
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		this.setBackground(Color.WHITE);
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

	}
	//@Override 
	public void mouseMoved(MouseEvent e){
	}
	@Override 
	public void mousePressed(MouseEvent e){
	}
	@Override 
	public void mouseReleased(MouseEvent e){
		int xTemp=e.getX()/60;
		int yTemp=e.getY()/60;
		int playerTurn = playBoard.GetPlayerTurn();
		if (!( xTemp==marked_x && yTemp == marked_y)){
			mark=!mark;
			if(mark && (xTemp<8 && yTemp<8)){
				if (playBoard.occupied(marked_x, marked_y) == playerTurn){
					if (playBoard.getPiece(marked_x, marked_y).moveIsAlowed(xTemp, yTemp)){
						Piece temp = playBoard.movePiece(marked_x, marked_y, xTemp, yTemp);
						if (playBoard.isMate(playerTurn)){
							playBoard.movePiece( xTemp, yTemp, marked_x, marked_y);
							playBoard.putPiece(temp, xTemp, yTemp);
						} else {
							playBoard.nextPlayer(); ;
							playBoard.getPiece(xTemp, yTemp).move();
							mark=!mark;
						}
					}
				}
				marked_x=xTemp;
				marked_y=yTemp;
			}
			if (playBoard.isMate(playerTurn)){ 
				if (playBoard.isCheckMate(playerTurn)){
					System.out.println("you won"+  (-playerTurn));
				}
			}
			repaint();

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

	public void setUp(Graphics g){
		g.setColor(Color.gray);
		for( int i = 0; i < 8; i++){
			for( int j = 0; j < 8; j++){
				if((i+j)%2 == 1){
					g.fillRect(i*60, j*60, 60, 60);
				}
			}
		}
	}
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

	private void drawPossible(Graphics g){
		HashSet<Integer[]> possible = playBoard.getPiece(marked_x, marked_y).getMoves();
		for (Iterator<Integer[]> i = possible.iterator(); i.hasNext(); ){
			Integer[] pos = i.next();
			g.drawImage(markedOrange, pos[0]*60, pos[1]*60, 60, 60, this);
		}
	}

	private void drawMarked(Graphics g){
		if (playBoard.occupied(marked_x,marked_y)!=playBoard.GetPlayerTurn()){
			g.drawImage(markedRed, marked_x*60, marked_y*60, 60, 60, this);
		} else {
			g.drawImage(marked, marked_x*60, marked_y*60, 60, 60, this);
		}
	}
	
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
}




