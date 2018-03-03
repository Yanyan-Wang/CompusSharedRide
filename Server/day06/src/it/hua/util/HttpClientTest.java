package it.hua.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientTest {
	static String html;
	static String[] s;

	public static void main(String[] args) throws IllegalStateException,
			HttpException, IOException {
		for (String s : XiangQing("201200301273", "199410")) {
			System.out.println(s);
		}
		System.out.println(message("201200301273", "199410"));
	}

	public static String message(String stuid, String pwd)
			throws IllegalStateException, HttpException, IOException {
		String[] s = htmlDeal(
				"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.loginmessage",
				"t>\n(.*?)\n<br>", stuid, pwd, 2);
		return s[1];
	}

	// http://jwxt.sdu.edu.cn:7890/zhxt_bks/zhxt_bks.html

	public static String[] XiangQing(String stuid, String pwd)
			throws IllegalStateException, HttpException, IOException {
		String[] s = htmlDeal(
				"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_xj.xjcx",
				"#EAE2F3\"><p align=\"center\">.*?</p></td>", stuid, pwd, 3);
		return s;
	}

	public static boolean isLogin(String stuid, String pwd) throws IOException {
		String[] s = htmlDeal(
				"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.loginmessage",
				"t>\n(.*?)\n<br>", stuid, pwd, 1);
		if (s[1] == "yes")
			return true;
		else
			return false;

	}

	public static String[] htmlDeal(String url, String regex, String stuid,
			String pwd, int ID) throws IllegalStateException, IOException,
			HttpException {
		String message = "ÃÜÂë´íÎó";
		String[] st = { null, null, null };
		HttpClient httpclient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		PostMethod postMethod = new PostMethod(
				"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login");
		NameValuePair[] postData = new NameValuePair[2];
		postData[0] = new NameValuePair("stuid", stuid);
		postData[1] = new NameValuePair("pwd", pwd);
		postMethod.addParameters(postData);
		int statusCode;
		statusCode = httpclient.executeMethod(postMethod);
		statusCode = httpclient.executeMethod(getMethod);

		html = getMethod.getResponseBodyAsString();
		getMethod.releaseConnection();
		postMethod.releaseConnection();

		Pattern urlPattern = Pattern.compile(regex);
		Matcher urlMatcher = urlPattern.matcher(html);
		boolean isFind = urlMatcher.find();

		if (ID == 1) {
			s = urlMatcher.group(0).split("\n");
			if (s == null)
				st[1] = "no";
			else
				st[1] = "yes";
		}
		if (ID == 2) {
			s = urlMatcher.group(0).split("\n");
			if (s != null)
				st[1] = s[1];
		}
		if (ID == 3) {
			String target;
			int Count = 0, t = 0;
			while (isFind) {
				Count++;
				target = urlMatcher.group();
				if (Count == 4 || Count == 9 || Count == 12) {
					s = target.split(">");
					if (s != null)
						message = s[2].split("<")[0];
					st[t] = message;
					t++;
				}
				isFind = urlMatcher.find();
			}
		}
		return st;
	}

}