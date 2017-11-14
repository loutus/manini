Type=Activity
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen:  True
	#IncludeTitle:False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private ScrollView1 As ScrollView
	Dim topset As Int
End Sub
'
Sub Activity_Create(FirstTime As Boolean)
	'Do Not forget To load the layout File created with the visual designer. For example:
	Activity.LoadLayout("property")
	topset = 5dip
'	Log(extra.propertyjson.Trim.Length)
'	If extra.propertyjson.Trim.Length < 8 Then
'		createnon
'	Else
		Try
			Dim parser As JSONParser
			parser.Initialize(extra.propertyjson)
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
				ScrollView1.Panel.AddView(lblnodata,5dip,topset,100%x-10dip,35dip)
				
				Dim lblnodata As Label
				lblnodata.Initialize("")
				lblnodata.Text ="  " & text
				lblnodata.Gravity = Gravity.RIGHT
				lblnodata.TextColor = Colors.rgb(115, 115, 115)
				lblnodata.color = Colors.rgb(242, 242, 242)
				lblnodata.TextSize = 9dip
				lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
				ScrollView1.Panel.AddView(lblnodata,5dip,topset+30dip,70%x-5dip,30dip)
				
				Dim lblnodata As Label
				lblnodata.Initialize("")
				lblnodata.Text ="  " & name
				lblnodata.Gravity = Gravity.RIGHT
				lblnodata.TextColor = Colors.rgb(115, 115, 115)
				lblnodata.color = Colors.rgb(242, 242, 242)
				lblnodata.TextSize = 10dip
				lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
				ScrollView1.Panel.AddView(lblnodata,70%x,topset+30dip,30%x-5dip,30dip)
				
				topset = topset + 65dip
				
				ScrollView1.Panel.Height = topset
			Next
		Catch
			createnon
		End Try
'End If 
	End Sub
Sub createnon()
	Dim lblnodata As Label
	lblnodata.Initialize("")
	lblnodata.Text ="هیچ مشخصه ای وجود ندارد"
	lblnodata.Gravity = Gravity.CENTER
	lblnodata.TextColor = Colors.rgb(179, 179, 179)
	lblnodata.TextSize = 12dip
	lblnodata.Typeface = Typeface.LoadFromAssets("yekan.ttf")
	ScrollView1.Panel.AddView(lblnodata,0,0,100%x,50%x)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
