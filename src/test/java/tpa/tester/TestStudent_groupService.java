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
import tpa.entity.Student_group;
import tpa.service.IStudent_groupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestStudent_groupService {
	@Autowired
	@Qualifier("student_groupService")
	private IStudent_groupService student_groupsvc;

	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

		// ---------------增加功能测试-------------------
		Student_group it = new Student_group();
		it.setIs_actived(false);
		it.setCode("mm");
		it.setName("mm");
		it.setCreate_gmt(new Date().getTime());
		it.setUpdate_gmt(0l);
		try {
			student_groupsvc.save(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证
		Student_group item = student_groupsvc.getById(it.getId());
		System.out.println("it="+it.toString());
		System.out.println("item="+item.toString());
		assertTrue(item.getCode().equals(it.getCode()));

		// ---------------修改功能测试-------------------
		UpdateWrapper<Student_group> ituw = new UpdateWrapper<>();
		ituw.set(Student_group.CODE , "hello!");
		ituw.eq(Student_group.ID , it.getId());		
		boolean itupdate = student_groupsvc.update(ituw);	
		// 验证
		item = student_groupsvc.getById(it.getId());
		assertTrue(item.getCode().equals("hello!"));

		// ---------------删除功能测试-------------------
		student_groupsvc.removeById(it.getId());
		item = student_groupsvc.getById(it.getId());
		assertEquals(item, null);

	}
}