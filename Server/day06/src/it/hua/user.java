package it.hua;

import java.io.Serializable;

public class user implements Serializable {
	private String name;
	private int age;
	private String gender;

	public user(String name, int age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public String toString() {
		return "姓名：" + name + " 年龄：" + age + "性别：" + gender;
	}
}
