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
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class Window4X  extends Window4{
    @FXML 
    protected Pane extend;
    
    public Window4X(){
        this(new Stage());
    }
    
    public Window4X(Stage stage){
        this(stage,
                WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window4x.fxml"),
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window4x.css"));
    }

    protected Window4X(Stage stage,InputStream input,String stylesheet){
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
                null,
                null);
    }
    
    protected Window4X(Stage stage,InputStream input,String stylesheet,
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
                dragService,focusService,showService,closeService,resizeService,fullscreenService,
                closeKey,minimizeKey,maximizeKey,fullscreenKey,baseTextName,bundleClassLoader);
    }
    
    @Override
    protected void initStyle(){
        super.initStyle();
        extend.getStyleClass().add(WindowManager.getCommonWindowStyles().getExtendStyle());
    }
    
    public Pane getExtend(){
        return extend;
    }
}
