//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: ServiceImpl
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.service.impl;

import tpa.entity.Vcurriculum;
import tpa.mapper.VcurriculumMapper;
import tpa.service.IVcurriculumService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sld.webutils.Util;

import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("vcurriculumService")
public class VcurriculumServiceImpl extends ServiceImpl<VcurriculumMapper, Vcurriculum> implements IVcurriculumService {

	@Autowired
	private VcurriculumMapper vcurriculumMapper;
	@Override
	public Integer max(String fieldname) {
		return vcurriculumMapper.max(fieldname);
	}
	@Override
	public Map<String, Integer> statMapByFieldname(String fieldname) {
		return Util.listmapToMap(vcurriculumMapper.statMapByFieldname(fieldname));
	}
	@Override
	public void updatePlus(String fieldname, Integer addend, Integer id) {
		vcurriculumMapper.updatePlus(fieldname, addend, id);
	}
}
