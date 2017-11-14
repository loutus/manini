package manini.b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,24);
if (RapidSub.canDelegate("activity_create")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","activity_create", _firsttime);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 24;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 26;BA.debugLine="Activity.LoadLayout(\"loading\")";
Debug.ShouldStop(33554432);
main.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("loading")),main.mostCurrent.activityBA);
 BA.debugLineNum = 27;BA.debugLine="logo.Bitmap = LoadBitmap ( File.DirAssets,\"filese";
Debug.ShouldStop(67108864);
main.mostCurrent._logo.runMethod(false,"setBitmap",(main.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(main.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.createImmutable("fileset/logo.png"))).getObject()));
 BA.debugLineNum = 28;BA.debugLine="cheknet";
Debug.ShouldStop(134217728);
_cheknet();
 BA.debugLineNum = 31;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_pause(RemoteObject _userclosed) throws Exception{
try {
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,59);
if (RapidSub.canDelegate("activity_pause")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 59;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 60;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _activity_resume() throws Exception{
try {
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,55);
if (RapidSub.canDelegate("activity_resume")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","activity_resume");
 BA.debugLineNum = 55;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 57;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _cheknet() throws Exception{
try {
		Debug.PushSubsStack("cheknet (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,49);
if (RapidSub.canDelegate("cheknet")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","cheknet");
RemoteObject _cheknetjob = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
 BA.debugLineNum = 49;BA.debugLine="Sub cheknet()";
Debug.ShouldStop(65536);
 BA.debugLineNum = 50;BA.debugLine="Log(\"cheknet\")";
Debug.ShouldStop(131072);
main.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("cheknet")));
 BA.debugLineNum = 51;BA.debugLine="Dim cheknetjob As HttpJob";
Debug.ShouldStop(262144);
_cheknetjob = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("cheknetjob", _cheknetjob);
 BA.debugLineNum = 52;BA.debugLine="cheknetjob.Initialize(\"cheknet\",Me)";
Debug.ShouldStop(524288);
_cheknetjob.runVoidMethod ("_initialize",main.processBA,(Object)(BA.ObjectToString("cheknet")),(Object)(main.getObject()));
 BA.debugLineNum = 53;BA.debugLine="cheknetjob.PostString(extra.api,\"op=cheknet\")";
Debug.ShouldStop(1048576);
_cheknetjob.runVoidMethod ("_poststring",(Object)(main.mostCurrent._extra._api),(Object)(RemoteObject.createImmutable("op=cheknet")));
 BA.debugLineNum = 54;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Dim timer_cheknet As Timer";
main.mostCurrent._timer_cheknet = RemoteObject.createNew ("anywheresoftware.b4a.objects.Timer");
 //BA.debugLineNum = 21;BA.debugLine="Private logo As ImageView";
main.mostCurrent._logo = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 22;BA.debugLine="Private loadding_text As Label";
main.mostCurrent._loadding_text = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _jobdone(RemoteObject _job) throws Exception{
try {
		Debug.PushSubsStack("jobdone (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,32);
if (RapidSub.canDelegate("jobdone")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","jobdone", _job);
Debug.locals.put("job", _job);
 BA.debugLineNum = 32;BA.debugLine="Sub jobdone(job As HttpJob)";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 33;BA.debugLine="If job.Success = True Then";
Debug.ShouldStop(1);
if (RemoteObject.solveBoolean("=",_job.getField(true,"_success"),main.mostCurrent.__c.getField(true,"True"))) { 
 BA.debugLineNum = 34;BA.debugLine="Log(job.JobName)";
Debug.ShouldStop(2);
main.mostCurrent.__c.runVoidMethod ("Log",(Object)(_job.getField(true,"_jobname")));
 BA.debugLineNum = 35;BA.debugLine="If job.JobName = \"cheknet\" Then";
Debug.ShouldStop(4);
if (RemoteObject.solveBoolean("=",_job.getField(true,"_jobname"),BA.ObjectToString("cheknet"))) { 
 BA.debugLineNum = 36;BA.debugLine="Log(job.GetString)";
Debug.ShouldStop(8);
main.mostCurrent.__c.runVoidMethod ("Log",(Object)(_job.runMethod(true,"_getstring")));
 BA.debugLineNum = 37;BA.debugLine="If job.GetString = \"ok\" Then";
Debug.ShouldStop(16);
if (RemoteObject.solveBoolean("=",_job.runMethod(true,"_getstring"),BA.ObjectToString("ok"))) { 
 BA.debugLineNum = 38;BA.debugLine="timer_cheknet.Enabled = False";
Debug.ShouldStop(32);
main.mostCurrent._timer_cheknet.runMethod(true,"setEnabled",main.mostCurrent.__c.getField(true,"False"));
 BA.debugLineNum = 39;BA.debugLine="timer_cheknet.Interval = 0";
Debug.ShouldStop(64);
main.mostCurrent._timer_cheknet.runMethod(true,"setInterval",BA.numberCast(long.class, 0));
 BA.debugLineNum = 40;BA.debugLine="StartActivity(index)";
Debug.ShouldStop(128);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._index.getObject())));
 BA.debugLineNum = 41;BA.debugLine="Activity.Finish";
Debug.ShouldStop(256);
main.mostCurrent._activity.runVoidMethod ("Finish");
 }else {
 BA.debugLineNum = 43;BA.debugLine="loadding_text.Text = \"اتصال خود به اینترنت را";
Debug.ShouldStop(1024);
main.mostCurrent._loadding_text.runMethod(true,"setText",BA.ObjectToCharSequence("اتصال خود به اینترنت را چک کنید"));
 BA.debugLineNum = 44;BA.debugLine="timer_cheknet.Enabled = True";
Debug.ShouldStop(2048);
main.mostCurrent._timer_cheknet.runMethod(true,"setEnabled",main.mostCurrent.__c.getField(true,"True"));
 };
 };
 };
 BA.debugLineNum = 48;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main_subs_0._process_globals();
starter_subs_0._process_globals();
extra_subs_0._process_globals();
index_subs_0._process_globals();
product_subs_0._process_globals();
property_subs_0._process_globals();
omid_subs_0._process_globals();
main.myClass = BA.getDeviceClass ("manini.b4a.example.main");
starter.myClass = BA.getDeviceClass ("manini.b4a.example.starter");
extra.myClass = BA.getDeviceClass ("manini.b4a.example.extra");
index.myClass = BA.getDeviceClass ("manini.b4a.example.index");
product.myClass = BA.getDeviceClass ("manini.b4a.example.product");
property.myClass = BA.getDeviceClass ("manini.b4a.example.property");
omid.myClass = BA.getDeviceClass ("manini.b4a.example.omid");
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _timer_cheknet_tick() throws Exception{
try {
		Debug.PushSubsStack("timer_cheknet_Tick (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,61);
if (RapidSub.canDelegate("timer_cheknet_tick")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","timer_cheknet_tick");
 BA.debugLineNum = 61;BA.debugLine="Sub timer_cheknet_Tick";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 62;BA.debugLine="Log(\"tick\")";
Debug.ShouldStop(536870912);
main.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("tick")));
 BA.debugLineNum = 63;BA.debugLine="cheknet";
Debug.ShouldStop(1073741824);
_cheknet();
 BA.debugLineNum = 64;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}