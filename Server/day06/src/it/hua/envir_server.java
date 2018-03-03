package it.hua;

import it.hua.beans.Poster;
import it.hua.beans.StuInfo;
import it.hua.beans.Trans;
import it.hua.beans.Trans.state;
import it.hua.beans.TransBack;
import it.hua.beans.TransBack.status;
import it.hua.control.Dao;
import it.hua.control.message.login;
import it.hua.util.HttpClientTest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;

public class envir_server extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		Trans t = null;
		System.out.println();
		System.out
				.println("---------------------------------------------------------------------------------");
		System.out.println("----" + new Date().toLocaleString()
				+ "\n----Httprequest!");
		try {
			t = (Trans) new ObjectInputStream(request.getInputStream())
					.readObject();
			System.out.println("state:---------" + t.getSt() + "----------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (t.getSt() == state.register) {
			doregister(request, response, t);
		} else if (t.getSt() == state.login) {
			dologin(request, response, t);
		} else if (t.getSt() == state.post) {
			doPoster(request, response, t);
		} else if (t.getSt() == state.search) {
			search(request, response, t);
		} else if (t.getSt() == state.myinfoedit) {
			myinfoedit(request, response, t);
		} else if (t.getSt() == state.mycollect) {
			mycollect(request, response, t);
		} else if (t.getSt() == state.mytext) {
			mytext(request, response, t);
		} else if (t.getSt() == state.collect) {
			collect(request, response, t);
		} else if (t.getSt() == state.posterdetail) {
			PosterDetail(request, response, t);
		} else if (t.getSt() == state.myinfo) {
				myinfo(request, response, t);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void doregister(HttpServletRequest request,
			HttpServletResponse response, Trans t) {// 注册
		try {
			System.out.println("注册");
			if (HttpClientTest.isLogin(t.getStuid(), t.getPwd())) {// 注册成功
				System.out.println("注册成功");
				String infos[];
				infos = HttpClientTest.message(t.getStuid(), t.getPwd()).split(
						" ");
				infos[2] = infos[2].split("\\(")[0];
				StuInfo stuInfo = new StuInfo();

				stuInfo.setCampus(infos[0]);
				stuInfo.setSpecialty(infos[1]);
				stuInfo.setName(infos[2]);
				stuInfo.setPwd(t.getPwd());
				stuInfo.setStuid(t.getStuid());

				infos = HttpClientTest.XiangQing(t.getStuid(), t.getPwd());
				stuInfo.setGender(infos[0]);
				stuInfo.setTisch(Integer.parseInt(infos[1]) / 10000);
				stuInfo.setcLass(infos[2]);
				if (t.getTele() != null)
					stuInfo.setTele(t.getTele());
				if (t.getNickname() != null)
					stuInfo.setNickname(t.getNickname());
				Dao.storeUser(stuInfo);
				TransBack tb = new TransBack();
				tb.setState(status.registersuccess);
				System.out.println("transback?");
				ObjectOutputStream os;
				try {
					os = new ObjectOutputStream(response.getOutputStream());
					os.writeObject(tb);
					os.flush();
					os.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {// 注册失败
				System.out.println("注册失败");
				TransBack tb = new TransBack();
				tb.setState(status.registerfail);
				ObjectOutputStream os = new ObjectOutputStream(
						response.getOutputStream());
				os.writeObject(tb);
				os.flush();
				os.close();
			}
		} catch (HttpException e) {// 其它未知问题
			TransBack tb = new TransBack();
			tb.setState(status.problemoccursinserver);
			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(response.getOutputStream());
				os.writeObject(tb);
				os.flush();
				os.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (IllegalStateException e) {// 密码有问题
			System.out.println("registerwrongpwd");
			e.printStackTrace();
			TransBack tb = new TransBack();
			tb.setState(status.wrongpassword);
			ObjectOutputStream os;
			try {
				System.out.println("??");
				os = new ObjectOutputStream(response.getOutputStream());
				os.writeObject(tb);
				System.out.println("??");
				os.flush();
				os.close();
				System.out.println("sendbacksucceed");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {// 服务器网络有问题
			e.printStackTrace();
			TransBack tb = new TransBack();
			tb.setState(status.problemoccursinserver);
			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(response.getOutputStream());
				os.writeObject(tb);
				os.flush();
				os.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {// sql语句有问题
			TransBack tb = new TransBack();
			tb.setState(status.problemoccursinserver);
			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(response.getOutputStream());
				os.writeObject(tb);
				os.flush();
				os.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void dologin(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String stuid = t.getStuid();
		String pwd = t.getPwd();
		login state = Dao.login(stuid, pwd);
		TransBack tb = new TransBack();
		switch (state) {
		case success:
			tb.setState(status.longinsuccess);
			System.out.println(status.longinsuccess);
			break;
		case wrongpwd:
			tb.setState(status.wrongpassword);
			System.out.println(status.wrongpassword);
			break;
		case noregist:
			tb.setState(status.noregister);
			System.out.println(status.noregister);
			break;
		}
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void doPoster(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String stuid = t.getStuid();
		Poster poster = t.getPoster();
		poster.setStuid(stuid);
		Dao.storePoster(poster);

		TransBack tb = new TransBack();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void myinfoedit(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String stuid = t.getStuid();
		String nickname = t.getNickname();
		String tele = t.getTele();
		Dao.updateUser(stuid, nickname, tele);
	}

	private void mycollect(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String stuid = t.getStuid();

		TransBack tb = new TransBack();
		tb.posters = Dao.queryMyCollect(stuid);
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void mytext(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String stuid = t.getStuid();
		TransBack tb = new TransBack();
		tb.posters = Dao.queryMyPoster(stuid);
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void collect(HttpServletRequest request,
			HttpServletResponse response, Trans t) {

		String stuid = t.getStuid();
		String posterID = t.posterID;
		Dao.collectPoster(stuid, posterID);
		// TransBack tb = new TransBack();
		// ObjectOutputStream os;
		// try {
		// os = new ObjectOutputStream(response.getOutputStream());
		// os.writeObject(tb);
		// os.flush();
		// os.close();
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
	}

	private void PosterDetail(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String posterID = t.posterID;
		TransBack tb = new TransBack();
		tb.posters = Dao.queryPosterDetail(posterID);
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void search(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		TransBack tb = new TransBack();
		tb.posters = Dao.searchv1();
		ObjectOutputStream os;
		System.out.println("search");
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void myinfo(HttpServletRequest request,
			HttpServletResponse response, Trans t) {
		String stuid=t.getStuid();
		System.out.println("get from db to envir"+t.getStuid());
		StuInfo info=Dao.queryuser(stuid);
		TransBack tb = new TransBack();
		tb.nickname=info.getNickname();
		tb.sex=info.getGender();
		tb.tele=info.getTele();
		tb.state=tb.state.editinfosuccess;
		System.out.println(tb.tele+tb.sex+tb.nickname);
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(response.getOutputStream());
			os.writeObject(tb);
			os.flush();
			os.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
