package it.hua.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class TransBack implements Serializable{
	public enum status {
		wrongpassword, longinsuccess, registersuccess, problemoccursinserver, registerfail,editinfosuccess,editinfofail,noregister
	}
	public String tele="0";
	public String nickname="";
	public String sex;
	
	public status state;
	public ArrayList<Poster> posters = new ArrayList<Poster>();

	public status getState() {
		return state;
	}
	public String getTele() {
		return tele;
	}
	public String getNickname() {
		return nickname;
	}
	public String getSex() {
		return sex;
	}

	public void setState(status state) {
		this.state = state;
	}

	public ArrayList<Poster> getPosters() {
		return posters;
	}
}
