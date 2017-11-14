package manini.b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class property_subs_0 {


public static RemoteObject  _activity_create(RemoteObject _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (property) ","property",5,property.mostCurrent.activityBA,property.mostCurrent,20);
if (RapidSub.canDelegate("activity_create")) return manini.b4a.example.property.remoteMe.runUserSub(false, "property","activity_create", _firsttime);
RemoteObject _parser = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.JSONParser");
RemoteObject _root = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.List");
RemoteObject _colroot = RemoteObject.declareNull("anywheresoftware.b4a.objects.collections.Map");
RemoteObject _name = RemoteObject.createImmutable("");
RemoteObject _text = RemoteObject.createImmutable("");
RemoteObject _grouping = RemoteObject.createImmutable("");
RemoteObject _lblnodata = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 20;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 22;BA.debugLine="Activity.LoadLayout(\"property\")";
Debug.ShouldStop(2097152);
property.mostCurrent._activity.runMethodAndSync(false,"LoadLayout",(Object)(RemoteObject.createImmutable("property")),property.mostCurrent.activityBA);
 BA.debugLineNum = 23;BA.debugLine="topset = 5dip";
Debug.ShouldStop(4194304);
property._topset = property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)));
 BA.debugLineNum = 28;BA.debugLine="Try";
Debug.ShouldStop(134217728);
try { BA.debugLineNum = 29;BA.debugLine="Dim parser As JSONParser";
Debug.ShouldStop(268435456);
_parser = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.JSONParser");Debug.locals.put("parser", _parser);
 BA.debugLineNum = 30;BA.debugLine="parser.Initialize(extra.propertyjson)";
Debug.ShouldStop(536870912);
_parser.runVoidMethod ("Initialize",(Object)(BA.NumberToString(property.mostCurrent._extra._propertyjson)));
 BA.debugLineNum = 31;BA.debugLine="Dim root As List = parser.NextArray";
Debug.ShouldStop(1073741824);
_root = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.List");
_root = _parser.runMethod(false,"NextArray");Debug.locals.put("root", _root);Debug.locals.put("root", _root);
 BA.debugLineNum = 32;BA.debugLine="For Each colroot As Map In root";
Debug.ShouldStop(-2147483648);
_colroot = RemoteObject.createNew ("anywheresoftware.b4a.objects.collections.Map");
{
final RemoteObject group7 = _root;
final int groupLen7 = group7.runMethod(true,"getSize").<Integer>get()
;int index7 = 0;
;
for (; index7 < groupLen7;index7++){
_colroot.setObject(group7.runMethod(false,"Get",index7));
Debug.locals.put("colroot", _colroot);
 BA.debugLineNum = 33;BA.debugLine="Dim name As String = colroot.Get(\"name\")";
Debug.ShouldStop(1);
_name = BA.ObjectToString(_colroot.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("name")))));Debug.locals.put("name", _name);Debug.locals.put("name", _name);
 BA.debugLineNum = 34;BA.debugLine="Dim text As String = colroot.Get(\"text\")";
Debug.ShouldStop(2);
_text = BA.ObjectToString(_colroot.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("text")))));Debug.locals.put("text", _text);Debug.locals.put("text", _text);
 BA.debugLineNum = 35;BA.debugLine="Dim grouping As String = colroot.Get(\"grouping";
Debug.ShouldStop(4);
_grouping = BA.ObjectToString(_colroot.runMethod(false,"Get",(Object)((RemoteObject.createImmutable("grouping")))));Debug.locals.put("grouping", _grouping);Debug.locals.put("grouping", _grouping);
 BA.debugLineNum = 37;BA.debugLine="Dim lblnodata As Label";
Debug.ShouldStop(16);
_lblnodata = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");Debug.locals.put("lblnodata", _lblnodata);
 BA.debugLineNum = 38;BA.debugLine="lblnodata.Initialize(\"\")";
Debug.ShouldStop(32);
_lblnodata.runVoidMethod ("Initialize",property.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 39;BA.debugLine="lblnodata.Text =grouping";
Debug.ShouldStop(64);
_lblnodata.runMethod(true,"setText",BA.ObjectToCharSequence(_grouping));
 BA.debugLineNum = 40;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
Debug.ShouldStop(128);
_lblnodata.runMethod(true,"setGravity",property.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER"));
 BA.debugLineNum = 41;BA.debugLine="lblnodata.TextColor = Colors.rgb(38, 38, 38)";
Debug.ShouldStop(256);
_lblnodata.runMethod(true,"setTextColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 38)),(Object)(BA.numberCast(int.class, 38)),(Object)(BA.numberCast(int.class, 38))));
 BA.debugLineNum = 42;BA.debugLine="lblnodata.color = Colors.rgb(217, 217, 217)";
Debug.ShouldStop(512);
_lblnodata.runVoidMethod ("setColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 217)),(Object)(BA.numberCast(int.class, 217)),(Object)(BA.numberCast(int.class, 217))));
 BA.debugLineNum = 43;BA.debugLine="lblnodata.TextSize = 11dip";
Debug.ShouldStop(1024);
_lblnodata.runMethod(true,"setTextSize",BA.numberCast(float.class, property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 11)))));
 BA.debugLineNum = 44;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"";
Debug.ShouldStop(2048);
_lblnodata.runMethod(false,"setTypeface",property.mostCurrent.__c.getField(false,"Typeface").runMethod(false,"LoadFromAssets",(Object)(RemoteObject.createImmutable("yekan.ttf"))));
 BA.debugLineNum = 45;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,5dip,topse";
Debug.ShouldStop(4096);
property.mostCurrent._scrollview1.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((_lblnodata.getObject())),(Object)(property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(property._topset),(Object)(RemoteObject.solve(new RemoteObject[] {property.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),property.mostCurrent.activityBA),property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))}, "-",1, 1)),(Object)(property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 35)))));
 BA.debugLineNum = 47;BA.debugLine="Dim lblnodata As Label";
Debug.ShouldStop(16384);
_lblnodata = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");Debug.locals.put("lblnodata", _lblnodata);
 BA.debugLineNum = 48;BA.debugLine="lblnodata.Initialize(\"\")";
Debug.ShouldStop(32768);
_lblnodata.runVoidMethod ("Initialize",property.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 49;BA.debugLine="lblnodata.Text =\"  \" & text";
Debug.ShouldStop(65536);
_lblnodata.runMethod(true,"setText",BA.ObjectToCharSequence(RemoteObject.concat(RemoteObject.createImmutable("  "),_text)));
 BA.debugLineNum = 50;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
Debug.ShouldStop(131072);
_lblnodata.runMethod(true,"setGravity",property.mostCurrent.__c.getField(false,"Gravity").getField(true,"RIGHT"));
 BA.debugLineNum = 51;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115";
Debug.ShouldStop(262144);
_lblnodata.runMethod(true,"setTextColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 115)),(Object)(BA.numberCast(int.class, 115)),(Object)(BA.numberCast(int.class, 115))));
 BA.debugLineNum = 52;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
Debug.ShouldStop(524288);
_lblnodata.runVoidMethod ("setColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 242)),(Object)(BA.numberCast(int.class, 242)),(Object)(BA.numberCast(int.class, 242))));
 BA.debugLineNum = 53;BA.debugLine="lblnodata.TextSize = 9dip";
Debug.ShouldStop(1048576);
_lblnodata.runMethod(true,"setTextSize",BA.numberCast(float.class, property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 9)))));
 BA.debugLineNum = 54;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"";
Debug.ShouldStop(2097152);
_lblnodata.runMethod(false,"setTypeface",property.mostCurrent.__c.getField(false,"Typeface").runMethod(false,"LoadFromAssets",(Object)(RemoteObject.createImmutable("yekan.ttf"))));
 BA.debugLineNum = 55;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,5dip,topse";
Debug.ShouldStop(4194304);
property.mostCurrent._scrollview1.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((_lblnodata.getObject())),(Object)(property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))),(Object)(RemoteObject.solve(new RemoteObject[] {property._topset,property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {property.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 70)),property.mostCurrent.activityBA),property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))}, "-",1, 1)),(Object)(property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))));
 BA.debugLineNum = 57;BA.debugLine="Dim lblnodata As Label";
Debug.ShouldStop(16777216);
_lblnodata = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");Debug.locals.put("lblnodata", _lblnodata);
 BA.debugLineNum = 58;BA.debugLine="lblnodata.Initialize(\"\")";
Debug.ShouldStop(33554432);
_lblnodata.runVoidMethod ("Initialize",property.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 59;BA.debugLine="lblnodata.Text =\"  \" & name";
Debug.ShouldStop(67108864);
_lblnodata.runMethod(true,"setText",BA.ObjectToCharSequence(RemoteObject.concat(RemoteObject.createImmutable("  "),_name)));
 BA.debugLineNum = 60;BA.debugLine="lblnodata.Gravity = Gravity.RIGHT";
Debug.ShouldStop(134217728);
_lblnodata.runMethod(true,"setGravity",property.mostCurrent.__c.getField(false,"Gravity").getField(true,"RIGHT"));
 BA.debugLineNum = 61;BA.debugLine="lblnodata.TextColor = Colors.rgb(115, 115, 115";
Debug.ShouldStop(268435456);
_lblnodata.runMethod(true,"setTextColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 115)),(Object)(BA.numberCast(int.class, 115)),(Object)(BA.numberCast(int.class, 115))));
 BA.debugLineNum = 62;BA.debugLine="lblnodata.color = Colors.rgb(242, 242, 242)";
Debug.ShouldStop(536870912);
_lblnodata.runVoidMethod ("setColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 242)),(Object)(BA.numberCast(int.class, 242)),(Object)(BA.numberCast(int.class, 242))));
 BA.debugLineNum = 63;BA.debugLine="lblnodata.TextSize = 10dip";
Debug.ShouldStop(1073741824);
_lblnodata.runMethod(true,"setTextSize",BA.numberCast(float.class, property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 10)))));
 BA.debugLineNum = 64;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"";
Debug.ShouldStop(-2147483648);
_lblnodata.runMethod(false,"setTypeface",property.mostCurrent.__c.getField(false,"Typeface").runMethod(false,"LoadFromAssets",(Object)(RemoteObject.createImmutable("yekan.ttf"))));
 BA.debugLineNum = 65;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,70%x,topse";
Debug.ShouldStop(1);
property.mostCurrent._scrollview1.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((_lblnodata.getObject())),(Object)(property.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 70)),property.mostCurrent.activityBA)),(Object)(RemoteObject.solve(new RemoteObject[] {property._topset,property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))}, "+",1, 1)),(Object)(RemoteObject.solve(new RemoteObject[] {property.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 30)),property.mostCurrent.activityBA),property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 5)))}, "-",1, 1)),(Object)(property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 30)))));
 BA.debugLineNum = 67;BA.debugLine="topset = topset + 65dip";
Debug.ShouldStop(4);
property._topset = RemoteObject.solve(new RemoteObject[] {property._topset,property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 65)))}, "+",1, 1);
 BA.debugLineNum = 69;BA.debugLine="ScrollView1.Panel.Height = topset";
Debug.ShouldStop(16);
property.mostCurrent._scrollview1.runMethod(false,"getPanel").runMethod(true,"setHeight",property._topset);
 }
}Debug.locals.put("colroot", _colroot);
;
 Debug.CheckDeviceExceptions();
} 
       catch (Exception e42) {
			BA.rdebugUtils.runVoidMethod("setLastException",property.processBA, e42.toString()); BA.debugLineNum = 72;BA.debugLine="createnon";
Debug.ShouldStop(128);
_createnon();
 };
 BA.debugLineNum = 75;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
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
		Debug.PushSubsStack("Activity_Pause (property) ","property",5,property.mostCurrent.activityBA,property.mostCurrent,91);
if (RapidSub.canDelegate("activity_pause")) return manini.b4a.example.property.remoteMe.runUserSub(false, "property","activity_pause", _userclosed);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 91;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 93;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
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
		Debug.PushSubsStack("Activity_Resume (property) ","property",5,property.mostCurrent.activityBA,property.mostCurrent,87);
if (RapidSub.canDelegate("activity_resume")) return manini.b4a.example.property.remoteMe.runUserSub(false, "property","activity_resume");
 BA.debugLineNum = 87;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 89;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _createnon() throws Exception{
try {
		Debug.PushSubsStack("createnon (property) ","property",5,property.mostCurrent.activityBA,property.mostCurrent,76);
if (RapidSub.canDelegate("createnon")) return manini.b4a.example.property.remoteMe.runUserSub(false, "property","createnon");
RemoteObject _lblnodata = RemoteObject.declareNull("anywheresoftware.b4a.objects.LabelWrapper");
 BA.debugLineNum = 76;BA.debugLine="Sub createnon()";
Debug.ShouldStop(2048);
 BA.debugLineNum = 77;BA.debugLine="Dim lblnodata As Label";
Debug.ShouldStop(4096);
_lblnodata = RemoteObject.createNew ("anywheresoftware.b4a.objects.LabelWrapper");Debug.locals.put("lblnodata", _lblnodata);
 BA.debugLineNum = 78;BA.debugLine="lblnodata.Initialize(\"\")";
Debug.ShouldStop(8192);
_lblnodata.runVoidMethod ("Initialize",property.mostCurrent.activityBA,(Object)(RemoteObject.createImmutable("")));
 BA.debugLineNum = 79;BA.debugLine="lblnodata.Text =\"هیچ مشخصه ای وجود ندارد\"";
Debug.ShouldStop(16384);
_lblnodata.runMethod(true,"setText",BA.ObjectToCharSequence("هیچ مشخصه ای وجود ندارد"));
 BA.debugLineNum = 80;BA.debugLine="lblnodata.Gravity = Gravity.CENTER";
Debug.ShouldStop(32768);
_lblnodata.runMethod(true,"setGravity",property.mostCurrent.__c.getField(false,"Gravity").getField(true,"CENTER"));
 BA.debugLineNum = 81;BA.debugLine="lblnodata.TextColor = Colors.rgb(179, 179, 179)";
Debug.ShouldStop(65536);
_lblnodata.runMethod(true,"setTextColor",property.mostCurrent.__c.getField(false,"Colors").runMethod(true,"RGB",(Object)(BA.numberCast(int.class, 179)),(Object)(BA.numberCast(int.class, 179)),(Object)(BA.numberCast(int.class, 179))));
 BA.debugLineNum = 82;BA.debugLine="lblnodata.TextSize = 12dip";
Debug.ShouldStop(131072);
_lblnodata.runMethod(true,"setTextSize",BA.numberCast(float.class, property.mostCurrent.__c.runMethod(true,"DipToCurrent",(Object)(BA.numberCast(int.class, 12)))));
 BA.debugLineNum = 83;BA.debugLine="lblnodata.Typeface = Typeface.LoadFromAssets(\"yek";
Debug.ShouldStop(262144);
_lblnodata.runMethod(false,"setTypeface",property.mostCurrent.__c.getField(false,"Typeface").runMethod(false,"LoadFromAssets",(Object)(RemoteObject.createImmutable("yekan.ttf"))));
 BA.debugLineNum = 84;BA.debugLine="ScrollView1.Panel.AddView(lblnodata,0,0,100%x,50%";
Debug.ShouldStop(524288);
property.mostCurrent._scrollview1.runMethod(false,"getPanel").runVoidMethod ("AddView",(Object)((_lblnodata.getObject())),(Object)(BA.numberCast(int.class, 0)),(Object)(BA.numberCast(int.class, 0)),(Object)(property.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 100)),property.mostCurrent.activityBA)),(Object)(property.mostCurrent.__c.runMethod(true,"PerXToCurrent",(Object)(BA.numberCast(float.class, 50)),property.mostCurrent.activityBA)));
 BA.debugLineNum = 85;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private ScrollView1 As ScrollView";
property.mostCurrent._scrollview1 = RemoteObject.createNew ("anywheresoftware.b4a.objects.ScrollViewWrapper");
 //BA.debugLineNum = 17;BA.debugLine="Dim topset As Int";
property._topset = RemoteObject.createImmutable(0);
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}