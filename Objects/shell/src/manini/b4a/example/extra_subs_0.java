package manini.b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class extra_subs_0 {


public static RemoteObject  _load_index(RemoteObject _ba) throws Exception{
try {
		Debug.PushSubsStack("load_index (extra) ","extra",2,_ba,extra.mostCurrent,13);
if (RapidSub.canDelegate("load_index")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","load_index", _ba);
RemoteObject _load_indexjob = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
;
 BA.debugLineNum = 13;BA.debugLine="Sub load_index()";
Debug.ShouldStop(4096);
 BA.debugLineNum = 14;BA.debugLine="Dim load_indexjob As HttpJob";
Debug.ShouldStop(8192);
_load_indexjob = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("load_indexjob", _load_indexjob);
 BA.debugLineNum = 15;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
Debug.ShouldStop(16384);
_load_indexjob.runVoidMethod ("_initialize",BA.rdebugUtils.runMethod(false, "processBAFromBA", _ba),(Object)(BA.ObjectToString("load_indexjob")),(Object)((extra.mostCurrent._index.getObject())));
 BA.debugLineNum = 16;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
Debug.ShouldStop(32768);
_load_indexjob.runVoidMethod ("_poststring",(Object)(extra._api),(Object)(RemoteObject.createImmutable("op=index")));
 BA.debugLineNum = 17;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim api As String = \"http://itrx.babapkg.ir/api.p";
extra._api = BA.ObjectToString("http://itrx.babapkg.ir/api.php");
 //BA.debugLineNum = 7;BA.debugLine="Dim image_address As String = \"http://itrx.babapk";
extra._image_address = BA.ObjectToString("http://itrx.babapkg.ir/image/");
 //BA.debugLineNum = 8;BA.debugLine="Dim index_ob_top As Int = 0";
extra._index_ob_top = BA.numberCast(int.class, 0);
 //BA.debugLineNum = 9;BA.debugLine="Dim index_ob_top_cach As Int =10dip";
extra._index_ob_top_cach = extra.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)));
 //BA.debugLineNum = 10;BA.debugLine="Dim index_ob_olaviyat(200) As Int";
extra._index_ob_olaviyat = RemoteObject.createNewArray ("int", new int[] {200}, new Object[]{});
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}