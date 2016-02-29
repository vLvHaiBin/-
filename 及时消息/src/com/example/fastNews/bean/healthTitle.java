package com.example.fastNews.bean;

import java.util.ArrayList;

public class healthTitle {

	public ArrayList<healthList> result;

	public ArrayList<healthList> getHealth() {
		return result;
	}

	public void setHealth(ArrayList<healthList> health) {
		this.result = health;
	}

	public class healthList {

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		public String id;
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		public String name;
	}

}
