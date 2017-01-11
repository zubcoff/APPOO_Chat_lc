package com.chat.app.actions;

import com.chat.app.view.MainFrame;

public class Cmds {

	private MainFrame object;

	public Cmds(MainFrame object) {
		super();
		this.object = object;
	}

	public void exit() {
		System.exit(0);
	}

	public void search() {
		// TODO Auto-generated method stub
		System.out.println("search");
	}

	public void preferences() {
		// TODO Auto-generated method stub
		System.out.println("preferences");
	}

	public void connect() {
		// TODO Auto-generated method stub
		System.out.println("connect");
	}

}
