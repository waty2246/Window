package vn.jfxapp.windows;

import vn.jfxapp.windows.service.CloseService;
import vn.jfxapp.windows.service.FocusService;
import vn.jfxapp.windows.service.ShowService;
import javafx.scene.Cursor;
import javafx.stage.Stage;
/**
 * Provide an efficient way to creates window0
 * @author waty
 * @param <W>
 */
public class Window0Builder<W extends Window0Builder<W>> {
    protected Stage stage;
    protected String stylesheet;
    protected double shadowSize=WindowManager.getCommonWindowAttributes().getShadowSize();
    protected double resizeSize=WindowManager.getCommonWindowAttributes().getResizeSize();
    protected Cursor defaultCursor;
    protected FocusService focusService;
    protected ShowService showService;
    protected CloseService closeService;
    
    @SuppressWarnings("unchecked")
    public W setStage(Stage stage){
        this.stage=stage;
        return (W)this; 
    }
    
    @SuppressWarnings("unchecked")
    public W setStylesheet(String stylesheet){
        this.stylesheet=stylesheet;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setShadowSize(double shadowSize){
        if(shadowSize>0.0)this.shadowSize=shadowSize;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setResizeSize(double resizeSize){
        if(resizeSize>0.0)this.resizeSize=resizeSize;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setDefaultCursor(Cursor defaultCursor){
        this.defaultCursor=defaultCursor;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setFocusService(FocusService focusService){
        this.focusService=focusService;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setShowService(ShowService showService){
        this.showService=showService;
        return (W)this;
    }
    
    @SuppressWarnings("unchecked")
    public W setCloseService(CloseService closeService){
        this.closeService=closeService;
        return (W)this;
    }
    
    public Window0 build(){
        if(null==stylesheet){
            stylesheet=WindowManager.getCommonWindowStyles().getResolveStylePath().getCssPath("window0.css");
        }
        Window0 window= new Window0(stage,null,stylesheet,shadowSize,resizeSize,
                defaultCursor,focusService,showService,closeService);
        window.construct();
        return window;
    }
}
