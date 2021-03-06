package chess;

import chess.pieces.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Board is the representation of the current state of a game of chess.
 * @author felix engstrom
 *
 */

public class Board implements Cloneable {


	private final int NUM_TILES = 8;
	private Piece[][] board;
	private HashSet<Piece> taken;
	private int playerTurn;

	/*
	Imagined board. board[x_coord][y_coord]
	board[2][5] == Piece on row 2 and column 5
	board[2][5] marked with X (i.e. X marks the spot ).

	0 1 2 3 4 5 6 7
	1
	2         X
	3
	4
	5   
	6
	7
	 */

	/**
	 * Creates a new board (a 8x8 matrix) of Pieces. Adds the pieces
	 * in an initial setup using the addPieces method.
	 */
	public Board(){
		this.board = new Piece[NUM_TILES][NUM_TILES];
		this.taken=new HashSet<Piece>();
		this.playerTurn = 1;

	}

	/**
	 * Adds all pieces to the board in their initial starting positions
	 * Black pieces are put on the two top rows(0-1) and 
	 * white pieces on the two bottom rows(6-7).
	 * @param board The board to add the pieces to
	 */

	public void addPieces(){
		//Black rooks
		board[0][0] = new Rook(-1);
		board[0][7] = new Rook(-1);
		//Black knights
		board[0][1] = new Knight(-1);
		board[0][6] = new Knight(-1);
		//Black bishops
		board[0][2] = new Bishop(-1);
		board[0][5] = new Bishop(-1);
		//Black queen
		board[0][3] = new Queen(-1);
		//Black king
		board[0][4] = new King(-1);
		//Black pawns
		for(int i = 0; i < NUM_TILES; i++){
			board[1][i] = new Pawn(-1);
		}
		//White rooks
		board[7][0] = new Rook(1);
		board[7][7] = new Rook(1);
		//White knights
		board[7][1] = new Knight(1);
		board[7][6] = new Knight(1);
		//White bishops
		board[7][2] = new Bishop(1);
		board[7][5] = new Bishop(1);
		//White queen
		board[7][3] = new Queen(1);
		//White king
		board[7][4] = new King(1);
		//White pawns
		for(int i = 0; i < NUM_TILES; i++){
			board[6][i] = new Pawn(1);
		}

		updatePossible();
	}



	/**
	 * Return an <code> int </code> indicating wither square at row r and column c is occupied.
	 * 0 indicates empty square, 1 indicates occupied by white piece and -1 indicates occupied by black piece.
	 * @param c Column position of square to investigate
	 * @param r Row position of square to investigate
	 * @return 0 if empty, 1 if white & -1 if black.
	 */
	//TODO: All methods that change board positions must create new posible moves for all pieces
	public int occupied(int c, int r){
		//TODO Check bounds
		if(board[r][c] == null){
			return 0;
		}
		return board[r][c].returnColor();
	}



	/**
	 * Removes Piece from from square at position row r and column c. Returns the piece removed, and null if square is empty.
	 * @param c Column to return from
	 * @param r	Row to remove from.
	 * @return null if square at position (c, r) is empty, otherwise it returns the removed piece.
	 */
	public Piece remove( int c, int r){
		//TODO Check bounds
		Piece p = board[r][c];
		board[r][c]=null;
		taken.add(p);
		updatePossible();
		return p;
	}




	/**
	 * Puts piece on square at position row r and column c and returns . If Square is occupied, return -1 and do nothing.
	 * @param piece
	 * @param c Column to put piece on.
	 * @param r Row to put piece on.
	 * @return
	 */
	//TODO: All methods that change board positions must create new posible moves for all pieces
	//TODO Wouldn't it be better if this method put the piece on the tile even if it is occupied?
	public int putPiece(Piece piece, int c, int r){
		//TODO Check bounds
		if(board[r][c] == null){
			board[r][c] = piece;
			updatePossible();
			return 0;
		}
		return -1;
	}


	/**
	 * Moves piece on square at position row r and column c to square at position row toC and column toR . If Square is occupied, 
	 * piece is removed. If (r,c) is empty, nothing happens.
	 * @param c Column to get piece from.
	 * @param r Row to get piece from.
	 * @param toC Column to put piece on.
	 * @param tor Row to put piece on.
	 * @return taken piece if one is taken, else returns null
	 */
	public Piece movePiece(int c, int r, int toC, int toR){
		//TODO Check bounds
		if (board[r][c]!=null){
			Piece temp = board[toR][toC];
			if ( board[toR][toC] != null){
				taken.add(board[toR][toC]);
			}
			board[toR][toC] = board[r][c] ;
			board[r][c]=null;
			updatePossible();
			return temp;
		}
		return null;

	}





	/**
	 * Gets the piece at position column c and row r.
	 * @param c Column to get piece from
	 * @param r Row to get piece from
	 * @return Returns Piece from position row r and column c, and null if position is empty.
	 */
	public Piece getPiece(int c, int r){
		//TODO Check bounds
		return board[r][c];
	}


	/**
	 * Goes trough active pieces and updates their possible moves and positions. Also finds out if in mate.
	 * @return 
	 * 
	 */
	public void updatePossible(){
		for ( int r = 0; r<8; r++){
			for ( int c = 0; c<8; c++){
				Piece p = board[r][c];
				if ( p!=null ){
					p.findNext(this, c, r);

				}
			}
		}
	}

	/**
	 * Finds out if king of chosen color i threatened.
	 * 
	 * @param color 1 to find out if white king is threatened
	 * @return true if king of 
	 */
	public boolean isMate(int color){
		for ( int r = 0; r<8; r++){
			for ( int c = 0; c<8; c++){
				Piece p = board[r][c];
				if ( p!=null ){
					if ( p.returnColor()!=color && p.kingThreat()){
						return true;

					}

				}
			}
		}
		return false;
	}


	/**
	 * Finds out if king of chosen color i threatened.
	 * 
	 * @param color 1 to find out if white king is threatened
	 * @return 
	 * @throws CloneNotSupportedException 
	 */
	public boolean isCheckMate(int color){
		Board temp = this.clone();
		for ( int r = 0; r<8; r++){
			for ( int c = 0; c<8; c++){
				if (board[r][c] != null){
					if ( board[r][c].returnColor() == color ){
						Piece p = temp.getPiece(c, r);
						HashSet<Integer[]> moves = p.getMoves(); 
						for (Iterator<Integer[]> move = moves.iterator(); move.hasNext();){
							Integer [] m = move.next();
							Piece tempP = temp.movePiece(c ,r , m[0],  m[1]);
							if (temp.isMate(color)){
								//TODO revert move?
								temp.movePiece(m[0],  m[1], c ,r);
								temp.putPiece(tempP, m[0],  m[1]);
							}else {
								return false;
							}



						}
					}
				}

			}
		}

		return true;
	}



	//TODO: write comment.
	/**
	 * 
	 */
	@Override 
	public Board clone(){
		Board temp = new Board();
		for ( int r = 0; r<8; r++){
			for ( int c = 0; c<8; c++){
				if (board[r][c] != null){
					Piece p = (Piece) board[r][c].clone();
					temp.putPiece(p, c, r);
				}
			}
		}

		return temp;
	}
	
	/**
	 * Changes turn to the other player. 1 is white players turn and -1 is black players turn.
	 */
	
	public void nextPlayer(){
		playerTurn*=-1;
	}
	
	/**
	 * Gets an int number representing the player whos turn it is.
	 * @return 1 if it is white players turn, -1 if it is black players turn.
	 */
	
	public int GetPlayerTurn(){
		return playerTurn;
	}


}     
