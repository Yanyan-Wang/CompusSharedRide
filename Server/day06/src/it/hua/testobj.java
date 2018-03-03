package it.hua;

import it.hua.util.user;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class testobj extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ObjectInputStream oi = new ObjectInputStream(req.getInputStream());
		user u = null;
		try {
			u = (user) oi.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("·þÎñÆ÷¶Ë£º" + u);
		ObjectOutputStream os = new ObjectOutputStream(res.getOutputStream());
		os.writeObject(u);
		os.flush();
		os.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}