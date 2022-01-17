package com.esoe.util;

import org.json.JSONObject;
import java.io.InputStream;
import java.util.Scanner;

public class FileHandle {

	public static InputStream inputStreamFromFile(String path) {
		try {
			InputStream inputStream = FileHandle.class.getResourceAsStream(path);
			return inputStream;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// TODO: Check if delete
	public static String getJSONStringFromFile(String path) {
		Scanner scanner;
		InputStream in = FileHandle.inputStreamFromFile(path);
		scanner = new Scanner(in);
		String json = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return json;
	}

	public static JSONObject getJSONObjectFromFile(String path) {
		return new JSONObject(getJSONStringFromFile(path));
	}

	public static boolean objectExists(JSONObject jsonObject, String key) {
		Object o;
		try {
			o = jsonObject.get(key);
		} catch(Exception e) {
			return false;
		}
		return o != null;
	}
	
}
