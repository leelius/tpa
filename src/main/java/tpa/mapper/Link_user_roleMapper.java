//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: Mapper
//-- Date Generated: 2020-10-09 10:48:00
//-----------------------------

package tpa.mapper;

import java.util.List;
import java.util.Map;
import tpa.entity.Link_user_role;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
public interface Link_user_roleMapper  extends BaseMapper<Link_user_role> {
	// 查询最大值
	Integer max(@Param("fieldname") String fieldname);
	// 按指定字段分组统计
	List<Map<String, Object>> statMapByFieldname(@Param("fieldname") String fieldname);
	// 更新计数器类型的指定字段
	void updatePlus(@Param("fieldname") String fieldname, @Param("addend") Integer addend, @Param("id") Integer id);
}
