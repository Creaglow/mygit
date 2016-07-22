package com.lq.proxy1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler{
    
	private Object target;

	public TimeHandler(Object target) {
		super();
		this.target = target;
	}


	@Override
	public void invoke(Object o, Method m) {  //必须有一个对象，哪个对象调的这个方法（Method）
		 long start = System.currentTimeMillis();
		 System.out.println("starttime:" + start);
		 try {
			m.invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		 long end = System.currentTimeMillis();
		 System.out.println("time:" + (end-start));
	}

}
