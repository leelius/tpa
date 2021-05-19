//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:47:59
//-----------------------------

package tpa.service.impl;

import tpa.entity.Config;
import tpa.mapper.ConfigMapper;
import tpa.service.IConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

	@Autowired
	private ConfigMapper configMapper;
	@Override
	public Integer max(String fieldname) {
		return configMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(configMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		configMapper.updatePlus(fieldname, addend, id);
	}
}
