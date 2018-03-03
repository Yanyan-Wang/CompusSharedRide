package com.example.sharedcar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
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
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.JPush.R;
import com.example.jpushdemo.ExampleUtil;

public class Edit extends Activity implements OnTouchListener, OnClickListener {
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

	public static boolean isForeground = true;
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	String str;
	private boolean bConnected = false;

	private boolean firstChat = true;
	private String flag = "1";
	String AliasSelf = "fuc";
	String AliasOther = "oth";
	// String stuidoftheonechatto,mystuid;

	private TextView name;
	private ImageView chatwith, chatcontent, chatnow;
	private Button bsend;
	private EditText chatedit;
	public TextView broadsearch, broadpost, broadmychat, broadmylike, broadset,
			broadmyinfo, broadabout, broadmytext;
	Thread tRecv = new Thread(new RecvThread());

	private String sendcontent;

	public ScrollView sv;
	public TextView Chatcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_edit);

		
		SearchOne.sAllActivitys.add(this);  
		
		// �������
		initValues();

		content.setOnTouchListener(this);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		FirstActivity.displaywidth = displayMetrics.widthPixels;
		FirstActivity.displayheight = displayMetrics.heightPixels;

		broadsearch = (TextView) findViewById(R.id.broadsearch6);
		broadsearch.setOnClickListener(this);
		broadpost = (TextView) findViewById(R.id.broadpost6);
		broadpost.setOnClickListener(this);
		broadmylike = (TextView) findViewById(R.id.broadmylike6);
		broadmylike.setOnClickListener(this);
		broadmyinfo = (TextView) findViewById(R.id.broadmyinfo6);
		broadmyinfo.setOnClickListener(this);
		broadmytext = (TextView) findViewById(R.id.broadmytext6);
		broadmytext.setOnClickListener(this);
		broadset = (TextView) findViewById(R.id.broadset6);
		broadset.setOnClickListener(this);
		broadmychat = (TextView) findViewById(R.id.broadmychat6);
		broadmychat.setOnClickListener(this);
		broadabout = (TextView) findViewById(R.id.broadabout6);
		broadabout.setOnClickListener(this);
		name = (TextView) findViewById(R.id.nickname);
		resize(name, 1 / 3.0, 1 / 12.0,
				(int) (FirstActivity.displaywidth * (230 / 720.0)),
				(int) (FirstActivity.displayheight * (40 / 1140.0)));
		

		Chatcontent = (TextView) findViewById(R.id.Chatcontentview1);// ����֮����ʾ���ݵ�����
		resize(Chatcontent, 5 / 7.0, 1 / 3.0,
				(int) (FirstActivity.displaywidth * (90 / 720.0)),
				(int) (FirstActivity.displayheight * (200 / 1140.0)));

		bsend = (Button) findViewById(R.id.bsend);
		resize(bsend, 1 / 6.0, 1 / 11.0,
				(int) (FirstActivity.displaywidth * (475 / 720.0)),
				(int) (FirstActivity.displayheight * (890 / 1140.0)));
		bsend.setOnClickListener(this);

		chatedit = (EditText) findViewById(R.id.chatedit);
		resize(chatedit, 6 / 16.0, 1 / 11.0,
				(int) (FirstActivity.displaywidth * (50 / 720.0)),
				(int) (FirstActivity.displayheight * (900 / 1140.0)));
		Intent intentname = getIntent();

		String nickname = null;
		nickname = intentname.getStringExtra("nickname");

		// if(nickname!=null){
		AliasOther = intentname.getStringExtra("stuidoftheonechatto");
		AliasSelf = FirstActivity.stuid;
		// }

		if (nickname == null) {
			firstChat = false;
			Bundle bundle = intentname.getExtras();
			// String title =
			// bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
			sendcontent = bundle.getString(JPushInterface.EXTRA_ALERT);
			System.out.println(sendcontent + "adaadadd");
			String[] con2 = sendcontent.split(":");
			AliasOther = con2[1];
			Chatcontent.setText(Chatcontent.getText().toString() + con2[1] + ": "
					+ con2[3] + "\n");
			

		}
		name.setText(nickname);
		tRecv.start();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	private void resize(View v, double scalew, double scaleh, int x, int y) {
		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) v
				.getLayoutParams(); // ȡ�ؼ� aaa ��ǰ�Ĳ��ֲ���
		System.out.println("iiiiiiiiidisplayheight"
				+ (int) (scaleh * FirstActivity.displayheight) + "," + scalew
				* FirstActivity.displaywidth);
		linearParams.height = (int) (scaleh * FirstActivity.displayheight); // ���ؼ��ĸ�ǿ��
		linearParams.width = (int) (scalew * FirstActivity.displaywidth);
		v.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ� aaa
		setLayout(v, x, y);
	}

	public static void setLayoutX(View view, int x) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(x, margin.topMargin, x + margin.width,
				margin.bottomMargin);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
	}

	/*
	 * ���ÿؼ����ڵ�λ��Y�����Ҳ��ı��ߣ� YΪ����λ�ã���ʱX���ܹ�0
	 */
	public static void setLayoutY(View view, int y) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(margin.leftMargin, y, margin.rightMargin, y
				+ margin.height);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
	}

	public static void setLayout(View view, int x, int y) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(x, y, x + margin.width, y + margin.height);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
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

	@Override
	protected void onResume() {
		super.onResume();

		isForeground = true;
		// try {
		// dos.writeUTF(AliasSelf+":"+" "+":"+" "+":"+"!@#$");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	@Override
	protected void onPause() {
		super.onPause();
		// disconnect();
		isForeground = false;
		try {   flag="0";
			dos.writeUTF(AliasSelf + ":" + " " + ":" + flag + ":" + "%^&*");
			    flag="1";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
		// disconnect();
	}

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
		return xUp - xDown > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * �ж��Ƿ�Ӧ�ù�����contentչʾ�����������ָ�ƶ��������menuPadding������Ļ��1/2��
	 * ������ָ�ƶ��ٶȴ���SNAP_VELOCITY�� ����ΪӦ�ù�����contentչʾ������
	 * 
	 * @return ���Ӧ�ù�����contentչʾ��������true�����򷵻�false��
	 */
	private boolean shouldScrollToContent() {
		return xDown - xUp + menuPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
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
		if (v == bsend) {
			// String ssend = chatedit.getText().toString();
			// chatedit.setText("");
			// String scontent = Chatcontent.getText().toString();
			// Chatcontent.setText(scontent+"\n�ң�"+ssend);

			String ssend = chatedit.getText().toString();
			chatedit.setText("");
			String scontent = Chatcontent.getText().toString();
			try {
				if (firstChat) {
					flag = "0";
					dos.writeUTF(AliasSelf + ":" + AliasOther + ":" + flag
							+ ":" + ssend);
					firstChat = false;
					flag = "1";
				} else
					dos.writeUTF(AliasSelf + ":" + AliasOther + ":" + flag
							+ ":" + ssend);
				Toast.makeText(
						this,
						AliasSelf + ":" + AliasOther + ":" + flag + ":" + ssend,
						Toast.LENGTH_LONG).show();
				// dos.writeUTF(ssend);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Chatcontent.setText(scontent + "\n�ң�" + ssend + "\n");
		} else if (v == broadsearch) {
			System.out.println("iiiiiiibroadsearch");
			// ���ڱ�ҳ����
			Intent intenttourist = new Intent();
			intenttourist.setClass(Edit.this, SearchOne.class);
			intenttourist.putExtra("iam", "edit");
			Edit.this.startActivity(intenttourist);
		} else if (v == broadpost) {
			// ȥ����ҳ��
			Intent intentpost = new Intent();
			System.out.println("iiiiiiiiiintent2");
			intentpost.setClass(Edit.this, Post.class);
			Edit.this.startActivity(intentpost);
		} else if (v == broadmychat) {
			// ȥ�鿴��Ϣ

			Intent intenttomychat = new Intent();
			intenttomychat.setClass(Edit.this, MyText.class);
			Edit.this.startActivity(intenttomychat);
		} else if (v == broadmylike) {
			// ȥ���ҵ��ղ�
			Intent intenttocollect = new Intent();
			System.out.println("iiiiiiiiiintent2");
			intenttocollect.setClass(Edit.this, MyCollect.class);
			Edit.this.startActivity(intenttocollect);
		} else if (v == broadmytext) {
			// ȥ���ҵ�����

			Intent intenttomytext = new Intent();
			intenttomytext.setClass(Edit.this, MyText.class);
			Edit.this.startActivity(intenttomytext);
		} else if (v == broadabout) {
			AlertDialog.Builder builder = new Builder(Edit.this);

			builder.setTitle("�������ǡ�����У԰ƴ����");
			builder.setMessage("ʹ��ɽ����ѧѧ�ź�ѡ�������¼��\n\n֧�ֵ�ͼѡ�����յ㣻\n\n�з��Ŷӣ�ɽ����ѧ���ѧԺ12������ʵ����;");
			builder.setPositiveButton("ȷ��", null);
			builder.show();
		} else if (v == broadmyinfo) {
			// ȥ���ҵ�����
			Intent intenttomyinfo = new Intent();
			System.out.println("iiiiiiiiiintent2");
			intenttomyinfo.setClass(Edit.this, MyInfo.class);
			Edit.this.startActivity(intenttomyinfo);
		} else if (v == broadset) {
			// ȥ����
		}
	}

	private class RecvThread implements Runnable {

		public void run() {
			try {
				try {
					s = new Socket("211.87.227.34", 80);
					dos = new DataOutputStream(s.getOutputStream());
					dis = new DataInputStream(s.getInputStream());
					System.out.println("connected!");
					try {
						dos.writeUTF(AliasSelf + ":" + " " + ":" + "1" + ":"
								+ "!@#$");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				while (bConnected) {
					str = dis.readUTF();
					// System.out.println(str);
					// taContent.setText(taContent.getText() + str + '\n');
					mHandler.sendMessage(mHandler.obtainMessage());// �������̷߳��͵�����,
					// ���ô�����������̸߳���UI.
				}
			} catch (SocketException e) {
				System.out.println("�˳��ˣ�bye!");
			} catch (EOFException e) {
				System.out.println("�Ƴ��ˣ�bye - bye!");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/*
		 * try { bConnected = false; tRecv.join(); } catch(InterruptedException
		 * e) { e.printStackTrace(); } finally { try { dos.close(); dis.close();
		 * s.close(); } catch (IOException e) { e.printStackTrace(); } }
		 */
	}

	public void disconnect() {
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Chatcontent.setText(Chatcontent.getText().toString() + str + "\n");
		}
	};

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
			// String messge = intent.getStringExtra(KEY_MESSAGE);
			// String extras = intent.getStringExtra(KEY_EXTRAS);
			// StringBuilder showMsg = new StringBuilder();
			// showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
			// if (!ExampleUtil.isEmpty(extras)) {
			// showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
			// }
			// setCostomMsg(showMsg.toString());
			// }
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(messge);
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(extras);
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {
		// if (null != msgText) {
		// msgText.setText(msg);
		String[] cc = msg.split(":");

		Chatcontent.setText(Chatcontent.getText().toString() + cc[1] + ": "
				+ cc[3] + "\n");

		Chatcontent.setVisibility(android.view.View.VISIBLE);
		// msgText.setVisibility(android.view.View.VISIBLE);
		// }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intentback = new Intent();

			intentback.putExtra("iam", "backfrommap");
			intentback.setClass(Edit.this, SearchOne.class);
			Edit.this.startActivity(intentback);
			System.out.println("iiiiiiiiiintent");
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

}
