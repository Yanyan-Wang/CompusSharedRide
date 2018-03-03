package com.example.sharedcar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.JPush.R;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class DigitalOne extends Activity implements OnClickListener {
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	
	//��λ���new
	private MapController mMapController = null;
	public MKMapViewListener mMapListener = null;
	MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
	LocationData locData = new LocationData();
	
	public LocationClient mLocationClient = null;
//	public BDLocationListener myListener = new MyLocationListenner();//@@@
	BDLocationListener myListener=null ;


	Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Toast.makeText(DigitalOne.this, "msg:" +msg.what, Toast.LENGTH_SHORT).show();
        };
    };
	
	
//	//��λ���
//    public LocationClient mLocationClient = null;
//	public BDLocationListener myListener= new MyLocationListener() ;
//	LocationData locData = new LocationData();
//	private MapController mMapController = null;
//	MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
	
	ImageView begin,end,begin2,end2,bok,searchok,myloc,searchpic;
	EditText searchcontent,beginedit,endedit;
	public static int Point_Lat,Point_Lon;
	public static GeoPoint MyPoint,MyLoc;
	
	public static float Slatitude,Slongtitude,Dlatitude,Dlongtitude;
	
	//��Ϣ�������search
	public static String search;
	MKSearch mMKSearch = null;
	
	private Button back;
	
	
//	Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		SearchOne.sAllActivitys.add(this);  
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("gLoLqkmImko7TG0DlFH6qbye", null);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_digital_one);
		mMapView =(MapView)findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		
		GeoPoint point =new GeoPoint((int)(36.674*1E6),(int)(117.031*1E6));
		mMapController.setCenter(point);
		mMapController.setZoom(12);
		
		

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        FirstActivity.displaywidth = displayMetrics.widthPixels;
        FirstActivity.displayheight = displayMetrics.heightPixels;
		
		back=(Button)findViewById(R.id.back);
		resize(back , 1/5.0 , 1/13.0 , (int)(FirstActivity.displaywidth*(40/720.0)) , (int)(FirstActivity.displayheight*(1000/1140.0)));
		back.setOnClickListener(this);
		
		System.out.println("iiiiiiiicreated");
		//��λ���
//		mLocationClient = new LocationClient(getApplicationContext()); 
//	    mLocationClient.registerLocationListener( myListener );
//		mHandler = new Handler() {
//	        public void handleMessage(android.os.Message msg) {
//	            Toast.makeText(DigitalOne.this, "msg:" +msg.what, Toast.LENGTH_SHORT).show();
//	        };
//	    };
		
		
		

		System.out.println("iiiiiiiilacateinit");
		// ��ͼ�Ͽؼ��Ĳ���
		begin = (ImageView)findViewById(R.id.begin);//��㰴ťͼƬ		
		resize(begin , 1/10.0 , 1/19.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(840/1140.0)));
		 begin2 = (ImageView)findViewById(R.id.begin2);//��㰴ťͼƬ
			resize(begin2 , 1/10.0 , 1/19.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(840/1140.0)));
		begin2.setVisibility(0x00000004);
		begin.setOnClickListener(this);
		
		end = (ImageView)findViewById(R.id.end);//�յ㰴ťͼƬ
		resize(end , 1/10.0 , 1/19.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(930/1140.0)));
		 end2 = (ImageView)findViewById(R.id.end2);//��㰴ťͼƬ
			resize(end2 , 1/10.0 , 1/19.0 , (int)(FirstActivity.displaywidth*(50/720.0)) , (int)(FirstActivity.displayheight*(930/1140.0)));
		end2.setVisibility(0x00000004);
		end.setOnClickListener(this);
		
		beginedit = (EditText)findViewById(R.id.beginedit);
		resize(beginedit , 4/5.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(120/720.0)) , (int)(FirstActivity.displayheight*(815/1140.0)));
		myloc = (ImageView)findViewById(R.id.myloc);
		resize(myloc , 1/10.0 , 1/18.0 , (int)(FirstActivity.displaywidth*(650/720.0)) , (int)(FirstActivity.displayheight*(830/1140.0)));
		endedit = (EditText)findViewById(R.id.endedit);
		resize(endedit , 4/5.0 , 1/12.0 , (int)(FirstActivity.displaywidth*(120/720.0)) , (int)(FirstActivity.displayheight*(905/1140.0)));
		bok = (ImageView)findViewById(R.id.bok);
		resize(bok , 1/10.0 , 1/18.0 , (int)(FirstActivity.displaywidth*(650/720.0)) , (int)(FirstActivity.displayheight*(910/1140.0)));
		bok.setOnClickListener(this);
		
		searchpic = (ImageView)findViewById(R.id.search);
		resize(searchpic , 2/5.0 , 1/17.0 , (int)(FirstActivity.displaywidth*(350/720.0)) , (int)(FirstActivity.displayheight*(15/1140.0)));
		searchcontent = (EditText)findViewById(R.id.searchcontent);
		resize(searchcontent , 2/5.0 , 1/17.0 , (int)(FirstActivity.displaywidth*(350/720.0)) , (int)(FirstActivity.displayheight*(21/1140.0)));
		searchok = (ImageView)findViewById(R.id.searchok);
		resize(searchok , 1/10.0 , 1/18.0 , (int)(FirstActivity.displaywidth*(650/720.0)) , (int)(FirstActivity.displayheight*(28/1140.0)));
		searchok.setOnClickListener(this);
		
		//��Ϣ�������search
		mMKSearch = new MKSearch();  
		mMKSearch.init(mBMapMan, new MySearchListener());//ע�⣬MKSearchListenerֻ֧��һ���������һ������Ϊ׼  
		
		//��λ��� new
				
	//			myListener= new BDLocationListener(){  
			  //      @Override
		//	        public void onReceiveLocation(BDLocation location) {

//			            if (location == null)
//			                return ;
			            
//			            locData.latitude = location.getLatitude();
//			            locData.longitude = location.getLongitude();
//			            locData.accuracy = location.getRadius();
//			            locData.direction = location.getDerect();
//			            myLocationOverlay.setData(locData);
//			            mMapView.getOverlays().add(myLocationOverlay);//�ڵ�ͼ������ҵ�λ��
//			            mMapView.refresh();
//			          mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)), mHandler.obtainMessage(1));
			           // mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude* 1e6)));
//			        }
			        
//			        public void onReceivePoi(BDLocation poiLocation) {
//			            if (poiLocation == null){
//			                return ;
//			            }
//		        }
//		};
//		OnClickListener clickListener = new OnClickListener(){
//			public void onClick(View v) {
//					testUpdateClick();
//			}
//       };
//System.out.println("iiiiiiiiiiiilistnerok");
//				    mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
//			    mLocationClient.registerLocationListener( myListener );    //ע���������
//			    myloc.setOnClickListener(clickListener);
			    
//			    System.out.println("iiiiiiiiclientbegin");
			    
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.digital_one, menu);
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
	  
	@Override
	protected void onDestroy(){
		mMapView.destroy();
		mLocationClient.stop();
	//	mLocationClient.unRegisterLocationListener(myListener);
		if(mBMapMan!= null){
			mBMapMan.destroy();
			mBMapMan= null;
		}
		super.onDestroy();
	}
	@Override
	protected void onPause(){
		mMapView.onPause();
		if(mBMapMan!=null){
			mBMapMan.stop();
		}super.onPause();
	}
	@Override
	protected void onResume(){
		mMapView.onResume();
		if(mBMapMan!=null){
			mBMapMan.start();
		}super.onResume();
	}

	
	
//��λ���new	
	 public void testUpdateClick(){
	    	setLocationOption();
			//mLocClient.start();
    	mLocationClient.start();
			//mLocClient.requestLocation();
	    	mLocationClient.requestLocation();
	    }
	 
	 private String getLocationAddress(GeoPoint point) {  
	        String add = "";  
	        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault()); 
	        float j,w;
	        w = point.getLatitudeE6();
	        j = point.getLongitudeE6();
	       
	        try {  
	            List<Address> addresses = geoCoder.getFromLocation(  
	                    point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6,  
	                    1);  
	            Address address = addresses.get(0);  
	            int maxLine = address.getMaxAddressLineIndex();  
	            if (maxLine >= 2) {  
	                add = address.getAddressLine(1) + address.getAddressLine(2);  
	            } else {  
	                add = address.getAddressLine(1);  
	            }  
	        } catch (IOException e) {  
	            add = "";  
	            e.printStackTrace();  
	        }  
	        
	        if((j<117071023&&j>117063262)&&(w<36678069&&w>36677172))//γ�� ����
	        	add = "ɽ����ѧ����У������";
	        else if((j<117067681&&j>117063549)&&(w<36685478&&w>36684668))//γ�� ����
	        	add = "ɽ����ѧ����У������";
	        else if((j<117149894&&j>117144792)&&(w<36674711&&w>36672106))//γ�� ����
	        	add = "ɽ����ѧ���԰У��";
	        else if((j<117079036&&j>117071167)&&(w<36695896&&w>36691093))//γ�� ����
	        	add = "ɽ����ѧ���¥У��";
	        else if((j<117028370&&j>117020287)&&(w<36661222&&w>36655113))//γ�� ����////
	        	add = "ɽ����ѧ��ͻȪУ��";
	        else if((j<117038289&&j>117032324)&&(w<36658703&&w>36651609))//γ�� ����
	        	add = "ɽ����ѧǧ��ɽУ��";
	        else if((j<117059064&&j>117053908)&&(w<36.608825&&w>36604465))//γ�� ����
	        	add = "ɽ����ѧ��¡ɽУ��";
	        else if((j<117000442&&j>116994262)&&(w<36678047&&w>36676253))//γ�� ����
	        	add = "���ϻ�վ";
	        else if((j<116899848&&j>116895429)&&(w<36677353&&w>36672924))//γ�� ����
	        	add = "������վ";
	        else if((j<116991864&&j>116989277)&&(w<36684361&&w>36682972))//γ�� ����
	        	add = "�����г�;��������";
	        else if((j<117079467&&j>117073754)&&(w<36684147&&w>36679516))//γ�� ����
	        	add = "�����аٻ���԰";
	        else if((j<117082126&&j>117079934)&&(w<36688257&&w>36686839))//γ�� ����
	        	add = "ɽ��ʡͼ���";
	        else if((j<117103614&&j>117100919)&&(w<36666404&&w>36661656))//γ�� ����
	        	add = "ɽ��ʡ�����";
	        else if((j<117135917&&j>117117483)&&(w<36665825&&w>36660758))//γ�� ����
	        	add = "ɽ��ʡ�����а�������";
	        else if((j<117069945&&j>117067825)&&(w<36694218&&w>36693263))//γ�� ����
	        	add = "���¥";
	        else if((j<117999841&&j>117986079)&&(w<36712823&&w>36704953))//γ�� ����
	        	add = "�����ж���԰";
	        else if((j<117033438&&j>117024706)&&(w<36668633&&w>36666635))//γ�� ����
	        	add = "������Ȫ�ǹ㳡";
	        else if((j<117024167&&j>117022047)&&(w<36667677&&w>36666867))//γ�� ����
	        	add = "��������ͻȪ��԰";
	        else if((j<117022407&&j>117020071)&&(w<36673901&&w>36671904))//γ�� ����
	        	add = "����������̶��԰";
	        else if((j<117019604&&j>117011735)&&(w<36654708&&w>36652015))//γ�� ����
	        	add = "ɽ��ʡ��������";
	        else if((j<117054494&&j>117033869)&&(w<36651465&&w>36642894))//γ�� ����
	        	add = "������ǧ��ɽ�羰��";
	        else if((j<117042637&&j>117023161)&&(w<36682758&&w>36679777))//γ�� ����
	        	add = "�����д������羰��";
	        else if((j<117.129701&&j>117126682)&&(w<36671788&&w>36666640))//γ�� ����
	        	add = "ɽ��ʡʡ��ҽԺ��Ժ��";
	        
	        return add;  
	    }  
	 
	/**
	 * �Զ���ViewPager������
	 * @author YangYxd
	 */
	public static class YxdPagerAdapter extends PagerAdapter {
	  public List<View> mListViews;
	  public ActivityGroup owner;
	  
	  public YxdPagerAdapter(List<View> mListViews) {
	    this.mListViews = mListViews;
	  }

	  @Override
	  public void destroyItem(View arg0, int arg1, Object arg2) {
	    if (arg1 == 4) return;
	    ((ViewPager) arg0).removeView(mListViews.get(arg1));        
	  }

	  @Override
	  public void finishUpdate(View arg0) {
	  }

	  @Override
	  public int getCount() {
	    return mListViews.size();
	  }

	  @Override
	  public Object instantiateItem(View arg0, int arg1) {
	    if (arg1 == 4) {
	      if (mListViews.get(arg1) == null ) {                        
	        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
	      }
	    } else 
	      ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
	    return mListViews.get(arg1);
	  }

	  @Override
	  public boolean isViewFromObject(View arg0, Object arg1) {
	    return arg0 == (arg1);
	  }

	  @Override
	  public void restoreState(Parcelable arg0, ClassLoader arg1) {
	  }

	  @Override
	  public Parcelable saveState() {
	    return null;
	  }

	  @Override
	  public void startUpdate(View arg0) {
	  }

	}
	
	public static int getWidth(View view) 
	{ 
	int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	view.measure(w, h); 
	return (view.getMeasuredWidth()); 
	} 
	/* 
	* ��ȡ�ؼ��� 
	*/ 
	public static int getHeight(View view) 
	{ 
	int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	view.measure(w, h); 
	return (view.getMeasuredHeight()); 
	} 

	/* 
	* ���ÿؼ����ڵ�λ��X�����Ҳ��ı��ߣ� 
	* XΪ����λ�ã���ʱY���ܹ�0 
	*/ 
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
	
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		//option.setAddrType("all"); 
		//option.setPoiExtraInfo(true);
		//option.setProdName("��λ�ҵ�ǰ��λ��");  
		//option.setOpenGps(false); 
		option.setOpenGps(true); 
		option.setScanSpan(500);
		//option.setPoiDistance(500); 
		//option.disableCache(true); 
		option.setCoorType("bd09ll"); 
		//option.setScanSpan(5000);
		//option.setPoiNumber(3); 
		option.setPriority(LocationClientOption.NetWorkFirst);  
		mLocationClient.setLocOption(option);
		//mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		
	}
	
	//��λ��ؼ���new
//	 private class MyLocationListenner implements BDLocationListener {
//	        @Override
//	        public void onReceiveLocation(BDLocation location) {
//
//	            if (location == null)
//	                return ;
//	            
//	            locData.latitude = location.getLatitude();
//	            locData.longitude = location.getLongitude();
//	            locData.accuracy = location.getRadius();
//	            locData.direction = location.getDerect();
//	            myLocationOverlay.setData(locData);
//	            mMapView.getOverlays().add(myLocationOverlay);//�ڵ�ͼ������ҵ�λ��
//	            mMapView.refresh();
//	          mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)), mHandler.obtainMessage(1));
//	           // mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude* 1e6)));
//	        }
//	        
//	        public void onReceivePoi(BDLocation poiLocation) {
//	            if (poiLocation == null){
//	                return ;
//	            }
//	        }
//	    }
	    

		
	public static String beginloc,endloc;
	public static boolean isbbegin=false;

	public static boolean isbend=false;
	


	    @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	    	if(v==begin2){
	    		begin2.setVisibility(0x00000004);
	    		beginedit.setFocusable(false); 
	    		isbbegin = false;
	    		isbend = false;
	    	}
	    	else if(v==begin){
				isbbegin = true;
				isbend = false;
				beginedit.setFocusable(true);  
				begin2.setOnClickListener(this);
	        	System.out.println("iiiiiiiiiiiiiionclick");
				//begin.setVisibility(0x00000004);
				begin2.setVisibility(0x00000000);

	        	System.out.println("iiiiiiiiiiiiiisetvisible");
				MKMapTouchListener mapTouchListener = new MKMapTouchListener(){  
			        @Override  
			        public void onMapClick(GeoPoint point) {  
			        	
			        	if(isbbegin){//�����ѡ������ʱ����������㾭γ��
			        		Slatitude = point.getLatitudeE6();
			        		Slongtitude = point.getLongitudeE6();
			        	}
			        	if(isbend){//�����ѡ���յ��ʱ���������յ�ľ�γ��
			        		Dlatitude = point.getLatitudeE6();
			        		Dlongtitude = point.getLongitudeE6();
			        	}
			        	System.out.println("iiiiiiiiiiiiiimapclick");
			            //�ڴ˴����ͼ�����¼�  
			        	String ads=getLocationAddress(point);
			        	if(ads==""){
			        		ads="�޾����ַ�����꣺("+point.getLatitudeE6()+","+point.getLongitudeE6()+")";
			        	}
			        	beginedit.setText(ads.toCharArray(), 0, ads.length()); 
			        	System.out.println("iiiiiiiiiiiiiisettext");
			        	begin2.setVisibility(0x00000004);
						//begin.setVisibility(0x00000000);
			        }  
			  
			        @Override  
			        public void onMapDoubleClick(GeoPoint point) {  
			            //�ڴ˴����ͼ˫���¼�  
			        }  
			  
			        @Override  
			        public void onMapLongClick(GeoPoint point) {  
			            //�ڴ˴����ͼ�����¼�   
			        }  
			    };  
			    mMapView.regMapTouchListner(mapTouchListener);  
				
			}
			
			else	if(v==end2){
				isbend = false;
				isbbegin =false;
				endedit.setFocusable(false); 
				beginedit.setFocusable(false); 
				end2.setVisibility(0x00000004);
			}
			else	if(v==end){
				isbend = true;
				isbbegin =false;
				endedit.setFocusable(true); 
				beginedit.setFocusable(false);
				begin2.setOnClickListener(this);
				end2.setVisibility(0x00000000);
				MKMapTouchListener mapTouchListener = new MKMapTouchListener(){  
			        @Override  
			        public void onMapClick(GeoPoint point) {  
			            //�ڴ˴����ͼ�����¼�  
			        	String ads=getLocationAddress(point);
			        	if(ads==""){
			        		ads="�޾����ַ�����꣺("+point.getLatitudeE6()+","+point.getLongitudeE6()+")";
			        	}
			        	endedit.setText(ads.toCharArray(), 0, ads.length()); 
			        	System.out.println("iiiiiiiiii"+ads);
			        	end2.setVisibility(0x00000004);
			        }  
			  
			        @Override  
			        public void onMapDoubleClick(GeoPoint point) {  
			            //�ڴ˴����ͼ˫���¼�  
			        }  
			  
			        @Override  
			        public void onMapLongClick(GeoPoint point) {  
			            //�ڴ˴����ͼ�����¼�   
			        }  
			    };  
			    mMapView.regMapTouchListner(mapTouchListener);  
			
}
			
			
			else if(v==searchok){
				search =searchcontent.getText().toString();
				mMKSearch.poiSearchInCity("����", search); 
			}
			else if(v==bok){//ȷ�������յ㣬�ص�SearchOne
				beginloc = beginedit.getText().toString();
				endloc = endedit.getText().toString();
				Intent intent1 = new Intent();
				intent1.putExtra("iam", "digitalone");
		    	intent1.setClass(DigitalOne.this, SearchOne.class);
				intent1.putExtra("beginloc",beginloc);
				intent1.putExtra("endloc",endloc);
				intent1.putExtra("Slatitude", Slatitude);
				intent1.putExtra("Slongtitude", Slongtitude);
				intent1.putExtra("Dlatitude", Dlatitude);
				intent1.putExtra("Dlongtitude", Dlongtitude);
				
				intent1.setClass(DigitalOne.this,SearchOne.class);
		    	DigitalOne.this.startActivity(intent1);
			}
			else if(v==back){
				Intent intentback = new Intent();	

				intentback.putExtra("iam", "backfrommap");
				intentback.setClass(DigitalOne.this, SearchOne.class);
				DigitalOne.this.startActivity(intentback);
		    	System.out.println("iiiiiiiiiintent");
			}
	    }
	    
	    
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event){
	    	if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getRepeatCount() == 0) {
	    		Intent intentback = new Intent();	

				intentback.putExtra("iam", "backfrommap");
				intentback.setClass(DigitalOne.this, SearchOne.class);
				DigitalOne.this.startActivity(intentback);
		    	System.out.println("iiiiiiiiiintent");
                return true;
            }
	    	else if(KeyEvent.KEYCODE_HOME==keyCode){

	    		android.os.Process.killProcess(android.os.Process.myPid());

	    		//�رճ��������������.

	    		}
            return super.onKeyDown(keyCode, event);
        
	    }
	    public String getBegin(){
	    	return beginloc;
	    }
	    public String getEnd(){
	    	return endloc;
	    }
		
	    
	    //��Ϣ���� search
	    public class MySearchListener implements MKSearchListener {    
	        @Override    
	        public void onGetAddrResult(MKAddrInfo result, int iError) {    
	               //���ص�ַ��Ϣ�������    
	        }    
	        @Override    
	        public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {    
	                //���ؼݳ�·���������    
	        }    
	       
	        @Override    
	        public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {    
	                //���ع����������    
	        }    
	        @Override    
	        public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {    
	                //���ز���·���������    
	        }    
	        @Override        
	        public void onGetBusDetailResult(MKBusLineResult result, int iError) {    
	                //���ع�����������Ϣ�������    
	        }    
	         @Override   
	         public void onGetShareUrlResult(MKShareUrlResult result , int type, int error) {  
	               //�ڴ˴���̴����󷵻ؽ��.   
	        }
			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onGetPoiResult(MKPoiResult res, int type, int error) {  
			  // ����ſɲο�MKEvent�еĶ���  
		       if ( error == MKEvent.ERROR_RESULT_NOT_FOUND){  
				    Toast.makeText(DigitalOne.this, "��Ǹ��δ�ҵ����",Toast.LENGTH_LONG).show();  
				return ;  
				        }  
				        else if (error != 0 || res == null) {  
				Toast.makeText(DigitalOne.this, "����������..", Toast.LENGTH_LONG).show();  
				return;  
				}  
				// ��poi�����ʾ����ͼ��  
				PoiOverlay poiOverlay = new PoiOverlay(DigitalOne.this, mMapView);  
				poiOverlay.setData(res.getAllPoi());  
			mMapView.getOverlays().clear();  
				mMapView.getOverlays().add(poiOverlay);  
				mMapView.refresh();  
				//��ePoiTypeΪ2��������·����4��������·��ʱ�� poi����Ϊ��  
			for(MKPoiInfo info:res.getAllPoi()){
				if(info.pt!=null){
					mMapView.getController().animateTo(info.pt);
					break;
				}
			}
				}  
			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}   
	}  
	    }
	