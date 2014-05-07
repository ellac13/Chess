package chess;
import java.awt.Image;

//import java.awt.*;
import javax.swing.*;


public class ChessGame {
	public static void main ( String[] args) {
		JFrame f = new JFrame("Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setUp
		//Board playBoard = new Board();
		
		Userinterf ui = new Userinterf ();
		f.add(ui);
		// 486x509 window
		f.setSize ( 480 ,600);
		f.setResizable(false);
		Image icon = new ImageIcon("pictures/king_black.png").getImage();
		f.setIconImage(icon);
		f.setVisible(true);
	}
}