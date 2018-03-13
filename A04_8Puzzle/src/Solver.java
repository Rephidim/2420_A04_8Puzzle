import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	
	private class SearchNode implements Comparable<SearchNode> {
		private SearchNode previous;
		private Board board;
		private int moveCounter = 0;
		
		public SearchNode(Board board) {
			this.board = board;
		}
		
		public SearchNode(Board board, SearchNode previous) {
			this.board = board;
			this.previous = previous;
			this.moveCounter = previous.moveCounter + 1;
		}
		
		public int compareTo(SearchNode child) {
			// if this sum > next sum, this sum lower priority
			return (this.board.manhattan() - child.board.manhattan()) + (this.moveCounter - child.moveCounter);
		}
	}
	
	private SearchNode finalBoard;
	
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)
    	// implement minPQ
    	// Check each board of highest priority
    	MinPQ<SearchNode> mpq = new MinPQ<SearchNode>();
    	mpq.insert(new SearchNode(initial));
    }
    public int moves() {
    	// min number of moves to solve initial board
    	return finalBoard.moveCounter;
    }
    public boolean isSolvable() {
    	return finalBoard != null;
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution
    	if (!isSolvable()) return null;
    	
    	Stack<Board> s = new Stack<Board>();
    	while (finalBoard != null) {
    		s.push(finalBoard.board);
    		finalBoard = finalBoard.previous;
    	}
    	
    	return s;
    }
    public static void main(String[] args) {
    	// solve a slider puzzle (given below) 
    }
}
