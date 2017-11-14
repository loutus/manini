
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

public class property implements IRemote{
	public static property mostCurrent;
	public static RemoteObject processBA;
    public static boolean processGlobalsRun;
    public static RemoteObject myClass;
    public static RemoteObject remoteMe;
	public property() {
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
        anywheresoftware.b4a.pc.RapidSub.moduleToObject.put(new B4XClass("property"), "manini.b4a.example.property");
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
		pcBA = new PCBA(this, property.class);
        main_subs_0.initializeProcessGlobals();
		return pcBA;
	}
public static RemoteObject __c = RemoteObject.declareNull("anywheresoftware.b4a.keywords.Common");
public static RemoteObject _scrollview1 = RemoteObject.declareNull("anywheresoftware.b4a.objects.ScrollViewWrapper");
public static RemoteObject _topset = RemoteObject.createImmutable(0);
public static RemoteObject _httputils2service = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httputils2service");
public static manini.b4a.example.main _main = null;
public static manini.b4a.example.starter _starter = null;
public static manini.b4a.example.extra _extra = null;
public static manini.b4a.example.index _index = null;
public static manini.b4a.example.product _product = null;
public static manini.b4a.example.omid _omid = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",property.mostCurrent._activity,"extra",Debug.moduleToString(manini.b4a.example.extra.class),"HttpUtils2Service",property.mostCurrent._httputils2service,"index",Debug.moduleToString(manini.b4a.example.index.class),"Main",Debug.moduleToString(manini.b4a.example.main.class),"omid",Debug.moduleToString(manini.b4a.example.omid.class),"product",Debug.moduleToString(manini.b4a.example.product.class),"ScrollView1",property.mostCurrent._scrollview1,"Starter",Debug.moduleToString(manini.b4a.example.starter.class),"topset",property._topset};
}
}