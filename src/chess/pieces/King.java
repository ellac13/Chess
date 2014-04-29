package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class King extends Piece{
	
	public King(int color){
		super(color);
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		//List of possible directions
		int [][] directions={{1,0},{1,1},{0,1},{-1,0},{-1,-1},{0,-1},{1,-1},{-1,1}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];

			//check that position is in range
			if (inBounds(checkPosition[0], checkPosition[1])){

				if (board.occupied(checkPosition[0], checkPosition[1])!=this.returnColor()){
					//TODO:this is where i can implement some sort of in-mate function
					possibleMoves.add(checkPosition);
				} else if (board.occupied(checkPosition[0], checkPosition[1])==0){
					//TODO:this is where i can implement some sort of in-mate function
					possibleMoves.add(checkPosition);
				}
			}
		}

	}
	public int picturePosition(){
		return 0;
	}
	

}


