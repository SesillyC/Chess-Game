package pieces;

import java.util.ArrayList;
import board.player;
import board.board;

public class queen extends anyPiece{

	public queen(anyPiece a) {
		super(a);
		if (!(a instanceof queen))
			System.exit(-1);;

	}

	public queen(anyPiece a, board newBoard) {
		super(a, newBoard);
		if (!(a instanceof queen))
			System.exit(-1);

	}

	public queen(player p, board b, String s) {
		super(p, b, s);
	}

	public ArrayList<coordinates> findPossibleMoves(){
		int xPos = this.xPos;
		int yPos = this.yPos;
		board b = this.getBoard();
		ArrayList<coordinates> listOfMoves = new ArrayList<coordinates>();

		for(int i = xPos - 1, j = yPos - 1; i >= 0 && j >= 0; i--,j--)		// Check diagonal top-left
			if(canMoveCanAdd(listOfMoves,i,j))
				i = -1;


		for(int j = yPos - 1; j >= 0; j--)									// Check directly above
			if(canMoveCanAdd(listOfMoves,xPos,j))
				j = -1;


		for(int i = xPos + 1, j = yPos - 1; i < width && j >= 0; i++,j--)	// Check diagonal top-right
			if(canMoveCanAdd(listOfMoves,i,j))
				i = width;

		for(int i = xPos + 1; i < width; i++)										// Check right
			if(canMoveCanAdd(listOfMoves,i,yPos))
				i = width;


		for(int i = xPos + 1, j = yPos + 1; i < width && j < height; i++,j++)			// Check diagonal bottom-right
			if(canMoveCanAdd(listOfMoves,i,j))
				i = width;

		for(int j = yPos + 1; j < height; j++)												// Check directly below
			if(canMoveCanAdd(listOfMoves,xPos,j))
				j = height;

		for(int i = xPos - 1, j = yPos + 1; i >= 0 && j < height; i--,j++ )				// Check diagonal bottom-left
			if(canMoveCanAdd(listOfMoves,i,j))
				i = -1;

		for(int i = xPos - 1; i >= 0; i--){											// Check left
			if(canMoveCanAdd(listOfMoves,i,yPos))
				i = -1;
		}

		return listOfMoves;
	}

	public char getType() {					// Returns char for the charBoard used in computing a hashValue
		if(this.p == player.WHITE)
			return 'Q';
		else
			return 'q';
	}

	@Override
	public int weight() {				// Returns weight of this piece
		if(this.p == player.WHITE)
			return -90;
		else
			return 90;
	}
}