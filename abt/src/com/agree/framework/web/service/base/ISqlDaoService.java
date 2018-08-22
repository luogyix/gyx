package com.agree.framework.web.service.base;
import org.springframework.dao.DataAccessException;

import com.agree.framework.web.common.DateLimitVo;

import javax.sql.DataSource;
import java.util.Map;
import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: yaojing
 * Date: 2008-6-10
 * Time: 10:38:49
 * To change this tsemplate use File | Settings | File Templates.
 */
@SuppressWarnings({"rawtypes"})
public interface ISqlDaoService {
  
    DataSource getDataSource();
    /**
      *
      * @param sqlID sql语句
      * @param Object 对象
      * @return list
      * @throws org.springframework.dao.DataAccessException
      */
    public List getPageRecordList(String sqlID, Object object) throws DataAccessException;

     /**
      * 
      *
      * @param sqlID sql语句
      * @param model 容器
      * @return List list
      * @throws DataAccessException
      */
     public List getPageRecordList(String sqlID, Map model) throws DataAccessException;
     /**
      * Obejct FOR QUERY
      *
      * @param sqlID   sql语句
      * @param object  对象
      * @return Object 对象
      * @throws DataAccessException 数据库访问异常
      */
     public Object getRecord(String sqlID, Object object) throws DataAccessException;

     /**
      * Obejct FOR QUERY
      *
      * @param sqlID   sql语句id
      * @param object  对象
      * @return Object 对象
      * @throws DataAccessException 数据库访问异常
      */
     public Object getRecord(String sqlID, Map model) throws DataAccessException;     
     
     /**
      *  不分页
      *
      * @param sqlID  sql语句id
      * @param object 查询参数
      * @return List
      * @throws DataAccessException 数据库访问异常
      */
     public List getRecordList(String sqlID, Object object) throws DataAccessException;

     /**
      *  不分页
      *
      * @param sqlID  sql语句id
      * @param object 查询参数
      * @return List
      * @throws DataAccessException 数据库访问异常
      */
     public List getRecordList(String sqlID, Map model) throws DataAccessException;     
     
     /**
      *  记录总数
      *
      * @param sqlID  sql语句id
      * @param object 查询参数
      * @return int
      * @throws DataAccessException 数据库访问异常
      */
     public int getRecordCount(String sqlID, Object object) throws DataAccessException;

     /**
      *  记录总数
      *
      * @param sqlID  sql语句id
      * @param object 查询参数
      * @return int
      * @throws DataAccessException 数据库访问异常
      */
     public int getRecordCount(String sqlID, Map model) throws DataAccessException;          

     /**
      * insert
      *
      * @param sqlID
      * @param object
      * @return int
      * @throws DataAccessException
      */
     public int insertRecord(String sqlID, Object object) throws DataAccessException;
     /**
      * insert
      *
      * @param sqlID
      * @param Map
      * @return int
      * @throws DataAccessException
      */
     public int insertRecord(String sqlID, Map model) throws DataAccessException;
     
     
     /**
      * update
      *
      * @param sqlID  sql语句id
      * @param object 更新对象
      * @return int 更新影响的记录数
      * @throws DataAccessException 数据库访问异常
      */
     public int updateRecord(String sqlID, Object object) throws DataAccessException;
     /**
      * update
      *
      * @param sqlID  sql语句id
      * @param object 更新Map
      * @return int 更新影响的记录数
      * @throws DataAccessException 数据库访问异常
      */
     public int updateRecord(String sqlID, Map model) throws DataAccessException;   
     /**
      * delete
      *
      * @param sqlID  sql语句id
      * @param object 删除对象
      * @return int 删除影响的记录数
      * @throws DataAccessException 数据库访问异常
      */
     public int deleteRecord(String sqlID, Object object) throws DataAccessException;
     /**
      * delete
      *
      * @param sqlID  sql语句id
      * @param object 删除Map
      * @return int 删除影响的记录数
      * @throws DataAccessException 数据库访问异常
      */
     public int deleteRecord(String sqlID, Map model) throws DataAccessException; 
     
     /**
      * 
      * @param sqlID  sql语句id
      * @param obj  查询参数
      * @return datalimit03\04对象
      * @throws DataAccessException
      */
     public DateLimitVo fetchdatelimit(String sqlID,Object obj) throws DataAccessException;
}

