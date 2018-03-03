package it.hua.junit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.hua.beans.Poster;
import it.hua.beans.StuInfo;
import it.hua.beans.Trans;
import it.hua.beans.TransBack;
import it.hua.control.Dao;
import org.junit.Test;

public class junittesters {

	@Test
	// ����it.hua.control.Dao.storeUser
	public void testUserdao() {
		StuInfo info = new StuInfo();
		info.setCampus("�������ѧ�뼼��ѧԺ");
		info.setcLass("�����12.1");
		info.setGender("��");
		info.setName("�����");
		info.setNickname("�ߴ���");
		info.setPwd("fucking");
		info.setSpecialty("�������ѧ�뼼��");
		info.setStuid("201201402394");
		info.setTele("18366119883");
		info.setTisch(2012);
		info.setUniver("ɽ����ѧ");
		try {
			Dao.storeUser(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testparse() {
		System.out.println("abcde".substring(1));
	}

	@Test
	public void testupdateuser() {
		Dao.updateUser("201200301310", "��", "12345787");
	}

	@Test
	public void testquerymycollect() {
		System.out.println(Dao.queryMyCollect("201200301310"));
	}

	// ------------------------------------------------------------------------------------------------------------------------
	// ��posterid��int��ת��Ϊx0000000001String�͵ķ���
	@Test
	public void intformattest() {
		String s = String.format("x%06d", 123);
		System.out.println(s);
	}

	@Test
	public void testPostersotre() {
		Poster poster = new Poster();
		poster.setContent("�����ҵ�ȥ��վ����ƴ��");
		poster.setDestination("��վ");
		poster.setDlatitude((float) 3.32);
		poster.setDlongitude((float) 3.31);
		poster.setGtime(new Date(2014, 4, 20));
		poster.setPtime(new Date());
		poster.setSlatitude((float) 2.32);
		poster.setSlongitude((float) 2.31);
		poster.setStart("���԰У��");
		poster.setStuid("201200402394");

		Dao.storePoster(poster);
		System.out.println(poster);

	}

	@Test
	public void queryMyPoster() {
		System.out.println(Dao.queryMyPoster("201200402394"));
	}

	void set(String s) {
		String aString = s;
		if (aString == null) {
			System.out.println(false);
		} else {
			System.out.println(true);
		}
	}

	@Test
	// null��������
	public void nulltest() {
		String s = null;
		set(s);
	}

	@Test
	public void datetest() {
		Date date = new Date(new Date().toString());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(format.format(date));
		System.out.println(new Date(date.toString()));
	}

	@Test
	public void logintest() {
		// System.out.println(Dao.login("201200402394", "fucking"));
		Trans s = new Trans();
		TransBack tBack = s.login("201200402394", "fucking");
	}

	@Test
	public void testpacong() {
		Trans s = new Trans();
		TransBack tBack = s.register("201200301310", "885176", "zjh",
				"18366119710");
		System.out.println(tBack.state);
		// try {
		// System.out.println(HttpClientTest.isLogin("201200301310", "885176"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	// google��������������ڸ��ݾ�γ������������֮��ľ���ġ�
	// private final double EARTH_RADIUS = 6378.137;
	// private static double rad(double d)
	// {
	// return d * Math.PI / 180.0;
	// }
	//
	// public static double GetDistance(double lat1, double lng1, double lat2,
	// double lng2)
	// {
	// double radLat1 = rad(lat1);
	// double radLat2 = rad(lat2);
	// double a = radLat1 - radLat2;
	// double b = rad(lng1) - rad(lng2);
	// double s = 2 * Math.Asin(Math.Sqrt(Math.Pow(Math.Sin(a/2),2) +
	// Math.Cos(radLat1)*Math.Cos(radLat2)*Math.Pow(Math.Sin(b/2),2)));
	// s = s * EARTH_RADIUS;
	// s = Math.Round(s*10000)/10000;
	// return s;
	// }

}
