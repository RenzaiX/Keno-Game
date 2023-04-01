import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * @author: Mykola Turchak
 * @date: 2023-03-19
 *
 * @description: A class containing test cases
 */

public class MyTest {
	private KenoGame kenoGame;

	@BeforeEach
	public void setUp() {
		kenoGame = new KenoGame();
	}


	@Test
	@DisplayName("Test playDrawing")
	public void testPlayDrawing() {
		kenoGame.playDrawing(Arrays.asList(1, 2, 3, 4, 5));
		assertEquals(20, kenoGame.getDrawnNumbers().size());
	}


	@Test
	@DisplayName("Test calculateWinnings for numSpots = 1")
	public void testCalculateWinnings_numSpots1() {
		kenoGame.setNumSpots(1);

		// Test matched numbers = 0
		int winnings = kenoGame.calculateWinnings(0);
		assertEquals(0, winnings);

		// Test matched numbers = 1
		winnings = kenoGame.calculateWinnings(1);
		assertEquals(2, winnings);

		// Test matched numbers > 1
		winnings = kenoGame.calculateWinnings(2);
		assertEquals(0, winnings);
	}

	@Test
	@DisplayName("Test calculateWinnings for numSpots = 4")
	public void testCalculateWinnings_numSpots4() {
		kenoGame.setNumSpots(4);

		// Test matched numbers = 0
		int winnings = kenoGame.calculateWinnings(0);
		assertEquals(0, winnings);

		// Test matched numbers = 2
		winnings = kenoGame.calculateWinnings(2);
		assertEquals(1, winnings);

		// Test matched numbers = 3
		winnings = kenoGame.calculateWinnings(3);
		assertEquals(5, winnings);

		// Test matched numbers = 4
		winnings = kenoGame.calculateWinnings(4);
		assertEquals(75, winnings);

		// Test matched numbers > 4
		winnings = kenoGame.calculateWinnings(5);
		assertEquals(0, winnings);
	}

	@Test
	@DisplayName("Test calculateWinnings for numSpots = 8")
	public void testCalculateWinnings_numSpots8() {
		kenoGame.setNumSpots(8);

		// Test matched numbers = 0
		int winnings = kenoGame.calculateWinnings(0);
		assertEquals(0, winnings);

		// Test matched numbers = 4
		winnings = kenoGame.calculateWinnings(4);
		assertEquals(2, winnings);

		// Test matched numbers = 5
		winnings = kenoGame.calculateWinnings(5);
		assertEquals(12, winnings);

		// Test matched numbers = 6
		winnings = kenoGame.calculateWinnings(6);
		assertEquals(50, winnings);

		// Test matched numbers = 7
		winnings = kenoGame.calculateWinnings(7);
		assertEquals(750, winnings);

		// Test matched numbers = 8
		winnings = kenoGame.calculateWinnings(8);
		assertEquals(10000, winnings);

		// Test matched numbers > 8
		winnings = kenoGame.calculateWinnings(9);
		assertEquals(0, winnings);
	}

	@Test
	@DisplayName("Test calculateWinnings for numSpots = 10")
	public void testCalculateWinnings_numSpots10() {
		kenoGame.setNumSpots(10);

		// Test winnings for matching 0 numbers
		assertEquals(5, kenoGame.calculateWinnings(0));

		// Test winnings for matching 5 numbers
		assertEquals(2, kenoGame.calculateWinnings(5));

		// Test winnings for matching 6 numbers
		assertEquals(15, kenoGame.calculateWinnings(6));

		// Test winnings for matching 7 numbers
		assertEquals(40, kenoGame.calculateWinnings(7));

		// Test winnings for matching 8 numbers
		assertEquals(450, kenoGame.calculateWinnings(8));

		// Test winnings for matching 9 numbers
		assertEquals(4250, kenoGame.calculateWinnings(9));

		// Test winnings for matching 10 numbers
		assertEquals(100000, kenoGame.calculateWinnings(10));
	}

	@Test
	@DisplayName("Test default values")
	public void testDefaultValues() {
		assertEquals(0, kenoGame.getNumDraws());
		assertEquals(0, kenoGame.getNumSpots());
		assertEquals(0, kenoGame.getTotalWinnings());
		assertEquals(0, kenoGame.getDrawnNumbers().size());
	}
	@Test
	@DisplayName("Test resetGame")
	public void testResetGame() {
		kenoGame.setNumDraws(5);
		kenoGame.setNumSpots(6);
		kenoGame.setTotalWinnings(100);
		kenoGame.playDrawing(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());
		assertEquals(0, kenoGame.getNumSpots());
		assertEquals(0, kenoGame.getTotalWinnings());
		assertEquals(0, kenoGame.getDrawnNumbers().size());
	}

	@Test
	@DisplayName("Test calculateMatchedNumbers no matched numbers")
	public void testCalculateMatchedNumbersNone() {
		// Test no matched numbers
		kenoGame.setDrawnNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		int matchedNumbers = kenoGame.calculateMatchedNumbers(Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
		assertEquals(0, matchedNumbers);
	}

	@Test
	@DisplayName("Test calculateMatchedNumbers 1 matched number")
	public void testCalculateMatchedNumbers1() {
		// Test 1 matched number
		kenoGame.setDrawnNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		int matchedNumbers = kenoGame.calculateMatchedNumbers(Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 1));
		assertEquals(1, matchedNumbers);
	}

	@Test
	@DisplayName("Test calculateMatchedNumbers all matched numbers")
	public void testCalculateMatchedNumbersAll()
	{
		// Test all matched numbers
		kenoGame.setDrawnNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		int matchedNumbers = kenoGame.calculateMatchedNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		assertEquals(10, matchedNumbers);
	}

	@Test
	@DisplayName("Test getNumDraws for numDraws = 1")
	public void testGetNumDraws1() {
		kenoGame.setNumDraws(1);
		assertEquals(1, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());
	}

	@Test
	@DisplayName("Test getNumDraws for numDraws = 2")
	public void testGetNumDraws2() {
		kenoGame.setNumDraws(2);
		assertEquals(2, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());
	}

	@Test
	@DisplayName("Test getNumDraws for numDraws = 3")
	public void testGetNumDraws3() {
		kenoGame.setNumDraws(3);
		assertEquals(3, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());
	}

	@Test
	@DisplayName("Test getNumDraws for numDraws = 4")
	public void testGetNumDraws4() {
		kenoGame.setNumDraws(4);
		assertEquals(4, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());
	}

	@Test
	@DisplayName("Test getNumSpots for numSpots = 1")
	public void testGetNumSpots() {
		kenoGame.setNumSpots(1);
		assertEquals(1, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());
	}

	@Test
	@DisplayName("Test getNumSpots for numSpots = 4")
	public void testGetNumSpots4() {
		kenoGame.setNumSpots(4);
		assertEquals(4, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());
	}

	@Test
	@DisplayName("Test getNumSpots for numSpots = 8")
	public void testGetNumSpots8() {
		kenoGame.setNumSpots(8);
		assertEquals(8, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());
	}

	@Test
	@DisplayName("Test getNumSpots for numSpots = 10")
	public void testGetNumSpots10() {
		kenoGame.setNumSpots(10);
		assertEquals(10, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());
	}


	@Test
	@DisplayName("Test getTotalWinnings")
	public void testGetTotalWinnings() {
		for(int i = 0; i < 10; i++) {
			kenoGame.setTotalWinnings(i);
			assertEquals(i, kenoGame.getTotalWinnings());
		}
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getTotalWinnings());



	}

	@Test
	@DisplayName("Test getDrawnNumbers")
	public void testGetDrawnNumbers() {
		kenoGame.setDrawnNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		assertEquals(10, kenoGame.getDrawnNumbers().size());
		assertEquals(1, kenoGame.getDrawnNumbers().get(0).intValue());
		assertEquals(2, kenoGame.getDrawnNumbers().get(1).intValue());
		assertEquals(3, kenoGame.getDrawnNumbers().get(2).intValue());
		assertEquals(4, kenoGame.getDrawnNumbers().get(3).intValue());
		assertEquals(5, kenoGame.getDrawnNumbers().get(4).intValue());
		assertEquals(6, kenoGame.getDrawnNumbers().get(5).intValue());
		assertEquals(7, kenoGame.getDrawnNumbers().get(6).intValue());
		assertEquals(8, kenoGame.getDrawnNumbers().get(7).intValue());
		assertEquals(9, kenoGame.getDrawnNumbers().get(8).intValue());
		assertEquals(10, kenoGame.getDrawnNumbers().get(9).intValue());
	}

	@Test
	@DisplayName("Test setNumDraws for numDraws = 1")
	public void testSetNumDraws1(){
		kenoGame.setNumDraws(1);
		assertEquals(1, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());

	}

	@Test
	@DisplayName("Test setNumDraws for numDraws = 2")
	public void testSetNumDraws2(){
		kenoGame.setNumDraws(2);
		assertEquals(2, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());

	}

	@Test
	@DisplayName("Test setNumDraws for numDraws = 3")
	public void testSetNumDraws3(){
		kenoGame.setNumDraws(3);
		assertEquals(3, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());

	}

	@Test
	@DisplayName("Test setNumDraws for numDraws = 4")
	public void testSetNumDraws4(){
		kenoGame.setNumDraws(4);
		assertEquals(4, kenoGame.getNumDraws());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumDraws());

	}

	@Test
	@DisplayName("Test setNumDraws for numSpots = 1")
	public void testSetNumSpots1() {
		kenoGame.setNumSpots(1);
		assertEquals(1, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());

	}

	@Test
	@DisplayName("Test setNumDraws for numSpots = 4")
	public void testSetNumSpots4() {
		kenoGame.setNumSpots(4);
		assertEquals(4, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());

	}

	@Test
	@DisplayName("Test setNumDraws for numSpots = 8")
	public void testSetNumSpots8() {
		kenoGame.setNumSpots(8);
		assertEquals(8, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());

	}

	@Test
	@DisplayName("Test setNumDraws for numSpots = 10")
	public void testSetNumSpots10() {
		kenoGame.setNumSpots(10);
		assertEquals(10, kenoGame.getNumSpots());
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getNumSpots());

	}

	@Test
	@DisplayName("Test setTotalWinnings")
	public void testSetTotalWinnings() {
		for (int i = 0; i < 10; i++) {
			kenoGame.setTotalWinnings(i);
			assertEquals(i, kenoGame.getTotalWinnings());
		}
		kenoGame.resetGame();
		assertEquals(0, kenoGame.getTotalWinnings());
	}

	@Test
	@DisplayName("Test setDrawnNumbers")
	public void testSetDrawnNumbers() {
		kenoGame.setDrawnNumbers(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		assertEquals(10, kenoGame.getDrawnNumbers().size());
		assertEquals(1, kenoGame.getDrawnNumbers().get(0).intValue());
		assertEquals(2, kenoGame.getDrawnNumbers().get(1).intValue());
		assertEquals(3, kenoGame.getDrawnNumbers().get(2).intValue());
		assertEquals(4, kenoGame.getDrawnNumbers().get(3).intValue());
		assertEquals(5, kenoGame.getDrawnNumbers().get(4).intValue());
		assertEquals(6, kenoGame.getDrawnNumbers().get(5).intValue());
		assertEquals(7, kenoGame.getDrawnNumbers().get(6).intValue());
		assertEquals(8, kenoGame.getDrawnNumbers().get(7).intValue());
		assertEquals(9, kenoGame.getDrawnNumbers().get(8).intValue());
		assertEquals(10, kenoGame.getDrawnNumbers().get(9).intValue());
	}
}