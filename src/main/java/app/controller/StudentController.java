package app.controller;

import app.entity.*;
import app.entity.UserSession;
import app.service.AppService;
import app.service.BrowseService;
import app.service.DbService;
import app.utils.Common;
import app.wechat.pojo.WeChat;
import net.coobird.thumbnailator.Thumbnails;
import sld.webutils.Util;
import tpa.entity.*;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/student")
public class StudentController {

	private static final Log logger = LogFactory.getLog(StudentController.class);

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

	public StudentController() {
		System.out.println("StudentController--控制器加载");
	}

	/***
	 * 在Controller每个方法执行之前都执行
	 * 
	 * @param model
	 */
	@ModelAttribute
	public void modelattribute(Model model, HttpSession session) {

		model.addAttribute("basePath", Common.getBasePath());//http://ip:port/工程名 -julius
		model.addAttribute("appPath", Common.AppPath);
		model.addAttribute("appName", Common.AppName);
		model.addAttribute("appSubName", Common.AppSubName);
		model.addAttribute("companyName", Common.companyName);
		model.addAttribute("companyShortName", Common.companyShortName);
		model.addAttribute("user", (UserSession) session.getAttribute(Common.sessionUser));

		// Config c1 =
		// //svcb.getConfigById(1);
		// svc.getConfigByKey("close_experiment");
		// System.out.println("StudentController.modelattribute()c1=" + c1.toString());

		model.addAttribute("show_experiment", svc.getConfigByKey("show_experiment"));
		model.addAttribute("show_evaluation", svc.getConfigByKey("show_evaluation"));
	}

	/***
	 * ajax上传
	 * @param request
	 * @param session
	 * @param uploadfile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/ajaxupload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxupload(HttpServletRequest request, HttpSession session,
			@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {
		System.out.println("StudentController.ajaxupload()进入了文件上传部分");
		String foldername = "headimg";
		String path = request.getServletContext()
				.getRealPath(Common.uploadFileSaveFolder + File.separatorChar + foldername);//uploadfile\\headimg -julius
		String pathsrc = request.getServletContext()
				.getRealPath(Common.uploadFileSaveFolderSrc + File.separatorChar + foldername);//uploadfilesrc\\headimg
		String saveUrl = Common.uploadFileSaveFolder + "\\" + foldername + "\\";//uploadfile\\headimg\\
		Integer currentUser_id = 0;// user.getId();
		Util.checkDirectoryExists(path);// 检查文件夹是否存在
		Util.checkDirectoryExists(pathsrc);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!uploadfile.isEmpty()) {
			String suffix = Util.getSuffix(uploadfile.getOriginalFilename());
			String filename = currentUser_id + "_" + new Date().getTime() + suffix;//filename=0_当前时间+文件后缀
			File srcFile = new File(pathsrc + "\\" + filename);
			File targetFile = new File(path + "\\" + filename);
			uploadfile.transferTo(srcFile);
			// 建议上传图片尺寸：200px,280px
			// app.utils.ImageUtils
			//ImageUtils imageResizer = new ImageUtils();
			//imageResizer.resizeOnWH(srcFile, targetFile, 200, 280);
			Thumbnails.of(srcFile).size(200, 280).toFile(targetFile);
			System.out.println(this.getClass().getName() + ".ajaxupload()文件已经保存到" + saveUrl + filename);
			map.put("fileName", saveUrl + filename);
		} else {
			map.put("msg", "上传文件失败！");
			map.put("fileName", "bssets/i/male.jpg");
		}
		return map;
	}

	@RequestMapping(value = { "", "/", "/index.html" })
	public ModelAndView index(ModelAndView mv, HttpSession session) {
		// System.out.println("Student/index had been called!");
		mv.addObject("pagetitle", "学习首页");
		mv.addObject("subtitle", "Study Home Page");
		mv.addObject("navitem", "Home");// 菜单项
		mv.addObject("visitToday", svc.getDay());// 访问量
		mv.addObject("visittotal",svc.getLastTotal());//访问总量


		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);
		// mv.addObject("student", "student");
		System.out.println("StudentController.index()student=" + student.toString());

		// 找到该同学参加的（学习组）
		List<Vlink_student2group> student2groups = svc.listVlink_student2groupByStudentId(student.getId());
		mv.addObject("student2groups", student2groups);
		System.out.println("StudentController.index()student2groups.size=" + student2groups.size());

		// 找到该同学所在学习组，已经开设的（学习课程）
		List<Vcurriculum> curriculums = svc.listVcurriculumByStudentId(student.getId());
		mv.addObject("curriculums", curriculums);
		System.out.println("StudentController.index()curriculums.size=" + curriculums.size());

		// 找到该同学所在学习组，可以开设的实验课程
		List<Vlink_experiment_curriculum_min> experimentCurriculums = svc.listLinkExperimentCurriculumByStudentId(student.getId());

		mv.addObject("experimentCurriculums", experimentCurriculums);
		System.out.println("StudentController.index()experimentCurriculums.size=" + experimentCurriculums.size());

		// SELECT * FROM tpa.experiment_record where student_id=826
		// 读取当前学生已经完成的实验id列表
		Map<String, String> exprecordmap = new HashMap<String, String>();
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Experiment_record.STUDENT_ID, student.getId());
			List<Experiment_record> experimentRecords = svcb.listExperiment_recordByMap(params);
			for (int i = 0; i < experimentRecords.size(); i++) {
				exprecordmap.put(experimentRecords.get(i).getSno() + experimentRecords.get(i).getExperiment_id(),
						experimentRecords.get(i).getScorescript());
			}
		}
		mv.addObject("exprecordmap", exprecordmap);

		// 通过学生id，获取过程考核列表
		// SELECT * FROM tpa.vevaluation
		// where is_actived=1 and student_group_id in
		// (SELECT student_group_id FROM vlink_student2group where student_is_actived=1
		// and student_group_is_actived=1 and student_id=826 )
		List<Vevaluation> evaluations = svc.listVevaluationByStudentId(student.getId());
		mv.addObject("evaluations", evaluations);
		// 现在时间
		mv.addObject("now", new Date().getTime());
		mv.setViewName("student/index");
		return mv;
	}

	/***
	 * 显示当前考核的所有内容
	 * @param evid
	 * @param request
	 * @param session
	 * @return
	 */
	// TODO evshow
	@RequestMapping(value = { "/evshow/{evid}", "/evshow/{evid}/", "/evshow/{evid}/index.html" }, method = RequestMethod.GET)
	public ModelAndView evshowGET(ModelAndView mv, @PathVariable Integer evid, HttpServletRequest request,
			HttpSession session) {

		String pagename = "student/evshow";
		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);
		System.out.println(this.getClass().getName() + ",evid=" + evid + ",student:" + student.getLogin_name());
		Evaluation_ip_record eirlast = null;

		eirlast = svc.getlastEvaluation_ip_record(evid, student.getLogin_name());


		System.out.println("StudentController.evshowGET()eirlast=" + eirlast.toString());

		Vevaluation evaluation = svcb.getVevaluationById(evid);
		List<Evaluation_result> evrlist = null;
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Evaluation_result.EVALUATION_ID, evid);
			params.put(Evaluation_result.STUDENT_ID, student.getId());
			evrlist = svcb.listEvaluation_resultByMap(params);
		}

		Long now = new Date().getTime();

		if (evrlist.size() != 0) {
			// 已经交卷
			mv.addObject("message", "交卷后，请离开考场！！！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");

		} else if (!eirlast.getIp().equals(Common.getIP())) {

			// 记录企图登录的ip
			Evaluation_ip_record eir = new Evaluation_ip_record(student.getId(), student.getLogin_name(), evid,
					Common.getIP(), 1);
			svcb.saveEvaluation_ip_record(eir);

			// 如果这次登录的ip和上次登录不一样
			mv.addObject("message", "当前考试，你只能在首次登录的机器上完成考试！！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");

		} else if (evaluation == null) {

			mv.addObject("message", "外星人警告你：不要东点西点！！！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");
		} else if (!evaluation.getIs_actived()) {

			mv.addObject("message", "你选中的考核已经关闭！！！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");

		} else if (now < evaluation.getBegin_gmt() || now > evaluation.getEnd_gmt()) {

			mv.addObject("message", "现在不在考试的时间范围内！！！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");

		} else {
			mv.addObject("pagetitle", evaluation.getSubject_name());
			mv.addObject("evaluation", evaluation);
			mv.addObject("begin", evaluation.getBegin_gmt_date());
			mv.addObject("end", evaluation.getEnd_gmt_date());

			// 记录当前ip
			Evaluation_ip_record eir = new Evaluation_ip_record(student.getId(), student.getLogin_name(), evid,
					Common.getIP(), 0);
			svcb.saveEvaluation_ip_record(eir);

			Student_group usergroup = svcb.getStudent_groupById(evaluation.getStudent_group_id());
			List<MajorQuestions> mqlist = new ArrayList<>();// 当前页面项目列表
			String[] evcontentlist = evaluation.getContent().split(";");
			for (int i = 0; i < evcontentlist.length; i++) {
				// System.out.println("Student.evshowGET()" + evcontentlist[i].trim());
				// ConfigEvAdmin.execute()1,5,2
				// ConfigEvAdmin.execute()3,5,2
				// ConfigEvAdmin.execute()4,5,2
				// ConfigEvAdmin.execute()5,3,10
				// ConfigEvAdmin.execute()6,2,20
				String[] mqinfolist = evcontentlist[i].trim().split(",");

				Evaluation_type evtype = svcb.getEvaluation_typeById(Integer.parseInt(mqinfolist[0]));
				Integer number = Integer.parseInt(mqinfolist[1]);
				Integer score = Integer.parseInt(mqinfolist[2]);

				// 读取当前考生的所有考核点记录
				List<Evaluation_result_record> evprlist = svcb
						.listEvaluation_result_recordByMap(new HashMap<String, Object>() {
							{
								put(Evaluation_result_record.STUDENT_ID, student.getId());
								put(Evaluation_result_record.EVALUATION_ID, evid);
								put(Evaluation_result_record.TYPE, evtype.getId());
							}
						});

				// System.out.println("Student.evshowGET()evprlist.size()=" + evprlist.size());
				// 返回考核点类型编号和名称的映射图
				HashMap<String, String> evtMap = new HashMap<>();
				{
					List<Evaluation_type> evtypes = svcb.listEvaluation_typeOrderByAsc(Evaluation_type.ID);
					for (int j = 0; j < evtypes.size(); j++) {
						evtMap.put(evtypes.get(j).getId() + "", evtypes.get(j).getName());
					}
				}

				List<Pevpoint> pevplist = new ArrayList<Pevpoint>();
				for (int j = 0; j < evprlist.size(); j++) {
					// 查找当前考题，已经上传的文件

					Evaluation_result_record evpr = evprlist.get(j);

					// TODO: 查找文件的目录
					String uploadfolder = "WEB-INF\\evfiles\\ug" + evpr.getStudent_group_id() + "e"
							+ evpr.getEvaluation_id() + "\\src\\s" + evpr.getSno();

					String savefolder = request.getServletContext().getRealPath(uploadfolder);

					List<FileNameHash> filelist = new ArrayList<FileNameHash>();
					File file = new File(savefolder);
					int filesize = 0;
					if (file != null && file.isDirectory()) {
						File[] dirFile = file.listFiles();
						for (File f : dirFile) {
							FileNameHash fhash = new FileNameHash();
							fhash.setFilename(f.getName());
							fhash.setFilehash(DigestUtils.md5Hex(f.getName()));
							fhash.setFilesize(f.length());
							filelist.add(fhash);
							filesize += (int) f.length();
						}
					}
					pevplist.add(new Pevpoint(evprlist.get(j), evtMap, filelist));
				}
				mqlist.add(new MajorQuestions(evtype, number, score, pevplist));
			}
			mv.setViewName(pagename);
			mv.addObject("mqlist", mqlist);
		}

		return mv;
	}

	/****
	 * 弹窗显示一道小题
	 * @param evprid
	 * @param operate
	 * @param request
	 * @param session
	 * @return
	 */
	// TODO evprshow
	@RequestMapping(value = { "/evprshow/{evprid}", "/evprshow/{evprid}/", "/evprshow/{evprid}/index.html" }, method = RequestMethod.GET)
	public ModelAndView evprshowGET(ModelAndView mv, @PathVariable Integer evprid, HttpServletRequest request,
			@RequestParam(value = "operate", required = false) String operate, HttpSession session) {

		System.out.println("StudentController.evprshowGET()" + ",evprid=" + evprid + ",operate=" + operate);
		// 用于制作彩色的标签
		mv.addObject("tagpostfixlist", Common.tagPostfix);

		String pagename = "student/evprshow";
		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);

		mv.addObject("pagetitle", "考核页面");
		Evaluation_result_record evpr = svcb.getEvaluation_result_recordById(evprid);
		mv.addObject("evpr", evpr);

		System.out.println("StudentController.evprshowGET(),evprid=" + evprid + ",type=" + evpr.getType());

		if (!evpr.getStudent_id().equals(student.getId())) {
			// 非考生本人操作
			mv.addObject("message", "信息错误，请从考核页面点击进入！！！");
			mv.addObject("jumpto", Common.getBasePath() + "student/evshow/" + evpr.getEvaluation_id());
			mv.setViewName("jumperclick");
		} else {

			// TODO: 查找文件的目录
			String uploadfolder = "WEB-INF\\evfiles\\ug" + evpr.getStudent_group_id() + "e" + evpr.getEvaluation_id()
					+ "\\src\\s" + evpr.getSno();
			String savefolder = request.getServletContext().getRealPath(uploadfolder);
			// 删除指定文件
			if (operate != null && operate.equals("deletefile")) {

				String fileid = request.getParameter("fileid");

				// 读取文件列表
				File file = new File(savefolder);
				if (file != null && file.isDirectory()) {
					File[] dirFile = file.listFiles();
					for (File f : dirFile) {
						if (fileid.equals(DigestUtils.md5Hex(f.getName()))) {
							f.delete();
						}
					}
				}
				// 文件都是唯一的答案，删除后恢复该题目为未作答状态
				svc.updateEvaluation_result_recordIs_submitedById(false, evpr.getId());
				mv.addObject("message", "指定文件已经删除，该题目恢复为未提交状态！");
				mv.addObject("jumpto", Common.getBasePath() + "student/");
				mv.setViewName("closer");
			} else {

				// 返回考核点类型编号和名称的映射图
				Map<Integer, String> evtMap = svcb.mapEvaluation_type();
				// 共用部分
				Integer filesize = 0;
				List<FileNameHash> filelist = new ArrayList<FileNameHash>();

				if (evpr.getType().equals(5) || evpr.getType().equals(6)) {
					Pevpoint item = new Pevpoint(evpr, evtMap);
					mv.addObject("item", item);
					if (item.getAnswers().get(0).getCorrect()) {
						// 允许上传文件
						System.out.println("Student.evprshowGET()允许上传文件");
						// 读取文件列表
						File file = new File(savefolder);
						filesize = 0;
						if (file != null && file.isDirectory()) {
							File[] dirFile = file.listFiles();

							System.out.println("StudentController.evprshowGET()dirFile.length=" + dirFile.length);

							for (File f : dirFile) {

								FileNameHash fhash = new FileNameHash();
								fhash.setFilename(f.getName());
								fhash.setFilehash(DigestUtils.md5Hex(f.getName()));
								fhash.setFilesize(f.length());
								filelist.add(fhash);
								filesize += (int) f.length();
							}
						}
						mv.addObject("filelist", filelist);
						System.out.println("StudentController.evprshowGET()filelist.size=" + filelist.size());
					}

				} else if (evpr.getType().equals(7)) {
					PevProgramPoint item = new PevProgramPoint(evpr, evtMap);
					mv.addObject("item", item);
					File file = new File(savefolder);
					filesize = 0;
					if (file != null && file.isDirectory()) {
						File[] dirFile = file.listFiles();

						System.out.println("StudentController.evprshowGET()dirFile.length=" + dirFile.length);

						for (File f : dirFile) {

							FileNameHash fhash = new FileNameHash();
							fhash.setFilename(f.getName());
							fhash.setFilehash(DigestUtils.md5Hex(f.getName()));
							fhash.setFilesize(f.length());
							filelist.add(fhash);
							filesize += (int) f.length();
						}
					}
					mv.addObject("filelist", filelist);
					System.out.println("StudentController.evprshowGET()filelist.size=" + filelist.size());

				} else {
					// 不是5,6,7
					Pevpoint item = new Pevpoint(evpr, evtMap);
					mv.addObject("item", item);
				}

				mv.setViewName(pagename);

				mv.addObject("pagename", pagename);
				mv.setViewName("student/evprshow");// C++

			}

		}

		return mv;

	}

	// TODO 保存学生提交的一道试题
	@RequestMapping(value = { "/evprshow/{evprid}", "/evprshow/{evprid}/",
			"/evprshow/{evprid}/index.html" }, method = RequestMethod.POST)
	public ModelAndView evprshowPOST(ModelAndView mv, @PathVariable Integer evprid,
			@RequestParam(value = "operate", required = false) String operate,
			@RequestParam(value = "answer", required = false) String answer, HttpServletRequest request,
			HttpSession session) {

		System.out.println(this.getClass().getName() + "evprshowPOST==,evprid=" + evprid);
		String pagename = "student/evprshow";
		mv.addObject("pagetitle", "考核页面");

		mv.addObject("pagename", pagename);

		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);
		Evaluation_result_record evpr = svcb.getEvaluation_result_recordById(evprid);

		// 对象之间的比较equals
		if (!student.getId().equals(evpr.getStudent_id())) {

			System.out.println("StudentController.evprshowPOST()student.getId=" + student.getId());
			System.out.println("StudentController.evprshowPOST()evpr.getStudent_id=" + evpr.getStudent_id());
			System.out.println("StudentController.evprshowPOST()学生登录信息不符合！");

			mv.addObject("message", "学生登录信息不符合！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperatonce");
		} else {

			// 返回考核点类型编号和名称的映射图
			Map<Integer, String> evtMap = svcb.mapEvaluation_type();
			Pevpoint item = new Pevpoint(evpr, evtMap);

			// TODO: 查找文件的目录
			// 查找文件
			String uploadfolder = "WEB-INF\\evfiles\\ug" + evpr.getStudent_group_id() + "e" + evpr.getEvaluation_id()
					+ "\\src\\s" + evpr.getSno();
			String savefolder = request.getServletContext().getRealPath(uploadfolder);
			System.out.println("Student.evprshowPOST()savefolder=" + savefolder);

			// 共用部分
			Integer filesize = 0;
			List<FileNameHash> filelist = new ArrayList<FileNameHash>();

			if (StringUtils.isBlank(answer)) {
				answer = "";
			} else {
				answer = answer.trim();// 去除多余的空格
			}
			System.out.println("StudentController.evprshowPOST()evpr.getType()=" + evpr.getType());

			switch (evpr.getType()) {
			case 1: {
				// 编写xml文件，包含本页面所有信息
				item.setReply(answer);// 记录用户的答案
				Integer score = 0;
				// Boolean replyIsRight = false;
				for (int i = 0; i < item.getAnswers().size(); i++) {
					if (answer.equals(item.getAnswers().get(i).getAnswer())) {
						// 多个答案中，只要有一个符合，即可
						// replyIsRight = true;
						score = evpr.getScore_wish();
					}
				}
				// 更新内容
				evpr.setSubmit_record(item.toXMLString());
				evpr.setIs_submited(true);
				evpr.setScore(score);
			}
				break;

			case 3: {
				Integer score = evpr.getScore_wish();
				String selected = "  ";
				for (int i = 0; i < item.getAnswers().size(); i++) {
					String tagname = "tevpa" + evpr.getType() + (i + 1);
					String checked = request.getParameter(tagname);
					// System.out.println("StudentController.evprshowPOST()tagname=" + tagname +
					// ";checked=" + checked);
					Boolean reply = false;
					if (!StringUtils.isBlank(checked)) {
						// System.out.println("StudentController.evprshowPOST()第" + (i + 1) +
						// "个答案，选中啦");
						reply = true;
						selected += item.getAnswers().get(i).getAnswer() + " , ";
					}
					item.getAnswers().get(i).setReply(reply);

					if (!item.getAnswers().get(i).getCorrect() && !reply
							|| item.getAnswers().get(i).getCorrect() && reply) {

					} else {
						// System.out.println("StudentController.evprshowPOST()有错误，第" + (i + 1) + "答案");
						score = 0;// 有错误，就是0分

					}
				}

				item.setReply(selected.substring(0, selected.length() - 2));
				// 更新内容
				evpr.setSubmit_record(item.toXMLString());
				evpr.setIs_submited(true);
				evpr.setScore(score);
			}
				break;
			case 4: {
				String selected = "  ";
				String tagname = "tevpa" + evpr.getType() + 1;
				Boolean reply = Boolean.parseBoolean(request.getParameter(tagname));
				System.out.println("StudentController.evprshowPOST()reply=" + reply);
				System.out.println("StudentController.evprshowPOST()Correct=" + item.getAnswers().get(0).getCorrect());

				Integer score = evpr.getScore_wish();
				if (reply != item.getAnswers().get(0).getCorrect()) {
					score = 0;

				}
				if (reply) {
					selected = "正确";
				} else {
					selected = "错误";
				}
				item.getAnswers().get(0).setReply(reply);
				item.setReply(selected);
				// 更新内容
				evpr.setSubmit_record(item.toXMLString());
				evpr.setIs_submited(true);
				evpr.setScore(score);
			}
				break;

			case 5:
			case 6: {

				if (item.getAnswers().get(0).getCorrect()) {
					// 允许上传文件
					if (operate.equals("deletefile")) {
						String fileid = request.getParameter("fileid");
						System.out.println("StudentController.evprshowPOST()准备删除文件！");
						// 删除指定digest的文件
						File file = new File(savefolder);
						if (file.isDirectory()) {
							File[] dirFile = file.listFiles();
							for (File f : dirFile) {
								if (DigestUtils.md5Hex(f.getName()).equals(fileid)) {
									f.delete();
								}
							}
						}
						// 删除文件后，恢复改条记录的未提交状态
						evpr.setIs_submited(false);
						try {
							svcb.updateEvaluation_result_recordById(evpr);

						} catch (Exception e) {

							e.printStackTrace();
						}

						mv.addObject("message", "指定文件已经删除！！！");
						mv.addObject("jumpto", Common.getBasePath() + "student/");
						mv.setViewName("jumperatonce");

					} else {

						MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
						MultipartFile file = mrequest.getFile("file");
						System.out.println("StudentController.evprshowPOST()准备保存文件！");
						System.out.println("StudentController.evprshowPOST()file.getOriginalFilename="
								+ file.getOriginalFilename());
						System.out.println("StudentController.evprshowPOST()file.getName=" + file.getName());
						String uploadfilemsg = "";
						// operate=="submitform"
						// 读取文件列表，统计已有文件的大小
						if (file != null) {
							// 检查上传文件是否存在
							String fileFileName = file.getOriginalFilename();
							// System.out.println("StudentController.evprshowPOST(),file != null");
							File targetfile = new File(savefolder, fileFileName);
							System.out.println(
									"StudentController.evprshowPOST(),targetfile=" + targetfile.getAbsolutePath());

							File dir = new File(savefolder);
							if (!dir.exists()) {
								dir.mkdirs();
							}
							// 统计上传文件的目录中，已经存在的文件大小
							File uploadfile = new File(savefolder);
							filesize = 0;
							if (uploadfile.isDirectory()) {
								File[] dirFile = uploadfile.listFiles();
								for (File f : dirFile) {
									filesize += (int) f.length();
								}
							}

							// 限定了后缀和文件大小
							String suffix = Util.getSuffix(file.getOriginalFilename()).toLowerCase();
							System.out.println("StudentController.evprshowPOST()suffix=" + suffix);

							if (!Common.allowfiles.contains(suffix)) {//
								System.out.println("StudentController.evprshowPOST()类型不在指定范围内");
								uploadfilemsg += "<br/>文件：" + fileFileName + ",类型不在指定范围内！"
										+ Common.allowfiles.toString();

							} else if (filesize + (int) (file.getSize()) > 5000000) {
								System.out.println("StudentController.evprshowPOST()(空间不足)");
								// 5000000=5MB
								uploadfilemsg += "<br/>剩余空间：" + (5000000 - filesize) + "字节，不足以保存文件：" + fileFileName
										+ ",该文件大小" + file.getSize() + "字节！";
							} else {
								System.out.println("Student.evprshowPOST(上传)");
								uploadfilemsg += "<br/>文件：" + fileFileName + ",符合要求，已经上传";
								try {

									file.transferTo(targetfile);
								} catch (IllegalStateException e) {

									e.printStackTrace();
								} catch (IOException e) {

									e.printStackTrace();
								}

							}

							// 不要结束，继续保持文字内容

						}
						evpr.setIs_submited(true);
					}
				}
				if (item.getAnswers().get(1).getCorrect()) {
					// 允许提交文字
					System.out.println("StudentController.evprshowPOST()允许提交文字");
					item.setReply(answer);// 记录用户的答案
					// 更新内容
					evpr.setSubmit_record(item.toXMLString());
					evpr.setIs_submited(true);
				}

			}
				break;
			case 7: {

				// 允许上传文件
				if (operate.equals("deletefile")) {
					String fileid = request.getParameter("fileid");
					System.out.println("StudentController.evprshowPOST()准备删除文件！");
					// 删除指定digest的文件
					File file = new File(savefolder);
					if (file.isDirectory()) {
						File[] dirFile = file.listFiles();
						for (File f : dirFile) {
							if (DigestUtils.md5Hex(f.getName()).equals(fileid)) {
								f.delete();
							}
						}
					}
					// 删除文件后，恢复改条记录的未提交状态
					evpr.setIs_submited(false);
					try {
						svcb.updateEvaluation_result_recordById(evpr);

					} catch (Exception e) {

						e.printStackTrace();
					}

					mv.addObject("message", "指定文件已经删除！！！");
					mv.addObject("jumpto", Common.getBasePath() + "student/");
					mv.setViewName("jumperatonce");

				} else {

					MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
					MultipartFile file = mrequest.getFile("file");
					System.out.println("StudentController.evprshowPOST()准备保存文件！");
					System.out.println(
							"StudentController.evprshowPOST()file.getOriginalFilename=" + file.getOriginalFilename());
					System.out.println("StudentController.evprshowPOST()file.getName=" + file.getName());
					String uploadfilemsg = "";
					// operate=="submitform"
					// 读取文件列表，统计已有文件的大小
					if (file != null) {
						// 检查上传文件是否存在
						String fileFileName = file.getOriginalFilename();
						// System.out.println("StudentController.evprshowPOST(),file != null");
						File targetfile = new File(savefolder, fileFileName);
						System.out
								.println("StudentController.evprshowPOST(),targetfile=" + targetfile.getAbsolutePath());

						File dir = new File(savefolder);
						if (!dir.exists()) {
							dir.mkdirs();
						}
						// 统计上传文件的目录中，已经存在的文件大小
						File uploadfile = new File(savefolder);
						filesize = 0;
						if (uploadfile.isDirectory()) {
							File[] dirFile = uploadfile.listFiles();
							for (File f : dirFile) {
								filesize += (int) f.length();
							}
						}

						// 限定了后缀和文件大小
						String suffix = Util.getSuffix(file.getOriginalFilename()).toLowerCase();
						System.out.println("StudentController.evprshowPOST()suffix=" + suffix);

						if (!Common.allowfiles.contains(suffix)) {//
							System.out.println("StudentController.evprshowPOST()类型不在指定范围内");
							uploadfilemsg += "<br/>文件：" + fileFileName + ",类型不在指定范围内！" + Common.allowfiles.toString();

						} else if (filesize + (int) (file.getSize()) > 5000000) {
							System.out.println("StudentController.evprshowPOST()(空间不足)");
							// 5000000=5MB
							uploadfilemsg += "<br/>剩余空间：" + (5000000 - filesize) + "字节，不足以保存文件：" + fileFileName
									+ ",该文件大小" + file.getSize() + "字节！";
						} else {
							System.out.println("Student.evprshowPOST(上传)");
							uploadfilemsg += "<br/>文件：" + fileFileName + ",符合要求，已经上传";
							try {

								file.transferTo(targetfile);
							} catch (IllegalStateException e) {

								e.printStackTrace();
							} catch (IOException e) {

								e.printStackTrace();
							}

						}

						// 不要结束，继续保持文字内容

					}
					evpr.setIs_submited(true);
				}

			}

				break;

			default:
				break;
			}

			try {

				evpr.setUpdate_gmt(new Date().getTime());
				svcb.updateEvaluation_result_recordById(evpr);

			} catch (Exception e) {

				e.printStackTrace();
			}

			mv.addObject("message", "内容已经更新，此窗口自动关闭！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("closer");
		}
		return mv;
	}

	/*****
	 * 学生交卷
	 * @param evid
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/evsubmit/{evid}", "/evsubmit/{evid}/",
			"/evsubmit/{evid}/index.html" }, method = RequestMethod.GET)
	public ModelAndView evsubmitGET(ModelAndView mv, @PathVariable Integer evid, HttpServletRequest request,
			HttpSession session) throws Exception {

		System.out.println(this.getClass().getName() + ",evid=" + evid);
		String pagename = "student/evshow";
		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);

		List<Evaluation_result> evrlist = svcb.listEvaluation_resultByMap(new HashMap<String, Object>() {
			{
				put(Evaluation_result.EVALUATION_ID, evid);
				put(Evaluation_result.STUDENT_ID, student.getId());
			}
		});
		System.out.println("Student.evsubmitGET()evrlist.size=" + evrlist.size());
		if (evrlist.size() == 0) {
			// 没有交卷
			Integer student_id = student.getId();
			String sno = student.getAlias();
			Integer evaluation_id = evid;
			String comment = "交卷了";
			String evaluation_record = "交卷了";
			Integer score = -99;
			Long submit_gmt = new Date().getTime();
			boolean is_reviewed = false;

			Evaluation_result evrecord = new Evaluation_result(student_id, evaluation_id, sno, comment,
					evaluation_record, score, submit_gmt, is_reviewed);

			svcb.saveEvaluation_result(evrecord);
			mv.addObject("message", "考试结束！");
		} else {

			mv.addObject("message", "已经交卷，不要重复提交！");

		}

		mv.addObject("jumpto", Common.getBasePath() + "student/");
		mv.setViewName("jumper");
		return mv;
	}

	/****
	 * 显示实验信息
	 * @param lecid   实验id
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/expshow/{lecid}", "/expshow/{lecid}/", "/expshow/{expid}/index.html" }, method = RequestMethod.GET)
	public ModelAndView expshowGET(ModelAndView mv, @PathVariable Integer lecid, HttpServletRequest request, HttpSession session) {
		System.out.println(this.getClass().getName() + ",lecid=" + lecid);
		String pagename = "student/expshow";
		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);
		// 找到该同学所在学习组，可以开设的实验课程
		List<Vlink_experiment_curriculum_min> experimentCurriculums = svc.listLinkExperimentCurriculumByStudentId(student.getId());
		List<Integer> lecidlist = new ArrayList<Integer>();
		{
			for (int i = 0; i < experimentCurriculums.size(); i++) {
				lecidlist.add(experimentCurriculums.get(i).getId());
			}
		}
		// 查询考核是否可以同时
		// System.out.println("Student.manageexperimentGET()syscfg=" +
		// syscfg.getCloseExperiment());
		// model.addAttribute("show_experiment", svc.getConfigByKey("show_experiment"));
		// model.addAttribute("show_evaluation", svc.getConfigByKey("show_evaluation"));
		if (svc.getConfigByKey("show_experiment").getValue().equals("false")) {
			// 关闭实验显示
			System.out.println("Student.manageexperimentGET(1)");
			mv.addObject("message", "外星人使用外星科技关闭了原有实验数据！哈哈哈哈哈哈……");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");
		} else if (!lecidlist.contains(lecid)) {
			mv.addObject("message", "这个实验好像不在你的实验范围，请给老师反馈一下！");
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumperclick");
		} else {
			// System.out.println("Student.manageexperimentGET(2)");
			Link_experiment_curriculum lec = svcb.getLink_experiment_curriculumById(lecid);
			Experiment exp = svcb.getExperimentById(lec.getExperiment_id());
			mv.addObject("lec", lec);
			mv.addObject("exp", exp);
			// System.out
			// .println("Student.manageexperimentGET()vlink_user_experiment=" +
			// vlink_user_experiment.toString());
			// 计算剩余天数
			Long now = new Date().getTime();
			Long closetime = lec.getClosing_gmt();
			mv.addObject("now", now);
			mv.addObject("closetime", closetime);
			// 2018年12月3日，修改为学生可以查看自己的已经提交内容
			// System.out.println("Student.manageexperimentGET(4)");
			// 读取学生上传的所有文件
			String uploadfolder = "WEB-INF\\expfiles\\s" + lec.getSubject_id() + "\\e" + lec.getExperiment_id() + "\\u" + student.getLogin_name() + "\\";
			// String savefolder1 =request.getServletContext().getRealPath("/");
			String savefolder = request.getServletContext().getRealPath(uploadfolder);
			// System.out.println("Student.manageexperimentGET()savefolder1="+savefolder1);
			System.out.println("Student.manageexperimentGET()savefolder2=" + savefolder);
			// http://localhost:8080/student/expshow/93?operate=deletefile&fileid=f8cad9a7df6dd24addff8c8975ad6d1d
			String operate = request.getParameter("operate");
			System.out.println("Student.expshowGET()operate=" + operate);
			if (!StringUtils.isBlank(operate) && operate.equals("deletefile")) {
				String fileid = request.getParameter("fileid");
				File file = new File(savefolder);
				if (file.isDirectory()) {
					File[] dirFile = file.listFiles();
					for (File f : dirFile) {
						if (DigestUtils.md5Hex(f.getName()).equals(fileid)) {
							f.delete();
						}
					}
				}
				// System.out.println("Student.expshowGET(已经删除)");
				mv.addObject("message", "指定文件已经删除！");
				mv.addObject("jumpto", Common.getBasePath() + "student/expshow/" + lecid);
				mv.setViewName("jumperclick");
			} else {
				System.out.println("Student.expshowGET(experiment_record)");
				Experiment_record experiment_record = null;
				{
					Map<String, Object> params = new HashMap<String, Object>();
					params.put(Experiment_record.EXPERIMENT_ID, lec.getExperiment_id());
					params.put(Experiment_record.STUDENT_ID + "", student.getId());
					List<Experiment_record> erlist = svcb.listExperiment_recordByMap(params);
					if (erlist.size() == 1) {
						experiment_record = erlist.get(0);
					} else {
						System.err.println("size()=" + erlist.size() + ",请检查！Experiment_record:(experiment_id="
								+ lec.getExperiment_id() + ",sno=" + student.getLogin_name());
					}
				}
				mv.addObject("exprecord", experiment_record);
				// 读取文件列表
				File file = new File(savefolder);
				Integer filesize = 0;
				List<FileNameHash> filelist = new ArrayList<FileNameHash>();
				if (file.isDirectory()) {
					File[] dirFile = file.listFiles();
					for (File f : dirFile) {
						FileNameHash it = new FileNameHash();
						it.setFilename(f.getName());
						it.setFilehash(DigestUtils.md5Hex(f.getName()));
						it.setFilesize(f.length());
						filelist.add(it);
						filesize += (int) f.length();
					}
				}
				mv.addObject("filesize", filesize);
				mv.addObject("filelist", filelist);
				System.out.println("Student.expshowGET()filesize=" + filesize);
				System.out.println("Student.expshowGET()filelist. size=" + filelist.size());
				pagename = "student/expshow";
				mv.setViewName(pagename);
			}
		}
		return mv;
	}

	/***
	 * 学生的实验报告提交到此，保存
	 * @param uploadfile
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/expresult", "/expresult/", "/expresult/index.html" }, method = RequestMethod.POST)
	public ModelAndView expresultPOST(ModelAndView mv, @RequestParam("uploadfile") MultipartFile[] uploadfile,
			HttpServletRequest request, HttpSession session) throws Exception {
		String pagename = "student/";
		// UserShiro student = (UserShiro) session.getAttribute(Common.sessionUser);
		// session.setAttribute(Common.sessionUser, new UserShiro(list.get(0)));
		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);
		// 查询考核是否可以同时
		// System.out.println("Student.manageexperimentGET()syscfg=" +
		// syscfg.getCloseExperiment());
		if (svc.getConfigByKey("show_experiment").getValue().equals("false")) {
			// 关闭实验显示
			mv.addObject("message", "实验报告提交系统已经关闭，亲，你又来迟了！");
			mv.addObject("jumpto", Common.getBasePath() + pagename);
			mv.setViewName("jumperclick");
		} else {
			String uploadfilemsg = "文件上传信息：";
			Integer lecid = Integer.parseInt(request.getParameter("lecid"));
			Link_experiment_curriculum lec = svcb.getLink_experiment_curriculumById(lecid);
			Experiment exp = svcb.getExperimentById(lec.getExperiment_id());
			mv.addObject("exp", exp);
			// 计算剩余天数
			Long now = new Date().getTime();
			Long closetime = lec.getClosing_gmt();
			if (closetime < now) {
				System.out.println("Student.manageexperimentGET(3)");
				pagename = "student/";
				mv.addObject("message", "报告提交时间已过，外星人已经回收实验报告，回半人马星系了！");
				mv.addObject("jumpto", pagename);
				mv.setViewName("jumperclick");
			} else {
				// 读取学生上传的所有文件
				String uploadfolder = "WEB-INF\\expfiles\\s" + lec.getSubject_id() + "\\e" + exp.getId() + "\\u"
						+ student.getLogin_name() + "\\";
				// String savefolder1 =request.getServletContext().getRealPath("/");
				String savefolder = request.getServletContext().getRealPath(uploadfolder);
				// System.out.println("Student.manageexperimentGET()savefolder1="+savefolder1);
				// System.out.println("Student.manageexperimentGET()savefolder2=" + savefolder);

				// 读取文件列表，统计已有文件的大小
				File dir = new File(savefolder);
				if (!dir.exists()) {
					dir.mkdirs();//生成所有目录
				}
				File file = new File(savefolder);
				int filesize = 0;
				if (file.isDirectory()) {
					File[] dirFile = file.listFiles();
					for (File f : dirFile) {
						filesize += f.length();
					}
				}
				if (uploadfile != null) {
					System.out.println("Student.expresultPOST()共发现" + uploadfile.length + "个文件");
					// 把得到的文件的集合通过循环的方式读取并放在指定的路径下
					ArrayList<String> allowfiles = new ArrayList<String>();
					allowfiles.add(".zip");
					allowfiles.add(".rar");
					// allowfiles.add("html");
					// allowfiles.add("htm");
					allowfiles.add(".txt");
					allowfiles.add(".c");
					allowfiles.add(".java");
					allowfiles.add(".doc");
					allowfiles.add(".docx");
					allowfiles.add(".mp3");
					// System.out.println("ExpResultUser.execute()uploadfile.size()="
					// + uploadfile.size());
					for (int i = 0; i < uploadfile.length; i++) {
						MultipartFile uf = uploadfile[i];
						String ufname = uf.getOriginalFilename();//文件的名字
						if (!StringUtils.isBlank(ufname)) {
							String suffix = Util.getSuffix(ufname).toLowerCase();
							// System.out.println("Student.expresultPOST()ufname=" + ufname + ",suffix=" +
							// suffix);
							// 限定了后缀和文件大小
							if (!allowfiles.contains(suffix)) {
								uploadfilemsg += "<br/>文件：" + ufname + ",类型不在指定范围内！" + allowfiles.toString();
							} else if (filesize + uf.getSize() > exp.getFilesize()) {
								// 5000000=5MB
								uploadfilemsg += "<br/>剩余空间：" + (exp.getFilesize() - filesize) + "字节，不足以保存文件：" + ufname
										+ ",该文件大小" + uf.getSize() + "字节！";
							} else {
								uploadfilemsg += "<br/>文件：" + ufname + ",符合要求，已经上传";
								uf.transferTo(new File(savefolder, ufname));
								System.out.println("Student.expresultPOST()文件保存在：" + savefolder + "\\" + ufname);
							}
						}
					}
				}
				if ("文件上传信息：".equals(uploadfilemsg)) {
					uploadfilemsg = "";
				} else {
					uploadfilemsg = "<p style='color:red;'>" + uploadfilemsg + "</p>";
				}
				// 保存实验结果到数据库
				Boolean is_new = true;
				Integer experiment_record_id = 0;
				{
					Map<String, Object> params = new HashMap<String, Object>();
					params.put(Experiment_record.EXPERIMENT_ID, lec.getExperiment_id());
					params.put(Experiment_record.STUDENT_ID, student.getId());
					List<Experiment_record> erlist = svcb.listExperiment_recordByMap(params);
					if (erlist.size() == 1) {
						is_new = false;
						experiment_record_id = erlist.get(0).getId();
					}
				}
				String mainflow = request.getParameter("mainflow");
				String result = request.getParameter("result");
				Integer student_id = student.getId();
				String sno = student.getLogin_name();
				Integer experiment_id = lec.getExperiment_id();
				String comment = "";
				String report = "";
				Integer score = -1;
				Long submit = new Date().getTime();
				String scorescript = "待评";
				Integer reviewstate = 2;
				Experiment_record er = new Experiment_record(student_id, sno, experiment_id, comment, mainflow, report,
						result, score, submit, scorescript, reviewstate);
				if (is_new) {
					uploadfilemsg = "信息已经保存" + uploadfilemsg;
					svcb.saveExperiment_record(er);
				} else {
					uploadfilemsg = "信息保存成功，点击<a href=\"" + Common.getBasePath() + "student/\">个人中心</a>，可以直接返回实验列表页面"
							+ uploadfilemsg;
					er.setId(experiment_record_id);
					svcb.updateExperiment_recordById(er);
				}
				mv.addObject("message", uploadfilemsg);
				mv.addObject("jumpto", Common.getBasePath() + "student");
				mv.setViewName("jumperclick");
			}
		}
		return mv;
	}

	/***
	 * 学生的实验报告提交到此，保存
	 * @param uploadfile
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/expresultupload", "/expresultupload/",
			"/expresultupload/index.html" }, method = RequestMethod.POST)
	public ModelAndView expresultuploadPOST(ModelAndView mv, @RequestParam("uploadfile") MultipartFile[] uploadfile,
			HttpServletRequest request, HttpSession session) throws Exception {

		String pagename = "student/";
		// UserShiro student = (UserShiro) session.getAttribute(Common.sessionUser);
		// session.setAttribute(Common.sessionUser, new UserShiro(list.get(0)));

		UserSession student = (UserSession) session.getAttribute(Common.sessionUser);

		// 查询考核是否可以同时
		// System.out.println("Student.manageexperimentGET()syscfg=" +
		// syscfg.getCloseExperiment());
		if (svc.getConfigByKey("show_experiment").getValue().equals("false")) {
			// 关闭实验显示
			mv.addObject("message", "实验报告提交系统已经关闭，亲，你又来迟了！");
			mv.addObject("jumpto", Common.getBasePath() + pagename);
			mv.setViewName("jumperclick");

		} else {
			String uploadfilemsg = "文件上传信息：";

			Integer lecid = Integer.parseInt(request.getParameter("lecid"));
			Link_experiment_curriculum lec = svcb.getLink_experiment_curriculumById(lecid);
			Experiment exp = svcb.getExperimentById(lec.getExperiment_id());

			mv.addObject("exp", exp);

			// 计算剩余天数
			Long now = new Date().getTime();
			Long closetime = lec.getClosing_gmt();
			if (closetime < now) {
				System.out.println("Student.manageexperimentGET(3)");
				pagename = "student/";
				mv.addObject("message", "报告提交时间已过，外星人已经回收实验报告，回半人马星系了！");
				mv.addObject("jumpto", pagename);
				mv.setViewName("jumperclick");
			} else {
				// 读取学生上传的所有文件
				String uploadfolder = "WEB-INF\\expfiles\\s" + lec.getSubject_id() + "\\e" + exp.getId() + "\\u"
						+ student.getLogin_name() + "\\";
				// String savefolder1 =request.getServletContext().getRealPath("/");
				String savefolder = request.getServletContext().getRealPath(uploadfolder);
				// System.out.println("Student.manageexperimentGET()savefolder1="+savefolder1);
				// System.out.println("Student.manageexperimentGET()savefolder2=" + savefolder);

				// 读取文件列表，统计已有文件的大小
				File dir = new File(savefolder);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File file = new File(savefolder);
				int filesize = 0;
				if (file.isDirectory()) {
					File[] dirFile = file.listFiles();
					for (File f : dirFile) {
						filesize += f.length();
					}
				}
				if (uploadfile != null) {
					System.out.println("Student.expresultPOST()共发现" + uploadfile.length + "个文件");
					// 把得到的文件的集合通过循环的方式读取并放在指定的路径下
					ArrayList<String> allowfiles = new ArrayList<String>();
					allowfiles.add("zip");
					allowfiles.add("rar");
					// allowfiles.add("html");
					// allowfiles.add("htm");
					allowfiles.add("txt");
					allowfiles.add("c");
					allowfiles.add("java");
					allowfiles.add("doc");
					allowfiles.add("docx");
					allowfiles.add("mp3");
					// System.out.println("ExpResultUser.execute()uploadfile.size()="
					// + uploadfile.size());

					for (int i = 0; i < uploadfile.length; i++) {

						MultipartFile uf = uploadfile[i];
						String ufname = uf.getOriginalFilename();
						if (!StringUtils.isBlank(ufname)) {
							String suffix = Util.getSuffix(ufname).toLowerCase();
							// System.out.println("Student.expresultPOST()ufname=" + ufname + ",suffix=" +
							// suffix);
							// 限定了后缀和文件大小
							if (!allowfiles.contains(suffix)) {//
								uploadfilemsg += "<br/>文件：" + ufname + ",类型不在指定范围内！" + allowfiles.toString();
							} else if (filesize + uf.getSize() > exp.getFilesize()) {
								// 5000000=5MB
								uploadfilemsg += "<br/>剩余空间：" + (exp.getFilesize() - filesize) + "字节，不足以保存文件：" + ufname
										+ ",该文件大小" + uf.getSize() + "字节！";
							} else {
								uploadfilemsg += "<br/>文件：" + ufname + ",符合要求，已经上传";
								uf.transferTo(new File(savefolder, ufname));
								System.out.println("Student.expresultPOST()文件保存在：" + savefolder + "\\" + ufname);
							}
						}
					}
				}
				if ("文件上传信息：".equals(uploadfilemsg)) {
					uploadfilemsg = "";
				} else {
					uploadfilemsg = "<p style='color:red;'>" + uploadfilemsg + "</p>";
				}
				// 保存实验结果到数据库
				Boolean is_new = true;
				Integer experiment_record_id = 0;
				{
					Map<String, Object> params = new HashMap<String, Object>();
					params.put(Experiment_record.EXPERIMENT_ID, lec.getExperiment_id());
					params.put(Experiment_record.STUDENT_ID, student.getId());
					List<Experiment_record> erlist = svcb.listExperiment_recordByMap(params);
					if (erlist.size() == 1) {
						is_new = false;
						experiment_record_id = erlist.get(0).getId();
					}
				}

				String mainflow = request.getParameter("mainflow");
				String result = request.getParameter("result");

				Integer student_id = student.getId();
				String sno = student.getLogin_name();
				Integer experiment_id = lec.getExperiment_id();
				String comment = "";
				String report = "";
				Integer score = -99;
				Long submit = new Date().getTime();
				String scorescript = "待评";
				Integer reviewstate = 2;

				Experiment_record er = new Experiment_record(student_id, sno, experiment_id, comment, mainflow, report,
						result, score, submit, scorescript, reviewstate);

				if (is_new) {
					uploadfilemsg = "信息已经保存" + uploadfilemsg;
					svcb.saveExperiment_record(er);
				} else {
					uploadfilemsg = "信息保存成功，点击<a href=\"" + Common.getBasePath() + "student/\">个人中心</a>，可以直接返回实验列表页面"
							+ uploadfilemsg;
					er.setId(experiment_record_id);
					svcb.updateExperiment_recordById(er);
				}

				mv.addObject("message", uploadfilemsg);
				mv.addObject("jumpto", Common.getBasePath() + "student");
				mv.setViewName("jumperclick");
			}
		}
		return mv;
	}

	// TODO: GET-stupassword
	@RequestMapping(value = { "/stupassword", "/stupassword/",
			"/stupassword/index.html" }, method = RequestMethod.GET)
	public ModelAndView stupasswordGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
										@RequestParam(value = "clrkw", required = false) String clrkw,
										@RequestParam(value = "operate", required = false) String operate,
										@RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
		logger.info(this.getClass().getName());
		System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",kw=" + kw + ",clrkw=" + clrkw);
		mv.addObject("navitem", "Personal");// 菜单项显示当前项
		String pagename = "student/stupassword";
		mv.addObject("tagpostfixlist", Common.tagPostfix);
		mv.addObject("operate", "edit");// 触发下拉的脚本和样式
		mv.addObject("pagetitle", "修改密码");
		mv.addObject("subtitle", "个人密码");
		mv.addObject("tabletitle", "管理");
		mv.addObject("formtitle", "个人密码");
		mv.addObject("pagename", pagename);// 当前页面的url
		return mv;

	}

	@RequestMapping(value = { "/stupassword", "/stupassword/",
			"/stupassword/index.html" }, method = RequestMethod.POST)
	public ModelAndView stupasswordPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
			throws Exception {
		System.out.println(this.getClass().getName() + "POST");
		String pagename = "student/stupassword";
		UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
		Integer id = userSession.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// --------编辑修改共有的部分---------
		String login_password2 = request.getParameter("password2");
		String login_password3 = request.getParameter("password3");
		// System.out.println("2=" + login_password2);
		// System.out.println("3=" + login_password3);
		// --------编辑修改共有的部分-结束---------
		// 来源页面
		String referer = request.getParameter("referer");
		mv.addObject("referer", referer);
		// ----------保存前检查逻辑上是否有冲突---------------
		// 如：用户名必须唯一，手机号必须唯一
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put(User.NAME, name);
		// List<User> list = svcb.listUserByMap(params);
		if (!login_password2.equals(login_password3)) {
			mv.addObject("message", "两次输入密码不一致");
			mv.addObject("jumpto", Common.getBasePath() + pagename);
			mv.setViewName("jumperback");
			return mv;
		} else if ("".equals(login_password2)){
			mv.addObject("message", "密码不能为空");
			mv.addObject("jumpto", Common.getBasePath() + pagename);
			mv.setViewName("jumperback");
			return mv;
		}else {
//			svc.updateUserById(login_password2, userSession.getId(), new Date().getTime(), id);
			svc.updateStudentById(login_password2,new Date().getTime(),id);
			// 记录日志
			svcb.saveSyslog(
					new Syslog(userSession.getId(), userSession.getLogin_name(), Common.getIP(), 0, "更新密码", "", false));

			// 更新登录ip和最后一次登录时间
			// svc.updateUserHasModifyPassword(userSession.getId(), "modified");
		}
		// ----------保存前检查逻辑问题结束------------
		mv.addObject("message", "信息已经修改！");
		mv.addObject("jumpto", Common.getBasePath() + "student");
		mv.setViewName("jumper");
		return mv;
	}

}
