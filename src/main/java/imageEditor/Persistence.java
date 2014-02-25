/*    */ package imageEditor;
/*    */ 
/*    */ import javafx.collections.FXCollections;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public class Persistence
/*    */ {
/* 16 */   private static int i = 0;
/*    */ 
/* 18 */   public static ObservableList<ImagePersistence> maps = FXCollections.observableArrayList();
/*    */   private static ImagePersistence originalImage;
/*    */ 
/*    */   public static ObservableList<ImagePersistence> getStates()
/*    */   {
/* 23 */     return maps;
/*    */   }
/*    */ 
/*    */   public static int addState(ImagePersistence im) {
/* 27 */     maps.add(im);
/* 28 */     return i;
/*    */   }
/*    */ 
/*    */   public static ImagePersistence removeState(int i) {
/* 32 */     return (ImagePersistence)maps.remove(i);
/*    */   }
/*    */ 
/*    */   public static boolean removeState(ImagePersistence i) {
/* 36 */     return maps.remove(i);
/*    */   }
/*    */ 
/*    */   public static void refresh()
/*    */   {
/* 41 */     ObservableList map = FXCollections.observableArrayList();
/*    */ 
/* 43 */     for (ImagePersistence a : maps) {
/* 44 */       ImagePersistence p = new ImagePersistence(a.getDate(), a.getImage());
/* 45 */       map.add(p);
/*    */     }
/*    */ 
/* 48 */     maps = map;
/*    */   }
/*    */ 
/*    */   public static void setOriginalImage(ImagePersistence i) {
/* 52 */     originalImage = i;
/*    */   }
/*    */   public static ImagePersistence getOriginalImage() {
/* 55 */     return originalImage;
/*    */   }
/*    */ 
/*    */   public static void clearAll() {
/* 59 */     maps.clear();
/*    */   }
/*    */ }

/* Location:           C:\Users\Albert\Desktop\feather-edit\FeatherEdit.jar
 * Qualified Name:     imageEditor.Persistence
 * JD-Core Version:    0.6.0
 */