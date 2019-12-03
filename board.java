package board;

import pieces.*;
import java.util.ArrayList;
import java.util.Random;

import game.GameConstants;
import game.gameMaster;

public class board implements GameConstants{		 
	public anyPiece squares[][];					// 2d array holding objects in place of the chess board
	public char charBoard[][] = new char[8][8];		// To be used to create hashValue for this board
													// Fully instantiated at the very bottom (line 402) ***
	public int turns;								// Number of turns to record
	public anyPiece whiteKing;						// Kings to set aside for win condition later
	public anyPiece blackKing;
	public long hashValue;							// Value of this particular board

	public board(){
		squares = new anyPiece[width][height];		// Set up the squares 
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				squares[i][j]= null;

		this.whiteKing = null;
		this.blackKing = null;
		this.turns = 0;	

		generateBoard();									// Pick from 1 of 2 boards to play with
		//	generateTestBoard();

		gameMaster.initTable();							
		this.hashValue = computeHash();						// Compute hash of this table
		System.out.println("Hash value of this board: " + hashValue);		
	}

	public board(board b){									// Creates a copy of the board for the AI to use
		this.hashValue = b.hashValue;
		this.squares = new anyPiece[width][height];
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				this.squares[i][j] = null;

		for(int i = 0; i < width; i++)					
			for(int j = 0; j < height; j++){	
				if(b.squares[i][j] != null){
					anyPiece a = b.squares[i][j];
					if(a instanceof pawn)
						this.squares[i][j] = new pawn(a, this);					
					else if(a instanceof bishop)
						this.squares[i][j] = new bishop(a, this);
					else if(a instanceof knight)
						this.squares[i][j] = new knight(a, this);
					else if(a instanceof rook)
						this.squares[i][j] = new rook(a, this);
					else if(a instanceof queen)
						this.squares[i][j] = new queen(a, this);
					else if(a instanceof king){
						this.squares[i][j] = new king(a, this);
						if(a.getPlayer() == player.WHITE)
							setWhiteKing(a);
						else
							setBlackKing(a);			
					}
					this.charBoard[i][j] = a.getType();
				}
				else
					this.charBoard[i][j] = '-';
			}
	}

	public void generateTestBoard(){
		/*
		 *  Black player's pieces
		 */
		anyPiece BP1 = new pawn(player.BLACK, this, "BP1");
		BP1.setCoordinate(0,3);

		anyPiece BP2 = new pawn(player.BLACK, this, "BP2");
		BP2.setCoordinate(1,2);

		anyPiece BP3 = new pawn(player.BLACK, this, "BP3");
		BP3.setCoordinate(2,1);

		anyPiece BP4 = new pawn(player.BLACK, this, "BP4");
		BP4.setCoordinate(3,1);

		anyPiece BP5 = new pawn(player.BLACK, this, "BP5");
		BP5.setCoordinate(4,1);

		anyPiece BP6 = new pawn(player.BLACK, this, "BP6");
		BP6.setCoordinate(5,3);

		anyPiece BP7 = new pawn(player.BLACK, this, "BP7");
		BP7.setCoordinate(6,1);

		anyPiece BP8 = new pawn(player.BLACK, this, "BP8");
		BP8.setCoordinate(7,2);

		anyPiece BR1 = new rook(player.BLACK, this, "BR1");
		BR1.setCoordinate(0,0);

		anyPiece Bk1 = new knight(player.BLACK, this, "Bk1");
		Bk1.setCoordinate(2,2);

		anyPiece BB1 = new bishop(player.BLACK, this, "BB1");
		BB1.setCoordinate(2,0);

		anyPiece BQ1 = new queen(player.BLACK, this, "BQ1");
		BQ1.setCoordinate(3,0);
		
		anyPiece BK1 = new king(player.BLACK, this, "BK1");
		BK1.setCoordinate(4,0);
		setBlackKing(BK1);

		anyPiece BB2 = new bishop(player.BLACK, this, "BB2");
		BB2.setCoordinate(5,0);

		anyPiece Bk2 = new knight(player.BLACK, this, "Bk2");
		Bk2.setCoordinate(6,0);

		anyPiece BR2 = new rook(player.BLACK, this, "BR2");
		BR2.setCoordinate(7,0);
		/*
		 * White player's pieces
		 */
		anyPiece WP1 = new pawn(player.WHITE, this, "WP1");
		WP1.setCoordinate(0,4);

		anyPiece WP2 = new pawn(player.WHITE, this, "WP2");
		WP2.setCoordinate(1,4);

		anyPiece WP3 = new pawn(player.WHITE, this, "WP3");
		WP3.setCoordinate(2,6);

		anyPiece WP4 = new pawn(player.WHITE, this, "WP4");
		WP4.setCoordinate(3,6);

		anyPiece WP5 = new pawn(player.WHITE, this, "WP5");
		WP5.setCoordinate(4,5);

		anyPiece WP6 = new pawn(player.WHITE, this, "WP6");
		WP6.setCoordinate(5,6);
		
		anyPiece WP7 = new pawn(player.WHITE, this, "WP7");
		WP7.setCoordinate(6,4);

		anyPiece WP8 = new pawn(player.WHITE, this, "WP8");
		WP8.setCoordinate(7,6);

		anyPiece WR1 = new rook(player.WHITE, this, "WR1");
		WR1.setCoordinate(0,7);

		anyPiece Wk1 = new knight(player.WHITE, this, "Wk1");
		Wk1.setCoordinate(1,7);

		anyPiece WB1 = new bishop(player.WHITE, this, "WB1");
		WB1.setCoordinate(1,6);

		anyPiece WQ1 = new queen(player.WHITE, this, "WQ1");
		WQ1.setCoordinate(3,7);

		anyPiece WK1 = new king(player.WHITE, this, "WK1");
		WK1.setCoordinate(4,7);
		setWhiteKing(WK1);

		anyPiece WB2 = new bishop(player.WHITE, this, "WB2");
		WB2.setCoordinate(5,7);

		anyPiece Wk2 = new knight(player.WHITE, this, "Wk2");
		Wk2.setCoordinate(6,7);

		anyPiece WR2 = new rook(player.WHITE, this, "WR2");
		WR2.setCoordinate(7,7);

	}
	public void setWhiteKing(anyPiece a){		// Set the white king
		this.whiteKing = a;
	}

	public void setBlackKing(anyPiece a){		// Set the black king
		this.blackKing = a;
	}

	public anyPiece getPiece(int x, int y){		// Returns the piece with given coordinates
		if(y < 0 || y >= height || x < 0 || x >= width)
			return null;
		return this.squares[x][y];
	}

	public void setPiece(anyPiece a, int x, int y){			// Set piece coordinates
		this.squares[x][y] = a;	
	}

	public void removePiece(anyPiece a){		// Remove a piece
		int x = a.getX();
		int y = a.getY();
		this.squares[x][y] = null;	
	}

	public int indexOf(char piece){				// Returns value of char for hashValue
		if (piece == 'P')
			return 0;

		if (piece == 'N')
			return 1;

		if (piece == 'B')
			return 2;

		if (piece == 'R')
			return 3;

		if (piece == 'Q')
			return 4;

		if (piece == 'K')
			return 5;

		if (piece == 'p')
			return 6;

		if (piece == 'n')
			return 7;

		if (piece == 'b')
			return 8;

		if (piece == 'r')
			return 9;

		if (piece == 'q')
			return 10;

		if (piece == 'k')
			return 11;

		//else
		return -1;	
	}


	// Computes the hash value of the current board
	public long computeHash(){
		long h = 0;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (charBoard[i][j] != '-'){
					int piece = indexOf(charBoard[i][j]);
					h ^= gameMaster.zobristTable[i][j][piece];
				}
			}
		}
		return h;
	}

	public void setTurn(int t){		// Set turns
		this.turns = t;
	}

	public void increaseTurn(){		// Increase turns
		this.turns++;
	}

	public void generateBoard(){
		/*
		 *  Black player's pieces
		 */
		anyPiece BP1 = new pawn(player.BLACK, this, "BP1");
		BP1.setCoordinate(0,1);	
		this.charBoard[0][1] = 'p';

		anyPiece BP2 = new pawn(player.BLACK, this, "BP2");
		BP2.setCoordinate(1,1);
		this.charBoard[1][1] = 'p';

		anyPiece BP3 = new pawn(player.BLACK, this, "BP3");
		BP3.setCoordinate(2,1);
		this.charBoard[2][1] = 'p';

		anyPiece BP4 = new pawn(player.BLACK, this, "BP4");
		BP4.setCoordinate(3,1);
		this.charBoard[3][1] = 'p';

		anyPiece BP5 = new pawn(player.BLACK, this, "BP5");
		BP5.setCoordinate(4,1);
		this.charBoard[4][1] = 'p';

		anyPiece BP6 = new pawn(player.BLACK, this, "BP6");
		BP6.setCoordinate(5,1);
		this.charBoard[5][1] = 'p';

		anyPiece BP7 = new pawn(player.BLACK, this, "BP7");
		BP7.setCoordinate(6,1);
		this.charBoard[6][1] = 'p';

		anyPiece BP8 = new pawn(player.BLACK, this, "BP8");
		BP8.setCoordinate(7,1);
		this.charBoard[7][1] = 'p';

		anyPiece BR1 = new rook(player.BLACK, this, "BR1");
		BR1.setCoordinate(0,0);
		this.charBoard[0][0] = 'r';

		anyPiece Bk1 = new knight(player.BLACK, this, "Bk1");
		Bk1.setCoordinate(1,0);
		this.charBoard[1][0] = 'n';

		anyPiece BB1 = new bishop(player.BLACK, this, "BB1");
		BB1.setCoordinate(2,0);
		this.charBoard[2][0] = 'b';

		anyPiece BQ1 = new queen(player.BLACK, this, "BQ1");
		BQ1.setCoordinate(3,0);
		this.charBoard[3][0] = 'q';

		anyPiece BK1 = new king(player.BLACK, this, "BK1");
		BK1.setCoordinate(4,0);
		this.charBoard[4][0] = 'k';
		setBlackKing(BK1);

		anyPiece BB2 = new bishop(player.BLACK, this, "BB2");
		BB2.setCoordinate(5,0);
		this.charBoard[5][0] = 'b';

		anyPiece Bk2 = new knight(player.BLACK, this, "Bk2");
		Bk2.setCoordinate(6,0);
		this.charBoard[6][0] = 'n';

		anyPiece BR2 = new rook(player.BLACK, this, "BR2");
		BR2.setCoordinate(7,0);
		this.charBoard[7][0] = 'r';

		/*
		 * White player's pieces
		 */
		anyPiece WP1 = new pawn(player.WHITE, this, "WP1");
		WP1.setCoordinate(0,6);
		this.charBoard[0][6] = 'P';

		anyPiece WP2 = new pawn(player.WHITE, this, "WP2");
		WP2.setCoordinate(1,6);
		this.charBoard[1][6] = 'P';

		anyPiece WP3 = new pawn(player.WHITE, this, "WP3");
		WP3.setCoordinate(2,6);
		this.charBoard[2][6] = 'P';

		anyPiece WP4 = new pawn(player.WHITE, this, "WP4");
		WP4.setCoordinate(3,6);
		this.charBoard[3][6] = 'P';

		anyPiece WP5 = new pawn(player.WHITE, this, "WP5");
		WP5.setCoordinate(4,6);
		this.charBoard[4][6] = 'P';

		anyPiece WP6 = new pawn(player.WHITE, this, "WP6");
		WP6.setCoordinate(5,6);
		this.charBoard[5][6] = 'P';

		anyPiece WP7 = new pawn(player.WHITE, this, "WP7");
		WP7.setCoordinate(6,6);
		this.charBoard[6][6] = 'P';

		anyPiece WP8 = new pawn(player.WHITE, this, "WP8");
		WP8.setCoordinate(7,6);
		this.charBoard[7][6] = 'P';

		anyPiece WR1 = new rook(player.WHITE, this, "WR1");
		WR1.setCoordinate(0,7);
		this.charBoard[0][7] = 'R';

		anyPiece Wk1 = new knight(player.WHITE, this, "Wk1");
		Wk1.setCoordinate(1,7);
		this.charBoard[1][7] = 'N';

		anyPiece WB1 = new bishop(player.WHITE, this, "WB1");
		WB1.setCoordinate(2,7);
		this.charBoard[2][7] = 'B';

		anyPiece WQ1 = new queen(player.WHITE, this, "WQ1");
		WQ1.setCoordinate(3,7);
		this.charBoard[3][7] = 'Q';

		anyPiece WK1 = new king(player.WHITE, this, "WK1");
		WK1.setCoordinate(4,7);
		this.charBoard[4][7] = 'K';
		setWhiteKing(WK1);

		anyPiece WB2 = new bishop(player.WHITE, this, "WB2");
		WB2.setCoordinate(5,7);
		this.charBoard[5][7] = 'B';

		anyPiece Wk2 = new knight(player.WHITE, this, "Wk2");
		Wk2.setCoordinate(6,7);
		this.charBoard[6][7] = 'N';

		anyPiece WR2 = new rook(player.WHITE, this, "WR2");
		WR2.setCoordinate(7,7);
		this.charBoard[7][7] = 'R';

		for(int i = 0; i < width; i++)						// Fully instantiate charBoard with the spots that have nothing
			for(int j = 0; j < height; j++){
				if((int)this.charBoard[i][j] == 0)
					this.charBoard[i][j] = '-';
			}

	}
}