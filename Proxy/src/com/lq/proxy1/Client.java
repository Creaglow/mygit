package com.lq.proxy1;

public class Client {
	public static void main(String[] args) throws Exception {
		Tank t= new Tank();
		InvocationHandler h = new TimeHandler(t);
		
		//��̬������ǲ����ٿ���������������ˣ�����TankTimeProxy
		Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class, h);
		
		m.move();
	}

}
//ʵ������п�����Proxy���InvocationHandler��