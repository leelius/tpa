//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.service.impl;

import tpa.entity.Vevaluation;
import tpa.mapper.VevaluationMapper;
import tpa.service.IVevaluationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("vevaluationService")
public class VevaluationServiceImpl extends ServiceImpl<VevaluationMapper, Vevaluation> implements IVevaluationService {

	@Autowired
	private VevaluationMapper vevaluationMapper;
	@Override
	public Integer max(String fieldname) {
		return vevaluationMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(vevaluationMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		vevaluationMapper.updatePlus(fieldname, addend, id);
	}
}
