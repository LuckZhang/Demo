package com.jdb.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/12/24.
 */
public class DemoModel {
	public List<Demo> demoList;

	public DemoModel() {
		this.demoList = generateDemoList();
	}

	public class Demo {
		public String content;

		public Demo() {
		}

		public Demo(String content) {
			this.content = content;
		}
	}

	public List<Demo> generateDemoList() {
		List<Demo> demos = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			demos.add(new Demo("This is a very powerful code " + i));
		}
		return demos;
	}
}
