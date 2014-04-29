package chess.pieces;

import java.util.HashSet;

import chess.Board;

public abstract class Piece {
	protected int color;
	protected boolean hasMoved;
	protected HashSet<Integer[]> possibleMoves;
	/**
	 * Constructor for 
	 * 
	 * @param color An integer representing the color of the piece, 1 means white and -1 means black
	 */
	public Piece(int color){
		//TODO Check color parameter for invalid entries
		this.color = color;
		hasMoved = false;
	}
	/**
	 * Returns a set of possible moves for piece placed at a chosen position on a chosen board.
	 * @param board Board on which to look for possible moves.
	 * @param currentPosstion Possition on board from which to lok for possible moves.
	 * @return A list of possible moves
	 */
	//TODO: Return format? maybe string? needs to show if a  move is creating mate.
	public abstract void findNext(Board board, int currentC, int currentR);
	
	/**
	 * Get color of the piece. Color is returned as an integer.
	 * 
	 * @return Color. 1 if white, -1 if black.
	 */
	public int returnColor(){
		return this.color;
	}
	/**
	 * Change pieces hasMoved status to <code>true</code> regardless of previous state.
	 */
	public void move(){
		hasMoved=true;
	}
	/**
	 * Return pieces current hasMoved state, indicating if the piece is in its original position or has been moved.
	 * @return <code>true</code> if piece has been moved, <code>false</code> if piece has not been moved yet.
	 */
	public boolean hasMoved(){
		return this.hasMoved;
	}
	
	/**
	 * Checks to see that parameters is in range of field.
	 * 
	 */
	protected boolean inBound(int rc){
		//TODO Change 4 to 8?
		return ((rc>=0)&&(rc<4));
	}
	
	/**
	 * Checks to see that parameters is in range of field.
	 * 
	 */
	protected boolean inBounds(int c, int r){
		return (inBound(c) && inBound(r));
	}
	
	/**
	 * Returns the possible moves for this piece
	 */
	public HashSet<Integer[]> getMoves(){
		return possibleMoves;
	}
	
	public int picturePossition(){
		return 0;
	}
	
	 
}
