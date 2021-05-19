package app.controller;

import app.entity.*;
import app.service.AppService;
import app.service.BrowseService;
import app.service.DbService;
import app.utils.Common;
import app.websocket.WechatBindManage;
import app.wechat.pojo.WeChat;
import jxl.Sheet;
import jxl.Workbook;
import net.coobird.thumbnailator.Thumbnails;
import sld.webutils.Util;
import tpa.entity.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
    private static final Log logger = LogFactory.getLog(TeacherController.class);
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

    public TeacherController() {
        System.out.println("TeacherController--控制器加载");
    }

    /***
     * 在Controller每个方法执行之前都执行
     * @param model
     */
    @ModelAttribute
    public void modelattribute(Model model, HttpSession session) {
        // System.out.println("TeacherController.modelattribute(添加共有属性)");
        model.addAttribute("basePath", Common.getBasePath());
        model.addAttribute("appPath", Common.AppPath);
        model.addAttribute("appName", Common.AppName);
        model.addAttribute("appSubName", Common.AppSubName);
        model.addAttribute("companyName", Common.companyName);
        model.addAttribute("companyShortName", Common.companyShortName);
        model.addAttribute("user", (UserSession) (session.getAttribute(Common.sessionUser)));
    }

    /**
     * 访问:http://localhost:8080/tpa
     * 执行此方法跳转到WEB-INF/view/default/login.jsp页面
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping(value = {"", "/", "/index.html"})
    public ModelAndView index(ModelAndView mv, HttpSession session) {

        System.out.println("TeacherController/index had been called!");

        mv.addObject("pagetitle", "管理首页");
        mv.addObject("subtitle", "system management");
        mv.addObject("navitem", "Home");// 菜单项
//		mv.addObject("articleNumber", "");// 文章数
//		mv.addObject("columnNumber", "");// 栏目数
        mv.addObject("visitToday", svc.getDay());// 访问量
//		mv.addObject("visitorCount", "");// 当前在线
        mv.setViewName("teacher/index");//WEB-INF/view/teacher/index.jsp
        return mv;
    }

    /***
     * 上传文件
     *
     * @param request
     * @param session
     * @param uploadfile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/ajaxupload", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> ajaxformupload(HttpServletRequest request, HttpSession session,
                                       @RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

        System.out.println("TeacherController.ajaxformupload()进入了文件上传部分");
        String foldername = "headimg";
        String path = request.getServletContext()
                .getRealPath(Common.uploadFileSaveFolder + File.separatorChar + foldername);
        String pathsrc = request.getServletContext()
                .getRealPath(Common.uploadFileSaveFolderSrc + File.separatorChar + foldername);
        String saveUrl = Common.uploadFileSaveFolder + "\\" + foldername + "\\";
        UserSession user = (UserSession) (session.getAttribute(Common.sessionUser));
        Integer currentUser_id = user.getId();
        Util.checkDirectoryExists(path);// 检查文件夹是否存在
        Util.checkDirectoryExists(pathsrc);
        Map<String, Object> map = new HashMap<String, Object>();

        if (!uploadfile.isEmpty()) {
            String suffix = Util.getSuffix(uploadfile.getOriginalFilename());
            String filename = currentUser_id + "_" + new Date().getTime() + suffix;
            File srcFile = new File(pathsrc + "\\" + filename);
            File targetFile = new File(path + "\\" + filename);

            uploadfile.transferTo(srcFile);
            // 建议上传图片尺寸：200px,280px
            // app.utils.ImageUtils
            // ImageUtils imageResizer = new ImageUtils();
            // imageResizer.resizeOnWH(srcFile, targetFile, 200, 280);
            Thumbnails.of(srcFile).size(200, 280).toFile(targetFile);

            System.out.println(this.getClass().getName() + ".ajaxformupload()文件已经保存到" + saveUrl + filename);
            map.put("fileName", saveUrl + filename);
        } else {
            map.put("msg", "上传头像图片文件失败！");
            map.put("fileName", "bssets/i/male.jpg");
        }

        return map;
    }

    /***
     * 通过AJAX查询数据库是否有相同操作
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/managepermitcheckisexist", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> permitCheckIsExist(HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {

        String code = request.getParameter("code");
        String operate = request.getParameter("operate");
        String id = request.getParameter("id");
        // System.out.println("TeacherController.managePermitCheckIsExist()code=" + code
        // +
        // ",operate=" + operate + ",id=" + id);
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Permit.CODE, code);
        List<Permit> list = svcb.listPermitByMap(params);

        if (operate.equals("edit") && !StringUtils.isBlank(id)) {
            for (int i = 0; i < list.size(); i++) {
                // System.out.println("TeacherController.managePermitCheckIsExist()id=" + id +
                // ",getid=" +
                // list.get(i).getId());
                if (id.equals(list.get(i).getId() + "")) {
                    list.remove(i);
                }
            }
        }
        // System.out.println("TeacherController.managePermitCheckIsExist()list.size()="
        // +
        // list.size());
        if (list.size() == 0) {

            map.put("msg", "success");
        } else {

            map.put("msg", "error");
        }
        // System.out.println("TeacherController.managePermitCheckIsExist()map=" + map);

        return map;
    }

    // TODO: GET-permit
    @RequestMapping(value = {"/permit", "/permit/", "/permit/index.html"}, method = RequestMethod.GET)
    public ModelAndView permitGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                  @RequestParam(value = "clrkw", required = false) String clrkw,
                                  @RequestParam(value = "operate", required = false) String operate,
                                  @RequestParam(value = "pn", required = false) Integer pn,
                                  @RequestParam(value = "ps", required = false) Integer ps,
                                  @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "User");// 菜单项显示当前项
        String pagename = "teacher/permit";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(app.entity.DbTable.FieldName.tableType + "", "BASE TABLE");
            List<String> dblist = svcDb.getDbTableList(params);
            mv.addObject("dblist", dblist);
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理许可");
                    mv.addObject("subtitle", "新增许可");
                    mv.addObject("formtitle", "新增许可");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理许可");
                    mv.addObject("subtitle", "修改许可");
                    mv.addObject("formtitle", "修改许可");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Permit it = svcb.getPermitById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);
                    mv.setViewName(pagename);

                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        rtv = svcb.removePermitById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumperclick");
                    }
                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_permit", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_permit");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            mv.addObject("pagetitle", "许可-管理页面");
            mv.addObject("subtitle", "分页显示");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Permit.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Permit.ID, "desc"));

            PagerList<Permit> pagerlist = svcb.pagerListPermit(pagenumber, pagesize, keyword, sqlin, params, ordlist);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.PermitGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Permit> pager = new Pager<Permit>(page);
                // System.out.println("Test.PermitGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/permit", "/permit/", "/permit/index.html"}, method = RequestMethod.POST)
    public ModelAndView permitPOST(ModelAndView mv, HttpServletRequest request, HttpSession session) throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/permit";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        String target = request.getParameter("target");
        String operation = request.getParameter("operation");
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {

                // String password_digest = DigestUtils.md5Hex(password);
                // Integer orderid = (int) Math.round(svc.maxPermit(Permit.SORTID)) + 1;
                // String target,String operation,String name,String code,Integer
                // create_userid,Integer update_userid
                Permit it = new Permit(target, operation, name, code, userSession.getId(), 0);

                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Permit.CODE, code);
                List<Permit> list = svcb.listPermitByMap(params);
                if (StringUtils.isBlank("code")) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "你输入的信息已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    svcb.savePermit(it);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));

                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Permit.CODE, code);
                List<Permit> list = svcb.listPermitByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svcb.updatePermitById(target, operation, name, code, userSession.getId(), new Date().getTime(), id);

                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经修改！");

            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    /*-------------------- 信息管理 ---------------------*/

    // TODO: GET-userpassword
    @RequestMapping(value = {"/userpassword", "/userpassword/",
            "/userpassword/index.html"}, method = RequestMethod.GET)
    public ModelAndView userpasswordGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                        @RequestParam(value = "clrkw", required = false) String clrkw,
                                        @RequestParam(value = "operate", required = false) String operate,
                                        @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Personal");// 菜单项显示当前项
        String pagename = "teacher/userpassword";
        mv.addObject("tagpostfixlist", Common.tagPostfix);
        mv.addObject("operate", "edit");// 触发下拉的脚本和样式
        mv.addObject("pagetitle", "修改密码");
        mv.addObject("subtitle", "个人密码");
        mv.addObject("tabletitle", "管理");
        mv.addObject("formtitle", "个人密码");
        mv.addObject("pagename", Common.getBasePath() + pagename);// 当前页面的url
        return mv;

    }

    @RequestMapping(value = {"/userpassword", "/userpassword/",
            "/userpassword/index.html"}, method = RequestMethod.POST)
    public ModelAndView userpasswordPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {
        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/userpassword";
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
        } else {
            svc.updateUserById(login_password2, userSession.getId(), new Date().getTime(), id);
            // 记录日志
            svcb.saveSyslog(
                    new Syslog(userSession.getId(), userSession.getLogin_name(), Common.getIP(), 0, "更新密码", "", false));

            // 更新登录ip和最后一次登录时间
            // svc.updateUserHasModifyPassword(userSession.getId(), "modified");
        }
        // ----------保存前检查逻辑问题结束------------
        mv.addObject("message", "信息已经修改！");
        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");
        return mv;
    }

    // TODO: GET-role
    @RequestMapping(value = {"/role", "/role/", "/role/index.html"}, method = RequestMethod.GET)
    public ModelAndView roleGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                @RequestParam(value = "clrkw", required = false) String clrkw,
                                @RequestParam(value = "operate", required = false) String operate,
                                @RequestParam(value = "pn", required = false) Integer pn,
                                @RequestParam(value = "ps", required = false) Integer ps,
                                @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "User");// 菜单项显示当前项
        String pagename = "teacher/role";

        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理角色表");
                    mv.addObject("subtitle", "新增角色表");
                    mv.addObject("formtitle", "新增角色表");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                    // 读取permit
                    List<Permit> permitall = svcb.listPermitOrderByAsc(Permit.ID);
                    mv.addObject("permitall", permitall);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理Role");
                    mv.addObject("subtitle", "修改Role");
                    mv.addObject("formtitle", "修改Role");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Role it = svcb.getRoleById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);
                    mv.setViewName(pagename);

                    // 读取permit
                    List<Permit> permitlist = svc.listPermitByRoleid(id);
                    mv.addObject("permitlist", permitlist);

                    List<Permit> permitall = svcb.listPermitOrderByAsc(Permit.ID);
                    mv.addObject("permitall", permitall);

                    mv.setViewName(pagename);
                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        rtv = svcb.removeRoleById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumperclick");
                    }
                }
                break;
                case "hideitem": {
                    Role it = svcb.getRoleById(id);

                    if (it.getIs_actived()) {
                        // true
                        svc.updateRoleActivedById(false, id);
                    } else {
                        // fale
                        svc.updateRoleActivedById(true, id);
                    }

                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperatonce");

                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_role", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_role");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            mv.addObject("pagetitle", "管理");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Role.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Role.ID, "desc"));
            PagerList<Role> pagerlist = svcb.pagerListRole(pagenumber, pagesize, keyword, sqlin, params, ordlist);

            List<Role> list = pagerlist.getList();

            // 在当前需要显示的角色中查找
            for (int i = 0; i < list.size(); i++) {
                // 跳过超级管理员
                if (!list.get(i).getName().equals("superadmin")) {
                    List<Permit> permits = svc.listPermitByRoleid(list.get(i).getId());
                    String permitstring = "";
                    for (Permit permit : permits) {
                        permitstring += permit.getName() + "、";
                    }
                    if (permitstring.endsWith("、")) {
                        permitstring = permitstring.substring(0, permitstring.length() - 1);
                    }
                    list.get(i).setRemark(permitstring);
                }
            }

            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.RoleGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Role> pager = new Pager<Role>(page);
                // System.out.println("Test.RoleGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", list);
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/role", "/role/", "/role/index.html"}, method = RequestMethod.POST)
    public ModelAndView rolePOST(ModelAndView mv, HttpServletRequest request, HttpSession session) throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/role";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        String name = request.getParameter("name");
        String alias = request.getParameter("alias");
        Boolean is_system = StringUtils.isBlank(request.getParameter("is_system")) ? false : true;
        Boolean is_actived = StringUtils.isBlank(request.getParameter("is_actived")) ? false : true;
        String remark = "";
        if (!StringUtils.isBlank(request.getParameter("remark"))) {
            remark = request.getParameter("remark");
        }
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {

                // String password_digest = DigestUtils.md5Hex(password);
                // Integer orderid = (int) Math.round(svc.maxRole(Role.SORTID)) + 1;
                Role it = new Role(name, alias, is_system, is_actived, remark, userSession.getId(), 0);

                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Role.NAME, name);
                List<Role> list = svcb.listRoleByMap(params);
                if (StringUtils.isBlank("name") || StringUtils.isBlank("alias")) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "你输入的信息已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    svcb.saveRole(it);

                    List<String> permit_ids = Arrays.asList(request.getParameterValues("permit_ids"));
                    for (int i = 0; i < permit_ids.size(); i++) {
                        // System.out.println("TeacherController.userPOST()role_ids=" +
                        // role_ids.get(i));
                        svc.saveLink_role_permitByNameandPermitname(name, permit_ids.get(i), userSession.getId());
                    }
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Role.NAME, name);
                List<Role> list = svcb.listRoleByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svcb.updateRoleById(name, alias, is_system, is_actived, remark, userSession.getId(),
                            new Date().getTime(), id);

                    // 读取该用户原有permit列表
                    List<Permit> permitlist = svc.listPermitByRoleid(id);
                    List<String> permitoldlist = new ArrayList<String>();
                    for (int i = 0; i < permitlist.size(); i++) {
                        permitoldlist.add(permitlist.get(i).getName());
                    }
                    List<String> permit_ids = new ArrayList<String>();
                    if (request.getParameterValues("permit_ids") != null) {
                        permit_ids = Arrays.asList(request.getParameterValues("permit_ids"));
                    }

                    // for (int i = 0; i < permit_ids.size(); i++) {
                    // System.out.println("TeacherController.userPOST()role_ids=" +
                    // permit_ids.get(i));
                    // }

                    // 查找已经删除了的权限
                    for (int i = 0; i < permitoldlist.size(); i++) {
                        if (!permit_ids.contains(permitoldlist.get(i))) {
                            // 已经删除了的权限
                            System.out.println("TeacherController.userPOST()需要删除：" + permitoldlist.get(i));
                            svc.removeLink_role_permitByRoleIdandPermitname(id, permitoldlist.get(i));
                        }
                    }
                    // 查找增加的角色
                    for (int i = 0; i < permit_ids.size(); i++) {
                        if (!permitoldlist.contains(permit_ids.get(i))) {
                            // 应该增加的角色
                            System.out.println("TeacherController.userPOST()需要增加：" + permit_ids.get(i));
                            svc.saveLink_role_permitByRoleIdandPermitname(id, permit_ids.get(i), userSession.getId());
                        }
                    }

                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经修改！");

            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    /*-------------------- 系统用户及权限 ---------------------*/

    // TODO: GET-user
    @RequestMapping(value = {"/user", "/user/", "/user/index.html"}, method = RequestMethod.GET)
    public ModelAndView userGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                @RequestParam(value = "clrkw", required = false) String clrkw,
                                @RequestParam(value = "operate", required = false) String operate,
                                @RequestParam(value = "pn", required = false) Integer pn,
                                @RequestParam(value = "ps", required = false) Integer ps,
                                @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "User");// 菜单项显示当前项
        String pagename = "teacher/user";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理管理员表");
                    mv.addObject("subtitle", "新增管理员表");
                    mv.addObject("formtitle", "新增管理员表");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                    // 读取role
                    List<Role> roleall = svcb.listRoleOrderByAsc(Role.ID);
                    mv.addObject("roleall", roleall);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理User");
                    mv.addObject("subtitle", "修改User");
                    mv.addObject("formtitle", "修改User");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    User it = svcb.getUserById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);

                    // 读取role
                    List<Role> rolelist = svc.listRoleByUserid(id);
                    mv.addObject("rolelist", rolelist);

                    List<Role> roleall = svcb.listRoleOrderByAsc(Role.ID);
                    mv.addObject("roleall", roleall);

                    mv.setViewName(pagename);
                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        rtv = svcb.removeUserById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumperclick");
                    }
                }
                break;
                case "hideitem": {

                    User it = svcb.getUserById(id);

                    if (it.getIs_actived()) {
                        // true
                        svc.updateUserActivedById(false, id);
                    } else {
                        // fale
                        svc.updateUserActivedById(true, id);
                    }

                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperatonce");

                }
                break;
                case "passwordrecovery": {
                    // 恢复密码为登录手机号码
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    if (userSession != null) {

                        svc.updateUserPasswordToUsernameById(id);
                        mv.addObject("message", "密码已经恢复为手机号码，请提醒用户及时登录，并修改密码！");
                        mv.setViewName("jumper");
                    } else {
                        mv.addObject("message", "该功能仅限管理员使用！");
                        mv.setViewName("jumper");
                    }
                    mv.addObject("jumpto", Common.getBasePath() + pagename);

                }
                break;
                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_user", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_user");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            if (!StringUtils.isBlank(request.getParameter("department_id_id"))) {
                sqlin.put("department_id_id", request.getParameter("department_id_id"));
            }

            if (!StringUtils.isBlank(request.getParameter("nation_id_id"))) {
                sqlin.put("nation_id_id", request.getParameter("nation_id_id"));
            }

            mv.addObject("pagetitle", "管理");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }

            PagerList<User> pagerlist = svcb.pagerListUser(pagenumber, pagesize, keyword, sqlin);

            List<User> list = pagerlist.getList();
            for (int i = 0; i < list.size(); i++) {

                List<Role> roles = svc.listRoleByUserid(list.get(i).getId());
                String rolestring = "";
                for (Role role : roles) {
                    rolestring += role.getAlias() + "、";
                }
                if (rolestring.endsWith("、")) {
                    rolestring = rolestring.substring(0, rolestring.length() - 1);
                }
                // 拥有的角色信息，放在电子邮件字段，页面从介绍读取
                list.get(i).setEmail(rolestring);

                // 如果密码不是modified，说明还是明文，替换
                if (!list.get(i).getLogin_password().equals("modified")) {
                    list.get(i).setLogin_password("no modified");
                }

            }
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.UserGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<User> pager = new Pager<User>(page);
                // System.out.println("Test.UserGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", list);
                mv.setViewName(pagename);
            }
        }

        return mv;
    }

    @RequestMapping(value = {"/user", "/user/", "/user/index.html"}, method = RequestMethod.POST)
    public ModelAndView userPOST(ModelAndView mv, HttpServletRequest request, HttpSession session) throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/user";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------

        String login_name = request.getParameter("login_name");
        String login_password = login_name;
        String login_name_digest = DigestUtils.md5Hex(login_name);
        String login_password_digest = DigestUtils.md5Hex(login_name);

        String salt = "salt";

        String alias = "";
        if (!StringUtils.isBlank(request.getParameter("alias"))) {
            alias = request.getParameter("alias");
        }
        String name = request.getParameter("name");
        Integer sex = 0;
        if (!StringUtils.isBlank(request.getParameter("sex"))) {
            sex = Integer.parseInt(request.getParameter("sex"));
        }
        String birthday = "";
        if (!StringUtils.isBlank(request.getParameter("birthday"))) {
            birthday = request.getParameter("birthday");
        }
        Boolean is_actived = StringUtils.isBlank(request.getParameter("is_actived")) ? false : true;
        Integer nation_id = 1;
        if (!StringUtils.isBlank(request.getParameter("nation_id"))) {
            nation_id = Integer.parseInt(request.getParameter("nation_id"));
        }
        String qq = "";
        if (!StringUtils.isBlank(request.getParameter("qq"))) {
            qq = request.getParameter("qq");
        }
        String telephone = "";
        if (!StringUtils.isBlank(request.getParameter("telephone"))) {
            telephone = request.getParameter("telephone");
        }
        String email = "";
        if (!StringUtils.isBlank(request.getParameter("email"))) {
            email = request.getParameter("email");
        }

        String wechat_openid = "";// request.getParameter("wechat_openid");
        String wechat_nickname = "";// request.getParameter("wechat_nickname");
        String wechat_headimgurl = "";// request.getParameter("wechat_headimgurl");

        // 状态：用户名密码登录0,微信登录1,两者同时可用2
        Integer login_method = 2;// Integer.parseInt(request.getParameter("login_method"));

        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {
                Long last_login_gmt = 0l;
                String last_login_ip = "0.0.0.0";
                // String password_digest = DigestUtils.md5Hex(password);
                // Integer orderid = (int) Math.round(svc.maxUser(User.SORTID)) + 1;
                User it = new User(name, login_name, login_name_digest, login_password, login_password_digest, email,
                        is_actived, telephone, qq, wechat_openid, wechat_nickname, wechat_headimgurl, login_method,
                        userSession.getId(), 0);

                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(User.LOGIN_NAME, login_name);
                List<User> list = svcb.listUserByMap(params);
                if (StringUtils.isBlank("login_name") || StringUtils.isBlank("name")) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "你输入的信息已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svcb.saveUser(it);
                    if (request.getParameterValues("role_ids") != null) {
                        List<String> role_ids = Arrays.asList(request.getParameterValues("role_ids"));
                        for (int i = 0; i < role_ids.size(); i++) {
                            // System.out.println("TeacherController.userPOST()role_ids=" +
                            // role_ids.get(i));
                            svc.saveLink_user_roleByLogin_nameandRolename(login_name, role_ids.get(i), userSession.getId());
                        }
                    }
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(User.LOGIN_NAME, login_name);
                List<User> list = svcb.listUserByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    svc.updateUserById(login_name, name, is_actived, userSession.getId(), new Date().getTime(), id);
                    List<Role> roleall = svcb.listRoleOrderByAsc(Role.ID);
                    // 读取该用户原有role列表
                    List<Role> rolelist = svc.listRoleByUserid(id);
                    List<String> roleoldlist = new ArrayList<String>();
                    for (int i = 0; i < rolelist.size(); i++) {
                        roleoldlist.add(rolelist.get(i).getName());
                    }
                    List<String> role_ids = Arrays.asList(request.getParameterValues("role_ids"));
//				for (int i = 0; i < role_ids.size(); i++) {
//					System.out.println("TeacherController.userPOST()role_ids=" + role_ids.get(i));
//				}
                    // 查找已经删除了的角色
                    for (int i = 0; i < roleoldlist.size(); i++) {
                        if (!roleoldlist.get(i).equals("superadmin") && !role_ids.contains(roleoldlist.get(i))) {
                            // 已经删除了的角色
                            // System.out.println("TeacherController.userPOST()需要删除：" + roleoldlist.get(i));
                            // 找到对应的roldid
                            Integer roleid = 0;
                            for (int j = 0; j < rolelist.size(); j++) {
                                if (rolelist.get(j).getName().equals(roleoldlist.get(i))) {
                                    roleid = rolelist.get(j).getId();
                                    break;
                                }
                            }
                            svc.removeLink_user_roleByUserIdandRoleid(id, roleid);
                        }
                    }
                    // 查找增加的角色
                    for (int i = 0; i < role_ids.size(); i++) {
                        if (!roleoldlist.contains(role_ids.get(i))) {
                            // 应该增加的角色
                            // System.out.println("TeacherController.userPOST()需要增加：" + role_ids.get(i));
                            // 找到对应的roldid
                            Integer roleid = 0;
                            for (int j = 0; j < roleall.size(); j++) {
                                if (roleall.get(j).getName().equals(role_ids.get(i))) {
                                    roleid = roleall.get(j).getId();
                                    break;
                                }
                            }
                            svc.saveLink_user_roleByUserIdandRoleid(id, roleid, userSession.getId());
                        }
                    }
                }
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经修改！");
            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    /*-------------------- 个人信息管理 ---------------------*/

    // TODO: GET-userinfo
    @RequestMapping(value = {"/userinfo", "/userinfo/", "/userinfo/index.html"}, method = RequestMethod.GET)
    public ModelAndView userinfoGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                    @RequestParam(value = "clrkw", required = false) String clrkw,
                                    @RequestParam(value = "operate", required = false) String operate,
                                    @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(
                this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Personal");// 菜单项显示当前项
        String pagename = "teacher/userinfo";
        mv.addObject("tagpostfixlist", Common.tagPostfix);
        mv.addObject("operate", "edit");// 触发下拉的脚本和样式
        mv.addObject("rolelist", svcb.listRoleOrderByAsc(Role.ID));
        // 使用shiro控制权限后
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));

        mv.addObject("pagetitle", "管理个人信息");
        mv.addObject("subtitle", "个人信息");
        mv.addObject("tabletitle", "管理");
        mv.addObject("formtitle", "个人信息");
        mv.addObject("pagename", pagename);// 当前页面的url

        // 读取当前已有信息
        User it = svcb.getUserById(userSession.getId());
        mv.addObject("it", it);
        mv.setViewName(pagename);

        return mv;

    }

    @RequestMapping(value = {"/userinfo", "/userinfo/", "/userinfo/index.html"}, method = RequestMethod.POST)
    public ModelAndView userinfoPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/userinfo";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        Integer id = userSession.getId();
        // --------编辑修改共有的部分---------
        String name = request.getParameter("name");

        String qq = "";
        if (!StringUtils.isBlank(request.getParameter("qq"))) {
            qq = request.getParameter("qq");
        }
        String email = "";
        if (!StringUtils.isBlank(request.getParameter("email"))) {
            email = request.getParameter("email");
        }

        // --------编辑修改共有的部分-结束---------
        // 来源页面
        String referer = request.getParameter("referer");
        // ----------保存前检查逻辑上是否有冲突---------------
        // 如：用户名必须唯一，手机号必须唯一
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(User.NAME, name);
        List<User> list = svcb.listUserByMap(params);
        if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

            mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
            mv.addObject("jumpto", Common.getBasePath() + pagename);
            mv.setViewName("jumperback");
            return mv;
        } else {
            svc.updateUserById(name, qq, email, userSession.getId(), new Date().getTime(), id);

            // 记录日志
            svcb.saveSyslog(new Syslog(userSession.getId(), userSession.getLogin_name(), Common.getIP(), 0, "更新个人信息",
                    "", false));

        }
        // ----------保存前检查逻辑问题结束------------

        mv.addObject("message", "信息已经修改！");
        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO: GET-syslog
    @RequestMapping(value = {"/syslog", "/syslog/", "/syslog/index.html"}, method = RequestMethod.GET)
    public ModelAndView syslogGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                  @RequestParam(value = "clrkw", required = false) String clrkw,
                                  @RequestParam(value = "operate", required = false) String operate,
                                  @RequestParam(value = "pn", required = false) Integer pn,
                                  @RequestParam(value = "ps", required = false) Integer ps,
                                  @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Website");// 菜单项显示当前项
        String pagename = "teacher/syslog";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            mv.addObject("userlist", svcb.listUserOrderByAsc(User.ID));
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_syslog", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_syslog");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();
            if (!StringUtils.isBlank(request.getParameter("user_id"))) {
                sqlin.put(Syslog.USER_ID + "_id", request.getParameter("user_id"));
                mv.addObject(Syslog.USER_ID + "_map", Util.splitToInteger(request.getParameter("user_id"), ","));
                qs += Syslog.USER_ID + "=" + request.getParameter("user_id") + "&";
            }

            mv.addObject("pagetitle", "日志列表");
            mv.addObject("subtitle", "");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Syslog.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Syslog.ID, "desc"));

            PagerList<Syslog> pagerlist = svcb.pagerListSyslog(pagenumber, pagesize, keyword, sqlin, params, ordlist);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.SyslogGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Syslog> pager = new Pager<Syslog>(page);
                // System.out.println("Test.SyslogGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    // TODO: GET-subject
    @RequestMapping(value = {"/subject", "/subject/", "/subject/index.html"}, method = RequestMethod.GET)
    public ModelAndView subjectGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                   @RequestParam(value = "clrkw", required = false) String clrkw,
                                   @RequestParam(value = "operate", required = false) String operate,
                                   @RequestParam(value = "pn", required = false) Integer pn,
                                   @RequestParam(value = "ps", required = false) Integer ps,
                                   @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "BaseInfo");// 菜单项显示当前项
        String pagename = "teacher/subject";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理科目");
                    mv.addObject("subtitle", "新增科目");
                    mv.addObject("formtitle", "新增科目");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理科目");
                    mv.addObject("subtitle", "修改");
                    mv.addObject("formtitle", "修改");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    tpa.entity.Subject it = svcb.getSubjectById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);
                    mv.setViewName(pagename);
                }
                break;
                case "hideitem":
                case "showitem": {

                    svcb.updateSubjectById(operate, id);
                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");

                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        rtv = svcb.removeSubjectById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumperclick");
                    }
                }
                break;
                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_subject", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_subject");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            mv.addObject("pagetitle", "管理科目");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Subject.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Subject.ID, "desc"));

            PagerList<tpa.entity.Subject> pagerlist = svcb.pagerListSubject(pagenumber, pagesize, keyword, sqlin,
                    params, ordlist);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.SubjectGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Subject> pager = new Pager<Subject>(page);
                // System.out.println("Test.SubjectGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/subject", "/subject/", "/subject/index.html"}, method = RequestMethod.POST)
    public ModelAndView subjectPOST(ModelAndView mv, HttpServletRequest request, HttpSession session) throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/subject";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        String intro = request.getParameter("intro");
        String name = request.getParameter("name");
        String illustration = request.getParameter("illustration");
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {

                // String password_digest = DigestUtils.md5Hex(password);
                Integer sortid = (int) Math.round(svcb.maxSubject(tpa.entity.Subject.SORTID)) + 1;
                tpa.entity.Subject it = new tpa.entity.Subject(true, sortid, intro, name, illustration);
                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(tpa.entity.Subject.NAME, name);
                List<tpa.entity.Subject> list = svcb.listSubjectByMap(params);
                if (StringUtils.isBlank(name)) {

                    mv.addObject("message", "科目标题必须输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "该科目已经存在，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    svcb.saveSubject(it);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));

                // ----------保存前检查逻辑上是否有冲突---------------
                // 科目名称不能相同
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(tpa.entity.Subject.NAME, name);
                List<tpa.entity.Subject> list = svcb.listSubjectByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svc.updateSubjectById(intro, name, illustration, new Date().getTime(), id);

                }
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经修改！");
            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO: GET-vcurriculum
    @RequestMapping(value = {"/curriculum", "/curriculum/", "/curriculum/index.html"}, method = RequestMethod.GET)
    public ModelAndView curriculumGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                      @RequestParam(value = "clrkw", required = false) String clrkw,
                                      @RequestParam(value = "operate", required = false) String operate,
                                      @RequestParam(value = "pn", required = false) Integer pn,
                                      @RequestParam(value = "ps", required = false) Integer ps,
                                      @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "BaseInfo");// 菜单项显示当前项
        String pagename = "teacher/curriculum";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            mv.addObject("subjectlist", svcb.listSubjectByMap(new HashMap<String, Object>() {
                {
                    put(tpa.entity.Subject.IS_ACTIVED, true);
                }
            }));

            mv.addObject("student_grouplist", svcb.listStudent_groupByMap(new HashMap<String, Object>() {
                {
                    put(Student_group.IS_ACTIVED, true);
                }
            }));
            mv.addObject("userlist", svcb.listUserOrderByAsc(User.ID));

        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    // 从配置读取当前学期编号
                    mv.addObject("semester", svc.getConfigByKey("semester").getValue());

                    mv.addObject("pagetitle", "管理课程");
                    mv.addObject("subtitle", "新增");
                    mv.addObject("formtitle", "新增");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理课程");
                    mv.addObject("subtitle", "修改");
                    mv.addObject("formtitle", "修改");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Vcurriculum it = svcb.getVcurriculumById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);
                    mv.setViewName(pagename);
                }
                break;

                case "hideitem":
                case "showitem": {

                    svcb.updateCurriculumById(operate, id);
                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");

                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        // 视图需要定制删除方法！
                        rtv = svcb.removeCurriculumById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumperclick");
                    }
                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vcurriculum", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vcurriculum");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            if (!StringUtils.isBlank(request.getParameter("subject_id"))) {
                sqlin.put(Vcurriculum.SUBJECT_ID + "_id", request.getParameter("subject_id"));
                mv.addObject(Vcurriculum.SUBJECT_ID + "_map",
                        Util.splitToInteger(request.getParameter("subject_id"), ","));
                qs += Vcurriculum.SUBJECT_ID + "=" + request.getParameter("subject_id") + "&";
            }
            if (!StringUtils.isBlank(request.getParameter("student_group_id"))) {
                sqlin.put(Vcurriculum.STUDENT_GROUP_ID + "_id", request.getParameter("student_group_id"));
                mv.addObject(Vcurriculum.STUDENT_GROUP_ID + "_map",
                        Util.splitToInteger(request.getParameter("student_group_id"), ","));
                qs += Vcurriculum.STUDENT_GROUP_ID + "=" + request.getParameter("student_group_id") + "&";
            }

            if (!StringUtils.isBlank(request.getParameter("user_id"))) {
                sqlin.put(Vcurriculum.USER_ID + "_id", request.getParameter("user_id"));
                mv.addObject(Vcurriculum.USER_ID + "_map", Util.splitToInteger(request.getParameter("user_id"), ","));
                qs += Vcurriculum.USER_ID + "=" + request.getParameter("user_id") + "&";
            }

            mv.addObject("pagetitle", "管理");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Vcurriculum.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Vcurriculum.ID, "desc"));

            PagerList<Vcurriculum> pagerlist = svcb.pagerListVcurriculum(pagenumber, pagesize, keyword, sqlin, params,
                    ordlist);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.VcurriculumGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Vcurriculum> pager = new Pager<Vcurriculum>(page);
                // System.out.println("Test.VcurriculumGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/curriculum", "/curriculum/", "/curriculum/index.html"}, method = RequestMethod.POST)
    public ModelAndView curriculumPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/curriculum";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        Integer subject_id = Integer.parseInt(request.getParameter("subject_id"));
        Integer student_group_id = Integer.parseInt(request.getParameter("student_group_id"));
        Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        String semester = request.getParameter("semester");
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {

                Curriculum it = new Curriculum(subject_id, student_group_id, user_id, true, semester);

                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Curriculum.SUBJECT_ID, subject_id);
                params.put(Curriculum.STUDENT_GROUP_ID, student_group_id);
                params.put(Curriculum.USER_ID, user_id);
                params.put(Curriculum.SEMESTER, semester);
                List<Curriculum> list = svcb.listCurriculumByMap(params);

                if (StringUtils.isBlank(semester)) {

                    mv.addObject("message", "学期信息必须输入，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "你输入的信息已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    // 视图需要定制新增方法！
                    svcb.saveCurriculum(it);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Curriculum.SUBJECT_ID, subject_id);
                params.put(Curriculum.STUDENT_GROUP_ID, student_group_id);
                params.put(Curriculum.USER_ID, user_id);
                params.put(Curriculum.SEMESTER, semester);
                List<Curriculum> list = svcb.listCurriculumByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svc.updateCurriculumById(subject_id, student_group_id, user_id, true, semester, new Date().getTime(),
                            id);

                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经修改！");

            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    /***
     * for superadmin，管理所有的实验
     * @param mv
     * @param kw
     * @param clrkw
     * @param operate
     * @param pn
     * @param ps
     * @param id
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/experimentmanagement", "/experimentmanagement/",
            "/experimentmanagement/index.html"}, method = RequestMethod.GET)
    public ModelAndView experimentmanagementGET(ModelAndView mv,
                                                @RequestParam(value = "kw", required = false) String kw,
                                                @RequestParam(value = "clrkw", required = false) String clrkw,
                                                @RequestParam(value = "operate", required = false) String operate,
                                                @RequestParam(value = "pn", required = false) Integer pn,
                                                @RequestParam(value = "ps", required = false) Integer ps,
                                                @RequestParam(value = "id", required = false) Integer id,
												HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "BaseInfo");// 菜单项显示当前项
        // 管理员和教师共用一个页面
        String pagename = "teacher/experimentmanagement";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            mv.addObject("subjectlist", svcb.listSubjectOrderByAsc(tpa.entity.Subject.ID));
            mv.addObject("student_grouplist", svcb.listStudent_groupOrderByAsc(Student_group.ID));
            mv.addObject("userlist", svcb.listUserOrderByAsc(User.ID));

        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "edititem": {
                    // 编辑实验内容
                    mv.addObject("pagetitle", "实验管理");
                    mv.addObject("subtitle", "编辑实验内容");
                    mv.addObject("formtitle", "编辑实验内容");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());
                    // 读取当前已有信息
                    Link_experiment_curriculum lec = svcb.getLink_experiment_curriculumById(id);
                    Experiment experiment = svcb.getExperimentById(lec.getExperiment_id());
                    // 修正信息：如默认头像
                    mv.addObject("lec", lec);
                    mv.addObject("it", experiment);
                    mv.setViewName(pagename);
                }
                break;
                case "hideitem":
                case "showitem": {
                    svcb.updateLink_experiment_curriculumById(operate, id);
                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");
                }
                break;
                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vcurriculum", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vcurriculum");
                keyword = null;
            }
            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();
            mv.addObject("pagetitle", "实验管理");
            mv.addObject("subtitle", "当前管理列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url
            // 在列表中需要筛选的条件
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Vcurriculum.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Vcurriculum.ID, "desc"));
            PagerList<Vcurriculum> pagerlist = svcb.pagerListVcurriculum(pagenumber, pagesize, keyword, sqlin, params, ordlist);
            List<Vcurriculum> curriculums = pagerlist.getList();
            List<ExperimentCurriculum> list = new ArrayList<ExperimentCurriculum>();
            for (int i = 0; i < curriculums.size(); i++) {
                int curriculum_id = curriculums.get(i).getId();
                ExperimentCurriculum it = new ExperimentCurriculum();
                // 老师的课程
                it.setCurriculum(curriculums.get(i));
                // 老师的实验
                List<Vlink_experiment_curriculum_min> experiments = svc.listExperimentByMap(new HashMap<String, Object>() {
                            {
                                put(Vlink_experiment_curriculum_min.CURRICULUM_ID, curriculum_id);
                            }
                        }, Vlink_experiment_curriculum_min.OPENING_GMT);
                it.setExperimentlist(experiments);
                list.add(it);
            }
            mv.addObject("list", list);
            // System.out.println("TeacherController.vcurriculumGET()list.size=" +
            // list.size());
            mv.addObject("now", new Date().getTime());
            mv.setViewName(pagename);
        }
        return mv;
    }

    /***
     * 教师更新实验内容和实验安排
     * 表experiment
     * for superadmin，管理所有的实验，对应表link_experiment_curriculum
     * @param mv
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/experimentmanagement", "/experimentmanagement/", "/experimentmanagement/index.html"},
            method = RequestMethod.POST)
    public ModelAndView experimentmanagementPOST(ModelAndView mv, HttpServletRequest request, HttpSession session) throws Exception {
        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/experimentarrange";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        // 实验标题
        String title = request.getParameter("title");
        // 开始时间
        Long opening_gmt = sdf.parse(request.getParameter("opening_gmt_date")).getTime();
        opening_gmt = opening_gmt > 0 ? opening_gmt : 0;
        // 截止时间
        Long closing_gmt = sdf.parse(request.getParameter("closing_gmt_date")).getTime();
        closing_gmt = closing_gmt > 0 ? closing_gmt : 0;
        // 实验简介
        String intro = request.getParameter("intro");
        // 文件大小
        Integer filesize = Integer.parseInt(request.getParameter("filesize"));
        // 实验内容
        String content = request.getParameter("content");
        // 实验目的及要求
        String purposes_requirement = request.getParameter("purposes_requirement");

//		Integer subject_id = Integer.parseInt(request.getParameter("subject_id"));
//		Integer curriculum_id = Integer.parseInt(request.getParameter("curriculum_id"));
//		Boolean is_actived = StringUtils.isBlank(request.getParameter("is_actived")) ? false : true;

        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面

        switch (operate) {
            case "editform": {
                Integer experiment_id = Integer.parseInt(request.getParameter("experiment_id"));
                Integer lec_id = Integer.parseInt(request.getParameter("lec_id"));
                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                svc.updateExperimentById(intro, title, content, purposes_requirement, filesize, new Date().getTime(), experiment_id);
                svc.updateLink_experiment_curriculumById(opening_gmt, closing_gmt, new Date().getTime(), lec_id);
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经修改！");
            }
            break;
            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }
        mv.addObject("jumpto", Common.getBasePath() + pagename);
        mv.setViewName("jumper");
        return mv;
    }

    /***
     * 当前用户的实验安排
     *
     * @param mv
     * @param kw
     * @param clrkw
     * @param operate
     * @param pn
     * @param ps
     * @param id
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/experimentarrange", "/experimentarrange/",
            "/experimentarrange/index.html"}, method = RequestMethod.GET)
    public ModelAndView experimentarrangeGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                             @RequestParam(value = "clrkw", required = false) String clrkw,
                                             @RequestParam(value = "operate", required = false) String operate,
                                             @RequestParam(value = "pn", required = false) Integer pn,
                                             @RequestParam(value = "ps", required = false) Integer ps,
                                             @RequestParam(value = "id", required = false) Integer id,
											 HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Experiment");// 菜单项显示当前项
        String pagename = "teacher/experimentarrange";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            mv.addObject("subjectlist", svcb.listSubjectOrderByAsc(tpa.entity.Subject.ID));
            mv.addObject("student_grouplist", svcb.listStudent_groupOrderByAsc(Student_group.ID));
            mv.addObject("userlist", svcb.listUserOrderByAsc(User.ID));
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "additem": {
                    mv.addObject("pagetitle", "管理实验");
                    mv.addObject("subtitle", "新增实验");
                    mv.addObject("formtitle", "新增实验");
                    mv.addObject("pagename", pagename);
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    Date opening_gmt_date = new Date(calendar.getTimeInMillis());
                    calendar.add(Calendar.DATE, 5);
                    Date closing_gmt_date = new Date(calendar.getTimeInMillis());
                    mv.addObject("opening_gmt_date", opening_gmt_date);
                    mv.addObject("closing_gmt_date", closing_gmt_date);
                    Vcurriculum curriculum = svcb.getVcurriculumById(id);
                    mv.addObject("curriculum", curriculum);
                    mv.setViewName(pagename);
                }
                break;
                case "edititem": {
                    // 编辑实验内容
                    mv.addObject("pagetitle", "管理实验");
                    mv.addObject("subtitle", "编辑实验内容");
                    mv.addObject("formtitle", "编辑实验内容");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());
                    // 读取当前已有信息
                    Link_experiment_curriculum lec = svcb.getLink_experiment_curriculumById(id);
                    Experiment experiment = svcb.getExperimentById(lec.getExperiment_id());
                    // 修正信息：如默认头像
                    mv.addObject("lec", lec);
                    mv.addObject("it", experiment);
                    mv.setViewName(pagename);
                }
                break;
                case "hideitem":
                case "showitem": {
                    svcb.updateLink_experiment_curriculumById(operate, id);
                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");
                }
                break;
                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);//当前页码
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);//每页的数量
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vcurriculum", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vcurriculum");
                keyword = null;
            }
            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();
            mv.addObject("pagetitle", "实验安排");
            mv.addObject("subtitle", "Experimental arrangement");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url
            // 在列表中需要筛选的条件
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Vcurriculum.USER_ID, userSession.getId());
            params.put(Vcurriculum.IS_ACTIVED, true);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Vcurriculum.ID, "desc"));
            //
            PagerList<Vcurriculum> pagerlist = svcb.pagerListVcurriculum(pagenumber, pagesize, keyword, sqlin, params, ordlist);
            List<Vcurriculum> curriculums = pagerlist.getList();
            List<ExperimentCurriculum> list = new ArrayList<ExperimentCurriculum>();
            for (int i = 0; i < curriculums.size(); i++) {
                int curriculum_id = curriculums.get(i).getId();
                ExperimentCurriculum it = new ExperimentCurriculum();
                // 老师的课程
                it.setCurriculum(curriculums.get(i));
                // 老师的实验
                List<Vlink_experiment_curriculum_min> experiments = svc.listExperimentByMap(new HashMap<String, Object>() {
                            {
                                put(Vlink_experiment_curriculum_min.CURRICULUM_ID, curriculum_id);
                            }
                        }, Vlink_experiment_curriculum_min.OPENING_GMT);
                it.setExperimentlist(experiments);
                list.add(it);
            }
            mv.addObject("list", list);//Vcurriculum、Vlink_experiment_curriculum_min  curriculum.id=45、44

            //==========================julius  4.22  begin========================================
            Pager pager = pagerlist.getPager();
            mv.addObject("pager", pager);
            //==========================julius  4.22  end========================================
            mv.addObject("now", new Date().getTime());
            mv.setViewName(pagename);
        }
        return mv;
    }

    /***
     * 教师更新实验内容和实验安排
     *
     * 表experiment
     *
     * 表link_experiment_curriculum
     *
     * @param mv
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/experimentarrange", "/experimentarrange/",
            "/experimentarrange/index.html"}, method = RequestMethod.POST)
    public ModelAndView experimentarrangePOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/experimentarrange";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------

        //

        // 实验标题
        String title = request.getParameter("title");
        // 开始时间
        Long opening_gmt = sdf.parse(request.getParameter("opening_gmt_date")).getTime();
        opening_gmt = opening_gmt > 0 ? opening_gmt : 0;
        // 截止时间
        Long closing_gmt = sdf.parse(request.getParameter("closing_gmt_date")).getTime();
        closing_gmt = closing_gmt > 0 ? closing_gmt : 0;
        // 实验简介
        String intro = request.getParameter("intro");
        // 文件大小
        Integer filesize = Integer.parseInt(request.getParameter("filesize"));
        // 实验内容
        String content = request.getParameter("content");
        // 实验目的及要求
        String purposes_requirement = request.getParameter("purposes_requirement");

        // --------编辑修改共有的部分-结束---------
        // 来源页面
        switch (operate) {
            case "addform": {

                // ----------保存前检查逻辑问题---------------
                if (StringUtils.isBlank(title) || StringUtils.isBlank(intro) || StringUtils.isBlank(content)
                        || StringUtils.isBlank(purposes_requirement)) {

                    mv.addObject("message", "实验目的及要求、实验内容，必须输入！");
                    mv.addObject("jumpto", pagename);
                    mv.setViewName("jumperback");
                    return mv;

                } else {

                    Integer subject_id = Integer.parseInt(request.getParameter("subject_id"));
                    Integer curriculum_id = Integer.parseInt(request.getParameter("curriculum_id"));
                    Experiment exp = new Experiment(subject_id, curriculum_id, true, intro, title, content,
                            purposes_requirement, filesize);
                    svcb.saveExperiment(exp);

                    Integer sortid = (int) Math.round(svcb.maxLink_experiment_curriculum(Link_experiment_curriculum.SORTID))
                            + 1;
                    Link_experiment_curriculum lec = new Link_experiment_curriculum(exp.getId(), curriculum_id, subject_id,
                            opening_gmt, closing_gmt, sortid, true);
                    svcb.saveLink_experiment_curriculum(lec);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {

                Integer experiment_id = Integer.parseInt(request.getParameter("experiment_id"));
                Integer lec_id = Integer.parseInt(request.getParameter("lec_id"));

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一

                svc.updateExperimentById(intro, title, content, purposes_requirement, filesize, new Date().getTime(),
                        experiment_id);

                svc.updateLink_experiment_curriculumById(opening_gmt, closing_gmt, new Date().getTime(), lec_id);

                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经修改！");

            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", Common.getBasePath() + pagename);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO: 统计各个课程的实验数量
    @RequestMapping(value = {"/statexperiment", "/statexperiment/",
            "/statexperiment/index.html"}, method = RequestMethod.GET)
    public ModelAndView statexperimentGET(ModelAndView mv, HttpServletRequest request, HttpSession session) {

        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        // System.out.println("TeacherController.experimentcurriculumGET()userSession="+userSession.toString());

        mv.addObject("navitem", "BaseInfo");// 菜单项显示当前项
        String pagename = "teacher/statexperiment";

        mv.addObject("pagetitle", "管理");
        mv.addObject("subtitle", "分页列表");
        mv.addObject("tabletitle", "管理");
        mv.addObject("pagename", pagename);// 当前页面的url

        // SELECT
        // curriculum_id,student_group_id,student_group_name,semester,subject_id,subject_name,experiment_curriculum_is_actived,count(experiment_curriculum_is_actived)
        // as num FROM tpa.vlink_experiment_curriculum_min
        // group by
        // curriculum_id,student_group_id,student_group_name,semester,subject_id,subject_name,experiment_curriculum_is_actived
        // order by experiment_curriculum_is_actived desc,semester

        String semester = svc.getConfigByKey("semester").getValue();
        System.out.println("TeacherController.statexperimentGET()semester=" + semester);
        List<StatExperimentCurriculum> list = svc.statExperimentCurriculum(semester);
        // System.out.println("TeacherController.vlink_experiment_curriculum_minGET()list.size="
        // +
        // list.size());

        List<StatExperimentCurriculum> listteacher = new ArrayList<StatExperimentCurriculum>();

        if (!userSession.getAlias().equals("刘洪")) {

            List<Integer> curriculum_ids = new ArrayList<Integer>();

            List<Curriculum> curriculums = svcb.listCurriculumByMap(new HashMap<String, Object>() {
                {
                    put(Curriculum.USER_ID, userSession.getId());
                }
            });

            for (int i = 0; i < curriculums.size(); i++) {
                curriculum_ids.add(curriculums.get(i).getId());
            }

            for (int i = 0; i < list.size(); i++) {
                if (curriculum_ids.contains(list.get(i).getCurriculum_id())) {
                    listteacher.add(list.get(i));
                }
            }
            mv.addObject("list", listteacher);
        } else {
            mv.addObject("list", list);
        }

        mv.setViewName(pagename);

        return mv;
    }

    /**
     * 通过学期semester统计该学期老师的所有实验
     *
     * @param mv
     * @param request
     * @param session
     * @return
     */
    // TODO: 统计各个课程的实验数量(教师版)
    @RequestMapping(value = {"/statexperimentteacher", "/statexperimentteacher/",
            "/statexperimentteacher/index.html"}, method = RequestMethod.GET)
    public ModelAndView statexperimentteacherGET(ModelAndView mv, HttpServletRequest request, HttpSession session) {
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        // System.out.println("TeacherController.experimentcurriculumGET()userSession="+userSession.toString());
        mv.addObject("navitem", "Experiment");// 菜单项显示当前项
        String pagename = "teacher/statexperimentteacher";
        mv.addObject("pagetitle", "实验统计");
        mv.addObject("subtitle", "Experimental statistics");
        mv.addObject("tabletitle", "实验管理");
        mv.addObject("pagename", pagename);// 当前页面的url
        // SELECT
        // curriculum_id,student_group_id,student_group_name,semester,subject_id,subject_name,experiment_curriculum_is_actived,count(experiment_curriculum_is_actived)
        // as num FROM tpa.vlink_experiment_curriculum_min
        // group by
        // curriculum_id,student_group_id,student_group_name,semester,subject_id,subject_name,experiment_curriculum_is_actived
        // order by experiment_curriculum_is_actived desc,semester
        String semester = svc.getConfigByKey("semester").getValue();
        System.out.println("TeacherController.statexperimentGET()semester=" + semester);
        List<StatExperimentCurriculum> list = svc.statExperimentCurriculum(semester);
        // System.out.println("TeacherController.vlink_experiment_curriculum_minGET()list.size="
        // +
        // list.size());
        List<StatExperimentCurriculum> listteacher = new ArrayList<StatExperimentCurriculum>();
        System.out.println("TeacherController.statexperimentteacherGET()userSession.getAlias()=" + userSession.getAlias());
        List<Integer> curriculum_ids = new ArrayList<Integer>();
        List<Curriculum> curriculums = svcb.listCurriculumByMap(new HashMap<String, Object>() {
            {
                put(Curriculum.USER_ID, userSession.getId());
            }
        });
        for (int i = 0; i < curriculums.size(); i++) {
            curriculum_ids.add(curriculums.get(i).getId());
        }
        for (int i = 0; i < list.size(); i++) {
            if (curriculum_ids.contains(list.get(i).getCurriculum_id())) {
                listteacher.add(list.get(i));
            }
        }
        mv.addObject("list", listteacher);
        mv.setViewName(pagename);
        return mv;
    }

    // TODO: GET-vevaluation
    @RequestMapping(value = {"/evaluation", "/evaluation/", "/evaluation/index.html"}, method = RequestMethod.GET)
    public ModelAndView evaluationGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                      @RequestParam(value = "clrkw", required = false) String clrkw,
                                      @RequestParam(value = "operate", required = false) String operate,
                                      @RequestParam(value = "pn", required = false) Integer pn,
                                      @RequestParam(value = "ps", required = false) Integer ps,
                                      @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Evaluation");// 菜单项显示当前项
        String pagename = "teacher/evaluation";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            mv.addObject("curriculumlist", svcb.listVcurriculumOrderByAsc(Vcurriculum.ID));
            // mv.addObject("curriculummap", svcb.mapVcurriculum());
            mv.addObject("subjectlist", svcb.listSubjectOrderByAsc(tpa.entity.Subject.ID));
            // mv.addObject("subjectmap", svcb.mapSubject());
            mv.addObject("student_grouplist", svcb.listStudent_groupOrderByAsc(Student_group.ID));
            // mv.addObject("student_groupmap", svcb.mapStudent_group());
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理考核");
                    mv.addObject("subtitle", "新增");
                    mv.addObject("formtitle", "新增");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理考核");
                    mv.addObject("subtitle", "修改");
                    mv.addObject("formtitle", "修改");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Vevaluation it = svcb.getVevaluationById(id);
                    mv.addObject("it", it);

                    // 解析考核内容
                    // 1,5,2;3,10,3;4,3,2;5,3,8;6,2,15
                    List<EvaluationTopic> topics = new ArrayList<EvaluationTopic>();
                    String[] list = it.getContent().split(";");
                    for (int i = 0; i < list.length; i++) {
                        String[] l2 = list[i].split(",");
                        Integer type = Integer.parseInt(l2[0]);
                        Integer num = Integer.parseInt(l2[1]);
                        Integer point = Integer.parseInt(l2[2]);

                        EvaluationTopic topic = new EvaluationTopic(svcb.getEvaluation_typeById(type), num, point);
                        topics.add(topic);
                    }
                    mv.addObject("topics", topics);

                    mv.setViewName(pagename);
                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        // 视图需要定制删除方法！
                        // rtv = svc.removeVevaluationById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumperclick");
                    }
                }
                break;
                case "hideitem":
                case "showitem": {

                    svcb.updateEvaluationById(operate, id);
                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");

                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vevaluation", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vevaluation");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            if (!StringUtils.isBlank(request.getParameter("curriculum_id"))) {
                sqlin.put(Vevaluation.CURRICULUM_ID + "_id", request.getParameter("curriculum_id"));
                mv.addObject(Vevaluation.CURRICULUM_ID + "_map",
                        Util.splitToInteger(request.getParameter("curriculum_id"), ","));
                qs += Vevaluation.CURRICULUM_ID + "=" + request.getParameter("curriculum_id") + "&";
            }

            if (!StringUtils.isBlank(request.getParameter("subject_id"))) {
                sqlin.put(Vevaluation.SUBJECT_ID + "_id", request.getParameter("subject_id"));
                mv.addObject(Vevaluation.SUBJECT_ID + "_map",
                        Util.splitToInteger(request.getParameter("subject_id"), ","));
                qs += Vevaluation.SUBJECT_ID + "=" + request.getParameter("subject_id") + "&";
            }

            if (!StringUtils.isBlank(request.getParameter("student_group_id"))) {
                sqlin.put(Vevaluation.STUDENT_GROUP_ID + "_id", request.getParameter("student_group_id"));
                mv.addObject(Vevaluation.STUDENT_GROUP_ID + "_map",
                        Util.splitToInteger(request.getParameter("student_group_id"), ","));
                qs += Vevaluation.STUDENT_GROUP_ID + "=" + request.getParameter("student_group_id") + "&";
            }

            mv.addObject("pagetitle", "管理考核");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Vevaluation.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Vevaluation.ID, "desc"));

            PagerList<Vevaluation> pagerlist = svcb.pagerListVevaluation(pagenumber, pagesize, keyword, sqlin, params,
                    ordlist);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.VevaluationGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Vevaluation> pager = new Pager<Vevaluation>(page);
                // System.out.println("Test.VevaluationGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/evaluation", "/evaluation/", "/evaluation/index.html"}, method = RequestMethod.POST)
    public ModelAndView evaluationPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/vevaluation";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        Boolean is_actived = StringUtils.isBlank(request.getParameter("is_actived")) ? false : true;
        Long begin_gmt = sdf.parse(request.getParameter("begin_gmt_date")).getTime();
        begin_gmt = begin_gmt > 0 ? begin_gmt : 0;
        Long end_gmt = sdf.parse(request.getParameter("end_gmt_date")).getTime();
        end_gmt = end_gmt > 0 ? end_gmt : 0;
        String content = request.getParameter("content");
        String title = request.getParameter("title");
        Integer curriculum_id = Integer.parseInt(request.getParameter("curriculum_id"));

        // --------编辑修改共有的部分-结束---------
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {
                // String password_digest = DigestUtils.md5Hex(password);
                Integer sortid = (int) Math.round(svcb.maxVevaluation(Vevaluation.SORTID)) + 1;

                Evaluation it = new Evaluation(curriculum_id, is_actived, begin_gmt, end_gmt, content, title, sortid);

                // ----------保存前检查逻辑问题---------------
                if (StringUtils.isBlank(content) || StringUtils.isBlank(title)) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;

                } else {

                    // 视图需要定制新增方法！
                    svcb.saveEvaluation(it);
                }
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // ----------保存前检查逻辑上是否有冲突---------------
                svc.updateEvaluationById(curriculum_id, true, begin_gmt, end_gmt, content, title, new Date().getTime(), id);
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经修改！");
            }
            break;
            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }
        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");
        return mv;
    }

    // TODO: GET-configevaluation
    @RequestMapping(value = {"/configevaluation", "/configevaluation/",
            "/configevaluation/index.html"}, method = RequestMethod.GET)
    public ModelAndView configEvaluationGET(ModelAndView mv, @RequestParam(value = "id", required = false) String id,
                                            @RequestParam(value = "operate", required = false) String operate,
                                            @RequestParam(value = "uid", required = false) Integer uid, HttpServletRequest request,
                                            HttpSession session) {
        System.out.println("TeacherController.configEvaluationGET()id=" + id);

        mv.addObject("navitem", "Evaluation");// 菜单项显示当前项
        String pagename = "teacher/configevaluation";
        mv.addObject("pagetitle", "配置考核");
        mv.addObject("subtitle", "考核详情");
        mv.addObject("tabletitle", "考核详情列表");
        mv.addObject("pagename", pagename);// 当前页面的url

        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            Integer itemid = 0;
            if (operate.equals("builditem") || operate.equals("builditems")) {
                try {
                    itemid = Integer.parseInt(id);
                } catch (Exception e) {
                    e.printStackTrace();
                    mv.addObject("message", "外星人偷偷篡改了请求信息，请求失败，请稍后重试！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperclick");
                    return mv;
                }
            }
            switch (operate) {
                case "builditem": {
                    System.out.println("TeacherController.configEvaluationGET()重置一个考生的考核内容,uid=" + uid + ",id=" + id);

                    String msg = svc.rebuildEvp(uid, itemid);

                    mv.addObject("message", "指定考生试题已经重新生成！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename + "?id=" + itemid);
                    mv.setViewName("jumperatonce");
                }
                break;
                case "builditems": {

                    // 读取当前考核信息
                    Vevaluation evaluation = svcb.getVevaluationById(itemid);
                    mv.addObject("evaluation", evaluation);

                    // 读取考核对应的分组所有成员
                    List<Vlink_student2group> vlink_student2groups = svc
                            .listVlink_student2groupByGroupId(evaluation.getStudent_group_id());

                    // 读取考核已经提交的信息
                    List<Integer> hadSubmitUserId = new ArrayList<>();// user_id
                    HashMap<Integer, Integer> evrUseridScoreMap = new HashMap<>();// user_id,score
                    {
                        List<Evaluation_result> evaluation_records = svc
                                .listEvaluation_resultByEvaluationId(evaluation.getId());
                        // mv.addObject("evaluation_records", evaluation_records);
                        for (int i = 0; i < evaluation_records.size(); i++) {
                            hadSubmitUserId.add(evaluation_records.get(i).getStudent_id());
                            evrUseridScoreMap.put(evaluation_records.get(i).getStudent_id(),
                                    evaluation_records.get(i).getScore());
                        }
                    }
                    for (int i = 0; i < vlink_student2groups.size(); i++) {
                        if (hadSubmitUserId.contains(vlink_student2groups.get(i).getStudent_id())) {
                            // 已经提交，不能重置
                        } else {
                            svc.rebuildEvp(vlink_student2groups.get(i).getStudent_id(), itemid);
                        }
                    }
                    mv.addObject("message", "所有未提交的考核已经重新生成！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename + "?id=" + itemid);
                    mv.setViewName("jumperatonce");
                }
                break;
                case "viewitem": {
                    System.out.println("TeacherController.managevevaluationGET()查看一个考生的考核详情,uid=" + uid + ",id=" + id);

                }
                break;

                default: {

                }
                break;
            }
        } else {
            Integer itemid = 0;
            try {
                itemid = Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                mv.addObject("message", "外星人偷偷篡改了请求信息，请求失败，请稍后重试！");
                mv.addObject("jumpto", Common.getBasePath() + pagename);
                mv.setViewName("jumperclick");
                return mv;
            }
            // 读取当前考核信息
            // System.out.println("TeacherController.managevevaluationGET()itemid=" +
            // itemid);
            Vevaluation evaluation = svcb.getVevaluationById(itemid);
            mv.addObject("evaluation", evaluation);
            // 读取考核对应的分组所有成员
            List<Vlink_student2group> vlink_student2group = svc
                    .listVlink_student2groupByGroupId(evaluation.getStudent_group_id());

            // 读取考核已经提交的信息
            List<Integer> hadSubmitUserId = new ArrayList<>();// user_id
            HashMap<Integer, Integer> evrUseridScoreMap = new HashMap<>();// user_id,score
            {
                List<Evaluation_result> evaluation_records = svc
                        .listEvaluation_resultByEvaluationId(evaluation.getId());
                // mv.addObject("evaluation_records", evaluation_records);
                for (int i = 0; i < evaluation_records.size(); i++) {
                    hadSubmitUserId.add(evaluation_records.get(i).getStudent_id());
                    evrUseridScoreMap.put(evaluation_records.get(i).getStudent_id(),
                            evaluation_records.get(i).getScore());
                }
            }
            // 读取当前考核的题目数，难度系数
//			SELECT student_id,count(difficulty) as cd,sum(difficulty) as sd,sum(score_wish) as sw  FROM evaluation_result_record
//			where evaluation_id=2
//			group by subject_id,student_id

            List<EvprStatInfo> statlist = null;
            HashMap<Integer, Integer> evpNumberMap = new HashMap<>();
            HashMap<Integer, Integer> evDifficultyMap = new HashMap<>();
            HashMap<Integer, Integer> evScoreMap = new HashMap<>();
            {

                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Evaluation_result_record.EVALUATION_ID, evaluation.getId());
                params.put("groupName",
                        Evaluation_result_record.SUBJECT_ID + " , " + Evaluation_result_record.STUDENT_ID);
                statlist = svc.statEvprStatInfoByMap(params);

                System.out
                        .println("TeacherController.managevevaluationGET()statlist.size(已经提交考核人数)=" + statlist.size());
                for (int i = 0; i < statlist.size(); i++) {

                    // System.out.println("TeacherController.configEvaluationGET()"+statlist.get(i).toString());

                    evpNumberMap.put(statlist.get(i).getId(), statlist.get(i).getNum());
                    evDifficultyMap.put(statlist.get(i).getId(), statlist.get(i).getSum_difficulty());
                    evScoreMap.put(statlist.get(i).getId(), statlist.get(i).getSum_score_wish());
                }
            }

//				HashMap<String, Integer> evpNumberMap = svc.getEvpNumberMap();
//				HashMap<String, Integer> evDifficultyMap = svcEvpr.getEvDifficultyMap();
//				HashMap<String, Integer> evScoreMap = svcEvpr.getEvScoreMap();
            // 合并成页面所需信息

            List<app.entity.PuserEv> list = new ArrayList<>();
            for (int i = 0; i < vlink_student2group.size(); i++) {
                Integer student_id = vlink_student2group.get(i).getStudent_id();
                Integer score = evrUseridScoreMap.get(vlink_student2group.get(i).getStudent_id());
                Integer evpNumber = evpNumberMap.get(vlink_student2group.get(i).getStudent_id());// 总题量
                Integer difficulty = evDifficultyMap.get(vlink_student2group.get(i).getStudent_id());// 总难度
                Integer scoreWish = evScoreMap.get(vlink_student2group.get(i).getStudent_id());// 成绩

                String sno = vlink_student2group.get(i).getSno();
                String alias = vlink_student2group.get(i).getName();
                String studentGroupCode = vlink_student2group.get(i).getStudent_group_code();
                String mobile = vlink_student2group.get(i).getMobile();

                PuserEv it = new PuserEv(student_id, score, evpNumber, difficulty, scoreWish, sno, alias,
                        studentGroupCode, mobile);
                // System.out.println("TeacherController.configEvaluationGET()it=" +
                // it.toString());
                list.add(it);
            }

            mv.addObject("list", list);
            mv.setViewName(pagename);
        }
        return mv;
    }

    /***
     * // 读取一个学生在一次过程考核中的所有考题，显示 // 提供题目调整功能 // 提供评阅功能
     *
     * @param evid
     * @param studentid
     * @param request
     * @param session
     * @return
     */
    // TODO: GET-evaluation_point_record
    @RequestMapping(value = {"/evaluationreview", "/evaluationreview/",
            "/evaluationreview/index.html"}, method = RequestMethod.GET)
    public ModelAndView evaluationReviewGET(ModelAndView mv,
                                            @RequestParam(value = "evid", required = false) Integer evid,
                                            @RequestParam(value = "studentid", required = false) Integer studentid, HttpServletRequest request,
                                            HttpSession session) {

        System.out.println("TeacherController.evaluationReviewGET()evid=" + evid + ",studentid=" + studentid);

        mv.addObject("navitem", "Evaluation");// 菜单项显示当前项
        String pagename = "teacher/evaluationreview";

        Integer scoretotal = 0;
        Vevaluation evaluation = svcb.getVevaluationById(evid);
        Student student = svcb.getStudentById(studentid);

        mv.addObject("student", student);
        mv.addObject("pagetitle", evaluation.getSubject_name());
        mv.addObject("evaluation", evaluation);
        mv.addObject("begin", evaluation.getBegin_gmt_date());
        mv.addObject("end", evaluation.getEnd_gmt_date());

        // 保存数据，识别evaluation_record
        mv.addObject("studentid", studentid);
        {
            // 读取当前考核是否已经提交总分
            List<Evaluation_result> list = svcb.listEvaluation_resultByMap(new HashMap<String, Object>() {
                {
                    put(Evaluation_result.STUDENT_ID, studentid);
                    put(Evaluation_result.EVALUATION_ID, evid);
                }
            });
            if (list.size() == 1) {
                mv.addObject("evrecord", list.get(0));
            }
        }
        Student_group studentGroup = svcb.getStudent_groupById(evaluation.getStudent_group_id());
        List<MajorQuestions> mqlist = new ArrayList<>();// 当前页面项目列表
        String[] evcontentlist = evaluation.getContent().split(";");
        for (int i = 0; i < evcontentlist.length; i++) {

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

                scoretotal = scoretotal + evprlist.get(j).getScore();

                // 查找当前考题，已经上传的文件
                Evaluation_result_record evpr = evprlist.get(j);

                String uploadfolder = "WEB-INF\\evfiles\\ug" + evpr.getStudent_group_id() + "\\e"
                        + evpr.getEvaluation_id() + "\\u" + evpr.getSno() + "\\" + evpr.getId();
                String savefolder = request.getServletContext().getRealPath(uploadfolder);
                // System.out.println("TeacherController.evaluationReviewGET(查找当前考题，已经上传的文件)savefolder="
                // +
                // savefolder);

                String param = "&ug=" + evpr.getStudent_group_id() + "&evid=" + evpr.getEvaluation_id() + "&u="
                        + evpr.getSno() + "&evprid=" + evpr.getId();

                List<FileNameHash> filelist = new ArrayList<FileNameHash>();
                File file = new File(savefolder);
                int filesize = 0;
                if (file != null && file.isDirectory()) {
                    File[] dirFile = file.listFiles();
                    for (File f : dirFile) {
                        FileNameHash fhash = new FileNameHash();
                        fhash.setFilename(f.getName());
                        // fhash.setFilehash(DigestUtils.md5Hex(f.getName()));
                        fhash.setFilehash(DigestUtils.md5Hex(f.getName()) + param);
                        fhash.setFilesize(f.length());
                        filelist.add(fhash);
                        filesize += (int) f.length();
                    }
                }
                pevplist.add(new Pevpoint(evprlist.get(j), evtMap, filelist));
            }
            mqlist.add(new MajorQuestions(evtype, number, score, pevplist));
        }

        mv.addObject("mqlist", mqlist);
        mv.addObject("scoretotal", scoretotal);
        mv.setViewName(pagename);

        System.out.println("TeacherController.evaluationReviewGET()scoretotal=" + scoretotal);
        return mv;
    }

    // TODO: GET-evaluation_point
    @RequestMapping(value = {"/statevaluationpoint", "/statevaluationpoint/",
            "/statevaluationpoint/index.html"}, method = RequestMethod.GET)
    public ModelAndView statevaluation_pointGET(ModelAndView mv,
                                                @RequestParam(value = "kw", required = false) String kw,
                                                @RequestParam(value = "clrkw", required = false) String clrkw,
                                                @RequestParam(value = "operate", required = false) String operate,
                                                @RequestParam(value = "pn", required = false) Integer pn,
                                                @RequestParam(value = "ps", required = false) Integer ps,
                                                @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Evaluation");// 菜单项显示当前项
        String pagename = "teacher/statevaluationpoint";

        mv.addObject("pagetitle", "管理");
        mv.addObject("subtitle", "分页列表");
        mv.addObject("tabletitle", "管理");
        mv.addObject("pagename", pagename);// 当前页面的url

        List<StatEvaluationPoint> list = svc.statEvaluationPointByMap();
        mv.addObject("list", list);

        mv.setViewName(pagename);

        return mv;

    }

    // TODO: GET-vevaluation_point
    @RequestMapping(value = {"/evaluationpoint", "/evaluationpoint/",
            "/evaluationpoint/index.html"}, method = RequestMethod.GET)
    public ModelAndView evaluation_pointGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                            @RequestParam(value = "clrkw", required = false) String clrkw,
                                            @RequestParam(value = "operate", required = false) String operate,
                                            @RequestParam(value = "pn", required = false) Integer pn,
                                            @RequestParam(value = "ps", required = false) Integer ps,
                                            @RequestParam(value = "id", required = false) Integer id,
                                            @RequestParam(value = "sid", required = false) Integer subject_id,
                                            @RequestParam(value = "type", required = false) Integer type,

                                            HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);

        mv.addObject("navitem", "Evaluation");// 菜单项显示当前项
        String pagename = "teacher/evaluationpoint";

        mv.addObject("subject", svcb.getSubjectById(subject_id));
        mv.addObject("type", svcb.getEvaluation_typeById(type));

        Map<String, String> evtMap = new HashMap<String, String>();
        {
            List<Evaluation_type> evtlist = svcb.listEvaluation_typeOrderByAsc(Evaluation_type.ID);
            for (Evaluation_type evt : evtlist) {
                evtMap.put(evt.getId() + "", evt.getName());
            }
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            mv.addObject("subjectlist", svcb.listSubjectOrderByAsc(tpa.entity.Subject.ID));
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理考核题目");
                    mv.addObject("subtitle", "新增题目");
                    mv.addObject("formtitle", "新增题目");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());

                    System.out.println("TeacherController.evaluation_pointGET()sid=" + subject_id + ",type=" + type);

                    mv.setViewName("teacher/evaluationpoint_add");
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理考核题目");
                    mv.addObject("subtitle", "修改题目");
                    mv.addObject("formtitle", "修改题目");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Vevaluation_point it = svcb.getVevaluation_pointById(id);
                    mv.addObject("it", it);

                    Pevpoint pevpoint = new Pevpoint(it, evtMap);
                    mv.addObject("item", pevpoint);

                    mv.addObject("subject", svcb.getSubjectById(it.getSubject_id()));
                    mv.addObject("type", svcb.getEvaluation_typeById(pevpoint.getType()));

                    // mv.setViewName(pagename);
                    mv.setViewName("teacher/evaluationpoint_edit");

                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        // 视图需要定制删除方法！
                        rtv = svcb.removeEvaluation_pointById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumperclick");
                    }
                }
                break;
                case "hideitem":
                case "showitem": {

                    svcb.updateEvaluation_pointById(operate, id);

                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");
                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "sid=" + subject_id + "&type=" + type + "&";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vevaluation_point", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vevaluation_point");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            mv.addObject("pagetitle", "管理");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            PagerList<Vevaluation_point> pagerlist = svc.pagerListVevaluation_point(pagenumber, pagesize, keyword,
                    sqlin, Vevaluation_point.IS_ACTIVED, "desc", subject_id, type);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.Vevaluation_pointGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Vevaluation_point> pager = new Pager<Vevaluation_point>(page);
                // System.out.println("Test.Vevaluation_pointGET()pager=" + pager.toString());

                List<Vevaluation_point> evlist = pagerlist.getList();

                // TODO:解析题目

                List<Pevpoint> list = new ArrayList<Pevpoint>();
                for (int i = 0; i < evlist.size(); i++) {
                    Pevpoint pevpoint = new Pevpoint(evlist.get(i), evtMap);
                    list.add(pevpoint);
                }

                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", list);
                mv.setViewName(pagename);
            }
        }

        return mv;
    }

    @RequestMapping(value = {"/evaluationpoint", "/evaluationpoint/",
            "/evaluationpoint/index.html"}, method = RequestMethod.POST)
    public ModelAndView evaluation_pointPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/evaluationpoint";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String operate = request.getParameter("operate");// modifyform
        // --------编辑修改共有的部分---------
        Integer subject_id = Integer.parseInt(request.getParameter("subject_id"));
        Integer difficulty = Integer.parseInt(request.getParameter("difficulty"));
        Integer type = Integer.parseInt(request.getParameter("evType"));// 类型

        String evptitle = request.getParameter("evptitle1");

        // ----content----
        List<PevOneAnswer> answers = new ArrayList<PevOneAnswer>();
        {
            Integer itnum = Integer.parseInt(request.getParameter("itnum" + type));
            for (int i = 1; i <= itnum; i++) {
                PevOneAnswer oneAnswer = new PevOneAnswer();
                oneAnswer.setKey(i + "");
                oneAnswer.setAnswer(request.getParameter("evpa" + type + i));
                String checked = request.getParameter("tevpa" + type + i);
                System.out.println("ManageEvpoint.execute()checked=" + checked);
                if (checked != null && checked.equals("on")) {
                    oneAnswer.setCorrect(true);
                } else {
                    oneAnswer.setCorrect(false);
                }
                answers.add(oneAnswer);
            }
        }
        // ----content----
        // --------编辑修改共有的部分-结束---------
        String referer = request.getParameter("referer");

        switch (operate) {
            case "addform": {

                Pevpoint pevpoint = new Pevpoint(subject_id, true, type, difficulty, "", evptitle, answers);
                String content = pevpoint.toXMLString();
                Evaluation_point it = new Evaluation_point(subject_id, true, type, difficulty, content);

                // ----------保存前检查逻辑问题---------------
                if (StringUtils.isBlank(content)) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    svcb.saveEvaluation_point(it);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // Integer vevaluation_point_id =
                // Integer.parseInt(request.getParameter("id"));// Vevaluation_point.id
                Pevpoint pevpoint = new Pevpoint(id, subject_id, true, type, difficulty, "", evptitle, answers);
                String content = pevpoint.toXMLString();
                // ----------保存前检查逻辑上是否有冲突---------------
                svcb.updateEvaluation_pointById(subject_id, true, type, difficulty, content, new Date().getTime(), id);
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经修改！");
            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }
        // https://localhost/tpa/admin/evaluationpoint?sid=1&type=1
        mv.addObject("jumpto", Common.getBasePath() + "teacher/evaluationpoint?sid=" + subject_id + "&type=" + type);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO: GET-student_group
    @RequestMapping(value = {"/studentgroup", "/studentgroup/",
            "/studentgroup/index.html"}, method = RequestMethod.GET)
    public ModelAndView student_groupGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                         @RequestParam(value = "clrkw", required = false) String clrkw,
                                         @RequestParam(value = "operate", required = false) String operate,
                                         @RequestParam(value = "pn", required = false) Integer pn,
                                         @RequestParam(value = "ps", required = false) Integer ps,
                                         @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "BaseInfo");// 菜单项显示当前项
        String pagename = "teacher/studentgroup";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理学生分组信息");
                    mv.addObject("subtitle", "新增学生分组信息");
                    mv.addObject("formtitle", "学生分组信息，可以按学校分班，也可以相同班级合并");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理Student_group");
                    mv.addObject("subtitle", "修改Student_group");
                    mv.addObject("formtitle", "修改Student_group");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Student_group it = svcb.getStudent_groupById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);
                    mv.setViewName(pagename);
                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        rtv = svcb.removeStudent_groupById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumperclick");
                    }
                }
                break;
                case "hideitem":
                case "showitem": {
                    svcb.updateStudent_groupById(operate, id);
                    mv.addObject("jumpto", Common.getRefererUrl());
                    mv.setViewName("jumperatonce");
                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_student_group", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_student_group");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            mv.addObject("pagetitle", "管理");
            mv.addObject("subtitle", "分页列表");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Student_group.ID, 0);
            List<KeyAndValue> ordlist = new ArrayList<KeyAndValue>();
            ordlist.add(new KeyAndValue(Student_group.ID, "desc"));

            PagerList<Student_group> pagerlist = svcb.pagerListStudent_group(pagenumber, pagesize, keyword, sqlin,
                    params, ordlist);
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.Student_groupGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Student_group> pager = new Pager<Student_group>(page);
                // System.out.println("Test.Student_groupGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/studentgroup", "/studentgroup/",
            "/studentgroup/index.html"}, method = RequestMethod.POST)
    public ModelAndView student_groupPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/student_group";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        Boolean is_actived = StringUtils.isBlank(request.getParameter("is_actived")) ? false : true;
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {

                // String password_digest = DigestUtils.md5Hex(password);
                // Integer sortid = (int)
                // Math.round(svcb.maxStudent_group(Student_group.SORTID)) + 1;
                Student_group it = new Student_group(is_actived, code, name);

                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Student_group.CODE, code);
                params.put(Student_group.NAME, name);
                List<Student_group> list = svcb.listStudent_groupByMap(params);
                if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "你输入的信息已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    svcb.saveStudent_group(it);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;
            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Student_group.ID, id);
                List<Student_group> list = svcb.listStudent_groupByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svcb.updateStudent_groupById(is_actived, code, name, new Date().getTime(), id);

                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经修改！");

            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO: GET-vlink_student2group
    @RequestMapping(value = {"/student", "/student/", "/student/index.html"}, method = RequestMethod.GET)
    public ModelAndView vlink_student2groupGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                               @RequestParam(value = "clrkw", required = false) String clrkw,
                                               @RequestParam(value = "operate", required = false) String operate,
                                               @RequestParam(value = "pn", required = false) Integer pn,
                                               @RequestParam(value = "ps", required = false) Integer ps,
                                               @RequestParam(value = "sgid", required = false) Integer id, HttpServletRequest request,
                                               HttpSession session) {
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "BaseInfo");// 菜单项显示当前项
        String pagename = "teacher/student";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            // mv.addObject("student_grouplist",
            // svcb.listStudent_groupOrderByAsc(Student_group.ID));
            mv.addObject("student_grouplist", svcb.listStudent_groupByMap(new HashMap<String, Object>() {
                {
                    put(Student_group.IS_ACTIVED, true);
                }
            }));
            mv.addObject("student_groupmap", svcb.mapStudent_group());
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);
            switch (operate) {
                case "add": {
                    mv.addObject("pagetitle", "管理VIEW");
                    mv.addObject("subtitle", "新增VIEW");
                    mv.addObject("formtitle", "新增VIEW");
                    mv.addObject("pagename", pagename);
                    // 默认作者是当前用户
                    UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
                    mv.addObject("author", userSession.getLogin_name());
                    // 默认置顶3天
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 3);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String toptill_date = sdf.format(new Date(calendar.getTimeInMillis()));
                    mv.addObject("toptill_date", toptill_date);
                    // 默认头像
                    mv.addObject("wechat_headimgurl", Common.userheadimgurl);
                    mv.setViewName(pagename);
                }
                break;
                case "edit": {

                    mv.addObject("pagetitle", "管理Vlink_student2group");
                    mv.addObject("subtitle", "修改Vlink_student2group");
                    mv.addObject("formtitle", "修改Vlink_student2group");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());

                    // 读取当前已有信息
                    Vlink_student2group it = svcb.getVlink_student2groupById(id);
                    // 修正信息：如默认头像
                    mv.addObject("it", it);
                    mv.setViewName(pagename);
                }
                break;
                case "delete": {

                    boolean rtv = false;
                    try {
                        // 视图需要定制删除方法！
                        // rtv = svc.removeVlink_student2groupById(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rtv) {

                        mv.addObject("message", "信息已经删除！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumper");
                    } else {

                        mv.addObject("message", "删除操作被外星人强行终止！");
                        mv.addObject("jumpto", Common.getRefererUrl());
                        mv.setViewName("jumperclick");
                    }
                }
                break;

                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vlink_student2group", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vlink_student2group");
                keyword = null;
            }

            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();

            if (!StringUtils.isBlank(request.getParameter("student_id"))) {
                sqlin.put(Vlink_student2group.STUDENT_ID + "_id", request.getParameter("student_id"));
                mv.addObject(Vlink_student2group.STUDENT_ID + "_map",
                        Util.splitToInteger(request.getParameter("student_id"), ","));
                qs += Vlink_student2group.STUDENT_ID + "=" + request.getParameter("student_id") + "&";
            }

            if (!StringUtils.isBlank(request.getParameter("student_group_id"))) {
                sqlin.put(Vlink_student2group.STUDENT_GROUP_ID + "_id", request.getParameter("student_group_id"));
                mv.addObject(Vlink_student2group.STUDENT_GROUP_ID + "_map",
                        Util.splitToInteger(request.getParameter("student_group_id"), ","));
                qs += Vlink_student2group.STUDENT_GROUP_ID + "=" + request.getParameter("student_group_id") + "&";
            }

            mv.addObject("pagetitle", "管理");
            mv.addObject("subtitle", "名单");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url

            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            // 读取分组信息
            mv.addObject("studentGroup", svcb.getStudent_groupById(id));

            PagerList<Vlink_student2group> pagerlist = svc.pagerListVlink_student2group(id, pagenumber, pagesize,
                    keyword, sqlin, Vlink_student2group.ID, "asc");
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.Vlink_student2groupGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Vlink_student2group> pager = new Pager<Vlink_student2group>(page);
                // System.out.println("Test.Vlink_student2groupGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
        }

        return mv;

    }

    @RequestMapping(value = {"/studentimport", "/studentimport/",
            "/studentimport/index.html"}, method = RequestMethod.POST)
    public ModelAndView vlink_student2groupPOSTImportFormXls(ModelAndView mv, HttpServletRequest request,
                                                             HttpSession session, @RequestParam("excelfile") MultipartFile uploadfile) throws Exception {
        // http://localhost:8080/tpa/admin/student?operate=add&sgid=16
        Integer student_group_id = Integer.parseInt(request.getParameter("sgid"));
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));

        if (uploadfile.isEmpty()) {
            // 输入内容中，有些内容要求值唯一！
            mv.addObject("message", "请选择从教务处下载的平时成绩册！");
            mv.addObject("jumpto", Common.getBasePath() + "teacher/student?operate=add&sgid=" + student_group_id);
            mv.setViewName("jumperback");
            return mv;
        } else {

            String path = request.getServletContext().getRealPath(Common.uploadFileSaveFolder);
            String suffix = Util.getSuffix(uploadfile.getOriginalFilename());
            String filename = "stu_" + new Date().getTime() + suffix;
            File savefile = new File(path + "\\" + filename);
            uploadfile.transferTo(savefile);
            System.out.println(this.getClass().getName() + ".ajaxformupload()文件已经保存到" + path + "\\" + filename);

            // 导入
            String msg = "";
            int numberNewStudent = 0;
            int numberNewLink = 0;
            int numberError = 0;
            int numberRepeat = 0;

            Workbook rwb = Workbook.getWorkbook(savefile);
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();// 得到列数
            int rows = rs.getRows();// 得到行数

            System.out.println("clos:" + clos + ", rows:" + rows);
            for (int i = 1; i < rows; i++) {
                // 第一个是列数，第二个是行数
                // String xm = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
                Student it = null;
                String login_name = rs.getCell(7, i).getContents();// 学号
                if (StringUtils.isBlank(login_name)) {
                    System.out.println("TeacherController.usergroupPOST()出现空白学号，自动跳过" + login_name);
                    // 学号信息有误！
                    numberError++;

                } else {
                    // ----------保存前检查逻辑问题---------------
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put(Student.SNO, login_name);// 按学号查找
                    List<Student> list = svcb.listStudentByMap(params);
                    if (list.size() == 0) {
                        // 没有出现相同学号，该学生信息空缺
                        String login_password = rs.getCell(7, i).getContents();// 学号
                        String login_name_digest = DigestUtils.md5Hex(login_name);
                        String login_password_digest = DigestUtils.md5Hex(login_password);
                        Integer user_id = userSession.getId();
                        Boolean is_frozen = false;
                        String wechat_openid = "";
                        String wechat_nickname = "";
                        String wechat_headimgurl = Common.userheadimgurl;
                        String authority_info = "student";
                        // String qq = "";
                        // String email = "";
                        String phone = rs.getCell(12, i).getContents();
                        String usergroup_code = "20" + rs.getCell(5, i).getContents();
                        String studentname = rs.getCell(8, i).getContents();
                        Integer login_method = 2;// 状态：用户名密码登录0,微信登录1,两者同时可用2

                        it = new Student(login_name, login_name, login_name_digest, login_password,
                                login_password_digest, studentname, "", true, phone, "", wechat_openid, wechat_nickname,
                                wechat_headimgurl, login_method, usergroup_code);

                        System.out.println("TeacherController.managevuserPOST()it=" + it.toString());
                        svcb.saveStudent(it);

                        // 保存后，id自动保存到it中
                        // 新建的记录，肯定没有link记录，直接添加link记录
                        // 绑定到班级
                        Link_student2group link_user_group = new Link_student2group(it.getId(), student_group_id);
                        svcb.saveLink_student2group(link_user_group);

                        svcb.saveSyslog(new Syslog(
                                userSession.getId(), userSession.getAlias(), Common.getIP(), 0, "增加了学生/学生分组link:"
                                + it.getName() + ",userid=" + it.getId() + ",usergroupid=" + student_group_id,
                                "", false));
                        // 学生不添加角色对应表

                        numberNewStudent++;

                        msg += "<br>学号：" + login_name + "，信息添加成功！";

                    } else {
                        // 出现了学号
                        it = list.get(0);

                        // 已有学生信息，查询link表中是否已经有该分组记录
                        Map<String, Object> params_link = new HashMap<String, Object>();
                        params_link.put(Link_student2group.STUDENT_ID, it.getId());
                        params_link.put(Link_student2group.STUDENT_GROUP_ID, student_group_id);
                        List<Link_student2group> linkuu = svcb.listLink_student2groupByMap(params_link);

                        // 当前用户班级链接不存在
                        if (linkuu.size() == 0) {
                            Link_student2group link_user_group = new Link_student2group(it.getId(), student_group_id);
                            svcb.saveLink_student2group(link_user_group);

                            svcb.saveSyslog(new Syslog(userSession.getId(), userSession.getAlias(), Common.getIP(), 0,
                                    "增加了学生分组link:" + it.getName() + ",userid=" + it.getId() + ",usergroupid="
                                            + student_group_id,
                                    "", false));
                            // 只是添加link
                            numberNewLink++;
                        } else {
                            // 已有信息，并且已经加入当前班级
                            numberRepeat++;
                        }

                        msg += "<br>学号：" + login_name + "，基础信息已经存在，添加到当前班级成功！";
                    }
                }
            }
            msg = "<br>新添加信息：" + numberNewStudent + "个，<br>已有信息加入当前班级" + numberNewLink + "个<br>重复出现，略过" + numberRepeat
                    + "个" + msg;

            mv.addObject("message", msg);
            mv.addObject("jumpto", Common.getBasePath() + "teacher/student?sgid=" + student_group_id);
            mv.setViewName("jumperclick");
            return mv;
        }

    }

    @RequestMapping(value = {"/student", "/student/", "/student/index.html"}, method = RequestMethod.POST)
    public ModelAndView vlink_student2groupPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/vlink_student2group";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        Integer student_id = Integer.parseInt(request.getParameter("student_id"));
        Integer student_group_id = Integer.parseInt(request.getParameter("student_group_id"));
        Boolean student_is_actived = StringUtils.isBlank(request.getParameter("student_is_actived")) ? false : true;
        Boolean student_group_is_actived = StringUtils.isBlank(request.getParameter("student_group_is_actived")) ? false
                : true;
        String sno = request.getParameter("sno");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String qq = request.getParameter("qq");
        String classcode = request.getParameter("classcode");
        Integer login_method = Integer.parseInt(request.getParameter("login_method"));
        String student_group_name = request.getParameter("student_group_name");
        String student_group_code = request.getParameter("student_group_code");
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {
            case "addform": {

                // String password_digest = DigestUtils.md5Hex(password);
                // Integer sortid = (int)
                // Math.round(svcb.maxVlink_student2group(Vlink_student2group.SORTID)) + 1;
                Vlink_student2group it = new Vlink_student2group(student_id, student_group_id, student_is_actived,
                        student_group_is_actived, sno, name, email, mobile, qq, classcode, login_method, student_group_name,
                        student_group_code);

                // ----------保存前检查逻辑问题---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                // params.put(Vlink_student2group.ID, id);
                List<Vlink_student2group> list = svcb.listVlink_student2groupByMap(params);
                if (StringUtils.isBlank("") || StringUtils.isBlank("")) {

                    mv.addObject("message", "你输入的信息没有通过星际外交部的严格审核，请重新输入！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else if (list.size() > 0) {

                    // 输入内容中，有些内容要求值唯一！
                    mv.addObject("message", "你输入的信息已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {

                    // 视图需要定制新增方法！
                    // svcb.saveVlink_student2group(it);
                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经保存！");
            }
            break;

            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);

                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Vlink_student2group.ID, id);
                List<Vlink_student2group> list = svcb.listVlink_student2groupByMap(params);
                if (list.size() > 1 || (list.size() == 1 && list.get(0).getId() != id)) {

                    mv.addObject("message", "你输入的手机号码已经被外星人抢先注册！");
                    mv.addObject("jumpto", Common.getBasePath() + pagename);
                    mv.setViewName("jumperback");
                    return mv;
                } else {
                    svcb.updateVlink_student2groupById(student_id, student_group_id, student_is_actived,
                            student_group_is_actived, sno, name, email, mobile, qq, classcode, login_method,
                            student_group_name, student_group_code, id);

                }
                // ----------保存前检查逻辑问题结束------------

                mv.addObject("message", "信息已经修改！");

            }
            break;

            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }

        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO: GET-vexperiment_record
    @RequestMapping(value = {"/experimentrecord", "/experimentrecord/",
            "/experimentrecord/index.html"}, method = RequestMethod.GET)
    public ModelAndView vexperiment_recordGET(ModelAndView mv, @RequestParam(value = "kw", required = false) String kw,
                                              @RequestParam(value = "clrkw", required = false) String clrkw,
                                              @RequestParam(value = "operate", required = false) String operate,
                                              @RequestParam(value = "pn", required = false) Integer pn,
                                              @RequestParam(value = "ps", required = false) Integer ps,
                                              @RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpSession session) {
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        logger.info(this.getClass().getName());
        System.out.println(this.getClass().getName() + ",operate=" + operate + ",id=" + id + ",pn=" + pn + ",ps=" + ps
                + ",kw=" + kw + ",clrkw=" + clrkw);
        mv.addObject("navitem", "Experiment");// 菜单项显示当前项
        String pagename = "teacher/experimentrecord";
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (StringUtils.isBlank(operate) || operate.equals("add") || operate.equals("edit")) {
            // 新建、编辑、列表的条件筛选
            // 用于制作彩色的标签
            mv.addObject("tagpostfixlist", Common.tagPostfix);
            Map<String, Object> curriculumParam = new HashMap<String, Object>() {
                {
                    put(Vcurriculum.USER_ID, userSession.getId());//Vcurriculum.user_id = user.id
                }
            };
            //List<Vcurriculum> vcurr = svcb.listVcurriculumByMap(curriculumParam);//返回的是Vcurriculum对象的集合
            mv.addObject("curriculumlist", svcb.listVcurriculumByMap(curriculumParam));//返回的是Vcurriculum对象的集合
            mv.addObject("curriculummap", svcb.mapCurriculum(curriculumParam));//map的key=curriculum.id value=curriculum.semester
            // SELECT distinct student_group_id,student_group_name FROM
            // tpa.vlink_experiment_curriculum_min where user_id=1
            // TODO:统计当前教师开设了课的班级
            mv.addObject("student_grouplist", svcb.listStudent_groupOrderByAsc(Student_group.ID));
            mv.addObject("student_groupmap", svcb.mapStudent_group());//map的key=Student_group.id value=Student_group.code(班号)
            // TODO:统计当前教师开设了的实验课
            // mv.addObject("experimentlist", svcb.listExperimentOrderByAsc(Experiment.ID));
            // mv.addObject("experimentmap", svcb.mapExperiment());
            mv.addObject("reviewstatelist", new ArrayList<Idname>() {
                {
                    add(new Idname(1, "已评"));
                    add(new Idname(2, "待评"));
                }
            });
        }
        // 新增和编辑，列表页面筛选也需要使用，页面部分都有一些预先读取的内容
        if (!StringUtils.isBlank(operate)) {
            // 操作不为空，增删改
            mv.addObject("operate", operate);//operate为edit
            Vexperiment_record_mini verm = svcb.getVexperiment_record_miniById(id);
            Integer experiment_id = verm.getExperiment_id();
            Experiment exp = svcb.getExperimentById(experiment_id);
            mv.addObject("exp", exp);
            switch (operate) {
                // edit作为批阅使用
                case "edit": {
                    mv.addObject("pagetitle", "评阅实验报告");
                    mv.addObject("subtitle", "Review the experimental report");
                    mv.addObject("formtitle", "实验报告");
                    mv.addObject("pagename", pagename);
                    mv.addObject("referer", Common.getRefererUrl());
                    // 读取当前已有信息
                    Vexperiment_record it = svcb.getVexperiment_recordById(id);
                    mv.addObject("it", it);
                    if (it == null) {
                        mv.addObject("message", "你所请求查看的实验报告，已经被外星人删除！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumperclick");
                    } else if (!it.getSubject_is_actived() || !it.getCurriculum_is_actived()) {
                        // 筛选条件1,subject_is_displayed,科目可用
                        // 筛选条件2,curriculum_is_valid,课程可用
                        mv.addObject("message", "你所请求查看的实验报告，已经被外星人关闭！");
                        mv.addObject("jumpto", Common.getBasePath() + pagename);
                        mv.setViewName("jumperclick");
                    } else {
                        // 读取学生上传的所有文件
                        String uploadfolder = "WEB-INF\\expfiles\\s" + it.getSubject_id() + "\\e" + it.getExperiment_id()
                                + "\\u" + it.getSno() + "\\";
                        // String savefolder1 =request.getServletContext().getRealPath("/");
                        String savefolder = request.getServletContext().getRealPath(uploadfolder);
                        System.out.println("TeacherController.vexperiment_recordGET()savefolder=" + savefolder);
                        // 读取文件列表
                        File file = new File(savefolder);
                        Integer filesize = 0;
                        List<FileNameHash> filelist = new ArrayList<FileNameHash>();
                        if (file.isDirectory()) {
                            File[] dirFile = file.listFiles();
                            for (File f : dirFile) {
                                FileNameHash fit = new FileNameHash();
                                fit.setFilename(f.getName());
                                fit.setFilehash(DigestUtils.md5Hex(f.getName()));
                                fit.setFilesize(f.length());
                                filelist.add(fit);
                                filesize += (int) f.length();
                            }
                        }
                        mv.addObject("filesize", filesize);
                        mv.addObject("filelist", filelist);
                    }
                    mv.setViewName(pagename);
                }
                break;
                default: {

                }
                break;
            }
        } else {
            // 操作为空,默认为搜索,显示列表
            String qs = "";
            Integer pagenumber = Common.recordToSession(session, "pagenumber", pn + "", 1);
            Integer pagesize = Common.recordToSession(session, "pagesize", ps + "", 10);
            String keyword = Util.replaceSqlChar(kw);
            keyword = Common.recordToSession(session, "keyword_vexperiment_record", keyword, "");
            // 清除搜索条件
            if (!StringUtils.isBlank(clrkw)) {
                session.removeAttribute("keyword_vexperiment_record");
                keyword = null;
            }
            // 读取url中是否包含筛选条件
            Map<String, String> sqlin = new HashMap<String, String>();
            if (!StringUtils.isBlank(request.getParameter("reviewstate_id"))) {// 是否评阅
                sqlin.put(Vexperiment_record_mini.REVIEWSTATE + "_id", request.getParameter("reviewstate_id"));
                mv.addObject(Vexperiment_record_mini.REVIEWSTATE + "_map",
                        Util.splitToInteger(request.getParameter("reviewstate_id"), ","));
                qs += Vexperiment_record_mini.REVIEWSTATE + "=" + request.getParameter("reviewstate_id") + "&";
            }
            if (!StringUtils.isBlank(request.getParameter("curriculum_id"))) {// 课程代码
                sqlin.put(Vexperiment_record_mini.CURRICULUM_ID + "_id", request.getParameter("curriculum_id"));
                mv.addObject(Vexperiment_record_mini.CURRICULUM_ID + "_map",
                        Util.splitToInteger(request.getParameter("curriculum_id"), ","));
                qs += Vexperiment_record_mini.CURRICULUM_ID + "=" + request.getParameter("curriculum_id") + "&";
            }
            System.out.println("Admin.vexperiment_recordGET()student_group_id=" + request.getParameter("student_group_id"));
            if (!StringUtils.isBlank(request.getParameter("student_group_id"))) {// 学生分组
                sqlin.put(Vexperiment_record_mini.STUDENT_GROUP_ID + "_id", request.getParameter("student_group_id"));
                mv.addObject(Vexperiment_record_mini.STUDENT_GROUP_ID + "_map",
                        Util.splitToInteger(request.getParameter("student_group_id"), ","));
                qs += Vexperiment_record_mini.STUDENT_GROUP_ID + "=" + request.getParameter("student_group_id") + "&";
            }
            if (!StringUtils.isBlank(keyword)) {
                mv.addObject("kw", keyword);
                qs += "kw=" + keyword + "&";
            }
            // 在列表中需要筛选的条件
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Vexperiment_record_mini.USER_ID, userSession.getId());// 只读取当前教师所属的
            PagerList<Vexperiment_record_mini> pagerlist = svc.pagerListVexperiment_record_mini(pagenumber, pagesize,
                    keyword, sqlin, params, Vexperiment_record_mini.SNO, "asc");
            if (pagerlist.getPager().getRecordsSize() == 0 && pagerlist.getPager().getPages() != 0
                    && pagerlist.getPager().getCurrent() > pagerlist.getPager().getPages()) {
                String url = Common.getBasePath() + pagename + "?" + qs + "pn=" + pagerlist.getPager().getPages();
                mv.addObject("jumpto", url);
                // System.out.println("Test.Vexperiment_recordGET()url=" + url);
                mv.setViewName("jumperatonce");
            } else {
                // Pager<Vexperiment_record> pager = new Pager<Vexperiment_record>(page);
                // System.out.println("Test.Vexperiment_recordGET()pager=" + pager.toString());
                mv.addObject("qs", qs);
                mv.addObject("pager", pagerlist.getPager());
                mv.addObject("list", pagerlist.getList());
                mv.setViewName(pagename);
            }
            mv.addObject("pagetitle", "评阅实验报告");
            mv.addObject("subtitle", "Review the experimental report");
            mv.addObject("tabletitle", "管理");
            mv.addObject("pagename", pagename);// 当前页面的url
            mv.addObject("now", new Date().getTime());
        }
        return mv;
    }

    @RequestMapping(value = {"/experimentrecord/download", "/experimentrecord/download/"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> experimentrecordDownload(HttpServletRequest request,
                                                           @RequestParam("filehash") String filehash, @RequestParam("s") Integer subjectid,
                                                           @RequestParam("e") Integer experimentid, @RequestParam("u") Integer username, Model model) throws Exception {
        // 读取学生上传的所有文件
        String uploadfolder = "WEB-INF\\expfiles\\s" + subjectid + "\\e" + experimentid + "\\u" + username + "\\";
        String savefolder = request.getServletContext().getRealPath(uploadfolder);
        System.out.println("TeacherController.experimentrecordDownload()" + savefolder);
        // 读取文件列表
        String filename = "";
        File targetfile = null;
        File files = new File(savefolder);
        if (files.isDirectory()) {
            File[] dirFile = files.listFiles();
            for (File f : dirFile) {
                if (filehash.equals(DigestUtils.md5Hex(f.getName()))) {
                    targetfile = f;
                    filename = f.getName();
                }
            }
        }
        System.out.println("TeacherController.download()filename=" + filename);
        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        // 通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 201 HttpStatus.CREATED
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetfile), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/experimentrecord", "/experimentrecord/",
            "/experimentrecord/index.html"}, method = RequestMethod.POST)
    public ModelAndView experiment_recordPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {
        System.out.println(this.getClass().getName() + "POST");
        String pagename = "teacher/experimentrecord";
        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operate = request.getParameter("operate");
        // --------编辑修改共有的部分---------
        Integer score = Integer.parseInt(request.getParameter("score"));
        // --------编辑修改共有的部分-结束---------
        // Integer create_userid = userSession.getId();
        // 来源页面
        String referer = request.getParameter("referer");
        switch (operate) {

            case "editform": {
                Integer id = Integer.parseInt(request.getParameter("id"));
                // update_gmt = sdf.parse(request.getParameter("update_gmt_date")).getTime();
                // String password_digest = DigestUtils.md5Hex(password);
                // ----------保存前检查逻辑上是否有冲突---------------
                // 如：用户名必须唯一，手机号必须唯一
                svc.updateExperiment_recordById(score, "已评", 1, new Date().getTime(), id);
                // ----------保存前检查逻辑问题结束------------
                mv.addObject("message", "信息已经修改！");
                // 自动跳转到下一个相同实验的待评报告
                // orderid 和id是同一值
                Vexperiment_record_mini item = svcb.getVexperiment_record_miniById(id);
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(Vexperiment_record_mini.CURRICULUM_ID, item.getCurriculum_id());
                params.put(Vexperiment_record_mini.EXPERIMENT_ID, item.getExperiment_id());
                params.put(Vexperiment_record_mini.SCORESCRIPT, "待评");
                System.out.println("TeacherController.experiment_recordPOST()读取下一个");
                List<Vexperiment_record_mini> nextitem = svcb.listVexperiment_record_miniByMap(params);
                if (nextitem.size() > 0) {
                    Integer nextitemid = nextitem.get(0).getId();
                    referer = "teacher/experimentrecord?operate=edit&id=" + nextitemid;
                } else {
                    referer = "teacher/experimentrecord?reviewstate_id=2&curriculum_id=" + item.getCurriculum_id()
                            + "&student_group_id=" + item.getStudent_group_id();
                }
            }
            break;
            default:
                System.out.println(this.getClass().getName() + "没有对应的operate=" + operate);
                break;
        }
        mv.addObject("jumpto", referer);
        mv.setViewName("jumper");
        return mv;
    }

    /***
     * 统计实验报告提交情况
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/statexprecordsubmit", "/statexprecordsubmit/",
            "/statexprecordsubmit/index.html"}, method = RequestMethod.GET)
    public ModelAndView statexprecordsubmitGET(ModelAndView mv,
                                               @RequestParam(value = "cid", required = true) Integer curriculumid, HttpServletRequest request,
                                               HttpSession session) {

        mv.addObject("pagetitle", "统计实验报告");
        mv.addObject("subtitle", "Statistical Experiment Report");
        mv.addObject("navitem", "StatExperimentRecord");// 菜单项
        mv.setViewName("teacher/statexprecordsubmit");

        // 使用shiro控制权限后

        UserSession userSession = (UserSession) (session.getAttribute(Common.sessionUser));// 当前教师

        System.out.println("TeacherController.statexprecordsubmitGET()curriculumid=" + curriculumid);

        if (userSession.getAuthorizedType().equals("student")) {

        } else {
            // 教师
            System.out.println("TeacherController.statexprecordsubmitGET()教师统计实验报告结果");
            // SELECT * FROM tpa.vexperiment_record_mini where curriculum_id=31

            // 读取指定课程，所有学生的实验记录
            List<Vexperiment_record_mini> exprecords = svcb
                    .listVexperiment_record_miniByMap(new HashMap<String, Object>() {
                        {
                            put(Vexperiment_record_mini.CURRICULUM_ID, curriculumid);
                        }
                    });

            System.out.println("TeacherController.statexprecordsubmitGET()exprecords.size=" + exprecords.size());

            // 准备送到页面的数据
            Map<String, Object> smap = new HashMap<String, Object>();
            Map<String, Object> eridmap = new HashMap<String, Object>();
            for (int i = 0; i < exprecords.size(); i++) {
                smap.put(exprecords.get(i).getStudent_id() + "" + exprecords.get(i).getExperiment_id(),
                        exprecords.get(i).getScore());
                eridmap.put(exprecords.get(i).getStudent_id() + "" + exprecords.get(i).getExperiment_id(),
                        exprecords.get(i).getId() + "");
            }
            mv.addObject("smap", smap);
            mv.addObject("eridmap", eridmap);
            // 读取当前课程的所有实验
            // SELECT * FROM tpa.vlink_experiment_curriculum where curriculum_id=31 order by
            // opening
            List<Vlink_experiment_curriculum> curriculums = svcb
                    .listVlink_experiment_curriculumByMap(new HashMap<String, Object>() {
                        {
                            put(Vlink_experiment_curriculum.CURRICULUM_ID, curriculumid);
                        }
                    });
            mv.addObject("curriculums", curriculums);
            System.out.println("TeacherController.statexprecordsubmitGET()curriculums.size=" + curriculums.size());
            // 读取当前课程的所有学生名单
            // SELECT distinct user_id FROM tpa.vlink_user_usergroup_curriculum_experiment
            // where curriculum_id=31
            // SELECT * FROM tpa.user where id in (
            // SELECT distinct user_id FROM tpa.vlink_user_usergroup_curriculum_experiment
            // where curriculum_id=31
            // ) order by username

            List<Student> students = svc.listStudentByCurriculumid(curriculumid);
            System.out.println("TeacherController.statexprecordsubmitGET()students.size=" + students.size());

            mv.addObject("students", students);

        }
        return mv;
    }

    /****
     * 弹窗评阅一道小题
     */
    // TODO evprreview
    @RequestMapping(value = {"/evprreview", "/evprreview/", "/evprreview/ndex.html"}, method = RequestMethod.GET)
    public ModelAndView evprshowGET(ModelAndView mv, HttpServletRequest request, HttpSession session) {

        String pagename = "teacher/evprreview";
        Integer evprid = Integer.parseInt(request.getParameter("evprid"));
        mv.addObject("pagetitle", "考核页面");
        mv.addObject("evprid", evprid);
        mv.addObject("tagpostfixlist", Common.tagPostfix);

        Evaluation_result_record evpr = svcb.getEvaluation_result_recordById(evprid);
        System.out.println("Student.evprshowGET(),evprid=" + evprid + ",type=" + evpr.getType());

        // 返回考核点类型编号和名称的映射图
        Map<Integer, String> evtMap = svcb.mapEvaluation_type();
        Pevpoint item = new Pevpoint(evpr, evtMap);

        System.out.println("TeacherController.evprshowGET()item=" + item.toString());

        String uploadfolder = "WEB-INF\\evfiles\\ug" + evpr.getStudent_group_id() + "\\e" + evpr.getEvaluation_id()
                + "\\u" + evpr.getSno() + "\\" + evprid;
        String savefolder = request.getServletContext().getRealPath(uploadfolder);
        System.out.println("Student.evprshowGET()savefolder=" + savefolder);

        // 共用部分
        Integer filesize = 0;
        List<FileNameHash> filelist = new ArrayList<FileNameHash>();

        // 显示题目内容
        switch (evpr.getType()) {
            case 5:
            case 6: {
                if (item.getAnswers().get(0).getCorrect()) {
                    // 允许上传文件
                    System.out.println("Student.evprshowGET()允许上传文件");
                    String param = "&ug=" + evpr.getStudent_group_id() + "&evid=" + evpr.getEvaluation_id() + "&u="
                            + evpr.getSno() + "&evprid=" + evpr.getId();

                    // 读取文件列表
                    File file = new File(savefolder);
                    filesize = 0;
                    if (file != null && file.isDirectory()) {
                        File[] dirFile = file.listFiles();
                        for (File f : dirFile) {

                            FileNameHash fhash = new FileNameHash();
                            fhash.setFilename(f.getName());
                            fhash.setFilehash(DigestUtils.md5Hex(f.getName()) + param);
                            fhash.setFilesize(f.length());
                            filelist.add(fhash);
                            filesize += (int) f.length();
                        }
                    }
                }
            }
            break;
            default:
                break;
        }

        mv.addObject("item", item);
        mv.addObject("filelist", filelist);
        mv.addObject("pagename", pagename);
        mv.setViewName(pagename);

        return mv;
    }

    /**
     * 提交一个小题得分
     *
     * @param mv
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/evprreview", "/evprreview/", "/evprreview/ndex.html"}, method = RequestMethod.POST)
    public ModelAndView evprreviewPOST(ModelAndView mv, HttpServletRequest request, HttpSession session) {

        String pagename = "teacher/evprreview";
        Integer score = Integer.parseInt(request.getParameter("score"));
        Integer evprid = Integer.parseInt(request.getParameter("evprid"));
        System.out.println("TeacherController.evprreviewPOST(提交一个小题得分)" + "score=" + score + ",evprid=" + evprid);

        svc.updateEvaluation_result_recordById(score, true, new Date().getTime(), evprid);

        mv.addObject("message", "信息已经修改！");
        mv.addObject("jumpto", Common.getBasePath() + pagename);
        mv.setViewName("closer");

        return mv;

    }

    // 下载过程考核时，学生的上传的文件
    @RequestMapping(value = {"/evrdownload", "/evrdownload/"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> evrDownload(HttpServletRequest request) throws Exception {

        System.out.println("TeacherController.evaluationReviewDownload()");

        // http://localhost:8080/admin/evrdownload/?filehash=ec04a64aafa2544816aa349efc629246&
        // ug=9&evid=1&u=2014110612&evprid=20655
        String filehash = request.getParameter("filehash");
        Integer ugid = Integer.parseInt(request.getParameter("ug"));
        Integer evid = Integer.parseInt(request.getParameter("evid"));
        Integer username = Integer.parseInt(request.getParameter("u"));
        Integer evprid = Integer.parseInt(request.getParameter("evprid"));

        // 读取学生上传的所有文件
        String uploadfolder = "WEB-INF\\evfiles\\ug" + ugid + "\\e" + evid + "\\u" + username + "\\" + evprid;
        String savefolder = request.getServletContext().getRealPath(uploadfolder);
        System.out.println("TeacherController.evaluationReviewDownload()" + savefolder);
        // 读取文件列表
        String filename = "";
        File targetfile = null;
        File files = new File(savefolder);
        if (files.isDirectory()) {
            File[] dirFile = files.listFiles();
            for (File f : dirFile) {
                if (filehash.equals(DigestUtils.md5Hex(f.getName()))) {
                    targetfile = f;
                    filename = f.getName();
                }
            }
        }
        System.out.println("TeacherController.download()filename=" + filename);

        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        // 通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 201 HttpStatus.CREATED
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetfile), headers, HttpStatus.CREATED);
    }

    /***
     * 提交已经评阅后的考核总分
     *
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/evaluationrecord", "/evaluationrecord/",
            "/evaluationrecord/index.html"}, method = RequestMethod.POST)
    public ModelAndView evaluation_recordPOST(ModelAndView mv, HttpServletRequest request, HttpSession session)
            throws Exception {

        System.out.println(this.getClass().getName() + "POST");

        // --------编辑修改共有的部分---------
        Integer score = Integer.parseInt(request.getParameter("score"));
        Integer evrid = Integer.parseInt(request.getParameter("evrid"));
        // Integer evid = Integer.parseInt(request.getParameter("evid"));
        // Integer studentid = Integer.parseInt(request.getParameter("studentid"));

        svc.updateEvaluation_resultById(score, true, new Date().getTime(), evrid);
        mv.addObject("message", "信息已经修改！");

        Evaluation_result evr = svcb.getEvaluation_resultById(evrid);

        // 提交一个成绩后，应该切换到下一个没有评阅
        List<Evaluation_result> nextlist = svcb.listEvaluation_resultByMap(new HashMap<String, Object>() {
            {
                put(Evaluation_result.IS_REVIEWED, false);
                put(Evaluation_result.EVALUATION_ID, evr.getEvaluation_id());
            }
        });

        if (nextlist.size() > 0) {
            // 还有未评阅的
            mv.addObject("jumpto", Common.getBasePath() + "teacher/evaluationreview/?evid=" + evr.getEvaluation_id()
                    + "&studentid=" + nextlist.get(0).getStudent_id());
        } else {
            // 评阅完成
            mv.addObject("jumpto", Common.getBasePath() + "teacher/configevaluation?id=" + evr.getEvaluation_id());
        }

        mv.setViewName("jumper");
        return mv;
    }

    // TODO 保存学生一道试题的文件
    @RequestMapping(value = {"/evprupload/{evprid}", "/evprupload/{evprid}/",
            "/evprupload/{evprid}/index.html"}, method = RequestMethod.POST)
    public ModelAndView evpruploadPOST(ModelAndView mv, @PathVariable Integer evprid, HttpServletRequest request,
                                       HttpSession session) {

        System.out.println("TeacherController.evpruploadPOST()evprid=" + evprid);
        Evaluation_result_record evpr = svcb.getEvaluation_result_recordById(evprid);

        // 返回考核点类型编号和名称的映射图
        Map<Integer, String> evtMap = svcb.mapEvaluation_type();
        Pevpoint item = new Pevpoint(evpr, evtMap);
        // 查找文件

        String uploadfolder = "WEB-INF\\evfiles\\ug" + evpr.getStudent_group_id() + "\\e" + evpr.getEvaluation_id()
                + "\\u" + evpr.getSno() + "\\" + evprid;
        String savefolder = request.getServletContext().getRealPath(uploadfolder);

        MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mrequest.getFile("file");
        System.out.println("StudentController.evprshowPOST()准备保存文件！");
        System.out.println("StudentController.evprshowPOST()file.getOriginalFilename=" + file.getOriginalFilename());
        System.out.println("StudentController.evprshowPOST()file.getName=" + file.getName());
        String uploadfilemsg = "";
        // operate=="submitform"
        // 读取文件列表，统计已有文件的大小
        if (file != null) {
            // 检查上传文件是否存在
            String fileFileName = file.getOriginalFilename();
            System.out.println("TeacherController.evpruploadPOST(),file != null");
            File targetfile = new File(savefolder, fileFileName);
            System.out.println("TeacherController.evpruploadPOST(),targetfile=" + targetfile.getAbsolutePath());

            File dir = new File(savefolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                file.transferTo(targetfile);
            } catch (IllegalStateException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        mv.addObject("message", "内容已经更新！");
        mv.addObject("jumpto", Common.getBasePath() + "teacher/evprreview?evprid=" + evprid);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO 保存学生一道试题的文件
    @RequestMapping(value = {"/evrdelete", "/evrdelete/", "/evrdelete/index.html"}, method = RequestMethod.GET)
    public ModelAndView evrDeleteFileGet(ModelAndView mv, HttpServletRequest request, HttpSession session) {

        // http://localhost:8080/tpa/admin/evrdelete?filehash=4081ac9424998b6ef184f17a6d400522&ug=14&evid=3&u=2017110701&evprid=24879
        String filehash = request.getParameter("filehash");

        String u = request.getParameter("u");
        Integer ug = Integer.parseInt(request.getParameter("ug"));
        Integer evid = Integer.parseInt(request.getParameter("evid"));
        Integer evprid = Integer.parseInt(request.getParameter("evprid"));

        // 查找文件

        String uploadfolder = "WEB-INF\\evfiles\\ug" + ug + "\\e" + evid + "\\u" + u + "\\" + evprid;
        String savefolder = request.getServletContext().getRealPath(uploadfolder);

        // 删除指定digest的文件
        File file = new File(savefolder);
        if (file.isDirectory()) {
            File[] dirFile = file.listFiles();
            for (File f : dirFile) {
                if (DigestUtils.md5Hex(f.getName()).equals(filehash)) {
                    f.delete();
                }
            }
        }

        mv.addObject("message", "内容已经更新！");
        mv.addObject("jumpto", Common.getBasePath() + "teacher/evprreview?evprid=" + evprid);
        mv.setViewName("jumper");

        return mv;
    }

    // TODO 管理员更新学生提交的试题
    @RequestMapping(value = {"/adminevprupdate/{evprid}", "/adminevprupdate/{evprid}/",
            "/adminevprupdate/{evprid}/index.html"}, method = RequestMethod.POST)
    public ModelAndView adminevprupdatePOST(ModelAndView mv, @PathVariable Integer evprid,
                                            @RequestParam(value = "answer", required = false) String answer, HttpServletRequest request,
                                            HttpSession session) {

        System.out.println(this.getClass().getName() + "adminevprupdatePOST==,evprid=" + evprid);

        UserSession student = (UserSession) session.getAttribute(Common.sessionUser);
        Evaluation_result_record evpr = svcb.getEvaluation_result_recordById(evprid);

        // 返回考核点类型编号和名称的映射图
        Map<Integer, String> evtMap = svcb.mapEvaluation_type();
        Pevpoint item = new Pevpoint(evpr, evtMap);

        switch (evpr.getType()) {
            case 1: {
                // 编写xml文件，包含本页面所有信息
                item.setReply(answer);// 记录用户的答案
                Integer score = 0;
                for (int i = 0; i < item.getAnswers().size(); i++) {
                    if (answer.equals(item.getAnswers().get(i).getAnswer())) {
                        // 多个答案中，只要有一个符合，即可
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
                if (item.getAnswers().get(1).getCorrect() && !StringUtils.isBlank(answer)) {
                    // 允许提交文字
                    System.out.println("StudentController.evprshowPOST()允许提交文字");
                    item.setReply(answer);// 记录用户的答案
                    // 更新内容
                    evpr.setSubmit_record(item.toXMLString());
                    evpr.setIs_submited(true);
                }
            }
            break;
            default:
                break;
        }
        try {
            // evpr.setUpdate_gmt(new Date().getTime());
            svcb.updateEvaluation_result_recordById(evpr);

        } catch (Exception e) {

            e.printStackTrace();
        }

        mv.addObject("message", "内容已经更新！");
        mv.addObject("jumpto", Common.getBasePath() + "teacher/evprreview?evprid=" + evprid);
        mv.setViewName("jumper");

        return mv;
    }

    /*****
     * 学生未交卷，教师回收试卷
     *
     * @param evid
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/takebackevaluation/{evid}", "/takebackevaluation/{evid}/",
            "/takebackevaluation/{evid}/index.html"}, method = RequestMethod.GET)
    public ModelAndView evsubmitGET(ModelAndView mv, @PathVariable Integer evid, HttpServletRequest request,
                                    HttpSession session) throws Exception {
        // http://localhost:8080/tpa/admin/takebackevaluation?evid=3&studentid=836
        System.out.println(this.getClass().getName() + ",evid=" + evid);

        Integer studentid = Integer.parseInt(request.getParameter("studentid"));

        List<Evaluation_result> evrlist = svcb.listEvaluation_resultByMap(new HashMap<String, Object>() {
            {
                put(Evaluation_result.EVALUATION_ID, evid);
                put(Evaluation_result.STUDENT_ID, studentid);
            }
        });
        System.out.println("Student.evsubmitGET()evrlist.size=" + evrlist.size());
        if (evrlist.size() == 0) {

            Student student = svcb.getStudentById(studentid);
            // 没有交卷
            Integer student_id = studentid;
            String sno = student.getSno();
            Integer evaluation_id = evid;
            String comment = "交卷了";
            String evaluation_record = "交卷了";
            Integer score = -99;
            Long submit_gmt = new Date().getTime();
            boolean is_reviewed = false;

            Evaluation_result evrecord = new Evaluation_result(student_id, evaluation_id, sno, comment,
                    evaluation_record, score, submit_gmt, is_reviewed);

            svcb.saveEvaluation_result(evrecord);
            mv.addObject("message", "强制回收成功！");
        } else {

            mv.addObject("message", "已经交卷，不要重复提交！");

        }

        mv.addObject("jumpto", Common.getBasePath() + "teacher/configevaluation?id=" + evid);
        mv.setViewName("jumper");
        return mv;
    }
}
