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
	dim product_id_toshow as Int
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

