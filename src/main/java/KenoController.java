// JavaFX animation imports
import javafx.animation.*;

// JavaFX application imports
import javafx.application.Platform;

// JavaFX beans property imports
import javafx.beans.property.SimpleObjectProperty;

// JavaFX geometry imports
import javafx.geometry.Insets;
import javafx.geometry.Pos;

// JavaFX scene control imports
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

// JavaFX layout imports
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// JavaFX node imports
import javafx.scene.Node;

// JavaFX utility imports
import javafx.util.Duration;

// Java utility imports
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * @author:  Seyfal Sultanov
 * @author:  Mykola Turchak
 * @version: 2.0
 * @date:    2023-03-15
 *
 * @description:
 *
 * The KenoController class is responsible for managing the user interface of a Keno game application,
 * and it interacts with other classes to control the game's logic and visuals. Here is a bird's-eye
 * view of the class and its interactions:
 *
 * @1 KenoController initializes and sets up the Keno game's user interface by calling the
 *    initializeKenoUI(BorderPane root, KenoGame kenoGame) method. It creates and positions
 *    various UI components (such as the bet card grid, winnings column, buttons for spots
 *    and draws, and the auto-play feature) within the main BorderPane layout provided as the
 *    root parameter. It uses the KenoGame object passed as the kenoGame parameter to manage the game's logic.
 *
 * @2 The class interacts with the BetCardGrid class, responsible for managing the grid of buttons
 *    that players use to place bets. The controller configures and controls the state of the bet card grid,
 *    including enabling and disabling buttons, setting the maximum number of spots, and resetting the grid when necessary.
 *
 * @3 KenoController uses the KenoGame object to manage the game's logic, such as the number of spots selected by the player,
 *    the number of draws, and calculating the winnings based on matched numbers.
 *
 * @4 The class employs various helper methods to create different UI components, including createWinningsColumn(),
 *    createButtonLayout(KenoGame kenoGame), createAutoPlayBox(KenoGame kenoGame), and
 *    createButtonBlock(String title, int[][] numbers, KenoGame kenoGame). These methods are called
 *    internally to set up the interface and should not be used directly by users.
 *
 * @5 KenoController utilizes property listeners to react to changes in the number of selected spots and draws.
 *    When the number of selected spots changes, the listener sets the maximum spots for the BetCardGrid
 *    and resets the grid. When the number of selected draws changes, the listener can be used to perform
 *    additional actions if necessary.
 *
 * @Summary In summary, the KenoController class serves as a bridge between the user interface and the game's logic.
 * It manages the UI components and interacts with the KenoGame and BetCardGrid classes to control the game's
 * flow and state.
 */

public class KenoController {

    // Declare BetCardGrid object that will represent the bet card grid on the UI
    private BetCardGrid betCardGrid;

    // Define selectedSpots property which will store the number of spots currently selected
    private final SimpleObjectProperty<Integer> selectedSpots = new SimpleObjectProperty<>();

    // Define selectedDraws property which will store the number of draws currently selected
    private final SimpleObjectProperty<Integer> selectedDraws = new SimpleObjectProperty<>();

    // Create a list to store the winnings labels displayed in the UI
    private final List<Label> winningsLabels = new ArrayList<>();

    // Create a list to store the spots labels displayed in the UI
    private final List<Label> spotsLabels = new ArrayList<>();

    // Create a custom text field to display the current game winnings
    CustomTextField gameWinningsBlock = new CustomTextField("Game: $0");

    // Create a custom text field to display the total winnings
    CustomTextField totalWinningsBlock = new CustomTextField("total: $0");

    // Create an HBox to hold the numbers drawn during the game
    HBox numberBox = new HBox();

    CustomButton resetButton = new CustomButton("Reset");

    // Create an "Auto" button for the automatic selection of numbers
    Button autoButton = new CustomButton("Auto");

    // Create a "Play" button for starting the game
    Button playButton = new CustomButton("Play");

    // Declare a variable to store the winnings from the current game
    int gameWinnings = 0;


    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Initializes the Keno user interface by setting up the UI elements and adding them to the provided BorderPane.
     * This method creates the main layout, top layout, button layout, bet card grid, winnings column, and number box.
     * It also sets the properties and styles for the UI elements and binds the height of the winnings column to the bet card grid.
     *
     * @param root     The BorderPane to which the UI elements will be added.
     * @param kenoGame The KenoGame object used for game logic.
     */
    public void initializeKenoUI(BorderPane root, KenoGame kenoGame) {
        // Create the bet card grid
        betCardGrid = new BetCardGrid(8, 10);

        // Create the winnings column
        VBox winningsColumn = createWinningsColumn();
        root.setLeft(winningsColumn);

        // Bind the height of the winnings column to the bet card grid
        winningsColumn.prefHeightProperty().bind(betCardGrid.heightProperty());

        // Create the top layout
        HBox topLayout = new HBox(20);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.getChildren().addAll(winningsColumn, betCardGrid);

        // Create the button layout
        HBox buttonLayout = createButtonLayout(kenoGame);

        // Configure the number box
        numberBox.setPrefWidth(350);
        numberBox.setPrefHeight(50);
        numberBox.setPadding(new Insets(10));
        numberBox.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-width: 2px;");

        // Create and configure the main layout
        VBox mainLayout = new VBox(30);
        mainLayout.getChildren().addAll(topLayout, numberBox, buttonLayout, playButton);
        // add reset button
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        // Add the main layout to the root
        root.setCenter(mainLayout);
    }

    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Creates and returns a VBox containing the winnings column for the Keno game interface.
     * The column displays the number of spots and their corresponding prizes.

     * The winnings column consists of a VBox container with 11 rows, each containing an HBox.
     * The first row contains the headers "Spots" and "Prize," and the remaining 10 rows display
     * the spots and prizes. The spots labels and winnings labels are stored in separate lists for
     * further use in the application.
     *
     * @return A VBox containing the configured winnings column.
     */
    private VBox createWinningsColumn() {
        // Create a VBox container for the winnings column
        VBox winningsColumn = new VBox(10);
        winningsColumn.setPrefWidth(240);
        winningsColumn.setPrefHeight(1000);
        winningsColumn.setSpacing(10);
        winningsColumn.setPadding(new Insets(10, 10, 10, 10));
        winningsColumn.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-width: 2px;");

        // Create a GridPane container for the chart
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(13);

        for (int i = 1; i <= 11; i++) {
            // Create and style the spots label
            Label spotsLabel;
            if (i == 1) {
                spotsLabel = new Label("Spots");
                spotsLabel.setUnderline(true);
            } else {
                spotsLabel = new Label();
                spotsLabel.setUnderline(false);
            }
            spotsLabel.setStyle("-fx-font-size: 18;-fx-font-weight: bold; -fx-text-fill: #333;");

            // Create and style the winnings label
            Label winningsLabel;
            if (i == 1) {
                winningsLabel = new Label("Prize");
                winningsLabel.setUnderline(true);
            } else {
                winningsLabel = new Label();
                winningsLabel.setUnderline(false);
            }
            winningsLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #333;");

            // Add the spots and winnings labels to the GridPane
            GridPane.setConstraints(spotsLabel, 0, i - 1);
            GridPane.setConstraints(winningsLabel, 1, i - 1);
            gridPane.getChildren().addAll(spotsLabel, winningsLabel);

            // Store the labels in their respective lists
            winningsLabels.add(winningsLabel);
            spotsLabels.add(spotsLabel);
        }

        // Add the GridPane to the winnings column
        winningsColumn.getChildren().add(gridPane);

        return winningsColumn;
    }


    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Creates and returns an HBox containing the button layout for the Keno game interface.
     * The layout includes the winnings block, spots block, draws block, and auto-play box.
     *
     * @param kenoGame An instance of the KenoGame class that manages the game's logic.
     * @return An HBox containing the configured button layout.
     */
    private HBox createButtonLayout(KenoGame kenoGame) {
        // Create blocks for spots, draws, and auto-play
        VBox spotsBlock = createButtonBlock("Spots", new int[][]{{1, 4}, {8, 10}}, kenoGame);
        VBox drawsBlock = createButtonBlock("Draws", new int[][]{{1, 2}, {3, 4}}, kenoGame);
        VBox autoPlayBox = createAutoPlayBox(kenoGame);

        // Configure the winnings block title
        Label blockTitle = new Label("Winnings");
        blockTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #333;");
        blockTitle.setMaxWidth(Double.MAX_VALUE);
        blockTitle.setAlignment(Pos.CENTER);

        // Initialize and configure the total winnings and game winnings text fields
        totalWinningsBlock = new CustomTextField("total: $" + 0);
        gameWinningsBlock = new CustomTextField("Game: $"+ 0);

        // Create and configure a grid to hold the winnings text fields
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.add(gameWinningsBlock, 0, 0);
        buttonGrid.add(totalWinningsBlock, 0, 1);

        // Create the winnings block container and add the title and grid to it
        VBox winningsBlock = new VBox(10, blockTitle, buttonGrid);

        // Create and configure the button layout container
        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER_LEFT);
        buttonLayout.getChildren().addAll(winningsBlock, spotsBlock, drawsBlock, autoPlayBox);

        // Set horizontal grow priority for the spots, draws, and auto-play blocks
        HBox.setHgrow(spotsBlock, Priority.ALWAYS);
        HBox.setHgrow(drawsBlock, Priority.ALWAYS);
        HBox.setHgrow(autoPlayBox, Priority.ALWAYS);

        return buttonLayout;
    }

    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Creates and returns a VBox containing the auto-play box for the Keno game interface.
     * The auto-play box includes the auto button and the play button.
     *
     * @param kenoGame An instance of the KenoGame class that manages the game's logic.
     * @return A VBox containing the configured auto-play box.
     */
    private VBox createAutoPlayBox(KenoGame kenoGame) {
        // Create the auto-play box container and set its alignment
        VBox autoPlayBox = new VBox(10);
        autoPlayBox.setAlignment(Pos.CENTER);

        // Add the auto and play buttons to the auto-play box
        autoPlayBox.getChildren().addAll(autoButton, resetButton);

        // Create an empty VBox above the buttons and set its vertical grow priority
        VBox emptyBoxAboveButtons = new VBox();
        VBox.setVgrow(emptyBoxAboveButtons, Priority.ALWAYS);
        autoPlayBox.getChildren().add(0, emptyBoxAboveButtons);

        // The 'autoButton' is responsible for automatically generating and selecting random spots
        // on the bet card grid based on the number of spots the user has chosen to play.
        // When the user clicks the 'autoButton', the application checks if the user has selected
        // the number of spots to play. If not, an error alert is displayed. If the user has
        // selected the number of spots, the application resets the bet card grid, generates a
        // random sublist of spots, and simulates a click on each of the corresponding bet buttons.
        autoButton.setOnAction(event -> {
            // Check if the user has selected the number of spots to play
            if (kenoGame.getNumSpots() == 0) {
                // Show an error alert if the user has not selected the number of spots
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose the number of spots you want to play", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // Get the number of spots the user has selected
            int numSpots = selectedSpots.get();

            // Reset the buttons on the bet card grid
            betCardGrid.resetButtons();

            // Generate a list of integers from 1 to 80
            List<Integer> numbers = IntStream.rangeClosed(1, 80).boxed().collect(Collectors.toList());

            // Shuffle the list to create random spots
            Collections.shuffle(numbers);

            // Get the sublist containing the first 'numSpots' elements
            List<Integer> randomSpots = numbers.subList(0, numSpots);

            // Iterate through the children of the bet card grid
            for (Node node : betCardGrid.getChildren()) {
                // Check if the current node is an instance of BetButton
                if (node instanceof BetButton) {
                    // Cast the node to a BetButton
                    BetButton betButton = (BetButton) node;

                    // Get the number of the current bet button
                    int buttonNumber = betButton.getNumber();

                    // Check if the button number is in the random spots list
                    if (randomSpots.contains(buttonNumber)) {
                        // Fire the bet button if its number is in the random spots list
                        betButton.fire();
                    }
                }
            }
        });

        // The 'resetButton' is responsible for resetting the game. When the user clicks the
        // 'resetButton', the application resets the bet card grid, the number box, and the
        // play button. It also resets the total winnings to 0.
        resetButton.setOnAction(actionEvent -> {
            resetGame(kenoGame, numberBox, playButton);
            kenoGame.setTotalWinnings(0);
            totalWinningsBlock.setText("Total: $" + kenoGame.getTotalWinnings());
        });

        // The 'playButton' is responsible for initiating the Keno game based on the user's selections
        // and updating the game state accordingly.
        // When the user clicks the 'playButton', the application checks if the user has selected the
        // required number of spots, numbers, and draws to play. If not, an error alert is displayed.
        // If the user has made the required selections, the button disables itself and the 'autoButton'
        // to prevent further input during the game, clears any previous drawn numbers, and proceeds
        // with the Keno drawing using the user's selected numbers.
        // The application then updates the game and total winnings, displays the drawn numbers with
        // a visual indication of whether they were part of the user's selection, and re-enables the
        // buttons when the drawing is complete. If there are more draws remaining, the user can
        // continue with the next draw. Otherwise, the game is reset and an informational alert
        // displays the user's game winnings.
        playButton.setOnAction(event -> {
            // Check if the user has selected the number of spots to play.
            if (kenoGame.getNumSpots() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose the number of spots you want to play!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // Check if the user has selected the correct number of numbers.
            if (betCardGrid.getSelectedNumbers().size() != kenoGame.getNumSpots()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select " + kenoGame.getNumSpots() + " numbers before you can play!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // Check if the user has chosen the number of draws.
            if (kenoGame.getNumDraws() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose the number of draws you want to do!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // Disable playButton and autoButton during the drawing process.
            playButton.setDisable(true);
            autoButton.setDisable(true);
            resetButton.setDisable(true);

            // Clear the numberBox for a new drawing.
            numberBox.getChildren().clear();

            // Get the user's selected numbers and play the drawing.
            List<Integer> selectedNumbers = betCardGrid.getSelectedNumbers();
            int matchedNumbers = kenoGame.playDrawing(selectedNumbers);

            // Update winnings and display them on the UI.


            // Create a timeline for displaying drawn numbers.
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().clear();

            // Set the playButton text if there are multiple draws.
            if (kenoGame.getNumDraws() > 1) {
                playButton.setText("Continue");
            }

            // Add drawn numbers to the timeline as keyframes.
            for (int i = 0; i < kenoGame.getDrawnNumbers().size(); i++) {
                int number = kenoGame.getDrawnNumbers().get(i);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(i), event1 -> {
                    BetButton numberLabel = new BetButton(number);
                    numberLabel.setPrefSize(42, 10);
                    numberLabel.setAlignment(Pos.CENTER);

                    // Set the label state based on whether the number was selected by the user.
                    if (selectedNumbers.contains(number)) {
                        numberLabel.setState(BetButton.ButtonState.CORRECT);
                    } else {
                        numberLabel.setState(BetButton.ButtonState.INCORRECT);
                    }
                    numberBox.getChildren().add(numberLabel);
                });
                timeline.getKeyFrames().add(keyFrame);
            }

            // Re-enable playButton and autoButton after the timeline finishes.
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(kenoGame.getDrawnNumbers().size()), event1 -> {
                playButton.setDisable(false);
                autoButton.setDisable(false);
                resetButton.setDisable(false);
                gameWinnings += kenoGame.calculateWinnings(matchedNumbers);
                kenoGame.setTotalWinnings(kenoGame.getTotalWinnings() + kenoGame.calculateWinnings(matchedNumbers));
                gameWinningsBlock.setText("Game: $" + gameWinnings);
                totalWinningsBlock.setText("Total: $" + kenoGame.getTotalWinnings());
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.setRate(2.0);
            timeline.play();

            // Handle the end of the drawing and the completion of the timeline.
            timeline.setOnFinished(event1 -> {
                // If there are more draws remaining, update the number of draws and the playButton text.
                if (kenoGame.getNumDraws() > 1) {
                    kenoGame.setNumDraws(kenoGame.getNumDraws() - 1);

                    playButton.setText("Continue");
                } else {
                    // If the game is over, display the winnings and reset the game.
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have won $" + gameWinnings + " this game! Keep playing to earn more!", ButtonType.OK);
                        alert.showAndWait();
                        resetGame(kenoGame, numberBox, playButton);
                    });
                }
            });
        });

        return autoPlayBox;
    }

    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Creates a button block with a specified title and a grid of buttons.
     * The button block allows the user to choose the number of spots or draws for the Keno game.
     *
     * @param title   The title of the button block, either "Spots" or "Draws".
     * @param numbers A 2D array containing the numbers for the buttons in the block.
     * @param kenoGame An instance of the KenoGame class, representing the current game.
     * @return A VBox containing the title and grid of buttons for the block.
     */

    List<Button> selectedButtons = new ArrayList<>();
    private VBox createButtonBlock(String title, int[][] numbers, KenoGame kenoGame) {
        // Create and style the title label for the button block
        Label blockTitle = new Label(title);
        blockTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #333;");
        blockTitle.setMaxWidth(Double.MAX_VALUE);
        blockTitle.setAlignment(Pos.CENTER);

        // Create a GridPane to hold the buttons
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        // Keep track of the currently selected buttons for this title

        // Iterate through the 2D array to create and add buttons to the grid
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                Button button = new CustomButton(Integer.toString(numbers[i][j]));

                // Set the action for the button depending on whether it's for spots or draws
                if (title.equals("Spots")) {
                    button.setOnAction(event -> {
                        if(playButton.getText().equals("Play")) {
                            int numSpots = Integer.parseInt(button.getText());
                            selectedSpots.set(numSpots);
                            kenoGame.setNumSpots(numSpots);
                            betCardGrid.enableButtons(true);

                            // Deselect the previously selected buttons for this title
                            for (Button selectedButton : selectedButtons) {
                                if(selectedButton.getParent().equals(button.getParent())){
                                    selectedButton.setStyle("-fx-base: #2980b9; -fx-text-fill: white; -fx-background-radius: 5;");
                                }
                            }

                            // Select this button and add it to the list of selected buttons for this title
                            button.setStyle("-fx-background-color: red; -fx-text-fill: #fff;");
                            selectedButtons.add(button);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot change the number of spots during the game!", ButtonType.OK);
                            alert.showAndWait();
                        }
                    });
                } else if (title.equals("Draws")) {
                    button.setOnAction(event -> {
                        if(playButton.getText().equals("Play")) {
                            int numDraws = Integer.parseInt(button.getText());
                            selectedDraws.set(numDraws);
                            kenoGame.setNumDraws(numDraws);

                            // Deselect the previously selected buttons for this title
                            for (Button selectedButton : selectedButtons) {
                                // only deselect the buttons that are under the same title
                                if(selectedButton.getParent().equals(button.getParent())){
                                    selectedButton.setStyle("-fx-base: #2980b9; -fx-text-fill: white; -fx-background-radius: 5;");
                                }
                            }

                            // Select this button and add it to the list of selected buttons for this title
                            button.setStyle("-fx-background-color: red; -fx-text-fill: #fff;");
                            selectedButtons.add(button);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot change the number of draws during the game!", ButtonType.OK);
                            alert.showAndWait();
                        }
                    });
                }
                // Add the button to the grid
                buttonGrid.add(button, j, i);
            }
        }

        // Create a VBox to hold the title and grid, and set its alignment and spacing
        VBox buttonBlock = new VBox(10);
        buttonBlock.setAlignment(Pos.CENTER);
        buttonBlock.getChildren().addAll(blockTitle, buttonGrid);

        return buttonBlock;
    }

    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Updates the winnings column to display the appropriate payout amounts
     * based on the number of spots selected in the Keno game.
     *
     * @param numSpots The number of spots selected by the player.
     */
    private void updateWinningsColumn(int numSpots) {
        // Define the matrix of winnings for different numbers of spots
        int[][] winningsMatrix = {
                {0, 2},
                {0, 0, 1, 5, 75},
                {0, 0, 0, 0, 2, 12, 50, 750, 10000},
                {5, 0, 0, 0, 0, 2, 15, 40, 450, 4250, 100000}
        };
        int[] spotsIndex = {1, 4, 8, 10};

        // Find the index of the winnings for the selected number of spots
        int index = -1;
        for (int i = 0; i < spotsIndex.length; i++) {
            if (spotsIndex[i] == numSpots) {
                index = i;
                break;
            }
        }

        int winningIndex = 0;

        // Update the winnings column labels according to the selected number of spots
        if (index >= 0) {
            int[] winnings = winningsMatrix[index];

            // Clear the spots and winnings labels
            for (int i = 1; i < winningsLabels.size(); i++) {
                spotsLabels.get(i).setText("");
                winningsLabels.get(i).setText("");
            }

            // Set the spots and winnings labels with the appropriate values
            for (int i = 1; i < winningsLabels.size(); i++) {
                if (i < winnings.length) {
                    // Add the spots label
                    while (winnings[winningIndex] == 0 && winnings.length - 1 > winningIndex) {
                        winningIndex++;
                    }
                    spotsLabels.get(i).setText(Integer.toString(winningIndex));
                    winningsLabels.get(i).setText("$" + winnings[winningIndex]);

                    if (winnings.length - 1 > winningIndex) {
                        winningIndex++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * @author:  Seyfal Sultanov
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * The constructor of the KenoController class. It adds listeners to the selectedSpots
     * and selectedDraws properties. When the number of spots or draws is changed, the
     * listeners update the BetCardGrid and perform other actions as needed.
     */
    public KenoController() {
        // Add a listener to the selectedSpots property
        selectedSpots.addListener((obs, oldValue, newValue) -> {
            // Only update the BetCardGrid if the game is not ongoing
            betCardGrid.setMaxSpots(newValue); // Set the max spots for BetCardGrid
            updateWinningsColumn(newValue); // Update the winnings column based on the selected spots
        });

        // Add a listener to the selectedDraws property
        selectedDraws.addListener((obs, oldValue, newValue) -> {
            // Only update the number of draws if the game is not ongoing
            betCardGrid.setNumDraws(newValue);
        });
    }

    /**
     * @author:  Mykola Turchak
     * @version: 3.0
     * @date:    2023-03-15
     *
     * @description:
     * Resets the game state by clearing the BetCardGrid, resetting selected spots and draws,
     * updating the winnings column, and resetting the KenoGame instance.
     *
     * @param kenoGame   The KenoGame instance to be reset.
     * @param numberBox  The HBox containing drawn numbers to be cleared.
     * @param playButton The play button whose text needs to be updated.
     */
    public void resetGame(KenoGame kenoGame, HBox numberBox, Button playButton) {
        betCardGrid.resetButtons(); // Clear the BetCardGrid
        selectedSpots.set(0); // Reset the selected spots
        selectedDraws.set(0); // Reset the selected draws
        // reset button color to default
        for (Button selectedButton : selectedButtons) {
            selectedButton.setStyle("-fx-base: #2980b9; -fx-text-fill: white; -fx-background-radius: 5;");
        }
        // reset the spots and winnings labels
        for (int i = 1; i < winningsLabels.size(); i++) {
            spotsLabels.get(i).setText("");
            winningsLabels.get(i).setText("");
        }

        updateWinningsColumn(0); // Update the winnings column
        gameWinnings = 0; // Reset the game winnings
        gameWinningsBlock.setText("Game: $" + gameWinnings); // Update the game winnings text
        kenoGame.resetGame(); // Reset the KenoGame instance
        numberBox.getChildren().clear(); // Clear the drawn numbers in the numberBox
        playButton.setText("Play"); // Update the play button text
    }

}