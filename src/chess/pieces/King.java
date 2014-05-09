package chess.pieces;

import java.util.HashSet;
import java.util.Iterator;

import chess.Board;

public class King extends Piece{

	HashSet<Integer[]> possibleCastling;

	public King(int color){
		super(color);
		super.isKing=true;

	}


	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		possibleCastling= new HashSet<Integer[]>();
		threateningKing=false;
		//List of possible directions
		int [][] directions={{1,0},{-1,0},{1,1},{0,1},{-1,-1},{0,-1},{1,-1},{-1,1}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];

			//check that position is in range
			if (inBounds(checkPosition[0], checkPosition[1])){

				if (board.occupied(checkPosition[0], checkPosition[1])==(-this.returnColor())){
					if  (board.getPiece(checkPosition[0], checkPosition[1]).king()){
						threateningKing=true;
					}
					possibleMoves.add(checkPosition);
				} else if (board.occupied(checkPosition[0], checkPosition[1])==0){
					//TODO:this is where i can implement some sort of in-mate function
					possibleMoves.add(checkPosition);
				}
			}
		}
		if (!hasMoved){
			for (int i=0; i<2; i++){
				Integer[] checkPosition = {0,0};
				int[] direction = directions[i];
				checkPosition[0]=currentC+direction[0];
				checkPosition[1]=currentR+direction[1];
				castlingStep(checkPosition, direction, board);
			}
		}


	}
	/*
	 * special step to check for castling
	 */
	protected void castlingStep(Integer[] position, int[] direction, Board board){
		if (inBounds(position[0], position[1])){
			if (board.occupied(position[0], position[1])==0){
				Integer[] nexPos= {position[0]+direction[0], position[1]+direction[1]};
				castlingStep(nexPos, direction, board);
			}else if (board.occupied(position[0], position[1])==(this.returnColor())){
				if  ((board.getPiece(position[0], position[1]) instanceof Rook) && !board.getPiece(position[0], position[1]).hasMoved()){
					possibleCastling.add(position);
				}
			}

		}
		
	}
	
	/**
	 * Looks to see if move (c,r) is allowed for piece.
	 * @param c
	 * @param r
	 * @return true if cr is a valid move, false if it is not
	 */
	public boolean castlingAlowed(int c, int r){
		if (possibleCastling!=null){
			for (Iterator<Integer[]> iterator = possibleCastling.iterator(); iterator.hasNext();) {
				Integer[] move = iterator.next();
				if (move[0] == c && move[1] == r ){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the possible moves for this piece
	 */
	public HashSet<Integer[]> getCastlingMoves(){
		return possibleCastling;
	}
	public int picturePosition(){
		return 0;
	}


}


