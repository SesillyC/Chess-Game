package pieces;

import java.util.ArrayList;
import board.player;
import board.board;

public class pawn extends anyPiece{

	public pawn(anyPiece a) {
		super(a);
		if (!(a instanceof pawn))
			System.exit(-1);

	}

	public pawn(anyPiece a, board newBoard) {
		super(a, newBoard);
		if (!(a instanceof pawn))
			System.exit(-1);

	}

	public pawn(player p, board b, String s) {		// Initialize pawn object
		super(p, b, s);
	}

	public ArrayList<coordinates> findPossibleMoves(){		// ArrayList for all possible moves
		int xPos = this.xPos;
		int yPos = this.yPos;
		board b = this.getBoard();		
		ArrayList<coordinates> listOfMoves = new ArrayList<coordinates>();

		int sideMove;					// Depending on which side, we'll increment or decrement yPos to find possible locations
		if(this.p == player.BLACK)		
			sideMove = 1;
		else 
			sideMove = -1;

		if(yPos + sideMove < 0 || yPos + sideMove >= height)		// Reached the top or bottom of the board
			return listOfMoves;
		if(b.getPiece(xPos, yPos + sideMove) == null){				// Check if there's no piece in front 
			listOfMoves.add(new coordinates(xPos,yPos + sideMove));	
		}

		if(b.getPiece(xPos + 1, yPos + sideMove) != null && b.getPiece(xPos + 1, yPos + sideMove).p != this.p)		// Check if there's an enemy on the right
			listOfMoves.add(new coordinates(xPos + 1, yPos + sideMove));
		if(b.getPiece(xPos - 1, yPos + sideMove) != null && b.getPiece(xPos - 1, yPos + sideMove).p != this.p)		// Check if there's an enemy on the left
			listOfMoves.add(new coordinates(xPos - 1, yPos + sideMove));
		return listOfMoves;

	}

	public char getType() {		// Returns char for the charBoard used in computing a hashValue
		if(this.p == player.WHITE)
			return 'P';
		else
			return 'p';
	}

	public int weight(){	// Returns weight of this piece
		if(this.p == player.WHITE)	
			return -10;
		else 
			return 10;
	}
}
