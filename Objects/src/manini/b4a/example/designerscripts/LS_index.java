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
//BA.debugLineNum = 11;BA.debugLine="categorypnl.SetLeftAndRight(0,100%x)"[index/General script]
views.get("categorypnl").vw.setLeft((int)(0d));
views.get("categorypnl").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 12;BA.debugLine="categorypnl.Height = 100%y"[index/General script]
views.get("categorypnl").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 13;BA.debugLine="categorypnl.Top  = 0"[index/General script]
views.get("categorypnl").vw.setTop((int)(0d));
//BA.debugLineNum = 16;BA.debugLine="menubtn.Height=35dip"[index/General script]
views.get("menubtn").vw.setHeight((int)((35d * scale)));
//BA.debugLineNum = 17;BA.debugLine="menubtn.Width = 35dip"[index/General script]
views.get("menubtn").vw.setWidth((int)((35d * scale)));
//BA.debugLineNum = 18;BA.debugLine="menubtn.Right = 100%x-10dip"[index/General script]
views.get("menubtn").vw.setLeft((int)((100d / 100 * width)-(10d * scale) - (views.get("menubtn").vw.getWidth())));
//BA.debugLineNum = 19;BA.debugLine="logo.Height = 15dip"[index/General script]
views.get("logo").vw.setHeight((int)((15d * scale)));
//BA.debugLineNum = 20;BA.debugLine="logo.Top = menubtn.top + 10dip"[index/General script]
views.get("logo").vw.setTop((int)((views.get("menubtn").vw.getTop())+(10d * scale)));
//BA.debugLineNum = 21;BA.debugLine="logo.Width = 81dip"[index/General script]
views.get("logo").vw.setWidth((int)((81d * scale)));
//BA.debugLineNum = 22;BA.debugLine="logo.Right = menubtn.Left -8dip"[index/General script]
views.get("logo").vw.setLeft((int)((views.get("menubtn").vw.getLeft())-(8d * scale) - (views.get("logo").vw.getWidth())));
//BA.debugLineNum = 25;BA.debugLine="catheader.SetLeftAndRight(0,100%x)"[index/General script]
views.get("catheader").vw.setLeft((int)(0d));
views.get("catheader").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 26;BA.debugLine="catheader.Height = 55dip"[index/General script]
views.get("catheader").vw.setHeight((int)((55d * scale)));
//BA.debugLineNum = 27;BA.debugLine="catheader.Top = 0"[index/General script]
views.get("catheader").vw.setTop((int)(0d));
//BA.debugLineNum = 29;BA.debugLine="catmenubtn.Height=35dip"[index/General script]
views.get("catmenubtn").vw.setHeight((int)((35d * scale)));
//BA.debugLineNum = 30;BA.debugLine="catmenubtn.Width = 35dip"[index/General script]
views.get("catmenubtn").vw.setWidth((int)((35d * scale)));
//BA.debugLineNum = 31;BA.debugLine="catmenubtn.Right = 100%x-10dip"[index/General script]
views.get("catmenubtn").vw.setLeft((int)((100d / 100 * width)-(10d * scale) - (views.get("catmenubtn").vw.getWidth())));
//BA.debugLineNum = 33;BA.debugLine="cattitle.Height = 55dip"[index/General script]
views.get("cattitle").vw.setHeight((int)((55d * scale)));
//BA.debugLineNum = 35;BA.debugLine="cattitle.Top = 0"[index/General script]
views.get("cattitle").vw.setTop((int)(0d));
//BA.debugLineNum = 36;BA.debugLine="cattitle.Width=50%x"[index/General script]
views.get("cattitle").vw.setWidth((int)((50d / 100 * width)));
//BA.debugLineNum = 37;BA.debugLine="cattitle.Right = catmenubtn.Left - 8dip"[index/General script]
views.get("cattitle").vw.setLeft((int)((views.get("catmenubtn").vw.getLeft())-(8d * scale) - (views.get("cattitle").vw.getWidth())));
//BA.debugLineNum = 40;BA.debugLine="categoryscroll.SetLeftAndRight(0,100%x)"[index/General script]
views.get("categoryscroll").vw.setLeft((int)(0d));
views.get("categoryscroll").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 41;BA.debugLine="categoryscroll.Height = 100%y-55dip"[index/General script]
views.get("categoryscroll").vw.setHeight((int)((100d / 100 * height)-(55d * scale)));
//BA.debugLineNum = 42;BA.debugLine="categoryscroll.Top = 55dip"[index/General script]
views.get("categoryscroll").vw.setTop((int)((55d * scale)));

}
}