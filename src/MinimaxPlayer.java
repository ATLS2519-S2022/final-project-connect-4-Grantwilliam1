/**
 * A Connect-4 player that uses a minimax method to solve play the game.
 * 
 * @author Grant Young
 *
 */
public class MinimaxPlayer implements Player
{
    private static java.util.Random rand = new java.util.Random();
    
    int id;
    int opponent_id;
    int cols;

    @Override
    public String name() {
        return "Michael Maxwell";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; //id is your player's id, opponent's id is 3-id
    	opponent_id = 3-id;
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
        
        int move = 0;
        int maxDepth = 1;
        int newScore = 0;
        
        //while there is time remaining and current search depth is <= the number of moves remaining
        while (!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
        	//run the minimax search and set move to be the column corresponding to the best score
        	int bestScore = -10000;
    		for (int i = 0; i < 7; i++) {
    			if (board.isValidMove(i)) {
	    			board.move(i,id);
		        	newScore = minimax(board, maxDepth -1, false, arb);
		        	if (newScore > bestScore) {
		        		bestScore = newScore;
		        		move = i;
		        		}
		        	board.unmove(i,id);
    				}
    		}
        	maxDepth++;
        	arb.setMove(move);
        }
    }
    
    public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb)
    {
    	if (depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board,id) - calcScore(board,opponent_id);
    	}
    	if (isMaximizing) {
    		int bestScore = -10000;
    		for (int i = 0; i < 7; i++) {
    			if (board.isValidMove(i)) {
	    			board.move(i,id);
		        	bestScore = Math.max(bestScore, minimax(board, depth -1, false, arb));
    				
		        	board.unmove(i,id);
    			}
    		}
    		return bestScore;
    	}
    	else {
    		int bestScore = 10000;
    		for (int i = 0; i < 7; i++) {
    			if (board.isValidMove(i)) {
	    			board.move(i,opponent_id);
	    			bestScore = Math.min(bestScore, minimax(board, depth -1, true, arb));
    				
	    			board.unmove(i,opponent_id);
    			}
    		}
    		return bestScore;
    	}
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