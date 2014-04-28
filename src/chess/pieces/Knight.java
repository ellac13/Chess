package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Knight extends Piece {
	
	public Knight(int color){
		super(color);
	}

	@Override
	public HashSet<Integer[]> findNext(Board board,int currentC, int currentR) {
		HashSet<Integer[]> nextPos= new HashSet<Integer[]>();
		//List of possible directions
		int [][] directions={{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];

			//check that position is in range
			if (inBounds(checkPosition[0], checkPosition[0])){
				if (board.occupied(checkPosition[0], checkPosition[1])!=this.returnColor()){
					//TODO:this is where i can implement some sort of in-mate function
					nextPos.add(checkPosition);
				} else if (board.occupied(checkPosition[0], checkPosition[1])!= 0){
					//TODO:this is where i can implement some sort of in-mate function
					nextPos.add(checkPosition);
				}
			}
		}

		return nextPos;
	}

}
