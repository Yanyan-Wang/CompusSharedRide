package com.example.sharedcar;
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

import com.JPush.R;


public class MyInfo  extends Activity implements OnTouchListener, OnClickListener{

	
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
	     * menu��ȫ��ʾʱ������content5�Ŀ��ֵ�� 
	     */  
	    private int menuPadding = 80;  
	  
	    /** 
	     * �����ݵĲ��֡� 
	     */  
	    private View content5;  
	  
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
	    
	    private int width,height;
	    private TextView mynickname,myid,mytele,mysex,save,mysexedit,mystuidedit;
	    private EditText nicknameedit,teleedit;
	    private ImageView picsave;
	    
	    public static String nicknamechange,telechange;
	    public TextView broadsearch,broadpost,broadmychat,broadmylike,broadset,broadmyinfo,broadabout,broadmytext;

	    public Trans ts;
	    
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_myinfo);  
	        initValues();  
	        
	        SearchOne.sAllActivitys.add(this);  
			
	        content5.setOnTouchListener(this);  
	        
	        //�˴�Ӧ����������� Ҫ���ȡ������Ϣ�����󣬽��ܵ�������Ӧ�ð��� ѧ�š��ǳơ��Ա𡢵绰
	        WindowManager wm = this.getWindowManager();
			 
		      width = wm.getDefaultDisplay().getWidth();
		      height = wm.getDefaultDisplay().getHeight();
		      
		      ts = new Trans();
		     
		     // if(tb.getTele()){
		   
		     
		      
		    
		      myid = (TextView)findViewById(R.id.myid5);//id�����޸�  ��ѧ��
				resize(myid , 3/8.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(30/720.0)) , (int)(FirstActivity.displayheight*(180/1140.0)));
				mystuidedit = (TextView)findViewById(R.id.mystuidedit);//id�����޸�  ��ѧ��
				resize(mystuidedit , 5/8.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(220/720.0)) , (int)(FirstActivity.displayheight*(180/1140.0)));
		      
				mynickname=(TextView)findViewById(R.id.mynickname5);
				resize(mynickname , 3/8.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(30/720.0)) , (int)(FirstActivity.displayheight*(295/1140.0)));
		      nicknameedit= (EditText)findViewById(R.id.nicknameedit5);
				resize(nicknameedit ,  0.55, 1/12.0 , (int)(FirstActivity.displaywidth*(250/720.0)) , (int)(FirstActivity.displayheight*(265/1140.0)));
		      mytele = (TextView)findViewById(R.id.mytele5);
				resize(mytele , 3/8.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(30/720.0)) , (int)(FirstActivity.displayheight*(405/1140.0)));
		      teleedit = (EditText)findViewById(R.id.teleedit5);
				resize(teleedit ,  0.55 , 1/12.0 , (int)(FirstActivity.displaywidth*(250/720.0)) , (int)(FirstActivity.displayheight*(375/1140.0)));
		      mysex = (TextView)findViewById(R.id.mysex5);//�����޸ģ����ݷ���������ҳ�õ�
				resize(mysex , 1/4.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(30/720.0)) , (int)(FirstActivity.displayheight*(520/1140.0)));
		      mysexedit = (TextView)findViewById(R.id.mysexedit);
		      resize(mysexedit , 0.55 , 1/12.0 , (int)(FirstActivity.displaywidth*(250/720.0)) , (int)(FirstActivity.displayheight*(520/1140.0)));
		      picsave = (ImageView)findViewById(R.id.picsave);
				resize(picsave , 2/5.0 , 1/9.0 , (int)(FirstActivity.displaywidth*(220/720.0)) , (int)(FirstActivity.displayheight*(800/1140.0)));
		      picsave.setOnClickListener(this);
		      save = (TextView)findViewById(R.id.savetext2);
				resize(save , 1/4.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(295/720.0)) , (int)(FirstActivity.displayheight*(835/1140.0)));
		      
				
				 
			      ts.st=ts.st.myinfo;
			      ts.stuid= FirstActivity.stuid;
			      TransBack tb =ts.MyInfoget(ts.stuid);
		      
				System.out.println("abd"+tb.getTele());
			 	  teleedit.setText(tb.getTele());
			    	//  }
			     nicknameedit.setText(tb.getNickname());
			     mysexedit.setText(tb.getSex());
			     mystuidedit.setText(FirstActivity.stuid);
			     
		      broadsearch = (TextView)findViewById(R.id.broadsearch5);
		        broadsearch.setOnClickListener(this);
		        broadpost = (TextView)findViewById(R.id.broadpost5);
		        broadpost.setOnClickListener(this);
		        broadmylike = (TextView)findViewById(R.id.broadmylike5);
		        broadmylike.setOnClickListener(this);
		        broadmyinfo = (TextView)findViewById(R.id.broadmyinfo5);
		        broadmyinfo.setOnClickListener(this);
		        broadmytext = (TextView)findViewById(R.id.broadmytext5);
		        broadmytext.setOnClickListener(this);
		        broadset = (TextView)findViewById(R.id.broadset5);
		        broadset.setOnClickListener(this);
		        broadmychat = (TextView)findViewById(R.id.broadmychat5);
		        broadmychat.setOnClickListener(this);
		        broadabout = (TextView)findViewById(R.id.broadabout5);
		        broadabout.setOnClickListener(this);
	    }
	    
	    private void resize(View v,double scalew,double scaleh,int x,int y)    {
	    	RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)v.getLayoutParams();    // ȡ�ؼ� aaa ��ǰ�Ĳ��ֲ��� 
	    	System.out.println("iiiiiiiiidisplayheight"+(int)(scaleh*FirstActivity.displayheight)+","+scalew*FirstActivity.displaywidth);
	    	linearParams.height =(int)(scaleh*FirstActivity.displayheight);        // ���ؼ��ĸ�ǿ��
	    	linearParams.width = (int)(scalew*FirstActivity.displaywidth);
	    	v.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ� aaa
	    	setLayout(v,x,y);
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
	            // ��ָ̧��ʱ�������жϵ�ǰ���Ƶ���ͼ���Ӷ������ǹ�����menu���棬���ǹ�����content5����  
	            xUp = event.getRawX();  
	            if (wantToShowMenu()) {  
	                if (shouldScrollToMenu()) {  
	                    scrollToMenu();  
	                } else {  
	                    scrollTocontent();  
	                }  
	            } else if (wantToShowcontent()) {  
	                if (shouldScrollTocontent()) {  
	                    scrollTocontent();  
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
	     * ��ʼ��һЩ�ؼ������ݡ�������ȡ��Ļ�Ŀ�ȣ���content5�����������ÿ�ȣ���menu�����������ÿ�Ⱥ�ƫ�ƾ���ȡ� 
	     */  
	    private void initValues() {  
	        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
	        screenWidth = window.getDefaultDisplay().getWidth();  
	        content5 = findViewById(R.id.content5);  
	        menu = findViewById(R.id.menu5);  
	        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();  
	        // ��menu�Ŀ������Ϊ��Ļ��ȼ�ȥmenuPadding  
	        menuParams.width = screenWidth - menuPadding;  
	        // ���Ե��ֵ��ֵΪmenu��ȵĸ���  
	        leftEdge = -menuParams.width;  
	        // menu��leftMargin����Ϊ���Ե��ֵ��������ʼ��ʱmenu�ͱ�Ϊ���ɼ�  
	        menuParams.leftMargin = leftEdge;  
	        // ��content5�Ŀ������Ϊ��Ļ���  
	        content5.getLayoutParams().width = screenWidth;  
	    }  
	    /** 
	     * �жϵ�ǰ���Ƶ���ͼ�ǲ�������ʾcontent5�������ָ�ƶ��ľ����Ǹ������ҵ�ǰmenu�ǿɼ��ģ�����Ϊ��ǰ��������Ҫ��ʾcontent5�� 
	     *  
	     * @return ��ǰ��������ʾcontent5����true�����򷵻�false�� 
	     */  
	    private boolean wantToShowcontent() {  
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
	     * �ж��Ƿ�Ӧ�ù�����content5չʾ�����������ָ�ƶ��������menuPadding������Ļ��1/2�� 
	     * ������ָ�ƶ��ٶȴ���SNAP_VELOCITY�� ����ΪӦ�ù�����content5չʾ������ 
	     *  
	     * @return ���Ӧ�ù�����content5չʾ��������true�����򷵻�false�� 
	     */  
	    private boolean shouldScrollTocontent() {  
	        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;  
	    }  
	  
	    /** 
	     * ����Ļ������menu���棬�����ٶ��趨Ϊ30. 
	     */  
	    private void scrollToMenu() {  
	        new ScrollTask().execute(30);  
	    }  
	  
	    /** 
	     * ����Ļ������content5���棬�����ٶ��趨Ϊ-30. 
	     */  
	    private void scrollTocontent() {  
	        new ScrollTask().execute(-30);  
	    }  
	  
	    /** 
	     * ����VelocityTracker���󣬲�������content5����Ļ����¼����뵽VelocityTracker���С� 
	     *  
	     * @param event 
	     *            content5����Ļ����¼� 
	     */  
	    private void createVelocityTracker(MotionEvent event) {  
	        if (mVelocityTracker == null) {  
	            mVelocityTracker = VelocityTracker.obtain();  
	        }  
	        mVelocityTracker.addMovement(event);  
	    }  
	  
	    /** 
	     * ��ȡ��ָ��content5���滬�����ٶȡ� 
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
			if(v==picsave){
				nicknamechange = nicknameedit.getText().toString();
				telechange = teleedit.getText().toString();
				
				//@@@@@@@@�˴�Ӧ������������޸ĺ����Ϣ  ����nicknamechange��string����telechange��string��
				ts.nickname = nicknamechange;
				ts.stuid = FirstActivity.stuid;
				ts.tele = telechange;
				ts.st= ts.st.myinfoedit;
				TransBack tb = ts.myinfo(ts.stuid, ts.nickname, ts.tele);
				
			//	if(tb.state==tb.state.editinfosuccess){
					Toast toast = Toast.makeText(MyInfo.this, "�޸���Ϣ�ɹ���", Toast.LENGTH_SHORT);
				     toast.show();
					Intent intenttosearch = new Intent();	
					System.out.println("iiiiiiiiiintent2");
					intenttosearch.setClass(MyInfo.this, SearchOne.class);
					MyInfo.this.startActivity(intenttosearch);	
				//}
				//else if(tb.state  ==tb.state.editinfofail){
				//	Toast toast = Toast.makeText(MyInfo.this, "�޸���Ϣʧ�ܣ����������Ӵ���", Toast.LENGTH_SHORT);
				 //    toast.show();
			//	}
				
			}
			else if(v == broadsearch){
				Intent intenttosearch = new Intent();	
				intenttosearch.putExtra("iam", "myinfofrombroad");

				intenttosearch.setClass(MyInfo.this, SearchOne.class);
				MyInfo.this.startActivity(intenttosearch);
			}
	else if(v == broadpost){
			//ȥ����ҳ��
		Intent intentpost = new Intent();	
		System.out.println("iiiiiiiiiintent2");
		intentpost.setClass(MyInfo.this, Post.class);
		MyInfo.this.startActivity(intentpost);
			}
	else if(v == broadmychat){

		Intent intenttomychat = new Intent();	
		intenttomychat.setClass(MyInfo.this, MyText.class);
		MyInfo.this.startActivity(intenttomychat);
		//ȥ�鿴��Ϣ
	}
	else if(v == broadmylike){
		//ȥ���ҵ��ղ�
		Intent intenttocollect = new Intent();	
		System.out.println("iiiiiiiiiintent2");
		intenttocollect.setClass(MyInfo.this, MyCollect.class);
		MyInfo.this.startActivity(intenttocollect);
	}
	else if(v == broadmytext){
	//ȥ���ҵ�����
		Intent intenttomytext = new Intent();	
		intenttomytext.setClass(MyInfo.this, MyText.class);
    	MyInfo.this.startActivity(intenttomytext);
	}
	else if(v == broadabout){
		AlertDialog.Builder builder  = new Builder(MyInfo.this);

		 builder.setTitle("�������ǡ�����У԰ƴ����" ) ;
		 builder.setMessage("ʹ��ɽ����ѧѧ�ź�ѡ�������¼��\n\n֧�ֵ�ͼѡ�����յ㣻\n\n�з��Ŷӣ�ɽ����ѧ���ѧԺ12������ʵ����;" ) ;
		 builder.setPositiveButton("ȷ��" ,  null );
		 builder.show(); 
	}
	else if(v == broadmyinfo){
		//���ڱ�ҳ
	}
	else if(v == broadset){
	//ȥ����
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
	  	public boolean onKeyDown(int keyCode, KeyEvent event){
	  		if (keyCode == KeyEvent.KEYCODE_BACK
	  	            && event.getRepeatCount() == 0) {
	  			Intent intentback = new Intent();	

	  			intentback.putExtra("iam", "backfrommap");
	  			intentback.setClass(MyInfo.this, SearchOne.class);
	  			MyInfo.this.startActivity(intentback);
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


