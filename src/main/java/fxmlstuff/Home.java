package fxmlstuff;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.VBox;

public class Home extends VBox implements Initializable{
    //Static listener instance of DockletListener
    private static DockletListener listener;

    @FXML
    private void handleRemoveAction(ActionEvent event) {  
        if(listener != null){       
            listener.removed("home"); 
        }                
    }

    @FXML
    private void handleAction(ActionEvent event) {
        if(listener != null){       
             listener.added(ButtonBuilder.create()
                    .text("Home")
                    .id("home")
                    .build()); 
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }

    public void addListener(DockletListener listener){
        Home.listener = listener;
    }   

}