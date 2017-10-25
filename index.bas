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
	index_draw("larg",1)
	index_draw("small",2)
	index_draw("small",3)
	index_draw("small",4)
	index_draw("medium",5)
	index_draw("small",6)
	index_draw("small",7)
	index_draw("small",8)
	index_draw("medium",9)
	index_draw("small",10)
	index_draw("larg",1)
	index_draw("small",2)
	index_draw("small",3)
	index_draw("small",4)
	index_draw("medium",5)
	index_draw("small",6)
	index_draw("small",7)
	index_draw("small",8)
	index_draw("medium",9)
	index_draw("small",10)
	index_draw("larg",1)
	index_draw("small",2)
	index_draw("small",3)
	index_draw("small",4)
	index_draw("medium",5)
	index_draw("small",6)
	index_draw("small",7)
	index_draw("small",8)
	index_draw("medium",9)
	index_draw("small",10)
	index_draw("larg",1)
	index_draw("small",2)
	index_draw("small",3)
	index_draw("small",4)
	index_draw("medium",5)
	index_draw("small",6)
	index_draw("small",7)
	index_draw("small",8)
	index_draw("medium",9)
	index_draw("small",10)
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
'	If job.Success = True Then 
'		If job.JobName = "load_indexjob" Then 
'			Dim parser As JSONParser
'			parser.Initialize(job.GetString)
'			Dim root As List = parser.NextArray
'			For Each colroot As Int In root
'				index_draw("small")
'			Next
'		End If
'	End If
End Sub
Sub index_draw(size As String,flag)
	Dim space As Int = 2dip
	Dim padding_space As Int = 2dip
	If size="larg" Then 
		Dim left_draw As Int = padding_space
		Dim width_draw As Int = 100%x - left_draw
		Dim shadow_space As Int = 5dip
		extra.index_ob_olaviyat(flag) = 1
		extra.index_ob_top_cach =  width_draw
	End If
	If size="medium" Then
		Select extra.index_ob_olaviyat(flag-1)
			Case 111
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top_cach =  width_draw
			Case 11
				Dim left_draw As Int = 33.2%x + padding_space
				Dim width_draw As Int = 66%x+ padding_space
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=222
				extra.index_ob_top_cach = 0
			Case 1
				Dim left_draw As Int = padding_space
				Dim width_draw As Int = 66%x   + padding_space
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=22
				extra.index_ob_top_cach = 0
		End Select
	End If
	If size="small" Then
		Select extra.index_ob_olaviyat(flag-1)
			Case 222
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top + 33.2%x
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
			Case 221
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top + 33.2%x
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
			Case 22
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=221
				extra.index_ob_top_cach = 0
			Case 111
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x 
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach =  width_draw
			Case 11
				Dim left_draw As Int = 33.2%x + padding_space
				Dim width_draw As Int = 33.2%x  
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=111
				extra.index_ob_top_cach = 0
			Case 1
				Dim left_draw As Int = padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=11
				extra.index_ob_top_cach = 0
		End Select
	End If
	Dim panel As Panel
	panel.Initialize("panel")
	panel.Color = Colors.DarkGray
	Dim cd As ColorDrawable
	'cd.Initialize (Colors.White,10dip)
	'panel.Background = cd
	index_ScrollView.Panel.AddView(panel,left_draw,extra.index_ob_top + space,width_draw-space,width_draw-space)
	extra.index_ob_top = extra.index_ob_top + extra.index_ob_top_cach
	index_ScrollView.Panel.Height = extra.index_ob_top  +space 
End Sub