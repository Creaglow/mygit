package com.lq.complier.test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.lq.proxy1.Moveable;
import com.lq.proxy1.Tank;

public class Test1 {
	public static void main(String[] args) throws Exception {
		String rt = "\r\n";
		String src = 
				
				"package com.lq.proxy1;" + rt +

				"public class TankTimeProxy implements Moveable{" + rt +
				"    public TankTimeProxy(Moveable t) {" + rt +
			    "        super();" + rt +
				"        this.t = t;" + rt +
				"    }" + rt +

				"    Moveable t;" + rt +

				"    @Override" + rt +
				"    public void move() {" + rt +
				"        long start = System.currentTimeMillis();" + rt +
				"        System.out.println(\"starttime:\" + start);" + rt +
				"        t.move();" + rt +
				"        long end = System.currentTimeMillis();" + rt +
				"        System.out.println(\"time:\" + (end-start));" + rt +
						
				"    }" + rt +
					
				"}";
		//System.out.println(System.getProperty("user.dir"));
		String fileName = System.getProperty("user.dir") 
				+ "/src/com/lq/proxy1/TankTimeProxy.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(src);
		fw.flush();
		fw.close();
		/**
		 * compile
		 * ��JavaCompiler��ʼ����������������class�ļ�
		 */
		//�õ�ϵͳ��ǰĬ�ϵ�java����������javac
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
		//System.out.println(compiler.getClass().getName());
		//��һ����������ϼ�����
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable units = fileMgr.getJavaFileObjects(fileName);  //������Щ�ļ�
		CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
		t.call();
		fileMgr.close();
		
		//load into memory and create an instance
		URL[] urls = new URL[] {new URL("file:/" + System.getProperty("user.dir")+"/src")};
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("com.lq.proxy1.TankTimeProxy");
		System.out.println(c);
		
		//�õ�����ΪMoveable���͵�class���͵�Constructor
		Constructor ctr = c.getConstructor(Moveable.class);
		//TankΪ���������
		Moveable m = (Moveable) ctr.newInstance(new Tank());
		m.move();
	}

}
