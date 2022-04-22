/**
	 * A Connect-4 player that makes moves that would give the highest possible score.
	 * 
	 * @author Daniel Szafir
	 *
	 */
public class GreedyPlayer implements Player
{
	    private static java.util.Random rand = new java.util.Random();
	    
	    int id;
	    int cols;

	    @Override
	    public String name() {
	        return "Gary";
	    }

	    @Override
	    public void init(int id, int msecPerMove, int rows, int cols) {
	    	this.id = id; //id is your player's id, opponent's id is 3-id
	    	this.cols = cols;
	    }

	    @Override
	    public void calcMove(
	        Connect4Board board, int oppMoveCol, Arbitrator arb) 
	        throws TimeUpException {
	        // Make sure there is room to make a move.
	        if (board.isFull()) {
	            throw new Error ("Complaint: The board is full!");
	        }
	        
	        int col = 0;
	        int trueScore = -1000;
	        //Find maximum score from all possible moves
	        for (int i = 0; i < 7; i++) {
	        	if (board.isValidMove(i)) {
	        		board.move(i, id);
		        	int score = calcScore(board, id);
	        		if (score > trueScore) {
	        			trueScore = score;
	        			col = i;
	        		}
	        		board.unmove(i, id);
	        	}
	        }
	        
	        arb.setMove(col);;
	    }
	    
		public int calcScore(Connect4Board board, int id)
		{
			final int rows = board.numRows();
			final int cols = board.numCols();
			int score = 0;
			// Look for horizontal connect-4s.
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c <= cols - 4; c++) {
					if (board.get(r, c + 0) != id) continue;
					if (board.get(r, c + 1) != id) continue;
					if (board.get(r, c + 2) != id) continue;
					if (board.get(r, c + 3) != id) continue;
					score++;
				}
			}
			// Look for vertical connect-4s.
			for (int c = 0; c < cols; c++) {
				for (int r = 0; r <= rows - 4; r++) {
					if (board.get(r + 0, c) != id) continue;
					if (board.get(r + 1, c) != id) continue;
					if (board.get(r + 2, c) != id) continue;
					if (board.get(r + 3, c) != id) continue;
					score++;
				}
			}
			// Look for diagonal connect-4s.
			for (int c = 0; c <= cols - 4; c++) {
				for (int r = 0; r <= rows - 4; r++) {
					if (board.get(r + 0, c + 0) != id) continue;
					if (board.get(r + 1, c + 1) != id) continue;
					if (board.get(r + 2, c + 2) != id) continue;
					if (board.get(r + 3, c + 3) != id) continue;
					score++;
				}
			}
			for (int c = 0; c <= cols - 4; c++) {
				for (int r = rows - 1; r >= 4 - 1; r--) {
					if (board.get(r - 0, c + 0) != id) continue;
					if (board.get(r - 1, c + 1) != id) continue;
					if (board.get(r - 2, c + 2) != id) continue;
					if (board.get(r - 3, c + 3) != id) continue;
					score++;
				}
			}
			return score;
		}
}

