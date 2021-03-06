//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.service.impl;

import tpa.entity.Vlink_experiment_curriculum_min;
import tpa.mapper.Vlink_experiment_curriculum_minMapper;
import tpa.service.IVlink_experiment_curriculum_minService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import app.entity.StatExperimentCurriculum;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vlink_experiment_curriculum_minService")
public class Vlink_experiment_curriculum_minServiceImpl
		extends ServiceImpl<Vlink_experiment_curriculum_minMapper, Vlink_experiment_curriculum_min>
		implements IVlink_experiment_curriculum_minService {

	@Autowired
	private Vlink_experiment_curriculum_minMapper vlink_experiment_curriculum_minMapper;

	@Override
	public Integer max(String fieldname) {
		return vlink_experiment_curriculum_minMapper.max(fieldname);
	}

	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(vlink_experiment_curriculum_minMapper.statMapByFieldname(fieldname));
	}

	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		vlink_experiment_curriculum_minMapper.updatePlus(fieldname, addend, id);
	}

	@Override
	public List<StatExperimentCurriculum> statExperimentCurriculum(String semester) {

		return vlink_experiment_curriculum_minMapper.statExperimentCurriculum(semester);
	}

}
