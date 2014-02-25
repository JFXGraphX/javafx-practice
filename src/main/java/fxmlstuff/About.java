package fxmlstuff;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * @author Narayan
 */
public class About extends VBox implements Initializable {

    
    private static DockletListener listener;

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    


    public void addListener(DockletListener listener){
        About.listener = listener;
    }   
}