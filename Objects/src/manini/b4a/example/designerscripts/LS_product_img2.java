package manini.b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_product_img2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("product_img2").vw.setLeft((int)((25d / 100 * width)));
views.get("product_img2").vw.setWidth((int)((75d / 100 * width) - ((25d / 100 * width))));
views.get("product_img2").vw.setTop((int)((5d / 100 * width)));
views.get("product_img2").vw.setHeight((int)((55d / 100 * width) - ((5d / 100 * width))));
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - (0d)));

}
}