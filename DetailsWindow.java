import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


// Window that is opened when 'Details' button is pressed. Displays the information for the selected
// network in the table. Has buttons to view the Clients and SSIDs for the selected network which
// open additional windows.
public class DetailsWindow {
    final static int SINGLE_PARENT_WIDTH = 27; // If the dropdown view (tree view) only has one item
    final static int SINGLE_CHILD_WIDTH = 26; // if the dropdown view (tree view) has >1 items

    public static void display(Network network) {
        Stage window = new Stage();
        window.setTitle("Details");
        window.setMinWidth(400);
        window.setMinHeight(700);
        window.setMaxWidth(400);
        window.setMaxHeight(700);

        TreeView<String> frequencyTree, carrierTree, encodingTree, alertTree;
        
        // Header
        Label headerLabel = new Label("Details");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        headerLabel.setPadding(new Insets(10, 0, 10, 0));
        
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(110));
        grid.setVgap(5);
        grid.setHgap(5);
        
        // Manufacturer
        Label manufLabel = new Label("Manuf: ");
        GridPane.setConstraints(manufLabel, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label manufLabelDetails = new Label(network.getManuf());
        GridPane.setConstraints(manufLabelDetails, 1, 0);
        
        // First seen
        Label firstSeenLabel = new Label("First: ");
        GridPane.setConstraints(firstSeenLabel, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label firstSeenLabelDetails = new Label(network.getFirstSeen());
        GridPane.setConstraints(firstSeenLabelDetails, 1, 1);
        
        // Last seen
        Label lastSeenLabel = new Label("Last: ");
        GridPane.setConstraints(lastSeenLabel, 0, 2, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label lastSeenLabelDetails = new Label(network.getLastSeen());
        GridPane.setConstraints(lastSeenLabelDetails, 1, 2);
        
        // Type
        Label typeLabel = new Label("Type: ");
        GridPane.setConstraints(typeLabel, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label typeLabelDetails = new Label(network.getType());
        GridPane.setConstraints(typeLabelDetails, 1, 3);
        
        // BSSID
        Label BSSIDLabel = new Label("BSSID: ");
        GridPane.setConstraints(BSSIDLabel, 0, 4, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label BSSIDLabelDetails = new Label(network.getBSSID());
        GridPane.setConstraints(BSSIDLabelDetails, 1, 4);
        
        // SSID - [TREE VIEW]
        Label SSIDLabel = new Label("SSID: ");
        GridPane.setConstraints(SSIDLabel, 0, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label SSIDLabelDetails = new Label(network.getSSIDCount());
        GridPane.setConstraints(SSIDLabelDetails, 1, 5);
        
        // Channel
        Label channelLabel = new Label("Channel: ");
        GridPane.setConstraints(channelLabel, 0, 6, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label channelLabelDetails = new Label(String.valueOf(network.getChannel()));
        GridPane.setConstraints(channelLabelDetails, 1, 6);
        
        
        // Frequency - [TREE VIEW]
        Label frequencyLabel = new Label("Frequency: ");
        Label frequencyLabelDetails = new Label("Frequency Details");
        TreeItem<String> frequencyRoot = new TreeItem<>("Frequency List");
        boolean oneFrequency = false;
        
        // If there is only one frequency, will display it as a simple label, not a tree view
        if (network.getNumFrequencies() <= 1) {
            frequencyLabelDetails.setText(network.getFrequency());
            oneFrequency = true;
        } // if
        else {
            for (int i = 0; i < network.getNumFrequencies(); i++)
                makeBranch(network.getFrequencyList().get(i), frequencyRoot);
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
        Label maxSeenLabelDetails = new Label(String.valueOf(network.getMaxSeen()));
        GridPane.setConstraints(maxSeenLabelDetails, 1, 8);
        
        
        // Carrier - [TREE VIEW]
        Label carrierLabel = new Label("Carrier: ");
        Label carrierLabelDetails = new Label("Carrier Details");
        TreeItem<String> carrierRoot = new TreeItem<>("Carrier List");
        boolean oneCarrier = false;
        
        // If there is only zero/one carrier, will display it as a simple label, not a tree view
        if (network.getNumCarriers() <= 1) {
            carrierLabelDetails.setText(network.getCarrier());
            oneCarrier = true;
        } // if
        else {
            for (int i = 0; i < network.getNumCarriers(); i++)
                makeBranch(network.getCarrierList().get(i), carrierRoot);
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
        if (network.getNumEncodings() <= 1) {
            encodingLabelDetails.setText(network.getEncoding());
            oneEncoding = true;
        } // if
        else {
            for (int i = 0; i < network.getNumEncodings(); i++)
                makeBranch(network.getEncodingList().get(i), encodingRoot);
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
        Label LLCLabelDetails = new Label(String.valueOf(network.getLLC()));
        GridPane.setConstraints(LLCLabelDetails, 1, 11);
        
        // Data
        Label dataLabel = new Label("Data: ");
        GridPane.setConstraints(dataLabel, 0, 12, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label dataLabelDetails = new Label(String.valueOf(network.getData()));
        GridPane.setConstraints(dataLabelDetails, 1, 12);
        
        // Crypt
        Label cryptLabel = new Label("Crypt: ");
        GridPane.setConstraints(cryptLabel, 0, 13, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label cryptLabelDetails = new Label(String.valueOf(network.getCrypt()));
        GridPane.setConstraints(cryptLabelDetails, 1, 13);
        
        // Fragments
        Label fragmentsLabel = new Label("Fragments: ");
        GridPane.setConstraints(fragmentsLabel, 0, 14, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label fragmentsLabelDetails = new Label(String.valueOf(network.getFragments()));
        GridPane.setConstraints(fragmentsLabelDetails, 1, 14);
        
        // Retries
        Label retriesLabel = new Label("Retries: ");
        GridPane.setConstraints(retriesLabel, 0, 15, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label retriesLabelDetails = new Label(String.valueOf(network.getRetries()));
        GridPane.setConstraints(retriesLabelDetails, 1, 15);
        
        // Total
        Label totalLabel = new Label("Total: ");
        GridPane.setConstraints(totalLabel, 0, 16, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label totalLabelDetails = new Label(String.valueOf(network.getTotal()));
        GridPane.setConstraints(totalLabelDetails, 1, 16);
        
        // Datasize
        Label datasizeLabel = new Label("Datasize: ");
        GridPane.setConstraints(datasizeLabel, 0, 17, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label datasizeLabelDetails = new Label(String.valueOf(network.getDatasize()));
        GridPane.setConstraints(datasizeLabelDetails, 1, 17);
        
        // Last BSSTS
        Label lastBBSTSLabel = new Label("Last BSSTS: ");
        GridPane.setConstraints(lastBBSTSLabel, 0, 18, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label lastBSSTSLabelDetails = new Label(network.getLastBSSTS());
        GridPane.setConstraints(lastBSSTSLabelDetails, 1, 18);
        
        // SeenBy
        Label seenByLabel = new Label("Seen By: ");
        GridPane.setConstraints(seenByLabel, 0, 19, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label seenByLabelDetails = new Label(network.getSeenBy_Formatted());
        seenByLabelDetails.setFont(Font.font("System", 10));
        seenByLabelDetails.setWrapText(true);
        GridPane.setConstraints(seenByLabelDetails, 1, 19);
        
        // Alerts - [TREE VIEW]
        Label alertLabel = new Label("Alert(s): ");
        Label alertLabelDetails = new Label("Alert Details");
        TreeItem<String> alertRoot = new TreeItem<>("Alert List");
        boolean oneAlert = false;
        
        // If there is only zero/one alert, will display it as a simple label, not a tree view
        if (network.getNumAlerts() <= 1) {
            alertLabelDetails.setText(network.getAlert());
            oneAlert = true;
        } // if
        else {
            for (int i = 0; i < network.getNumAlerts(); i++)
                makeBranch(network.getAlertList().get(i), alertRoot);
        } // else
        
        alertTree = new TreeView<>(alertRoot);
        alertTree.setMinHeight(SINGLE_PARENT_WIDTH);
        alertTree.setPrefHeight(SINGLE_PARENT_WIDTH);
        alertTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
        grid.addRow(20, alertLabel, (oneAlert) ? alertLabelDetails : alertTree);
        GridPane.setConstraints(alertLabel, 0, 20, 1, 1, HPos.RIGHT, VPos.CENTER);
        
        
        // Client - [BUTTON]
        Label clientLabel = new Label("Clients: ");
        GridPane.setConstraints(clientLabel, 0, 21, 1, 1, HPos.RIGHT, VPos.CENTER);
        Label clientLabelDetails = new Label(network.getClientCount());
        GridPane.setConstraints(clientLabelDetails, 1, 21);
        
        
        
        
        // LISTENERS
        // Honestly, I'm not sure why this works, it's not supposed to. It should be tied to the
        // new value (newVal) but for some reason that makes it backwards. Also, not sure why
        // it doesn't work the first time you expand the TreeView.
        frequencyTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            frequencyTree.setMaxHeight(SINGLE_PARENT_WIDTH + 2*SINGLE_CHILD_WIDTH);
            frequencyTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(carrierTree);
            changeHeight(encodingTree);
            changeHeight(alertTree);
        }); // frequencyTree Listener
    
        
        carrierTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            carrierTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
            carrierTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(frequencyTree);
            changeHeight(encodingTree);
            changeHeight(alertTree);
        }); // carrierTree Listener
        
        
        encodingTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            encodingTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
            encodingTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(frequencyTree);
            changeHeight(carrierTree);
            changeHeight(alertTree);
        }); // encodingTree Listener
        
        
        alertTree.expandedItemCountProperty().addListener((obs, oldVal, newVal) -> {
            alertTree.setMaxHeight(SINGLE_PARENT_WIDTH + SINGLE_CHILD_WIDTH);
            alertTree.setPrefHeight(SINGLE_CHILD_WIDTH * oldVal.intValue());
            
            changeHeight(frequencyTree);
            changeHeight(carrierTree);
            changeHeight(encodingTree);
        }); // alertTree Listener
        
        
        grid.getChildren().addAll(manufLabel, firstSeenLabel, lastSeenLabel, typeLabel, BSSIDLabel,
                                  SSIDLabel, channelLabel, /*frequencyLabel,*/ maxSeenLabel, /*carrierLabel,*/
                                  /*encodingLabel,*/ LLCLabel, dataLabel, cryptLabel, fragmentsLabel,
                                  retriesLabel, totalLabel, datasizeLabel, lastBBSTSLabel, seenByLabel,
                                  /*alertLabel,*/ clientLabel);
        
        grid.getChildren().addAll(manufLabelDetails, firstSeenLabelDetails, lastSeenLabelDetails,
                                  typeLabelDetails, BSSIDLabelDetails, SSIDLabelDetails, channelLabelDetails,
                                  /*frequencyLabelDetails,*/ maxSeenLabelDetails, /*carrierLabelDetails,*/
                                  /*encodingLabelDetails,*/ LLCLabelDetails, dataLabelDetails, cryptLabelDetails,
                                  fragmentsLabelDetails, retriesLabelDetails, totalLabelDetails,
                                  datasizeLabelDetails, lastBSSTSLabelDetails, seenByLabelDetails,
                                  /*alertLabelDetails,*/ clientLabelDetails);
        
        Button clientsButton = new Button("Clients");
        Button SSIDButton = new Button("SSID(s)");
        Button closeButton = new Button("Close");
        
        if (network.getNumClients() == 0)
            clientsButton.setDisable(true);
        
        if (network.getNumSSIDs() == 0)
            SSIDButton.setDisable(true);
        
        clientsButton.setOnAction(e -> {
            ClientsWindow.display(network.getClientList());
        });
        
        SSIDButton.setOnAction(e -> {
            SSIDWindow.display(network.getSSIDNetworkList());
        });
        
        closeButton.setOnAction(e -> {
            window.close();
        });
        
        window.setOnCloseRequest(e -> {
            e.consume();
            window.close();
        });
        
        VBox layout = new VBox(3);
        layout.setPadding(new Insets(0, 0, 5, 0));
        HBox buttonMenu = new HBox();
        buttonMenu.getChildren().addAll(clientsButton, SSIDButton, closeButton);
        buttonMenu.setAlignment(Pos.CENTER);
        buttonMenu.setSpacing(20);
        
        layout.getChildren().addAll(headerLabel, grid, buttonMenu);
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

} // DetailsWindow
