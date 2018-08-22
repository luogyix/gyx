package cn.gyx.jdkProxy;

public class UserDaoImpl implements IUserDao {

	public void save(int i) {
		System.out.println("增加方法触发");
	}

	public void delect(int i) {
		System.out.println("删除方法触发");
	}

}
