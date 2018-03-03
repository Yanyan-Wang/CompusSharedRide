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
		
		// 侧栏相关
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
		

		Chatcontent = (TextView) findViewById(R.id.Chatcontentview1);// 发送之后显示内容到这里
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
				.getLayoutParams(); // 取控件 aaa 当前的布局参数
		System.out.println("iiiiiiiiidisplayheight"
				+ (int) (scaleh * FirstActivity.displayheight) + "," + scalew
				* FirstActivity.displaywidth);
		linearParams.height = (int) (scaleh * FirstActivity.displayheight); // 当控件的高强制
		linearParams.width = (int) (scalew * FirstActivity.displaywidth);
		v.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件 aaa
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
	 * 设置控件所在的位置Y，并且不改变宽高， Y为绝对位置，此时X可能归0
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
		return xUp - xDown > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
	 * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
	 * 
	 * @return 如果应该滚动将content展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToContent() {
		return xDown - xUp + menuPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
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
		// TODO Auto-generated method stub
		if (v == bsend) {
			// String ssend = chatedit.getText().toString();
			// chatedit.setText("");
			// String scontent = Chatcontent.getText().toString();
			// Chatcontent.setText(scontent+"\n我："+ssend);

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
			Chatcontent.setText(scontent + "\n我：" + ssend + "\n");
		} else if (v == broadsearch) {
			System.out.println("iiiiiiibroadsearch");
			// 留在本页面上
			Intent intenttourist = new Intent();
			intenttourist.setClass(Edit.this, SearchOne.class);
			intenttourist.putExtra("iam", "edit");
			Edit.this.startActivity(intenttourist);
		} else if (v == broadpost) {
			// 去发帖页面
			Intent intentpost = new Intent();
			System.out.println("iiiiiiiiiintent2");
			intentpost.setClass(Edit.this, Post.class);
			Edit.this.startActivity(intentpost);
		} else if (v == broadmychat) {
			// 去查看消息

			Intent intenttomychat = new Intent();
			intenttomychat.setClass(Edit.this, MyText.class);
			Edit.this.startActivity(intenttomychat);
		} else if (v == broadmylike) {
			// 去看我的收藏
			Intent intenttocollect = new Intent();
			System.out.println("iiiiiiiiiintent2");
			intenttocollect.setClass(Edit.this, MyCollect.class);
			Edit.this.startActivity(intenttocollect);
		} else if (v == broadmytext) {
			// 去看我的贴子

			Intent intenttomytext = new Intent();
			intenttomytext.setClass(Edit.this, MyText.class);
			Edit.this.startActivity(intenttomytext);
		} else if (v == broadabout) {
			AlertDialog.Builder builder = new Builder(Edit.this);

			builder.setTitle("关于我们———校园拼车族");
			builder.setMessage("使用山东大学学号和选课密码登录；\n\n支持地图选择起终点；\n\n研发团队：山东大学软件学院12级创新实验室;");
			builder.setPositiveButton("确认", null);
			builder.show();
		} else if (v == broadmyinfo) {
			// 去看我的资料
			Intent intenttomyinfo = new Intent();
			System.out.println("iiiiiiiiiintent2");
			intenttomyinfo.setClass(Edit.this, MyInfo.class);
			Edit.this.startActivity(intenttomyinfo);
		} else if (v == broadset) {
			// 去设置
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
					mHandler.sendMessage(mHandler.obtainMessage());// 接受子线程发送的数据,
					// 并用此数据配合主线程更新UI.
				}
			} catch (SocketException e) {
				System.out.println("退出了，bye!");
			} catch (EOFException e) {
				System.out.println("推出了，bye - bye!");
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
