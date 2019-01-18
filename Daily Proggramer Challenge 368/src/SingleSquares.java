import java.util.ArrayList;
import java.util.Scanner;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/** 
 * Challenge is to create a board of Xs and Os that do not make a square. 
 * For example: 
 * 	X O X
 *  O X O 
 *  X O X 
 *  
 *  is not a valid configuration because the corners form a square. 
 *   O  X  X 
 *	 X  X  O 
 *	 X  O  X 
 *
 * is a valid configuration because there are no 4 pieces that form a square
 */
public class SingleSquares {
	


	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print("enter a number: ");
			int in = sc.nextInt();
			boolean[][] board;
			if(in < 8) {
				board = initailize(in);
				board = smallSolve(in, board);
				printBoard(board);
			}else {
				board = rndSolve(in);
				System.out.println("printing board");
				printBoard(board);
			}
		}
	}
	/**
	 * this algorithm steps through each possible solution.
	 * very inefficient 
	 * @param n
	 */
	private static boolean[][] smallSolve(int n, boolean[][] board) {
		while(!solutionFound(board)) {
			ArrayList<Cordante> change = findFailPoints(board);
			boolean[][] starting = board;
			boolean flag = false;
			// for each fail point, check if changing one makes a solution
			for(Cordante i : change) {
				board[i.getX()][i.getY()] = !board[i.getX()][i.getY()];
				if(solutionFound(board)) {
//					System.out.println("solution found");
					flag = true;
					return board;
				}else {
					// solution did not work
					// change the board to get rid of the square we had and move on
					board = starting;
				}
			}
			if(!flag) { 
					// solution failed. In order to progress, we have to change a pice on the board 
					board[change.get(0).getX()][change.get(0).getY()] = !board[change.get(0).getX()][change.get(0).getY()];
				}
	}
	return board;
}
	
	/**
	 *  after we check if we solved the board, we randomly change a problem peace
	 * @param n
	 * @param board
	 * @return
	 */
	private static boolean[][] rndSolve(int n){
		// place an Xs in a random spot.
		boolean[][] board = initailize(n);
		for(int x =0; x < board.length; x++) {
			for(int  y = 0; y < board[x].length; y++) {
				board[x][y] = (Math.random()  >  .5)? true: false;
			}
		}
		while(!solutionFound(board)) {
			// change anything that makes a square based on this X
			ArrayList<Cordante> fails = findFailPoints(board);
			int rnd = (int)(Math.random() * 4);
			board[fails.get(rnd).getX()][fails.get(rnd).getY()] = !board[fails.get(rnd).getX()][fails.get(rnd).getY()];
			// check for a solve
		}
		return board; 
		
	}
	/**
	 * creates a blank board ( all 0s)
	 * @param input
	 */
	public static boolean[][] initailize(int input) {
		boolean[][] board = new boolean[input][input];
		for(int x = 0; x < board.length; x++) {
			for(boolean i: board[x]) {
				i = false;
			}
		}
		return board;
	}
	/**
	 * method to print the current board to the console
	 */
	public static void printBoard( boolean[][] board) {
		System.out.println("--------------");
		for(int x =0 ; x < board.length; x++) {
			for(int y= 0; y < board[x].length; y++) {
				System.out.print(board[x][y]? " X " : " O ");
			}
			System.out.println();
		}

	}

	/**
	 * this will find any squares in the board
	 * @return will return null if there is no fail point . 
	 */
	public static ArrayList<Cordante> findFailPoints(boolean[][] board) {
		
		int check 	= 1;

			while(check < board.length) {
				for(int x = 0; x + check < board.length; x ++) {
					for(int y = 0; y + check < board[x].length; y++) {
						boolean test = board[x][y];
						if(	(test == board[x + check][y]) && 
							(test == board[x][y + check]) && 
							(test == board[x + check] [y + check])) {
							
								ArrayList<Cordante> give = new ArrayList<Cordante>();
								give.add(new Cordante(x,y));
								give.add(new Cordante(x + check,y));
								give.add(new Cordante(x,y + check));
								give.add(new Cordante(x + check ,y + check));
								return give;
						}
					}
						
				}
				check++;
			}
		
		return null;
	}

	/**
	 * return true if the board is in a valid configuration
	 * @return
	 */
	public static boolean solutionFound(boolean[][] board) {
		return null == findFailPoints(board);
	}
}
