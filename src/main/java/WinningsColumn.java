import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

/**
 * /**
 *  @author: Mykola Turchak
 *  @version: 3.0
 *  @date: 2023-03-15
 *
 *  @description: A class representing a column of labels that display the winnings for each number of spots.
 */
public class WinningsColumn extends VBox {
    // A list of labels that display the winnings for each number of spots
    private List<Label> winningsLabels = new ArrayList<>();

    // Constructor
    public WinningsColumn() {
        super(10);
        setPadding(new Insets(10, 10, 10, 10));
        setStyle("-fx-background-color: white;");

        // Add a label for each number of spots
        for (int i = 1; i <= 10; i++) {
            HBox row = new HBox(5);
            Label spotsLabel = new Label("Spots: " + i);
            Label winningsLabel = new Label("");
            row.getChildren().addAll(spotsLabel, winningsLabel);
            winningsLabels.add(winningsLabel);
            getChildren().add(row);
        }

        // Add an empty VBox to the bottom of the column to fill the remaining space
        VBox emptyBox = new VBox();
        VBox.setVgrow(emptyBox, Priority.ALWAYS);
        getChildren().add(emptyBox);
    }

}
