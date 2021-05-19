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
import tpa.entity.Experiment;
import tpa.service.IExperimentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestExperimentService {
	@Autowired
	@Qualifier("experimentService")
	private IExperimentService experimentsvc;

	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

		// ---------------增加功能测试-------------------
		Experiment it = new Experiment();
		it.setSubject_id(0);
		it.setCurriculum_id(0);
		it.setIs_actived(false);
		it.setIntro("mm");
		it.setTitle("mm");
		it.setContent("mm");
		it.setPurposes_requirement("mm");
		it.setFilesize(0);
		it.setCreate_gmt(new Date().getTime());
		it.setUpdate_gmt(0l);
		try {
			experimentsvc.save(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证
		Experiment item = experimentsvc.getById(it.getId());
		System.out.println("it="+it.toString());
		System.out.println("item="+item.toString());
		assertTrue(item.getIntro().equals(it.getIntro()));

		// ---------------修改功能测试-------------------
		UpdateWrapper<Experiment> ituw = new UpdateWrapper<>();
		ituw.set(Experiment.INTRO , "hello!");
		ituw.eq(Experiment.ID , it.getId());		
		boolean itupdate = experimentsvc.update(ituw);	
		// 验证
		item = experimentsvc.getById(it.getId());
		assertTrue(item.getIntro().equals("hello!"));

		// ---------------删除功能测试-------------------
		experimentsvc.removeById(it.getId());
		item = experimentsvc.getById(it.getId());
		assertEquals(item, null);

	}
}
