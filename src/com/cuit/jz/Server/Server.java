package com.cuit.jz.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Server {
	static String user;
	static String dirPath;

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(10086);
		Socket accept = serverSocket.accept();
		InputStream inputStream = accept.getInputStream();
		OutputStream outputStream = accept.getOutputStream();
		handel(inputStream, outputStream);
		serverSocket.close();

	}

	private static void handel(InputStream inputStream, OutputStream outputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		writer.write("connect success\n");
		writer.flush();
		String user = reader.readLine();
		String dirPath = "AccData/" + user;//账户数据文件夹
		String fileName = dirPath + "/" + user + ".txt";
		System.out.println(user);
		System.out.println(dirPath);
		System.out.println(fileName);
		File file = new File(dirPath);
		File acc = new File(fileName);
		while (true) {
			String line = reader.readLine();
			System.out.println(line);
			if (line.equals("bye")) {
				writer.write("bye\n");
				writer.flush();
				break;
			}
			if (line.equals("1")) {//上传文件
				writer.write("开始上传:\n");
				System.out.println("开始上传");
				writer.flush();
				StringBuilder sb = new StringBuilder();
				String s;
				while ((s = reader.readLine()) != null) {
					sb.append(s).append(System.getProperty("line.separator"));
					if (s.equals("")) break;
				}
				System.out.println(sb);
				//写入文件
				if (!file.exists()) {
					file.mkdirs();
					FileWriter fileWriter = new FileWriter(fileName);
					fileWriter.write(String.valueOf(sb));
					fileWriter.close();
				} else {//该用户文件存在
					if (acc.exists()) {
						acc.delete();
						FileWriter fileWriter = new FileWriter(fileName);
						fileWriter.write(String.valueOf(sb));
						fileWriter.close();
					} else {
						FileWriter fileWriter = new FileWriter(fileName);
						fileWriter.write(String.valueOf(sb));
						fileWriter.close();
					}
				}


			}
			if (line.equals("2")) {//下载账目
				if (acc.exists()) {
					System.out.println("test");
					BufferedReader reader1 = new BufferedReader(new FileReader(acc));
					String s;
					while ((s = reader1.readLine()) != null) {
						writer.write(s + "\n");
						if (s.equals("")) break;
					}
					System.out.println("write end");
					writer.flush();
				} else {
					System.out.println("该用户没有上传的账目");
					writer.write("该用户没有上传的账目\n");
					writer.flush();
				}
			}
			if (line.equals("3")) {//upload files
				String name = reader.readLine();
				File file1 = new File(name);
				System.out.println(name);
				System.out.println("kaishi shangchuan");
				String length = reader.readLine();
				int length1 = Integer.parseInt(length);
				FileOutputStream outputStream1 = new FileOutputStream(dirPath+"/"+name);
				BufferedOutputStream bos = new BufferedOutputStream(outputStream1);
				byte[] bytes = new byte[1024];
				int len;
				int temp=0;
				while ((len = inputStream.read(bytes))!= -1) {
                    bos.write(bytes, 0, len);
					temp+=len;
					bos.flush();
					if(temp>=length1) break;
                }
				bos.close();
				outputStream1.close();
				System.out.println("上传完毕");
			}
			if (line.equals("4")) {

			}
		}
	}
}