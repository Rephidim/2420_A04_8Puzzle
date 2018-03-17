import java.util.LinkedList;

public class Board {
    private static final int BLANK = 0;
    private final int N;

    private int[][] board;

    public Board(int[][] blocks) {
    	this.N = blocks.length;
        this.board = copy(blocks);
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                copy[i][j] = blocks[i][j];

        return copy;
    }

    public int size() {
        return N;
    }

    public int hamming() {
        int inversions = 0;
        for (int x = 0; x < N; x++)
            for (int y = 0; y < N; y++)
                if (blockOutOfPosition(x, y)) inversions++;

        return inversions;
    }

    private boolean blockOutOfPosition(int x, int y) {
        int n = node(x, y);
        return !isEmpty(n) && n != goalFor(x, y);
    }

    private int goalFor(int x, int y) {
        return x*size() + y + 1;
    }

    private boolean isEmpty(int block) {
        return block == BLANK;
    }

    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < N; row++)
            for (int col = 0; col < N; col++)
                sum += distance(row, col);

        return sum;
    }

    private int distance(int row, int col) {
        int block = node(row, col);

        return (isEmpty(block)) ? 0 : Math.abs(row - row(block)) + Math.abs(col - col(block));
    }

    private int node(int x, int y) {
        return board[x][y];
    }

    private int row (int block) {
        return (block - 1) / size();
    }

    private int col (int block) {
        return (block - 1) % size();
    }

    public boolean isGoal() {
    	return this.hamming() == 0;
    }

    public Board twin() {
        for (int row = 0; row < N; row++)
            for (int col = 0; col < N - 1; col++)
                if (!isEmpty(node(row, col)) && !isEmpty(node(row, col + 1)))
                    return new Board(swap(row, col, row, col + 1));
        throw new RuntimeException();
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copy(board);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    public boolean equals(Object y) {
        if (y==this) return true;
        if (y==null || !(y instanceof Board) || ((Board)y).N != N) return false;
        for (int row = 0; row < N; row++)
            for (int col = 0; col < N; col++)
                if (((Board) y).board[row][col] != node(row, col)) return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        int[] location = blankSpot();
        int spaceRow = location[0];
        int spaceCol = location[1];

        if (spaceRow > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        if (spaceRow < size() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        if (spaceCol > 0)               neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        if (spaceCol < size() - 1) neighbors.add(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

        return neighbors;
    }
    
    private int[] blankSpot() {
        for (int row = 0; row < N; row++)
            for (int col = 0; col < N; col++)
                if (isEmpty(node(row, col))) {
                    int[] location = new int[2];
                    location[0] = row;
                    location[1] = col;

                    return location;
                }
        throw new RuntimeException();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(size() + "\n");
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++)
                str.append(String.format("%2d ", node(row, col)));
            str.append("\n");
        }

        return str.toString();
    }
}
