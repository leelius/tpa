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
import tpa.entity.Student;
import tpa.service.IStudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestUpdateStudent {

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

	@Autowired
	@Qualifier("studentService")
	private IStudentService svcstudent;

	@Transactional
	@Test
	@Rollback(false)
	public void testExecute() throws Exception {

		List<Student> list = svcb.listStudentOrderByAsc(Student.ID);
		for (int i = 0; i < list.size(); i++) {
			// list.get(i).setLogin_name(list.get(i).getSno());
			// list.get(i).setLogin_name_digest(DigestUtils.md5Hex(list.get(i).getSno()));

			svcstudent.update(new UpdateWrapper<Student>().eq(Student.ID, list.get(i).getId())
					.set(Student.LOGIN_NAME, list.get(i).getSno())
					.set(Student.LOGIN_NAME_DIGEST, DigestUtils.md5Hex(list.get(i).getSno()))
				);

		}
		System.out.println("TestUpdateStudent.testExecute()");

	}
}
