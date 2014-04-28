package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Pawn extends Piece {
	
	public Pawn(int color){
		super(color);
	}

	@Override
	public HashSet<Integer[]> findNext(Board board,int currentC, int currentR) {
		HashSet<Integer[]> nextPos= new HashSet<Integer[]>();
		//check that position is in range

		Integer[] checkPosition={currentC, currentR+this.returnColor()};
		if (inBound(checkPosition[1])){
			if (board.occupied(checkPosition[0], checkPosition[1])==0){
				nextPos.add(checkPosition);
			}
			Integer [] temp = {1,-1};
			for (Integer t : temp){
				checkPosition[0]=currentR+t;
				if (inBound(checkPosition[0])){
					if (board.occupied(checkPosition[0], checkPosition[1])!=this.returnColor()){
						nextPos.add(checkPosition);
					}
				}
			}
		}
		if (!this.hasMoved()){
			if (board.occupied(checkPosition[0], checkPosition[1])==0){
				nextPos.add(checkPosition);
			}
		}


		return nextPos;
	}

}
