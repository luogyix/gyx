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
		//����Ŀ����󣬲����ڲ����õĶ������ʱfinal�����ɸı�ģ�
		final IUserDao target = new UserDaoImpl();
		//����������� ����1��Ŀ�������������������2��Ŀ�����ʵ�ֵĽӿ��ǣ�����3���ص�������
		IUserDao proxy = (IUserDao) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new InvocationHandler() {
					
					//���ص�Ŀ������Ҫִ�еķ���,Ҳ����֪ͨ
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("���ص�Ŀ����󷽷���"+method.getName()+",�δ˷����Ĳ���Ϊ:"+(int)args[0]);
						//����
						return method.invoke(target, args);
					}
				});
		//���ô�����󷽷�
		proxy.save(11);
		proxy.delect(12);
	}
	
	@Test
	public void cflibProxy(){
		//����Ŀ�����
		final UserDaoImpl target = new UserDaoImpl();
		//�����������
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new MethodInterceptor() {
			
			@Override
			public Object intercept(Object obj, Method method, 
					Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("���ص�Ŀ�����"+target.getClass().getName()+",���ص�Ŀ����󷽷���"+method.getName()+",�δ˷����Ĳ���Ϊ:"+(int)args[0]);
				return method.invoke(target, args);
			}
		});
		
		UserDaoImpl proxy = (UserDaoImpl)enhancer.create();
		proxy.save(94);
		proxy.delect(254);
	}
}
