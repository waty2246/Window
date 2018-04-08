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
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vn.jfxapp.windows.cursor.BaseCursor;

/**
 * <p>
 * {@link Window1} class actually wrapper {@link Stage} class, almost thing call in {@link Stage}
 * will valid in {@link Window1} except {@link Stage#close() } ,{@link Stage#setOnCloseRequest(javafx.event.EventHandler) }
 * ,{@link Stage#show() } {@link Stage#showAndWait() }.
 * </p>
 * <p>
 * The default {@link CloseService} has an implements name {@link CloseServicecConcrete} supply alternative
 * function for {@link Stage#setOnCloseRequest(javafx.event.EventHandler) }
 * </p>
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class Window1 extends Window0 {
    @FXML 
    protected Button close;
    @FXML
    protected Label title;
    @FXML
    protected Pane titleBar;
    protected final Cursor dragCursor;
    protected final KeyCombination closeKey;
    protected final DragService dragService;
    protected double x,y;
    
    public Window1(){
        this(new Stage());
    }
    
    public Window1(Stage stage){
        this(stage,
                WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window1.fxml"),
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window1.css"),
                WindowManager.getCommonWindowAttributes().getShadowSize(),
                WindowManager.getCommonWindowAttributes().getResizeSize(),
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    protected Window1(Stage stage,InputStream input,String stylesheet,double shadowSize,double resizeSize,
            Cursor defaultCursor,Cursor dragCursor,DragService dragService,
            FocusService focusService,ShowService showService,CloseService closeService,KeyCombination closeKey){
        super(stage,input,stylesheet,shadowSize,resizeSize,defaultCursor,focusService,showService,closeService);
        
        if(dragCursor!=null)this.dragCursor=dragCursor;
        else this.dragCursor=WindowManager.getCommonWindowCursor().getDragCursor();
        
        if(dragService!=null)this.dragService=dragService;
        else this.dragService=new DefaultDragService();
        
        if(closeKey!=null)this.closeKey=closeKey;
        else this.closeKey=WindowManager.getCommonWindowKeys().getCloseKeys();
    } 

    @Override
    protected void initDecoration(){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setController(this);
            decoration=(AnchorPane)fxmlLoader.load(input);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void initStyle(){
        super.initStyle();
        close.getStyleClass().add(WindowManager.getCommonWindowStyles().getCloseStyle());
        close.setStyle("-fx-background-color:transparent;");
        title.getStyleClass().add(WindowManager.getCommonWindowStyles().getTitleStyle());
        titleBar.getStyleClass().add(WindowManager.getCommonWindowStyles().getTitleBarStyle());
    }
    
    @Override
    protected void initUI(){
        stage.setResizable(false); 
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
    }
    
    @Override
    protected void initBaseListener(){
        super.initBaseListener();
        
        title.textProperty().bindBidirectional(stage.titleProperty());
        
        layout.addListener((ov,o,n)->{
            decoration.getChildren().remove(o);
            decoration.getChildren().add(n);
            AnchorPane.setTopAnchor(n, titleBar.getHeight());
            AnchorPane.setLeftAnchor(n, 0.0);
            AnchorPane.setRightAnchor(n, 0.0);
            AnchorPane.setBottomAnchor(n, 0.0);
        });
        
        titleBar.heightProperty().addListener((ov,o,n)->{
            AnchorPane.setTopAnchor(layout.get(), n.doubleValue());
        });
        
        titleBar.setOnMousePressed(e->{
            if (e.isPrimaryButtonDown()) {
                x = e.getScreenX();
                y = e.getScreenY();
            } else {
                x = -1;
                y = -1;
            }
            e.consume();
        });

        close.setOnAction(e->close());
    }
    
    @Override
    protected void initListener(){
        this.initBaseListener();
        
        titleBar.setOnMouseDragged(e->{
            if (!e.isPrimaryButtonDown() || (x == -1 && y == -1)){
                e.consume();
                return;
            }
            if (e.isStillSincePress()){
                e.consume();
                return;
            }
            titleBar.setCursor(dragCursor);
            dragService.run(stage, titleBar,x, y,shadowSize,resizeSize,e,null);
            e.consume();
        });

        titleBar.setOnMouseReleased(e->{
            titleBar.setCursor(defaultCursor);
            ///restore cursor.
            x = -1;
            y = -1;
            /// implement dock.
            dragService.stop(stage,titleBar,e);
            e.consume();
        });
        
        scene.getAccelerators().put(closeKey, ()->close());
    }

    /**
     * Close this window.
     * Do not call close in {@link Stage}, it'll break CloseService.
     */
   @Override
   public void close(){
       Platform.runLater(()->stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
   }
   
   static class DefaultDragService implements DragService{
       double initX,initY;
        {
            initX=initY=-1.0;
        }
        
        @Override
        public void run(Stage stage, Node node, double initX, double initY, double shadow, double resize, MouseEvent mouse, BaseCursor cursor){
            if(this.initX==-1.0&&this.initY== -1.0){this.initX=initX;this.initY=initY;}
            double mX=mouse.getScreenX();
            double mY=mouse.getScreenY();
            stage.setX(mX-this.initX+stage.getX());
            stage.setY(mY-this.initY+stage.getY());
            this.initX=mX;
            this.initY=mY;
        }

        @Override
        public void stop(Stage stage, Node node, MouseEvent mouse) {
            this.initX=-1.0;
            this.initY=-1.0;
        } 
   };
}
