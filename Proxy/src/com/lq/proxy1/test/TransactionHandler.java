package com.lq.proxy1.test;

import java.lang.reflect.Method;

import com.lq.proxy1.InvocationHandler;

public class TransactionHandler implements InvocationHandler{
	private Object target;

	public TransactionHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public void invoke(Object o, Method m) {
		 System.out.println("Tansaction Start");
		 try {
			m.invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		 System.out.println("Transaction Commit");
	
	}

}
