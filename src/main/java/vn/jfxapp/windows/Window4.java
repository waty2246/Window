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
import vn.jfxapp.windows.service.FullscreenService;
import vn.jfxapp.windows.service.ResizeService;
import vn.jfxapp.windows.service.ShowService;
import java.io.InputStream;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class Window4 extends Window3{
    @FXML
    protected Button fullscreen;
    protected final FullscreenService fullscreenService;
    protected final CheckMenuItem fullscreenItem;
    protected final KeyCombination fullscreenKey;
    protected Bounds savedFullscreenBounds;
    
    public Window4(){
        this(new Stage());
    }
    
    public Window4(Stage stage){
        this(stage,
            WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window4.fxml"),
            WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window4.css"));
    }
    
    protected Window4(Stage stage,InputStream input,String stylesheet){
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
                new DefaultDragDockService(stage),
                null,null,null,null,null,//base service (drag fos sho clo rez)
                null,
                null,
                null,
                null,
                null,
                null);
    }
    
    protected Window4(Stage stage,InputStream input,String stylesheet,
            double shadowSize,double resizeSize,
            Cursor defaultCursor,Cursor dragCursor,Cursor selectCursor,Cursor waitCursor,Cursor nResizeCursor,
            Cursor wResizeCursor,Cursor sResizeCursor,Cursor eResizeCursor,Cursor nwResizeCursor,
            Cursor neResizeCursor,Cursor swResizeCursor,Cursor seResizeCursor,Cursor textCursor,
            DragService dragService,FocusService focusService,ShowService showService,
            CloseService closeService, ResizeService resizeService,FullscreenService fullscreenService,
            KeyCombination closeKey,KeyCombination minimizeKey,KeyCombination maximizeKey,KeyCombination fullscreenKey,
            String baseTextName,ClassLoader bundleClassLoader){
        super(stage,input,stylesheet,shadowSize,resizeSize,defaultCursor,dragCursor,
                selectCursor,waitCursor,nResizeCursor,wResizeCursor,sResizeCursor,eResizeCursor,
                nwResizeCursor,neResizeCursor,swResizeCursor,seResizeCursor,textCursor,
                dragService,focusService,showService,closeService,resizeService,closeKey,minimizeKey,
                maximizeKey,baseTextName,bundleClassLoader);
        fullscreenItem=new CheckMenuItem();
        if(fullscreenKey!=null)this.fullscreenKey=fullscreenKey;
        else this.fullscreenKey=WindowManager.getCommonWindowKeys().getFullScreenKeys();
        if(fullscreenService!=null)this.fullscreenService=fullscreenService;
        else this.fullscreenService=new DefaultFullscreenService();
    }
    
    @Override
    protected void initStyle(){
        super.initStyle();
        fullscreen.getStyleClass().add(WindowManager.getCommonWindowStyles().getFullScreenStyle());
        fullscreen.setStyle("-fx-background-color:transparent;");
    }
    
    @Override
    protected void initUI(){
        super.initUI();
        fullscreenItem.setAccelerator(fullscreenKey);
        final String fullscreenText=textBundle.getString(WindowManager.getCommonWindowResources().getFullScreenString());
        if(null==fullscreen.getTooltip())fullscreen.setTooltip(new Tooltip(fullscreenText));
        else fullscreen.getTooltip().setText(fullscreenText);
        fullscreenItem.setText(fullscreenText);
        contextMenu.getItems().add(fullscreenItem);
    }
    
    @Override
    protected void initListener(){
        initBaseListener();
        
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
            if(n){
                shadowVisible.set(false);
                ///toggle class fullscreen and tooltip
                savedFullscreenBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                fullscreen.getTooltip().setText(textBundle.getString(WindowManager.getCommonWindowResources().getFullScreenString())); 
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
    }
    
    @Override
    protected void initBaseListener(){
        super.initBaseListener();
        fullscreenItem.setOnAction(e->fullscreen()); 
    }

    @Override
    public void minimize(boolean value){
        if(!stage.isFullScreen())stage.setIconified(value);
    }
    
    @Override
    public void maximize(boolean value){
        if(stage.isResizable())if(!stage.isFullScreen())maximized.set(value);
    }
    
    public void fullscreen(){
        fullscreen(!stage.isFullScreen());
    }
    
    public void fullscreen(boolean value){
        if(stage.isResizable())stage.setFullScreen(value);
    }
    
    @Override
     protected void reloadText(){
        super.reloadText();
        final String fullscreenText;
        if(stage.isFullScreen()){
            fullscreenText=textBundle.getString(WindowManager.getCommonWindowResources().getUnfullScreenString());
        }else{
            fullscreenText=textBundle.getString(WindowManager.getCommonWindowResources().getFullScreenString());
        }
        fullscreen.getTooltip().setText(fullscreenText);
        fullscreenItem.setText(fullscreenText);
    }
    
    private static class DefaultFullscreenService implements FullscreenService{

        TranslateTransition translateFullScreenButtonXrun,translateFullScreenButtonXstop;
        {
            translateFullScreenButtonXrun=new TranslateTransition();
            translateFullScreenButtonXrun.setDuration(Duration.millis(800));
            translateFullScreenButtonXrun.setToX(70);

            translateFullScreenButtonXstop=new TranslateTransition();
            translateFullScreenButtonXstop.setDuration(Duration.millis(800));
            translateFullScreenButtonXstop.setToX(0);
        }
        
        @Override
        public void run(Node titleBar, Node layout, Node fullscreenButton){
            fullscreenButton.setCache(true);
            fullscreenButton.setCacheHint(CacheHint.SPEED);
            translateFullScreenButtonXrun.setNode(fullscreenButton);
            translateFullScreenButtonXstop.setNode(fullscreenButton);
            translateFullScreenButtonXstop.stop();
            translateFullScreenButtonXrun.play();
        }

        @Override
        public void stop() {
            translateFullScreenButtonXrun.stop();
            translateFullScreenButtonXstop.play();
        }
    }
}
