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
public static String _image_address_nc = "";
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
public static int _pagecountflag = 0;
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
 //BA.debugLineNum = 27;BA.debugLine="path= path.Replace(\".jpg\",\"-600x600.jpg\")";
_path = _path.replace(".jpg","-600x600.jpg");
 //BA.debugLineNum = 28;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 29;BA.debugLine="downloader.Initialize(\"downloadimage*\" & id & \"$\"";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimage*"+_id+"$"+_path+"#"+_flag,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 30;BA.debugLine="downloader.Download(image_address & path)";
_downloader._download(_image_address+_path);
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _initpanel(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _pnl,float _borderwidth,int _fillcolor,int _bordercolor) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas1 = null;
float _borderwidth_2 = 0f;
 //BA.debugLineNum = 52;BA.debugLine="Sub InitPanel(pnl As Panel,BorderWidth As Float, F";
 //BA.debugLineNum = 53;BA.debugLine="Dim Rec As Rect";
_rec = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim Canvas1 As Canvas";
_canvas1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim BorderWidth_2 As Float";
_borderwidth_2 = 0f;
 //BA.debugLineNum = 56;BA.debugLine="BorderWidth_2=BorderWidth/2";
_borderwidth_2 = (float) (_borderwidth/(double)2);
 //BA.debugLineNum = 57;BA.debugLine="Rec.Initialize(BorderWidth_2,BorderWidth_2,pnl.Wi";
_rec.Initialize((int) (_borderwidth_2),(int) (_borderwidth_2),(int) (_pnl.getWidth()-_borderwidth_2),(int) (_pnl.getHeight()-_borderwidth_2));
 //BA.debugLineNum = 58;BA.debugLine="Canvas1.Initialize(pnl)";
_canvas1.Initialize((android.view.View)(_pnl.getObject()));
 //BA.debugLineNum = 59;BA.debugLine="Canvas1.DrawRect(Rec,FillColor,True,FillColor)";
_canvas1.DrawRect((android.graphics.Rect)(_rec.getObject()),_fillcolor,anywheresoftware.b4a.keywords.Common.True,(float) (_fillcolor));
 //BA.debugLineNum = 60;BA.debugLine="Canvas1.DrawRect(Rec,BorderColor,False,BorderWidt";
_canvas1.DrawRect((android.graphics.Rect)(_rec.getObject()),_bordercolor,anywheresoftware.b4a.keywords.Common.False,_borderwidth);
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _load_index(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _load_indexjob = null;
 //BA.debugLineNum = 20;BA.debugLine="Sub load_index()";
 //BA.debugLineNum = 21;BA.debugLine="Log(\"load_index\")";
anywheresoftware.b4a.keywords.Common.Log("load_index");
 //BA.debugLineNum = 22;BA.debugLine="Dim load_indexjob As HttpJob";
_load_indexjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 23;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
_load_indexjob._initialize((_ba.processBA == null ? _ba : _ba.processBA),"load_indexjob",(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 24;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
_load_indexjob._poststring(_api,"op=index");
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_category_list(anywheresoftware.b4a.BA _ba,String _indexf,String _image) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
 //BA.debugLineNum = 37;BA.debugLine="Sub main_download_category_list(indexf,image)";
 //BA.debugLineNum = 38;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 39;BA.debugLine="downloader.Initialize(\"downloadimgcatlist*\" & ind";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimgcatlist*"+_indexf+"*"+_image,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 40;BA.debugLine="downloader.Download( image)";
_downloader._download(_image);
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_categorypic(anywheresoftware.b4a.BA _ba,String _indexf,String _image) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
 //BA.debugLineNum = 42;BA.debugLine="Sub main_download_categorypic(indexf,image)";
 //BA.debugLineNum = 43;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 44;BA.debugLine="downloader.Initialize(\"downloadimgcategory*\" & in";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimgcategory*"+_indexf+"*"+_image,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 45;BA.debugLine="downloader.Download( image)";
_downloader._download(_image);
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_lastproduct(anywheresoftware.b4a.BA _ba,String _indexf,String _image) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
 //BA.debugLineNum = 32;BA.debugLine="Sub main_download_lastproduct(indexf,image)";
 //BA.debugLineNum = 33;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 34;BA.debugLine="downloader.Initialize(\"downloadimglastproc*\" & in";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimglastproc*"+_indexf+"*"+_image,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 35;BA.debugLine="downloader.Download( image)";
_downloader._download(_image);
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _main_download_product(anywheresoftware.b4a.BA _ba,String _indexf,String _image) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _downloader = null;
 //BA.debugLineNum = 47;BA.debugLine="Sub main_download_product(indexf,image)";
 //BA.debugLineNum = 48;BA.debugLine="Dim downloader As HttpJob";
_downloader = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 49;BA.debugLine="downloader.Initialize(\"downloadimgproduct*\" & ind";
_downloader._initialize((_ba.processBA == null ? _ba : _ba.processBA),"downloadimgproduct*"+_indexf+"*"+_image,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 50;BA.debugLine="downloader.Download(  image)";
_downloader._download(_image);
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Dim api As String = \"http://itrx.babapkg.ir/api.p";
_api = "http://itrx.babapkg.ir/api.php";
 //BA.debugLineNum = 3;BA.debugLine="Dim image_address As String = \"http://itrx.babapk";
_image_address = "http://itrx.babapkg.ir/image/cache/";
 //BA.debugLineNum = 4;BA.debugLine="Dim image_address_nc As String = \"http://itrx.bab";
_image_address_nc = "http://itrx.babapkg.ir/image/";
 //BA.debugLineNum = 5;BA.debugLine="Dim share_link As String = \"http://itrx.babapkg.i";
_share_link = "http://itrx.babapkg.ir/index.php?route=product/product&product_id=";
 //BA.debugLineNum = 6;BA.debugLine="Dim index_ob_top As Int = 0";
_index_ob_top = (int) (0);
 //BA.debugLineNum = 7;BA.debugLine="Dim index_ob_top_cach As Int =10dip";
_index_ob_top_cach = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 8;BA.debugLine="Dim index_ob_olaviyat(1000) As Int";
_index_ob_olaviyat = new int[(int) (1000)];
;
 //BA.debugLineNum = 9;BA.debugLine="Dim index_ob_olaviyat_load As Int=1";
_index_ob_olaviyat_load = (int) (1);
 //BA.debugLineNum = 10;BA.debugLine="Dim product_id_toshow As Int";
_product_id_toshow = 0;
 //BA.debugLineNum = 11;BA.debugLine="Dim product_title As String";
_product_title = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim product_title_top As Int";
_product_title_top = 0;
 //BA.debugLineNum = 13;BA.debugLine="Dim product_title_flag As Boolean =False";
_product_title_flag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 14;BA.debugLine="Dim propertyjson As Int = 0";
_propertyjson = (int) (0);
 //BA.debugLineNum = 15;BA.debugLine="Dim procimg_flag As Int = 0";
_procimg_flag = (int) (0);
 //BA.debugLineNum = 16;BA.debugLine="Dim procimg_count(50) As Int";
_procimg_count = new int[(int) (50)];
;
 //BA.debugLineNum = 17;BA.debugLine="Dim flag_procpnl As Int = 0";
_flag_procpnl = (int) (0);
 //BA.debugLineNum = 18;BA.debugLine="Dim pagecountflag As Int=0";
_pagecountflag = (int) (0);
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _programsharepost(anywheresoftware.b4a.BA _ba,String _id) throws Exception{
String _text = "";
anywheresoftware.b4a.objects.IntentWrapper _share = null;
 //BA.debugLineNum = 62;BA.debugLine="Sub programsharepost(id As String)";
 //BA.debugLineNum = 63;BA.debugLine="Dim text As String";
_text = "";
 //BA.debugLineNum = 64;BA.debugLine="text = \"فروشگاه لوازم کودک و سیسمونی مانینی \" & C";
_text = "فروشگاه لوازم کودک و سیسمونی مانینی "+anywheresoftware.b4a.keywords.Common.CRLF+_share_link+_id;
 //BA.debugLineNum = 65;BA.debugLine="Dim share As Intent";
_share = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 66;BA.debugLine="share.Initialize(share.ACTION_SEND,\"\")";
_share.Initialize(_share.ACTION_SEND,"");
 //BA.debugLineNum = 67;BA.debugLine="share.SetType(\"text/plain\")";
_share.SetType("text/plain");
 //BA.debugLineNum = 68;BA.debugLine="share.PutExtra(\"android.intent.extra.TEXT\", text)";
_share.PutExtra("android.intent.extra.TEXT",(Object)(_text));
 //BA.debugLineNum = 69;BA.debugLine="share.WrapAsIntentChooser(\"اشتراک محصول\")";
_share.WrapAsIntentChooser("اشتراک محصول");
 //BA.debugLineNum = 70;BA.debugLine="StartActivity(share)";
anywheresoftware.b4a.keywords.Common.StartActivity((_ba.processBA == null ? _ba : _ba.processBA),(Object)(_share.getObject()));
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
}
