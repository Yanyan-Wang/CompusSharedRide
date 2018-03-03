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
		detailcontent = intent2.getStringExtra("detailcontent");//û�����ˡ�֮ǰ������ʱ����
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
	      date.setText(thisposter.Gtime.getYear()+"��"+thisposter.Gtime.getMonth()+"��"+thisposter.Gtime.getDay()+"��");
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
	    	RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)v.getLayoutParams();    // ȡ�ؼ� aaa ��ǰ�Ĳ��ֲ��� 
	    	System.out.println("iiiiiiiiidisplayheight"+(int)(scaleh*FirstActivity.displayheight)+","+scalew*FirstActivity.displaywidth);
	    	linearParams.height =(int)(scaleh*FirstActivity.displayheight);        // ���ؼ��ĸ�ǿ��
	    	linearParams.width = (int)(scalew*FirstActivity.displaywidth);
	    	v.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ� aaa
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
		//ȥ����ҳ��
	Intent intentpost = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intentpost.setClass(DigitalTwo.this, Post.class);
	DigitalTwo.this.startActivity(intentpost);
		}
else if(v == broadmychat){
	//ȥ�鿴��Ϣ

	Intent intenttomychat = new Intent();	
	intenttomychat.setClass(DigitalTwo.this, MyText.class);
	DigitalTwo.this.startActivity(intenttomychat);
}
else if(v == broadmylike){
	//ȥ���ҵ��ղ�
	Intent intenttocollect = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttocollect.setClass(DigitalTwo.this, MyCollect.class);
	DigitalTwo.this.startActivity(intenttocollect);
}
else if(v == broadmytext){
//ȥ���ҵ�����

	Intent intenttomytext = new Intent();	
	intenttomytext.setClass(DigitalTwo.this, MyText.class);
	DigitalTwo.this.startActivity(intenttomytext);
}
else if(v == broadabout){
	AlertDialog.Builder builder  = new Builder(DigitalTwo.this);
	 builder.setTitle("�������ǡ�����У԰ƴ����" ) ;
	 builder.setMessage("ʹ��ɽ����ѧѧ�ź�ѡ�������¼��\n\n֧�ֵ�ͼѡ�����յ㣻\n\n�з��Ŷӣ�ɽ����ѧ���ѧԺ12������ʵ����;" ) ;
	 builder.setPositiveButton("ȷ��" ,  null );
	 builder.show(); 
}
else if(v == broadmyinfo){
	//ȥ���ҵ�����
	Intent intenttomyinfo = new Intent();	
	System.out.println("iiiiiiiiiintent2");
	intenttomyinfo.setClass(DigitalTwo.this, MyInfo.class);
	DigitalTwo.this.startActivity(intenttomyinfo);
}
else if(v == broadset){
//ȥ����
}
		
else if(v==collectbutton){
	//������������ղص���������#############
	
	Trans ts2 = new Trans();
	ts2.st=ts2.st.collect;//������水ť
	ts2.stuid=FirstActivity.stuid;
	ts2.posterID=Integer.parseInt(posterID.substring(1))+"";
	TransBack tb2 = ts2.collectatext(ts2.stuid,ts2.posterID);
	
	Toast toast = Toast.makeText(DigitalTwo.this, "�ղسɹ�,��ͨ�������˵�����", Toast.LENGTH_SHORT);
	     toast.show();
}
		
	} 
	
	
	 /** 
     * ��ʼ��һЩ�ؼ������ݡ�������ȡ��Ļ�Ŀ�ȣ���content�����������ÿ�ȣ���menu�����������ÿ�Ⱥ�ƫ�ƾ���ȡ� 
     */  
    private void initValues() {  
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        screenWidth = window.getDefaultDisplay().getWidth();  
        content = findViewById(R.id.content);  
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

    		//�رճ��������������.

    		}
        return super.onKeyDown(keyCode, event);
    
    }
}
