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

public class product extends Activity implements B4AActivity{
	public static product mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "manini.b4a.example", "manini.b4a.example.product");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (product).");
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
		activityBA = new BA(this, layout, processBA, "manini.b4a.example", "manini.b4a.example.product");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "manini.b4a.example.product", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (product) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (product) Resume **");
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
		return product.class;
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
        BA.LogInfo("** Activity (product) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (product) Resume **");
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
public anywheresoftware.b4a.objects.ScrollViewWrapper _product_scrollview = null;
public anywheresoftware.b4a.objects.PanelWrapper _product_header = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _product_imag1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _product_img2 = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scrollview_lastproduct = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _category_hscrollview = null;
public anywheresoftware.b4a.objects.ImageViewWrapper[] _imageview = null;
public anywheresoftware.b4a.objects.LabelWrapper _header_title = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_title = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_titlen = null;
public static int _tempint = 0;
public anywheresoftware.b4a.objects.LabelWrapper _moretext = null;
public anywheresoftware.b4a.objects.LabelWrapper _rtljustify1 = null;
public static String _moretext_data = "";
public anywheresoftware.b4a.objects.PanelWrapper _pnl4_moshakhasat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnl4_line = null;
public anywheresoftware.b4a.objects.LabelWrapper _total = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public manini.b4a.example.index _index = null;
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
anywheresoftware.b4a.samples.httputils2.httpjob _downloadtext = null;
anywheresoftware.b4a.samples.httputils2.httpjob _download = null;
anywheresoftware.b4a.objects.PanelWrapper _pnlw = null;
de.amberhome.viewpager.AHPageContainer _container = null;
de.amberhome.viewpager.AHViewPager _pager = null;
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl2 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl3 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
anywheresoftware.b4a.objects.PanelWrapper _btn = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _buy = null;
anywheresoftware.b4a.objects.LabelWrapper _buyicon = null;
njdude.fontawesome.lib.fontawesome _fae = null;
anywheresoftware.b4a.objects.LabelWrapper _propert = null;
njdude.fontawesome.lib.fontawesome _fa = null;
anywheresoftware.b4a.objects.LabelWrapper _properticon = null;
anywheresoftware.b4a.objects.ImageViewWrapper _pic_like = null;
anywheresoftware.b4a.objects.LabelWrapper _color = null;
anywheresoftware.b4a.objects.LabelWrapper _garanti = null;
anywheresoftware.b4a.objects.LabelWrapper _saler = null;
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"product\")";
mostCurrent._activity.LoadLayout("product",mostCurrent.activityBA);
 //BA.debugLineNum = 40;BA.debugLine="Dim downloadtext As HttpJob";
_downloadtext = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 41;BA.debugLine="downloadtext.Initialize(\"textproc\",Me)";
_downloadtext._initialize(processBA,"textproc",product.getObject());
 //BA.debugLineNum = 42;BA.debugLine="downloadtext.PostString(extra.api,\"op=productdesc";
_downloadtext._poststring(mostCurrent._extra._api,"op=productdescription&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
 //BA.debugLineNum = 44;BA.debugLine="Dim download As HttpJob";
_download = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 45;BA.debugLine="download.Initialize(\"nameproc\",Me)";
_download._initialize(processBA,"nameproc",product.getObject());
 //BA.debugLineNum = 46;BA.debugLine="download.PostString(extra.api,\"op=product&id=\" &";
_download._poststring(mostCurrent._extra._api,"op=product&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
 //BA.debugLineNum = 50;BA.debugLine="Dim pnlw As Panel";
_pnlw = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="pnlw.Initialize(\"\")";
_pnlw.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 52;BA.debugLine="pnlw.Color = Colors.White";
_pnlw.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 53;BA.debugLine="product_ScrollView.Panel.AddView(pnlw,0,0,100%x,5";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnlw.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)));
 //BA.debugLineNum = 55;BA.debugLine="Dim Container As AHPageContainer";
_container = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 56;BA.debugLine="Dim Pager As AHViewPager";
_pager = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 57;BA.debugLine="Container.Initialize";
_container.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 58;BA.debugLine="Dim pan(5) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (5)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 60;BA.debugLine="pan(0).Initialize(\"\")";
_pan[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 61;BA.debugLine="pan(1).Initialize(\"\")";
_pan[(int) (1)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 62;BA.debugLine="pan(0).LoadLayout(\"product_img1\")";
_pan[(int) (0)].LoadLayout("product_img1",mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="Container.AddPageAt(pan(0), \"Main\", 0)";
_container.AddPageAt((android.view.View)(_pan[(int) (0)].getObject()),"Main",(int) (0));
 //BA.debugLineNum = 65;BA.debugLine="pan(1).LoadLayout(\"product_img2\")";
_pan[(int) (1)].LoadLayout("product_img2",mostCurrent.activityBA);
 //BA.debugLineNum = 66;BA.debugLine="Container.AddPageAt(pan(1), \"Main2\", 1)";
_container.AddPageAt((android.view.View)(_pan[(int) (1)].getObject()),"Main2",(int) (1));
 //BA.debugLineNum = 68;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
_pager.Initialize2(mostCurrent.activityBA,_container,"Pager");
 //BA.debugLineNum = 69;BA.debugLine="product_ScrollView.Panel.AddView(Pager,0,55dip,10";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pager.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))));
 //BA.debugLineNum = 76;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 77;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 78;BA.debugLine="pnl.Color = Colors.rgb(250, 250, 250)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
 //BA.debugLineNum = 79;BA.debugLine="product_ScrollView.Panel.AddView(pnl,0,50%x+55dip";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 81;BA.debugLine="Dim pnl2 As Panel";
_pnl2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="pnl2.Initialize(\"\")";
_pnl2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 83;BA.debugLine="pnl2.Color = Colors.White";
_pnl2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 84;BA.debugLine="product_ScrollView.Panel.AddView(pnl2,15dip,50%x+";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (163))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 85;BA.debugLine="extra.InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl2,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
 //BA.debugLineNum = 87;BA.debugLine="Dim pnl3 As Panel";
_pnl3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="pnl3.Initialize(\"\")";
_pnl3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 89;BA.debugLine="pnl3.Color = Colors.White";
_pnl3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 90;BA.debugLine="product_ScrollView.Panel.AddView(pnl3,15dip,50%x+";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
 //BA.debugLineNum = 91;BA.debugLine="extra.InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl3,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
 //BA.debugLineNum = 94;BA.debugLine="pnl4_moshakhasat.Initialize(\"\")";
mostCurrent._pnl4_moshakhasat.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 95;BA.debugLine="pnl4_moshakhasat.Color = Colors.White";
mostCurrent._pnl4_moshakhasat.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 96;BA.debugLine="product_ScrollView.Panel.AddView(pnl4_moshakhasat";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._pnl4_moshakhasat.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (289))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
 //BA.debugLineNum = 97;BA.debugLine="extra.InitPanel(pnl4_moshakhasat,2,1,Colors.rgb(2";
mostCurrent._extra._initpanel(mostCurrent.activityBA,mostCurrent._pnl4_moshakhasat,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
 //BA.debugLineNum = 99;BA.debugLine="moretext_data = \"در حال بارگذاری\"";
mostCurrent._moretext_data = "در حال بارگذاری";
 //BA.debugLineNum = 101;BA.debugLine="RTLJustify1.Initialize(\"\")";
mostCurrent._rtljustify1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 102;BA.debugLine="RTLJustify1.Text = moretext_data";
mostCurrent._rtljustify1.setText(BA.ObjectToCharSequence(mostCurrent._moretext_data));
 //BA.debugLineNum = 103;BA.debugLine="RTLJustify1.Typeface = Typeface.LoadFromAssets(\"y";
mostCurrent._rtljustify1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 105;BA.debugLine="RTLJustify1.TextColor = Colors.rgb(89, 89, 89)";
mostCurrent._rtljustify1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (89),(int) (89),(int) (89)));
 //BA.debugLineNum = 106;BA.debugLine="RTLJustify1.Gravity = Gravity.FILL";
mostCurrent._rtljustify1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 107;BA.debugLine="RTLJustify1.TextSize = 10dip";
mostCurrent._rtljustify1.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 109;BA.debugLine="Dim Obj1 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 110;BA.debugLine="Obj1.Target = RTLJustify1";
_obj1.Target = (Object)(mostCurrent._rtljustify1.getObject());
 //BA.debugLineNum = 111;BA.debugLine="Obj1.RunMethod3(\"setLineSpacing\", 1, \"java.lang.f";
_obj1.RunMethod3("setLineSpacing",BA.NumberToString(1),"java.lang.float",BA.NumberToString(1.5),"java.lang.float");
 //BA.debugLineNum = 112;BA.debugLine="product_ScrollView.Panel.AddView(RTLJustify1,15di";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._rtljustify1.getObject()),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185)));
 //BA.debugLineNum = 115;BA.debugLine="pnl4_line.Initialize(\"\")";
mostCurrent._pnl4_line.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 116;BA.debugLine="pnl4_line.Color = Colors.rgb(179, 179, 179)";
mostCurrent._pnl4_line.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
 //BA.debugLineNum = 117;BA.debugLine="product_ScrollView.Panel.AddView(pnl4_line,30dip,";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._pnl4_line.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 120;BA.debugLine="category_hscrollview.Initialize(100%x,\"\")";
mostCurrent._category_hscrollview.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
 //BA.debugLineNum = 121;BA.debugLine="category_hscrollview.Panel.Height = 50dip";
mostCurrent._category_hscrollview.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 122;BA.debugLine="product_ScrollView.Panel.AddView(category_hscroll";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._category_hscrollview.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 125;BA.debugLine="scrollview_lastproduct.Initialize(100%x,\"\")";
mostCurrent._scrollview_lastproduct.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
 //BA.debugLineNum = 126;BA.debugLine="scrollview_lastproduct.Panel.Height = 450dip";
mostCurrent._scrollview_lastproduct.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
 //BA.debugLineNum = 127;BA.debugLine="product_ScrollView.Panel.AddView(scrollview_lastp";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._scrollview_lastproduct.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
 //BA.debugLineNum = 131;BA.debugLine="moretext.Initialize(\"moretext\")";
mostCurrent._moretext.Initialize(mostCurrent.activityBA,"moretext");
 //BA.debugLineNum = 132;BA.debugLine="moretext.TextColor = Colors.rgb(140, 140, 140)";
mostCurrent._moretext.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 133;BA.debugLine="moretext.Gravity = Gravity.CENTER";
mostCurrent._moretext.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 134;BA.debugLine="moretext.Typeface = Typeface.LoadFromAssets(\"yeka";
mostCurrent._moretext.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 135;BA.debugLine="moretext.TextSize = 10dip";
mostCurrent._moretext.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 136;BA.debugLine="moretext.Text = \"ادامه مطلب\"";
mostCurrent._moretext.setText(BA.ObjectToCharSequence("ادامه مطلب"));
 //BA.debugLineNum = 137;BA.debugLine="product_ScrollView.Panel.AddView(moretext,15dip,5";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._moretext.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 139;BA.debugLine="Dim btn As Panel";
_btn = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 140;BA.debugLine="btn.Initialize(\"\")";
_btn.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 141;BA.debugLine="btn.Color = Colors.rgb(102, 187, 106)";
_btn.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 143;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 144;BA.debugLine="cd.Initialize(Colors.rgb(102, 187, 106), 5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 145;BA.debugLine="btn.Background = cd";
_btn.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 146;BA.debugLine="product_ScrollView.Panel.AddView(btn,50dip,50%x+4";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_btn.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 148;BA.debugLine="Dim buy As Label";
_buy = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 149;BA.debugLine="buy.Initialize(\"\")";
_buy.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 150;BA.debugLine="buy.TextColor = Colors.White";
_buy.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 151;BA.debugLine="buy.Gravity = Gravity.CENTER";
_buy.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 152;BA.debugLine="buy.Typeface = Typeface.LoadFromAssets(\"yekan.ttf";
_buy.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 153;BA.debugLine="buy.TextSize = 12dip";
_buy.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
 //BA.debugLineNum = 154;BA.debugLine="buy.Text = \"افزودن به سبد خرید\"";
_buy.setText(BA.ObjectToCharSequence("افزودن به سبد خرید"));
 //BA.debugLineNum = 155;BA.debugLine="product_ScrollView.Panel.AddView(buy,50dip,50%x+4";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_buy.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (65),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 157;BA.debugLine="Dim buyicon As Label";
_buyicon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 158;BA.debugLine="buyicon.Initialize(\"\")";
_buyicon.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 159;BA.debugLine="Private FAe As FontAwesome";
_fae = new njdude.fontawesome.lib.fontawesome();
 //BA.debugLineNum = 160;BA.debugLine="FAe.Initialize";
_fae._initialize(processBA);
 //BA.debugLineNum = 161;BA.debugLine="buyicon.Gravity = Gravity.CENTER_VERTICAL";
_buyicon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 162;BA.debugLine="buyicon.Typeface = Typeface.FONTAWESOME";
_buyicon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 163;BA.debugLine="buyicon.TextColor = Colors.White";
_buyicon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 164;BA.debugLine="buyicon.TextSize = 13dip";
_buyicon.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
 //BA.debugLineNum = 165;BA.debugLine="buyicon.Text = FAe.GetFontAwesomeIconByName(\"fa-c";
_buyicon.setText(BA.ObjectToCharSequence(_fae._getfontawesomeiconbyname("fa-cart-plus")));
 //BA.debugLineNum = 166;BA.debugLine="product_ScrollView.Panel.AddView(buyicon,70%x,50%";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_buyicon.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 169;BA.debugLine="Dim propert As Label";
_propert = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 170;BA.debugLine="propert.Initialize(\"propert\")";
_propert.Initialize(mostCurrent.activityBA,"propert");
 //BA.debugLineNum = 171;BA.debugLine="propert.TextColor = Colors.Black";
_propert.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 172;BA.debugLine="propert.Gravity = Gravity.CENTER";
_propert.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 173;BA.debugLine="propert.Typeface = Typeface.LoadFromAssets(\"yekan";
_propert.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 174;BA.debugLine="propert.Text = \"مشخصات\"";
_propert.setText(BA.ObjectToCharSequence("مشخصات"));
 //BA.debugLineNum = 175;BA.debugLine="product_ScrollView.Panel.AddView(propert,15dip,50";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_propert.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 179;BA.debugLine="total.Initialize(\"\")";
mostCurrent._total.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 180;BA.debugLine="total.TextColor = Colors.rgb(102, 187, 106)";
mostCurrent._total.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 181;BA.debugLine="total.Gravity = Gravity.LEFT";
mostCurrent._total.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 182;BA.debugLine="total.Typeface = Typeface.LoadFromAssets(\"yekan.t";
mostCurrent._total.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 183;BA.debugLine="total.Text = \"\"";
mostCurrent._total.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 184;BA.debugLine="total.TextSize = 13dip";
mostCurrent._total.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
 //BA.debugLineNum = 185;BA.debugLine="product_ScrollView.Panel.AddView(total,32dip,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._total.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (178))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 190;BA.debugLine="Private FA As FontAwesome";
_fa = new njdude.fontawesome.lib.fontawesome();
 //BA.debugLineNum = 191;BA.debugLine="FA.Initialize";
_fa._initialize(processBA);
 //BA.debugLineNum = 192;BA.debugLine="Dim properticon As Label";
_properticon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 193;BA.debugLine="properticon.Initialize(\"\")";
_properticon.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 194;BA.debugLine="properticon.TextColor = Colors.Black";
_properticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 196;BA.debugLine="properticon.Gravity = Gravity.CENTER_VERTICAL";
_properticon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 197;BA.debugLine="properticon.Typeface = Typeface.FONTAWESOME";
_properticon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 198;BA.debugLine="properticon.Text = FA.GetFontAwesomeIconByName(\"f";
_properticon.setText(BA.ObjectToCharSequence(_fa._getfontawesomeiconbyname("fa-calendar-o")));
 //BA.debugLineNum = 199;BA.debugLine="product_ScrollView.Panel.AddView(properticon,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_properticon.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
 //BA.debugLineNum = 203;BA.debugLine="lbl_title.Initialize(\"\")";
mostCurrent._lbl_title.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 204;BA.debugLine="lbl_title.Text =  \"\"";
mostCurrent._lbl_title.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 205;BA.debugLine="extra.product_title =  \"\"";
mostCurrent._extra._product_title = "";
 //BA.debugLineNum = 206;BA.debugLine="lbl_title.Typeface = Typeface.LoadFromAssets(\"yek";
mostCurrent._lbl_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 207;BA.debugLine="lbl_title.TextSize = 11dip";
mostCurrent._lbl_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 208;BA.debugLine="lbl_title.Gravity = Gravity.RIGHT";
mostCurrent._lbl_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 209;BA.debugLine="lbl_title.TextColor = Colors.Black";
mostCurrent._lbl_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 210;BA.debugLine="product_ScrollView.Panel.AddView(lbl_title,0,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._lbl_title.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (85))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 212;BA.debugLine="lbl_titlen.Initialize(\"\")";
mostCurrent._lbl_titlen.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 213;BA.debugLine="lbl_titlen.Text =  \"\"";
mostCurrent._lbl_titlen.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 214;BA.debugLine="lbl_titlen.Typeface = Typeface.LoadFromAssets(\"ye";
mostCurrent._lbl_titlen.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 215;BA.debugLine="lbl_titlen.TextSize = 8dip";
mostCurrent._lbl_titlen.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 216;BA.debugLine="lbl_titlen.Gravity = Gravity.RIGHT";
mostCurrent._lbl_titlen.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 217;BA.debugLine="lbl_titlen.TextColor = Colors.rgb(169, 169, 169)";
mostCurrent._lbl_titlen.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (169),(int) (169),(int) (169)));
 //BA.debugLineNum = 218;BA.debugLine="product_ScrollView.Panel.AddView(lbl_titlen,0,50%";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._lbl_titlen.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 225;BA.debugLine="Dim pic_like As ImageView";
_pic_like = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 226;BA.debugLine="pic_like.Initialize(\"\")";
_pic_like.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 227;BA.debugLine="pic_like.Gravity = Gravity.FILL";
_pic_like.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 228;BA.debugLine="pic_like.Bitmap = LoadBitmap(File.DirAssets,\"like";
_pic_like.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"like.png").getObject()));
 //BA.debugLineNum = 232;BA.debugLine="Dim color As Label";
_color = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 233;BA.debugLine="color.Initialize(\"\")";
_color.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 234;BA.debugLine="color.TextColor = Colors.rgb(140, 140, 140)";
_color.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 235;BA.debugLine="color.Gravity = Gravity.RIGHT";
_color.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 236;BA.debugLine="color.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_color.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 237;BA.debugLine="color.Text = \"رنگ\"";
_color.setText(BA.ObjectToCharSequence("رنگ"));
 //BA.debugLineNum = 238;BA.debugLine="color.TextSize = 9dip";
_color.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 239;BA.debugLine="product_ScrollView.Panel.AddView(color,0,50%x+270";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_color.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (270))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 242;BA.debugLine="Dim garanti As Label";
_garanti = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 243;BA.debugLine="garanti.Initialize(\"\")";
_garanti.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 244;BA.debugLine="garanti.TextColor = Colors.rgb(140, 140, 140)";
_garanti.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 245;BA.debugLine="garanti.Gravity = Gravity.RIGHT";
_garanti.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 246;BA.debugLine="garanti.Typeface = Typeface.LoadFromAssets(\"yekan";
_garanti.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 247;BA.debugLine="garanti.Text = \"گارانتی\"";
_garanti.setText(BA.ObjectToCharSequence("گارانتی"));
 //BA.debugLineNum = 248;BA.debugLine="garanti.TextSize = 9dip";
_garanti.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 249;BA.debugLine="product_ScrollView.Panel.AddView(garanti,0,50%x+3";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_garanti.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 252;BA.debugLine="Dim saler As Label";
_saler = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 253;BA.debugLine="saler.Initialize(\"\")";
_saler.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 254;BA.debugLine="saler.TextColor = Colors.rgb(140, 140, 140)";
_saler.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
 //BA.debugLineNum = 255;BA.debugLine="saler.Gravity = Gravity.RIGHT";
_saler.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 256;BA.debugLine="saler.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_saler.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 257;BA.debugLine="saler.Text = \"فروشنده\"";
_saler.setText(BA.ObjectToCharSequence("فروشنده"));
 //BA.debugLineNum = 258;BA.debugLine="saler.TextSize = 9dip";
_saler.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 261;BA.debugLine="Dim header_title As Label";
mostCurrent._header_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 262;BA.debugLine="header_title.Initialize(\"\")";
mostCurrent._header_title.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 264;BA.debugLine="header_title.Gravity = Gravity.RIGHT";
mostCurrent._header_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 265;BA.debugLine="header_title.Typeface = Typeface.LoadFromAssets(\"";
mostCurrent._header_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 266;BA.debugLine="header_title.TextColor = Colors.red";
mostCurrent._header_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 267;BA.debugLine="header_title.TextSize = 11dip";
mostCurrent._header_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 268;BA.debugLine="header_title.Visible = False";
mostCurrent._header_title.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 269;BA.debugLine="product_header.AddView(header_title,0,5dip,95%x,4";
mostCurrent._product_header.AddView((android.view.View)(mostCurrent._header_title.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 279;BA.debugLine="Sub Activity_KeyPress(KeyCode As Int) As Boolean";
 //BA.debugLineNum = 281;BA.debugLine="If KeyCode= KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 282;BA.debugLine="Log(\"backed\")";
anywheresoftware.b4a.keywords.Common.Log("backed");
 //BA.debugLineNum = 283;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 318;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _jober = null;
 //BA.debugLineNum = 312;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 313;BA.debugLine="Dim jober As HttpJob";
_jober = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 314;BA.debugLine="jober.Initialize(\"lastproduct\",Me)";
_jober._initialize(processBA,"lastproduct",product.getObject());
 //BA.debugLineNum = 315;BA.debugLine="jober.PostString(extra.api,\"op=lastproduct\")";
_jober._poststring(mostCurrent._extra._api,"op=lastproduct");
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private product_ScrollView As ScrollView";
mostCurrent._product_scrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private product_header As Panel";
mostCurrent._product_header = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private product_imag1 As ImageView";
mostCurrent._product_imag1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private product_img2 As ImageView";
mostCurrent._product_img2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim scrollview_lastproduct As HorizontalScrollVie";
mostCurrent._scrollview_lastproduct = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim category_hscrollview As HorizontalScrollView";
mostCurrent._category_hscrollview = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ImageView(20)  As ImageView";
mostCurrent._imageview = new anywheresoftware.b4a.objects.ImageViewWrapper[(int) (20)];
{
int d0 = mostCurrent._imageview.length;
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._imageview[i0] = new anywheresoftware.b4a.objects.ImageViewWrapper();
}
}
;
 //BA.debugLineNum = 23;BA.debugLine="Dim header_title As Label";
mostCurrent._header_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim lbl_title As Label";
mostCurrent._lbl_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim lbl_titlen As Label";
mostCurrent._lbl_titlen = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim tempint As Int";
_tempint = 0;
 //BA.debugLineNum = 27;BA.debugLine="Dim moretext As Label";
mostCurrent._moretext = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim RTLJustify1 As Label";
mostCurrent._rtljustify1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim moretext_data As String";
mostCurrent._moretext_data = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim pnl4_moshakhasat As Panel";
mostCurrent._pnl4_moshakhasat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim pnl4_line As Panel";
mostCurrent._pnl4_line = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim total As Label";
mostCurrent._total = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _s = "";
String _d1 = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _colroot = null;
String _price = "";
String _name = "";
String _model = "";
int _indexf = 0;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outstream = null;
int _left = 0;
String _id = "";
String _text = "";
anywheresoftware.b4a.objects.LabelWrapper _lable = null;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
String _image = "";
anywheresoftware.b4a.objects.PanelWrapper _panel = null;
anywheresoftware.b4a.objects.LabelWrapper _lable2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvasgraph = null;
 //BA.debugLineNum = 323;BA.debugLine="Sub jobdone(job As HttpJob)";
 //BA.debugLineNum = 325;BA.debugLine="Log(job.JobName)";
anywheresoftware.b4a.keywords.Common.Log(_job._jobname);
 //BA.debugLineNum = 326;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 328;BA.debugLine="If job.JobName = \"picproc\" Then";
if ((_job._jobname).equals("picproc")) { 
 //BA.debugLineNum = 329;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 };
 //BA.debugLineNum = 340;BA.debugLine="If job.JobName = \"textproc\" Then";
if ((_job._jobname).equals("textproc")) { 
 //BA.debugLineNum = 341;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 342;BA.debugLine="s = job.GetString.Replace(\"&lt;\",\"\").Replace(\"";
_s = _job._getstring().replace("&lt;","").replace("p&gt;","").replace("br&gt;","").replace("/&lt;","").replace("/p&gt;","").replace("/br&gt;","").replace("p style=&quot;text-align: justify; &quot;&gt;","");
 //BA.debugLineNum = 343;BA.debugLine="s =s.Replace(\"amp;\",\"\").Replace(\"nbsp;\",\"\").Re";
_s = _s.replace("amp;","").replace("nbsp;","").replace("/null","");
 //BA.debugLineNum = 344;BA.debugLine="RTLJustify1.Text = s";
mostCurrent._rtljustify1.setText(BA.ObjectToCharSequence(_s));
 };
 //BA.debugLineNum = 347;BA.debugLine="Try";
try { //BA.debugLineNum = 348;BA.debugLine="If job.JobName = \"nameproc\" Then";
if ((_job._jobname).equals("nameproc")) { 
 //BA.debugLineNum = 349;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 350;BA.debugLine="Dim d1 As String";
_d1 = "";
 //BA.debugLineNum = 351;BA.debugLine="d1 = job.GetString.SubString2(0,job.GetString.";
_d1 = _job._getstring().substring((int) (0),_job._getstring().indexOf("^"));
 //BA.debugLineNum = 353;BA.debugLine="extra.propertyjson = job.GetString.SubString2(";
mostCurrent._extra._propertyjson = (int)(Double.parseDouble(_job._getstring().substring((int) (_job._getstring().indexOf("^")+1),_job._getstring().length())));
 //BA.debugLineNum = 354;BA.debugLine="Log(extra.propertyjson)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._extra._propertyjson));
 //BA.debugLineNum = 355;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 356;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 357;BA.debugLine="parser.Initialize(d1)";
_parser.Initialize(_d1);
 //BA.debugLineNum = 358;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 359;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group23 = _root;
final int groupLen23 = group23.getSize()
;int index23 = 0;
;
for (; index23 < groupLen23;index23++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group23.Get(index23)));
 //BA.debugLineNum = 360;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
 //BA.debugLineNum = 361;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 362;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
 //BA.debugLineNum = 363;BA.debugLine="lbl_title.Text = name";
mostCurrent._lbl_title.setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 364;BA.debugLine="lbl_titlen.Text =  model";
mostCurrent._lbl_titlen.setText(BA.ObjectToCharSequence(_model));
 //BA.debugLineNum = 365;BA.debugLine="header_title.Text = name";
mostCurrent._header_title.setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 366;BA.debugLine="extra.product_title = name";
mostCurrent._extra._product_title = _name;
 //BA.debugLineNum = 367;BA.debugLine="total.Text = price.SubString2(0,price.IndexOf";
mostCurrent._total.setText(BA.ObjectToCharSequence(_price.substring((int) (0),_price.indexOf("."))+" "+"تومان"));
 }
};
 };
 } 
       catch (Exception e35) {
			processBA.setLastException(e35); //BA.debugLineNum = 371;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 373;BA.debugLine="Try";
try { //BA.debugLineNum = 374;BA.debugLine="If  job.JobName.SubString2(0,9)=\"imageview\" The";
if ((_job._jobname.substring((int) (0),(int) (9))).equals("imageview")) { 
 //BA.debugLineNum = 375;BA.debugLine="Dim indexf As Int";
_indexf = 0;
 //BA.debugLineNum = 376;BA.debugLine="Dim name As String";
_name = "";
 //BA.debugLineNum = 377;BA.debugLine="indexf = job.JobName.SubString2(job.JobName.In";
_indexf = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
 //BA.debugLineNum = 378;BA.debugLine="name = job.JobName.SubString(job.JobName.LastI";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
 //BA.debugLineNum = 379;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 380;BA.debugLine="OutStream = File.OpenOutput(File.DirInternalCa";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 381;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
 //BA.debugLineNum = 382;BA.debugLine="OutStream.Close";
_outstream.Close();
 //BA.debugLineNum = 383;BA.debugLine="ImageView(indexf).Bitmap = LoadBitmap(File.Dir";
mostCurrent._imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e50) {
			processBA.setLastException(e50); //BA.debugLineNum = 390;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 392;BA.debugLine="If job.JobName = \"load_category_main\" Then";
if ((_job._jobname).equals("load_category_main")) { 
 //BA.debugLineNum = 393;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 394;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 395;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 396;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 397;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group57 = _root;
final int groupLen57 = group57.getSize()
;int index57 = 0;
;
for (; index57 < groupLen57;index57++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group57.Get(index57)));
 //BA.debugLineNum = 398;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 399;BA.debugLine="Dim text As String = colroot.Get(\"name\")";
_text = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 400;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 401;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
 //BA.debugLineNum = 402;BA.debugLine="lable.Color = Colors.rgb(102, 187, 106)";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 403;BA.debugLine="lable.TextColor = Colors.White";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 404;BA.debugLine="lable.Gravity = Gravity.CENTER";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 405;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"yeka";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 406;BA.debugLine="lable.TextSize = \"20\"";
_lable.setTextSize((float)(Double.parseDouble("20")));
 //BA.debugLineNum = 407;BA.debugLine="lable.Text = text";
_lable.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 408;BA.debugLine="category_hscrollview.Panel.AddView(lable,left,";
mostCurrent._category_hscrollview.getPanel().AddView((android.view.View)(_lable.getObject()),_left,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2.5)),(int) ((_text.length()*30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 //BA.debugLineNum = 409;BA.debugLine="left =( text.Length * 30  ) + left +8dip";
_left = (int) ((_text.length()*30)+_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8)));
 }
};
 //BA.debugLineNum = 411;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 412;BA.debugLine="r.Target = category_hscrollview";
_r.Target = (Object)(mostCurrent._category_hscrollview.getObject());
 //BA.debugLineNum = 413;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\", F";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 414;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 416;BA.debugLine="category_hscrollview.Panel.Width = left";
mostCurrent._category_hscrollview.getPanel().setWidth(_left);
 //BA.debugLineNum = 417;BA.debugLine="category_hscrollview.FullScroll(True)";
mostCurrent._category_hscrollview.FullScroll(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 418;BA.debugLine="category_hscrollview.ScrollPosition = 50dip";
mostCurrent._category_hscrollview.setScrollPosition(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 };
 //BA.debugLineNum = 420;BA.debugLine="If job.JobName=\"lastproduct\" Then";
if ((_job._jobname).equals("lastproduct")) { 
 //BA.debugLineNum = 421;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 422;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 423;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 424;BA.debugLine="Dim indexf As Int = 1";
_indexf = (int) (1);
 //BA.debugLineNum = 425;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
 //BA.debugLineNum = 426;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group85 = _root;
final int groupLen85 = group85.getSize()
;int index85 = 0;
;
for (; index85 < groupLen85;index85++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group85.Get(index85)));
 //BA.debugLineNum = 427;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
 //BA.debugLineNum = 428;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
 //BA.debugLineNum = 429;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
 //BA.debugLineNum = 430;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
 //BA.debugLineNum = 431;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
 //BA.debugLineNum = 432;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 433;BA.debugLine="panel.Initialize(\"lastproduct_panel\")";
_panel.Initialize(mostCurrent.activityBA,"lastproduct_panel");
 //BA.debugLineNum = 434;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
 //BA.debugLineNum = 435;BA.debugLine="panel.Color = Colors.White";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 436;BA.debugLine="scrollview_lastproduct.Panel.AddView(panel,lef";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_panel.getObject()),_left,(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (225))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 437;BA.debugLine="extra.InitPanel(panel,1dip,Colors.White,Colors";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_panel,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)));
 //BA.debugLineNum = 438;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 439;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
 //BA.debugLineNum = 440;BA.debugLine="lable.Color = Colors.White";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 441;BA.debugLine="lable.TextColor = Colors.rgb(65, 65, 65)";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (65),(int) (65),(int) (65)));
 //BA.debugLineNum = 442;BA.debugLine="lable.Gravity = Gravity.RIGHT";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 443;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"yeka";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 444;BA.debugLine="lable.TextSize = \"16\"";
_lable.setTextSize((float)(Double.parseDouble("16")));
 //BA.debugLineNum = 445;BA.debugLine="lable.Text = name";
_lable.setText(BA.ObjectToCharSequence(_name));
 //BA.debugLineNum = 446;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable,lef";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 448;BA.debugLine="Dim lable2 As Label";
_lable2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 449;BA.debugLine="lable2.Initialize(\"lable2\")";
_lable2.Initialize(mostCurrent.activityBA,"lable2");
 //BA.debugLineNum = 450;BA.debugLine="lable2.Color = Colors.White";
_lable2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 451;BA.debugLine="lable2.TextColor = Colors.rgb(102, 187, 106)";
_lable2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
 //BA.debugLineNum = 452;BA.debugLine="lable2.Typeface = Typeface.DEFAULT_BOLD";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 453;BA.debugLine="lable2.Gravity = Gravity.LEFT";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
 //BA.debugLineNum = 454;BA.debugLine="lable2.Gravity = Gravity.CENTER_VERTICAL";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 455;BA.debugLine="lable2.Typeface = Typeface.LoadFromAssets(\"yek";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 456;BA.debugLine="lable2.TextSize = \"16\"";
_lable2.setTextSize((float)(Double.parseDouble("16")));
 //BA.debugLineNum = 457;BA.debugLine="lable2.Text = price";
_lable2.setText(BA.ObjectToCharSequence(_price));
 //BA.debugLineNum = 458;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable2,le";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable2.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (195)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)));
 //BA.debugLineNum = 460;BA.debugLine="ImageView(indexf).Initialize(\"ImageView\")";
mostCurrent._imageview[_indexf].Initialize(mostCurrent.activityBA,"ImageView");
 //BA.debugLineNum = 461;BA.debugLine="If File.Exists(File.DirInternalCache, image.Su";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_image.substring((int) (_image.lastIndexOf("/")+1),_image.length()))) { 
 //BA.debugLineNum = 462;BA.debugLine="ImageView(indexf).Bitmap = LoadBitmap(File.Di";
mostCurrent._imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_image.substring((int) (_image.lastIndexOf("/")+1),_image.length())).getObject()));
 }else {
 //BA.debugLineNum = 464;BA.debugLine="ImageView(indexf).Bitmap = LoadBitmap(File.Di";
mostCurrent._imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg").getObject()));
 };
 //BA.debugLineNum = 467;BA.debugLine="ImageView(indexf).Gravity = Gravity.FILL";
mostCurrent._imageview[_indexf].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 468;BA.debugLine="scrollview_lastproduct.Panel.AddView(ImageView";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(mostCurrent._imageview[_indexf].getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 470;BA.debugLine="Dim CanvasGraph As Canvas";
_canvasgraph = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 471;BA.debugLine="CanvasGraph.Initialize(panel)";
_canvasgraph.Initialize((android.view.View)(_panel.getObject()));
 //BA.debugLineNum = 473;BA.debugLine="CanvasGraph.DrawLine(0,140dip,160dip,140dip,Co";
_canvasgraph.DrawLine((float) (0),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 474;BA.debugLine="left = left + 170dip";
_left = (int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
 //BA.debugLineNum = 475;BA.debugLine="indexf= indexf + 1";
_indexf = (int) (_indexf+1);
 }
};
 //BA.debugLineNum = 477;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 478;BA.debugLine="r.Target = scrollview_lastproduct";
_r.Target = (Object)(mostCurrent._scrollview_lastproduct.getObject());
 //BA.debugLineNum = 479;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\", F";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
 //BA.debugLineNum = 480;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
 //BA.debugLineNum = 481;BA.debugLine="scrollview_lastproduct.Panel.Width = left";
mostCurrent._scrollview_lastproduct.getPanel().setWidth(_left);
 //BA.debugLineNum = 482;BA.debugLine="product_ScrollView.Panel.Height = 50%x+164.5dip";
mostCurrent._product_scrollview.getPanel().setHeight((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (295))));
 };
 };
 //BA.debugLineNum = 491;BA.debugLine="End Sub";
return "";
}
public static String  _lastproduct_panel_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _lastproduct_panel = null;
 //BA.debugLineNum = 492;BA.debugLine="Sub lastproduct_panel_click()";
 //BA.debugLineNum = 493;BA.debugLine="Dim lastproduct_panel As Panel";
_lastproduct_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 494;BA.debugLine="lastproduct_panel = Sender";
_lastproduct_panel.setObject((android.view.ViewGroup)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 496;BA.debugLine="End Sub";
return "";
}
public static String  _moretext_click() throws Exception{
anywheresoftware.b4a.objects.StringUtils _su = null;
int _lener = 0;
 //BA.debugLineNum = 497;BA.debugLine="Sub moretext_click()";
 //BA.debugLineNum = 498;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 499;BA.debugLine="Dim lener As Int";
_lener = 0;
 //BA.debugLineNum = 500;BA.debugLine="lener = RTLJustify1.Text.Length";
_lener = mostCurrent._rtljustify1.getText().length();
 //BA.debugLineNum = 501;BA.debugLine="Log(lener)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lener));
 //BA.debugLineNum = 502;BA.debugLine="pnl4_moshakhasat.Height = lener * 0.275%x + 30dip";
mostCurrent._pnl4_moshakhasat.setHeight((int) (_lener*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0.275),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))));
 //BA.debugLineNum = 503;BA.debugLine="RTLJustify1.Height = lener * 0.275%x";
mostCurrent._rtljustify1.setHeight((int) (_lener*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0.275),mostCurrent.activityBA)));
 //BA.debugLineNum = 505;BA.debugLine="moretext.Visible = False";
mostCurrent._moretext.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 506;BA.debugLine="pnl4_line.Visible = False";
mostCurrent._pnl4_line.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 508;BA.debugLine="category_hscrollview.Top = RTLJustify1.Top + RTLJ";
mostCurrent._category_hscrollview.setTop((int) (mostCurrent._rtljustify1.getTop()+mostCurrent._rtljustify1.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 509;BA.debugLine="scrollview_lastproduct.Top = category_hscrollview";
mostCurrent._scrollview_lastproduct.setTop((int) (mostCurrent._category_hscrollview.getTop()+mostCurrent._category_hscrollview.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
 //BA.debugLineNum = 511;BA.debugLine="product_ScrollView.Panel.Height =product_ScrollVi";
mostCurrent._product_scrollview.getPanel().setHeight((int) (mostCurrent._product_scrollview.getPanel().getHeight()+mostCurrent._rtljustify1.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (289))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))));
 //BA.debugLineNum = 512;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _product_scrollview_scrollchanged(int _position) throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub product_ScrollView_ScrollChanged(Position As I";
 //BA.debugLineNum = 287;BA.debugLine="If Position <= 511 Then";
if (_position<=511) { 
 //BA.debugLineNum = 288;BA.debugLine="product_header.Color = Colors.ARGB(Position/2,25";
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (_position/(double)2),(int) (255),(int) (204),(int) (255)));
 };
 //BA.debugLineNum = 290;BA.debugLine="If Position = 0 Then product_header.Color = Color";
if (_position==0) { 
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (255),(int) (255)));};
 //BA.debugLineNum = 292;BA.debugLine="If (Position+83dip) > lbl_title.Top Then";
if ((_position+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (83)))>mostCurrent._lbl_title.getTop()) { 
 //BA.debugLineNum = 293;BA.debugLine="If extra.product_title_flag = False Then extra.p";
if (mostCurrent._extra._product_title_flag==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._extra._product_title_top = _position;};
 //BA.debugLineNum = 296;BA.debugLine="tempint =  75dip    -    (  Position - extra.pro";
_tempint = (int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (75))-(_position-mostCurrent._extra._product_title_top));
 //BA.debugLineNum = 297;BA.debugLine="If tempint>= 10dip Then";
if (_tempint>=anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))) { 
 //BA.debugLineNum = 298;BA.debugLine="Log(tempint)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_tempint));
 //BA.debugLineNum = 299;BA.debugLine="header_title.Top = tempint";
mostCurrent._header_title.setTop(_tempint);
 //BA.debugLineNum = 300;BA.debugLine="header_title.Visible = True";
mostCurrent._header_title.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 302;BA.debugLine="product_header.Color = Colors.ARGB(255,255, 204";
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (204),(int) (255)));
 //BA.debugLineNum = 303;BA.debugLine="header_title.Top = 10dip";
mostCurrent._header_title.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 304;BA.debugLine="Log(\"end i\")";
anywheresoftware.b4a.keywords.Common.Log("end i");
 };
 //BA.debugLineNum = 306;BA.debugLine="extra.product_title_flag = True";
mostCurrent._extra._product_title_flag = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 308;BA.debugLine="extra.product_title_flag = False";
mostCurrent._extra._product_title_flag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 309;BA.debugLine="header_title.Visible = False";
mostCurrent._header_title.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 311;BA.debugLine="End Sub";
return "";
}
public static String  _propert_click() throws Exception{
 //BA.debugLineNum = 513;BA.debugLine="Sub propert_click()";
 //BA.debugLineNum = 514;BA.debugLine="StartActivity(property)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._property.getObject()));
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
}
