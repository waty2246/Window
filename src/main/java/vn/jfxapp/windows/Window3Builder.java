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

import vn.jfxapp.windows.service.DragDockService;
import vn.jfxapp.windows.service.DragService;
import vn.jfxapp.windows.service.ResizeService;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 * @param <W>
 */
public class Window3Builder<W extends Window3Builder<W>> extends Window2Builder<W> {
    protected Cursor selectCursor;
    protected Cursor waitCursor;
    protected Cursor nResizeCursor;
    protected Cursor wResizeCursor;
    protected Cursor sResizeCursor;
    protected Cursor eResizeCursor;
    protected Cursor nwResizeCursor;
    protected Cursor neResizeCursor;
    protected Cursor swResizeCursor;
    protected Cursor seResizeCursor;
    protected Cursor textCursor;
    protected ResizeService resizeService;
    protected KeyCombination maximizeKey;
    protected String baseTextName;
    protected ClassLoader bundleClassLoader;
    protected Background dockBackground;
    
    /**
     * Change background dock window display when window meet condition docking.
     * @param background
     * @return 
     */
    @SuppressWarnings("unchecked")
    public W setDockBackground(Background background){
        dockBackground=background;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setLanguageResource(String baseName,String systemPath){
        this.baseTextName=baseName;
        try{
            bundleClassLoader=new URLClassLoader(new URL[]{(new File(systemPath)).toURI().toURL()}); 
        }catch(MalformedURLException e){
            this.baseTextName=null;
            WindowManager.WMLW.LOGGER.log(Level.WARNING, "Language resource file {0} not found in system path:{1}", new Object[]{baseName, systemPath});
        }
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setLanguageResource(String baseName,Class<?> classPath){
        this.baseTextName=baseName;
        bundleClassLoader=classPath.getClassLoader();
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setLanguageResource(String baseName,ClassLoader classLoader){
        this.baseTextName=baseName;
        bundleClassLoader=classLoader;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setMaximizeKey(KeyCombination maximizeKey){
        this.maximizeKey=maximizeKey;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setResizeService(ResizeService resizeService){
        this.resizeService=resizeService;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setTextCursor(Cursor textCursor){
        this.textCursor=textCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setSEResizeCursor(Cursor seResizeCursor){
        this.seResizeCursor=seResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setSWResizeCursor(Cursor swResizeCursor){
        this.swResizeCursor=swResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setNEResizeCursor(Cursor neResizeCursor){
        this.neResizeCursor=neResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setNWResizeCursor(Cursor nwResizeCursor){
        this.nwResizeCursor=nwResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setEResizeCursor(Cursor eResizeCursor){
        this.eResizeCursor=eResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setSResizeCursor(Cursor sResizeCursor){
        this.sResizeCursor=sResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setWResizeCursor(Cursor wResizeCursor){
        this.wResizeCursor=wResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setNResizeCursor(Cursor nResizeCursor){
        this.nResizeCursor=nResizeCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setWaitCursor(Cursor waitCursor){
        this.waitCursor=waitCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setSelectCursor(Cursor selectCursor){
        this.selectCursor=selectCursor;
        return (W)this;
    }

    @SuppressWarnings("unchecked")
    @Override 
    public W setDragService(DragService dragService){
        if(dragService!=null){
            if(dragService instanceof DragDockService){
                this.dragService=dragService;
            }
        }
        return (W)this;
    }
    
    @Override
    public Window3 build(){
        
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
            input=WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window3.fxml");
        }
        
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window3.css");
        }
        
        Window3 window= new Window3(
                stage,
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
                closeKey,
                minimizeKey,
                maximizeKey,
                baseTextName,
                bundleClassLoader);
        
        ///immediately construct after create.
        window.construct();
        return window;
    }
}
