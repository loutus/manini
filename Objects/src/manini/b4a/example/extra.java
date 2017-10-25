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
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim api As String = \"http://itrx.babapkg.ir/api.p";
_api = "http://itrx.babapkg.ir/api.php";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}
