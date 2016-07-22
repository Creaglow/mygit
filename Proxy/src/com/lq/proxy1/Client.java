package com.lq.proxy1;

public class Client {
	public static void main(String[] args) throws Exception {
		Tank t= new Tank();
		InvocationHandler h = new TimeHandler(t);
		
		//动态代理就是不用再看到代理类的名字了，比如TankTimeProxy
		Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class, h);
		
		m.move();
	}

}
//实际情况中看不到Proxy类和InvocationHandler类