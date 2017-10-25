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
	Dim index_ob_top As Int = 0
	Dim index_ob_top_cach As Int =10dip
	Dim index_ob_olaviyat(50) As Int 
End Sub

Sub load_index()
	Dim load_indexjob As HttpJob
	load_indexjob.Initialize("load_indexjob",index)
	load_indexjob.PostString(api,"op=index")
End Sub

