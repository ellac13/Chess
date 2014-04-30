package chess.pieces;

import java.util.HashSet;
import java.util.Iterator;

import chess.Board;

public abstract class Piece {
	
	
	protected int color;
	protected boolean hasMoved;
	protected boolean isKing;
	protected boolean threateningKing;
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
		this.hasMoved=true;
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
		return ((rc>=0)&&(rc<8));
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


	/**
	 * Returns a number to be used for finding piece picture.
	 * @return int x position of pieces picture in chessPieces 
	 */
	public int picturePosition(){
		return 0;
	}


	/**
	 * Looks to see if move (c,r) is allowed for piece.
	 * @param c
	 * @param r
	 * @return true if cr is a valid move, false if it is not
	 */
	public boolean moveIsAlowed(int c, int r){
		if (possibleMoves!=null){
			for (Iterator<Integer[]> iterator = possibleMoves.iterator(); iterator.hasNext();) {
				Integer[] move = iterator.next();
				if (move[0] == c && move[1] == r ){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if piece is king.
	 * @return
	 */

	public boolean king(){
		return isKing;
	}
	
	/**
	 * Returns true if piece is king.
	 * @return
	 */

	public boolean kingThreat(){
		return threateningKing;
	}


	/*
	 * To be used by all pieces with multiple steps type of movement. Help method.
	 */
	protected void step(Integer[] position, int[] direction, Board board){
		threateningKing=false;
		if (inBounds(position[0], position[1])){
			if (board.occupied(position[0], position[1])==0){
				possibleMoves.add(position);
				Integer[] nexPos= {position[0]+direction[0], position[1]+direction[1]};
				step(nexPos, direction, board);
			}else if (!(board.occupied(position[0], position[1])==this.returnColor())){
				possibleMoves.add(position);
				if  (board.getPiece(position[0], position[1]).king()){
					threateningKing=true;
				}
			}

		}

	}



}
