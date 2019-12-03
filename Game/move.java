package game;

import pieces.anyPiece;


public class move{			// For the AI to make a turn

	private anyPiece a;
	private int x1;			// Previous x
	private int y1;			// Previous y
	private int x2;			// New x
	private int y2;			// New y

	protected int depth;
	protected int move;
	protected int score;

	public move(anyPiece aPiece, int newX, int newY){
		this.x1 = aPiece.getX();
		this.y1 = aPiece.getY();
		this.x2 = newX;
		this.y2 = newY;
		a = aPiece;
	}

	public anyPiece getPiece(){			// Returns the piece
		return this.a;
	}

	public int getOldX() {
		return x1;
	}

	public int getNewX(){
		return x2;
	}

	public int getOldY(){
		return y1;
	}

	public int getNewY(){
		return y2;
	}

	public int getScore() {
		return score;
	}

	
	//	Produces the a readable version of the move as a string
	public String toString() {
		return  a.getPlayer().getStr() + " " + a.getType() + ": " +
				(char)(65 + x1) + (8-y1) + "-" + (char)(65 + x2) + (8-y2);
	}
}