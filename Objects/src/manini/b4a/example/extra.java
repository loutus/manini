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
public static String _share_link = "";
public static int _index_ob_top = 0;
public static int _index_ob_top_cach = 0;
public static int[] _index_ob_olaviyat = null;
public static int _index_ob_olaviyat_load = 0;
public static int _product_id_toshow = 0;
public static String _product_title = "";
public static int _product_title_top = 0;
public static boolean _product_title_flag = false;
public static int _propertyjson = 0;
public static int _procimg_flag = 0;
public static int[] _procimg_count = null;
public static int _flag_procpnl = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public manini.b4a.example.main _main = null;
public manini.b4a.example.starter _starter = null;
public manini.b4a.example.index _index = null;
public manini.b4a.example.product _product = null;
public manini.b4a.example.property _property = null;
public manini.b4a.example.omid _omid = null;
public static String  _download_image(anywheresoftware.b4a.BA _ba,String _id,String _path,String _flag) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "download_image"))
	return (String) Debug.delegate(null, "download_image", new Object[] {_ba,_id,_path,_flag});
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
RDebugUtils.currentLine=851968;
 //BA.debugLineNum = 851968;BA.debugLine="Sub download_image(id,path,flag)";
RDebugUtils.currentLine=851969;
 //BA.debugLineNum = 851969;BA.debugLine="path= path.Replace(\".jpg\",\"-600x600.jpg\")";
_path = _path.replace(".jpg","-600x600.jpg");
RDebugUtils.currentLine=851970;
 //BA.debugLineNum = 851970;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=851971;
 //BA.debugLineNum = 851971;BA.debugLine="downloader.Initialize(\"downloadimage*\" & id & \"$\"";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimage*"+_id+"$"+_path+"#"+_flag,(Object)(mostCurrent._index.getObject()));
RDebugUtils.currentLine=851972;
 //BA.debugLineNum = 851972;BA.debugLine="downloader.Download(image_address & path)";
_downloader._download(_image_address+_path);
RDebugUtils.currentLine=851973;
 //BA.debugLineNum = 851973;BA.debugLine="End Sub";
return "";
}
public static String  _initpanel(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _pnl,float _borderwidth,int _fillcolor,int _bordercolor) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "initpanel"))
	return (String) Debug.delegate(null, "initpanel", new Object[] {_ba,_pnl,_borderwidth,_fillcolor,_bordercolor});
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas1 = null;
float _borderwidth_2 = 0f;
RDebugUtils.currentLine=1114112;
 //BA.debugLineNum = 1114112;BA.debugLine="Sub InitPanel(pnl As Panel,BorderWidth As Float, F";
RDebugUtils.currentLine=1114113;
 //BA.debugLineNum = 1114113;BA.debugLine="Dim Rec As Rect";
_rec = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
RDebugUtils.currentLine=1114114;
 //BA.debugLineNum = 1114114;BA.debugLine="Dim Canvas1 As Canvas";
_canvas1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
RDebugUtils.currentLine=1114115;
 //BA.debugLineNum = 1114115;BA.debugLine="Dim BorderWidth_2 As Float";
_borderwidth_2 = 0f;
RDebugUtils.currentLine=1114116;
 //BA.debugLineNum = 1114116;BA.debugLine="BorderWidth_2=BorderWidth/2";
_borderwidth_2 = (float) (_borderwidth/(double)2);
RDebugUtils.currentLine=1114117;
 //BA.debugLineNum = 1114117;BA.debugLine="Rec.Initialize(BorderWidth_2,BorderWidth_2,pnl.Wi";
_rec.Initialize((int) (_borderwidth_2),(int) (_borderwidth_2),(int) (_pnl.getWidth()-_borderwidth_2),(int) (_pnl.getHeight()-_borderwidth_2));
RDebugUtils.currentLine=1114118;
 //BA.debugLineNum = 1114118;BA.debugLine="Canvas1.Initialize(pnl)";
_canvas1.Initialize((android.view.View)(_pnl.getObject()));
RDebugUtils.currentLine=1114119;
 //BA.debugLineNum = 1114119;BA.debugLine="Canvas1.DrawRect(Rec,FillColor,True,FillColor)";
_canvas1.DrawRect((android.graphics.Rect)(_rec.getObject()),_fillcolor,anywheresoftware.b4a.keywords.Common.True,(float) (_fillcolor));
RDebugUtils.currentLine=1114120;
 //BA.debugLineNum = 1114120;BA.debugLine="Canvas1.DrawRect(Rec,BorderColor,False,BorderWidt";
_canvas1.DrawRect((android.graphics.Rect)(_rec.getObject()),_bordercolor,anywheresoftware.b4a.keywords.Common.False,_borderwidth);
RDebugUtils.currentLine=1114121;
 //BA.debugLineNum = 1114121;BA.debugLine="End Sub";
return "";
}
public static String  _load_index(anywheresoftware.b4a.BA _ba) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "load_index"))
	return (String) Debug.delegate(null, "load_index", new Object[] {_ba});
anywheresoftware.b4a.samples.httputils2.httpjob _load_indexjob = null;
RDebugUtils.currentLine=786432;
 //BA.debugLineNum = 786432;BA.debugLine="Sub load_index()";
RDebugUtils.currentLine=786433;
 //BA.debugLineNum = 786433;BA.debugLine="Log(\"load_index\")";
anywheresoftware.b4a.keywords.Common.Log("load_index");
RDebugUtils.currentLine=786434;
 //BA.debugLineNum = 786434;BA.debugLine="Dim load_indexjob As HttpJob";
_load_indexjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=786435;
 //BA.debugLineNum = 786435;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
_load_indexjob._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_indexjob",(Object)(mostCurrent._index.getObject()));
RDebugUtils.currentLine=786436;
 //BA.debugLineNum = 786436;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
_load_indexjob._poststring(_api,"op=index");
RDebugUtils.currentLine=786437;
 //BA.debugLineNum = 786437;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_lastproduct(anywheresoftware.b4a.BA _ba,String _indexf,String _image) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "main_download_lastproduct"))
	return (String) Debug.delegate(null, "main_download_lastproduct", new Object[] {_ba,_indexf,_image});
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
RDebugUtils.currentLine=917504;
 //BA.debugLineNum = 917504;BA.debugLine="Sub main_download_lastproduct(indexf,image)";
RDebugUtils.currentLine=917505;
 //BA.debugLineNum = 917505;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=917506;
 //BA.debugLineNum = 917506;BA.debugLine="downloader.Initialize(\"downloadimglastproc*\" & in";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimglastproc*"+_indexf+"*"+_image,(Object)(mostCurrent._index.getObject()));
RDebugUtils.currentLine=917507;
 //BA.debugLineNum = 917507;BA.debugLine="downloader.Download( image)";
_downloader._download(_image);
RDebugUtils.currentLine=917508;
 //BA.debugLineNum = 917508;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_product(anywheresoftware.b4a.BA _ba,String _indexf,String _image) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "main_download_product"))
	return (String) Debug.delegate(null, "main_download_product", new Object[] {_ba,_indexf,_image});
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
RDebugUtils.currentLine=4653056;
 //BA.debugLineNum = 4653056;BA.debugLine="Sub main_download_product(indexf,image)";
RDebugUtils.currentLine=4653058;
 //BA.debugLineNum = 4653058;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
RDebugUtils.currentLine=4653059;
 //BA.debugLineNum = 4653059;BA.debugLine="downloader.Initialize(\"downloadimgproduct*\" & ind";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimgproduct*"+_indexf+"*"+_image,(Object)(mostCurrent._index.getObject()));
RDebugUtils.currentLine=4653060;
 //BA.debugLineNum = 4653060;BA.debugLine="downloader.Download( image)";
_downloader._download(_image);
RDebugUtils.currentLine=4653061;
 //BA.debugLineNum = 4653061;BA.debugLine="End Sub";
return "";
}
public static String  _programsharepost(anywheresoftware.b4a.BA _ba,String _id) throws Exception{
RDebugUtils.currentModule="extra";
if (Debug.shouldDelegate(null, "programsharepost"))
	return (String) Debug.delegate(null, "programsharepost", new Object[] {_ba,_id});
String _text = "";
anywheresoftware.b4a.objects.IntentWrapper _share = null;
RDebugUtils.currentLine=1179648;
 //BA.debugLineNum = 1179648;BA.debugLine="Sub programsharepost(id As String)";
RDebugUtils.currentLine=1179649;
 //BA.debugLineNum = 1179649;BA.debugLine="Dim text As String";
_text = "";
RDebugUtils.currentLine=1179650;
 //BA.debugLineNum = 1179650;BA.debugLine="text = \"فروشگاه لوازم کودک و سیسمونی مانینی \" & C";
_text = "فروشگاه لوازم کودک و سیسمونی مانینی "+anywheresoftware.b4a.keywords.Common.CRLF+_share_link+_id;
RDebugUtils.currentLine=1179651;
 //BA.debugLineNum = 1179651;BA.debugLine="Dim share As Intent";
_share = new anywheresoftware.b4a.objects.IntentWrapper();
RDebugUtils.currentLine=1179652;
 //BA.debugLineNum = 1179652;BA.debugLine="share.Initialize(share.ACTION_SEND,\"\")";
_share.Initialize(_share.ACTION_SEND,"");
RDebugUtils.currentLine=1179653;
 //BA.debugLineNum = 1179653;BA.debugLine="share.SetType(\"text/plain\")";
_share.SetType("text/plain");
RDebugUtils.currentLine=1179654;
 //BA.debugLineNum = 1179654;BA.debugLine="share.PutExtra(\"android.intent.extra.TEXT\", text)";
_share.PutExtra("android.intent.extra.TEXT",(Object)(_text));
RDebugUtils.currentLine=1179655;
 //BA.debugLineNum = 1179655;BA.debugLine="share.WrapAsIntentChooser(\"اشتراک محصول\")";
_share.WrapAsIntentChooser("اشتراک محصول");
RDebugUtils.currentLine=1179656;
 //BA.debugLineNum = 1179656;BA.debugLine="StartActivity(share)";
anywheresoftware.b4a.keywords.Common.StartActivity((_ba.processBA == null ? _ba : _ba.processBA),(Object)(_share.getObject()));
RDebugUtils.currentLine=1179657;
 //BA.debugLineNum = 1179657;BA.debugLine="End Sub";
return "";
}
}