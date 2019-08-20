import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

// Window that is opened when 'Open...' menu item is pressed. Allows user to input the file path/
// file name to be opened. 
public class OpenWindow {
    
    static String [] answer = new String[2];

    public static String [] display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // blocks user interaction until popup closed
        window.setTitle("Open");
        window.setMinWidth(300);
        window.setMinHeight(400);
        window.setMaxWidth(300);
        window.setMaxHeight(400);

        
        Label headerLabel = new Label("Instructions:");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        headerLabel.setPadding(new Insets(5, 0, 0, 20));
        

        Label instructionLabel1 = new Label("1) Please enter the full path and " +
                                            "name of the Kismet output file");
        instructionLabel1.setWrapText(true);
        instructionLabel1.setPadding(new Insets(5, 20, 0, 20));
        
        
        Label instructionLabel2 = new Label("2) Do not include the extension unless it's a .txt file");
        instructionLabel2.setWrapText(true);
        instructionLabel2.setPadding(new Insets(5, 20, 0, 20));
        
        
        Label instructionLabel3 = new Label("3) Please ensure that all output files are in the " +
                                            "same directory and have the same name");
        instructionLabel3.setWrapText(true);
        instructionLabel3.setPadding(new Insets(5, 20, 0, 20));
        
        
        Label exampleLabel = new Label("Example: /User/JohnSmith/Desktop/Kismet-20180607-15-46-47-1");
        exampleLabel.setFont(Font.font("System", 10));
        exampleLabel.setWrapText(true);
        exampleLabel.setPadding(new Insets(10, 0, 5, 0));
        exampleLabel.setTextAlignment(TextAlignment.CENTER);
        
        
        TextArea inputTextArea = new TextArea();
        inputTextArea.setPromptText("Enter path here...");
        inputTextArea.setMinHeight(62);
        inputTextArea.setMaxHeight(62);
        inputTextArea.setWrapText(true);
        
        
        Button openButton = new Button("Open");
        Button cancelButton = new Button("Cancel");
        
        openButton.setOnAction(e -> {
            answer[0] = "Valid";
            answer[1] = inputTextArea.getText().trim(); // file path and name
            window.close();
        });
        
        cancelButton.setOnAction(e -> {
            answer[0] = "CANCEL_SENTVAL";
            window.close();
        });
        
        window.setOnCloseRequest(e -> {
            e.consume();
            answer[0] = "CANCEL_SENTVAL";
            window.close();
        });
        
        VBox layout = new VBox(5);
        Insets insets = new Insets(0, 0, 5, 0);
        layout.setPadding(insets);
        HBox buttonMenu = new HBox();
        buttonMenu.getChildren().addAll(openButton, cancelButton);
        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setSpacing(20);
        
        layout.getChildren().addAll(headerLabel, instructionLabel1, instructionLabel2,
                                    instructionLabel3, exampleLabel, inputTextArea, buttonMenu);
        layout.setMargin(inputTextArea, new Insets(20, 20, 20, 20));
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // displays to the user and waits for it to be closed before returning to caller
    
        return answer;
    } // display

} // OpenWindow
