package it.hua.beans;

public class StuInfo {
	private String stuid;
	private String pwd;
	private String name;
	private String campus = "软件学院";
	private String specialty = "软件工程专业";
	private String nickname = "nickname";
	private String gender = "男";
	private String univer = "山东大学";
	private int tisch = 2012;
	private String tele;
	private String cLass = "计软12.2";

	public String getcLass() {
		return cLass;
	}

	public void setcLass(String cLass) {
		this.cLass = cLass;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUniver() {
		return univer;
	}

	public void setUniver(String univer) {
		this.univer = univer;
	}

	public int getTisch() {
		return tisch;
	}

	public void setTisch(int tisch) {
		this.tisch = tisch;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

}
