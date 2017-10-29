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
	Dim index_retrive_list_img As List
	Dim index_retrive_list_model As List
	Dim imgdrew(5000) As ImageView
End Sub
Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer.
	Activity.LoadLayout("index")
	extra.index_ob_olaviyat(0) = 1
	'index_ScrollView.Panel.LoadLayout("indexdata")
 	If File.Exists(File.DirInternalCache & "/product","")=False Then 
		File.MakeDir(File.DirInternalCache,"product"	)
	End If
 
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
Sub index_ScrollView_ScrollChanged(Position As Int)
	If (Position+1950) >= extra.index_ob_top Then
		 Log(extra.index_ob_olaviyat_load)
		extra.index_ob_olaviyat(extra.index_ob_olaviyat_load-1)=1
		Dim job As HttpJob	
		job.Initialize("",Me)
		load_indexjob(job,False)
	End If
	 
End Sub
Sub load_indexjob(job As HttpJob,create As Boolean)
	Try
	'Log(job.GetString)
	Dim lastpage As Int
	Dim i As Int
	If create=True Then
		Dim id As String = job.GetString.SubString2(0, job.GetString.IndexOf("*"))
		Dim img As String = job.GetString.SubString2(job.GetString.IndexOf("*")+3,job.GetString.IndexOf("$"))
		Dim modle As String = job.GetString.SubString2(job.GetString.IndexOf("$")+3,job.GetString.Length )
		'					Log(id)
		'					Log(img)
		'					Log(modle)
		Dim parser As JSONParser
		parser.Initialize(id)
		index_retrive_list= parser.NextArray
		parser.Initialize(img)
		index_retrive_list_img= parser.NextArray
		parser.Initialize(modle)
		index_retrive_list_model= parser.NextArray
		i = 1
		lastpage = 20
	Else
		i = extra.index_ob_olaviyat_load
		lastpage = extra.index_ob_olaviyat_load + 20
	End If
	
	do  While (i<lastpage Or  extra.index_ob_olaviyat(i-1)<>1)
					
		'Log("loop " & extra.index_ob_olaviyat(i-1))
		Select extra.index_ob_olaviyat(i-1)
			Case 1
				Dim x As Int = Rnd(1,5)
				'x = Rnd(1,5)
				If x = 1 Then
					index_draw("larg",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
					'Log("larg")index_retrive_list.Get(i-1)
				End If
				If x = 2 Then
					index_draw("medium",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
					'Log("medium")
				End If
				If x = 3 Then
					index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
					'Log("small")
				End If
				If x = 4 Then
					extra.index_ob_olaviyat(i-1) = 4
					index_draw("medium",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
					'Log("medium 4 ")
				End If
			Case 22
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
			Case 225
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
				'Log("225")
			Case 224
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
			Case 223
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
			Case 222
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
			Case 221
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
			Case 11
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
				'Dim x As Int = Rnd(1,3)
				'If x = 1 Then  index_draw("small",i)
				'If x = 2 Then  index_draw("medium",i)
			Case 111
				index_draw("small",i,index_retrive_list.Get(i),index_retrive_list_img.Get(i),index_retrive_list_model.Get(i))
		End Select
		i=i+1
	Loop

Catch
	Log(LastException)
End Try
End Sub
Sub jobdone(job As HttpJob)
If job.Success = True Then
	Select job.JobName	 
		Case "load_indexjob"
					load_indexjob(job,True)
				Case Else
				Try
					If job.JobName.SubString2(0,13)="downloadimage" Then
						Dim id As String = job.JobName.SubString2(job.JobName.IndexOf("*")+1,job.JobName.IndexOf("$"))
						Dim img As String = job.JobName.SubString2(job.JobName.LastIndexOf("/")+1,job.JobName.LastIndexOf("#"))
						Dim flag As String = job.JobName.SubString2(job.JobName.LastIndexOf("#")+1,job.JobName.Length)
						Dim OutStream As OutputStream
						OutStream = File.OpenOutput(File.DirInternalCache  & "/product"  ,  img, False)
						File.Copy2(job.GetInputStream,OutStream)
						OutStream.Close
						imgdrew(flag).Bitmap = LoadBitmapSample(File.DirInternalCache  & "/product"  ,  img,200dip,200dip)
						
					End If
			Catch
				'Log(LastException)
			End Try
		End Select		
	End If
End Sub
Sub index_draw(size As String,flag,id,img,model)
	extra.index_ob_olaviyat_load = flag
	Dim panel As Panel
	Dim lbl As Label
	Dim color As Int
	color =Colors.White
	panel.Initialize("panel")
	lbl.Initialize("lbl")
	lbl.Text = model
	lbl.TextColor = Colors.White
	lbl.Color = Colors.ARGB(140, 140, 140,100)
	Dim space As Int = 2dip
	Dim padding_space As Int = 2dip
	If size="larg" Then 
		Dim left_draw As Int = padding_space
		Dim width_draw As Int = 100%x - left_draw
		Dim shadow_space As Int = 5dip
		extra.index_ob_olaviyat(flag) = 1
		extra.index_ob_top_cach =  width_draw
		panel.Color = color
	End If
	If size="medium" Then
		Select extra.index_ob_olaviyat(flag-1)
			Case 4
				Dim left_draw As Int = 33.2%x + padding_space
				Dim width_draw As Int = 66%x+ padding_space
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=224
				extra.index_ob_top_cach = 0
				panel.Color = color
			Case 111
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top_cach =  width_draw
				panel.Color = color
			Case 11
				Dim left_draw As Int = 33.2%x + padding_space
				Dim width_draw As Int = 66%x+ padding_space
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=222
				extra.index_ob_top_cach = 0
				panel.Color = color
			Case 1
				Dim left_draw As Int = padding_space
				Dim width_draw As Int = 66.4%x   
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=22
				extra.index_ob_top_cach = 0
				panel.Color = color
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
				panel.Color = color
				'Log("225 ok")
			Case 224
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top
				extra.index_ob_olaviyat(flag)=225
				extra.index_ob_top_cach = width_draw
				panel.Color = color
			Case 223
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top 
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
				panel.Color = color
			Case 222
				Dim left_draw As Int =  padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top + 33.2%x
				extra.index_ob_olaviyat(flag)=223
				extra.index_ob_top_cach = 0
				panel.Color = color
			Case 221
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.23%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_top = extra.index_ob_top + 33.2%x
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach = width_draw
				panel.Color = color
			Case 22
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=221
				extra.index_ob_top_cach = 0
				panel.Color = color
			Case 111
				Dim left_draw As Int = 66.4%x  + padding_space
				Dim width_draw As Int = 33.2%x 
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=1
				extra.index_ob_top_cach =  width_draw
				panel.Color = color
			Case 11
				Dim left_draw As Int = 33.3%x + padding_space
				Dim width_draw As Int = 33.2%x  
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=111
				extra.index_ob_top_cach = 0
				panel.Color = color
			Case 1
				Dim left_draw As Int = padding_space
				Dim width_draw As Int = 33.2%x
				Dim shadow_space As Int = 15dip
				extra.index_ob_olaviyat(flag)=11
				extra.index_ob_top_cach = 0
				panel.Color = color
		End Select
	End If
	
	Dim cd As ColorDrawable
	'cd.Initialize (Colors.White,10dip)
	'panel.Background = cd
	lbl.Gravity = Gravity.FILL
	lbl.Gravity = Gravity.CENTER
	panel.Tag = id
	
	
	
	Dim imgfile As String = img.SubString2(img.LastIndexOf("/")+1,img.Length)
	'Log("file:" & imgfile)
	If File.Exists (File.DirInternalCache,"product/" & imgfile) = False Then
		
		imgdrew(flag).Initialize("imgdrew")
		imgdrew(flag).Bitmap = LoadBitmapSample(File.DirAssets,"fileset/main_defult_product.jpg",100dip,100dip)
		
		imgdrew(flag).Tag = id  
		imgdrew(flag).Gravity = Gravity.FILL
		  
		extra.download_image(id,img,flag)
		
	Else
		 
		imgdrew(flag).Initialize("imgdrew")
		imgdrew(flag).Bitmap = LoadBitmapSample(File.DirInternalCache, "product/" & imgfile,100dip,100dip)
		imgdrew(flag).Gravity = Gravity.FILL
		imgdrew(flag).Tag =  id 
		'Log("cach : " & img)
	End If
	
	index_ScrollView.Panel.AddView(panel,left_draw,extra.index_ob_top + space,width_draw-space,width_draw-space)
	
	index_ScrollView.Panel.AddView(imgdrew(flag),left_draw,extra.index_ob_top + space,width_draw-space,width_draw-space)
	
	index_ScrollView.Panel.AddView(lbl,left_draw,extra.index_ob_top + width_draw-25dip,width_draw-space,25dip)
	extra.index_ob_top = extra.index_ob_top + extra.index_ob_top_cach
	index_ScrollView.Panel.Height = extra.index_ob_top  +space 
End Sub

Sub imgdrew_click()
	Dim imgdre As ImageView
	imgdre = Sender
	extra.product_id_toshow = imgdre.Tag
	StartActivity(product)
End Sub

 