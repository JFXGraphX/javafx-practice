import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
public class ScalingDemo extends Application{
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        Slider slider = new Slider(0.5,2,1);
        webView.scaleXProperty().bind(slider.valueProperty());
        webView.scaleYProperty().bind(slider.valueProperty());
        
        
        BorderPane borderPane = new BorderPane();
        
        borderPane.setTop(webView);
        borderPane.setCenter(slider);
        
//        primaryStage.setScene(new Scene(new BorderPane(webView, null, null, slider, null)));
        primaryStage.setScene(new Scene(borderPane));
        webView.getEngine().load("http://www.google.com");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}