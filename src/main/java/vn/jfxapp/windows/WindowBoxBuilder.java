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

/**
 *
 * @author waty
 * @param <W>
 */
public class WindowBoxBuilder<W extends WindowBoxBuilder<W>> extends Window1Builder<W>{
    @Override
    public WindowBox build(){
        if(null==input){
            input=WindowManager.getCommonWindowStyles().getResolveStylePath().getFXMLStream("window1.fxml");
        }
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("windowbox.css");
        }
        WindowBox window= new WindowBox(stage,input,stylesheet,shadowSize,resizeSize,
                defaultCursor,dragCursor,dragService,focusService,showService,closeService,closeKey);
        window.construct();
        return window;
    }
}
