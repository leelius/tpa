package app.service.tester;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import app.service.AppService;
import app.service.BrowseService;
import app.service.DbService;
import app.wechat.pojo.WeChat;
import tpa.entity.Link_student2group;
import tpa.entity.Student;
import tpa.service.IStudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TesStudentAddToClass {

	@Autowired
	@Qualifier("wechat")
	private WeChat weChat;

	@Autowired
	@Qualifier("AppService")
	private AppService svc;

	@Autowired
	@Qualifier("BrowseService")
	private BrowseService svcb;

	@Autowired
	@Qualifier("DbService")
	private DbService svcDb;

	@Transactional
	@Test
	@Rollback(false)
	public void testExecute() throws Exception {

		for (int i = 827; i <= 849; i++) {
			Link_student2group it = new Link_student2group(i, 17);
			svcb.saveLink_student2group(it);

		}
		System.out.println("TesStudentAddToClass.testExecute()");
	}
}
