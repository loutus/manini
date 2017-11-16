package manini.b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_menu{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
String _space="";
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((105d / 100 * width) - (0d)));
views.get("panel1").vw.setHeight((int)((155d * scale)));
views.get("panel1").vw.setTop((int)(0-5d));
views.get("panel2").vw.setLeft((int)(0d));
views.get("panel2").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel2").vw.setTop((int)(0d));
views.get("panel2").vw.setHeight((int)((100d / 100 * height) - (0d)));
views.get("menuusername").vw.setLeft((int)((5d * scale)));
views.get("menuusername").vw.setWidth((int)((100d / 100 * width)-(5d * scale) - ((5d * scale))));
views.get("menuusername").vw.setHeight((int)((30d * scale)));
views.get("menuusername").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight())-(35d * scale)));
//BA.debugLineNum = 13;BA.debugLine="menuavatar.Height = 86dip"[menu/General script]
views.get("menuavatar").vw.setHeight((int)((86d * scale)));
//BA.debugLineNum = 14;BA.debugLine="menuavatar.Width = 86dip"[menu/General script]
views.get("menuavatar").vw.setWidth((int)((86d * scale)));
//BA.debugLineNum = 16;BA.debugLine="menuavatar.Top = 20dip"[menu/General script]
views.get("menuavatar").vw.setTop((int)((20d * scale)));
//BA.debugLineNum = 17;BA.debugLine="menuavatar.Left = 50%x - 43dip"[menu/General script]
views.get("menuavatar").vw.setLeft((int)((50d / 100 * width)-(43d * scale)));
//BA.debugLineNum = 18;BA.debugLine="space=5dip"[menu/General script]
_space = BA.NumberToString((5d * scale));
//BA.debugLineNum = 19;BA.debugLine="menuhome.SetLeftAndRight(0,90%x)"[menu/General script]
views.get("menuhome").vw.setLeft((int)(0d));
views.get("menuhome").vw.setWidth((int)((90d / 100 * width) - (0d)));
//BA.debugLineNum = 20;BA.debugLine="menuhome.Top = Panel1.Bottom + space"[menu/General script]
views.get("menuhome").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight())+Double.parseDouble(_space)));
//BA.debugLineNum = 22;BA.debugLine="menucategory.SetLeftAndRight(0,90%x)"[menu/General script]
views.get("menucategory").vw.setLeft((int)(0d));
views.get("menucategory").vw.setWidth((int)((90d / 100 * width) - (0d)));
//BA.debugLineNum = 23;BA.debugLine="menucategory.Top = menuhome.Bottom + space"[menu/General script]
views.get("menucategory").vw.setTop((int)((views.get("menuhome").vw.getTop() + views.get("menuhome").vw.getHeight())+Double.parseDouble(_space)));
//BA.debugLineNum = 25;BA.debugLine="menunew.SetLeftAndRight(0,90%x)"[menu/General script]
views.get("menunew").vw.setLeft((int)(0d));
views.get("menunew").vw.setWidth((int)((90d / 100 * width) - (0d)));
//BA.debugLineNum = 26;BA.debugLine="menunew.Top = menucategory.Bottom + space"[menu/General script]
views.get("menunew").vw.setTop((int)((views.get("menucategory").vw.getTop() + views.get("menucategory").vw.getHeight())+Double.parseDouble(_space)));
//BA.debugLineNum = 28;BA.debugLine="menufantast.SetLeftAndRight(0,90%x)"[menu/General script]
views.get("menufantast").vw.setLeft((int)(0d));
views.get("menufantast").vw.setWidth((int)((90d / 100 * width) - (0d)));
//BA.debugLineNum = 29;BA.debugLine="menufantast.Top = menunew.Bottom + space"[menu/General script]
views.get("menufantast").vw.setTop((int)((views.get("menunew").vw.getTop() + views.get("menunew").vw.getHeight())+Double.parseDouble(_space)));
//BA.debugLineNum = 32;BA.debugLine="menuporfrosh.SetLeftAndRight(0,90%x)"[menu/General script]
views.get("menuporfrosh").vw.setLeft((int)(0d));
views.get("menuporfrosh").vw.setWidth((int)((90d / 100 * width) - (0d)));
//BA.debugLineNum = 33;BA.debugLine="menuporfrosh.Top = menufantast.Bottom + space"[menu/General script]
views.get("menuporfrosh").vw.setTop((int)((views.get("menufantast").vw.getTop() + views.get("menufantast").vw.getHeight())+Double.parseDouble(_space)));
//BA.debugLineNum = 35;BA.debugLine="menutakhfif.SetLeftAndRight(0,90%x)"[menu/General script]
views.get("menutakhfif").vw.setLeft((int)(0d));
views.get("menutakhfif").vw.setWidth((int)((90d / 100 * width) - (0d)));
//BA.debugLineNum = 36;BA.debugLine="menutakhfif.Top = menuporfrosh.Bottom + space"[menu/General script]
views.get("menutakhfif").vw.setTop((int)((views.get("menuporfrosh").vw.getTop() + views.get("menuporfrosh").vw.getHeight())+Double.parseDouble(_space)));

}
}