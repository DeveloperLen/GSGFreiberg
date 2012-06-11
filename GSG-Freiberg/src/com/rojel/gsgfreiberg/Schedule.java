package com.rojel.gsgfreiberg;

import java.io.Serializable;
import java.util.ArrayList;

public class Schedule implements Serializable {
	private static final long serialVersionUID = 7701010616467501059L;
	
	private ArrayList<Lesson> schedule = new ArrayList<Lesson>();
	
	public Lesson get(int i) {
		return schedule.get(i);
	}
	
	public void add(Lesson lesson) {
		schedule.add(lesson);
	}
	
	public ArrayList<Lesson> getByClass(String classname) {
		ArrayList<Lesson> result = new ArrayList<Lesson>();
		
		for(Lesson lesson : schedule) {
			if(lesson.classname.contains(classname))
				result.add(lesson);
		}
		
		return result;
	}
	
	public int size() {
		return schedule.size();
	}
}
