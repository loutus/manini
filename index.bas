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
	Dim navi As AHNavigationDrawer
	Dim pCantent As Panel
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private index_ScrollView As ScrollView
	Dim index_retrive_list As List
	Dim index_retrive_list_img As List
	Dim index_retrive_list_model As List
	Dim imgdrew(5000) As ImageView

	Dim product_ScrollView(500) As ScrollView
	Dim scrollview_lastproduct As HorizontalScrollView
	Dim category_hscrollview As HorizontalScrollView
	Dim lastproduct_ImageView(5000) As ImageView
	Dim headerproc(5000)  As Panel
	Dim headerproctxt(5000)  As Label
	Dim procimage(5000) As ImageView
	Dim categoryimg(50) As ImageView
	Dim cat_title(70) As Label
	Dim cat_title_line As Panel
	Dim Pagerc As AHViewPager
	Dim productheader As HorizontalScrollView
	Dim property_pnl As ScrollView
	Private appbg As Panel
	Private categorypnl As Panel
	Private categoryscroll As ScrollView
End Sub
Sub activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	
	If KeyCode= KeyCodes.KEYCODE_BACK Then
		If categorypnl.Visible =True Then 
				categorypnl.Visible = False
				Return True
			Else
					If extra.propertyjson = 1 Then
						property_pnl.RemoveView
						property_pnl.Visible = False
						extra.propertyjson = 0
						Return True
					Else If extra.flag_procpnl = 0 Then
						extra.procimg_flag = 0
						Return False
					Else
						extra.procimg_flag =extra.procimg_flag -  extra.procimg_count(extra.flag_procpnl-1) 'dispose imge location
						headerproc(extra.flag_procpnl-1).Visible = False
						product_ScrollView(extra.flag_procpnl-1).RemoveView
						product_ScrollView(extra.flag_procpnl-1).Visible = False
						extra.flag_procpnl = extra.flag_procpnl - 1
						Return True
					End If
		End If
	End If
End Sub
Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("index")
	navi.Initialize2("navi",Activity,50%x,navi.GRAVITY_RIGHT)
	navi.NavigationPanel.Color = Colors.ARGB(150,236,239,241)
	
	
	navi.NavigationPanel.LoadLayout("menu")
	navi.NavigationPanel.BringToFront
	pCantent.Initialize("")
	navi.ContentPanel.AddView(pCantent,0,0,100%x,100%y)

	pCantent.LoadLayout("index")
	pCantent.Color = Colors.RGB(234,234,234)
	'Do not forget to load the layout file created with the visual designer.
	'Activity.LoadLayout("index")
	

	'index_ScrollView.Panel.LoadLayout("indexdata")
 	If File.Exists(File.DirInternalCache & "/product","")=False Then 
		File.MakeDir(File.DirInternalCache,"product"	)
	End If


	Dim r As Reflector
	r.Target = index_ScrollView
	r.RunMethod2("setVerticalScrollBarEnabled", False, "java.lang.boolean")
	r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
	extra.load_index
	extra.index_ob_olaviyat(0) = 1
	extra.flag_procpnl = 0
	extra.propertyjson = 0
	
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
Sub Pagerc_PageChanged (Position As Int)

	cat_title_line.SetLayoutAnimated(300,	cat_title(Position).Left,cat_title_line.Top, 	cat_title(Position).Width,2dip)
	productheader.ScrollPosition=	cat_title(Position).Left
  
	Log(Position)
End Sub
Sub jobdone(job As HttpJob)

If job.Success = True Then
	Select job.JobName	 
			Case "getsubcategorydescription"
				Dim topseting As Int =0
				Dim pagecount As Int=-1
				Dim pagecountflag As Int=0
				Dim pageflag As Int=0
				Log(job.GetString)
				Dim pan(500) As Panel				
				Dim Containerc As AHPageContainer
			
				Containerc.Initialize
				Dim parser As JSONParser
				parser.Initialize(job.GetString)
				Dim root As List = parser.NextArray
				For Each colroot As Map In root
					Dim catname As String = colroot.Get("catname")
					Dim proname As String = colroot.Get("proname")
					Dim id As String = colroot.Get("id")
					If pagecount <> id Then
						topseting =0
						pageflag = pageflag + 1
						pagecount = id
						pan(pageflag).Initialize("")
						Containerc.AddPageAt(pan(pageflag), "Main", 0)
						pagecountflag= pagecountflag+ 1
					End If
					
					Dim cd As ColorDrawable
					cd.Initialize (Colors.rgb(230, 230, 230),6dip)
					Dim bg As Panel
					bg.Initialize("")
					bg.Background =cd
					pan(pageflag).AddView(bg,5dip,topseting,100%x-10dip,170dip)
					
					Dim cd As ColorDrawable
					cd.Initialize (Colors.rgb(242, 242, 242),6dip)
					Dim bg As Panel
					bg.Initialize("")
					bg.Background =cd
					pan(pageflag).AddView(bg,6dip,topseting+1dip,100%x-12dip,168dip)
					
					
					Dim procname As Label
					procname.Initialize("")
					procname.Text = proname
					procname.TextColor = Colors.Black
					procname.Gravity = Gravity.RIGHT
					pan(pageflag).AddView(procname,40%x,topseting+20dip,60%x-30dip,25dip)
					topseting = topseting + 175dip
					
				Next
				Pagerc.Initialize2(Containerc, "Pagerc")
				
				categoryscroll.Panel.AddView(Pagerc,0,55dip,100%x,100%y-55dip)
				
				Log(" page cound " & pagecountflag)
				categoryscroll.Panel.Height = 100%y
				categoryscroll.Panel.Color = Colors.White
 			
			Case "subcategory"
				Try
					Dim parser As JSONParser
					parser.Initialize(job.GetString)
					Dim root As List = parser.NextArray
					Dim leftset As Int=2dip
				
					categoryscroll.Panel.RemoveAllViews
					
					productheader.Initialize(100%x,"")
					productheader.Color=Colors.rgb(75, 231, 200)
					Dim yekanfont As Typeface
					yekanfont = Typeface.LoadFromAssets("yekan.ttf")
					categoryscroll.Panel.AddView(productheader,0,0,100%x,50dip)
					
					Dim r As Reflector
					r.Target = productheader
					r.RunMethod2("setHorizontalScrollBarEnabled", False, "java.lang.boolean")
					r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
					Dim flagt As Int = 0
					For Each colroot As Map In root
						Dim image As String = colroot.Get("image")
						Dim name As String = colroot.Get("name")
						Dim id As String = colroot.Get("id")
						
						cat_title(flagt).Initialize("cat_title")
						cat_title(flagt).Text = name
						cat_title(flagt).Tag = flagt
						cat_title(flagt).Gravity = Gravity.CENTER
						cat_title(flagt).TextColor = Colors.Black
						cat_title(flagt).Typeface = yekanfont
						cat_title(flagt).TextSize = 9dip
						
						productheader.Panel.AddView(cat_title(flagt),leftset,0,name.Length*12dip,50dip)
						leftset = leftset + (name.Length*12dip)  + 2dip
						flagt = flagt + 1
					Next
					
					
					cat_title_line.Initialize("")
					cat_title_line.Color = Colors.rgb(18, 135, 111)
					productheader.Panel.AddView(cat_title_line,0,48dip,cat_title(0).Width,2dip)
					productheader.Panel.Width = leftset + 2dip
					categoryscroll.Color = Colors.White
					
			Catch
				Log(LastException)
			End Try
			Case "categorypage"
				Dim flag As Int=0
				Dim gflag As Boolean=False
				Log(job.GetString)
				Dim topset As Int = 55dip
				Dim leftset As Int=5dip
				Dim parser As JSONParser
				parser.Initialize(job.GetString)
				Dim root As List = parser.NextArray
				For Each colroot As Map In root
					Dim image As String = colroot.Get("image")
					Dim name As String = colroot.Get("name")
					Dim id As String = colroot.Get("id")
					Dim filename As String
					filename = image.SubString(image.LastIndexOf("/")+1)
					Log(filename)
'					Dim lable As Label
'					lable.Initialize("")
'					lable.Text = name
'					'lable.Color = Colors.White
'					lable.TextColor = Colors.Black
'					lable.Typeface = Typeface.LoadFromAssets("yekan.ttf")
'					lable.Gravity = Gravity.RIGHT
'					lable.TextSize = 9dip
'					categoryscroll.Panel.AddView(lable,35%x,topset-10dip,65%x-15dip,25dip)
'					
'					

					categoryimg(flag).Initialize("categoryitem")
					If File.Exists(File.DirInternalCache,filename) = True Then
							categoryimg(flag).Bitmap = LoadBitmap(File.DirInternalCache,filename)
						Else
							categoryimg(flag).Bitmap = LoadBitmap(File.DirAssets,"main_defult_product.jpg")
						extra.main_download_categorypic(flag,extra.image_address_nc &image)
						Log("file " & extra.image_address_nc &image)
					End If
				
					categoryimg(flag).Gravity = Gravity.FILL
					categoryimg(flag).Tag = id
					If leftset=5dip Then 
						gflag=False
						categoryscroll.Panel.AddView(categoryimg(flag),leftset , topset-49dip,50%x-8dip,50%x-8dip)
						leftset = 50%x-8dip
						Else
						gflag=True
						categoryscroll.Panel.AddView(categoryimg(flag),leftset +10dip, topset-49dip,50%x-8dip,50%x-8dip)
						topset = topset + (50%x-8)
						leftset=5dip
					End If
				
'					
'					Dim pnl As Panel
'					pnl.Initialize("")
'					pnl.Color = Colors.rgb(191, 191, 191)
'					categoryscroll.Panel.AddView(pnl,0,topset+50dip,100%x,1dip)
'					
'					Dim pnlim As Panel
'					pnlim.Initialize("categoryitem")
'					pnlim.Tag=id
'					categoryscroll.Panel.AddView(pnlim,0,topset-50dip,100%x,100dip)
					flag = flag + 1
				'	topset = topset + 100dip
				Next
				If gflag=False Then topset = topset  + 50%x-8 
				categoryscroll.Panel.Height = topset 
		Case "load_indexjob"
				load_indexjob(job,True)
				Case Else
				Try
					If job.JobName.SubString2(0,13)="downloadimage" Then
						Dim id As String = job.JobName.SubString2(job.JobName.IndexOf("*")+1,job.JobName.IndexOf("$"))
						Dim img As String = job.JobName.SubString2(job.JobName.LastIndexOf("/")+1,job.JobName.LastIndexOf("#"))
						Dim flag1 As String = job.JobName.SubString2(job.JobName.LastIndexOf("#")+1,job.JobName.Length)
						Dim OutStream As OutputStream
						OutStream = File.OpenOutput(File.DirInternalCache  & "/product"  ,  img, False)
						File.Copy2(job.GetInputStream,OutStream)
						OutStream.Close
						imgdrew(flag1).Bitmap = LoadBitmapSample(File.DirInternalCache  & "/product"  ,  img,200dip,200dip)
					End If
				Catch
					'Log(LastException)
				End Try
				
				If job.JobName.SubString2(0,7) = "picproc" Then
					Log(job.GetString)
					
					Dim pan(20) As Panel
					Dim flagerpic,flagpanel As Int
					flagpanel = 0
					flagerpic =  job.JobName.SubString(7)
					
					Dim Container As AHPageContainer
					Dim Pager As AHViewPager
					Container.Initialize
					
					Try
						
				
					Dim parser As JSONParser
					parser.Initialize( job.GetString)
					Dim root As List = parser.NextArray
					For Each colroot As Map In root
						Dim image As String = colroot.Get("image")
							image = extra.image_address & image.Replace(".jpg","-600x600.jpg")
							Dim filename  As String = image.SubString(image.LastIndexOf("/")+1)
							pan(flagpanel).Initialize("")
							
							procimage(extra.procimg_flag).Initialize("")
						If File.Exists(File.DirInternalCache,filename)= True Then 
							
								procimage(extra.procimg_flag).Bitmap = LoadBitmap(File.DirInternalCache,filename)
							Else
								extra.main_download_product(extra.procimg_flag, image)
								procimage(extra.procimg_flag).Bitmap = LoadBitmap(File.DirAssets,"main_defult_product.jpg")
						End If
							procimage(extra.procimg_flag).Gravity = Gravity.FILL
							pan(flagpanel).AddView(procimage(extra.procimg_flag),25%x,0,50%x,50%x)
							Container.AddPageAt(pan(flagpanel), "Main", 0)
							flagpanel = flagpanel + 1
							extra.procimg_flag = extra.procimg_flag + 1
					Next
						Log("indexer " & extra.procimg_flag )
						extra.procimg_count(extra.flag_procpnl) = flagpanel
						
						Pager.Initialize2(Container, "Pager")
						product_ScrollView(flagerpic).Panel.AddView(Pager,0,55dip,100%x,50%x)
						Catch
							Log(LastException)
						End Try
				End If
				Try
					
				Catch
					Log(LastException)
				End Try
				Try
					If job.JobName.SubString2(0,8) = "textproc" Then
						Dim flager As Int
						flager =  job.JobName.SubString(8)
						Dim s As String
						s = job.GetString.Replace("&lt;","").Replace("p&gt;","").Replace("br&gt;","").Replace("/&lt;","").Replace("/p&gt;","").Replace("/br&gt;","").Replace("p style=&quot;text-align: justify; &quot;&gt;","")
						s =s.Replace("amp;","").Replace("nbsp;","").Replace("/null","")
						
						Dim RTLJustify1 As Label
						RTLJustify1.Initialize("")
						RTLJustify1.Text = s
						RTLJustify1.Typeface = Typeface.LoadFromAssets("yekan.ttf")
 
						RTLJustify1.TextColor = Colors.rgb(89, 89, 89)
						RTLJustify1.Gravity = Gravity.FILL
						RTLJustify1.TextSize = 10dip
	
						Dim Obj1 As Reflector
						Obj1.Target = RTLJustify1
						Obj1.RunMethod3("setLineSpacing", 1, "java.lang.float",1.5, "java.lang.float")
						product_ScrollView(flager).Panel.AddView(RTLJustify1,15dip+15dip,50%x+164.5dip + 48dip+310dip,100%x-60dip,185dip)
						
					End If
				Catch
					Log("erro textproc job")
				End Try
				
				Try
					If job.JobName.SubString2(0,18)="downloadimgproduct" Then
					
						Dim index As Int
						Dim name As String
						index = job.JobName.SubString2(job.JobName.IndexOf("*")+1,job.JobName.LastIndexOf("*"))
						name = job.JobName.SubString(job.JobName.LastIndexOf("/")+1)
						Log(index)
						Log(name)
						Dim OutStream As OutputStream
						OutStream = File.OpenOutput(File.DirInternalCache, name, False)
						File.Copy2(job.GetInputStream,OutStream)
						OutStream.Close
						procimage(index).Bitmap = LoadBitmap(File.DirInternalCache, name)
					End If
				Catch
					Log("error downloadimgproduct-- job")
				End Try
				
				Try
					If job.JobName.SubString2(0,19)="downloadimgcategory" Then
						Dim index As Int
						Dim name As String
						index = job.JobName.SubString2(job.JobName.IndexOf("*")+1,job.JobName.LastIndexOf("*"))
						name = job.JobName.SubString(job.JobName.LastIndexOf("/")+1)
						Dim OutStream As OutputStream
						OutStream = File.OpenOutput(File.DirInternalCache, name, False)
						File.Copy2(job.GetInputStream,OutStream)
						OutStream.Close
						categoryimg(index).Bitmap = LoadBitmap(File.DirInternalCache, name)
					End If
				Catch
					Log("error downloadimglastproc job")
				End Try
				Try
					If job.JobName.SubString2(0,19)="downloadimglastproc" Then
						Dim index As Int
						Dim name As String
						index = job.JobName.SubString2(job.JobName.IndexOf("*")+1,job.JobName.LastIndexOf("*"))
						name = job.JobName.SubString(job.JobName.LastIndexOf("/")+1)
						Dim OutStream As OutputStream
						OutStream = File.OpenOutput(File.DirInternalCache, name, False)
						File.Copy2(job.GetInputStream,OutStream)
						OutStream.Close
						lastproduct_ImageView(index).Bitmap = LoadBitmap(File.DirInternalCache, name)
					End If
				Catch
					Log("error downloadimglastproc job")
				End Try
				Try
					
					If job.JobName.SubString2(0,8)="nameproc" Then
					
					
							Dim flager As Int
						flager =  job.JobName.SubString(8)

						Dim d1 As String
						d1 = job.GetString.SubString2(0,job.GetString.IndexOf("^"))
				
	
						Dim propert As Label
						propert.Initialize("propert")
						propert.TextColor = Colors.Black
						propert.Gravity = Gravity.CENTER
						propert.Typeface = Typeface.LoadFromAssets("yekan.ttf")
						propert.Text = "مشخصات"
						propert.Tag = job.GetString.SubString2(job.GetString.IndexOf("^")+1,job.GetString.Length) & "#" & flager
						product_ScrollView(flager).Panel.AddView(propert,15dip,50%x+164dip,100%x-30dip,40dip)
					 
					
						Dim s As String
						Dim parser As JSONParser
					 
						parser.Initialize(d1)
						Dim root As List = parser.NextArray
						For Each colroot As Map In root
							Dim price As String = colroot.Get("price")
							Dim name As String = colroot.Get("name")
							Dim model As String = colroot.Get("model")
						
							'							lbl_titlen.Text =  model
							'							header_title.Text = name
							'							extra.product_title = name
							'							total.Text = price.SubString2(0,price.IndexOf("."))   & " " & "تومان"
						Next
						
						Dim lbl_titlen As Label
						lbl_titlen.Initialize("")
						lbl_titlen.Text = model
						lbl_titlen.Typeface = Typeface.LoadFromAssets("yekan.ttf")
						lbl_titlen.TextSize = 8dip
						lbl_titlen.Gravity = Gravity.RIGHT
						lbl_titlen.TextColor = Colors.rgb(169, 169, 169)
						product_ScrollView(flager).Panel.AddView(lbl_titlen,0,50%x+120dip,95%x,30dip)
						
						
	
						Dim model As String = colroot.Get("model")
						Dim lbl_title As Label
						lbl_title.Initialize("")
						lbl_title.Text =  ""
						extra.product_title =  ""
						lbl_title.Typeface = Typeface.LoadFromAssets("yekan.ttf")
						lbl_title.TextSize = 11dip
						lbl_title.Gravity = Gravity.RIGHT
						lbl_title.TextColor = Colors.Black
						product_ScrollView(flager).Panel.AddView(lbl_title,0,50%x+85dip,95%x,30dip)
						lbl_title.Text = name
						
						headerproctxt(flager).Text =name
						Dim total As Label
						total.Initialize("")
						total.TextColor = Colors.rgb(102, 187, 106)
						total.Gravity = Gravity.LEFT
						total.Typeface = Typeface.LoadFromAssets("yekan.ttf")
						total.Text = price.SubString2(0,price.LastIndexOf(".")) &  " " & "تومان"
						total.TextSize = 13dip
						product_ScrollView(flager).Panel.AddView(total,32dip,50%x+178dip + 48dip,60%x,40dip)	
'						
'						
'						Dim pic_sheare As ImageView
'						pic_sheare.Initialize("sharepost")
'						pic_sheare.Gravity = Gravity.FILL
'						pic_sheare.Tag = 
'						pic_sheare.Bitmap = LoadBitmap(File.DirAssets,"sharing-interface.png")
'						
						
						
					End If
				Catch
					Log("erro nameproc job")
				End Try
				
				Try
					If job.JobName.SubString2(0,12) ="loadcategory" Then
						Dim flager As Int
						flager =  job.JobName.SubString(12)
						
						category_hscrollview.Initialize(100%x,"")
						category_hscrollview.Panel.Height = 50dip
						product_ScrollView(flager).Panel.AddView(category_hscrollview,0,50%x+164.5dip + 48dip+310dip+220dip +50dip,100%x,50dip)
						
							Dim left As Int=15dip
							Dim parser As JSONParser
							parser.Initialize(job.GetString)
							Dim root As List = parser.NextArray
							For Each colroot As Map In root
								Dim id As String = colroot.Get("id")
								Dim text As String = colroot.Get("name")
								Dim lable As Label
								lable.Initialize("lable")
								lable.Color = Colors.rgb(102, 187, 106)
								lable.TextColor = Colors.White
								lable.Gravity = Gravity.CENTER
								lable.Typeface = Typeface.LoadFromAssets("yekan.ttf")
								lable.TextSize = "20"
								lable.Text = text
								category_hscrollview.Panel.AddView(lable,left,2.5dip,(text.Length * 30 ),45dip)
								left =( text.Length * 30  ) + left +8dip
							Next
							Dim r As Reflector
							r.Target = category_hscrollview
							r.RunMethod2("setHorizontalScrollBarEnabled", False, "java.lang.boolean")
							r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
							'category_panel.Width = left + 8dip
							category_hscrollview.Panel.Width = left
							category_hscrollview.FullScroll(True)
							category_hscrollview.ScrollPosition = 50dip
					End If
					If job.JobName.SubString2(0,11) ="lastproduct" Then
						Dim flager As Int
						flager =  job.JobName.SubString(11)
						
						scrollview_lastproduct.Initialize(100%x,"")
						scrollview_lastproduct.Panel.Height = 450dip
						product_ScrollView(flager).Panel.AddView(scrollview_lastproduct,0,50%x+164.5dip + 48dip+310dip+220dip +50dip + 60dip ,100%x,450dip)
							
						Dim left As Int=15dip
						Dim parser As JSONParser
						parser.Initialize(job.GetString)
						Dim indexf As Int = flager &  1
						Dim root As List = parser.NextArray
						For Each colroot As Map In root	
							Dim image As String = colroot.Get("image")
							Dim price As String = colroot.Get("price")
							Dim model As String = colroot.Get("model")
							Dim name As String = colroot.Get("name")
							Dim id As String = colroot.Get("id")
							Dim panel As Panel
							panel.Initialize("lastproduct_panel")
							panel.Tag = id
							panel.Color = Colors.White
							scrollview_lastproduct.Panel.AddView(panel,left,0,160dip,225dip-2dip)
							extra.InitPanel(panel,1dip,Colors.White,Colors.rgb(207, 204, 204))
							Dim lable As Label
							lable.Initialize("lable")
							lable.Color = Colors.White
							lable.TextColor = Colors.rgb(65, 65, 65)
							lable.Gravity = Gravity.RIGHT
							lable.Typeface = Typeface.LoadFromAssets("yekan.ttf")
							lable.TextSize = "16"
							lable.Text = name
							scrollview_lastproduct.Panel.AddView(lable,left+2dip,170dip,140dip,20dip)
				
							Dim lable2 As Label
							lable2.Initialize("lable2")
							lable2.Color = Colors.White
							lable2.TextColor = Colors.rgb(102, 187, 106)
							lable2.Typeface = Typeface.DEFAULT_BOLD
							lable2.Gravity = Gravity.LEFT
							lable2.Gravity = Gravity.CENTER_VERTICAL
							lable2.Typeface = Typeface.LoadFromAssets("yekan.ttf")
							lable2.TextSize = "16"
							lable2.Text = price
							scrollview_lastproduct.Panel.AddView(lable2,left+15dip,195dip,125dip,24dip)
							image= image.Replace(".jpg","-600x600.jpg").Replace("/catalog/categoty/","/cache/catalog/categoty/")
							lastproduct_ImageView(indexf).Initialize("lastproduct_ImageView")
							lastproduct_ImageView(indexf).Tag = id
							Dim filename As String=image.SubString2(image.LastIndexOf("/")+1,image.Length)
							
							If File.Exists(File.DirInternalCache, filename)=True Then
								Try
									lastproduct_ImageView(indexf).Bitmap = LoadBitmap(File.DirInternalCache, filename)
								Catch
									Log("erro 	lastproduct_ImageView(indexf).")
								End Try
								
							Else
								lastproduct_ImageView(indexf).Bitmap = LoadBitmap(File.DirAssets,"fileset/main_defult_product.jpg")
								extra.main_download_lastproduct(indexf,image)
							End If
							
							lastproduct_ImageView(indexf).Gravity = Gravity.FILL
							scrollview_lastproduct.Panel.AddView(lastproduct_ImageView(indexf),left+1dip,1dip,157dip-2dip,157dip-2dip)
'				
							Dim CanvasGraph As Canvas
							CanvasGraph.Initialize(panel)
							'CanvasGraph.DrawColor(Colors.White)
							CanvasGraph.DrawLine(0,140dip,160dip,140dip,Colors.rgb(207, 204, 204),1dip)
							left = left + 170dip
							indexf= indexf + 1
						Next
						Dim r As Reflector
						r.Target = scrollview_lastproduct
						r.RunMethod2("setHorizontalScrollBarEnabled", False, "java.lang.boolean")
						r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
						scrollview_lastproduct.Panel.Width = left
						product_ScrollView(flager).Panel.Height = 50%x+164.5dip + 48dip+310dip+220dip +50dip+295dip
						
					End If
		Catch
					Log("eroro lastproduct job")
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
	lbl.Color = Colors.rgb(15, 112, 93)
	lbl.Gravity = Gravity.FILL
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
	
	'Dim cd As ColorDrawable
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
	Log(imgdre.Tag)
	extra.product_id_toshow = imgdre.Tag
	
	product_ScrollView(extra.flag_procpnl).Initialize2(500,"product_ScrollView")
	product_ScrollView(extra.flag_procpnl).Color = Colors.rgb(250, 250, 250)
	Activity.AddView(product_ScrollView(extra.flag_procpnl),0,0,100%x,100%y)
	loadroc(extra.flag_procpnl)
	extra.flag_procpnl = extra.flag_procpnl+ 1
	'Activity_Create(True)
End Sub

Sub loadroc(flag As Int)
	
	
	headerproc(flag).Initialize("")
	headerproc(flag).Color = Colors.rgb(26, 188, 156)
	Activity.AddView(headerproc(flag),0,0,100%x,55dip)
	
	
	headerproctxt(flag).Initialize("")
	headerproctxt(flag).Gravity = Bit.Or(Gravity.CENTER_VERTICAL,Gravity.RIGHT)
	headerproctxt(flag).Typeface = Typeface.LoadFromAssets("yekan.ttf")
	headerproctxt(flag).TextSize = 8dip
	headerproctxt(flag).Text = "در حال بارگزاری"
	headerproctxt(flag).TextColor = Colors.White
	headerproc(flag).AddView(headerproctxt(flag),0,5dip,95%x,50dip)
	
	
	
	Dim download As HttpJob
	download.Initialize("nameproc" & flag,Me)
	download.PostString(extra.api,"op=product&id=" & extra.product_id_toshow)
	
	
	Dim downloadtext As HttpJob
	downloadtext.Initialize("textproc"  & flag ,Me)
	downloadtext.PostString(extra.api,"op=productdescription&id=" & extra.product_id_toshow)
	
	Dim downloadpic As HttpJob
	downloadpic.Initialize("picproc" & flag,Me)
	downloadpic.PostString(extra.api,"op=productpics&id=" & extra.product_id_toshow)
	
	
	Dim jober As HttpJob
	jober.Initialize("lastproduct"  & flag ,Me)
	jober.PostString(extra.api,"op=lastproduct")
	
	Dim load_category As HttpJob
	load_category.Initialize("loadcategory" & flag,Me)
	load_category.PostString(extra.api,"op=category")
	
	Dim moretext_data As String
	Dim pnl As Panel
	pnl.Initialize("")
	pnl.Color = Colors.rgb(250, 250, 250)
	product_ScrollView(flag).Panel.AddView(pnl,0,50%x+55dip,100%x,100dip)

	Dim pnl2 As Panel
	pnl2.Initialize("")
	pnl2.Color = Colors.White
	product_ScrollView(flag).Panel.AddView(pnl2,15dip,50%x+163dip,100%x-30dip,40dip)
	extra.InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204))

	Dim pnl3 As Panel
	pnl3.Initialize("")
	pnl3.Color = Colors.White
	product_ScrollView(flag).Panel.AddView(pnl3,15dip,50%x+164.5dip + 48dip,100%x-30dip,280dip)
	extra.InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204))
	
	Dim pnl4_moshakhasat As Panel
	pnl4_moshakhasat.Initialize("")
	pnl4_moshakhasat.Color = Colors.White
	product_ScrollView(flag).Panel.AddView(pnl4_moshakhasat,15dip,50%x+164.5dip + 48dip+289dip,100%x-30dip,280dip)
	extra.InitPanel(pnl4_moshakhasat,2,1,Colors.rgb(204, 204, 204))
	
	moretext_data = "در حال بارگذاری"



	
	Dim pnl4_line As Panel
	pnl4_line.Initialize("")
	pnl4_line.Color = Colors.rgb(179, 179, 179)
	product_ScrollView(flag).Panel.AddView(pnl4_line,30dip,50%x+164.5dip + 48dip+310dip+185dip +15dip,100%x-60dip,1dip)
	

	
	
	Dim moretext As Label
	moretext.Initialize("moretext")
	moretext.TextColor = Colors.rgb(140, 140, 140)
	moretext.Gravity = Gravity.CENTER
	moretext.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	moretext.TextSize = 10dip
	moretext.Text = "ادامه مطلب"
	product_ScrollView(flag).Panel.AddView(moretext,15dip,50%x+164.5dip + 48dip+310dip+185dip +15dip,100%x-30dip,50dip)
	
	Dim btn As Panel
	btn.Initialize("")
	btn.Color = Colors.rgb(102, 187, 106)
	
	Dim cd As ColorDrawable
	cd.Initialize(Colors.rgb(102, 187, 106), 5dip)
	btn.Background = cd
	product_ScrollView(flag).Panel.AddView(btn,50dip,50%x+420dip,100%x-100dip,50dip)
	
	Dim buy As Label
	buy.Initialize("")
	buy.TextColor = Colors.White
	buy.Gravity = Gravity.CENTER
	buy.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	buy.TextSize = 12dip
	buy.Text = "افزودن به سبد خرید"
	product_ScrollView(flag).Panel.AddView(buy,50dip,50%x+420dip,65%x,50dip)
	
	Dim buyicon As Label
	buyicon.Initialize("")
	Private FAe As FontAwesome
	FAe.Initialize
	buyicon.Gravity = Gravity.CENTER_VERTICAL
	buyicon.Typeface = Typeface.FONTAWESOME
	buyicon.TextColor = Colors.White
	buyicon.TextSize = 13dip
	buyicon.Text = FAe.GetFontAwesomeIconByName("fa-cart-plus")
	product_ScrollView(flag).Panel.AddView(buyicon,70%x,50%x+420dip,20%x,50dip)
	

	

	Private FA As FontAwesome
	FA.Initialize
	Dim properticon As Label
	properticon.Initialize("")
	properticon.TextColor = Colors.Black
	
	properticon.Gravity = Gravity.CENTER_VERTICAL
	properticon.Typeface = Typeface.FONTAWESOME
	properticon.Text = FA.GetFontAwesomeIconByName("fa-calendar-o")
	product_ScrollView(flag).Panel.AddView(properticon,50%x+10dip+20dip,50%x+164dip,100%x-30dip,35dip)
	
	Dim pic_sheare As ImageView
	pic_sheare.Initialize("sharepost")
	pic_sheare.Gravity = Gravity.FILL
	pic_sheare.Tag = extra.product_id_toshow
	pic_sheare.Bitmap = LoadBitmap(File.DirAssets,"sharing-interface.png")
	
	Dim pic_like As ImageView
	pic_like.Initialize("")
	pic_like.Gravity = Gravity.FILL
	pic_like.Bitmap = LoadBitmap(File.DirAssets,"like.png")
	
	
	Dim color As Label
	color.Initialize("")
	color.TextColor = Colors.rgb(140, 140, 140)
	color.Gravity = Gravity.RIGHT
	color.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	color.Text = "رنگ"
	color.TextSize = 9dip
	product_ScrollView(flag).Panel.AddView(color,0,50%x+270dip,100%x-35dip,80dip)
	
	
	Dim garanti As Label
	garanti.Initialize("")
	garanti.TextColor = Colors.rgb(140, 140, 140)
	garanti.Gravity = Gravity.RIGHT
	garanti.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	garanti.Text = "گارانتی"
	garanti.TextSize = 9dip
	product_ScrollView(flag).Panel.AddView(garanti,0,50%x+310dip,100%x-35dip,80dip)

	
	Dim saler As Label
	saler.Initialize("")
	saler.TextColor = Colors.rgb(140, 140, 140)
	saler.Gravity = Gravity.RIGHT
	saler.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	saler.Text = "فروشنده"
	saler.TextSize = 9dip
	
	
	Dim header_title As Label
	header_title.Initialize("")

	header_title.Gravity = Gravity.RIGHT
	header_title.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	header_title.TextColor = Colors.red
	header_title.TextSize = 11dip
	header_title.Visible = False
	'product_header.AddView(header_title,0,5dip,95%x,45dip)

	product_ScrollView(flag).Panel.AddView(saler,0,50%x+350dip,100%x-35dip,80dip)
	product_ScrollView(flag).Panel.AddView(pic_sheare,30dip,50%x+70dip,18dip,18dip)
	product_ScrollView(flag).Panel.AddView(pic_like,80dip,50%x+70dip,18dip,18dip)
	'product_header.BringToFront
	product_ScrollView(flag).Panel.Height = 2125
End Sub

Sub lastproduct_ImageView_click()
	Dim img As ImageView
	img = Sender
	extra.product_id_toshow = img.Tag

	product_ScrollView(extra.flag_procpnl).Initialize(500)
	product_ScrollView(extra.flag_procpnl).Color = Colors.rgb(250, 250, 250)
	Activity.AddView(product_ScrollView(extra.flag_procpnl),0,0,100%x,100%y)
	loadroc(extra.flag_procpnl)
	extra.flag_procpnl = extra.flag_procpnl + 1
End Sub

Sub propert_click()
	Dim propert As Label
	Dim temp As String
	extra.propertyjson = 1
	propert = Sender
	temp = propert.Tag
	temp =   temp.SubString2(0,  temp.LastIndexOf("#"))
	Log(temp)
	property_pnl.Initialize(100%y)
	property_pnl.Color = Colors.rgb(250, 250, 250)
	Activity.AddView(property_pnl,0,0,100%x,100%y)
	Dim topset As Int = 5dip
	Try
		Dim parser As JSONParser
		parser.Initialize(temp)
		Dim root As List = parser.NextArray
		For Each colroot As Map In root
			Dim name As String = colroot.Get("name")
			Dim text As String = colroot.Get("text")
			Dim grouping As String = colroot.Get("grouping")
				
			Dim lblnodata As Label
			lblnodata.Initialize("")
			lblnodata.Text =grouping
			lblnodata.Gravity = Gravity.CENTER
			lblnodata.TextColor = Colors.rgb(38, 38, 38)
			lblnodata.color = Colors.rgb(217, 217, 217)
			lblnodata.TextSize = 11dip
			lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
			property_pnl.Panel.AddView(lblnodata,5dip,topset,100%x-10dip,35dip)
				
			Dim lblnodata As Label
			lblnodata.Initialize("")
			lblnodata.Text ="  " & text
			lblnodata.Gravity = Gravity.RIGHT
			lblnodata.TextColor = Colors.rgb(115, 115, 115)
			lblnodata.color = Colors.rgb(242, 242, 242)
			lblnodata.TextSize = 9dip
			lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
			property_pnl.Panel.AddView(lblnodata,5dip,topset+30dip,70%x-5dip,30dip)
				
			Dim lblnodata As Label
			lblnodata.Initialize("")
			lblnodata.Text ="  " & name
			lblnodata.Gravity = Gravity.RIGHT
			lblnodata.TextColor = Colors.rgb(115, 115, 115)
			lblnodata.color = Colors.rgb(242, 242, 242)
			lblnodata.TextSize = 10dip
			lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
			property_pnl.Panel.AddView(lblnodata,70%x,topset+30dip,30%x-5dip,30dip)

			topset = topset + 65dip
				
			property_pnl.Panel.Height = topset
		Next
	Catch
		createnon
	End Try
End Sub
Sub createnon()
	Dim lblnodata As Label
	lblnodata.Initialize("")
	lblnodata.Text ="هیچ مشخصه ای وجود ندارد"
	lblnodata.Gravity = Gravity.CENTER
	lblnodata.TextColor = Colors.rgb(179, 179, 179)
	lblnodata.TextSize = 12dip
	lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	property_pnl.Panel.AddView(lblnodata,0,0,100%x,50%x)
End Sub


Sub sharepost_click()
	Dim pic_sheare As ImageView
	pic_sheare = Sender
	extra.programsharepost(pic_sheare.tag)
End Sub



Sub menubtn_Click
	navi.OpenDrawer2(navi.GRAVITY_RIGHT)
End Sub

Sub menuporfrosh_Click
	
End Sub

Sub menutakhfif_Click
	
End Sub

Sub menufantast_Click
	
End Sub

Sub menunew_Click
	
End Sub

Sub menuhome_Click
	navi.CloseDrawer2(navi.GRAVITY_END)
	index_ScrollView.Panel.RemoveAllViews
	extra.load_index
	extra.index_ob_olaviyat(0) = 1
	extra.flag_procpnl = 0
	extra.index_ob_top = 0
	extra.index_ob_top_cach = 0
	extra.propertyjson = 0
End Sub

Sub menucategory_Click
	categoryscroll.Panel.RemoveAllViews
	navi.CloseDrawer2(navi.GRAVITY_END)
	categorypnl.Visible = True
	Dim  a As Animation
	a.InitializeAlpha("",0, 1)
	a.Duration = 500
	a.Start(categorypnl)
	 Dim download As HttpJob
	 download.Initialize("categorypage",Me)
	download.PostString(extra.api,"op=getcategory")
End Sub

Sub catmenubtn_Click
	navi.CloseDrawer2(navi.GRAVITY_END)
	Dim  a As Animation
	a.InitializeAlpha("animationexitcategory",1, 0)
	a.Duration = 500
	a.Start(categorypnl)
End Sub

Sub animationexitcategory_AnimationEnd
	categorypnl.Visible = False
End Sub

Sub categoryitem_Click
	Dim imgdre As ImageView
	imgdre = Sender
	Log(imgdre.Tag)
	
	Dim download As HttpJob
	download.Initialize("subcategory",Me)
	download.PostString(extra.api,"op=getsubcategory&id="& imgdre.Tag)


	Dim download2 As HttpJob
	download2.Initialize("getsubcategorydescription",Me)
	download2.PostString (extra.api,"op=getsubcategorydescription&id=" & imgdre.Tag)
	
End Sub

Sub cat_title_Click
	Dim target As Label
	target = Sender
	'cat_title_line.SetLayoutAnimated(300,target.Left,cat_title_line.Top, target.Width,2dip)
	productheader.ScrollPosition=target.Left 
	Pagerc.GotoPage(target.Tag,True)
End Sub