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
public static int _index_ob_olaviyat_load = 0;
public static int _product_id_toshow = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.index _index = null;
public manini.b4a.example.product _product = null;
public static String  _download_image(anywheresoftware.b4a.BA _ba,String _id,String _path,String _flag) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
 //BA.debugLineNum = 22;BA.debugLine="Sub download_image(id,path,flag)";
 //BA.debugLineNum = 23;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 24;BA.debugLine="downloader.Initialize(\"downloadimage*\" & id & \"$\"";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimage*"+_id+"$"+_path+"#"+_flag,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 25;BA.debugLine="downloader.Download(image_address & path)";
_downloader._download(_image_address+_path);
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _load_index(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _load_indexjob = null;
 //BA.debugLineNum = 15;BA.debugLine="Sub load_index()";
 //BA.debugLineNum = 16;BA.debugLine="Log(\"load_index\")";
anywheresoftware.b4a.keywords.Common.Log("load_index");
 //BA.debugLineNum = 17;BA.debugLine="Dim load_indexjob As HttpJob";
_load_indexjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 18;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
_load_indexjob._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_indexjob",(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 19;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
_load_indexjob._poststring(_api,"op=index");
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _load_lastproduct_main(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _load_category = null;
 //BA.debugLineNum = 30;BA.debugLine="Sub load_lastproduct_main()";
 //BA.debugLineNum = 31;BA.debugLine="Dim load_category As HttpJob";
_load_category = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 32;BA.debugLine="load_category.Initialize(\"load_lastproduct_main\",";
_load_category._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_lastproduct_main",(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 33;BA.debugLine="load_category.PostString(api,\"op=lastproduct\")";
_load_category._poststring(_api,"op=lastproduct");
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim api As String = \"http://itrx.babapkg.ir/api.p";
_api = "http://itrx.babapkg.ir/api.php";
 //BA.debugLineNum = 7;BA.debugLine="Dim image_address As String = \"http://itrx.babapk";
_image_address = "http://itrx.babapkg.ir/image/";
 //BA.debugLineNum = 8;BA.debugLine="Dim index_ob_top As Int = 0";
_index_ob_top = (int) (0);
 //BA.debugLineNum = 9;BA.debugLine="Dim index_ob_top_cach As Int =10dip";
_index_ob_top_cach = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 10;BA.debugLine="Dim index_ob_olaviyat(1000) As Int";
_index_ob_olaviyat = new int[(int) (1000)];
;
 //BA.debugLineNum = 11;BA.debugLine="Dim index_ob_olaviyat_load As Int=1";
_index_ob_olaviyat_load = (int) (1);
 //BA.debugLineNum = 12;BA.debugLine="Dim product_id_toshow As Int";
_product_id_toshow = 0;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
}
