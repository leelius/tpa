package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.DbTableDao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/***
 * 读取当前数据库中有哪些表
 * 
 * 在操作权限许可时需要查询当前的所有表名
 * 
 * @author Denny
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("DbService")
public class DbService {

	private static final Log logger = LogFactory.getLog(DbService.class);

	@Autowired
	@Qualifier("DbTableDao")
	private DbTableDao daodbTable;

	/***
	 * 按条件查询，返回DbTable列表
	 * 
	 * @param params
	 * @return DbTable列表
	 */
	@Transactional(readOnly = true)
	public List<String> getDbTableList(Map<String, Object> params) {

		logger.debug(this.getClass().getName() + ".getDbTableList()");
		return daodbTable.getlist(params);
	}

}
