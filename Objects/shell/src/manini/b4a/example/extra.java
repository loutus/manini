
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

public class extra implements IRemote{
	public static extra mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public extra() {
		mostCurrent = this;
	}
    public RemoteObject getRemoteMe() {
        return remoteMe;    
    }
    
public boolean isSingleton() {
		return true;
	}
     private static PCBA pcBA = new PCBA(null, extra.class);
    static {
		mostCurrent = new extra();
        remoteMe = RemoteObject.declareNull("manini.b4a.example.extra");
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("extra"), "manini.b4a.example.extra");
        RDebug.INSTANCE.eventTargets.put(new DeviceClass("manini.b4a.example.extra"), new java.lang.ref.WeakReference<PCBA> (pcBA));
	}
   
	public static RemoteObject runMethod(boolean notUsed, String method, Object... args) throws Exception{
		return (RemoteObject) pcBA.raiseEvent(method.substring(1), args);
	}
    public static void runVoidMethod(String method, Object... args) throws Exception{
		runMethod(false, method, args);
	}
	public PCBA create(Object[] args) throws ClassNotFoundException{
        throw new RuntimeException("CREATE is not supported.");
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _api = RemoteObject.createImmutable("");
public static RemoteObject _image_address = RemoteObject.createImmutable("");
public static RemoteObject _share_link = RemoteObject.createImmutable("");
public static RemoteObject _index_ob_top = RemoteObject.createImmutable(0);
public static RemoteObject _index_ob_top_cach = RemoteObject.createImmutable(0);
public static RemoteObject _index_ob_olaviyat = null;
public static RemoteObject _index_ob_olaviyat_load = RemoteObject.createImmutable(0);
public static RemoteObject _product_id_toshow = RemoteObject.createImmutable(0);
public static RemoteObject _product_title = RemoteObject.createImmutable("");
public static RemoteObject _product_title_top = RemoteObject.createImmutable(0);
public static RemoteObject _product_title_flag = RemoteObject.createImmutable(false);
public static RemoteObject _propertyjson = RemoteObject.createImmutable(0);
public static RemoteObject _procimg_flag = RemoteObject.createImmutable(0);
public static RemoteObject _procimg_count = null;
public static RemoteObject _flag_procpnl = RemoteObject.createImmutable(0);
public static RemoteObject _httputils2service = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httputils2service");
public static manini.b4a.example.main _main = null;
public static manini.b4a.example.starter _starter = null;
public static manini.b4a.example.index _index = null;
public static manini.b4a.example.product _product = null;
public static manini.b4a.example.property _property = null;
public static manini.b4a.example.omid _omid = null;
  public Object[] GetGlobals() {
		return new Object[] {"api",extra._api,"flag_procpnl",extra._flag_procpnl,"HttpUtils2Service",extra.mostCurrent._httputils2service,"image_address",extra._image_address,"index",Debug.moduleToString(manini.b4a.example.index.class),"index_ob_olaviyat",extra._index_ob_olaviyat,"index_ob_olaviyat_load",extra._index_ob_olaviyat_load,"index_ob_top",extra._index_ob_top,"index_ob_top_cach",extra._index_ob_top_cach,"Main",Debug.moduleToString(manini.b4a.example.main.class),"omid",Debug.moduleToString(manini.b4a.example.omid.class),"procimg_count",extra._procimg_count,"procimg_flag",extra._procimg_flag,"product",Debug.moduleToString(manini.b4a.example.product.class),"product_id_toshow",extra._product_id_toshow,"product_title",extra._product_title,"product_title_flag",extra._product_title_flag,"product_title_top",extra._product_title_top,"property",Debug.moduleToString(manini.b4a.example.property.class),"propertyjson",extra._propertyjson,"share_link",extra._share_link,"Starter",Debug.moduleToString(manini.b4a.example.starter.class)};
}
}