//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: service tester
//-- Date Generated: 2020-10-09 10:47:59
//-----------------------------

package tpa.tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import org.junit.Test;
import java.util.Date;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import tpa.entity.Link_experiment_curriculum;
import tpa.service.ILink_experiment_curriculumService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestLink_experiment_curriculumService {
	@Autowired
	@Qualifier("link_experiment_curriculumService")
	private ILink_experiment_curriculumService link_experiment_curriculumsvc;

	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

		// ---------------增加功能测试-------------------
		Link_experiment_curriculum it = new Link_experiment_curriculum();
		it.setExperiment_id(0);
		it.setCurriculum_id(0);
		it.setSubject_id(0);
		it.setOpening_gmt(0l);
		it.setClosing_gmt(0l);
		it.setSortid(0);
		it.setIs_actived(false);
		it.setCreate_gmt(new Date().getTime());
		it.setUpdate_gmt(0l);
		try {
			link_experiment_curriculumsvc.save(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证
		Link_experiment_curriculum item = link_experiment_curriculumsvc.getById(it.getId());
		System.out.println("it="+it.toString());
		System.out.println("item="+item.toString());
		assertTrue(item.getExperiment_id().equals(it.getExperiment_id()));

		// ---------------修改功能测试-------------------
		UpdateWrapper<Link_experiment_curriculum> ituw = new UpdateWrapper<>();
		ituw.set(Link_experiment_curriculum.EXPERIMENT_ID , 99);
		ituw.eq(Link_experiment_curriculum.ID , it.getId());		
		boolean itupdate = link_experiment_curriculumsvc.update(ituw);	
		// 验证
		item = link_experiment_curriculumsvc.getById(it.getId());
		assertTrue(item.getExperiment_id().equals(99));

		// ---------------删除功能测试-------------------
		link_experiment_curriculumsvc.removeById(it.getId());
		item = link_experiment_curriculumsvc.getById(it.getId());
		assertEquals(item, null);

	}
}
