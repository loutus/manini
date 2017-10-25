package manini.b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_loading{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[loading/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="logo.Height = 200dip"[loading/General script]
views.get("logo").vw.setHeight((int)((200d * scale)));
//BA.debugLineNum = 4;BA.debugLine="logo.Width = 200dip"[loading/General script]
views.get("logo").vw.setWidth((int)((200d * scale)));
//BA.debugLineNum = 6;BA.debugLine="logo.Top = 50%y -100dip"[loading/General script]
views.get("logo").vw.setTop((int)((50d / 100 * height)-(100d * scale)));
//BA.debugLineNum = 7;BA.debugLine="logo.Left = 50%x - 100dip"[loading/General script]
views.get("logo").vw.setLeft((int)((50d / 100 * width)-(100d * scale)));

}
}