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
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.extra _extra = null;
public manini.b4a.example.index _index = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
de.amberhome.viewpager.AHPageContainer _container = null;
de.amberhome.viewpager.AHViewPager _pager = null;
anywheresoftware.b4a.objects.PanelWrapper[] _pan = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl_title = null;
anywheresoftware.b4a.objects.ImageViewWrapper _pic_sheare = null;
anywheresoftware.b4a.objects.ImageViewWrapper _pic_like = null;
 //BA.debugLineNum = 22;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 24;BA.debugLine="Activity.LoadLayout(\"product\")";
mostCurrent._activity.LoadLayout("product",mostCurrent.activityBA);
 //BA.debugLineNum = 25;BA.debugLine="Dim Container As AHPageContainer";
_container = new de.amberhome.viewpager.AHPageContainer();
 //BA.debugLineNum = 26;BA.debugLine="Dim Pager As AHViewPager";
_pager = new de.amberhome.viewpager.AHViewPager();
 //BA.debugLineNum = 27;BA.debugLine="Container.Initialize";
_container.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 28;BA.debugLine="Dim pan(5) As Panel";
_pan = new anywheresoftware.b4a.objects.PanelWrapper[(int) (5)];
{
int d0 = _pan.length;
for (int i0 = 0;i0 < d0;i0++) {
_pan[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 29;BA.debugLine="pan(0).Initialize(\"\")";
_pan[(int) (0)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 30;BA.debugLine="pan(1).Initialize(\"\")";
_pan[(int) (1)].Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 31;BA.debugLine="pan(0).LoadLayout(\"product_img1\")";
_pan[(int) (0)].LoadLayout("product_img1",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="Container.AddPageAt(pan(0), \"Main\", 0)";
_container.AddPageAt((android.view.View)(_pan[(int) (0)].getObject()),"Main",(int) (0));
 //BA.debugLineNum = 34;BA.debugLine="pan(1).LoadLayout(\"product_img2\")";
_pan[(int) (1)].LoadLayout("product_img2",mostCurrent.activityBA);
 //BA.debugLineNum = 35;BA.debugLine="Container.AddPageAt(pan(1), \"Main2\", 1)";
_container.AddPageAt((android.view.View)(_pan[(int) (1)].getObject()),"Main2",(int) (1));
 //BA.debugLineNum = 37;BA.debugLine="Pager.Initialize2(Container, \"Pager\")";
_pager.Initialize2(mostCurrent.activityBA,_container,"Pager");
 //BA.debugLineNum = 38;BA.debugLine="product_ScrollView.Panel.AddView(Pager,0,55dip,10";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pager.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))));
 //BA.debugLineNum = 40;BA.debugLine="Dim pnl As Panel";
_pnl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="pnl.Initialize(\"\")";
_pnl.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 42;BA.debugLine="pnl.Color = Colors.rgb(250, 250, 250)";
_pnl.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (250),(int) (250),(int) (250)));
 //BA.debugLineNum = 44;BA.debugLine="product_ScrollView.Panel.AddView(pnl,0,50%x+55dip";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pnl.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 45;BA.debugLine="Dim lbl_title As Label";
_lbl_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="lbl_title.Initialize(\"\")";
_lbl_title.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 47;BA.debugLine="lbl_title.Text =  \"دوربین 20 مگاپیکسلی قضیه\"";
_lbl_title.setText(BA.ObjectToCharSequence("دوربین 20 مگاپیکسلی قضیه"));
 //BA.debugLineNum = 48;BA.debugLine="lbl_title.Typeface = Typeface.LoadFromAssets(\"yek";
_lbl_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 49;BA.debugLine="lbl_title.TextSize = 11dip";
_lbl_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (11))));
 //BA.debugLineNum = 50;BA.debugLine="lbl_title.Gravity = Gravity.RIGHT";
_lbl_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 51;BA.debugLine="lbl_title.TextColor = Colors.Black";
_lbl_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 52;BA.debugLine="product_ScrollView.Panel.AddView(lbl_title,0,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_lbl_title.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (85))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 54;BA.debugLine="lbl_title.Initialize(\"\")";
_lbl_title.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 55;BA.debugLine="lbl_title.Text =  \"Camera 20 MP Pexual for\"";
_lbl_title.setText(BA.ObjectToCharSequence("Camera 20 MP Pexual for"));
 //BA.debugLineNum = 56;BA.debugLine="lbl_title.Typeface = Typeface.LoadFromAssets(\"yek";
_lbl_title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("yekan.ttf"));
 //BA.debugLineNum = 57;BA.debugLine="lbl_title.TextSize = 8dip";
_lbl_title.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 58;BA.debugLine="lbl_title.Gravity = Gravity.RIGHT";
_lbl_title.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 59;BA.debugLine="lbl_title.TextColor = Colors.rgb(169, 169, 169)";
_lbl_title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (169),(int) (169),(int) (169)));
 //BA.debugLineNum = 60;BA.debugLine="product_ScrollView.Panel.AddView(lbl_title,0,50%x";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_lbl_title.getObject()),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 62;BA.debugLine="Dim pic_sheare As ImageView";
_pic_sheare = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 63;BA.debugLine="pic_sheare.Initialize(\"\")";
_pic_sheare.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 64;BA.debugLine="pic_sheare.Gravity = Gravity.FILL";
_pic_sheare.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 65;BA.debugLine="pic_sheare.Bitmap = LoadBitmap(File.DirAssets,\"sh";
_pic_sheare.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sharing-interface.png").getObject()));
 //BA.debugLineNum = 67;BA.debugLine="Dim pic_like As ImageView";
_pic_like = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 68;BA.debugLine="pic_like.Initialize(\"\")";
_pic_like.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 69;BA.debugLine="pic_like.Gravity = Gravity.FILL";
_pic_like.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 70;BA.debugLine="pic_like.Bitmap = LoadBitmap(File.DirAssets,\"like";
_pic_like.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"like.png").getObject()));
 //BA.debugLineNum = 72;BA.debugLine="product_ScrollView.Panel.AddView(pic_sheare,30dip";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pic_sheare.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)));
 //BA.debugLineNum = 73;BA.debugLine="product_ScrollView.Panel.AddView(pic_like,80dip,5";
mostCurrent._product_scrollview.getPanel().AddView((android.view.View)(_pic_like.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (18)));
 //BA.debugLineNum = 74;BA.debugLine="product_header.BringToFront";
mostCurrent._product_header.BringToFront();
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private product_ScrollView As ScrollView";
mostCurrent._product_scrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private product_header As Panel";
mostCurrent._product_header = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private product_imag1 As ImageView";
mostCurrent._product_imag1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private product_img2 As ImageView";
mostCurrent._product_img2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _product_scrollview_scrollchanged(int _position) throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Sub product_ScrollView_ScrollChanged(Position As I";
 //BA.debugLineNum = 79;BA.debugLine="If Position < 510 Then";
if (_position<510) { 
 //BA.debugLineNum = 80;BA.debugLine="product_header.Color = Colors.RGB(200,-Position/";
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (200),(int) (-_position/(double)2),(int) (150)));
 };
 //BA.debugLineNum = 82;BA.debugLine="If Position = 0 Then product_header.Color = Color";
if (_position==0) { 
mostCurrent._product_header.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (255),(int) (255),(int) (255)));};
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
}
