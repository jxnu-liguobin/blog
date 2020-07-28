package cn.edu.jxnu.blog.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author： liguobin
 * @Description： 该功能已经在PVFinalCount工具类中
 * @时间： 2018-1-5 下午4:09:57
 * @version： V1.0
 * 
 */
@Deprecated
public class FileUtil {

	public static void writeFileString(String count) throws IOException {
		File file = new File("C:\\web\\PV");
		if (!file.isDirectory())
			file.mkdir();// 创建目录
		File fileDir = new File(file, "pv.txt");
		if (!fileDir.isFile()) {
			try {
				fileDir.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileDir);// FileWriter写入文件时不能指定编码格式，编码格式是系统默认的编码格式
			fw.write(count); // 向文件中写入字符串
			fw.flush(); // 刷新
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fw.close(); // 关闭流
		}
	}

	public static String readFileString() throws IOException {
		BufferedReader br = null;
		FileReader fr = null;
		String string = null;
		try {
			fr = new FileReader("C:\\web\\PV\\pv.txt");// 字符读入流
			br = new BufferedReader(fr);
			string = br.readLine();// 读入数据
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fr.close();
			br.close();
		}
		return string;
	}

	public static void main(String[] args) throws IOException {
		FileUtil.writeFileString("88888");
		String countString = FileUtil.readFileString();
		System.out.println(countString);
	}
}