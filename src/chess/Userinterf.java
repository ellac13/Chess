package chess;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
@SuppressWarnings("serial")
public class Userinterf extends JPanel implements MouseListener, MouseMotionListener {
	
	static int x= 0, y=0;
	int king_x = 3;
	int king_y = 0;
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		g.setColor(Color.BLACK);
		for( int i = 0; i < 8; i++){
			for( int j = 0; j < 8; j++){
				if((i+j)%2 == 1){
					g.fillRect(i*60, j*60, 60, 60);
				}
			}
		}
		//TODO Add method drawPieces()
		Image kingBlack = new ImageIcon("pictures/king_black.png").getImage();
		
		g.drawImage(kingBlack, king_x*60, king_y*60, this);
		//g.setColor(Color.RED);
		//g.fillRect(x-20,y-20,40,40);
		g.setColor(Color.blue);
		//g.fillRect(10,20,100,200);
		g.drawString("Fuck Fuck Fuck", x, y);
		Image chessPieces;
		chessPieces = new ImageIcon("pictures/chessPieces.png").getImage();
		g.drawImage(chessPieces, x, y, this);
		//TODO Remove
		//Border border = new LineBorder(Color.RED);
		//this.setBorder(border);
		
	}
	//@Override 
	public void mouseMoved(MouseEvent e){
		x=e.getX();
		y=e.getY();
		repaint();
	}
	@Override 
	public void mousePressed(MouseEvent e){
	}
	@Override 
	public void mouseReleased(MouseEvent e){
		king_y += 2;}
	@Override 
	public void mouseClicked(MouseEvent e){
	
	}
	@Override 
	public void mouseExited(MouseEvent e){}
	@Override 
	public void mouseDragged(MouseEvent e){
		x=e.getX();
		y=e.getY();
		repaint();
	}
	@Override 
	public void mouseEntered(MouseEvent e){}
	

}
