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
public de.amberhome.navdrawer.NavigationDrawer _navi = null;
public anywheresoftware.b4a.objects.PanelWrapper _pcantent = null;
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
public anywheresoftware.b4a.objects.ImageViewWrapper[] _categoryimg = null;
public anywheresoftware.b4a.objects.LabelWrapper[] _cat_title = null;
public anywheresoftware.b4a.objects.PanelWrapper _cat_title_line = null;
public de.amberhome.viewpager.AHViewPager _pagerc = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _productheader = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _property_pnl = null;
public anywheresoftware.b4a.objects.PanelWrapper _appbg = null;
public anywheresoftware.b4a.objects.PanelWrapper _categorypnl = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _categoryscroll = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public manini.b4a.example.product _product = null;
public manini.b4a.example.property _property = null;
public manini.b4a.example.omid _omid = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 63;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="Activity.LoadLayout(\"index\")";
mostCurrent._activity.LoadLayout("index",mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="navi.Initialize2(\"navi\",Activity,50%x,navi.GRAVIT";
mostCurrent._navi.Initialize2(mostCurrent.activityBA,"navi",(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),mostCurrent._navi.GRAVITY_RIGHT);
 //BA.debugLineNum = 66;BA.debugLine="navi.NavigationPanel.Color = Colors.ARGB(150,236,";
mostCurrent._navi.getNavigationPanel().setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (236),(int) (239),(int) (241)));
 //BA.debugLineNum = 69;BA.debugLine="navi.NavigationPanel.LoadLayout(\"menu\")";
mostCurrent._navi.getNavigationPanel().LoadLayout("menu",mostCurrent.activityBA);
 //BA.debugLineNum = 70;BA.debugLine="navi.NavigationPanel.BringToFront";
mostCurrent._navi.getNavigationPanel().BringToFront();
 //BA.debugLineNum = 71;BA.debugLine="pCantent.Initialize(\"\")";
mostCurrent._pcantent.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 72;BA.debugLine="navi.ContentPanel.AddView(pCantent,0,0,100%x,100%";
mostCurrent._navi.getContentPanel().AddView((android.view.View)(mostCurrent._pcantent.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 74;BA.debugLine="pCantent.LoadLayout(\"index\")";
mostCurrent._pcantent.LoadLayout("index",mostCurrent.activityBA);
 //BA.debugLineNum = 75;BA.debugLine="pCantent.Color = Colors.RGB(234,234,234)";
mostCurrent._pcantent.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (234),(int) (234),(int) (234)));
 //BA.debugLineNum = 81;BA.debugLine="If File.Exists(File.DirInternalCache & \"/product";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 82;BA.debugLine="File.MakeDir(File.DirInternalCache,\"product\"	)";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product");
 };
 //BA.debugLineNum = 86;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 87;BA.debugLine="r.Target = index_ScrollView";
_r.Target = (Object)(mostCurrent._index_scrollview.getObject());
 //BA.debugLineNum = 88;BA.debugLine="r.RunMethod2(\"setVerticalScrollBarEnabled\", False";
_r.RunMethod2("setVerticalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 89;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang.i";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 90;BA.debugLine="extra.load_index";
mostCurrent._extra._load_index(mostCurrent.activityBA);
 //BA.debugLineNum = 91;BA.debugLine="extra.index_ob_olaviyat(0) = 1";
mostCurrent._extra._index_ob_olaviyat[(int) (0)] = (int) (1);
 //BA.debugLineNum = 92;BA.debugLine="extra.flag_procpnl = 0";
mostCurrent._extra._flag_procpnl = (int) (0);
 //BA.debugLineNum = 93;BA.debugLine="extra.propertyjson = 0";
mostCurrent._extra._propertyjson = (int) (0);
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 39;BA.debugLine="If KeyCode= KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 40;BA.debugLine="If categorypnl.Visible =True Then";
if (mostCurrent._categorypnl.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 41;BA.debugLine="categorypnl.Visible = False";
mostCurrent._categorypnl.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 42;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 44;BA.debugLine="If extra.propertyjson = 1 Then";
if (mostCurrent._extra._propertyjson==1) { 
 //BA.debugLineNum = 45;BA.debugLine="property_pnl.RemoveView";
mostCurrent._property_pnl.RemoveView();
 //BA.debugLineNum = 46;BA.debugLine="property_pnl.Visible = False";
mostCurrent._property_pnl.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 47;BA.debugLine="extra.propertyjson = 0";
mostCurrent._extra._propertyjson = (int) (0);
 //BA.debugLineNum = 48;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if(mostCurrent._extra._flag_procpnl==0) { 
 //BA.debugLineNum = 50;BA.debugLine="extra.procimg_flag = 0";
mostCurrent._extra._procimg_flag = (int) (0);
 //BA.debugLineNum = 51;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 53;BA.debugLine="extra.procimg_flag =extra.procimg_flag -  ex";
mostCurrent._extra._procimg_flag = (int) (mostCurrent._extra._procimg_flag-mostCurrent._extra._procimg_count[(int) (mostCurrent._extra._flag_procpnl-1)]);
 //BA.debugLineNum = 54;BA.debugLine="headerproc(extra.flag_procpnl-1).Visible = F";
mostCurrent._headerproc[(int) (mostCurrent._extra._flag_procpnl-1)].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 55;BA.debugLine="product_ScrollView(extra.flag_procpnl-1).Rem";
mostCurrent._product_scrollview[(int) (mostCurrent._extra._flag_procpnl-1)].RemoveView();
 //BA.debugLineNum = 56;BA.debugLine="product_ScrollView(extra.flag_procpnl-1).Vis";
mostCurrent._product_scrollview[(int) (mostCurrent._extra._flag_procpnl-1)].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 57;BA.debugLine="extra.flag_procpnl = extra.flag_procpnl - 1";
mostCurrent._extra._flag_procpnl = (int) (mostCurrent._extra._flag_procpnl-1);
 //BA.debugLineNum = 58;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 99;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _animationexitcategory_animationend() throws Exception{
 //BA.debugLineNum = 1235;BA.debugLine="Sub animationexitcategory_AnimationEnd";
 //BA.debugLineNum = 1236;BA.debugLine="categorypnl.Visible = False";
mostCurrent._categorypnl.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1237;BA.debugLine="End Sub";
return "";
}
public static String  _cat_title_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _target = null;
 //BA.debugLineNum = 1255;BA.debugLine="Sub cat_title_Click";
 //BA.debugLineNum = 1256;BA.debugLine="Dim target As Label";
_target = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1257;BA.debugLine="target = Sender";
_target.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 1259;BA.debugLine="productheader.ScrollPosition=target.Left";
mostCurrent._productheader.setScrollPosition(_target.getLeft());
 //BA.debugLineNum = 1260;BA.debugLine="Pagerc.GotoPage(target.Tag,True)";
mostCurrent._pagerc.GotoPage((int)(BA.ObjectToNumber(_target.getTag())),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1261;BA.debugLine="End Sub";
return "";
}
public static String  _categoryitem_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _imgdre = null;
anywheresoftware.b4a.samples.httputils2.httpjob _download = null;
anywheresoftware.b4a.samples.httputils2.httpjob _download2 = null;
 //BA.debugLineNum = 1239;BA.debugLine="Sub categoryitem_Click";
 //BA.debugLineNum = 1240;BA.debugLine="Dim imgdre As ImageView";
_imgdre = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1241;BA.debugLine="imgdre = Sender";
_imgdre.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 1242;BA.debugLine="Log(imgdre.Tag)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_imgdre.getTag()));
 //BA.debugLineNum = 1244;BA.debugLine="Dim download As HttpJob";
_download = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 1245;BA.debugLine="download.Initialize(\"subcategory\",Me)";
_download._initialize(processBA,"subcategory",index.getObject());
 //BA.debugLineNum = 1246;BA.debugLine="download.PostString(extra.api,\"op=getsubcategory&";
_download._poststring(mostCurrent._extra._api,"op=getsubcategory&id="+BA.ObjectToString(_imgdre.getTag()));
 //BA.debugLineNum = 1249;BA.debugLine="Dim download2 As HttpJob";
_download2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 1250;BA.debugLine="download2.Initialize(\"getsubcategorydescription\",";
_download2._initialize(processBA,"getsubcategorydescription",index.getObject());
 //BA.debugLineNum = 1251;BA.debugLine="download2.PostString (extra.api,\"op=getsubcategor";
_download2._poststring(mostCurrent._extra._api,"op=getsubcategorydescription&id="+BA.ObjectToString(_imgdre.getTag()));
 //BA.debugLineNum = 1253;BA.debugLine="End Sub";
return "";
}
public static String  _catmenubtn_click() throws Exception{
anywheresoftware.b4a.objects.AnimationWrapper _a = null;
 //BA.debugLineNum = 1227;BA.debugLine="Sub catmenubtn_Click";
 //BA.debugLineNum = 1228;BA.debugLine="navi.CloseDrawer2(navi.GRAVITY_END)";
mostCurrent._navi.CloseDrawer2(mostCurrent._navi.GRAVITY_END);
 //BA.debugLineNum = 1229;BA.debugLine="Dim  a As Animation";
_a = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 1230;BA.debugLine="a.InitializeAlpha(\"animationexitcategory\",1, 0)";
_a.InitializeAlpha(mostCurrent.activityBA,"animationexitcategory",(float) (1),(float) (0));
 //BA.debugLineNum = 1231;BA.debugLine="a.Duration = 500";
_a.setDuration((long) (500));
 //BA.debugLineNum = 1232;BA.debugLine="a.Start(categorypnl)";
_a.Start((android.view.View)(mostCurrent._categorypnl.getObject()));
 //BA.debugLineNum = 1233;BA.debugLine="End Sub";
return "";
}
public static String  _createnon() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lblnodata = null;
 //BA.debugLineNum = 1163;BA.debugLine="Sub createnon()";
 //BA.debugLineNum = 1164;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1165;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1166;BA.debugLine="lblnodata.Text =\"هیچ مشخصه ای وجود ندارد\"";
_lblnodata.setText(BA.ObjectToCharSequence("هیچ مشخصه ای وجود ندارد"));
 //BA.debugLineNum = 1167;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 1168;BA.debugLine="lblnodata.TextColor = Colors.rgb(179, 179, 179)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
 //BA.debugLineNum = 1169;BA.debugLine="lblnodata.TextSize = 12dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
 //BA.debugLineNum = 1170;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"yek";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1171;BA.debugLine="property_pnl.Panel.AddView(lblnodata,0,0,100%x,50";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 1172;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 10;BA.debugLine="Dim navi As AHNavigationDrawer";
mostCurrent._navi = new de.amberhome.navdrawer.NavigationDrawer();
 //BA.debugLineNum = 11;BA.debugLine="Dim pCantent As Panel";
mostCurrent._pcantent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private index_ScrollView As ScrollView";
mostCurrent._index_scrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim index_retrive_list As List";
mostCurrent._index_retrive_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Dim index_retrive_list_img As List";
mostCurrent._index_retrive_list_img = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Dim index_retrive_list_model As List";
mostCurrent._index_retrive_list_model = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 18;BA.debugLine="Dim imgdrew(5000) As ImageView";
mostCurrent._imgdrew = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (5000)];
{
int d0 = mostCurrent._imgdrew.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._imgdrew[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 20;BA.debugLine="Dim product_ScrollView(500) As ScrollView";
mostCurrent._product_scrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper[(int) (500)];
{
int d0 = mostCurrent._product_scrollview.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._product_scrollview[i0] = new anywheresoftware.b4a.objects.ScrollViewWrapper();
}
}
;
 //BA.debugLineNum = 21;BA.debugLine="Dim scrollview_lastproduct As HorizontalScrollVie";
mostCurrent._scrollview_lastproduct = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim category_hscrollview As HorizontalScrollView";
mostCurrent._category_hscrollview = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim lastproduct_ImageView(5000) As ImageView";
mostCurrent._lastproduct_imageview = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (5000)];
{
int d0 = mostCurrent._lastproduct_imageview.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._lastproduct_imageview[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 24;BA.debugLine="Dim headerproc(5000)  As Panel";
mostCurrent._headerproc = new anywheresoftware.b4a.objects.PanelWrapper[(int) (5000)];
{
int d0 = mostCurrent._headerproc.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._headerproc[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 25;BA.debugLine="Dim headerproctxt(5000)  As Label";
mostCurrent._headerproctxt = new anywheresoftware.b4a.objects.LabelWrapper[(int) (5000)];
{
int d0 = mostCurrent._headerproctxt.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._headerproctxt[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 26;BA.debugLine="Dim procimage(5000) As ImageView";
mostCurrent._procimage = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (5000)];
{
int d0 = mostCurrent._procimage.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._procimage[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 27;BA.debugLine="Dim categoryimg(50) As ImageView";
mostCurrent._categoryimg = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (50)];
{
int d0 = mostCurrent._categoryimg.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._categoryimg[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 28;BA.debugLine="Dim cat_title(70) As Label";
mostCurrent._cat_title = new anywheresoftware.b4a.objects.LabelWrapper[(int) (70)];
{
int d0 = mostCurrent._cat_title.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._cat_title[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;
 //BA.debugLineNum = 29;BA.debugLine="Dim cat_title_line As Panel";
mostCurrent._cat_title_line = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim Pagerc As AHViewPager";
mostCurrent._pagerc = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 31;BA.debugLine="Dim productheader As HorizontalScrollView";
mostCurrent._productheader = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim property_pnl As ScrollView";
mostCurrent._property_pnl = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private appbg As Panel";
mostCurrent._appbg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private categorypnl As Panel";
mostCurrent._categorypnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private categoryscroll As ScrollView";
mostCurrent._categoryscroll = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _imgdrew_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _imgdre = null;
 //BA.debugLineNum = 891;BA.debugLine="Sub imgdrew_click()";
 //BA.debugLineNum = 892;BA.debugLine="Dim imgdre As ImageView";
_imgdre = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 893;BA.debugLine="imgdre = Sender";
_imgdre.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 894;BA.debugLine="Log(imgdre.Tag)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_imgdre.getTag()));
 //BA.debugLineNum = 895;BA.debugLine="extra.product_id_toshow = imgdre.Tag";
mostCurrent._extra._product_id_toshow = (int)(BA.ObjectToNumber(_imgdre.getTag()));
 //BA.debugLineNum = 897;BA.debugLine="product_ScrollView(extra.flag_procpnl).Initialize";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].Initialize2(mostCurrent.activityBA,(int) (500),"product_ScrollView");
 //BA.debugLineNum = 898;BA.debugLine="product_ScrollView(extra.flag_procpnl).Color = Co";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
 //BA.debugLineNum = 899;BA.debugLine="Activity.AddView(product_ScrollView(extra.flag_pr";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 900;BA.debugLine="loadroc(extra.flag_procpnl)";
_loadroc(mostCurrent._extra._flag_procpnl);
 //BA.debugLineNum = 901;BA.debugLine="extra.flag_procpnl = extra.flag_procpnl+ 1";
mostCurrent._extra._flag_procpnl = (int) (mostCurrent._extra._flag_procpnl+1);
 //BA.debugLineNum = 903;BA.debugLine="End Sub";
return "";
}
public static String  _index_draw(String _size,String _flag,String _id,String _img,String _model) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
int _color = 0;
int _space = 0;
int _padding_space = 0;
int _left_draw = 0;
int _width_draw = 0;
int _shadow_space = 0;
String _imgfile = "";
 //BA.debugLineNum = 726;BA.debugLine="Sub index_draw(size As String,flag,id,img,model)";
 //BA.debugLineNum = 727;BA.debugLine="extra.index_ob_olaviyat_load = flag";
mostCurrent._extra._index_ob_olaviyat_load = (int)(Double.parseDouble(_flag));
 //BA.debugLineNum = 728;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 729;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 730;BA.debugLine="Dim color As Int";
_color = 0;
 //BA.debugLineNum = 731;BA.debugLine="color =Colors.White";
_color = anywheresoftware.b4a.keywords.Common.Colors.White;
 //BA.debugLineNum = 732;BA.debugLine="panel.Initialize(\"panel\")";
_panel.Initialize(mostCurrent.activityBA,"panel");
 //BA.debugLineNum = 733;BA.debugLine="lbl.Initialize(\"lbl\")";
_lbl.Initialize(mostCurrent.activityBA,"lbl");
 //BA.debugLineNum = 734;BA.debugLine="lbl.Text = model";
_lbl.setText(BA.ObjectToCharSequence(_model));
 //BA.debugLineNum = 735;BA.debugLine="lbl.TextColor = Colors.White";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 736;BA.debugLine="lbl.Color = Colors.rgb(15, 112, 93)";
_lbl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (15),(int) (112),(int) (93)));
 //BA.debugLineNum = 737;BA.debugLine="lbl.Gravity = Gravity.FILL";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 738;BA.debugLine="Dim space As Int = 2dip";
_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 739;BA.debugLine="Dim padding_space As Int = 2dip";
_padding_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 740;BA.debugLine="If size=\"larg\" Then";
if ((_size).equals("larg")) { 
 //BA.debugLineNum = 741;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 742;BA.debugLine="Dim width_draw As Int = 100%x - left_draw";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_left_draw);
 //BA.debugLineNum = 743;BA.debugLine="Dim shadow_space As Int = 5dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 744;BA.debugLine="extra.index_ob_olaviyat(flag) = 1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 745;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 746;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 };
 //BA.debugLineNum = 748;BA.debugLine="If size=\"medium\" Then";
if ((_size).equals("medium")) { 
 //BA.debugLineNum = 749;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (4),(int) (111),(int) (11),(int) (1))) {
case 0: {
 //BA.debugLineNum = 751;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 752;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 753;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 754;BA.debugLine="extra.index_ob_olaviyat(flag)=224";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (224);
 //BA.debugLineNum = 755;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 756;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 1: {
 //BA.debugLineNum = 758;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 759;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 760;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 761;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 762;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 2: {
 //BA.debugLineNum = 764;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 765;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 766;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 767;BA.debugLine="extra.index_ob_olaviyat(flag)=222";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (222);
 //BA.debugLineNum = 768;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 769;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 3: {
 //BA.debugLineNum = 771;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 772;BA.debugLine="Dim width_draw As Int = 66.4%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA);
 //BA.debugLineNum = 773;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 774;BA.debugLine="extra.index_ob_olaviyat(flag)=22";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (22);
 //BA.debugLineNum = 775;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 776;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
}
;
 };
 //BA.debugLineNum = 779;BA.debugLine="If size=\"small\" Then";
if ((_size).equals("small")) { 
 //BA.debugLineNum = 780;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (22),(int) (111),(int) (11),(int) (1))) {
case 0: {
 //BA.debugLineNum = 782;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 783;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 784;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 785;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
 //BA.debugLineNum = 786;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 787;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 788;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 1: {
 //BA.debugLineNum = 791;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 792;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 793;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 794;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
 //BA.debugLineNum = 795;BA.debugLine="extra.index_ob_olaviyat(flag)=225";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (225);
 //BA.debugLineNum = 796;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 797;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 2: {
 //BA.debugLineNum = 799;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 800;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 801;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 802;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
 //BA.debugLineNum = 803;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 804;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 805;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 3: {
 //BA.debugLineNum = 807;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 808;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 809;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 810;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
 //BA.debugLineNum = 811;BA.debugLine="extra.index_ob_olaviyat(flag)=223";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (223);
 //BA.debugLineNum = 812;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 813;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 4: {
 //BA.debugLineNum = 815;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 816;BA.debugLine="Dim width_draw As Int = 33.23%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.23),mostCurrent.activityBA);
 //BA.debugLineNum = 817;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 818;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
 //BA.debugLineNum = 819;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 820;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 821;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 5: {
 //BA.debugLineNum = 823;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 824;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 825;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 826;BA.debugLine="extra.index_ob_olaviyat(flag)=221";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (221);
 //BA.debugLineNum = 827;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 828;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 6: {
 //BA.debugLineNum = 830;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 831;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 832;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 833;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 834;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 835;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 7: {
 //BA.debugLineNum = 837;BA.debugLine="Dim left_draw As Int = 33.3%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.3),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 838;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 839;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 840;BA.debugLine="extra.index_ob_olaviyat(flag)=111";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (111);
 //BA.debugLineNum = 841;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 842;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 8: {
 //BA.debugLineNum = 844;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 845;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 846;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 847;BA.debugLine="extra.index_ob_olaviyat(flag)=11";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (11);
 //BA.debugLineNum = 848;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 849;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
}
;
 };
 //BA.debugLineNum = 856;BA.debugLine="lbl.Gravity = Gravity.FILL";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 857;BA.debugLine="lbl.Gravity = Gravity.CENTER";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 858;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
 //BA.debugLineNum = 862;BA.debugLine="Dim imgfile As String = img.SubString2(img.LastIn";
_imgfile = _img.substring((int) (_img.lastIndexOf("/")+1),_img.length());
 //BA.debugLineNum = 864;BA.debugLine="If File.Exists (File.DirInternalCache,\"product/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product/"+_imgfile)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 866;BA.debugLine="imgdrew(flag).Initialize(\"imgdrew\")";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].Initialize(mostCurrent.activityBA,"imgdrew");
 //BA.debugLineNum = 867;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject()));
 //BA.debugLineNum = 869;BA.debugLine="imgdrew(flag).Tag = id";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setTag((Object)(_id));
 //BA.debugLineNum = 870;BA.debugLine="imgdrew(flag).Gravity = Gravity.FILL";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 871;BA.debugLine="extra.download_image(id,img,flag)";
mostCurrent._extra._download_image(mostCurrent.activityBA,_id,_img,_flag);
 }else {
 //BA.debugLineNum = 875;BA.debugLine="imgdrew(flag).Initialize(\"imgdrew\")";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].Initialize(mostCurrent.activityBA,"imgdrew");
 //BA.debugLineNum = 876;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product/"+_imgfile,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject()));
 //BA.debugLineNum = 877;BA.debugLine="imgdrew(flag).Gravity = Gravity.FILL";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 878;BA.debugLine="imgdrew(flag).Tag =  id";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setTag((Object)(_id));
 };
 //BA.debugLineNum = 882;BA.debugLine="index_ScrollView.Panel.AddView(panel,left_draw,ex";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_panel.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
 //BA.debugLineNum = 884;BA.debugLine="index_ScrollView.Panel.AddView(imgdrew(flag),left";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
 //BA.debugLineNum = 886;BA.debugLine="index_ScrollView.Panel.AddView(lbl,left_draw,extr";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_lbl.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_width_draw-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25))),(int) (_width_draw-_space),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 887;BA.debugLine="extra.index_ob_top = extra.index_ob_top + extra.i";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+mostCurrent._extra._index_ob_top_cach);
 //BA.debugLineNum = 888;BA.debugLine="index_ScrollView.Panel.Height = extra.index_ob_to";
mostCurrent._index_scrollview.getPanel().setHeight((int) (mostCurrent._extra._index_ob_top+_space));
 //BA.debugLineNum = 889;BA.debugLine="End Sub";
return "";
}
public static String  _index_scrollview_scrollchanged(int _position) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 101;BA.debugLine="Sub index_ScrollView_ScrollChanged(Position As Int";
 //BA.debugLineNum = 102;BA.debugLine="If (Position+1950) >= extra.index_ob_top Then";
if ((_position+1950)>=mostCurrent._extra._index_ob_top) { 
 //BA.debugLineNum = 103;BA.debugLine="Log(extra.index_ob_olaviyat_load)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._extra._index_ob_olaviyat_load));
 //BA.debugLineNum = 104;BA.debugLine="extra.index_ob_olaviyat(extra.index_ob_olaviyat_";
mostCurrent._extra._index_ob_olaviyat[(int) (mostCurrent._extra._index_ob_olaviyat_load-1)] = (int) (1);
 //BA.debugLineNum = 105;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 106;BA.debugLine="job.Initialize(\"\",Me)";
_job._initialize(processBA,"",index.getObject());
 //BA.debugLineNum = 107;BA.debugLine="load_indexjob(job,False)";
_load_indexjob(_job,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
int _topseting = 0;
int _pagecount = 0;
int _pagecountflag = 0;
int _pageflag = 0;
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
de.amberhome.viewpager.AHPageContainer _containerc = null;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
String _catname = "";
String _proname = "";
String _id = "";
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.PanelWrapper _bg = null;
anywheresoftware.b4a.objects.LabelWrapper _procname = null;
int _leftset = 0;
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _yekanfont = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _flagt = 0;
String _image = "";
String _name = "";
int _flag = 0;
boolean _gflag = false;
int _topset = 0;
String _filename = "";
String _img = "";
String _flag1 = "";
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outstream = null;
int _flagerpic = 0;
int _flagpanel = 0;
de.amberhome.viewpager.AHPageContainer _container = null;
de.amberhome.viewpager.AHViewPager _pager = null;
int _flager = 0;
String _s = "";
anywheresoftware.b4a.objects.LabelWrapper _rtljustify1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
int _index = 0;
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
int _indexf = 0;
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
anywheresoftware.b4a.objects.LabelWrapper _lable2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvasgraph = null;
 //BA.debugLineNum = 196;BA.debugLine="Sub jobdone(job As HttpJob)";
 //BA.debugLineNum = 198;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 199;BA.debugLine="Select job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"getsubcategorydescription","subcategory","categorypage","load_indexjob")) {
case 0: {
 //BA.debugLineNum = 201;BA.debugLine="Dim topseting As Int =0";
_topseting = (int) (0);
 //BA.debugLineNum = 202;BA.debugLine="Dim pagecount As Int=-1";
_pagecount = (int) (-1);
 //BA.debugLineNum = 203;BA.debugLine="Dim pagecountflag As Int=0";
_pagecountflag = (int) (0);
 //BA.debugLineNum = 204;BA.debugLine="Dim pageflag As Int=0";
_pageflag = (int) (0);
 //BA.debugLineNum = 205;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 206;BA.debugLine="Dim pan(500) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (500)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 207;BA.debugLine="Dim Containerc As AHPageContainer";
_containerc = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 209;BA.debugLine="Containerc.Initialize";
_containerc.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 210;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 211;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 212;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 213;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group15 = _root;
final int groupLen15 = group15.getSize()
;int index15 = 0;
;
for (; index15 < groupLen15;index15++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group15.Get(index15)));
 //BA.debugLineNum = 214;BA.debugLine="Dim catname As String = colroot.Get(\"catname\"";
_catname = BA.ObjectToString(_colroot.Get((Object)("catname")));
 //BA.debugLineNum = 215;BA.debugLine="Dim proname As String = colroot.Get(\"proname\"";
_proname = BA.ObjectToString(_colroot.Get((Object)("proname")));
 //BA.debugLineNum = 216;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 217;BA.debugLine="If pagecount <> id Then";
if (_pagecount!=(double)(Double.parseDouble(_id))) { 
 //BA.debugLineNum = 218;BA.debugLine="topseting =0";
_topseting = (int) (0);
 //BA.debugLineNum = 219;BA.debugLine="pageflag = pageflag + 1";
_pageflag = (int) (_pageflag+1);
 //BA.debugLineNum = 220;BA.debugLine="pagecount = id";
_pagecount = (int)(Double.parseDouble(_id));
 //BA.debugLineNum = 221;BA.debugLine="pan(pageflag).Initialize(\"\")";
_pan[_pageflag].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 222;BA.debugLine="Containerc.AddPageAt(pan(pageflag), \"Main\",";
_containerc.AddPageAt((android.view.View)(_pan[_pageflag].getObject()),"Main",(int) (0));
 //BA.debugLineNum = 223;BA.debugLine="pagecountflag= pagecountflag+ 1";
_pagecountflag = (int) (_pagecountflag+1);
 };
 //BA.debugLineNum = 226;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 227;BA.debugLine="cd.Initialize (Colors.rgb(230, 230, 230),6dip";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (230),(int) (230),(int) (230)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)));
 //BA.debugLineNum = 228;BA.debugLine="Dim bg As Panel";
_bg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 229;BA.debugLine="bg.Initialize(\"\")";
_bg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 230;BA.debugLine="bg.Background =cd";
_bg.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 231;BA.debugLine="pan(pageflag).AddView(bg,5dip,topseting,100%x";
_pan[_pageflag].AddView((android.view.View)(_bg.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),_topseting,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
 //BA.debugLineNum = 233;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 234;BA.debugLine="cd.Initialize (Colors.rgb(242, 242, 242),6dip";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)));
 //BA.debugLineNum = 235;BA.debugLine="Dim bg As Panel";
_bg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 236;BA.debugLine="bg.Initialize(\"\")";
_bg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 237;BA.debugLine="bg.Background =cd";
_bg.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 238;BA.debugLine="pan(pageflag).AddView(bg,6dip,topseting+1dip,";
_pan[_pageflag].AddView((android.view.View)(_bg.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)),(int) (_topseting+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (168)));
 //BA.debugLineNum = 241;BA.debugLine="Dim procname As Label";
_procname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 242;BA.debugLine="procname.Initialize(\"\")";
_procname.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 243;BA.debugLine="procname.Text = proname";
_procname.setText(BA.ObjectToCharSequence(_proname));
 //BA.debugLineNum = 244;BA.debugLine="procname.TextColor = Colors.Black";
_procname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 245;BA.debugLine="procname.Gravity = Gravity.RIGHT";
_procname.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 246;BA.debugLine="pan(pageflag).AddView(procname,40%x,topseting";
_pan[_pageflag].AddView((android.view.View)(_procname.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA),(int) (_topseting+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 247;BA.debugLine="topseting = topseting + 175dip";
_topseting = (int) (_topseting+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (175)));
 }
};
 //BA.debugLineNum = 250;BA.debugLine="Pagerc.Initialize2(Containerc, \"Pagerc\")";
mostCurrent._pagerc.Initialize2(mostCurrent.activityBA,_containerc,"Pagerc");
 //BA.debugLineNum = 252;BA.debugLine="categoryscroll.Panel.AddView(Pagerc,0,55dip,10";
mostCurrent._categoryscroll.getPanel().AddView((android.view.View)(mostCurrent._pagerc.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))));
 //BA.debugLineNum = 254;BA.debugLine="Log(\" page cound \" & pagecountflag)";
anywheresoftware.b4a.keywords.Common.Log(" page cound "+BA.NumberToString(_pagecountflag));
 //BA.debugLineNum = 255;BA.debugLine="categoryscroll.Panel.Height = 100%y";
mostCurrent._categoryscroll.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 256;BA.debugLine="categoryscroll.Panel.Color = Colors.White";
mostCurrent._categoryscroll.getPanel().setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 break; }
case 1: {
 //BA.debugLineNum = 259;BA.debugLine="Try";
try { //BA.debugLineNum = 260;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 261;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 262;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 263;BA.debugLine="Dim leftset As Int=2dip";
_leftset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 265;BA.debugLine="categoryscroll.Panel.RemoveAllViews";
mostCurrent._categoryscroll.getPanel().RemoveAllViews();
 //BA.debugLineNum = 267;BA.debugLine="productheader.Initialize(100%x,\"\")";
mostCurrent._productheader.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
 //BA.debugLineNum = 268;BA.debugLine="productheader.Color=Colors.rgb(75, 231, 200)";
mostCurrent._productheader.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (75),(int) (231),(int) (200)));
 //BA.debugLineNum = 269;BA.debugLine="Dim yekanfont As Typeface";
_yekanfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 270;BA.debugLine="yekanfont = Typeface.LoadFromAssets(\"yekan.tt";
_yekanfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf")));
 //BA.debugLineNum = 271;BA.debugLine="categoryscroll.Panel.AddView(productheader,0,";
mostCurrent._categoryscroll.getPanel().AddView((android.view.View)(mostCurrent._productheader.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 273;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 274;BA.debugLine="r.Target = productheader";
_r.Target = (Object)(mostCurrent._productheader.getObject());
 //BA.debugLineNum = 275;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\",";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 276;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.la";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 277;BA.debugLine="Dim flagt As Int = 0";
_flagt = (int) (0);
 //BA.debugLineNum = 278;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group69 = _root;
final int groupLen69 = group69.getSize()
;int index69 = 0;
;
for (; index69 < groupLen69;index69++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group69.Get(index69)));
 //BA.debugLineNum = 279;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
 //BA.debugLineNum = 280;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 281;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 283;BA.debugLine="cat_title(flagt).Initialize(\"cat_title\")";
mostCurrent._cat_title[_flagt].Initialize(mostCurrent.activityBA,"cat_title");
 //BA.debugLineNum = 284;BA.debugLine="cat_title(flagt).Text = name";
mostCurrent._cat_title[_flagt].setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 285;BA.debugLine="cat_title(flagt).Tag = flagt";
mostCurrent._cat_title[_flagt].setTag((Object)(_flagt));
 //BA.debugLineNum = 286;BA.debugLine="cat_title(flagt).Gravity = Gravity.CENTER";
mostCurrent._cat_title[_flagt].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 287;BA.debugLine="cat_title(flagt).TextColor = Colors.Black";
mostCurrent._cat_title[_flagt].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 288;BA.debugLine="cat_title(flagt).Typeface = yekanfont";
mostCurrent._cat_title[_flagt].setTypeface((android.graphics.Typeface)(_yekanfont.getObject()));
 //BA.debugLineNum = 289;BA.debugLine="cat_title(flagt).TextSize = 9dip";
mostCurrent._cat_title[_flagt].setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 291;BA.debugLine="productheader.Panel.AddView(cat_title(flagt)";
mostCurrent._productheader.getPanel().AddView((android.view.View)(mostCurrent._cat_title[_flagt].getObject()),_leftset,(int) (0),(int) (_name.length()*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 292;BA.debugLine="leftset = leftset + (name.Length*12dip)  + 2";
_leftset = (int) (_leftset+(_name.length()*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12)))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)));
 //BA.debugLineNum = 293;BA.debugLine="flagt = flagt + 1";
_flagt = (int) (_flagt+1);
 }
};
 //BA.debugLineNum = 297;BA.debugLine="cat_title_line.Initialize(\"\")";
mostCurrent._cat_title_line.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 298;BA.debugLine="cat_title_line.Color = Colors.rgb(18, 135, 11";
mostCurrent._cat_title_line.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (18),(int) (135),(int) (111)));
 //BA.debugLineNum = 299;BA.debugLine="productheader.Panel.AddView(cat_title_line,0,";
mostCurrent._productheader.getPanel().AddView((android.view.View)(mostCurrent._cat_title_line.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48)),mostCurrent._cat_title[(int) (0)].getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)));
 //BA.debugLineNum = 300;BA.debugLine="productheader.Panel.Width = leftset + 2dip";
mostCurrent._productheader.getPanel().setWidth((int) (_leftset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 301;BA.debugLine="categoryscroll.Color = Colors.White";
mostCurrent._categoryscroll.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 } 
       catch (Exception e90) {
			processBA.setLastException(e90); //BA.debugLineNum = 304;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 break; }
case 2: {
 //BA.debugLineNum = 307;BA.debugLine="Dim flag As Int=0";
_flag = (int) (0);
 //BA.debugLineNum = 308;BA.debugLine="Dim gflag As Boolean=False";
_gflag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 309;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 310;BA.debugLine="Dim topset As Int = 55dip";
_topset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55));
 //BA.debugLineNum = 311;BA.debugLine="Dim leftset As Int=5dip";
_leftset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 312;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 313;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 314;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 315;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group101 = _root;
final int groupLen101 = group101.getSize()
;int index101 = 0;
;
for (; index101 < groupLen101;index101++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group101.Get(index101)));
 //BA.debugLineNum = 316;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
 //BA.debugLineNum = 317;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 318;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 319;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 320;BA.debugLine="filename = image.SubString(image.LastIndexOf(";
_filename = _image.substring((int) (_image.lastIndexOf("/")+1));
 //BA.debugLineNum = 321;BA.debugLine="Log(filename)";
anywheresoftware.b4a.keywords.Common.Log(_filename);
 //BA.debugLineNum = 334;BA.debugLine="categoryimg(flag).Initialize(\"categoryitem\")";
mostCurrent._categoryimg[_flag].Initialize(mostCurrent.activityBA,"categoryitem");
 //BA.debugLineNum = 335;BA.debugLine="If File.Exists(File.DirInternalCache,filename";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 336;BA.debugLine="categoryimg(flag).Bitmap = LoadBitmap(File.";
mostCurrent._categoryimg[_flag].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename).getObject()));
 }else {
 //BA.debugLineNum = 338;BA.debugLine="categoryimg(flag).Bitmap = LoadBitmap(File.";
mostCurrent._categoryimg[_flag].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"main_defult_product.jpg").getObject()));
 //BA.debugLineNum = 339;BA.debugLine="extra.main_download_categorypic(flag,extra.i";
mostCurrent._extra._main_download_categorypic(mostCurrent.activityBA,BA.NumberToString(_flag),mostCurrent._extra._image_address_nc+_image);
 //BA.debugLineNum = 340;BA.debugLine="Log(\"file \" & extra.image_address_nc &image)";
anywheresoftware.b4a.keywords.Common.Log("file "+mostCurrent._extra._image_address_nc+_image);
 };
 //BA.debugLineNum = 343;BA.debugLine="categoryimg(flag).Gravity = Gravity.FILL";
mostCurrent._categoryimg[_flag].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 344;BA.debugLine="categoryimg(flag).Tag = id";
mostCurrent._categoryimg[_flag].setTag((Object)(_id));
 //BA.debugLineNum = 345;BA.debugLine="If leftset=5dip Then";
if (_leftset==anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))) { 
 //BA.debugLineNum = 346;BA.debugLine="gflag=False";
_gflag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 347;BA.debugLine="categoryscroll.Panel.AddView(categoryimg(fla";
mostCurrent._categoryscroll.getPanel().AddView((android.view.View)(mostCurrent._categoryimg[_flag].getObject()),_leftset,(int) (_topset-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (49))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 348;BA.debugLine="leftset = 50%x-8dip";
_leftset = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8)));
 }else {
 //BA.debugLineNum = 350;BA.debugLine="gflag=True";
_gflag = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 351;BA.debugLine="categoryscroll.Panel.AddView(categoryimg(fla";
mostCurrent._categoryscroll.getPanel().AddView((android.view.View)(mostCurrent._categoryimg[_flag].getObject()),(int) (_leftset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(int) (_topset-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (49))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 352;BA.debugLine="topset = topset + (50%x-8)";
_topset = (int) (_topset+(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-8));
 //BA.debugLineNum = 353;BA.debugLine="leftset=5dip";
_leftset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 };
 //BA.debugLineNum = 366;BA.debugLine="flag = flag + 1";
_flag = (int) (_flag+1);
 }
};
 //BA.debugLineNum = 369;BA.debugLine="If gflag=False Then topset = topset  + 50%x-8";
if (_gflag==anywheresoftware.b4a.keywords.Common.False) { 
_topset = (int) (_topset+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)-8);};
 //BA.debugLineNum = 370;BA.debugLine="categoryscroll.Panel.Height = topset";
mostCurrent._categoryscroll.getPanel().setHeight(_topset);
 break; }
case 3: {
 //BA.debugLineNum = 372;BA.debugLine="load_indexjob(job,True)";
_load_indexjob(_job,anywheresoftware.b4a.keywords.Common.True);
 break; }
default: {
 //BA.debugLineNum = 374;BA.debugLine="Try";
try { //BA.debugLineNum = 375;BA.debugLine="If job.JobName.SubString2(0,13)=\"downloadimag";
if ((_job._jobname.substring((int) (0),(int) (13))).equals("downloadimage")) { 
 //BA.debugLineNum = 376;BA.debugLine="Dim id As String = job.JobName.SubString2(jo";
_id = _job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.indexOf("$"));
 //BA.debugLineNum = 377;BA.debugLine="Dim img As String = job.JobName.SubString2(j";
_img = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1),_job._jobname.lastIndexOf("#"));
 //BA.debugLineNum = 378;BA.debugLine="Dim flag1 As String = job.JobName.SubString2";
_flag1 = _job._jobname.substring((int) (_job._jobname.lastIndexOf("#")+1),_job._jobname.length());
 //BA.debugLineNum = 379;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 380;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product",_img,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 381;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
 //BA.debugLineNum = 382;BA.debugLine="OutStream.Close";
_outstream.Close();
 //BA.debugLineNum = 383;BA.debugLine="imgdrew(flag1).Bitmap = LoadBitmapSample(Fil";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag1))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product",_img,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200))).getObject()));
 };
 } 
       catch (Exception e147) {
			processBA.setLastException(e147); };
 //BA.debugLineNum = 389;BA.debugLine="If job.JobName.SubString2(0,7) = \"picproc\" The";
if ((_job._jobname.substring((int) (0),(int) (7))).equals("picproc")) { 
 //BA.debugLineNum = 390;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 392;BA.debugLine="Dim pan(20) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (20)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 393;BA.debugLine="Dim flagerpic,flagpanel As Int";
_flagerpic = 0;
_flagpanel = 0;
 //BA.debugLineNum = 394;BA.debugLine="flagpanel = 0";
_flagpanel = (int) (0);
 //BA.debugLineNum = 395;BA.debugLine="flagerpic =  job.JobName.SubString(7)";
_flagerpic = (int)(Double.parseDouble(_job._jobname.substring((int) (7))));
 //BA.debugLineNum = 397;BA.debugLine="Dim Container As AHPageContainer";
_container = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 398;BA.debugLine="Dim Pager As AHViewPager";
_pager = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 399;BA.debugLine="Container.Initialize";
_container.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 401;BA.debugLine="Try";
try { //BA.debugLineNum = 404;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 405;BA.debugLine="parser.Initialize( job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 406;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 407;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group161 = _root;
final int groupLen161 = group161.getSize()
;int index161 = 0;
;
for (; index161 < groupLen161;index161++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group161.Get(index161)));
 //BA.debugLineNum = 408;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
 //BA.debugLineNum = 409;BA.debugLine="image = extra.image_address & image.Replace";
_image = mostCurrent._extra._image_address+_image.replace(".jpg","-600x600.jpg");
 //BA.debugLineNum = 410;BA.debugLine="Dim filename  As String = image.SubString(i";
_filename = _image.substring((int) (_image.lastIndexOf("/")+1));
 //BA.debugLineNum = 411;BA.debugLine="pan(flagpanel).Initialize(\"\")";
_pan[_flagpanel].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 413;BA.debugLine="procimage(extra.procimg_flag).Initialize(\"\"";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 414;BA.debugLine="If File.Exists(File.DirInternalCache,filenam";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 416;BA.debugLine="procimage(extra.procimg_flag).Bitmap = Loa";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename).getObject()));
 }else {
 //BA.debugLineNum = 418;BA.debugLine="extra.main_download_product(extra.procimg_";
mostCurrent._extra._main_download_product(mostCurrent.activityBA,BA.NumberToString(mostCurrent._extra._procimg_flag),_image);
 //BA.debugLineNum = 419;BA.debugLine="procimage(extra.procimg_flag).Bitmap = Loa";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"main_defult_product.jpg").getObject()));
 };
 //BA.debugLineNum = 421;BA.debugLine="procimage(extra.procimg_flag).Gravity = Gra";
mostCurrent._procimage[mostCurrent._extra._procimg_flag].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 422;BA.debugLine="pan(flagpanel).AddView(procimage(extra.proc";
_pan[_flagpanel].AddView((android.view.View)(mostCurrent._procimage[mostCurrent._extra._procimg_flag].getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 423;BA.debugLine="Container.AddPageAt(pan(flagpanel), \"Main\",";
_container.AddPageAt((android.view.View)(_pan[_flagpanel].getObject()),"Main",(int) (0));
 //BA.debugLineNum = 424;BA.debugLine="flagpanel = flagpanel + 1";
_flagpanel = (int) (_flagpanel+1);
 //BA.debugLineNum = 425;BA.debugLine="extra.procimg_flag = extra.procimg_flag + 1";
mostCurrent._extra._procimg_flag = (int) (mostCurrent._extra._procimg_flag+1);
 }
};
 //BA.debugLineNum = 427;BA.debugLine="Log(\"indexer \" & extra.procimg_flag )";
anywheresoftware.b4a.keywords.Common.Log("indexer "+BA.NumberToString(mostCurrent._extra._procimg_flag));
 //BA.debugLineNum = 428;BA.debugLine="extra.procimg_count(extra.flag_procpnl) = fl";
mostCurrent._extra._procimg_count[mostCurrent._extra._flag_procpnl] = _flagpanel;
 //BA.debugLineNum = 430;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
_pager.Initialize2(mostCurrent.activityBA,_container,"Pager");
 //BA.debugLineNum = 431;BA.debugLine="product_ScrollView(flagerpic).Panel.AddView(";
mostCurrent._product_scrollview[_flagerpic].getPanel().AddView((android.view.View)(_pager.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA));
 } 
       catch (Exception e184) {
			processBA.setLastException(e184); //BA.debugLineNum = 433;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 };
 //BA.debugLineNum = 436;BA.debugLine="Try";
try { } 
       catch (Exception e189) {
			processBA.setLastException(e189); //BA.debugLineNum = 439;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 441;BA.debugLine="Try";
try { //BA.debugLineNum = 442;BA.debugLine="If job.JobName.SubString2(0,8) = \"textproc\" T";
if ((_job._jobname.substring((int) (0),(int) (8))).equals("textproc")) { 
 //BA.debugLineNum = 443;BA.debugLine="Dim flager As Int";
_flager = 0;
 //BA.debugLineNum = 444;BA.debugLine="flager =  job.JobName.SubString(8)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (8))));
 //BA.debugLineNum = 445;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 446;BA.debugLine="s = job.GetString.Replace(\"&lt;\",\"\").Replace";
_s = _job._getstring().replace("&lt;","").replace("p&gt;","").replace("br&gt;","").replace("/&lt;","").replace("/p&gt;","").replace("/br&gt;","").replace("p style=&quot;text-align: justify; &quot;&gt;","");
 //BA.debugLineNum = 447;BA.debugLine="s =s.Replace(\"amp;\",\"\").Replace(\"nbsp;\",\"\").";
_s = _s.replace("amp;","").replace("nbsp;","").replace("/null","");
 //BA.debugLineNum = 449;BA.debugLine="Dim RTLJustify1 As Label";
_rtljustify1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 450;BA.debugLine="RTLJustify1.Initialize(\"\")";
_rtljustify1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 451;BA.debugLine="RTLJustify1.Text = s";
_rtljustify1.setText(BA.ObjectToCharSequence(_s));
 //BA.debugLineNum = 452;BA.debugLine="RTLJustify1.Typeface = Typeface.LoadFromAsse";
_rtljustify1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 454;BA.debugLine="RTLJustify1.TextColor = Colors.rgb(89, 89, 8";
_rtljustify1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (89),(int) (89),(int) (89)));
 //BA.debugLineNum = 455;BA.debugLine="RTLJustify1.Gravity = Gravity.FILL";
_rtljustify1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 456;BA.debugLine="RTLJustify1.TextSize = 10dip";
_rtljustify1.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 458;BA.debugLine="Dim Obj1 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 459;BA.debugLine="Obj1.Target = RTLJustify1";
_obj1.Target = (Object)(_rtljustify1.getObject());
 //BA.debugLineNum = 460;BA.debugLine="Obj1.RunMethod3(\"setLineSpacing\", 1, \"java.l";
_obj1.RunMethod3("setLineSpacing",BA.NumberToString(1),"java.lang.float",BA.NumberToString(1.5),"java.lang.float");
 //BA.debugLineNum = 461;BA.debugLine="product_ScrollView(flager).Panel.AddView(RTL";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_rtljustify1.getObject()),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185)));
 };
 } 
       catch (Exception e211) {
			processBA.setLastException(e211); //BA.debugLineNum = 465;BA.debugLine="Log(\"erro textproc job\")";
anywheresoftware.b4a.keywords.Common.Log("erro textproc job");
 };
 //BA.debugLineNum = 468;BA.debugLine="Try";
try { //BA.debugLineNum = 469;BA.debugLine="If job.JobName.SubString2(0,18)=\"downloadimgp";
if ((_job._jobname.substring((int) (0),(int) (18))).equals("downloadimgproduct")) { 
 //BA.debugLineNum = 471;BA.debugLine="Dim index As Int";
_index = 0;
 //BA.debugLineNum = 472;BA.debugLine="Dim name As String";
_name = "";
 //BA.debugLineNum = 473;BA.debugLine="index = job.JobName.SubString2(job.JobName.I";
_index = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
 //BA.debugLineNum = 474;BA.debugLine="name = job.JobName.SubString(job.JobName.Las";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
 //BA.debugLineNum = 475;BA.debugLine="Log(index)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_index));
 //BA.debugLineNum = 476;BA.debugLine="Log(name)";
anywheresoftware.b4a.keywords.Common.Log(_name);
 //BA.debugLineNum = 477;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 478;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 479;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
 //BA.debugLineNum = 480;BA.debugLine="OutStream.Close";
_outstream.Close();
 //BA.debugLineNum = 481;BA.debugLine="procimage(index).Bitmap = LoadBitmap(File.Di";
mostCurrent._procimage[_index].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e228) {
			processBA.setLastException(e228); //BA.debugLineNum = 484;BA.debugLine="Log(\"error downloadimgproduct-- job\")";
anywheresoftware.b4a.keywords.Common.Log("error downloadimgproduct-- job");
 };
 //BA.debugLineNum = 487;BA.debugLine="Try";
try { //BA.debugLineNum = 488;BA.debugLine="If job.JobName.SubString2(0,19)=\"downloadimgc";
if ((_job._jobname.substring((int) (0),(int) (19))).equals("downloadimgcategory")) { 
 //BA.debugLineNum = 489;BA.debugLine="Dim index As Int";
_index = 0;
 //BA.debugLineNum = 490;BA.debugLine="Dim name As String";
_name = "";
 //BA.debugLineNum = 491;BA.debugLine="index = job.JobName.SubString2(job.JobName.I";
_index = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
 //BA.debugLineNum = 492;BA.debugLine="name = job.JobName.SubString(job.JobName.Las";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
 //BA.debugLineNum = 493;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 494;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 495;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
 //BA.debugLineNum = 496;BA.debugLine="OutStream.Close";
_outstream.Close();
 //BA.debugLineNum = 497;BA.debugLine="categoryimg(index).Bitmap = LoadBitmap(File.";
mostCurrent._categoryimg[_index].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e243) {
			processBA.setLastException(e243); //BA.debugLineNum = 500;BA.debugLine="Log(\"error downloadimglastproc job\")";
anywheresoftware.b4a.keywords.Common.Log("error downloadimglastproc job");
 };
 //BA.debugLineNum = 502;BA.debugLine="Try";
try { //BA.debugLineNum = 503;BA.debugLine="If job.JobName.SubString2(0,19)=\"downloadimgl";
if ((_job._jobname.substring((int) (0),(int) (19))).equals("downloadimglastproc")) { 
 //BA.debugLineNum = 504;BA.debugLine="Dim index As Int";
_index = 0;
 //BA.debugLineNum = 505;BA.debugLine="Dim name As String";
_name = "";
 //BA.debugLineNum = 506;BA.debugLine="index = job.JobName.SubString2(job.JobName.I";
_index = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
 //BA.debugLineNum = 507;BA.debugLine="name = job.JobName.SubString(job.JobName.Las";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
 //BA.debugLineNum = 508;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 509;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 510;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
 //BA.debugLineNum = 511;BA.debugLine="OutStream.Close";
_outstream.Close();
 //BA.debugLineNum = 512;BA.debugLine="lastproduct_ImageView(index).Bitmap = LoadBi";
mostCurrent._lastproduct_imageview[_index].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e258) {
			processBA.setLastException(e258); //BA.debugLineNum = 515;BA.debugLine="Log(\"error downloadimglastproc job\")";
anywheresoftware.b4a.keywords.Common.Log("error downloadimglastproc job");
 };
 //BA.debugLineNum = 517;BA.debugLine="Try";
try { //BA.debugLineNum = 519;BA.debugLine="If job.JobName.SubString2(0,8)=\"nameproc\" The";
if ((_job._jobname.substring((int) (0),(int) (8))).equals("nameproc")) { 
 //BA.debugLineNum = 522;BA.debugLine="Dim flager As Int";
_flager = 0;
 //BA.debugLineNum = 523;BA.debugLine="flager =  job.JobName.SubString(8)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (8))));
 //BA.debugLineNum = 525;BA.debugLine="Dim d1 As String";
_d1 = "";
 //BA.debugLineNum = 526;BA.debugLine="d1 = job.GetString.SubString2(0,job.GetStrin";
_d1 = _job._getstring().substring((int) (0),_job._getstring().indexOf("^"));
 //BA.debugLineNum = 529;BA.debugLine="Dim propert As Label";
_propert = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 530;BA.debugLine="propert.Initialize(\"propert\")";
_propert.Initialize(mostCurrent.activityBA,"propert");
 //BA.debugLineNum = 531;BA.debugLine="propert.TextColor = Colors.Black";
_propert.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 532;BA.debugLine="propert.Gravity = Gravity.CENTER";
_propert.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 533;BA.debugLine="propert.Typeface = Typeface.LoadFromAssets(\"";
_propert.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 534;BA.debugLine="propert.Text = \"مشخصات\"";
_propert.setText(BA.ObjectToCharSequence("مشخصات"));
 //BA.debugLineNum = 535;BA.debugLine="propert.Tag = job.GetString.SubString2(job.G";
_propert.setTag((Object)(_job._getstring().substring((int) (_job._getstring().indexOf("^")+1),_job._getstring().length())+"#"+BA.NumberToString(_flager)));
 //BA.debugLineNum = 536;BA.debugLine="product_ScrollView(flager).Panel.AddView(pro";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_propert.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 539;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 540;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 542;BA.debugLine="parser.Initialize(d1)";
_parser.Initialize(_d1);
 //BA.debugLineNum = 543;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 544;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group278 = _root;
final int groupLen278 = group278.getSize()
;int index278 = 0;
;
for (; index278 < groupLen278;index278++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group278.Get(index278)));
 //BA.debugLineNum = 545;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
 //BA.debugLineNum = 546;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 547;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
 }
};
 //BA.debugLineNum = 555;BA.debugLine="Dim lbl_titlen As Label";
_lbl_titlen = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 556;BA.debugLine="lbl_titlen.Initialize(\"\")";
_lbl_titlen.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 557;BA.debugLine="lbl_titlen.Text = model";
_lbl_titlen.setText(BA.ObjectToCharSequence(_model));
 //BA.debugLineNum = 558;BA.debugLine="lbl_titlen.Typeface = Typeface.LoadFromAsset";
_lbl_titlen.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 559;BA.debugLine="lbl_titlen.TextSize = 8dip";
_lbl_titlen.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 560;BA.debugLine="lbl_titlen.Gravity = Gravity.RIGHT";
_lbl_titlen.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 561;BA.debugLine="lbl_titlen.TextColor = Colors.rgb(169, 169,";
_lbl_titlen.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (169),(int) (169),(int) (169)));
 //BA.debugLineNum = 562;BA.debugLine="product_ScrollView(flager).Panel.AddView(lbl";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_lbl_titlen.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 566;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
 //BA.debugLineNum = 567;BA.debugLine="Dim lbl_title As Label";
_lbl_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 568;BA.debugLine="lbl_title.Initialize(\"\")";
_lbl_title.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 569;BA.debugLine="lbl_title.Text =  \"\"";
_lbl_title.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 570;BA.debugLine="extra.product_title =  \"\"";
mostCurrent._extra._product_title = "";
 //BA.debugLineNum = 571;BA.debugLine="lbl_title.Typeface = Typeface.LoadFromAssets";
_lbl_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 572;BA.debugLine="lbl_title.TextSize = 11dip";
_lbl_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 573;BA.debugLine="lbl_title.Gravity = Gravity.RIGHT";
_lbl_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 574;BA.debugLine="lbl_title.TextColor = Colors.Black";
_lbl_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 575;BA.debugLine="product_ScrollView(flager).Panel.AddView(lbl";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_lbl_title.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (85))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 576;BA.debugLine="lbl_title.Text = name";
_lbl_title.setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 578;BA.debugLine="headerproctxt(flager).Text =name";
mostCurrent._headerproctxt[_flager].setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 579;BA.debugLine="Dim total As Label";
_total = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 580;BA.debugLine="total.Initialize(\"\")";
_total.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 581;BA.debugLine="total.TextColor = Colors.rgb(102, 187, 106)";
_total.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 582;BA.debugLine="total.Gravity = Gravity.LEFT";
_total.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 583;BA.debugLine="total.Typeface = Typeface.LoadFromAssets(\"ye";
_total.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 584;BA.debugLine="total.Text = price.SubString2(0,price.LastIn";
_total.setText(BA.ObjectToCharSequence(_price.substring((int) (0),_price.lastIndexOf("."))+" "+"تومان"));
 //BA.debugLineNum = 585;BA.debugLine="total.TextSize = 13dip";
_total.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
 //BA.debugLineNum = 586;BA.debugLine="product_ScrollView(flager).Panel.AddView(tot";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(_total.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (178))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 };
 } 
       catch (Exception e313) {
			processBA.setLastException(e313); //BA.debugLineNum = 599;BA.debugLine="Log(\"erro nameproc job\")";
anywheresoftware.b4a.keywords.Common.Log("erro nameproc job");
 };
 //BA.debugLineNum = 602;BA.debugLine="Try";
try { //BA.debugLineNum = 603;BA.debugLine="If job.JobName.SubString2(0,12) =\"loadcategor";
if ((_job._jobname.substring((int) (0),(int) (12))).equals("loadcategory")) { 
 //BA.debugLineNum = 604;BA.debugLine="Dim flager As Int";
_flager = 0;
 //BA.debugLineNum = 605;BA.debugLine="flager =  job.JobName.SubString(12)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (12))));
 //BA.debugLineNum = 607;BA.debugLine="category_hscrollview.Initialize(100%x,\"\")";
mostCurrent._category_hscrollview.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
 //BA.debugLineNum = 608;BA.debugLine="category_hscrollview.Panel.Height = 50dip";
mostCurrent._category_hscrollview.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 609;BA.debugLine="product_ScrollView(flager).Panel.AddView(cat";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(mostCurrent._category_hscrollview.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 611;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 612;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 613;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 614;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 615;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group326 = _root;
final int groupLen326 = group326.getSize()
;int index326 = 0;
;
for (; index326 < groupLen326;index326++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group326.Get(index326)));
 //BA.debugLineNum = 616;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 617;BA.debugLine="Dim text As String = colroot.Get(\"name\")";
_text = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 618;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 619;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
 //BA.debugLineNum = 620;BA.debugLine="lable.Color = Colors.rgb(102, 187, 106)";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 621;BA.debugLine="lable.TextColor = Colors.White";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 622;BA.debugLine="lable.Gravity = Gravity.CENTER";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 623;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 624;BA.debugLine="lable.TextSize = \"20\"";
_lable.setTextSize((float)(Double.parseDouble("20")));
 //BA.debugLineNum = 625;BA.debugLine="lable.Text = text";
_lable.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 626;BA.debugLine="category_hscrollview.Panel.AddView(lable,l";
mostCurrent._category_hscrollview.getPanel().AddView((android.view.View)(_lable.getObject()),_left,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2.5)),(int) ((_text.length()*30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 //BA.debugLineNum = 627;BA.debugLine="left =( text.Length * 30  ) + left +8dip";
_left = (int) ((_text.length()*30)+_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8)));
 }
};
 //BA.debugLineNum = 629;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 630;BA.debugLine="r.Target = category_hscrollview";
_r.Target = (Object)(mostCurrent._category_hscrollview.getObject());
 //BA.debugLineNum = 631;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 632;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 634;BA.debugLine="category_hscrollview.Panel.Width = left";
mostCurrent._category_hscrollview.getPanel().setWidth(_left);
 //BA.debugLineNum = 635;BA.debugLine="category_hscrollview.FullScroll(True)";
mostCurrent._category_hscrollview.FullScroll(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 636;BA.debugLine="category_hscrollview.ScrollPosition = 50dip";
mostCurrent._category_hscrollview.setScrollPosition(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 };
 //BA.debugLineNum = 638;BA.debugLine="If job.JobName.SubString2(0,11) =\"lastproduct";
if ((_job._jobname.substring((int) (0),(int) (11))).equals("lastproduct")) { 
 //BA.debugLineNum = 639;BA.debugLine="Dim flager As Int";
_flager = 0;
 //BA.debugLineNum = 640;BA.debugLine="flager =  job.JobName.SubString(11)";
_flager = (int)(Double.parseDouble(_job._jobname.substring((int) (11))));
 //BA.debugLineNum = 642;BA.debugLine="scrollview_lastproduct.Initialize(100%x,\"\")";
mostCurrent._scrollview_lastproduct.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
 //BA.debugLineNum = 643;BA.debugLine="scrollview_lastproduct.Panel.Height = 450dip";
mostCurrent._scrollview_lastproduct.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
 //BA.debugLineNum = 644;BA.debugLine="product_ScrollView(flager).Panel.AddView(scr";
mostCurrent._product_scrollview[_flager].getPanel().AddView((android.view.View)(mostCurrent._scrollview_lastproduct.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
 //BA.debugLineNum = 646;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 647;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 648;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 649;BA.debugLine="Dim indexf As Int = flager &  1";
_indexf = (int)(Double.parseDouble(BA.NumberToString(_flager)+BA.NumberToString(1)));
 //BA.debugLineNum = 650;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 651;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group359 = _root;
final int groupLen359 = group359.getSize()
;int index359 = 0;
;
for (; index359 < groupLen359;index359++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group359.Get(index359)));
 //BA.debugLineNum = 652;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
 //BA.debugLineNum = 653;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
 //BA.debugLineNum = 654;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
 //BA.debugLineNum = 655;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 656;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 657;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 658;BA.debugLine="panel.Initialize(\"lastproduct_panel\")";
_panel.Initialize(mostCurrent.activityBA,"lastproduct_panel");
 //BA.debugLineNum = 659;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
 //BA.debugLineNum = 660;BA.debugLine="panel.Color = Colors.White";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 661;BA.debugLine="scrollview_lastproduct.Panel.AddView(panel,";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_panel.getObject()),_left,(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (225))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 662;BA.debugLine="extra.InitPanel(panel,1dip,Colors.White,Col";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_panel,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)));
 //BA.debugLineNum = 663;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 664;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
 //BA.debugLineNum = 665;BA.debugLine="lable.Color = Colors.White";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 666;BA.debugLine="lable.TextColor = Colors.rgb(65, 65, 65)";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (65),(int) (65),(int) (65)));
 //BA.debugLineNum = 667;BA.debugLine="lable.Gravity = Gravity.RIGHT";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 668;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"y";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 669;BA.debugLine="lable.TextSize = \"16\"";
_lable.setTextSize((float)(Double.parseDouble("16")));
 //BA.debugLineNum = 670;BA.debugLine="lable.Text = name";
_lable.setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 671;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable,";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 673;BA.debugLine="Dim lable2 As Label";
_lable2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 674;BA.debugLine="lable2.Initialize(\"lable2\")";
_lable2.Initialize(mostCurrent.activityBA,"lable2");
 //BA.debugLineNum = 675;BA.debugLine="lable2.Color = Colors.White";
_lable2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 676;BA.debugLine="lable2.TextColor = Colors.rgb(102, 187, 106";
_lable2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 677;BA.debugLine="lable2.Typeface = Typeface.DEFAULT_BOLD";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 678;BA.debugLine="lable2.Gravity = Gravity.LEFT";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 679;BA.debugLine="lable2.Gravity = Gravity.CENTER_VERTICAL";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 680;BA.debugLine="lable2.Typeface = Typeface.LoadFromAssets(\"";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 681;BA.debugLine="lable2.TextSize = \"16\"";
_lable2.setTextSize((float)(Double.parseDouble("16")));
 //BA.debugLineNum = 682;BA.debugLine="lable2.Text = price";
_lable2.setText(BA.ObjectToCharSequence(_price));
 //BA.debugLineNum = 683;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable2";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable2.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (195)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)));
 //BA.debugLineNum = 684;BA.debugLine="image= image.Replace(\".jpg\",\"-600x600.jpg\")";
_image = _image.replace(".jpg","-600x600.jpg").replace("/catalog/categoty/","/cache/catalog/categoty/");
 //BA.debugLineNum = 685;BA.debugLine="lastproduct_ImageView(indexf).Initialize(\"l";
mostCurrent._lastproduct_imageview[_indexf].Initialize(mostCurrent.activityBA,"lastproduct_ImageView");
 //BA.debugLineNum = 686;BA.debugLine="lastproduct_ImageView(indexf).Tag = id";
mostCurrent._lastproduct_imageview[_indexf].setTag((Object)(_id));
 //BA.debugLineNum = 687;BA.debugLine="Dim filename As String=image.SubString2(ima";
_filename = _image.substring((int) (_image.lastIndexOf("/")+1),_image.length());
 //BA.debugLineNum = 689;BA.debugLine="If File.Exists(File.DirInternalCache, filen";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 690;BA.debugLine="Try";
try { //BA.debugLineNum = 691;BA.debugLine="lastproduct_ImageView(indexf).Bitmap = Lo";
mostCurrent._lastproduct_imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_filename).getObject()));
 } 
       catch (Exception e399) {
			processBA.setLastException(e399); //BA.debugLineNum = 693;BA.debugLine="Log(\"erro 	lastproduct_ImageView(indexf).";
anywheresoftware.b4a.keywords.Common.Log("erro 	lastproduct_ImageView(indexf).");
 };
 }else {
 //BA.debugLineNum = 697;BA.debugLine="lastproduct_ImageView(indexf).Bitmap = Loa";
mostCurrent._lastproduct_imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg").getObject()));
 //BA.debugLineNum = 698;BA.debugLine="extra.main_download_lastproduct(indexf,ima";
mostCurrent._extra._main_download_lastproduct(mostCurrent.activityBA,BA.NumberToString(_indexf),_image);
 };
 //BA.debugLineNum = 701;BA.debugLine="lastproduct_ImageView(indexf).Gravity = Gra";
mostCurrent._lastproduct_imageview[_indexf].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 702;BA.debugLine="scrollview_lastproduct.Panel.AddView(lastpr";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(mostCurrent._lastproduct_imageview[_indexf].getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 704;BA.debugLine="Dim CanvasGraph As Canvas";
_canvasgraph = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 705;BA.debugLine="CanvasGraph.Initialize(panel)";
_canvasgraph.Initialize((android.view.View)(_panel.getObject()));
 //BA.debugLineNum = 707;BA.debugLine="CanvasGraph.DrawLine(0,140dip,160dip,140dip";
_canvasgraph.DrawLine((float) (0),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 708;BA.debugLine="left = left + 170dip";
_left = (int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
 //BA.debugLineNum = 709;BA.debugLine="indexf= indexf + 1";
_indexf = (int) (_indexf+1);
 }
};
 //BA.debugLineNum = 711;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 712;BA.debugLine="r.Target = scrollview_lastproduct";
_r.Target = (Object)(mostCurrent._scrollview_lastproduct.getObject());
 //BA.debugLineNum = 713;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\"";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 714;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.l";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 715;BA.debugLine="scrollview_lastproduct.Panel.Width = left";
mostCurrent._scrollview_lastproduct.getPanel().setWidth(_left);
 //BA.debugLineNum = 716;BA.debugLine="product_ScrollView(flager).Panel.Height = 50";
mostCurrent._product_scrollview[_flager].getPanel().setHeight((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (295))));
 };
 } 
       catch (Exception e421) {
			processBA.setLastException(e421); //BA.debugLineNum = 720;BA.debugLine="Log(\"eroro lastproduct job\")";
anywheresoftware.b4a.keywords.Common.Log("eroro lastproduct job");
 };
 break; }
}
;
 };
 //BA.debugLineNum = 725;BA.debugLine="End Sub";
return "";
}
public static String  _lastproduct_imageview_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img = null;
 //BA.debugLineNum = 1092;BA.debugLine="Sub lastproduct_ImageView_click()";
 //BA.debugLineNum = 1093;BA.debugLine="Dim img As ImageView";
_img = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1094;BA.debugLine="img = Sender";
_img.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 1095;BA.debugLine="extra.product_id_toshow = img.Tag";
mostCurrent._extra._product_id_toshow = (int)(BA.ObjectToNumber(_img.getTag()));
 //BA.debugLineNum = 1097;BA.debugLine="product_ScrollView(extra.flag_procpnl).Initialize";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].Initialize(mostCurrent.activityBA,(int) (500));
 //BA.debugLineNum = 1098;BA.debugLine="product_ScrollView(extra.flag_procpnl).Color = Co";
mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
 //BA.debugLineNum = 1099;BA.debugLine="Activity.AddView(product_ScrollView(extra.flag_pr";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._product_scrollview[mostCurrent._extra._flag_procpnl].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1100;BA.debugLine="loadroc(extra.flag_procpnl)";
_loadroc(mostCurrent._extra._flag_procpnl);
 //BA.debugLineNum = 1101;BA.debugLine="extra.flag_procpnl = extra.flag_procpnl + 1";
mostCurrent._extra._flag_procpnl = (int) (mostCurrent._extra._flag_procpnl+1);
 //BA.debugLineNum = 1102;BA.debugLine="End Sub";
return "";
}
public static String  _load_indexjob(anywheresoftware.b4a.samples.httputils2.httpjob _job,boolean _create) throws Exception{
int _lastpage = 0;
int _i = 0;
String _id = "";
String _img = "";
String _modle = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
int _x = 0;
 //BA.debugLineNum = 111;BA.debugLine="Sub load_indexjob(job As HttpJob,create As Boolean";
 //BA.debugLineNum = 112;BA.debugLine="Try";
try { //BA.debugLineNum = 114;BA.debugLine="Dim lastpage As Int";
_lastpage = 0;
 //BA.debugLineNum = 115;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 116;BA.debugLine="If create=True Then";
if (_create==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 117;BA.debugLine="Dim id As String = job.GetString.SubString2(0, j";
_id = _job._getstring().substring((int) (0),_job._getstring().indexOf("*"));
 //BA.debugLineNum = 118;BA.debugLine="Dim img As String = job.GetString.SubString2(job";
_img = _job._getstring().substring((int) (_job._getstring().indexOf("*")+3),_job._getstring().indexOf("$"));
 //BA.debugLineNum = 119;BA.debugLine="Dim modle As String = job.GetString.SubString2(j";
_modle = _job._getstring().substring((int) (_job._getstring().indexOf("$")+3),_job._getstring().length());
 //BA.debugLineNum = 123;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 124;BA.debugLine="parser.Initialize(id)";
_parser.Initialize(_id);
 //BA.debugLineNum = 125;BA.debugLine="index_retrive_list= parser.NextArray";
mostCurrent._index_retrive_list = _parser.NextArray();
 //BA.debugLineNum = 126;BA.debugLine="parser.Initialize(img)";
_parser.Initialize(_img);
 //BA.debugLineNum = 127;BA.debugLine="index_retrive_list_img= parser.NextArray";
mostCurrent._index_retrive_list_img = _parser.NextArray();
 //BA.debugLineNum = 128;BA.debugLine="parser.Initialize(modle)";
_parser.Initialize(_modle);
 //BA.debugLineNum = 129;BA.debugLine="index_retrive_list_model= parser.NextArray";
mostCurrent._index_retrive_list_model = _parser.NextArray();
 //BA.debugLineNum = 130;BA.debugLine="i = 1";
_i = (int) (1);
 //BA.debugLineNum = 131;BA.debugLine="lastpage = 20";
_lastpage = (int) (20);
 }else {
 //BA.debugLineNum = 133;BA.debugLine="i = extra.index_ob_olaviyat_load";
_i = mostCurrent._extra._index_ob_olaviyat_load;
 //BA.debugLineNum = 134;BA.debugLine="lastpage = extra.index_ob_olaviyat_load + 20";
_lastpage = (int) (mostCurrent._extra._index_ob_olaviyat_load+20);
 };
 //BA.debugLineNum = 137;BA.debugLine="do  While (i<lastpage Or  extra.index_ob_olaviyat";
while ((_i<_lastpage || mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)]!=1)) {
 //BA.debugLineNum = 140;BA.debugLine="Select extra.index_ob_olaviyat(i-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)],(int) (1),(int) (22),(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (11),(int) (111))) {
case 0: {
 //BA.debugLineNum = 142;BA.debugLine="Dim x As Int = Rnd(1,5)";
_x = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
 //BA.debugLineNum = 144;BA.debugLine="If x = 1 Then";
if (_x==1) { 
 //BA.debugLineNum = 145;BA.debugLine="index_draw(\"larg\",i,index_retrive_list.Get(i)";
_index_draw("larg",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 //BA.debugLineNum = 148;BA.debugLine="If x = 2 Then";
if (_x==2) { 
 //BA.debugLineNum = 149;BA.debugLine="index_draw(\"medium\",i,index_retrive_list.Get(";
_index_draw("medium",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 //BA.debugLineNum = 152;BA.debugLine="If x = 3 Then";
if (_x==3) { 
 //BA.debugLineNum = 153;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 //BA.debugLineNum = 156;BA.debugLine="If x = 4 Then";
if (_x==4) { 
 //BA.debugLineNum = 157;BA.debugLine="extra.index_ob_olaviyat(i-1) = 4";
mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)] = (int) (4);
 //BA.debugLineNum = 158;BA.debugLine="index_draw(\"medium\",i,index_retrive_list.Get(";
_index_draw("medium",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 break; }
case 1: {
 //BA.debugLineNum = 162;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 2: {
 //BA.debugLineNum = 164;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 3: {
 //BA.debugLineNum = 167;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 4: {
 //BA.debugLineNum = 169;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 5: {
 //BA.debugLineNum = 171;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 6: {
 //BA.debugLineNum = 173;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 7: {
 //BA.debugLineNum = 175;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 8: {
 //BA.debugLineNum = 180;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
}
;
 //BA.debugLineNum = 182;BA.debugLine="i=i+1";
_i = (int) (_i+1);
 }
;
 } 
       catch (Exception e58) {
			processBA.setLastException(e58); //BA.debugLineNum = 186;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _loadroc(int _flag) throws Exception{
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
 //BA.debugLineNum = 905;BA.debugLine="Sub loadroc(flag As Int)";
 //BA.debugLineNum = 908;BA.debugLine="headerproc(flag).Initialize(\"\")";
mostCurrent._headerproc[_flag].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 909;BA.debugLine="headerproc(flag).Color = Colors.rgb(26, 188, 156)";
mostCurrent._headerproc[_flag].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (26),(int) (188),(int) (156)));
 //BA.debugLineNum = 910;BA.debugLine="Activity.AddView(headerproc(flag),0,0,100%x,55dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._headerproc[_flag].getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)));
 //BA.debugLineNum = 913;BA.debugLine="headerproctxt(flag).Initialize(\"\")";
mostCurrent._headerproctxt[_flag].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 914;BA.debugLine="headerproctxt(flag).Gravity = Bit.Or(Gravity.CENT";
mostCurrent._headerproctxt[_flag].setGravity(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL,anywheresoftware.b4a.keywords.Common.Gravity.RIGHT));
 //BA.debugLineNum = 915;BA.debugLine="headerproctxt(flag).Typeface = Typeface.LoadFromA";
mostCurrent._headerproctxt[_flag].setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 916;BA.debugLine="headerproctxt(flag).TextSize = 8dip";
mostCurrent._headerproctxt[_flag].setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 917;BA.debugLine="headerproctxt(flag).Text = \"در حال بارگزاری\"";
mostCurrent._headerproctxt[_flag].setText(BA.ObjectToCharSequence("در حال بارگزاری"));
 //BA.debugLineNum = 918;BA.debugLine="headerproctxt(flag).TextColor = Colors.White";
mostCurrent._headerproctxt[_flag].setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 919;BA.debugLine="headerproc(flag).AddView(headerproctxt(flag),0,5d";
mostCurrent._headerproc[_flag].AddView((android.view.View)(mostCurrent._headerproctxt[_flag].getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 923;BA.debugLine="Dim download As HttpJob";
_download = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 924;BA.debugLine="download.Initialize(\"nameproc\" & flag,Me)";
_download._initialize(processBA,"nameproc"+BA.NumberToString(_flag),index.getObject());
 //BA.debugLineNum = 925;BA.debugLine="download.PostString(extra.api,\"op=product&id=\" &";
_download._poststring(mostCurrent._extra._api,"op=product&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
 //BA.debugLineNum = 928;BA.debugLine="Dim downloadtext As HttpJob";
_downloadtext = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 929;BA.debugLine="downloadtext.Initialize(\"textproc\"  & flag ,Me)";
_downloadtext._initialize(processBA,"textproc"+BA.NumberToString(_flag),index.getObject());
 //BA.debugLineNum = 930;BA.debugLine="downloadtext.PostString(extra.api,\"op=productdesc";
_downloadtext._poststring(mostCurrent._extra._api,"op=productdescription&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
 //BA.debugLineNum = 932;BA.debugLine="Dim downloadpic As HttpJob";
_downloadpic = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 933;BA.debugLine="downloadpic.Initialize(\"picproc\" & flag,Me)";
_downloadpic._initialize(processBA,"picproc"+BA.NumberToString(_flag),index.getObject());
 //BA.debugLineNum = 934;BA.debugLine="downloadpic.PostString(extra.api,\"op=productpics&";
_downloadpic._poststring(mostCurrent._extra._api,"op=productpics&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
 //BA.debugLineNum = 937;BA.debugLine="Dim jober As HttpJob";
_jober = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 938;BA.debugLine="jober.Initialize(\"lastproduct\"  & flag ,Me)";
_jober._initialize(processBA,"lastproduct"+BA.NumberToString(_flag),index.getObject());
 //BA.debugLineNum = 939;BA.debugLine="jober.PostString(extra.api,\"op=lastproduct\")";
_jober._poststring(mostCurrent._extra._api,"op=lastproduct");
 //BA.debugLineNum = 941;BA.debugLine="Dim load_category As HttpJob";
_load_category = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 942;BA.debugLine="load_category.Initialize(\"loadcategory\" & flag,Me";
_load_category._initialize(processBA,"loadcategory"+BA.NumberToString(_flag),index.getObject());
 //BA.debugLineNum = 943;BA.debugLine="load_category.PostString(extra.api,\"op=category\")";
_load_category._poststring(mostCurrent._extra._api,"op=category");
 //BA.debugLineNum = 945;BA.debugLine="Dim moretext_data As String";
_moretext_data = "";
 //BA.debugLineNum = 946;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 947;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 948;BA.debugLine="pnl.Color = Colors.rgb(250, 250, 250)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
 //BA.debugLineNum = 949;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl,0,50%x";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 951;BA.debugLine="Dim pnl2 As Panel";
_pnl2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 952;BA.debugLine="pnl2.Initialize(\"\")";
_pnl2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 953;BA.debugLine="pnl2.Color = Colors.White";
_pnl2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 954;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl2,15dip";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (163))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 955;BA.debugLine="extra.InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl2,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
 //BA.debugLineNum = 957;BA.debugLine="Dim pnl3 As Panel";
_pnl3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 958;BA.debugLine="pnl3.Initialize(\"\")";
_pnl3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 959;BA.debugLine="pnl3.Color = Colors.White";
_pnl3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 960;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl3,15dip";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
 //BA.debugLineNum = 961;BA.debugLine="extra.InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl3,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
 //BA.debugLineNum = 963;BA.debugLine="Dim pnl4_moshakhasat As Panel";
_pnl4_moshakhasat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 964;BA.debugLine="pnl4_moshakhasat.Initialize(\"\")";
_pnl4_moshakhasat.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 965;BA.debugLine="pnl4_moshakhasat.Color = Colors.White";
_pnl4_moshakhasat.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 966;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl4_mosha";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl4_moshakhasat.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (289))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
 //BA.debugLineNum = 967;BA.debugLine="extra.InitPanel(pnl4_moshakhasat,2,1,Colors.rgb(2";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl4_moshakhasat,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
 //BA.debugLineNum = 969;BA.debugLine="moretext_data = \"در حال بارگذاری\"";
_moretext_data = "در حال بارگذاری";
 //BA.debugLineNum = 974;BA.debugLine="Dim pnl4_line As Panel";
_pnl4_line = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 975;BA.debugLine="pnl4_line.Initialize(\"\")";
_pnl4_line.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 976;BA.debugLine="pnl4_line.Color = Colors.rgb(179, 179, 179)";
_pnl4_line.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
 //BA.debugLineNum = 977;BA.debugLine="product_ScrollView(flag).Panel.AddView(pnl4_line,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pnl4_line.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 982;BA.debugLine="Dim moretext As Label";
_moretext = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 983;BA.debugLine="moretext.Initialize(\"moretext\")";
_moretext.Initialize(mostCurrent.activityBA,"moretext");
 //BA.debugLineNum = 984;BA.debugLine="moretext.TextColor = Colors.rgb(140, 140, 140)";
_moretext.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 985;BA.debugLine="moretext.Gravity = Gravity.CENTER";
_moretext.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 986;BA.debugLine="moretext.Typeface = Typeface.LoadFromAssets(\"yeka";
_moretext.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 987;BA.debugLine="moretext.TextSize = 10dip";
_moretext.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 988;BA.debugLine="moretext.Text = \"ادامه مطلب\"";
_moretext.setText(BA.ObjectToCharSequence("ادامه مطلب"));
 //BA.debugLineNum = 989;BA.debugLine="product_ScrollView(flag).Panel.AddView(moretext,1";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_moretext.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 991;BA.debugLine="Dim btn As Panel";
_btn = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 992;BA.debugLine="btn.Initialize(\"\")";
_btn.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 993;BA.debugLine="btn.Color = Colors.rgb(102, 187, 106)";
_btn.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 995;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 996;BA.debugLine="cd.Initialize(Colors.rgb(102, 187, 106), 5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 997;BA.debugLine="btn.Background = cd";
_btn.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 998;BA.debugLine="product_ScrollView(flag).Panel.AddView(btn,50dip,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_btn.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 1000;BA.debugLine="Dim buy As Label";
_buy = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1001;BA.debugLine="buy.Initialize(\"\")";
_buy.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1002;BA.debugLine="buy.TextColor = Colors.White";
_buy.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1003;BA.debugLine="buy.Gravity = Gravity.CENTER";
_buy.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 1004;BA.debugLine="buy.Typeface = Typeface.LoadFromAssets(\"yekan.ttf";
_buy.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1005;BA.debugLine="buy.TextSize = 12dip";
_buy.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
 //BA.debugLineNum = 1006;BA.debugLine="buy.Text = \"افزودن به سبد خرید\"";
_buy.setText(BA.ObjectToCharSequence("افزودن به سبد خرید"));
 //BA.debugLineNum = 1007;BA.debugLine="product_ScrollView(flag).Panel.AddView(buy,50dip,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_buy.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (65),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 1009;BA.debugLine="Dim buyicon As Label";
_buyicon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1010;BA.debugLine="buyicon.Initialize(\"\")";
_buyicon.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1011;BA.debugLine="Private FAe As FontAwesome";
_fae = new njdude.fontawesome.lib.fontawesome();
 //BA.debugLineNum = 1012;BA.debugLine="FAe.Initialize";
_fae._initialize(processBA);
 //BA.debugLineNum = 1013;BA.debugLine="buyicon.Gravity = Gravity.CENTER_VERTICAL";
_buyicon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 1014;BA.debugLine="buyicon.Typeface = Typeface.FONTAWESOME";
_buyicon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 1015;BA.debugLine="buyicon.TextColor = Colors.White";
_buyicon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1016;BA.debugLine="buyicon.TextSize = 13dip";
_buyicon.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
 //BA.debugLineNum = 1017;BA.debugLine="buyicon.Text = FAe.GetFontAwesomeIconByName(\"fa-c";
_buyicon.setText(BA.ObjectToCharSequence(_fae._getfontawesomeiconbyname("fa-cart-plus")));
 //BA.debugLineNum = 1018;BA.debugLine="product_ScrollView(flag).Panel.AddView(buyicon,70";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_buyicon.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 1023;BA.debugLine="Private FA As FontAwesome";
_fa = new njdude.fontawesome.lib.fontawesome();
 //BA.debugLineNum = 1024;BA.debugLine="FA.Initialize";
_fa._initialize(processBA);
 //BA.debugLineNum = 1025;BA.debugLine="Dim properticon As Label";
_properticon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1026;BA.debugLine="properticon.Initialize(\"\")";
_properticon.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1027;BA.debugLine="properticon.TextColor = Colors.Black";
_properticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1029;BA.debugLine="properticon.Gravity = Gravity.CENTER_VERTICAL";
_properticon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 1030;BA.debugLine="properticon.Typeface = Typeface.FONTAWESOME";
_properticon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 1031;BA.debugLine="properticon.Text = FA.GetFontAwesomeIconByName(\"f";
_properticon.setText(BA.ObjectToCharSequence(_fa._getfontawesomeiconbyname("fa-calendar-o")));
 //BA.debugLineNum = 1032;BA.debugLine="product_ScrollView(flag).Panel.AddView(propertico";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_properticon.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
 //BA.debugLineNum = 1034;BA.debugLine="Dim pic_sheare As ImageView";
_pic_sheare = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1035;BA.debugLine="pic_sheare.Initialize(\"sharepost\")";
_pic_sheare.Initialize(mostCurrent.activityBA,"sharepost");
 //BA.debugLineNum = 1036;BA.debugLine="pic_sheare.Gravity = Gravity.FILL";
_pic_sheare.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1037;BA.debugLine="pic_sheare.Tag = extra.product_id_toshow";
_pic_sheare.setTag((Object)(mostCurrent._extra._product_id_toshow));
 //BA.debugLineNum = 1038;BA.debugLine="pic_sheare.Bitmap = LoadBitmap(File.DirAssets,\"sh";
_pic_sheare.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sharing-interface.png").getObject()));
 //BA.debugLineNum = 1040;BA.debugLine="Dim pic_like As ImageView";
_pic_like = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1041;BA.debugLine="pic_like.Initialize(\"\")";
_pic_like.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1042;BA.debugLine="pic_like.Gravity = Gravity.FILL";
_pic_like.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 1043;BA.debugLine="pic_like.Bitmap = LoadBitmap(File.DirAssets,\"like";
_pic_like.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"like.png").getObject()));
 //BA.debugLineNum = 1046;BA.debugLine="Dim color As Label";
_color = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1047;BA.debugLine="color.Initialize(\"\")";
_color.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1048;BA.debugLine="color.TextColor = Colors.rgb(140, 140, 140)";
_color.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 1049;BA.debugLine="color.Gravity = Gravity.RIGHT";
_color.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 1050;BA.debugLine="color.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_color.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1051;BA.debugLine="color.Text = \"رنگ\"";
_color.setText(BA.ObjectToCharSequence("رنگ"));
 //BA.debugLineNum = 1052;BA.debugLine="color.TextSize = 9dip";
_color.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 1053;BA.debugLine="product_ScrollView(flag).Panel.AddView(color,0,50";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_color.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (270))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 1056;BA.debugLine="Dim garanti As Label";
_garanti = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1057;BA.debugLine="garanti.Initialize(\"\")";
_garanti.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1058;BA.debugLine="garanti.TextColor = Colors.rgb(140, 140, 140)";
_garanti.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 1059;BA.debugLine="garanti.Gravity = Gravity.RIGHT";
_garanti.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 1060;BA.debugLine="garanti.Typeface = Typeface.LoadFromAssets(\"yekan";
_garanti.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1061;BA.debugLine="garanti.Text = \"گارانتی\"";
_garanti.setText(BA.ObjectToCharSequence("گارانتی"));
 //BA.debugLineNum = 1062;BA.debugLine="garanti.TextSize = 9dip";
_garanti.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 1063;BA.debugLine="product_ScrollView(flag).Panel.AddView(garanti,0,";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_garanti.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 1066;BA.debugLine="Dim saler As Label";
_saler = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1067;BA.debugLine="saler.Initialize(\"\")";
_saler.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1068;BA.debugLine="saler.TextColor = Colors.rgb(140, 140, 140)";
_saler.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 1069;BA.debugLine="saler.Gravity = Gravity.RIGHT";
_saler.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 1070;BA.debugLine="saler.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_saler.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1071;BA.debugLine="saler.Text = \"فروشنده\"";
_saler.setText(BA.ObjectToCharSequence("فروشنده"));
 //BA.debugLineNum = 1072;BA.debugLine="saler.TextSize = 9dip";
_saler.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 1075;BA.debugLine="Dim header_title As Label";
_header_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1076;BA.debugLine="header_title.Initialize(\"\")";
_header_title.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1078;BA.debugLine="header_title.Gravity = Gravity.RIGHT";
_header_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 1079;BA.debugLine="header_title.Typeface = Typeface.LoadFromAssets(\"";
_header_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1080;BA.debugLine="header_title.TextColor = Colors.red";
_header_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1081;BA.debugLine="header_title.TextSize = 11dip";
_header_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 1082;BA.debugLine="header_title.Visible = False";
_header_title.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1085;BA.debugLine="product_ScrollView(flag).Panel.AddView(saler,0,50";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_saler.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 1086;BA.debugLine="product_ScrollView(flag).Panel.AddView(pic_sheare";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pic_sheare.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)));
 //BA.debugLineNum = 1087;BA.debugLine="product_ScrollView(flag).Panel.AddView(pic_like,8";
mostCurrent._product_scrollview[_flag].getPanel().AddView((android.view.View)(_pic_like.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)));
 //BA.debugLineNum = 1089;BA.debugLine="product_ScrollView(flag).Panel.Height = 2125";
mostCurrent._product_scrollview[_flag].getPanel().setHeight((int) (2125));
 //BA.debugLineNum = 1090;BA.debugLine="End Sub";
return "";
}
public static String  _menubtn_click() throws Exception{
 //BA.debugLineNum = 1183;BA.debugLine="Sub menubtn_Click";
 //BA.debugLineNum = 1184;BA.debugLine="navi.OpenDrawer2(navi.GRAVITY_RIGHT)";
mostCurrent._navi.OpenDrawer2(mostCurrent._navi.GRAVITY_RIGHT);
 //BA.debugLineNum = 1185;BA.debugLine="End Sub";
return "";
}
public static String  _menucategory_click() throws Exception{
anywheresoftware.b4a.objects.AnimationWrapper _a = null;
anywheresoftware.b4a.samples.httputils2.httpjob _download = null;
 //BA.debugLineNum = 1214;BA.debugLine="Sub menucategory_Click";
 //BA.debugLineNum = 1215;BA.debugLine="categoryscroll.Panel.RemoveAllViews";
mostCurrent._categoryscroll.getPanel().RemoveAllViews();
 //BA.debugLineNum = 1216;BA.debugLine="navi.CloseDrawer2(navi.GRAVITY_END)";
mostCurrent._navi.CloseDrawer2(mostCurrent._navi.GRAVITY_END);
 //BA.debugLineNum = 1217;BA.debugLine="categorypnl.Visible = True";
mostCurrent._categorypnl.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1218;BA.debugLine="Dim  a As Animation";
_a = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 1219;BA.debugLine="a.InitializeAlpha(\"\",0, 1)";
_a.InitializeAlpha(mostCurrent.activityBA,"",(float) (0),(float) (1));
 //BA.debugLineNum = 1220;BA.debugLine="a.Duration = 500";
_a.setDuration((long) (500));
 //BA.debugLineNum = 1221;BA.debugLine="a.Start(categorypnl)";
_a.Start((android.view.View)(mostCurrent._categorypnl.getObject()));
 //BA.debugLineNum = 1222;BA.debugLine="Dim download As HttpJob";
_download = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 1223;BA.debugLine="download.Initialize(\"categorypage\",Me)";
_download._initialize(processBA,"categorypage",index.getObject());
 //BA.debugLineNum = 1224;BA.debugLine="download.PostString(extra.api,\"op=getcategory\")";
_download._poststring(mostCurrent._extra._api,"op=getcategory");
 //BA.debugLineNum = 1225;BA.debugLine="End Sub";
return "";
}
public static String  _menufantast_click() throws Exception{
 //BA.debugLineNum = 1195;BA.debugLine="Sub menufantast_Click";
 //BA.debugLineNum = 1197;BA.debugLine="End Sub";
return "";
}
public static String  _menuhome_click() throws Exception{
 //BA.debugLineNum = 1203;BA.debugLine="Sub menuhome_Click";
 //BA.debugLineNum = 1204;BA.debugLine="navi.CloseDrawer2(navi.GRAVITY_END)";
mostCurrent._navi.CloseDrawer2(mostCurrent._navi.GRAVITY_END);
 //BA.debugLineNum = 1205;BA.debugLine="index_ScrollView.Panel.RemoveAllViews";
mostCurrent._index_scrollview.getPanel().RemoveAllViews();
 //BA.debugLineNum = 1206;BA.debugLine="extra.load_index";
mostCurrent._extra._load_index(mostCurrent.activityBA);
 //BA.debugLineNum = 1207;BA.debugLine="extra.index_ob_olaviyat(0) = 1";
mostCurrent._extra._index_ob_olaviyat[(int) (0)] = (int) (1);
 //BA.debugLineNum = 1208;BA.debugLine="extra.flag_procpnl = 0";
mostCurrent._extra._flag_procpnl = (int) (0);
 //BA.debugLineNum = 1209;BA.debugLine="extra.index_ob_top = 0";
mostCurrent._extra._index_ob_top = (int) (0);
 //BA.debugLineNum = 1210;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 1211;BA.debugLine="extra.propertyjson = 0";
mostCurrent._extra._propertyjson = (int) (0);
 //BA.debugLineNum = 1212;BA.debugLine="End Sub";
return "";
}
public static String  _menunew_click() throws Exception{
 //BA.debugLineNum = 1199;BA.debugLine="Sub menunew_Click";
 //BA.debugLineNum = 1201;BA.debugLine="End Sub";
return "";
}
public static String  _menuporfrosh_click() throws Exception{
 //BA.debugLineNum = 1187;BA.debugLine="Sub menuporfrosh_Click";
 //BA.debugLineNum = 1189;BA.debugLine="End Sub";
return "";
}
public static String  _menutakhfif_click() throws Exception{
 //BA.debugLineNum = 1191;BA.debugLine="Sub menutakhfif_Click";
 //BA.debugLineNum = 1193;BA.debugLine="End Sub";
return "";
}
public static String  _pagerc_pagechanged(int _position) throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Sub Pagerc_PageChanged (Position As Int)";
 //BA.debugLineNum = 191;BA.debugLine="cat_title_line.SetLayoutAnimated(300,	cat_title(P";
mostCurrent._cat_title_line.SetLayoutAnimated((int) (300),mostCurrent._cat_title[_position].getLeft(),mostCurrent._cat_title_line.getTop(),mostCurrent._cat_title[_position].getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)));
 //BA.debugLineNum = 192;BA.debugLine="productheader.ScrollPosition=	cat_title(Position)";
mostCurrent._productheader.setScrollPosition(mostCurrent._cat_title[_position].getLeft());
 //BA.debugLineNum = 194;BA.debugLine="Log(Position)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_position));
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _propert_click() throws Exception{
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
 //BA.debugLineNum = 1104;BA.debugLine="Sub propert_click()";
 //BA.debugLineNum = 1105;BA.debugLine="Dim propert As Label";
_propert = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1106;BA.debugLine="Dim temp As String";
_temp = "";
 //BA.debugLineNum = 1107;BA.debugLine="extra.propertyjson = 1";
mostCurrent._extra._propertyjson = (int) (1);
 //BA.debugLineNum = 1108;BA.debugLine="propert = Sender";
_propert.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 1109;BA.debugLine="temp = propert.Tag";
_temp = BA.ObjectToString(_propert.getTag());
 //BA.debugLineNum = 1110;BA.debugLine="temp =   temp.SubString2(0,  temp.LastIndexOf(\"#\"";
_temp = _temp.substring((int) (0),_temp.lastIndexOf("#"));
 //BA.debugLineNum = 1111;BA.debugLine="Log(temp)";
anywheresoftware.b4a.keywords.Common.Log(_temp);
 //BA.debugLineNum = 1112;BA.debugLine="property_pnl.Initialize(100%y)";
mostCurrent._property_pnl.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1113;BA.debugLine="property_pnl.Color = Colors.rgb(250, 250, 250)";
mostCurrent._property_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
 //BA.debugLineNum = 1114;BA.debugLine="Activity.AddView(property_pnl,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._property_pnl.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 1115;BA.debugLine="Dim topset As Int = 5dip";
_topset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 1116;BA.debugLine="Try";
try { //BA.debugLineNum = 1117;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1118;BA.debugLine="parser.Initialize(temp)";
_parser.Initialize(_temp);
 //BA.debugLineNum = 1119;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 1120;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group16 = _root;
final int groupLen16 = group16.getSize()
;int index16 = 0;
;
for (; index16 < groupLen16;index16++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group16.Get(index16)));
 //BA.debugLineNum = 1121;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 1122;BA.debugLine="Dim text As String = colroot.Get(\"text\")";
_text = BA.ObjectToString(_colroot.Get((Object)("text")));
 //BA.debugLineNum = 1123;BA.debugLine="Dim grouping As String = colroot.Get(\"grouping\"";
_grouping = BA.ObjectToString(_colroot.Get((Object)("grouping")));
 //BA.debugLineNum = 1125;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1126;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1127;BA.debugLine="lblnodata.Text =grouping";
_lblnodata.setText(BA.ObjectToCharSequence(_grouping));
 //BA.debugLineNum = 1128;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 1129;BA.debugLine="lblnodata.TextColor = Colors.rgb(38, 38, 38)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (38),(int) (38),(int) (38)));
 //BA.debugLineNum = 1130;BA.debugLine="lblnodata.color = Colors.rgb(217, 217, 217)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (217),(int) (217),(int) (217)));
 //BA.debugLineNum = 1131;BA.debugLine="lblnodata.TextSize = 11dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 1132;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"y";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1133;BA.debugLine="property_pnl.Panel.AddView(lblnodata,5dip,topse";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),_topset,(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
 //BA.debugLineNum = 1135;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1136;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1137;BA.debugLine="lblnodata.Text =\"  \" & text";
_lblnodata.setText(BA.ObjectToCharSequence("  "+_text));
 //BA.debugLineNum = 1138;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 1139;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (115),(int) (115),(int) (115)));
 //BA.debugLineNum = 1140;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)));
 //BA.debugLineNum = 1141;BA.debugLine="lblnodata.TextSize = 9dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 1142;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"y";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1143;BA.debugLine="property_pnl.Panel.AddView(lblnodata,5dip,topse";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 1145;BA.debugLine="Dim lblnodata As Label";
_lblnodata = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1146;BA.debugLine="lblnodata.Initialize(\"\")";
_lblnodata.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1147;BA.debugLine="lblnodata.Text =\"  \" & name";
_lblnodata.setText(BA.ObjectToCharSequence("  "+_name));
 //BA.debugLineNum = 1148;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
_lblnodata.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 1149;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115)";
_lblnodata.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (115),(int) (115),(int) (115)));
 //BA.debugLineNum = 1150;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
_lblnodata.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (242),(int) (242),(int) (242)));
 //BA.debugLineNum = 1151;BA.debugLine="lblnodata.TextSize = 10dip";
_lblnodata.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 1152;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"y";
_lblnodata.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 1153;BA.debugLine="property_pnl.Panel.AddView(lblnodata,70%x,topse";
mostCurrent._property_pnl.getPanel().AddView((android.view.View)(_lblnodata.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 1155;BA.debugLine="topset = topset + 65dip";
_topset = (int) (_topset+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65)));
 //BA.debugLineNum = 1157;BA.debugLine="property_pnl.Panel.Height = topset";
mostCurrent._property_pnl.getPanel().setHeight(_topset);
 }
};
 } 
       catch (Exception e51) {
			processBA.setLastException(e51); //BA.debugLineNum = 1160;BA.debugLine="createnon";
_createnon();
 };
 //BA.debugLineNum = 1162;BA.debugLine="End Sub";
return "";
}
public static String  _sharepost_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _pic_sheare = null;
 //BA.debugLineNum = 1175;BA.debugLine="Sub sharepost_click()";
 //BA.debugLineNum = 1176;BA.debugLine="Dim pic_sheare As ImageView";
_pic_sheare = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1177;BA.debugLine="pic_sheare = Sender";
_pic_sheare.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 1178;BA.debugLine="extra.programsharepost(pic_sheare.tag)";
mostCurrent._extra._programsharepost(mostCurrent.activityBA,BA.ObjectToString(_pic_sheare.getTag()));
 //BA.debugLineNum = 1179;BA.debugLine="End Sub";
return "";
}
}
