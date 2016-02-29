package com.example.mobilesafe.bean;

/**
 * @author like
 *黑名单bean界面
 */
public class Bean_blacklist {
		public String name;
		public String number;
		public String status;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		public Bean_blacklist(String name, String number, String status) {
			super();
			this.name = name;
			this.number = number;
			this.status = status;
		}
		
		
}
