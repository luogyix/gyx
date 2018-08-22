package cn.gyx.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class RunTest {

	public static void main(String[] args) {
		//创建目标对象，参数内部调用的对象必须时final（不可改变的）
		final IUserDao target = new UserDaoImpl();
		//创建代理对象 参数1：目标对象的类加载器，参数2：目标对象实现的接口们，参数3：回掉处理器
		IUserDao proxy = (IUserDao) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new InvocationHandler() {
					
					//拦截到目标对象后要执行的方法,也就是通知
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("拦截到目标对象方法："+method.getName()+",次此方法的参数为:"+(int)args[0]);
						//放行
						return method.invoke(target, args);
					}
				});
		//调用代理对象方法
		proxy.save(11);
		proxy.delect(12);
	}
	
	@Test
	public void cflibProxy(){
		//创建目标对象
		final UserDaoImpl target = new UserDaoImpl();
		//创建代理对象
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new MethodInterceptor() {
			
			@Override
			public Object intercept(Object obj, Method method, 
					Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("拦截到目标对象："+target.getClass().getName()+",拦截到目标对象方法："+method.getName()+",次此方法的参数为:"+(int)args[0]);
				return method.invoke(target, args);
			}
		});
		
		UserDaoImpl proxy = (UserDaoImpl)enhancer.create();
		proxy.save(94);
		proxy.delect(254);
	}
}
