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

	public int size() {
		return this.board.length;
	}

	public int hamming() {
		// number of blocks out of place
		int inversions = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = i + 1; j < board.length; j++) {
				try {
					if (board[i][j] > board[i + 1][j] || (board[i][j] == 0 && inversions % 2 == 1))
						inversions++;
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}

		return inversions;
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

	public boolean isGoal() {
		// is this board the goal board?
		return (this.manhattan() == 0);
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
		Board dup = new Board(this.dup());

		// change position with above block
		if (emptyRow > 0) {
			dup.swap(emptyRow, emptyCol, emptyRow - 1, emptyCol);
			qu.enqueue(dup);
		}
		// change position with block below
		if (emptyRow < N - 1) {
			dup.swap(emptyRow, emptyCol, emptyRow + 1, emptyCol);
			qu.enqueue(dup);
		}
		// change position to the left
		if (emptyRow > 0) {
			dup.swap(emptyRow, emptyCol, emptyRow, emptyCol - 1);
			qu.enqueue(dup);
		}
		// change position to the right
		if (emptyRow < N - 1) {
			dup.swap(emptyRow, emptyCol, emptyRow, emptyCol + 1);
			qu.enqueue(dup);
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

	public static void main(String[] args) {
		// unit tests (not graded)
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) {
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else {
			StdOut.println("Unsolvable puzzle");
		}
	}
}
