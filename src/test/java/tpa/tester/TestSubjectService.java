//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: service tester
//-- Date Generated: 2020-10-09 10:48:00
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
import tpa.entity.Subject;
import tpa.service.ISubjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestSubjectService {
	@Autowired
	@Qualifier("subjectService")
	private ISubjectService subjectsvc;

	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

		// ---------------增加功能测试-------------------
		Subject it = new Subject();
		it.setIs_actived(false);
		it.setSortid(0);
		it.setIntro("mm");
		it.setName("mm");
		it.setIllustration("mm");
		it.setCreate_gmt(new Date().getTime());
		it.setUpdate_gmt(0l);
		try {
			subjectsvc.save(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证
		Subject item = subjectsvc.getById(it.getId());
		System.out.println("it="+it.toString());
		System.out.println("item="+item.toString());
		assertTrue(item.getIntro().equals(it.getIntro()));

		// ---------------修改功能测试-------------------
		UpdateWrapper<Subject> ituw = new UpdateWrapper<>();
		ituw.set(Subject.INTRO , "hello!");
		ituw.eq(Subject.ID , it.getId());		
		boolean itupdate = subjectsvc.update(ituw);	
		// 验证
		item = subjectsvc.getById(it.getId());
		assertTrue(item.getIntro().equals("hello!"));

		// ---------------删除功能测试-------------------
		subjectsvc.removeById(it.getId());
		item = subjectsvc.getById(it.getId());
		assertEquals(item, null);

	}
}
