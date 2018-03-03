package it.hua.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ts {

	private final static String PATH = "http://211.87.227.8:8080/day06/servlet/envir_server";
	private static URL url;
	private static HttpURLConnection urlConnection;

	public static Object sendMessage(Object obj) {
		try {
			url = new URL(PATH);
			if (null != url) {
				urlConnection = (HttpURLConnection) url.openConnection();
				if (null == urlConnection) {
					return -1;
				}
				urlConnection.setConnectTimeout(3000);
				urlConnection.setRequestMethod("POST");
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				OutputStream outputStream = urlConnection.getOutputStream();
				ObjectOutputStream oj = new ObjectOutputStream(outputStream);
				oj.writeObject(obj);
				oj.flush();
				oj.close();
				outputStream.close();
				int responseCode = urlConnection.getResponseCode();
				if (200 == responseCode) {
					return changeInputStream(urlConnection.getInputStream(),
							"utf-8");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private static Object changeInputStream(InputStream inputStream,
			String encode) {
		Object obj = null;
		if (null != inputStream) {
			ObjectInputStream oi;
			try {
				oi = new ObjectInputStream(inputStream);
				obj = oi.readObject();
				System.out.println("obj:" + obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(obj);
		return obj;
	}
}
