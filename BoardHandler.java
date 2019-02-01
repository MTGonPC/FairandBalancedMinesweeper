import java.awt.Dimension;
import java.util.Random;

public class BoardHandler {
	private static final int[][] SURROUND = {
			{-1,-1}, {0,-1}, {1,-1},
			{-1, 0}, {0, 0}, {1, 0},
			{-1, 1}, {0, 1}, {1, 1}
	};
	private char [][] publicBoard;
	private char [][] hiddenBoard;
	private boolean[][] doubleCheckingBoard;
	private int numOfBombs;
	private long seed;
	public BoardHandler(int width, int height, int numOfBombs) {
		publicBoard = new char[width][height];
		boardSetup(publicBoard);
		hiddenBoard = publicBoard.clone();
		doubleCheckingBoard = new boolean[width][height];
		this.numOfBombs = numOfBombs;
		
		
	}

	public BoardHandler(Dimension dim, int numOfBombs) {
		publicBoard = new char[(int) dim.getWidth()][(int) dim.getHeight()];
		boardSetup(publicBoard);
		hiddenBoard = publicBoard.clone();
		this.numOfBombs = numOfBombs;
	}

	public BoardHandler(int width, int height, int numOfBombs,long seed) {
		publicBoard = new char[width][height];
		boardSetup(publicBoard);
		hiddenBoard = publicBoard.clone();
		this.numOfBombs = numOfBombs;
		this.seed = seed;
	}

	private void boardSetup(char[][] board) {
		for(int rows = 0; rows < board.length; rows++) {
			for(int cols = 0; cols < board[0].length; cols++) {
				board[rows][cols] = '#';
			}
		}
	}
	public void firstClick(int x, int y,long seed) {
		hiddenBoard[y][x] = ' ';
		Random rand = new Random(seed);
		for(int i = 0; i < numOfBombs; i++) {
			int rx, ry;
			rx = rand.nextInt(hiddenBoard[0].length);
			ry = rand.nextInt(hiddenBoard.length);
			while(inSurrounding(x,y,rx,ry) || hiddenBoard[ry][rx] == 'X') {
				rx = rand.nextInt(hiddenBoard[0].length);
				ry = rand.nextInt(hiddenBoard.length);
			}
			hiddenBoard[ry][rx] = 'X';
			
		}
		fillNumbers();
		click(x,y,true);
	}
	public void firstClick(int x, int y) {
		hiddenBoard[y][x] = ' ';
		Random rand = new Random();
		for(int i = 0; i < numOfBombs; i++) {
			int rx, ry;
			rx = rand.nextInt(hiddenBoard[0].length);
			ry = rand.nextInt(hiddenBoard.length);
			while(inSurrounding(x,y,rx,ry) || hiddenBoard[ry][rx] == 'X') {
				rx = rand.nextInt(hiddenBoard[0].length);
				ry = rand.nextInt(hiddenBoard.length);
			}
			hiddenBoard[ry][rx] = 'X';
			
		}
		fillNumbers();
		click(x,y,true);
	}
	private void fillNumbers() {
		for(int rows = 0; rows < hiddenBoard.length; rows++) {
			for(int cols = 0; cols < hiddenBoard[0].length; cols++) {
				if(hiddenBoard[rows][cols] != 'X') {
					if(surBombs(cols,rows) == 0) {
						hiddenBoard[rows][cols] = ' ';
					} else {
						hiddenBoard[rows][cols] = (char) (surBombs(cols,rows) + '0');
					}
				}
			}
		}
	}
	private int surBombs(int x, int y) {
		int a = 0;
		for(int i = 0; i < 9; i++) {
			
				try {
					if(hiddenBoard[y+SURROUND[i][1]][x+SURROUND[i][0]] == 'X') {
						a++;
					}
				} catch(ArrayIndexOutOfBoundsException e) {}
			
		}

		return a;
	}
	private boolean inSurrounding(int x, int y, int ix, int iy) {
		for(int i = 0; i < 9; i++) {
			try {
				if(y+SURROUND[i][1]== iy && x+SURROUND[i][0]== ix) {
					return true;
				}
			} catch(ArrayIndexOutOfBoundsException e) {}
		}

		return false;
	}
	
	public char[][] getPublicBoard() {
		return publicBoard;
	}
	public char[][] getHiddenBoard() {
		return hiddenBoard;
	}
	public void click(int x,int y,boolean need) {
		publicBoard[y][x] = hiddenBoard[y][x];
		doubleCheckingBoard[y][x] = true;
		if(publicBoard[y][x] == 'X') {
			System.out.println("Boom goes your life");
			System.exit(0);
		} else if(publicBoard[y][x] == ' '){
			for(int i = 0; i< 9;i ++) {
				if(i != 4) {
					try {
						if(!doubleCheckingBoard[y+SURROUND[i][1]][x+SURROUND[i][0]]) {
							click(x+SURROUND[i][0],y+SURROUND[i][1],false);
						}
					} catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
		if(need) {
			doubleCheckingBoard = new boolean[hiddenBoard.length][hiddenBoard[0].length];
		}
	}
	public void debug(char[][] charArray) {
		for(int rows = 0; rows < charArray.length; rows++) {
			for(int cols = 0; cols < charArray[0].length; cols++) {
				System.out.print(charArray[rows][cols]);
			}
			System.out.println("");
		}
	}

}
