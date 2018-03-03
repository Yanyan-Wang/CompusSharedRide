package it.hua.beans;

import java.io.Serializable;

public class Trans implements Serializable {
	public enum state {
		login, register, post, search, detailsearch, myinfoedit, mycollect, mytext,collect,posterdetail,myinfo
	}

	public state st = null;
	public String stuid = null;
	public String pwd = null;
	public String nickname = null;
	public String tele = null;
	public Poster poster = null;
	public String posterID = null;

	public state getSt() {
		return st;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTele() {
		return tele;
	}

	public String getStuid() {
		return stuid;
	}

	public String getPwd() {
		return pwd;
	}

	public Poster getPoster() {
		return poster;
	}

	public TransBack login(String stuid, String pwd) {// ok
		st = state.login;
		this.stuid = stuid;
		this.pwd = pwd;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}

	public TransBack register(String stuid, String pwd, String nickname,
			String telephone) {// ok
		st = state.register;
		this.stuid = stuid;
		this.pwd = pwd;
		this.nickname = nickname;
		this.tele = telephone;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}

	public TransBack post(String stuid, Poster poster) {// ok
		st = state.post;
		this.poster = poster;
		this.stuid = stuid;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}

	public TransBack search(String stuid, Poster poster) {// ++
		st = state.search;
		this.poster = poster;
		this.stuid = stuid;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}

	public TransBack myinfo(String stuid, String nickname, String tele) {// ok
		st = state.myinfoedit;
		this.nickname = nickname;
		this.stuid = stuid;
		this.tele = tele;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}

	public TransBack mycollect(String stuid) {// ++
		st = state.mycollect;
		this.stuid = stuid;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}

	public TransBack mytext(String stuid) {// ++
		st = state.mytext;
		this.stuid = stuid;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}
	
	public TransBack MyInfoget(String stuid) {// ++
		st = state.myinfo;
		this.stuid = stuid;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}
	
	public TransBack posterditail(String stuid, String posterID) {//++
		st = state.posterdetail;
		this.stuid = stuid;
		this.posterID = posterID;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}
	public TransBack collectatext(String stuid, String posterID) {//++
		st = state.collect;
		this.stuid = stuid;
		this.posterID = posterID;
		return (TransBack) it.hua.util.Ts.sendMessage(this);
	}
}
