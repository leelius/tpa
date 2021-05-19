package app.controller;

import app.entity.Menu;
import app.entity.SocketSessionTime;
import app.entity.UserSession;
import app.service.AppService;
import app.service.BrowseService;
import app.utils.Common;
import app.websocket.WechatWebSocketLinkManage;
import app.wechat.pojo.WeChat;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tpa.service.IStudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 最开始在没有登录的时候，都在browse中进行登录验证
 * @author liuhong
 */
@Controller
public class Browse {

	private static final Log logger = LogFactory.getLog(Browse.class);

	private String themeName = "default/";

	@Autowired
	@Qualifier("wechat")
	private WeChat weChat;

	@Autowired
	@Qualifier("AppService")
	private AppService svc;

	@Autowired
	@Qualifier("BrowseService")
	private BrowseService svcb;

	public Browse() {
		System.out.println("Browse--控制器加载");
	}

	/***
	 * 在Controller每个方法执行之前都执行
	 * @param model
	 */
	@ModelAttribute
	public void modelattribute(Model model) {
		model.addAttribute("basePath", Common.getBasePath());
		model.addAttribute("appPath", Common.AppPath);
		model.addAttribute("appName", Common.AppName);
		model.addAttribute("appSubName", Common.AppSubName);
		svc.recordVisit();// 记录当前浏览量
		// 首页菜单显示
		List<Menu> menus = svc.getMenuTree("onlyshow");
		model.addAttribute("menus", menus);

		model.addAttribute("sttotal", svc.getLastTotal());
		model.addAttribute("sttoday", svc.getDay());

	}

	// TODO:首页
	@RequestMapping(value = { "/", "/index.html" }, method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mv, HttpSession session, HttpServletRequest request) throws Exception {
//		System.out.println("Browse.index()当前在线人数：" + getVisitnumber());
//		System.out.println("浏览器基本信息：" + request.getHeader("user-agent"));
//		System.out.println("客户端系统名称：" + System.getProperty("os.name"));
//		System.out.println("客户端系统版本：" + System.getProperty("os.version"));
//		System.out.println("客户端操作系统位数：" + System.getProperty("os.arch"));
//		System.out.println("HTTP协议版本：" + request.getProtocol());
//		System.out.println("请求编码格式：" + request.getCharacterEncoding());
//		System.out.println("Accept：" + request.getHeader("Accept"));
//		System.out.println("Accept-语言：" + request.getHeader("Accept-Language"));
//		System.out.println("Accept-编码：" + request.getHeader("Accept-Encoding"));
//		System.out.println("Connection：" + request.getHeader("Connection"));
//		System.out.println("Cookie：" + request.getHeader("Cookie"));
//		System.out.println("客户端发出请求时的完整URL" + request.getRequestURL());
//		System.out.println("请求行中的资源名部分" + request.getRequestURI());
//		System.out.println("请求行中的参数部分" + request.getRemoteAddr());
//		System.out.println("客户机所使用的网络端口号" + request.getRemotePort());
//		System.out.println("WEB服务器的IP地址" + request.getLocalAddr());
//		System.out.println("WEB服务器的主机名" + request.getLocalName());
//		System.out.println("客户机请求方式" + request.getMethod());
//		System.out.println("请求的文件的路径" + request.getServerName());
//		System.out.println("请求体的数据流" + request.getReader());

		// String browser = Common.getBrowser();
		// mv.addObject("pagetitle", "首页");
		// mv.setViewName(themeName + "index");
		mv.addObject("jumpto", Common.getBasePath() + "teacherlogin");
		mv.setViewName("jumperatonce");
		return mv;
	}

	// TODO:登录
	@RequestMapping(value = "/ckkv", method = RequestMethod.GET)
	public ModelAndView checkkvlist(ModelAndView mv, HttpSession session) {
		System.out.println("Browse.checkkvlist()-----------------------------------------");
		List<SocketSessionTime> kvList = WechatWebSocketLinkManage.getKvList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Browse.loginWechatOpenidGET()，当前共有" + kvList.size() + "条记录");
		System.out.println("Browse.loginWechatOpenidGET()session.getId()=" + session.getId());
		for (int i = 0; i < kvList.size(); i++) {
			System.out.print("第" + (i + 1) + "条记录：jsid=" + kvList.get(i).getJsid());
			if (session.getId().equals(kvList.get(i).getJsid())) {
				System.out.print("第" + (i + 1) + "条记录：ws.id=" + ((Session) kvList.get(i).getWs()).getId() + ",Jsid="
						+ kvList.get(i).getJsid() + ",time=" + kvList.get(i).getTime() + "("
						+ sdf.format(new Date(kvList.get(i).getTime())) + ")" + ",openid=" + kvList.get(i).getOpenid());
				System.out.println("时间差：" + (new Date().getTime() - kvList.get(i).getTime()));
			}
		}
		System.out.println("Browse.checkkvlist()-----------------------------------------");
		return null;
	}

	/***
	 * 教师未登录时即可访问 -julius
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/teacherlogin", method = RequestMethod.GET)
	public ModelAndView teacherloginGET(ModelAndView mv, HttpSession session) {
		System.out.println("Browse/teacherloginGET had been called!");
		mv.addObject("title", "教师登录");
		mv.addObject("pagetitle", "登录");
		mv.addObject("message", "请输入用户和密码");
		mv.addObject("action", "teacherlogin");
		if (Common.getBasePath().contains("localhost")) {
			// System.out.println("Browse.loginGET()canusewechat==" + false);
			mv.addObject("canusewechat", false);
		} else {
			// System.out.println("Browse.loginGET()canusewechat==" + true);
			mv.addObject("canusewechat", true);
		}
		// 服务器重启后，原有凭证丢失
		UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
		if (userSession != null && userSession.getAuthorizedType().equals("teacher")) {
			String msg = "已经登录，如果要重新登录，请先退出！";
			mv.addObject("message", msg);
			mv.addObject("jumpto", Common.getBasePath() + "teacher/");
			mv.setViewName("jumper");
		}  else {
			// 刷新图片使用的随机数
			Random random = new Random();
			Integer rnd = random.nextInt(822);
			mv.addObject("rnd", rnd);
			// 建立websocket链接时，需要附带jsessionid
			mv.addObject("jsessionid", session.getId());
			// 占位用
			mv.addObject("sid", "sid");
			// 读取有效期
			mv.addObject("periodOfValidity", Common.periodOfValidity);// 二维码有效期
			mv.setViewName(themeName + "login");
		}
		return mv;
	}

	/***
	 * 教师登录 --julius
	 * @param username
	 * @param password
	 * @param verificationcode
	 * @param session
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/teacherlogin", method = RequestMethod.POST)
	public ModelAndView teacherloginPOST(ModelAndView mv, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("vcodeinput") String verificationcode,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		verificationcode = verificationcode.trim();
		username = username.trim();
		password = password.trim();
		verificationcode = verificationcode.toUpperCase();// 用户输入的验证码
		if (session.getAttribute(Common.sessionVerificationCode) == null) {
			mv.addObject("message", "请输入验证码");
			mv.addObject("jumpto", Common.getBasePath() + "teacherlogin");
			mv.setViewName("jumper");
		} else {
			//对登录账号和密码做了封装-julius
			String vcodeinsession = ((String) session.getAttribute(Common.sessionVerificationCode)).toUpperCase();// session里面的验证码
			String udg = DigestUtils.md5Hex(username);//用户摘要-julius
			String pdg = DigestUtils.md5Hex(password);//密码摘要-julius
			// 获取当前，假如有的话
			UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
			if (userSession != null) {
				System.out.println("Browse.teacherloginPOST()userSession=" + userSession.toString());
			} else {
				System.out.println("Browse.teacherloginPOST()userSession=null");
			}
			if (userSession != null && StringUtils.isNotBlank(userSession.getAuthorizedType())) {
//				String msg = "该用户已经登录，请勿重新登录！";
				String msg = "用户已经登录，若要重新登录，请先退出！";
				mv.addObject("message", msg);
				mv.addObject("jumpto", Common.getBasePath() + userSession.getAuthorizedType());
//				mv.addObject("jumpto", Common.getBasePath() + "login");
				mv.setViewName("jumper");
			} else if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
					|| StringUtils.isBlank(verificationcode)) {
				String msg = "请输入用户名和密码以及验证码!";
				mv.addObject("message", msg);
				mv.addObject("jumpto", Common.getBasePath() + "teacherlogin");
				mv.setViewName("jumper");
			} else if (!vcodeinsession.equals(verificationcode)) {
				mv.addObject("message", "验证码输入不正确!");
				mv.addObject("jumpto", Common.getBasePath() + "teacherlogin");
				mv.setViewName("jumper");
			} else {
				//根据用户登录摘要获取
//				UserSession tuser = svc.getUserByUsernameDigest(udg);
				//根据用户名和密码获取usersession对象
                Map<String, Object> params = new HashMap<>();
                params.put("login_name",username);
                params.put("login_password",password);
                UserSession tuser = svc.getUserByUsernameAndPassword(params);
                System.out.println("Browse.loginPOST()tuser=" + tuser.toString());
				if (tuser != null) {
					session.setAttribute(Common.sessionUser, tuser);
					// 记录日志
					svcb.saveSyslog(new Syslog(tuser.getId(), username, Common.getIP(), 0, "密码登录成功", "", false));
					// System.out.println("Browse.loginPOST()记录日志，登录ip和登录时间");
					// --------- 记录日志，登录ip和登录时间 ---------
					String operating_system = System.getProperty("os.name") + "," + System.getProperty("os.version");
					String browser = request.getHeader("user-agent");
					// 限定长度不超过50
					operating_system = Util.restrictLength(operating_system, 50);
					browser = Util.restrictLength(browser, 50);
					svc.saveUserLoginIpDateById(tuser.getId(), Common.getIP(), new Date().getTime(), operating_system,
							browser);
					// --------- 记录日志，登录ip和登录时间 ---------
					mv.addObject("message", "登录成功！");
					mv.addObject("jumpto", Common.getBasePath() + tuser.getAuthorizedType());
					mv.setViewName("jumper");
				} else {
					mv.addObject("message", "登录信息有误验证码输入不正确!");
					mv.addObject("jumpto", Common.getBasePath() + "teacherlogin");
					mv.setViewName("jumper");
				}
			}
		}
		return mv;
	}

//	// TODO:注册
//	@RequestMapping(value = "/register", method = RequestMethod.GET)
//	public ModelAndView registerGET() {
//
//		System.out.println("Browse/registerGET had been called!");
//
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("title", "注册");
//		mv.addObject("pagetitle", "注册");
//		mv.addObject("action", "register");
//		mv.addObject("message", "I'm login registerGET!");
//
//		mv.setViewName(themeName + "register");
//		return mv;
//	}

//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public ModelAndView registerPOST(@RequestParam("username") String username,
//			@RequestParam("password") String password, @RequestParam("password2") String password2,
//		 	@RequestParam("vcodeinput") String verificationcode) {
//
//		System.out.println("Browse/registerPOST had been called!-----username=" + username + ",password=" + password
//				+ ",password2=" + password2+ ",vcodeinput=" + verificationcode);
//		//调用service的保存业务，service调用mapper将注册信息保存到对应数据库中的表里
//
//		// 添加用户
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("message", "I'm login registerPOST!");
//		mv.setViewName(themeName + "index");
//
//		return mv;
//	}

	/**
	 * 退出时，删除用户在session中的信息
	 *
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutGet(ModelAndView mv, HttpSession session) throws IOException {
		svc.recordVisit();
		// System.out.println("进入了退出方法");
		session.removeAttribute(Common.sessionUser);
		mv.addObject("message", "用户已经正常退出！");
		mv.addObject("jumpto", Common.getBasePath());
		mv.setViewName("jumper");
		return mv;
	}

	/***
	 * 记录侧边栏的状态到session
	 * @param name
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/recordsidenav", method = RequestMethod.POST)
	public void ajaxPost(@RequestParam("name") String name, HttpSession session) throws Exception {
		System.out.println("Browse.recordsidenav()name:" + name);
		session.setAttribute("sidenav", name);
	}

	// -------------------------------------------------------------------

	/***
	 * 学生未登录时即可访问 -julius
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/studentlogin", method = RequestMethod.GET)
	public ModelAndView studentloginGET(ModelAndView mv, HttpSession session) {
		System.out.println("Browse/studentloginGET() is called");
		mv.addObject("title", "学生登录");
		mv.addObject("pagetitle", "登录");
		mv.addObject("message", "请输入用户和密码");
		mv.addObject("action", "studentlogin");
		if (Common.getBasePath().contains("localhost")) {
			// System.out.println("Browse.loginGET()canusewechat==" + false);
			mv.addObject("canusewechat", false);
		} else {
			// System.out.println("Browse.loginGET()canusewechat==" + true);
			mv.addObject("canusewechat", true);
		}
		UserSession userinshiro = (UserSession) session.getAttribute(Common.sessionUser);
		// authorizedType;// 授权类型：Admin管理员/Teacher教师/Student学生
		if (session.getAttribute(Common.sessionUser) != null && userinshiro.getAuthorizedType().equals("student")) {
			String msg = "已经登录，如果要重新登录，请先退出！";
			// System.out.println(msg);
			// System.out.println("Browse.loginGET");
			mv.addObject("message", msg);
			// 跳转的路径为role表中的角色名字，比如admin，projectdirector等等
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumper");
		} else {
			// 刷新图片使用的随机数
			Random random = new Random();
			Integer rnd = random.nextInt(822);
			mv.addObject("rnd", rnd);
			// 建立websocket链接时，需要附带jsessionid
			mv.addObject("jsessionid", session.getId());
			// 占位用
			mv.addObject("sid", "sid");
			// 读取有效期
			mv.addObject("periodOfValidity", Common.periodOfValidity);// 二维码有效期
			mv.setViewName(themeName + "login");
		}
		return mv;
	}

	/***
	 * 学生登录
	 * 
	 * @param mv
	 * @param username
	 * @param password
	 * @param verificationcode
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/studentlogin", method = RequestMethod.POST)
	public ModelAndView studentloginPOST(ModelAndView mv, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("vcodeinput") String verificationcode,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) {

		System.out.println("Browse.studentloginPOST(),username=" + username + ",password=" + password
				+ ",verificationcode=" + verificationcode);
//		Student studentBySno = svc.svcstudent.getStudentBySno("2017110617");//空指针异常 报错java.lang.NullPointerException
//		System.out.println(studentBySno.toString());
		verificationcode = verificationcode.trim();
		username = username.trim();
		password = password.trim();
		verificationcode = verificationcode.toUpperCase();
		// String vcodeinsession = ((String)
		// session.getAttribute(Common.sessionVerificationCode)).toUpperCase();//
		// session里面的验证码
		String udg = DigestUtils.md5Hex(username);
		String pdg = DigestUtils.md5Hex(password);
//		Object Code = session.getAttribute(Common.sessionVerificationCode);
//		System.out.println(Code);
		UserSession userShiro = (UserSession) session.getAttribute(Common.sessionUser);
//		UserSession tuser = svc.getUserByUsernameDigest(udg);
//		System.out.println("Browse.loginPOST()tuser=" + tuser.toString());
		if (session.getAttribute(Common.sessionUser) != null && userShiro.getAuthorizedType().equals("Student")) {
			String msg = "已经登录，如果要重新登录，请先退出！";
			mv.addObject("message", msg);
			mv.addObject("jumpto", Common.getBasePath() + "student/");
			mv.setViewName("jumper");
		} else if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(verificationcode)) {
			String msg = "请输入用户和密码以及验证码!";
			mv.addObject("message", msg);
			mv.addObject("jumpto", Common.getBasePath() + "studentlogin");
			mv.setViewName("jumper");
		} else if (session.getAttribute(Common.sessionVerificationCode) != null
				&& !((String) session.getAttribute(Common.sessionVerificationCode)).toUpperCase().equals(verificationcode)) {
			//账号密码验证码均对，登录后跳到此处，返回mv，结束方法  --julius 4.7
//			 System.out.println(
//			 "Browse.loginPOST()verificationcode=" + verificationcode +
//					 ",but vcodeinsession=" + vcodeinsession);
			mv.addObject("message", "验证码输入不正确!");
			mv.addObject("jumpto", Common.getBasePath() + "studentlogin");
			mv.setViewName("jumper");
		} else {
			// 构造一个用户名密码令牌
			username = Util.restrictToNumber(username);
			Map<String, Object> params = new HashMap<String, Object>();
//			params.put(Student.LOGIN_NAME_DIGEST, udg);//不用摘要做登录验证
//			params.put(Student.LOGIN_PASSWORD_DIGEST, pdg);
			params.put("login_name",username);
			params.put("login_password",password);
			// params.put(Student.IS_ACTIVED, true);
//			System.out.println("Browse.studentloginPOST()udg=" + udg + ",pdg=" + pdg);
			System.out.println("Browse.studentloginPOST()udg=" + username + ",pdg=" + password);
			//查询对应的学生 -julius
			List<Student> list = svcb.listStudentByMap(params);
			if (list.size() == 1 && list.get(0).getIs_actived()) {
				session.setAttribute(Common.sessionUser, new UserSession(list.get(0)));
				// 记录日志
				svcb.saveSyslog(new Syslog(list.get(0).getId(), username, Common.getIP(), 0, "密码登录成功", "", false));
				System.out.println("Browse.loginPOST()记录日志，登录ip和登录时间");
				// --------- 记录日志，登录ip和登录时间 ---------
				svc.saveUserLoginIpDateById(list.get(0).getId(), Common.getIP(), new Date().getTime(), Common.getOs(),
						Common.getBrowser());
				// --------- 记录日志，登录ip和登录时间 ---------
				mv.addObject("message", "登录成功！");
				mv.addObject("jumpto", Common.getBasePath() + "student");
				mv.setViewName("jumper");
			} else if (list.size() == 1 && !list.get(0).getIs_actived()) {
				// 登录失败
				mv.addObject("message", "登录失败，该账号已经停用！");
				mv.addObject("jumpto", Common.getBasePath() + "studentlogin");
				mv.setViewName("jumperclick");
			} else {
				// 登录失败
				mv.addObject("message", "登录失败！");
				mv.addObject("jumpto", Common.getBasePath() + "studentlogin");
				mv.setViewName("jumper");
			}
		}
		return mv;
	}
	// -------------------------------------------------------------------


	/***
	 * superadmin未登录时即可访问 -julius
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/superadminlogin", method = RequestMethod.GET)
	public ModelAndView superadminloginGET(ModelAndView mv, HttpSession session) {
		System.out.println("Browse/superadminloginGET had been called!");
		mv.addObject("title", "系统管理员登录");
		mv.addObject("pagetitle", "登录");
		mv.addObject("message", "请输入用户和密码");
		mv.addObject("action", "superadminlogin");
		if (Common.getBasePath().contains("localhost")) {
			// System.out.println("Browse.loginGET()canusewechat==" + false);
			mv.addObject("canusewechat", false);
		} else {
			// System.out.println("Browse.loginGET()canusewechat==" + true);
			mv.addObject("canusewechat", true);
		}
		// 服务器重启后，原有凭证丢失
		UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
		if (userSession != null && userSession.getAuthorizedType().equals("superadmin")) {
			String msg = "已经登录，如果要重新登录，请先退出！";
			mv.addObject("message", msg);
			mv.addObject("jumpto", Common.getBasePath() + "superadmin/");
			mv.setViewName("jumper");
		}  else {
			// 刷新图片使用的随机数
			Random random = new Random();
			Integer rnd = random.nextInt(822);
			mv.addObject("rnd", rnd);
			// 建立websocket链接时，需要附带jsessionid
			mv.addObject("jsessionid", session.getId());
			// 占位用
			mv.addObject("sid", "sid");
			// 读取有效期
			mv.addObject("periodOfValidity", Common.periodOfValidity);// 二维码有效期
			mv.setViewName(themeName + "login");
		}
		return mv;
	}

	/***
	 * superadmin登录
	 *
	 * @param mv
	 * @param username
	 * @param password
	 * @param verificationcode
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/superadminlogin", method = RequestMethod.POST)
	public ModelAndView superadminloginPOST(ModelAndView mv, @RequestParam("username") String username,
										 @RequestParam("password") String password, @RequestParam("vcodeinput") String verificationcode,
										 HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		System.out.println("Browse.superadminloginPOST(),username=" + username + ",password=" + password
				+ ",verificationcode=" + verificationcode);
//		Student studentBySno = svc.svcstudent.getStudentBySno("2017110617");//空指针异常 报错java.lang.NullPointerException
//		System.out.println(studentBySno.toString());
		verificationcode = verificationcode.trim();
		username = username.trim();
		password = password.trim();
		verificationcode = verificationcode.toUpperCase();
		UserSession userAdmin = (UserSession) session.getAttribute(Common.sessionUser);
		if (session.getAttribute(Common.sessionUser) != null && userAdmin.getAuthorizedType().equals("superadmin")) {
			String msg = "已经登录，如果要重新登录，请先退出！";
			mv.addObject("message", msg);
			mv.addObject("jumpto", Common.getBasePath() + "superadmin/");
			mv.setViewName("jumper");
		} else if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(verificationcode)) {
			String msg = "请输入用户和密码以及验证码!";
			mv.addObject("message", msg);
			mv.addObject("jumpto", Common.getBasePath() + "superadminlogin");
			mv.setViewName("jumper");
		} else if (session.getAttribute(Common.sessionVerificationCode) != null
				&& !((String) session.getAttribute(Common.sessionVerificationCode)).toUpperCase().equals(verificationcode)) {
			//账号密码验证码均对，登录后跳到此处，返回mv，结束方法  --julius 4.7
//			 System.out.println(
//			 "Browse.loginPOST()verificationcode=" + verificationcode +
//					 ",but vcodeinsession=" + vcodeinsession);
			mv.addObject("message", "验证码输入不正确!");
			mv.addObject("jumpto", Common.getBasePath() + "superadminlogin");
			mv.setViewName("jumper");
		} else {
			// 构造一个用户名密码令牌
			username = Util.restrictToNumber(username);
			Map<String, Object> params = new HashMap<String, Object>();
//			params.put(Student.LOGIN_NAME_DIGEST, udg);//不用摘要做登录验证
//			params.put(Student.LOGIN_PASSWORD_DIGEST, pdg);
			params.put("login_name",username);
			params.put("login_password",password);
			// params.put(Student.IS_ACTIVED, true);
//			System.out.println("Browse.studentloginPOST()udg=" + udg + ",pdg=" + pdg);
//			System.out.println("Browse.superadminloginPOST()udg=" + username + ",pdg=" + password);
			List<User> list = svcb.listUserByMap(params);
			UserSession tuser = svc.getSuperAdminByUsernameAndPassword(params);//新建查询 -julius
			if (list.size() == 1 && list.get(0).getIs_actived()) {
				session.setAttribute(Common.sessionUser, tuser);
				System.out.println("Browse.superadminloginPOST()UserSession=" +tuser);
				// 记录日志
				svcb.saveSyslog(new Syslog(list.get(0).getId(), username, Common.getIP(), 0, "密码登录成功", "", false));
				System.out.println("Browse.superadminloginPOST()记录日志，登录ip和登录时间");
				// --------- 记录日志，登录ip和登录时间 ---------
				svc.saveUserLoginIpDateById(list.get(0).getId(), Common.getIP(), new Date().getTime(), Common.getOs(),
						Common.getBrowser());
				// --------- 记录日志，登录ip和登录时间 ---------
				mv.addObject("message", "登录成功！");
				mv.addObject("jumpto", Common.getBasePath() + "superadmin");
				mv.setViewName("jumper");
			} else if (list.size() == 1 && !list.get(0).getIs_actived()) {
				// 登录失败
				mv.addObject("message", "登录失败，该账号已经停用！");
				mv.addObject("jumpto", Common.getBasePath() + "superadminlogin");
				mv.setViewName("jumperclick");
			}else if (list.size() == 0 ){//没有该用户的情况
				// 登录失败
				mv.addObject("message", "该用户不存在！");
				mv.addObject("jumpto", Common.getBasePath() + "superadminlogin");
				mv.setViewName("jumper");
			} else {
				// 登录失败
				mv.addObject("message", "登录失败！");
				mv.addObject("jumpto", Common.getBasePath() + "superadminlogin");
				mv.setViewName("jumper");
			}
		}
		return mv;
	}
	// -------------------------------------------------------------------
}
