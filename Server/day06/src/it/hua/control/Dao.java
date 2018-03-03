package it.hua.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import it.hua.beans.Poster;
import it.hua.beans.StuInfo;
import it.hua.control.message.login;
import it.hua.test.receiver;
import it.hua.util.JdbcUtils;

public class Dao {
	public static boolean storeUser(StuInfo info) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user values (?,?,?,?,?,?,?,(select cam_id from campus where campus=?),(select specia_id from specialty where specialty=?),?,?) ";
			ps = conn.prepareStatement(sql);

			ResultSet result = ps
					.executeQuery("select * from campus where campus='"
							+ info.getCampus() + "'");
			if (!result.next()) {// 查询结果为空
				ps.executeUpdate("insert into campus(campus)value('"
						+ info.getCampus() + "')");
			}
			result = ps
					.executeQuery("select * from specialty where specialty='"
							+ info.getSpecialty() + "'");
			if (!result.next()) {// 查询结果为空
				ps.executeUpdate("insert into specialty(specialty)value('"
						+ info.getSpecialty() + "')");
			}
			ps.setString(1, info.getStuid());
			ps.setString(2, info.getPwd());
			ps.setString(3, info.getName());
			ps.setString(4, info.getNickname());
			ps.setString(5, info.getGender());
			ps.setString(6, info.getUniver());
			ps.setInt(7, info.getTisch());
			ps.setString(8, info.getCampus());
			ps.setString(9, info.getSpecialty());
			ps.setString(10, info.getcLass());
			ps.setString(11, info.getTele());

			int i = ps.executeUpdate();
			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return false;
	}

	public static StuInfo queryuser(String stuid) {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		StuInfo info = new StuInfo();
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select nickname,gender,tele from user where stuid='"
					+ stuid + "'");
			while (rs.next()) {
				info.setTele(rs.getString("tele"));
				info.setGender(rs.getString("gender"));
				info.setNickname(rs.getString("nickname"));
			}
			System.out.println("get from db"+info.getTele());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return info;
	}

	public static void updateUser(String stuid, String nickname, String tele) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();

			String sql = "update user set nickname='" + nickname + "',tele='"
					+ tele + "' where stuid='" + stuid + "'";

			int i = st.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	public static void storePoster(Poster poster) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into poster(stuid,ptime,content,sttp,Slongitude,"
					+ // 5
					"Slatitude,dstp,Dlongitude,Dlatitude,Gtime) values(?,?,?,?,?,?,?,?,?,?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, poster.getStuid());
			ps.setString(2, poster.getPtime().toString());
			ps.setString(3, poster.getContent());
			ps.setString(4, poster.getStart());
			ps.setString(5, ((Float) poster.getSlongitude()).toString());
			ps.setString(6, ((Float) poster.getSlatitude()).toString());
			ps.setString(7, poster.getDestination());
			ps.setString(8, ((Float) poster.getDlongitude()).toString());
			ps.setString(9, ((Float) poster.getDlatitude()).toString());
			ps.setString(10, poster.getGtime().toString());
			int i = ps.executeUpdate();
			System.out.println("i=" + i);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	public static void collectPoster(String stuid, String posterID) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "insert into mycollect values ('" + stuid + "',"
					+ posterID + ")";
			System.out.println("Dao.collectPoster:" + sql);
			int i = st.executeUpdate(sql);
			System.out.println("i=" + i);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	public static ArrayList<Poster> queryPosterDetail(String posterID) {
		ArrayList<Poster> posters = new ArrayList<Poster>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from poster natural join user where pstid="
					+ posterID);
			while (rs.next()) {
				Poster poster = new Poster();
				poster.setPosterID(rs.getObject("pstid").toString());
				poster.setStuid((String) rs.getObject("stuid"));
				poster.setPtime(new Date(rs.getObject("ptime").toString()));
				poster.setContent(rs.getObject("content").toString());
				poster.setStart((String) rs.getObject("sttp"));
				poster.setSlongitude(Float.parseFloat((String) rs
						.getObject("Slongitude")));
				poster.setSlatitude(Float.parseFloat((String) rs
						.getObject("Slatitude")));
				poster.setDestination((String) rs.getObject("dstp"));
				poster.setDlongitude(Float.parseFloat((String) rs
						.getObject("Dlongitude")));
				poster.setDlatitude(Float.parseFloat((String) rs
						.getObject("Dlatitude")));
				poster.setGtime(new Date(rs.getObject("Gtime").toString()));
				poster.setNickname(rs.getString("nickname"));
				poster.setTele(rs.getString("tele"));
				posters.add(poster);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return posters;
	}

	public static ArrayList<Poster> searchv1() {
		ArrayList<Poster> posters = new ArrayList<Poster>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from poster natural join user");
			while (rs.next()) {
				Poster poster = new Poster();
				poster.setPosterID(rs.getObject("pstid").toString());
				poster.setStuid((String) rs.getObject("stuid"));
				poster.setPtime(new Date(rs.getObject("ptime").toString()));
				poster.setContent(rs.getObject("content").toString());
				poster.setStart((String) rs.getObject("sttp"));
				poster.setSlongitude(Float.parseFloat((String) rs
						.getObject("Slongitude")));
				poster.setSlatitude(Float.parseFloat((String) rs
						.getObject("Slatitude")));
				poster.setDestination((String) rs.getObject("dstp"));
				poster.setDlongitude(Float.parseFloat((String) rs
						.getObject("Dlongitude")));
				poster.setDlatitude(Float.parseFloat((String) rs
						.getObject("Dlatitude")));
				poster.setGtime(new Date(rs.getObject("Gtime").toString()));
				poster.setNickname(rs.getString("nickname"));
				poster.setTele(rs.getString("tele"));
				posters.add(poster);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return posters;
	}

	public static ArrayList<Poster> queryMyPoster(String stuid) {
		ArrayList<Poster> posters = new ArrayList<Poster>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from poster natural join user where stuid='"
					+ stuid + "'");
			while (rs.next()) {
				Poster poster = new Poster();
				poster.setPosterID(rs.getObject("pstid").toString());
				poster.setStuid((String) rs.getObject("stuid"));
				poster.setPtime(new Date(rs.getObject("ptime").toString()));
				poster.setContent(rs.getObject("content").toString());
				poster.setStart((String) rs.getObject("sttp"));
				poster.setSlongitude(Float.parseFloat((String) rs
						.getObject("Slongitude")));
				poster.setSlatitude(Float.parseFloat((String) rs
						.getObject("Slatitude")));
				poster.setDestination((String) rs.getObject("dstp"));
				poster.setDlongitude(Float.parseFloat((String) rs
						.getObject("Dlongitude")));
				poster.setDlatitude(Float.parseFloat((String) rs
						.getObject("Dlatitude")));
				poster.setGtime(new Date(rs.getObject("Gtime").toString()));
				poster.setNickname(rs.getString("nickname"));
				poster.setTele(rs.getString("tele"));
				posters.add(poster);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return posters;
	}

	public static ArrayList<Poster> queryMyCollect(String stuid) {
		ArrayList<Poster> posters = new ArrayList<Poster>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select poster.pstid,poster.stuid,poster.ptime,poster.content,poster.sttp,poster.Slongitude,poster.Slatitude,poster.dstp"
					+ ",poster.Dlongitude,poster.Dlatitude,poster.Gtime,nickname,tele from user natural join mycollect,poster where poster.pstid=mycollect.pstid and user.stuid='"
					+ stuid + "'");
			while (rs.next()) {
				Poster poster = new Poster();
				poster.setPosterID(rs.getObject("pstid").toString());
				poster.setStuid((String) rs.getObject("stuid"));
				poster.setPtime(new Date(rs.getObject("ptime").toString()));
				poster.setContent(rs.getObject("content").toString());
				poster.setStart((String) rs.getObject("sttp"));
				poster.setSlongitude(Float.parseFloat((String) rs
						.getObject("Slongitude")));
				poster.setSlatitude(Float.parseFloat((String) rs
						.getObject("Slatitude")));
				poster.setDestination((String) rs.getObject("dstp"));
				poster.setDlongitude(Float.parseFloat((String) rs
						.getObject("Dlongitude")));
				poster.setDlatitude(Float.parseFloat((String) rs
						.getObject("Dlatitude")));
				poster.setGtime(new Date(rs.getObject("Gtime").toString()));
				poster.setNickname(rs.getString("nickname"));
				poster.setTele(rs.getString("tele"));
				posters.add(poster);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return posters;
	}

	public static login login(String stuid, String pwd) {
		login state = login.success;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			try {
				conn = JdbcUtils.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				st = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = st.executeQuery("select pwd from user where stuid='"
						+ stuid + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (rs.next()) {
					if (pwd.equals((String) rs.getObject(1))) {
						state = login.success;
					} else {
						state = login.wrongpwd;
					}
				} else {
					state = login.noregist;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return state;
	}
}