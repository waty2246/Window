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

import javafx.stage.Stage;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 * @param <W>
 */
public class Window4XBuilder<W extends Window4XBuilder<W>> extends Window4Builder<W> {
    @Override
    public Window4X build(){
        if(null==stage){
            stage=new Stage();
        }
         if(null==dragService){
            if(dockBackground!=null)
                dragService=new Window3.DefaultDragDockService(stage,dockBackground);
            else
                dragService=new Window3.DefaultDragDockService(stage);
        }
        if(null==input){
            input=WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window4x.fxml");
        }
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window4x.css");
        }
        
        Window4X window= new Window4X(stage,
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
                baseTextName,
                bundleClassLoader);
        window.construct();
        return window;
    }
}
