Type=Activity
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
End Sub
Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private index_ScrollView As ScrollView
End Sub
Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("index")
	'index_ScrollView.Panel.LoadLayout("indexdata")
	index_draw("medium")
	index_draw("medium")
	index_draw("larg")
	extra.load_index
	Dim r As Reflector
	r.Target = index_ScrollView
	r.RunMethod2("setVerticalScrollBarEnabled", False, "java.lang.boolean")
	r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
End Sub
Sub Activity_Resume
End Sub
Sub Activity_Pause (UserClosed As Boolean)
End Sub
Sub jobdone(job As HttpJob)
	If job.Success = True Then 
		If job.JobName = "load_indexjob" Then 
			Log(job.GetString)
		End If
	End If
End Sub
Sub index_draw(size As String)
	If size="larg" Then 
		Dim left_draw As Int = 10dip
		Dim top_draw As Int = 10dip
		Dim width_draw As Int = 100%x
		Dim space As Int = 5dip
		Dim shadow_space As Int = 5dip
	End If
	If size="medium" Then
		Dim left_draw As Int = 10dip
		Dim top_draw As Int = 10dip
		Dim width_draw As Int = 50%x
		Dim space As Int = 5dip
		Dim shadow_space As Int = 15dip
	End If
	
'	Dim shadow As ImageView
'	shadow.Initialize("shadow")
'	shadow.Bitmap = LoadBitmap(File.DirAssets,"shadowbox.png")
	'shadow.Gravity = Gravity.FILL
'	index_ScrollView.Panel.AddView(shadow,shadow_space,shadow_space,width_draw-shadow_space,width_draw-shadow_space)
	Dim panel As Panel
	panel.Initialize("panel")
	panel.Color = Colors.White
	Dim cd As ColorDrawable
	'cd.Initialize (Colors.White,10dip)
	'panel.Background = cd
	index_ScrollView.Panel.AddView(panel,left_draw,extra.index_ob_top +  top_draw,width_draw-space,width_draw-space)
	
	extra.index_ob_top =extra.index_ob_top +  width_draw
End Sub