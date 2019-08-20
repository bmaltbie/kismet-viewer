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


// Window that is opened when 'Clients' button is pressed. Displays the information for all clients
// that are associated with the selected network. Only one client is displayed at a time and the
// user can cycle through each client to view them.
public class ClientsWindow {
    final static int SINGLE_PARENT_WIDTH = 27; // If the dropdown view (tree view) only has one item
    final static int SINGLE_CHILD_WIDTH = 26; // if the dropdown view (tree view) has >1 items

    public static void display(ArrayList<Client> clientList) {
        Stage window = new Stage();
        window.setTitle("Clients");
        window.setMinWidth(400);
        window.setMinHeight(620);
        window.setMaxWidth(400);
        window.setMaxHeight(620);
        

        TreeView<String> frequencyTree, carrierTree, encodingTree;
        IntegerProperty currentIndex = new SimpleIntegerProperty(0); // which client is being viewed
        
        // Header
        Label headerLabel = new Label("Clients");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        headerLabel.setPadding(new Insets(10, 0, 10, 0));
        
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(110));
        grid.setVgap(5);
        grid.setHgap(5);
        
        // Manufacturer
        Label manufLabel = new Label("Manuf: ");
        GridPane.setConstraints(manufLabel, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label manufLabelDetails = new Label(clientList.get(currentIndex.get()).getManuf());
        GridPane.setConstraints(manufLabelDetails, 1, 0);
        
        // First seen
        Label firstSeenLabel = new Label("First: ");
        GridPane.setConstraints(firstSeenLabel, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label firstSeenLabelDetails = new Label(clientList.get(currentIndex.get()).getFirstSeen());
        GridPane.setConstraints(firstSeenLabelDetails, 1, 1);
        
        // Last seen
        Label lastSeenLabel = new Label("Last: ");
        GridPane.setConstraints(lastSeenLabel, 0, 2, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label lastSeenLabelDetails = new Label(clientList.get(currentIndex.get()).getLastSeen());
        GridPane.setConstraints(lastSeenLabelDetails, 1, 2);
        
        // Type
        Label typeLabel = new Label("Type: ");
        GridPane.setConstraints(typeLabel, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label typeLabelDetails = new Label(clientList.get(currentIndex.get()).getType());
        GridPane.setConstraints(typeLabelDetails, 1, 3);
        
        // MAC
        Label MACLabel = new Label("MAC: ");
        GridPane.setConstraints(MACLabel, 0, 4, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label MACLabelDetails = new Label(clientList.get(currentIndex.get()).getMAC());
        GridPane.setConstraints(MACLabelDetails, 1, 4);
        
        // SSID
        Label SSIDLabel = new Label("SSID: ");
        GridPane.setConstraints(SSIDLabel, 0, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label SSIDLabelDetails = new Label(clientList.get(currentIndex.get()).getSSIDCount());
        GridPane.setConstraints(SSIDLabelDetails, 1, 5);
        
        // Channel
        Label channelLabel = new Label("Channel: ");
        GridPane.setConstraints(channelLabel, 0, 6, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label channelLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getChannel()));
        GridPane.setConstraints(channelLabelDetails, 1, 6);
        
        
        // Frequency - [TREE VIEW]
        Label frequencyLabel = new Label("Frequency: ");
        Label frequencyLabelDetails = new Label("Frequency Details");
        TreeItem<String> frequencyRoot = new TreeItem<>("Frequency List");
        boolean oneFrequency = false;
        
        // If there is only one frequency, will display it as a simple label, not a tree view
        if (clientList.get(currentIndex.get()).getNumFrequencies() <= 1) {
            frequencyLabelDetails.setText(clientList.get(currentIndex.get()).getFrequency());
            oneFrequency = true;
        }
        else {
            for (int i = 0; i < clientList.get(currentIndex.get()).getNumFrequencies(); i++)
                makeBranch(clientList.get(currentIndex.get()).getFrequencyList().get(i), frequencyRoot);
        } // else
        
        frequencyTree = new TreeView<>(frequencyRoot);
        frequencyTree.setMinHeight(SINGLE_PARENT_WIDTH);
        frequencyTree.setPrefHeight(SINGLE_PARENT_WIDTH);
        frequencyTree.setMaxHeight(SINGLE_PARENT_WIDTH + 2*SINGLE_CHILD_WIDTH);
        grid.addRow(7, frequencyLabel, (oneFrequency) ? frequencyLabelDetails : frequencyTree);
        GridPane.setConstraints(frequencyLabel, 0, 7, 1, 1, HPos.RIGHT, VPos.CENTER);

        
        // Max seen
        Label maxSeenLabel = new Label("Max Seen: ");
        GridPane.setConstraints(maxSeenLabel, 0, 8, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label maxSeenLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getMaxSeen()));
        GridPane.setConstraints(maxSeenLabelDetails, 1, 8);
        
        
        // Carrier - [TREE VIEW]
        Label carrierLabel = new Label("Carrier: ");
        Label carrierLabelDetails = new Label("Carrier Details");
        TreeItem<String> carrierRoot = new TreeItem<>("Carrier List");
        boolean oneCarrier = false;
        
        // If there is only zero/one carrier, will display it as a simple label, not a tree view
        if (clientList.get(currentIndex.get()).getNumCarriers() <= 1) {
            carrierLabelDetails.setText(clientList.get(currentIndex.get()).getCarrier());
            oneCarrier = true;
        }
        else {
            for (int i = 0; i < clientList.get(currentIndex.get()).getNumCarriers(); i++)
                makeBranch(clientList.get(currentIndex.get()).getCarrierList().get(i), carrierRoot);
        } // else
        
        carrierTree = new TreeView<>(carrierRoot);
        carrierTree.setMinHeight(SINGLE_PARENT_WIDTH);
        carrierTree.setPrefHeight(SINGLE_PARENT_WIDTH);
        carrierTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
        grid.addRow(9, carrierLabel, (oneCarrier) ? carrierLabelDetails : carrierTree);
        GridPane.setConstraints(carrierLabel, 0, 9, 1, 1, HPos.RIGHT, VPos.CENTER);
        
        
        // Encoding - [TREE VIEW]
        Label encodingLabel = new Label("Encoding: ");
        Label encodingLabelDetails = new Label("Encoding Details");
        TreeItem<String> encodingRoot = new TreeItem<>("Encoding List");
        boolean oneEncoding = false;
        
        // If there is only zero/one encoding, will display it as a simple label, not a tree view
        if (clientList.get(currentIndex.get()).getNumEncodings() <= 1) {
            encodingLabelDetails.setText(clientList.get(currentIndex.get()).getEncoding());
            oneEncoding = true;
        }
        else {
            for (int i = 0; i < clientList.get(currentIndex.get()).getNumEncodings(); i++)
                makeBranch(clientList.get(currentIndex.get()).getEncodingList().get(i), encodingRoot);
        } // else

        encodingTree = new TreeView<>(encodingRoot);
        encodingTree.setMinHeight(SINGLE_PARENT_WIDTH);
        encodingTree.setPrefHeight(SINGLE_PARENT_WIDTH);
        encodingTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
        grid.addRow(10, encodingLabel, (oneEncoding) ? encodingLabelDetails : encodingTree);
        GridPane.setConstraints(encodingLabel, 0, 10, 1, 1, HPos.RIGHT, VPos.CENTER);

        
        // LLC
        Label LLCLabel = new Label("LLC: ");
        GridPane.setConstraints(LLCLabel, 0, 11, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label LLCLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getLLC()));
        GridPane.setConstraints(LLCLabelDetails, 1, 11);
        
        // Data
        Label dataLabel = new Label("Data: ");
        GridPane.setConstraints(dataLabel, 0, 12, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label dataLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getData()));
        GridPane.setConstraints(dataLabelDetails, 1, 12);
        
        // Crypt
        Label cryptLabel = new Label("Crypt: ");
        GridPane.setConstraints(cryptLabel, 0, 13, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label cryptLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getCrypt()));
        GridPane.setConstraints(cryptLabelDetails, 1, 13);
        
        // Fragments
        Label fragmentsLabel = new Label("Fragments: ");
        GridPane.setConstraints(fragmentsLabel, 0, 14, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label fragmentsLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getFragments()));
        GridPane.setConstraints(fragmentsLabelDetails, 1, 14);
        
        // Retries
        Label retriesLabel = new Label("Retries: ");
        GridPane.setConstraints(retriesLabel, 0, 15, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label retriesLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getRetries()));
        GridPane.setConstraints(retriesLabelDetails, 1, 15);
        
        // Total
        Label totalLabel = new Label("Total: ");
        GridPane.setConstraints(totalLabel, 0, 16, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label totalLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getTotal()));
        GridPane.setConstraints(totalLabelDetails, 1, 16);
        
        // Datasize
        Label datasizeLabel = new Label("Datasize: ");
        GridPane.setConstraints(datasizeLabel, 0, 17, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label datasizeLabelDetails = new Label(String.valueOf(clientList.get(currentIndex.get()).getDatasize()));
        GridPane.setConstraints(datasizeLabelDetails, 1, 17);
        
        // SeenBy
        Label seenByLabel = new Label("Seen By: ");
        GridPane.setConstraints(seenByLabel, 0, 18, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label seenByLabelDetails = new Label(clientList.get(currentIndex.get()).getSeenBy_Formatted());
        seenByLabelDetails.setFont(Font.font("System", 10));
        seenByLabelDetails.setWrapText(true);
        GridPane.setConstraints(seenByLabelDetails, 1, 18);
        

        
        
        // LISTENERS
        // Honestly, I'm not sure why this works, it's not supposed to. It should be tied to the
        // new value (newVal) but for some reason that makes it backwards. Also, not sure why
        // it doesn't work the first time you expand the TreeView.
        frequencyTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            frequencyTree.setMaxHeight(SINGLE_PARENT_WIDTH + 2*SINGLE_CHILD_WIDTH);
            frequencyTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(carrierTree);
            changeHeight(encodingTree);
        }); // frequencyTree Listener
    
        
        carrierTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            carrierTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
            carrierTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(frequencyTree);
            changeHeight(encodingTree);
        }); // carrierTree Listener
        
        
        encodingTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            encodingTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
            encodingTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(frequencyTree);
            changeHeight(carrierTree);
        }); // encodingTree Listener
        
        
        
        grid.getChildren().addAll(manufLabel, firstSeenLabel, lastSeenLabel, typeLabel, MACLabel,
                                  SSIDLabel, channelLabel, /*frequencyLabel,*/ maxSeenLabel, /*carrierLabel,*/
                                  /*encodingLabel,*/ LLCLabel, dataLabel, cryptLabel, fragmentsLabel,
                                  retriesLabel, totalLabel, datasizeLabel, seenByLabel);
        
        grid.getChildren().addAll(manufLabelDetails, firstSeenLabelDetails, lastSeenLabelDetails,
                                  typeLabelDetails, MACLabelDetails, SSIDLabelDetails, channelLabelDetails,
                                  /*frequencyLabelDetails,*/ maxSeenLabelDetails, /*carrierLabelDetails,*/
                                  /*encodingLabelDetails,*/ LLCLabelDetails, dataLabelDetails, cryptLabelDetails,
                                  fragmentsLabelDetails, retriesLabelDetails, totalLabelDetails,
                                  datasizeLabelDetails, seenByLabelDetails);
        
        Button SSIDButton = new Button("SSID(s)");
        Button closeButton = new Button("Close");
        Label indexLabel = new Label("1/" + clientList.size());
        
        if (clientList.get(currentIndex.get()).getNumSSIDs() == 0)
            SSIDButton.setDisable(true);
        
        // Right and left triangles are used to cycle through the clients (act as arrows)
        Polygon rightTriangle = new Polygon();
        rightTriangle.getPoints().setAll(0.0, 0.0, 30.0, 15.0, 0.0, 30.0);
        rightTriangle.setOnMousePressed(e -> rightTriangle.setFill(Color.DIMGREY));
        if (clientList.size() == 1)
            rightTriangle.setFill(Color.DIMGREY);
        
        Polygon leftTriangle = new Polygon();
        leftTriangle.getPoints().setAll(0.0, 15.0, 30.0, 0.0, 30.0, 30.0);
        leftTriangle.setFill(Color.DIMGREY);
        leftTriangle.setOnMousePressed(e -> leftTriangle.setFill(Color.DIMGREY));
        
        rightTriangle.setOnMouseReleased(e -> {
            if (currentIndex.get() == clientList.size()-1)
                rightTriangle.setFill(Color.DIMGREY);
            else {
                currentIndex.set(currentIndex.get()+1);
                if(currentIndex.get() == clientList.size()-1)
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
            indexLabel.setText(currentIndex.get()+1 + "/" + clientList.size());
            manufLabelDetails.setText(clientList.get(currentIndex.get()).getManuf());
            firstSeenLabelDetails.setText(clientList.get(currentIndex.get()).getFirstSeen());
            lastSeenLabelDetails.setText(clientList.get(currentIndex.get()).getLastSeen());
            typeLabelDetails.setText(clientList.get(currentIndex.get()).getType());
            MACLabelDetails.setText(clientList.get(currentIndex.get()).getMAC());
            SSIDLabelDetails.setText(clientList.get(currentIndex.get()).getSSIDCount());
            channelLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getChannel()));
            maxSeenLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getMaxSeen()));
            LLCLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getLLC()));
            dataLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getData()));
            cryptLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getCrypt()));
            fragmentsLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getFragments()));
            retriesLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getRetries()));
            totalLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getTotal()));
            datasizeLabelDetails.setText(String.valueOf(clientList.get(currentIndex.get()).getDatasize()));
            seenByLabelDetails.setText(clientList.get(currentIndex.get()).getSeenBy_Formatted());
            
            // change between static label and tree view if necessary
            grid.getChildren().remove(frequencyLabelDetails);
            grid.getChildren().remove(frequencyTree);
            if (clientList.get(currentIndex.get()).getNumFrequencies() <= 1) {
                frequencyLabelDetails.setText(clientList.get(currentIndex.get()).getFrequency());
                grid.add(frequencyLabelDetails, 1, 7);
            }
            else {
                frequencyRoot.getChildren().removeAll(frequencyRoot.getChildren());
                for (int i = 0; i < clientList.get(currentIndex.get()).getNumFrequencies(); i++)
                    makeBranch(clientList.get(currentIndex.get()).getFrequencyList().get(i), frequencyRoot);
                grid.add(frequencyTree, 1, 7);
            } // else
            
            grid.getChildren().remove(carrierLabelDetails);
            grid.getChildren().remove(carrierTree);
            if (clientList.get(currentIndex.get()).getNumCarriers() <= 1) {
                carrierLabelDetails.setText(clientList.get(currentIndex.get()).getCarrier());
                grid.add(carrierLabelDetails, 1, 9);
            }
            else {
                carrierRoot.getChildren().removeAll(carrierRoot.getChildren());
                for (int i = 0; i < clientList.get(currentIndex.get()).getNumCarriers(); i++)
                    makeBranch(clientList.get(currentIndex.get()).getCarrierList().get(i), carrierRoot);
                grid.add(carrierTree, 1, 9);
            } // else
            
            grid.getChildren().remove(encodingLabelDetails);
            grid.getChildren().remove(encodingTree);
            if (clientList.get(currentIndex.get()).getNumEncodings() <= 1) {
                encodingLabelDetails.setText(clientList.get(currentIndex.get()).getEncoding());
                grid.add(encodingLabelDetails, 1, 10);
            }
            else {
                encodingRoot.getChildren().removeAll(encodingRoot.getChildren());
                for (int i = 0; i < clientList.get(currentIndex.get()).getNumEncodings(); i++)
                    makeBranch(clientList.get(currentIndex.get()).getEncodingList().get(i), encodingRoot);
                grid.add(encodingTree, 1, 10);
            } // else
            
            if (clientList.get(currentIndex.get()).getNumSSIDs() == 0)
                SSIDButton.setDisable(true);
            else
                SSIDButton.setDisable(false);
            
        }); // Integer property listener
        
        
        SSIDButton.setOnAction(e -> {
            SSIDWindow.display(clientList.get(currentIndex.get()).getSSIDClientList());
        });
        
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
        buttonMenu.getChildren().addAll(SSIDButton, closeButton);
        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setSpacing(20);
        
        layout.getChildren().addAll(headerLabel, grid, arrowMenu, buttonMenu);
        layout.setAlignment(Pos.TOP_CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // displays to the user and waits for it to be closed before returning to caller
    } // display
    
    
    // Helper function to make a branch for the passed in tree view
    public static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(false);
        parent.getChildren().add(item);
        
        return item;
    } // makeBranch
    
    
    // Helper function to change tree view heights as the user operates each displayed tree view
    // Needed so the information doesn't extend past the bottom of the GUI window, also just to
    // make it look prettier.
    public static void changeHeight(TreeView<String> tree) {
            tree.setMaxHeight(SINGLE_PARENT_WIDTH);
    } // changeHeight

} // ClientsWindow
