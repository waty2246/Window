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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import vn.jfxapp.windows.service.CloseService;
import vn.jfxapp.windows.service.DragService;
import vn.jfxapp.windows.service.FocusService;
import vn.jfxapp.windows.service.FullscreenService;
import vn.jfxapp.windows.service.ResizeService;
import vn.jfxapp.windows.service.ShowService;
import vn.jfxapp.windows.cursor.BaseCursor;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public final class Window4XApp extends Window4X{
    private final List<ToggleButton> dockMenu=new ArrayList<>(3);
    private final ToggleGroup dockGroup=new ToggleGroup();
    private final List<CheckMenuItem> dockItem=new ArrayList<>(3);
    private final List<KeyCombination> dockKey=new ArrayList<>(3);
    
    public Window4XApp(){
        this(new Stage());
    }
    
    public Window4XApp(Stage stage){
        this(stage,WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window4xapp.fxml"),
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window4xapp.css"));
    }

    protected Window4XApp(Stage stage,InputStream input,String stylesheet){
        this(stage,input,stylesheet, 
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
                new DefaultDragDockService(stage),null,null,null,null,null,//base service (drag fos sho clo rez)
                null,
                null,
                null,
                null,
                WindowManager.getCommonWindowKeys().getDockLeftKeys(),
                WindowManager.getCommonWindowKeys().getDockRightKeys(),
                WindowManager.getCommonWindowKeys().getDockTopKeys(),
                null,
                null);
    }
    
    protected Window4XApp(Stage stage,InputStream input,String stylesheet,
            double shadowSize,double resizeSize,
            Cursor defaultCursor,Cursor dragCursor,Cursor selectCursor,Cursor waitCursor,Cursor nResizeCursor,
            Cursor wResizeCursor,Cursor sResizeCursor,Cursor eResizeCursor,Cursor nwResizeCursor,
            Cursor neResizeCursor,Cursor swResizeCursor,Cursor seResizeCursor,Cursor textCursor,
            DragService dragService,FocusService focusService,ShowService showService,
            CloseService closeService, ResizeService resizeService,FullscreenService fullscreenService,
            KeyCombination closeKey,KeyCombination minimizeKey,KeyCombination maximizeKey,KeyCombination fullscreenKey,
            KeyCombination dockLeftKey,KeyCombination dockRightKey,KeyCombination dockTopKey,
            String baseTextName,ClassLoader bundleClassLoader){
        super(stage,input,stylesheet,shadowSize,resizeSize,defaultCursor,dragCursor,
                selectCursor,waitCursor,nResizeCursor,wResizeCursor,sResizeCursor,eResizeCursor,
                nwResizeCursor,neResizeCursor,swResizeCursor,seResizeCursor,textCursor,
                dragService,focusService,showService,closeService,resizeService,fullscreenService,
                closeKey,minimizeKey,maximizeKey,fullscreenKey,baseTextName,bundleClassLoader);
        for(int i=0;i<3;i++){
            dockItem.add(new CheckMenuItem());
        }
        if(dockLeftKey!=null)this.dockKey.add(dockLeftKey);
        else this.dockKey.add(WindowManager.getCommonWindowKeys().getDockLeftKeys());
        if(dockRightKey!=null)this.dockKey.add(dockRightKey);
        else this.dockKey.add(WindowManager.getCommonWindowKeys().getDockRightKeys());
        if(dockTopKey!=null)this.dockKey.add(dockTopKey);
        else this.dockKey.add(WindowManager.getCommonWindowKeys().getDockTopKeys());
    }

    @Override
    protected void initUI(){
        super.initUI();
        dockMenu.add(new ToggleButton());//left
        dockMenu.add(new ToggleButton());//right
        dockMenu.add(new ToggleButton());//top
        for(int i=0;i<3;i++){
            dockMenu.get(i).setToggleGroup(dockGroup);
            dockMenu.get(i).getStyleClass().clear();
            dockMenu.get(i).getStyleClass().addAll(WindowManager.getCommonWindowStyles().getCommonDockStyle(),
                    WindowManager.getCommonWindowStyles().getDockStyle()[i]);
            dockMenu.get(i).setPrefSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
            dockMenu.get(i).setMaxSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
            dockMenu.get(i).setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
            String text=textBundle.getString(WindowManager.getCommonWindowResources().getDockString()[i]);
            if(null==dockMenu.get(i).getTooltip())dockMenu.get(i).setTooltip(new Tooltip(text));
            else dockMenu.get(i).getTooltip().setText(text); 
            CheckMenuItem item=dockItem.get(i);
            item.setAccelerator(dockKey.get(i));
            item.setText(text); 
            contextMenu.getItems().add(item);
        }
        ((Window4XAppDragDockService)dragService).initDockMember(dockMenu,dockItem,shadowSize);
        extend.getChildren().addAll(dockMenu);
    }
    
    @Override
    protected void initListener(){
        super.initBaseListener();
        
        titleBar.setOnMouseDragged(e->{
            if (!e.isPrimaryButtonDown() || (x == -1.0 && y == -1.0)){
                e.consume();
                return;
            }
            if(stage.isFullScreen()){
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
            currentDrag=true;
            if(maximized.get()){
                maximize();
            }
            titleBar.setCursor(dragCursor);
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
            else if (stage.isFullScreen()){
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
            if(!stage.isResizable()||stage.isFullScreen()||maximized.get()){
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
        
        stage.fullScreenProperty().addListener((ov,o,n)->{
            maximize.setVisible(!n);
            minimize.setVisible(!n);
            drag.setVisible(!n);
            minimizeItem.setVisible(!n);
            maximizeItem.setVisible(!n);
            fullscreenItem.setSelected(n);
            for(int i=0;i<3;i++){
                dockMenu.get(i).setDisable(n);
                dockItem.get(i).setVisible(!n);
            }
            if(n){
                shadowVisible.set(false);
                ///toggle class fullscreen and tooltip
                savedFullscreenBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                fullscreen.getTooltip().setText(textBundle.getString(WindowManager.getCommonWindowResources().getUnfullScreenString())); 
                fullscreen.getStyleClass().add(WindowManager.getCommonWindowStyles().getUnfullScreenStyle());
                fullscreenService.run(titleBar,layout.get(),fullscreen);
                ///transition fullscreen button
            }else{
                if(!maximized.get()) shadowVisible.set(true);
                ///toggle class and tooltip
                if(savedFullscreenBounds!=null){
                    stage.setX(savedFullscreenBounds.getMinX());
                    stage.setY(savedFullscreenBounds.getMinY());
                    stage.setWidth(savedFullscreenBounds.getWidth());
                    stage.setHeight(savedFullscreenBounds.getHeight());
                    savedFullscreenBounds = null;
                }
                fullscreen.getTooltip().setText(textBundle.getString(WindowManager.getCommonWindowResources().getFullScreenString()));
                fullscreen.getStyleClass().remove(WindowManager.getCommonWindowStyles().getUnfullScreenStyle());
                fullscreenService.stop();
            }
        });
        
        stage.resizableProperty().addListener((ov,o,n)->{
                maximize.setDisable(!n);
                maximizeItem.setDisable(!n); 
                fullscreen.setDisable(!n);
                fullscreenItem.setDisable(!n); 
        });
        
        fullscreen.setOnAction(e->fullscreen());
        dockItem.get(0).setOnAction(e->{
            ((Window4XAppDragDockService)dragService).dockLeft();
        }); 
        dockItem.get(1).setOnAction(e->{
            ((Window4XAppDragDockService)dragService).dockRight();
        });
        dockItem.get(2).setOnAction(e->{
            ((Window4XAppDragDockService)dragService).dockTop();
        });
    }
    
    @Override
     protected void reloadText(){
        super.reloadText();
        for(int i=0;i<3;i++){
            dockMenu.get(i).getTooltip().setText(textBundle.getString(WindowManager.getCommonWindowResources().getDockString()[i]));
        }
    }
    
    protected final static class Window4XAppDragDockService extends DefaultDragDockService{
        private List<ToggleButton> dockMenu;
        private List<CheckMenuItem> dockItem;
        protected Window4XAppDragDockService(Stage owner){
            this(owner,new Background(new BackgroundFill(Color.rgb(153, 217,234,0.5),CornerRadii.EMPTY,Insets.EMPTY)));
        }
        
        protected Window4XAppDragDockService(Stage owner,Background rootBackground){
           super(owner,rootBackground);
        }
        
        private void initDockMember(List<ToggleButton> dockMenu,List<CheckMenuItem> dockItem,double shadowSize){
            this.dockMenu=dockMenu;
            this.dockItem=dockItem;
            this.shadowSize=shadowSize;
            this.dockMenu.get(0).setOnMouseClicked(e->{
                dockLeft();
            });
            
             this.dockMenu.get(1).setOnMouseClicked(e->{
                dockRight();
            });
            
             this.dockMenu.get(2).setOnMouseClicked(e->{
                dockTop();
            });
        }
        
        private void dockLeft(){
            if(lastDockedState!=DOCKSTATE.DOCK_LEFT){
                if(lastDockedState!=DOCKSTATE.DOCK_NONE){
                    undocking(0,0);
                }
                lastDockedState=DOCKSTATE.DOCK_LEFT;
                docking();
            }
            else{
                undocking(-shadowSize,-shadowSize);
            }
        }
        
        private void dockRight(){
            if(lastDockedState!=DOCKSTATE.DOCK_RIGHT){
                if(lastDockedState!=DOCKSTATE.DOCK_NONE){
                    undocking(-shadowSize,-shadowSize);
                }
                lastDockedState=DOCKSTATE.DOCK_RIGHT;
                docking();
            }
            else{
               Rectangle2D bounds=Screen.getPrimary().getVisualBounds();
               undocking((bounds.getWidth()/2)-shadowSize,-shadowSize);
            }
        }
        
        private void dockTop(){
            if(lastDockedState!=DOCKSTATE.DOCK_TOP){
                if(lastDockedState!=DOCKSTATE.DOCK_NONE){
                    undocking(-shadowSize,-shadowSize);
                }
                lastDockedState=DOCKSTATE.DOCK_TOP;
                docking();
            }
            else{
                undocking(-shadowSize,-shadowSize);
            }
        }
        
        @Override
        public void run(Stage stage, Node titleBar, double initX, double initY, double shadowSize, 
                double resizeSize, MouseEvent mouse, BaseCursor cursor) {
            this.shadowSize=shadowSize;
            if(0==Double.compare(x,-1.0)&&0==Double.compare(y, -1.0)){
                x=initX;
                y=initY;
            }
            //if already dock, remove it!
            if(lastDockedState!=DOCKSTATE.DOCK_NONE&&savedDockBounds!=null){
                undocking(mouse.getScreenX() - savedDockBounds.getWidth()/2,shadowSize*(-1.0));
            }
            else{
                double mX=mouse.getScreenX();
                double mY=mouse.getScreenY();
                owner.setX(mX-x+owner.getX());
                owner.setY(mY-y+owner.getY());
                x=mX;
                y=mY;
                testDock(mouse);
            }
        }

        @Override
        public void stop(Stage stage, Node titleBar, MouseEvent mouse) {
            x=-1.0;
            y=-1.0;
            if(lastDockedState!=DOCKSTATE.DOCK_NONE){
                    docking();
            }
            owner.setAlwaysOnTop(false);
        }

        private void docking(){
            //test whether stage can dockable?
            if(owner.isResizable()){
                if (dockStage.isShowing()){
                        dockStage.hide();
                }
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                savedDockBounds = new BoundingBox(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight());
                dockMenu.get(lastDockedState.value).getStyleClass().add(WindowManager.getCommonWindowStyles().getDockSelectedStyle()[lastDockedState.value]);
                switch (lastDockedState){
                    case DOCK_LEFT:{
                        owner.setX(visualBounds.getMinX()-shadowSize);
                        owner.setY(visualBounds.getMinY()-shadowSize);
                        double width = visualBounds.getWidth() / 2+shadowSize*2;
                        double maxWidth=owner.getMaxWidth();
                        if(maxWidth<width){
                            width=maxWidth;
                        }     
                        double height = visualBounds.getHeight()+shadowSize*2;
                        double maxHeight=owner.getMaxHeight();
                        if(maxHeight<height){
                            height=maxHeight;
                        }      
                        owner.setWidth(width);
                        owner.setHeight(height);
                        break;
                    }
                    case DOCK_RIGHT:{
                        double width = visualBounds.getWidth() / 2-shadowSize;
                        owner.setX(width + visualBounds.getMinX());
                        width+=shadowSize*3;
                        double maxWidth=owner.getMaxWidth();
                        if(maxWidth<width){
                            width=maxWidth;
                        }     
                        double height = visualBounds.getHeight()+shadowSize*2;
                        double maxHeight=owner.getMaxHeight();
                        if(maxHeight<height){
                            height=maxHeight;
                        }  
                        owner.setWidth(width);
                        owner.setHeight(height);
                        owner.setY(visualBounds.getMinY()-shadowSize);
                        break;
                    }
                    default:{
                        double width = visualBounds.getWidth()+shadowSize*2;
                        double maxWidth=owner.getMaxWidth();
                        if(maxWidth<width){
                            width=maxWidth;
                        }   
                        double height =visualBounds.getHeight()+shadowSize*2;
                        double maxHeight=owner.getMaxHeight();
                        if(maxHeight<height){
                            height=maxHeight;
                        }
                        owner.setX(visualBounds.getMinX()-shadowSize);
                        owner.setY(visualBounds.getMinY()-shadowSize);
                        owner.setWidth(width);
                        owner.setHeight(height);
                        break;
                    }
                }
            }
        }

        //proof savedDockBounds alway != null when call this function.
        private void undocking(double x,double y){
            owner.setX(x);
            owner.setY(y);
            owner.setWidth(savedDockBounds.getWidth());
            owner.setHeight(savedDockBounds.getHeight());
            //release saveDockBounds (dock meaningless)
            savedDockBounds = null;
            dockMenu.get(lastDockedState.value).getStyleClass().remove(2);
            dockItem.get(lastDockedState.value).setSelected(false);
            lastDockedState=DOCKSTATE.DOCK_NONE;
        }
    }
}
