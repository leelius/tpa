//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: iservice
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.service;

import tpa.entity.Vcurriculum;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.*;
import org.apache.ibatis.annotations.Param;

public interface IVcurriculumService extends IService<Vcurriculum> {
	/***
	 * 统计最大值
	 * 
	 * @param fieldname 需要统计的字段名
	 * @return
	 */
	Integer max(String fieldname);
	/***
	 * 按指定字段分组统计
	 * 
	 * @param fieldname 需要统计的字段名
	 * @return Map
	 */
	Map<String, Integer>  statMapByFieldname(String fieldname);
	/***
	 * 更新计数器类型的指定字段
	 * 
	 * @param fieldname	指定字段
	 * @param addend	计数器为1
	 * @param id		需要更新的记录id
	 */
	void updatePlus(@Param("fieldname") String fieldname, @Param("addend") Integer addend, @Param("id") Integer id);
}