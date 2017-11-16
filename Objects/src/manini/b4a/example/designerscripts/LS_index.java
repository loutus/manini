package manini.b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_index{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[index/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="header.SetLeftAndRight(0,100%x)"[index/General script]
views.get("header").vw.setLeft((int)(0d));
views.get("header").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 4;BA.debugLine="header.Height = 55dip"[index/General script]
views.get("header").vw.setHeight((int)((55d * scale)));
//BA.debugLineNum = 5;BA.debugLine="header.Top = 0"[index/General script]
views.get("header").vw.setTop((int)(0d));
//BA.debugLineNum = 6;BA.debugLine="index_ScrollView.SetLeftAndRight(0,100%x)"[index/General script]
views.get("index_scrollview").vw.setLeft((int)(0d));
views.get("index_scrollview").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 7;BA.debugLine="index_ScrollView.Height = 100%y - header.Height"[index/General script]
views.get("index_scrollview").vw.setHeight((int)((100d / 100 * height)-(views.get("header").vw.getHeight())));
//BA.debugLineNum = 8;BA.debugLine="index_ScrollView.Top  = header.Bottom"[index/General script]
views.get("index_scrollview").vw.setTop((int)((views.get("header").vw.getTop() + views.get("header").vw.getHeight())));
//BA.debugLineNum = 11;BA.debugLine="menubtn.Height=35dip"[index/General script]
views.get("menubtn").vw.setHeight((int)((35d * scale)));
//BA.debugLineNum = 12;BA.debugLine="menubtn.Width = 35dip"[index/General script]
views.get("menubtn").vw.setWidth((int)((35d * scale)));
//BA.debugLineNum = 13;BA.debugLine="menubtn.Right = 100%x-10dip"[index/General script]
views.get("menubtn").vw.setLeft((int)((100d / 100 * width)-(10d * scale) - (views.get("menubtn").vw.getWidth())));

}
}