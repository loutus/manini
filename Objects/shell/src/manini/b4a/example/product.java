
package manini.b4a.example;

import java.io.IOException;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.PCBA;
import anywheresoftware.b4a.pc.RDebug;
import anywheresoftware.b4a.pc.RemoteObject;
import anywheresoftware.b4a.pc.RDebug.IRemote;
import anywheresoftware.b4a.pc.Debug;
import anywheresoftware.b4a.pc.B4XTypes.B4XClass;
import anywheresoftware.b4a.pc.B4XTypes.DeviceClass;

public class product implements IRemote{
	public static product mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public product() {
		mostCurrent = this;
	}
    public RemoteObject getRemoteMe() {
        return remoteMe;    
    }
    
	public static void main (String[] args) throws Exception {
		new RDebug(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
		RDebug.INSTANCE.waitForTask();

	}
    static {
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("product"), "manini.b4a.example.product");
	}

public boolean isSingleton() {
		return true;
	}
     public static RemoteObject getObject() {
		return myClass;
	 }

	public RemoteObject activityBA;
	public RemoteObject _activity;
    private PCBA pcBA;

	public PCBA create(Object[] args) throws ClassNotFoundException{
		processBA = (RemoteObject) args[1];
		activityBA = (RemoteObject) args[2];
		_activity = (RemoteObject) args[3];
        anywheresoftware.b4a.keywords.Common.Density = (Float)args[4];
        remoteMe = (RemoteObject) args[5];
		pcBA = new PCBA(this, product.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _product_scrollview = RemoteObject.declareNull("anywheresoftware.b4a.objects.ScrollViewWrapper");
public static RemoteObject _product_header = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
public static RemoteObject _product_imag1 = RemoteObject.declareNull("anywheresoftware.b4a.objects.ImageViewWrapper");
public static RemoteObject _product_img2 = RemoteObject.declareNull("anywheresoftware.b4a.objects.ImageViewWrapper");
public static RemoteObject _scrollview_lastproduct = RemoteObject.declareNull("anywheresoftware.b4a.objects.HorizontalScrollViewWrapper");
public static RemoteObject _category_hscrollview = RemoteObject.declareNull("anywheresoftware.b4a.objects.HorizontalScrollViewWrapper");
public static RemoteObject _imageview = null;
public static RemoteObject _header_title = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _lbl_title = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _lbl_titlen = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _tempint = RemoteObject.createImmutable(0);
public static RemoteObject _moretext = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _rtljustify1 = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _moretext_data = RemoteObject.createImmutable("");
public static RemoteObject _pnl4_moshakhasat = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
public static RemoteObject _pnl4_line = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
public static RemoteObject _total = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
public static RemoteObject _httputils2service = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httputils2service");
public static manini.b4a.example.main _main = null;
public static manini.b4a.example.starter _starter = null;
public static manini.b4a.example.extra _extra = null;
public static manini.b4a.example.index _index = null;
public static manini.b4a.example.property _property = null;
public static manini.b4a.example.omid _omid = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",product.mostCurrent._activity,"category_hscrollview",product.mostCurrent._category_hscrollview,"extra",Debug.moduleToString(manini.b4a.example.extra.class),"header_title",product.mostCurrent._header_title,"HttpUtils2Service",product.mostCurrent._httputils2service,"ImageView",product.mostCurrent._imageview,"index",Debug.moduleToString(manini.b4a.example.index.class),"lbl_title",product.mostCurrent._lbl_title,"lbl_titlen",product.mostCurrent._lbl_titlen,"Main",Debug.moduleToString(manini.b4a.example.main.class),"moretext",product.mostCurrent._moretext,"moretext_data",product.mostCurrent._moretext_data,"omid",Debug.moduleToString(manini.b4a.example.omid.class),"pnl4_line",product.mostCurrent._pnl4_line,"pnl4_moshakhasat",product.mostCurrent._pnl4_moshakhasat,"product_header",product.mostCurrent._product_header,"product_imag1",product.mostCurrent._product_imag1,"product_img2",product.mostCurrent._product_img2,"product_ScrollView",product.mostCurrent._product_scrollview,"property",Debug.moduleToString(manini.b4a.example.property.class),"RTLJustify1",product.mostCurrent._rtljustify1,"scrollview_lastproduct",product.mostCurrent._scrollview_lastproduct,"Starter",Debug.moduleToString(manini.b4a.example.starter.class),"tempint",product._tempint,"total",product.mostCurrent._total};
}
}