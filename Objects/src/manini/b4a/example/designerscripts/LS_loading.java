package manini.b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_loading{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("logo").vw.setHeight((int)((200d * scale)));
views.get("logo").vw.setWidth((int)((200d * scale)));
views.get("logo").vw.setTop((int)((50d / 100 * height)-(100d * scale)));
views.get("logo").vw.setLeft((int)((50d / 100 * width)-(100d * scale)));
views.get("loadding_text").vw.setLeft((int)(0d));
views.get("loadding_text").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("loadding_text").vw.setHeight((int)((45d * scale)));
views.get("loadding_text").vw.setTop((int)((95d / 100 * height) - (views.get("loadding_text").vw.getHeight())));

}
}