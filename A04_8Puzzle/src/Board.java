import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[][] board;
	
    public Board(int[][] blocks)   {
    	// construct a board from an N-by-N array of blocks
    	// (where blocks[i][j] = block in row i, column j)
    	for (int i = 0; i < blocks.length; i++) {
    		for (int j = 0; j < blocks.length; j++) {
    			this.board[i][j] = blocks[i][j];
    		}
    	}
    }
                                           
    public int size()   {
    	return this.board.length;
    }
    public int hamming()   {
    	// number of blocks out of place
    }
    public int manhattan()    {
    	// sum of Manhattan distances between blocks and goal
    }
    public boolean isGoal()    {
    	// is this board the goal board?
    	if (this.manhattan() == 0) {
    		return true;
    	} return false;
    }
    public boolean isSolvable()   {
    	// is this board solvable?
    	
    }
    
    public boolean equals(Object y) {
    	// does this board equal y?
    	return this.equals(y);
    }
    
    public Iterable<Board> neighbors()  {
    	// all neighboring boards
    }
    
    public String toString()   {
    	// string representation of this board (in the output format specified below)
    	final int N = this.size();
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