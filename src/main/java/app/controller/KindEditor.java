
package app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import app.entity.UserSession;
import app.service.AppService;
import app.service.BrowseService;
import app.utils.Common;
import net.coobird.thumbnailator.Thumbnails;
import sld.webutils.Util;
import tpa.entity.*;

@Controller
public class KindEditor {

	@Autowired
	@Qualifier("AppService")
	private AppService svc;

	@Autowired
	@Qualifier("BrowseService")
	private BrowseService svcb;

	public KindEditor() {
		System.out.println("KindEditor--控制器加载");
	}

	/***
	 * 
	 * @param category 用于区分上传的种类，product商品图片上传(productid)/article文章附件上传
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/uploadfile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadfile(@RequestParam(value = "category", required = false) String category,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("KindEditor.uploadfile()进入附件上传！");

		// ?category=product&productid=1
		// ?category=article

		String foldername = category;// product/article
		String path = request.getServletContext()
				.getRealPath(Common.uploadFileSaveFolder + File.separatorChar + foldername);
		String pathsrc = request.getServletContext()
				.getRealPath(Common.uploadFileSaveFolderSrc + File.separatorChar + foldername);
		String saveUrl = Common.uploadFileSaveFolder + "/" + foldername + "/";
		Integer currentUser_id = ((UserSession) (session.getAttribute(Common.sessionUser))).getId();

		Map<String, Object> map = new HashMap<String, Object>();

		if (!ServletFileUpload.isMultipartContent(request)) {
			System.out.println("请选择文件。");
			return getError("请选择文件");
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		System.out.println("KindEditor.uploadfile()dirName=" + dirName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		// 定义允许上传的文件扩展名
		Map<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp,svg");
		extMap.put("media", "flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,txt,zip,rar");

		if (!extMap.containsKey(dirName)) {
			return getError("目录名不正确。");
		}

		// 创建文件夹
		path += "\\" + dirName + "\\" + ymd + "\\";
		pathsrc += "\\" + dirName + "\\" + ymd + "\\";
		saveUrl += dirName + "/" + ymd + "/";

		System.out.println("KindEditor.uploadfile()path=" + path);
		System.out.println("KindEditor.uploadfile()pathsrc=" + pathsrc);
		System.out.println("KindEditor.uploadfile()saveUrl=" + saveUrl);

		Util.checkDirectoryExists(path);// 检查文件夹是否存在
		Util.checkDirectoryExists(pathsrc);
		Util.checkDirectoryExists(saveUrl);

		// 最大文件大小
		long maxSize = Common.maxFileSize;

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
		// System.out.println("KindEditor.uploadfile()fileMap.size()=" +
		// fileMap.size());
		String fileName = null;
		for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();
			fileName = mFile.getOriginalFilename();
			// 检查文件大小
			if (mFile.getSize() > maxSize) {
				return getError("上传文件大小超过限制，请适当裁剪修改后再上传。");
			}
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
				return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
			}

			String filename = currentUser_id + "_" + new Date().getTime() + "_" + new Random().nextInt(1000) + "."
					+ fileExt;
			path = path + filename;
			pathsrc = pathsrc + filename;
			saveUrl = saveUrl + filename;

			// System.out.println("KindEditor.uploadFile()path=" + path);
			// System.out.println("KindEditor.uploadFile()saveUrl=" + saveUrl);

			if (dirName.equals("image")) {
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pathsrc));
				FileCopyUtils.copy(mFile.getInputStream(), outputStream);

				// 如果图片超出宽度800，调整			
				Thumbnails.of(pathsrc).size(800, 500).toFile(path);

			} else {
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
				FileCopyUtils.copy(mFile.getInputStream(), outputStream);
			}

			// 数据库中记录上传了的附件 product/article
			if (category.equals("article")) {
				
			//} else if (category.equals("article")) {

			} else {

			}

			Map<String, Object> succMap = new HashMap<String, Object>();
			succMap.put("error", 0);
			succMap.put("url", "../" + saveUrl);

			// System.out.println("KindEditor.uploadfile()saveUrl=" + saveUrl);

			return succMap;

		}

		return null;
	}

	private Map<String, Object> getError(String errorMsg) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("error", 1);
		errorMap.put("message", errorMsg);
		return errorMap;
	}

	/**
	 * 文件空间
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return json
	 */
	@RequestMapping(value = "/admin/filemanager")
	@ResponseBody
	public Object fileManager(HttpServletRequest request, HttpServletResponse response) {

		String foldername = "article";
		// 根目录路径，可以指定绝对路径
		String rootPath = request.getServletContext()
				.getRealPath(Common.uploadFileSaveFolder + File.separatorChar + foldername + File.separatorChar);

		// 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = request.getContextPath() + "/" + Common.uploadFileSaveFolder + "/" + foldername;

		// 图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp", "svg" };

		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if (!Arrays.<String>asList(new String[] { "image", "media", "file" }).contains(dirName)) {
				return "Invalid Directory name.";
			}
			rootPath += "\\" + dirName + "\\";
			rootUrl += "/" + dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}

		// System.out.println("KindEditor.fileManager()rootPath=" + rootPath);
		// System.out.println("KindEditor.fileManager()rootUrl=" + rootUrl);
		// 根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";

		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";

		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		// System.out.println("KindEditor.fileManager()path=" + path + ",order=" +
		// order);
		// System.out.println("KindEditor.fileManager()currentPath=" + currentPath);

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			return "Access is not allowed.";
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			return "Parameter is not valid.";
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			return "Directory does not exist.";
		}

		// 遍历目录取的文件信息
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		return result;
	}

	private class NameComparator implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> hashA, Map<String, Object> hashB) {
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
			}
		}
	}

	private class SizeComparator implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> hashA, Map<String, Object> hashB) {
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
					return 1;
				} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	private class TypeComparator implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> hashA, Map<String, Object> hashB) {
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
			}
		}
	}

}
