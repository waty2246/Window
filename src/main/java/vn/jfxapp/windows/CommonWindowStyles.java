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
 */
public interface CommonWindowStyles {
    interface W{
        CommonWindowResolvePath RESOLVE_STYLE_PATH=new CommonWindowResolvePath(){};
    }
    interface D{
        String[] dock={
            "dock-left",
            "dock-right",
            "dock-top"
        };
        String[] dockSelected={
            "dock-left-selected",
            "dock-right-selected",
            "dock-top-selected"
        };
    }
    default CommonWindowResolvePath getResolveStylePath(){
        return W.RESOLVE_STYLE_PATH;
    }
    default String getCloseStyle(){
        return "close";
    }
    default String getMinimizeStyle(){
        return "minimize";
    }
    default String getMaximizeStyle(){
        return "maximize";
    }
    default String getRestoreStyle(){
        return "restore";
    }
    default String getFullScreenStyle(){
        return "fullscreen";
    }
    default String getUnfullScreenStyle(){
        return "unfullscreen";
    }
    default String getShadowStyle(){
        return "shadow";
    }
    default String getBackgroundStyle(){
        return "background";
    }
    default String getTitleStyle(){
        return "title";
    }
    default String getTitleBarStyle(){
        return "title-bar";
    }
    default String getIconStyle(){
        return "icon";
    }
    default String getDragStyle(){
        return "drag";
    }
    default String getTextStyle(){
        return "text";
    }
    default String getExtendStyle(){
        return "extend";
    }
    ///size() must be >3 to avoid exception ^_^
    default String getCommonDockStyle(){
        return "dock";
    }
    default String[] getDockStyle(){
        return D.dock;
    }
    default String[] getDockSelectedStyle(){
        return D.dockSelected;
    }
}
