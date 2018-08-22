package com.agree.framework.web.service.administration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.service.base.BaseService;

/** 
* 机构信息表
*/
@SuppressWarnings({"unchecked","rawtypes"})
public class UnitinfoService extends BaseService implements IUnitinfoService{
	private IAdministrationService administrationService;

	public void setAdministrationService(IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}
	public IAdministrationService getAdministrationService() {
		return administrationService;
	}
	/**
	 * 添加机构信息
	 */
	@Transactional
	public ServiceReturn addUnit(Unit unit,String createflag) throws Exception {
		//Integer unitid = (Integer) sqlDao_h.getRecord("select max(u.unitid) from Unit u");
		//unit.setUnitid(String.valueOf(unitid.intValue() + 1));
		checkBranchNo(unit);
		checkBankLevel(unit);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		unit.setCreatedate(date);
		unit.setLastmoddate(date);
		unit.setUnitlevel(String.valueOf(Integer.parseInt(unit.getUnitlevel()) + 1));
		unit.setUnitlist(unit.getUnitlist() + String.valueOf(unit.getUnitid())+"|");
		Object[] param ={unit.getUnitname(),unit.getUnitlevel(),unit.getParentunitid(),unit.getCreateuser(),unit.getCreatedate(),unit.getLastmoduser(),unit.getLastmoddate(),unit.getUnitorder(),unit.getUnitlist(),unit.getAddress(),unit.getLongitude(),unit.getLatitude(),unit.getBank_level(),unit.getNear_station(),unit.getBank_tel(),unit.getManager_name(),unit.getManager_phone(),unit.getManager_tel(),unit.getCity(),unit.getNote(),unit.getCheckcode(),unit.getUnitid()}; 
		String sql = "insert into BT_SYS_BRANCH_INFO (BRANCH_NAME, UNITLEVEL, SUBBANK_NUM, CREATEUSER, CREATEDATE, LASTMODUSER, LASTMODDATE, UNITORDER, UNITLIST, ADDRESS, LONGITUDE, LATITUDE, BANK_LEVEL, NEAR_STATION, BANK_TEL, MANAGER_NAME, MANAGER_PHONE, MANAGER_TEL, CITY, NOTE, CHECKCODE, BRANCH) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		sqlDao_h.excuteSql(sql, param);
		//机构创建完成 判断是否创建相关的4个用户
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		/*if("1".equals(createflag)){
			String[] usernames ={"网点负责人","网点大堂经理","网点理财经理","网点预留人员信息"};
			String[] defaultflags ={"1","2","3","4"};
			for(int i=0;i<4;i++){
				String username = usernames[i];
				Map<String, String> map = new HashMap<String, String>();
				map.put("default_flag", defaultflags[i]);
				CreatUser(unit,i,date,username,map);
			}
		}*/
		
		return sRet;
	}
	/*@Transactional
	*//**
	 * 创建机构默认的4个用户
	 **//*
	public void CreatUser(Unit unit,int i,String date,String username, Map<String, String> map) throws Exception{
		User user = new User();
		user.setUsercode(unit.getUnitid()+"0"+(i+1));
		user.setUnitid(unit.getUnitid());
		user.setUsername(username);
		user.setPassword("888888");
		user.setUsertype("2");
		user.setCreateuser(unit.getCreateuser().toString());
		user.setCreatedate(date);
		user.setState("1");
		administrationService.addSystemUser_itransc(user);
		String hql = "from Role r where r.default_flag =:default_flag";
		String hql1 ="from User f where f.usercode =:usercode";
		Map<String,String> map1 = new HashMap<String, String>();
		map1.put("usercode", user.getUsercode());
		List<Role> list=(List<Role>) sqlDao_h.getRecordList(hql,map,false);
		user = (User) sqlDao_h.getRecord(hql1,map1,false);
		for(int j=0;j<list.size();j++){
			String userid = user.getUserid().toString();
			String roleid = list.get(j).getRoleid();
			String[] param ={userid,roleid};
			String sql = "insert into T_BSMS_MNGUSERROLE values(?,?)";
			sqlDao_h.excuteSql(sql, param);
		}
		
	}*/
	
	/**
	 * 删除机构信息
	 * @edit lilei 2011-10-13
	 */
	public ServiceReturn delUnit(List<Unit> units) throws Exception {
		for(Unit unit : units){
			Long countPent = (Long) sqlDao_h.getRecord("select count(u.parentunitid) from Unit u where u.parentunitid = '"+unit.getUnitid() + "'");
			if(countPent.intValue() == 0){
				//modified 2012-11-1 已删除用户，不考虑
				Long counts = (Long) sqlDao_h.getRecord("select count(*) from User u where u.unitid = '"+unit.getUnitid()+ "' and u.state!='"+ApplicationConstants.USERSTATE_DEL.toString() + "'");
				if(counts.intValue() == 0){
					//级联调用存储过程结束
					sqlDao_h.deleteById(Unit.class, unit.getUnitid());
				}else{
					throw new AppException("该机构正在被用户使用,不能删除该机构,机构号:"+unit.getUnitid());
				}
			}else{
				throw new AppException("该机构存在下级机构,不能删除该机构,机构号:"+unit.getUnitid());
			}
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		return ret;
	}
	
	/**
	 * 修改机构信息
	 *  @edit lilei 2011-10-13
	 */
	public ServiceReturn editUnit(Unit unit,String newParentid) throws Exception {
		checkBankLevel(unit);
		Unit newUnit = (Unit)sqlDao_h.getRecord("from Unit u where u.unitid = '"+unit.getUnitid_old() + "'");//获得修改机构信息
		if(Integer.parseInt(newUnit.getParentunitid()) == -1 && Integer.parseInt(newUnit.getUnitlevel()) == 0){			//修改总行,不可以修改parentunitid
			throw new AppException("总行信息不可修改");
		}else {
			String oldList = unit.getUnitlist();
			int oldLevel = Integer.parseInt(unit.getUnitlevel());
			newUnit.setParentunitid(newParentid);
			if(StringUtils.isNotEmpty(newParentid)){
				Unit parent = (Unit)sqlDao_h.getRecord("from Unit u where u.unitid ='"+newParentid + "'");
				newUnit.setUnitlevel(String.valueOf(Integer.parseInt(parent.getUnitlevel()) + 1));
				newUnit.setUnitlist(parent.getUnitlist() + unit.getUnitid() + "|");
				
				//修改当前机构的下级机构
				//editNextUnit(newUnit,oldLevel, oldList);
				int level = Integer.parseInt(newUnit.getUnitlevel()) - oldLevel;
				List<Integer> nexts = getNextUnitAll(String.valueOf(newUnit.getUnitid()));
				nexts.remove(newUnit.getUnitid());
				if(nexts.size() != 0){
					Map map=new HashMap();
					//查询当前节点的子节点
					List<Unit> nextUnits = sqlDao_h.getRecordList("from Unit u where u.unitid in (select t.unitid from Unit t where t.unitlist!='"+unit.getUnitlist()+"' and t.unitlist like '"+unit.getUnitlist()+"%')", map,false);
					for(int i=0; i<nextUnits.size(); i++){
						Unit nextUn = (Unit)nextUnits.get(i);
						if(newUnit.getUnitid().equals(nextUn.getUnitid())){
							continue;
						}
						Unit nextUnit = (Unit)sqlDao_h.getRecord("from Unit u where u.unitid ='"+nextUn.getUnitid() + "'");
						nextUnit.setUnitlevel(String.valueOf(Integer.parseInt(nextUnit.getUnitlevel()) + level));
						nextUnit.setUnitlist(nextUnit.getUnitlist().replace(oldList, newUnit.getUnitlist()));
						nextUnit.setLastmoddate(newUnit.getLastmoddate());
						nextUnit.setLastmoduser(newUnit.getLastmoduser());
						sqlDao_h.updateRecord(nextUnit);
					}
				}
				
			}
		}		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		newUnit.setLastmoddate(sdf.format(new Date()));
		newUnit.setLastmoduser(unit.getLastmoduser());
		newUnit.setUnitname(unit.getUnitname());
		newUnit.setUnitorder(unit.getUnitorder());
		newUnit.setParentName(unit.getParentName());
		newUnit.setAddress(unit.getAddress());
		newUnit.setBank_level(unit.getBank_level());
		newUnit.setBank_tel(unit.getBank_tel());
		newUnit.setCity(unit.getCity());
		newUnit.setLatitude(unit.getLatitude());
		newUnit.setLongitude(unit.getLongitude());
		newUnit.setManager_name(unit.getManager_name());
		newUnit.setManager_phone(unit.getManager_phone());
		newUnit.setManager_tel(unit.getManager_tel());
		newUnit.setNear_station(unit.getNear_station());
		newUnit.setNote(unit.getNote());
		//newUnit.setMngbrno(unit.getMngbrno());
		sqlDao_h.updateRecord(newUnit);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		return ret;
	}
	
	/**
	 * 机构信息查询
	 */
	public List<Unit> findUnitinfoAll(Map map, Page pageInfo) throws Exception {
		List unitList = new ArrayList<Unit>();
		String sql = "from Unit u where 1=1 ";
		String unitid = (String)map.get("unitid");
		if(StringUtils.isNotEmpty(unitid)){
			sql += " and (u.unitlist like concat(concat('%|',:unitid),'|%') or unitlist is null)";
		}
		String unitname = (String)map.get("unitname");
		if(StringUtils.isNotEmpty(unitname)){
			sql += "and (u.unitname like :unitname or u.unitid like :unitname) ";
		}
		sql += "order by u.unitlevel,u.unitid asc";//substr(u.unitid,1,2) asc
		unitList = sqlDao_h.queryPage(sql,map,pageInfo,false);
		unitList = formatUnitName(unitList);
		return unitList;
	}

	/**
	 * 获取该机构下的所有机构
	 * */
	public List<Integer> getNextUnitAll(String unitid){
		List<Integer> nextUnitList = new ArrayList<Integer>();
		List<Unit> units=(List<Unit>) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITS);//获取内存所有机构
		for(Unit unit : units){
			String unitlist=unit.getUnitlist();
			String[] strs=unitlist.split("\\|");//获得机构上级菜单数组
			for(int i=0;i<strs.length;i++){
				if(strs[i].equals(unitid)){
					nextUnitList.add(Integer.parseInt(unit.getUnitid()));
					break;
				}
			}
		}
		return nextUnitList;
	}
	
	public List<Unit> formatUnitName(List<Unit> units){
		List<Unit> unitList = new ArrayList<Unit>();
		for(Unit unit : units){
			if(unit.getParentunitid()!=null&&!("".equals(unit.getParentunitid()))){
				Unit parent = getParentUnit(Integer.parseInt(unit.getParentunitid()));
				unit.setParentName(parent.getUnitname());
				unit.setUnitname(unit.getUnitname().replace("珠海华润银行", "").replace("股份有限公司", ""));
				unitList.add(unit);
			}else{
				unit.setUnitname(unit.getUnitname().replace("珠海华润银行", "").replace("股份有限公司", ""));
				unitList.add(unit);
			}
		}
		return unitList;
	}
	
	public Unit getParentUnit(Integer parentId){
		List<Unit> initUnits=(List<Unit>) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITS);
		for(Unit unit : initUnits){
			if(Integer.parseInt(unit.getUnitid()) == parentId.intValue()){
				
				return unit;
			}
		}
		return new Unit();
	}
	
	/**
	 * 检验机构号是否存在
	 * @param unit
	 * @throws Exception
	 */
	private void checkBranchNo(Unit unit) throws Exception{
		Long countPent = (Long)sqlDao_h.getRecord("select count(u.unitid) from Unit u where u.unitid = '"+unit.getUnitid() + "'");
		if(countPent>0){
			throw new AppException("机构号已存在");
		}
	}
	
	/**
	 * 检验银行级别是否有问题
	 * @param unit
	 * @throws Exception
	 */
	private void checkBankLevel(Unit unit) throws Exception{
		Unit parent = (Unit)sqlDao_h.getRecord("from Unit u where u.unitid = '"+unit.getParentunitid() + "'");
		//网点添加机构
		if("4".equals(parent.getBank_level())){
			throw new AppException("级别为网点的机构不可添加子机构");
		}
		//判断添加的机构的banklevel是否比父机构还要高
		if(Integer.parseInt(parent.getBank_level())>=Integer.parseInt(unit.getBank_level())){
			throw new AppException("不可添加比父机构级别更高的子机构");
		}
	}
	
	/**
	 * 修改用户状态为禁用
	 */
	public ServiceReturn changeUnitUserState(Unit unit) {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String hql = "update User set state=2 where unitid=:unitid";
		
		Map map = new HashMap();
		map.put("unitid", unit.getUnitid());
		sqlDao_h.excuteHql(hql, map);
		ret.setSuccess(true);
		return ret;
	}
}
