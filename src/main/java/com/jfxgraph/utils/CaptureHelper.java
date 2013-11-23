package com.jfxgraph.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javafx.animation.ScaleTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.imageio.ImageIO;

/**
 * 应用程序截图捕获工具。
 * 
 * http://edu.makery.ch/blog/2013/01/04/javafx-snapshot-as-png-image/
 * 
 * @author Albert
 */
public abstract class CaptureHelper
{
    /**
     * 截屏效果.
     * <p>
     * 截屏效果加载后，文件保存
     * </p>
     * 
     * @since 3.1
     * @param printStage
     */
    public static void printScreenEffect(final Stage printStage)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
       
        Rectangle rectangle = new Rectangle(0, 0, dimension.getWidth(), dimension.getHeight());
        rectangle.setFill(Color.GRAY);
        
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:white");
        
        stackPane.getChildren().add(rectangle);

        final Stage stage =  new Stage();
        
        stage.setX(0.0);
        stage.setY(0.0);
        stage.setWidth(dimension.getWidth());
        stage.setHeight(dimension.getHeight());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(stackPane));
        
        stage.show();

        final ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(rectangle);
        scaleTransition.durationProperty().set(Duration.millis(200));
        scaleTransition.toXProperty().set(0.0);
        scaleTransition.toYProperty().set(0.0);
        
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                stage.close();
                try
                {
                    saveImageFile(sceneSnapshot(printStage), printStage);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                actionEvent.consume();
            }
        });

        scaleTransition.play();
    }

    /**
     * 获取scene截图.
     */
    private static WritableImage sceneSnapshot(Stage stage)
    {
        // Optimized by Albert: Use javaFX Scene API Complete Snapshot.
        if (null != stage && stage.sceneProperty() != null)
        {
            Scene currentScene = stage.sceneProperty().get();

            int width = (int) currentScene.widthProperty().get();
            int height = (int) currentScene.heightProperty().get();

            WritableImage image = currentScene.snapshot(new WritableImage(width, height));

            return image;
        }

        // 返回空图片
        return new WritableImage(800, 600);
    }

    /**
     * 文件保存.
     * <p>
     * 1. 弹出文件保存框，选择文件保存路径和输入文件名称.<br>
     * 2. 文件保存
     * </p>
     * 
     * @since 3.1
     * @param image
     *            要保存的文件对象.
     * @param stage
     * @throws IOException
     */
    private static void saveImageFile(WritableImage writableImage, Stage stage) throws IOException
    {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null)
        {
            /**
             * 校验文件是否为指定类型，不是转为指定类型.
             */
            String fileName = file.getName();

            if (!fileName.toUpperCase().endsWith(".PNG"))
            {
                file = new File(file.getAbsolutePath() + ".png");
            }

            // PixelReader pixelReader = image.getPixelReader();
            // int width = (int) image.getWidth();
            // int height = (int) image.getHeight();
            // WritableImage writableImage = new WritableImage(pixelReader, width, height);

            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        }
    }
}
