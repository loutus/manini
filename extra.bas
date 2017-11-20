Type=StaticCode
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
Sub Process_Globals
	Dim api As String = "http://itrx.babapkg.ir/api.php"
	Dim image_address As String = "http://itrx.babapkg.ir/image/cache/"
	Dim image_address_nc As String = "http://itrx.babapkg.ir/image/"
	Dim share_link As String = "http://itrx.babapkg.ir/index.php?route=product/product&product_id="
	Dim index_ob_top As Int = 0
	Dim index_ob_top_cach As Int =10dip
	Dim index_ob_olaviyat(1000) As Int 
	Dim index_ob_olaviyat_load As Int=1
	Dim product_id_toshow As Int
	Dim product_title As String
	Dim product_title_top As Int
	Dim product_title_flag As Boolean =False
	Dim propertyjson As Int = 0
	Dim procimg_flag As Int = 0
	Dim procimg_count(50) As Int
	Dim flag_procpnl As Int = 0
	Dim pagecountflag As Int=0
End Sub
Sub load_index()
	Log("load_index")
	Dim load_indexjob As HttpJob
	load_indexjob.Initialize("load_indexjob",index)
	load_indexjob.PostString(api,"op=index")
End Sub
Sub download_image(id,path,flag)
	path= path.Replace(".jpg","-600x600.jpg")
	Dim downloader As HttpJob
	downloader.Initialize("downloadimage*" & id & "$"  & path & "#" & flag,index )
	downloader.Download(image_address & path)
End Sub
Sub main_download_lastproduct(indexf,image)
	Dim downloader As HttpJob
	downloader.Initialize("downloadimglastproc*" & indexf & "*" & image,index )
	downloader.Download( image)
End Sub
Sub main_download_category_list(indexf,image)
	Dim downloader As HttpJob
	downloader.Initialize("downloadimgcatlist*" & indexf & "*" & image,index )
	downloader.Download( image)
End Sub
Sub main_download_categorypic(indexf,image)
	Dim downloader As HttpJob
	downloader.Initialize("downloadimgcategory*" & indexf & "*" & image,index )
	downloader.Download( image)
End Sub
Sub main_download_product(indexf,image)
	Dim downloader As HttpJob
	downloader.Initialize("downloadimgproduct*" & indexf & "*" & image,index )
	downloader.Download(  image)
End Sub
Sub InitPanel(pnl As Panel,BorderWidth As Float, FillColor As Int, BorderColor As Int)
	Dim Rec As Rect
	Dim Canvas1 As Canvas
	Dim BorderWidth_2 As Float
	BorderWidth_2=BorderWidth/2
	Rec.Initialize(BorderWidth_2,BorderWidth_2,pnl.Width-BorderWidth_2,pnl.Height-BorderWidth_2)
	Canvas1.Initialize(pnl)
	Canvas1.DrawRect(Rec,FillColor,True,FillColor)
	Canvas1.DrawRect(Rec,BorderColor,False,BorderWidth)
End Sub
Sub programsharepost(id As String)
	Dim text As String
	text = "فروشگاه لوازم کودک و سیسمونی مانینی " & CRLF & share_link & id
	Dim share As Intent
	share.Initialize(share.ACTION_SEND,"")
	share.SetType("text/plain")
	share.PutExtra("android.intent.extra.TEXT", text)
	share.WrapAsIntentChooser("اشتراک محصول")
	StartActivity(share)
End Sub