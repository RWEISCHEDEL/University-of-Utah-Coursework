package ttt;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This JUnit test is for the TTTBoard Class that was created for PS7
 * @author Robert Weischedel
 *
 */
public class TTTBoardTest {

	/**
	 * This JUnit test is to see if the TTTBoard correctly builds and sets the XWins, OWins and draw values to 0.
	 */
	@Test
	public void testTTTBoard() {
		TTTBoard ttt = new TTTBoard();
		
		assertEquals(0,ttt.getOWins());
		assertEquals(0,ttt.getXWins());
		assertEquals(0,ttt.getDrawCount());
	}

	/**
	 * This JUnit test is to see if the TTTBoard correctly determines if it is X or O's turn.
	 */
	@Test
	public void testMove() {
		TTTBoard ttt = new TTTBoard();
		
		assertEquals("X",ttt.move(1));
		assertEquals("O",ttt.move(6));
		assertEquals("X",ttt.move(9));
	}
	
	/**
	 * This JUnit test is to see if the exceptions in move are thrown if the right data is entered.  
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMoveExceptions1() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(4);
		ttt.move(10);
		
	}
	
	/**
	 * This JUnit test is to see if the exceptions in move are thrown if the right data is entered. 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMoveExceptions2() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(4);
		ttt.move(0);
		
	}
	
	/**
	 * This JUnit test is to see if the exceptions in move are thrown if the right data is entered. 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMoveExceptions3() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(4);
		ttt.move(4);
		
	}

	/**
	 * This JUnit test is to see if the correct value in in getToMove is returned based on whose turn it is. 
	 */
	@Test
	public void testGetToMove() {
		TTTBoard ttt = new TTTBoard();
		
		assertEquals("X",ttt.getToMove());
		ttt.move(2);
		assertEquals("O",ttt.getToMove());
		ttt.move(3);
		assertEquals("X",ttt.getToMove());
		
	}

	/**
	 * This JUnit test is to see if the game ends in a draw that isDrawn returns correctly. 
	 */
	@Test
	public void testIsDrawn() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(1);
		ttt.move(3);
		ttt.move(2);
		ttt.move(4);
		ttt.move(5);
		ttt.move(8);
		ttt.move(6);
		ttt.move(9);
		ttt.move(7);
		
		assertTrue(ttt.isDrawn());
	}

	/**
	 * This JUnit test is to see if the game ends with a win for X if isWon returns correctly. 
	 */
	@Test
	public void testIsWonX() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(1);
		ttt.move(9);
		ttt.move(2);
		ttt.move(6);
		ttt.move(3);
		
		assertTrue(ttt.isWon());
	}
	
	/**
	 * This JUnit test is to see if the game ends with a win for O if isWon returns correctly. 
	 */
	@Test
	public void testIsWonY() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(9);
		ttt.move(1);
		ttt.move(6);
		ttt.move(2);
		ttt.move(8);
		ttt.move(3);
		
		assertTrue(ttt.isWon());
	}

	/**
	 * This JUnit test is to see if the game board resets correctly, without resetting the scores.
	 */
	@Test
	public void testReset() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(1);
		ttt.move(9);
		ttt.move(2);
		ttt.move(6);
		ttt.move(3);
		
		ttt.reset();
		
		
		ttt.move(9);
		ttt.move(1);
		ttt.move(6);
		ttt.move(2);
		ttt.move(8);
		ttt.move(3);
		
		assertEquals(1,ttt.getXWins());
		assertEquals(1,ttt.getOWins());
	}

	/**
	 * This JUnit test is to see if the game ends with a win for X and it returns the number of X wins.
	 */
	@Test
	public void testGetXWins() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(1);
		ttt.move(9);
		ttt.move(2);
		ttt.move(6);
		ttt.move(3);
		
		assertEquals(1,ttt.getXWins());
	}

	/**
	 * This JUnit test is to see if the game ends with a win for O and it returns the number of O wins.
	 */
	@Test
	public void testGetOWins() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(9);
		ttt.move(1);
		ttt.move(6);
		ttt.move(2);
		ttt.move(8);
		ttt.move(3);
		
		assertEquals(1,ttt.getOWins());
	}

	/**
	 * This JUnit test is to see if the game ends with a draw and it returns the number of draws.
	 */
	@Test
	public void testGetDrawCount() {
		TTTBoard ttt = new TTTBoard();
		
		ttt.move(1);
		ttt.move(3);
		ttt.move(2);
		ttt.move(4);
		ttt.move(5);
		ttt.move(8);
		ttt.move(6);
		ttt.move(9);
		ttt.move(7);
		
		assertEquals(1,ttt.getDrawCount());
	}

}
