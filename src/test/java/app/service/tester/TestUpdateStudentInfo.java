package app.service.tester;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import app.entity.Menu;
import app.service.AppService;
import app.service.BrowseService;
import tpa.entity.*;
import tpa.service.IStudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestUpdateStudentInfo {

	@Autowired
	@Qualifier("studentService")
	private IStudentService svcstudent;

	@Transactional
	@Test
	@Rollback(false)
	public void testExecute() throws Exception {
		
		List<Student> list=svcstudent.list();
		for (int i = 0; i < list.size(); i++) {
			
			
			
			
		}
		

		System.out.println("TestBrowseService.testExecute()");
	}

}
