package imageEditor;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class ImageEditorController implements Initializable
{
    private Editor editor;
    private boolean liveStop = true;
    Image image;
    ImageView imageView;
    Effect userEffect;
    private boolean resize = true;

    @FXML
    private TextArea txt_feed;

    @FXML
    private Button btn_capture;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_save_state;

    @FXML
    private Button btn_reset;

    @FXML
    private VBox box_effects;

    @FXML
    private Slider slider_bright;

    @FXML
    private Slider slider_hue;

    @FXML
    private Slider slider_sat;

    @FXML
    private Slider slider_cont;

    @FXML
    private Slider slider_glow;

    @FXML
    private Slider slider_zoom;

    @FXML
    private Slider slider_level;

    @FXML
    private AnchorPane pane_main;

    @FXML
    private AnchorPane pane_option;

    @FXML
    private AnchorPane pane_blur;

    @FXML
    private AnchorPane pane_blur_main;

    @FXML
    private AnchorPane pane_level;

    @FXML
    private AnchorPane pane_about;

    @FXML
    private StackPane pane_img;

    @FXML
    private ChoiceBox select_states;

    @FXML
    private ChoiceBox<Object> select_img_format;

    public StackPane getPane()
    {
        return this.pane_img;
    }

    public ChoiceBox<Object> getImageFormat()
    {
        return this.select_img_format;
    }

    public ImageView getImageView()
    {
        return this.imageView;
    }

    public void setResize(boolean b)
    {
        this.resize = b;
    }

    public void setEditor(Editor e)
    {
        this.editor = e;
    }

    public void reCapture(ActionEvent e)
    {
        this.editor.swapStage();
    }

    private void disableAllControls(boolean b)
    {
        this.btn_capture.setDisable(b);
        this.btn_save.setDisable(b);
        this.btn_save_state.setDisable(b);
        this.btn_reset.setDisable(b);
        this.select_states.setDisable(b);
    }

    private void updateBlurBackground()
    {
        this.pane_blur_main.getChildren().clear();
        Scene sc = this.pane_main.getScene();
        if ((sc == null) || (sc.getWindow() == null) || (!this.pane_blur_main.isVisible()))
        {
            return;
        }

        WritableImage im = new WritableImage(new Double(sc.getWindow().getWidth()).intValue(), new Double(sc.getWindow().getHeight()).intValue());

        this.pane_about.setLayoutX(sc.getWindow().getWidth() / 2.0D - this.pane_about.getWidth() / 2.0D);
        this.pane_about.setLayoutY(sc.getWindow().getHeight() / 2.0D - this.pane_about.getHeight() / 2.0D);
        this.pane_about.setVisible(false);

        this.pane_main.getScene().snapshot(im);
        ImageView img = new ImageView(im);
        img.setEffect(new BoxBlur());
        this.pane_blur_main.getChildren().add(img);
        this.pane_about.setVisible(true);
    }

    private void animateAboutPane(boolean in)
    {
        Timeline t = new Timeline();

        this.pane_blur_main.setVisible(true);

        updateBlurBackground();

        this.pane_about.setVisible(true);

        if (in)
        {
            t.getKeyFrames().addAll(
                    new KeyFrame[] { new KeyFrame(Duration.millis(0.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleXProperty(), Integer.valueOf(0)) }),
                            new KeyFrame(Duration.millis(0.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleYProperty(), Integer.valueOf(0)) }),
                            new KeyFrame(Duration.millis(200.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleXProperty(), Double.valueOf(1.1D)) }),
                            new KeyFrame(Duration.millis(200.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleYProperty(), Double.valueOf(1.1D)) }),
                            new KeyFrame(Duration.millis(250.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleXProperty(), Integer.valueOf(1)) }),
                            new KeyFrame(Duration.millis(250.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleYProperty(), Integer.valueOf(1)) }) });
        } else
        {
            t.getKeyFrames().addAll(
                    new KeyFrame[] { new KeyFrame(Duration.millis(150.0D), new KeyValue[] { new KeyValue(this.pane_about.scaleXProperty(), Integer.valueOf(0)) }),
                            new KeyFrame(Duration.millis(150.0D), new EventHandler<ActionEvent>()
                            {
                                public void handle(ActionEvent t)
                                {
                                    ImageEditorController.this.pane_blur_main.setVisible(false);
                                    ImageEditorController.this.pane_blur_main.getChildren().clear();
                                    ImageEditorController.this.pane_about.setVisible(false);
                                }
                            }, new KeyValue[] { new KeyValue(this.pane_about.scaleYProperty(), Integer.valueOf(0)) }) });
        }

        t.play();
    }

    private void animateLevelPane(boolean in)
    {
        Timeline t = new Timeline();
        int change = 225;
        this.pane_blur.setTranslateX(-225.0D);
        if (in)
        {
            t.getKeyFrames().addAll(
                    new KeyFrame[] { new KeyFrame(Duration.millis(300.0D), new KeyValue[] { new KeyValue(this.pane_level.translateXProperty(), Integer.valueOf(-225)) }),
                            new KeyFrame(Duration.millis(300.0D), new KeyValue[] { new KeyValue(this.pane_blur.opacityProperty(), Integer.valueOf(1)) }) });
        } else
        {
            t.getKeyFrames().addAll(
                    new KeyFrame[] { new KeyFrame(Duration.millis(300.0D), new KeyValue[] { new KeyValue(this.pane_level.translateXProperty(), Integer.valueOf(225)) }),
                            new KeyFrame(Duration.millis(300.0D), new EventHandler<ActionEvent>()
                            {
                                public void handle(ActionEvent t)
                                {
                                    ImageEditorController.this.pane_blur.setTranslateX(225.0D);
                                    ImageEditorController.this.setEffect(ImageEditorController.this.imageView.getEffect());
                                }
                            }, new KeyValue[] { new KeyValue(this.pane_blur.opacityProperty(), Integer.valueOf(0)) }) });
        }

        t.play();
    }

    private double getScaleRate(double val, double total)
    {
        double d = val / total;
        if (d > this.slider_zoom.getMax()) return this.slider_zoom.getMax();
        if (d < this.slider_zoom.getMin()) return this.slider_zoom.getMin();
        return d;
    }

    private void changeImageSize()
    {
        if (this.resize)
        {
            if (this.imageView.getLayoutBounds().getWidth() > this.pane_img.getWidth())
            {
                double s = getScaleRate(this.pane_img.getWidth(), this.image.getWidth());
                this.slider_zoom.setValue(s);
            } else if (this.imageView.getLayoutBounds().getHeight() > this.pane_img.getHeight())
            {
                double s = getScaleRate(this.pane_img.getHeight(), this.image.getHeight());
                this.slider_zoom.setValue(s);
            } else
            {
                double s = getScaleRate(this.pane_img.getHeight(), this.image.getHeight());
                this.slider_zoom.setValue(s);
            }
        }
    }

    private Accordion getAccordion()
    {
        TitledPane t1 = new TitledPane("T1", new Button("B1"));
        TitledPane t2 = new TitledPane("T2", new Button("B2"));
        TitledPane t3 = new TitledPane("T3", new Button("B3"));
        Accordion accordion = new Accordion();
        accordion.getPanes().addAll(new TitledPane[] { t1, t2, t3 });
        return accordion;
    }

    private Image takeSnapshot()
    {
        int w = new Double(this.image.getWidth()).intValue();
        int h = new Double(this.image.getHeight()).intValue();
        WritableImage wimage = new WritableImage(w, h);
        SnapshotParameters p = new SnapshotParameters();
        p.setDepthBuffer(false);

        p.setFill(Color.BLACK);

        double sx = this.imageView.getScaleX();
        double sy = this.imageView.getScaleY();

        this.imageView.setScaleX(1.0D);
        this.imageView.setScaleY(1.0D);
        this.imageView.snapshot(p, wimage);

        return wimage;
    }

    public void resetZoom(ActionEvent e)
    {
        this.slider_zoom.setValue(1.0D);
    }

    public void feedback(ActionEvent e)
    {
        final Button b = (Button) e.getSource();
        b.setText("Sending...");

        Task t = new Task()
        {
            protected Object call() throws Exception
            {
                HttpWork work = new HttpWork();
                String data = URLEncoder.encode(ImageEditorController.this.txt_feed.getText(), "utf-8");
                int i = work.doPost("http://apps.ngopal.com.np/feather_edit/feedback.php", "message=" + data);

                Platform.runLater(new Runnable()
                {
                    public void run()
                    {
                        b.setText("Send Feedback");
                    }
                });
                return null;
            }
        };
        new Thread(t).start();
    }

    public void openBrowser(ActionEvent e)
    {
        this.slider_zoom.setValue(1.0D);
        Desktop d = Desktop.getDesktop();
        try
        {
            d.browse(new URI("http://apps.ngopal.com.np/feather_edit"));
        } catch (URISyntaxException ex)
        {
            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openMail(ActionEvent e)
    {
        this.slider_zoom.setValue(1.0D);
        Desktop d = Desktop.getDesktop();
        try
        {
            d.mail(new URI("mailto://fe@apps.ngopal.com.np"));
        } catch (URISyntaxException ex)
        {
            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveState(ActionEvent e)
    {
        Image im = takeSnapshot();
        ImagePersistence p = new ImagePersistence(new Date(), im);
        this.image = im;
        this.imageView.setImage(this.image);
        Persistence.addState(p);
    }

    public void saveImage(ActionEvent e)
    {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.setTitle("Save File");
        String format = this.select_img_format.getSelectionModel().getSelectedItem().toString();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(format, new String[] { format }));
        File f = fc.showSaveDialog(this.imageView.getScene().getWindow());
        if (f != null) try
        {
            if (!f.getName().contains("."))
            {
                f = new File(f.getCanonicalPath() + "." + format);
            }
            SaveImage s = new SaveImage(f, this.imageView.getImage(), this.imageView.getEffect(), this.select_img_format.getSelectionModel().getSelectedItem().toString());
        } catch (IOException ex)
        {

            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetEffect()
    {
        this.slider_glow.setValue(0.0D);
        this.slider_bright.setValue(0.0D);
        this.slider_cont.setValue(0.0D);
        this.slider_hue.setValue(0.0D);
        this.slider_sat.setValue(0.0D);
    }

    private void updateLiveImage(final Image im)
    {
        Platform.runLater(new Runnable()
        {
            public void run()
            {
                ImageEditorController.this.imageView.setImage(im);
            }
        });
    }

    public void hideAbout(MouseEvent e)
    {
        animateAboutPane(false);
    }

    public void goAbout(ActionEvent e)
    {
        animateAboutPane(true);
    }

    public void goLive(ActionEvent e)
    {
        Button b = (Button) e.getSource();
        boolean found = false;
        for (String cls : b.getStyleClass())
        {
            if (cls.equals("button-green"))
            {
                found = true;
                break;
            }

        }

        if (found)
        {
            this.liveStop = true;
            disableAllControls(false);
            b.getStyleClass().remove("button-green");
        } else
        {
            this.liveStop = false;
            disableAllControls(true);
            b.getStyleClass().add("button-green");
            SnapshotParameters p = new SnapshotParameters();

            final int x = this.editor.getRegionHelper().getInitX().intValue();
            final int y = this.editor.getRegionHelper().getInitY().intValue();
            final int w = this.editor.getRegionHelper().getWidth().intValue();
            final int h = this.editor.getRegionHelper().getHeight().intValue();

            Task t = new Task()
            {
                protected Object call()
                {
                    while (!ImageEditorController.this.liveStop)
                    {
                        try
                        {
                            Robot ro = new Robot();
                            BufferedImage im = ro.createScreenCapture(new Rectangle(x, y, w, h));
                            WritableImage wim = new WritableImage(w, h);
                            SwingFXUtils.toFXImage(im, wim);
                            ImageEditorController.this.image = wim;
                            ImageEditorController.this.updateLiveImage(ImageEditorController.this.image);

                            Thread.sleep(10L);
                        } catch (InterruptedException ex)
                        {
                            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (AWTException ex)
                        {
                            Logger.getLogger(ImageEditorController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    return null;
                }
            };
            new Thread(t).start();
        }
    }

    public void reset(ActionEvent e)
    {
        this.imageView.setImage(Persistence.getOriginalImage().getImage());
        resetEffect();
        Persistence.clearAll();
    }

    public void setEffect(Effect e)
    {
        this.imageView.setEffect(e);
        int w = new Double(this.image.getWidth()).intValue();
        int h = new Double(this.image.getHeight()).intValue();
        WritableImage wimage = new WritableImage(w, h);
        SnapshotParameters p = new SnapshotParameters();
        p.setDepthBuffer(false);

        p.setFill(Color.BLACK);

        double sx = this.imageView.getScaleX();
        double sy = this.imageView.getScaleY();

        this.imageView.setImage(this.image);
        this.imageView.setScaleX(1.0D);
        this.imageView.setScaleY(1.0D);

        this.imageView.snapshot(p, wimage);

        this.imageView.setImage(wimage);
        this.imageView.setEffect(null);
        resetEffect();
        this.imageView.setEffect(this.userEffect);

        this.imageView.setScaleX(sx);
        /* 525 */this.imageView.setScaleY(sy);
        /*     */}

    /*     */
    public Effect getImageEffect()
    {
        DropShadow ds = new DropShadow();
        ds.setOffsetX(0.0D);
        ds.setOffsetY(0.0D);
        ds.setSpread(0.7D);
        ds.setRadius(1.0D);
        ds.setColor(Color.GRAY);

        Glow glow = new Glow();
        glow.levelProperty().bind(this.slider_glow.valueProperty());

        Shadow sh = new Shadow();
        sh.setColor(Color.GRAY);

        sh.heightProperty().add(this.slider_glow.valueProperty());
        ColorAdjust adjust = new ColorAdjust();
        adjust.brightnessProperty().bind(this.slider_bright.valueProperty());
        adjust.contrastProperty().bind(this.slider_cont.valueProperty());
        adjust.hueProperty().bind(this.slider_hue.valueProperty());
        adjust.saturationProperty().bind(this.slider_sat.valueProperty());
        glow.setInput(adjust);

        this.userEffect = glow;
        return glow;
    }

    public void populateEffects(VBox box)
    {
        Image im = new Image(getClass().getResource("img/image.png").toExternalForm());
        EffectBuilder eb = new EffectBuilder();

        for (int i = 0; i < 5; i++)
        {
            final ImageView iv = new ImageView();

            iv.setImage(im);
            final Effect effect = eb.getEffect(i);
            if (effect != null) iv.setEffect(effect);
            SnapshotParameters p = new SnapshotParameters();
            p.setDepthBuffer(true);
            WritableImage wim = new WritableImage(new Double(im.getWidth()).intValue(), new Double(im.getHeight()).intValue());

            iv.snapshot(p, wim);
            iv.setImage(wim);
            final DropShadow sh = new DropShadow();
            sh.setRadius(6.0D);
            sh.setSpread(0.7D);
            sh.setColor(Color.GRAY);
            iv.setOnMouseEntered(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    iv.setEffect(sh);
                }
            });
            iv.setOnMouseExited(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    iv.setEffect(null);
                }
            });
            iv.setOnMouseReleased(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    iv.setEffect(new InnerShadow());

                    ImageView im = new ImageView();

                    SnapshotParameters p = new SnapshotParameters();
                    WritableImage wim = new WritableImage(new Double(ImageEditorController.this.pane_blur.getLayoutBounds().getWidth()).intValue(), new Double(ImageEditorController.this.pane_blur
                            .getLayoutBounds().getHeight()).intValue());

                    ImageEditorController.this.pane_option.snapshot(p, wim);
                    im.setImage(wim);
                    im.setEffect(new BoxBlur());
                    ImageEditorController.this.pane_blur.getChildren().clear();
                    ImageEditorController.this.pane_blur.getChildren().add(im);

                    ImageEditorController.this.slider_level.setRotate(0.0D);

                    WritableImage wi = new WritableImage(new Double(ImageEditorController.this.image.getWidth()).intValue(), new Double(ImageEditorController.this.image.getHeight()).intValue());
                    ImageEditorController.this.imageView.setScaleX(1.0D);
                    ImageEditorController.this.imageView.setScaleY(1.0D);
                    ImageEditorController.this.imageView.setImage(ImageEditorController.this.image);

                    ImageEditorController.this.imageView.setScaleX(ImageEditorController.this.slider_zoom.getValue());
                    ImageEditorController.this.imageView.setScaleY(ImageEditorController.this.slider_zoom.getValue());

                    ImageEditorController.this.imageView.setEffect(effect);
                    if ((effect instanceof Bloom))
                    {
                        Bloom b = (Bloom) effect;
                        ImageEditorController.this.slider_level.setRotate(180.0D);
                        ImageEditorController.this.slider_level.setMin(0.0D);
                        ImageEditorController.this.slider_level.setMax(1.0D);
                        ImageEditorController.this.slider_level.setValue(b.getThreshold());

                        b.thresholdProperty().bind(ImageEditorController.this.slider_level.valueProperty());
                        ImageEditorController.this.animateLevelPane(true);
                    } else if ((effect instanceof SepiaTone))
                    {
                        SepiaTone s = (SepiaTone) effect;

                        ImageEditorController.this.slider_level.setMin(0.0D);
                        ImageEditorController.this.slider_level.setMax(1.0D);
                        ImageEditorController.this.slider_level.setValue(s.getLevel());
                        s.levelProperty().bind(ImageEditorController.this.slider_level.valueProperty());
                        ImageEditorController.this.animateLevelPane(true);
                    } else if ((effect instanceof InnerShadow))
                    {
                        InnerShadow shadow = (InnerShadow) effect;
                        ImageEditorController.this.slider_level.setMin(0.0D);
                        ImageEditorController.this.slider_level.setMax(127.0D);
                        ImageEditorController.this.slider_level.setValue(shadow.getRadius());
                        shadow.radiusProperty().bind(ImageEditorController.this.slider_level.valueProperty());
                        ImageEditorController.this.animateLevelPane(true);
                    } else
                    {
                        ImageEditorController.this.setEffect(effect);
                    }
                }
            });
            box.getChildren().add(iv);
        }
    }

    public void retriveState(ImagePersistence p)
    {
        this.image = p.getImage();
        this.imageView.setImage(this.image);

        resetEffect();
    }

    public void loadStates(MouseEvent e)
    {
        Persistence.refresh();
        this.select_states.setItems(Persistence.maps);
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        this.pane_main.heightProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                ImageEditorController.this.pane_about.setLayoutY(newValue.doubleValue() / 2.0D);
                ImageEditorController.this.updateBlurBackground();
            }
        });
        this.pane_main.widthProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                ImageEditorController.this.pane_about.setLayoutX(newValue.doubleValue() / 2.0D);
                ImageEditorController.this.updateBlurBackground();
            }
        });
        this.pane_img.heightProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                ImageEditorController.this.changeImageSize();
            }
        });
        this.pane_img.widthProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                ImageEditorController.this.changeImageSize();
            }
        });
        this.imageView = new ImageView();
        this.imageView.setPreserveRatio(true);
        this.slider_zoom.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                ImageEditorController.this.imageView.setScaleX(t1.doubleValue());
                ImageEditorController.this.imageView.setScaleY(t1.doubleValue());
            }
        });
        this.pane_img.getChildren().add(this.imageView);

        this.pane_img.setOnScroll(new EventHandler<ScrollEvent>()
        {
            public void handle(ScrollEvent t)
            {
                if (t.getDeltaY() < 0.0D)
                {
                    if (ImageEditorController.this.slider_zoom.getValue() - 0.1D < ImageEditorController.this.slider_zoom.getMin())
                        ImageEditorController.this.slider_zoom.setValue(1.0D);
                    else
                    {
                        ImageEditorController.this.slider_zoom.setValue(ImageEditorController.this.slider_zoom.getValue() - 0.1D);
                    }
                } else if (ImageEditorController.this.slider_zoom.getValue() + 0.1D > ImageEditorController.this.slider_zoom.getMax())
                    ImageEditorController.this.slider_zoom.setValue(3.0D);
                else
                    ImageEditorController.this.slider_zoom.setValue(ImageEditorController.this.slider_zoom.getValue() + 0.1D);
            }
        });
        this.imageView.setEffect(getImageEffect());

        this.select_states.setItems(Persistence.maps);
        this.select_states.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImagePersistence>()
        {
            public void changed(ObservableValue<? extends ImagePersistence> ov, ImagePersistence t, ImagePersistence t1)
            {
                ImageEditorController.this.retriveState(t1);
            }
        });
        StackPane.setAlignment(this.imageView, Pos.CENTER);
        populateEffects(this.box_effects);
        this.pane_level.setOnMouseExited(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent t)
            {
                ImageEditorController.this.animateLevelPane(false);
            }
        });
    }
}
