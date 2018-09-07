package com.example.myproject.client;

import java.io.DataInputStream;

public class ReadThread implements Runnable{
	private DataInputStream dis;

	public ReadThread(DataInputStream dis) {
		super();
		this.dis = dis;
	}

	@Override
	public void run() {
		String info=null;
		while(true) {
			try {
				info=dis.readUTF();
				if (info.equals("bye")) {
					System.out.println("对方已经下线");
					break;
				}
				System.out.println(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
