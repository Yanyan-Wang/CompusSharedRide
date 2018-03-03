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
     * ��ʼ��һЩ�ؼ������ݡ�������ȡ��Ļ�Ŀ�ȣ���content�����������ÿ�ȣ���menu�����������ÿ�Ⱥ�ƫ�ƾ���ȡ� 
     */  
    private void initValues() {  
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        screenWidth = window.getDefaultDisplay().getWidth();  
        content = findViewById(R.id.content);  
        menu = findViewById(R.id.menu3);  
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
		if(v==ToPost){
			postid=idedit.getText().toString();
			postdate = "2014-"+dateedit.getText().toString();
			posttime = timeedit.getText().toString()+"-00";
			postfrom = fromedit.getText().toString();
			posttele = teleedit.getText().toString();
			postto = toedit.getText().toString();
			extratext = extracontent.getText().toString();
			//@@@@@�˴�Ӧ������������ͷ�����Ϣ
			
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
		 
		 posters = tb.getPosters();//��searchone�����poster����
		 int lengthof_posters= posters.size();
	    	//String poster_String[]=new String[lengthof_posters];
		 ArrayList<String> poster_String = new ArrayList();
	    	for(int i=0;i<lengthof_posters;i++){
	    		poster_String.add("ID:"+posters.get(i).getFormatedPosterID()+"#"+posters.get(i).nickname+"("+posters.get(i).stuid+"):"+posters.get(i).Gtime
	    				+",��    "+posters.get(i).start+" ��  "+posters.get(i).destination);
	    		//posterID ��ʽ��x00000001, ��9λ
	    	}
		 
		 Intent intent1 = new Intent();
		 intent1.putExtra("iam", "post");
			intent1.putStringArrayListExtra("posters",poster_String);
	    	intent1.setClass(Post.this, SearchOne.class);
	    	Post.this.startActivity(intent1);
		 
		 
		
		}else if(v == broadpost){
			//���ڱ�ҳ
				}
		else if(v == broadmychat){
			//ȥ�鿴��Ϣ

			Intent intenttomychat = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intenttomychat.setClass(Post.this, MyChat.class);
			Post.this.startActivity(intenttomychat);
		}
		else if(v == broadmylike){
			//ȥ���ҵ��ղ�
			Intent intenttocollect = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intenttocollect.setClass(Post.this, MyCollect.class);
			Post.this.startActivity(intenttocollect);
		}
		else if(v == broadmytext){
		//ȥ���ҵ�����

			Intent intenttomytext = new Intent();	
			intenttomytext.setClass(Post.this, MyText.class);
	    	Post.this.startActivity(intenttomytext);
		}
		else if(v == broadabout){
			AlertDialog.Builder builder  = new Builder(Post.this);

			 builder.setTitle("�������ǡ�����У԰ƴ����" ) ;
			 builder.setMessage("ʹ��ɽ����ѧѧ�ź�ѡ�������¼��\n\n֧�ֵ�ͼѡ�����յ㣻\n\n�з��Ŷӣ�ɽ����ѧ���ѧԺ12������ʵ����;" ) ;
			 builder.setPositiveButton("ȷ��" ,  null );
			 builder.show(); 
		}
		else if(v == broadmyinfo){
			//ȥ���ҵ�����
			Intent intenttomyinfo = new Intent();	
			System.out.println("iiiiiiiiiintent2");
			intenttomyinfo.setClass(Post.this, MyInfo.class);
			Post.this.startActivity(intenttomyinfo);
		}
		else if(v == broadset){
		//ȥ����
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
    private void resize(View v,double scalew,double scaleh,int x,int y)    {
    	RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)v.getLayoutParams();    // ȡ�ؼ� aaa ��ǰ�Ĳ��ֲ��� 
    	System.out.println("iiiiiiiiidisplayheight"+(int)(scaleh*FirstActivity.displayheight)+","+scalew*FirstActivity.displaywidth);
    	linearParams.height =(int)(scaleh*FirstActivity.displayheight);        // ���ؼ��ĸ�ǿ��
    	linearParams.width = (int)(scalew*FirstActivity.displaywidth);
    	v.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ� aaa
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

//�رճ��������������.

}
        return super.onKeyDown(keyCode, event);
    
    }
    
}
