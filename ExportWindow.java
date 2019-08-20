import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


// Window that is opened when 'Export...' menu item is pressed. Gives the user the option to select
// which file type they want to export the information in the table as. Doesn't currently have the
// functionality for the user to determine where to export the file, just saves the file in the
// same directory that KismetViewer is in. Has some functionality for exporting to .xlsx but it
// is not implemented.
public class ExportWindow {
    
    static String [] answer = new String[2];

    public static String [] display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // blocks user interaction until popup closed
        window.setTitle("Export");
        window.setMinWidth(300);
        window.setMinHeight(280);
        window.setMaxWidth(300);
        window.setMaxHeight(280);
        ToggleGroup group = new ToggleGroup();

        Label headerLabel = new Label("Export");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        RadioButton csvRadioButton = new RadioButton(".csv");
        csvRadioButton.setToggleGroup(group);
        csvRadioButton.setSelected(true);
        csvRadioButton.setPadding(new Insets(15, 0, 0, 40));
        csvRadioButton.setUserData("csv");
        
        RadioButton txtRadioButton = new RadioButton(".txt (re-openable)");
        txtRadioButton.setToggleGroup(group);
        txtRadioButton.setPadding(new Insets(5, 0, 0, 40));
        txtRadioButton.setUserData("txt");
        
        // Not implemented
        RadioButton xlsxRadioButton = new RadioButton(".xlsx (Excel)");
        xlsxRadioButton.setToggleGroup(group);
        xlsxRadioButton.setPadding(new Insets(5, 0, 0, 40));
        xlsxRadioButton.setUserData("xlsx");
        
        
        Label saveAsLabel = new Label("Save as:");
        saveAsLabel.setTextAlignment(TextAlignment.CENTER);
        saveAsLabel.setPadding(new Insets(20, 0, 0, 0));
        
        TextField inputTextField = new TextField();
        Label errorLabel = new Label("");
        
        Button exportButton = new Button("Export");
        Button cancelButton = new Button("Cancel");
        
        exportButton.setOnAction(e -> {
            if (inputTextField.getText().trim().equals("") || inputTextField.getText().trim() == null) {
                errorLabel.setText("Error: Invalid file name");
            }
            else {
                answer[0] = group.getSelectedToggle().getUserData().toString(); // file type
                answer[1] = inputTextField.getText().trim(); // file name
                window.close();
            } // else
        }); // exportButton setOnAction
        
        cancelButton.setOnAction(e -> {
            answer[0] = "CANCEL_SENTVAL";
            window.close();
        }); // cancelButton setOnAction
        
        window.setOnCloseRequest(e -> {
            e.consume();
            answer[0] = "CANCEL_SENTVAL";
            window.close();
        });
        
        VBox layout = new VBox(5);
        HBox csvRow = new HBox();
        csvRow.getChildren().add(csvRadioButton);
        
        HBox txtRow = new HBox();
        txtRow.getChildren().add(txtRadioButton);
        
        HBox xlsxRow = new HBox();
        xlsxRow.getChildren().add(xlsxRadioButton);
        
        HBox buttonMenu = new HBox();
        buttonMenu.getChildren().addAll(exportButton, cancelButton);
        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setSpacing(10);
        
        layout.getChildren().addAll(headerLabel, csvRow, txtRow, /*xlsxRow,*/ saveAsLabel,
                                    inputTextField, errorLabel, buttonMenu);
        layout.setMargin(inputTextField, new Insets(0, 40, 0, 40));
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // displays to the user and waits for it to be closed before returning to caller
    
        return answer;
    } // display

} // ExportWindow
