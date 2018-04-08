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
package vn.jfxapp.windows.cursor;

import javafx.scene.Cursor;

/**
 *
 * @author waty
 */
public interface CommonCursor extends BaseCursor{
    default Cursor getSelectCursor() {
        return Cursor.HAND;
    }
    
    default Cursor getDefaultCursor() {
        return Cursor.DEFAULT;
    }
    default Cursor getDragCursor() {
        return Cursor.CLOSED_HAND;
    }
    default Cursor getWaitCursor() {
        return Cursor.WAIT;
    }
    default Cursor getTextCursor() {
        return Cursor.TEXT;
    }
}
