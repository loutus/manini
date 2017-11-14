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
public anywheresoftware.b4a.objects.collections.List _index_retrive_list_img = null;
public anywheresoftware.b4a.objects.collections.List _index_retrive_list_model = null;
public anywheresoftware.b4a.objects.ImageViewWrapper[] _imgdrew = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper[] _product_scrollview = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scrollview_lastproduct = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _category_hscrollview = null;
public anywheresoftware.b4a.objects.ImageViewWrapper[] _lastproduct_imageview = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _headerproc = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _headerproctxt = null;
public anywheresoftware.b4a.objects.ImageViewWrapper[] _procimage = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _property_pnl = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public manini.b4a.example.product _product = null;
public manini.b4a.example.property _property = null;
public manini.b4a.example.omid _omid = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime});
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
RDebugUtils.currentLine=1441792;
 //BA.debugLineNum = 1441792;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=1441794;
 //BA.debugLineNum = 1441794;BA.debugLine="Activity.LoadLayout(\"index\")";
mostCurrent._activity.LoadLayout("index",mostCurrent.activityBA);
RDebugUtils.currentLine=1441795;
 //BA.debugLineNum = 1441795;BA.debugLine="extra.index_ob_olaviyat(0) = 1";
mostCurrent._extra._index_ob_olaviyat[(int) (0)] = (int) (1);
RDebugUtils.currentLine=1441797;
 //BA.debugLineNum = 1441797;BA.debugLine="If File.Exists(File.DirInternalCache & \"/product";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product","")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=1441798;
 //BA.debugLineNum = 1441798;BA.debugLine="File.MakeDir(File.DirInternalCache,\"product\"	)";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product");
 };
RDebugUtils.currentLine=1441801;
 //BA.debugLineNum = 1441801;BA.debugLine="extra.load_index";
mostCurrent._extra._load_index(mostCurrent.activityBA);
RDebugUtils.currentLine=1441802;
 //BA.debugLineNum = 1441802;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=1441803;
 //BA.debugLineNum = 1441803;BA.debugLine="r.Target = index_ScrollView";
_r.Target = (Object)(mostCurrent._index_scrollview.getObject());
RDebugUtils.currentLine=1441804;
 //BA.debugLineNum = 1441804;BA.debugLine="r.RunMethod2(\"setVerticalScrollBarEnabled\", False";
_r.RunMethod2("setVerticalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
RDebugUtils.currentLine=1441805;
 //BA.debugLineNum = 1441805;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang.i";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
RDebugUtils.currentLine=1441808;
 //BA.debugLineNum = 1441808;BA.debugLine="extra.flag_procpnl = 0";
mostCurrent._extra._flag_procpnl = (int) (0);
RDebugUtils.currentLine=1441809;
 //BA.debugLineNum = 1441809;BA.debugLine="extra.propertyjson = 0";
mostCurrent._extra._propertyjson = (int) (0);
RDebugUtils.currentLine=1441810;
 //BA.debugLineNum = 1441810;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	return (Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode});
RDebugUtils.currentLine=1376256;
 //BA.debugLineNum = 1376256;BA.debugLine="Sub activity_KeyPress (KeyCode As Int) As Boolean";
RDebugUtils.currentLine=1376258;
 //BA.debugLineNum = 1376258;BA.debugLine="If KeyCode= KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=1376259;
 //BA.debugLineNum = 1376259;BA.debugLine="If extra.propertyjson = 1 Then";
if (mostCurrent._extra._propertyjson==1) { 
RDebugUtils.currentLine=1376260;
 //BA.debugLineNum = 1376260;BA.debugLine="property_pnl.RemoveView";
mostCurrent._property_pnl.RemoveView();
RDebugUtils.currentLine=1376261;
 //BA.debugLineNum = 1376261;BA.debugLine="property_pnl.Visible = False";
mostCurrent._property_pnl.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1376262;
 //BA.debugLineNum = 1376262;BA.debugLine="extra.propertyjson = 0";
mostCurrent._extra._propertyjson = (int) (0);
RDebugUtils.currentLine=1376263;
 //BA.debugLineNum = 1376263;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else 
{RDebugUtils.currentLine=1376264;
 //BA.debugLineNum = 1376264;BA.debugLine="Else If extra.flag_procpnl = 0 Then";
if (mostCurrent._extra._flag_procpnl==0) { 
RDebugUtils.currentLine=1376265;
 //BA.debugLineNum = 1376265;BA.debugLine="extra.procimg_flag = 0";
mostCurrent._extra._procimg_flag = (int) (0);
RDebugUtils.currentLine=1376266;
 //BA.debugLineNum = 1376266;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
RDebugUtils.currentLine=1376269;
 //BA.debugLineNum = 1376269;BA.debugLine="extra.procimg_flag =extra.procimg_flag -  extra";
mostCurrent._extra._procimg_flag = (int) (mostCurrent._extra._procimg_flag-mostCurrent._extra._procimg_count[(int) (mostCurrent._extra._flag_procpnl-1)]);
RDebugUtils.currentLine=1376271;
 //BA.debugLineNum = 1376271;BA.debugLine="headerproc(extra.flag_procpnl-1).Visible = Fals";
mostCurrent._headerproc[(int) (mostCurrent._extra._flag_procpnl-1)].setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1376272;
 //BA.debugLineNum = 1376272;BA.debugLine="product_ScrollView(extra.flag_procpnl-1).Remove";
mostCurrent._product_scrollview[(int) (mostCurrent._extra._flag_procpnl-1)].RemoveView();
RDebugUtils.currentLine=1376273;
 //BA.debugLineNum = 1376273;BA.debugLine="product_ScrollView(extra.flag_procpnl-1).Visibl";
mostCurrent._product_scrollview[(int) (mostCurrent._extra._flag_procpnl-1)].setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1376274;
 //BA.debugLineNum = 1376274;BA.debugLine="extra.flag_procpnl = extra.flag_procpnl - 1";
mostCurrent._extra._flag_procpnl = (int) (mostCurrent._extra._flag_procpnl-1);
RDebugUtils.currentLine=1376275;
 //BA.debugLineNum = 1376275;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }}
;
 };
RDebugUtils.currentLine=1376279;
 //BA.debugLineNum = 1376279;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="index";
RDebugUtils.currentLine=1572864;
 //BA.debugLineNum = 1572864;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=1572865;
 //BA.debugLineNum = 1572865;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null);
RDebugUtils.currentLine=1507328;
 //BA.debugLineNum = 1507328;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=1507329;
 //BA.debugLineNum = 1507329;BA.debugLine="End Sub";
return "";
}
public static String  _createnon() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "createnon"))
	return (String) Debug.delegate(mostCurrent.activityBA, "createnon", null);
anywheresoftware.b4a.objects.LabelWrapper _lblnodata = null;
RDebugUtils.currentLine=2162688;
 //BA.debugLineNum = 2162688;BA.debugLine="Sub createnon()";
RDebugUtils.currentLine=2162689;
 //BA.debugLineNum = 2162689;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2162690;
 //BA.debugLineNum = 2162690;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2162691;
 //BA.debugLineNum = 2162691;BA.debugLine="lblnodata.Text =\"هیچ مشخصه ای وجود ندارد\"";
_lblnodata.setText(BA.ObjectToCharSequence("هیچ مشخصه ای وجود ندارد"));
RDebugUtils.currentLine=2162692;
 //BA.debugLineNum = 2162692;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=2162693;
 //BA.debugLineNum = 2162693;BA.debugLine="lblnodata.TextColor = Colors.rgb(179, 179, 179)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
RDebugUtils.currentLine=2162694;
 //BA.debugLineNum = 2162694;BA.debugLine="lblnodata.TextSize = 12dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
RDebugUtils.currentLine=2162695;
 //BA.debugLineNum = 2162695;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"yek";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2162696;
 //BA.debugLineNum = 2162696;BA.debugLine="property_pnl.Panel.AddView(lblnodata,0,0,100%x,50";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
RDebugUtils.currentLine=2162697;
 //BA.debugLineNum = 2162697;BA.debugLine="End Sub";
return "";
}
public static String  _imgdrew_click() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "imgdrew_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "imgdrew_click", null);
anywheresoftware.b4a.objects.ImageViewWrapper _imgdre = null;
RDebugUtils.currentLine=1900544;
 //BA.debugLineNum = 1900544;BA.debugLine="Sub imgdrew_click()";
RDebugUtils.currentLine=1900545;
 //BA.debugLineNum = 1900545;BA.debugLine="Dim imgdre As ImageView";
_imgdre = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=1900546;
 //BA.debugLineNum = 1900546;BA.debugLine="imgdre = Sender";
_imgdre.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=1900547;
 //BA.debugLineNum = 1900547;BA.debugLine="Log(imgdre.Tag)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_imgdre.getTag()));
RDebugUtils.currentLine=1900548;
 //BA.debugLineNum = 1900548;BA.debugLine="extra.product_id_toshow = imgdre.Tag";
mostCurrent._extra._product_id_toshow = (int)(BA.ObjectToNumber(_imgdre.getTag()));
RDebugUtils.currentLine=1900550;
 //BA.debugLineNum = 1900550;BA.debugLine="product_ScrollView(extra.flag_procpnl).Initialize";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].Initialize2(mostCurrent.activityBA,(int) (500),"product_ScrollView");
RDebugUtils.currentLine=1900551;
 //BA.debugLineNum = 1900551;BA.debugLine="product_ScrollView(extra.flag_procpnl).Color = Co";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
RDebugUtils.currentLine=1900552;
 //BA.debugLineNum = 1900552;BA.debugLine="Activity.AddView(product_ScrollView(extra.flag_pr";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=1900553;
 //BA.debugLineNum = 1900553;BA.debugLine="loadroc(extra.flag_procpnl)";
_loadroc(mostCurrent._extra._flag_procpnl);
RDebugUtils.currentLine=1900554;
 //BA.debugLineNum = 1900554;BA.debugLine="extra.flag_procpnl = extra.flag_procpnl+ 1";
mostCurrent._extra._flag_procpnl = (int) (mostCurrent._extra._flag_procpnl+1);
RDebugUtils.currentLine=1900556;
 //BA.debugLineNum = 1900556;BA.debugLine="End Sub";
return "";
}
public static String  _loadroc(int _flag) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "loadroc"))
	return (String) Debug.delegate(mostCurrent.activityBA, "loadroc", new Object[] {_flag});
anywheresoftware.b4a.samples.httputils2.httpjob _download = null;
anywheresoftware.b4a.samples.httputils2.httpjob _downloadtext = null;
anywheresoftware.b4a.samples.httputils2.httpjob _downloadpic = null;
anywheresoftware.b4a.samples.httputils2.httpjob _jober = null;
anywheresoftware.b4a.samples.httputils2.httpjob _load_category = null;
String _moretext_data = "";
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl2 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl3 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl4_moshakhasat = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl4_line = null;
anywheresoftware.b4a.objects.LabelWrapper _moretext = null;
anywheresoftware.b4a.objects.PanelWrapper _btn = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _buy = null;
anywheresoftware.b4a.objects.LabelWrapper _buyicon = null;
njdude.fontawesome.lib.fontawesome _fae = null;
njdude.fontawesome.lib.fontawesome _fa = null;
anywheresoftware.b4a.objects.LabelWrapper _properticon = null;
anywheresoftware.b4a.objects.ImageViewWrapper _pic_sheare = null;
anywheresoftware.b4a.objects.ImageViewWrapper _pic_like = null;
anywheresoftware.b4a.objects.LabelWrapper _color = null;
anywheresoftware.b4a.objects.LabelWrapper _garanti = null;
anywheresoftware.b4a.objects.LabelWrapper _saler = null;
anywheresoftware.b4a.objects.LabelWrapper _header_title = null;
RDebugUtils.currentLine=1966080;
 //BA.debugLineNum = 1966080;BA.debugLine="Sub loadroc(flag As Int)";
RDebugUtils.currentLine=1966083;
 //BA.debugLineNum = 1966083;BA.debugLine="headerproc(flag).Initialize(\"\")";
mostCurrent._headerproc[_flag].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966084;
 //BA.debugLineNum = 1966084;BA.debugLine="headerproc(flag).Color = Colors.rgb(26, 188, 156)";
mostCurrent._headerproc[_flag].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (26),(int) (188),(int) (156)));
RDebugUtils.currentLine=1966085;
 //BA.debugLineNum = 1966085;BA.debugLine="Activity.AddView(headerproc(flag),0,0,100%x,55dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._headerproc[_flag].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)));
RDebugUtils.currentLine=1966090;
 //BA.debugLineNum = 1966090;BA.debugLine="headerproctxt(flag).Initialize(\"\")";
mostCurrent._headerproctxt[_flag].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966091;
 //BA.debugLineNum = 1966091;BA.debugLine="headerproctxt(flag).Gravity = Bit.Or(Gravity.CENT";
mostCurrent._headerproctxt[_flag].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.RIGHT));
RDebugUtils.currentLine=1966092;
 //BA.debugLineNum = 1966092;BA.debugLine="headerproctxt(flag).Typeface = Typeface.LoadFromA";
mostCurrent._headerproctxt[_flag].setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966093;
 //BA.debugLineNum = 1966093;BA.debugLine="headerproctxt(flag).TextSize = 8dip";
mostCurrent._headerproctxt[_flag].setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
RDebugUtils.currentLine=1966094;
 //BA.debugLineNum = 1966094;BA.debugLine="headerproctxt(flag).Text = \"در حال بارگزاری\"";
mostCurrent._headerproctxt[_flag].setText(BA.ObjectToCharSequence("در حال بارگزاری"));
RDebugUtils.currentLine=1966095;
 //BA.debugLineNum = 1966095;BA.debugLine="headerproctxt(flag).TextColor = Colors.White";
mostCurrent._headerproctxt[_flag].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1966096;
 //BA.debugLineNum = 1966096;BA.debugLine="Activity.AddView(headerproctxt(flag),0,5dip,95%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._headerproctxt[_flag].getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1966100;
 //BA.debugLineNum = 1966100;BA.debugLine="Dim download As HttpJob";
_download = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=1966101;
 //BA.debugLineNum = 1966101;BA.debugLine="download.Initialize(\"nameproc\" & flag,Me)";
_download._initialize(processBA,"nameproc"+BA.NumberToString(_flag),index.getObject());
RDebugUtils.currentLine=1966102;
 //BA.debugLineNum = 1966102;BA.debugLine="download.PostString(extra.api,\"op=product&id=\" &";
_download._poststring(mostCurrent._extra._api,"op=product&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
RDebugUtils.currentLine=1966105;
 //BA.debugLineNum = 1966105;BA.debugLine="Dim downloadtext As HttpJob";
_downloadtext = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=1966106;
 //BA.debugLineNum = 1966106;BA.debugLine="downloadtext.Initialize(\"textproc\"  & flag ,Me)";
_downloadtext._initialize(processBA,"textproc"+BA.NumberToString(_flag),index.getObject());
RDebugUtils.currentLine=1966107;
 //BA.debugLineNum = 1966107;BA.debugLine="downloadtext.PostString(extra.api,\"op=productdesc";
_downloadtext._poststring(mostCurrent._extra._api,"op=productdescription&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
RDebugUtils.currentLine=1966109;
 //BA.debugLineNum = 1966109;BA.debugLine="Dim downloadpic As HttpJob";
_downloadpic = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=1966110;
 //BA.debugLineNum = 1966110;BA.debugLine="downloadpic.Initialize(\"picproc\" & flag,Me)";
_downloadpic._initialize(processBA,"picproc"+BA.NumberToString(_flag),index.getObject());
RDebugUtils.currentLine=1966111;
 //BA.debugLineNum = 1966111;BA.debugLine="downloadpic.PostString(extra.api,\"op=productpics&";
_downloadpic._poststring(mostCurrent._extra._api,"op=productpics&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
RDebugUtils.currentLine=1966114;
 //BA.debugLineNum = 1966114;BA.debugLine="Dim jober As HttpJob";
_jober = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=1966115;
 //BA.debugLineNum = 1966115;BA.debugLine="jober.Initialize(\"lastproduct\"  & flag ,Me)";
_jober._initialize(processBA,"lastproduct"+BA.NumberToString(_flag),index.getObject());
RDebugUtils.currentLine=1966116;
 //BA.debugLineNum = 1966116;BA.debugLine="jober.PostString(extra.api,\"op=lastproduct\")";
_jober._poststring(mostCurrent._extra._api,"op=lastproduct");
RDebugUtils.currentLine=1966118;
 //BA.debugLineNum = 1966118;BA.debugLine="Dim load_category As HttpJob";
_load_category = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=1966119;
 //BA.debugLineNum = 1966119;BA.debugLine="load_category.Initialize(\"loadcategory\" & flag,Me";
_load_category._initialize(processBA,"loadcategory"+BA.NumberToString(_flag),index.getObject());
RDebugUtils.currentLine=1966120;
 //BA.debugLineNum = 1966120;BA.debugLine="load_category.PostString(extra.api,\"op=category\")";
_load_category._poststring(mostCurrent._extra._api,"op=category");
RDebugUtils.currentLine=1966122;
 //BA.debugLineNum = 1966122;BA.debugLine="Dim moretext_data As String";
_moretext_data = "";
RDebugUtils.currentLine=1966123;
 //BA.debugLineNum = 1966123;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1966124;
 //BA.debugLineNum = 1966124;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966125;
 //BA.debugLineNum = 1966125;BA.debugLine="pnl.Color = Colors.rgb(250, 250, 250)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
RDebugUtils.currentLine=1966126;
 //BA.debugLineNum = 1966126;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl,0,50%x";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
RDebugUtils.currentLine=1966128;
 //BA.debugLineNum = 1966128;BA.debugLine="Dim pnl2 As Panel";
_pnl2 = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1966129;
 //BA.debugLineNum = 1966129;BA.debugLine="pnl2.Initialize(\"\")";
_pnl2.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966130;
 //BA.debugLineNum = 1966130;BA.debugLine="pnl2.Color = Colors.White";
_pnl2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1966131;
 //BA.debugLineNum = 1966131;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl2,15dip";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (163))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=1966132;
 //BA.debugLineNum = 1966132;BA.debugLine="extra.InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl2,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
RDebugUtils.currentLine=1966134;
 //BA.debugLineNum = 1966134;BA.debugLine="Dim pnl3 As Panel";
_pnl3 = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1966135;
 //BA.debugLineNum = 1966135;BA.debugLine="pnl3.Initialize(\"\")";
_pnl3.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966136;
 //BA.debugLineNum = 1966136;BA.debugLine="pnl3.Color = Colors.White";
_pnl3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1966137;
 //BA.debugLineNum = 1966137;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl3,15dip";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
RDebugUtils.currentLine=1966138;
 //BA.debugLineNum = 1966138;BA.debugLine="extra.InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl3,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
RDebugUtils.currentLine=1966140;
 //BA.debugLineNum = 1966140;BA.debugLine="Dim pnl4_moshakhasat As Panel";
_pnl4_moshakhasat = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1966141;
 //BA.debugLineNum = 1966141;BA.debugLine="pnl4_moshakhasat.Initialize(\"\")";
_pnl4_moshakhasat.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966142;
 //BA.debugLineNum = 1966142;BA.debugLine="pnl4_moshakhasat.Color = Colors.White";
_pnl4_moshakhasat.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1966143;
 //BA.debugLineNum = 1966143;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl4_mosha";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl4_moshakhasat.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (289))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
RDebugUtils.currentLine=1966144;
 //BA.debugLineNum = 1966144;BA.debugLine="extra.InitPanel(pnl4_moshakhasat,2,1,Colors.rgb(2";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl4_moshakhasat,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
RDebugUtils.currentLine=1966146;
 //BA.debugLineNum = 1966146;BA.debugLine="moretext_data = \"در حال بارگذاری\"";
_moretext_data = "در حال بارگذاری";
RDebugUtils.currentLine=1966151;
 //BA.debugLineNum = 1966151;BA.debugLine="Dim pnl4_line As Panel";
_pnl4_line = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1966152;
 //BA.debugLineNum = 1966152;BA.debugLine="pnl4_line.Initialize(\"\")";
_pnl4_line.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966153;
 //BA.debugLineNum = 1966153;BA.debugLine="pnl4_line.Color = Colors.rgb(179, 179, 179)";
_pnl4_line.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
RDebugUtils.currentLine=1966154;
 //BA.debugLineNum = 1966154;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl4_line,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl4_line.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
RDebugUtils.currentLine=1966159;
 //BA.debugLineNum = 1966159;BA.debugLine="Dim moretext As Label";
_moretext = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966160;
 //BA.debugLineNum = 1966160;BA.debugLine="moretext.Initialize(\"moretext\")";
_moretext.Initialize(mostCurrent.activityBA,"moretext");
RDebugUtils.currentLine=1966161;
 //BA.debugLineNum = 1966161;BA.debugLine="moretext.TextColor = Colors.rgb(140, 140, 140)";
_moretext.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=1966162;
 //BA.debugLineNum = 1966162;BA.debugLine="moretext.Gravity = Gravity.CENTER";
_moretext.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=1966163;
 //BA.debugLineNum = 1966163;BA.debugLine="moretext.Typeface = Typeface.LoadFromAssets(\"yeka";
_moretext.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966164;
 //BA.debugLineNum = 1966164;BA.debugLine="moretext.TextSize = 10dip";
_moretext.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=1966165;
 //BA.debugLineNum = 1966165;BA.debugLine="moretext.Text = \"ادامه مطلب\"";
_moretext.setText(BA.ObjectToCharSequence("ادامه مطلب"));
RDebugUtils.currentLine=1966166;
 //BA.debugLineNum = 1966166;BA.debugLine="product_ScrollView(flag).Panel.AddView(moretext,1";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_moretext.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1966168;
 //BA.debugLineNum = 1966168;BA.debugLine="Dim btn As Panel";
_btn = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1966169;
 //BA.debugLineNum = 1966169;BA.debugLine="btn.Initialize(\"\")";
_btn.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966170;
 //BA.debugLineNum = 1966170;BA.debugLine="btn.Color = Colors.rgb(102, 187, 106)";
_btn.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=1966172;
 //BA.debugLineNum = 1966172;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
RDebugUtils.currentLine=1966173;
 //BA.debugLineNum = 1966173;BA.debugLine="cd.Initialize(Colors.rgb(102, 187, 106), 5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
RDebugUtils.currentLine=1966174;
 //BA.debugLineNum = 1966174;BA.debugLine="btn.Background = cd";
_btn.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
RDebugUtils.currentLine=1966175;
 //BA.debugLineNum = 1966175;BA.debugLine="product_ScrollView(flag).Panel.AddView(btn,50dip,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_btn.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1966177;
 //BA.debugLineNum = 1966177;BA.debugLine="Dim buy As Label";
_buy = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966178;
 //BA.debugLineNum = 1966178;BA.debugLine="buy.Initialize(\"\")";
_buy.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966179;
 //BA.debugLineNum = 1966179;BA.debugLine="buy.TextColor = Colors.White";
_buy.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1966180;
 //BA.debugLineNum = 1966180;BA.debugLine="buy.Gravity = Gravity.CENTER";
_buy.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=1966181;
 //BA.debugLineNum = 1966181;BA.debugLine="buy.Typeface = Typeface.LoadFromAssets(\"yekan.ttf";
_buy.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966182;
 //BA.debugLineNum = 1966182;BA.debugLine="buy.TextSize = 12dip";
_buy.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
RDebugUtils.currentLine=1966183;
 //BA.debugLineNum = 1966183;BA.debugLine="buy.Text = \"افزودن به سبد خرید\"";
_buy.setText(BA.ObjectToCharSequence("افزودن به سبد خرید"));
RDebugUtils.currentLine=1966184;
 //BA.debugLineNum = 1966184;BA.debugLine="product_ScrollView(flag).Panel.AddView(buy,50dip,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_buy.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (65),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1966186;
 //BA.debugLineNum = 1966186;BA.debugLine="Dim buyicon As Label";
_buyicon = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966187;
 //BA.debugLineNum = 1966187;BA.debugLine="buyicon.Initialize(\"\")";
_buyicon.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966188;
 //BA.debugLineNum = 1966188;BA.debugLine="Private FAe As FontAwesome";
_fae = new njdude.fontawesome.lib.fontawesome();
RDebugUtils.currentLine=1966189;
 //BA.debugLineNum = 1966189;BA.debugLine="FAe.Initialize";
_fae._initialize(processBA);
RDebugUtils.currentLine=1966190;
 //BA.debugLineNum = 1966190;BA.debugLine="buyicon.Gravity = Gravity.CENTER_VERTICAL";
_buyicon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
RDebugUtils.currentLine=1966191;
 //BA.debugLineNum = 1966191;BA.debugLine="buyicon.Typeface = Typeface.FONTAWESOME";
_buyicon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
RDebugUtils.currentLine=1966192;
 //BA.debugLineNum = 1966192;BA.debugLine="buyicon.TextColor = Colors.White";
_buyicon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1966193;
 //BA.debugLineNum = 1966193;BA.debugLine="buyicon.TextSize = 13dip";
_buyicon.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
RDebugUtils.currentLine=1966194;
 //BA.debugLineNum = 1966194;BA.debugLine="buyicon.Text = FAe.GetFontAwesomeIconByName(\"fa-c";
_buyicon.setText(BA.ObjectToCharSequence(_fae._getfontawesomeiconbyname("fa-cart-plus")));
RDebugUtils.currentLine=1966195;
 //BA.debugLineNum = 1966195;BA.debugLine="product_ScrollView(flag).Panel.AddView(buyicon,70";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_buyicon.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1966200;
 //BA.debugLineNum = 1966200;BA.debugLine="Private FA As FontAwesome";
_fa = new njdude.fontawesome.lib.fontawesome();
RDebugUtils.currentLine=1966201;
 //BA.debugLineNum = 1966201;BA.debugLine="FA.Initialize";
_fa._initialize(processBA);
RDebugUtils.currentLine=1966202;
 //BA.debugLineNum = 1966202;BA.debugLine="Dim properticon As Label";
_properticon = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966203;
 //BA.debugLineNum = 1966203;BA.debugLine="properticon.Initialize(\"\")";
_properticon.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966204;
 //BA.debugLineNum = 1966204;BA.debugLine="properticon.TextColor = Colors.Black";
_properticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=1966206;
 //BA.debugLineNum = 1966206;BA.debugLine="properticon.Gravity = Gravity.CENTER_VERTICAL";
_properticon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
RDebugUtils.currentLine=1966207;
 //BA.debugLineNum = 1966207;BA.debugLine="properticon.Typeface = Typeface.FONTAWESOME";
_properticon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
RDebugUtils.currentLine=1966208;
 //BA.debugLineNum = 1966208;BA.debugLine="properticon.Text = FA.GetFontAwesomeIconByName(\"f";
_properticon.setText(BA.ObjectToCharSequence(_fa._getfontawesomeiconbyname("fa-calendar-o")));
RDebugUtils.currentLine=1966209;
 //BA.debugLineNum = 1966209;BA.debugLine="product_ScrollView(flag).Panel.AddView(propertico";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_properticon.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
RDebugUtils.currentLine=1966211;
 //BA.debugLineNum = 1966211;BA.debugLine="Dim pic_sheare As ImageView";
_pic_sheare = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=1966212;
 //BA.debugLineNum = 1966212;BA.debugLine="pic_sheare.Initialize(\"sharepost\")";
_pic_sheare.Initialize(mostCurrent.activityBA,"sharepost");
RDebugUtils.currentLine=1966213;
 //BA.debugLineNum = 1966213;BA.debugLine="pic_sheare.Gravity = Gravity.FILL";
_pic_sheare.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1966214;
 //BA.debugLineNum = 1966214;BA.debugLine="pic_sheare.Tag = extra.product_id_toshow";
_pic_sheare.setTag((Object)(mostCurrent._extra._product_id_toshow));
RDebugUtils.currentLine=1966215;
 //BA.debugLineNum = 1966215;BA.debugLine="pic_sheare.Bitmap = LoadBitmap(File.DirAssets,\"sh";
_pic_sheare.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sharing-interface.png").getObject()));
RDebugUtils.currentLine=1966217;
 //BA.debugLineNum = 1966217;BA.debugLine="Dim pic_like As ImageView";
_pic_like = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=1966218;
 //BA.debugLineNum = 1966218;BA.debugLine="pic_like.Initialize(\"\")";
_pic_like.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966219;
 //BA.debugLineNum = 1966219;BA.debugLine="pic_like.Gravity = Gravity.FILL";
_pic_like.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1966220;
 //BA.debugLineNum = 1966220;BA.debugLine="pic_like.Bitmap = LoadBitmap(File.DirAssets,\"like";
_pic_like.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"like.png").getObject()));
RDebugUtils.currentLine=1966223;
 //BA.debugLineNum = 1966223;BA.debugLine="Dim color As Label";
_color = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966224;
 //BA.debugLineNum = 1966224;BA.debugLine="color.Initialize(\"\")";
_color.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966225;
 //BA.debugLineNum = 1966225;BA.debugLine="color.TextColor = Colors.rgb(140, 140, 140)";
_color.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=1966226;
 //BA.debugLineNum = 1966226;BA.debugLine="color.Gravity = Gravity.RIGHT";
_color.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1966227;
 //BA.debugLineNum = 1966227;BA.debugLine="color.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_color.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966228;
 //BA.debugLineNum = 1966228;BA.debugLine="color.Text = \"رنگ\"";
_color.setText(BA.ObjectToCharSequence("رنگ"));
RDebugUtils.currentLine=1966229;
 //BA.debugLineNum = 1966229;BA.debugLine="color.TextSize = 9dip";
_color.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=1966230;
 //BA.debugLineNum = 1966230;BA.debugLine="product_ScrollView(flag).Panel.AddView(color,0,50";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_color.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (270))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=1966233;
 //BA.debugLineNum = 1966233;BA.debugLine="Dim garanti As Label";
_garanti = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966234;
 //BA.debugLineNum = 1966234;BA.debugLine="garanti.Initialize(\"\")";
_garanti.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966235;
 //BA.debugLineNum = 1966235;BA.debugLine="garanti.TextColor = Colors.rgb(140, 140, 140)";
_garanti.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=1966236;
 //BA.debugLineNum = 1966236;BA.debugLine="garanti.Gravity = Gravity.RIGHT";
_garanti.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1966237;
 //BA.debugLineNum = 1966237;BA.debugLine="garanti.Typeface = Typeface.LoadFromAssets(\"yekan";
_garanti.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966238;
 //BA.debugLineNum = 1966238;BA.debugLine="garanti.Text = \"گارانتی\"";
_garanti.setText(BA.ObjectToCharSequence("گارانتی"));
RDebugUtils.currentLine=1966239;
 //BA.debugLineNum = 1966239;BA.debugLine="garanti.TextSize = 9dip";
_garanti.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=1966240;
 //BA.debugLineNum = 1966240;BA.debugLine="product_ScrollView(flag).Panel.AddView(garanti,0,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_garanti.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=1966243;
 //BA.debugLineNum = 1966243;BA.debugLine="Dim saler As Label";
_saler = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966244;
 //BA.debugLineNum = 1966244;BA.debugLine="saler.Initialize(\"\")";
_saler.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966245;
 //BA.debugLineNum = 1966245;BA.debugLine="saler.TextColor = Colors.rgb(140, 140, 140)";
_saler.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=1966246;
 //BA.debugLineNum = 1966246;BA.debugLine="saler.Gravity = Gravity.RIGHT";
_saler.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1966247;
 //BA.debugLineNum = 1966247;BA.debugLine="saler.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_saler.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966248;
 //BA.debugLineNum = 1966248;BA.debugLine="saler.Text = \"فروشنده\"";
_saler.setText(BA.ObjectToCharSequence("فروشنده"));
RDebugUtils.currentLine=1966249;
 //BA.debugLineNum = 1966249;BA.debugLine="saler.TextSize = 9dip";
_saler.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=1966252;
 //BA.debugLineNum = 1966252;BA.debugLine="Dim header_title As Label";
_header_title = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1966253;
 //BA.debugLineNum = 1966253;BA.debugLine="header_title.Initialize(\"\")";
_header_title.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1966255;
 //BA.debugLineNum = 1966255;BA.debugLine="header_title.Gravity = Gravity.RIGHT";
_header_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1966256;
 //BA.debugLineNum = 1966256;BA.debugLine="header_title.Typeface = Typeface.LoadFromAssets(\"";
_header_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1966257;
 //BA.debugLineNum = 1966257;BA.debugLine="header_title.TextColor = Colors.red";
_header_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
RDebugUtils.currentLine=1966258;
 //BA.debugLineNum = 1966258;BA.debugLine="header_title.TextSize = 11dip";
_header_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
RDebugUtils.currentLine=1966259;
 //BA.debugLineNum = 1966259;BA.debugLine="header_title.Visible = False";
_header_title.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1966262;
 //BA.debugLineNum = 1966262;BA.debugLine="product_ScrollView(flag).Panel.AddView(saler,0,50";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_saler.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=1966263;
 //BA.debugLineNum = 1966263;BA.debugLine="product_ScrollView(flag).Panel.AddView(pic_sheare";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pic_sheare.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)));
RDebugUtils.currentLine=1966264;
 //BA.debugLineNum = 1966264;BA.debugLine="product_ScrollView(flag).Panel.AddView(pic_like,8";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pic_like.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)));
RDebugUtils.currentLine=1966266;
 //BA.debugLineNum = 1966266;BA.debugLine="product_ScrollView(flag).Panel.Height = 2125";
mostCurrent._product_scrollview[_flag].getPanel().setHeight((int) (2125));
RDebugUtils.currentLine=1966267;
 //BA.debugLineNum = 1966267;BA.debugLine="End Sub";
return "";
}
public static String  _index_draw(String _size,String _flag,String _id,String _img,String _model) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "index_draw"))
	return (String) Debug.delegate(mostCurrent.activityBA, "index_draw", new Object[] {_size,_flag,_id,_img,_model});
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
int _color = 0;
int _space = 0;
int _padding_space = 0;
int _left_draw = 0;
int _width_draw = 0;
int _shadow_space = 0;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
String _imgfile = "";
RDebugUtils.currentLine=1835008;
 //BA.debugLineNum = 1835008;BA.debugLine="Sub index_draw(size As String,flag,id,img,model)";
RDebugUtils.currentLine=1835009;
 //BA.debugLineNum = 1835009;BA.debugLine="extra.index_ob_olaviyat_load = flag";
mostCurrent._extra._index_ob_olaviyat_load = (int)(Double.parseDouble(_flag));
RDebugUtils.currentLine=1835010;
 //BA.debugLineNum = 1835010;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1835011;
 //BA.debugLineNum = 1835011;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1835012;
 //BA.debugLineNum = 1835012;BA.debugLine="Dim color As Int";
_color = 0;
RDebugUtils.currentLine=1835013;
 //BA.debugLineNum = 1835013;BA.debugLine="color =Colors.White";
_color = anywheresoftware.b4a.keywords.Common.Colors.White;
RDebugUtils.currentLine=1835014;
 //BA.debugLineNum = 1835014;BA.debugLine="panel.Initialize(\"panel\")";
_panel.Initialize(mostCurrent.activityBA,"panel");
RDebugUtils.currentLine=1835015;
 //BA.debugLineNum = 1835015;BA.debugLine="lbl.Initialize(\"lbl\")";
_lbl.Initialize(mostCurrent.activityBA,"lbl");
RDebugUtils.currentLine=1835016;
 //BA.debugLineNum = 1835016;BA.debugLine="lbl.Text = model";
_lbl.setText(BA.ObjectToCharSequence(_model));
RDebugUtils.currentLine=1835017;
 //BA.debugLineNum = 1835017;BA.debugLine="lbl.TextColor = Colors.White";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1835018;
 //BA.debugLineNum = 1835018;BA.debugLine="lbl.Color = Colors.ARGB(140, 140, 140,100)";
_lbl.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (140),(int) (140),(int) (140),(int) (100)));
RDebugUtils.currentLine=1835019;
 //BA.debugLineNum = 1835019;BA.debugLine="Dim space As Int = 2dip";
_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
RDebugUtils.currentLine=1835020;
 //BA.debugLineNum = 1835020;BA.debugLine="Dim padding_space As Int = 2dip";
_padding_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
RDebugUtils.currentLine=1835021;
 //BA.debugLineNum = 1835021;BA.debugLine="If size=\"larg\" Then";
if ((_size).equals("larg")) { 
RDebugUtils.currentLine=1835022;
 //BA.debugLineNum = 1835022;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835023;
 //BA.debugLineNum = 1835023;BA.debugLine="Dim width_draw As Int = 100%x - left_draw";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_left_draw);
RDebugUtils.currentLine=1835024;
 //BA.debugLineNum = 1835024;BA.debugLine="Dim shadow_space As Int = 5dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
RDebugUtils.currentLine=1835025;
 //BA.debugLineNum = 1835025;BA.debugLine="extra.index_ob_olaviyat(flag) = 1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1835026;
 //BA.debugLineNum = 1835026;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835027;
 //BA.debugLineNum = 1835027;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 };
RDebugUtils.currentLine=1835029;
 //BA.debugLineNum = 1835029;BA.debugLine="If size=\"medium\" Then";
if ((_size).equals("medium")) { 
RDebugUtils.currentLine=1835030;
 //BA.debugLineNum = 1835030;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (4),(int) (111),(int) (11),(int) (1))) {
case 0: {
RDebugUtils.currentLine=1835032;
 //BA.debugLineNum = 1835032;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835033;
 //BA.debugLineNum = 1835033;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835034;
 //BA.debugLineNum = 1835034;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835035;
 //BA.debugLineNum = 1835035;BA.debugLine="extra.index_ob_olaviyat(flag)=224";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (224);
RDebugUtils.currentLine=1835036;
 //BA.debugLineNum = 1835036;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835037;
 //BA.debugLineNum = 1835037;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 1: {
RDebugUtils.currentLine=1835039;
 //BA.debugLineNum = 1835039;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835040;
 //BA.debugLineNum = 1835040;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835041;
 //BA.debugLineNum = 1835041;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835042;
 //BA.debugLineNum = 1835042;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835043;
 //BA.debugLineNum = 1835043;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 2: {
RDebugUtils.currentLine=1835045;
 //BA.debugLineNum = 1835045;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835046;
 //BA.debugLineNum = 1835046;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835047;
 //BA.debugLineNum = 1835047;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835048;
 //BA.debugLineNum = 1835048;BA.debugLine="extra.index_ob_olaviyat(flag)=222";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (222);
RDebugUtils.currentLine=1835049;
 //BA.debugLineNum = 1835049;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835050;
 //BA.debugLineNum = 1835050;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 3: {
RDebugUtils.currentLine=1835052;
 //BA.debugLineNum = 1835052;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835053;
 //BA.debugLineNum = 1835053;BA.debugLine="Dim width_draw As Int = 66.4%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA);
RDebugUtils.currentLine=1835054;
 //BA.debugLineNum = 1835054;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835055;
 //BA.debugLineNum = 1835055;BA.debugLine="extra.index_ob_olaviyat(flag)=22";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (22);
RDebugUtils.currentLine=1835056;
 //BA.debugLineNum = 1835056;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835057;
 //BA.debugLineNum = 1835057;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
}
;
 };
RDebugUtils.currentLine=1835060;
 //BA.debugLineNum = 1835060;BA.debugLine="If size=\"small\" Then";
if ((_size).equals("small")) { 
RDebugUtils.currentLine=1835061;
 //BA.debugLineNum = 1835061;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (22),(int) (111),(int) (11),(int) (1))) {
case 0: {
RDebugUtils.currentLine=1835063;
 //BA.debugLineNum = 1835063;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835064;
 //BA.debugLineNum = 1835064;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835065;
 //BA.debugLineNum = 1835065;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835066;
 //BA.debugLineNum = 1835066;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
RDebugUtils.currentLine=1835067;
 //BA.debugLineNum = 1835067;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1835068;
 //BA.debugLineNum = 1835068;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835069;
 //BA.debugLineNum = 1835069;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 1: {
RDebugUtils.currentLine=1835072;
 //BA.debugLineNum = 1835072;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835073;
 //BA.debugLineNum = 1835073;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835074;
 //BA.debugLineNum = 1835074;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835075;
 //BA.debugLineNum = 1835075;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
RDebugUtils.currentLine=1835076;
 //BA.debugLineNum = 1835076;BA.debugLine="extra.index_ob_olaviyat(flag)=225";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (225);
RDebugUtils.currentLine=1835077;
 //BA.debugLineNum = 1835077;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835078;
 //BA.debugLineNum = 1835078;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 2: {
RDebugUtils.currentLine=1835080;
 //BA.debugLineNum = 1835080;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835081;
 //BA.debugLineNum = 1835081;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835082;
 //BA.debugLineNum = 1835082;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835083;
 //BA.debugLineNum = 1835083;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
RDebugUtils.currentLine=1835084;
 //BA.debugLineNum = 1835084;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1835085;
 //BA.debugLineNum = 1835085;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835086;
 //BA.debugLineNum = 1835086;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 3: {
RDebugUtils.currentLine=1835088;
 //BA.debugLineNum = 1835088;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835089;
 //BA.debugLineNum = 1835089;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835090;
 //BA.debugLineNum = 1835090;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835091;
 //BA.debugLineNum = 1835091;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
RDebugUtils.currentLine=1835092;
 //BA.debugLineNum = 1835092;BA.debugLine="extra.index_ob_olaviyat(flag)=223";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (223);
RDebugUtils.currentLine=1835093;
 //BA.debugLineNum = 1835093;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835094;
 //BA.debugLineNum = 1835094;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 4: {
RDebugUtils.currentLine=1835096;
 //BA.debugLineNum = 1835096;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835097;
 //BA.debugLineNum = 1835097;BA.debugLine="Dim width_draw As Int = 33.23%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.23),mostCurrent.activityBA);
RDebugUtils.currentLine=1835098;
 //BA.debugLineNum = 1835098;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835099;
 //BA.debugLineNum = 1835099;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
RDebugUtils.currentLine=1835100;
 //BA.debugLineNum = 1835100;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1835101;
 //BA.debugLineNum = 1835101;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835102;
 //BA.debugLineNum = 1835102;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 5: {
RDebugUtils.currentLine=1835104;
 //BA.debugLineNum = 1835104;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835105;
 //BA.debugLineNum = 1835105;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835106;
 //BA.debugLineNum = 1835106;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835107;
 //BA.debugLineNum = 1835107;BA.debugLine="extra.index_ob_olaviyat(flag)=221";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (221);
RDebugUtils.currentLine=1835108;
 //BA.debugLineNum = 1835108;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835109;
 //BA.debugLineNum = 1835109;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 6: {
RDebugUtils.currentLine=1835111;
 //BA.debugLineNum = 1835111;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835112;
 //BA.debugLineNum = 1835112;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835113;
 //BA.debugLineNum = 1835113;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835114;
 //BA.debugLineNum = 1835114;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
RDebugUtils.currentLine=1835115;
 //BA.debugLineNum = 1835115;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
RDebugUtils.currentLine=1835116;
 //BA.debugLineNum = 1835116;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 7: {
RDebugUtils.currentLine=1835118;
 //BA.debugLineNum = 1835118;BA.debugLine="Dim left_draw As Int = 33.3%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.3),mostCurrent.activityBA)+_padding_space);
RDebugUtils.currentLine=1835119;
 //BA.debugLineNum = 1835119;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835120;
 //BA.debugLineNum = 1835120;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835121;
 //BA.debugLineNum = 1835121;BA.debugLine="extra.index_ob_olaviyat(flag)=111";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (111);
RDebugUtils.currentLine=1835122;
 //BA.debugLineNum = 1835122;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835123;
 //BA.debugLineNum = 1835123;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 8: {
RDebugUtils.currentLine=1835125;
 //BA.debugLineNum = 1835125;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
RDebugUtils.currentLine=1835126;
 //BA.debugLineNum = 1835126;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
RDebugUtils.currentLine=1835127;
 //BA.debugLineNum = 1835127;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1835128;
 //BA.debugLineNum = 1835128;BA.debugLine="extra.index_ob_olaviyat(flag)=11";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (11);
RDebugUtils.currentLine=1835129;
 //BA.debugLineNum = 1835129;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
RDebugUtils.currentLine=1835130;
 //BA.debugLineNum = 1835130;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
}
;
 };
RDebugUtils.currentLine=1835134;
 //BA.debugLineNum = 1835134;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
RDebugUtils.currentLine=1835137;
 //BA.debugLineNum = 1835137;BA.debugLine="lbl.Gravity = Gravity.FILL";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1835138;
 //BA.debugLineNum = 1835138;BA.debugLine="lbl.Gravity = Gravity.CENTER";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=1835139;
 //BA.debugLineNum = 1835139;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
RDebugUtils.currentLine=1835143;
 //BA.debugLineNum = 1835143;BA.debugLine="Dim imgfile As String = img.SubString2(img.LastIn";
_imgfile = _img.substring((int) (_img.lastIndexOf("/")+1),_img.length());
RDebugUtils.currentLine=1835145;
 //BA.debugLineNum = 1835145;BA.debugLine="If File.Exists (File.DirInternalCache,\"product/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product/"+_imgfile)==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=1835147;
 //BA.debugLineNum = 1835147;BA.debugLine="imgdrew(flag).Initialize(\"imgdrew\")";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].Initialize(mostCurrent.activityBA,"imgdrew");
RDebugUtils.currentLine=1835148;
 //BA.debugLineNum = 1835148;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject()));
RDebugUtils.currentLine=1835150;
 //BA.debugLineNum = 1835150;BA.debugLine="imgdrew(flag).Tag = id";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setTag((Object)(_id));
RDebugUtils.currentLine=1835151;
 //BA.debugLineNum = 1835151;BA.debugLine="imgdrew(flag).Gravity = Gravity.FILL";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1835152;
 //BA.debugLineNum = 1835152;BA.debugLine="extra.download_image(id,img,flag)";
mostCurrent._extra._download_image(mostCurrent.activityBA,_id,_img,_flag);
 }else {
RDebugUtils.currentLine=1835156;
 //BA.debugLineNum = 1835156;BA.debugLine="imgdrew(flag).Initialize(\"imgdrew\")";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].Initialize(mostCurrent.activityBA,"imgdrew");
RDebugUtils.currentLine=1835157;
 //BA.debugLineNum = 1835157;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product/"+_imgfile,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject()));
RDebugUtils.currentLine=1835158;
 //BA.debugLineNum = 1835158;BA.debugLine="imgdrew(flag).Gravity = Gravity.FILL";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1835159;
 //BA.debugLineNum = 1835159;BA.debugLine="imgdrew(flag).Tag =  id";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setTag((Object)(_id));
 };
RDebugUtils.currentLine=1835163;
 //BA.debugLineNum = 1835163;BA.debugLine="index_ScrollView.Panel.AddView(panel,left_draw,ex";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_panel.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
RDebugUtils.currentLine=1835165;
 //BA.debugLineNum = 1835165;BA.debugLine="index_ScrollView.Panel.AddView(imgdrew(flag),left";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
RDebugUtils.currentLine=1835167;
 //BA.debugLineNum = 1835167;BA.debugLine="index_ScrollView.Panel.AddView(lbl,left_draw,extr";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_lbl.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_width_draw-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25))),(int) (_width_draw-_space),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
RDebugUtils.currentLine=1835168;
 //BA.debugLineNum = 1835168;BA.debugLine="extra.index_ob_top = extra.index_ob_top + extra.i";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+mostCurrent._extra._index_ob_top_cach);
RDebugUtils.currentLine=1835169;
 //BA.debugLineNum = 1835169;BA.debugLine="index_ScrollView.Panel.Height = extra.index_ob_to";
mostCurrent._index_scrollview.getPanel().setHeight((int) (mostCurrent._extra._index_ob_top+_space));
RDebugUtils.currentLine=1835170;
 //BA.debugLineNum = 1835170;BA.debugLine="End Sub";
return "";
}
public static String  _index_scrollview_scrollchanged(int _position) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "index_scrollview_scrollchanged"))
	return (String) Debug.delegate(mostCurrent.activityBA, "index_scrollview_scrollchanged", new Object[] {_position});
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
RDebugUtils.currentLine=1638400;
 //BA.debugLineNum = 1638400;BA.debugLine="Sub index_ScrollView_ScrollChanged(Position As Int";
RDebugUtils.currentLine=1638401;
 //BA.debugLineNum = 1638401;BA.debugLine="If (Position+1950) >= extra.index_ob_top Then";
if ((_position+1950)>=mostCurrent._extra._index_ob_top) { 
RDebugUtils.currentLine=1638402;
 //BA.debugLineNum = 1638402;BA.debugLine="Log(extra.index_ob_olaviyat_load)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._extra._index_ob_olaviyat_load));
RDebugUtils.currentLine=1638403;
 //BA.debugLineNum = 1638403;BA.debugLine="extra.index_ob_olaviyat(extra.index_ob_olaviyat_";
mostCurrent._extra._index_ob_olaviyat[(int) (mostCurrent._extra._index_ob_olaviyat_load-1)] = (int) (1);
RDebugUtils.currentLine=1638404;
 //BA.debugLineNum = 1638404;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=1638405;
 //BA.debugLineNum = 1638405;BA.debugLine="job.Initialize(\"\",Me)";
_job._initialize(processBA,"",index.getObject());
RDebugUtils.currentLine=1638406;
 //BA.debugLineNum = 1638406;BA.debugLine="load_indexjob(job,False)";
_load_indexjob(_job,anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=1638409;
 //BA.debugLineNum = 1638409;BA.debugLine="End Sub";
return "";
}
public static String  _load_indexjob(anywheresoftware.b4a.samples.httputils2.httpjob _job,boolean _create) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "load_indexjob"))
	return (String) Debug.delegate(mostCurrent.activityBA, "load_indexjob", new Object[] {_job,_create});
int _lastpage = 0;
int _i = 0;
String _id = "";
String _img = "";
String _modle = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
int _x = 0;
RDebugUtils.currentLine=1703936;
 //BA.debugLineNum = 1703936;BA.debugLine="Sub load_indexjob(job As HttpJob,create As Boolean";
RDebugUtils.currentLine=1703937;
 //BA.debugLineNum = 1703937;BA.debugLine="Try";
try {RDebugUtils.currentLine=1703939;
 //BA.debugLineNum = 1703939;BA.debugLine="Dim lastpage As Int";
_lastpage = 0;
RDebugUtils.currentLine=1703940;
 //BA.debugLineNum = 1703940;BA.debugLine="Dim i As Int";
_i = 0;
RDebugUtils.currentLine=1703941;
 //BA.debugLineNum = 1703941;BA.debugLine="If create=True Then";
if (_create==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1703942;
 //BA.debugLineNum = 1703942;BA.debugLine="Dim id As String = job.GetString.SubString2(0, j";
_id = _job._getstring().substring((int) (0),_job._getstring().indexOf("*"));
RDebugUtils.currentLine=1703943;
 //BA.debugLineNum = 1703943;BA.debugLine="Dim img As String = job.GetString.SubString2(job";
_img = _job._getstring().substring((int) (_job._getstring().indexOf("*")+3),_job._getstring().indexOf("$"));
RDebugUtils.currentLine=1703944;
 //BA.debugLineNum = 1703944;BA.debugLine="Dim modle As String = job.GetString.SubString2(j";
_modle = _job._getstring().substring((int) (_job._getstring().indexOf("$")+3),_job._getstring().length());
RDebugUtils.currentLine=1703948;
 //BA.debugLineNum = 1703948;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=1703949;
 //BA.debugLineNum = 1703949;BA.debugLine="parser.Initialize(id)";
_parser.Initialize(_id);
RDebugUtils.currentLine=1703950;
 //BA.debugLineNum = 1703950;BA.debugLine="index_retrive_list= parser.NextArray";
mostCurrent._index_retrive_list = _parser.NextArray();
RDebugUtils.currentLine=1703951;
 //BA.debugLineNum = 1703951;BA.debugLine="parser.Initialize(img)";
_parser.Initialize(_img);
RDebugUtils.currentLine=1703952;
 //BA.debugLineNum = 1703952;BA.debugLine="index_retrive_list_img= parser.NextArray";
mostCurrent._index_retrive_list_img = _parser.NextArray();
RDebugUtils.currentLine=1703953;
 //BA.debugLineNum = 1703953;BA.debugLine="parser.Initialize(modle)";
_parser.Initialize(_modle);
RDebugUtils.currentLine=1703954;
 //BA.debugLineNum = 1703954;BA.debugLine="index_retrive_list_model= parser.NextArray";
mostCurrent._index_retrive_list_model = _parser.NextArray();
RDebugUtils.currentLine=1703955;
 //BA.debugLineNum = 1703955;BA.debugLine="i = 1";
_i = (int) (1);
RDebugUtils.currentLine=1703956;
 //BA.debugLineNum = 1703956;BA.debugLine="lastpage = 20";
_lastpage = (int) (20);
 }else {
RDebugUtils.currentLine=1703958;
 //BA.debugLineNum = 1703958;BA.debugLine="i = extra.index_ob_olaviyat_load";
_i = mostCurrent._extra._index_ob_olaviyat_load;
RDebugUtils.currentLine=1703959;
 //BA.debugLineNum = 1703959;BA.debugLine="lastpage = extra.index_ob_olaviyat_load + 20";
_lastpage = (int) (mostCurrent._extra._index_ob_olaviyat_load+20);
 };
RDebugUtils.currentLine=1703962;
 //BA.debugLineNum = 1703962;BA.debugLine="do  While (i<lastpage Or  extra.index_ob_olaviyat";
while ((_i<_lastpage || mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)]!=1)) {
RDebugUtils.currentLine=1703965;
 //BA.debugLineNum = 1703965;BA.debugLine="Select extra.index_ob_olaviyat(i-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)],(int) (1),(int) (22),(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (11),(int) (111))) {
case 0: {
RDebugUtils.currentLine=1703967;
 //BA.debugLineNum = 1703967;BA.debugLine="Dim x As Int = Rnd(1,5)";
_x = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
RDebugUtils.currentLine=1703969;
 //BA.debugLineNum = 1703969;BA.debugLine="If x = 1 Then";
if (_x==1) { 
RDebugUtils.currentLine=1703970;
 //BA.debugLineNum = 1703970;BA.debugLine="index_draw(\"larg\",i,index_retrive_list.Get(i)";
_index_draw("larg",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
RDebugUtils.currentLine=1703973;
 //BA.debugLineNum = 1703973;BA.debugLine="If x = 2 Then";
if (_x==2) { 
RDebugUtils.currentLine=1703974;
 //BA.debugLineNum = 1703974;BA.debugLine="index_draw(\"medium\",i,index_retrive_list.Get(";
_index_draw("medium",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
RDebugUtils.currentLine=1703977;
 //BA.debugLineNum = 1703977;BA.debugLine="If x = 3 Then";
if (_x==3) { 
RDebugUtils.currentLine=1703978;
 //BA.debugLineNum = 1703978;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
RDebugUtils.currentLine=1703981;
 //BA.debugLineNum = 1703981;BA.debugLine="If x = 4 Then";
if (_x==4) { 
RDebugUtils.currentLine=1703982;
 //BA.debugLineNum = 1703982;BA.debugLine="extra.index_ob_olaviyat(i-1) = 4";
mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)] = (int) (4);
RDebugUtils.currentLine=1703983;
 //BA.debugLineNum = 1703983;BA.debugLine="index_draw(\"medium\",i,index_retrive_list.Get(";
_index_draw("medium",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 break; }
case 1: {
RDebugUtils.currentLine=1703987;
 //BA.debugLineNum = 1703987;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 2: {
RDebugUtils.currentLine=1703989;
 //BA.debugLineNum = 1703989;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 3: {
RDebugUtils.currentLine=1703992;
 //BA.debugLineNum = 1703992;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 4: {
RDebugUtils.currentLine=1703994;
 //BA.debugLineNum = 1703994;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 5: {
RDebugUtils.currentLine=1703996;
 //BA.debugLineNum = 1703996;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 6: {
RDebugUtils.currentLine=1703998;
 //BA.debugLineNum = 1703998;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 7: {
RDebugUtils.currentLine=1704000;
 //BA.debugLineNum = 1704000;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 8: {
RDebugUtils.currentLine=1704005;
 //BA.debugLineNum = 1704005;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
}
;
RDebugUtils.currentLine=1704007;
 //BA.debugLineNum = 1704007;BA.debugLine="i=i+1";
_i = (int) (_i+1);
 }
;
 } 
       catch (Exception e58) {
			processBA.setLastException(e58);RDebugUtils.currentLine=1704011;
 //BA.debugLineNum = 1704011;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
RDebugUtils.currentLine=1704013;
 //BA.debugLineNum = 1704013;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "jobdone"))
	return (String) Debug.delegate(mostCurrent.activityBA, "jobdone", new Object[] {_job});
String _id = "";
String _img = "";
String _flag = "";
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outstream = null;
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
int _flagerpic = 0;
int _flagpanel = 0;
de.amberhome.viewpager.AHPageContainer _container = null;
de.amberhome.viewpager.AHViewPager _pager = null;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
String _image = "";
String _filename = "";
int _flager = 0;
String _s = "";
anywheresoftware.b4a.objects.LabelWrapper _rtljustify1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
int _index = 0;
String _name = "";
String _d1 = "";
anywheresoftware.b4a.objects.LabelWrapper _propert = null;
String _price = "";
String _model = "";
anywheresoftware.b4a.objects.LabelWrapper _lbl_titlen = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_title = null;
anywheresoftware.b4a.objects.LabelWrapper _total = null;
int _left = 0;
String _text = "";
anywheresoftware.b4a.objects.LabelWrapper _lable = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _indexf = 0;
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
anywheresoftware.b4a.objects.LabelWrapper _lable2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvasgraph = null;
RDebugUtils.currentLine=1769472;
 //BA.debugLineNum = 1769472;BA.debugLine="Sub jobdone(job As HttpJob)";
RDebugUtils.currentLine=1769474;
 //BA.debugLineNum = 1769474;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1769475;
 //BA.debugLineNum = 1769475;BA.debugLine="Select job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"load_indexjob")) {
case 0: {
RDebugUtils.currentLine=1769477;
 //BA.debugLineNum = 1769477;BA.debugLine="load_indexjob(job,True)";
_load_indexjob(_job,anywheresoftware.b4a.keywords.Common.True);
 break; }
default: {
RDebugUtils.currentLine=1769479;
 //BA.debugLineNum = 1769479;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769480;
 //BA.debugLineNum = 1769480;BA.debugLine="If job.JobName.SubString2(0,13)=\"downloadimag";
if ((_job._jobname.substring((int) (0),(int) (13))).equals("downloadimage")) { 
RDebugUtils.currentLine=1769481;
 //BA.debugLineNum = 1769481;BA.debugLine="Dim id As String = job.JobName.SubString2(jo";
_id = _job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.indexOf("$"));
RDebugUtils.currentLine=1769482;
 //BA.debugLineNum = 1769482;BA.debugLine="Dim img As String = job.JobName.SubString2(j";
_img = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1),_job._jobname.lastIndexOf("#"));
RDebugUtils.currentLine=1769483;
 //BA.debugLineNum = 1769483;BA.debugLine="Dim flag As String = job.JobName.SubString2(";
_flag = _job._jobname.substring((int) (_job._jobname.lastIndexOf("#")+1),_job._jobname.length());
RDebugUtils.currentLine=1769484;
 //BA.debugLineNum = 1769484;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=1769485;
 //BA.debugLineNum = 1769485;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product",_img,anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1769486;
 //BA.debugLineNum = 1769486;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
RDebugUtils.currentLine=1769487;
 //BA.debugLineNum = 1769487;BA.debugLine="OutStream.Close";
_outstream.Close();
RDebugUtils.currentLine=1769488;
 //BA.debugLineNum = 1769488;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product",_img,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200))).getObject()));
 };
 } 
       catch (Exception e18) {
			processBA.setLastException(e18); };
RDebugUtils.currentLine=1769494;
 //BA.debugLineNum = 1769494;BA.debugLine="If job.JobName.SubString2(0,7) = \"picproc\" The";
if ((_job._jobname.substring((int) (0),(int) (7))).equals("picproc")) { 
RDebugUtils.currentLine=1769495;
 //BA.debugLineNum = 1769495;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
RDebugUtils.currentLine=1769497;
 //BA.debugLineNum = 1769497;BA.debugLine="Dim pan(20) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (20)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
RDebugUtils.currentLine=1769498;
 //BA.debugLineNum = 1769498;BA.debugLine="Dim flagerpic,flagpanel As Int";
_flagerpic = 0;
_flagpanel = 0;
RDebugUtils.currentLine=1769499;
 //BA.debugLineNum = 1769499;BA.debugLine="flagpanel = 0";
_flagpanel = (int) (0);
RDebugUtils.currentLine=1769500;
 //BA.debugLineNum = 1769500;BA.debugLine="flagerpic =  job.JobName.SubString(7)";
_flagerpic = (int)(Double.parseDouble(_job._jobname.substring((int) (7))));
RDebugUtils.currentLine=1769502;
 //BA.debugLineNum = 1769502;BA.debugLine="Dim Container As AHPageContainer";
_container = new de.amberhome.viewpager.AHPageContainer();
RDebugUtils.currentLine=1769503;
 //BA.debugLineNum = 1769503;BA.debugLine="Dim Pager As AHViewPager";
_pager = new de.amberhome.viewpager.AHViewPager();
RDebugUtils.currentLine=1769504;
 //BA.debugLineNum = 1769504;BA.debugLine="Container.Initialize";
_container.Initialize(mostCurrent.activityBA);
RDebugUtils.currentLine=1769506;
 //BA.debugLineNum = 1769506;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769509;
 //BA.debugLineNum = 1769509;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=1769510;
 //BA.debugLineNum = 1769510;BA.debugLine="parser.Initialize( job.GetString)";
_parser.Initialize(_job._getstring());
RDebugUtils.currentLine=1769511;
 //BA.debugLineNum = 1769511;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=1769512;
 //BA.debugLineNum = 1769512;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group32 = _root;
final int groupLen32 = group32.getSize()
;int index32 = 0;
;
for (; index32 < groupLen32;index32++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group32.Get(index32)));
RDebugUtils.currentLine=1769513;
 //BA.debugLineNum = 1769513;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
RDebugUtils.currentLine=1769514;
 //BA.debugLineNum = 1769514;BA.debugLine="image = extra.image_address & image.Replace";
_image = mostCurrent._extra._image_address+_image.replace(".jpg","-600x600.jpg");
RDebugUtils.currentLine=1769515;
 //BA.debugLineNum = 1769515;BA.debugLine="Dim filename  As String = image.SubString(i";
_filename = _image.substring((int) (_image.lastIndexOf("/")+1));
RDebugUtils.currentLine=1769516;
 //BA.debugLineNum = 1769516;BA.debugLine="pan(flagpanel).Initialize(\"\")";
_pan[_flagpanel].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1769518;
 //BA.debugLineNum = 1769518;BA.debugLine="procimage(extra.procimg_flag).Initialize(\"\"";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1769519;
 //BA.debugLineNum = 1769519;BA.debugLine="If File.Exists(File.DirInternalCache,filenam";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename)==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1769521;
 //BA.debugLineNum = 1769521;BA.debugLine="procimage(extra.procimg_flag).Bitmap = Loa";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename).getObject()));
 }else {
RDebugUtils.currentLine=1769523;
 //BA.debugLineNum = 1769523;BA.debugLine="extra.main_download_product(extra.procimg_";
mostCurrent._extra._main_download_product(mostCurrent.activityBA,BA.NumberToString(mostCurrent._extra._procimg_flag),_image);
RDebugUtils.currentLine=1769524;
 //BA.debugLineNum = 1769524;BA.debugLine="procimage(extra.procimg_flag).Bitmap = Loa";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"main_defult_product.jpg").getObject()));
 };
RDebugUtils.currentLine=1769526;
 //BA.debugLineNum = 1769526;BA.debugLine="procimage(extra.procimg_flag).Gravity = Gra";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1769527;
 //BA.debugLineNum = 1769527;BA.debugLine="pan(flagpanel).AddView(procimage(extra.proc";
_pan[_flagpanel].AddView((android.view.View)(mostCurrent._procimage[mostCurrent._extra._procimg_flag].getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
RDebugUtils.currentLine=1769528;
 //BA.debugLineNum = 1769528;BA.debugLine="Container.AddPageAt(pan(flagpanel), \"Main\",";
_container.AddPageAt((android.view.View)(_pan[_flagpanel].getObject()),"Main",(int) (0));
RDebugUtils.currentLine=1769529;
 //BA.debugLineNum = 1769529;BA.debugLine="flagpanel = flagpanel + 1";
_flagpanel = (int) (_flagpanel+1);
RDebugUtils.currentLine=1769530;
 //BA.debugLineNum = 1769530;BA.debugLine="extra.procimg_flag = extra.procimg_flag + 1";
mostCurrent._extra._procimg_flag = (int) (mostCurrent._extra._procimg_flag+1);
 }
};
RDebugUtils.currentLine=1769533;
 //BA.debugLineNum = 1769533;BA.debugLine="Log(\"indexer \" & extra.procimg_flag )";
anywheresoftware.b4a.keywords.Common.Log("indexer "+BA.NumberToString(mostCurrent._extra._procimg_flag));
RDebugUtils.currentLine=1769534;
 //BA.debugLineNum = 1769534;BA.debugLine="extra.procimg_count(extra.flag_procpnl) = fl";
mostCurrent._extra._procimg_count[mostCurrent._extra._flag_procpnl] = _flagpanel;
RDebugUtils.currentLine=1769536;
 //BA.debugLineNum = 1769536;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
_pager.Initialize2(mostCurrent.activityBA,_container,"Pager");
RDebugUtils.currentLine=1769537;
 //BA.debugLineNum = 1769537;BA.debugLine="product_ScrollView(flagerpic).Panel.AddView(P";
mostCurrent._product_scrollview[_flagerpic].getPanel().AddView((android.view.View)(_pager.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 } 
       catch (Exception e55) {
			processBA.setLastException(e55);RDebugUtils.currentLine=1769539;
 //BA.debugLineNum = 1769539;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 };
RDebugUtils.currentLine=1769542;
 //BA.debugLineNum = 1769542;BA.debugLine="Try";
try { } 
       catch (Exception e60) {
			processBA.setLastException(e60);RDebugUtils.currentLine=1769545;
 //BA.debugLineNum = 1769545;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
RDebugUtils.currentLine=1769547;
 //BA.debugLineNum = 1769547;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769548;
 //BA.debugLineNum = 1769548;BA.debugLine="If job.JobName.SubString2(0,8) = \"textproc\" T";
if ((_job._jobname.substring((int) (0),(int) (8))).equals("textproc")) { 
RDebugUtils.currentLine=1769549;
 //BA.debugLineNum = 1769549;BA.debugLine="Dim flager As Int";
_flager = 0;
RDebugUtils.currentLine=1769550;
 //BA.debugLineNum = 1769550;BA.debugLine="flager =  job.JobName.SubString(8)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (8))));
RDebugUtils.currentLine=1769551;
 //BA.debugLineNum = 1769551;BA.debugLine="Dim s As String";
_s = "";
RDebugUtils.currentLine=1769552;
 //BA.debugLineNum = 1769552;BA.debugLine="s = job.GetString.Replace(\"&lt;\",\"\").Replace";
_s = _job._getstring().replace("&lt;","").replace("p&gt;","").replace("br&gt;","").replace("/&lt;","").replace("/p&gt;","").replace("/br&gt;","").replace("p style=&quot;text-align: justify; &quot;&gt;","");
RDebugUtils.currentLine=1769553;
 //BA.debugLineNum = 1769553;BA.debugLine="s =s.Replace(\"amp;\",\"\").Replace(\"nbsp;\",\"\").";
_s = _s.replace("amp;","").replace("nbsp;","").replace("/null","");
RDebugUtils.currentLine=1769555;
 //BA.debugLineNum = 1769555;BA.debugLine="Dim RTLJustify1 As Label";
_rtljustify1 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769556;
 //BA.debugLineNum = 1769556;BA.debugLine="RTLJustify1.Initialize(\"\")";
_rtljustify1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1769557;
 //BA.debugLineNum = 1769557;BA.debugLine="RTLJustify1.Text = s";
_rtljustify1.setText(BA.ObjectToCharSequence(_s));
RDebugUtils.currentLine=1769558;
 //BA.debugLineNum = 1769558;BA.debugLine="RTLJustify1.Typeface = Typeface.LoadFromAsse";
_rtljustify1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769560;
 //BA.debugLineNum = 1769560;BA.debugLine="RTLJustify1.TextColor = Colors.rgb(89, 89, 8";
_rtljustify1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (89),(int) (89),(int) (89)));
RDebugUtils.currentLine=1769561;
 //BA.debugLineNum = 1769561;BA.debugLine="RTLJustify1.Gravity = Gravity.FILL";
_rtljustify1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1769562;
 //BA.debugLineNum = 1769562;BA.debugLine="RTLJustify1.TextSize = 10dip";
_rtljustify1.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=1769564;
 //BA.debugLineNum = 1769564;BA.debugLine="Dim Obj1 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=1769565;
 //BA.debugLineNum = 1769565;BA.debugLine="Obj1.Target = RTLJustify1";
_obj1.Target = (Object)(_rtljustify1.getObject());
RDebugUtils.currentLine=1769566;
 //BA.debugLineNum = 1769566;BA.debugLine="Obj1.RunMethod3(\"setLineSpacing\", 1, \"java.l";
_obj1.RunMethod3("setLineSpacing",BA.NumberToString(1),"java.lang.float",BA.NumberToString(1.5),"java.lang.float");
RDebugUtils.currentLine=1769567;
 //BA.debugLineNum = 1769567;BA.debugLine="product_ScrollView(flager).Panel.AddView(RTL";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_rtljustify1.getObject()),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185)));
 };
 } 
       catch (Exception e82) {
			processBA.setLastException(e82);RDebugUtils.currentLine=1769571;
 //BA.debugLineNum = 1769571;BA.debugLine="Log(\"erro textproc job\")";
anywheresoftware.b4a.keywords.Common.Log("erro textproc job");
 };
RDebugUtils.currentLine=1769574;
 //BA.debugLineNum = 1769574;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769575;
 //BA.debugLineNum = 1769575;BA.debugLine="If job.JobName.SubString2(0,18)=\"downloadimgp";
if ((_job._jobname.substring((int) (0),(int) (18))).equals("downloadimgproduct")) { 
RDebugUtils.currentLine=1769577;
 //BA.debugLineNum = 1769577;BA.debugLine="Dim index As Int";
_index = 0;
RDebugUtils.currentLine=1769578;
 //BA.debugLineNum = 1769578;BA.debugLine="Dim name As String";
_name = "";
RDebugUtils.currentLine=1769579;
 //BA.debugLineNum = 1769579;BA.debugLine="index = job.JobName.SubString2(job.JobName.I";
_index = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
RDebugUtils.currentLine=1769580;
 //BA.debugLineNum = 1769580;BA.debugLine="name = job.JobName.SubString(job.JobName.Las";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
RDebugUtils.currentLine=1769581;
 //BA.debugLineNum = 1769581;BA.debugLine="Log(index)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_index));
RDebugUtils.currentLine=1769582;
 //BA.debugLineNum = 1769582;BA.debugLine="Log(name)";
anywheresoftware.b4a.keywords.Common.Log(_name);
RDebugUtils.currentLine=1769583;
 //BA.debugLineNum = 1769583;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=1769584;
 //BA.debugLineNum = 1769584;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1769585;
 //BA.debugLineNum = 1769585;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
RDebugUtils.currentLine=1769586;
 //BA.debugLineNum = 1769586;BA.debugLine="OutStream.Close";
_outstream.Close();
RDebugUtils.currentLine=1769587;
 //BA.debugLineNum = 1769587;BA.debugLine="procimage(index).Bitmap = LoadBitmap(File.Di";
mostCurrent._procimage[_index].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e99) {
			processBA.setLastException(e99);RDebugUtils.currentLine=1769590;
 //BA.debugLineNum = 1769590;BA.debugLine="Log(\"error downloadimgproduct-- job\")";
anywheresoftware.b4a.keywords.Common.Log("error downloadimgproduct-- job");
 };
RDebugUtils.currentLine=1769593;
 //BA.debugLineNum = 1769593;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769594;
 //BA.debugLineNum = 1769594;BA.debugLine="If job.JobName.SubString2(0,19)=\"downloadimgl";
if ((_job._jobname.substring((int) (0),(int) (19))).equals("downloadimglastproc")) { 
RDebugUtils.currentLine=1769595;
 //BA.debugLineNum = 1769595;BA.debugLine="Dim index As Int";
_index = 0;
RDebugUtils.currentLine=1769596;
 //BA.debugLineNum = 1769596;BA.debugLine="Dim name As String";
_name = "";
RDebugUtils.currentLine=1769597;
 //BA.debugLineNum = 1769597;BA.debugLine="index = job.JobName.SubString2(job.JobName.I";
_index = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
RDebugUtils.currentLine=1769598;
 //BA.debugLineNum = 1769598;BA.debugLine="name = job.JobName.SubString(job.JobName.Las";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
RDebugUtils.currentLine=1769599;
 //BA.debugLineNum = 1769599;BA.debugLine="Log(index)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_index));
RDebugUtils.currentLine=1769600;
 //BA.debugLineNum = 1769600;BA.debugLine="Log(name)";
anywheresoftware.b4a.keywords.Common.Log(_name);
RDebugUtils.currentLine=1769601;
 //BA.debugLineNum = 1769601;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=1769602;
 //BA.debugLineNum = 1769602;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=1769603;
 //BA.debugLineNum = 1769603;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
RDebugUtils.currentLine=1769604;
 //BA.debugLineNum = 1769604;BA.debugLine="OutStream.Close";
_outstream.Close();
RDebugUtils.currentLine=1769605;
 //BA.debugLineNum = 1769605;BA.debugLine="lastproduct_ImageView(index).Bitmap = LoadBi";
mostCurrent._lastproduct_imageview[_index].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e116) {
			processBA.setLastException(e116);RDebugUtils.currentLine=1769608;
 //BA.debugLineNum = 1769608;BA.debugLine="Log(\"error downloadimglastproc job\")";
anywheresoftware.b4a.keywords.Common.Log("error downloadimglastproc job");
 };
RDebugUtils.currentLine=1769610;
 //BA.debugLineNum = 1769610;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769612;
 //BA.debugLineNum = 1769612;BA.debugLine="If job.JobName.SubString2(0,8)=\"nameproc\" The";
if ((_job._jobname.substring((int) (0),(int) (8))).equals("nameproc")) { 
RDebugUtils.currentLine=1769615;
 //BA.debugLineNum = 1769615;BA.debugLine="Dim flager As Int";
_flager = 0;
RDebugUtils.currentLine=1769616;
 //BA.debugLineNum = 1769616;BA.debugLine="flager =  job.JobName.SubString(8)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (8))));
RDebugUtils.currentLine=1769618;
 //BA.debugLineNum = 1769618;BA.debugLine="Dim d1 As String";
_d1 = "";
RDebugUtils.currentLine=1769619;
 //BA.debugLineNum = 1769619;BA.debugLine="d1 = job.GetString.SubString2(0,job.GetStrin";
_d1 = _job._getstring().substring((int) (0),_job._getstring().indexOf("^"));
RDebugUtils.currentLine=1769622;
 //BA.debugLineNum = 1769622;BA.debugLine="Dim propert As Label";
_propert = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769623;
 //BA.debugLineNum = 1769623;BA.debugLine="propert.Initialize(\"propert\")";
_propert.Initialize(mostCurrent.activityBA,"propert");
RDebugUtils.currentLine=1769624;
 //BA.debugLineNum = 1769624;BA.debugLine="propert.TextColor = Colors.Black";
_propert.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=1769625;
 //BA.debugLineNum = 1769625;BA.debugLine="propert.Gravity = Gravity.CENTER";
_propert.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=1769626;
 //BA.debugLineNum = 1769626;BA.debugLine="propert.Typeface = Typeface.LoadFromAssets(\"";
_propert.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769627;
 //BA.debugLineNum = 1769627;BA.debugLine="propert.Text = \"مشخصات\"";
_propert.setText(BA.ObjectToCharSequence("مشخصات"));
RDebugUtils.currentLine=1769628;
 //BA.debugLineNum = 1769628;BA.debugLine="propert.Tag = job.GetString.SubString2(job.G";
_propert.setTag((Object)(_job._getstring().substring((int) (_job._getstring().indexOf("^")+1),_job._getstring().length())+"#"+BA.NumberToString(_flager)));
RDebugUtils.currentLine=1769629;
 //BA.debugLineNum = 1769629;BA.debugLine="product_ScrollView(flager).Panel.AddView(pro";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_propert.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=1769632;
 //BA.debugLineNum = 1769632;BA.debugLine="Dim s As String";
_s = "";
RDebugUtils.currentLine=1769633;
 //BA.debugLineNum = 1769633;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=1769635;
 //BA.debugLineNum = 1769635;BA.debugLine="parser.Initialize(d1)";
_parser.Initialize(_d1);
RDebugUtils.currentLine=1769636;
 //BA.debugLineNum = 1769636;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=1769637;
 //BA.debugLineNum = 1769637;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group136 = _root;
final int groupLen136 = group136.getSize()
;int index136 = 0;
;
for (; index136 < groupLen136;index136++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group136.Get(index136)));
RDebugUtils.currentLine=1769638;
 //BA.debugLineNum = 1769638;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
RDebugUtils.currentLine=1769639;
 //BA.debugLineNum = 1769639;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=1769640;
 //BA.debugLineNum = 1769640;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
 }
};
RDebugUtils.currentLine=1769648;
 //BA.debugLineNum = 1769648;BA.debugLine="Dim lbl_titlen As Label";
_lbl_titlen = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769649;
 //BA.debugLineNum = 1769649;BA.debugLine="lbl_titlen.Initialize(\"\")";
_lbl_titlen.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1769650;
 //BA.debugLineNum = 1769650;BA.debugLine="lbl_titlen.Text = model";
_lbl_titlen.setText(BA.ObjectToCharSequence(_model));
RDebugUtils.currentLine=1769651;
 //BA.debugLineNum = 1769651;BA.debugLine="lbl_titlen.Typeface = Typeface.LoadFromAsset";
_lbl_titlen.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769652;
 //BA.debugLineNum = 1769652;BA.debugLine="lbl_titlen.TextSize = 8dip";
_lbl_titlen.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
RDebugUtils.currentLine=1769653;
 //BA.debugLineNum = 1769653;BA.debugLine="lbl_titlen.Gravity = Gravity.RIGHT";
_lbl_titlen.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1769654;
 //BA.debugLineNum = 1769654;BA.debugLine="lbl_titlen.TextColor = Colors.rgb(169, 169,";
_lbl_titlen.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (169),(int) (169),(int) (169)));
RDebugUtils.currentLine=1769655;
 //BA.debugLineNum = 1769655;BA.debugLine="product_ScrollView(flager).Panel.AddView(lbl";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_lbl_titlen.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=1769659;
 //BA.debugLineNum = 1769659;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
RDebugUtils.currentLine=1769660;
 //BA.debugLineNum = 1769660;BA.debugLine="Dim lbl_title As Label";
_lbl_title = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769661;
 //BA.debugLineNum = 1769661;BA.debugLine="lbl_title.Initialize(\"\")";
_lbl_title.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1769662;
 //BA.debugLineNum = 1769662;BA.debugLine="lbl_title.Text =  \"\"";
_lbl_title.setText(BA.ObjectToCharSequence(""));
RDebugUtils.currentLine=1769663;
 //BA.debugLineNum = 1769663;BA.debugLine="extra.product_title =  \"\"";
mostCurrent._extra._product_title = "";
RDebugUtils.currentLine=1769664;
 //BA.debugLineNum = 1769664;BA.debugLine="lbl_title.Typeface = Typeface.LoadFromAssets";
_lbl_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769665;
 //BA.debugLineNum = 1769665;BA.debugLine="lbl_title.TextSize = 11dip";
_lbl_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
RDebugUtils.currentLine=1769666;
 //BA.debugLineNum = 1769666;BA.debugLine="lbl_title.Gravity = Gravity.RIGHT";
_lbl_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1769667;
 //BA.debugLineNum = 1769667;BA.debugLine="lbl_title.TextColor = Colors.Black";
_lbl_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=1769668;
 //BA.debugLineNum = 1769668;BA.debugLine="product_ScrollView(flager).Panel.AddView(lbl";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_lbl_title.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (85))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=1769669;
 //BA.debugLineNum = 1769669;BA.debugLine="lbl_title.Text = name";
_lbl_title.setText(BA.ObjectToCharSequence(_name));
RDebugUtils.currentLine=1769671;
 //BA.debugLineNum = 1769671;BA.debugLine="headerproctxt(flager).Text =name";
mostCurrent._headerproctxt[_flager].setText(BA.ObjectToCharSequence(_name));
RDebugUtils.currentLine=1769672;
 //BA.debugLineNum = 1769672;BA.debugLine="Dim total As Label";
_total = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769673;
 //BA.debugLineNum = 1769673;BA.debugLine="total.Initialize(\"\")";
_total.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=1769674;
 //BA.debugLineNum = 1769674;BA.debugLine="total.TextColor = Colors.rgb(102, 187, 106)";
_total.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=1769675;
 //BA.debugLineNum = 1769675;BA.debugLine="total.Gravity = Gravity.LEFT";
_total.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
RDebugUtils.currentLine=1769676;
 //BA.debugLineNum = 1769676;BA.debugLine="total.Typeface = Typeface.LoadFromAssets(\"ye";
_total.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769677;
 //BA.debugLineNum = 1769677;BA.debugLine="total.Text = price.SubString2(0,price.LastIn";
_total.setText(BA.ObjectToCharSequence(_price.substring((int) (0),_price.lastIndexOf("."))+" "+"تومان"));
RDebugUtils.currentLine=1769678;
 //BA.debugLineNum = 1769678;BA.debugLine="total.TextSize = 13dip";
_total.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
RDebugUtils.currentLine=1769679;
 //BA.debugLineNum = 1769679;BA.debugLine="product_ScrollView(flager).Panel.AddView(tot";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_total.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (178))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 } 
       catch (Exception e171) {
			processBA.setLastException(e171);RDebugUtils.currentLine=1769692;
 //BA.debugLineNum = 1769692;BA.debugLine="Log(\"erro nameproc job\")";
anywheresoftware.b4a.keywords.Common.Log("erro nameproc job");
 };
RDebugUtils.currentLine=1769695;
 //BA.debugLineNum = 1769695;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769696;
 //BA.debugLineNum = 1769696;BA.debugLine="If job.JobName.SubString2(0,12) =\"loadcategor";
if ((_job._jobname.substring((int) (0),(int) (12))).equals("loadcategory")) { 
RDebugUtils.currentLine=1769697;
 //BA.debugLineNum = 1769697;BA.debugLine="Dim flager As Int";
_flager = 0;
RDebugUtils.currentLine=1769698;
 //BA.debugLineNum = 1769698;BA.debugLine="flager =  job.JobName.SubString(12)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (12))));
RDebugUtils.currentLine=1769700;
 //BA.debugLineNum = 1769700;BA.debugLine="category_hscrollview.Initialize(100%x,\"\")";
mostCurrent._category_hscrollview.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
RDebugUtils.currentLine=1769701;
 //BA.debugLineNum = 1769701;BA.debugLine="category_hscrollview.Panel.Height = 50dip";
mostCurrent._category_hscrollview.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1769702;
 //BA.debugLineNum = 1769702;BA.debugLine="product_ScrollView(flager).Panel.AddView(cat";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(mostCurrent._category_hscrollview.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=1769704;
 //BA.debugLineNum = 1769704;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1769705;
 //BA.debugLineNum = 1769705;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=1769706;
 //BA.debugLineNum = 1769706;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
RDebugUtils.currentLine=1769707;
 //BA.debugLineNum = 1769707;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=1769708;
 //BA.debugLineNum = 1769708;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group184 = _root;
final int groupLen184 = group184.getSize()
;int index184 = 0;
;
for (; index184 < groupLen184;index184++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group184.Get(index184)));
RDebugUtils.currentLine=1769709;
 //BA.debugLineNum = 1769709;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
RDebugUtils.currentLine=1769710;
 //BA.debugLineNum = 1769710;BA.debugLine="Dim text As String = colroot.Get(\"name\")";
_text = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=1769711;
 //BA.debugLineNum = 1769711;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769712;
 //BA.debugLineNum = 1769712;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
RDebugUtils.currentLine=1769713;
 //BA.debugLineNum = 1769713;BA.debugLine="lable.Color = Colors.rgb(102, 187, 106)";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=1769714;
 //BA.debugLineNum = 1769714;BA.debugLine="lable.TextColor = Colors.White";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1769715;
 //BA.debugLineNum = 1769715;BA.debugLine="lable.Gravity = Gravity.CENTER";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=1769716;
 //BA.debugLineNum = 1769716;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769717;
 //BA.debugLineNum = 1769717;BA.debugLine="lable.TextSize = \"20\"";
_lable.setTextSize((float)(Double.parseDouble("20")));
RDebugUtils.currentLine=1769718;
 //BA.debugLineNum = 1769718;BA.debugLine="lable.Text = text";
_lable.setText(BA.ObjectToCharSequence(_text));
RDebugUtils.currentLine=1769719;
 //BA.debugLineNum = 1769719;BA.debugLine="category_hscrollview.Panel.AddView(lable,l";
mostCurrent._category_hscrollview.getPanel().AddView((android.view.View)(_lable.getObject()),_left,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2.5)),(int) ((_text.length()*30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
RDebugUtils.currentLine=1769720;
 //BA.debugLineNum = 1769720;BA.debugLine="left =( text.Length * 30  ) + left +8dip";
_left = (int) ((_text.length()*30)+_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8)));
 }
};
RDebugUtils.currentLine=1769722;
 //BA.debugLineNum = 1769722;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=1769723;
 //BA.debugLineNum = 1769723;BA.debugLine="r.Target = category_hscrollview";
_r.Target = (Object)(mostCurrent._category_hscrollview.getObject());
RDebugUtils.currentLine=1769724;
 //BA.debugLineNum = 1769724;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
RDebugUtils.currentLine=1769725;
 //BA.debugLineNum = 1769725;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
RDebugUtils.currentLine=1769727;
 //BA.debugLineNum = 1769727;BA.debugLine="category_hscrollview.Panel.Width = left";
mostCurrent._category_hscrollview.getPanel().setWidth(_left);
RDebugUtils.currentLine=1769728;
 //BA.debugLineNum = 1769728;BA.debugLine="category_hscrollview.FullScroll(True)";
mostCurrent._category_hscrollview.FullScroll(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=1769729;
 //BA.debugLineNum = 1769729;BA.debugLine="category_hscrollview.ScrollPosition = 50dip";
mostCurrent._category_hscrollview.setScrollPosition(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 };
RDebugUtils.currentLine=1769731;
 //BA.debugLineNum = 1769731;BA.debugLine="If job.JobName.SubString2(0,11) =\"lastproduct";
if ((_job._jobname.substring((int) (0),(int) (11))).equals("lastproduct")) { 
RDebugUtils.currentLine=1769732;
 //BA.debugLineNum = 1769732;BA.debugLine="Dim flager As Int";
_flager = 0;
RDebugUtils.currentLine=1769733;
 //BA.debugLineNum = 1769733;BA.debugLine="flager =  job.JobName.SubString(11)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (11))));
RDebugUtils.currentLine=1769735;
 //BA.debugLineNum = 1769735;BA.debugLine="scrollview_lastproduct.Initialize(100%x,\"\")";
mostCurrent._scrollview_lastproduct.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
RDebugUtils.currentLine=1769736;
 //BA.debugLineNum = 1769736;BA.debugLine="scrollview_lastproduct.Panel.Height = 450dip";
mostCurrent._scrollview_lastproduct.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
RDebugUtils.currentLine=1769737;
 //BA.debugLineNum = 1769737;BA.debugLine="product_ScrollView(flager).Panel.AddView(scr";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(mostCurrent._scrollview_lastproduct.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
RDebugUtils.currentLine=1769739;
 //BA.debugLineNum = 1769739;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=1769740;
 //BA.debugLineNum = 1769740;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=1769741;
 //BA.debugLineNum = 1769741;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
RDebugUtils.currentLine=1769742;
 //BA.debugLineNum = 1769742;BA.debugLine="Dim indexf As Int = flager &  1";
_indexf = (int)(Double.parseDouble(BA.NumberToString(_flager)+BA.NumberToString(1)));
RDebugUtils.currentLine=1769743;
 //BA.debugLineNum = 1769743;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=1769744;
 //BA.debugLineNum = 1769744;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group217 = _root;
final int groupLen217 = group217.getSize()
;int index217 = 0;
;
for (; index217 < groupLen217;index217++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group217.Get(index217)));
RDebugUtils.currentLine=1769745;
 //BA.debugLineNum = 1769745;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
RDebugUtils.currentLine=1769746;
 //BA.debugLineNum = 1769746;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
RDebugUtils.currentLine=1769747;
 //BA.debugLineNum = 1769747;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
RDebugUtils.currentLine=1769748;
 //BA.debugLineNum = 1769748;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=1769749;
 //BA.debugLineNum = 1769749;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
RDebugUtils.currentLine=1769750;
 //BA.debugLineNum = 1769750;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=1769751;
 //BA.debugLineNum = 1769751;BA.debugLine="panel.Initialize(\"lastproduct_panel\")";
_panel.Initialize(mostCurrent.activityBA,"lastproduct_panel");
RDebugUtils.currentLine=1769752;
 //BA.debugLineNum = 1769752;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
RDebugUtils.currentLine=1769753;
 //BA.debugLineNum = 1769753;BA.debugLine="panel.Color = Colors.White";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1769754;
 //BA.debugLineNum = 1769754;BA.debugLine="scrollview_lastproduct.Panel.AddView(panel,";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_panel.getObject()),_left,(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (225))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
RDebugUtils.currentLine=1769755;
 //BA.debugLineNum = 1769755;BA.debugLine="extra.InitPanel(panel,1dip,Colors.White,Col";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_panel,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)));
RDebugUtils.currentLine=1769756;
 //BA.debugLineNum = 1769756;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769757;
 //BA.debugLineNum = 1769757;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
RDebugUtils.currentLine=1769758;
 //BA.debugLineNum = 1769758;BA.debugLine="lable.Color = Colors.White";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1769759;
 //BA.debugLineNum = 1769759;BA.debugLine="lable.TextColor = Colors.rgb(65, 65, 65)";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (65),(int) (65),(int) (65)));
RDebugUtils.currentLine=1769760;
 //BA.debugLineNum = 1769760;BA.debugLine="lable.Gravity = Gravity.RIGHT";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=1769761;
 //BA.debugLineNum = 1769761;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"y";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769762;
 //BA.debugLineNum = 1769762;BA.debugLine="lable.TextSize = \"16\"";
_lable.setTextSize((float)(Double.parseDouble("16")));
RDebugUtils.currentLine=1769763;
 //BA.debugLineNum = 1769763;BA.debugLine="lable.Text = name";
_lable.setText(BA.ObjectToCharSequence(_name));
RDebugUtils.currentLine=1769764;
 //BA.debugLineNum = 1769764;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable,";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=1769766;
 //BA.debugLineNum = 1769766;BA.debugLine="Dim lable2 As Label";
_lable2 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=1769767;
 //BA.debugLineNum = 1769767;BA.debugLine="lable2.Initialize(\"lable2\")";
_lable2.Initialize(mostCurrent.activityBA,"lable2");
RDebugUtils.currentLine=1769768;
 //BA.debugLineNum = 1769768;BA.debugLine="lable2.Color = Colors.White";
_lable2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=1769769;
 //BA.debugLineNum = 1769769;BA.debugLine="lable2.TextColor = Colors.rgb(102, 187, 106";
_lable2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=1769770;
 //BA.debugLineNum = 1769770;BA.debugLine="lable2.Typeface = Typeface.DEFAULT_BOLD";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
RDebugUtils.currentLine=1769771;
 //BA.debugLineNum = 1769771;BA.debugLine="lable2.Gravity = Gravity.LEFT";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
RDebugUtils.currentLine=1769772;
 //BA.debugLineNum = 1769772;BA.debugLine="lable2.Gravity = Gravity.CENTER_VERTICAL";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
RDebugUtils.currentLine=1769773;
 //BA.debugLineNum = 1769773;BA.debugLine="lable2.Typeface = Typeface.LoadFromAssets(\"";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=1769774;
 //BA.debugLineNum = 1769774;BA.debugLine="lable2.TextSize = \"16\"";
_lable2.setTextSize((float)(Double.parseDouble("16")));
RDebugUtils.currentLine=1769775;
 //BA.debugLineNum = 1769775;BA.debugLine="lable2.Text = price";
_lable2.setText(BA.ObjectToCharSequence(_price));
RDebugUtils.currentLine=1769776;
 //BA.debugLineNum = 1769776;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable2";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable2.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (195)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)));
RDebugUtils.currentLine=1769777;
 //BA.debugLineNum = 1769777;BA.debugLine="image= image.Replace(\".jpg\",\"-600x600.jpg\")";
_image = _image.replace(".jpg","-600x600.jpg").replace("/catalog/categoty/","/cache/catalog/categoty/");
RDebugUtils.currentLine=1769778;
 //BA.debugLineNum = 1769778;BA.debugLine="lastproduct_ImageView(indexf).Initialize(\"l";
mostCurrent._lastproduct_imageview[_indexf].Initialize(mostCurrent.activityBA,"lastproduct_ImageView");
RDebugUtils.currentLine=1769779;
 //BA.debugLineNum = 1769779;BA.debugLine="lastproduct_ImageView(indexf).Tag = id";
mostCurrent._lastproduct_imageview[_indexf].setTag((Object)(_id));
RDebugUtils.currentLine=1769780;
 //BA.debugLineNum = 1769780;BA.debugLine="Dim filename As String=image.SubString2(ima";
_filename = _image.substring((int) (_image.lastIndexOf("/")+1),_image.length());
RDebugUtils.currentLine=1769782;
 //BA.debugLineNum = 1769782;BA.debugLine="If File.Exists(File.DirInternalCache, filen";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename)==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=1769783;
 //BA.debugLineNum = 1769783;BA.debugLine="Try";
try {RDebugUtils.currentLine=1769784;
 //BA.debugLineNum = 1769784;BA.debugLine="lastproduct_ImageView(indexf).Bitmap = Lo";
mostCurrent._lastproduct_imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename).getObject()));
 } 
       catch (Exception e257) {
			processBA.setLastException(e257);RDebugUtils.currentLine=1769786;
 //BA.debugLineNum = 1769786;BA.debugLine="Log(\"erro 	lastproduct_ImageView(indexf).";
anywheresoftware.b4a.keywords.Common.Log("erro 	lastproduct_ImageView(indexf).");
 };
 }else {
RDebugUtils.currentLine=1769790;
 //BA.debugLineNum = 1769790;BA.debugLine="lastproduct_ImageView(indexf).Bitmap = Loa";
mostCurrent._lastproduct_imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg").getObject()));
RDebugUtils.currentLine=1769791;
 //BA.debugLineNum = 1769791;BA.debugLine="extra.main_download_lastproduct(indexf,ima";
mostCurrent._extra._main_download_lastproduct(mostCurrent.activityBA,BA.NumberToString(_indexf),_image);
 };
RDebugUtils.currentLine=1769794;
 //BA.debugLineNum = 1769794;BA.debugLine="lastproduct_ImageView(indexf).Gravity = Gra";
mostCurrent._lastproduct_imageview[_indexf].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=1769795;
 //BA.debugLineNum = 1769795;BA.debugLine="scrollview_lastproduct.Panel.AddView(lastpr";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(mostCurrent._lastproduct_imageview[_indexf].getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
RDebugUtils.currentLine=1769797;
 //BA.debugLineNum = 1769797;BA.debugLine="Dim CanvasGraph As Canvas";
_canvasgraph = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
RDebugUtils.currentLine=1769798;
 //BA.debugLineNum = 1769798;BA.debugLine="CanvasGraph.Initialize(panel)";
_canvasgraph.Initialize((android.view.View)(_panel.getObject()));
RDebugUtils.currentLine=1769800;
 //BA.debugLineNum = 1769800;BA.debugLine="CanvasGraph.DrawLine(0,140dip,160dip,140dip";
_canvasgraph.DrawLine((float) (0),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
RDebugUtils.currentLine=1769801;
 //BA.debugLineNum = 1769801;BA.debugLine="left = left + 170dip";
_left = (int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
RDebugUtils.currentLine=1769802;
 //BA.debugLineNum = 1769802;BA.debugLine="indexf= indexf + 1";
_indexf = (int) (_indexf+1);
 }
};
RDebugUtils.currentLine=1769804;
 //BA.debugLineNum = 1769804;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=1769805;
 //BA.debugLineNum = 1769805;BA.debugLine="r.Target = scrollview_lastproduct";
_r.Target = (Object)(mostCurrent._scrollview_lastproduct.getObject());
RDebugUtils.currentLine=1769806;
 //BA.debugLineNum = 1769806;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\"";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
RDebugUtils.currentLine=1769807;
 //BA.debugLineNum = 1769807;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.l";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
RDebugUtils.currentLine=1769808;
 //BA.debugLineNum = 1769808;BA.debugLine="scrollview_lastproduct.Panel.Width = left";
mostCurrent._scrollview_lastproduct.getPanel().setWidth(_left);
RDebugUtils.currentLine=1769809;
 //BA.debugLineNum = 1769809;BA.debugLine="product_ScrollView(flager).Panel.Height = 50";
mostCurrent._product_scrollview[_flager].getPanel().setHeight((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (295))));
 };
 } 
       catch (Exception e279) {
			processBA.setLastException(e279);RDebugUtils.currentLine=1769813;
 //BA.debugLineNum = 1769813;BA.debugLine="Log(\"eroro lastproduct job\")";
anywheresoftware.b4a.keywords.Common.Log("eroro lastproduct job");
 };
 break; }
}
;
 };
RDebugUtils.currentLine=1769818;
 //BA.debugLineNum = 1769818;BA.debugLine="End Sub";
return "";
}
public static String  _lastproduct_imageview_click() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lastproduct_imageview_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "lastproduct_imageview_click", null);
anywheresoftware.b4a.objects.ImageViewWrapper _img = null;
RDebugUtils.currentLine=2031616;
 //BA.debugLineNum = 2031616;BA.debugLine="Sub lastproduct_ImageView_click()";
RDebugUtils.currentLine=2031617;
 //BA.debugLineNum = 2031617;BA.debugLine="Dim img As ImageView";
_img = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=2031618;
 //BA.debugLineNum = 2031618;BA.debugLine="img = Sender";
_img.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=2031619;
 //BA.debugLineNum = 2031619;BA.debugLine="extra.product_id_toshow = img.Tag";
mostCurrent._extra._product_id_toshow = (int)(BA.ObjectToNumber(_img.getTag()));
RDebugUtils.currentLine=2031621;
 //BA.debugLineNum = 2031621;BA.debugLine="product_ScrollView(extra.flag_procpnl).Initialize";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].Initialize(mostCurrent.activityBA,(int) (500));
RDebugUtils.currentLine=2031622;
 //BA.debugLineNum = 2031622;BA.debugLine="product_ScrollView(extra.flag_procpnl).Color = Co";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
RDebugUtils.currentLine=2031623;
 //BA.debugLineNum = 2031623;BA.debugLine="Activity.AddView(product_ScrollView(extra.flag_pr";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=2031624;
 //BA.debugLineNum = 2031624;BA.debugLine="loadroc(extra.flag_procpnl)";
_loadroc(mostCurrent._extra._flag_procpnl);
RDebugUtils.currentLine=2031625;
 //BA.debugLineNum = 2031625;BA.debugLine="extra.flag_procpnl = extra.flag_procpnl + 1";
mostCurrent._extra._flag_procpnl = (int) (mostCurrent._extra._flag_procpnl+1);
RDebugUtils.currentLine=2031626;
 //BA.debugLineNum = 2031626;BA.debugLine="End Sub";
return "";
}
public static String  _propert_click() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "propert_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "propert_click", null);
anywheresoftware.b4a.objects.LabelWrapper _propert = null;
String _temp = "";
int _topset = 0;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
String _name = "";
String _text = "";
String _grouping = "";
anywheresoftware.b4a.objects.LabelWrapper _lblnodata = null;
RDebugUtils.currentLine=2097152;
 //BA.debugLineNum = 2097152;BA.debugLine="Sub propert_click()";
RDebugUtils.currentLine=2097153;
 //BA.debugLineNum = 2097153;BA.debugLine="Dim propert As Label";
_propert = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2097154;
 //BA.debugLineNum = 2097154;BA.debugLine="Dim temp As String";
_temp = "";
RDebugUtils.currentLine=2097155;
 //BA.debugLineNum = 2097155;BA.debugLine="extra.propertyjson = 1";
mostCurrent._extra._propertyjson = (int) (1);
RDebugUtils.currentLine=2097156;
 //BA.debugLineNum = 2097156;BA.debugLine="propert = Sender";
_propert.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=2097157;
 //BA.debugLineNum = 2097157;BA.debugLine="temp = propert.Tag";
_temp = BA.ObjectToString(_propert.getTag());
RDebugUtils.currentLine=2097158;
 //BA.debugLineNum = 2097158;BA.debugLine="temp =   temp.SubString2(0,  temp.LastIndexOf(\"#\"";
_temp = _temp.substring((int) (0),_temp.lastIndexOf("#"));
RDebugUtils.currentLine=2097159;
 //BA.debugLineNum = 2097159;BA.debugLine="Log(temp)";
anywheresoftware.b4a.keywords.Common.Log(_temp);
RDebugUtils.currentLine=2097160;
 //BA.debugLineNum = 2097160;BA.debugLine="property_pnl.Initialize(100%y)";
mostCurrent._property_pnl.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=2097161;
 //BA.debugLineNum = 2097161;BA.debugLine="property_pnl.Color = Colors.rgb(250, 250, 250)";
mostCurrent._property_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
RDebugUtils.currentLine=2097162;
 //BA.debugLineNum = 2097162;BA.debugLine="Activity.AddView(property_pnl,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._property_pnl.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
RDebugUtils.currentLine=2097163;
 //BA.debugLineNum = 2097163;BA.debugLine="Dim topset As Int = 5dip";
_topset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
RDebugUtils.currentLine=2097164;
 //BA.debugLineNum = 2097164;BA.debugLine="Try";
try {RDebugUtils.currentLine=2097165;
 //BA.debugLineNum = 2097165;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=2097166;
 //BA.debugLineNum = 2097166;BA.debugLine="parser.Initialize(temp)";
_parser.Initialize(_temp);
RDebugUtils.currentLine=2097167;
 //BA.debugLineNum = 2097167;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=2097168;
 //BA.debugLineNum = 2097168;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group16 = _root;
final int groupLen16 = group16.getSize()
;int index16 = 0;
;
for (; index16 < groupLen16;index16++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group16.Get(index16)));
RDebugUtils.currentLine=2097169;
 //BA.debugLineNum = 2097169;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=2097170;
 //BA.debugLineNum = 2097170;BA.debugLine="Dim text As String = colroot.Get(\"text\")";
_text = BA.ObjectToString(_colroot.Get((Object)("text")));
RDebugUtils.currentLine=2097171;
 //BA.debugLineNum = 2097171;BA.debugLine="Dim grouping As String = colroot.Get(\"grouping\"";
_grouping = BA.ObjectToString(_colroot.Get((Object)("grouping")));
RDebugUtils.currentLine=2097173;
 //BA.debugLineNum = 2097173;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2097174;
 //BA.debugLineNum = 2097174;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2097175;
 //BA.debugLineNum = 2097175;BA.debugLine="lblnodata.Text =grouping";
_lblnodata.setText(BA.ObjectToCharSequence(_grouping));
RDebugUtils.currentLine=2097176;
 //BA.debugLineNum = 2097176;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=2097177;
 //BA.debugLineNum = 2097177;BA.debugLine="lblnodata.TextColor = Colors.rgb(38, 38, 38)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (38),(int) (38),(int) (38)));
RDebugUtils.currentLine=2097178;
 //BA.debugLineNum = 2097178;BA.debugLine="lblnodata.color = Colors.rgb(217, 217, 217)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (217),(int) (217),(int) (217)));
RDebugUtils.currentLine=2097179;
 //BA.debugLineNum = 2097179;BA.debugLine="lblnodata.TextSize = 11dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
RDebugUtils.currentLine=2097180;
 //BA.debugLineNum = 2097180;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"y";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2097181;
 //BA.debugLineNum = 2097181;BA.debugLine="property_pnl.Panel.AddView(lblnodata,5dip,topse";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),_topset,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
RDebugUtils.currentLine=2097183;
 //BA.debugLineNum = 2097183;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2097184;
 //BA.debugLineNum = 2097184;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2097185;
 //BA.debugLineNum = 2097185;BA.debugLine="lblnodata.Text =\"  \" & text";
_lblnodata.setText(BA.ObjectToCharSequence("  "+_text));
RDebugUtils.currentLine=2097186;
 //BA.debugLineNum = 2097186;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2097187;
 //BA.debugLineNum = 2097187;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (115),(int) (115),(int) (115)));
RDebugUtils.currentLine=2097188;
 //BA.debugLineNum = 2097188;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)));
RDebugUtils.currentLine=2097189;
 //BA.debugLineNum = 2097189;BA.debugLine="lblnodata.TextSize = 9dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=2097190;
 //BA.debugLineNum = 2097190;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"y";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2097191;
 //BA.debugLineNum = 2097191;BA.debugLine="property_pnl.Panel.AddView(lblnodata,5dip,topse";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=2097193;
 //BA.debugLineNum = 2097193;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2097194;
 //BA.debugLineNum = 2097194;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2097195;
 //BA.debugLineNum = 2097195;BA.debugLine="lblnodata.Text =\"  \" & name";
_lblnodata.setText(BA.ObjectToCharSequence("  "+_name));
RDebugUtils.currentLine=2097196;
 //BA.debugLineNum = 2097196;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2097197;
 //BA.debugLineNum = 2097197;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (115),(int) (115),(int) (115)));
RDebugUtils.currentLine=2097198;
 //BA.debugLineNum = 2097198;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)));
RDebugUtils.currentLine=2097199;
 //BA.debugLineNum = 2097199;BA.debugLine="lblnodata.TextSize = 10dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=2097200;
 //BA.debugLineNum = 2097200;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"y";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2097201;
 //BA.debugLineNum = 2097201;BA.debugLine="property_pnl.Panel.AddView(lblnodata,70%x,topse";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=2097203;
 //BA.debugLineNum = 2097203;BA.debugLine="topset = topset + 65dip";
_topset = (int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65)));
RDebugUtils.currentLine=2097205;
 //BA.debugLineNum = 2097205;BA.debugLine="property_pnl.Panel.Height = topset";
mostCurrent._property_pnl.getPanel().setHeight(_topset);
 }
};
 } 
       catch (Exception e51) {
			processBA.setLastException(e51);RDebugUtils.currentLine=2097208;
 //BA.debugLineNum = 2097208;BA.debugLine="createnon";
_createnon();
 };
RDebugUtils.currentLine=2097210;
 //BA.debugLineNum = 2097210;BA.debugLine="End Sub";
return "";
}
public static String  _sharepost_click() throws Exception{
RDebugUtils.currentModule="index";
if (Debug.shouldDelegate(mostCurrent.activityBA, "sharepost_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "sharepost_click", null);
anywheresoftware.b4a.objects.ImageViewWrapper _pic_sheare = null;
RDebugUtils.currentLine=2228224;
 //BA.debugLineNum = 2228224;BA.debugLine="Sub sharepost_click()";
RDebugUtils.currentLine=2228225;
 //BA.debugLineNum = 2228225;BA.debugLine="Dim pic_sheare As ImageView";
_pic_sheare = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=2228226;
 //BA.debugLineNum = 2228226;BA.debugLine="pic_sheare = Sender";
_pic_sheare.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=2228227;
 //BA.debugLineNum = 2228227;BA.debugLine="extra.programsharepost(pic_sheare.tag)";
mostCurrent._extra._programsharepost(mostCurrent.activityBA,BA.ObjectToString(_pic_sheare.getTag()));
RDebugUtils.currentLine=2228228;
 //BA.debugLineNum = 2228228;BA.debugLine="End Sub";
return "";
}
}