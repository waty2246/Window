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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import vn.jfxapp.windows.cursor.CommonCursor;
import vn.jfxapp.windows.service.CloseService;
import vn.jfxapp.windows.service.DragService;
import vn.jfxapp.windows.service.FocusService;
import vn.jfxapp.windows.service.FullscreenService;
import vn.jfxapp.windows.service.ResizeService;
import vn.jfxapp.windows.service.ShowService;
/**
 * Bill Pugh instance implementation
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class WindowManager {
    ///prevent re-initialize static method call
    private static boolean isInitialized;
    
    private WindowManager(){
        isInitialized=true;
        if(null==commonStyles){
            commonWindowStyles=new CommonWindowStyles(){};
        }else{
            commonWindowStyles=commonStyles;
            commonStyles=null;
        }
        
        if(null==commonKeys){
            commonWindowKeys=new CommonWindowKeys(){};
        }else{
            commonWindowKeys=commonKeys;
            commonKeys=null;
        }
        
        if(null==commonStrings){
            commonWindowStrings=new CommonWindowStrings(){};
        }else{
            commonWindowStrings=commonStrings;
            commonStrings=null;
        }
        
        if(null==commonValues){
            commonWindowAttributes=new CommonWindowValues(){};
        }else{
            commonWindowAttributes=commonValues;
            commonValues=null;
        }
        
        if(null==commonCursors){
            commonWindowCursors=new CommonCursor(){};
        }else{
            commonWindowCursors=commonCursors;
            commonCursors=null;
        }
    }
    
    private static CommonWindowStyles commonStyles;
    private static CommonWindowKeys commonKeys;
    private static CommonWindowStrings commonStrings;
    private static CommonWindowValues commonValues;
    private static CommonCursor commonCursors;
    
    private static class WMIW{
        private static final WindowManager INSTANCE;
        ///will be removed in java 9. 
        static {
            INSTANCE=new WindowManager();
            try{
                ///try to reflect field name BEHAVIOR.May be fail on other java version.
                Constructor<?> constructor =Tooltip.class.getDeclaredClasses()[0].getDeclaredConstructor(Duration.class,Duration.class,Duration.class,boolean.class);
                constructor.setAccessible(true);
                Object behavior = constructor.newInstance(new Duration(INSTANCE.getCommonAttributes().getDelayTime()),
                                                        new Duration(INSTANCE.getCommonAttributes().getVisibleTime()),
                                                        new Duration(INSTANCE.getCommonAttributes().getEndTime()),false);
                Field behaviorField = Tooltip.class.getDeclaredField("BEHAVIOR");
                behaviorField.setAccessible(true);
                behaviorField.set(null, behavior);
            }catch(IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException e){
                ///just warning this exception.
                final Logger LOGGER=Logger.getLogger(WMIW.class.getName());
                LOGGER.log(Level.WARNING, "Reflect Tooltip.BEHAVIOR fail");
            }
        }
    }
    
    static class WMLW{
        static final Logger LOGGER=Logger.getLogger(WindowManager.class.getPackage().getName());
    } 
    
    /**
     * Css style for control window
     * @param commonStyles 
     */
    public static void initCommonWindowStyles(CommonWindowStyles commonStyles){
        if(!isInitialized){
            WindowManager.commonStyles=commonStyles;
        }else{
            WMLW.LOGGER.log(Level.WARNING,"Try to init common styles after window has used");
        }
    }
    
    /**
     * Include shortcut to interact with window
     * @param commonKeys 
     */
    public static void initCommonWindowKeys(CommonWindowKeys commonKeys){
        if(!isInitialized){
            WindowManager.commonKeys=commonKeys;
        }else{
            WMLW.LOGGER.log(Level.WARNING,"Try to init common keys after window has used");
        }
    }
    
    /**
     * Init annotation text for control and menu
     * @param commonResources 
     */
    public static void initCommonWindowResources(CommonWindowStrings commonResources){
        if(!isInitialized){
            WindowManager.commonStrings=commonResources;
        }else{
            WMLW.LOGGER.log(Level.WARNING,"Try to init common resources after window has used");
        }
    }
    
    /**
     * Init window values include shadow,resize size, time,...
     * @param commonValues 
     */
    public static void initCommonWindowValues(CommonWindowValues commonValues){
        if(!isInitialized){
            WindowManager.commonValues=commonValues;
        }else{
            WMLW.LOGGER.log(Level.WARNING,"Try to init common attributes after window has used");
        }
    }
    
    public static void initCommonWindowCursors(CommonCursor commonCursors){
        if(!isInitialized){
            WindowManager.commonCursors=commonCursors;
        }else{
            WMLW.LOGGER.log(Level.WARNING,"Try to init common cursors after window has used");
        }
    }
    
    public static CommonWindowStyles getCommonWindowStyles(){
        return WMIW.INSTANCE.getCommonStyles();
    }
    
    public static CommonWindowKeys getCommonWindowKeys(){
        return WMIW.INSTANCE.getCommonKeys();
    }
    
    public static CommonWindowStrings getCommonWindowResources(){
        return WMIW.INSTANCE.getCommonResources();
    }
    
    public static CommonWindowValues getCommonWindowAttributes(){
        return WMIW.INSTANCE.getCommonAttributes();
    }
    
    public static CommonCursor getCommonWindowCursor(){
        return WMIW.INSTANCE.getCommonCursor();
    }
    
    public static Object getServiceOf(Window0 window,Class<?> service){
        if(service.isAssignableFrom(ShowService.class)){
            return window.showService;
        }
        if(service.isAssignableFrom(FocusService.class)){
            return window.focusService;
        }
        if(service.isAssignableFrom(DragService.class)){
            if(window instanceof Window1)
                return ((Window1)window).dragService;
            return null;
        }
        if(service.isAssignableFrom(FullscreenService.class)){
            if(window instanceof Window4)
                return ((Window4)window).fullscreenService;
            return null;
        }
        if(service.isAssignableFrom(CloseService.class)){
            return window.closeService;
        }
        if(service.isAssignableFrom(ResizeService.class)){
            if(window instanceof Window3)
                return ((Window3)window).resizeService;
            return null;
        }
        return null;
    }
    
    /**
     * Window component styles
     */
    
    private final CommonWindowStyles commonWindowStyles;
    private final CommonWindowValues commonWindowAttributes;
    private final CommonWindowStrings commonWindowStrings;
    private final CommonWindowKeys commonWindowKeys;
    private final CommonCursor commonWindowCursors;

    private CommonWindowStyles getCommonStyles(){
        return commonWindowStyles;
    }
    
    private CommonWindowKeys getCommonKeys(){
        return commonWindowKeys;
    }
    
    private CommonWindowStrings getCommonResources(){
        return commonWindowStrings;
    }
    
    private CommonWindowValues getCommonAttributes(){
        return commonWindowAttributes;
    }
    
    private CommonCursor getCommonCursor(){
        return commonWindowCursors;
    }
}
