package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Queen extends Piece {
	
	public Queen(int color){
		super(color);
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		HashSet<Integer[]> nextPos= new HashSet<Integer[]>();
		//List of possible directions
		int [][] directions={{1,0},{1,1},{0,1},{-1,0},{-1,-1},{0,-1},{1,-1},{-1,1}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];

			//check that position is in range
			if (inBounds(checkPosition[0], checkPosition[0])){
				// Keep going in the chosen direction
				while ((board.occupied(checkPosition[0], checkPosition[1])==0) && inBounds(checkPosition[0], checkPosition[1])){
					nextPos.add(checkPosition);
					checkPosition[0]+=direction[0];
					checkPosition[1]+=direction[1];

				}
			}
			if (board.occupied(checkPosition[0], checkPosition[1])!=this.returnColor() && inBounds(checkPosition[0], checkPosition[1])){
				//TODO:this is where i can implement some sort of in-mate function
				nextPos.add(checkPosition);
			}
		}

		possibleMoves = nextPos;
	}

}
