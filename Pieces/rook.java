package pieces;

import java.util.ArrayList;
import board.board;
import board.player;

public class rook extends anyPiece{

	public rook(anyPiece a) {
		super(a);
		if (!(a instanceof rook))
			System.exit(-1);;
		
	}
	
	public rook(anyPiece a, board newBoard) {
		super(a, newBoard);
		if (!(a instanceof rook))
			System.exit(-1);
		
	}

	public rook(player p, board b, String s) {	// Initialize rook object
		super(p, b, s);
	}
	
	public ArrayList<coordinates> findPossibleMoves(){	// ArrayList for all possible moves
		int xPos = this.xPos;			
		int yPos = this.yPos;

		board b = this.getBoard();
		ArrayList<coordinates> listOfMoves = new ArrayList<coordinates>();

		for(int i = xPos - 1; i >= 0; i--)			// Check the left
			if(canMoveCanAdd(listOfMoves,i,yPos))	
				break;
		for(int i = xPos + 1; i < width; i++)		// Check the right
			if(canMoveCanAdd(listOfMoves,i,yPos))	
				break;
		for(int i = yPos + 1; i < height; i++)			// Check down
			if(canMoveCanAdd(listOfMoves,xPos,i))
				break;
		for(int i = yPos - 1; i >= 0; i--)		// Check up
			if(canMoveCanAdd(listOfMoves,xPos,i))
				break;
		return listOfMoves;
	}

	public char getType() {			// Returns char for the charBoard used in computing a hashValue
		if(this.p == player.WHITE)
			return 'R';
		else
			return 'r';
	}

	@Override
	public int weight() {
		if(this.p == player.WHITE)
			return -50;
		else
			return 50;
	}
}