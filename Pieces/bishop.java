package pieces;

import board.player;
import board.board;
import java.util.ArrayList;


public class bishop extends anyPiece{

	public bishop(anyPiece a) {
		super(a);
		if (!(a instanceof bishop))
			System.exit(-1);;
		
	}
	
	public bishop(anyPiece a, board newBoard) {
		super(a, newBoard);
		if (!(a instanceof bishop))
			System.exit(-1);
		
	}
	public bishop(player p,board b, String s) { 	// Initialize bishop object
		super(p, b, s);
	}
	
	public ArrayList<coordinates> findPossibleMoves(){	// ArrayList for all possible moves
		int xPos = this.xPos;
		int yPos = this.yPos;
		board b = this.getBoard();						// Get current board
		ArrayList<coordinates> listOfMoves = new ArrayList<coordinates>();
		
		for(int i = xPos + 1, j = yPos - 1; i < width && j >= 0; i++,j--){	// Check top-right diagonal
			if(canMoveCanAdd(listOfMoves,i,j)){
				break;
			}
		}
		
		for(int i = xPos - 1, j = yPos - 1; i >= 0 && j >= 0; i--,j--){	// Check top-left diagonal
			if(canMoveCanAdd(listOfMoves,i,j)){
				break;
			}
		}
		
		for(int i = xPos + 1, j = yPos + 1; i < width && j < height; i++,j++){		// Check bottom-right diagonal
			if(canMoveCanAdd(listOfMoves,i,j)){
				break;
			}
		}
		
		for(int i = xPos - 1, j = yPos + 1; i >= 0 && j < height; i--,j++){				// Check bottom-left diagonal
			if(canMoveCanAdd(listOfMoves,i,j)){
				break;
			}
		}
		return listOfMoves;
	}
	
	public char getType() {			// Returns char for the charBoard used in computing a hashValue
		if(this.p == player.WHITE)
			return 'B';
		else
			return 'b';
		
	}
	
	public int weight(){	// Returns weight of this piece
		if(this.p == player.WHITE)
			return -30;
		else 
			return 30;
	}
}