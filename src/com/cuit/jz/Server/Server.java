package com.cuit.jz.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
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
		System.out.println("connecting----");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		writer.write("connect success\n");
		writer.flush();
		String user = reader.readLine();
		String dirPath = "AccData/" + user;//账户数据文件夹
		String fileName = dirPath + "/" + user + ".txt";
		System.out.println("用户名："+user);
//		System.out.println(dirPath);
//		System.out.println(fileName);
		File file = new File(dirPath);
		File acc = new File(fileName);
		label:
		while (true) {
			String line = reader.readLine();
			System.out.println("用户指令："+line);
			switch (line) {
				case "bye":
					writer.write("bye\n");
					writer.flush();
					break label;
				case "1": //上传文件
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
					break;
				case "2": //下载账目
					if (acc.exists()) {
						BufferedReader reader1 = new BufferedReader(new FileReader(acc));
						String s1;
						while ((s1 = reader1.readLine()) != null) {
							writer.write(s1 + "\n");
							if (s1.equals("")) break;
						}
						System.out.println("write end");
						writer.flush();
					} else {
						System.out.println("该用户没有上传的账目");
						writer.write("该用户没有上传的账目\n");
						writer.flush();
					}
					break;
				case "3": {//upload files
					if (!file.exists()) file.mkdirs();
					String name = reader.readLine();
					File file1 = new File(name);
					System.out.println(name);
					System.out.println("kaishi shangchuan");
					String length = reader.readLine();
					int length1 = Integer.parseInt(length);
					FileOutputStream outputStream1 = new FileOutputStream(dirPath + "/" + name);
					BufferedOutputStream bos = new BufferedOutputStream(outputStream1);
					byte[] bytes = new byte[1024];
					int len;
					int temp = 0;
					while ((len = inputStream.read(bytes)) != -1) {
						bos.write(bytes, 0, len);
						temp += len;
						bos.flush();
						if (temp >= length1) break;
					}
					bos.close();
					outputStream1.close();
					System.out.println("上传完毕");
					break;
				}
				case "4": {
					File[] files = file.listFiles();
					int len = files.length;
					if (len == 0) {
						writer.write("0\n");
						writer.flush();
						System.out.println("没有上传的文件");
						break label;
					}
					writer.write(len + "\n");
					writer.flush();
					for (int i = 1; i <= files.length; i++) {

						writer.write(i + "." + files[i - 1].getName() + "\n");
						writer.flush();
					}
					writer.flush();
					int index = Integer.valueOf(reader.readLine());
					System.out.println("用户下载第" + index + "个文件");
					File downLoad = files[index - 1];
					long fileLen = downLoad.length();
					System.out.println("文件大小：" + fileLen);
					writer.write(String.valueOf(fileLen) + "\n");
					writer.flush();
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downLoad));
					byte[] bytes = new byte[1024];
					int len1;
					while ((len1 = bis.read(bytes)) != -1) {
						outputStream.write(bytes, 0, len1);
						outputStream.flush();
					}
//				bos.close();
					System.out.println("write end");
					writer.flush();
					break;
				}
				default:

					System.out.println(">>>" + line);
					break;
			}
		}
	}
}