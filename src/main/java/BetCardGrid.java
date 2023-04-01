import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Seyfal Sultanov
 * @version: 3.0
 * @date: 2023-03-15
 *
 * @description:
 *
 * BetCardGrid is a custom GridPane layout designed for betting applications, such as lottery games.
 * It represents a grid of clickable BetButton instances for selecting numbers on the bet card.
 * Each BetButton in the grid displays a unique number, and players can select and deselect numbers
 * by interacting with the buttons.

 * Usage:

 * Create a BetCardGrid with 5 rows and 10 columns
 * BetCardGrid betCardGrid = new BetCardGrid(5, 10);

 * Set the maximum number of spots that can be selected on the bet card
 * betCardGrid.setMaxSpots(5);

 * Create a BorderPane as the root layout and add the BetCardGrid to it
 * BorderPane root = new BorderPane();
 * root.setCenter(betCardGrid);

 * Create a scene, set the root layout, and display the stage
 * Scene scene = new Scene(root, 600, 400);
 * primaryStage.setScene(scene);
 * primaryStage.setTitle("BetCardGrid Example");
 * primaryStage.show();
 *
 */
class BetCardGrid extends GridPane {

    private int maxSpots;
    private int numDraws;

    public BetCardGrid(int rows, int columns) {
        setHgap(12);
        setVgap(12);
        setAlignment(Pos.CENTER);
        int number = 1;

        // Create a BetButton for each row and column
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                BetButton betButton = createBetButton(number);
                add(betButton, j, i);
                number++;
            }
        }
    }

    // Create a BetButton instance and set its action handler
    private BetButton createBetButton(int number) {
        BetButton betButton = new BetButton(number);

        betButton.setOnAction(event -> {
            BetButton.ButtonState currentState = betButton.getState();

            // If the button is unselected and the number of selected buttons is less than the maximum number of spots,
            // then select the button. Otherwise, deselect the button.
            if (currentState == BetButton.ButtonState.UNSELECTED && getSelectedNumbers().size() < maxSpots) {
                betButton.setState(BetButton.ButtonState.SELECTED);
            } else if (currentState == BetButton.ButtonState.SELECTED) {
                betButton.setState(BetButton.ButtonState.UNSELECTED);
            }
        });

        return betButton;
    }

    // Get the BetButton instance with the specified number
    public BetButton getBetButton(int number) {
        // Iterate through all the nodes in the grid and return the BetButton with the specified number
        for (Node node : getChildren()) {
            if (node instanceof BetButton) {
                BetButton betButton = (BetButton) node;
                if (betButton.getNumber() == number) {
                    return betButton;
                }
            }
        }
        return null;
    }

    // Get the numbers of all the selected buttons
    public void setMaxSpots(int maxSpots) {
        this.maxSpots = maxSpots;
    }

    // Get the numbers of all the selected buttons
    public void setNumDraws(int newValue)
    {
        this.numDraws = newValue;
    }

    // Get the numbers of all the selected buttons
    public void resetButtons() {
        // Iterate through all the nodes in the grid and reset the state of each button
        for (Node node : getChildren()) {
            if (node instanceof BetButton) {
                BetButton betButton = (BetButton) node;
                betButton.setState(BetButton.ButtonState.UNSELECTED);
            }
        }
    }

    // Update the state of each button based on the numbers that were drawn and the numbers that were selected
    public void updateButtons(List<Integer> drawnNumbers, List<Integer> selectedNumbers) {
        // Iterate through all the nodes in the grid and update the state of each button
        for (Node node : getChildren()) {
            if (node instanceof BetButton) {
                BetButton betButton = (BetButton) node;
                int number = betButton.getNumber();

                if (drawnNumbers.contains(number) && selectedNumbers.contains(number)) {
                    betButton.setState(BetButton.ButtonState.CORRECT);
                } else if (drawnNumbers.contains(number) && !selectedNumbers.contains(number)) {
                    betButton.setState(BetButton.ButtonState.DRAWN);
                } else if (selectedNumbers.contains(number) && !drawnNumbers.contains(number)) {
                    betButton.setState(BetButton.ButtonState.INCORRECT);
                } else {
                    betButton.setState(BetButton.ButtonState.UNSELECTED);
                }
            }
        }
    }

    // Enable or disable all the buttons in the grid
    public void enableButtons(boolean enable) {
        // Iterate through all the nodes in the grid and enable or disable each button
        for (Node node : getChildren()) {
            if (node instanceof BetButton) {
                BetButton betButton = (BetButton) node;
                betButton.setDisable(!enable);
            }
        }
    }

    // Get the numbers of all the selected buttons
    public List<Integer> getSelectedNumbers() {
        List<Integer> selectedNumbers = new ArrayList<>();
        // Iterate through all the nodes in the grid and add the number of each selected button to the list
        for (Node node : getChildren()) {
            if (node instanceof BetButton) {
                BetButton betButton = (BetButton) node;
                if (betButton.getState() == BetButton.ButtonState.SELECTED) {
                    selectedNumbers.add(betButton.getNumber());
                }
            }
        }
        return selectedNumbers;
    }

}