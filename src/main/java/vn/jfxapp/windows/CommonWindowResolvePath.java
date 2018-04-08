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

import java.io.InputStream;
import java.net.URL;

/**
 * Using to resolve common FXML,CSS,other resource(image,file...) path.
 * @author VO ANH TUAN (tuana1648@gmail.com)
 */
public interface CommonWindowResolvePath {
    /**
     * Used to resolve FXML path decorates Window class
     * @param fxmlFile
     * @return 
     */
    default InputStream getFXMLStream(String fxmlFile){
        return WindowManager.class.getResourceAsStream(fxmlFile);
    }
    
    /**
     * Used to resolve css path reference in Window class
     * @param cssFile
     * @return 
     */
    default String getCssPath(String cssFile){
        return WindowManager.class.getResource(cssFile).toExternalForm();
    }
    
    /**
     * Used to resolve other resouce reference in FXML file.
     * @return 
     */
    default URL getLocation(){
        return WindowManager.class.getResource("");
    }
}
