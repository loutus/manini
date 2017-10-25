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

public class index extends Activity implements B4AActivity{
	public static index mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "manini.b4a.example", "manini.b4a.example.index");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (index).");
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
		activityBA = new BA(this, layout, processBA, "manini.b4a.example", "manini.b4a.example.index");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "manini.b4a.example.index", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (index) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (index) Resume **");
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
		return index.class;
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
        BA.LogInfo("** Activity (index) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (index) Resume **");
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
public anywheresoftware.b4a.objects.ScrollViewWrapper _index_scrollview = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 14;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 16;BA.debugLine="Activity.LoadLayout(\"index\")";
mostCurrent._activity.LoadLayout("index",mostCurrent.activityBA);
 //BA.debugLineNum = 19;BA.debugLine="index_draw(\"small\",2)";
_index_draw("small",BA.NumberToString(2));
 //BA.debugLineNum = 20;BA.debugLine="index_draw(\"small\",3)";
_index_draw("small",BA.NumberToString(3));
 //BA.debugLineNum = 21;BA.debugLine="index_draw(\"small\",4)";
_index_draw("small",BA.NumberToString(4));
 //BA.debugLineNum = 22;BA.debugLine="index_draw(\"medium\",5)";
_index_draw("medium",BA.NumberToString(5));
 //BA.debugLineNum = 23;BA.debugLine="index_draw(\"small\",6)";
_index_draw("small",BA.NumberToString(6));
 //BA.debugLineNum = 24;BA.debugLine="index_draw(\"small\",7)";
_index_draw("small",BA.NumberToString(7));
 //BA.debugLineNum = 25;BA.debugLine="index_draw(\"small\",8)";
_index_draw("small",BA.NumberToString(8));
 //BA.debugLineNum = 26;BA.debugLine="index_draw(\"medium\",9)";
_index_draw("medium",BA.NumberToString(9));
 //BA.debugLineNum = 27;BA.debugLine="index_draw(\"small\",10)";
_index_draw("small",BA.NumberToString(10));
 //BA.debugLineNum = 28;BA.debugLine="index_draw(\"larg\",1)";
_index_draw("larg",BA.NumberToString(1));
 //BA.debugLineNum = 29;BA.debugLine="index_draw(\"small\",2)";
_index_draw("small",BA.NumberToString(2));
 //BA.debugLineNum = 30;BA.debugLine="index_draw(\"small\",3)";
_index_draw("small",BA.NumberToString(3));
 //BA.debugLineNum = 31;BA.debugLine="index_draw(\"small\",4)";
_index_draw("small",BA.NumberToString(4));
 //BA.debugLineNum = 32;BA.debugLine="index_draw(\"medium\",5)";
_index_draw("medium",BA.NumberToString(5));
 //BA.debugLineNum = 33;BA.debugLine="index_draw(\"small\",6)";
_index_draw("small",BA.NumberToString(6));
 //BA.debugLineNum = 34;BA.debugLine="index_draw(\"small\",7)";
_index_draw("small",BA.NumberToString(7));
 //BA.debugLineNum = 35;BA.debugLine="index_draw(\"small\",8)";
_index_draw("small",BA.NumberToString(8));
 //BA.debugLineNum = 36;BA.debugLine="index_draw(\"medium\",9)";
_index_draw("medium",BA.NumberToString(9));
 //BA.debugLineNum = 37;BA.debugLine="index_draw(\"small\",10)";
_index_draw("small",BA.NumberToString(10));
 //BA.debugLineNum = 38;BA.debugLine="index_draw(\"larg\",1)";
_index_draw("larg",BA.NumberToString(1));
 //BA.debugLineNum = 39;BA.debugLine="index_draw(\"small\",2)";
_index_draw("small",BA.NumberToString(2));
 //BA.debugLineNum = 40;BA.debugLine="index_draw(\"small\",3)";
_index_draw("small",BA.NumberToString(3));
 //BA.debugLineNum = 41;BA.debugLine="index_draw(\"small\",4)";
_index_draw("small",BA.NumberToString(4));
 //BA.debugLineNum = 42;BA.debugLine="index_draw(\"medium\",5)";
_index_draw("medium",BA.NumberToString(5));
 //BA.debugLineNum = 43;BA.debugLine="index_draw(\"small\",6)";
_index_draw("small",BA.NumberToString(6));
 //BA.debugLineNum = 44;BA.debugLine="index_draw(\"small\",7)";
_index_draw("small",BA.NumberToString(7));
 //BA.debugLineNum = 45;BA.debugLine="index_draw(\"small\",8)";
_index_draw("small",BA.NumberToString(8));
 //BA.debugLineNum = 46;BA.debugLine="index_draw(\"medium\",9)";
_index_draw("medium",BA.NumberToString(9));
 //BA.debugLineNum = 47;BA.debugLine="index_draw(\"small\",10)";
_index_draw("small",BA.NumberToString(10));
 //BA.debugLineNum = 48;BA.debugLine="index_draw(\"larg\",1)";
_index_draw("larg",BA.NumberToString(1));
 //BA.debugLineNum = 49;BA.debugLine="index_draw(\"small\",2)";
_index_draw("small",BA.NumberToString(2));
 //BA.debugLineNum = 50;BA.debugLine="index_draw(\"small\",3)";
_index_draw("small",BA.NumberToString(3));
 //BA.debugLineNum = 51;BA.debugLine="index_draw(\"small\",4)";
_index_draw("small",BA.NumberToString(4));
 //BA.debugLineNum = 52;BA.debugLine="index_draw(\"medium\",5)";
_index_draw("medium",BA.NumberToString(5));
 //BA.debugLineNum = 53;BA.debugLine="index_draw(\"small\",6)";
_index_draw("small",BA.NumberToString(6));
 //BA.debugLineNum = 54;BA.debugLine="index_draw(\"small\",7)";
_index_draw("small",BA.NumberToString(7));
 //BA.debugLineNum = 55;BA.debugLine="index_draw(\"small\",8)";
_index_draw("small",BA.NumberToString(8));
 //BA.debugLineNum = 56;BA.debugLine="index_draw(\"medium\",9)";
_index_draw("medium",BA.NumberToString(9));
 //BA.debugLineNum = 57;BA.debugLine="index_draw(\"small\",10)";
_index_draw("small",BA.NumberToString(10));
 //BA.debugLineNum = 58;BA.debugLine="extra.load_index";
mostCurrent._extra._load_index(mostCurrent.activityBA);
 //BA.debugLineNum = 59;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 60;BA.debugLine="r.Target = index_ScrollView";
_r.Target = (Object)(mostCurrent._index_scrollview.getObject());
 //BA.debugLineNum = 61;BA.debugLine="r.RunMethod2(\"setVerticalScrollBarEnabled\", False";
_r.RunMethod2("setVerticalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 62;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang.i";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private index_ScrollView As ScrollView";
mostCurrent._index_scrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _index_draw(String _size,String _flag) throws Exception{
int _space = 0;
int _padding_space = 0;
int _left_draw = 0;
int _width_draw = 0;
int _shadow_space = 0;
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 80;BA.debugLine="Sub index_draw(size As String,flag)";
 //BA.debugLineNum = 81;BA.debugLine="Dim space As Int = 2dip";
_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 82;BA.debugLine="Dim padding_space As Int = 2dip";
_padding_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 83;BA.debugLine="If size=\"larg\" Then";
if ((_size).equals("larg")) { 
 //BA.debugLineNum = 84;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 85;BA.debugLine="Dim width_draw As Int = 100%x - left_draw";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_left_draw);
 //BA.debugLineNum = 86;BA.debugLine="Dim shadow_space As Int = 5dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 87;BA.debugLine="extra.index_ob_olaviyat(flag) = 1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 88;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 };
 //BA.debugLineNum = 90;BA.debugLine="If size=\"medium\" Then";
if ((_size).equals("medium")) { 
 //BA.debugLineNum = 91;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (111),(int) (11),(int) (1))) {
case 0: {
 //BA.debugLineNum = 93;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 94;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 95;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 96;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 break; }
case 1: {
 //BA.debugLineNum = 98;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 99;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 100;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 101;BA.debugLine="extra.index_ob_olaviyat(flag)=222";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (222);
 //BA.debugLineNum = 102;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 break; }
case 2: {
 //BA.debugLineNum = 104;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 105;BA.debugLine="Dim width_draw As Int = 66%x   + padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 106;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 107;BA.debugLine="extra.index_ob_olaviyat(flag)=22";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (22);
 //BA.debugLineNum = 108;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 break; }
}
;
 };
 //BA.debugLineNum = 111;BA.debugLine="If size=\"small\" Then";
if ((_size).equals("small")) { 
 //BA.debugLineNum = 112;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (222),(int) (221),(int) (22),(int) (111),(int) (11),(int) (1))) {
case 0: {
 //BA.debugLineNum = 114;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 115;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 116;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 117;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
 //BA.debugLineNum = 118;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 119;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 break; }
case 1: {
 //BA.debugLineNum = 121;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 122;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 123;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 124;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
 //BA.debugLineNum = 125;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 126;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 break; }
case 2: {
 //BA.debugLineNum = 128;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 129;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 130;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 131;BA.debugLine="extra.index_ob_olaviyat(flag)=221";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (221);
 //BA.debugLineNum = 132;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 break; }
case 3: {
 //BA.debugLineNum = 134;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 135;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 136;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 137;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 138;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 break; }
case 4: {
 //BA.debugLineNum = 140;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 141;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 142;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 143;BA.debugLine="extra.index_ob_olaviyat(flag)=111";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (111);
 //BA.debugLineNum = 144;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 break; }
case 5: {
 //BA.debugLineNum = 146;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 147;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 148;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 149;BA.debugLine="extra.index_ob_olaviyat(flag)=11";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (11);
 //BA.debugLineNum = 150;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 break; }
}
;
 };
 //BA.debugLineNum = 153;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 154;BA.debugLine="panel.Initialize(\"panel\")";
_panel.Initialize(mostCurrent.activityBA,"panel");
 //BA.debugLineNum = 155;BA.debugLine="panel.Color = Colors.DarkGray";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 156;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 159;BA.debugLine="index_ScrollView.Panel.AddView(panel,left_draw,ex";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_panel.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
 //BA.debugLineNum = 160;BA.debugLine="extra.index_ob_top = extra.index_ob_top + extra.i";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+mostCurrent._extra._index_ob_top_cach);
 //BA.debugLineNum = 161;BA.debugLine="index_ScrollView.Panel.Height = extra.index_ob_to";
mostCurrent._index_scrollview.getPanel().setHeight((int) (mostCurrent._extra._index_ob_top+_space));
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub jobdone(job As HttpJob)";
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
