package manini.b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class extra_subs_0 {


public static RemoteObject  _download_image(RemoteObject _ba,RemoteObject _id,RemoteObject _path,RemoteObject _flag) throws Exception{
try {
		Debug.PushSubsStack("download_image (extra) ","extra",2,_ba,extra.mostCurrent,30);
if (RapidSub.canDelegate("download_image")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","download_image", _ba, _id, _path, _flag);
RemoteObject _downloader = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
;
Debug.locals.put("id", _id);
Debug.locals.put("path", _path);
Debug.locals.put("flag", _flag);
 BA.debugLineNum = 30;BA.debugLine="Sub download_image(id,path,flag)";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 31;BA.debugLine="path= path.Replace(\".jpg\",\"-600x600.jpg\")";
Debug.ShouldStop(1073741824);
_path = _path.runMethod(true,"replace",(Object)(BA.ObjectToString(".jpg")),(Object)(RemoteObject.createImmutable("-600x600.jpg")));Debug.locals.put("path", _path);
 BA.debugLineNum = 32;BA.debugLine="Dim downloader As HttpJob";
Debug.ShouldStop(-2147483648);
_downloader = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("downloader", _downloader);
 BA.debugLineNum = 33;BA.debugLine="downloader.Initialize(\"downloadimage*\" & id & \"$\"";
Debug.ShouldStop(1);
_downloader.runVoidMethod ("_initialize",BA.rdebugUtils.runMethod(false, "processBAFromBA", _ba),(Object)(RemoteObject.concat(RemoteObject.createImmutable("downloadimage*"),_id,RemoteObject.createImmutable("$"),_path,RemoteObject.createImmutable("#"),_flag)),(Object)((extra.mostCurrent._index.getObject())));
 BA.debugLineNum = 34;BA.debugLine="downloader.Download(image_address & path)";
Debug.ShouldStop(2);
_downloader.runVoidMethod ("_download",(Object)(RemoteObject.concat(extra._image_address,_path)));
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
public static RemoteObject  _initpanel(RemoteObject _ba,RemoteObject _pnl,RemoteObject _borderwidth,RemoteObject _fillcolor,RemoteObject _bordercolor) throws Exception{
try {
		Debug.PushSubsStack("InitPanel (extra) ","extra",2,_ba,extra.mostCurrent,58);
if (RapidSub.canDelegate("initpanel")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","initpanel", _ba, _pnl, _borderwidth, _fillcolor, _bordercolor);
RemoteObject _rec = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper");
RemoteObject _canvas1 = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.CanvasWrapper");
RemoteObject _borderwidth_2 = RemoteObject.createImmutable(0f);
;
Debug.locals.put("pnl", _pnl);
Debug.locals.put("BorderWidth", _borderwidth);
Debug.locals.put("FillColor", _fillcolor);
Debug.locals.put("BorderColor", _bordercolor);
 BA.debugLineNum = 58;BA.debugLine="Sub InitPanel(pnl As Panel,BorderWidth As Float, F";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 59;BA.debugLine="Dim Rec As Rect";
Debug.ShouldStop(67108864);
_rec = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper");Debug.locals.put("Rec", _rec);
 BA.debugLineNum = 60;BA.debugLine="Dim Canvas1 As Canvas";
Debug.ShouldStop(134217728);
_canvas1 = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.CanvasWrapper");Debug.locals.put("Canvas1", _canvas1);
 BA.debugLineNum = 61;BA.debugLine="Dim BorderWidth_2 As Float";
Debug.ShouldStop(268435456);
_borderwidth_2 = RemoteObject.createImmutable(0f);Debug.locals.put("BorderWidth_2", _borderwidth_2);
 BA.debugLineNum = 62;BA.debugLine="BorderWidth_2=BorderWidth/2";
Debug.ShouldStop(536870912);
_borderwidth_2 = BA.numberCast(float.class, RemoteObject.solve(new RemoteObject[] {_borderwidth,RemoteObject.createImmutable(2)}, "/",0, 0));Debug.locals.put("BorderWidth_2", _borderwidth_2);
 BA.debugLineNum = 63;BA.debugLine="Rec.Initialize(BorderWidth_2,BorderWidth_2,pnl.Wi";
Debug.ShouldStop(1073741824);
_rec.runVoidMethod ("Initialize",(Object)(BA.numberCast(int.class, _borderwidth_2)),(Object)(BA.numberCast(int.class, _borderwidth_2)),(Object)(BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {_pnl.runMethod(true,"getWidth"),_borderwidth_2}, "-",1, 0))),(Object)(BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {_pnl.runMethod(true,"getHeight"),_borderwidth_2}, "-",1, 0))));
 BA.debugLineNum = 64;BA.debugLine="Canvas1.Initialize(pnl)";
Debug.ShouldStop(-2147483648);
_canvas1.runVoidMethod ("Initialize",(Object)((_pnl.getObject())));
 BA.debugLineNum = 65;BA.debugLine="Canvas1.DrawRect(Rec,FillColor,True,FillColor)";
Debug.ShouldStop(1);
_canvas1.runVoidMethod ("DrawRect",(Object)((_rec.getObject())),(Object)(_fillcolor),(Object)(extra.mostCurrent.__c.getField(true,"True")),(Object)(BA.numberCast(float.class, _fillcolor)));
 BA.debugLineNum = 66;BA.debugLine="Canvas1.DrawRect(Rec,BorderColor,False,BorderWidt";
Debug.ShouldStop(2);
_canvas1.runVoidMethod ("DrawRect",(Object)((_rec.getObject())),(Object)(_bordercolor),(Object)(extra.mostCurrent.__c.getField(true,"False")),(Object)(_borderwidth));
 BA.debugLineNum = 67;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _load_index(RemoteObject _ba) throws Exception{
try {
		Debug.PushSubsStack("load_index (extra) ","extra",2,_ba,extra.mostCurrent,23);
if (RapidSub.canDelegate("load_index")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","load_index", _ba);
RemoteObject _load_indexjob = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
;
 BA.debugLineNum = 23;BA.debugLine="Sub load_index()";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 24;BA.debugLine="Log(\"load_index\")";
Debug.ShouldStop(8388608);
extra.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("load_index")));
 BA.debugLineNum = 25;BA.debugLine="Dim load_indexjob As HttpJob";
Debug.ShouldStop(16777216);
_load_indexjob = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("load_indexjob", _load_indexjob);
 BA.debugLineNum = 26;BA.debugLine="load_indexjob.Initialize(\"load_indexjob\",index)";
Debug.ShouldStop(33554432);
_load_indexjob.runVoidMethod ("_initialize",BA.rdebugUtils.runMethod(false, "processBAFromBA", _ba),(Object)(BA.ObjectToString("load_indexjob")),(Object)((extra.mostCurrent._index.getObject())));
 BA.debugLineNum = 27;BA.debugLine="load_indexjob.PostString(api,\"op=index\")";
Debug.ShouldStop(67108864);
_load_indexjob.runVoidMethod ("_poststring",(Object)(extra._api),(Object)(RemoteObject.createImmutable("op=index")));
 BA.debugLineNum = 28;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _main_download_lastproduct(RemoteObject _ba,RemoteObject _indexf,RemoteObject _image) throws Exception{
try {
		Debug.PushSubsStack("main_download_lastproduct (extra) ","extra",2,_ba,extra.mostCurrent,36);
if (RapidSub.canDelegate("main_download_lastproduct")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","main_download_lastproduct", _ba, _indexf, _image);
RemoteObject _downloader = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
;
Debug.locals.put("indexf", _indexf);
Debug.locals.put("image", _image);
 BA.debugLineNum = 36;BA.debugLine="Sub main_download_lastproduct(indexf,image)";
Debug.ShouldStop(8);
 BA.debugLineNum = 37;BA.debugLine="Dim downloader As HttpJob";
Debug.ShouldStop(16);
_downloader = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("downloader", _downloader);
 BA.debugLineNum = 38;BA.debugLine="downloader.Initialize(\"downloadimglastproc*\" & in";
Debug.ShouldStop(32);
_downloader.runVoidMethod ("_initialize",BA.rdebugUtils.runMethod(false, "processBAFromBA", _ba),(Object)(RemoteObject.concat(RemoteObject.createImmutable("downloadimglastproc*"),_indexf,RemoteObject.createImmutable("*"),_image)),(Object)((extra.mostCurrent._index.getObject())));
 BA.debugLineNum = 39;BA.debugLine="downloader.Download( image)";
Debug.ShouldStop(64);
_downloader.runVoidMethod ("_download",(Object)(_image));
 BA.debugLineNum = 40;BA.debugLine="End Sub";
Debug.ShouldStop(128);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _main_download_product(RemoteObject _ba,RemoteObject _indexf,RemoteObject _image) throws Exception{
try {
		Debug.PushSubsStack("main_download_product (extra) ","extra",2,_ba,extra.mostCurrent,42);
if (RapidSub.canDelegate("main_download_product")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","main_download_product", _ba, _indexf, _image);
RemoteObject _downloader = RemoteObject.declareNull("anywheresoftware.b4a.samples.httputils2.httpjob");
;
Debug.locals.put("indexf", _indexf);
Debug.locals.put("image", _image);
 BA.debugLineNum = 42;BA.debugLine="Sub main_download_product(indexf,image)";
Debug.ShouldStop(512);
 BA.debugLineNum = 44;BA.debugLine="Dim downloader As HttpJob";
Debug.ShouldStop(2048);
_downloader = RemoteObject.createNew ("anywheresoftware.b4a.samples.httputils2.httpjob");Debug.locals.put("downloader", _downloader);
 BA.debugLineNum = 45;BA.debugLine="downloader.Initialize(\"downloadimgproduct*\" & ind";
Debug.ShouldStop(4096);
_downloader.runVoidMethod ("_initialize",BA.rdebugUtils.runMethod(false, "processBAFromBA", _ba),(Object)(RemoteObject.concat(RemoteObject.createImmutable("downloadimgproduct*"),_indexf,RemoteObject.createImmutable("*"),_image)),(Object)((extra.mostCurrent._index.getObject())));
 BA.debugLineNum = 46;BA.debugLine="downloader.Download( image)";
Debug.ShouldStop(8192);
_downloader.runVoidMethod ("_download",(Object)(_image));
 BA.debugLineNum = 47;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
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
extra._image_address = BA.ObjectToString("http://itrx.babapkg.ir/image/cache/");
 //BA.debugLineNum = 8;BA.debugLine="Dim share_link As String = \"http://itrx.babapkg.i";
extra._share_link = BA.ObjectToString("http://itrx.babapkg.ir/index.php?route=product/product&product_id=");
 //BA.debugLineNum = 9;BA.debugLine="Dim index_ob_top As Int = 0";
extra._index_ob_top = BA.numberCast(int.class, 0);
 //BA.debugLineNum = 10;BA.debugLine="Dim index_ob_top_cach As Int =10dip";
extra._index_ob_top_cach = extra.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)));
 //BA.debugLineNum = 11;BA.debugLine="Dim index_ob_olaviyat(1000) As Int";
extra._index_ob_olaviyat = RemoteObject.createNewArray ("int", new int[] {1000}, new Object[]{});
 //BA.debugLineNum = 12;BA.debugLine="Dim index_ob_olaviyat_load As Int=1";
extra._index_ob_olaviyat_load = BA.numberCast(int.class, 1);
 //BA.debugLineNum = 13;BA.debugLine="Dim product_id_toshow As Int";
extra._product_id_toshow = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 14;BA.debugLine="Dim product_title As String";
extra._product_title = RemoteObject.createImmutable("");
 //BA.debugLineNum = 15;BA.debugLine="Dim product_title_top As Int";
extra._product_title_top = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 16;BA.debugLine="Dim product_title_flag As Boolean =False";
extra._product_title_flag = extra.mostCurrent.__c.getField(true,"False");
 //BA.debugLineNum = 17;BA.debugLine="Dim propertyjson As Int = 0";
extra._propertyjson = BA.numberCast(int.class, 0);
 //BA.debugLineNum = 18;BA.debugLine="Dim procimg_flag As Int = 0";
extra._procimg_flag = BA.numberCast(int.class, 0);
 //BA.debugLineNum = 19;BA.debugLine="Dim procimg_count(50) As Int";
extra._procimg_count = RemoteObject.createNewArray ("int", new int[] {50}, new Object[]{});
 //BA.debugLineNum = 20;BA.debugLine="Dim flag_procpnl As Int = 0";
extra._flag_procpnl = BA.numberCast(int.class, 0);
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _programsharepost(RemoteObject _ba,RemoteObject _id) throws Exception{
try {
		Debug.PushSubsStack("programsharepost (extra) ","extra",2,_ba,extra.mostCurrent,69);
if (RapidSub.canDelegate("programsharepost")) return manini.b4a.example.extra.remoteMe.runUserSub(false, "extra","programsharepost", _ba, _id);
RemoteObject _text = RemoteObject.createImmutable("");
RemoteObject _share = RemoteObject.declareNull("anywheresoftware.b4a.objects.IntentWrapper");
;
Debug.locals.put("id", _id);
 BA.debugLineNum = 69;BA.debugLine="Sub programsharepost(id As String)";
Debug.ShouldStop(16);
 BA.debugLineNum = 70;BA.debugLine="Dim text As String";
Debug.ShouldStop(32);
_text = RemoteObject.createImmutable("");Debug.locals.put("text", _text);
 BA.debugLineNum = 71;BA.debugLine="text = \"فروشگاه لوازم کودک و سیسمونی مانینی \" & C";
Debug.ShouldStop(64);
_text = RemoteObject.concat(RemoteObject.createImmutable("فروشگاه لوازم کودک و سیسمونی مانینی "),extra.mostCurrent.__c.getField(true,"CRLF"),extra._share_link,_id);Debug.locals.put("text", _text);
 BA.debugLineNum = 72;BA.debugLine="Dim share As Intent";
Debug.ShouldStop(128);
_share = RemoteObject.createNew ("anywheresoftware.b4a.objects.IntentWrapper");Debug.locals.put("share", _share);
 BA.debugLineNum = 73;BA.debugLine="share.Initialize(share.ACTION_SEND,\"\")";
Debug.ShouldStop(256);
_share.runVoidMethod ("Initialize",(Object)(_share.getField(true,"ACTION_SEND")),(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 74;BA.debugLine="share.SetType(\"text/plain\")";
Debug.ShouldStop(512);
_share.runVoidMethod ("SetType",(Object)(RemoteObject.createImmutable("text/plain")));
 BA.debugLineNum = 75;BA.debugLine="share.PutExtra(\"android.intent.extra.TEXT\", text)";
Debug.ShouldStop(1024);
_share.runVoidMethod ("PutExtra",(Object)(BA.ObjectToString("android.intent.extra.TEXT")),(Object)((_text)));
 BA.debugLineNum = 76;BA.debugLine="share.WrapAsIntentChooser(\"اشتراک محصول\")";
Debug.ShouldStop(2048);
_share.runVoidMethod ("WrapAsIntentChooser",(Object)(RemoteObject.createImmutable("اشتراک محصول")));
 BA.debugLineNum = 77;BA.debugLine="StartActivity(share)";
Debug.ShouldStop(4096);
extra.mostCurrent.__c.runVoidMethod ("StartActivity",BA.rdebugUtils.runMethod(false, "processBAFromBA", _ba),(Object)((_share.getObject())));
 BA.debugLineNum = 78;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
}