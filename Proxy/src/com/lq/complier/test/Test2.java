package com.lq.complier.test;

import java.lang.reflect.Method;

public class Test2 {
	public static void main(String[] args) {
		Method[] methods = com.lq.proxy1.Moveable.class.getMethods();
		for(Method m : methods) {
			System.out.println(m.getName());
		}
	}

}
