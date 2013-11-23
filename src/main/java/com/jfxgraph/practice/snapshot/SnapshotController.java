package com.jfxgraph.practice.snapshot;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * @author Albert
 *
 */
public class SnapshotController implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private void processSnapshot(ActionEvent event) {
        
        System.out.println("--------");
        
        
    }

    /* (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        // TODO Auto-generated method stub

    }

}
