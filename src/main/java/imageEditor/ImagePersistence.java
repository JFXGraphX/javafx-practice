/*    */ package imageEditor;
/*    */ 
/*    */ import java.util.Date;
/*    */ import javafx.scene.image.Image;
/*    */ 
/*    */ public class ImagePersistence
/*    */ {
/*    */   private Date date;
/*    */   private Image image;
/*    */   private String message;
/* 20 */   private int i = 0;
/*    */ 
/*    */   public String toString()
/*    */   {
/* 26 */     Date d = new Date();
/* 27 */     long l = d.getTime() - this.date.getTime();
/*    */ 
/* 29 */     int sec = (int)(l / 1000.0D);
/* 30 */     int min = (int)(sec / 60.0D);
/* 31 */     int hr = (int)(min / 60.0D);
/*    */ 
/* 35 */     if ((min > 0) && (hr == 0))
/* 36 */       return "" + min + "min " + (sec - min * 60) + "sec ago";
/* 37 */     if (hr > 0) {
/* 38 */       return "" + hr + "hr " + (min - min * 60) + "min ago";
/*    */     }
/* 40 */     return "" + sec + " sec ago";
/*    */   }
/*    */ 
/*    */   public Image getImage()
/*    */   {
/* 45 */     return this.image;
/*    */   }
/*    */ 
/*    */   public Date getDate()
/*    */   {
/* 51 */     return this.date;
/*    */   }
/*    */   public ImagePersistence(Date d, Image i) {
/* 54 */     this.date = d;
/* 55 */     this.image = i;
/* 56 */     this.message = "t";
/*    */   }
/*    */ }

/* Location:           C:\Users\Albert\Desktop\feather-edit\FeatherEdit.jar
 * Qualified Name:     imageEditor.ImagePersistence
 * JD-Core Version:    0.6.0
 */