package manini.b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class property extends Activity implements B4AActivity{
	public static property mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "manini.b4a.example", "manini.b4a.example.property");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (property).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "manini.b4a.example", "manini.b4a.example.property");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "manini.b4a.example.property", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (property) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (property) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return property.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (property) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (property) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public static int _topset = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public manini.b4a.example.index _index = null;
public manini.b4a.example.product _product = null;
public manini.b4a.example.omid _omid = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
String _name = "";
String _text = "";
String _grouping = "";
anywheresoftware.b4a.objects.LabelWrapper _lblnodata = null;
 //BA.debugLineNum = 20;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 22;BA.debugLine="Activity.LoadLayout(\"property\")";
mostCurrent._activity.LoadLayout("property",mostCurrent.activityBA);
 //BA.debugLineNum = 23;BA.debugLine="topset = 5dip";
_topset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 28;BA.debugLine="Try";
try { //BA.debugLineNum = 29;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 30;BA.debugLine="parser.Initialize(extra.propertyjson)";
_parser.Initialize(BA.NumberToString(mostCurrent._extra._propertyjson));
 //BA.debugLineNum = 31;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 32;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group7 = _root;
final int groupLen7 = group7.getSize()
;int index7 = 0;
;
for (; index7 < groupLen7;index7++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group7.Get(index7)));
 //BA.debugLineNum = 33;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 34;BA.debugLine="Dim text As String = colroot.Get(\"text\")";
_text = BA.ObjectToString(_colroot.Get((Object)("text")));
 //BA.debugLineNum = 35;BA.debugLine="Dim grouping As String = colroot.Get(\"grouping";
_grouping = BA.ObjectToString(_colroot.Get((Object)("grouping")));
 //BA.debugLineNum = 37;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 39;BA.debugLine="lblnodata.Text =grouping";
_lblnodata.setText(BA.ObjectToCharSequence(_grouping));
 //BA.debugLineNum = 40;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 41;BA.debugLine="lblnodata.TextColor = Colors.rgb(38, 38, 38)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (38),(int) (38),(int) (38)));
 //BA.debugLineNum = 42;BA.debugLine="lblnodata.color = Colors.rgb(217, 217, 217)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (217),(int) (217),(int) (217)));
 //BA.debugLineNum = 43;BA.debugLine="lblnodata.TextSize = 11dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 44;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 45;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,5dip,topse";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),_topset,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
 //BA.debugLineNum = 47;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 49;BA.debugLine="lblnodata.Text =\"  \" & text";
_lblnodata.setText(BA.ObjectToCharSequence("  "+_text));
 //BA.debugLineNum = 50;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 51;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (115),(int) (115),(int) (115)));
 //BA.debugLineNum = 52;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)));
 //BA.debugLineNum = 53;BA.debugLine="lblnodata.TextSize = 9dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 54;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 55;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,5dip,topse";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 57;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 59;BA.debugLine="lblnodata.Text =\"  \" & name";
_lblnodata.setText(BA.ObjectToCharSequence("  "+_name));
 //BA.debugLineNum = 60;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 61;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (115),(int) (115),(int) (115)));
 //BA.debugLineNum = 62;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)));
 //BA.debugLineNum = 63;BA.debugLine="lblnodata.TextSize = 10dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 64;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 65;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,70%x,topse";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 67;BA.debugLine="topset = topset + 65dip";
_topset = (int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65)));
 //BA.debugLineNum = 69;BA.debugLine="ScrollView1.Panel.Height = topset";
mostCurrent._scrollview1.getPanel().setHeight(_topset);
 }
};
 } 
       catch (Exception e42) {
			processBA.setLastException(e42); //BA.debugLineNum = 72;BA.debugLine="createnon";
_createnon();
 };
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _createnon() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lblnodata = null;
 //BA.debugLineNum = 76;BA.debugLine="Sub createnon()";
 //BA.debugLineNum = 77;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 79;BA.debugLine="lblnodata.Text =\"هیچ مشخصه ای وجود ندارد\"";
_lblnodata.setText(BA.ObjectToCharSequence("هیچ مشخصه ای وجود ندارد"));
 //BA.debugLineNum = 80;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 81;BA.debugLine="lblnodata.TextColor = Colors.rgb(179, 179, 179)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
 //BA.debugLineNum = 82;BA.debugLine="lblnodata.TextSize = 12dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
 //BA.debugLineNum = 83;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"yek";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 84;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,0,0,100%x,50%";
mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_lblnodata.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim topset As Int";
_topset = 0;
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
