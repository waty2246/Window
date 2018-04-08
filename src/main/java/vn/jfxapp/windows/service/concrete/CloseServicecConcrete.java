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
package vn.jfxapp.windows.service.concrete;

import java.util.function.Supplier;
import javafx.util.Duration;
import vn.jfxapp.windows.service.CloseService;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public interface CloseServicecConcrete extends CloseService{
    void setOnClose(Runnable r);
    
    /**
     * 
     * @param s -supplier must return true to close action run.
     */
    void setOnCloseCondition(Supplier<Boolean> s);
    void setDuration(Supplier<Duration> s);
}
