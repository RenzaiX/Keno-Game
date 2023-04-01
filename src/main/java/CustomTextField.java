import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author: Mykola Turchak
 * @version: 1.0
 * @date: 2023-03-15
 *
 * @description: This class is used to create a custom text field.
 */
public class CustomTextField extends TextField {

    /**
     * This is a constructor for the CustomTextField class.
     * @param text
     */
    public CustomTextField(String text) {
        setText(text);
        setFont(new Font("Arial", 18));
        setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-radius: 5;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(8.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.rgb(50, 50, 50, 0.7));
        setEffect(dropShadow);

        // Set text field size
        setPrefWidth(120); // Set preferred width
        setPrefHeight(50); // Set preferred height
        setMaxWidth(160); // Set maximum width
        setMaxHeight(70); // Set maximum height
    }

}