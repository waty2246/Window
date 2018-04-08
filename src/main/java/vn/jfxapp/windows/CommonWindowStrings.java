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
public interface CommonWindowStrings {
    interface D{
        String[] dockString={"dock-left","dock-right","dock-top"};
    }
    default String getDefaultLang(){
        return "vi";
    }
    default String getCloseString(){
        return "close";
    }
    default String getMinimizeString(){
        return "minimize";
    }
    default String getMaximizeString(){
        return "maximize";
    }
    default String getRestoreString(){
        return "restore";
    }
    default String getFullScreenString(){
        return "fullscreen";
    }
    default String getUnfullScreenString(){
        return "unfullscreen";
    }
    default String[] getDockString(){
        return D.dockString;
    }
    default ClassLoader getResourceLoader(){
        return WindowManager.class.getClassLoader();   
    }
    default String getResourceName(){
        return "vn/jfxapp/windows/annotation";
    }
}
