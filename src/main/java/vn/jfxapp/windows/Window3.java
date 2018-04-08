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
import vn.jfxapp.windows.service.DragDockService;
import vn.jfxapp.windows.service.DragService;
import vn.jfxapp.windows.service.FocusService;
import vn.jfxapp.windows.service.ResizeService;
import vn.jfxapp.windows.service.ShowService;
import java.io.InputStream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import vn.jfxapp.windows.cursor.CommonCursor;
import vn.jfxapp.windows.cursor.BaseCursor;
import vn.jfxapp.windows.service.concrete.DragDockServiceConcrete;

/**
 *
 * @author waty
 */
public class Window3 extends Window2 implements CommonCursor{
    @FXML
    protected Button maximize;
    @FXML
    protected Button icon;
    @FXML
    protected Button drag;
    protected final ContextMenu contextMenu;
    protected final MenuItem closeItem;
    protected final MenuItem minimizeItem;
    protected final MenuItem maximizeItem;
    protected final KeyCombination maximizeKey;
    protected final BooleanProperty maximized=new SimpleBooleanProperty(false);
    protected boolean currentDrag;
    protected final ResizeService resizeService;
    protected final Cursor selectCursor,
                            waitCursor,
                            nResizeCursor,
                            wResizeCursor,
                            sResizeCursor,
                            eResizeCursor,
                            nwResizeCursor,
                            neResizeCursor,
                            swResizeCursor,
                            seResizeCursor,
                            textCursor;
    protected final BooleanProperty shadowVisible=new SimpleBooleanProperty(true);
    protected Bounds savedMaximizeBounds;
    protected ResourceBundle textBundle;
    protected final ObjectProperty<Locale> lang=new SimpleObjectProperty<>(new Locale(WindowManager.getCommonWindowResources().getDefaultLang())); 
    protected final String resourceName;//null for default in package resource (DEFAULT_BASE_TEXT_NAME)
    protected final ClassLoader resourceLoader;//null for default

    public Window3(){
        this(new Stage());
    }
    
    public Window3(Stage stage){
        this(stage,
                WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window3.fxml"),
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window3.css"));
    }
    
    protected Window3(Stage stage,InputStream input,String stylesheet){
        this(stage,
                input,
                stylesheet,
                WindowManager.getCommonWindowAttributes().getShadowSize(),
                WindowManager.getCommonWindowAttributes().getResizeSize(),//shadow and resize size
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new DefaultDragDockService(stage),
                null,
                null,
                null,
                null,//base service (drag fos sho clo rez)
                null,
                null,
                null,
                null,
                null);//baseName and classLoader
    }
    
    /// make sure that builder will pass DragDockService to dragService parameter.
    protected Window3(Stage stage,InputStream input,String stylesheet,
            double shadowSize,double resizeSize,
            Cursor defaultCursor,Cursor dragCursor,Cursor selectCursor,Cursor waitCursor,Cursor nResizeCursor,
            Cursor wResizeCursor,Cursor sResizeCursor,Cursor eResizeCursor,Cursor nwResizeCursor,
            Cursor neResizeCursor,Cursor swResizeCursor,Cursor seResizeCursor,Cursor textCursor,
            DragService dragService,FocusService focusService,ShowService showService,
            CloseService closeService, ResizeService resizeService,
            KeyCombination closeKey,KeyCombination minimizeKey,KeyCombination maximizeKey,
            String resourceStringName,ClassLoader resourceClassLoader){
        super(stage,input,stylesheet,shadowSize,resizeSize,defaultCursor,dragCursor,
                dragService,focusService,showService,closeService,closeKey,minimizeKey);
        
        if(selectCursor!=null)this.selectCursor=selectCursor;
        else this.selectCursor=WindowManager.getCommonWindowCursor().getSelectCursor();
        if(waitCursor!=null)this.waitCursor=waitCursor;
        else this.waitCursor=WindowManager.getCommonWindowCursor().getWaitCursor();
        if(nResizeCursor!=null)this.nResizeCursor=nResizeCursor;
        else this.nResizeCursor=WindowManager.getCommonWindowCursor().getNResizeCursor();
        if(wResizeCursor!=null)this.wResizeCursor=wResizeCursor;
        else this.wResizeCursor=WindowManager.getCommonWindowCursor().getWResizeCursor();
        if(sResizeCursor!=null)this.sResizeCursor=sResizeCursor;
        else this.sResizeCursor=WindowManager.getCommonWindowCursor().getSResizeCursor();
        if(eResizeCursor!=null)this.eResizeCursor=eResizeCursor;
        else this.eResizeCursor=WindowManager.getCommonWindowCursor().getEResizeCursor();
        if(nwResizeCursor!=null)this.nwResizeCursor=nwResizeCursor;
        else this.nwResizeCursor=WindowManager.getCommonWindowCursor().getNWResizeCursor();
        if(neResizeCursor!=null)this.neResizeCursor=neResizeCursor;
        else this.neResizeCursor=WindowManager.getCommonWindowCursor().getNEResizeCursor();
        if(swResizeCursor!=null)this.swResizeCursor=swResizeCursor;
        else this.swResizeCursor=WindowManager.getCommonWindowCursor().getSWResizeCursor();
        if(seResizeCursor!=null)this.seResizeCursor=seResizeCursor;
        else this.seResizeCursor=WindowManager.getCommonWindowCursor().getSEResizeCursor();
        if(textCursor!=null)this.textCursor=textCursor;
        else this.textCursor=WindowManager.getCommonWindowCursor().getTextCursor();
        contextMenu=new ContextMenu();
        closeItem=new MenuItem();
        minimizeItem=new MenuItem();
        maximizeItem=new MenuItem();
        if(maximizeKey!=null)this.maximizeKey=maximizeKey;
        else this.maximizeKey=WindowManager.getCommonWindowKeys().getMaximizeKeys();
        if(resizeService!=null)this.resizeService=resizeService;
        else this.resizeService=new DefaultResizeService();
        if(resourceClassLoader!=null&&resourceStringName!=null){
            this.resourceName=resourceStringName;
            this.resourceLoader=resourceClassLoader;
        }
        else{
            this.resourceName=WindowManager.getCommonWindowResources().getResourceName();
            this.resourceLoader=WindowManager.getCommonWindowResources().getResourceLoader();
        }
    }
    
    @Override
    protected void initStyle(){
        super.initStyle();
        maximize.getStyleClass().add(WindowManager.getCommonWindowStyles().getMaximizeStyle());
        maximize.setStyle("-fx-background-color:transparent;");
        icon.getStyleClass().add(WindowManager.getCommonWindowStyles().getIconStyle());
        icon.setStyle("-fx-background-color:transparent;");
        drag.getStyleClass().add(WindowManager.getCommonWindowStyles().getDragStyle());
        drag.setStyle("-fx-background-color:transparent;");
    }
    
    @Override
    protected void initUI(){
        stage.setResizable(true);
        if(null==layout.get()){
            layout.set(new Pane());
            decoration.getChildren().add(layout.get());
            AnchorPane.setTopAnchor(layout.get(), titleBar.getHeight());
            AnchorPane.setLeftAnchor(layout.get(), 0.0);
            AnchorPane.setRightAnchor(layout.get(), 0.0);
            AnchorPane.setBottomAnchor(layout.get(), 0.0);
        }else{
            decoration.getChildren().add(layout.get());
            AnchorPane.setTopAnchor(layout.get(), titleBar.getHeight());
            AnchorPane.setLeftAnchor(layout.get(), 0.0);
            AnchorPane.setRightAnchor(layout.get(), 0.0);
            AnchorPane.setBottomAnchor(layout.get(), 0.0);
        }
        textBundle=ResourceBundle.getBundle(resourceName, lang.get(), resourceLoader);
        contextMenu.getItems().addAll(closeItem,minimizeItem,maximizeItem);
        icon.setContextMenu(contextMenu);
        closeItem.setAccelerator(closeKey);
        minimizeItem.setAccelerator(minimizeKey);
        maximizeItem.setAccelerator(maximizeKey);
        final String closeText=textBundle.getString(WindowManager.getCommonWindowResources().getCloseString());
        final String minimizeText=textBundle.getString(WindowManager.getCommonWindowResources().getMinimizeString());
        final String maximizeText=textBundle.getString(WindowManager.getCommonWindowResources().getMaximizeString());
        if(null==close.getTooltip())close.setTooltip(new Tooltip(closeText));
        else close.getTooltip().setText(closeText);
        if(null==minimize.getTooltip())minimize.setTooltip(new Tooltip(minimizeText));
        else minimize.getTooltip().setText(minimizeText);
        if(null==maximize.getTooltip()) maximize.setTooltip(new Tooltip(maximizeText));
        else maximize.getTooltip().setText(maximizeText);
        closeItem.setText(closeText);
        minimizeItem.setText(minimizeText);
        maximizeItem.setText(maximizeText);
    }
    
    @Override
    protected void initBaseListener(){
        super.initBaseListener();
        
        icon.setOnAction(e->{
            if(contextMenu.isShowing())
                contextMenu.hide();
            else{
                contextMenu.show(icon, Side.BOTTOM, 0.0, 0.0);
            }
        });
        
        closeItem.setOnAction(e->close());
        
        minimizeItem.setOnAction(e->minimize());
        
        maximizeItem.setOnAction(e->maximize());
        
        maximize.setOnAction(e->maximize());
        
        lang.addListener((ov,o,n)->{
            reloadText();
        });
        
        shadowVisible.addListener((ov,o,n)->{
            if(!n){
                effectiveShadowSize=0.0;
                shadow.setEffect(null);
                frame.requestLayout();
            }
            else{
                effectiveShadowSize=shadowSize;
                shadow.setEffect(focusService.run(effectiveShadowSize));
                frame.requestLayout();
            }
        });
        
        maximized.addListener((ov,o,n)->{
            if(n){
                shadowVisible.set(false);
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                savedMaximizeBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                stage.setX(visualBounds.getMinX()-effectiveShadowSize);
                stage.setY(visualBounds.getMinY()-effectiveShadowSize);
                stage.setWidth(visualBounds.getWidth()+effectiveShadowSize*2);
                stage.setHeight(visualBounds.getHeight()+effectiveShadowSize*2);
                maximize.getStyleClass().add(WindowManager.getCommonWindowStyles().getRestoreStyle());
                drag.setVisible(false);
                final String maximizeText=textBundle.getString(WindowManager.getCommonWindowResources().getRestoreString());
                maximize.getTooltip().setText(maximizeText);
                maximizeItem.setText(maximizeText);
            }else{
                shadowVisible.set(true);
                if(savedMaximizeBounds!=null){
                    if(currentDrag){
                        double width=savedMaximizeBounds.getWidth();
                        stage.setX(x-width/2);
                        stage.setY(effectiveShadowSize*(-1.0));
                        stage.setWidth(width);
                        stage.setHeight(savedMaximizeBounds.getHeight());
                    }else{
                        stage.setX(savedMaximizeBounds.getMinX());
                        stage.setY(savedMaximizeBounds.getMinY());
                        stage.setWidth(savedMaximizeBounds.getWidth());
                        stage.setHeight(savedMaximizeBounds.getHeight());
                    }
                    savedMaximizeBounds = null;
                }
               maximize.getStyleClass().remove(WindowManager.getCommonWindowStyles().getRestoreStyle());
               drag.setVisible(true);
               final String maximizeText=textBundle.getString(WindowManager.getCommonWindowResources().getMaximizeString());
                maximize.getTooltip().setText(maximizeText);
                maximizeItem.setText(maximizeText);
            }
        });

        titleBar.setOnMouseClicked(e->{
            if(e.getClickCount()>1){
                maximized.set(!maximized.get());
                e.consume();
            }
        });
        
        titleBar.setOnMouseReleased(e->{
            if(currentDrag){
                currentDrag=false;
                titleBar.setCursor(defaultCursor);
                x = -1;
                y = -1;
                if(stage.isResizable()){
                    dragService.stop(stage,titleBar,e);
//                    if(((DragDockService)dragService).isDock()){
//                        shadowVisible.set(false); 
//                    }
                }
            }
            e.consume();
        });
        
        background.setOnMousePressed(e->{
            if (e.isPrimaryButtonDown()){
                x = e.getScreenX();
                y = e.getScreenY();
                e.consume();
            }
        });
        
        background.setOnMouseReleased(e->{
            resizeService.stop(stage, background, e);
            e.consume();
        });
    }
    
    @Override
    protected void initListener(){
        initBaseListener();
        
        titleBar.setOnMouseDragged(e->{
            if (!e.isPrimaryButtonDown() || (x == -1.0 && y == -1.0)){
                e.consume();
                return;
            }
            if (e.isStillSincePress()){
                e.consume();
                return;
            }
//            if(((DragDockService)dragService).isDock()){
//                shadowVisible.set(true); 
//            }
            if(maximized.get()){
                maximize();
            }
            titleBar.setCursor(dragCursor);
            currentDrag=true; 
            dragService.run(stage, titleBar,x, y,effectiveShadowSize,effectiveResizeSize,e,this);
            e.consume();
        });

        background.setOnMouseDragged(e->{
            if (!e.isPrimaryButtonDown() || (x == -1 && y == -1)){
                e.consume();
                return;
            }
            else if(!stage.isResizable()){
                e.consume();
                return;
            }
            else if(maximized.get()){
                e.consume();
                return;
            }
            else if (e.isStillSincePress()){
                e.consume();
                return;
            }
            resizeService.run(stage, background, x, y,effectiveShadowSize,effectiveResizeSize, e,this);
            e.consume();
        });

        background.setOnMouseMoved(e->{
            if(!stage.isResizable()||maximized.get()){
                e.consume();
                background.setCursor(defaultCursor);
                return;
            }
            
            double x = e.getX()-effectiveShadowSize;
            double y = e.getY()-effectiveShadowSize;
            Bounds boundsInParent = background.getBoundsInParent();
            double width=boundsInParent.getWidth();
            double height=boundsInParent.getHeight();
            if (determineRightEdge(x, y, width)){
                if (y < effectiveResizeSize) {
                    background.setCursor(neResizeCursor);
                } else if (y > boundsInParent.getHeight() - effectiveResizeSize) {
                    background.setCursor(seResizeCursor);
                } else {
                    background.setCursor(eResizeCursor);
                }

            } else if (determineLeftEdge(x, y)) {
                if (y < effectiveResizeSize) {
                    background.setCursor(nwResizeCursor);
                } else if (y > boundsInParent.getHeight() - effectiveResizeSize){
                    background.setCursor(swResizeCursor);
                } else {
                    background.setCursor(wResizeCursor);
                }
            } else if (determineTopEdge(x, y)){
                background.setCursor(nResizeCursor);
            } else if (determineBottomEdge(x, y, height)) {
                background.setCursor(sResizeCursor);
            } else {
                background.setCursor(defaultCursor);
            }
            e.consume();
        });
        
        stage.resizableProperty().addListener((ov,o,n)->{
                maximize.setDisable(!n);
                maximizeItem.setDisable(!n); 
        });
    }
    
    /*
    +Call when lang change
    */
    protected void reloadText(){
        textBundle=ResourceBundle.getBundle(resourceName, lang.get(), resourceLoader);
        final String closeText=textBundle.getString(WindowManager.getCommonWindowResources().getCloseString());
        final String minimizeText=textBundle.getString(WindowManager.getCommonWindowResources().getMinimizeString());
        final String maximizeText;
        if(maximized.get()){
            maximizeText=textBundle.getString(WindowManager.getCommonWindowResources().getRestoreString());
        }else{
            maximizeText=textBundle.getString(WindowManager.getCommonWindowResources().getMaximizeString());
        }
        close.getTooltip().setText(closeText);
        closeItem.setText(closeText);
        minimize.getTooltip().setText(minimizeText);
        minimizeItem.setText(minimizeText);
        maximize.getTooltip().setText(maximizeText);
        maximizeItem.setText(maximizeText);
    }
    
    public void maximize(){
        maximize(!maximized.get());
    }
    
    public void setLanguage(String lang){
        this.lang.set(new Locale(lang));
    }
    
    public ReadOnlyObjectProperty<Locale> languageProperty(){
        return lang;
    }
    
    public void maximize(boolean value){
        if(stage.isResizable())maximized.set(value); 
    }
    
    @Override
    public Cursor getSelectCursor() {
        return selectCursor;
    }

    @Override
    public Cursor getDefaultCursor() {
        return defaultCursor;
    }

    @Override
    public Cursor getDragCursor() {
        return dragCursor;
    }

    @Override
    public Cursor getWaitCursor() {
        return waitCursor;
    }

    @Override
    public Cursor getTextCursor() {
        return textCursor;
    }

    @Override
    public Cursor getEResizeCursor() {
        return eResizeCursor;
    }

    @Override
    public Cursor getWResizeCursor() {
        return wResizeCursor;
    }

    @Override
    public Cursor getNResizeCursor() {
        return nResizeCursor;
    }

    @Override
    public Cursor getSResizeCursor() {
        return sResizeCursor;
    }

    @Override
    public Cursor getNEResizeCursor() {
        return neResizeCursor;
    }

    @Override
    public Cursor getSEResizeCursor() {
        return seResizeCursor;
    }

    @Override
    public Cursor getNWResizeCursor() {
        return nwResizeCursor;
    }

    @Override
    public Cursor getSWResizeCursor() {
        return swResizeCursor;
    }
    
    boolean determineRightEdge(double x, double y, double width) {
        return x < width && x > width - effectiveResizeSize;
    }

    boolean determineTopEdge(double x, double y){
        return y >= 0 && y < effectiveResizeSize;
    }

    boolean determineBottomEdge(double x, double y, double height) {
        return y < height && y > height - effectiveResizeSize;
    }

    boolean determineLeftEdge(double x, double y) {
        if (x >= 0 && x < effectiveResizeSize){
            return true;
        }
        return false;
    }
    
    protected static enum DOCKSTATE{
        DOCK_LEFT(0),
        DOCK_RIGHT(1),
        DOCK_TOP(2),
        DOCK_NONE(3);
        protected final int value;
        private DOCKSTATE(int value){
            this.value=value;
        }
    }
    
    protected static class DefaultDragDockService implements DragDockServiceConcrete{
        protected final Stage dockStage=new Stage(StageStyle.TRANSPARENT);
        protected DOCKSTATE lastDockedState ;
        protected Bounds savedDockBounds;///save when docking
        protected double x,y;
        protected double shadowSize;
        protected final Stage owner;
        private final Pane dockRoot=new Pane();
        
        protected DefaultDragDockService(Stage owner){
            this(owner,new Background(new BackgroundFill(Color.rgb(153, 217,234,0.5),CornerRadii.EMPTY,Insets.EMPTY)));
        }
        
        protected DefaultDragDockService(Stage owner,Background rootBackground){
            this.owner=owner;
            initDockStage(rootBackground);
        }
        
        private void initDockStage(Background rootBackground){
            dockStage.initOwner(owner);
            x=-1;
            y=-1;
            lastDockedState=DOCKSTATE.DOCK_NONE;
            Scene dockScene=new Scene(dockRoot); 
            //default color display for dock is rgb(153,217,234,0.5)
            dockRoot.setBackground(rootBackground);
            dockStage.setScene(dockScene);
            dockScene.setFill(Color.TRANSPARENT);
            dockStage.sizeToScene();
            //not understand why i do that? still confused but keep.
            dockStage.show();
            dockStage.hide();
        }
        
        @Override
        public boolean isDock() {
            return lastDockedState!=DOCKSTATE.DOCK_NONE;
        }

        @Override
        public void removeDock() {
            lastDockedState=DOCKSTATE.DOCK_NONE;
        }

        @Override
        public Stage getDockStage() {
            return dockStage;
        }

        @Override
        public void run(Stage stage, Node titleBar, double initX, double initY, double shadowSize, 
                double resizeSize, MouseEvent mouse, BaseCursor cursor) {
            this.shadowSize=shadowSize;
            //first time drag occur!
            if(0==Double.compare(x,-1.0)&&0==Double.compare(y, -1.0)){
                x=initX;
                y=initY;
            }
            //if already dock, remove it!
            if(lastDockedState!=DOCKSTATE.DOCK_NONE&&savedDockBounds!=null){
                //get previous save width stage.
                double width=savedDockBounds.getWidth();
                //center mouse in title-bar 
                stage.setX(mouse.getScreenX() - width/2);
                stage.setY(shadowSize*(-1.0));
                stage.setWidth(width);
                stage.setHeight(savedDockBounds.getHeight());
                //release saveDockBounds (dock meaningless)
                savedDockBounds = null;
                lastDockedState=DOCKSTATE.DOCK_NONE;
            }
            else{
                double mX=mouse.getScreenX();
                double mY=mouse.getScreenY();
                stage.setX(mX-x+stage.getX());
                stage.setY(mY-y+stage.getY());
                x=mX;
                y=mY;
                testDock(mouse);
            }
        }

        @Override
        public void stop(Stage stage, Node titleBar, MouseEvent mouse) {
            x=-1.0;y=-1.0;
            if(stage.isResizable()){
                if (dockStage.isShowing()){
                        dockStage.hide();
                }
                if(lastDockedState!=DOCKSTATE.DOCK_NONE){
                    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                    savedDockBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                    switch (lastDockedState){
                        case DOCK_LEFT:{
                                stage.setX(visualBounds.getMinX()-shadowSize);
                                stage.setY(visualBounds.getMinY()-shadowSize);
                                double width = visualBounds.getWidth() / 2+shadowSize*2;
                                double maxWidth=stage.getMaxWidth();
                                if(maxWidth<width){
                                    width=maxWidth;
                                }     
                                double height = visualBounds.getHeight()+shadowSize*2;
                                double maxHeight=stage.getMaxHeight();
                                if(maxHeight<height){
                                    height=maxHeight;
                                }      
                                stage.setWidth(width);
                                stage.setHeight(height);
                                break;
                            }
                        case DOCK_RIGHT:{
                                double width = visualBounds.getWidth() / 2-shadowSize;
                                stage.setX(width + visualBounds.getMinX());
                                width+=shadowSize*3;
                                double maxWidth=stage.getMaxWidth();
                                if(maxWidth<width){
                                    width=maxWidth;
                                }     
                                double height = visualBounds.getHeight()+shadowSize*2;
                                double maxHeight=stage.getMaxHeight();
                                if(maxHeight<height){
                                    height=maxHeight;
                                }  
                                stage.setWidth(width);
                                stage.setHeight(height);
                                stage.setY(visualBounds.getMinY()-shadowSize);
                                break;
                            }
                        default:{
                            double width = visualBounds.getWidth()+shadowSize*2;
                            double maxWidth=stage.getMaxWidth();
                            if(maxWidth<width){
                                width=maxWidth;
                            }   
                            double height =visualBounds.getHeight()+shadowSize*2;
                            double maxHeight=stage.getMaxHeight();
                            if(maxHeight<height){
                                height=maxHeight;
                            }
                            stage.setX(visualBounds.getMinX()-shadowSize);
                            stage.setY(visualBounds.getMinY()-shadowSize);
                            stage.setWidth(width);
                            stage.setHeight(height);
                            break;
                        }
                    }
                }
            }
            stage.setAlwaysOnTop(false);
        }
        
        void testDock(MouseEvent mouseEvent) {
            if (!owner.isResizable()) {
                return;
            }
            double currentScreenX=mouseEvent.getScreenX(),
                   currentScreenY=mouseEvent.getScreenY();
            
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            double minX = visualBounds.getMinX();
            double minY = visualBounds.getMinY();
            double maxX = visualBounds.getMaxX();
            
            // Dock Left?
            if (currentScreenX <= minX){
                if (lastDockedState == DOCKSTATE.DOCK_LEFT) {
                    return;
                }      
                visibleDockStage(minX,minY,visualBounds.getWidth() / 2,visualBounds.getHeight());
                lastDockedState = DOCKSTATE.DOCK_LEFT;
            } // Dock right?
            else if (currentScreenX >= maxX - 1){
                if (lastDockedState == DOCKSTATE.DOCK_RIGHT) {
                    return;
                }
                double width = visualBounds.getWidth() / 2;
                visibleDockStage(minX + width,minY,width,visualBounds.getHeight());
                lastDockedState = DOCKSTATE.DOCK_RIGHT;
            }// Dock top? 
            else if (currentScreenY <= minY) {   
                if (lastDockedState == DOCKSTATE.DOCK_TOP) {
                    return;
                }
                visibleDockStage(minX,minY,visualBounds.getWidth(),visualBounds.getHeight());
                lastDockedState = DOCKSTATE.DOCK_TOP;
            }
            else{
                if (dockStage.isShowing()){
                    dockStage.hide();
                }
                lastDockedState = DOCKSTATE.DOCK_NONE;
                owner.setAlwaysOnTop(false);
            }
        }
        
        protected void visibleDockStage(double x,double y,double w,double h){
            dockStage.setX(x);
            dockStage.setY(y);
            dockStage.setWidth(w);
            dockStage.setHeight(h);
            dockStage.show();
            owner.setAlwaysOnTop(true); 
        }

        @Override
        public void setDockBackgorundColor(Color c) {
            dockRoot.setBackground(new Background(new BackgroundFill(c,CornerRadii.EMPTY,Insets.EMPTY)));
        }
    }
    
    protected static class  DefaultResizeService implements ResizeService{ 
        private double x=-1.0;
        private double y=-1.0;
        private double newMouseDragX=0.0;
        private double newMouseDragY=0.0;
        
        @Override
        public void run(Stage stage, Node background, double initX, double initY, 
                double shadowSize, double resizeSize, MouseEvent mouse, BaseCursor cursor) {
            if(0==Double.compare(x,-1.0)&&0==Double.compare(y,-1.0)){x=initX;y=initY;}
            newMouseDragX = mouse.getScreenX();
            newMouseDragY = mouse.getScreenY();
            double deltax = newMouseDragX - x;
            double deltay = newMouseDragY - y;
            Cursor cursorBackground = background.getCursor();
            if (cursorBackground.equals(cursor.getEResizeCursor())) {
                setStageWidth(stage, stage.getWidth() + deltax);
            } 
            else if (cursorBackground.equals(cursor.getNEResizeCursor())) {
                //height change so Y change.
                if (setStageHeight(stage, stage.getHeight() - deltay)) {
                    setStageY(stage, stage.getY() + deltay,shadowSize);
                }
                setStageWidth(stage, stage.getWidth() + deltax);
            } 
            else if (cursorBackground.equals(cursor.getSEResizeCursor())) {
                setStageWidth(stage, stage.getWidth() + deltax);
                setStageHeight(stage, stage.getHeight() + deltay);
            } 
            else if (cursorBackground.equals(cursor.getSResizeCursor())) {
                setStageHeight(stage, stage.getHeight() + deltay);
            } 
            else if (cursorBackground.equals(cursor.getWResizeCursor())) {
                if (setStageWidth(stage, stage.getWidth() - deltax)) {
                    stage.setX(stage.getX() + deltax);
                }
            } 
            else if (cursorBackground.equals(cursor.getSWResizeCursor())) {
                //width change so X change
                if (setStageWidth(stage, stage.getWidth() - deltax)) {
                    stage.setX(stage.getX() + deltax);
                }
                setStageHeight(stage, stage.getHeight() + deltay);
            }
            else if (cursorBackground.equals(cursor.getNWResizeCursor())) {
                if (setStageWidth(stage, stage.getWidth() - deltax)) {
                    stage.setX(stage.getX() + deltax);
                }
                if (setStageHeight(stage, stage.getHeight() - deltay)) {
                    setStageY(stage, stage.getY() + deltay,shadowSize);
                }
            } 
            else if (cursorBackground.equals(cursor.getNResizeCursor())) {
                if (setStageHeight(stage, stage.getHeight() - deltay)){
                    setStageY(stage, stage.getY() + deltay,shadowSize);
                }
            }
        }

        @Override
        public void stop(Stage stage, Node background, MouseEvent mouse) {
            x=-1.0;
            y=-1.0;
        }
        
        private boolean setStageWidth(Stage stage, double width) {
            if (width >stage.getMinWidth()) {
                stage.setWidth(width);
                x = newMouseDragX;
                return true;
            }
            return false;
        }

        private boolean setStageHeight(Stage stage, double height) {
            if (height >stage.getMinHeight()){
                stage.setHeight(height);
                //update relative Y position
                y = newMouseDragY;
                return true;
            }
            return false;
        }

        private void setStageY(Stage stage, double y,double shadow) {
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            if (y < visualBounds.getHeight()&& y>= visualBounds.getMinY()-shadow){
                stage.setY(y);
            }
        }
    }
}
