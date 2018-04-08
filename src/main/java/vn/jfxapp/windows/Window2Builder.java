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

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 * @param <W>
 */
public class Window2Builder<W extends Window2Builder<W>> extends Window1Builder<W> {
    protected KeyCombination minimizeKey;
    
    @SuppressWarnings("unchecked")
    public W setMinimizeKey(KeyCombination minimizeKey){
        this.minimizeKey=minimizeKey;
        return (W)this;
    }
    
    @Override
    public Window2 build(){
        if(null==input){
            input=WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window2.fxml");
        }
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window2.css");
        }
        Window2 window= new Window2(stage,input,stylesheet,shadowSize,resizeSize,
                defaultCursor,dragCursor,dragService,focusService,showService,closeService,closeKey,minimizeKey);
        window.construct();
        return window;
    }
}
