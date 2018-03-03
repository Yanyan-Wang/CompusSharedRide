package com.example.sharedcar;

import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.JPush.R;
import com.example.jpushdemo.ExampleUtil;
import com.example.jpushdemo.PushSetActivity;

public class Login extends Activity implements OnClickListener {
private ImageView i1,title;

private static final String TAG = "JPush";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        i1=(ImageView)findViewById(R.id.imageView5);
		i1.setOnClickListener(this);
		
		title=(ImageView)findViewById(R.id.title);
		setLayout(title,50,15);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent1 = new Intent();	
    	intent1.setClass(Login.this, Search.class);
    	Login.this.startActivity(intent1);
		
	}
   
	private void setTag(String tag){
//		 tagEdit = (EditText) findViewById(R.id.et_tag);
//		tag = tagEdit.getText().toString().trim();
		
       // ��� tag ����Ч��
		if (TextUtils.isEmpty(tag)) {
			Toast.makeText(Login.this,R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		// ","�����Ķ�� ת���� Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(Login.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//����JPush API����Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	} 
	
	private void setAlias(String alias){
//		aliasEdit = (EditText) findViewById(R.id.et_alias);
//		alias = aliasEdit.getText().toString().trim();
		if (TextUtils.isEmpty(alias)) {
			Toast.makeText(Login.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(Login.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		//����JPush API����Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}
	
	
	/**
	 *����֪ͨ��ʾ��ʽ - ��������
	 */
	private void setStyleBasic(){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(Login.this);
		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ������Զ���ʧ
		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //����Ϊ������ Notification.DEFAULT_SOUND�������𶯣� Notification.DEFAULT_VIBRATE��  
		JPushInterface.setPushNotificationBuilder(1, builder);
		Toast.makeText(Login.this, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 *����֪ͨ����ʽ - ����֪ͨ��Layout
	 */
	private void setStyleCustom(){
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(Login.this,R.layout.customer_notitfication_layout,R.id.icon, R.id.title, R.id.text);
		builder.layoutIconDrawable = R.drawable.ic_launcher;
		builder.developerArg0 = "developerArg2";
		JPushInterface.setPushNotificationBuilder(2, builder);
		Toast.makeText(Login.this,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
		else if(KeyEvent.KEYCODE_HOME==keyCode){

			 android.os.Process.killProcess(android.os.Process.myPid());

			//�رճ��������������.

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
