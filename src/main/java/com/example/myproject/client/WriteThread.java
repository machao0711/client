package com.example.myproject.client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class WriteThread implements Runnable{
	private ConcurrentHashMap<String, Socket> socketMap;

	public WriteThread(ConcurrentHashMap<String, Socket> socketMap) {
		super();
		this.socketMap = socketMap;
	}

	@Override
	public void run() {
		String info=null;
		Scanner sc=new Scanner(System.in);
		try {
			while (true) {
				System.out.println("输入聊天信息，格式为（ip:内容）");
				info = sc.nextLine();//4
				if (info.equals("bye")) {
					System.out.println("结束与对方聊天!");
					break;
				}else {
					int maxSplit = 2;
					String[] arr = info.split(":", maxSplit);
					if(arr.length!=2) {
						System.out.println("聊天格式错误!");
						break;
					}
					String ip=info.split(":")[0];
					String message=info.split(":")[1];
					//获取要交谈人的socket
					Socket soc=socketMap.get(ip);
					//输出流
					DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
					dos.writeUTF(message);
					dos.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			sc.close();
		}
		
	}

}
