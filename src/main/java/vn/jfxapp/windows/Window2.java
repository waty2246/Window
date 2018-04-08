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
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class Window2 extends Window1{
    @FXML
    protected Button minimize;
    protected final KeyCombination minimizeKey;
    
    public Window2(){
        this(new Stage());
    }
    
    public Window2(Stage stage){
        this(stage,
                WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window2.fxml"),
                WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window2.css"),
                WindowManager.getCommonWindowAttributes().getShadowSize(),
                WindowManager.getCommonWindowAttributes().getResizeSize(),
                null,
                null,
                null,//default drag-service
                null,//default focus-service
                null,//default show-service
                null,//default close-service
                null,
                null);
    }
    
    protected Window2(Stage stage,
            InputStream input,
            String stylesheet,
            double shadowSize,
            double resizeSize,
            Cursor defaultCursor,
            Cursor dragCursor,
            DragService dragService,
            FocusService focusService,
            ShowService showService,
            CloseService closeService,
            KeyCombination closeKey,
            KeyCombination minimizeKey){
        super(stage,
                input,
                stylesheet,
                shadowSize,
                resizeSize,
                defaultCursor,
                dragCursor,
                dragService,
                focusService,
                showService,
                closeService,
                closeKey);
        if(minimizeKey!=null)this.minimizeKey=minimizeKey;
        else this.minimizeKey=WindowManager.getCommonWindowKeys().getMinimizeKeys();
    }
    
    @Override
    protected void initStyle(){
        super.initStyle();
        minimize.getStyleClass().add(WindowManager.getCommonWindowStyles().getMinimizeStyle());
        minimize.setStyle("-fx-background-color:transparent;");
    }
    
    @Override
    protected void initUI(){
        super.initUI();
    }
    
    @Override
    protected void initBaseListener(){
        super.initBaseListener();
        minimize.setOnAction(e->minimize());
    }
    
    @Override
    protected void initListener(){
        super.initListener();
        scene.getAccelerators().put(minimizeKey,()-> stage.setIconified(!stage.isIconified()));
    }

    public void minimize(){
        minimize(!stage.isIconified());
    }
    
    public void minimize(boolean value){
        stage.setIconified(value);
    }
}
