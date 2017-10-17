package ttt;

import java.util.ArrayList;

/**
 * @author Robert Weischedel
 * Represents the state of a tic-tac-toe board. This specification assumes that
 * you know how to play tic-tac-toe. Consult Wikipedia if you don't.
 * 
 * A tic-tac-toe board consists of nine squares, numbered like this:
 * 
 * <pre>
 *  1 | 2 | 3 
 * ---+---+--- 
 *  4 | 5 | 6
 * ---+---+--- 
 *  7 | 8 | 9
 * </pre>
 * 
 * Each square can be unoccupied, contain an X, or contain an O.
 * 
 * Beginning from an empty board, the players take turns moving until X wins, O
 * wins, or there is a draw. X always moves first.
 * 
 * In addition, a tic-tac-toe board knows how many times X has won, how many
 * times O has won, and how many times there has been a draw.
 */
public class TTTBoard
{
	// Private Variables
	private int position;
	private int xWins;
	private int oWins;
	private int draw;
	private ArrayList<Integer> filledSquares;
	
    /**
     * Constructs an empty board in which X has won zero times, O has won zero
     * times, and there have been no draws.
     */
    public TTTBoard ()
    {
    	// Set all values to 0. And fill ArrayList with appropriate square values.
    	xWins = 0;
    	oWins = 0;
    	draw = 0;
    	position = 1;
    	filledSquares = new ArrayList<Integer>();
    	for(int i = 1; i <= 9; i++){
    		filledSquares.add(i);
    	}
    	
    }

    /**
     * If the current position is a win for X, a win for O, or a draw, throws an
     * IllegalArgumentException.
     * 
     * If the specified square is already occupied, throws an
     * IllegalArgumentException.
     * 
     * If square is invalid (less than 1 or greater than 9), throws an
     * IllegalArgumentException.
     * 
     * Otherwise, if it is X's turn to move, records an X move to the specified
     * square and returns "X". If it is O's turn to move, records an O move to
     * the specified square and returns "O". If the move makes the position a
     * win for X, the "wins for X" count is incremented. If the move makes the
     * position a win for O, the "wins for O" count is incremented. If the move
     * makes the position a draw, the "draws" count is incremented.
     */
    public String move (int square)
    {
    	// If the board is a draw or X or O wins, throw an exception. 
		if(isDrawn() || isWon()){
			throw new IllegalArgumentException();
		}
    	
		// If the square is not within the acceptable range, throw an exception. 
    	if(square < 1 || square > 9){
    		throw new IllegalArgumentException();
    	}
    	
    	// Check the ArrayList to see if the square has already been used.
    	if(!filledSquares.contains(square)){
    		throw new IllegalArgumentException();
    	}
    	
    	// This set of code deals with X.
    	if(getToMove().equals("X")){ 
    		
    		position++;
    		
    		// Put a -1 in the ArrayList location where the square was just used.
    		switch(square){
    		
    		case 1: 
    			filledSquares.set(0, -1);
    			break;
    			
    		case 2:
    			filledSquares.set(1, -1);
    			break;
    			
    		case 3:
    			filledSquares.set(2, -1);
    			break;
    			
    		case 4:
    			filledSquares.set(3, -1);
    			break;
    			
    		case 5:
    			filledSquares.set(4, -1);
    			break;
    			
    		case 6:
    			filledSquares.set(5, -1);
    			break;
    			
    		case 7:
    			filledSquares.set(6, -1);
    			break;
    			
    		case 8:
    			filledSquares.set(7, -1);
    			break;

    		case 9:
    			filledSquares.set(8, -1);
    			break;
    			
    		default:
    			throw new IllegalArgumentException();
    				
    		}
    		
    		// Check and see if X has won or there is a draw. 
			if(isWon()){
				System.out.println("X Win");
				xWins++;
			}
			else if(isDrawn()){
				draw++;
			}
			
    		return "X";
    	}
    	
    	
    	// This set of code deals with O.
    	else {
    		position++;
    		
    		// Put a 0 in the ArrayList location where the square was just used.
    		switch(square){
    		
    		case 1: 
    			filledSquares.set(0, 0);
    			break;
    			
    		case 2:
    			filledSquares.set(1, 0);
    			break;
    			
    		case 3:
    			filledSquares.set(2, 0);
    			break;
    			
    		case 4:
    			filledSquares.set(3, 0);
    			break;
    			
    		case 5:
    			filledSquares.set(4, 0);
    			break;
    			
    		case 6:
    			filledSquares.set(5, 0);
    			break;
    			
    		case 7:
    			filledSquares.set(6, 0);
    			break;
    			
    		case 8:
    			filledSquares.set(7, 0);
    			break;

    		case 9:
    			filledSquares.set(8, 0);
    			break;
    			
    		default:
    			throw new IllegalArgumentException();
    		}
    		
    		// Check and see if O has won or there is a draw. 
			if (isWon()){
				oWins++;
			}
			else if(isDrawn()){
				draw++;
			}
			
    		return "O";
    		
    		
    	}	
    	
    }

    /**
     * Returns "X" (if it is X's turn to move) or "O" (otherwise).
     */
    public String getToMove ()
    {  
    	// If position is odd. it is X turn.
    	if(position % 2 != 0){
    		return "X";
    	}
    	
    	else{
        return "O";
    	}
    }

    /**
     * Reports whether or not the board has a drawn position (all squares filled
     * in, neither X nor O has three in a row).
     */
    public boolean isDrawn ()
    {
    	if(position == 10 && isWon() != true){
    		return true;	
    	}
        return false;
    }

    /**
     * Reports whether or not the board has a won position (either X or O has
     * three in a row).
     */
    public boolean isWon ()
    {
    	System.out.println("Entered is Won");
    	
    	// All senarios for a X Win.
    	if(filledSquares.get(0).equals(-1) && filledSquares.get(1).equals(-1) && filledSquares.get(2).equals(-1)){
    		return true;
    	}
    	
    	if(filledSquares.get(3).equals(-1) && filledSquares.get(4).equals(-1) && filledSquares.get(5).equals(-1)){
    		return true;
    	}
    	
    	if(filledSquares.get(6).equals(-1) && filledSquares.get(7).equals(-1) && filledSquares.get(8).equals(-1)){
    		return true;
    	}
    	
    	if(filledSquares.get(0).equals(-1) && filledSquares.get(3).equals(-1) && filledSquares.get(6).equals(-1)){
    		return true;
    	}
    	
    	if(filledSquares.get(1).equals(-1) && filledSquares.get(4).equals(-1) && filledSquares.get(7).equals(-1)){
    		return true;
    	}
    	
    	System.out.println("Check");
    	if(filledSquares.get(2).equals(-1) && filledSquares.get(5).equals(-1) && filledSquares.get(8).equals(-1)){
    		System.out.println("Win");
    		return true;
    	}
    	
    	if(filledSquares.get(0).equals(-1) && filledSquares.get(4).equals(-1) && filledSquares.get(8).equals(-1)){
    		return true;
    	}
    	
    	if(filledSquares.get(2).equals(-1) && filledSquares.get(4).equals(-1) && filledSquares.get(6).equals(-1)){
    		return true;
    	}
    	
    	
    	// All senario for a O Win
    	if(filledSquares.get(0).equals(0) && filledSquares.get(1).equals(0) && filledSquares.get(2).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(3).equals(0) && filledSquares.get(4).equals(0) && filledSquares.get(5).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(6).equals(0) && filledSquares.get(7).equals(0) && filledSquares.get(8).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(0).equals(0) && filledSquares.get(3).equals(0) && filledSquares.get(6).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(1).equals(0) && filledSquares.get(4).equals(0) && filledSquares.get(7).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(2).equals(0) && filledSquares.get(5).equals(0) && filledSquares.get(8).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(0).equals(0) && filledSquares.get(4).equals(0) && filledSquares.get(8).equals(0)){
    		return true;
    	}
    	
    	if(filledSquares.get(2).equals(0) && filledSquares.get(4).equals(0) && filledSquares.get(6).equals(0)){
    		return true;
    	}
    	
        return false;
    	
    }

    /**
     * Resets the board so that a fresh game can be played. Does not modify the
     * scoring records.
     */
    public void reset ()
    {
    	position = 1;

    	filledSquares.clear();
    	
    	for(int i = 1; i <= 9; i++){
    		filledSquares.add(i);
    		
    	}
    }

    /**
     * Returns the number of games that X has won.
     */
    public int getXWins ()
    {
        return xWins;
    }

    /**
     * Returns the number of games that O has won.
     */
    public int getOWins ()
    {
        return oWins;
    }

    /**
     * Returns the number of games that have been drawn.
     */
    public int getDrawCount ()
    {
        return draw;
    }
}
