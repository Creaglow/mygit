package com.lq.proxy1;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;



public class Proxy {
    /**
     * 用来产生新的代理类
     * @return
     */
	//JDK6 Complier API,CGLib,ASM
	public static Object newProxyInstance(Class infce, InvocationHandler h) throws Exception{  
		String methodStr = "";
		String rt = "\r\n";
		
		Method[] methods = infce.getMethods();
		
		/*for(Method m : methods) {
			methodStr += "@Override" +rt +
					"public void " + m.getName() + "() {" + rt +
				    "	     long start = System.currentTimeMillis();" + rt +
				    "        System.out.println(\"starttime:\" + start);" + rt +
				    "        t." +m.getName() + "();"+ rt +
				    "        long end = System.currentTimeMillis();" + rt +
				    "        System.out.println(\"time:\" + (end-start));" + rt +
					"}";
		}*/
		for(Method m : methods) {
			methodStr += "@Override" +rt +
					"public void " + m.getName() + "() {" + rt +
					"    try{" + rt +
					"    Method md = " + infce.getName() + ".class.getMethod(\"" +m.getName() +"\");" + rt +
					"    h.invoke(this, md);" + rt +
					"    }catch(Exception e) {e.printStackTrace();}" + rt +					
					"}";
		}
	
		
		
		String src = 
				
				"package com.lq.proxy1;" + rt +
				"import java.lang.reflect.Method;" + rt +

				"public class $Proxy1 implements " + infce.getName() + "{" + rt +
				"    public $Proxy1(InvocationHandler h) {" + rt +
			    "        super();" + rt +
				"        this.h = h;" + rt +
				"    }" + rt +

				"    com.lq.proxy1.InvocationHandler h;" + rt +

				methodStr +	
				"}";
		//System.out.println(System.getProperty("user.dir"));
				String fileName =   "d:/src/com/lq/proxy1/$Proxy1.java";
				File f = new File(fileName);
				FileWriter fw = new FileWriter(f);
				fw.write(src);
				fw.flush();
				fw.close();
				/**
				 * compile
				 * 从JavaCompiler开始到最后的作用是生成class文件
				 */
				//拿到系统当前默认的java编译器，即javac
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
				//System.out.println(compiler.getClass().getName());
				//第一个参数：诊断监听器
				StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
				Iterable units = fileMgr.getJavaFileObjects(fileName);  //编译哪些文件
				CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
				t.call();
				fileMgr.close();
				
				//load into memory and create an instance
				URL[] urls = new URL[] {new URL("file:/" + "d:/src/")};
				URLClassLoader ul = new URLClassLoader(urls);
				Class c = ul.loadClass("com.lq.proxy1.$Proxy1");
				System.out.println(c);
				
				//拿到参数为Moveable类型的class类型的Constructor
				Constructor ctr = c.getConstructor(InvocationHandler.class);
				//Tank为被代理对象
				Object m = ctr.newInstance(h);
				//m.move();
				
			
		return m;
		
	}

}