package com.example.myproject.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class ClientChat {
	private String host;
	private int port;
	private DataInputStream in;
	private DataOutputStream out;
	private ConcurrentHashMap<String, Socket> socketMap;
	public ClientChat(String host, int port,ConcurrentHashMap<String, Socket> socketMap) {
		super();
		this.host = host;
		this.port = port;
		this.socketMap = socketMap;
		Socket s=null;
		try {
			s=new Socket(host,port);
			this.in=new DataInputStream(s.getInputStream());
			this.out=new DataOutputStream(s.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}
	//客户端读写操作
	public void startChat() {
		Scanner sca= new Scanner(System.in);
		try {
			while(true) {
				Thread read=new Thread(new ReadThread(in));
				Thread write=new Thread(new WriteThread(socketMap));
				read.start();
				write.start();
				//写入阻塞
				write.join();
			}
		}catch(Exception e) {
			e.printStackTrace();
			sca.close();
		}



	}


}
