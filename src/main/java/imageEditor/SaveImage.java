package imageEditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class SaveImage
{
    private Image image;
    private Effect effect;

    public SaveImage(File file, Image im, Effect e, String format) throws IOException
    {
        if (file.getPath().endsWith("."))
        {
            file = new File(file.getPath() + format);
        }

        this.image = im;
        this.effect = e;
        ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(this.image.getWidth());
        imgView.setImage(this.image);
        imgView.setEffect(this.effect);
        SnapshotParameters p = new SnapshotParameters();

        WritableImage wm = new WritableImage(new Double(this.image.getWidth()).intValue(), new Double(this.image.getHeight()).intValue());

        p.setDepthBuffer(true);
        imgView.snapshot(p, wm);
        BufferedImage bm = new BufferedImage(new Double(this.image.getWidth()).intValue(), new Double(this.image.getHeight()).intValue(), 2);

        SwingFXUtils.fromFXImage(wm, bm);

        if (format.equals("jpg"))
        {
            BufferedImage newBi = new BufferedImage(bm.getWidth(), bm.getHeight(), 1);
            Graphics2D g2d = (Graphics2D) newBi.getGraphics();
            g2d.drawImage(bm, 0, 0, bm.getWidth(), bm.getHeight(), Color.BLACK, null);

            ByteArrayOutputStream osByteArray = new ByteArrayOutputStream();
            
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(osByteArray);

            Iterator iter = ImageIO.getImageWritersByFormatName(format);
            
            ImageWriter writer = (ImageWriter) iter.next();

            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(2);

            iwp.setCompressionType(iwp.getCompressionTypes()[0]);
            iwp.setCompressionQuality(1.0F);

            IIOImage image = new IIOImage(newBi, null, null);

            FileImageOutputStream output = new FileImageOutputStream(file);
            writer.setOutput(output);
            writer.write(null, image, iwp);
            writer.dispose();

            output.flush();
            output.close();
        } else
        {
            ImageIO.write(bm, format, file);
        }
    }

    public void setImage()
    {
    }

    public void setEffect()
    {
    }
}
