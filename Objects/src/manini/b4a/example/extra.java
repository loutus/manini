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
public static String _product_title = "";
public static int _product_title_top = 0;
public static boolean _product_title_flag = false;
public static String _propertyjson = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.index _index = null;
public manini.b4a.example.product _product = null;
public manini.b4a.example.property _property = null;
public manini.b4a.example.omid _omid = null;
public static String  _download_image(anywheresoftware.b4a.BA _ba,String _id,String _path,String _flag) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
 //BA.debugLineNum = 26;BA.debugLine="Sub download_image(id,path,flag)";
 //BA.debugLineNum = 27;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 28;BA.debugLine="downloader.Initialize(\"downloadimage*\" & id & \"$\"";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimage*"+_id+"$"+_path+"#"+_flag,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 29;BA.debugLine="downloader.Download(image_address & path)";
_downloader._download(_image_address+_path);
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _initpanel(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _pnl,float _borderwidth,int _fillcolor,int _bordercolor) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas1 = null;
float _borderwidth_2 = 0f;
 //BA.debugLineNum = 45;BA.debugLine="Sub InitPanel(pnl As Panel,BorderWidth As Float, F";
 //BA.debugLineNum = 46;BA.debugLine="Dim Rec As Rect";
_rec = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim Canvas1 As Canvas";
_canvas1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim BorderWidth_2 As Float";
_borderwidth_2 = 0f;
 //BA.debugLineNum = 49;BA.debugLine="BorderWidth_2=BorderWidth/2";
_borderwidth_2 = (float) (_borderwidth/(double)2);
 //BA.debugLineNum = 50;BA.debugLine="Rec.Initialize(BorderWidth_2,BorderWidth_2,pnl.Wi";
_rec.Initialize((int) (_borderwidth_2),(int) (_borderwidth_2),(int) (_pnl.getWidth()-_borderwidth_2),(int) (_pnl.getHeight()-_borderwidth_2));
 //BA.debugLineNum = 51;BA.debugLine="Canvas1.Initialize(pnl)";
_canvas1.Initialize((android.view.View)(_pnl.getObject()));
 //BA.debugLineNum = 52;BA.debugLine="Canvas1.DrawRect(Rec,FillColor,True,FillColor)";
_canvas1.DrawRect((android.graphics.Rect)(_rec.getObject()),_fillcolor,anywheresoftware.b4a.keywords.Common.True,(float) (_fillcolor));
 //BA.debugLineNum = 53;BA.debugLine="Canvas1.DrawRect(Rec,BorderColor,False,BorderWidt";
_canvas1.DrawRect((android.graphics.Rect)(_rec.getObject()),_bordercolor,anywheresoftware.b4a.keywords.Common.False,_borderwidth);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _load_category_main(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _load_category = null;
 //BA.debugLineNum = 32;BA.debugLine="Sub load_category_main()";
 //BA.debugLineNum = 33;BA.debugLine="Dim load_category As HttpJob";
_load_category = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 34;BA.debugLine="load_category.Initialize(\"load_category_main\",pro";
_load_category._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_category_main",(Object)(mostCurrent._product.getObject()));
 //BA.debugLineNum = 35;BA.debugLine="load_category.PostString(api,\"op=category\")";
_load_category._poststring(_api,"op=category");
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _load_index(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _load_indexjob = null;
 //BA.debugLineNum = 19;BA.debugLine="Sub load_index()";
 //BA.debugLineNum = 20;BA.debugLine="Log(\"load_index\")";
anywheresoftware.b4a.keywords.Common.Log("load_index");
 //BA.debugLineNum = 21;BA.debugLine="Dim load_indexjob As HttpJob";
_load_indexjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 22;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
_load_indexjob._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_indexjob",(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 23;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
_load_indexjob._poststring(_api,"op=index");
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_image(anywheresoftware.b4a.BA _ba,String _name,String _image) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _idownload = null;
 //BA.debugLineNum = 39;BA.debugLine="Sub main_download_image(name As String,image As St";
 //BA.debugLineNum = 40;BA.debugLine="Dim idownload As HttpJob";
_idownload = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 41;BA.debugLine="idownload.Initialize(\"imageview*\" & name & \"*\" &";
_idownload._initialize((_ba.processBA == null ? _ba : _ba.processBA),"imageview*"+_name+"*"+_image,(Object)(mostCurrent._product.getObject()));
 //BA.debugLineNum = 42;BA.debugLine="idownload.Download(image)";
_idownload._download(_image);
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 13;BA.debugLine="Dim product_title As String";
_product_title = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim product_title_top As Int";
_product_title_top = 0;
 //BA.debugLineNum = 15;BA.debugLine="Dim product_title_flag As Boolean =False";
_product_title_flag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 16;BA.debugLine="dim propertyjson as String";
_propertyjson = "";
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
}
