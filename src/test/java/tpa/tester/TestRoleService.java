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
import tpa.entity.Role;
import tpa.service.IRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestRoleService {
	@Autowired
	@Qualifier("roleService")
	private IRoleService rolesvc;

	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

		// ---------------增加功能测试-------------------
		Role it = new Role();
		it.setName("mm");
		it.setAlias("mm");
		it.setIs_system(false);
		it.setIs_actived(false);
		it.setRemark("mm");
		it.setCreate_userid(0);
		it.setUpdate_userid(0);
		it.setCreate_gmt(new Date().getTime());
		it.setUpdate_gmt(0l);
		try {
			rolesvc.save(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证
		Role item = rolesvc.getById(it.getId());
		System.out.println("it="+it.toString());
		System.out.println("item="+item.toString());
		assertTrue(item.getName().equals(it.getName()));

		// ---------------修改功能测试-------------------
		UpdateWrapper<Role> ituw = new UpdateWrapper<>();
		ituw.set(Role.NAME , "hello!");
		ituw.eq(Role.ID , it.getId());		
		boolean itupdate = rolesvc.update(ituw);	
		// 验证
		item = rolesvc.getById(it.getId());
		assertTrue(item.getName().equals("hello!"));

		// ---------------删除功能测试-------------------
		rolesvc.removeById(it.getId());
		item = rolesvc.getById(it.getId());
		assertEquals(item, null);

	}
}
