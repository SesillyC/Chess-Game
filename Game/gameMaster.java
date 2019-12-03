package game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import board.board;
import board.log;
import pieces.coordinates;

public class gameMaster implements GameConstants{					// Manages the current board as moves are made
	protected textView t;
	private board b;									// Board to be manipulated
	protected boolean aiTurn = false;					// Boolean for if it's the AI's turn
	protected boolean selection = true;					// Boolean for if we are selecting a piece or not
	boolean usePrerecordedMoves = true;					// Check line 117
	public static long[][][] zobristTable = new long[8][8][12];		// Our Zobrist Table for the current game
	public static Hashtable<Long, score> transposeTable = new Hashtable<Long, score>();	// Our Hashtable to record all the boards examined
	Stack<log> historyLog;								// Holds the history of the game
	protected log log;									
	String piece = "";
	int playerTurn = 0;


	public gameMaster(board b,textView t){		// Initialize gameMaster
		this.b = b;
		this.t = t;
		this.historyLog = new Stack<log>();
		gameStart();
	}

	public static void initTable(){				// Initiates Zobrist Table for the game
		Random rand = new Random();
		for (int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				for(int k = 0; k < 12; k++){
					zobristTable[i][j][k] = rand.nextInt();				
				}
			}
		}	
	}

	public void aiTurn(){								// AI's turn
		log aiLog = historyLog.pop();					// Create a log from the top of the stack
		board aiBoard = aiLog.createBoard();			// Create a board from the log
		possibility bestPossibility =  miniMax.findBestMove(aiBoard);	// Find the best way to proceed
		move m = bestPossibility.getMove();

		if(b.squares[m.getNewX()][m.getNewY()] == b.whiteKing){					// Check if the AI wins this round and if so,
			if(b.squares[m.getOldX()][m.getOldY()] != null){						// Show the final board and end the game
				System.out.println(b.squares[m.getOldX()][m.getOldY()].getName() + " at (" + m.getOldX()+ ", " + m.getOldY() +
						") to (" + m.getNewX()+ ", " + m.getNewY() + ")");					// Print the AI's move coordinates
				b.squares[m.getOldX()][m.getOldY()].setCoordinate(m.getNewX(),m.getNewY());
			}
			t = new textView(b.squares);									// Print the updated board
			System.out.println("Black side's victory!!!");					// End the game
			System.exit(-1);
		}

		else{														// Otherwise, proceed as normal
			if(b.squares[m.getOldX()][m.getOldY()] != null){		// and make the move
				System.out.println(b.squares[m.getOldX()][m.getOldY()].getName() + " at (" + m.getOldX()+ ", " + m.getOldY() +
						") to (" + m.getNewX()+ ", " + m.getNewY() + ")");				// Print the AI's move coordinates
				b.squares[m.getOldX()][m.getOldY()].setCoordinate(m.getNewX(),m.getNewY());
			}
			t = new textView(b.squares);		// Print the updated board

			log = new log(b);					// Create a new log to push onto the stack
			historyLog.push(log);			
			b.increaseTurn();					// Turn is now over, so increment the counter
			aiTurn = false;						// End the turn	
		}
	}
	
	public ArrayList<coordinates> showMoves(int i, int j){		// Shows the chosen piece's coordinates and possible moves
		System.out.println("Piece coordinates: " + (int)b.squares[i][j].getX() + "," + (int)b.squares[i][j].getY());
		System.out.println(" ");
		System.out.println("List of possible moves:");
		ArrayList<coordinates>	list = b.squares[i][j].findPossibleMoves();		// Get ArrayList of possible moves for the piece

		for(int k = 0; k < list.size(); k++)						// Print out moves for user
			if(list != null)
				System.out.println((int)list.get(k).getX() + ", " + (int)list.get(k).getY());

		System.out.println(" ");
		System.out.println("Enter coordinates to move to in '(x,y)' format");	// Prompt user

		return list;

	}
	
	public void playerTurn(){							// It's now the user's turn
		Scanner scanner = new Scanner(System.in);
		while(selection){											// System will prompt you until you select a white piece
			System.out.println("Turn #" + b.turns);					// Show current turn
			System.out.println(" ");
			System.out.println("Select your piece");		

			/*	if (usePrerecordedMoves) {
				piece = playerMove[playerTurn][0];					// To use with prerecorded input
				System.out.println(piece);							
			}
			else {
				piece = scanner.nextLine();
			}*/

			piece = scanner.nextLine();
			if(piece.substring(0,1).equals("W")){					// Check if selected piece is white or not
				selection = false;		
			}
			else{
				System.out.println("That's not one of your pieces");		// Tells you if it's not
				System.out.println(" ");
			}
		}
		ArrayList<coordinates>	list;
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++){				
				if(b.squares[i][j] != null){							//  Check user input
					if(piece.equals(b.squares[i][j].getName())){	
						list = showMoves(i,j);

						String c = scanner.nextLine();
						String coords[] = c.split(",");									// Take apart given input to be used 
						String coords1 = coords[0];
						String coords2 = coords[1];

						coords1 = coords1.substring(1, coords1.length());			// The " (x, "
						coords2 = coords2.substring(0, coords2.length()-1);			// " y) "

						int x = Integer.valueOf(coords1);								// Change it from string to integer
						int y = Integer.valueOf(coords2);
						if(x < 0 || x >= width || y < 0 || y >= height){			// Give a warning if coordinates are outside loop and then start over
							System.out.println("Given coordinates are outside the board, try again");
							System.out.println(" ");

							j = 8;				// Not sure how to leave the nested for loops otherwise since break simply sends me
							i = 8;				// back to asking for the coordinates again rather than resetting to asking for a piece
						}
						
						if(list != null){
							for(int l = 0; l < list.size(); l++){							// Check the list of moves							
								if(list.get(l).getX() == x && list.get(l).getY() == y){		// If the coordinates that the user gave are
									if(b.squares[x][y] == b.blackKing)						//  in the possible moves list, use them
										b.blackKing = null;									// Check for win condition
									b.squares[i][j].setCoordinate(x, y);				// Coordinates updated and piece moved	

									char piece = b.charBoard[i][j];						// Update charBoard to reflect the current board
									b.charBoard[i][j] = '-';							// using the zobristTable
									b.hashValue ^= zobristTable[i][j][b.indexOf(piece)];

									b.charBoard[x][y] = piece;
									b.hashValue ^= zobristTable[x][y][b.indexOf(piece)];
									System.out.println("Hash value of this board: " + b.hashValue);
									log = new log(this.b);
									historyLog.push(log);
									t = new textView(b.squares);						// Redraw the board
									break;												// End loop as soon as a match is found
								}
								else if(l + 1 == list.size()){		// Made it to the end of the list
									System.out.println("Given coordinates not possible, try again");
									System.out.println(" ");
								}
							}
						}
						b.increaseTurn();						// Just finished the turn, so +1
						aiTurn = true;							// AI's turn
						selection = true;						// Go back to selecting another piece on the board
						j = 8;				// For some reason, using break doesn't work so I'll just end the for loops this way
						i = 8;
					}	
					else if(j + 1 == 8 && i + 1 == 8){					// Selected a piece that's already been captured
						j = 8;				
						i = 8;
					}
				}
			}
	}


	public void gameStart(){			// Start the game
		boolean gameOn = true;			// Boolean for if the game is ongoing
		Scanner scanner = new Scanner(System.in);		

		while(gameOn){									// While the game is ongoing
			while(aiTurn){					
				System.out.println(" ");
				System.out.println(" AI's turn: Turn #" + b.turns);
				System.out.println(" ");

				aiTurn();								// AI proceeds with their turn

				System.out.println(" ");
				System.out.println(" End of AI's turn");
				System.out.println(" ");
			}

			if(b.whiteKing == null){									// Check if this turn was the winning turn for Black
				System.out.println("Black side's victory!!!");
				System.exit(-1);
			}

			playerTurn();												// Otherwise, go onto the player's turn

			if(b.blackKing == null){									// Check if this turn was the winning turn for White
				System.out.println("White side's victory!!!");
				System.exit(-1);
			}
		}
	}
}