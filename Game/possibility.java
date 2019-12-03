package game;

import board.board;

public class possibility{			// Responsible for holding a board, a move to be made on that board, and it's score
	
	protected board board;
	protected move move;
	protected score score;

	
	public possibility(board b, move m, score s){
		this.board = b;
		this.move = m;
		this.score = s;
	}
	
	public move getMove(){
		return this.move;
	}

	public board getBoard(){
		return this.board;
	}

	public score getScore(){
		return this.score;
	}
}