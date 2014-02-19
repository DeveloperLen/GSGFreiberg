package com.rojel.gsgfreiberg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;

public class GSGSave {
	public static Document page;
	public static String lastFilter;
	
	public static void load(Context context) {
		GSGSave.File file = (File) loadObject(context.getString(R.string.filename), context);
		
		if (file == null) {
			file = new GSGSave.File();
			saveObject(file, context.getString(R.string.filename), context);
		}
		
		page = Jsoup.parse(file.html);
		lastFilter = file.lastFilter;
	}
	
	public static void save(Context context) {
		GSGSave.File file = new GSGSave.File();
		
		file.html = page.html();
		file.lastFilter = lastFilter;
		
		saveObject(file, context.getString(R.string.filename), context);
	}
	
	private static Object loadObject(String fileName, Context context) {
		Object object = null;
		
		try {
			FileInputStream fis = context.openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			object = is.readObject();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	private static void saveObject(Object object, String fileName, Context context) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(object);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class File implements Serializable {
		private static final long serialVersionUID = 7996304149655430555L;
		
		public String html = "";
		public String lastFilter = "";
	}
}
