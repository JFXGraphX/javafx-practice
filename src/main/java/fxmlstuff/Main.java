package fxmlstuff;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Polygon;

public class Main extends BorderPane implements Initializable, DockletListener{

    //FXML ATTRIBUTES
    @FXML
    private Home homeContent;
    @FXML
    private About aboutContent;    
    @FXML
    private Polygon dock_bottom;    
    @FXML
    private FlowPane dockPanel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // Adding Swing style of custom Listener 
        aboutContent.addListener(this);
        homeContent.addListener(this); 

        //This is for Dock bottom part which is a polygon
        double slide=50;
        double height =15;
        double width= 700;
        double offset = 20;        
        dock_bottom.getPoints().addAll(
                slide, 0.0,
                0.0, height,
                width,height ,
                width-slide, 0.0

        );  

        //Default Dock Item added
        added(ButtonBuilder.create()
            .text("Dock")
            .id("default")
            .build()
        );        
    }

    /**
     * This is the implemented method of Node being added to docklet
     * @param n Node
     */
    @Override
    public void added(Node n) {
        if(dockPanel.getChildren().size()<5){            
            //Let's assume currently we make dock item of only Button
            final Button b = (Button) n;
            b.setPrefHeight(50);         
            b.setPrefWidth(60);  
            b.getStyleClass().add("dock-item"); 
            dockPanel.getChildren().add(b);            
        }
    }

    /**
     * This is the implemented method of Node being removed from docklet
     * @param n String
     */
    @Override
    public void removed(String id) {   
        Node rm = null;
        //Checking for dock item according to it's ID
        for(Node n:dockPanel.getChildren()){            
            if(n.getId().equals(id)){
                rm = n;                
                break;
            }            
        }        

        if(rm!=null){
            final Button b = (Button)rm;
            dockPanel.getChildren().remove(b);
        }        
    }    
}