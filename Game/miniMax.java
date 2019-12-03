package game;

import java.util.ArrayList;
import java.util.Random;

import board.board;
import board.player;
import pieces.anyPiece;
import pieces.coordinates;


public class miniMax implements GameConstants{		// Implements the Minimax algorithm for the AI to use
	private static int depth = 4;					// Depth we're searching at ****

	private static ArrayList<possibility> findMoves(board b) {
		ArrayList<possibility> possible = new ArrayList<possibility>();	// List of possibilites
		for(int i = 0; i < width; i++){									// Finds all possible moves for black AI to make
			for(int j = 0; j < height; j++){							// throughout the current board
				if(b.squares[i][j] != null){
					anyPiece a = b.squares[i][j];	
					ArrayList<coordinates> list = a.findPossibleMoves();				// Create a list of possible moves for a specific piece
					if (a.getPlayer() == player.BLACK) {				// If the piece we're checking is a black one, proceed
						if(list.size() > 0 && list != null){
							for(int z = 0; z < list.size(); z++){		// Create a new board reflecting a move and add to list
								move m = new move(a, (int)list.get(z).getX(), (int)list.get(z).getY());		// of possibilities
								board newB = new board(b);
								possible.add(new possibility(newB, m, new score()));
							}
						}
					}
				}
			}
		}
		return possible;
	}

	public static possibility findBestMove(board b){		// Find the move that's best for the AI to make
		ArrayList<possibility> possibilities = findMoves(b);	// Fill the list with possible moves

		possibility best = possibilities.get(0);				// Our "best" is the very first in the list
		int bestScore = 0;										

		ArrayList<possibility> tiedBoards = new ArrayList<possibility>();	// Create list of tied boards for the event multiple matching scores
		tiedBoards.add(possibilities.get(0));								// Add our best to it
		bestScore = evaluation(possibilities.get(0).getBoard(), depth, -200000, 200000, player.WHITE);		// Find the current best score

		for(int i = 1; i < possibilities.size(); i++){											// Find the score of the current possibilities' 
			int boardEval;																		// board either in Transpose table or in a new search		
			if(gameMaster.transposeTable.contains(possibilities.get(i).getBoard().hashValue))		// Search Transpose table and if we have this
				boardEval = gameMaster.transposeTable.get(possibilities.get(i).getBoard().hashValue).getScore();//  board recorded, no need for
																									// there to be a search
			else																					// Otherwise, 
				boardEval = evaluation(possibilities.get(i).getBoard(), depth, -200000, 200000, player.WHITE);	// Check each board		
			if(boardEval > bestScore){																// If we found a better score,					
				best = possibilities.get(i);														// Update the variables for the best move
				bestScore = boardEval;				
				tiedBoards = new ArrayList<possibility>();											// Empty list of tied boards
				tiedBoards.add(best);																// and start with a new board
			}
			else if(boardEval == bestScore){														// Otherwise add the board to list of
				tiedBoards.add(possibilities.get(i));												// matching ones
			}
		}

		if(tiedBoards.size() > 0){								// If we have more than 1 board that share the best score, pick a random one
			Random rand = new Random();
			int k = rand.nextInt(tiedBoards.size());
			best = tiedBoards.get(k);
		}
		return best;
	}

	public static void moveOnBoard(board b,move m){					// Makes the move on copied board
		int fromX = m.getOldX(), fromY = m.getOldY();
		int toX = m.getNewX(), toY = m.getNewY();

		anyPiece pieceToCatch = b.squares[toX][toY];
		anyPiece pieceToMove = b.squares[fromX][fromY];

		long [][][] zobristTable = gameMaster.zobristTable;			// Make a copy of the zobristTable for this instance

		if(pieceToCatch == b.whiteKing)								// Set winning condition for black ai
			b.whiteKing = null;
		pieceToMove.setCoordinate(toX, toY);						// Update the copied board with the piece

		char piece = pieceToMove.getType();							// Update this board's charBoard with the move
		b.charBoard[fromX][fromY] = '-';							// to create a new hashvalue for recording
		b.hashValue ^= zobristTable[fromX][fromY][b.indexOf(piece)];	

		b.charBoard[toX][toY] = piece;
		b.hashValue ^= zobristTable[toX][toY][b.indexOf(piece)];
	}

	public static int evaluation(board b, int d, int alpha, int beta, player p){		// Alpha Beta Pruning for a more efficient search
		if(d == 0){															// Depth is 0 and so we've run through an entire branch 
			int eval = evaluateBoard(b);									// via recursion		
			score s = new score();											// Create a new score			
			s.setScore(eval);												// Record score of the board
			s.setDepth(depth);												// and it's depth 
			gameMaster.transposeTable.put(b.hashValue, s);					// Put them in the transposeTable
			return eval;
		}

		else if(p == player.WHITE){											// Find the moves possible for White player
			ArrayList<move> m = new ArrayList<move>();						// Given this board
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					if(b.squares[i][j] != null){
						if(b.squares[i][j].getPlayer() == p){
							anyPiece a = b.squares[i][j];
							ArrayList<coordinates>	list = a.findPossibleMoves();
							if(list.size() > 0 && list != null){
								for(int z = 0; z < list.size(); z++){
									m.add(new move(a,(int)list.get(z).getX(),(int)list.get(z).getY()));
								}
							}
						}
					}
				}
			}
			int min = beta;
			for(move mo :m){				// Find min value for minimizing player
				board newB = new board(b);
				moveOnBoard(newB,mo);				
				min = Math.min(min, evaluation(newB, d-1, alpha, beta, player.BLACK));	// Evaluate the next turn on this branch, Black's
				score s = new score();									// Create new score
				s.setScore(min);										// Set it with the score
				s.setDepth(depth - d);									// and depth of the board 
				gameMaster.transposeTable.put(newB.hashValue, s);		// Put it in the transposeTable
				if(min <= alpha)				
					break;
			}
			return min;
		}
		else{
			ArrayList<move> m = new ArrayList<move>();
			for(int i = 0; i < height; i++){						// Find the moves possible for Black player,
				for(int j = 0; j < width; j++){						// given this board
					if(b.squares[i][j] != null){
						if(b.squares[i][j].getPlayer() == player.BLACK){
							anyPiece a = b.squares[i][j];
							ArrayList<coordinates>	list = a.findPossibleMoves();	
							if(list.size() > 0 && list != null){
								for(int z = 0; z < list.size(); z++){
									m.add(new move(a,(int)list.get(z).getX(),(int)list.get(z).getY()));
								}
							}
						}
					}
				}
			}
			int max = alpha;
			for(move mo : m){						// Find max value for maximizing player
				board newB = new board(b);
				moveOnBoard(newB,mo);
				max = Math.max(max, evaluation(newB, d-1, alpha, beta, player.WHITE));	// Evaluate the next turn on this branch, White's
				score s = new score();								// Create new score
				s.setScore(max);									// Set it with the score
				s.setDepth(depth - d);								// and depth of the board
				gameMaster.transposeTable.put(newB.hashValue, s);	// Put it in the transposeTable
				if(beta <= max)				
					break;			
			}
			return max;
		}
	}

	public static int evaluateBoard(board b){			// All pieces are weighted and added together to give a combined score
		int score = 0;
		for(int i = 0; i < height; i++){							// Takes into account the entire board, and essentially
			for(int j = 0; j < width; j++){							// subtracts points whenever it finds a white piece in play
				if(b.squares[i][j] != null){						// and adds when it finds a black piece based on their weight
					score += b.squares[i][j].weight();
				}	
			}
		}
		return score;
	}
}