
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

public class index implements IRemote{
	public static index mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public index() {
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
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("index"), "manini.b4a.example.index");
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
		pcBA = new PCBA(this, index.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _index_scrollview = RemoteObject.declareNull("anywheresoftware.b4a.objects.ScrollViewWrapper");
public static RemoteObject _index_retrive_list = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
public static RemoteObject _index_retrive_list_img = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
public static RemoteObject _index_retrive_list_model = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
public static RemoteObject _imgdrew = null;
public static RemoteObject _product_scrollview = null;
public static RemoteObject _scrollview_lastproduct = RemoteObject.declareNull("anywheresoftware.b4a.objects.HorizontalScrollViewWrapper");
public static RemoteObject _category_hscrollview = RemoteObject.declareNull("anywheresoftware.b4a.objects.HorizontalScrollViewWrapper");
public static RemoteObject _lastproduct_imageview = null;
public static RemoteObject _headerproc = null;
public static RemoteObject _headerproctxt = null;
public static RemoteObject _procimage = null;
public static RemoteObject _property_pnl = RemoteObject.declareNull("anywheresoftware.b4a.objects.ScrollViewWrapper");
public static RemoteObject _httputils2service = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httputils2service");
public static manini.b4a.example.main _main = null;
public static manini.b4a.example.starter _starter = null;
public static manini.b4a.example.extra _extra = null;
public static manini.b4a.example.product _product = null;
public static manini.b4a.example.property _property = null;
public static manini.b4a.example.omid _omid = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",index.mostCurrent._activity,"category_hscrollview",index.mostCurrent._category_hscrollview,"extra",Debug.moduleToString(manini.b4a.example.extra.class),"headerproc",index.mostCurrent._headerproc,"headerproctxt",index.mostCurrent._headerproctxt,"HttpUtils2Service",index.mostCurrent._httputils2service,"imgdrew",index.mostCurrent._imgdrew,"index_retrive_list",index.mostCurrent._index_retrive_list,"index_retrive_list_img",index.mostCurrent._index_retrive_list_img,"index_retrive_list_model",index.mostCurrent._index_retrive_list_model,"index_ScrollView",index.mostCurrent._index_scrollview,"lastproduct_ImageView",index.mostCurrent._lastproduct_imageview,"Main",Debug.moduleToString(manini.b4a.example.main.class),"omid",Debug.moduleToString(manini.b4a.example.omid.class),"procimage",index.mostCurrent._procimage,"product",Debug.moduleToString(manini.b4a.example.product.class),"product_ScrollView",index.mostCurrent._product_scrollview,"property",Debug.moduleToString(manini.b4a.example.property.class),"property_pnl",index.mostCurrent._property_pnl,"scrollview_lastproduct",index.mostCurrent._scrollview_lastproduct,"Starter",Debug.moduleToString(manini.b4a.example.starter.class)};
}
}