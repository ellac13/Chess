package chess;

import chess.pieces.*;
import java.util.HashSet;

public class Board {
	
	//TODO: Maybe board should contain two sets, one for active pieces and one for taken pieces.
	// to make it easier to go trough all pieces. It could also be god to have a field to keep track of
	// if current state contains a move that threatens a king. ( isMate ).
	
	private static final int NUM_TILES = 8;
	private static Piece[][] board;
	private static int inMate = 0;
	private static HashSet<Piece> taken;
	
	/*
	Imagined board. board[x_coord][y_coord]
	board[2][5] == Piece on column 2 and row 5
	board[2][5] marked with X
	
	0 1 2 3 4 5 6 7
	1
	2
	3
	4
	5   X
	6
	7
	*/
	
	/*
	 * Creates a new board (a 8x8 matrix) of Pieces. Adds the pieces
	 * in an initial setup using the addPieces method.
	 */
	public Board(){
		board = new Piece[NUM_TILES][NUM_TILES];
		taken=new HashSet<Piece>();
		addPieces(board);
	}
	
	/*
	 * Adds all pieces to the board in their initial starting positions
	 * Black pieces are put on the two top rows(0-1) and 
	 * white pieces on the two bottom rows(6-7).
	 * @param board The board to add the pieces to
	 */
	//TODO: Maybe put board setup in main class.
	
	//TODO: All methods that change board positions must create new posible moves for all pieces
	public void addPieces(Piece[][] board){
		/*
		 * //Black rooks
		board[0][0] = new Rook(-1);
		board[7][0] = new Rook(-1);
		//Black knights
		board[1][0] = new Knight(-1);
		board[6][0] = new Knight(-1);
		//Black bishops
		board[2][0] = new Bishop(-1);
		board[5][0] = new Bishop(-1);
		//Black queen
		board[3][0] = new Rook(-1);
		//Black king
		board[4][0] = new Rook(-1);
		//Black pawns
		for(int i = 0; i < NUM_TILES; i++){
			board[i][1] = new Pawn(-1);
		}
		//White rooks
		board[0][7] = new Rook(1);
		board[7][7] = new Rook(1);
		//White knights
		board[1][7] = new Knight(1);
		board[6][7] = new Knight(1);
		//White bishops
		board[2][7] = new Bishop(1);
		board[5][7] = new Bishop(1);
		//White queen
		board[3][7] = new Rook(1);
		//White king
		board[4][7] = new Rook(1);
		//White pawns
		for(int i = 0; i < NUM_TILES; i++){
			board[i][6] = new Pawn(1);
		}
		*/
		inMate = updatePossible();
	}
	
	/**
	 * Return a boolean indicating wither square at row r and column c is occupied.
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
	//TODO: All methods that change board positions must create new posible moves for all pieces
	public Piece remove( int c, int r){
		//TODO Check bounds
		Piece p = board[r][c];
		board[r][c]=null;
		taken.add(p);
		inMate = updatePossible();
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
			inMate = updatePossible();
			return 0;
		}
		return -1;
	}
	/**
	  * Gets the piece at position row r and column c.
	  * @param r Row to get piece from
	  * Moves piece on square at position row r and column c to square at position row toC and column toR . If Square is occupied, 
	  * piece is removed. If (r,c) is empty, nothing happens.
	  * @param piece
	  * @param c Column to put piece on.
	  * @param r Row to put piece on.
	 */
	 //TODO: All methods that change board positions must create new posible moves for all pieces
	 public void movePiece(int c, int r, int toC, int toR){
	 	//TODO Check bounds
	 	if(board[r][c] != null){
	 		Piece temp = board[r][c];
	 		board[r][c] = temp;
	 		inMate = updatePossible();
	 	}
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
	  * @return 1 if black in mate, -1 if white in mate and 0 no mate.
	  * 
	  */
	 public int updatePossible(){
	 	return 0;
	 }
	 
	 
	 public int isMate(){
	 	return inMate;
	 }
}
