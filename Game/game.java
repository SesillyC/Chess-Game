// By: Sesilly Cruz
package game;

import board.board;

public class game{			// Handles the ongoing game and everything involved with it
	private board board;
	private textView view;
	private gameMaster master;

	public game(board b, textView v, gameMaster m){
		this.board = b;
		this.view = v;
		this.master = m;

	}
	public static void main(String[] args){
		board b = new board();
		textView v = new textView(b.squares);
		gameMaster m = new gameMaster(b,v);	
		game g = new game(b,v,m);
	}
}
