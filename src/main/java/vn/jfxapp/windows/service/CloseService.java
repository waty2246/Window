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

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Do job when user click on close button or shortcut to close it window.
 * Function close in window will fire WINDOW_CLOSE Event force it's window to call
 * CloseService to close window.
 * Noting that:
 *  +You can prompt user select exit message when this function call. 
 *  + must call stage.close() to explicit close window.
 *  + Some implement may be check when dragService is DragDockService and close it docking window.
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public interface CloseService {
    void run(Stage stage,Node root,DragService dragService);
}
