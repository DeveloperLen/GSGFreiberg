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
			
			if(lesson.classname.startsWith("0",0)&&lesson.classname.startsWith(classname,1)){
				result.add(lesson);
			}else if(lesson.classname.startsWith("1",0)&&lesson.classname.startsWith(classname,0)){
				result.add(lesson);
			}
		}
		
		return result;
	}
	
	public int size() {
		return schedule.size();
	}
}
