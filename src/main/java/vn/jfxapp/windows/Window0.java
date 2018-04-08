/**
 * Copyright (c) 2017 Vo Anh Tuan(tuana1648@gmail.com).
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package vn.jfxapp.windows;

import vn.jfxapp.windows.service.CloseService;
import vn.jfxapp.windows.service.DragService;
import vn.jfxapp.windows.service.FocusService;
import vn.jfxapp.windows.service.ShowService;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.logging.Level;
import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import vn.jfxapp.windows.service.concrete.CloseServicecConcrete;
import vn.jfxapp.windows.service.concrete.FocusServiceConcrete;

/**
 * Window0 provide basic operation for other class.
 * By default CSS file using for it is window0.css,
 * if you want custom this file name using Window0Builder instead.
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class Window0{
    protected Stage stage;
    protected Scene scene;
    protected AnchorPane decoration;
    
    ///window extension.
    protected final Rectangle shadow=new Rectangle();
    protected final Rectangle background=new Rectangle();
    private final Rectangle internal=new Rectangle();
    private final Rectangle external=new Rectangle();
    
    ///common service.
    protected final CloseService closeService;
    protected final ShowService showService;
    protected final FocusService focusService;
    protected final double shadowSize;
    protected final double resizeSize;
    protected double effectiveShadowSize,effectiveResizeSize;
    protected final ObjectProperty<Pane> layout=new SimpleObjectProperty<>(null);
    protected final InputStream input;
    protected final Cursor defaultCursor;
    private boolean notConstruct;
    
    public Window0(Stage stage){
        this(stage,null,//no input 
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window0.css"), 
                WindowManager.getCommonWindowAttributes().getShadowSize(),
                WindowManager.getCommonWindowAttributes().getResizeSize(),
                null,
                null,//default focus-service
                null,//default show-service
                null//default close-service
        );
    }
    
    protected Window0(Stage stage,InputStream input,String stylesheet,
            double shadowSize,double resizeSize,Cursor defaultCursor,
            FocusService focusService,ShowService showService,CloseService closeService){
        if(stage!=null)this.stage=stage;
        else this.stage=new Stage();
        
        scene=new Scene(frame);
        
        this.input=input;
        this.shadowSize=shadowSize;
        this.resizeSize=resizeSize;
        
        effectiveShadowSize=this.shadowSize;
        effectiveResizeSize=this.resizeSize;
        
        this.notConstruct=true;
        
        if(defaultCursor!=null)this.defaultCursor=defaultCursor;
        else this.defaultCursor=WindowManager.getCommonWindowCursor().getDefaultCursor();
        
        ///LOG if stylesheet null
        if(null==stylesheet){
            WindowManager.WMLW.LOGGER.log(Level.WARNING, "Stylesheet path set for {0} is null, use default setting instead", this.getClass().getSimpleName());
            Class<?> cls=getClass();
            stylesheet=cls.getResource(cls.getSimpleName().toLowerCase()).toExternalForm();
        }
        setStylesheet(stylesheet,false);
        
        if(focusService!=null)this.focusService=focusService;
        else this.focusService=new DefaultFocusService();
        
        if(showService!=null)this.showService=showService;
        else this.showService=new DefaultShowService();
        
        if(closeService!=null)this.closeService=closeService;
        else this.closeService=new DefaultCloseService();
    }
    
    /**
     * 
     * @param stylesheet can be class path or system path.
     * @see #setStylesheet(java.lang.String, boolean) 
     */
    public final void setStylesheet(String stylesheet){
        setStylesheet(stylesheet,true);
    }
    
    /**
     * Add CSS to this window.
     * @param stylesheet can be class path or system path.
     * @param append specify should override or append to previous stylesheet
     */
    public final void setStylesheet(String stylesheet,boolean append){
        if(append){
            scene.getStylesheets().add(stylesheet);
        }else{
            scene.getStylesheets().clear(); 
            scene.getStylesheets().add(stylesheet);
        }
    }
    
    /**
     * Set layout for window application.
     * @param layout - layout represents application function.
     */
    public void setLayout(Pane layout){
        if(layout!=null){
            this.layout.set(layout);
        }
        else{
            if(null==this.layout.get())
                WindowManager.WMLW.LOGGER.log(Level.WARNING, "Layout set for {0} is null, use default layout window instead", getClass().getSimpleName());
            else
                WindowManager.WMLW.LOGGER.log(Level.WARNING, "Layout set for {0} is null, still use previous layout window", getClass().getSimpleName()); 
        }
    }
    
    /**
     * Show window.
     * Do not call {@link Stage#show() } directly. It'll cause undefined behaviors.
     */
    public final void show(){
        construct();
        stage.toFront();
        stage.show();
    }
    
    /**
     * Show and wait until window closed.
     * Do not call {@link Stage#showAndWait()} directly. It'll cause undefined behaviors.
     */
    public final void showAndWait(){
        construct();
        stage.toFront();
        stage.showAndWait();
    }
    
    void close(){
       stage.close();
    }
    
    /**
     * @return current stage attach to this window
     */
    public final Stage getStage(){
        return stage;
    }
    
    protected void clipShadow(Bounds newBounds){
        //re-position,size of external rectangle
        external.relocate(newBounds.getMinX() - shadowSize,newBounds.getMinY() - shadowSize);
        external.setWidth(newBounds.getWidth() + shadowSize * 2);
        external.setHeight(newBounds.getHeight() + shadowSize * 2);
        
        //re-position,size of internal shadow
        internal.setX(shadowSize);
        internal.setY(shadowSize);
        internal.setWidth(newBounds.getWidth());
        internal.setHeight(newBounds.getHeight());
        internal.setArcWidth(shadow.getArcWidth());//
        internal.setArcHeight(shadow.getArcHeight());
        //apply clip.
        Shape clip = Shape.subtract(external, internal);
        shadow.setClip(clip);
    }
    
    final void construct(){
         if(notConstruct){
             notConstruct=false;
             initDecoration();
             initStyle();
             initUI();
             initListener();
             initWindow();
         }
    }
    
    protected void initDecoration(){
        decoration=new AnchorPane();
    }
    
    protected void initStyle(){
        shadow.getStyleClass().add(WindowManager.getCommonWindowStyles().getShadowStyle());
        background.getStyleClass().add(WindowManager.getCommonWindowStyles().getBackgroundStyle());
        frame.setStyle("-fx-background-color:transparent;");
    }
    
    protected void initUI(){
        ///Disable Window1 resizing
        stage.setResizable(false); 
        
        if(null==layout.get()){
            layout.set(new Pane());
            decoration.getChildren().add(layout.get());
        }else{
            decoration.getChildren().add(layout.get());
        }
        AnchorPane.setTopAnchor(layout.get(), 0.0);
        AnchorPane.setLeftAnchor(layout.get(), 0.0);
        AnchorPane.setRightAnchor(layout.get(), 0.0);
        AnchorPane.setBottomAnchor(layout.get(), 0.0);
    }
    
    protected void initListener(){
        initBaseListener();
        layout.addListener((ov,o,n)->{
            decoration.getChildren().remove(o);
            decoration.getChildren().add(n);
            AnchorPane.setTopAnchor(n, 0.0);
            AnchorPane.setLeftAnchor(n, 0.0);
            AnchorPane.setRightAnchor(n, 0.0);
            AnchorPane.setBottomAnchor(n, 0.0);
        });
    }
    
    protected void initBaseListener(){
        shadow.layoutBoundsProperty().addListener((ov,o,n)->{
            clipShadow(n);
        });
        
        stage.focusedProperty().addListener((ov,o,n)->setShadowFocus(n));

        stage.setOnCloseRequest(e->{
            e.consume();
            closeService.run(stage,frame,null);
        });
        
        stage.showingProperty().addListener((ov,o,n)->{
           if(n)showService.run(stage, frame);
        });
    }
    
    private void initWindow(){
        shadow.setMouseTransparent(true);
        frame.getChildren().addAll(shadow,background,decoration);
        frame.setCursor(defaultCursor);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setMinWidth(frame.minWidth(Region.USE_COMPUTED_SIZE));
        stage.setMinHeight(frame.minHeight(Region.USE_COMPUTED_SIZE));
        stage.setMaxWidth(frame.maxWidth(Region.USE_COMPUTED_SIZE));
        stage.setMaxHeight(frame.maxHeight(Region.USE_COMPUTED_SIZE));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
    }
    
    protected void setShadowFocus(boolean value){
        if (value){
            shadow.setEffect(focusService.run(shadowSize));
        } else{

            shadow.setEffect(focusService.stop(shadowSize));
        }
    }
    
    protected final StackPane frame=new StackPane(){
        @Override
        public void layoutChildren(){
            //Get real size window to calculate other size and position.
            Bounds b = super.getLayoutBounds();
            double w = b.getWidth();
            double h = b.getHeight();

            //calculate size and position of shadow , background and layout.
            super.getChildren().forEach(node->{
                if (node == shadow) {
                    shadow.setWidth(w - effectiveShadowSize * 2);
                    shadow.setHeight(h - effectiveShadowSize * 2);
                    shadow.setX(effectiveShadowSize);
                    shadow.setY(effectiveShadowSize);
                } else if (node == background) {
                    background.setWidth(w - effectiveShadowSize * 2);
                    background.setHeight(h - effectiveShadowSize * 2);
                    background.setX(effectiveShadowSize);
                    background.setY(effectiveShadowSize);
                }
                else {
                    node.resize(w - effectiveShadowSize * 2-effectiveResizeSize*2, h - effectiveShadowSize * 2-effectiveResizeSize*2);
                    node.setLayoutX(effectiveShadowSize+effectiveResizeSize);
                    node.setLayoutY(effectiveShadowSize+effectiveResizeSize);
                }
            });
        }
        
        @Override
        protected double computePrefWidth(double d) {
            return decoration.prefWidth(d)+ effectiveShadowSize * 2 + effectiveResizeSize * 2;
        }

        @Override
        protected double computePrefHeight(double d) {
            return decoration.prefHeight(d)+ effectiveShadowSize * 2 + effectiveResizeSize * 2;
        }

        @Override
        protected double computeMaxHeight(double d) {
            return decoration.maxHeight(d)+effectiveShadowSize * 2 + effectiveResizeSize* 2;
        }

        @Override
        protected double computeMinHeight(double d) {
            return decoration.minHeight(d)+effectiveShadowSize * 2 + effectiveResizeSize* 2;
        }

        @Override
        protected double computeMaxWidth(double d){
            return decoration.maxWidth(d) + effectiveShadowSize * 2 + effectiveResizeSize * 2;
        }

        @Override
        protected double computeMinWidth(double d) {
            return decoration.minWidth(d)+effectiveShadowSize * 2 + effectiveResizeSize * 2;
        }
    };
    
    static class DefaultFocusService implements FocusServiceConcrete{
        private final DropShadow 
                focusShadow=new DropShadow(BlurType.ONE_PASS_BOX, Color.rgb(17,43,46), 0.0, 0.1, 0, 0),
                unfocusShadow=new DropShadow(BlurType.GAUSSIAN, Color.rgb(82,147,226), 0.0, 0, 0, 0);
        
        @Override
        public Effect run(double shadowSpace) {
            focusShadow.setRadius(shadowSpace); 
            return focusShadow;
        }

        @Override
        public Effect stop(double shadowSpace) {
            unfocusShadow.setRadius(shadowSpace);
            return unfocusShadow;
        }

        @Override
        public void setFocusedColor(Color c) {
            focusShadow.setColor(c);
        }

        @Override
        public void setUnfocusedColor(Color c) {
            unfocusShadow.setColor(c);
        }
   };
   
   static class DefaultShowService implements ShowService{

        @Override
        public void run(Stage stage, Node root) {
            root.setOpacity(0.0);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(350.0),root);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }
       
    }
   
   static class DefaultCloseService implements CloseServicecConcrete{
        private final FadeTransition fadeTransition= new FadeTransition(Duration.millis(250.0));
        {
            fadeTransition.setToValue(0.0);
        }
        private Runnable run;
        private Supplier<Boolean> condition;
        private Supplier<Duration> duration;
        
        @Override
        public void run(Stage stage, Node root, DragService draggingService) {
            boolean isClose=true;
            if(condition!=null&&!condition.get())isClose=false;
            if(isClose){
                fadeTransition.setNode(root);
                if(duration!=null)fadeTransition.setDuration(duration.get());
                fadeTransition.setOnFinished(a->{
                    if(run!=null)run.run();
                    stage.close();
                });
                fadeTransition.play();
            }
        }

        @Override
        public void setOnClose(Runnable r) {
            run=r;
        }

        @Override
        public void setOnCloseCondition(Supplier<Boolean> s) {
            condition=s;
        }

        @Override
        public void setDuration(Supplier<Duration> s) {
            duration=s;
        }
   }
}
