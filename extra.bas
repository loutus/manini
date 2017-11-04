Type=StaticCode
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim api As String = "http://itrx.babapkg.ir/api.php"
	Dim image_address As String = "http://itrx.babapkg.ir/image/"
	Dim index_ob_top As Int = 0
	Dim index_ob_top_cach As Int =10dip
	Dim index_ob_olaviyat(1000) As Int 
	Dim index_ob_olaviyat_load As Int=1
	Dim product_id_toshow As Int
	Dim product_title As String
	Dim product_title_top As Int
	Dim product_title_flag As Boolean =False
	dim propertyjson as String
End Sub

Sub load_index()
	Log("load_index")
	Dim load_indexjob As HttpJob
	load_indexjob.Initialize("load_indexjob",index)
	load_indexjob.PostString(api,"op=index")
End Sub

Sub download_image(id,path,flag)
	Dim downloader As HttpJob
	downloader.Initialize("downloadimage*" & id & "$"  & path & "#" & flag,index )
	downloader.Download(image_address & path)
End Sub

Sub load_category_main()
	Dim load_category As HttpJob
	load_category.Initialize("load_category_main",product)
	load_category.PostString(api,"op=category")
End Sub


Sub main_download_image(name As String,image As String)
	Dim idownload As HttpJob
	idownload.Initialize("imageview*" & name & "*" & image,product)
	idownload.Download(image)
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