package app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import app.entity.UserSession;
import app.utils.Common;
import sld.webutils.Util;

/**
 * 判断用户权限的Spring MVC的拦截器
 * 
 * Shiro
 * 
 */
public class UserAuthorizedInterceptor implements HandlerInterceptor {

	/** 定义不需要拦截的请求 */
	// private static final String[] IGNORE_URI = { "/index.html", "/login",
	// "/register",
	// "/404.html" };
	private static final String LOGIN_PAGE = "/register";

	/**
	 * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
	 * 当preHandle的返回值为false的时候整个请求就结束了。
	 * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("UserAuthorizedInterceptor.preHandle()网站管理用户登录拦截器");
		HttpSession session = request.getSession();
		/** 默认用户没有登录 */
		boolean flag = false;
		/** 获得请求的ServletPath */
		String servletPath = request.getServletPath();
		System.out.println(this.getClass().getName() + "拦截器检测了访问权限=" + servletPath);
		servletPath = Util.getFirstFolderNameByUrl(servletPath);
		System.out.println("AuthorizedInterceptor.preHandle()servletPath=" + servletPath);
		/** 判断请求是否需要拦截 */
		// for (String s : IGNORE_URI) {
		// if (servletPath.equals(s)) {
		// flag = true;
		// break;
		// }
		// }
		// System.out.println("AuthorizedInterceptor.preHandle()flag=" + flag);
		/** 拦截请求 */
		if (!flag) {
			/** 1.获取session中的用户 */
			// 服务器重启后，原有凭证丢失
			UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));

			if (userSession == null) {
				System.out.println("AuthorizedInterceptor.preHandle()没有登录信息");
				// 没有授权，到登录页面
				/** 如果用户没有登录，跳转到登录页面 */
				request.setAttribute("message", "请先登录再访问网站!");
				request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
				return flag;
			} else if (!servletPath.equals(userSession.getAuthorizedType())) {
				String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
				System.out.println("UserAuthorizedInterceptor.preHandle()url=" + url);
				response.sendRedirect(url + "/" + userSession.getAuthorizedType());
			} else {
				flag = true;
			}
		}
		return flag;

	}

	/**
	 * 该方法需要preHandle方法的返回值为true时才会执行。 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {

	}

	/**
	 * 这个方法在preHandle方法返回值为true的时候才会执行。 执行时间是在处理器进行处理之 后，也就是在Controller的方法调用之后执行。
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
			throws Exception {

	}
}
