package com.rojel.gsgfreiberg;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

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
		System.out.println(page.title());
		
		return page;
	}
	
	public static Schedule parse(Document doc) {
		Schedule result = new Schedule();
		
		Element schedule = doc.getElementById("Vertretungsplan");
		Elements lessons = schedule.getElementsByTag("tr");

		lessons.remove(0); //remove header
		lessons.remove(0);
		
		System.out.println("Number of cancels: " + lessons.size());
		
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
