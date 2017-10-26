package manini.b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,29);
if (RapidSub.canDelegate("activity_create")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","activity_create", _firsttime);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"loading\")";
Debug.ShouldStop(1073741824);
main.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("loading")),main.mostCurrent.activityBA);
 BA.debugLineNum = 32;BA.debugLine="logo.Bitmap = LoadBitmap ( File.DirAssets,\"filese";
Debug.ShouldStop(-2147483648);
main.mostCurrent._logo.runMethod(false,"setBitmap",(main.mostCurrent.__c.runMethod(false,"LoadBitmap",(Object)(main.mostCurrent.__c.getField(false,"File").runMethod(true,"getDirAssets")),(Object)(RemoteObject.createImmutable("fileset/logo.png"))).getObject()));
 BA.debugLineNum = 33;BA.debugLine="timer_cheknet.Initialize(\"timer_cheknet\",4000)";
Debug.ShouldStop(1);
main.mostCurrent._timer_cheknet.runVoidMethod ("Initialize",main.processBA,(Object)(BA.ObjectToString("timer_cheknet")),(Object)(BA.numberCast(long.class, 4000)));
 BA.debugLineNum = 34;BA.debugLine="timer_cheknet.Enabled = True";
Debug.ShouldStop(2);
main.mostCurrent._timer_cheknet.runMethod(true,"setEnabled",main.mostCurrent.__c.getField(true,"True"));
 BA.debugLineNum = 35;BA.debugLine="End Sub";
Debug.ShouldStop(4);
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
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,60);
if (RapidSub.canDelegate("activity_pause")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 60;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 62;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
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
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,56);
if (RapidSub.canDelegate("activity_resume")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","activity_resume");
 BA.debugLineNum = 56;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 58;BA.debugLine="End Sub";
Debug.ShouldStop(33554432);
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
		Debug.PushSubsStack("cheknet (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,51);
if (RapidSub.canDelegate("cheknet")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","cheknet");
RemoteObject _cheknetjob = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
 BA.debugLineNum = 51;BA.debugLine="Sub cheknet()";
Debug.ShouldStop(262144);
 BA.debugLineNum = 52;BA.debugLine="Dim cheknetjob As HttpJob";
Debug.ShouldStop(524288);
_cheknetjob = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("cheknetjob", _cheknetjob);
 BA.debugLineNum = 53;BA.debugLine="cheknetjob.Initialize(\"cheknet\",Me)";
Debug.ShouldStop(1048576);
_cheknetjob.runVoidMethod ("_initialize",main.processBA,(Object)(BA.ObjectToString("cheknet")),(Object)(main.getObject()));
 BA.debugLineNum = 54;BA.debugLine="cheknetjob.PostString(extra.api,\"op=cheknet\")";
Debug.ShouldStop(2097152);
_cheknetjob.runVoidMethod ("_poststring",(Object)(main.mostCurrent._extra._api),(Object)(RemoteObject.createImmutable("op=cheknet")));
 BA.debugLineNum = 55;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim timer_cheknet As Timer";
main.mostCurrent._timer_cheknet = RemoteObject.createNew ("anywheresoftware.b4a.objects.Timer");
 //BA.debugLineNum = 25;BA.debugLine="Private logo As ImageView";
main.mostCurrent._logo = RemoteObject.createNew ("anywheresoftware.b4a.objects.ImageViewWrapper");
 //BA.debugLineNum = 26;BA.debugLine="Private loadding_text As Label";
main.mostCurrent._loadding_text = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _jobdone(RemoteObject _job) throws Exception{
try {
		Debug.PushSubsStack("jobdone (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,36);
if (RapidSub.canDelegate("jobdone")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","jobdone", _job);
Debug.locals.put("job", _job);
 BA.debugLineNum = 36;BA.debugLine="Sub jobdone(job As HttpJob)";
Debug.ShouldStop(8);
 BA.debugLineNum = 37;BA.debugLine="If job.Success = True Then";
Debug.ShouldStop(16);
if (RemoteObject.solveBoolean("=",_job.getField(true,"_success"),main.mostCurrent.__c.getField(true,"True"))) { 
 BA.debugLineNum = 38;BA.debugLine="If job.JobName = \"cheknet\" Then";
Debug.ShouldStop(32);
if (RemoteObject.solveBoolean("=",_job.getField(true,"_jobname"),BA.ObjectToString("cheknet"))) { 
 BA.debugLineNum = 39;BA.debugLine="If job.GetString = \"ok\" Then";
Debug.ShouldStop(64);
if (RemoteObject.solveBoolean("=",_job.runMethod(true,"_getstring"),BA.ObjectToString("ok"))) { 
 BA.debugLineNum = 40;BA.debugLine="Log(job.GetString)";
Debug.ShouldStop(128);
main.mostCurrent.__c.runVoidMethod ("Log",(Object)(_job.runMethod(true,"_getstring")));
 BA.debugLineNum = 41;BA.debugLine="timer_cheknet.Enabled = False";
Debug.ShouldStop(256);
main.mostCurrent._timer_cheknet.runMethod(true,"setEnabled",main.mostCurrent.__c.getField(true,"False"));
 BA.debugLineNum = 42;BA.debugLine="StartActivity(index)";
Debug.ShouldStop(512);
main.mostCurrent.__c.runVoidMethod ("StartActivity",main.processBA,(Object)((main.mostCurrent._index.getObject())));
 BA.debugLineNum = 43;BA.debugLine="Activity.Finish";
Debug.ShouldStop(1024);
main.mostCurrent._activity.runVoidMethod ("Finish");
 }else {
 BA.debugLineNum = 45;BA.debugLine="loadding_text.Text = \"اتصال خود به اینترنت را";
Debug.ShouldStop(4096);
main.mostCurrent._loadding_text.runMethod(true,"setText",BA.ObjectToCharSequence("اتصال خود به اینترنت را چک کنید"));
 BA.debugLineNum = 46;BA.debugLine="timer_cheknet.Enabled = True";
Debug.ShouldStop(8192);
main.mostCurrent._timer_cheknet.runMethod(true,"setEnabled",main.mostCurrent.__c.getField(true,"True"));
 };
 };
 };
 BA.debugLineNum = 50;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
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
main.myClass = BA.getDeviceClass ("manini.b4a.example.main");
starter.myClass = BA.getDeviceClass ("manini.b4a.example.starter");
extra.myClass = BA.getDeviceClass ("manini.b4a.example.extra");
index.myClass = BA.getDeviceClass ("manini.b4a.example.index");
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _timer_cheknet_tick() throws Exception{
try {
		Debug.PushSubsStack("timer_cheknet_Tick (main) ","main",0,main.mostCurrent.activityBA,main.mostCurrent,63);
if (RapidSub.canDelegate("timer_cheknet_tick")) return manini.b4a.example.main.remoteMe.runUserSub(false, "main","timer_cheknet_tick");
 BA.debugLineNum = 63;BA.debugLine="Sub timer_cheknet_Tick";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 64;BA.debugLine="Log(\"tick\")";
Debug.ShouldStop(-2147483648);
main.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("tick")));
 BA.debugLineNum = 65;BA.debugLine="cheknet";
Debug.ShouldStop(1);
_cheknet();
 BA.debugLineNum = 66;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}