package it.hua.beans;

import java.io.Serializable;
import java.util.Date;

public class Poster implements Serializable{
	public String stuid;// 学号
	public Date ptime = new Date();// 发帖日期
	public String content;// 帖子内容
	public String start;// 起始地点
	public float Slongitude;// 起始地点经度
	public float Slatitude;// 起始地点纬度
	public String destination;// 目的地
	public float Dlongitude;// 目的地经度
	public float Dlatitude;// 目的地纬度
	public Date Gtime;// 出发时间
	public String posterID;// 帖子编号：格式x00000001

	public String nickname;// ++++给人员昵称

	public String tele;// ++客户端发送poster的时候不需要发送tele,客户端想要获取他人poster信息的时候服务器应该返回有tele

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
