package app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sld.webutils.Util;


public class Common {

	// 网站验证码在session中保存的名称
	// VerificationCode中使用了此名称
	public static String sessionVerificationCode = "sessionSecurityName";

	/***
	 * session中保存的用户登录后的信息名称 user 用户角色
	 * 0---管理员Admin;1---项目总监ProjectDirector;2---项目经理ProjectManager;
	 * 3---商务人员BusinessPersonnel;4----运营人员Operator;5---财务人员Financial;
	 * 6---采购人员Purchasers;7---库管人员WarehousePersonnel
	 */
	public static String sessionUser = "tpauser";

	/***
	 * 
	 */
	public static Integer periodOfValidity = 300000;// 单位：毫秒

	// 默认用户头像
	public static String userheadimgurl = "bssets/i/male.png";
	public static String userheadimgurl_male = "bssets/i/male.png";
	public static String userheadimgurl_female = "bssets/i/female.png";

	public static String uploadFileSaveFolder = "uploadfile";
	public static String uploadFileSaveFolderSrc = "uploadfilesrc";
	/***
	 * 允许用户上传的单个附件大小，当前是20M
	 */
	public static long maxFileSize = 20000000;

	// 网站标题中显示的网站名称（应用名称）
	public static String AppPath = getServletPath();// "/iwms/";
	// 公司名称
	public static String companyName = "四川师范大学";
	// 公司名称
	public static String companyShortName = "川师";
	// 网站标题中显示的网站名称（应用名称）
	public static String AppName = "实验报告管理系统";
	// 网站标题中显示的网站名称（应用名称）
	public static String AppSubName = "Sichuan Normal University";
	// 公司电话
	public static String companyTelephone = "028-8476";

	public static String[] tagPostfix = { "primary", "success", "info", "warning", "danger", "link" };

	public static ArrayList<String> allowfiles = new ArrayList<String>(
			Arrays.asList("zip", "rar", "c", "java", "txt", "doc", "cpp"));

	/**
	 * 页面提示信息出现在注册、登录界面中
	 */
	public Common() {
	}

	/***
	 * 为页面查找内容添加标注
	 * 
	 * @param keyword
	 * @return
	 */
	public static String getKeywordSpan(String keyword) {
		return "<span class='bg-kw'>" + keyword + "</span>";
	}

	/***
	 * 记录value到session/从session恢复值
	 * 
	 * @param key
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer recordToSession(HttpSession session, String key, String value, Integer defaultValue) {
		Boolean haveError = false;
		Integer newvalue = defaultValue;
		try {
			newvalue = Integer.parseInt(value);
		} catch (Exception e) {
			haveError = true;
		}
		if (value == null || haveError) {
			// 页面传入为0，表示这次没有改变
			if (session.getAttribute(key) != null) {
				newvalue = Integer.parseInt((String) session.getAttribute(key));
			} else {
				// 页面没有传入参数，并且没有session值
				newvalue = defaultValue;
			}
		} else {
			// 页面传入不为0，有参数
			session.setAttribute(key, newvalue + "");
		}
		return newvalue;
	}

	public static String recordToSession(HttpSession session, String key, String value, String defaultValue) {
		if (StringUtils.isBlank(value)) {
			if (session.getAttribute(key) != null) {
				value = (String) session.getAttribute(key);
			} else {
				value = defaultValue;
			}
		} else {
			value = Util.replaceSqlChar(value);
			session.setAttribute(key, value);
		}
		return value;
	}

	/**
	 * 获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return requestAttributes == null ? null : requestAttributes.getRequest();
	}

	public static HttpServletResponse getResponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return requestAttributes == null ? null : requestAttributes.getResponse();
	}

	/***
	 * 返回不超过50个字符的客户端浏览器信息
	 * 
	 * @return
	 */
	public static String getBrowser() {

		String browser = getRequest().getHeader("user-agent");
		// 限定长度不超过50
		browser = Util.restrictLength(browser, 50);
		return browser;
	}

	/***
	 * 返回服务器的操作系统信息
	 * 
	 * @return
	 */
	public static String getOs() {
		String operating_system = System.getProperty("os.name") + System.getProperty("os.version");

		// 限定长度不超过50
		operating_system = Util.restrictLength(operating_system, 50);
		return operating_system;
	}

	/*****
	 * 获取完整url
	 * 
	 * @return
	 */
	public static String getUrl() {
		// 完整url
		HttpServletRequest request = getRequest();
		String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath()
				+ request.getServletPath();
		if (!StringUtils.isBlank(request.getQueryString())) {
			url += "?" + request.getQueryString();
		}
		return url;
	}

	/***
	 * 当前应用在网站的目录名称
	 * 
	 * @return
	 */
	public static String getServletPath() {

		return getRequest().getServletPath();
	}

	/***
	 * 当前请求来源url
	 *
	 * @return
	 */
	public static String getRefererUrl() {
		HttpServletRequest request = getRequest();
		String url = request.getHeader("Referer");
		return url;
	}

	/***
	 * 网站的根目录
	 *
	 * @return
	 */
	public static String getBasePath() {
		// 完整url
		HttpServletRequest request = getRequest();

		String basePath = "";
		if (request.getServerPort() != 80 && request.getServerPort() != 443) {

			basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
		} else {
			basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
		}
		return basePath;
	}

	/***
	 * 获取ip地址
	 * 
	 * IPV6本机地址转化为127.0.0.1
	 * 
	 * @return
	 */
	public static String getIP() {

		HttpServletRequest request = getRequest();
		String ip = "127.0.0.1";
		try {
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if (ip.equals("0:0:0:0:0:0:0:1")) {
				ip = "127.0.0.1";
			}
			if (ip.equals("::1")) {
				ip = "127.0.0.1";
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return ip;
	}

	/***
	 * 上传文件时，保存文件
	 * 
	 * @param fileNamePath 源文件
	 * @param savefolder   目标目录
	 * @param fileName     目标文件名
	 * @return
	 */
	public String uploadfile(String fileNamePath, String savefolder, String fileName) {
		// 写到指定的路径中
		File filefolder = new File(savefolder);
		// 如果指定的路径没有就创建
		if (!filefolder.exists()) {
			filefolder.mkdirs();
		}
		File file = new File(fileNamePath);
		File savefile = new File(savefolder, fileName);
		try {
			FileUtils.copyFile(file, savefile);
		} catch (Exception e) {
			return null;
		}
		return savefile.getAbsolutePath();
	}

}
