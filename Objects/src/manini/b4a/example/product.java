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
			processBA = new anywheresoftware.b4a.ShellBA(this.getApplicationContext(), null, null, "manini.b4a.example", "manini.b4a.example.product");
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



public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
public static String  _activity_create(boolean _firsttime) throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_create"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_create", new Object[] {_firsttime});
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
RDebugUtils.currentLine=2424832;
 //BA.debugLineNum = 2424832;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
RDebugUtils.currentLine=2424834;
 //BA.debugLineNum = 2424834;BA.debugLine="Activity.LoadLayout(\"product\")";
mostCurrent._activity.LoadLayout("product",mostCurrent.activityBA);
RDebugUtils.currentLine=2424836;
 //BA.debugLineNum = 2424836;BA.debugLine="Dim downloadtext As HttpJob";
_downloadtext = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=2424837;
 //BA.debugLineNum = 2424837;BA.debugLine="downloadtext.Initialize(\"textproc\",Me)";
_downloadtext._initialize(processBA,"textproc",product.getObject());
RDebugUtils.currentLine=2424838;
 //BA.debugLineNum = 2424838;BA.debugLine="downloadtext.PostString(extra.api,\"op=productdesc";
_downloadtext._poststring(mostCurrent._extra._api,"op=productdescription&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
RDebugUtils.currentLine=2424840;
 //BA.debugLineNum = 2424840;BA.debugLine="Dim download As HttpJob";
_download = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=2424841;
 //BA.debugLineNum = 2424841;BA.debugLine="download.Initialize(\"nameproc\",Me)";
_download._initialize(processBA,"nameproc",product.getObject());
RDebugUtils.currentLine=2424842;
 //BA.debugLineNum = 2424842;BA.debugLine="download.PostString(extra.api,\"op=product&id=\" &";
_download._poststring(mostCurrent._extra._api,"op=product&id="+BA.NumberToString(mostCurrent._extra._product_id_toshow));
RDebugUtils.currentLine=2424846;
 //BA.debugLineNum = 2424846;BA.debugLine="Dim pnlw As Panel";
_pnlw = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2424847;
 //BA.debugLineNum = 2424847;BA.debugLine="pnlw.Initialize(\"\")";
_pnlw.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424848;
 //BA.debugLineNum = 2424848;BA.debugLine="pnlw.Color = Colors.White";
_pnlw.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2424849;
 //BA.debugLineNum = 2424849;BA.debugLine="product_ScrollView.Panel.AddView(pnlw,0,0,100%x,5";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnlw.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)));
RDebugUtils.currentLine=2424851;
 //BA.debugLineNum = 2424851;BA.debugLine="Dim Container As AHPageContainer";
_container = new de.amberhome.viewpager.AHPageContainer();
RDebugUtils.currentLine=2424852;
 //BA.debugLineNum = 2424852;BA.debugLine="Dim Pager As AHViewPager";
_pager = new de.amberhome.viewpager.AHViewPager();
RDebugUtils.currentLine=2424853;
 //BA.debugLineNum = 2424853;BA.debugLine="Container.Initialize";
_container.Initialize(mostCurrent.activityBA);
RDebugUtils.currentLine=2424854;
 //BA.debugLineNum = 2424854;BA.debugLine="Dim pan(5) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (5)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
RDebugUtils.currentLine=2424856;
 //BA.debugLineNum = 2424856;BA.debugLine="pan(0).Initialize(\"\")";
_pan[(int) (0)].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424857;
 //BA.debugLineNum = 2424857;BA.debugLine="pan(1).Initialize(\"\")";
_pan[(int) (1)].Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424858;
 //BA.debugLineNum = 2424858;BA.debugLine="pan(0).LoadLayout(\"product_img1\")";
_pan[(int) (0)].LoadLayout("product_img1",mostCurrent.activityBA);
RDebugUtils.currentLine=2424859;
 //BA.debugLineNum = 2424859;BA.debugLine="Container.AddPageAt(pan(0), \"Main\", 0)";
_container.AddPageAt((android.view.View)(_pan[(int) (0)].getObject()),"Main",(int) (0));
RDebugUtils.currentLine=2424861;
 //BA.debugLineNum = 2424861;BA.debugLine="pan(1).LoadLayout(\"product_img2\")";
_pan[(int) (1)].LoadLayout("product_img2",mostCurrent.activityBA);
RDebugUtils.currentLine=2424862;
 //BA.debugLineNum = 2424862;BA.debugLine="Container.AddPageAt(pan(1), \"Main2\", 1)";
_container.AddPageAt((android.view.View)(_pan[(int) (1)].getObject()),"Main2",(int) (1));
RDebugUtils.currentLine=2424864;
 //BA.debugLineNum = 2424864;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
_pager.Initialize2(mostCurrent.activityBA,_container,"Pager");
RDebugUtils.currentLine=2424865;
 //BA.debugLineNum = 2424865;BA.debugLine="product_ScrollView.Panel.AddView(Pager,0,55dip,10";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pager.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))));
RDebugUtils.currentLine=2424872;
 //BA.debugLineNum = 2424872;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2424873;
 //BA.debugLineNum = 2424873;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424874;
 //BA.debugLineNum = 2424874;BA.debugLine="pnl.Color = Colors.rgb(250, 250, 250)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
RDebugUtils.currentLine=2424875;
 //BA.debugLineNum = 2424875;BA.debugLine="product_ScrollView.Panel.AddView(pnl,0,50%x+55dip";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
RDebugUtils.currentLine=2424877;
 //BA.debugLineNum = 2424877;BA.debugLine="Dim pnl2 As Panel";
_pnl2 = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2424878;
 //BA.debugLineNum = 2424878;BA.debugLine="pnl2.Initialize(\"\")";
_pnl2.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424879;
 //BA.debugLineNum = 2424879;BA.debugLine="pnl2.Color = Colors.White";
_pnl2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2424880;
 //BA.debugLineNum = 2424880;BA.debugLine="product_ScrollView.Panel.AddView(pnl2,15dip,50%x+";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (163))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=2424881;
 //BA.debugLineNum = 2424881;BA.debugLine="extra.InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl2,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
RDebugUtils.currentLine=2424883;
 //BA.debugLineNum = 2424883;BA.debugLine="Dim pnl3 As Panel";
_pnl3 = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2424884;
 //BA.debugLineNum = 2424884;BA.debugLine="pnl3.Initialize(\"\")";
_pnl3.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424885;
 //BA.debugLineNum = 2424885;BA.debugLine="pnl3.Color = Colors.White";
_pnl3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2424886;
 //BA.debugLineNum = 2424886;BA.debugLine="product_ScrollView.Panel.AddView(pnl3,15dip,50%x+";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
RDebugUtils.currentLine=2424887;
 //BA.debugLineNum = 2424887;BA.debugLine="extra.InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_pnl3,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
RDebugUtils.currentLine=2424890;
 //BA.debugLineNum = 2424890;BA.debugLine="pnl4_moshakhasat.Initialize(\"\")";
mostCurrent._pnl4_moshakhasat.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424891;
 //BA.debugLineNum = 2424891;BA.debugLine="pnl4_moshakhasat.Color = Colors.White";
mostCurrent._pnl4_moshakhasat.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2424892;
 //BA.debugLineNum = 2424892;BA.debugLine="product_ScrollView.Panel.AddView(pnl4_moshakhasat";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._pnl4_moshakhasat.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (289))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)));
RDebugUtils.currentLine=2424893;
 //BA.debugLineNum = 2424893;BA.debugLine="extra.InitPanel(pnl4_moshakhasat,2,1,Colors.rgb(2";
mostCurrent._extra._initpanel(mostCurrent.activityBA,mostCurrent._pnl4_moshakhasat,(float) (2),(int) (1),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (204),(int) (204),(int) (204)));
RDebugUtils.currentLine=2424895;
 //BA.debugLineNum = 2424895;BA.debugLine="moretext_data = \"در حال بارگذاری\"";
mostCurrent._moretext_data = "در حال بارگذاری";
RDebugUtils.currentLine=2424897;
 //BA.debugLineNum = 2424897;BA.debugLine="RTLJustify1.Initialize(\"\")";
mostCurrent._rtljustify1.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424898;
 //BA.debugLineNum = 2424898;BA.debugLine="RTLJustify1.Text = moretext_data";
mostCurrent._rtljustify1.setText(BA.ObjectToCharSequence(mostCurrent._moretext_data));
RDebugUtils.currentLine=2424899;
 //BA.debugLineNum = 2424899;BA.debugLine="RTLJustify1.Typeface = Typeface.LoadFromAssets(\"y";
mostCurrent._rtljustify1.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2424901;
 //BA.debugLineNum = 2424901;BA.debugLine="RTLJustify1.TextColor = Colors.rgb(89, 89, 89)";
mostCurrent._rtljustify1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (89),(int) (89),(int) (89)));
RDebugUtils.currentLine=2424902;
 //BA.debugLineNum = 2424902;BA.debugLine="RTLJustify1.Gravity = Gravity.FILL";
mostCurrent._rtljustify1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=2424903;
 //BA.debugLineNum = 2424903;BA.debugLine="RTLJustify1.TextSize = 10dip";
mostCurrent._rtljustify1.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=2424905;
 //BA.debugLineNum = 2424905;BA.debugLine="Dim Obj1 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=2424906;
 //BA.debugLineNum = 2424906;BA.debugLine="Obj1.Target = RTLJustify1";
_obj1.Target = (Object)(mostCurrent._rtljustify1.getObject());
RDebugUtils.currentLine=2424907;
 //BA.debugLineNum = 2424907;BA.debugLine="Obj1.RunMethod3(\"setLineSpacing\", 1, \"java.lang.f";
_obj1.RunMethod3("setLineSpacing",BA.NumberToString(1),"java.lang.float",BA.NumberToString(1.5),"java.lang.float");
RDebugUtils.currentLine=2424908;
 //BA.debugLineNum = 2424908;BA.debugLine="product_ScrollView.Panel.AddView(RTLJustify1,15di";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._rtljustify1.getObject()),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185)));
RDebugUtils.currentLine=2424911;
 //BA.debugLineNum = 2424911;BA.debugLine="pnl4_line.Initialize(\"\")";
mostCurrent._pnl4_line.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424912;
 //BA.debugLineNum = 2424912;BA.debugLine="pnl4_line.Color = Colors.rgb(179, 179, 179)";
mostCurrent._pnl4_line.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (179),(int) (179),(int) (179)));
RDebugUtils.currentLine=2424913;
 //BA.debugLineNum = 2424913;BA.debugLine="product_ScrollView.Panel.AddView(pnl4_line,30dip,";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._pnl4_line.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
RDebugUtils.currentLine=2424916;
 //BA.debugLineNum = 2424916;BA.debugLine="category_hscrollview.Initialize(100%x,\"\")";
mostCurrent._category_hscrollview.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
RDebugUtils.currentLine=2424917;
 //BA.debugLineNum = 2424917;BA.debugLine="category_hscrollview.Panel.Height = 50dip";
mostCurrent._category_hscrollview.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=2424918;
 //BA.debugLineNum = 2424918;BA.debugLine="product_ScrollView.Panel.AddView(category_hscroll";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._category_hscrollview.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=2424921;
 //BA.debugLineNum = 2424921;BA.debugLine="scrollview_lastproduct.Initialize(100%x,\"\")";
mostCurrent._scrollview_lastproduct.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),"");
RDebugUtils.currentLine=2424922;
 //BA.debugLineNum = 2424922;BA.debugLine="scrollview_lastproduct.Panel.Height = 450dip";
mostCurrent._scrollview_lastproduct.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
RDebugUtils.currentLine=2424923;
 //BA.debugLineNum = 2424923;BA.debugLine="product_ScrollView.Panel.AddView(scrollview_lastp";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._scrollview_lastproduct.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (450)));
RDebugUtils.currentLine=2424927;
 //BA.debugLineNum = 2424927;BA.debugLine="moretext.Initialize(\"moretext\")";
mostCurrent._moretext.Initialize(mostCurrent.activityBA,"moretext");
RDebugUtils.currentLine=2424928;
 //BA.debugLineNum = 2424928;BA.debugLine="moretext.TextColor = Colors.rgb(140, 140, 140)";
mostCurrent._moretext.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=2424929;
 //BA.debugLineNum = 2424929;BA.debugLine="moretext.Gravity = Gravity.CENTER";
mostCurrent._moretext.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=2424930;
 //BA.debugLineNum = 2424930;BA.debugLine="moretext.Typeface = Typeface.LoadFromAssets(\"yeka";
mostCurrent._moretext.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2424931;
 //BA.debugLineNum = 2424931;BA.debugLine="moretext.TextSize = 10dip";
mostCurrent._moretext.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
RDebugUtils.currentLine=2424932;
 //BA.debugLineNum = 2424932;BA.debugLine="moretext.Text = \"ادامه مطلب\"";
mostCurrent._moretext.setText(BA.ObjectToCharSequence("ادامه مطلب"));
RDebugUtils.currentLine=2424933;
 //BA.debugLineNum = 2424933;BA.debugLine="product_ScrollView.Panel.AddView(moretext,15dip,5";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._moretext.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (185))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=2424935;
 //BA.debugLineNum = 2424935;BA.debugLine="Dim btn As Panel";
_btn = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2424936;
 //BA.debugLineNum = 2424936;BA.debugLine="btn.Initialize(\"\")";
_btn.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424937;
 //BA.debugLineNum = 2424937;BA.debugLine="btn.Color = Colors.rgb(102, 187, 106)";
_btn.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=2424939;
 //BA.debugLineNum = 2424939;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
RDebugUtils.currentLine=2424940;
 //BA.debugLineNum = 2424940;BA.debugLine="cd.Initialize(Colors.rgb(102, 187, 106), 5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
RDebugUtils.currentLine=2424941;
 //BA.debugLineNum = 2424941;BA.debugLine="btn.Background = cd";
_btn.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
RDebugUtils.currentLine=2424942;
 //BA.debugLineNum = 2424942;BA.debugLine="product_ScrollView.Panel.AddView(btn,50dip,50%x+4";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_btn.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=2424944;
 //BA.debugLineNum = 2424944;BA.debugLine="Dim buy As Label";
_buy = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2424945;
 //BA.debugLineNum = 2424945;BA.debugLine="buy.Initialize(\"\")";
_buy.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424946;
 //BA.debugLineNum = 2424946;BA.debugLine="buy.TextColor = Colors.White";
_buy.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2424947;
 //BA.debugLineNum = 2424947;BA.debugLine="buy.Gravity = Gravity.CENTER";
_buy.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=2424948;
 //BA.debugLineNum = 2424948;BA.debugLine="buy.Typeface = Typeface.LoadFromAssets(\"yekan.ttf";
_buy.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2424949;
 //BA.debugLineNum = 2424949;BA.debugLine="buy.TextSize = 12dip";
_buy.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))));
RDebugUtils.currentLine=2424950;
 //BA.debugLineNum = 2424950;BA.debugLine="buy.Text = \"افزودن به سبد خرید\"";
_buy.setText(BA.ObjectToCharSequence("افزودن به سبد خرید"));
RDebugUtils.currentLine=2424951;
 //BA.debugLineNum = 2424951;BA.debugLine="product_ScrollView.Panel.AddView(buy,50dip,50%x+4";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_buy.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (65),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=2424953;
 //BA.debugLineNum = 2424953;BA.debugLine="Dim buyicon As Label";
_buyicon = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2424954;
 //BA.debugLineNum = 2424954;BA.debugLine="buyicon.Initialize(\"\")";
_buyicon.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424955;
 //BA.debugLineNum = 2424955;BA.debugLine="Private FAe As FontAwesome";
_fae = new njdude.fontawesome.lib.fontawesome();
RDebugUtils.currentLine=2424956;
 //BA.debugLineNum = 2424956;BA.debugLine="FAe.Initialize";
_fae._initialize(processBA);
RDebugUtils.currentLine=2424957;
 //BA.debugLineNum = 2424957;BA.debugLine="buyicon.Gravity = Gravity.CENTER_VERTICAL";
_buyicon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
RDebugUtils.currentLine=2424958;
 //BA.debugLineNum = 2424958;BA.debugLine="buyicon.Typeface = Typeface.FONTAWESOME";
_buyicon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
RDebugUtils.currentLine=2424959;
 //BA.debugLineNum = 2424959;BA.debugLine="buyicon.TextColor = Colors.White";
_buyicon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2424960;
 //BA.debugLineNum = 2424960;BA.debugLine="buyicon.TextSize = 13dip";
_buyicon.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
RDebugUtils.currentLine=2424961;
 //BA.debugLineNum = 2424961;BA.debugLine="buyicon.Text = FAe.GetFontAwesomeIconByName(\"fa-c";
_buyicon.setText(BA.ObjectToCharSequence(_fae._getfontawesomeiconbyname("fa-cart-plus")));
RDebugUtils.currentLine=2424962;
 //BA.debugLineNum = 2424962;BA.debugLine="product_ScrollView.Panel.AddView(buyicon,70%x,50%";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_buyicon.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (420))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
RDebugUtils.currentLine=2424965;
 //BA.debugLineNum = 2424965;BA.debugLine="Dim propert As Label";
_propert = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2424966;
 //BA.debugLineNum = 2424966;BA.debugLine="propert.Initialize(\"propert\")";
_propert.Initialize(mostCurrent.activityBA,"propert");
RDebugUtils.currentLine=2424967;
 //BA.debugLineNum = 2424967;BA.debugLine="propert.TextColor = Colors.Black";
_propert.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2424968;
 //BA.debugLineNum = 2424968;BA.debugLine="propert.Gravity = Gravity.CENTER";
_propert.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=2424969;
 //BA.debugLineNum = 2424969;BA.debugLine="propert.Typeface = Typeface.LoadFromAssets(\"yekan";
_propert.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2424970;
 //BA.debugLineNum = 2424970;BA.debugLine="propert.Text = \"مشخصات\"";
_propert.setText(BA.ObjectToCharSequence("مشخصات"));
RDebugUtils.currentLine=2424971;
 //BA.debugLineNum = 2424971;BA.debugLine="product_ScrollView.Panel.AddView(propert,15dip,50";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_propert.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=2424975;
 //BA.debugLineNum = 2424975;BA.debugLine="total.Initialize(\"\")";
mostCurrent._total.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424976;
 //BA.debugLineNum = 2424976;BA.debugLine="total.TextColor = Colors.rgb(102, 187, 106)";
mostCurrent._total.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=2424977;
 //BA.debugLineNum = 2424977;BA.debugLine="total.Gravity = Gravity.LEFT";
mostCurrent._total.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
RDebugUtils.currentLine=2424978;
 //BA.debugLineNum = 2424978;BA.debugLine="total.Typeface = Typeface.LoadFromAssets(\"yekan.t";
mostCurrent._total.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2424979;
 //BA.debugLineNum = 2424979;BA.debugLine="total.Text = \"\"";
mostCurrent._total.setText(BA.ObjectToCharSequence(""));
RDebugUtils.currentLine=2424980;
 //BA.debugLineNum = 2424980;BA.debugLine="total.TextSize = 13dip";
mostCurrent._total.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (13))));
RDebugUtils.currentLine=2424981;
 //BA.debugLineNum = 2424981;BA.debugLine="product_ScrollView.Panel.AddView(total,32dip,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._total.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (178))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
RDebugUtils.currentLine=2424986;
 //BA.debugLineNum = 2424986;BA.debugLine="Private FA As FontAwesome";
_fa = new njdude.fontawesome.lib.fontawesome();
RDebugUtils.currentLine=2424987;
 //BA.debugLineNum = 2424987;BA.debugLine="FA.Initialize";
_fa._initialize(processBA);
RDebugUtils.currentLine=2424988;
 //BA.debugLineNum = 2424988;BA.debugLine="Dim properticon As Label";
_properticon = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2424989;
 //BA.debugLineNum = 2424989;BA.debugLine="properticon.Initialize(\"\")";
_properticon.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2424990;
 //BA.debugLineNum = 2424990;BA.debugLine="properticon.TextColor = Colors.Black";
_properticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2424992;
 //BA.debugLineNum = 2424992;BA.debugLine="properticon.Gravity = Gravity.CENTER_VERTICAL";
_properticon.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
RDebugUtils.currentLine=2424993;
 //BA.debugLineNum = 2424993;BA.debugLine="properticon.Typeface = Typeface.FONTAWESOME";
_properticon.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
RDebugUtils.currentLine=2424994;
 //BA.debugLineNum = 2424994;BA.debugLine="properticon.Text = FA.GetFontAwesomeIconByName(\"f";
_properticon.setText(BA.ObjectToCharSequence(_fa._getfontawesomeiconbyname("fa-calendar-o")));
RDebugUtils.currentLine=2424995;
 //BA.debugLineNum = 2424995;BA.debugLine="product_ScrollView.Panel.AddView(properticon,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_properticon.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35)));
RDebugUtils.currentLine=2424999;
 //BA.debugLineNum = 2424999;BA.debugLine="lbl_title.Initialize(\"\")";
mostCurrent._lbl_title.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425000;
 //BA.debugLineNum = 2425000;BA.debugLine="lbl_title.Text =  \"\"";
mostCurrent._lbl_title.setText(BA.ObjectToCharSequence(""));
RDebugUtils.currentLine=2425001;
 //BA.debugLineNum = 2425001;BA.debugLine="extra.product_title =  \"\"";
mostCurrent._extra._product_title = "";
RDebugUtils.currentLine=2425002;
 //BA.debugLineNum = 2425002;BA.debugLine="lbl_title.Typeface = Typeface.LoadFromAssets(\"yek";
mostCurrent._lbl_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2425003;
 //BA.debugLineNum = 2425003;BA.debugLine="lbl_title.TextSize = 11dip";
mostCurrent._lbl_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
RDebugUtils.currentLine=2425004;
 //BA.debugLineNum = 2425004;BA.debugLine="lbl_title.Gravity = Gravity.RIGHT";
mostCurrent._lbl_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2425005;
 //BA.debugLineNum = 2425005;BA.debugLine="lbl_title.TextColor = Colors.Black";
mostCurrent._lbl_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
RDebugUtils.currentLine=2425006;
 //BA.debugLineNum = 2425006;BA.debugLine="product_ScrollView.Panel.AddView(lbl_title,0,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._lbl_title.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (85))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=2425008;
 //BA.debugLineNum = 2425008;BA.debugLine="lbl_titlen.Initialize(\"\")";
mostCurrent._lbl_titlen.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425009;
 //BA.debugLineNum = 2425009;BA.debugLine="lbl_titlen.Text =  \"\"";
mostCurrent._lbl_titlen.setText(BA.ObjectToCharSequence(""));
RDebugUtils.currentLine=2425010;
 //BA.debugLineNum = 2425010;BA.debugLine="lbl_titlen.Typeface = Typeface.LoadFromAssets(\"ye";
mostCurrent._lbl_titlen.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2425011;
 //BA.debugLineNum = 2425011;BA.debugLine="lbl_titlen.TextSize = 8dip";
mostCurrent._lbl_titlen.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
RDebugUtils.currentLine=2425012;
 //BA.debugLineNum = 2425012;BA.debugLine="lbl_titlen.Gravity = Gravity.RIGHT";
mostCurrent._lbl_titlen.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2425013;
 //BA.debugLineNum = 2425013;BA.debugLine="lbl_titlen.TextColor = Colors.rgb(169, 169, 169)";
mostCurrent._lbl_titlen.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (169),(int) (169),(int) (169)));
RDebugUtils.currentLine=2425014;
 //BA.debugLineNum = 2425014;BA.debugLine="product_ScrollView.Panel.AddView(lbl_titlen,0,50%";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(mostCurrent._lbl_titlen.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
RDebugUtils.currentLine=2425021;
 //BA.debugLineNum = 2425021;BA.debugLine="Dim pic_like As ImageView";
_pic_like = new anywheresoftware.b4a.objects.ImageViewWrapper();
RDebugUtils.currentLine=2425022;
 //BA.debugLineNum = 2425022;BA.debugLine="pic_like.Initialize(\"\")";
_pic_like.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425023;
 //BA.debugLineNum = 2425023;BA.debugLine="pic_like.Gravity = Gravity.FILL";
_pic_like.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=2425024;
 //BA.debugLineNum = 2425024;BA.debugLine="pic_like.Bitmap = LoadBitmap(File.DirAssets,\"like";
_pic_like.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"like.png").getObject()));
RDebugUtils.currentLine=2425028;
 //BA.debugLineNum = 2425028;BA.debugLine="Dim color As Label";
_color = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2425029;
 //BA.debugLineNum = 2425029;BA.debugLine="color.Initialize(\"\")";
_color.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425030;
 //BA.debugLineNum = 2425030;BA.debugLine="color.TextColor = Colors.rgb(140, 140, 140)";
_color.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=2425031;
 //BA.debugLineNum = 2425031;BA.debugLine="color.Gravity = Gravity.RIGHT";
_color.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2425032;
 //BA.debugLineNum = 2425032;BA.debugLine="color.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_color.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2425033;
 //BA.debugLineNum = 2425033;BA.debugLine="color.Text = \"رنگ\"";
_color.setText(BA.ObjectToCharSequence("رنگ"));
RDebugUtils.currentLine=2425034;
 //BA.debugLineNum = 2425034;BA.debugLine="color.TextSize = 9dip";
_color.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=2425035;
 //BA.debugLineNum = 2425035;BA.debugLine="product_ScrollView.Panel.AddView(color,0,50%x+270";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_color.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (270))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=2425038;
 //BA.debugLineNum = 2425038;BA.debugLine="Dim garanti As Label";
_garanti = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2425039;
 //BA.debugLineNum = 2425039;BA.debugLine="garanti.Initialize(\"\")";
_garanti.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425040;
 //BA.debugLineNum = 2425040;BA.debugLine="garanti.TextColor = Colors.rgb(140, 140, 140)";
_garanti.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=2425041;
 //BA.debugLineNum = 2425041;BA.debugLine="garanti.Gravity = Gravity.RIGHT";
_garanti.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2425042;
 //BA.debugLineNum = 2425042;BA.debugLine="garanti.Typeface = Typeface.LoadFromAssets(\"yekan";
_garanti.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2425043;
 //BA.debugLineNum = 2425043;BA.debugLine="garanti.Text = \"گارانتی\"";
_garanti.setText(BA.ObjectToCharSequence("گارانتی"));
RDebugUtils.currentLine=2425044;
 //BA.debugLineNum = 2425044;BA.debugLine="garanti.TextSize = 9dip";
_garanti.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=2425045;
 //BA.debugLineNum = 2425045;BA.debugLine="product_ScrollView.Panel.AddView(garanti,0,50%x+3";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_garanti.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
RDebugUtils.currentLine=2425048;
 //BA.debugLineNum = 2425048;BA.debugLine="Dim saler As Label";
_saler = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2425049;
 //BA.debugLineNum = 2425049;BA.debugLine="saler.Initialize(\"\")";
_saler.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425050;
 //BA.debugLineNum = 2425050;BA.debugLine="saler.TextColor = Colors.rgb(140, 140, 140)";
_saler.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (140),(int) (140),(int) (140)));
RDebugUtils.currentLine=2425051;
 //BA.debugLineNum = 2425051;BA.debugLine="saler.Gravity = Gravity.RIGHT";
_saler.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2425052;
 //BA.debugLineNum = 2425052;BA.debugLine="saler.Typeface = Typeface.LoadFromAssets(\"yekan.t";
_saler.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2425053;
 //BA.debugLineNum = 2425053;BA.debugLine="saler.Text = \"فروشنده\"";
_saler.setText(BA.ObjectToCharSequence("فروشنده"));
RDebugUtils.currentLine=2425054;
 //BA.debugLineNum = 2425054;BA.debugLine="saler.TextSize = 9dip";
_saler.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=2425057;
 //BA.debugLineNum = 2425057;BA.debugLine="Dim header_title As Label";
mostCurrent._header_title = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2425058;
 //BA.debugLineNum = 2425058;BA.debugLine="header_title.Initialize(\"\")";
mostCurrent._header_title.Initialize(mostCurrent.activityBA,"");
RDebugUtils.currentLine=2425060;
 //BA.debugLineNum = 2425060;BA.debugLine="header_title.Gravity = Gravity.RIGHT";
mostCurrent._header_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2425061;
 //BA.debugLineNum = 2425061;BA.debugLine="header_title.Typeface = Typeface.LoadFromAssets(\"";
mostCurrent._header_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2425062;
 //BA.debugLineNum = 2425062;BA.debugLine="header_title.TextColor = Colors.red";
mostCurrent._header_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
RDebugUtils.currentLine=2425063;
 //BA.debugLineNum = 2425063;BA.debugLine="header_title.TextSize = 11dip";
mostCurrent._header_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
RDebugUtils.currentLine=2425064;
 //BA.debugLineNum = 2425064;BA.debugLine="header_title.Visible = False";
mostCurrent._header_title.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=2425065;
 //BA.debugLineNum = 2425065;BA.debugLine="product_header.AddView(header_title,0,5dip,95%x,4";
mostCurrent._product_header.AddView((android.view.View)(mostCurrent._header_title.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
RDebugUtils.currentLine=2425074;
 //BA.debugLineNum = 2425074;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_keypress"))
	return (Boolean) Debug.delegate(mostCurrent.activityBA, "activity_keypress", new Object[] {_keycode});
RDebugUtils.currentLine=2490368;
 //BA.debugLineNum = 2490368;BA.debugLine="Sub Activity_KeyPress(KeyCode As Int) As Boolean";
RDebugUtils.currentLine=2490370;
 //BA.debugLineNum = 2490370;BA.debugLine="If KeyCode= KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
RDebugUtils.currentLine=2490371;
 //BA.debugLineNum = 2490371;BA.debugLine="Log(\"backed\")";
anywheresoftware.b4a.keywords.Common.Log("backed");
RDebugUtils.currentLine=2490372;
 //BA.debugLineNum = 2490372;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
RDebugUtils.currentLine=2490374;
 //BA.debugLineNum = 2490374;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
RDebugUtils.currentModule="product";
RDebugUtils.currentLine=2686976;
 //BA.debugLineNum = 2686976;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
RDebugUtils.currentLine=2686978;
 //BA.debugLineNum = 2686978;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "activity_resume"))
	return (String) Debug.delegate(mostCurrent.activityBA, "activity_resume", null);
anywheresoftware.b4a.samples.httputils2.httpjob _jober = null;
RDebugUtils.currentLine=2621440;
 //BA.debugLineNum = 2621440;BA.debugLine="Sub Activity_Resume";
RDebugUtils.currentLine=2621441;
 //BA.debugLineNum = 2621441;BA.debugLine="Dim jober As HttpJob";
_jober = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=2621442;
 //BA.debugLineNum = 2621442;BA.debugLine="jober.Initialize(\"lastproduct\",Me)";
_jober._initialize(processBA,"lastproduct",product.getObject());
RDebugUtils.currentLine=2621443;
 //BA.debugLineNum = 2621443;BA.debugLine="jober.PostString(extra.api,\"op=lastproduct\")";
_jober._poststring(mostCurrent._extra._api,"op=lastproduct");
RDebugUtils.currentLine=2621444;
 //BA.debugLineNum = 2621444;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "jobdone"))
	return (String) Debug.delegate(mostCurrent.activityBA, "jobdone", new Object[] {_job});
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
RDebugUtils.currentLine=2752512;
 //BA.debugLineNum = 2752512;BA.debugLine="Sub jobdone(job As HttpJob)";
RDebugUtils.currentLine=2752514;
 //BA.debugLineNum = 2752514;BA.debugLine="Log(job.JobName)";
anywheresoftware.b4a.keywords.Common.Log(_job._jobname);
RDebugUtils.currentLine=2752515;
 //BA.debugLineNum = 2752515;BA.debugLine="If job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
RDebugUtils.currentLine=2752517;
 //BA.debugLineNum = 2752517;BA.debugLine="If job.JobName = \"picproc\" Then";
if ((_job._jobname).equals("picproc")) { 
RDebugUtils.currentLine=2752518;
 //BA.debugLineNum = 2752518;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 };
RDebugUtils.currentLine=2752529;
 //BA.debugLineNum = 2752529;BA.debugLine="If job.JobName = \"textproc\" Then";
if ((_job._jobname).equals("textproc")) { 
RDebugUtils.currentLine=2752530;
 //BA.debugLineNum = 2752530;BA.debugLine="Dim s As String";
_s = "";
RDebugUtils.currentLine=2752531;
 //BA.debugLineNum = 2752531;BA.debugLine="s = job.GetString.Replace(\"&lt;\",\"\").Replace(\"";
_s = _job._getstring().replace("&lt;","").replace("p&gt;","").replace("br&gt;","").replace("/&lt;","").replace("/p&gt;","").replace("/br&gt;","").replace("p style=&quot;text-align: justify; &quot;&gt;","");
RDebugUtils.currentLine=2752532;
 //BA.debugLineNum = 2752532;BA.debugLine="s =s.Replace(\"amp;\",\"\").Replace(\"nbsp;\",\"\").Re";
_s = _s.replace("amp;","").replace("nbsp;","").replace("/null","");
RDebugUtils.currentLine=2752533;
 //BA.debugLineNum = 2752533;BA.debugLine="RTLJustify1.Text = s";
mostCurrent._rtljustify1.setText(BA.ObjectToCharSequence(_s));
 };
RDebugUtils.currentLine=2752536;
 //BA.debugLineNum = 2752536;BA.debugLine="Try";
try {RDebugUtils.currentLine=2752537;
 //BA.debugLineNum = 2752537;BA.debugLine="If job.JobName = \"nameproc\" Then";
if ((_job._jobname).equals("nameproc")) { 
RDebugUtils.currentLine=2752538;
 //BA.debugLineNum = 2752538;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
RDebugUtils.currentLine=2752539;
 //BA.debugLineNum = 2752539;BA.debugLine="Dim d1 As String";
_d1 = "";
RDebugUtils.currentLine=2752540;
 //BA.debugLineNum = 2752540;BA.debugLine="d1 = job.GetString.SubString2(0,job.GetString.";
_d1 = _job._getstring().substring((int) (0),_job._getstring().indexOf("^"));
RDebugUtils.currentLine=2752542;
 //BA.debugLineNum = 2752542;BA.debugLine="extra.propertyjson = job.GetString.SubString2(";
mostCurrent._extra._propertyjson = (int)(Double.parseDouble(_job._getstring().substring((int) (_job._getstring().indexOf("^")+1),_job._getstring().length())));
RDebugUtils.currentLine=2752543;
 //BA.debugLineNum = 2752543;BA.debugLine="Log(extra.propertyjson)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._extra._propertyjson));
RDebugUtils.currentLine=2752544;
 //BA.debugLineNum = 2752544;BA.debugLine="Dim s As String";
_s = "";
RDebugUtils.currentLine=2752545;
 //BA.debugLineNum = 2752545;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=2752546;
 //BA.debugLineNum = 2752546;BA.debugLine="parser.Initialize(d1)";
_parser.Initialize(_d1);
RDebugUtils.currentLine=2752547;
 //BA.debugLineNum = 2752547;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=2752548;
 //BA.debugLineNum = 2752548;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group23 = _root;
final int groupLen23 = group23.getSize()
;int index23 = 0;
;
for (; index23 < groupLen23;index23++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group23.Get(index23)));
RDebugUtils.currentLine=2752549;
 //BA.debugLineNum = 2752549;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
RDebugUtils.currentLine=2752550;
 //BA.debugLineNum = 2752550;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=2752551;
 //BA.debugLineNum = 2752551;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
RDebugUtils.currentLine=2752552;
 //BA.debugLineNum = 2752552;BA.debugLine="lbl_title.Text = name";
mostCurrent._lbl_title.setText(BA.ObjectToCharSequence(_name));
RDebugUtils.currentLine=2752553;
 //BA.debugLineNum = 2752553;BA.debugLine="lbl_titlen.Text =  model";
mostCurrent._lbl_titlen.setText(BA.ObjectToCharSequence(_model));
RDebugUtils.currentLine=2752554;
 //BA.debugLineNum = 2752554;BA.debugLine="header_title.Text = name";
mostCurrent._header_title.setText(BA.ObjectToCharSequence(_name));
RDebugUtils.currentLine=2752555;
 //BA.debugLineNum = 2752555;BA.debugLine="extra.product_title = name";
mostCurrent._extra._product_title = _name;
RDebugUtils.currentLine=2752556;
 //BA.debugLineNum = 2752556;BA.debugLine="total.Text = price.SubString2(0,price.IndexOf";
mostCurrent._total.setText(BA.ObjectToCharSequence(_price.substring((int) (0),_price.indexOf("."))+" "+"تومان"));
 }
};
 };
 } 
       catch (Exception e35) {
			processBA.setLastException(e35);RDebugUtils.currentLine=2752560;
 //BA.debugLineNum = 2752560;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
RDebugUtils.currentLine=2752562;
 //BA.debugLineNum = 2752562;BA.debugLine="Try";
try {RDebugUtils.currentLine=2752563;
 //BA.debugLineNum = 2752563;BA.debugLine="If  job.JobName.SubString2(0,9)=\"imageview\" The";
if ((_job._jobname.substring((int) (0),(int) (9))).equals("imageview")) { 
RDebugUtils.currentLine=2752564;
 //BA.debugLineNum = 2752564;BA.debugLine="Dim indexf As Int";
_indexf = 0;
RDebugUtils.currentLine=2752565;
 //BA.debugLineNum = 2752565;BA.debugLine="Dim name As String";
_name = "";
RDebugUtils.currentLine=2752566;
 //BA.debugLineNum = 2752566;BA.debugLine="indexf = job.JobName.SubString2(job.JobName.In";
_indexf = (int)(Double.parseDouble(_job._jobname.substring((int) (_job._jobname.indexOf("*")+1),_job._jobname.lastIndexOf("*"))));
RDebugUtils.currentLine=2752567;
 //BA.debugLineNum = 2752567;BA.debugLine="name = job.JobName.SubString(job.JobName.LastI";
_name = _job._jobname.substring((int) (_job._jobname.lastIndexOf("/")+1));
RDebugUtils.currentLine=2752568;
 //BA.debugLineNum = 2752568;BA.debugLine="Dim OutStream As OutputStream";
_outstream = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
RDebugUtils.currentLine=2752569;
 //BA.debugLineNum = 2752569;BA.debugLine="OutStream = File.OpenOutput(File.DirInternalCa";
_outstream = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name,anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=2752570;
 //BA.debugLineNum = 2752570;BA.debugLine="File.Copy2(job.GetInputStream,OutStream)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_outstream.getObject()));
RDebugUtils.currentLine=2752571;
 //BA.debugLineNum = 2752571;BA.debugLine="OutStream.Close";
_outstream.Close();
RDebugUtils.currentLine=2752572;
 //BA.debugLineNum = 2752572;BA.debugLine="ImageView(indexf).Bitmap = LoadBitmap(File.Dir";
mostCurrent._imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_name).getObject()));
 };
 } 
       catch (Exception e50) {
			processBA.setLastException(e50);RDebugUtils.currentLine=2752579;
 //BA.debugLineNum = 2752579;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
RDebugUtils.currentLine=2752581;
 //BA.debugLineNum = 2752581;BA.debugLine="If job.JobName = \"load_category_main\" Then";
if ((_job._jobname).equals("load_category_main")) { 
RDebugUtils.currentLine=2752582;
 //BA.debugLineNum = 2752582;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=2752583;
 //BA.debugLineNum = 2752583;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=2752584;
 //BA.debugLineNum = 2752584;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
RDebugUtils.currentLine=2752585;
 //BA.debugLineNum = 2752585;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=2752586;
 //BA.debugLineNum = 2752586;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group57 = _root;
final int groupLen57 = group57.getSize()
;int index57 = 0;
;
for (; index57 < groupLen57;index57++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group57.Get(index57)));
RDebugUtils.currentLine=2752587;
 //BA.debugLineNum = 2752587;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
RDebugUtils.currentLine=2752588;
 //BA.debugLineNum = 2752588;BA.debugLine="Dim text As String = colroot.Get(\"name\")";
_text = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=2752589;
 //BA.debugLineNum = 2752589;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2752590;
 //BA.debugLineNum = 2752590;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
RDebugUtils.currentLine=2752591;
 //BA.debugLineNum = 2752591;BA.debugLine="lable.Color = Colors.rgb(102, 187, 106)";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=2752592;
 //BA.debugLineNum = 2752592;BA.debugLine="lable.TextColor = Colors.White";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2752593;
 //BA.debugLineNum = 2752593;BA.debugLine="lable.Gravity = Gravity.CENTER";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
RDebugUtils.currentLine=2752594;
 //BA.debugLineNum = 2752594;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"yeka";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2752595;
 //BA.debugLineNum = 2752595;BA.debugLine="lable.TextSize = \"20\"";
_lable.setTextSize((float)(Double.parseDouble("20")));
RDebugUtils.currentLine=2752596;
 //BA.debugLineNum = 2752596;BA.debugLine="lable.Text = text";
_lable.setText(BA.ObjectToCharSequence(_text));
RDebugUtils.currentLine=2752597;
 //BA.debugLineNum = 2752597;BA.debugLine="category_hscrollview.Panel.AddView(lable,left,";
mostCurrent._category_hscrollview.getPanel().AddView((android.view.View)(_lable.getObject()),_left,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2.5)),(int) ((_text.length()*30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
RDebugUtils.currentLine=2752598;
 //BA.debugLineNum = 2752598;BA.debugLine="left =( text.Length * 30  ) + left +8dip";
_left = (int) ((_text.length()*30)+_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8)));
 }
};
RDebugUtils.currentLine=2752600;
 //BA.debugLineNum = 2752600;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=2752601;
 //BA.debugLineNum = 2752601;BA.debugLine="r.Target = category_hscrollview";
_r.Target = (Object)(mostCurrent._category_hscrollview.getObject());
RDebugUtils.currentLine=2752602;
 //BA.debugLineNum = 2752602;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\", F";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
RDebugUtils.currentLine=2752603;
 //BA.debugLineNum = 2752603;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
RDebugUtils.currentLine=2752605;
 //BA.debugLineNum = 2752605;BA.debugLine="category_hscrollview.Panel.Width = left";
mostCurrent._category_hscrollview.getPanel().setWidth(_left);
RDebugUtils.currentLine=2752606;
 //BA.debugLineNum = 2752606;BA.debugLine="category_hscrollview.FullScroll(True)";
mostCurrent._category_hscrollview.FullScroll(anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=2752607;
 //BA.debugLineNum = 2752607;BA.debugLine="category_hscrollview.ScrollPosition = 50dip";
mostCurrent._category_hscrollview.setScrollPosition(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 };
RDebugUtils.currentLine=2752609;
 //BA.debugLineNum = 2752609;BA.debugLine="If job.JobName=\"lastproduct\" Then";
if ((_job._jobname).equals("lastproduct")) { 
RDebugUtils.currentLine=2752610;
 //BA.debugLineNum = 2752610;BA.debugLine="Dim left As Int=15dip";
_left = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
RDebugUtils.currentLine=2752611;
 //BA.debugLineNum = 2752611;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
RDebugUtils.currentLine=2752612;
 //BA.debugLineNum = 2752612;BA.debugLine="parser.Initialize(job.GetString)";
_parser.Initialize(_job._getstring());
RDebugUtils.currentLine=2752613;
 //BA.debugLineNum = 2752613;BA.debugLine="Dim indexf As Int = 1";
_indexf = (int) (1);
RDebugUtils.currentLine=2752614;
 //BA.debugLineNum = 2752614;BA.debugLine="Dim root As List = parser.NextArray";
_root = new anywheresoftware.b4a.objects.collections.List();
_root = _parser.NextArray();
RDebugUtils.currentLine=2752615;
 //BA.debugLineNum = 2752615;BA.debugLine="For Each colroot As Map In root";
_colroot = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group85 = _root;
final int groupLen85 = group85.getSize()
;int index85 = 0;
;
for (; index85 < groupLen85;index85++){
_colroot.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group85.Get(index85)));
RDebugUtils.currentLine=2752616;
 //BA.debugLineNum = 2752616;BA.debugLine="Dim image As String = colroot.Get(\"image\")";
_image = BA.ObjectToString(_colroot.Get((Object)("image")));
RDebugUtils.currentLine=2752617;
 //BA.debugLineNum = 2752617;BA.debugLine="Dim price As String = colroot.Get(\"price\")";
_price = BA.ObjectToString(_colroot.Get((Object)("price")));
RDebugUtils.currentLine=2752618;
 //BA.debugLineNum = 2752618;BA.debugLine="Dim model As String = colroot.Get(\"model\")";
_model = BA.ObjectToString(_colroot.Get((Object)("model")));
RDebugUtils.currentLine=2752619;
 //BA.debugLineNum = 2752619;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
_name = BA.ObjectToString(_colroot.Get((Object)("name")));
RDebugUtils.currentLine=2752620;
 //BA.debugLineNum = 2752620;BA.debugLine="Dim id As String = colroot.Get(\"id\")";
_id = BA.ObjectToString(_colroot.Get((Object)("id")));
RDebugUtils.currentLine=2752621;
 //BA.debugLineNum = 2752621;BA.debugLine="Dim panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2752622;
 //BA.debugLineNum = 2752622;BA.debugLine="panel.Initialize(\"lastproduct_panel\")";
_panel.Initialize(mostCurrent.activityBA,"lastproduct_panel");
RDebugUtils.currentLine=2752623;
 //BA.debugLineNum = 2752623;BA.debugLine="panel.Tag = id";
_panel.setTag((Object)(_id));
RDebugUtils.currentLine=2752624;
 //BA.debugLineNum = 2752624;BA.debugLine="panel.Color = Colors.White";
_panel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2752625;
 //BA.debugLineNum = 2752625;BA.debugLine="scrollview_lastproduct.Panel.AddView(panel,lef";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_panel.getObject()),_left,(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (225))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
RDebugUtils.currentLine=2752626;
 //BA.debugLineNum = 2752626;BA.debugLine="extra.InitPanel(panel,1dip,Colors.White,Colors";
mostCurrent._extra._initpanel(mostCurrent.activityBA,_panel,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)));
RDebugUtils.currentLine=2752627;
 //BA.debugLineNum = 2752627;BA.debugLine="Dim lable As Label";
_lable = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2752628;
 //BA.debugLineNum = 2752628;BA.debugLine="lable.Initialize(\"lable\")";
_lable.Initialize(mostCurrent.activityBA,"lable");
RDebugUtils.currentLine=2752629;
 //BA.debugLineNum = 2752629;BA.debugLine="lable.Color = Colors.White";
_lable.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2752630;
 //BA.debugLineNum = 2752630;BA.debugLine="lable.TextColor = Colors.rgb(65, 65, 65)";
_lable.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (65),(int) (65),(int) (65)));
RDebugUtils.currentLine=2752631;
 //BA.debugLineNum = 2752631;BA.debugLine="lable.Gravity = Gravity.RIGHT";
_lable.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
RDebugUtils.currentLine=2752632;
 //BA.debugLineNum = 2752632;BA.debugLine="lable.Typeface = Typeface.LoadFromAssets(\"yeka";
_lable.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2752633;
 //BA.debugLineNum = 2752633;BA.debugLine="lable.TextSize = \"16\"";
_lable.setTextSize((float)(Double.parseDouble("16")));
RDebugUtils.currentLine=2752634;
 //BA.debugLineNum = 2752634;BA.debugLine="lable.Text = name";
_lable.setText(BA.ObjectToCharSequence(_name));
RDebugUtils.currentLine=2752635;
 //BA.debugLineNum = 2752635;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable,lef";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
RDebugUtils.currentLine=2752637;
 //BA.debugLineNum = 2752637;BA.debugLine="Dim lable2 As Label";
_lable2 = new anywheresoftware.b4a.objects.LabelWrapper();
RDebugUtils.currentLine=2752638;
 //BA.debugLineNum = 2752638;BA.debugLine="lable2.Initialize(\"lable2\")";
_lable2.Initialize(mostCurrent.activityBA,"lable2");
RDebugUtils.currentLine=2752639;
 //BA.debugLineNum = 2752639;BA.debugLine="lable2.Color = Colors.White";
_lable2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
RDebugUtils.currentLine=2752640;
 //BA.debugLineNum = 2752640;BA.debugLine="lable2.TextColor = Colors.rgb(102, 187, 106)";
_lable2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (102),(int) (187),(int) (106)));
RDebugUtils.currentLine=2752641;
 //BA.debugLineNum = 2752641;BA.debugLine="lable2.Typeface = Typeface.DEFAULT_BOLD";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
RDebugUtils.currentLine=2752642;
 //BA.debugLineNum = 2752642;BA.debugLine="lable2.Gravity = Gravity.LEFT";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.LEFT);
RDebugUtils.currentLine=2752643;
 //BA.debugLineNum = 2752643;BA.debugLine="lable2.Gravity = Gravity.CENTER_VERTICAL";
_lable2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
RDebugUtils.currentLine=2752644;
 //BA.debugLineNum = 2752644;BA.debugLine="lable2.Typeface = Typeface.LoadFromAssets(\"yek";
_lable2.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
RDebugUtils.currentLine=2752645;
 //BA.debugLineNum = 2752645;BA.debugLine="lable2.TextSize = \"16\"";
_lable2.setTextSize((float)(Double.parseDouble("16")));
RDebugUtils.currentLine=2752646;
 //BA.debugLineNum = 2752646;BA.debugLine="lable2.Text = price";
_lable2.setText(BA.ObjectToCharSequence(_price));
RDebugUtils.currentLine=2752647;
 //BA.debugLineNum = 2752647;BA.debugLine="scrollview_lastproduct.Panel.AddView(lable2,le";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(_lable2.getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (195)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)));
RDebugUtils.currentLine=2752649;
 //BA.debugLineNum = 2752649;BA.debugLine="ImageView(indexf).Initialize(\"ImageView\")";
mostCurrent._imageview[_indexf].Initialize(mostCurrent.activityBA,"ImageView");
RDebugUtils.currentLine=2752650;
 //BA.debugLineNum = 2752650;BA.debugLine="If File.Exists(File.DirInternalCache, image.Su";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_image.substring((int) (_image.lastIndexOf("/")+1),_image.length()))) { 
RDebugUtils.currentLine=2752651;
 //BA.debugLineNum = 2752651;BA.debugLine="ImageView(indexf).Bitmap = LoadBitmap(File.Di";
mostCurrent._imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirInternalCache(),_image.substring((int) (_image.lastIndexOf("/")+1),_image.length())).getObject()));
 }else {
RDebugUtils.currentLine=2752653;
 //BA.debugLineNum = 2752653;BA.debugLine="ImageView(indexf).Bitmap = LoadBitmap(File.Di";
mostCurrent._imageview[_indexf].setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fileset/main_defult_product.jpg").getObject()));
 };
RDebugUtils.currentLine=2752656;
 //BA.debugLineNum = 2752656;BA.debugLine="ImageView(indexf).Gravity = Gravity.FILL";
mostCurrent._imageview[_indexf].setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
RDebugUtils.currentLine=2752657;
 //BA.debugLineNum = 2752657;BA.debugLine="scrollview_lastproduct.Panel.AddView(ImageView";
mostCurrent._scrollview_lastproduct.getPanel().AddView((android.view.View)(mostCurrent._imageview[_indexf].getObject()),(int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (157))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
RDebugUtils.currentLine=2752659;
 //BA.debugLineNum = 2752659;BA.debugLine="Dim CanvasGraph As Canvas";
_canvasgraph = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
RDebugUtils.currentLine=2752660;
 //BA.debugLineNum = 2752660;BA.debugLine="CanvasGraph.Initialize(panel)";
_canvasgraph.Initialize((android.view.View)(_panel.getObject()));
RDebugUtils.currentLine=2752662;
 //BA.debugLineNum = 2752662;BA.debugLine="CanvasGraph.DrawLine(0,140dip,160dip,140dip,Co";
_canvasgraph.DrawLine((float) (0),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140))),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (207),(int) (204),(int) (204)),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
RDebugUtils.currentLine=2752663;
 //BA.debugLineNum = 2752663;BA.debugLine="left = left + 170dip";
_left = (int) (_left+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
RDebugUtils.currentLine=2752664;
 //BA.debugLineNum = 2752664;BA.debugLine="indexf= indexf + 1";
_indexf = (int) (_indexf+1);
 }
};
RDebugUtils.currentLine=2752666;
 //BA.debugLineNum = 2752666;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
RDebugUtils.currentLine=2752667;
 //BA.debugLineNum = 2752667;BA.debugLine="r.Target = scrollview_lastproduct";
_r.Target = (Object)(mostCurrent._scrollview_lastproduct.getObject());
RDebugUtils.currentLine=2752668;
 //BA.debugLineNum = 2752668;BA.debugLine="r.RunMethod2(\"setHorizontalScrollBarEnabled\", F";
_r.RunMethod2("setHorizontalScrollBarEnabled",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False),"java.lang.boolean");
RDebugUtils.currentLine=2752669;
 //BA.debugLineNum = 2752669;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang";
_r.RunMethod2("setOverScrollMode",BA.NumberToString(2),"java.lang.int");
RDebugUtils.currentLine=2752670;
 //BA.debugLineNum = 2752670;BA.debugLine="scrollview_lastproduct.Panel.Width = left";
mostCurrent._scrollview_lastproduct.getPanel().setWidth(_left);
RDebugUtils.currentLine=2752671;
 //BA.debugLineNum = 2752671;BA.debugLine="product_ScrollView.Panel.Height = 50%x+164.5dip";
mostCurrent._product_scrollview.getPanel().setHeight((int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164.5))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (48))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (295))));
 };
 };
RDebugUtils.currentLine=2752680;
 //BA.debugLineNum = 2752680;BA.debugLine="End Sub";
return "";
}
public static String  _lastproduct_panel_click() throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "lastproduct_panel_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "lastproduct_panel_click", null);
anywheresoftware.b4a.objects.PanelWrapper _lastproduct_panel = null;
RDebugUtils.currentLine=2818048;
 //BA.debugLineNum = 2818048;BA.debugLine="Sub lastproduct_panel_click()";
RDebugUtils.currentLine=2818049;
 //BA.debugLineNum = 2818049;BA.debugLine="Dim lastproduct_panel As Panel";
_lastproduct_panel = new anywheresoftware.b4a.objects.PanelWrapper();
RDebugUtils.currentLine=2818050;
 //BA.debugLineNum = 2818050;BA.debugLine="lastproduct_panel = Sender";
_lastproduct_panel.setObject((android.view.ViewGroup)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
RDebugUtils.currentLine=2818052;
 //BA.debugLineNum = 2818052;BA.debugLine="End Sub";
return "";
}
public static String  _moretext_click() throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "moretext_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "moretext_click", null);
anywheresoftware.b4a.objects.StringUtils _su = null;
int _lener = 0;
RDebugUtils.currentLine=2883584;
 //BA.debugLineNum = 2883584;BA.debugLine="Sub moretext_click()";
RDebugUtils.currentLine=2883585;
 //BA.debugLineNum = 2883585;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
RDebugUtils.currentLine=2883586;
 //BA.debugLineNum = 2883586;BA.debugLine="Dim lener As Int";
_lener = 0;
RDebugUtils.currentLine=2883587;
 //BA.debugLineNum = 2883587;BA.debugLine="lener = RTLJustify1.Text.Length";
_lener = mostCurrent._rtljustify1.getText().length();
RDebugUtils.currentLine=2883588;
 //BA.debugLineNum = 2883588;BA.debugLine="Log(lener)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lener));
RDebugUtils.currentLine=2883589;
 //BA.debugLineNum = 2883589;BA.debugLine="pnl4_moshakhasat.Height = lener * 0.275%x + 30dip";
mostCurrent._pnl4_moshakhasat.setHeight((int) (_lener*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0.275),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))));
RDebugUtils.currentLine=2883590;
 //BA.debugLineNum = 2883590;BA.debugLine="RTLJustify1.Height = lener * 0.275%x";
mostCurrent._rtljustify1.setHeight((int) (_lener*anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0.275),mostCurrent.activityBA)));
RDebugUtils.currentLine=2883592;
 //BA.debugLineNum = 2883592;BA.debugLine="moretext.Visible = False";
mostCurrent._moretext.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=2883593;
 //BA.debugLineNum = 2883593;BA.debugLine="pnl4_line.Visible = False";
mostCurrent._pnl4_line.setVisible(anywheresoftware.b4a.keywords.Common.False);
RDebugUtils.currentLine=2883595;
 //BA.debugLineNum = 2883595;BA.debugLine="category_hscrollview.Top = RTLJustify1.Top + RTLJ";
mostCurrent._category_hscrollview.setTop((int) (mostCurrent._rtljustify1.getTop()+mostCurrent._rtljustify1.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
RDebugUtils.currentLine=2883596;
 //BA.debugLineNum = 2883596;BA.debugLine="scrollview_lastproduct.Top = category_hscrollview";
mostCurrent._scrollview_lastproduct.setTop((int) (mostCurrent._category_hscrollview.getTop()+mostCurrent._category_hscrollview.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (9))));
RDebugUtils.currentLine=2883598;
 //BA.debugLineNum = 2883598;BA.debugLine="product_ScrollView.Panel.Height =product_ScrollVi";
mostCurrent._product_scrollview.getPanel().setHeight((int) (mostCurrent._product_scrollview.getPanel().getHeight()+mostCurrent._rtljustify1.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (289))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))));
RDebugUtils.currentLine=2883599;
 //BA.debugLineNum = 2883599;BA.debugLine="End Sub";
return "";
}
public static String  _product_scrollview_scrollchanged(int _position) throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "product_scrollview_scrollchanged"))
	return (String) Debug.delegate(mostCurrent.activityBA, "product_scrollview_scrollchanged", new Object[] {_position});
RDebugUtils.currentLine=2555904;
 //BA.debugLineNum = 2555904;BA.debugLine="Sub product_ScrollView_ScrollChanged(Position As I";
RDebugUtils.currentLine=2555905;
 //BA.debugLineNum = 2555905;BA.debugLine="If Position <= 511 Then";
if (_position<=511) { 
RDebugUtils.currentLine=2555906;
 //BA.debugLineNum = 2555906;BA.debugLine="product_header.Color = Colors.ARGB(Position/2,25";
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (_position/(double)2),(int) (255),(int) (204),(int) (255)));
 };
RDebugUtils.currentLine=2555908;
 //BA.debugLineNum = 2555908;BA.debugLine="If Position = 0 Then product_header.Color = Color";
if (_position==0) { 
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (255),(int) (255)));};
RDebugUtils.currentLine=2555910;
 //BA.debugLineNum = 2555910;BA.debugLine="If (Position+83dip) > lbl_title.Top Then";
if ((_position+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (83)))>mostCurrent._lbl_title.getTop()) { 
RDebugUtils.currentLine=2555911;
 //BA.debugLineNum = 2555911;BA.debugLine="If extra.product_title_flag = False Then extra.p";
if (mostCurrent._extra._product_title_flag==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._extra._product_title_top = _position;};
RDebugUtils.currentLine=2555914;
 //BA.debugLineNum = 2555914;BA.debugLine="tempint =  75dip    -    (  Position - extra.pro";
_tempint = (int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (75))-(_position-mostCurrent._extra._product_title_top));
RDebugUtils.currentLine=2555915;
 //BA.debugLineNum = 2555915;BA.debugLine="If tempint>= 10dip Then";
if (_tempint>=anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))) { 
RDebugUtils.currentLine=2555916;
 //BA.debugLineNum = 2555916;BA.debugLine="Log(tempint)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_tempint));
RDebugUtils.currentLine=2555917;
 //BA.debugLineNum = 2555917;BA.debugLine="header_title.Top = tempint";
mostCurrent._header_title.setTop(_tempint);
RDebugUtils.currentLine=2555918;
 //BA.debugLineNum = 2555918;BA.debugLine="header_title.Visible = True";
mostCurrent._header_title.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
RDebugUtils.currentLine=2555920;
 //BA.debugLineNum = 2555920;BA.debugLine="product_header.Color = Colors.ARGB(255,255, 204";
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (204),(int) (255)));
RDebugUtils.currentLine=2555921;
 //BA.debugLineNum = 2555921;BA.debugLine="header_title.Top = 10dip";
mostCurrent._header_title.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
RDebugUtils.currentLine=2555922;
 //BA.debugLineNum = 2555922;BA.debugLine="Log(\"end i\")";
anywheresoftware.b4a.keywords.Common.Log("end i");
 };
RDebugUtils.currentLine=2555924;
 //BA.debugLineNum = 2555924;BA.debugLine="extra.product_title_flag = True";
mostCurrent._extra._product_title_flag = anywheresoftware.b4a.keywords.Common.True;
 }else {
RDebugUtils.currentLine=2555926;
 //BA.debugLineNum = 2555926;BA.debugLine="extra.product_title_flag = False";
mostCurrent._extra._product_title_flag = anywheresoftware.b4a.keywords.Common.False;
RDebugUtils.currentLine=2555927;
 //BA.debugLineNum = 2555927;BA.debugLine="header_title.Visible = False";
mostCurrent._header_title.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
RDebugUtils.currentLine=2555929;
 //BA.debugLineNum = 2555929;BA.debugLine="End Sub";
return "";
}
public static String  _propert_click() throws Exception{
RDebugUtils.currentModule="product";
if (Debug.shouldDelegate(mostCurrent.activityBA, "propert_click"))
	return (String) Debug.delegate(mostCurrent.activityBA, "propert_click", null);
RDebugUtils.currentLine=2949120;
 //BA.debugLineNum = 2949120;BA.debugLine="Sub propert_click()";
RDebugUtils.currentLine=2949121;
 //BA.debugLineNum = 2949121;BA.debugLine="StartActivity(property)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._property.getObject()));
RDebugUtils.currentLine=2949122;
 //BA.debugLineNum = 2949122;BA.debugLine="End Sub";
return "";
}
}