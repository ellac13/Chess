package chess.pieces;

import java.util.HashSet;

import chess.Board;

public class Rook extends Piece {

	public Rook(int color){
		super(color);
	}

	@Override
	public void findNext(Board board,int currentC, int currentR) {
		possibleMoves= new HashSet<Integer[]>();
		//List of possible directions
		int [][] directions={{1,0},{0,1},{-1,0},{0,-1}};

		//Go through directions and see if move is available
		for (int[] direction : directions ){
			Integer[] checkPosition = {0,0};
			checkPosition[0]=currentC+direction[0];
			checkPosition[1]=currentR+direction[1];
			step(checkPosition, direction, board);
		}

	}
	
	/*
	 * Recursive method to make steps in direction. position should be first position to check.
	 */
	
	private void step(Integer[] position, int[] direction, Board board){
		if (inBounds(position[0], position[1])){
			if (board.occupied(position[0], position[1])==0){
				possibleMoves.add(position);
				System.out.println("hej");
				position[0]+=direction[0];
				position[1]+=direction[1];
				step(position, direction, board);
				return;
			}else if (!(board.occupied(position[0], position[1])==this.returnColor())){
				possibleMoves.add(position);
				return;
			}
			return;
		}

	}



	public int picturePosition(){
		return 130;
	}




}
