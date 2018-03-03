package com.example.sharedcar;

import it.hua.beans.Poster;
import it.hua.beans.Trans;
import it.hua.beans.TransBack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.JPush.R;

public class SearchOne extends Activity implements OnTouchListener,  OnClickListener {
    /** 
     * 滚动显示和隐藏menu时，手指滑动需要达到的速度。 
     */  
    public static final int SNAP_VELOCITY = 200;  
  
    /** 
     * 屏幕宽度值。 
     */  
    private int screenWidth;  
  
    /** 
     * menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。 
     */  
    private int leftEdge;  
  
    /** 
     * menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。 
     */  
    private int rightEdge = 0;  
  
    /** 
     * menu完全显示时，留给content的宽度值。 
     */  
    private int menuPadding = 80;  
  
    /** 
     * 主内容的布局。 
     */  
    private View content;  
  
    /** 
     * menu的布局。 
     */  
    private View menu;  
  
    /** 
     * menu布局的参数，通过此参数来更改leftMargin的值。 
     */  
    private LinearLayout.LayoutParams menuParams;  
  
    /** 
     * 记录手指按下时的横坐标。 
     */  
    private float xDown;  
  
    /** 
     * 记录手指移动时的横坐标。 
     */  
    private float xMove;  
  
    /** 
     * 记录手机抬起时的横坐标。 
     */  
    private float xUp;  
  
    /** 
     * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。 
     */  
    private boolean isMenuVisible;  
  
    /** 
     * 用于计算手指滑动的速度。 
     */  
    private VelocityTracker mVelocityTracker; 
	private ImageView i1,choosedate,bok2;
	private EditText beginLoc,endLoc;
	private TextView lookmap;
	private static TextView d1,d2,d3,d4,d5,d6,d7,day1,day2,day3,day4,day5,day6,day7;
    public TextView broadsearch,broadpost,broadmychat,broadmylike,broadset,broadmyinfo,broadabout,broadmytext;

	public static int width, height,DateIndex;
	public static String chooseday,from,to;//#####是用户选择的日期 mm-dd用于数据返回
	public TextView[] info;
	public String[] postersID;
	public String[] nicknames;
	public String[] stuidoftheonechatto;
	 ArrayList<Poster> posters;
	
	public ScrollView scrollview;
	
	public Trans ts;
	

	float Slatitude=0f;
	float Slongtitude=0f;
	float Dlatitude=0f;
	float Dlongtitude=0f;
	
	public boolean startfrommap = false;
	public boolean endfrommap = false;
	public static LinkedList<Activity> sAllActivitys = new LinkedList<Activity>(); 
	
public boolean isExit=false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  SearchOne.sAllActivitys.add(this); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_one);

        initValues();  
		
        content.setOnTouchListener(this);  


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        FirstActivity.displaywidth = displayMetrics.widthPixels;
        FirstActivity.displayheight = displayMetrics.heightPixels;
        

		//获取屏幕宽高
//		WindowManager wm = this.getWindowManager();
//		 
//	      width = wm.getDefaultDisplay().getWidth();
//	      height = wm.getDefaultDisplay().getHeight();
        width = FirstActivity.displaywidth;
        height = FirstActivity.displayheight;
        
        ts = new Trans();
        
	      System.out.println("iiiiiii"+width+","+height);
        
        bok2 = (ImageView)findViewById(R.id.bok2);
		resize(bok2 , 1/8.0 , 1/8.0 , (int)(FirstActivity.displaywidth*(580/720.0)) , (int)(FirstActivity.displayheight*(155/1140.0)));
        bok2.setOnClickListener(this);
		
		
        broadsearch = (TextView)findViewById(R.id.broadsearch2);
        broadsearch.setOnClickListener(this);
        broadpost = (TextView)findViewById(R.id.broadpost2);
        broadpost.setOnClickListener(this);
        broadmylike = (TextView)findViewById(R.id.broadmylike2);
        broadmylike.setOnClickListener(this);
        broadmyinfo = (TextView)findViewById(R.id.broadmyinfo2);
        broadmyinfo.setOnClickListener(this);
        broadmytext = (TextView)findViewById(R.id.broadmytext2);
        broadmytext.setOnClickListener(this);
        broadset = (TextView)findViewById(R.id.broadset2);
        broadset.setOnClickListener(this);
        broadmychat = (TextView)findViewById(R.id.broadmychat2);
        broadmychat.setOnClickListener(this);
        broadabout = (TextView)findViewById(R.id.broadabout2);
        broadabout.setOnClickListener(this);
        
		
		i1=(ImageView)findViewById(R.id.bmap);
		resize(i1 , 1/3.0 , 1/14.0 , (int)(FirstActivity.displaywidth*(225/720.0)) , (int)(FirstActivity.displayheight*(925/1140.0)));
		i1.setOnClickListener(this);
		
	      
	      choosedate= (ImageView)findViewById(R.id.choosedate);
	      choosedate.setVisibility(0x00000004);
 
		//星期文字
		d7=(TextView)findViewById(R.id.sunday);
		d7.setOnClickListener(this);
		d1=(TextView)findViewById(R.id.monday);
		d1.setOnClickListener(this);
		d2=(TextView)findViewById(R.id.tuesday);
		d2.setOnClickListener(this);
		d3=(TextView)findViewById(R.id.wednesday);
		d3.setOnClickListener(this);
		d4=(TextView)findViewById(R.id.thursday);
		d4.setOnClickListener(this);
		d5=(TextView)findViewById(R.id.friday);
		d5.setOnClickListener(this);
		d6=(TextView)findViewById(R.id.saturday);
		d6.setOnClickListener(this);

		resize(d7 , 1/9.0 , 1/15.0 ,0,0);
		resize(d6 , 1/9.0 , 1/15.0 ,0,0);
		resize(d5 , 1/9.0 , 1/15.0 ,0,0);
		resize(d4 , 1/9.0 , 1/15.0 ,0,0);
		resize(d3 , 1/9.0 , 1/15.0 ,0,0);
		resize(d2 , 1/9.0 , 1/15.0 ,0,0);
		resize(d1 , 1/9.0 , 1/15.0 ,0,0);
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		DateIndex = setDateView(date);
		switch  (DateIndex){
			case 1:{//今天周日
				setLayout(d7,30,80);
				setLayout(d1,110,80);
				setLayout(d2,220,80);
				setLayout(d3,315,80);
				setLayout(d4,410,80);
				setLayout(d5,505,80);
				setLayout(d6,600,80);
				break;
			}
			case 2:{//今天周一
				setLayout(d1,30,80);
				setLayout(d2,110,80);
				setLayout(d3,220,80);
				setLayout(d4,315,80);
				setLayout(d5,410,80);
				setLayout(d6,505,80);
				setLayout(d7,600,80);
				break;
			} 
			case 3:{//今天周2
				setLayout(d2,30,80);
				setLayout(d3,110,80);
				setLayout(d4,220,80);
				setLayout(d5,315,80);
				setLayout(d6,410,80);
				setLayout(d7,505,80);
				setLayout(d1,600,80);
				break;
			} 
			case 4:{
				setLayout(d3,30,80);
				setLayout(d4,110,80);
				setLayout(d5,220,80);
				setLayout(d6,315,80);
				setLayout(d7,410,80);
				setLayout(d1,505,80);
				setLayout(d2,600,80);
				break;
			}
			case 5:{
				setLayout(d4,30,80);
				setLayout(d5,110,80);
				setLayout(d6,220,80);
				setLayout(d7,315,80);
				setLayout(d1,410,80);
				setLayout(d2,505,80);
				setLayout(d3,600,80);
				break;
			}
			case 6:{
				setLayout(d5,30,80);
				setLayout(d6,110,80);
				setLayout(d7,220,80);
				setLayout(d1,315,80);
				setLayout(d2,410,80);
				setLayout(d3,505,80);
				setLayout(d4,600,80);
				break;
			}
			case 7:{
				setLayout(d6,30,80);
				setLayout(d7,110,80);
				setLayout(d1,220,80);
				setLayout(d2,315,80);
				setLayout(d3,410,80);
				setLayout(d4,505,80);
				setLayout(d5,600,80);
				break;
			}
				}
		
		//设置日期
		day7=(TextView)findViewById(R.id.day7);
		resize(day7 , 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(10/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
	    day7.setTextColor(Color.GRAY);
		
		day1=(TextView)findViewById(R.id.day1);	
		resize(day1 , 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(91/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
		day1.setTextColor(Color.GRAY);	
		
		day2=(TextView)findViewById(R.id.day2);
		resize(day2, 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(202/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
		day2.setTextColor(Color.GRAY);
		
		day3=(TextView)findViewById(R.id.day3);
		resize(day3 , 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(298/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
		day3.setTextColor(Color.GRAY);
		
		day4=(TextView)findViewById(R.id.day4);
		resize(day4 , 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(394/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
		day4.setTextColor(Color.GRAY);
		
		day5=(TextView)findViewById(R.id.day5);
		resize(day5 , 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(490/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
		day5.setTextColor(Color.GRAY);
		
		day6=(TextView)findViewById(R.id.day6);
		resize(day6 , 1/7.0 , 1/20.0 , (int)(FirstActivity.displaywidth*(586/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));
		day6.setTextColor(Color.GRAY);
		
		day7.setText(getIDayLater(0));
		day1.setText(getIDayLater(1));
		day2.setText(getIDayLater(2));
		day3.setText(getIDayLater(3));
		day4.setText(getIDayLater(4));
		day5.setText(getIDayLater(5));
		day6.setText(getIDayLater(6));

		lookmap = (TextView)findViewById(R.id.lookmap);	
		resize(lookmap , 1/5.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(275/720.0)) , (int)(FirstActivity.displayheight*(935/1140.0)));
		System.out.println("iiiiiiiiiishere");
	
//位置输入组
		beginLoc=(EditText)findViewById(R.id.begintext);
		resize(beginLoc , 0.27 , 1/12.0 , (int)(FirstActivity.displaywidth*(62/720.0)) , (int)(FirstActivity.displayheight*(170/1140.0)));

		endLoc=(EditText)findViewById(R.id.endtext);
		resize(endLoc , 0.29 , 1/12.0 , (int)(FirstActivity.displaywidth*(360/720.0)) , (int)(FirstActivity.displayheight*(170/1140.0)));
//		myloc=(ImageView)findViewById(R.id.mylocation);
//		setLayout(myloc,310,280);
//		okbutton = (ImageView)findViewById(R.id.okbutton);
//		setLayout(okbutton,500,280);
//		


		Intent intent2 = getIntent();
		System.out.println("searchone begin");
		
		String intentfrom = intent2.getStringExtra("iam");
		
		if(intentfrom.equals("loginastourist")){
			FirstActivity.isLogin=false;
			Toast toast = Toast.makeText(SearchOne.this, "您尚未登录，大部分功能将无法使用，建议注册或登录", Toast.LENGTH_SHORT);
			
		     toast.show();
			
		}
		else if(intentfrom.equals("editfrombroad")
				||intentfrom.equals("mychatfrombroad")||intentfrom.equals("mycollectfrombroad")
				||intentfrom.equals("mytextfrombroad")||intentfrom.equals("postfrombroad")){
			
		}
		else if(intentfrom.equals("register")){
			System.out.println("it is register");
		}
		else if(intentfrom.equals("login")){
			System.out.println("login");
		}else if (intentfrom.equals("digitalone")){
			//这事从DigitalOne（地图）传过来的数据 ，包括起终点中文名和各自经纬度
	    	String beginloc = null;
			beginloc = intent2.getStringExtra("beginloc");
			String endloc = null;
			endloc = intent2.getStringExtra("endloc");
			

			intent2.getFloatExtra("Slatitude",Slatitude);
			intent2.getFloatExtra("Slongtitude",Slongtitude);
			intent2.getFloatExtra("Dlatitude",Dlatitude);
			intent2.getFloatExtra("Dlongtitude",Dlongtitude);
			
			System.out.println("iiiiiiii"+beginloc+"  "+endloc);
			beginLoc.setText(beginloc);
			endLoc.setText(endloc);
			
		}
		else if(intentfrom.equals("digitaltwo")){//come back from the detail of a test
			//it seems that the activity of the detail of a test will not come back to searchone 
		}
		else if(intentfrom.equals("post")){//获取post传过来的
			ArrayList<String>  poster_String_get = new ArrayList();
			poster_String_get=intent2.getStringArrayListExtra("posters");
			//判断是否是从post传过来的intent即查poster_String_get是否只有一个元素
			if(poster_String_get.size()==1){
				int info_length=info.length;
				info[info_length-1].setText(poster_String_get.get(0));
				postersID[info_length-1] = poster_String_get.get(0).substring(3,12);//第3-11是poster的id
			}//@@@@@@@@@@@@
				
		}
		else if(intentfrom.equals("backfrommap")){
			
		}
		else {
		
		System.out.println("searchone begin2");
		
	
		}
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(  
                LayoutParams. FILL_PARENT,(int)((270/480.0)*FirstActivity.displaywidth));// LayoutParams.WRAP_CONTENT  

		
		scrollview=(ScrollView)findViewById(R.id.scrollview);
		resize(scrollview,0.85,0.6,(int)(20/480.0*FirstActivity.displaywidth),
				(int)(225/800.0*FirstActivity.displayheight));


		LinearLayout body = (LinearLayout) findViewById(R.id.body);
		info = new TextView[10];
		int info_length = info.length;
		postersID=new String[info_length];
		for(int i = 0;i<10;i++){
			System.out.println("iiiiiiiiiinfoinfo");
			info[i] = new TextView(this);//(TextView)findViewById(R.id.info1);
			info[i].setTextSize(25);
			 info[i].setWidth((int)((245/480.0)*FirstActivity.displaywidth));
			 info[i].setOnClickListener(this);
//			TextView tv = new TextView(this);	
//			tv.setText("Test" + i+"Test" + i+"Test" + i+"Test" + i+"Test" + i+"Test" + i+"Test" + i);	
			body.addView(info[i],lp2);
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_one, menu);
		return true;
	}
	
	  private void resize(View v,double scalew,double scaleh,int x,int y)    {
	    	RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)v.getLayoutParams();    // 取控件 aaa 当前的布局参数 
	    	System.out.println("iiiiiiiiidisplayheight"+(int)(scaleh*FirstActivity.displayheight)+","+scalew*FirstActivity.displaywidth);
	    	linearParams.height =(int)(scaleh*FirstActivity.displayheight);        // 当控件的高强制
	    	linearParams.width = (int)(scalew*FirstActivity.displaywidth);
	    	v.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件 aaa
	    	setLayout(v,x,y);
	    	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
System.out.println("iiiiiiiionclicklistner");
		if(v==i1){//看地图按钮
			
		Intent intent1 = new Intent();	
    	intent1.setClass(SearchOne.this, DigitalOne.class);
    	SearchOne.this.startActivity(intent1);
    	System.out.println("iiiiiiiiactivitycontinue");
    	}
		else if((!info[0].equals(""))&&(info[0]!=null)&&v==info[0]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
System.out.println("iiiiiiiiiiiinfotonewactiviyti");
			String detailcontent = info[0].getText().toString();
			Intent intentinfo = new Intent();
			intentinfo.putExtra("detailcontent",detailcontent);
			intentinfo.putExtra("nickname",nicknames[0]);
			intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[0]);
			intentinfo.putExtra("posterID",postersID[0]);
	    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
	    	SearchOne.this.startActivity(intentinfo);
	    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
		}
		else if((!info[1].equals(""))&&(info[1]!=null)&&v==info[1]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[1].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[1]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[1]);
						intentinfo.putExtra("posterID",postersID[1]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		else if((!info[2].equals(""))&&(info[2]!=null)&&v==info[1]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[2].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[2]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[2]);
						intentinfo.putExtra("posterID",postersID[2]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		else if((!info[3].equals(""))&&(info[3]!=null)&&v==info[3]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[3].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[3]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[3]);
						intentinfo.putExtra("posterID",postersID[3]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		else if((!info[4].equals(""))&&(info[4]!=null)&&v==info[4]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[4].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[4]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[4]);
						intentinfo.putExtra("posterID",postersID[4]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		
		else if((!info[5].equals(""))&&(info[5]!=null)&&v==info[5]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[5].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[5]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[5]);
						intentinfo.putExtra("posterID",postersID[5]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		
		else if((!info[3].equals(""))&&(info[6]!=null)&&v==info[6]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[6].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[6]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[6]);
						intentinfo.putExtra("posterID",postersID[6]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		else if((!info[7].equals(""))&&(info[7]!=null)&&v==info[7]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[7].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[7]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[7]);
						intentinfo.putExtra("posterID",postersID[7]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		else if((!info[8].equals(""))&&(info[8]!=null)&&v==info[8]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[8].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[8]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[8]);
						intentinfo.putExtra("posterID",postersID[8]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
		else if((!info[9].equals(""))&&(info[9]!=null)&&v==info[9]){//######### 只实现textview数组中的第一个元素的监听，应该实现用通配符监听所有元素！！
			System.out.println("iiiiiiiiiiiinfotonewactiviyti");
						String detailcontent = info[9].getText().toString();
						Intent intentinfo = new Intent();
						intentinfo.putExtra("detailcontent",detailcontent);
						intentinfo.putExtra("nickname",nicknames[9]);
						intentinfo.putExtra("stuidoftheonechatto", stuidoftheonechatto[9]);
						intentinfo.putExtra("posterID",postersID[9]);
				    	intentinfo.setClass(SearchOne.this, DigitalTwo.class);
				    	SearchOne.this.startActivity(intentinfo);
				    	System.out.println("iiiiiiiiiiiinfotonewactiviytiend");
					}
//		else if(v==bmenu){
//			//出侧栏
//			 scrollToMenu();
//		}
		else if(v == broadsearch){
			System.out.println("iiiiiiibroadsearch");
			//留在本页面上
		}
else if(v == broadpost){
	if(FirstActivity.isLogin==false){
		Toast toast = Toast.makeText(SearchOne.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
	}
	else{
		//去发帖页面
	Intent intentpost = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intentpost.setClass(SearchOne.this, Post.class);
	SearchOne.this.startActivity(intentpost);
	}
		}
else if(v == broadmychat){
	//去查看消息
	if(FirstActivity.isLogin==false){
		Toast toast = Toast.makeText(SearchOne.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
	}
	else{
	Intent intenttomychat = new Intent();	
	intenttomychat.setClass(SearchOne.this, MyText.class);
	SearchOne.this.startActivity(intenttomychat);
	}
}
else if(v == broadmylike){
	if(FirstActivity.isLogin==false){
		Toast toast = Toast.makeText(SearchOne.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
	}
	else{
	//去看我的收藏
		
	
	
	Intent intenttocollect = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttocollect.setClass(SearchOne.this, MyCollect.class);
	SearchOne.this.startActivity(intenttocollect);
	}
}
else if(v == broadmytext){
	if(FirstActivity.isLogin==false){
		Toast toast = Toast.makeText(SearchOne.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
	}
	else{
//去看我的贴子

	Intent intenttomytext = new Intent();	
	intenttomytext.setClass(SearchOne.this, MyText.class);
	SearchOne.this.startActivity(intenttomytext);
	}
}
else if(v == broadabout){
	AlertDialog.Builder builder  = new Builder(SearchOne.this);

	 builder.setTitle("关于我们———校园拼车族" ) ;
	 builder.setMessage("使用山东大学学号和选课密码登录；\n\n支持地图选择起终点；\n\n研发团队：山东大学软件学院12级创新实验室;" ) ;
	 builder.setPositiveButton("确认" ,  null );
	 builder.show(); 
}
else if(v == broadmyinfo){
	if(FirstActivity.isLogin==false){
		Toast toast = Toast.makeText(SearchOne.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
	}
	else{
	//去看我的资料
	Intent intenttomyinfo = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttomyinfo.setClass(SearchOne.this, MyInfo.class);
	SearchOne.this.startActivity(intenttomyinfo);
	}
}
else if(v == broadset){
	if(FirstActivity.isLogin==false){
		Toast toast = Toast.makeText(SearchOne.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
	}
	else{
//去设置
	}
}
		else if(v==d7||v==d1||v==d2||v==d3||v==d4||v==d5||v==d6){

			choosedate.setVisibility(0x00000000);
			switch (DateIndex){
			case 1://edited
				if(v==d7){
					System.out.println("iiiiicase1andv==d7");
					setLayout(choosedate, (int)(25/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(0);
				}else if(v==d1){
					System.out.println("iiiiicase1andv==d1");
					setLayout(choosedate, (int)(90/480.0*width), (int)(20/800.0*height));
					 chooseday=getIDayLater(1);
				}else if(v==d2){
					System.out.println("iiiiicase1andv==d2");
					setLayout(choosedate,(int)(150/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(2);
					
				}else if(v==d3){
					System.out.println("iiiiicase1andv==d3");
					setLayout(choosedate, (int)(215/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(3);
				}else if(v==d4){
					System.out.println("iiiiicase1andv==d4");
					setLayout(choosedate, (int)(280/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(4);
				}else if(v==d5){
					System.out.println("iiiiicase1andv==d5");
					setLayout(choosedate, (int)(340/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(5);
					}
				else if(v==d6){
					System.out.println("iiiiicase1andv==d6");
					setLayout(choosedate, (int)(405/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(6);
						
					}
				break;
			case 2://edited
				if(v==d7){
				System.out.println("iiiiicase1andv==d1");
				setLayout(choosedate,(int)(405/480.0*width), (int)(20/800.0*height) );
				chooseday=getIDayLater(6);
			}else if(v==d1){
				setLayout(choosedate, (int)(25/480.0*width), (int)(20/800.0*height) );
				 chooseday=getIDayLater(0);
			}else if(v==d2){
				setLayout(choosedate,(int)(90/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(1);
				
			}else if(v==d3){
				setLayout(choosedate, (int)(150/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(2);
			}else if(v==d4){
				setLayout(choosedate, (int)(215/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(3);
			}else if(v==d5){
				setLayout(choosedate, (int)(280/480.0*width), (int)(20/800.0*height) );
				chooseday=getIDayLater(4);
				}
			else if(v==d6){
				setLayout(choosedate,(int)(340/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(5);
				}
				 break;
			case 3://edited
				if(v==d7){
					System.out.println("iiiiicase1andv==d1");
					setLayout(choosedate, (int)(340/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(5);
				}else if(v==d1){
					setLayout(choosedate,  (int)(405/480.0*width), (int)(20/800.0*height));
					 chooseday=getIDayLater(6);
				}else if(v==d2){
					setLayout(choosedate, (int)(25/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(0);
				}else if(v==d3){
					setLayout(choosedate, (int)(90/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(1);
				}else if(v==d4){
					setLayout(choosedate, (int)(150/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(2);
				}else if(v==d5){
					setLayout(choosedate, (int)(215/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(3);
					}
				else if(v==d6){
					setLayout(choosedate,(int)(280/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(4);
					}
				break;
			case 4://edited
				if(v==d7){
					System.out.println("iiiiicase1andv==d1");
					setLayout(choosedate, (int)(280/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(4);
				}else if(v==d1){
					setLayout(choosedate,  (int)(340/480.0*width), (int)(20/800.0*height));
					 chooseday=getIDayLater(5);
				}else if(v==d2){
					setLayout(choosedate,(int)(405/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(6);
				}else if(v==d3){
					setLayout(choosedate, (int)(25/480.0*width), (int)(20/800.0*height) );
					chooseday=getIDayLater(0);
				}else if(v==d4){
					setLayout(choosedate, (int)(90/480.0*width), (int)(20/800.0*height) );
					chooseday=getIDayLater(1);
				}else if(v==d5){
					setLayout(choosedate,(int)(150/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(2);
					}
				else if(v==d6){
					setLayout(choosedate,(int)(215/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(3);
					}
				break;
			case 5://edited
				if(v==d7){
					System.out.println("iiiiicase1andv==d1");
					setLayout(choosedate, (int)(215/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(3);
				}else if(v==d1){
					setLayout(choosedate,(int)(280/480.0*width), (int)(20/800.0*height) );
					 chooseday=getIDayLater(4);
				}else if(v==d2){
					setLayout(choosedate, (int)(340/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(5);
				}else if(v==d3){
					System.out.println("iiiiiiiiiithisislastone wednesday");
					setLayout(choosedate, (int)(405/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(6);
				}else if(v==d4){
					setLayout(choosedate, (int)(25/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(0);
				}else if(v==d5){
					setLayout(choosedate, (int)(90/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(1);
					}
				else if(v==d6){
					setLayout(choosedate,(int) (150/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(2);
					}
				break;
			case 6://edited
				if(v==d7){
					System.out.println("iiiiicase1andv==d1");
					setLayout(choosedate,(int)(150/480.0*width), (int)(20/800.0*height)); 
					chooseday=getIDayLater(2);
				}else if(v==d1){
					setLayout(choosedate,(int)(215/480.0*width), (int)(20/800.0*height));
					 chooseday=getIDayLater(3);
				}else if(v==d2){
					setLayout(choosedate, (int)(280/480.0*width), (int)(20/800.0*height)  );
					chooseday=getIDayLater(4);
				}else if(v==d3){
					setLayout(choosedate, (int)(340/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(5);
				}else if(v==d4){
					setLayout(choosedate, (int)(405/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(6);
				}else if(v==d5){
					setLayout(choosedate,(int)(25/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(0);
					}
				else if(v==d6){
					setLayout(choosedate, (int)(90/480.0*width), (int)(20/800.0*height));
					chooseday=getIDayLater(1);
					}
				break;
			case 7://edited
				if(v==d7){
				System.out.println("iiiiicase1andv==d1");
				setLayout(choosedate, (int)(90/480.0*width), (int)(20/800.0*height) );
				chooseday=getIDayLater(1);
			}else if(v==d1){
				setLayout(choosedate, (int)(150/480.0*width), (int)(20/800.0*height));
				 chooseday=getIDayLater(2);
			}else if(v==d2){
				setLayout(choosedate,(int)(215/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(3);
			}else if(v==d3){
				setLayout(choosedate,  (int)(280/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(4);
			}else if(v==d4){
				setLayout(choosedate, (int)(340/480.0*width), (int)(20/800.0*height) );
				chooseday=getIDayLater(5);
			}else if(v==d5){
				setLayout(choosedate,(int)(405/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(6);
				}
			else if(v==d6){
				setLayout(choosedate,(int)(25/480.0*width), (int)(20/800.0*height));
				chooseday=getIDayLater(0);
				}
				break;
				
				
			}
			System.out.println("iiiiiiiiiii"+DateIndex+","+chooseday);
			}
		else if(v==bok2){//点击终点旁边的ok按钮时
			System.out.println("iiiiiiiitrans to server");
			from=beginLoc.getText().toString();
			to=endLoc.getText().toString();
			
			//应该先要判断这里的textview起终点内容是用户手动输入还是地图获取
			
			if(!startfrommap)//当起点文字不是从map来的的话，应该要使用百度地图方法获得一个坐标地址
			{
				//#########      尚未实现，演示时只能从地图上获取地点信息
				
			}
			
			//@@@@这里应该传内容给服务器，包括日期choosedate和起终点from（string）  和to（string）
			Poster post = new Poster();
			post.start = from;
			post.destination = to;
			 DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
			 Date date=null;
			 System.out.println("abbc"+chooseday);
			 if(chooseday==null){
				 Toast toast = Toast.makeText(SearchOne.this, "您尚未选择日期！", Toast.LENGTH_SHORT);
		   	     toast.show();
			 }
			 else{
			try {
				date = fmt.parse("2014-"+chooseday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			post.Gtime=date;
			post.Slatitude=Slatitude;
			post.Slongitude=Slongtitude;
			post.Dlatitude=Dlatitude;
			post.Dlongitude=Dlongtitude;
			ts.st = ts.st.search;
			ts.poster = post;
			TransBack tb = ts.search(FirstActivity.stuid, ts.poster);
			
			posters = tb.getPosters();
			int lengthof_posters = posters.size();
			stuidoftheonechatto=new String[lengthof_posters];
	        nicknames=new String[lengthof_posters];
	    	//String poster_String[]=new String[lengthof_posters];
		 ArrayList<String> poster_String = new ArrayList();
	    	for(int i=0;i<lengthof_posters;i++){
	    		poster_String.add("ID:"+posters.get(i).getFormatedPosterID()+posters.get(i).nickname+"("+posters.get(i).stuid+"):"+posters.get(i).Gtime.toLocaleString()
	    				+",从    "+posters.get(i).start+" 到  "+posters.get(i).destination);
	    		//posterID 格式：x00000001, 共9位
	    		postersID[i] = poster_String.get(i).substring(3,12);//postersID[i]对应poster_String(i)的posterid
	    		info[i].setText(poster_String.get(i));
	    		nicknames[i] = posters.get(i).nickname;
	    		stuidoftheonechatto[i]=posters.get(i).stuid;
	    	
	    		
	    		//########这里很有可能把poster发过来的盖住了。不过问题不大，逻辑上行得通
	    	}
			//服务器返回和搜索情况相似的贴子添加到textview上
			
			 }
		}
	}
	
//	public boolean onTouchEvent(MotionEvent event) {
//		// 在这里判断一下如果是按下操作就获取坐标然后执行方法
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			System.out.println("iiiiiiiitotouch");
//		displayXY(event.getX(), event.getY());
//		}
//		return super.onTouchEvent(event);
//		}
//		// 获取到坐标，进行判断
//		private void displayXY(float x, float y) {
//			float bw=x/width;
//			float bh=y/height;
//			System.out.println("iiiiiiiiiii"+bw+","+bh);
//			if(bw>(2/7)){//(bh>(1/5))&&(bh<(1/4))&&
//				System.out.println("iiiiiiiiiiigetintobh");
//				//@@@@@此处应该传给服务器搜索信息， 包括日期和起终点
//		}
//		}

	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
			if(resultCode==RESULT_OK){
				String beginloc = null;
				Bundle bundle1 = data.getExtras();
				beginloc = bundle1.getString("bd");
				String endloc = null;
				Bundle bundle2 = data.getExtras();
				endloc = bundle2.getString("bd2");
				System.out.println("iiiiiiii"+beginloc+"  "+endloc);
				beginLoc.setText(beginloc);
				endLoc.setText(endloc);
			}
				
		
	}
	
	public static int setDateView(Date date) {  
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(date);  
	    int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);  
	    return dayIndex;
	      
	}  
	
	public int getMonth(){
		
		Time t = new Time();
		t.setToNow();
		int m = t.month;
		return m+1;
	}
	
	public int getDate(){
		Time t = new Time();
		t.setToNow();
		int d = t.monthDay;
		return d;
	}

	public String getIDayLater(int i ){
		SimpleDateFormat   formatDate_   =   new   SimpleDateFormat("yyyy-MM-dd");   
		  GregorianCalendar   cal   =   new   GregorianCalendar();   
		  cal.setTime(new   Date());   
		  cal.add(GregorianCalendar.DATE,i);   
		  String   sdate   =   formatDate_.format(cal.getTime());  
		  String md = sdate.substring(5);
		  return md;
	}
	
	public static void setLayoutX(View view,int x) 
	{ 
	MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams()); 
	margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin); 
	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin); 
	view.setLayoutParams(layoutParams); 
	} 
	/* 
	* 设置控件所在的位置Y，并且不改变宽高， 
	* Y为绝对位置，此时X可能归0 
	*/ 
	public static void setLayoutY(View view,int y) 
	{ 
	MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams()); 
	margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height); 
	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin); 
	view.setLayoutParams(layoutParams); 
	} 

	public static void setLayout(View view,int x,int y) 
	{ 
	MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams()); 
	margin.setMargins(x,y, x+margin.width, y+margin.height); 
	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin); 
	view.setLayoutParams(layoutParams); 
	} 
	
	

    /** 
     * 初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。 
     */  
    private void initValues() {  
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        screenWidth = window.getDefaultDisplay().getWidth();  
        content = findViewById(R.id.content);  
        menu = findViewById(R.id.menu);  
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();  
        // 将menu的宽度设置为屏幕宽度减去menuPadding  
        menuParams.width = screenWidth - menuPadding;  
        // 左边缘的值赋值为menu宽度的负数  
        leftEdge = -menuParams.width;  
        // menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见  
        menuParams.leftMargin = leftEdge;  
        // 将content的宽度设置为屏幕宽度  
        content.getLayoutParams().width = screenWidth;  
    }  
  
    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
        createVelocityTracker(event);  
        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            // 手指按下时，记录按下时的横坐标  
            xDown = event.getRawX();  
            break;  
        case MotionEvent.ACTION_MOVE:  
            // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu  
            xMove = event.getRawX();  
            int distanceX = (int) (xMove - xDown);  
            if (isMenuVisible) {  
                menuParams.leftMargin = distanceX;  
            } else {  
                menuParams.leftMargin = leftEdge + distanceX;  
            }  
            if (menuParams.leftMargin < leftEdge) {  
                menuParams.leftMargin = leftEdge;  
            } else if (menuParams.leftMargin > rightEdge) {  
                menuParams.leftMargin = rightEdge;  
            }  
            menu.setLayoutParams(menuParams);  
            break;  
        case MotionEvent.ACTION_UP:  
            // 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面  
            xUp = event.getRawX();  
            if (wantToShowMenu()) {  
                if (shouldScrollToMenu()) {  
                    scrollToMenu();  
                } else {  
                    scrollToContent();  
                }  
            } else if (wantToShowContent()) {  
                if (shouldScrollToContent()) {  
                    scrollToContent();  
                } else {  
                    scrollToMenu();  
                }  
            }  
            recycleVelocityTracker();  
            break;  
        }  
        return true;  
    }  
  
    /** 
     * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。 
     *  
     * @return 当前手势想显示content返回true，否则返回false。 
     */  
    private boolean wantToShowContent() {  
        return xUp - xDown < 0 && isMenuVisible;  
    }  
  
    /** 
     * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。 
     *  
     * @return 当前手势想显示menu返回true，否则返回false。 
     */  
    private boolean wantToShowMenu() {  
        return xUp - xDown > 0 && !isMenuVisible;  
    }  
  
    /** 
     * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY， 
     * 就认为应该滚动将menu展示出来。 
     *  
     * @return 如果应该滚动将menu展示出来返回true，否则返回false。 
     */  
    private boolean shouldScrollToMenu() {  
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;  
    }  
  
    /** 
     * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2， 
     * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。 
     *  
     * @return 如果应该滚动将content展示出来返回true，否则返回false。 
     */  
    private boolean shouldScrollToContent() {  
        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;  
    }  
  
    /** 
     * 将屏幕滚动到menu界面，滚动速度设定为30. 
     */  
    private void scrollToMenu() {  
        new ScrollTask().execute(30);  
    }  
  
    /** 
     * 将屏幕滚动到content界面，滚动速度设定为-30. 
     */  
    private void scrollToContent() {  
        new ScrollTask().execute(-30);  
    }  
  
    /** 
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。 
     *  
     * @param event 
     *            content界面的滑动事件 
     */  
    private void createVelocityTracker(MotionEvent event) {  
        if (mVelocityTracker == null) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
        mVelocityTracker.addMovement(event);  
    }  
  
    /** 
     * 获取手指在content界面滑动的速度。 
     *  
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。 
     */  
    private int getScrollVelocity() {  
        mVelocityTracker.computeCurrentVelocity(1000);  
        int velocity = (int) mVelocityTracker.getXVelocity();  
        return Math.abs(velocity);  
    }  
  
    /** 
     * 回收VelocityTracker对象。 
     */  
    private void recycleVelocityTracker() {  
        mVelocityTracker.recycle();  
        mVelocityTracker = null;  
    }  
  
    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {  
  
        protected Integer doInBackground(Integer... speed) {  
            int leftMargin = menuParams.leftMargin;  
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。  
            while (true) {  
                leftMargin = leftMargin + speed[0];  
                if (leftMargin > rightEdge) {  
                    leftMargin = rightEdge;  
                    break;  
                }  
                if (leftMargin < leftEdge) {  
                    leftMargin = leftEdge;  
                    break;  
                }  
                publishProgress(leftMargin);  
                // 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。  
                sleep(20);  
            }  
            if (speed[0] > 0) {  
                isMenuVisible = true;  
            } else {  
                isMenuVisible = false;  
            }  
            return leftMargin;  
        }  
  
        @Override  
        protected void onProgressUpdate(Integer... leftMargin) {  
            menuParams.leftMargin = leftMargin[0];  
            menu.setLayoutParams(menuParams);  
        }  
  
        @Override  
        protected void onPostExecute(Integer leftMargin) {  
            menuParams.leftMargin = leftMargin;  
            menu.setLayoutParams(menuParams);  
        }  
    }  
  
    /** 
     * 使当前线程睡眠指定的毫秒数。 
     *  
     * @param millis 
     *            指定当前线程睡眠多久，以毫秒为单位 
     */  
    private void sleep(long millis) {  
        try {  
            Thread.sleep(millis);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }
    
    
    @Override
    public void onBackPressed()
    {
       //实现Home键效果
       //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
    	System.out.println("want to exit");
    	finishAll();  
        // 这个主要是用来关闭进程的, 关把所有activity finish  
        // 的话，进程是不会关闭的  
        System.exit(0);  
}
    
    public static void finishAll() {  
        for(Activity activity : sAllActivitys) {  
            activity.finish();  
        }  
          
        sAllActivitys.clear();  
    }  
  
}