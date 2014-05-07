package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import chess.pieces.*;

//TODO Consider removing class since it is not used
public class PieceChooser extends JPanel implements MouseListener {
	
	//Available pieces for choosing
	private Piece[] pieces;
	
	//Pictures used in the graphical representation
	Image chessPieces;
	
	public PieceChooser(int color){
		importPictures();
		createPieces(color);
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.addMouseListener(this);
		
		drawBackground(g);
		
		drawPieces(g);
		
	}
	
	/**
	 * Creases the possible pieces in array pieces
	 * @param color Color of the pieces
	 */
	private void createPieces(int color) {
		Piece queen = new Queen(color);
		Piece bishop = new Bishop(color);
		Piece knight = new Knight(color);
		Piece rook = new Rook(color);
		pieces = new Piece[] {queen, bishop, knight, rook};
		
	}
	
	/**
	 * Draws a 4x1 chessboard
	 * @param g
	 */
	private void drawBackground(Graphics g){
		this.setBackground(Color.WHITE);
		g.setColor(Color.gray);
		for( int i = 0; i < pieces.length; i++){
			int j = 0;
				if((i+j)%2 == 1){
					g.fillRect(i*60, j*60, 60, 60);
				}
			
		}
	}
	
	/**
	 * Draws the possible pieces
	 * @param g 
	 */
	private void drawPieces(Graphics g){
		
		
		for( int i = 0; i < pieces.length; i++){
			int r = 0;
			Piece temp = pieces[i];
			if (temp!=null){
				int tempInt = temp.picturePosition();
				int color=33;
				color=color+(color*temp.returnColor());
				g.drawImage(chessPieces, i*60, r*60, i*60+60, r*60+60, tempInt, color, tempInt+60, 60+color, this);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX()/60;
		System.out.println("x= " + x);
		
		if(x > 0 && x < pieces.length){
			getPiece(x);
		}

	}
	
	private Piece getPiece(int x) {
		return pieces[x];
		
	}

	private void importPictures(){
		try{
			chessPieces = new ImageIcon("pictures/chessPieces.png").getImage();
		} catch (Exception e){
			System.err.println("Problem occured loading game pictures.");
		}
	}

}
