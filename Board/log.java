package board;

import game.GameConstants;
import pieces.*;

public class log implements GameConstants{				
	protected int turns;
	protected String squares[][];

	public log(board b){			// Create a log of the current board
		this.turns = b.turns;

		this.squares = new String[width][height];		// Initialize and store pieces in 2d array
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				this.squares[i][j] = null;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				anyPiece a = b.squares[i][j];
				if(a != null){
					String name = a.getName();
					this.squares[i][j] = name;
				}
			}
		}
	}

	public board createBoard(){				// Create a new board from log
		board b = new board();
		b.setTurn(this.turns);
	
		for(int i =0; i < width; i++)
			for(int j = 0; j < height; j++)
				b.squares[i][j] = null;

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int y = j;
				int x = i;
				String name = this.squares[i][j];		// Get the name of a piece
				anyPiece a;							// anyPiece object
				player p;							// Player
				if(name != null){
					if(name.substring(0,1).equals("B"))		// Get player's color
						p = player.BLACK;

					else
						p = player.WHITE;

					if(name.substring(1,2).equals("P")) {		// Check the name and create new anyPiece object based off of that
						a = new pawn(p, b, name);
						a.setCoordinate(x, y);
					} 
					else if(name.substring(1,2).equals("B")) {
						a = new bishop(p, b, name);
						a.setCoordinate(x, y);
					} 
					else if(name.substring(1,2).equals("K")) {
						a = new king(p, b, name);
						a.setCoordinate(x, y);
						
					} 
					else if(name.substring(1,2).equals("k")) {
						a = new knight(p, b, name);
						a.setCoordinate(x, y);
					}
					else if(name.substring(1,2).equals("Q")) {
						a = new queen(p, b, name);
						a.setCoordinate(x, y);
					} 
					else if(name.substring(1,2).equals("R")){
						a = new rook(p, b, name);
						a.setCoordinate(x, y);
					}
				}

			}
		}

		return b;				// Return new board
	}



}