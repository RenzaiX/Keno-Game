import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author: Seyfal Sultanov
 * @version: 1.1
 * @date: 2023-03-15
 *
 * @description:
 *
 * BetButton is a custom button class for betting applications, such as lottery games.
 * Each BetButton displays a number, and the button has different states and visual
 * appearances based on its current state.
 *
 * BetButton extends the JavaFX Button class and adds a Circle and Text elements
 * to create a custom appearance for the button. BetButton also provides methods
 * for updating the state of the button, which affects its visual appearance.
 *
 * Usage:
 * BetButton betButton = new BetButton(number);
 * betButton.setOnAction(event -> {
 *     // Perform actions based on the button click
 * });
 *
 * When the button is clicked, we toggle the selection state of the button using the
 * toggleSelection() method. This method switches between the UNSELECTED and SELECTED
 * states and updates the button's appearance accordingly.
 *
 * You can also manually set the state of the button using the setState() method.
 * For example, you can set the button to a correct or incorrect state by passing
 * BetButton.ButtonState.CORRECT or BetButton.ButtonState.INCORRECT as arguments
 * to the setState() method. In the example above, you can replace the toggleSelection()
 * call with betButton.setState(BetButton.ButtonState.CORRECT); to set the button to the
 * correct state when clicked.
 *
 * Here's a summary of the available states and their corresponding visual appearances:
 *
UNSELECTED: Neutral color with black text.
SELECTED: Blue color with white text.
DRAWN: Light gray color with black text.
CORRECT: Green color with white text.
INCORRECT: Red color with white text.
 *
 * Use getNumber() to get the number displayed on the button.
 * Use getState() to get the current state of the button.
 *
 */
public class BetButton extends Button {
    private int            number;
    private ButtonState    state;
    private Circle         circle;
    private Text           text;
    private StackPane      stack;
    private RadialGradient neutralGradient, selectedGradient, drawnGradient;

    public
    BetButton (int number) { // Constructor

        this.number = number; // Set the number of the button
        this.state = ButtonState.UNSELECTED; // Set the state of the button to unselected

        // Create the gradients for the buttons
        neutralGradient = new RadialGradient(
                0, 0, 0.5, 0.5, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#cccccc")),
                new Stop(1, Color.web("#aaaaaa"))
        );
        selectedGradient = new RadialGradient(
                0, 0, 0.5, 0.5, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2f6dff")),
                new Stop(1, Color.web("#0d47a1"))
        );
        drawnGradient = new RadialGradient(
                0, 0, 0.5, 0.5, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#eeeeee")),
                new Stop(1, Color.web("#cccccc"))
        );

        circle = new Circle(20); // Create a circle with a radius of 20
        circle.setFill(neutralGradient); // Set the color of the circle to neutral

        DropShadow dropShadow = new DropShadow(); // Create a drop shadow effect
        dropShadow.setRadius(10); // Set the radius of the drop shadow
        dropShadow.setOffsetX(2); // Set the offset of the drop shadow
        dropShadow.setOffsetY(2); // Set the offset of the drop shadow
        dropShadow.setColor(Color.rgb(50, 50, 50, 0.7)); // Set the color of the drop shadow
        circle.setEffect(dropShadow); // Set the effect of the circle to the drop shadow

        text = new Text(Integer.toString(number)); // Create a text object with the number of the button
        text.setFont(new Font(14)); // Set the font of the text
        text.setFill(Color.BLACK); // Set the color of the text

        stack = new StackPane(circle, text); // Create a stack pane with the circle and the text
        setGraphic(stack); // Set the graphic of the button to the stack pane
        setStyle("-fx-background-color: transparent; -fx-padding: 0;"); // Set the style of the button

        setOnMouseClicked(this::onMouseClicked); // Add an event handler for the mouse click event
        setOnAction(event -> toggleSelection()); // Add an event handler for the action event

    }

    // mouse click event handler
    private
    void onMouseClicked (MouseEvent event) {
        // Add the scaling animation
        ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1.1);
        st.setToY(1.1);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    // toggle the selection state of the button
    public
    void toggleSelection () {
        if (state == ButtonState.UNSELECTED) {
            state = ButtonState.SELECTED;
        } else if (state == ButtonState.SELECTED) {
            state = ButtonState.UNSELECTED;
        }
        updateAppearance();
    }
    // set the state of the button
    private
    void updateAppearance () {
        switch (state) {
            case UNSELECTED:
                circle.setFill(neutralGradient);
                text.setFill(Color.BLACK);
                break;
            case SELECTED:
                circle.setFill(selectedGradient);
                text.setFill(Color.WHITE);
                break;
            case DRAWN:
                circle.setFill(drawnGradient);
                text.setFill(Color.BLACK);
                break;
            case CORRECT:
                circle.setFill(Color.web("#4caf50"));
                text.setFill(Color.WHITE);
                break;
            case INCORRECT:
                circle.setFill(Color.web("#f44336"));
                text.setFill(Color.WHITE);
                break;
        }
    }

    // get the number of the button
    public
    int getNumber () {
        return number;
    }

    // get the state of the button
    public
    ButtonState getState () {
        return state;
    }

    // get the button object itself
    public
    Button getButton () { return this; }

    // set the state of the button manually
    public
    void setState (ButtonState state) {
        this.state = state;
        updateAppearance();
    }

    // the possible states of the button
    public
    enum ButtonState {
        UNSELECTED, SELECTED, DRAWN, CORRECT, INCORRECT
    }
}