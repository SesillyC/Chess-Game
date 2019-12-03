package pieces;

import board.player;
import board.board;
import java.util.ArrayList;

public class king extends anyPiece{

	public king(anyPiece a) {
		super(a);
		if (!(a instanceof king))
			System.exit(-1);;

	}

	public king(anyPiece a, board newBoard) {
		super(a, newBoard);
		if (!(a instanceof king))
			System.exit(-1);

	}

	public king(player p, board b, String s) {		// Initializes king object
		super(p, b, s);
	}

	public ArrayList<coordinates> findPossibleMoves(){		// Finds all possible moves
		int xPos = this.xPos;
		int yPos = this.yPos;
		board b = this.getBoard();
		ArrayList<coordinates> listOfMoves = new ArrayList<coordinates>();
		if(xPos - 1 >= 0 && yPos - 1 >= 0){
			if(b.getPiece(xPos - 1, yPos - 1) != null && b.getPiece(xPos - 1, yPos - 1).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos - 1, yPos - 1); 	// Check top-left
				listOfMoves.add(new coordinates(xPos - 1, yPos - 1));
			else if(b.getPiece(xPos - 1, yPos - 1) == null)
				listOfMoves.add(new coordinates(xPos - 1, yPos - 1));
		}

		if(yPos - 1 >= 0){
			if(b.getPiece(xPos, yPos - 1) != null && b.getPiece(xPos, yPos - 1).p != this.p)
				//	canMoveCanAdd(listOfMoves,xPos, yPos - 1);		// Check directly above
				listOfMoves.add(new coordinates(xPos, yPos - 1));
			else if(b.getPiece(xPos, yPos - 1) == null)
				listOfMoves.add(new coordinates(xPos, yPos - 1));
		}

		if(xPos + 1 < width && yPos - 1 >= 0){
			if(b.getPiece(xPos + 1, yPos - 1) != null && b.getPiece(xPos + 1, yPos - 1).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos + 1, yPos - 1);	// Check top-right
				listOfMoves.add(new coordinates(xPos + 1, yPos - 1));
			else if(b.getPiece(xPos + 1, yPos - 1) == null)
				listOfMoves.add(new coordinates(xPos + 1, yPos - 1));
		}

		if(xPos - 1 >= 0){
			if(b.getPiece(xPos - 1, yPos) != null && b.getPiece(xPos - 1, yPos).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos - 1, yPos);		// Check left
				listOfMoves.add(new coordinates(xPos - 1, yPos));
			else if(b.getPiece(xPos - 1, yPos) == null)
				listOfMoves.add(new coordinates(xPos - 1, yPos));
		}

		if(xPos + 1 < width){
			if(b.getPiece(xPos + 1, yPos) != null && b.getPiece(xPos + 1, yPos).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos + 1, yPos);		// Check right
				listOfMoves.add(new coordinates(xPos + 1, yPos));
			else if(b.getPiece(xPos + 1, yPos) == null)
				listOfMoves.add(new coordinates(xPos + 1, yPos));
		}

		if(xPos - 1 >= 0 && yPos + 1 < height){
			if(b.getPiece(xPos - 1, yPos + 1) != null && b.getPiece(xPos - 1, yPos + 1).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos - 1, yPos + 1 );	// Check bottom-left
				listOfMoves.add(new coordinates(xPos - 1, yPos + 1));
			else if(b.getPiece(xPos - 1, yPos + 1) == null)
				listOfMoves.add(new coordinates(xPos - 1, yPos + 1));
		}

		if(yPos + 1 < height){
			if(b.getPiece(xPos, yPos + 1) != null && b.getPiece(xPos, yPos + 1).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos, yPos + 1);		// Check directly below
				listOfMoves.add(new coordinates(xPos, yPos + 1));
			else if(b.getPiece(xPos, yPos + 1) == null)
				listOfMoves.add(new coordinates(xPos, yPos + 1));
		}

		if(xPos + 1 < width && yPos + 1 < height){
			if(b.getPiece(xPos + 1, yPos + 1) != null && b.getPiece(xPos + 1, yPos + 1).p != this.p)
				//canMoveCanAdd(listOfMoves,xPos + 1, yPos + 1);	// Check bottom-right
				listOfMoves.add(new coordinates(xPos + 1, yPos + 1));
			else if(b.getPiece(xPos + 1, yPos + 1) == null)
				listOfMoves.add(new coordinates(xPos + 1, yPos + 1));
		}

		return listOfMoves;
	}

	public char getType() { 			// Returns char for the charBoard used in computing a hashValue
		if(this.p == player.WHITE)
			return 'K';
		else 
			return 'k';
	}

	@Override
	public int weight() {				// Returns weight of this piece
		if(this.p == player.WHITE)
			return -9001;
		else
			return 9001;
	}
}