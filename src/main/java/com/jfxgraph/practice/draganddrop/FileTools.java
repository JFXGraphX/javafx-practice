package com.jfxgraph.practice.draganddrop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class FileTools {
	public static String encoding="UTF-8"; 
	public static String readFile(File file) {
		StringBuilder resultStr = new StringBuilder();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),encoding);
			BufferedReader bReader = new BufferedReader(isr);
			String line = bReader.readLine();
			System.out.println("Fdafda:" + isr.getEncoding());
			while (line != null) {
				//add the line separator
				line += System.getProperty("line.separator");
				//line = line.replaceAll("\n", System.getProperty("line.separator"));
				resultStr.append(line);
				line = bReader.readLine();
			}
			bReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultStr.toString();
	}

	public static void writeFile(File file, String str) {
        try {
		  BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
		  bWriter.write(str.replaceAll("\n", System.getProperty("line.separator")));
		  bWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
