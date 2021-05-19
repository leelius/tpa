//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.service.impl;

import tpa.entity.User;
import tpa.mapper.UserMapper;
import tpa.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Override
	public Integer max(String fieldname) {
		return userMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(userMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		userMapper.updatePlus(fieldname, addend, id);
	}
	@Override
	public List<User> selectAdult(int current, int size) {
		Page<User> page = new Page<User>(2, 2);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>().gt(User.ID + "", 18);
		List<User> list = userMapper.selectPage(page, queryWrapper).getRecords();
		return list;
	}
}
