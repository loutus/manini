package manini.b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class extra {
private static extra mostCurrent = new extra();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _api = "";
public static int _index_ob_top = 0;
public static int _index_ob_top_cach = 0;
public static int[] _index_ob_olaviyat = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.index _index = null;
public static String  _load_index(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _load_indexjob = null;
 //BA.debugLineNum = 12;BA.debugLine="Sub load_index()";
 //BA.debugLineNum = 13;BA.debugLine="Dim load_indexjob As HttpJob";
_load_indexjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 14;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
_load_indexjob._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_indexjob",(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 15;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
_load_indexjob._poststring(_api,"op=index");
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim api As String = \"http://itrx.babapkg.ir/api.p";
_api = "http://itrx.babapkg.ir/api.php";
 //BA.debugLineNum = 7;BA.debugLine="Dim index_ob_top As Int = 0";
_index_ob_top = (int) (0);
 //BA.debugLineNum = 8;BA.debugLine="Dim index_ob_top_cach As Int =10dip";
_index_ob_top_cach = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 9;BA.debugLine="Dim index_ob_olaviyat(50) As Int";
_index_ob_olaviyat = new int[(int) (50)];
;
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
