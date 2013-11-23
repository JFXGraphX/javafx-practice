package com.jfxgraph.practice.snapshot;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class SaveAdjustedImage extends Application
{
    public static void main(String[] args) throws Exception { launch(args); }
    @Override public void start(final Stage stage) throws Exception {
      // create an image for adjustment.
      final ImageView imageView = new ImageView(new Image(
       getClass().getResource( "Pizza-icon.png").toExternalForm()
      ));
      
      // add a status label to register program actions.
      final Label status = new Label();
      
      // add a control which can adjust the image.
      final ColorAdjust monochrome = new ColorAdjust(0, -1, 0, 0);
      final ToggleButton adjustButton = new ToggleButton("Adjust");
      adjustButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent arg0) {
          if (adjustButton.isSelected()) {
            imageView.setEffect(monochrome);
            status.setText("Applied monochrome effect.");
          } else {
            imageView.setEffect(null);
            status.setText("Removed monochrome effect.");
          }  
        }
      });
      
      // add a control to save the image.
      final Button saveButton = new Button("Save");
      saveButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent arg0) {
          try {
            String fileType = monochrome.equals(imageView.getEffect()) ? "Monochrome" : "Color";
            File output = new File("Pizza" + fileType + ".png");
            
            ImageIO.write(SwingFXUtils.fromFXImage(imageView.snapshot(null, null), null), "png", output);
   
            status.setText("Saved: " + System.getProperty("user.dir") + File.separatorChar + output.getPath());
          } catch (IOException ex) {
            System.out.println("Ah, the pizza saving failed, due to: " + ex);
          }
        }
      });
   
      // layout the scene.
      Region spring = new Region();
      VBox.setVgrow(spring, Priority.ALWAYS);
      stage.setScene(
        new Scene(
          VBoxBuilder.create().spacing(5).children(
            imageView,
            HBoxBuilder.create().spacing(10).children(
              adjustButton, saveButton
            ).build(),
            spring,
            status
          ).style("-fx-padding: 10; -fx-background-color: cornsilk;").build(),
          425, 200    
        )
      );
      stage.show();
    }
  }