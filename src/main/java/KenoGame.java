import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: Seyfal Sultanov
 * @author: Mykola Turchak
 * @version: 2.0
 * @date: 2023-03-15
 *
 * @description: A class representing a Keno game with functionality to play drawings,
 * calculate winnings, and manage game state.
 */
public class KenoGame {
    private int numDrawings;
    private int numSpots;
    private int totalWinnings;
    private List<Integer> drawnNumbers;

    /**
     * Constructor for KenoGame
     */
    public KenoGame() {
        numDrawings = 0;
        numSpots = 0;
        totalWinnings = 0;
        drawnNumbers = new ArrayList<>();
    }

    /**
     * Clears the list and generates a list of 20 random numbers between 1 and 80 and
     * stores them in the drawnNumbers list.
     * @return the number of numbers matched between the selected numbers and the drawn numbers.
     * @param selectedNumbers the list of numbers selected by the player.
     */
    public int playDrawing(List<Integer> selectedNumbers) {
        drawnNumbers.clear();
        drawNumbers();
        return calculateMatchedNumbers(selectedNumbers);
    }

    /**
     * Generates a list of 20 random numbers between 1 and 80 and stores them in the drawnNumbers list.
     */
    private void drawNumbers() {
        List<Integer> possibleNumbers = IntStream.rangeClosed(1, 80).boxed().collect(Collectors.toList());
        Collections.shuffle(possibleNumbers, new Random());
        drawnNumbers = possibleNumbers.subList(0, 20);
    }

    /**
     * Calculates the winnings based on the number of numbers matched between the selected numbers and the drawn numbers.
     * @return the winnings.
     * @param matchedNumbers the number of numbers matched between the selected numbers and the drawn numbers.
     */
    public int calculateWinnings(int matchedNumbers) {
        int winnings = 0;

        if (numSpots == 1) {
            if (matchedNumbers == 1) {
                winnings += 2;
            }
        } else if (numSpots == 4) {
            if (matchedNumbers == 2) {
                winnings += 1;
            } else if (matchedNumbers == 3) {
                winnings += 5;
            } else if (matchedNumbers == 4) {
                winnings += 75;
            }
        } else if (numSpots == 8) {
            if (matchedNumbers == 4) {
                winnings += 2;
            } else if (matchedNumbers == 5) {
                winnings += 12;
            } else if (matchedNumbers == 6) {
                winnings += 50;
            } else if (matchedNumbers == 7) {
                winnings += 750;
            } else if (matchedNumbers == 8) {
                winnings += 10000;
            }
        } else if (numSpots == 10) {
            if (matchedNumbers == 0) {
                winnings += 5;
            }else if (matchedNumbers == 5) {
                winnings += 2;
            } else if (matchedNumbers == 6) {
                winnings += 15;
            } else if (matchedNumbers == 7) {
                winnings += 40;
            } else if (matchedNumbers == 8) {
                winnings += 450;
            } else if (matchedNumbers == 9) {
                winnings += 4250;
            } else if (matchedNumbers == 10) {
                winnings += 100000;
            }
        }

        return winnings;
    }

    /**
     * Calculates the number of numbers matched between the selected numbers and the drawn numbers.
     * @return the number of numbers matched between the selected numbers and the drawn numbers.
     * @param selectedNumbers the list of numbers selected by the player.
     */
    public int calculateMatchedNumbers(List<Integer> selectedNumbers) {
        int matchedNumbers = 0;
        for (Integer number : selectedNumbers) {
            if (drawnNumbers.contains(number)) {
                matchedNumbers++;
            }
        }
        return matchedNumbers;
    }


    /**
     * Getters
     */
    public int getNumDraws() {
        return numDrawings;
    }

    public int getNumSpots() {
        return numSpots;
    }

    public int getTotalWinnings() {
        return totalWinnings;
    }

    public List<Integer> getDrawnNumbers() {
        return drawnNumbers;
    }

    /**
     * Setters
     */
    public void setDrawnNumbers(List<Integer> drawnNumbers) {
        this.drawnNumbers = drawnNumbers;
    }
    public void setNumDraws(int numDraws) {
        this.numDrawings = numDraws;
    }

    public void setNumSpots(int numSpots) {
        this.numSpots = numSpots;
    }

    public void setTotalWinnings(int totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    public void resetGame() {
        this.numDrawings = 0;
        this.numSpots = 0;
        this.drawnNumbers.clear();
    }
}