package imageEditor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TransparentStage extends Application implements EventHandler<MouseEvent>
{
    private Double height;
    private Double width;
    private Polygon region;
    private RegionHelper helper;
    private Button capture;
    private Editor editor;
    private Stage stage;

    public RegionHelper getRegionHelper()
    {
        return this.helper;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private void animateCaptureButton()
    {
        this.capture.setVisible(true);
        this.capture.setLayoutX(this.helper.getCenterX().doubleValue() - this.capture.getLayoutBounds().getWidth() / 2.0D);
        this.capture.setLayoutY(this.helper.getCenterY().doubleValue() - 30.0D - this.capture.getLayoutBounds().getHeight() / 2.0D);
        Timeline t = new Timeline();

        t.getKeyFrames().addAll(
                new KeyFrame[] { new KeyFrame(Duration.millis(300.0D), new KeyValue[] { new KeyValue(this.capture.layoutYProperty(), Double.valueOf(this.helper.getCenterY().doubleValue()
                        - this.capture.getLayoutBounds().getHeight() / 2.0D), Interpolator.EASE_BOTH) }) });

        t.play();
    }

    public void start(Stage primaryStage)
    {
        System.setProperty("java.awt.headless", "false");

        this.editor = new Editor();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        this.stage = primaryStage;
        this.height = Double.valueOf(primaryScreenBounds.getHeight());
        this.width = Double.valueOf(primaryScreenBounds.getWidth());

        this.capture = new Button("Capture");

        this.capture.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                final int x = TransparentStage.this.helper.getInitX().intValue();
                final int y = TransparentStage.this.helper.getInitY().intValue();
                final int w = TransparentStage.this.helper.getWidth().intValue();
                final int h = TransparentStage.this.helper.getHeight().intValue();

                final Thread th = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(100L);
                            Robot r = new Robot();
                            BufferedImage im = r.createScreenCapture(new java.awt.Rectangle(x, y, w, h));

                            final WritableImage fxImg = new WritableImage(w, h);
                            SwingFXUtils.toFXImage(im, fxImg);

                            Platform.runLater(new Runnable()
                            {
                                public void run()
                                {
                                    TransparentStage.this.editor.setImage(fxImg);
                                    TransparentStage.this.editor.setInitialStage(TransparentStage.this);
                                    TransparentStage.this.editor.show();
                                    TransparentStage.this.stage.hide();
                                }
                            });
                        } catch (InterruptedException ex)
                        {
                            Logger.getLogger(TransparentStage.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (AWTException ex)
                        {
                            Logger.getLogger(TransparentStage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                Platform.runLater(new Runnable()
                {
                    public void run()
                    {
                        TransparentStage.this.stage.getScene().getRoot().setVisible(false);
                        TransparentStage.this.stage.setIconified(true);
                        th.start();
                    }
                });

            }
        });
        
        this.capture.getStyleClass().add("btn");
        this.capture.setVisible(false);
        this.capture.setLayoutX(0.0D);
        this.capture.setLayoutY(0.0D);

        this.region = new Polygon();
        this.region.setFill(Color.TRANSPARENT);
        this.region.setStrokeDashOffset(50.0D);
        this.region.setStrokeDashOffset(45.0D);
        this.region.getStrokeDashArray().addAll(new Double[] { Double.valueOf(5.0D), Double.valueOf(10.0D) });
        this.region.setStrokeType(StrokeType.INSIDE);
        this.region.setStrokeWidth(2.0D);
        this.region.setStroke(Color.RED);

        this.helper = new RegionHelper();

        Group root = new Group();

        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(this.width.doubleValue(), this.height.doubleValue());
        rect.setOpacity(0.51D);
        rect.setCursor(Cursor.CROSSHAIR);

        Text t = new Text("Please create rectangular region for screenshot");
        t.setFill(Color.WHITE);
        t.setFont(Font.font(Font.getDefault().getName(), 16.0D));
        t.setX(this.width.doubleValue() / 2.0D - t.getLayoutBounds().getWidth() / 2.0D);
        t.setY(this.height.doubleValue() / 2.0D);

        root.getChildren().addAll(new Node[] { rect, this.region, this.capture, t });

        Scene scene = new Scene(root, this.width.doubleValue(), this.height.doubleValue());
        scene.setFill(Color.TRANSPARENT);
        scene.setOnMousePressed(this);
        scene.setOnMouseDragged(this);
        scene.setOnMouseReleased(this);

        scene.getStylesheets().add(TransparentStage.class.getResource("css/gui.css").toExternalForm());

        this.stage.getIcons().add(new Image(getClass().getResource("img/logo.png").toExternalForm()));
        if (Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW))
        {
            System.out.println("Set Transparent");
            this.stage.initStyle(StageStyle.TRANSPARENT);
        } else
        {
            System.out.println("Set Non Transparent");
            this.stage.initStyle(StageStyle.DECORATED);
        }
        this.stage.setTitle("Feather Edit");

        this.stage.setScene(scene);
        this.stage.show();
    }

    public void show()
    {
        this.stage.setIconified(false);
        this.stage.getScene().getRoot().setVisible(true);
        this.stage.show();
        this.stage.toFront();
    }

    public void close()
    {
        this.stage.close();
    }

    public void handle(MouseEvent event)
    {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
        {
            this.capture.setVisible(false);
            this.region.getPoints().clear();
            this.helper.setInitXY(event.getScreenX(), event.getScreenY());

            this.region.getPoints().setAll(this.helper.getPoints(event.getScreenX(), event.getSceneX(), event.getScreenY(), event.getSceneY()));
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
        {
//            double w = event.getX() - this.region.getLayoutX();
//            double h = event.getY() - this.region.getLayoutY();

            this.helper.setFinalXY(event.getScreenX(), event.getScreenY());

            this.region.getPoints().setAll(this.helper.getPoints(event.getScreenX(), event.getSceneX(), event.getScreenY(), event.getSceneY()));
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
        {
            if (!this.helper.isInitEqualFinal())
            {
                animateCaptureButton();
            }
        }
    }
}
