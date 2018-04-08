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

import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import vn.jfxapp.windows.service.DragService;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 * @param <W>
 */
public class Window4XAppBuilder<W extends Window4XAppBuilder<W>> extends Window4Builder<W>{
    private KeyCombination dockLeftKey;
    private KeyCombination dockRightKey;
    private KeyCombination dockTopKey;
    @SuppressWarnings("unchecked")
    public W setDockLeftKey(KeyCombination key){
        this.dockLeftKey=key;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDockRightKey(KeyCombination key){
        this.dockRightKey=key;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDockTopKey(KeyCombination key){
        this.dockTopKey=key;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public W setDragService(DragService dragService){
        //prevent change dragService.
        return (W)this;
    }
    
    @Override
    public Window4XApp build(){
        if(null==stage){
            stage=new Stage();
        }
        if(dockBackground!=null)
            dragService=new Window4XApp.Window4XAppDragDockService(stage,dockBackground);
        else
            dragService=new Window4XApp.Window4XAppDragDockService(stage);
        
        if(null==input){
            input=WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window4xapp.fxml");
        }
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window4xapp.css");
        }
        
        Window4XApp window= new Window4XApp(stage,
                input,
                stylesheet,
                shadowSize,
                resizeSize,
                defaultCursor,
                dragCursor,
                selectCursor,
                waitCursor,
                nResizeCursor,
                wResizeCursor,
                sResizeCursor,
                eResizeCursor,
                nwResizeCursor,
                neResizeCursor,
                swResizeCursor,
                seResizeCursor,
                textCursor,
                dragService,
                focusService,
                showService,
                closeService,
                resizeService,
                fullscreenService,
                closeKey,
                minimizeKey,
                maximizeKey,
                fullscreenKey,
                dockLeftKey,
                dockRightKey,
                dockTopKey,
                baseTextName,
                bundleClassLoader);
        window.construct();
        return window;
    }
}
