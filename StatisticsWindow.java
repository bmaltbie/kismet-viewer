import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

// Window that is opened when 'Statistics' button is pressed. At the moment just shows a pie graph
// of all the different types of encryptions encountered for the networks.
// Can be expanded in future to show other/more details statistics.
public class StatisticsWindow {
    
    public static void display(List<Network> networkList) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // blocks user interaction until popup closed
        window.setTitle("Statistics");
        window.setMinWidth(800);
        window.setMinHeight(400);
        window.setMaxWidth(800);
        window.setMaxHeight(400);
        
        Map<String, Integer> encryptionHM = new HashMap<String, Integer>();
        ObservableList<PieChart.Data> encryptionChartData = FXCollections.observableArrayList();
        
        // Counts the number of each encryption and creates a pie chart showing those counts. The
        // hash map keys are the encryption types and they map to their counts.
        for (int i = 0; i < networkList.size(); i++) {
            if (encryptionHM.containsKey(networkList.get(i).getEncryption())) {
                encryptionHM.put(networkList.get(i).getEncryption(), encryptionHM.get(networkList.get(i).getEncryption())+1);
            }
            else
                encryptionHM.put(networkList.get(i).getEncryption(), 1);
        } // for
        
        Object [] keys = encryptionHM.keySet().toArray();
        
        // Adds the encryptions and their counts to pie chart data
        for (Object obj : keys) {
            encryptionChartData.add(new PieChart.Data(obj.toString(), encryptionHM.get(obj.toString())));
        }
        
        PieChart encryptionChart = new PieChart(encryptionChartData);
        encryptionChart.setTitle("Encryption Types");
        encryptionChart.setLegendSide(Side.LEFT);
        encryptionChart.setMinHeight(350);
        encryptionChart.setMaxHeight(350);
        
        // Label that acts as the pop-up label giving more information about whichever section of
        // the pie chart that the user's mouse is hovering over.
        Label encryptionLabel = new Label("");
        encryptionLabel.setVisible(false);
        encryptionLabel.getStyleClass().addAll("chart-line-symbol", "chart-series-line");
        encryptionLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        encryptionLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        
        
        // For all data on the pie chart, this for loop creates MouseEvents that allow a pop-up
        // informational label to appear that gives more details regarding the section of the pie
        // chart that the user's cursor is currently above.
        for (final PieChart.Data data : encryptionChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   encryptionLabel.setMouseTransparent(true);
                                                   encryptionLabel.setTranslateX(e.getX()+130);
                                                   encryptionLabel.setTranslateY(e.getY()-250);
                                                   encryptionLabel.setText(String.valueOf(data.getPieValue()) + "\n" + data.getName());
                                                   if (encryptionLabel.getStyleClass().size() == 4) {
                                                       encryptionLabel.getStyleClass().remove(3);
                                                   }
                                                   encryptionLabel.getStyleClass().add(data.getNode().getStyleClass().get(2));
                                                   encryptionLabel.setVisible(true);
                                               }
                                           }); // MOUSE_ENTERED
            
            data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   encryptionLabel.setTranslateX(e.getX()+130);
                                                   encryptionLabel.setTranslateY(e.getY()-250);
                                               }
                                           }); // MOUSE_MOVED
            
            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   encryptionLabel.setVisible(false);
                                               }
                                           }); // MOUSE_EXITED
        } // for
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> { window.close(); });
        closeButton.setTranslateY(-70);
        
        VBox layout = new VBox(5);
        layout.getChildren().addAll(encryptionChart, encryptionLabel, closeButton);
        layout.setAlignment(Pos.TOP_CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // displays to the user and waits for it to be closed before returning to caller
    } // display

} // StatisticsWindow
