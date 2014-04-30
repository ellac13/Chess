package chess;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import chess.pieces.Piece;
@SuppressWarnings("serial")
public class Userinterf extends JPanel implements MouseListener, MouseMotionListener {

	static int x= 0, y=0;
	Board playBoard;
	int king_x = 2;
	int king_y = 2;
	int marked_x = 2;
	int marked_y = 2;
	Graphics g;
	Image chessPieces = new ImageIcon("pictures/chessPieces.png").getImage();
	
	int playerTurn = 1;

	
	public Userinterf(){
		this.playBoard = new Board();
		playBoard.addPieces();
	}

	@Override 
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.g=g;

		setUp(g);

		Image marked = new ImageIcon("pictures/transpBlue50.png").getImage();
		g.drawImage(marked, marked_x*60, marked_y*60, 60, 60, this);


		//testline
		//Image kingBlack = new ImageIcon("pictures/king_black.png").getImage();
		//g.drawImage(kingBlack, king_x*60, king_y*60, this);

		drawPieces(g);

		g.drawImage(chessPieces, 60, 60, 120, 120, 60, 120, 120, 120, this);
		//TODO Remove
		//Border border = new LineBorder(Color.RED);
		//this.setBorder(border);

	}
	//@Override 
	public void mouseMoved(MouseEvent e){
		/*
		x=e.getX();
		y=e.getY();
		repaint();
		 */
	}
	@Override 
	public void mousePressed(MouseEvent e){
	}
	@Override 
	public void mouseReleased(MouseEvent e){
		int xTemp=e.getX()/60;
		int yTemp=e.getY()/60;
		if(!( xTemp==marked_x && yTemp == marked_y)){
			if (playBoard.occupied(marked_x, marked_y) == playerTurn){
				if (playBoard.getPiece(marked_x, marked_y).moveIsAlowed(xTemp, yTemp)){
					playBoard.movePiece(marked_x, marked_y, xTemp, yTemp);
					playerTurn = playerTurn * -1;
					playBoard.getPiece(xTemp, yTemp).move();
				}
			}
			marked_x=xTemp;
			marked_y=yTemp;

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
		g.setColor(Color.BLACK);
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
}


