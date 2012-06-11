package com.rojel.gsgfreiberg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.concurrent.ExecutionException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;

public class HTMLHandler {	
	public static Document downloadPage(String url) {		
		Document page = null;
		
		System.out.println("Downloading page.");
		
		try {
			page = new DownloaderTask().execute(url).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("Download finished.");
		
		return page;
	}
	
	public static Schedule parse(Document doc) {
		Schedule result = new Schedule();
		
		Element schedule = doc.getElementById("Vertretungsplan");
		Elements lessons = schedule.getElementsByTag("tr");

		lessons.remove(0); //remove header
		lessons.remove(0);
		
		for(Element cancel : lessons) {
			String date = cancel.getElementsByTag("td").get(0).text();
			String day = cancel.getElementsByTag("td").get(1).text();
			String classname = cancel.getElementsByTag("td").get(2).text();
			String teacher = cancel.getElementsByTag("td").get(3).text();
			String subject = cancel.getElementsByTag("td").get(4).text();
			String lesson = cancel.getElementsByTag("td").get(5).text();
			String newSubject = cancel.getElementsByTag("td").get(6).text();
			String newTeacher = cancel.getElementsByTag("td").get(7).text();
			String room = cancel.getElementsByTag("td").get(8).text();
			String instead = cancel.getElementsByTag("td").get(9).text();
			
			Lesson newCancel = new Lesson(date, day, classname, teacher, subject, lesson, newSubject, newTeacher, room, instead);
			
			result.add(newCancel);
		}
		
		System.out.println("Parsed schedule from document.");
		
		return result;
	}
	
	public static void save(Context context, Document doc) throws IOException {
		String docString = doc.html();
		
		FileOutputStream fos = context.openFileOutput(context.getString(R.string.fileName), Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(docString);
		os.close();
	}
	
	public static Document load(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(context.getString(R.string.fileName));
		ObjectInputStream is = new ObjectInputStream(fis);
		String docString = (String) is.readObject();
		is.close();
		
		Document doc = Jsoup.parse(docString);
		
		return doc;
	}
	
	private static class DownloaderTask extends AsyncTask<String, Void, Document> {
		protected Document doInBackground(String... urls) {
			Document page = null;
			
			try {
				page = Jsoup.connect(urls[0]).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return page;
		}
	}
}
