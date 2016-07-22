package com.lq.proxy1;

import java.lang.reflect.Method;

public interface InvocationHandler {
	public void  invoke(Object o, Method m);  
    
}
