package pieces;
import board.player;
import board.board;
import java.util.ArrayList;


public abstract class anyPiece implements game.GameConstants{
	protected String piece; 
	protected player p;
	protected board b;

	public int xPos;
	public int yPos;


	public anyPiece(anyPiece a) {
		this.xPos = a.xPos;
		this.yPos = a.yPos;
		this.piece = a.piece;
		this.p = a.p;
		this.b = a.b;
	}

	public anyPiece(anyPiece a, board newB) {
		this.xPos = a.xPos;
		this.yPos = a.yPos;
		this.piece = a.piece;
		this.p = a.p;
		this.b = newB;
}

	public anyPiece(player p,board b,String name){
		this.piece = name;				// Name of the piece
		this.xPos = -1;					// Piece is currently not on board
		this.yPos = -1;
		this.b = b;
		this.p = p;

	}
	public abstract ArrayList<coordinates> findPossibleMoves();

	public boolean setCoordinate(int x, int y){		// Set a piece at the coordinates while removing any that's there
		if(this.xPos != -1 && this.yPos != -1){		// Remove current piece from the spot
			this.selfRemoval();
		}
		else if(x >= width || x < 0 || y >= height || y < 0 )
			return false;
		this.xPos = x;
		this.yPos = y;
		this.b.setPiece(this, x, y);				// Set the new one in
		return true;

	}

	public int getX(){	// Returns the current piece's x coordinate
		return this.xPos;
	}

	public int getY(){	// Returns the current piece's y coordinate
		return this.yPos;
	}

	/*
	 *  If a piece can move to the given coordinates, add it to the Array List 
	 *  and then either returns true if there's another piece in the square (enemy/ally)
	 *  or returns false
	 */
	public boolean canMoveCanAdd(ArrayList<coordinates> c, int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)	// Coordinates are outside the board		
			return false;
				
		if(this.b.getPiece(x, y) == null){							// The square is empty
			c.add(new coordinates(x,y));
			return false;
		}	
		else if(this.b.getPiece(x,y).p != this.p){					// The square is occupied by the enemy
			c.add(new coordinates(x,y));
			return true;
		}
		else 														// The square is occupied by an allied piece
			return true;
	}
	public void selfRemoval(){	// Removes the current piece
		this.b.removePiece(this);
		this.xPos = -1;
		this.yPos = -1;
	}

	public board getBoard(){	// Returns the current board
		return this.b;
	}

	public String getName(){	// Gets the name of the piece
		return this.piece;
	}

	public player getPlayer(){		// Get the player
		return this.p;
	}

	public abstract char getType();
	
	public abstract int weight();
	
}