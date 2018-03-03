package it.hua.beans;

import java.io.Serializable;
import java.util.Date;

public class Poster implements Serializable{
	public String stuid;// ѧ��
	public Date ptime = new Date();// ��������
	public String content;// ��������
	public String start;// ��ʼ�ص�
	public float Slongitude;// ��ʼ�ص㾭��
	public float Slatitude;// ��ʼ�ص�γ��
	public String destination;// Ŀ�ĵ�
	public float Dlongitude;// Ŀ�ĵؾ���
	public float Dlatitude;// Ŀ�ĵ�γ��
	public Date Gtime;// ����ʱ��
	public String posterID;// ���ӱ�ţ���ʽx00000001

	public String nickname;// ++++����Ա�ǳ�

	public String tele;// ++�ͻ��˷���poster��ʱ����Ҫ����tele,�ͻ�����Ҫ��ȡ����poster��Ϣ��ʱ�������Ӧ�÷�����tele

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getPosterID() {
		return posterID;
	}
	
	public String getFormatedPosterID(){
		return String.format("x%08d", Integer.parseInt(posterID));
	}

	public void setPosterID(String posterID) {
		this.posterID = posterID;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public void setPtime(Date ptime) {
		this.ptime = ptime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public float getSlongitude() {
		return Slongitude;
	}

	public void setSlongitude(float slongitude) {
		Slongitude = slongitude;
	}

	public float getSlatitude() {
		return Slatitude;
	}

	public void setSlatitude(float slatitude) {
		Slatitude = slatitude;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public float getDlongitude() {
		return Dlongitude;
	}

	public void setDlongitude(float dlongitude) {
		Dlongitude = dlongitude;
	}

	public float getDlatitude() {
		return Dlatitude;
	}

	public void setDlatitude(float dlatitude) {
		Dlatitude = dlatitude;
	}

	public Date getGtime() {
		return Gtime;
	}

	public void setGtime(Date gtime) {
		Gtime = gtime;
	}

	public Date getPtime() {
		return ptime;
	}

	@Override
	public String toString() {

		return "stuid:" + stuid + "ptime:" + ptime.toLocaleString()
				+ "content:" + content + "start:" + start + "Slongitude:"
				+ Slongitude + "slatitude:" + Slatitude + "destination:"
				+ destination + "Dlongitude" + Dlongitude + "Dlatitude:"
				+ Dlatitude + "Gtime:" + Gtime.toLocaleString() + "posterID:"
				+ getFormatedPosterID() + "nickname:" + nickname + "tele:" + tele+"\n";
	}
}
