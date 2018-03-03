package com.example.sharedcar;

import it.hua.beans.Poster;
import it.hua.beans.Trans;
import it.hua.beans.TransBack;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.JPush.R;

public class DigitalTwo extends Activity  implements OnTouchListener, OnClickListener {  
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
	public TextView id,date,time,from, to,tele,add;
	public ImageView touchbutton,detailbg,collectbutton;
	public TextView touchtext;
	public int width,height;
	public static String nickname = "nickname";
	public static String stuidoftheonechatto="stuidoftheonechatto";
    public TextView broadsearch,broadpost,broadmychat,broadmylike,broadset,broadmyinfo,broadabout,broadmytext,collecttext;

    public Trans ts;
    public String posterID;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_digital_two);
		initValues();  
		
		SearchOne.sAllActivitys.add(this);  
		
        content.setOnTouchListener(this);  
        
		WindowManager wm = this.getWindowManager();
		 
	      width = wm.getDefaultDisplay().getWidth();
	      height = wm.getDefaultDisplay().getHeight();
	      
//	      
//	      detailbg = (ImageView)findViewById(R.id.detailbg);
//			resize(detailbg , 0.85 , 0.9 , (int)(FirstActivity.displaywidth*(10/720.0)) , (int)(FirstActivity.displayheight*(20/1140.0)));
//	      
	      
	      
	      broadsearch = (TextView)findViewById(R.id.broadsearch10);
	        broadsearch.setOnClickListener(this);
	        broadpost = (TextView)findViewById(R.id.broadpost10);
	        broadpost.setOnClickListener(this);
	        broadmylike = (TextView)findViewById(R.id.broadmylike10);
	        broadmylike.setOnClickListener(this);
	        broadmyinfo = (TextView)findViewById(R.id.broadmyinfo10);
	        broadmyinfo.setOnClickListener(this);
	        broadmytext = (TextView)findViewById(R.id.broadmytext10);
	        broadmytext.setOnClickListener(this);
	        broadset = (TextView)findViewById(R.id.broadset10);
	        broadset.setOnClickListener(this);
	        broadmychat = (TextView)findViewById(R.id.broadmychat10);
	        broadmychat.setOnClickListener(this);
	        broadabout = (TextView)findViewById(R.id.broadabout10);
	        broadabout.setOnClickListener(this);
	      
		id = (TextView)findViewById(R.id.idedit2);
		resize(id , 0.8 , 1/12.0 , (int)(FirstActivity.displaywidth*(185/720.0)) , (int)(FirstActivity.displayheight*(35/1140.0)));

		date = (TextView)findViewById(R.id.dateedit2);
		resize(date , 0.8 , 1/12.0 , (int)(FirstActivity.displaywidth*(185/720.0)) , (int)(FirstActivity.displayheight*(120/1140.0)));

		time = (TextView)findViewById(R.id.timeedit2);
		resize(time , 0.8 , 1/12.0 , (int)(FirstActivity.displaywidth*(185/720.0)) , (int)(FirstActivity.displayheight*(200/1140.0)));

		from = (TextView)findViewById(R.id.fromedit2);
		resize(from , 0.4 , 1/12.0 , (int)(FirstActivity.displaywidth*(150/720.0)) , (int)(FirstActivity.displayheight*(292/1140.0)));

		to = (TextView)findViewById(R.id.toedit2);
		resize(to , 0.3 , 1/12.0 , (int)(FirstActivity.displaywidth*(445/720.0)) , (int)(FirstActivity.displayheight*(292/1140.0)));

		tele = (TextView)findViewById(R.id.teleedit2);
		resize(tele , 0.6 , 1/12.0 , (int)(FirstActivity.displaywidth*(272/720.0)) , (int)(FirstActivity.displayheight*(381/1140.0)));

		add = (TextView)findViewById(R.id.addcontent);
		resize(add , 0.65 , 3/12.0 , (int)(FirstActivity.displaywidth*(210/720.0)) , (int)(FirstActivity.displayheight*(520/1140.0)));

//		

		touchbutton = (ImageView)findViewById(R.id.touchbutton);
		resize(touchbutton , 1/3.0 , 1/14.0 , (int)(FirstActivity.displaywidth*(105/720.0)) , (int)(FirstActivity.displayheight*(830/1140.0)));
		 touchbutton.setOnClickListener(this);
		touchtext = (TextView)findViewById(R.id.touchtext);
		resize(touchtext , 1/3.0 , 1/14.0 , (int)(FirstActivity.displaywidth*(135/720.0)) , (int)(FirstActivity.displayheight*(830/1140.0)));
		
		collectbutton = (ImageView)findViewById(R.id.collectbutton);
		resize(collectbutton , 1/3.0 , 1/14.0 , (int)(FirstActivity.displaywidth*(425/720.0)) , (int)(FirstActivity.displayheight*(830/1140.0)));
		collectbutton.setOnClickListener(this);
		collecttext = (TextView)findViewById(R.id.collecttext);
		resize(collecttext , 1/3.0 , 1/14.0 , (int)(FirstActivity.displaywidth*(475/720.0)) , (int)(FirstActivity.displayheight*(830/1140.0)));
		
		Intent intent2 = getIntent();
		
    	String detailcontent = null;
		detailcontent = intent2.getStringExtra("detailcontent");//没有用了。之前测试临时内容
		stuidoftheonechatto = intent2.getStringExtra("stuidoftheonechatto");
		posterID = intent2.getStringExtra("posterID");
		nickname =intent2.getStringExtra("nickname");
	//	stuidoftheonechatto=posterID;
	      ts = new Trans();
	      ts.st=ts.st.posterdetail;
	      ts.posterID = Integer.parseInt(posterID.substring(1))+"";
	      System.out.println("aaa"+ts.posterID);
	      TransBack tb = ts.posterditail(FirstActivity.stuid, ts.posterID);
	 
	      
	      Poster thisposter = tb.posters.get(0);
	      id.setText(thisposter.stuid);
	      date.setText(thisposter.Gtime.getYear()+"年"+thisposter.Gtime.getMonth()+"月"+thisposter.Gtime.getDay()+"日");
	      time.setText(thisposter.Gtime.getHours()+":"+thisposter.Gtime.getMinutes()+":"+thisposter.Gtime.getSeconds());
	      from.setText(thisposter.start);
	      to.setText(thisposter.destination);
	      tele.setText(thisposter.tele);
	      add.setText(thisposter.content);
	      
	      
	      
	      
	      
		//#############id.setText(detailcontent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.digital_two, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v==touchbutton){
			Intent intenttouch = new Intent();
			intenttouch.setClass(DigitalTwo.this, Edit.class);
			intenttouch.putExtra("nickname",nickname);
			intenttouch.putExtra("stuidoftheonechatto",stuidoftheonechatto);
			intenttouch.setClass(DigitalTwo.this,Edit.class);
	    	DigitalTwo.this.startActivity(intenttouch);
		}		
		else if(v == broadsearch){
			Intent intentpost = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intentpost.putExtra("iam", "digitaltwo");
			intentpost.setClass(DigitalTwo.this, SearchOne.class);
			DigitalTwo.this.startActivity(intentpost);
		}
else if(v == broadpost){
		//去发帖页面
	Intent intentpost = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intentpost.setClass(DigitalTwo.this, Post.class);
	DigitalTwo.this.startActivity(intentpost);
		}
else if(v == broadmychat){
	//去查看消息

	Intent intenttomychat = new Intent();	
	intenttomychat.setClass(DigitalTwo.this, MyText.class);
	DigitalTwo.this.startActivity(intenttomychat);
}
else if(v == broadmylike){
	//去看我的收藏
	Intent intenttocollect = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttocollect.setClass(DigitalTwo.this, MyCollect.class);
	DigitalTwo.this.startActivity(intenttocollect);
}
else if(v == broadmytext){
//去看我的贴子

	Intent intenttomytext = new Intent();	
	intenttomytext.setClass(DigitalTwo.this, MyText.class);
	DigitalTwo.this.startActivity(intenttomytext);
}
else if(v == broadabout){
	AlertDialog.Builder builder  = new Builder(DigitalTwo.this);
	 builder.setTitle("关于我们———校园拼车族" ) ;
	 builder.setMessage("使用山东大学学号和选课密码登录；\n\n支持地图选择起终点；\n\n研发团队：山东大学软件学院12级创新实验室;" ) ;
	 builder.setPositiveButton("确认" ,  null );
	 builder.show(); 
}
else if(v == broadmyinfo){
	//去看我的资料
	Intent intenttomyinfo = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttomyinfo.setClass(DigitalTwo.this, MyInfo.class);
	DigitalTwo.this.startActivity(intenttomyinfo);
}
else if(v == broadset){
//去设置
}
		
else if(v==collectbutton){
	//向服务器发送收藏的贴子内容#############
	
	Trans ts2 = new Trans();
	ts2.st=ts2.st.collect;//点击保存按钮
	ts2.stuid=FirstActivity.stuid;
	ts2.posterID=Integer.parseInt(posterID.substring(1))+"";
	TransBack tb2 = ts2.collectatext(ts2.stuid,ts2.posterID);
	
	Toast toast = Toast.makeText(DigitalTwo.this, "收藏成功,请通过侧拉菜单返回", Toast.LENGTH_SHORT);
	     toast.show();
}
		
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
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
    		Intent intentback = new Intent();	

			intentback.putExtra("iam", "backfrommap");
			intentback.setClass(DigitalTwo.this, SearchOne.class);
			DigitalTwo.this.startActivity(intentback);
	    	System.out.println("iiiiiiiiiintent");
            return true;
        }
    	else if(KeyEvent.KEYCODE_HOME==keyCode){

    		android.os.Process.killProcess(android.os.Process.myPid());

    		//关闭程序或做其他操作.

    		}
        return super.onKeyDown(keyCode, event);
    
    }
}
