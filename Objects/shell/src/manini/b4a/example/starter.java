
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

public class starter implements IRemote{
	public static starter mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public starter() {
		mostCurrent = this;
	}
    public RemoteObject getRemoteMe() {
        return remoteMe;    
    }
    
public boolean isSingleton() {
		return true;
	}
    static {
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("starter"), "manini.b4a.example.starter");
	}
     public static RemoteObject getObject() {
		return myClass;
	 }
	public RemoteObject _service;
    private PCBA pcBA;

	public PCBA create(Object[] args) throws ClassNotFoundException{
		processBA = (RemoteObject) args[1];
        _service = (RemoteObject) args[2];
        remoteMe = RemoteObject.declareNull("manini.b4a.example.starter");
        anywheresoftware.b4a.keywords.Common.Density = (Float)args[3];
		pcBA = new PCBA(this, starter.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _httputils2service = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httputils2service");
public static manini.b4a.example.main _main = null;
public static manini.b4a.example.extra _extra = null;
public static manini.b4a.example.index _index = null;
public static manini.b4a.example.product _product = null;
public static manini.b4a.example.property _property = null;
public static manini.b4a.example.omid _omid = null;
  public Object[] GetGlobals() {
		return new Object[] {"extra",Debug.moduleToString(manini.b4a.example.extra.class),"HttpUtils2Service",starter.mostCurrent._httputils2service,"index",Debug.moduleToString(manini.b4a.example.index.class),"Main",Debug.moduleToString(manini.b4a.example.main.class),"omid",Debug.moduleToString(manini.b4a.example.omid.class),"product",Debug.moduleToString(manini.b4a.example.product.class),"property",Debug.moduleToString(manini.b4a.example.property.class),"Service",starter.mostCurrent._service};
}
}