package game;

import board.board;
import pieces.anyPiece; 

public class textView implements GameConstants{
	board board;
	protected String[][] textBoard = new String[8][8];

	public textView(anyPiece[][] a){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(a[i][j] != null)
					this.textBoard[j][i] = a[i][j].getName();		// Make sure to present the pieces vertically
			}
		}
		displayWithLabels();
	}
	
	public void displayWithLabels(){			// Display the board
		System.out.print("    A   B   C   D   E   F   G   H  ");
		for(int i = 0; i < textBoard.length; i++){
			System.out.println(" ");
			System.out.println("  ---------------------------------");
			System.out.print("" + (char)(56-i) + " ");
			for(int j = 0; j < textBoard.length; j++ ){			
				if(textBoard[i][j] == null)
					System.out.print("|   ");
				else
					System.out.print("|" + textBoard[i][j]);
			}
			System.out.print("| " + (char)(56-i));
		}
		System.out.println("");
		System.out.println("---------------------------------");
		System.out.println("    A   B   C   D   E   F   G   H  ");
	}
	
	public void addToBoard(int x, int y, String p){		// Add a piece to the board
		textBoard[x][y] = p;
	}

	public void removeFromBoard(int x, int y){			// Remove a piece from the board
		textBoard[x][y] = null;
	}

}