package com.example.sharedcar;

import it.hua.beans.Trans;
import it.hua.beans.TransBack;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.JPush.R;

public class Register extends Activity implements OnClickListener {
	private ImageView i1,idpic,passwordpic,namepic,telepic,newusertittle;
	private TextView id,password,name,tele,zhuce;
	private EditText idedit,passwordedit,nameedit,teleedit;
	
	public Trans ts;
	
	public static String userid,userpassword,username,usertele;//这些为最后要发送的内容
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		ts  = new Trans();
		
		i1=(ImageView)findViewById(R.id.zhucebutton);
		resize(i1 , 2/5.0 , 1/7.0 , (int)(FirstActivity.displaywidth*(240/720.0)) , (int)(FirstActivity.displayheight*(850/1140.0)));
		i1.setOnClickListener(this);
		zhuce = (TextView)findViewById(R.id.zhuce);
		resize(zhuce , 1/6.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(320/720.0)) , (int)(FirstActivity.displayheight*(900/1140.0)));

		
//		newusertittle=(ImageView)findViewById(R.id.newusertittle);
//		resize(newusertittle , 1/2.0 , 1/8.0 , (int)(FirstActivity.displaywidth*(200/720.0)) , (int)(FirstActivity.displayheight*(30/1140.0)));
		
		idpic = (ImageView)findViewById(R.id.idpic);
		resize(idpic , 2/3.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(230/720.0)) , (int)(FirstActivity.displayheight*(205/1140.0)));
		passwordpic = (ImageView)findViewById(R.id.passwordpic);
		resize(passwordpic , 2/3.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(230/720.0)) , (int)(FirstActivity.displayheight*(375/1140.0)));
		namepic = (ImageView)findViewById(R.id.namepic);
		resize(namepic , 2/3.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(230/720.0)) , (int)(FirstActivity.displayheight*(545/1140.0)));
		telepic = (ImageView)findViewById(R.id.telepic);
		resize(telepic , 2/3.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(230/720.0)) , (int)(FirstActivity.displayheight*(715/1140.0)));
		
		id = (TextView)findViewById(R.id.idtext);
		resize(id , 1/4.0 , 1/13.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(230/1140.0)));

		password = (TextView)findViewById(R.id.passwordtext);
		resize(password , 1/4.0 , 1/13.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(395/1140.0)));
		name = (TextView)findViewById(R.id.nametext);
		resize(name , 1/4.0 , 1/13.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(570/1140.0)));
		tele = (TextView)findViewById(R.id.teletext);
		resize(tele , 1/4.0 , 1/13.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(740/1140.0)));
		
		idedit = (EditText)findViewById(R.id.idedit);
		resize(idedit , 7/12.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(252/720.0)) , (int)(FirstActivity.displayheight*(212/1140.0)));
		passwordedit = (EditText)findViewById(R.id.passwordedit);
		resize(passwordedit , 7/12.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(252/720.0)) , (int)(FirstActivity.displayheight*(382/1140.0)));
		nameedit = (EditText)findViewById(R.id.nameedit);
		resize(nameedit , 7/12.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(252/720.0)) , (int)(FirstActivity.displayheight*(553/1140.0)));
		teleedit = (EditText)findViewById(R.id.teleedit);
		resize(teleedit , 7/12.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(252/720.0)) , (int)(FirstActivity.displayheight*(722/1140.0)));
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_two, menu);
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

	  public boolean regsuccess=false;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==i1){
			//此处应该发送消息后判断
			
			userid = idedit.getText().toString();
			username = nameedit.getText().toString();
			userpassword = passwordedit.getText().toString();
			usertele = teleedit.getText().toString();
	
			
	//测试使用		
	//		userid ="201200301310";
	//		username="zjh";
	//		userpassword ="885176";
	//		usertele="18366110000";
			
			
			//点击注册后发送信息，获取服务器发来的是否密码匹配，即登陆是否成功
			ts.stuid = userid;
			ts.getStuid();
			ts.pwd=userpassword;
			ts.tele = usertele;
			ts.st = ts.st.register;
			ts.nickname=username;
			System.out.println(ts.stuid+","+ts.pwd+","+ts.tele+","+ts.nickname);
			//ts.register(ts.stuid, ts.pwd, ts.nickname, ts.tele);
			TransBack tb = ts.register(ts.stuid,ts.pwd,ts.nickname,ts.tele);
			System.out.println("transback");
			
			if(tb.state==tb.state.registersuccess){
				System.out.println("success1");
				regsuccess = true;
				FirstActivity.isLogin=true;
				
			}else if(tb.state == tb.state.wrongpassword){
				System.out.println("regste failed ");
				regsuccess = false;
				Toast toast = Toast.makeText(Register.this, "注册失败！可能是你的密码与学号不匹配（必须填选课密码）！", Toast.LENGTH_SHORT);
	      	     toast.show();
			}else if (tb.state == tb.state.problemoccursinserver||tb.state == tb.state.registerfail){
				regsuccess = false;
				Toast toast = Toast.makeText(Register.this, "注册失败！未知错误！", Toast.LENGTH_SHORT);
	      	     toast.show();
			}
			
			
			if(regsuccess){//如果注册成功了，转activity到search
				System.out.println("success2");
			Intent intenti1 = new Intent();	
			System.out.println("success3");
			//intenti1.putExtra("iam","register");
	    	intenti1.setClass(Register.this, FirstActivity.class);
	    	System.out.println("success4");
	    	Register.this.startActivity(intenti1);
			}
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
	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event){
	    	if (keyCode == KeyEvent.KEYCODE_BACK
	                && event.getRepeatCount() == 0) {
	    		Intent intentback = new Intent();	

				intentback.putExtra("iam", "backfrompost");
				intentback.setClass(Register.this, SearchOne.class);
				Register.this.startActivity(intentback);
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
