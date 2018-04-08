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
package vn.jfxapp.windows.service;

import javafx.stage.Stage;

/**
 * Do job when user drag title-bar window.
 * When window position in left top right of screen it's can be docking.
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public interface DragDockService extends DragService {
    /**
     * Must be implement this function, if not something run may be wrong.
     * @return value indicate this window has docking or not
     */
    default boolean isDock(){return false;}
    
    /**
     * When window exit it'dock state, it must release to make isDock return false.
     * Must be implement this function, if not something run may be wrong.
     */
    default void removeDock(){};
    
    /**
     * 
     * @return stage of the dock service use to display when condition dock occur.
     */
    default Stage getDockStage(){return null;}
}
