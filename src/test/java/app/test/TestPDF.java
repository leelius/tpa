//-----------------------------
//-- Generated By Denny(luhox@qq.com)
//-- Remarks: service tester
//-- Date Generated: 2019-07-09 22:59:20
//-----------------------------

package app.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import app.entity.KeyAndValue;
import app.pdf.PdfExperimentReport;
import app.service.AppService;
import app.service.BrowseService;
import tpa.entity.Experiment;
import tpa.entity.Experiment_record;
import tpa.entity.Student;
import tpa.entity.Student_group;
import tpa.entity.Subject;
import tpa.entity.User;
import tpa.entity.Vexperiment_record;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class TestPDF {

	@Autowired
	@Qualifier("AppService")
	private AppService svc;

	@Autowired
	@Qualifier("BrowseService")
	private BrowseService svcb;

	@Transactional
	@Test
	@Rollback(false)
	public void testExecute() throws Exception {

		PdfExperimentReport ins = new PdfExperimentReport();

		// --------------通过读取一个实验记录创建报告--------------
		Map<String, Object> params = new HashMap<String, Object>();
		//params.put(Vexperiment_record.ID, 10717);
		params.put(Vexperiment_record.STUDENT_GROUP_CODE, 20172821);

		List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
		ordlist.add(new KeyAndValue(Vexperiment_record.ID, "desc"));

		List<Vexperiment_record> list = svcb.listVexperiment_recordByMap(params, ordlist);

		for (int i = 0; i < list.size(); i++)
		// int i=10;
		{

			Vexperiment_record ev = list.get(i);

			Student student = svcb.getStudentById(ev.getStudent_id());// 学生id

			User teacher = svcb.getUserById(ev.getUser_id());// 教师id

			Experiment_record er = svcb.getExperiment_recordById(ev.getId());

			Experiment exp = svcb.getExperimentById(ev.getExperiment_id());

			Subject subject = svcb.getSubjectById(ev.getSubject_id());

			System.out.println("PdfExperimentReport.main()Student_group_code=" + ev.getStudent_group_code());

			Student_group ug = svcb.getStudent_groupById(ev.getStudent_group_id());

			System.out.println("PdfExperimentReport.main()" + ug.toString());

			String pdfsavePath = "D:\\temp\\" + ev.getSubject_name() + ev.getSubject_id() + "\\" + ev.getClasscode()
					+ "\\" + ev.getTruename() + "\\";

			String fileName = ev.getExperiment_id() + ev.getExperiment_title() + ".pdf";

			try {
				System.out.println("PdfExperimentReport.main()开始处理:id=" + ev.getId() + ",pdfsavePath=" + pdfsavePath);

				// File file = new File(pdfsavePath, fileName);
				// if (!file.exists()) {
				ins.buildExpReport(student, teacher, er, exp, ug, subject, pdfsavePath, fileName);
				// }else{
				// System.out.println("PdfExperimentReport.main()跳过"+i);
				// }

			} catch (Exception e) {

				System.out.println("PdfExperimentReport.main()ev=" + ev.toString());
				e.printStackTrace();
			}

		}

		// --------------通过读取一个实验记录创建报告--------------

		// ins.buildReport(1, "测试科目", new Date(), "测试班级", "测试实验", 80, "学生姓名",
		// "20090808", "教师姓名", "<p>实验目的</p>",
		// "<p>实验内容</p>", "<p>实验流程</p>", "<p>实验结果</p>", "E:\\test", "test.pdf");

		System.out.println("PdfExperimentReport.enclosing_method()----over");

	}
}
