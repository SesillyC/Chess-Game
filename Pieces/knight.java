package pieces;

import board.player;
import board.board;
import java.util.ArrayList;



public class knight extends anyPiece{

	public knight(anyPiece a) {
		super(a);
		if (!(a instanceof knight))
			System.exit(-1);;

	}

	public knight(anyPiece a, board newBoard) {
		super(a, newBoard);
		if (!(a instanceof knight))
			System.exit(-1);

	}

	public knight(player p, board b, String s) {
		super(p, b, s);		
	}

	public ArrayList<coordinates> findPossibleMoves(){		// ArrayList for all possible moves
		board b = this.getBoard();
		ArrayList<coordinates> listOfMoves = new ArrayList<coordinates>();

		int xPos = this.xPos - 2;
		int yPos = this.yPos - 1;
		if(xPos >= 0 && yPos >= 0){		// Checks farthest to left (top)
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos - 1;
		yPos = this.yPos - 2;
		if(xPos >= 0 && yPos >= 0){		// Checks top-left
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos + 1;
		yPos = this.yPos - 2;
		if(xPos < width && yPos >= 0){	// Checks top-right
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos + 2;
		yPos = this.yPos - 1;
		if(xPos < width && yPos >= 0){	// Checks farthest to right (top)
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos + 2;
		yPos = this.yPos + 1;
		if(xPos < width && yPos < height){			// Checks farthest to right (bottom) 
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));	
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos + 1;
		yPos = this.yPos + 2;
		if(xPos < width && yPos < height){			// Checks bottom-right 
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos - 1;
		yPos = this.yPos + 2;
		if(xPos >= 0 && yPos < height){				// Checks bottom-left
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		xPos = this.xPos - 2;
		yPos = this.yPos + 1;
		if(xPos >= 0 && yPos < height){				// Checks farthest to left (bottom)
			if(b.getPiece(xPos, yPos) != null && b.getPiece(xPos, yPos).p != this.p)
				listOfMoves.add(new coordinates(xPos,yPos));
			else if(b.getPiece(xPos, yPos) == null)
				listOfMoves.add(new coordinates(xPos,yPos));
		}

		return listOfMoves;
	}

	public char getType() {				// Returns char for the charBoard used in computing a hashValue
		if(this.p == player.WHITE)
			return 'N';
		else 
			return 'n';
	}
	public int weight(){			// Returns weight of this piece
		if(this.p == player.WHITE)
			return -30;
		else
			return 30;
	}
	
}