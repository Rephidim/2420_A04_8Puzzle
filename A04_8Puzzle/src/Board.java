

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[][] board;
	private final int N;
	// holds position of 0
	private int emptyRow;
	private int emptyCol;

	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)
		this.N = blocks.length;
		board = new int[N][N];
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				// assigns 0 its position
				if (blocks[i][j] == 0) {
					emptyRow = i;
					emptyCol = j;
				}
				this.board[i][j] = blocks[i][j];
			}
		}

	}

	public int dimension() {
		return N;
	}

	  public int hamming() {
	        int block;
	        int total = 0;
	        for (int i = 0; i < N; i++) {
	            for (int j = 0; j < N; j++) {
	                block = board[i][j];
	                if (block != 0)
	                    total += hammingDistance(block, i, j);
	            }
	        }
	        return total;
	    }
	  
	  private int hammingDistance(int block, int i, int j) {
	        int goalRow = (block - 1) / N;
	        int goalCol = (block - 1) % N;
	        if (goalRow == i && goalCol == j)
	            return 0;
	        return 1;
	    }

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i + j != board[i][j]) {
					sum += distance(i, j);
				}
			}
			// x - x1 + y - y1
		}
		return sum;
	}
	 public Board twin() {
	        int tmp;
	        Board twinBoard = new Board(this.dup());
	        // Switch row 1 if there's an empty block in first blocks of row 0
	        if (board[0][0] == 0 || board[0][1] == 0) {
	            twinBoard.swap(1, 0, 1, 1);
	        }   
	        // Switch row 0 if there's no empty block in first blocks of row 0
	        else {
	            twinBoard.swap(0, 0, 0, 1);
	        }
	        return twinBoard;
	    }

	public boolean isGoal() {
		// is this board the goal board?
		return (this.hamming() == 0);
	}

	public boolean isSolvable() {
		// is this board solvable?
		return this.hamming() % 2 == 0;
	}

	public boolean equals(Object y) {
		// does this board equal y?
		if (this == y)
			return true;
		if (y == null || !(y instanceof Board) || ((Board) y).board.length != board.length)
			return false;
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board.length; col++)
				if (((Board) y).board[row][col] != node(row, col))
					return false;
		return true;
	}

	public Iterable<Board> neighbors() {

		Queue<Board> qu = new Queue<Board>();
		Board d;

		// change position with above block
		if (emptyRow > 0) {
			d = new Board(dup());
			d.swap(emptyRow, emptyCol, emptyRow - 1, emptyCol);
			qu.enqueue(d);
		}
		// change position with block below
		if (emptyRow < N - 1) {
			d = new Board(dup());
			d.swap(emptyRow, emptyCol, emptyRow + 1, emptyCol);
			qu.enqueue(d);
		}
		// change position to the left
		if (emptyCol > 0) {
			d = new Board(dup());
			d.swap(emptyRow, emptyCol, emptyRow, emptyCol - 1);
			qu.enqueue(d);
		}
		// change position to the right
		if (emptyCol < N - 1) {
			d = new Board(dup());
			d.swap(emptyRow, emptyCol, emptyRow, emptyCol + 1);
			qu.enqueue(d);
		}
		return qu;
	}

	private int[][] dup() {
		// copy of matrix to iterate through
		int[][] dup = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dup[i][j] = board[i][j];
			}
		}
		return dup;

	}

	private void swap(int row1, int col1, int row2, int col2) {
		// moves piece 
		int temp = board[row1][col1];
		board[row1][col1] = board[row2][col2];
		board[row2][col2] = temp;

		// updates position of 0 if moved
		if (board[row1][col1] == 0) {
			emptyRow = row1;
			emptyCol = col1;
		}
		if (board[row2][col2] == 0) {
			emptyRow = row2;
			emptyCol = col2;
		}

	}

	public String toString() {
		// string representation of this board (in the output format specified below)

		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	private boolean isEmpty(int node) {
		return node == 0;
	}

	private int distance(int x, int y) {
		int block = node(x, y);
		return (isEmpty(block)) ? 0 : Math.abs(x - col(block) + Math.abs(y - row(block)));
	}

	private int node(int x, int y) {
		return board[x][y];
	}

	private int row(int r) {
		return (r - 1) / N;
	}

	private int col(int c) {
		return (c - 1) % N;
	}
}
