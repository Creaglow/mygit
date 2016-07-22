package com.lq.proxy;

public class Client {
	public static void main(String[] args) {
		Tank t= new Tank();
//		TankTimeProxy ttp = new TankTimeProxy(t);
//		TankLogProxy tlp = new TankLogProxy(ttp);   //�ȼ�¼��־�ټ�¼ʱ��
//		Moveable m = tlp;
		
		TankLogProxy tlp = new TankLogProxy(t);   //�ȼ�¼ʱ���ټ�¼��־
		TankTimeProxy ttp = new TankTimeProxy(tlp);
		Moveable m = ttp;
		m.move();
	}

}
