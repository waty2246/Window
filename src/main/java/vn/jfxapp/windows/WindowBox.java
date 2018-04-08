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
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vn.jfxapp.windows.service.CloseService;
import vn.jfxapp.windows.service.DragService;
import vn.jfxapp.windows.service.FocusService;
import vn.jfxapp.windows.service.ShowService;

/**
 * Display message to end user.
 * Support 3 type of WindowBox:
  +MB_OK_CANCEL
  +MB_OK
  +MB_OK_CANCEL_RETRY
 * @author waty
 */
public class WindowBox extends Window1{
    public WindowBox(){
        this(new Stage());
    }
    
    public WindowBox(Stage stage){
        super(stage,
                WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window1.fxml"),
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("windowbox.css"),
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
    
    protected WindowBox(Stage stage,InputStream input,String stylesheet,double shadowSize,double resizeSize,
            Cursor defaultCursor,Cursor dragCursor,DragService dragService,
            FocusService focusService,ShowService showService,CloseService closeService,KeyCombination closeKey){
        super(stage,input,stylesheet,shadowSize,resizeSize,defaultCursor,dragCursor,dragService,focusService,showService,closeService,closeKey);
    }
    
    @Override
    protected void initListener(){
        layout.addListener((ov,o,n)->{
            decoration.getChildren().remove(o);
            decoration.getChildren().add(n);
            AnchorPane.setTopAnchor(n, titleBar.getHeight());
            AnchorPane.setLeftAnchor(n, 0.0);
            AnchorPane.setRightAnchor(n, 0.0);
            AnchorPane.setBottomAnchor(n, 0.0);
        });
        
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
        
        titleBar.heightProperty().addListener((ov,o,n)->{
            AnchorPane.setTopAnchor(layout.get(), n.doubleValue());
        });
        
        title.textProperty().bindBidirectional(stage.titleProperty());

        decoration.setOnMousePressed(e->{
            if (e.isPrimaryButtonDown()) {
                x = e.getScreenX();
                y = e.getScreenY();
            } else {
                x = -1;
                y = -1;
            }
            e.consume();
        });

        decoration.setOnMouseDragged(e->{
            if (!e.isPrimaryButtonDown() || (x == -1 && y == -1)){
                e.consume();
                return;
            }
            if (e.isStillSincePress()){
                e.consume();
                return;
            }
            decoration.setCursor(dragCursor);
            dragService.run(stage, decoration,x, y,shadowSize,resizeSize,e,null);
            e.consume();
        });

        decoration.setOnMouseReleased(e->{
            decoration.setCursor(defaultCursor);
            ///restore cursor.
            x = -1;
            y = -1;
            /// implement dock.
            dragService.stop(stage,decoration,e);
            e.consume();
        });
        
        scene.getAccelerators().put(closeKey, ()->close());
        
        close.setOnAction(e->close());
    }
}
