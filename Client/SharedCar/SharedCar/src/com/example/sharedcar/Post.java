package com.example.sharedcar;

import it.hua.beans.Poster;
import it.hua.beans.Trans;
import it.hua.beans.TransBack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
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
import android.widget.TextView;

import com.JPush.R;

public class Post extends Activity implements OnTouchListener, OnClickListener{
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
    
    private ImageView ToPost,detailbg;
    private EditText idedit,dateedit,timeedit,fromedit,teleedit,toedit,extracontent;
    private TextView ToPosttext;
    private int width,height;
    public String postid,postdate,posttime,postfrom,posttele,postto,extratext;
    public TextView broadsearch,broadpost,broadmychat,broadmylike,broadset,broadmyinfo,broadabout,broadmytext;

    public Poster post ;
   public  ArrayList<Poster> posters ;
   
   public Trans ts;
    
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post);  
        initValues();  
        
        SearchOne.sAllActivitys.add(this);  
		
        content.setOnTouchListener(this);  
        WindowManager wm = this.getWindowManager();
		 
	      width = wm.getDefaultDisplay().getWidth();
	      height = wm.getDefaultDisplay().getHeight();
	      
	      ts = new Trans();
	      
	     post = new Poster();
	     posters = new ArrayList<Poster>();
	      
	   //   detailbg = (ImageView)findViewById(R.id.detailbg3);
		//	resize(detailbg , 0.7f , 5/6.0f , (int)(FirstActivity.displaywidth*(10/720.0)) , (int)(FirstActivity.displayheight*(20/1140.0)));

	  //   setLayout(detailbg , 10,20);
	      
		idedit = (EditText)findViewById(R.id.idedit3);
		resize(idedit , 0.55 , 1/11.0 , (int)(FirstActivity.displaywidth*(185/720.0)) , (int)(FirstActivity.displayheight*(10/1140.0)));
		idedit.setText(FirstActivity.stuid);
		//setLayout(idedit,185,60);

		dateedit = (EditText)findViewById(R.id.dateedit3);
		resize(dateedit , 0.55 , 1/11.0 , (int)(FirstActivity.displaywidth*(185/720.0)) , (int)(FirstActivity.displayheight*(85/1140.0)));

		//setLayout(dateedit,185,145);

		timeedit = (EditText)findViewById(R.id.timeedit3);
		resize(timeedit , 0.55 , 1/11.0 , (int)(FirstActivity.displaywidth*(185/720.0)) , (int)(FirstActivity.displayheight*(165/1140.0)));

		//setLayout(timeedit,185,225);

		fromedit = (EditText)findViewById(R.id.fromedit3);
		resize(fromedit ,0.25 , 1/11.0 , (int)(FirstActivity.displaywidth*(150/720.0)) , (int)(FirstActivity.displayheight*(257/1140.0)));

		//setLayout(fromedit,150,317);

		toedit = (EditText)findViewById(R.id.toedit3);
		resize(toedit , 0.25 , 1/11.0 , (int)(FirstActivity.displaywidth*(445/720.0)) , (int)(FirstActivity.displayheight*(257/1140.0)));

		//setLayout(toedit,445,317);

		teleedit = (EditText)findViewById(R.id.teleedit3);
		resize(teleedit , 0.55 , 1/11.0 , (int)(FirstActivity.displaywidth*(272/720.0)) , (int)(FirstActivity.displayheight*(346/1140.0)));

		//setLayout(teleedit,272,406);

		extracontent=(EditText)findViewById(R.id.extracontent);
		resize(extracontent , 0.65 , 1/11.0 , (int)(FirstActivity.displaywidth*(202/720.0)) , (int)(FirstActivity.displayheight*(520/1140.0)));

		//setLayout(extracontent,272,600);
		
		
//		add = (EditText)findViewById(R.id.addedit2);
//		setLayout(id,230/480*width,470/800*height);

		ToPost = (ImageView)findViewById(R.id.touchbutton3);
	//	resize(ToPost , 1/3.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(215/720.0)) , (int)(FirstActivity.displayheight*(750/1140.0)));
		setLayout(ToPost,(int)(FirstActivity.displaywidth*(165/720.0)),(int)(FirstActivity.displayheight*(850/1140.0)));
		ToPost.setOnClickListener(this);
		ToPosttext = (TextView)findViewById(R.id.touchtext3);
		//resize(ToPosttext , 1/3.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(220/720.0)) , (int)(FirstActivity.displayheight*(770/1140.0)));

		setLayout(ToPosttext,(int)(FirstActivity.displaywidth*(280/720.0)), (int)(FirstActivity.displayheight*(870/1140.0)));
		
		
		  broadsearch = (TextView)findViewById(R.id.broadsearch3);
	        broadsearch.setOnClickListener(this);
	        broadpost = (TextView)findViewById(R.id.broadpost3);
	        broadpost.setOnClickListener(this);
	        broadmylike = (TextView)findViewById(R.id.broadmylike3);
	        broadmylike.setOnClickListener(this);
	        broadmyinfo = (TextView)findViewById(R.id.broadmyinfo3);
	        broadmyinfo.setOnClickListener(this);
	        broadmytext = (TextView)findViewById(R.id.broadmytext3);
	        broadmytext.setOnClickListener(this);
	        broadset = (TextView)findViewById(R.id.broadset3);
	        broadset.setOnClickListener(this);
	        broadmychat = (TextView)findViewById(R.id.broadmychat3);
	        broadmychat.setOnClickListener(this);
	        broadabout = (TextView)findViewById(R.id.broadabout3);
	        broadabout.setOnClickListener(this);
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
     * 初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。 
     */  
    private void initValues() {  
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        screenWidth = window.getDefaultDisplay().getWidth();  
        content = findViewById(R.id.content);  
        menu = findViewById(R.id.menu3);  
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
	public void onClick(View v) {
		if(v==ToPost){
			postid=idedit.getText().toString();
			postdate = "2014-"+dateedit.getText().toString();
			posttime = timeedit.getText().toString()+"-00";
			postfrom = fromedit.getText().toString();
			posttele = teleedit.getText().toString();
			postto = toedit.getText().toString();
			extratext = extracontent.getText().toString();
			//@@@@@此处应该向服务器发送发帖信息
			
			if(posttele==null||posttele==""){
				posttele = 000+"";
			}
			if(extratext==null||extratext==""){
				posttele = "nothing";
			}
		 post.stuid = FirstActivity.stuid;
		 post.destination=toedit.getText().toString();
		 DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");  
		 Date date=null;
		try {
			date = fmt.parse(postdate+"T"+posttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 post.Gtime=date;
		 Date    curDate    =   new    Date(System.currentTimeMillis());
		 post.ptime=curDate;
		 post.start=fromedit.getText().toString();
		 post.content=extratext;
		 post.Dlatitude=1.1f;
		 post.Dlongitude=1.1f;
		 post.Slatitude=1.1f;
		 post.Slongitude=1.1f;
		 ts.poster=post;
		 TransBack tb =  ts.post(post.stuid, ts.poster);
		 
		 posters = tb.getPosters();//在searchone里面把poster贴上
		 int lengthof_posters= posters.size();
	    	//String poster_String[]=new String[lengthof_posters];
		 ArrayList<String> poster_String = new ArrayList();
	    	for(int i=0;i<lengthof_posters;i++){
	    		poster_String.add("ID:"+posters.get(i).getFormatedPosterID()+"#"+posters.get(i).nickname+"("+posters.get(i).stuid+"):"+posters.get(i).Gtime
	    				+",从    "+posters.get(i).start+" 到  "+posters.get(i).destination);
	    		//posterID 格式：x00000001, 共9位
	    	}
		 
		 Intent intent1 = new Intent();
		 intent1.putExtra("iam", "post");
			intent1.putStringArrayListExtra("posters",poster_String);
	    	intent1.setClass(Post.this, SearchOne.class);
	    	Post.this.startActivity(intent1);
		 
		 
		
		}else if(v == broadpost){
			//留在本页
				}
		else if(v == broadmychat){
			//去查看消息

			Intent intenttomychat = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intenttomychat.setClass(Post.this, MyChat.class);
			Post.this.startActivity(intenttomychat);
		}
		else if(v == broadmylike){
			//去看我的收藏
			Intent intenttocollect = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intenttocollect.setClass(Post.this, MyCollect.class);
			Post.this.startActivity(intenttocollect);
		}
		else if(v == broadmytext){
		//去看我的贴子

			Intent intenttomytext = new Intent();	
			intenttomytext.setClass(Post.this, MyText.class);
	    	Post.this.startActivity(intenttomytext);
		}
		else if(v == broadabout){
			AlertDialog.Builder builder  = new Builder(Post.this);

			 builder.setTitle("关于我们———校园拼车族" ) ;
			 builder.setMessage("使用山东大学学号和选课密码登录；\n\n支持地图选择起终点；\n\n研发团队：山东大学软件学院12级创新实验室;" ) ;
			 builder.setPositiveButton("确认" ,  null );
			 builder.show(); 
		}
		else if(v == broadmyinfo){
			//去看我的资料
			Intent intenttomyinfo = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intenttomyinfo.setClass(Post.this, MyInfo.class);
			Post.this.startActivity(intenttomyinfo);
		}
		else if(v == broadset){
		//去设置
		}
		else if(v==broadsearch){

			Intent intenttosearch = new Intent();	
			intenttosearch.putExtra("iam", "postfrombroad");

			intenttosearch.setClass(Post.this, SearchOne.class);
	    	Post.this.startActivity(intenttosearch);
		}
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
    private void resize(View v,double scalew,double scaleh,int x,int y)    {
    	RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)v.getLayoutParams();    // 取控件 aaa 当前的布局参数 
    	System.out.println("iiiiiiiiidisplayheight"+(int)(scaleh*FirstActivity.displayheight)+","+scalew*FirstActivity.displaywidth);
    	linearParams.height =(int)(scaleh*FirstActivity.displayheight);        // 当控件的高强制
    	linearParams.width = (int)(scalew*FirstActivity.displaywidth);
    	v.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件 aaa
    	setLayout(v,x,y);
    	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
    		Intent intentback = new Intent();	

			intentback.putExtra("iam", "backfrompost");
			intentback.setClass(Post.this, SearchOne.class);
			Post.this.startActivity(intentback);
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
