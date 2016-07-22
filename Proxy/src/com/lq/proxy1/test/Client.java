package com.lq.proxy1.test;

import com.lq.proxy1.InvocationHandler;
import com.lq.proxy1.Proxy;


public class Client {
	public static void main(String[] args) throws Exception {
		UserMgr mgr = new UserMgrImpl();
		InvocationHandler h = new TransactionHandler(mgr);
		UserMgr u = (UserMgr) Proxy.newProxyInstance(UserMgr.class, h);
		u.addUser();

	}
   
}
