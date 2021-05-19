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
import tpa.entity.Link_role_permit;
import tpa.service.ILink_role_permitService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestLink_role_permitService {
	@Autowired
	@Qualifier("link_role_permitService")
	private ILink_role_permitService link_role_permitsvc;

	@Transactional
	@Test
	@Rollback(true)
	public void testExecute() throws Exception {

		// ---------------增加功能测试-------------------
		Link_role_permit it = new Link_role_permit();
		it.setRole_id(0);
		it.setPermit_id(0);
		it.setCreate_userid(0);
		it.setUpdate_userid(0);
		it.setCreate_gmt(new Date().getTime());
		it.setUpdate_gmt(0l);
		try {
			link_role_permitsvc.save(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证
		Link_role_permit item = link_role_permitsvc.getById(it.getId());
		System.out.println("it="+it.toString());
		System.out.println("item="+item.toString());
		assertTrue(item.getRole_id().equals(it.getRole_id()));

		// ---------------修改功能测试-------------------
		UpdateWrapper<Link_role_permit> ituw = new UpdateWrapper<>();
		ituw.set(Link_role_permit.ROLE_ID , 99);
		ituw.eq(Link_role_permit.ID , it.getId());		
		boolean itupdate = link_role_permitsvc.update(ituw);	
		// 验证
		item = link_role_permitsvc.getById(it.getId());
		assertTrue(item.getRole_id().equals(99));

		// ---------------删除功能测试-------------------
		link_role_permitsvc.removeById(it.getId());
		item = link_role_permitsvc.getById(it.getId());
		assertEquals(item, null);

	}
}
