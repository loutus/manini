package manini.b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class index_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (index) ","index",3,index.mostCurrent.activityBA,index.mostCurrent,15);
if (RapidSub.canDelegate("activity_create")) return manini.b4a.example.index.remoteMe.runUserSub(false, "index","activity_create", _firsttime);
RemoteObject _r = RemoteObject.declareNull("anywheresoftware.b4a.agraham.reflection.Reflection");
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 15;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 17;BA.debugLine="Activity.LoadLayout(\"index\")";
Debug.ShouldStop(65536);
index.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("index")),index.mostCurrent.activityBA);
 BA.debugLineNum = 18;BA.debugLine="extra.index_ob_olaviyat(0) = 1";
Debug.ShouldStop(131072);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 1),BA.numberCast(int.class, 0));
 BA.debugLineNum = 22;BA.debugLine="extra.load_index";
Debug.ShouldStop(2097152);
index.mostCurrent._extra.runVoidMethod ("_load_index",index.mostCurrent.activityBA);
 BA.debugLineNum = 23;BA.debugLine="Dim r As Reflector";
Debug.ShouldStop(4194304);
_r = RemoteObject.createNew ("anywheresoftware.b4a.agraham.reflection.Reflection");Debug.locals.put("r", _r);
 BA.debugLineNum = 24;BA.debugLine="r.Target = index_ScrollView";
Debug.ShouldStop(8388608);
_r.setField ("Target",(index.mostCurrent._index_scrollview.getObject()));
 BA.debugLineNum = 25;BA.debugLine="r.RunMethod2(\"setVerticalScrollBarEnabled\", False";
Debug.ShouldStop(16777216);
_r.runVoidMethod ("RunMethod2",(Object)(BA.ObjectToString("setVerticalScrollBarEnabled")),(Object)(BA.ObjectToString(index.mostCurrent.__c.getField(true,"False"))),(Object)(RemoteObject.createImmutable("java.lang.boolean")));
 BA.debugLineNum = 26;BA.debugLine="r.RunMethod2(\"setOverScrollMode\", 2, \"java.lang.i";
Debug.ShouldStop(33554432);
_r.runVoidMethod ("RunMethod2",(Object)(BA.ObjectToString("setOverScrollMode")),(Object)(BA.NumberToString(2)),(Object)(RemoteObject.createImmutable("java.lang.int")));
 BA.debugLineNum = 27;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
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
		Debug.PushSubsStack("Activity_Pause (index) ","index",3,index.mostCurrent.activityBA,index.mostCurrent,30);
if (RapidSub.canDelegate("activity_pause")) return manini.b4a.example.index.remoteMe.runUserSub(false, "index","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 30;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 31;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
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
		Debug.PushSubsStack("Activity_Resume (index) ","index",3,index.mostCurrent.activityBA,index.mostCurrent,28);
if (RapidSub.canDelegate("activity_resume")) return manini.b4a.example.index.remoteMe.runUserSub(false, "index","activity_resume");
 BA.debugLineNum = 28;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 29;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private index_ScrollView As ScrollView";
index.mostCurrent._index_scrollview = RemoteObject.createNew ("anywheresoftware.b4a.objects.ScrollViewWrapper");
 //BA.debugLineNum = 13;BA.debugLine="Dim index_retrive_list As List";
index.mostCurrent._index_retrive_list = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _index_draw(RemoteObject _size,RemoteObject _flag) throws Exception{
try {
		Debug.PushSubsStack("index_draw (index) ","index",3,index.mostCurrent.activityBA,index.mostCurrent,87);
if (RapidSub.canDelegate("index_draw")) return manini.b4a.example.index.remoteMe.runUserSub(false, "index","index_draw", _size, _flag);
RemoteObject _panel = RemoteObject.declareNull("anywheresoftware.b4a.objects.PanelWrapper");
RemoteObject _space = RemoteObject.createImmutable(0);
RemoteObject _padding_space = RemoteObject.createImmutable(0);
RemoteObject _left_draw = RemoteObject.createImmutable(0);
RemoteObject _width_draw = RemoteObject.createImmutable(0);
RemoteObject _shadow_space = RemoteObject.createImmutable(0);
RemoteObject _cd = RemoteObject.declareNull("anywheresoftware.b4a.objects.drawable.ColorDrawable");
Debug.locals.put("size", _size);
Debug.locals.put("flag", _flag);
 BA.debugLineNum = 87;BA.debugLine="Sub index_draw(size As String,flag)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 88;BA.debugLine="Dim panel As Panel";
Debug.ShouldStop(8388608);
_panel = RemoteObject.createNew ("anywheresoftware.b4a.objects.PanelWrapper");Debug.locals.put("panel", _panel);
 BA.debugLineNum = 89;BA.debugLine="panel.Initialize(\"panel\")";
Debug.ShouldStop(16777216);
_panel.runVoidMethod ("Initialize",index.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("panel")));
 BA.debugLineNum = 90;BA.debugLine="Dim space As Int = 2dip";
Debug.ShouldStop(33554432);
_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 2)));Debug.locals.put("space", _space);Debug.locals.put("space", _space);
 BA.debugLineNum = 91;BA.debugLine="Dim padding_space As Int = 2dip";
Debug.ShouldStop(67108864);
_padding_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 2)));Debug.locals.put("padding_space", _padding_space);Debug.locals.put("padding_space", _padding_space);
 BA.debugLineNum = 92;BA.debugLine="If size=\"larg\" Then";
Debug.ShouldStop(134217728);
if (RemoteObject.solveBoolean("=",_size,BA.ObjectToString("larg"))) { 
 BA.debugLineNum = 93;BA.debugLine="Dim left_draw As Int = padding_space";
Debug.ShouldStop(268435456);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 94;BA.debugLine="Dim width_draw As Int = 100%x - left_draw";
Debug.ShouldStop(536870912);
_width_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),index.mostCurrent.activityBA),_left_draw}, "-",1, 1);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 95;BA.debugLine="Dim shadow_space As Int = 5dip";
Debug.ShouldStop(1073741824);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 96;BA.debugLine="extra.index_ob_olaviyat(flag) = 1";
Debug.ShouldStop(-2147483648);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 1),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 97;BA.debugLine="extra.index_ob_top_cach =  width_draw";
Debug.ShouldStop(1);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 98;BA.debugLine="panel.Color = Colors.red";
Debug.ShouldStop(2);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Red"));
 };
 BA.debugLineNum = 100;BA.debugLine="If size=\"medium\" Then";
Debug.ShouldStop(8);
if (RemoteObject.solveBoolean("=",_size,BA.ObjectToString("medium"))) { 
 BA.debugLineNum = 101;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
Debug.ShouldStop(16);
switch (BA.switchObjectToInt(index.mostCurrent._extra._index_ob_olaviyat.getArrayElement(true,BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {BA.numberCast(double.class, _flag),RemoteObject.createImmutable(1)}, "-",1, 0))),BA.numberCast(int.class, 4),BA.numberCast(int.class, 111),BA.numberCast(int.class, 11),BA.numberCast(int.class, 1))) {
case 0: {
 BA.debugLineNum = 103;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
Debug.ShouldStop(64);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 104;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
Debug.ShouldStop(128);
_width_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 105;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(256);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 106;BA.debugLine="extra.index_ob_olaviyat(flag)=224";
Debug.ShouldStop(512);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 224),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 107;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(1024);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 108;BA.debugLine="panel.Color = Colors.Green";
Debug.ShouldStop(2048);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Green"));
 break; }
case 1: {
 BA.debugLineNum = 110;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
Debug.ShouldStop(8192);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66.4)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 111;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(16384);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 112;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(32768);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 113;BA.debugLine="extra.index_ob_top_cach =  width_draw";
Debug.ShouldStop(65536);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 114;BA.debugLine="panel.Color = Colors.Blue";
Debug.ShouldStop(131072);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Blue"));
 break; }
case 2: {
 BA.debugLineNum = 116;BA.debugLine="Dim left_draw As Int = 33.2%x + padding_space";
Debug.ShouldStop(524288);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 117;BA.debugLine="Dim width_draw As Int = 66%x+ padding_space";
Debug.ShouldStop(1048576);
_width_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 118;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(2097152);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 119;BA.debugLine="extra.index_ob_olaviyat(flag)=222";
Debug.ShouldStop(4194304);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 222),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 120;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(8388608);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 121;BA.debugLine="panel.Color = Colors.Green";
Debug.ShouldStop(16777216);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Green"));
 break; }
case 3: {
 BA.debugLineNum = 124;BA.debugLine="Dim left_draw As Int = padding_space";
Debug.ShouldStop(134217728);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 125;BA.debugLine="Dim width_draw As Int = 66.4%x";
Debug.ShouldStop(268435456);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66.4)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 126;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(536870912);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 127;BA.debugLine="extra.index_ob_olaviyat(flag)=22";
Debug.ShouldStop(1073741824);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 22),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 128;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(-2147483648);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 129;BA.debugLine="panel.Color = Colors.red";
Debug.ShouldStop(1);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Red"));
 break; }
}
;
 };
 BA.debugLineNum = 132;BA.debugLine="If size=\"small\" Then";
Debug.ShouldStop(8);
if (RemoteObject.solveBoolean("=",_size,BA.ObjectToString("small"))) { 
 BA.debugLineNum = 134;BA.debugLine="Select extra.index_ob_olaviyat(flag-1)";
Debug.ShouldStop(32);
switch (BA.switchObjectToInt(index.mostCurrent._extra._index_ob_olaviyat.getArrayElement(true,BA.numberCast(int.class, RemoteObject.solve(new RemoteObject[] {BA.numberCast(double.class, _flag),RemoteObject.createImmutable(1)}, "-",1, 0))),BA.numberCast(int.class, 225),BA.numberCast(int.class, 224),BA.numberCast(int.class, 223),BA.numberCast(int.class, 222),BA.numberCast(int.class, 221),BA.numberCast(int.class, 22),BA.numberCast(int.class, 111),BA.numberCast(int.class, 11),BA.numberCast(int.class, 1))) {
case 0: {
 BA.debugLineNum = 136;BA.debugLine="Dim left_draw As Int =  padding_space";
Debug.ShouldStop(128);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 137;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(256);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 138;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(512);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 139;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
Debug.ShouldStop(1024);
index.mostCurrent._extra._index_ob_top = index.mostCurrent._extra._index_ob_top;
 BA.debugLineNum = 140;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
Debug.ShouldStop(2048);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 1),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 141;BA.debugLine="extra.index_ob_top_cach = width_draw";
Debug.ShouldStop(4096);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 142;BA.debugLine="panel.Color = Colors.rgb(255, 51, 0) ' range";
Debug.ShouldStop(8192);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 255)),(Object)(BA.numberCast(int.class, 51)),(Object)(BA.numberCast(int.class, 0))));
 BA.debugLineNum = 143;BA.debugLine="Log(\"225 ok\")";
Debug.ShouldStop(16384);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("225 ok")));
 break; }
case 1: {
 BA.debugLineNum = 145;BA.debugLine="Dim left_draw As Int =  padding_space";
Debug.ShouldStop(65536);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 146;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(131072);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 147;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(262144);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 148;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
Debug.ShouldStop(524288);
index.mostCurrent._extra._index_ob_top = index.mostCurrent._extra._index_ob_top;
 BA.debugLineNum = 149;BA.debugLine="extra.index_ob_olaviyat(flag)=225";
Debug.ShouldStop(1048576);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 225),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 150;BA.debugLine="extra.index_ob_top_cach = width_draw";
Debug.ShouldStop(2097152);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 151;BA.debugLine="panel.Color = Colors.rgb(0, 51, 0) ' green";
Debug.ShouldStop(4194304);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 0)),(Object)(BA.numberCast(int.class, 51)),(Object)(BA.numberCast(int.class, 0))));
 break; }
case 2: {
 BA.debugLineNum = 153;BA.debugLine="Dim left_draw As Int =  padding_space";
Debug.ShouldStop(16777216);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 154;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(33554432);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 155;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(67108864);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 156;BA.debugLine="extra.index_ob_top = extra.index_ob_top";
Debug.ShouldStop(134217728);
index.mostCurrent._extra._index_ob_top = index.mostCurrent._extra._index_ob_top;
 BA.debugLineNum = 157;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
Debug.ShouldStop(268435456);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 1),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 158;BA.debugLine="extra.index_ob_top_cach = width_draw";
Debug.ShouldStop(536870912);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 159;BA.debugLine="panel.Color = Colors.rgb(255, 255, 102) ' yell";
Debug.ShouldStop(1073741824);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 255)),(Object)(BA.numberCast(int.class, 255)),(Object)(BA.numberCast(int.class, 102))));
 break; }
case 3: {
 BA.debugLineNum = 161;BA.debugLine="Dim left_draw As Int =  padding_space";
Debug.ShouldStop(1);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 162;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(2);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 163;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(4);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 164;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
Debug.ShouldStop(8);
index.mostCurrent._extra._index_ob_top = RemoteObject.solve(new RemoteObject[] {index.mostCurrent._extra._index_ob_top,index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA)}, "+",1, 1);
 BA.debugLineNum = 165;BA.debugLine="extra.index_ob_olaviyat(flag)=223";
Debug.ShouldStop(16);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 223),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 166;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(32);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 167;BA.debugLine="panel.Color = Colors.red";
Debug.ShouldStop(64);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Red"));
 break; }
case 4: {
 BA.debugLineNum = 169;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
Debug.ShouldStop(256);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66.4)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 170;BA.debugLine="Dim width_draw As Int = 33.23%x";
Debug.ShouldStop(512);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.23)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 171;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(1024);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 172;BA.debugLine="extra.index_ob_top = extra.index_ob_top + 33.2";
Debug.ShouldStop(2048);
index.mostCurrent._extra._index_ob_top = RemoteObject.solve(new RemoteObject[] {index.mostCurrent._extra._index_ob_top,index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA)}, "+",1, 1);
 BA.debugLineNum = 173;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
Debug.ShouldStop(4096);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 1),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 174;BA.debugLine="extra.index_ob_top_cach = width_draw";
Debug.ShouldStop(8192);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 175;BA.debugLine="panel.Color = Colors.Green";
Debug.ShouldStop(16384);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Green"));
 break; }
case 5: {
 BA.debugLineNum = 177;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
Debug.ShouldStop(65536);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66.4)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 178;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(131072);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 179;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(262144);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 180;BA.debugLine="extra.index_ob_olaviyat(flag)=221";
Debug.ShouldStop(524288);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 221),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 181;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(1048576);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 182;BA.debugLine="panel.Color = Colors.Blue";
Debug.ShouldStop(2097152);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Blue"));
 break; }
case 6: {
 BA.debugLineNum = 184;BA.debugLine="Dim left_draw As Int = 66.4%x  + padding_space";
Debug.ShouldStop(8388608);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 66.4)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 185;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(16777216);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 186;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(33554432);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 187;BA.debugLine="extra.index_ob_olaviyat(flag)=1";
Debug.ShouldStop(67108864);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 1),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 188;BA.debugLine="extra.index_ob_top_cach =  width_draw";
Debug.ShouldStop(134217728);
index.mostCurrent._extra._index_ob_top_cach = _width_draw;
 BA.debugLineNum = 189;BA.debugLine="panel.Color = Colors.rgb(255, 102, 255)";
Debug.ShouldStop(268435456);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 255)),(Object)(BA.numberCast(int.class, 102)),(Object)(BA.numberCast(int.class, 255))));
 break; }
case 7: {
 BA.debugLineNum = 191;BA.debugLine="Dim left_draw As Int = 33.3%x + padding_space";
Debug.ShouldStop(1073741824);
_left_draw = RemoteObject.solve(new RemoteObject[] {index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.3)),index.mostCurrent.activityBA),_padding_space}, "+",1, 1);Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 192;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(-2147483648);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 193;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(1);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 194;BA.debugLine="extra.index_ob_olaviyat(flag)=111";
Debug.ShouldStop(2);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 111),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 195;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(4);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 196;BA.debugLine="panel.Color = Colors.Black";
Debug.ShouldStop(8);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"Black"));
 break; }
case 8: {
 BA.debugLineNum = 198;BA.debugLine="Dim left_draw As Int = padding_space";
Debug.ShouldStop(32);
_left_draw = _padding_space;Debug.locals.put("left_draw", _left_draw);Debug.locals.put("left_draw", _left_draw);
 BA.debugLineNum = 199;BA.debugLine="Dim width_draw As Int = 33.2%x";
Debug.ShouldStop(64);
_width_draw = index.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 33.2)),index.mostCurrent.activityBA);Debug.locals.put("width_draw", _width_draw);Debug.locals.put("width_draw", _width_draw);
 BA.debugLineNum = 200;BA.debugLine="Dim shadow_space As Int = 15dip";
Debug.ShouldStop(128);
_shadow_space = index.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 15)));Debug.locals.put("shadow_space", _shadow_space);Debug.locals.put("shadow_space", _shadow_space);
 BA.debugLineNum = 201;BA.debugLine="extra.index_ob_olaviyat(flag)=11";
Debug.ShouldStop(256);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 11),BA.numberCast(int.class, _flag));
 BA.debugLineNum = 202;BA.debugLine="extra.index_ob_top_cach = 0";
Debug.ShouldStop(512);
index.mostCurrent._extra._index_ob_top_cach = BA.numberCast(int.class, 0);
 BA.debugLineNum = 203;BA.debugLine="panel.Color = Colors.DarkGray";
Debug.ShouldStop(1024);
_panel.runVoidMethod ("setColor",index.mostCurrent.__c.getField(false,"Colors").getField(true,"DarkGray"));
 break; }
}
;
 };
 BA.debugLineNum = 208;BA.debugLine="Dim cd As ColorDrawable";
Debug.ShouldStop(32768);
_cd = RemoteObject.createNew ("anywheresoftware.b4a.objects.drawable.ColorDrawable");Debug.locals.put("cd", _cd);
 BA.debugLineNum = 211;BA.debugLine="index_ScrollView.Panel.AddView(panel,left_draw,ex";
Debug.ShouldStop(262144);
index.mostCurrent._index_scrollview.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((_panel.getObject())),(Object)(_left_draw),(Object)(RemoteObject.solve(new RemoteObject[] {index.mostCurrent._extra._index_ob_top,_space}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {_width_draw,_space}, "-",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {_width_draw,_space}, "-",1, 1)));
 BA.debugLineNum = 212;BA.debugLine="extra.index_ob_top = extra.index_ob_top + extra.i";
Debug.ShouldStop(524288);
index.mostCurrent._extra._index_ob_top = RemoteObject.solve(new RemoteObject[] {index.mostCurrent._extra._index_ob_top,index.mostCurrent._extra._index_ob_top_cach}, "+",1, 1);
 BA.debugLineNum = 213;BA.debugLine="index_ScrollView.Panel.Height = extra.index_ob_to";
Debug.ShouldStop(1048576);
index.mostCurrent._index_scrollview.runMethod(false,"getPanel").runMethod(true,"setHeight",RemoteObject.solve(new RemoteObject[] {index.mostCurrent._extra._index_ob_top,_space}, "+",1, 1));
 BA.debugLineNum = 214;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _jobdone(RemoteObject _job) throws Exception{
try {
		Debug.PushSubsStack("jobdone (index) ","index",3,index.mostCurrent.activityBA,index.mostCurrent,33);
if (RapidSub.canDelegate("jobdone")) return manini.b4a.example.index.remoteMe.runUserSub(false, "index","jobdone", _job);
RemoteObject _parser = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.JSONParser");
int _i = 0;
RemoteObject _x = RemoteObject.createImmutable(0);
Debug.locals.put("job", _job);
 BA.debugLineNum = 33;BA.debugLine="Sub jobdone(job As HttpJob)";
Debug.ShouldStop(1);
 BA.debugLineNum = 34;BA.debugLine="If job.Success = True Then";
Debug.ShouldStop(2);
if (RemoteObject.solveBoolean("=",_job.getField(true,"_success"),index.mostCurrent.__c.getField(true,"True"))) { 
 BA.debugLineNum = 35;BA.debugLine="If job.JobName = \"load_indexjob\" Then";
Debug.ShouldStop(4);
if (RemoteObject.solveBoolean("=",_job.getField(true,"_jobname"),BA.ObjectToString("load_indexjob"))) { 
 BA.debugLineNum = 36;BA.debugLine="Dim parser As JSONParser";
Debug.ShouldStop(8);
_parser = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.JSONParser");Debug.locals.put("parser", _parser);
 BA.debugLineNum = 37;BA.debugLine="parser.Initialize(job.GetString)";
Debug.ShouldStop(16);
_parser.runVoidMethod ("Initialize",(Object)(_job.runMethod(true,"_getstring")));
 BA.debugLineNum = 38;BA.debugLine="index_retrive_list= parser.NextArray";
Debug.ShouldStop(32);
index.mostCurrent._index_retrive_list = _parser.runMethod(false,"NextArray");
 BA.debugLineNum = 40;BA.debugLine="Log(extra.image_address &  index_retrive_list.G";
Debug.ShouldStop(128);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.concat(index.mostCurrent._extra._image_address,index.mostCurrent._index_retrive_list.runMethod(false,"Get",(Object)(BA.numberCast(int.class, 2))))));
 BA.debugLineNum = 41;BA.debugLine="For  i = 1 To 50";
Debug.ShouldStop(256);
{
final int step7 = 1;
final int limit7 = 50;
_i = 1 ;
for (;(step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7) ;_i = ((int)(0 + _i + step7))  ) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 42;BA.debugLine="Select extra.index_ob_olaviyat(i-1)";
Debug.ShouldStop(512);
switch (BA.switchObjectToInt(index.mostCurrent._extra._index_ob_olaviyat.getArrayElement(true,RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(_i),RemoteObject.createImmutable(1)}, "-",1, 1)),BA.numberCast(int.class, 1),BA.numberCast(int.class, 22),BA.numberCast(int.class, 225),BA.numberCast(int.class, 224),BA.numberCast(int.class, 223),BA.numberCast(int.class, 222),BA.numberCast(int.class, 221),BA.numberCast(int.class, 11),BA.numberCast(int.class, 111))) {
case 0: {
 BA.debugLineNum = 44;BA.debugLine="Dim x As Int = Rnd(1,5)";
Debug.ShouldStop(2048);
_x = index.mostCurrent.__c.runMethod(true,"Rnd",(Object)(BA.numberCast(int.class, 1)),(Object)(BA.numberCast(int.class, 5)));Debug.locals.put("x", _x);Debug.locals.put("x", _x);
 BA.debugLineNum = 45;BA.debugLine="If x = 1 Then";
Debug.ShouldStop(4096);
if (RemoteObject.solveBoolean("=",_x,BA.numberCast(double.class, 1))) { 
 BA.debugLineNum = 46;BA.debugLine="index_draw(\"larg\",i)";
Debug.ShouldStop(8192);
_index_draw(BA.ObjectToString("larg"),BA.NumberToString(_i));
 BA.debugLineNum = 47;BA.debugLine="Log(\"larg\")";
Debug.ShouldStop(16384);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("larg")));
 };
 BA.debugLineNum = 49;BA.debugLine="If x = 2 Then";
Debug.ShouldStop(65536);
if (RemoteObject.solveBoolean("=",_x,BA.numberCast(double.class, 2))) { 
 BA.debugLineNum = 50;BA.debugLine="index_draw(\"medium\",i)";
Debug.ShouldStop(131072);
_index_draw(BA.ObjectToString("medium"),BA.NumberToString(_i));
 BA.debugLineNum = 51;BA.debugLine="Log(\"medium\")";
Debug.ShouldStop(262144);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("medium")));
 };
 BA.debugLineNum = 53;BA.debugLine="If x = 3 Then";
Debug.ShouldStop(1048576);
if (RemoteObject.solveBoolean("=",_x,BA.numberCast(double.class, 3))) { 
 BA.debugLineNum = 54;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(2097152);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 BA.debugLineNum = 55;BA.debugLine="Log(\"small\")";
Debug.ShouldStop(4194304);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("small")));
 };
 BA.debugLineNum = 57;BA.debugLine="If x = 4 Then";
Debug.ShouldStop(16777216);
if (RemoteObject.solveBoolean("=",_x,BA.numberCast(double.class, 4))) { 
 BA.debugLineNum = 58;BA.debugLine="extra.index_ob_olaviyat(i-1) = 4";
Debug.ShouldStop(33554432);
index.mostCurrent._extra._index_ob_olaviyat.setArrayElement (BA.numberCast(int.class, 4),RemoteObject.solve(new RemoteObject[] {RemoteObject.createImmutable(_i),RemoteObject.createImmutable(1)}, "-",1, 1));
 BA.debugLineNum = 59;BA.debugLine="index_draw(\"medium\",i)";
Debug.ShouldStop(67108864);
_index_draw(BA.ObjectToString("medium"),BA.NumberToString(_i));
 BA.debugLineNum = 60;BA.debugLine="Log(\"medium 4 \")";
Debug.ShouldStop(134217728);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("medium 4 ")));
 };
 break; }
case 1: {
 BA.debugLineNum = 63;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(1073741824);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 break; }
case 2: {
 BA.debugLineNum = 65;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(1);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 BA.debugLineNum = 66;BA.debugLine="Log(\"225\")";
Debug.ShouldStop(2);
index.mostCurrent.__c.runVoidMethod ("Log",(Object)(RemoteObject.createImmutable("225")));
 break; }
case 3: {
 BA.debugLineNum = 68;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(8);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 break; }
case 4: {
 BA.debugLineNum = 70;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(32);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 break; }
case 5: {
 BA.debugLineNum = 72;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(128);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 break; }
case 6: {
 BA.debugLineNum = 74;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(512);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 break; }
case 7: {
 BA.debugLineNum = 76;BA.debugLine="Dim x As Int = Rnd(1,3)";
Debug.ShouldStop(2048);
_x = index.mostCurrent.__c.runMethod(true,"Rnd",(Object)(BA.numberCast(int.class, 1)),(Object)(BA.numberCast(int.class, 3)));Debug.locals.put("x", _x);Debug.locals.put("x", _x);
 BA.debugLineNum = 77;BA.debugLine="If x = 1 Then  index_draw(\"small\",i)";
Debug.ShouldStop(4096);
if (RemoteObject.solveBoolean("=",_x,BA.numberCast(double.class, 1))) { 
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));};
 BA.debugLineNum = 78;BA.debugLine="If x = 2 Then  index_draw(\"medium\",i)";
Debug.ShouldStop(8192);
if (RemoteObject.solveBoolean("=",_x,BA.numberCast(double.class, 2))) { 
_index_draw(BA.ObjectToString("medium"),BA.NumberToString(_i));};
 break; }
case 8: {
 BA.debugLineNum = 80;BA.debugLine="index_draw(\"small\",i)";
Debug.ShouldStop(32768);
_index_draw(BA.ObjectToString("small"),BA.NumberToString(_i));
 break; }
}
;
 }
}Debug.locals.put("i", _i);
;
 };
 };
 BA.debugLineNum = 86;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}