
package com.example.sharedcar;

import android.app.ActionBar.LayoutParams;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.JPush.R;

public class MyChat extends Activity implements OnTouchListener, OnClickListener {//�ҵ�����
    /** 
     * ������ʾ������menuʱ����ָ������Ҫ�ﵽ���ٶȡ� 
     */  
    public static final int SNAP_VELOCITY = 200;  
  
    /** 
     * ��Ļ���ֵ�� 
     */  
    private int screenWidth;  
  
    /** 
     * menu�����Ի����������Ե��ֵ��menu���ֵĿ��������marginLeft�����ֵ֮�󣬲����ټ��١� 
     */  
    private int leftEdge;  
  
    /** 
     * menu�����Ի��������ұ�Ե��ֵ��Ϊ0����marginLeft����0֮�󣬲������ӡ� 
     */  
    private int rightEdge = 0;  
  
    /** 
     * menu��ȫ��ʾʱ������content�Ŀ��ֵ�� 
     */  
    private int menuPadding = 80;  
  
    /** 
     * �����ݵĲ��֡� 
     */  
    private View content;  
  
    /** 
     * menu�Ĳ��֡� 
     */  
    private View menu;  
  
    /** 
     * menu���ֵĲ�����ͨ���˲���������leftMargin��ֵ�� 
     */  
    private LinearLayout.LayoutParams menuParams;  
  
    /** 
     * ��¼��ָ����ʱ�ĺ����ꡣ 
     */  
    private float xDown;  
  
    /** 
     * ��¼��ָ�ƶ�ʱ�ĺ����ꡣ 
     */  
    private float xMove;  
  
    /** 
     * ��¼�ֻ�̧��ʱ�ĺ����ꡣ 
     */  
    private float xUp;  
  
    /** 
     * menu��ǰ����ʾ�������ء�ֻ����ȫ��ʾ������menuʱ�Ż���Ĵ�ֵ�����������д�ֵ��Ч�� 
     */  
    private boolean isMenuVisible;  
  
    /** 
     * ���ڼ�����ָ�������ٶȡ� 
     */  
    private VelocityTracker mVelocityTracker; 

	private TextView name ;
	private ImageView chatwith,chatcontent,chatnow;
	private Button bsend;
	private EditText chatedit;
    public TextView broadsearch,broadpost,broadmychat,broadmylike,broadset,broadmyinfo,broadabout,broadmytext;

    private int width,height;
    private TextView[] info;
    private ScrollView scrollview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_mychat);
		
		SearchOne.sAllActivitys.add(this);  

		//�������
		        initValues();  
				
		        content.setOnTouchListener(this);  
		                
		        
		        broadsearch = (TextView)findViewById(R.id.broadsearch8);
		        broadsearch.setOnClickListener(this);
		        broadpost = (TextView)findViewById(R.id.broadpost8);
		        broadpost.setOnClickListener(this);
		        broadmylike = (TextView)findViewById(R.id.broadmylike8);
		        broadmylike.setOnClickListener(this);
		        broadmyinfo = (TextView)findViewById(R.id.broadmyinfo8);
		        broadmyinfo.setOnClickListener(this);
		        broadmytext = (TextView)findViewById(R.id.broadmytext8);
		        broadmytext.setOnClickListener(this);
		        broadset = (TextView)findViewById(R.id.broadset8);
		        broadset.setOnClickListener(this);
		        broadmychat = (TextView)findViewById(R.id.broadmychat8);
		        broadmychat.setOnClickListener(this);
		        broadabout = (TextView)findViewById(R.id.broadabout8);
		        broadabout.setOnClickListener(this);
		        
		        WindowManager wm = this.getWindowManager();
				 
			      width = wm.getDefaultDisplay().getWidth();
			      height = wm.getDefaultDisplay().getHeight();
			      
//			      
			      LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(  
			                LayoutParams. FILL_PARENT,(int)((230/480.0)*width));// LayoutParams.WRAP_CONTENT  

					
					scrollview=(ScrollView)findViewById(R.id.scrollview8);
//					
//					setLayout(scrollview,(int)(20/480.0*width),
//							(int)(225/800.0*height));

			      
			      LinearLayout body = (LinearLayout) findViewById(R.id.body8);
					info = new TextView[10];
					for(int i = 0;i<10;i++){
						System.out.println("iiiiiiiiiinfoinfo44");
						info[i] = new TextView(this);//(TextView)findViewById(R.id.info1);
						info[i].setText("info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i+"info"+i);
						info[i].setTextSize(25);
						 info[i].setWidth((int)((245/480.0)*width));
						 info[i].setOnClickListener(this);
//						TextView tv = new TextView(this);	
//						tv.setText("Test" + i+"Test" + i+"Test" + i+"Test" + i+"Test" + i+"Test" + i+"Test" + i);	
						body.addView(info[i],lp2);
					}
				
		        
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	public static void setLayoutX(View view,int x) 
	{ 
	MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams()); 
	margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin); 
	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin); 
	view.setLayoutParams(layoutParams); 
	} 
	/* 
	* ���ÿؼ����ڵ�λ��Y�����Ҳ��ı��ߣ� 
	* YΪ����λ�ã���ʱX���ܹ�0 
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
     * ��ʼ��һЩ�ؼ������ݡ�������ȡ��Ļ�Ŀ�ȣ���content�����������ÿ�ȣ���menu�����������ÿ�Ⱥ�ƫ�ƾ���ȡ� 
     */  
    private void initValues() {  
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        screenWidth = window.getDefaultDisplay().getWidth();  
        content = findViewById(R.id.content8);  
        menu = findViewById(R.id.menu);  
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();  
        // ��menu�Ŀ������Ϊ��Ļ��ȼ�ȥmenuPadding  
        menuParams.width = screenWidth - menuPadding;  
        // ���Ե��ֵ��ֵΪmenu��ȵĸ���  
        leftEdge = -menuParams.width;  
        // menu��leftMargin����Ϊ���Ե��ֵ��������ʼ��ʱmenu�ͱ�Ϊ���ɼ�  
        menuParams.leftMargin = leftEdge;  
        // ��content�Ŀ������Ϊ��Ļ���  
        content.getLayoutParams().width = screenWidth;  
    }  
  
    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
        createVelocityTracker(event);  
        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            // ��ָ����ʱ����¼����ʱ�ĺ�����  
            xDown = event.getRawX();  
            break;  
        case MotionEvent.ACTION_MOVE:  
            // ��ָ�ƶ�ʱ���ԱȰ���ʱ�ĺ����꣬������ƶ��ľ��룬������menu��leftMarginֵ���Ӷ���ʾ������menu  
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
            // ��ָ̧��ʱ�������жϵ�ǰ���Ƶ���ͼ���Ӷ������ǹ�����menu���棬���ǹ�����content����  
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
     * �жϵ�ǰ���Ƶ���ͼ�ǲ�������ʾcontent�������ָ�ƶ��ľ����Ǹ������ҵ�ǰmenu�ǿɼ��ģ�����Ϊ��ǰ��������Ҫ��ʾcontent�� 
     *  
     * @return ��ǰ��������ʾcontent����true�����򷵻�false�� 
     */  
    private boolean wantToShowContent() {  
        return xUp - xDown < 0 && isMenuVisible;  
    }  
  
    /** 
     * �жϵ�ǰ���Ƶ���ͼ�ǲ�������ʾmenu�������ָ�ƶ��ľ������������ҵ�ǰmenu�ǲ��ɼ��ģ�����Ϊ��ǰ��������Ҫ��ʾmenu�� 
     *  
     * @return ��ǰ��������ʾmenu����true�����򷵻�false�� 
     */  
    private boolean wantToShowMenu() {  
        return xUp - xDown > 0 && !isMenuVisible;  
    }  
  
    /** 
     * �ж��Ƿ�Ӧ�ù�����menuչʾ�����������ָ�ƶ����������Ļ��1/2��������ָ�ƶ��ٶȴ���SNAP_VELOCITY�� 
     * ����ΪӦ�ù�����menuչʾ������ 
     *  
     * @return ���Ӧ�ù�����menuչʾ��������true�����򷵻�false�� 
     */  
    private boolean shouldScrollToMenu() {  
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;  
    }  
  
    /** 
     * �ж��Ƿ�Ӧ�ù�����contentչʾ�����������ָ�ƶ��������menuPadding������Ļ��1/2�� 
     * ������ָ�ƶ��ٶȴ���SNAP_VELOCITY�� ����ΪӦ�ù�����contentչʾ������ 
     *  
     * @return ���Ӧ�ù�����contentչʾ��������true�����򷵻�false�� 
     */  
    private boolean shouldScrollToContent() {  
        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;  
    }  
  
    /** 
     * ����Ļ������menu���棬�����ٶ��趨Ϊ30. 
     */  
    private void scrollToMenu() {  
        new ScrollTask().execute(30);  
    }  
  
    /** 
     * ����Ļ������content���棬�����ٶ��趨Ϊ-30. 
     */  
    private void scrollToContent() {  
        new ScrollTask().execute(-30);  
    }  
  
    /** 
     * ����VelocityTracker���󣬲�������content����Ļ����¼����뵽VelocityTracker���С� 
     *  
     * @param event 
     *            content����Ļ����¼� 
     */  
    private void createVelocityTracker(MotionEvent event) {  
        if (mVelocityTracker == null) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
        mVelocityTracker.addMovement(event);  
    }  
  
    /** 
     * ��ȡ��ָ��content���滬�����ٶȡ� 
     *  
     * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ�� 
     */  
    private int getScrollVelocity() {  
        mVelocityTracker.computeCurrentVelocity(1000);  
        int velocity = (int) mVelocityTracker.getXVelocity();  
        return Math.abs(velocity);  
    }  
  
    /** 
     * ����VelocityTracker���� 
     */  
    private void recycleVelocityTracker() {  
        mVelocityTracker.recycle();  
        mVelocityTracker = null;  
    }  
  
    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {  
  
        protected Integer doInBackground(Integer... speed) {  
            int leftMargin = menuParams.leftMargin;  
            // ���ݴ�����ٶ����������棬������������߽���ұ߽�ʱ������ѭ����  
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
                // Ϊ��Ҫ�й���Ч��������ÿ��ѭ��ʹ�߳�˯��20���룬�������۲��ܹ���������������  
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
     * ʹ��ǰ�߳�˯��ָ���ĺ������� 
     *  
     * @param millis 
     *            ָ����ǰ�߳�˯�߶�ã��Ժ���Ϊ��λ 
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
		// TODO Auto-generated method stub
		 if(v == broadsearch){
			System.out.println("iiiiiiibroadsearch");
			

			Intent intenttourist = new Intent();
			intenttourist.putExtra("iam", "mychat");
	
	    	intenttourist.setClass(MyChat.this, SearchOne.class);
	    	MyChat.this.startActivity(intenttourist);
		}
else if(v == broadpost){
		//ȥ����ҳ��
	Intent intentpost = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intentpost.setClass(MyChat.this, Post.class);
	MyChat.this.startActivity(intentpost);
		}
else if(v == broadmychat){
	//���ڱ�ҳ
}
else if(v == broadmylike){
	//ȥ���ҵ��ղ�
	Intent intenttocollect = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttocollect.setClass(MyChat.this, MyCollect.class);
	MyChat.this.startActivity(intenttocollect);
}
else if(v == broadmytext){
//ȥ���ҵ�����

	Intent intenttomytext = new Intent();	
	intenttomytext.setClass(MyChat.this, MyText.class);
	MyChat.this.startActivity(intenttomytext);
}
else if(v == broadabout){
	AlertDialog.Builder builder  = new Builder(MyChat.this);

	 builder.setTitle("�������ǡ�����У԰ƴ����" ) ;
	 builder.setMessage("ʹ��ɽ����ѧѧ�ź�ѡ�������¼��\n\n֧�ֵ�ͼѡ�����յ㣻\n\n�з��Ŷӣ�ɽ����ѧ���ѧԺ12������ʵ����;" ) ;
	 builder.setPositiveButton("ȷ��" ,  null );
	 builder.show(); 
}
else if(v == broadmyinfo){
	//ȥ���ҵ�����
	Intent intenttomyinfo = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttomyinfo.setClass(MyChat.this, MyInfo.class);
	MyChat.this.startActivity(intenttomyinfo);
}
else if(v == broadset){
//ȥ����
}
	}
	
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
	if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getRepeatCount() == 0) {
		Intent intentback = new Intent();	

		intentback.putExtra("iam", "backfrommap");
		intentback.setClass(MyChat.this, SearchOne.class);
		MyChat.this.startActivity(intentback);
    	System.out.println("iiiiiiiiiintent");
        return true;
    }
	else if(KeyEvent.KEYCODE_HOME==keyCode){

		 android.os.Process.killProcess(android.os.Process.myPid());

		//�رճ��������������.

		}
    return super.onKeyDown(keyCode, event);

}

}
