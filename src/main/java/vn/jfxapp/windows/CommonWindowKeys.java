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

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Provide Common window key-code to access window function.
 * You must provide your common key if want handles window function in your way.
 * @author waty
 */
public interface CommonWindowKeys {
    interface M{
        KeyCombination MINIMIZE_KEYCOM=new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN); 
    }
    interface C{
        KeyCombination CLOSE_KEYCOM=new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN); 
    }
    interface MA{
        KeyCombination MAXIMIZE_KEYCOM=new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN); 
    }
    interface F{
        KeyCombination FULLSCREEN_KEYCOM=new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
    }
    interface E{
        KeyCombination EXITFULLSCREEN_KEYCOM=new KeyCodeCombination(KeyCode.ESCAPE);
    }
    interface D{
        KeyCombination DOCK_LEFT_KEYCOM=new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHORTCUT_DOWN); 
        KeyCombination DOCK_RIGHT_KEYCOM=new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_DOWN); 
        KeyCombination DOCK_TOP_KEYCOM=new KeyCodeCombination(KeyCode.UP, KeyCombination.SHORTCUT_DOWN); 
    }
    
    /**
     * Close key shortcut command to close window.
     * @return 
     */
    default KeyCombination getCloseKeys(){
        return C.CLOSE_KEYCOM;
    }
    
    /**
     * Minimize key shortcut command to hide window in taskbar
     * Only affect on Window2 and above.
     * @return 
     */
    default KeyCombination getMinimizeKeys(){
        return M.MINIMIZE_KEYCOM;
    }
    
    /**
     * Maximize key shortcut to forces window in maximize mode or back to normal mode.
     * Only affect on Window3 and above.
     * @return 
     */
    default KeyCombination getMaximizeKeys(){
        return MA.MAXIMIZE_KEYCOM;
    }
    
    /**
     * Fullscreen key shortcut to forces window in fullscreen mode or back to previous mode.
     * Only affect on Window4 and above.
     * @return 
     */
    default KeyCombination getFullScreenKeys(){
        return F.FULLSCREEN_KEYCOM;
    }
    
    /**
     * Exit fullscreen key shortcut to forces window restore to previous modes.
     * Same function can do with fullscreen key shortcut.
     * Only affect on Window4 and above.
     * @return 
     */
    default KeyCombination getExitFullScreenKeys(){
        return E.EXITFULLSCREEN_KEYCOM;
    }
    
    /**
     * Dock left key shortcut forces window position in left with width=widthscreen/2
     * Only affect on Window3 and above.
     * @return 
     */
    default KeyCombination getDockLeftKeys(){
        return D.DOCK_LEFT_KEYCOM;
    }
    
    /**
     * Dock right key shortcut forces window position in right with width=widthscreen/2
     * Only affect on Window3 and above.
     * @return 
     */
    default KeyCombination getDockRightKeys(){
        return D.DOCK_RIGHT_KEYCOM;
    }
    
    /**
     * Dock top key shortcut same as maximize mode (but not maximize mode)
     * Only affect on Window3 and above.
     * @return 
     */
    default KeyCombination getDockTopKeys(){
        return D.DOCK_TOP_KEYCOM;
    }
}
