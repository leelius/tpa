//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:47:59
//-----------------------------

package tpa.service.impl;

import tpa.entity.Evaluation;
import tpa.mapper.EvaluationMapper;
import tpa.service.IEvaluationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("evaluationService")
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements IEvaluationService {

	@Autowired
	private EvaluationMapper evaluationMapper;
	@Override
	public Integer max(String fieldname) {
		return evaluationMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(evaluationMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		evaluationMapper.updatePlus(fieldname, addend, id);
	}
}
