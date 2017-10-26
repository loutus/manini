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
public static String _image_address = "";
public static int _index_ob_top = 0;
public static int _index_ob_top_cach = 0;
public static int[] _index_ob_olaviyat = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.index _index = null;
public static String  _load_index(anywheresoftware.b4a.BA _ba) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "load_index"))
	return (String) Debug.delegate(null, "load_index", new Object[] {_ba});
anywheresoftware.b4a.samples.httputils2.httpjob _load_indexjob = null;
RDebugUtils.currentLine=983040;
 //BA.debugLineNum = 983040;BA.debugLine="Sub load_index()";
RDebugUtils.currentLine=983041;
 //BA.debugLineNum = 983041;BA.debugLine="Dim load_indexjob As HttpJob";
_load_indexjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=983042;
 //BA.debugLineNum = 983042;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
_load_indexjob._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_indexjob",(Object)(mostCurrent._index.getObject()));
RDebugUtils.currentLine=983043;
 //BA.debugLineNum = 983043;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
_load_indexjob._poststring(_api,"op=index");
RDebugUtils.currentLine=983044;
 //BA.debugLineNum = 983044;BA.debugLine="End Sub";
return "";
}
}