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
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("product")
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
	
	Dim pnl As Panel
	pnl.Initialize("")
	pnl.Color = Colors.rgb(250, 250, 250)

	product_ScrollView.Panel.AddView(pnl,0,50%x+55dip,100%x,100dip)
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
