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

import com.sun.javafx.application.LauncherImpl;
import java.util.function.Consumer;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

/**
 *
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public class WindowPreloader extends Preloader{
    private static Window0 window;
    private static Runnable init,beforeLoad,beforeInit,beforeStart;
    private static Consumer<PreloaderNotification> handleApplicationNotification;
    private static boolean notLaunch=true;
    
    public static void launch(Class<? extends Application> app,String[]args){
        if(notLaunch){
            notLaunch=false;
            LauncherImpl.launchApplication(app,WindowPreloader.class,args);
        }
    }
    
    public static void setWindow(Window0 window){
        WindowPreloader.window=window;
    }
    
    public static void setInit(Runnable init){
        if(notLaunch)WindowPreloader.init=init;
    }

    public static void setBeforeApplicationLoad(Runnable beforeLoad){
        if(notLaunch)WindowPreloader.beforeLoad=beforeLoad;
    }
    
    public static void setBeforeApplicationInit(Runnable beforeInit){
        if(notLaunch)WindowPreloader.beforeInit=beforeInit;
    }
    
    public static void setBeforeApplicationStart(Runnable beforeStart){
        if(notLaunch)WindowPreloader.beforeStart=beforeStart;
    }
    
    public static void setHandleApplicationNotification(Consumer<PreloaderNotification>handleApplicationNotification){
        if(notLaunch)WindowPreloader.handleApplicationNotification=handleApplicationNotification;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        if(null==window)window=new Window0Builder().build();
        window.show();
    }
    
    @Override
    public void init() throws Exception {
       if(init!=null)init.run();
    }
    
    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if(handleApplicationNotification!=null)handleApplicationNotification.accept(info);
    }
    
     @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        // Handle state change notifications.
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:
                // Called after MyPreloader#start is called.
                if(beforeLoad!=null)beforeLoad.run();
                break;
            case BEFORE_INIT:
                // Called before MyApplication#init is called.
                if(beforeInit!=null)beforeInit.run();
                break;
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                if(beforeStart!=null)beforeStart.run();
                window.close();
                window=null;
                init=null;
                beforeInit=null;
                beforeLoad=null;
                beforeStart=null;
                handleApplicationNotification=null;
                break;
        }
    }
}
