//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:47:59
//-----------------------------

package tpa.service.impl;

import tpa.entity.Evaluation_type;
import tpa.mapper.Evaluation_typeMapper;
import tpa.service.IEvaluation_typeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("evaluation_typeService")
public class Evaluation_typeServiceImpl extends ServiceImpl<Evaluation_typeMapper, Evaluation_type> implements IEvaluation_typeService {

	@Autowired
	private Evaluation_typeMapper evaluation_typeMapper;
	@Override
	public Integer max(String fieldname) {
		return evaluation_typeMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(evaluation_typeMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		evaluation_typeMapper.updatePlus(fieldname, addend, id);
	}
}