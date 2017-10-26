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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "manini.b4a.example", "manini.b4a.example.index");
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



public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _index_scrollview = null;
public anywheresoftware.b4a.objects.collections.List _index_retrive_list = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime});
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
RDebugUtils.currentLine=1179648;
 //BA.debugLineNum = 1179648;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=1179650;
 //BA.debugLineNum = 1179650;BA.debugLine="Activity.LoadLayout(\"index\")";
mostCurrent._activity.LoadLayout("index",mostCurrent.activityBA);
RDebugUtils.currentLine=1179651;
 //BA.debugLineNum = 1179651;BA.debugLine="extra.index_ob_olaviyat(0) = 1";
mostCurrent._extra._index_ob_olaviyat[(int) (0)] = (int) (1);
RDebugUtils.currentLine=1179655;
 //BA.debugLineNum = 1179655;BA.debugLine="extra.load_index";
mostCurrent._extra._load_index(mostCurrent.activityBA);
RDebugUtils.currentLine=1179656;
 //BA.debugLineNum = 1179656;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=1179657;
 //BA.debugLineNum = 1179657;BA.debugLine="r.Target = index_ScrollView";
_r.Target = (Object)(mostCurrent._index_scrollview.getObject());
RDebugUtils.currentLine=1179658;
 //BA.debugLineNum = 1179658;BA.debugLine="r.RunMethod2(\"setVerticalScrollBarEnabled\", False";
_r.RunMethod2("setVerticalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
RDebugUtils.currentLine=1179659;
 //BA.debugLineNum = 1179659;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang.i";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
RDebugUtils.currentLine=1179660;
 //BA.debugLineNum = 1179660;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="index";
RDebugUtils.currentLine=1310720;
 //BA.debugLineNum = 1310720;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=1310721;
 //BA.debugLineNum = 1310721;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null);
RDebugUtils.currentLine=1245184;
 //BA.debugLineNum = 1245184;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=1245185;
 //BA.debugLineNum = 1245185;BA.debugLine="End Sub";
return "";
}
public static String  _index_draw(String _size,String _flag) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "index_draw"))
	return (String) Debug.delegate(mostCurrent.activityBA, "index_draw", new Object[] {_size,_flag});
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
int _space = 0;
int _padding_space = 0;
int _left_draw = 0;
int _width_draw = 0;
int _shadow_space = 0;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
RDebugUtils.currentLine=1441792;
 //BA.debugLineNum = 1441792;BA.debugLine="Sub index_draw(size As String,flag)";
RDebugUtils.currentLine=1441793;
 //BA.debugLineNum = 1441793;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1441794;
 //BA.debugLineNum = 1441794;BA.debugLine="panel.Initialize(\"panel\")";
_panel.Initialize(mostCurrent.activityBA,"panel");
RDebugUtils.currentLine=1441795;
 //BA.debugLineNum = 1441795;BA.debugLine="Dim space As Int = 2dip";
_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
RDebugUtils.currentLine=1441796;
 //BA.debugLineNum = 1441796;BA.debugLine="Dim padding_space As Int = 2dip";
_padding_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
RDebugUtils.currentLine=1441797;
 //BA.debugLineNum = 1441797;BA.debugLine="If size=\"larg\" Then";
if ((_size).equals("larg")) { 
RDebugUtils.currentLine=1441798;
 //BA.debugLineNum = 1441798;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441799;
 //BA.debugLineNum = 1441799;BA.debugLine="Dim width_draw As Int = 100%x - left_draw";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_left_draw);
RDebugUtils.currentLine=1441800;
 //BA.debugLineNum = 1441800;BA.debugLine="Dim shadow_space As Int = 5dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
RDebugUtils.currentLine=1441801;
 //BA.debugLineNum = 1441801;BA.debugLine="extra.index_ob_olaviyat(flag) = 1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1441802;
 //BA.debugLineNum = 1441802;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441803;
 //BA.debugLineNum = 1441803;BA.debugLine="panel.Color = Colors.red";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
RDebugUtils.currentLine=1441805;
 //BA.debugLineNum = 1441805;BA.debugLine="If size=\"medium\" Then";
if ((_size).equals("medium")) { 
RDebugUtils.currentLine=1441806;
 //BA.debugLineNum = 1441806;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (4),(int) (111),(int) (11),(int) (1))) {
case 0: {
RDebugUtils.currentLine=1441808;
 //BA.debugLineNum = 1441808;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441809;
 //BA.debugLineNum = 1441809;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441810;
 //BA.debugLineNum = 1441810;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441811;
 //BA.debugLineNum = 1441811;BA.debugLine="extra.index_ob_olaviyat(flag)=224";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (224);
RDebugUtils.currentLine=1441812;
 //BA.debugLineNum = 1441812;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441813;
 //BA.debugLineNum = 1441813;BA.debugLine="panel.Color = Colors.Green";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 break; }
case 1: {
RDebugUtils.currentLine=1441815;
 //BA.debugLineNum = 1441815;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441816;
 //BA.debugLineNum = 1441816;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441817;
 //BA.debugLineNum = 1441817;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441818;
 //BA.debugLineNum = 1441818;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441819;
 //BA.debugLineNum = 1441819;BA.debugLine="panel.Color = Colors.Blue";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 break; }
case 2: {
RDebugUtils.currentLine=1441821;
 //BA.debugLineNum = 1441821;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441822;
 //BA.debugLineNum = 1441822;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441823;
 //BA.debugLineNum = 1441823;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441824;
 //BA.debugLineNum = 1441824;BA.debugLine="extra.index_ob_olaviyat(flag)=222";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (222);
RDebugUtils.currentLine=1441825;
 //BA.debugLineNum = 1441825;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441826;
 //BA.debugLineNum = 1441826;BA.debugLine="panel.Color = Colors.Green";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 break; }
case 3: {
RDebugUtils.currentLine=1441828;
 //BA.debugLineNum = 1441828;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441829;
 //BA.debugLineNum = 1441829;BA.debugLine="Dim width_draw As Int = 66.4%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA);
RDebugUtils.currentLine=1441830;
 //BA.debugLineNum = 1441830;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441831;
 //BA.debugLineNum = 1441831;BA.debugLine="extra.index_ob_olaviyat(flag)=22";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (22);
RDebugUtils.currentLine=1441832;
 //BA.debugLineNum = 1441832;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441833;
 //BA.debugLineNum = 1441833;BA.debugLine="panel.Color = Colors.red";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 break; }
}
;
 };
RDebugUtils.currentLine=1441836;
 //BA.debugLineNum = 1441836;BA.debugLine="If size=\"small\" Then";
if ((_size).equals("small")) { 
RDebugUtils.currentLine=1441838;
 //BA.debugLineNum = 1441838;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (22),(int) (111),(int) (11),(int) (1))) {
case 0: {
RDebugUtils.currentLine=1441840;
 //BA.debugLineNum = 1441840;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441841;
 //BA.debugLineNum = 1441841;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441842;
 //BA.debugLineNum = 1441842;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441843;
 //BA.debugLineNum = 1441843;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
RDebugUtils.currentLine=1441844;
 //BA.debugLineNum = 1441844;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1441845;
 //BA.debugLineNum = 1441845;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441846;
 //BA.debugLineNum = 1441846;BA.debugLine="panel.Color = Colors.rgb(255, 51, 0) ' range";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (51),(int) (0)));
 break; }
case 1: {
RDebugUtils.currentLine=1441849;
 //BA.debugLineNum = 1441849;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441850;
 //BA.debugLineNum = 1441850;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441851;
 //BA.debugLineNum = 1441851;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441852;
 //BA.debugLineNum = 1441852;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
RDebugUtils.currentLine=1441853;
 //BA.debugLineNum = 1441853;BA.debugLine="extra.index_ob_olaviyat(flag)=225";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (225);
RDebugUtils.currentLine=1441854;
 //BA.debugLineNum = 1441854;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441855;
 //BA.debugLineNum = 1441855;BA.debugLine="panel.Color = Colors.rgb(0, 51, 0) ' green";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (51),(int) (0)));
 break; }
case 2: {
RDebugUtils.currentLine=1441857;
 //BA.debugLineNum = 1441857;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441858;
 //BA.debugLineNum = 1441858;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441859;
 //BA.debugLineNum = 1441859;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441860;
 //BA.debugLineNum = 1441860;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
RDebugUtils.currentLine=1441861;
 //BA.debugLineNum = 1441861;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1441862;
 //BA.debugLineNum = 1441862;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441863;
 //BA.debugLineNum = 1441863;BA.debugLine="panel.Color = Colors.rgb(255, 255, 102) ' yell";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (255),(int) (102)));
 break; }
case 3: {
RDebugUtils.currentLine=1441865;
 //BA.debugLineNum = 1441865;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441866;
 //BA.debugLineNum = 1441866;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441867;
 //BA.debugLineNum = 1441867;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441868;
 //BA.debugLineNum = 1441868;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
RDebugUtils.currentLine=1441869;
 //BA.debugLineNum = 1441869;BA.debugLine="extra.index_ob_olaviyat(flag)=223";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (223);
RDebugUtils.currentLine=1441870;
 //BA.debugLineNum = 1441870;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441871;
 //BA.debugLineNum = 1441871;BA.debugLine="panel.Color = Colors.red";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 break; }
case 4: {
RDebugUtils.currentLine=1441873;
 //BA.debugLineNum = 1441873;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441874;
 //BA.debugLineNum = 1441874;BA.debugLine="Dim width_draw As Int = 33.23%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.23),mostCurrent.activityBA);
RDebugUtils.currentLine=1441875;
 //BA.debugLineNum = 1441875;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441876;
 //BA.debugLineNum = 1441876;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
RDebugUtils.currentLine=1441877;
 //BA.debugLineNum = 1441877;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1441878;
 //BA.debugLineNum = 1441878;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441879;
 //BA.debugLineNum = 1441879;BA.debugLine="panel.Color = Colors.Green";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 break; }
case 5: {
RDebugUtils.currentLine=1441881;
 //BA.debugLineNum = 1441881;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441882;
 //BA.debugLineNum = 1441882;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441883;
 //BA.debugLineNum = 1441883;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441884;
 //BA.debugLineNum = 1441884;BA.debugLine="extra.index_ob_olaviyat(flag)=221";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (221);
RDebugUtils.currentLine=1441885;
 //BA.debugLineNum = 1441885;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441886;
 //BA.debugLineNum = 1441886;BA.debugLine="panel.Color = Colors.Blue";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 break; }
case 6: {
RDebugUtils.currentLine=1441888;
 //BA.debugLineNum = 1441888;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441889;
 //BA.debugLineNum = 1441889;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441890;
 //BA.debugLineNum = 1441890;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441891;
 //BA.debugLineNum = 1441891;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1441892;
 //BA.debugLineNum = 1441892;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1441893;
 //BA.debugLineNum = 1441893;BA.debugLine="panel.Color = Colors.rgb(255, 102, 255)";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (102),(int) (255)));
 break; }
case 7: {
RDebugUtils.currentLine=1441895;
 //BA.debugLineNum = 1441895;BA.debugLine="Dim left_draw As Int = 33.3%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.3),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1441896;
 //BA.debugLineNum = 1441896;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441897;
 //BA.debugLineNum = 1441897;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441898;
 //BA.debugLineNum = 1441898;BA.debugLine="extra.index_ob_olaviyat(flag)=111";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (111);
RDebugUtils.currentLine=1441899;
 //BA.debugLineNum = 1441899;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441900;
 //BA.debugLineNum = 1441900;BA.debugLine="panel.Color = Colors.Black";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 break; }
case 8: {
RDebugUtils.currentLine=1441902;
 //BA.debugLineNum = 1441902;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1441903;
 //BA.debugLineNum = 1441903;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1441904;
 //BA.debugLineNum = 1441904;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1441905;
 //BA.debugLineNum = 1441905;BA.debugLine="extra.index_ob_olaviyat(flag)=11";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (11);
RDebugUtils.currentLine=1441906;
 //BA.debugLineNum = 1441906;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1441907;
 //BA.debugLineNum = 1441907;BA.debugLine="panel.Color = Colors.DarkGray";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 break; }
}
;
 };
RDebugUtils.currentLine=1441912;
 //BA.debugLineNum = 1441912;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
RDebugUtils.currentLine=1441915;
 //BA.debugLineNum = 1441915;BA.debugLine="index_ScrollView.Panel.AddView(panel,left_draw,ex";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_panel.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
RDebugUtils.currentLine=1441916;
 //BA.debugLineNum = 1441916;BA.debugLine="extra.index_ob_top = extra.index_ob_top + extra.i";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+mostCurrent._extra._index_ob_top_cach);
RDebugUtils.currentLine=1441917;
 //BA.debugLineNum = 1441917;BA.debugLine="index_ScrollView.Panel.Height = extra.index_ob_to";
mostCurrent._index_scrollview.getPanel().setHeight((int) (mostCurrent._extra._index_ob_top+_space));
RDebugUtils.currentLine=1441918;
 //BA.debugLineNum = 1441918;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "jobdone"))
	return (String) Debug.delegate(mostCurrent.activityBA, "jobdone", new Object[] {_job});
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
int _i = 0;
int _x = 0;
RDebugUtils.currentLine=1376256;
 //BA.debugLineNum = 1376256;BA.debugLine="Sub jobdone(job As HttpJob)";
RDebugUtils.currentLine=1376257;
 //BA.debugLineNum = 1376257;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1376258;
 //BA.debugLineNum = 1376258;BA.debugLine="If job.JobName = \"load_indexjob\" Then";
if ((_job._jobname).equals("load_indexjob")) { 
RDebugUtils.currentLine=1376259;
 //BA.debugLineNum = 1376259;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=1376260;
 //BA.debugLineNum = 1376260;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
RDebugUtils.currentLine=1376261;
 //BA.debugLineNum = 1376261;BA.debugLine="index_retrive_list= parser.NextArray";
mostCurrent._index_retrive_list = _parser.NextArray();
RDebugUtils.currentLine=1376263;
 //BA.debugLineNum = 1376263;BA.debugLine="Log(extra.image_address &  index_retrive_list.G";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._extra._image_address+BA.ObjectToString(mostCurrent._index_retrive_list.Get((int) (2))));
RDebugUtils.currentLine=1376264;
 //BA.debugLineNum = 1376264;BA.debugLine="For  i = 1 To 11";
{
final int step7 = 1;
final int limit7 = (int) (11);
_i = (int) (1) ;
for (;(step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7) ;_i = ((int)(0 + _i + step7))  ) {
RDebugUtils.currentLine=1376265;
 //BA.debugLineNum = 1376265;BA.debugLine="Select extra.index_ob_olaviyat(i-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)],(int) (1),(int) (22),(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (11),(int) (111))) {
case 0: {
RDebugUtils.currentLine=1376267;
 //BA.debugLineNum = 1376267;BA.debugLine="Dim x As Int = Rnd(1,5)";
_x = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
RDebugUtils.currentLine=1376268;
 //BA.debugLineNum = 1376268;BA.debugLine="If x = 1 Then";
if (_x==1) { 
RDebugUtils.currentLine=1376269;
 //BA.debugLineNum = 1376269;BA.debugLine="index_draw(\"larg\",i)";
_index_draw("larg",BA.NumberToString(_i));
 };
RDebugUtils.currentLine=1376272;
 //BA.debugLineNum = 1376272;BA.debugLine="If x = 2 Then";
if (_x==2) { 
RDebugUtils.currentLine=1376273;
 //BA.debugLineNum = 1376273;BA.debugLine="index_draw(\"medium\",i)";
_index_draw("medium",BA.NumberToString(_i));
 };
RDebugUtils.currentLine=1376276;
 //BA.debugLineNum = 1376276;BA.debugLine="If x = 3 Then";
if (_x==3) { 
RDebugUtils.currentLine=1376277;
 //BA.debugLineNum = 1376277;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 };
RDebugUtils.currentLine=1376280;
 //BA.debugLineNum = 1376280;BA.debugLine="If x = 4 Then";
if (_x==4) { 
RDebugUtils.currentLine=1376281;
 //BA.debugLineNum = 1376281;BA.debugLine="extra.index_ob_olaviyat(i-1) = 4";
mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)] = (int) (4);
RDebugUtils.currentLine=1376282;
 //BA.debugLineNum = 1376282;BA.debugLine="index_draw(\"medium\",i)";
_index_draw("medium",BA.NumberToString(_i));
 };
 break; }
case 1: {
RDebugUtils.currentLine=1376286;
 //BA.debugLineNum = 1376286;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
case 2: {
RDebugUtils.currentLine=1376288;
 //BA.debugLineNum = 1376288;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
case 3: {
RDebugUtils.currentLine=1376291;
 //BA.debugLineNum = 1376291;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
case 4: {
RDebugUtils.currentLine=1376293;
 //BA.debugLineNum = 1376293;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
case 5: {
RDebugUtils.currentLine=1376295;
 //BA.debugLineNum = 1376295;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
case 6: {
RDebugUtils.currentLine=1376297;
 //BA.debugLineNum = 1376297;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
case 7: {
RDebugUtils.currentLine=1376299;
 //BA.debugLineNum = 1376299;BA.debugLine="Dim x As Int = Rnd(1,3)";
_x = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (3));
RDebugUtils.currentLine=1376300;
 //BA.debugLineNum = 1376300;BA.debugLine="If x = 1 Then  index_draw(\"small\",i)";
if (_x==1) { 
_index_draw("small",BA.NumberToString(_i));};
RDebugUtils.currentLine=1376301;
 //BA.debugLineNum = 1376301;BA.debugLine="If x = 2 Then  index_draw(\"medium\",i)";
if (_x==2) { 
_index_draw("medium",BA.NumberToString(_i));};
 break; }
case 8: {
RDebugUtils.currentLine=1376303;
 //BA.debugLineNum = 1376303;BA.debugLine="index_draw(\"small\",i)";
_index_draw("small",BA.NumberToString(_i));
 break; }
}
;
 }
};
 };
 };
RDebugUtils.currentLine=1376308;
 //BA.debugLineNum = 1376308;BA.debugLine="End Sub";
return "";
}
}