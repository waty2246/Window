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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vn.jfxapp.windows.cursor.BaseCursor;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public interface DragService {
    void run(Stage stage,Node titleBar,double initX,double initY,double  shadowSize,double resizeSize,MouseEvent mouse,BaseCursor cursor);
    void stop(Stage stage,Node titleBar,MouseEvent mouse);
}
