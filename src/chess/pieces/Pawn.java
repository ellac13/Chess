package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Pawn extends Piece {
	public Pawn(int color){
		super(color);
		
		
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		//check that position is in range

		Integer[] checkPosition = {0,0};
		checkPosition[0]=currentC;
		checkPosition[1]=currentR-this.returnColor();
		if (inBounds(checkPosition[0], checkPosition[1])){
			if (board.occupied(checkPosition[0], checkPosition[1])==0){
				possibleMoves.add(checkPosition);
			}
			int[ ] sides = {1,-1};
			for (int side : sides ){
				if (inBounds(checkPosition[0]+side, checkPosition[1])){
					if ((board.occupied(checkPosition[0]+side, checkPosition[1])==(-this.returnColor()))){
						Integer[] temp= {checkPosition[0]+side, checkPosition[1]};
						possibleMoves.add(temp);
					}
				}
			}
		}
		if (!this.hasMoved()){
			Integer[] temp = {checkPosition[0], checkPosition[1]-this.returnColor()*1};
			if (board.occupied(temp[0], temp[1])==0){
				super.move();
				possibleMoves.add(temp);
			}
		 }

	}
	public int picturePosition(){
		return 320;
	}


}
