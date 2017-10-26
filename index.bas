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
	Dim index_retrive_list As List
End Sub
Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer.
	Activity.LoadLayout("index")
	extra.index_ob_olaviyat(0) = 1
	'index_ScrollView.Panel.LoadLayout("indexdata")
 
 
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
			Dim parser As JSONParser
			parser.Initialize(job.GetString)
			index_retrive_list= parser.NextArray
			
			Log(extra.image_address &  index_retrive_list.Get(2))
			For  i = 1 To 11
				Select extra.index_ob_olaviyat(i-1)
					Case 1
						Dim x As Int = Rnd(1,5)
						If x = 1 Then 
							index_draw("larg",i)
							'Log("larg")
						End If
						If x = 2 Then
							index_draw("medium",i)
							'Log("medium")
						End If
						If x = 3 Then
							index_draw("small",i)
							'Log("small")
						End If
						If x = 4 Then
							extra.index_ob_olaviyat(i-1) = 4
							index_draw("medium",i)
							'Log("medium 4 ")
						End If
					Case 22
						index_draw("small",i)
					Case 225
						index_draw("small",i)
						'Log("225")
					Case 224
						index_draw("small",i)
					Case 223
						index_draw("small",i)
					Case 222
						index_draw("small",i)
					Case 221
						index_draw("small",i)
					Case 11
						Dim x As Int = Rnd(1,3)
						If x = 1 Then  index_draw("small",i)
						If x = 2 Then  index_draw("medium",i)
					Case 111
						index_draw("small",i)
				End Select
			Next
		End If
	End If
End Sub
Sub index_draw(size As String,flag)
	Dim panel As Panel
	panel.Initialize("panel")
	Dim space As Int = 2dip
	Dim padding_space As Int = 2dip
	If size="larg" Then 
		Dim left_draw As Int = padding_space
		Dim width_draw As Int = 100%x - left_draw
		Dim shadow_space As Int = 5dip
		extra.index_ob_olaviyat(flag) = 1
		extra.index_ob_top_cach =  width_draw
		panel.Color = Colors.red
	End If
	If size="medium" Then
		Select extra.index_ob_olaviyat(flag-1)
			Case 4
				Dim left_draw As Int = 33.2%x + padding_space
				Dim width_draw As Int = 66%x+ padding_space
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=224
				extra.index_ob_top_cach = 0
				panel.Color = Colors.Green
			Case 111
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top_cach =  width_draw
				panel.Color = Colors.Blue
			Case 11
				Dim left_draw As Int = 33.2%x + padding_space
				Dim width_draw As Int = 66%x+ padding_space
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=222
				extra.index_ob_top_cach = 0
				panel.Color = Colors.Green
			Case 1
				Dim left_draw As Int = padding_space
				Dim width_draw As Int = 66.4%x   
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=22
				extra.index_ob_top_cach = 0
				panel.Color = Colors.red
		End Select
	End If
	If size="small" Then
	
		Select extra.index_ob_olaviyat(flag-1)
			Case 225
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top 
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
				panel.Color = Colors.rgb(255, 51, 0) ' range
				'Log("225 ok")
			Case 224
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top
				extra.index_ob_olaviyat(flag)=225
				extra.index_ob_top_cach = width_draw
				panel.Color = Colors.rgb(0, 51, 0) ' green
			Case 223
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top 
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
				panel.Color = Colors.rgb(255, 255, 102) ' yellow
			Case 222
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top + 33.2%x
				extra.index_ob_olaviyat(flag)=223
				extra.index_ob_top_cach = 0
				panel.Color = Colors.red
			Case 221
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.23%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top + 33.2%x
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
				panel.Color = Colors.Green
			Case 22
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=221
				extra.index_ob_top_cach = 0
				panel.Color = Colors.Blue
			Case 111
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x 
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach =  width_draw
				panel.Color = Colors.rgb(255, 102, 255)
			Case 11
				Dim left_draw As Int = 33.3%x + padding_space
				Dim width_draw As Int = 33.2%x  
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=111
				extra.index_ob_top_cach = 0
				panel.Color = Colors.Black
			Case 1
				Dim left_draw As Int = padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=11
				extra.index_ob_top_cach = 0
				panel.Color = Colors.DarkGray
		End Select
	End If
	
	
	Dim cd As ColorDrawable
	'cd.Initialize (Colors.White,10dip)
	'panel.Background = cd
	index_ScrollView.Panel.AddView(panel,left_draw,extra.index_ob_top + space,width_draw-space,width_draw-space)
	extra.index_ob_top = extra.index_ob_top + extra.index_ob_top_cach
	index_ScrollView.Panel.Height = extra.index_ob_top  +space 
End Sub