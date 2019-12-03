package game;

public class score		// Responsible for representing a board found at a certain depth and it's score
{
	private int value;
	private int depth;
	
	public score() {
		value = depth = 0;
	}
	
	public int getScore() {
		return value;
	}
	public void setScore(int theScore) {
		value = theScore;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int theDepth) {
		if (theDepth >= 0)
			depth = theDepth;
	}
}
