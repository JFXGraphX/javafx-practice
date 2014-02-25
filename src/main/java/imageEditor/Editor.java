package imageEditor;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Editor
{
    private Scene scene;
    private Stage stage;
    private TransparentStage previousStage;
    private ImageEditorController controller;

    public Editor()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("imageEditor.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            this.controller = ((ImageEditorController) loader.getController());
            this.controller = ((ImageEditorController) loader.getController());
            this.controller.setEditor(this);
            
            this.scene = new Scene(root, 500.0D, 500.0D);
            this.stage = new Stage(); 
            this.stage.setScene(this.scene);
            this.stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent t)
                {
                    Editor.this.previousStage.close();
                    Editor.this.stage.close();
                }
            });
            this.controller.getImageFormat().getItems().setAll(new Object[] { "jpg", "gif", "png" });
            this.controller.getImageFormat().getSelectionModel().selectFirst();
        } catch (IOException ex)
        {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void swapStage()
    {
        this.stage.hide();

        this.previousStage.show();
    }

    public void setInitialStage(TransparentStage s)
    {
        this.previousStage = s;
    }

    public RegionHelper getRegionHelper()
    {
        return this.previousStage.getRegionHelper();
    }

    public void setImage(Image im)
    {
        this.controller.getImageView().setImage(im);
        this.controller.image = im;
        Persistence.setOriginalImage(new ImagePersistence(new Date(), im));
        
//        Screen sc = Screen.getPrimary();

        this.stage.getIcons().add(new Image(getClass().getResource("img/logo.png").toExternalForm()));

        this.stage.setMinHeight(500.0D);
        this.stage.setMinWidth(640.0D);
        this.stage.setTitle("Feather Edit");
        if ((im.getHeight() < 450.0D) && (im.getWidth() < 550.0D))
        {
            this.controller.setResize(false);
        } else
            this.controller.setResize(true);
    }

    public Scene getScene()
    {
        return this.scene;
    }

    public void show()
    {
        this.stage.show();
        this.stage.toFront();
    }

}
