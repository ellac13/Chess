package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Knight extends Piece {
	
	public Knight(int color){
		super(color);
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		threateningKing=false;
		//List of possible directions
		int [][] directions={{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];

			//check that position is in range
			if (inBounds(checkPosition[0], checkPosition[1])){
				if (board.occupied(checkPosition[0], checkPosition[1])==(-this.returnColor())){
					//TODO:this is where i can implement some sort of in-mate function
					if  (board.getPiece(checkPosition[0], checkPosition[1]).king()){
						threateningKing=true;
					}
					possibleMoves.add(checkPosition);
				} else if (board.occupied(checkPosition[0], checkPosition[1])== 0){
					possibleMoves.add(checkPosition);
				}
			}
		}
	}
	public int picturePosition(){
		return 195;
	}
	

}
