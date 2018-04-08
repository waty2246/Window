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

import vn.jfxapp.windows.service.DragService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;


/**
 * @author VO ANH TUAN (tuana1648@gmail.com)
 * @param <W>
 */
public class Window1Builder<W extends Window1Builder<W>> extends Window0Builder<W>{
    protected Cursor dragCursor;
    protected DragService dragService;
    protected KeyCombination closeKey;
    protected InputStream input;
    
    /**
     * Set FXML file for window decoration from the system path.
     * The file must contain a close button.
     * @param fileName
     * @return 
     */
    @SuppressWarnings("unchecked")
    public W setDecorateFXMLStream(String fileName){
        try{
            input=new FileInputStream(fileName);
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDecorateFXMLStream(String resourceName,Class<?> cls){
        this.input=cls.getResourceAsStream(resourceName);
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDecorateFXMLStream(InputStream input){
        this.input=input;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setCloseKey(KeyCombination closeKey){
        this.closeKey=closeKey;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDragCursor(Cursor dragCursor){
        this.dragCursor=dragCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDragService(DragService dragService){
        this.dragService=dragService;
        return (W)this;
    }
    
    @Override
    public Window1 build(){
        if(null==input){
            input=WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window1.fxml");
        }
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window1.css");
        }
        Window1 window= new Window1(stage,input,stylesheet,shadowSize,resizeSize,
                defaultCursor,dragCursor,dragService,focusService,showService,closeService,closeKey);
        window.construct();
        return window;
    }
}
