
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
public static RemoteObject _index_ob_top = RemoteObject.createImmutable(0);
public static RemoteObject _index_ob_top_cach = RemoteObject.createImmutable(0);
public static RemoteObject _index_ob_olaviyat = null;
public static RemoteObject _httputils2service = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httputils2service");
public static manini.b4a.example.main _main = null;
public static manini.b4a.example.starter _starter = null;
public static manini.b4a.example.index _index = null;
  public Object[] GetGlobals() {
		return new Object[] {"api",extra._api,"HttpUtils2Service",extra.mostCurrent._httputils2service,"image_address",extra._image_address,"index",Debug.moduleToString(manini.b4a.example.index.class),"index_ob_olaviyat",extra._index_ob_olaviyat,"index_ob_top",extra._index_ob_top,"index_ob_top_cach",extra._index_ob_top_cach,"Main",Debug.moduleToString(manini.b4a.example.main.class),"Starter",Debug.moduleToString(manini.b4a.example.starter.class)};
}
}