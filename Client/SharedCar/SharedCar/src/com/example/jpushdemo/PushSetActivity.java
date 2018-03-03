//package com.example.jpushdemo;
//
//import java.net.URISyntaxException;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import com.JPush.R;
//import com.example.sharedcar.Edit;
//import com.example.sharedcar.FirstActivity;
//
//
//
//import android.app.Notification;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//import cn.jpush.android.api.BasicPushNotificationBuilder;
//import cn.jpush.android.api.InstrumentedActivity;
//import cn.jpush.android.api.JPushInterface;
//
//import cn.jpush.android.api.TagAliasCallback;
//
//
//public class PushSetActivity extends InstrumentedActivity  {
//    private static final String TAG = "JPush";
//    
//
//
//	
//	@Override
//	public void onCreate(Bundle icicle) {
//		super.onCreate(icicle);
//	
//			Intent intent = getIntent();
//			String value = intent.getStringExtra("alias");
//			setAlias(value);
//			Intent intenttouch = new Intent();
//			intenttouch.setClass(PushSetActivity.this,Edit.class);
//			PushSetActivity.this.startActivity(intenttouch);
//	
//	}
//	
//	public void setTag(String tag){
//        // ��� tag ����Ч��
//		if (TextUtils.isEmpty(tag)) {
//			Toast.makeText(PushSetActivity.this,R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
//			return;
//		}
//		
//		// ","�����Ķ�� ת���� Set
//		String[] sArray = tag.split(",");
//		Set<String> tagSet = new LinkedHashSet<String>();
//		for (String sTagItme : sArray) {
//			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
//				Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//				return;
//			}
//			tagSet.add(sTagItme);
//		}
//		
//		//����JPush API����Tag
//		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tag));
//		System.out.println("asdf111122233333");
//
//	} 
//	
//	public void setAlias(String alias){
//		if (TextUtils.isEmpty(alias)) {
//			Toast.makeText(PushSetActivity.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
//			return;
//		}
//		if (!ExampleUtil.isValidTagAndAlias(alias)) {
//			Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//			return;
//		}
//		
//		//����JPush API����Alias
//		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
//	}
//	
//	
//	/**
//	 *����֪ͨ��ʾ��ʽ - ��������
//	 */
//	public void setStyleBasic(){
//		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(PushSetActivity.this);
//		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ������Զ���ʧ
//		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //����Ϊ������ Notification.DEFAULT_SOUND�������𶯣� Notification.DEFAULT_VIBRATE��  
//		JPushInterface.setPushNotificationBuilder(1, builder);
//		Toast.makeText(PushSetActivity.this, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
//	}
//
//	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs ;
//            switch (code) {
//            case 0:
//                logs = "Set tag and alias success";
//                Log.i(TAG, logs);
//                break;
//                
//            case 6002:
//                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                Log.i(TAG, logs);
//                if (ExampleUtil.isConnected(getApplicationContext())) {
//                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                } else {
//                	Log.i(TAG, "No network");
//                }
//                break;
//            
//            default:
//                logs = "Failed with errorCode = " + code;
//                Log.e(TAG, logs);
//            }
//            
//            ExampleUtil.showToast(logs, getApplicationContext());
//        }
//	    
//	};
//	
//    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
//
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs ;
//            switch (code) {
//            case 0:
//                logs = "Set tag and alias success";
//                Log.i(TAG, logs);
//                break;
//                
//            case 6002:
//                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                Log.i(TAG, logs);
//                if (ExampleUtil.isConnected(getApplicationContext())) {
//                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
//                } else {
//                	Log.i(TAG, "No network");
//                }
//                break;
//            
//            default:
//                logs = "Failed with errorCode = " + code;
//                Log.e(TAG, logs);
//            }
//            
//            ExampleUtil.showToast(logs, getApplicationContext());
//        }
//        
//    };
//    
//	private static final int MSG_SET_ALIAS = 1001;
//	private static final int MSG_SET_TAGS = 1002;
//	
//	
//
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//            case MSG_SET_ALIAS:
//                Log.d(TAG, "Set alias in handler.");
//                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
//                break;
//                
//            case MSG_SET_TAGS:
//                Log.d(TAG, "Set tags in handler.");
//                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
//                break;
//                
//            default:
//                Log.i(TAG, "Unhandled msg - " + msg.what);
//            }
//        }
//    };
//	
//}
//





package com.example.jpushdemo;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import  com.JPush.R;
import com.example.sharedcar.Edit;

import cn.jpush.android.api.TagAliasCallback;


public class PushSetActivity extends Activity  {
    private static final String TAG = "JPush";
    
//	Button mSetTag;
//	Button mSetAlias;
//	Button mStyleBasic;
//	Button mStyleCustom;
//	Button mSetPushTime;
//	EditText aliasEdit,tagEdit;
	String alias,tag;

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.push_set_dialog);
//		init();
//		initListener();
	}
	
//	private void init(){
//	    mSetTag = (Button)findViewById(R.id.bt_tag);
//	    mSetAlias = (Button)findViewById(R.id.bt_alias);
//	    mStyleBasic = (Button)findViewById(R.id.setStyle1);
//	    mStyleCustom = (Button)findViewById(R.id.setStyle2);
//	    mSetPushTime = (Button)findViewById(R.id.bu_setTime);
//	}
	
//	private void initListener(){
//		mSetTag.setOnClickListener(this);
//		mSetAlias.setOnClickListener(this);
//		mStyleBasic.setOnClickListener(this);
//		mStyleCustom.setOnClickListener(this);
//		mSetPushTime.setOnClickListener(this);
//	}
//	
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt_tag:
//			setTag();
//			break;
//		case R.id.bt_alias:
//			setAlias();
//			break;
//		case R.id.setStyle1:
//			setStyleBasic();
//			break;
//		case R.id.setStyle2:
//			setStyleCustom();
//			break;
//		case R.id.bu_setTime:
//			Intent intenttouch = new Intent();
//			intenttouch.putExtra("alias",alias);
//			intenttouch.putExtra("tag",tag);
//			intenttouch.setClass(PushSetActivity.this,Edit.class);
//			PushSetActivity.this.startActivity(intenttouch);
//			break;
//		}
//	}
//	
	private void setTag(String tag){
//		 tagEdit = (EditText) findViewById(R.id.et_tag);
//		tag = tagEdit.getText().toString().trim();
		
        // ��� tag ����Ч��
		if (TextUtils.isEmpty(tag)) {
			Toast.makeText(PushSetActivity.this,R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		// ","�����Ķ�� ת���� Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
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
			Toast.makeText(PushSetActivity.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		//����JPush API����Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}
	
	
	/**
	 *����֪ͨ��ʾ��ʽ - ��������
	 */
	private void setStyleBasic(){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(PushSetActivity.this);
		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ������Զ���ʧ
		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //����Ϊ������ Notification.DEFAULT_SOUND�������𶯣� Notification.DEFAULT_VIBRATE��  
		JPushInterface.setPushNotificationBuilder(1, builder);
		Toast.makeText(PushSetActivity.this, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 *����֪ͨ����ʽ - ����֪ͨ��Layout
	 */
	private void setStyleCustom(){
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(PushSetActivity.this,R.layout.customer_notitfication_layout,R.id.icon, R.id.title, R.id.text);
		builder.layoutIconDrawable = R.drawable.ic_launcher;
		builder.developerArg0 = "developerArg2";
		JPushInterface.setPushNotificationBuilder(2, builder);
		Toast.makeText(PushSetActivity.this,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			finish();
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