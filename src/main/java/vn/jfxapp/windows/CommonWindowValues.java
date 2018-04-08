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
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public interface CommonWindowValues {
    default double getShadowSize(){
        return 12.0;
    }
    default double getResizeSize(){
        return 8.0;
    }
    default double getDelayTime(){
        return 400.0;
    }
    default double getVisibleTime(){
        return 8000.0;
    }
    default double getEndTime(){
        return 400.0;
    }
}
