package com.example.sharedcar;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.JPush.R;
import com.example.jpushdemo.ExampleUtil;

import it.hua.beans.Trans;
import it.hua.beans.TransBack;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.Toast;

public class FirstActivity extends Activity implements OnTouchListener, OnClickListener {  
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
    
    public static int height,width;
  
    public ImageView tittle,text1,text2,picdenglu,newuser1,newuser2,bmenu;
    public TextView id,password,bdenglu;
    public EditText idedit,passwordedit;
    public TextView broadsearch,broadpost,broadmychat,broadmylike,broadset,broadmyinfo,broadabout,broadmytext;
    public static int displayheight;

	public static int displaywidth;
	public static boolean isLogin=false;
	
	public static String stuid;
	

private static final String TAG = "JPush";
	
	
	 Trans ts;
	 
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);  
        initValues();  
		
        content.setOnTouchListener(this);  
        
        ts =new Trans(); 
        
        
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
		initView();   
        
        broadsearch = (TextView)findViewById(R.id.broadsearch);
        broadsearch.setOnClickListener(this);
        broadpost = (TextView)findViewById(R.id.broadpost);
        broadpost.setOnClickListener(this);
        broadmylike = (TextView)findViewById(R.id.broadmylike);
        broadmylike.setOnClickListener(this);
        broadmyinfo = (TextView)findViewById(R.id.broadmyinfo);
        broadmyinfo.setOnClickListener(this);
        broadmytext = (TextView)findViewById(R.id.broadmytext);
        broadmytext.setOnClickListener(this);
        broadset = (TextView)findViewById(R.id.broadset);
        broadset.setOnClickListener(this);
        broadmychat = (TextView)findViewById(R.id.broadmychat);
        broadmychat.setOnClickListener(this);
        broadabout = (TextView)findViewById(R.id.broadabout);
        broadabout.setOnClickListener(this);
        

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        FirstActivity.displaywidth = displayMetrics.widthPixels;
        FirstActivity.displayheight = displayMetrics.heightPixels;
        
//		tittle=(ImageView)findViewById(R.id.title);
//		resize(tittle,400/480.0,100/800.0,(int)(displaywidth*(35/480.0)),(int)(displayheight*(90/800.0)));
		
		id=(TextView)findViewById(R.id.idtextview);//"学号"
		resize(id,2/9.0,1/12.0,(int)(displaywidth*(40/720.0)),(int)(displayheight*(400/1140.0)));
		text1=(ImageView)findViewById(R.id.imageView1);//学号输入框背景
		resize(text1,2/3.0,1/12.0,(int)(displaywidth*(150/720.0)),(int)(displayheight*(380/1140.0)));
		idedit=(EditText)findViewById(R.id.editText1);//学号输入
		resize(idedit,2/3.0,1/12.0,(int)(displaywidth*(150/720.0)),(int)(displayheight*(380/1140.0)));
		
		password=(TextView)findViewById(R.id.textView3);
		resize(password,2/9.0,1/12.0,(int)(displaywidth*(40/720.0)),(int)(displayheight*(600/1140.0)));
		text2=(ImageView)findViewById(R.id.imageView2);
		resize(text2,2/3.0,1/12.0,(int)(displaywidth*(150/720.0)),(int)(displayheight*(580/1140.0)));
		passwordedit=(EditText)findViewById(R.id.editText2);
		resize(passwordedit,2/3.0,1/12.0,(int)(displaywidth*(150/720.0)),(int)(displayheight*(580/1140.0)));
		
		picdenglu = (ImageView)findViewById(R.id.picdenglu);
		resize(picdenglu , 1/3.0 , 1/12.0 , (int)(displaywidth*(220/720.0)) , (int)(displayheight*(750/1140.0)));
		picdenglu.setOnClickListener(this);
		bdenglu = (TextView)findViewById(R.id.bdenglu);
		resize(bdenglu,1/4.0,1/13.0,(int)(displaywidth*(280/720.0)),(int)(displayheight*(770/1140.0)));
		
//		
//		WindowManager wm = this.getWindowManager();
//		 
//	      width = wm.getDefaultDisplay().getWidth();
//	      height = wm.getDefaultDisplay().getHeight();
	      
		newuser1 = (ImageView)findViewById(R.id.newuser1);
		newuser2 = (ImageView)findViewById(R.id.newuser2);
		newuser2.setVisibility(0x00000004);

		resize(newuser1 , 2/5.0 , 1/12.0 , (int)(displaywidth*(200/720.0)) , (int)(displayheight*(850/1140.0)));
		resize(newuser2 , 2/5.0 , 1/12.0 , (int)(displaywidth*(200/720.0)) , (int)(displayheight*(850/1140.0)));
		//setLayout(newuser1,(int)(80/380.0*width),(int)(620/800.0*height));
		//setLayout(newuser2,(int)(80/380.0*width),(int)(620/800.0*height));
		newuser1.setOnClickListener(this);
		
		bmenu =(ImageView)findViewById(R.id.bmenu);
		bmenu.setOnClickListener(this);
		resize(bmenu , 1/13.0 , 1/13.0 , (int)(displaywidth*(10/720.0)) , (int)(displayheight*(10/1140.0)));
		
    }  
  
    private void initView(){
		String udid =  ExampleUtil.getImei(getApplicationContext(), "a946707dd9b5424ddf5da1ac");
        
		String appKey = ExampleUtil.getAppKey(getApplicationContext());
		if (null == appKey) appKey = "AppKey异常";

		String packageName =  getPackageName();
		
		String versionName =  ExampleUtil.GetVersion(getApplicationContext());
		
		
	}
    
    private void resize(View v,double scalew,double scaleh,int x,int y)    {
    	RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)v.getLayoutParams();    // 取控件 aaa 当前的布局参数 
    	System.out.println("iiiiiiiiidisplayheight"+(int)(scaleh*displayheight)+","+scalew*displaywidth);
    	linearParams.height =(int)(scaleh*displayheight);        // 当控件的高强制
    	linearParams.width = (int)(scalew*displaywidth);
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

    public boolean logsuccess=false;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==picdenglu){
    	
    	
    	//@@@@@@@@@@@@@@点登陆按钮后发送id和密码，获取确认信息
    	ts.st = ts.st.login;
    	ts.getSt();
    	stuid = idedit.getText().toString();
    	ts.stuid=stuid;
    	ts.pwd = passwordedit.getText().toString();
    	TransBack tb = ts.login(idedit.getText().toString(), passwordedit.getText().toString());
    	System.out.println(ts.stuid+"dafs");
    	
    	if(tb.state==tb.state.longinsuccess){
    		setAlias(stuid);
    		logsuccess = true;
    	}else if(tb.state==tb.state.wrongpassword){
    		logsuccess = false;
    		Toast toast = Toast.makeText(FirstActivity.this, "密码和您的选课密码不匹配或学号错误！", Toast.LENGTH_SHORT);
   	     toast.show();
    	}else if(tb.state==tb.state.problemoccursinserver){
    		logsuccess = false;
    		Toast toast = Toast.makeText(FirstActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_SHORT);
      	     toast.show();
    	}
    	
    	
    	if(logsuccess){
		isLogin=true;
	Intent intent1 = new Intent();	
	intent1.putExtra("iam","login");
	System.out.println("iiiiiiiiiintent2");
	intent1.setClass(FirstActivity.this, SearchOne.class);
	FirstActivity.this.startActivity(intent1);
	System.out.println("iiiiiiiiiintent");
    	}
		}
		else if(v==newuser1||v==newuser2){
			newuser2.setVisibility(0x00000000);
			newuser2.setOnClickListener(this);
			newuser1.setVisibility(0x00000004);
			Intent intent2 = new Intent();	
	    	intent2.setClass(FirstActivity.this, Register.class);
	    	FirstActivity.this.startActivity(intent2);
		}
		else if(v==bmenu){
			//出侧栏
			 scrollToMenu();
		}
		else if(v == broadsearch){
			isLogin=false;
			Intent intenttourist = new Intent();	
			intenttourist.putExtra("iam", "loginasatourist");
	    	intenttourist.setClass(FirstActivity.this, SearchOne.class);
	    	FirstActivity.this.startActivity(intenttourist);
		}
else if(v == broadpost){
		Toast toast = Toast.makeText(FirstActivity.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
	     toast.show();
			
		}
else if(v == broadmychat){
	Toast toast = Toast.makeText(FirstActivity.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
    toast.show();
}
else if(v == broadmylike){
	Toast toast = Toast.makeText(FirstActivity.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
    toast.show();
}
else if(v == broadmytext){
	Toast toast = Toast.makeText(FirstActivity.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
    toast.show();
}
else if(v == broadabout){
	AlertDialog.Builder builder  = new Builder(FirstActivity.this);

	 builder.setTitle("关于我们———校园拼车族" ) ;
	 builder.setMessage("使用山东大学学号和选课密码登录；\n\n支持地图选择起终点；\n\n研发团队：山东大学软件学院12级创新实验室;" ) ;
	 builder.setPositiveButton("确认" ,  null );
	 builder.show(); 
}
else if(v == broadmyinfo){
	Toast toast = Toast.makeText(FirstActivity.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
    toast.show();
}
else if(v == broadset){
	Toast toast = Toast.makeText(FirstActivity.this, "您尚未登录！", Toast.LENGTH_SHORT);
	
    toast.show();
}
	}  
	private void setTag(String tag){
//		 tagEdit = (EditText) findViewById(R.id.et_tag);
//		tag = tagEdit.getText().toString().trim();
		
      // 检查 tag 的有效性
		if (TextUtils.isEmpty(tag)) {
			Toast.makeText(FirstActivity.this,R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		// ","隔开的多个 转换成 Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(FirstActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	} 
	
	private void setAlias(String alias){
//		aliasEdit = (EditText) findViewById(R.id.et_alias);
//		alias = aliasEdit.getText().toString().trim();
		if (TextUtils.isEmpty(alias)) {
			Toast.makeText(FirstActivity.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(FirstActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		//调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}
	
	
	/**
	 *设置通知提示方式 - 基础属性
	 */
	private void setStyleBasic(){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(FirstActivity.this);
		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）  
		JPushInterface.setPushNotificationBuilder(1, builder);
		Toast.makeText(FirstActivity.this, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 *设置通知栏样式 - 定义通知栏Layout
	 */
	private void setStyleCustom(){
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(FirstActivity.this,R.layout.customer_notitfication_layout,R.id.icon, R.id.title, R.id.text);
		builder.layoutIconDrawable = R.drawable.ic_launcher;
		builder.developerArg0 = "developerArg2";
		JPushInterface.setPushNotificationBuilder(2, builder);
		Toast.makeText(FirstActivity.this,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
		if(KeyEvent.KEYCODE_HOME==keyCode){

			 android.os.Process.killProcess(android.os.Process.myPid());

			//关闭程序或做其他操作.

			}
		return super.onKeyDown(keyCode, event);
	}

	

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

       @Override
       public void gotResult(int code, String alias, Set<String> tags) {
           String logs ;
           switch (code) {
           case 0:
               logs = "Set tag and alias success";
               Log.i(TAG, logs);
               break;
               
           case 6002:
               logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
               Log.i(TAG, logs);
               if (ExampleUtil.isConnected(getApplicationContext())) {
               	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
               } else {
               	Log.i(TAG, "No network");
               }
               break;
           
           default:
               logs = "Failed with errorCode = " + code;
               Log.e(TAG, logs);
           }
           
           ExampleUtil.showToast(logs, getApplicationContext());
       }
	    
	};
	
   private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

       @Override
       public void gotResult(int code, String alias, Set<String> tags) {
           String logs ;
           switch (code) {
           case 0:
               logs = "Set tag and alias success";
               Log.i(TAG, logs);
               break;
               
           case 6002:
               logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
               Log.i(TAG, logs);
               if (ExampleUtil.isConnected(getApplicationContext())) {
               	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
               } else {
               	Log.i(TAG, "No network");
               }
               break;
           
           default:
               logs = "Failed with errorCode = " + code;
               Log.e(TAG, logs);
           }
           
           ExampleUtil.showToast(logs, getApplicationContext());
       }
       
   };
   
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	
	

   private final Handler mHandler = new Handler() {
       @Override
       public void handleMessage(android.os.Message msg) {
           super.handleMessage(msg);
           switch (msg.what) {
           case MSG_SET_ALIAS:
               Log.d(TAG, "Set alias in handler.");
               JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
               break;
               
           case MSG_SET_TAGS:
               Log.d(TAG, "Set tags in handler.");
               JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
               break;
               
           default:
               Log.i(TAG, "Unhandled msg - " + msg.what);
           }
       }
   };
	
}  