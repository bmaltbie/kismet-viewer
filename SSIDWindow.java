import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.util.ArrayList;


// Window that is opened when 'SSID' button is pressed. Displays the information for all SSIDs
// that are associated with the selected network/client. Only one SSID is displayed at a time and the
// user can cycle through each SSID to view them.
public class SSIDWindow {
    final static int SINGLE_PARENT_WIDTH = 27; // If the dropdown view (tree view) only has one item
    final static int SINGLE_CHILD_WIDTH = 26; // if the dropdown view (tree view) has >1 items

    public static void display(ArrayList<SSID> SSIDList) {
        Stage window = new Stage();
        window.setTitle("SSIDs");
        window.setMinWidth(400);
        window.setMinHeight(500);
        window.setMaxWidth(400);
        window.setMaxHeight(500);
        

        TreeView<String> encryptionTree;
        IntegerProperty currentIndex = new SimpleIntegerProperty(0); // which SSID is being viewed
        
        // Header
        Label headerLabel = new Label("SSIDs");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        headerLabel.setPadding(new Insets(10, 0, 10, 0));
        
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(110));
        grid.setVgap(5);
        grid.setHgap(5);
        
        // Type
        Label typeLabel = new Label("Type: ");
        GridPane.setConstraints(typeLabel, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label typeLabelDetails = new Label(SSIDList.get(currentIndex.get()).getType());
        GridPane.setConstraints(typeLabelDetails, 1, 0);
        
        // SSID
        Label SSIDLabel = new Label("SSID: ");
        GridPane.setConstraints(SSIDLabel, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label SSIDLabelDetails = new Label(SSIDList.get(currentIndex.get()).getSSIDName());
        GridPane.setConstraints(SSIDLabelDetails, 1, 1);
        SSIDLabelDetails.setWrapText(true);
        
        // First seen
        Label firstSeenLabel = new Label("First: ");
        GridPane.setConstraints(firstSeenLabel, 0, 2, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label firstSeenLabelDetails = new Label(SSIDList.get(currentIndex.get()).getFirstSeen());
        GridPane.setConstraints(firstSeenLabelDetails, 1, 2);
        
        // Last seen
        Label lastSeenLabel = new Label("Last: ");
        GridPane.setConstraints(lastSeenLabel, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label lastSeenLabelDetails = new Label(SSIDList.get(currentIndex.get()).getLastSeen());
        GridPane.setConstraints(lastSeenLabelDetails, 1, 3);
        
        // Max rate
        Label maxRateLabel = new Label("Max Rate: ");
        GridPane.setConstraints(maxRateLabel, 0, 4, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label maxRateLabelDetails = new Label(String.valueOf(SSIDList.get(currentIndex.get()).getMaxRate()));
        GridPane.setConstraints(maxRateLabelDetails, 1, 4);
        
        // Beacon
        Label beaconLabel = new Label("Beacon: ");
        GridPane.setConstraints(beaconLabel, 0, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label beaconLabelDetails = new Label(String.valueOf(SSIDList.get(currentIndex.get()).getBeacon()));
        GridPane.setConstraints(beaconLabelDetails, 1, 5);
        
        // Packets
        Label packetsLabel = new Label("Beacon: ");
        GridPane.setConstraints(packetsLabel, 0, 6, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label packetsLabelDetails = new Label(String.valueOf(SSIDList.get(currentIndex.get()).getPackets()));
        GridPane.setConstraints(packetsLabelDetails, 1, 6);
        
        // WPS
        Label WPSLabel = new Label("WPS: ");
        GridPane.setConstraints(WPSLabel, 0, 7, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label WPSLabelDetails = new Label(SSIDList.get(currentIndex.get()).getWPS());
        GridPane.setConstraints(WPSLabelDetails, 1, 7);
        
        // WPS Manufacturer
        Label WPSManufLabel = new Label("WPS: ");
        GridPane.setConstraints(WPSManufLabel, 0, 8, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label WPSManufLabelDetails = new Label(SSIDList.get(currentIndex.get()).getWPSManuf());
        GridPane.setConstraints(WPSManufLabelDetails, 1, 8);
        
        // Dev Name
        Label devNameLabel = new Label("Dev Name: ");
        GridPane.setConstraints(devNameLabel, 0, 9, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label devNameLabelDetails = new Label(SSIDList.get(currentIndex.get()).getDevName());
        GridPane.setConstraints(devNameLabelDetails, 1, 9);
        
        // Model Name
        Label modelNameLabel = new Label("Model Name: ");
        GridPane.setConstraints(modelNameLabel, 0, 10, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label modelNameLabelDetails = new Label(SSIDList.get(currentIndex.get()).getModelName());
        GridPane.setConstraints(modelNameLabelDetails, 1, 10);
        
        // Model Num
        Label modelNumLabel = new Label("Model Num: ");
        GridPane.setConstraints(modelNumLabel, 0, 11, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label modelNumLabelDetails = new Label(SSIDList.get(currentIndex.get()).getModelNum());
        GridPane.setConstraints(modelNumLabelDetails, 1, 11);
        
    
        // Encryption - [TREE VIEW]
        Label encryptionLabel = new Label("Encryption: ");
        Label encryptionLabelDetails = new Label("Encryption Details");
        TreeItem<String> encryptionRoot = new TreeItem<>("Encryption List");
        boolean oneEncryption = false;
        
        // If there is only one encryption, will display it as a simple label, not a tree view
        if (SSIDList.get(currentIndex.get()).getNumEncryptions() <= 1) {
            encryptionLabelDetails.setText(SSIDList.get(currentIndex.get()).getEncryption_plain());
            oneEncryption = true;
        }
        else {
            for (int i = 0; i < SSIDList.get(currentIndex.get()).getNumEncryptions(); i++)
                makeBranch(SSIDList.get(currentIndex.get()).getEncryptionList().get(i), encryptionRoot);
        } // else
        
        encryptionTree = new TreeView<>(encryptionRoot);
        encryptionTree.setMinHeight(SINGLE_PARENT_WIDTH);
        encryptionTree.setPrefHeight(SINGLE_PARENT_WIDTH);
        encryptionTree.setMaxHeight(SINGLE_PARENT_WIDTH + 2*SINGLE_CHILD_WIDTH);
        grid.addRow(12, encryptionLabel, (oneEncryption) ? encryptionLabelDetails : encryptionTree);
        GridPane.setConstraints(encryptionLabel, 0, 12, 1, 1, HPos.RIGHT, VPos.CENTER);

        
        // WPA Version
        Label WPAVersionLabel = new Label("WPA Version: ");
        GridPane.setConstraints(WPAVersionLabel, 0, 13, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label WPAVersionLabelDetails = new Label(SSIDList.get(currentIndex.get()).getWPAVersion());
        GridPane.setConstraints(WPAVersionLabelDetails, 1, 13);
        
        
        
        // LISTENERS
        // Honestly, I'm not sure why this works, it's not supposed to. It should be tied to the
        // new value (newVal) but for some reason that makes it backwards. Also, not sure why
        // it doesn't work the first time you expand the TreeView.
        encryptionTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            encryptionTree.setMaxHeight(SINGLE_PARENT_WIDTH + 2*SINGLE_CHILD_WIDTH);
            encryptionTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
        }); // encryptionTree Listener
    
        
        grid.getChildren().addAll(typeLabel, SSIDLabel, firstSeenLabel, lastSeenLabel, maxRateLabel,
                                  beaconLabel, packetsLabel, WPSLabel, WPSManufLabel, devNameLabel,
                                  modelNameLabel, modelNumLabel, WPAVersionLabel);
        
        grid.getChildren().addAll(typeLabelDetails, SSIDLabelDetails, firstSeenLabelDetails,
                                  lastSeenLabelDetails, maxRateLabelDetails, beaconLabelDetails,
                                  packetsLabelDetails, WPSLabelDetails, WPSManufLabelDetails,
                                  devNameLabelDetails, modelNameLabelDetails, modelNumLabelDetails,
                                  WPAVersionLabelDetails);
        
        Button closeButton = new Button("Close");
        Label indexLabel = new Label("1/" + SSIDList.size());
        
        
        // Right and left triangles are used to cycle through the clients (act as arrows)
        Polygon rightTriangle = new Polygon();
        rightTriangle.getPoints().setAll(0.0, 0.0, 30.0, 15.0, 0.0, 30.0);
        rightTriangle.setOnMousePressed(e -> rightTriangle.setFill(Color.DIMGREY));
        if (SSIDList.size() == 1)
            rightTriangle.setFill(Color.DIMGREY);
        
        
        Polygon leftTriangle = new Polygon();
        leftTriangle.getPoints().setAll(0.0, 15.0, 30.0, 0.0, 30.0, 30.0);
        leftTriangle.setFill(Color.DIMGREY);
        leftTriangle.setOnMousePressed(e -> leftTriangle.setFill(Color.DIMGREY));
        
        rightTriangle.setOnMouseReleased(e -> {
            if (currentIndex.get() == SSIDList.size()-1)
                rightTriangle.setFill(Color.DIMGREY);
            else {
                currentIndex.set(currentIndex.get()+1);
                if(currentIndex.get() == SSIDList.size()-1)
                    rightTriangle.setFill(Color.DIMGREY);
                else
                    rightTriangle.setFill(Color.BLACK);
                leftTriangle.setFill(Color.BLACK);
            } // else
        }); // setOnMouseReleased
        
        
        leftTriangle.setOnMouseReleased(e -> {
            if (currentIndex.get() == 0)
                leftTriangle.setFill(Color.DIMGREY);
            else {
                currentIndex.set(currentIndex.get()-1);
                if (currentIndex.get() == 0)
                    leftTriangle.setFill(Color.DIMGREY);
                else
                    leftTriangle.setFill(Color.BLACK);
                rightTriangle.setFill(Color.BLACK);
            } // else
        }); // setOnMouseReleased
        
        
        // Updates all labels when changing clients
        currentIndex.addListener((obs, ov, nv) -> {
            indexLabel.setText(currentIndex.get()+1 + "/" + SSIDList.size());
            typeLabelDetails.setText(SSIDList.get(currentIndex.get()).getType());
            SSIDLabelDetails.setText(SSIDList.get(currentIndex.get()).getSSIDName());
            firstSeenLabelDetails.setText(SSIDList.get(currentIndex.get()).getFirstSeen());
            lastSeenLabelDetails.setText(SSIDList.get(currentIndex.get()).getLastSeen());
            maxRateLabelDetails.setText(String.valueOf(SSIDList.get(currentIndex.get()).getMaxRate()));
            beaconLabelDetails.setText(String.valueOf(SSIDList.get(currentIndex.get()).getBeacon()));
            packetsLabelDetails.setText(String.valueOf(SSIDList.get(currentIndex.get()).getPackets()));
            WPSLabelDetails.setText(SSIDList.get(currentIndex.get()).getWPS());
            WPSManufLabelDetails.setText(SSIDList.get(currentIndex.get()).getWPSManuf());
            devNameLabelDetails.setText(SSIDList.get(currentIndex.get()).getDevName());
            modelNameLabelDetails.setText(SSIDList.get(currentIndex.get()).getModelName());
            modelNumLabelDetails.setText(SSIDList.get(currentIndex.get()).getModelNum());
            WPAVersionLabelDetails.setText(SSIDList.get(currentIndex.get()).getWPAVersion());
            
            
            grid.getChildren().remove(encryptionLabelDetails);
            grid.getChildren().remove(encryptionTree);
            if (SSIDList.get(currentIndex.get()).getNumEncryptions() <= 1) {
                encryptionLabelDetails.setText(SSIDList.get(currentIndex.get()).getEncryption_plain());
                grid.add(encryptionLabelDetails, 1, 12);
            }
            else {
                encryptionRoot.getChildren().removeAll(encryptionRoot.getChildren());
                for (int i = 0; i < SSIDList.get(currentIndex.get()).getNumEncryptions(); i++)
                    makeBranch(SSIDList.get(currentIndex.get()).getEncryptionList().get(i), encryptionRoot);
                grid.add(encryptionTree, 1, 12);
            } // else
            
        }); // Integer property listener
        
        
        
        closeButton.setOnAction(e -> {
            window.close();
        });
        
        window.setOnCloseRequest(e -> {
            e.consume();
            window.close();
        });
        
        VBox layout = new VBox(4);
        layout.setPadding(new Insets(0, 0, 5, 0));
        HBox arrowMenu = new HBox();
        arrowMenu.getChildren().addAll(leftTriangle, indexLabel, rightTriangle);
        arrowMenu.setAlignment(Pos.CENTER);
        indexLabel.setPadding(new Insets(0, 20, 0, 20));
        
        HBox buttonMenu = new HBox();
        buttonMenu.getChildren().addAll(closeButton);
        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setSpacing(20);
        
        layout.getChildren().addAll(headerLabel, grid, arrowMenu, buttonMenu);
        layout.setAlignment(Pos.TOP_CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // displays to the user and waits for it to be closed before returning to caller
    } // display
    
    public static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        
        return item;
    } // makeBranch
    
    public static void changeHeight(TreeView<String> tree) {
            tree.setMaxHeight(SINGLE_PARENT_WIDTH);
    } // changeHeight

} // SSIDWindow
