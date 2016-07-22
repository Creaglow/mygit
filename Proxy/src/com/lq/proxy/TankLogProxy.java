package com.lq.proxy;

public class TankLogProxy implements Moveable{
	public TankLogProxy(Moveable t) {
		super();
		this.t = t; 
	}

	Moveable t;

	@Override
	public void move() {
		System.out.println("Tank Start....");
		t.move(); 
		System.out.println("Tank Stop....");
		
	}
	

}
