import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.FontWeight;

/**
 * @author: Seyfal Sultanov
 * @author: Mykola Turchak
 * @version: 3.0
 * @date: 2023-03-15
 *
 * @description: This is a JavaFXTemplate class with a BorderPane as the root node of the scene graph.
 * It contains a different texts and a CustomButtons for playing KenoGame.
 * The class also includes menu items for game rules, odds of winning, and new look.
 * There is also functionality for changing the background image and displaying game rules.
 */


public
class JavaFXTemplate extends Application {
    private BorderPane root; // root node of scene graph
    private Text welcomeText; // welcome text
    private CustomButton backButton = new CustomButton("Back"); // back button
    private int newLookNum; // number of times new-look button has been clicked
    private Node previousPage; // previous page
    private boolean isPlaying;
    KenoGame kenoGame = new KenoGame();


    public static void main(String[] args) { // entry point of JavaFX application
        launch(args); // launch JavaFX application
    }


    @Override
    public void start(Stage primaryStage) { // called by launch()
        primaryStage.setTitle("Welcome to JavaFX"); // set title of window

        Rectangle rect = new Rectangle(100, 40, 100, 100); // create rectangle
        rect.setArcHeight(50); // set arc height
        rect.setArcWidth(50); // set arc width
        rect.setFill(Color.VIOLET); // set fill color

        RotateTransition rt = new RotateTransition(Duration.millis(5000), rect); // create rotate transition
        rt.setByAngle(270); // set by angle
        rt.setCycleCount(4); // set cycle count
        rt.setAutoReverse(true); // set auto reverse
        SequentialTransition seqTransition = new SequentialTransition(new PauseTransition(Duration.millis(500)), rt); // create sequential transition
        seqTransition.play(); // play sequential transition

        FadeTransition ft = new FadeTransition(Duration.millis(5000), rect); // create fade transition
        ft.setFromValue(1.0); // set from value
        ft.setToValue(0.3); // set to value
        ft.setCycleCount(4); // set cycle count
        ft.setAutoReverse(true); // set auto reverse

        ft.play(); // play fade transition
        root = new BorderPane(); // create root node of scene graph
        isPlaying = false;

        // add menu bar
        MenuBar menuBar = new MenuBar(); // create menu bar
        Menu fileMenu = new Menu("Menu"); // create file menu
        MenuItem rules = new MenuItem("Game Rules"); // create rules menu item
        MenuItem odds = new MenuItem("Odds of Winning"); // create odds menu item
        MenuItem newLook = new MenuItem("New Look"); // create new-look menu item
        MenuItem exit = new MenuItem("Exit"); // create exit menu item
        fileMenu.getItems().addAll(rules, odds, exit); // add menu items to file menu
        menuBar.getMenus().addAll(fileMenu); // add file menu to menu bar
        root.setTop(menuBar); // add menu bar to root node of scene graph

        // add welcome text
        welcomeText = new Text("Welcome to Keno\n"); // create welcome text
        welcomeText.setFont(Font.font("Verdana", FontWeight.BOLD, 40)); // set font of welcome text
        welcomeText.setFill(Color.WHITE); // set fill color of welcome text
        welcomeText.setStroke(Color.BLACK); // set stroke color of welcome text
        welcomeText.setStrokeType(StrokeType.OUTSIDE); // set stroke type of welcome text
        welcomeText.setStrokeWidth(3); // set stroke width of welcome text
        root.setCenter(welcomeText); // add welcome text to root node of scene graph

        // add play button
        CustomButton playButton = new CustomButton("Play"); // create play button
        playButton.setFont(new Font(30)); // set font of play button

        // ----------------- KenoController -----------------
        /*
         * KenoController is a class that controls the Keno game.
         * It is responsible for initializing the Keno UI,
         * handling the user's input, and updating the Keno UI.
         */
        playButton.setOnAction(event -> { // add event handler to play button
            // add new look to the menu bar
            isPlaying = true;
            fileMenu.getItems().add(2, newLook);
            KenoController kenoController = new KenoController(); // then create a KenoController object
            kenoController.initializeKenoUI(root, kenoGame); // initialize the Keno UI and pass the root node of the scene graph and the KenoGame object to the KenoController object
        });
        // --------------------------------------------------

        VBox vBox = new VBox(welcomeText, playButton); // create vertical box
        vBox.setAlignment(Pos.CENTER); // set alignment of vertical box
        root.setCenter(vBox); // add vertical box to root node of scene graph
        newLookNum = 0; // initialize new-look number
        String backgroundPath = "/KenoBackground" + newLookNum + ".jpg"; // create path to background image
        root.setStyle("-fx-background-image: url('" + backgroundPath + "');" + // set background image
                "-fx-background-size: cover;" + // set background size
                "-fx-background-position: left center;"); // set background position
        Scene scene = new Scene(root, 900, 900); // create scene
        primaryStage.setScene(scene); // add scene to stage
        primaryStage.show(); // display stage

        // event handlers for menu items
        rules.setOnAction(event -> {
            previousPage = root.getCenter();
            root.getChildren().remove(menuBar);
            displayRules(backButton);

            backButton.setOnAction(event1 -> {
                root.setTop(menuBar);
                root.setLeft(null);
                root.setCenter(previousPage);
            });
        });

        odds.setOnAction(event -> { // add event handler to odds menu item
            previousPage = root.getCenter();    // save previous page
            root.getChildren().remove(menuBar); // remove menu bar from root node of scene graph
            displayOdds(backButton);

            backButton.setOnAction(event1 -> {
                root.setTop(menuBar);
                root.setLeft(null);
                root.setCenter(previousPage);
            });
        });

        newLook.setOnAction(event -> { // add event handler to new-look menu item

            newLookNum = (newLookNum + 1) % 4; // increment new-look number
            String newBackgroundPath = "/KenoBackground" + newLookNum + ".jpg"; // create path to background image

            root.setStyle("-fx-background-image: url('" + newBackgroundPath + "');" + // set background image
                    "-fx-background-size: cover;" + // set background size
                    "-fx-background-position: left center;" + // set background position
                    "");

            // Randomize font
            String[] fonts = {"Arial", "Comic Sans MS", "Verdana", "Trebuchet MS"}; // create array of fonts
            String randomFont = fonts[newLookNum]; // get random font
            welcomeText.setFont(Font.font(randomFont, FontWeight.BOLD, 30)); // set font of welcome text

            // Randomize color
            Color[] colors = {Color.ORANGE, Color.AQUA, Color.YELLOW, Color.GREEN}; // create array of colors
            Color randomColor = colors[newLookNum]; // get random color
            welcomeText.setFill(randomColor); // set fill color of welcome text
            welcomeText.setStroke(randomColor.darker()); // set stroke color of welcome text

        });

        exit.setOnAction(event -> primaryStage.close()); // add event handler to exit menu item
    }

    /**
     * Displays the odds of winning.
     *
     * @param backButton the back button
     */
    private void displayRules(Button backButton) {
        VBox rulesBox = new VBox(20);
        rulesBox.setStyle("-fx-padding: 20; -fx-background-color: transparent;");
        rulesBox.setAlignment(Pos.CENTER);

        // Add title
        Text title = new Text("Keno Rules:");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        rulesBox.getChildren().add(title);

        // Add rules
        String[] rulesText = {
                "1. Select the number of spots you want to play (1 to 10).",
                "2. Choose your bet amount.",
                "3. Pick the numbers you want to play (1 to 80).",
                "4. The game will draw 20 random numbers.",
                "5. Win if your selected numbers match the drawn numbers.",
                "6. The more numbers you match, the higher your prize.",
        };

        // Add rules to VBox
        for (String rule : rulesText) {
            Text ruleText = new Text(rule);
            ruleText.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));
            ruleText.setWrappingWidth(600);
            ruleText.setTextAlignment(TextAlignment.JUSTIFY);
            rulesBox.getChildren().add(ruleText);
        }
        rulesBox.getChildren().add(backButton);

        // Create a scroll pane
        ScrollPane scrollPane = new ScrollPane(rulesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 20;");

        // Create a white rectangle as a background
        Rectangle background = new Rectangle();
        background.widthProperty().bind(scrollPane.widthProperty());
        background.heightProperty().bind(scrollPane.heightProperty());
        background.setFill(Color.WHITE);

        // Add the background rectangle and the scroll pane to a StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, scrollPane);

        // Center the StackPane in the scene
        root.setCenter(stackPane);
    }

    /**
     * Displays the odds of winning.
     *
     * @param backButton the back button
     */

    private void displayOdds(Button backButton) {
        VBox OddsBox = new VBox(20);
        OddsBox.setStyle("-fx-padding: 20; -fx-background-color: transparent;");
        OddsBox.setAlignment(Pos.CENTER);

        // Add title
        Text title = new Text("The Odds of Winning:");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        OddsBox.getChildren().add(title);

        // Add odds
        String[] OddsText = {
                "1 Spot Game: 1 in 4.00",
                "4 Spot Game: 1 in 3.86",
                "8 Spot Game: 1 in 9.77",
                "10 Spot Game: 1 in 9.05"
        };

        // Add odds to VBox
        for (String odds : OddsText) {
            Text OddText = new Text(odds);
            OddText.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));
            OddText.setWrappingWidth(600);
            OddText.setStyle("-fx-background-color: white");
            OddText.setTextAlignment(TextAlignment.CENTER);
            OddsBox.getChildren().add(OddText);
        }
        OddsBox.getChildren().add(backButton);

        // Create a scroll pane
        ScrollPane scrollPane = new ScrollPane(OddsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 20;");

        // Create a white rectangle as a background
        Rectangle background = new Rectangle();
        background.widthProperty().bind(scrollPane.widthProperty());
        background.heightProperty().bind(scrollPane.heightProperty());
        background.setFill(Color.WHITE);

        // Add the background rectangle and the scroll pane to a StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, scrollPane);

        // Center the VBox in the scene
        root.setCenter(stackPane);
    }
}