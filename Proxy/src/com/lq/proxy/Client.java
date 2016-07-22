package com.lq.proxy;

public class Client {
	public static void main(String[] args) {
		Tank t= new Tank();
//		TankTimeProxy ttp = new TankTimeProxy(t);
//		TankLogProxy tlp = new TankLogProxy(ttp);   //先记录日志再记录时间
//		Moveable m = tlp;
		
		TankLogProxy tlp = new TankLogProxy(t);   //先记录时间再记录日志
		TankTimeProxy ttp = new TankTimeProxy(tlp);
		Moveable m = ttp;
		m.move();
	}

}
