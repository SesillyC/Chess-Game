package board;

public enum player{
	WHITE("White"), BLACK("Black");

	private String str_;
	
	private player(String str) {
		str_ = str;
	}
	
	public String getStr() {
		return str_;
	}
}