import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// KismetViewer; this file is what is executed to start the application. 
public class KismetViewer extends Application {
   @Override
   public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("KismetViewer.fxml"));
      
      Scene scene = new Scene(root);
      stage.setTitle("KismetViewer");
      stage.setScene(scene);
      stage.show();
   } // start

   public static void main(String[] args) {
      launch(args);
   } // main
} // KismetViewer
