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
public anywheresoftware.b4a.objects.collections.List _index_retrive_list = null;
public anywheresoftware.b4a.objects.collections.List _index_retrive_list_img = null;
public anywheresoftware.b4a.objects.collections.List _index_retrive_list_model = null;
public anywheresoftware.b4a.objects.ImageViewWrapper[] _imgdrew = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public manini.b4a.example.product _product = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 20;BA.debugLine="Activity.LoadLayout(\"index\")";
mostCurrent._activity.LoadLayout("index",mostCurrent.activityBA);
 //BA.debugLineNum = 21;BA.debugLine="extra.index_ob_olaviyat(0) = 1";
mostCurrent._extra._index_ob_olaviyat[(int) (0)] = (int) (1);
 //BA.debugLineNum = 23;BA.debugLine="If File.Exists(File.DirInternalCache & \"/product";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 24;BA.debugLine="File.MakeDir(File.DirInternalCache,\"product\"	)";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product");
 };
 //BA.debugLineNum = 27;BA.debugLine="extra.load_index";
mostCurrent._extra._load_index(mostCurrent.activityBA);
 //BA.debugLineNum = 28;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 29;BA.debugLine="r.Target = index_ScrollView";
_r.Target = (Object)(mostCurrent._index_scrollview.getObject());
 //BA.debugLineNum = 30;BA.debugLine="r.RunMethod2(\"setVerticalScrollBarEnabled\", False";
_r.RunMethod2("setVerticalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 31;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang.i";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private index_ScrollView As ScrollView";
mostCurrent._index_scrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim index_retrive_list As List";
mostCurrent._index_retrive_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 14;BA.debugLine="Dim index_retrive_list_img As List";
mostCurrent._index_retrive_list_img = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 15;BA.debugLine="Dim index_retrive_list_model As List";
mostCurrent._index_retrive_list_model = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Dim imgdrew(5000) As ImageView";
mostCurrent._imgdrew = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (5000)];
{
int d0 = mostCurrent._imgdrew.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._imgdrew[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _imgdrew_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _imgdre = null;
 //BA.debugLineNum = 315;BA.debugLine="Sub imgdrew_click()";
 //BA.debugLineNum = 316;BA.debugLine="Dim imgdre As ImageView";
_imgdre = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 317;BA.debugLine="imgdre = Sender";
_imgdre.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 318;BA.debugLine="extra.product_id_toshow = imgdre.Tag";
mostCurrent._extra._product_id_toshow = (int)(BA.ObjectToNumber(_imgdre.getTag()));
 //BA.debugLineNum = 319;BA.debugLine="StartActivity(product)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._product.getObject()));
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
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
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
String _imgfile = "";
 //BA.debugLineNum = 150;BA.debugLine="Sub index_draw(size As String,flag,id,img,model)";
 //BA.debugLineNum = 151;BA.debugLine="extra.index_ob_olaviyat_load = flag";
mostCurrent._extra._index_ob_olaviyat_load = (int)(Double.parseDouble(_flag));
 //BA.debugLineNum = 152;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 153;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 154;BA.debugLine="Dim color As Int";
_color = 0;
 //BA.debugLineNum = 155;BA.debugLine="color =Colors.White";
_color = anywheresoftware.b4a.keywords.Common.Colors.White;
 //BA.debugLineNum = 156;BA.debugLine="panel.Initialize(\"panel\")";
_panel.Initialize(mostCurrent.activityBA,"panel");
 //BA.debugLineNum = 157;BA.debugLine="lbl.Initialize(\"lbl\")";
_lbl.Initialize(mostCurrent.activityBA,"lbl");
 //BA.debugLineNum = 158;BA.debugLine="lbl.Text = model";
_lbl.setText(BA.ObjectToCharSequence(_model));
 //BA.debugLineNum = 159;BA.debugLine="lbl.TextColor = Colors.White";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 160;BA.debugLine="lbl.Color = Colors.ARGB(140, 140, 140,100)";
_lbl.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (140),(int) (140),(int) (140),(int) (100)));
 //BA.debugLineNum = 161;BA.debugLine="Dim space As Int = 2dip";
_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 162;BA.debugLine="Dim padding_space As Int = 2dip";
_padding_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 163;BA.debugLine="If size=\"larg\" Then";
if ((_size).equals("larg")) { 
 //BA.debugLineNum = 164;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 165;BA.debugLine="Dim width_draw As Int = 100%x - left_draw";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_left_draw);
 //BA.debugLineNum = 166;BA.debugLine="Dim shadow_space As Int = 5dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 167;BA.debugLine="extra.index_ob_olaviyat(flag) = 1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 168;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 169;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 };
 //BA.debugLineNum = 171;BA.debugLine="If size=\"medium\" Then";
if ((_size).equals("medium")) { 
 //BA.debugLineNum = 172;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (4),(int) (111),(int) (11),(int) (1))) {
case 0: {
 //BA.debugLineNum = 174;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 175;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 176;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 177;BA.debugLine="extra.index_ob_olaviyat(flag)=224";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (224);
 //BA.debugLineNum = 178;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 179;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 1: {
 //BA.debugLineNum = 181;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 182;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 183;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 184;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 185;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 2: {
 //BA.debugLineNum = 187;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 188;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
_width_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 189;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 190;BA.debugLine="extra.index_ob_olaviyat(flag)=222";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (222);
 //BA.debugLineNum = 191;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 192;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 3: {
 //BA.debugLineNum = 194;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 195;BA.debugLine="Dim width_draw As Int = 66.4%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA);
 //BA.debugLineNum = 196;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 197;BA.debugLine="extra.index_ob_olaviyat(flag)=22";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (22);
 //BA.debugLineNum = 198;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 199;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
}
;
 };
 //BA.debugLineNum = 202;BA.debugLine="If size=\"small\" Then";
if ((_size).equals("small")) { 
 //BA.debugLineNum = 203;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) ((double)(Double.parseDouble(_flag))-1)],(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (22),(int) (111),(int) (11),(int) (1))) {
case 0: {
 //BA.debugLineNum = 205;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 206;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 207;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 208;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
 //BA.debugLineNum = 209;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 210;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 211;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 1: {
 //BA.debugLineNum = 214;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 215;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 216;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 217;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
 //BA.debugLineNum = 218;BA.debugLine="extra.index_ob_olaviyat(flag)=225";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (225);
 //BA.debugLineNum = 219;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 220;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 2: {
 //BA.debugLineNum = 222;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 223;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 224;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 225;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
mostCurrent._extra._index_ob_top = mostCurrent._extra._index_ob_top;
 //BA.debugLineNum = 226;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 227;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 228;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 3: {
 //BA.debugLineNum = 230;BA.debugLine="Dim left_draw As Int =  padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 231;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 232;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 233;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
 //BA.debugLineNum = 234;BA.debugLine="extra.index_ob_olaviyat(flag)=223";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (223);
 //BA.debugLineNum = 235;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 236;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 4: {
 //BA.debugLineNum = 238;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 239;BA.debugLine="Dim width_draw As Int = 33.23%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.23),mostCurrent.activityBA);
 //BA.debugLineNum = 240;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 241;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA));
 //BA.debugLineNum = 242;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 243;BA.debugLine="extra.index_ob_top_cach = width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 244;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 5: {
 //BA.debugLineNum = 246;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 247;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 248;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 249;BA.debugLine="extra.index_ob_olaviyat(flag)=221";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (221);
 //BA.debugLineNum = 250;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 251;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 6: {
 //BA.debugLineNum = 253;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (66.4),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 254;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 255;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 256;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (1);
 //BA.debugLineNum = 257;BA.debugLine="extra.index_ob_top_cach =  width_draw";
mostCurrent._extra._index_ob_top_cach = _width_draw;
 //BA.debugLineNum = 258;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 7: {
 //BA.debugLineNum = 260;BA.debugLine="Dim left_draw As Int = 33.3%x + padding_space";
_left_draw = (int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.3),mostCurrent.activityBA)+_padding_space);
 //BA.debugLineNum = 261;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 262;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 263;BA.debugLine="extra.index_ob_olaviyat(flag)=111";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (111);
 //BA.debugLineNum = 264;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 265;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
case 8: {
 //BA.debugLineNum = 267;BA.debugLine="Dim left_draw As Int = padding_space";
_left_draw = _padding_space;
 //BA.debugLineNum = 268;BA.debugLine="Dim width_draw As Int = 33.2%x";
_width_draw = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (33.2),mostCurrent.activityBA);
 //BA.debugLineNum = 269;BA.debugLine="Dim shadow_space As Int = 15dip";
_shadow_space = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 270;BA.debugLine="extra.index_ob_olaviyat(flag)=11";
mostCurrent._extra._index_ob_olaviyat[(int)(Double.parseDouble(_flag))] = (int) (11);
 //BA.debugLineNum = 271;BA.debugLine="extra.index_ob_top_cach = 0";
mostCurrent._extra._index_ob_top_cach = (int) (0);
 //BA.debugLineNum = 272;BA.debugLine="panel.Color = color";
_panel.setColor(_color);
 break; }
}
;
 };
 //BA.debugLineNum = 276;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 279;BA.debugLine="lbl.Gravity = Gravity.FILL";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 280;BA.debugLine="lbl.Gravity = Gravity.CENTER";
_lbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 281;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
 //BA.debugLineNum = 285;BA.debugLine="Dim imgfile As String = img.SubString2(img.LastIn";
_imgfile = _img.substring((int) (_img.lastIndexOf("/")+1),_img.length());
 //BA.debugLineNum = 287;BA.debugLine="If File.Exists (File.DirInternalCache,\"product/\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product/"+_imgfile)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 289;BA.debugLine="imgdrew(flag).Initialize(\"imgdrew\")";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].Initialize(mostCurrent.activityBA,"imgdrew");
 //BA.debugLineNum = 290;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject()));
 //BA.debugLineNum = 292;BA.debugLine="imgdrew(flag).Tag = id";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setTag((Object)(_id));
 //BA.debugLineNum = 293;BA.debugLine="imgdrew(flag).Gravity = Gravity.FILL";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 295;BA.debugLine="extra.download_image(id,img,flag)";
mostCurrent._extra._download_image(mostCurrent.activityBA,_id,_img,_flag);
 }else {
 //BA.debugLineNum = 299;BA.debugLine="imgdrew(flag).Initialize(\"imgdrew\")";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].Initialize(mostCurrent.activityBA,"imgdrew");
 //BA.debugLineNum = 300;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File.Dir";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),"product/"+_imgfile,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))).getObject()));
 //BA.debugLineNum = 301;BA.debugLine="imgdrew(flag).Gravity = Gravity.FILL";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 302;BA.debugLine="imgdrew(flag).Tag =  id";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setTag((Object)(_id));
 };
 //BA.debugLineNum = 306;BA.debugLine="index_ScrollView.Panel.AddView(panel,left_draw,ex";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_panel.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
 //BA.debugLineNum = 308;BA.debugLine="index_ScrollView.Panel.AddView(imgdrew(flag),left";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_space),(int) (_width_draw-_space),(int) (_width_draw-_space));
 //BA.debugLineNum = 310;BA.debugLine="index_ScrollView.Panel.AddView(lbl,left_draw,extr";
mostCurrent._index_scrollview.getPanel().AddView((android.view.View)(_lbl.getObject()),_left_draw,(int) (mostCurrent._extra._index_ob_top+_width_draw-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25))),(int) (_width_draw-_space),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 311;BA.debugLine="extra.index_ob_top = extra.index_ob_top + extra.i";
mostCurrent._extra._index_ob_top = (int) (mostCurrent._extra._index_ob_top+mostCurrent._extra._index_ob_top_cach);
 //BA.debugLineNum = 312;BA.debugLine="index_ScrollView.Panel.Height = extra.index_ob_to";
mostCurrent._index_scrollview.getPanel().setHeight((int) (mostCurrent._extra._index_ob_top+_space));
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static String  _index_scrollview_scrollchanged(int _position) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 37;BA.debugLine="Sub index_ScrollView_ScrollChanged(Position As Int";
 //BA.debugLineNum = 38;BA.debugLine="If (Position+1950) >= extra.index_ob_top Then";
if ((_position+1950)>=mostCurrent._extra._index_ob_top) { 
 //BA.debugLineNum = 39;BA.debugLine="Log(extra.index_ob_olaviyat_load)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._extra._index_ob_olaviyat_load));
 //BA.debugLineNum = 40;BA.debugLine="extra.index_ob_olaviyat(extra.index_ob_olaviyat_";
mostCurrent._extra._index_ob_olaviyat[(int) (mostCurrent._extra._index_ob_olaviyat_load-1)] = (int) (1);
 //BA.debugLineNum = 41;BA.debugLine="Dim job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 42;BA.debugLine="job.Initialize(\"\",Me)";
_job._initialize(processBA,"",index.getObject());
 //BA.debugLineNum = 43;BA.debugLine="load_indexjob(job,False)";
_load_indexjob(_job,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _id = "";
String _img = "";
String _flag = "";
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outstream = null;
 //BA.debugLineNum = 125;BA.debugLine="Sub jobdone(job As HttpJob)";
 //BA.debugLineNum = 126;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 127;BA.debugLine="Select job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"load_indexjob")) {
case 0: {
 //BA.debugLineNum = 129;BA.debugLine="load_indexjob(job,True)";
_load_indexjob(_job,anywheresoftware.b4a.keywords.Common.True);
 break; }
default: {
 //BA.debugLineNum = 131;BA.debugLine="Try";
try { //BA.debugLineNum = 132;BA.debugLine="If job.JobName.SubString2(0,13)=\"downloadimag";
if ((_job._jobname.substring((int) (0),(int) (13))).equals("downloadimage")) { 
 //BA.debugLineNum = 133;BA.debugLine="Dim id As String = job.JobName.SubString2(jo";
_id = _job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.indexOf("$"));
 //BA.debugLineNum = 134;BA.debugLine="Dim img As String = job.JobName.SubString2(j";
_img = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1),_job._jobname.lastIndexOf("#"));
 //BA.debugLineNum = 135;BA.debugLine="Dim flag As String = job.JobName.SubString2(";
_flag = _job._jobname.substring((int) (_job._jobname.lastIndexOf("#")+1),_job._jobname.length());
 //BA.debugLineNum = 136;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 137;BA.debugLine="OutStream = File.OpenOutput(File.DirInternal";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product",_img,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
 //BA.debugLineNum = 139;BA.debugLine="OutStream.Close";
_outstream.Close();
 //BA.debugLineNum = 140;BA.debugLine="imgdrew(flag).Bitmap = LoadBitmapSample(File";
mostCurrent._imgdrew[(int)(Double.parseDouble(_flag))].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache()+"/product",_img,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200))).getObject()));
 };
 } 
       catch (Exception e18) {
			processBA.setLastException(e18); };
 break; }
}
;
 };
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 47;BA.debugLine="Sub load_indexjob(job As HttpJob,create As Boolean";
 //BA.debugLineNum = 48;BA.debugLine="Try";
try { //BA.debugLineNum = 50;BA.debugLine="Dim lastpage As Int";
_lastpage = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 52;BA.debugLine="If create=True Then";
if (_create==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 53;BA.debugLine="Dim id As String = job.GetString.SubString2(0, j";
_id = _job._getstring().substring((int) (0),_job._getstring().indexOf("*"));
 //BA.debugLineNum = 54;BA.debugLine="Dim img As String = job.GetString.SubString2(job";
_img = _job._getstring().substring((int) (_job._getstring().indexOf("*")+3),_job._getstring().indexOf("$"));
 //BA.debugLineNum = 55;BA.debugLine="Dim modle As String = job.GetString.SubString2(j";
_modle = _job._getstring().substring((int) (_job._getstring().indexOf("$")+3),_job._getstring().length());
 //BA.debugLineNum = 59;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 60;BA.debugLine="parser.Initialize(id)";
_parser.Initialize(_id);
 //BA.debugLineNum = 61;BA.debugLine="index_retrive_list= parser.NextArray";
mostCurrent._index_retrive_list = _parser.NextArray();
 //BA.debugLineNum = 62;BA.debugLine="parser.Initialize(img)";
_parser.Initialize(_img);
 //BA.debugLineNum = 63;BA.debugLine="index_retrive_list_img= parser.NextArray";
mostCurrent._index_retrive_list_img = _parser.NextArray();
 //BA.debugLineNum = 64;BA.debugLine="parser.Initialize(modle)";
_parser.Initialize(_modle);
 //BA.debugLineNum = 65;BA.debugLine="index_retrive_list_model= parser.NextArray";
mostCurrent._index_retrive_list_model = _parser.NextArray();
 //BA.debugLineNum = 66;BA.debugLine="i = 1";
_i = (int) (1);
 //BA.debugLineNum = 67;BA.debugLine="lastpage = 20";
_lastpage = (int) (20);
 }else {
 //BA.debugLineNum = 69;BA.debugLine="i = extra.index_ob_olaviyat_load";
_i = mostCurrent._extra._index_ob_olaviyat_load;
 //BA.debugLineNum = 70;BA.debugLine="lastpage = extra.index_ob_olaviyat_load + 20";
_lastpage = (int) (mostCurrent._extra._index_ob_olaviyat_load+20);
 };
 //BA.debugLineNum = 73;BA.debugLine="do  While (i<lastpage Or  extra.index_ob_olaviyat";
while ((_i<_lastpage || mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)]!=1)) {
 //BA.debugLineNum = 76;BA.debugLine="Select extra.index_ob_olaviyat(i-1)";
switch (BA.switchObjectToInt(mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)],(int) (1),(int) (22),(int) (225),(int) (224),(int) (223),(int) (222),(int) (221),(int) (11),(int) (111))) {
case 0: {
 //BA.debugLineNum = 78;BA.debugLine="Dim x As Int = Rnd(1,5)";
_x = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
 //BA.debugLineNum = 80;BA.debugLine="If x = 1 Then";
if (_x==1) { 
 //BA.debugLineNum = 81;BA.debugLine="index_draw(\"larg\",i,index_retrive_list.Get(i)";
_index_draw("larg",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 //BA.debugLineNum = 84;BA.debugLine="If x = 2 Then";
if (_x==2) { 
 //BA.debugLineNum = 85;BA.debugLine="index_draw(\"medium\",i,index_retrive_list.Get(";
_index_draw("medium",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 //BA.debugLineNum = 88;BA.debugLine="If x = 3 Then";
if (_x==3) { 
 //BA.debugLineNum = 89;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 //BA.debugLineNum = 92;BA.debugLine="If x = 4 Then";
if (_x==4) { 
 //BA.debugLineNum = 93;BA.debugLine="extra.index_ob_olaviyat(i-1) = 4";
mostCurrent._extra._index_ob_olaviyat[(int) (_i-1)] = (int) (4);
 //BA.debugLineNum = 94;BA.debugLine="index_draw(\"medium\",i,index_retrive_list.Get(";
_index_draw("medium",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 };
 break; }
case 1: {
 //BA.debugLineNum = 98;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 2: {
 //BA.debugLineNum = 100;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 3: {
 //BA.debugLineNum = 103;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 4: {
 //BA.debugLineNum = 105;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 5: {
 //BA.debugLineNum = 107;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 6: {
 //BA.debugLineNum = 109;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 7: {
 //BA.debugLineNum = 111;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
case 8: {
 //BA.debugLineNum = 116;BA.debugLine="index_draw(\"small\",i,index_retrive_list.Get(i)";
_index_draw("small",BA.NumberToString(_i),BA.ObjectToString(mostCurrent._index_retrive_list.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_img.Get(_i)),BA.ObjectToString(mostCurrent._index_retrive_list_model.Get(_i)));
 break; }
}
;
 //BA.debugLineNum = 118;BA.debugLine="i=i+1";
_i = (int) (_i+1);
 }
;
 } 
       catch (Exception e58) {
			processBA.setLastException(e58); //BA.debugLineNum = 122;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
