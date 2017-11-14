Type=Activity
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
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
	Private product_ScrollView As ScrollView
	Private product_header As Panel
	Private product_imag1 As ImageView
	Private product_img2 As ImageView
	Dim scrollview_lastproduct As HorizontalScrollView
	Dim category_hscrollview As HorizontalScrollView
	Dim ImageView(20)  As ImageView
	Dim header_title As Label
	Dim lbl_title As Label
	Dim lbl_titlen As Label
	Dim tempint As Int
	Dim moretext As Label
	Dim RTLJustify1 As Label
	Dim moretext_data As String 
	Dim pnl4_moshakhasat As Panel
	Dim pnl4_line As Panel
	Dim total As Label
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("product")
	
	Dim downloadtext As HttpJob
	downloadtext.Initialize("textproc",Me)
	downloadtext.PostString(extra.api,"op=productdescription&id=" & extra.product_id_toshow)
	
	Dim download As HttpJob
	download.Initialize("nameproc",Me)
	download.PostString(extra.api,"op=product&id=" & extra.product_id_toshow)
	
	
	
	Dim pnlw As Panel
	pnlw.Initialize("")
	pnlw.Color = Colors.White
	product_ScrollView.Panel.AddView(pnlw,0,0,100%x,55dip)

	Dim Container As AHPageContainer
	Dim Pager As AHViewPager
	Container.Initialize
	Dim pan(5) As Panel

	pan(0).Initialize("")
	pan(1).Initialize("")
	pan(0).LoadLayout("product_img1")
	Container.AddPageAt(pan(0), "Main", 0)
				
	pan(1).LoadLayout("product_img2")
	Container.AddPageAt(pan(1), "Main2", 1)
 
	Pager.Initialize2(Container, "Pager")
	product_ScrollView.Panel.AddView(Pager,0,55dip,100%x,50%x+55dip)
	
	
	'*********************
	
	
	
	Dim pnl As Panel
	pnl.Initialize("")
	pnl.Color = Colors.rgb(250, 250, 250)
	product_ScrollView.Panel.AddView(pnl,0,50%x+55dip,100%x,100dip)

	Dim pnl2 As Panel
	pnl2.Initialize("")
	pnl2.Color = Colors.White
	product_ScrollView.Panel.AddView(pnl2,15dip,50%x+163dip,100%x-30dip,40dip)
	extra.InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204))

	Dim pnl3 As Panel
	pnl3.Initialize("")
	pnl3.Color = Colors.White
	product_ScrollView.Panel.AddView(pnl3,15dip,50%x+164.5dip + 48dip,100%x-30dip,280dip)
	extra.InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204))
	

	pnl4_moshakhasat.Initialize("")
	pnl4_moshakhasat.Color = Colors.White
	product_ScrollView.Panel.AddView(pnl4_moshakhasat,15dip,50%x+164.5dip + 48dip+289dip,100%x-30dip,280dip)
	extra.InitPanel(pnl4_moshakhasat,2,1,Colors.rgb(204, 204, 204))
	
	moretext_data = "در حال بارگذاری"

	RTLJustify1.Initialize("")
	RTLJustify1.Text = moretext_data
	RTLJustify1.Typeface = Typeface.LoadFromAssets("yekan.ttf")
 
	RTLJustify1.TextColor = Colors.rgb(89, 89, 89)
	RTLJustify1.Gravity = Gravity.FILL
	RTLJustify1.TextSize = 10dip
	
	Dim Obj1 As Reflector
	Obj1.Target = RTLJustify1
	Obj1.RunMethod3("setLineSpacing", 1, "java.lang.float",1.5, "java.lang.float")
	product_ScrollView.Panel.AddView(RTLJustify1,15dip+15dip,50%x+164.5dip + 48dip+310dip,100%x-60dip,185dip)
	

	pnl4_line.Initialize("")
	pnl4_line.Color = Colors.rgb(179, 179, 179)
	product_ScrollView.Panel.AddView(pnl4_line,30dip,50%x+164.5dip + 48dip+310dip+185dip +15dip,100%x-60dip,1dip)
	
	
	category_hscrollview.Initialize(100%x,"")
	category_hscrollview.Panel.Height = 50dip
	product_ScrollView.Panel.AddView(category_hscrollview,0,50%x+164.5dip + 48dip+310dip+220dip +50dip,100%x,50dip)
	
	
	scrollview_lastproduct.Initialize(100%x,"")
	scrollview_lastproduct.Panel.Height = 450dip
	product_ScrollView.Panel.AddView(scrollview_lastproduct,0,50%x+164.5dip + 48dip+310dip+220dip +50dip + 60dip ,100%x,450dip)
	
	
	
	moretext.Initialize("moretext")
	moretext.TextColor = Colors.rgb(140, 140, 140)
	moretext.Gravity = Gravity.CENTER
	moretext.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	moretext.TextSize = 10dip
	moretext.Text = "ادامه مطلب"
	product_ScrollView.Panel.AddView(moretext,15dip,50%x+164.5dip + 48dip+310dip+185dip +15dip,100%x-30dip,50dip)
	
	Dim btn As Panel
	btn.Initialize("")
	btn.Color = Colors.rgb(102, 187, 106)
	
	Dim cd As ColorDrawable
	cd.Initialize(Colors.rgb(102, 187, 106), 5dip)
	btn.Background = cd
	product_ScrollView.Panel.AddView(btn,50dip,50%x+420dip,100%x-100dip,50dip)
	
	Dim buy As Label
	buy.Initialize("")
	buy.TextColor = Colors.White
	buy.Gravity = Gravity.CENTER
	buy.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	buy.TextSize = 12dip
	buy.Text = "افزودن به سبد خرید"
	product_ScrollView.Panel.AddView(buy,50dip,50%x+420dip,65%x,50dip)
	
	Dim buyicon As Label
	buyicon.Initialize("")
	Private FAe As FontAwesome
	FAe.Initialize
	buyicon.Gravity = Gravity.CENTER_VERTICAL
	buyicon.Typeface = Typeface.FONTAWESOME
	buyicon.TextColor = Colors.White
	buyicon.TextSize = 13dip
	buyicon.Text = FAe.GetFontAwesomeIconByName("fa-cart-plus")
	product_ScrollView.Panel.AddView(buyicon,70%x,50%x+420dip,20%x,50dip)
	
		
	Dim propert As Label
	propert.Initialize("propert")
	propert.TextColor = Colors.Black
	propert.Gravity = Gravity.CENTER
	propert.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	propert.Text = "مشخصات"
	product_ScrollView.Panel.AddView(propert,15dip,50%x+164dip,100%x-30dip,40dip)
	
	

	total.Initialize("")
	total.TextColor = Colors.rgb(102, 187, 106)
	total.Gravity = Gravity.LEFT
	total.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	total.Text = ""
	total.TextSize = 13dip
	product_ScrollView.Panel.AddView(total,32dip,50%x+178dip + 48dip,60%x,40dip)
	
	
	
	
	Private FA As FontAwesome
	FA.Initialize
	Dim properticon As Label
	properticon.Initialize("")
	properticon.TextColor = Colors.Black
	
	properticon.Gravity = Gravity.CENTER_VERTICAL
	properticon.Typeface = Typeface.FONTAWESOME
	properticon.Text = FA.GetFontAwesomeIconByName("fa-calendar-o")
	product_ScrollView.Panel.AddView(properticon,50%x+10dip+20dip,50%x+164dip,100%x-30dip,35dip)
	
	
	
	lbl_title.Initialize("")
	lbl_title.Text =  ""
	extra.product_title =  ""
	lbl_title.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	lbl_title.TextSize = 11dip
	lbl_title.Gravity = Gravity.RIGHT
	lbl_title.TextColor = Colors.Black
	product_ScrollView.Panel.AddView(lbl_title,0,50%x+85dip,95%x,30dip)
	
	lbl_titlen.Initialize("")
	lbl_titlen.Text =  ""
	lbl_titlen.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	lbl_titlen.TextSize = 8dip
	lbl_titlen.Gravity = Gravity.RIGHT
	lbl_titlen.TextColor = Colors.rgb(169, 169, 169)
	product_ScrollView.Panel.AddView(lbl_titlen,0,50%x+120dip,95%x,30dip)
	
'	Dim pic_sheare As ImageView
'	pic_sheare.Initialize("")
'	pic_sheare.Gravity = Gravity.FILL
'	pic_sheare.Bitmap = LoadBitmap(File.DirAssets,"sharing-interface.png")
	
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
	product_ScrollView.Panel.AddView(color,0,50%x+270dip,100%x-35dip,80dip)
	
	
	Dim garanti As Label
	garanti.Initialize("")
	garanti.TextColor = Colors.rgb(140, 140, 140)
	garanti.Gravity = Gravity.RIGHT
	garanti.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	garanti.Text = "گارانتی"
	garanti.TextSize = 9dip
	product_ScrollView.Panel.AddView(garanti,0,50%x+310dip,100%x-35dip,80dip)

	
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
	product_header.AddView(header_title,0,5dip,95%x,45dip)

'	product_ScrollView.Panel.AddView(saler,0,50%x+350dip,100%x-35dip,80dip)
'	product_ScrollView.Panel.AddView(pic_sheare,30dip,50%x+70dip,18dip,18dip)
'	product_ScrollView.Panel.AddView(pic_like,80dip,50%x+70dip,18dip,18dip)
'	product_header.BringToFront
'	product_ScrollView.Panel.Height = 2125
	'.load_lastproduct_main
	'extra.load_category_main
End Sub
Sub Activity_KeyPress(KeyCode As Int) As Boolean
	'Log(product_ScrollView.ScrollPosition)
	If KeyCode= KeyCodes.KEYCODE_BACK Then
		Log("backed")
		Return False
	End If
End Sub
Sub product_ScrollView_ScrollChanged(Position As Int)
	If Position <= 511 Then 
		product_header.Color = Colors.ARGB(Position/2,255, 204, 255)
	End If
	If Position = 0 Then product_header.Color = Colors.rgb(255, 255, 255)
	
	If (Position+83dip) > lbl_title.Top Then
		If extra.product_title_flag = False Then extra.product_title_top = Position
		'header_title.Visible = True
		
		tempint =  75dip    -    (  Position - extra.product_title_top   )		
		If tempint>= 10dip Then
			Log(tempint)
			header_title.Top = tempint
			header_title.Visible = True
			Else
			product_header.Color = Colors.ARGB(255,255, 204, 255)
			header_title.Top = 10dip
			Log("end i")
		End If
		extra.product_title_flag = True
	Else
		extra.product_title_flag = False
		header_title.Visible = False
	End If
End Sub
Sub Activity_Resume
	Dim jober As HttpJob
	jober.Initialize("lastproduct",Me)
	jober.PostString(extra.api,"op=lastproduct")
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub jobdone(job As HttpJob)
	'Log("asdsd")
	Log(job.JobName)
	If job.Success = True Then

		If job.JobName = "picproc" Then
			Log(job.GetString)
'			parser.Initialize(job.GetString)
'			Dim root As List = parser.NextArray
'			For Each colroot As Map In root
'				Dim image As String = colroot.Get("image")
'				
'			Next
'			
			
			
		End If
			If job.JobName = "textproc" Then
				Dim s As String
				s = job.GetString.Replace("&lt;","").Replace("p&gt;","").Replace("br&gt;","").Replace("/&lt;","").Replace("/p&gt;","").Replace("/br&gt;","").Replace("p style=&quot;text-align: justify; &quot;&gt;","")
				s =s.Replace("amp;","").Replace("nbsp;","").Replace("/null","")
				RTLJustify1.Text = s
			End If

		Try
			If job.JobName = "nameproc" Then
				Log(job.GetString)
				Dim d1 As String
				d1 = job.GetString.SubString2(0,job.GetString.IndexOf("^"))
				
				extra.propertyjson = job.GetString.SubString2(job.GetString.IndexOf("^")+1,job.GetString.Length)
				Log(extra.propertyjson)
				Dim s As String
				Dim parser As JSONParser
				parser.Initialize(d1)
				Dim root As List = parser.NextArray
				For Each colroot As Map In root
					Dim price As String = colroot.Get("price")
					Dim name As String = colroot.Get("name")
					Dim model As String = colroot.Get("model")
					lbl_title.Text = name
					lbl_titlen.Text =  model
					header_title.Text = name
					extra.product_title = name
					total.Text = price.SubString2(0,price.IndexOf("."))   & " " & "تومان"
				Next
			End If
		Catch
			Log(LastException)
		End Try
		Try
			If  job.JobName.SubString2(0,9)="imageview" Then
				Dim indexf As Int
				Dim name As String
				indexf = job.JobName.SubString2(job.JobName.IndexOf("*")+1,job.JobName.LastIndexOf("*"))
				name = job.JobName.SubString(job.JobName.LastIndexOf("/")+1)
				Dim OutStream As OutputStream
				OutStream = File.OpenOutput(File.DirInternalCache, name, False)
				File.Copy2(job.GetInputStream,OutStream)
				OutStream.Close
				ImageView(indexf).Bitmap = LoadBitmap(File.DirInternalCache, name)
			End If
			'			If  = "load_lastproduct_main" Then
			'
			'
			'			End If
		Catch
			Log(LastException)
		End Try
		If job.JobName = "load_category_main" Then
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
		If job.JobName="lastproduct" Then
			Dim left As Int=15dip
			Dim parser As JSONParser
			parser.Initialize(job.GetString)
			Dim indexf As Int = 1
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
				
				ImageView(indexf).Initialize("ImageView")
				If File.Exists(File.DirInternalCache, image.SubString2(image.LastIndexOf("/")+1,image.Length)) Then
					ImageView(indexf).Bitmap = LoadBitmap(File.DirInternalCache, image.SubString2(image.LastIndexOf("/")+1,image.Length))
				Else
					ImageView(indexf).Bitmap = LoadBitmap(File.DirAssets,"fileset/main_defult_product.jpg")
					'extra.main_download_image(indexf,image)
				End If
				ImageView(indexf).Gravity = Gravity.FILL
				scrollview_lastproduct.Panel.AddView(ImageView(indexf),left+1dip,1dip,157dip-2dip,157dip-2dip)
				
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
			product_ScrollView.Panel.Height = 50%x+164.5dip + 48dip+310dip+220dip +50dip+295dip 		
			
'			Dim r As Reflector
'			r.Target = mostview_hscrollview
'			r.RunMethod2("setHorizontalScrollBarEnabled", False, "java.lang.boolean")
'			r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
'			
		End If
	End If
End Sub
Sub lastproduct_panel_click()
	Dim lastproduct_panel As Panel
	lastproduct_panel = Sender
 
End Sub
Sub moretext_click()
	Dim su As StringUtils
	Dim lener As Int
	lener = RTLJustify1.Text.Length
	Log(lener)
	pnl4_moshakhasat.Height = lener * 0.275%x + 30dip
	RTLJustify1.Height = lener * 0.275%x
	'moretext.Top = pnl4_moshakhasat.Top + pnl4_moshakhasat.Height   - 50dip
	moretext.Visible = False
	pnl4_line.Visible = False
	'pnl4_line.Top = moretext.Top 
	category_hscrollview.Top = RTLJustify1.Top + RTLJustify1.Height + 20dip
	scrollview_lastproduct.Top = category_hscrollview.Top + category_hscrollview.Height + 9dip
	'product_ScrollView.Height = scrollview_lastproduct.Top	 + scrollview_lastproduct.Height + 9dip
	product_ScrollView.Panel.Height =product_ScrollView.Panel.Height +  RTLJustify1.Height - 289dip  + 40dip
End Sub
Sub propert_click()
	StartActivity(property)
End Sub

