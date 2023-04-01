import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Mykola Turchak
 * @version: 1.0
 * @date: 2023-03-15
 *
 * @description: This class is used to create a grid for the drawn numbers.
 */

class DrawingGrid extends GridPane {

    private int maxSpots;
    private int numDraws;

    // Constructor
    public DrawingGrid(List<Integer> drawnNumbers ) {
        setHgap(12);
        setVgap(12);
        setAlignment(Pos.CENTER);

        // Create a BetButton for each row and column
        for (int i = 0; i < 20; i++) {
            BetButton betButton = createBetButton(drawnNumbers.get(i));
            add(betButton, i, 0);
        }
    }

    // create the buttons for the grid
    private BetButton createBetButton(int number) {
        BetButton betButton = new BetButton(number);

        // make them clickable
        betButton.setOnAction(event -> {
            BetButton.ButtonState currentState = betButton.getState();
            // if the button is not selected and the number of spots is less than the max spots
            // then set the button to selected
            if (currentState == BetButton.ButtonState.UNSELECTED && getNumbers().size() < maxSpots) {
                betButton.setState(BetButton.ButtonState.SELECTED);
            } else if (currentState == BetButton.ButtonState.SELECTED) {
                betButton.setState(BetButton.ButtonState.UNSELECTED);
            }
        });

        return betButton;
    }

    // get the numbers from the grid
    public List<Integer> getNumbers() {
        List<Integer> Numbers = new ArrayList<>();
        // Iterate through all the nodes in the grid and add the number of each selected button to the list
        for (Node node : getChildren()) {
            if (node instanceof BetButton) {
                BetButton betButton = (BetButton) node;
                Numbers.add(betButton.getNumber());
            }
        }
        return Numbers;
    }


}
