package cn.gyx.mybatis.run;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.opensymphony.xwork2.interceptor.annotations.Before;





public class MybatisTest {
	
	SqlSessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		//��ʼ��session����
		InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
		sessionFactory = new SqlSessionFactoryBuilder().build(in);
	}

	
	@Test
	public void selectPersonByIdWithRM() throws Exception {
		setUp();
		//����һ��session
		SqlSession session = sessionFactory.openSession();
		try {
			//selectOne��һ������namespace+"."+sql����id���ҵ�Ψһһ��sql�� �ڶ���������Ҫ���ݸ�sql�Ĳ���
			
			List<String> list = session.selectList("sql.mapper.BranchGroupRLT.selectByPrimaryKey");
			//session.selectOne("sql.mapper.BranchGroupRLT.addBranchGroupRLT", 1430121);
			for(int i = 0;i < list.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("branchgroupid", "BG003");
				map.put("branchnum", list.get(i));
				map.put("mainbranch", "0001");
				map.put("mainuser", "000107");
				map.put("maindate", "20180912");
				map.put("maintime", "203204");
				session.insert("sql.mapper.BranchGroupRLT.addBranchGroupRLT", map);
				session.commit();
			}
			System.out.println(list);
		}finally{
			session.close();
		}
		
	}
}
