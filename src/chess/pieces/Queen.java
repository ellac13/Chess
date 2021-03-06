package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Queen extends Piece {

	public Queen(int color){
		super(color);
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		threateningKing=false;
		//List of possible directions
		int [][] directions={{1,0},{1,1},{0,1},{-1,0},{-1,-1},{0,-1},{1,-1},{-1,1}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];
			step(checkPosition, direction, board);
		}

	}

	public int picturePosition(){
		return 65;
	}


}
