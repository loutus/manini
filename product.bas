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
	Private product_ScrollView As ScrollView
	Private product_header As Panel
	Private product_imag1 As ImageView
	Private product_img2 As ImageView
	Dim scrollview_lastproduct As HorizontalScrollView
	Dim ImageView(20)  As ImageView
End Sub
Sub jobdone(job As HttpJob)
	If job.Success = True Then
		If job.JobName="load_lastproduct_main" Then
			Dim left As Int=8dip
			Dim parser As JSONParser
			parser.Initialize(job.GetString)
			Dim index As Int = 6
			Dim root As List = parser.NextArray
			For Each colroot As Map In root
				Dim image As String = colroot.Get("image")
				Dim price As String = colroot.Get("price")
				Dim model As String = colroot.Get("model")
				Dim name As String = colroot.Get("name")
				Dim id As String = colroot.Get("id")
				

				Dim panel As Panel
				panel.Initialize("panel")
				
				panel.Color = Colors.White
				scrollview_lastproduct.Panel.AddView(panel,left,0,160dip,200dip-2dip)
				extra.InitPanel(panel,1dip,Colors.White,Colors.rgb(207, 204, 204))
 
				Dim lable As Label
				lable.Initialize("lable")
				lable.Color = Colors.White
				lable.TextColor = Colors.rgb(65, 65, 65)
				lable.Gravity = Gravity.RIGHT
				lable.Typeface = Typeface.LoadFromAssets("yekan.ttf")
				lable.TextSize = "16"
				lable.Text = model
				scrollview_lastproduct.Panel.AddView(lable,left+2dip,145dip,140dip,20dip)
				
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
				scrollview_lastproduct.Panel.AddView(lable2,left+15dip,170dip,125dip,24dip)
				
				ImageView(index).Initialize("ImageView")
				If File.Exists(File.DirInternalCache, image.SubString2(image.LastIndexOf("/")+1,image.Length)) Then
					ImageView(index).Bitmap = LoadBitmap(File.DirInternalCache, image.SubString2(image.LastIndexOf("/")+1,image.Length))
					Log("from cach")
				Else
					ImageView(index).Bitmap = LoadBitmap(File.DirAssets,"setting/main_defult_product.jpg")
					extra.main_download_image(index,image)
				End If
				ImageView(index).Gravity = Gravity.FILL
				scrollview_lastproduct.Panel.AddView(ImageView(index),left+1dip,25dip,157dip,88dip)
				
				Dim CanvasGraph As Canvas
				CanvasGraph.Initialize(panel)
				'CanvasGraph.DrawColor(Colors.White)
				CanvasGraph.DrawLine(0,140dip,160dip,140dip,Colors.rgb(207, 204, 204),1dip)
				left = left + 170dip
				index= index + 1
			Next
			Dim r As Reflector
			r.Target = scrollview_lastproduct
			r.RunMethod2("setHorizontalScrollBarEnabled", False, "java.lang.boolean")
			r.RunMethod2("setOverScrollMode", 2, "java.lang.int" )
			scrollview_lastproduct.Panel.Width = left
			
			
			
		End If
	End If
End Sub
Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("product")
	Dim Container As AHPageContainer
	Dim Pager As AHViewPager
	Container.Initialize
	Dim pan(5) As Panel	
	
	extra.load_lastproduct_main
	
	pan(0).Initialize("")
	pan(1).Initialize("")
	pan(0).LoadLayout("product_img1")
	Container.AddPageAt(pan(0), "Main", 0)
				
	pan(1).LoadLayout("product_img2")
	Container.AddPageAt(pan(1), "Main2", 1)
 
	Pager.Initialize2(Container, "Pager")
	product_ScrollView.Panel.AddView(Pager,0,55dip,100%x,50%x+55dip)
	
	Dim pnl As Panel
	pnl.Initialize("")
	pnl.Color = Colors.rgb(250, 250, 250)
	product_ScrollView.Panel.AddView(pnl,0,50%x+55dip,100%x,100dip)

	Dim pnl2 As Panel
	pnl2.Initialize("")
	pnl2.Color = Colors.White
	product_ScrollView.Panel.AddView(pnl2,15dip,50%x+163dip,100%x-30dip,40dip)
	InitPanel(pnl2,2,1,Colors.rgb(204, 204, 204))

	Dim pnl3 As Panel
	pnl3.Initialize("")
	pnl3.Color = Colors.White
	product_ScrollView.Panel.AddView(pnl3,15dip,50%x+164.5dip + 48dip,100%x-30dip,280dip)
	InitPanel(pnl3,2,1,Colors.rgb(204, 204, 204))
	
	Dim pnl4 As Panel
	pnl4.Initialize("")
	pnl4.Color = Colors.White
	product_ScrollView.Panel.AddView(pnl4,15dip,50%x+164.5dip + 48dip+289dip,100%x-30dip,280dip)
	InitPanel(pnl4,2,1,Colors.rgb(204, 204, 204))
	
	Dim text As String = "لوسیون مرطوب کننده هیدرا ب ب ماستلا، مناسب برای کودکانی است که پوست معمولی دارند. این لوسیون فاقد چربی است و باعث چرب و آلوده شدن پوست بدن نمی شود، رطوبت مورد نیاز پوست را تأمین می کند و از خشک شدن و زبری پوست جلوگیری می نماید."& "به علاوه به دلیل وجود کره شی آ، پوست کودک نرم و لطیف باقی می ماند. در عین حال به سرعت جذب پوست شده و چربی باقی نمی گذارد." &"- حاوی روغن جوجوبا، روغن بادام شیرین، کره شی آ و ویتامین E است و پوست بدن کودک را تغذیه و تقویت می کند و موجب شادابی و طراوت پوست کودک می شود."
	text = text & text & text
	Dim RTLJustify1 As RTLJustifyTextView
	RTLJustify1.Initialize("")
	RTLJustify1.SetText(text,False)
	RTLJustify1.DrawingCacheEnabled = True
	RTLJustify1.TextColor = Colors.rgb(89, 89, 89)
	RTLJustify1.TextGravity = RTLJustify1.GRAVITY_FILL_VERTICAL
	RTLJustify1.SetText(text,True)
	RTLJustify1.TextSize = 7dip
	Dim Obj1 As Reflector
	Obj1.Target = RTLJustify1
	Obj1.RunMethod3("setLineSpacing", 1, "java.lang.float",1.5, "java.lang.float")
	product_ScrollView.Panel.AddView(RTLJustify1,15dip+15dip,50%x+164.5dip + 48dip+310dip,100%x-60dip,185dip)
	
	Dim pnl4 As Panel
	pnl4.Initialize("")
	pnl4.Color = Colors.rgb(179, 179, 179)
	product_ScrollView.Panel.AddView(pnl4,30dip,50%x+164.5dip + 48dip+310dip+185dip +15dip,100%x-60dip,1dip)
	
	
	
	scrollview.Initialize(100%x,"")
	scrollview.Panel.Height = 1500
	scrollview.Color = Colors.red
	product_ScrollView.Panel.AddView(scrollview,0,50%x+164.5dip + 48dip+310dip+220dip +50dip,100%x,400dip)
	
	
	
	Dim moretext As Label
	moretext.Initialize("")
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
	product_ScrollView.Panel.AddView(buy,50dip,50%x+420dip,75%x,50dip)
	
	Dim buyicon As Label
	buyicon.Initialize("")
	Private FAe As FontAwesome
	FAe.Initialize
	buyicon.Gravity = Gravity.CENTER_VERTICAL
	buyicon.Typeface = Typeface.FONTAWESOME
	buyicon.TextColor = Colors.White
	buyicon.TextSize = 13dip
	buyicon.Text = FAe.GetFontAwesomeIconByName("fa-cart-plus")
	product_ScrollView.Panel.AddView(buyicon,65%x,50%x+420dip,20%x,50dip)
	
		
	Dim propert As Label
	propert.Initialize("")
	propert.TextColor = Colors.Black
	propert.Gravity = Gravity.CENTER
	propert.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	propert.Text = "مشخصات" 
	product_ScrollView.Panel.AddView(propert,15dip,50%x+164dip,100%x-30dip,40dip)
	
	
	Dim total As Label
	total.Initialize("")
	total.TextColor = Colors.rgb(102, 187, 106)
	total.Gravity = Gravity.LEFT
	total.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	total.Text = "26000 تومان"
	total.TextSize = 13dip
	product_ScrollView.Panel.AddView(total,32dip,50%x+178dip + 48dip,30%x,40dip)
	
	
	
	
	Private FA As FontAwesome
	FA.Initialize
	Dim properticon As Label
	properticon.Initialize("")
	properticon.TextColor = Colors.Black
	
	properticon.Gravity = Gravity.CENTER_VERTICAL
	properticon.Typeface = Typeface.FONTAWESOME
	properticon.Text = FA.GetFontAwesomeIconByName("fa-calendar-o")
	product_ScrollView.Panel.AddView(properticon,50%x+10dip+20dip,50%x+164dip,100%x-30dip,35dip)
	
	
	Dim lbl_title As Label
	lbl_title.Initialize("")
	lbl_title.Text =  "دوربین 20 مگاپیکسلی قضیه"
	lbl_title.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	lbl_title.TextSize = 11dip
	lbl_title.Gravity = Gravity.RIGHT
	lbl_title.TextColor = Colors.Black
	product_ScrollView.Panel.AddView(lbl_title,0,50%x+85dip,95%x,30dip)
	
	lbl_title.Initialize("")
	lbl_title.Text =  "Camera 20 MP Pexual for"
	lbl_title.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	lbl_title.TextSize = 8dip
	lbl_title.Gravity = Gravity.RIGHT
	lbl_title.TextColor = Colors.rgb(169, 169, 169)
	product_ScrollView.Panel.AddView(lbl_title,0,50%x+120dip,95%x,30dip)
	
	Dim pic_sheare As ImageView
	pic_sheare.Initialize("")
	pic_sheare.Gravity = Gravity.FILL
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
	
	
	product_ScrollView.Panel.AddView(saler,0,50%x+350dip,100%x-35dip,80dip)
	product_ScrollView.Panel.AddView(pic_sheare,30dip,50%x+70dip,18dip,18dip)
	product_ScrollView.Panel.AddView(pic_like,80dip,50%x+70dip,18dip,18dip)
	product_header.BringToFront
End Sub

Sub product_ScrollView_ScrollChanged(Position As Int)
	'Log(Position)
	If Position < 510 Then 
		product_header.Color = Colors.RGB(200,-Position/2,150)
	End If
	If Position = 0 Then product_header.Color = Colors.rgb(255, 255, 255)
	

End Sub
Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

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