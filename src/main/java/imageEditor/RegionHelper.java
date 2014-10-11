/*     */ package imageEditor;
/*     */ 
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class RegionHelper
/*     */ {
/*     */   private SimpleDoubleProperty initX;
/*     */   private SimpleDoubleProperty initY;
/*     */   private SimpleDoubleProperty xsideX;
/*     */   private SimpleDoubleProperty xsideY;
/*     */   private SimpleDoubleProperty ysideX;
/*     */   private SimpleDoubleProperty ysideY;
/*     */   private SimpleDoubleProperty finalX;
/*     */   private SimpleDoubleProperty finalY;
/*     */   private ObservableList<Double> points;
/*     */ 
/*     */   public Double getInitX()
/*     */   {
/*  26 */     if (this.initX.get() < this.finalX.get()) {
/*  27 */       return Double.valueOf(this.initX.get());
/*     */     }
/*  29 */     return Double.valueOf(this.finalX.get());
/*     */   }
/*     */ 
/*     */   public Double getInitY() {
/*  33 */     if (this.initY.get() < this.finalY.get()) {
/*  34 */       return Double.valueOf(this.initY.get());
/*     */     }
/*  36 */     return Double.valueOf(this.finalY.get());
/*     */   }
/*     */   public Double getFinalX() {
/*  39 */     if (this.initX.get() > this.finalX.get()) {
/*  40 */       return Double.valueOf(this.initX.get());
/*     */     }
/*  42 */     return Double.valueOf(this.finalX.get());
/*     */   }
/*     */ 
/*     */   public Double getFinalY() {
/*  46 */     if (this.initY.get() > this.finalY.get()) {
/*  47 */       return Double.valueOf(this.initY.get());
/*     */     }
/*  49 */     return Double.valueOf(this.finalY.get());
/*     */   }
/*     */ 
/*     */   public Double getWidth()
/*     */   {
/*  56 */     return Double.valueOf(getFinalX().doubleValue() - getInitX().doubleValue());
/*     */   }
/*     */ 
/*     */   public Double getHeight() {
/*  60 */     return Double.valueOf(getFinalY().doubleValue() - getInitY().doubleValue());
/*     */   }
/*     */ 
/*     */   public Double getCenterX() {
/*  64 */     return Double.valueOf(getInitX().doubleValue() + getWidth().doubleValue() / 2.0D);
/*     */   }
/*     */ 
/*     */   public Double getCenterY() {
/*  68 */     return Double.valueOf(getInitY().doubleValue() + getHeight().doubleValue() / 2.0D);
/*     */   }
/*     */   public RegionHelper() {
/*  71 */     this.initX = new SimpleDoubleProperty();
/*  72 */     this.initY = new SimpleDoubleProperty();
/*  73 */     this.xsideX = new SimpleDoubleProperty();
/*  74 */     this.xsideY = new SimpleDoubleProperty();
/*  75 */     this.ysideX = new SimpleDoubleProperty();
/*  76 */     this.ysideY = new SimpleDoubleProperty();
/*  77 */     this.finalX = new SimpleDoubleProperty();
/*  78 */     this.finalY = new SimpleDoubleProperty();
/*     */   }
/*     */ 
/*     */   public boolean isInitEqualFinal()
/*     */   {
/*  83 */     return (this.initX.get() == this.finalX.get()) && (this.initY.get() == this.finalY.get());
/*     */   }
/*     */ 
/*     */   public void setFinalXY(double x, double y)
/*     */   {
/*  88 */     this.finalX.set(x);
/*  89 */     this.finalY.set(y);
/*     */ 
/*  91 */     this.ysideX.set(this.initX.get());
/*  92 */     this.ysideY.set(y);
/*     */ 
/*  94 */     this.xsideX.set(x);
/*  95 */     this.xsideY.set(this.initY.get());
/*     */   }
/*     */ 
/*     */   public void setInitXY(double x, double y)
/*     */   {
/* 101 */     this.initX.set(x);
/* 102 */     this.initY.set(y);
/* 103 */     this.finalX.set(x);
/* 104 */     this.finalY.set(y);
/*     */   }
/*     */ 
/*     */   public Double[] getPoints(double screenx, double scenex, double screeny, double sceney)
/*     */   {
/* 112 */     double changedy = 0.0D;
/* 113 */     if (screeny > sceney) {
/* 114 */       changedy = screeny - sceney;
/* 115 */       return new Double[] { Double.valueOf(this.initX.doubleValue()), Double.valueOf(this.initY.doubleValue() - changedy), Double.valueOf(this.xsideX.doubleValue()), Double.valueOf(this.xsideY.doubleValue() - changedy), Double.valueOf(this.finalX.doubleValue()), Double.valueOf(this.finalY.doubleValue() - changedy), Double.valueOf(this.ysideX.doubleValue()), Double.valueOf(this.ysideY.doubleValue() - changedy) };
/*     */     }
/*     */ 
/* 123 */     return new Double[] { Double.valueOf(this.initX.doubleValue()), Double.valueOf(this.initY.doubleValue()), Double.valueOf(this.xsideX.doubleValue()), Double.valueOf(this.xsideY.doubleValue()), Double.valueOf(this.finalX.doubleValue()), Double.valueOf(this.finalY.doubleValue()), Double.valueOf(this.ysideX.doubleValue()), Double.valueOf(this.ysideY.doubleValue()) };
/*     */   }
/*     */ }

/* Location:           C:\Users\Albert\Desktop\feather-edit\FeatherEdit.jar
 * Qualified Name:     imageEditor.RegionHelper
 * JD-Core Version:    0.6.0
 */