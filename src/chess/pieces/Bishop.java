package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Bishop extends Piece {
	
	public Bishop(int color){
		super(color);
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		//List of possible directions
		int [][] directions={{1,1},{-1,-1},{1,-1},{-1,1}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];
			step(checkPosition, direction, board);
		}
	}
	
	/*
	 * Recursive method to make steps in direction. Possition shold be first possition to check.
	 */
	
	private void step(Integer[] position, int[] direction, Board board){
		if (inBounds(position[0], position[1])){
			if (board.occupied(position[0], position[1])==0){
				possibleMoves.add(position);
				Integer[] nexPos= {position[0]+direction[0], position[1]+direction[1]};
				step(nexPos, direction, board);
			}else if (!(board.occupied(position[0], position[1])==this.returnColor())){
				possibleMoves.add(position);
			}
		}

	}


	public int picturePosition(){
		return 260;
	}
	

}
