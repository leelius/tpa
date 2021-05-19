package app.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import tpa.entity.*;

public class PdfExperimentReport {

	/***
	 * 
	 * @param user        学生
	 * @param teacher     教师
	 * @param er          实验记录
	 * @param exp         实验
	 * @param ug          学生分组
	 * @param subject     科目
	 * @param pdfsavePath 报告保存路径
	 * @param fileName    报告文件名
	 */
	public void buildExpReport(Student student, User teacher, Experiment_record er, Experiment exp, Student_group ug,
			Subject subject, String pdfsavePath, String fileName) {
		buildReport(exp.getId(), subject.getName(), new Date(er.getSubmit()), ug.getName(), exp.getTitle(),
				er.getScore() + "", student.getLogin_name(), student.getName(), teacher.getName(),
				exp.getPurposes_requirement(), exp.getContent(), er.getMainflow(), er.getResult(), pdfsavePath,
				fileName);

	}

	/***
	 * 文字版
	 * 
	 * @param expOrderId              实验序号
	 * @param subjTitle               科目名称
	 * @param submitdate              提交日期
	 * @param ugname                  班名
	 * @param expTitle                实验名称
	 * @param erscore                 实验成绩
	 * @param userAlias               学生名字
	 * @param username                学号
	 * @param teacherAlias            教师名字
	 * @param expPurposesRequirements 实验目的及要求
	 * @param expExpContent           实验内容
	 * @param erMainflow              实验流程
	 * @param erResult                实验结果
	 * @param pdfsavePath             文件保存路径
	 * @param fileName                文件名
	 */
	public void buildReport(Integer expOrderId, // 实验序号
			String subjTitle, // 科目名称
			Date submitdate, // 提交日期
			String ugname, // 班名
			String expTitle, // 实验名称
			String erscore, // 实验成绩
			String userAlias, // 学生名字
			String username, // 学号
			String teacherAlias, // 教师名字
			String expPurposesRequirements, // 实验目的及要求
			String expExpContent, // 实验内容
			String erMainflow, // 实验流程
			String erResult, // 实验结果
			String pdfsavePath, String fileName) {

		Document document = new Document(PageSize.A4, 40, 40, 40, 40);
		try {
			// 第二步：
			// 创建一个PdfWriter实例，
			// 将文件输出流指向一个文件。
			File dir = new File(pdfsavePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(pdfsavePath, fileName);
			System.out.println("PdfExperimentReportCommon.buildExpReport()file=" + file.getAbsolutePath());

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
			// 第三步：打开文档。
			document.open();
			// 第四步：在文档中增加一个段落。
			BaseFont fontkai = BaseFont.createFont("C:/fonts/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			BaseFont fonthei = BaseFont.createFont("C:/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			BaseFont fontzongyi = BaseFont.createFont("C:/fonts/zongyi.ttf", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			BaseFont fontsongti = BaseFont.createFont("C:/fonts/songti.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);

			com.itextpdf.text.Font fsongti9 = new com.itextpdf.text.Font(fontkai, 9, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fsongti11 = new com.itextpdf.text.Font(fontkai, 11, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fsongti14 = new com.itextpdf.text.Font(fontkai, 14, com.itextpdf.text.Font.NORMAL);

			com.itextpdf.text.Font fkai9 = new com.itextpdf.text.Font(fontkai, 9, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fkai11 = new com.itextpdf.text.Font(fontkai, 11, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fkai14 = new com.itextpdf.text.Font(fontkai, 14, com.itextpdf.text.Font.NORMAL);

			com.itextpdf.text.Font fhei9 = new com.itextpdf.text.Font(fonthei, 9, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fhei11 = new com.itextpdf.text.Font(fonthei, 11, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fhei14 = new com.itextpdf.text.Font(fonthei, 14, com.itextpdf.text.Font.NORMAL);

			// 文档开始
			buildOneReport(document, expOrderId, subjTitle, submitdate, ugname, expTitle, erscore, userAlias, username,
					teacherAlias, expPurposesRequirements, expExpContent, erMainflow, erResult, fsongti11, fhei14,
					fhei11, fsongti9);
			// 文档结束

			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Integer expOrderId,// 实验序号
	// String subjTitle,// 科目名称
	// Date submitdate,// 提交日期
	// String ugname,// 班名
	// String expTitle,// 实验名称
	// Integer erscore,// 实验成绩
	// String userAlias,// 学生名字
	// String username,// 学号
	// String teacherAlias,// 教师名字
	// String expPurposesRequirements,// 实验目的及要求
	// String expExpContent,// 实验内容
	// String erMainflow,// 实验流程
	// String erResult,// 实验结果

	public void buildReportByList(List<PdfInfo> list, String pdfsavePath, String fileName) {
		System.out.println("PdfExperimentReportCommon.buildExpReport()");

		Document document = new Document(PageSize.A4, 40, 40, 40, 40);
		try {
			// 第二步：
			// 创建一个PdfWriter实例，
			// 将文件输出流指向一个文件。
			File dir = new File(pdfsavePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(pdfsavePath, fileName);
			System.out.println("PdfExperimentReportCommon.buildExpReport()file=" + file.getAbsolutePath());

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
			// 第三步：打开文档。
			document.open();
			// 第四步：在文档中增加一个段落。
			BaseFont fontkai = BaseFont.createFont("C:/fonts/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			BaseFont fonthei = BaseFont.createFont("C:/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			BaseFont fontzongyi = BaseFont.createFont("C:/fonts/zongyi.ttf", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			BaseFont fontsongti = BaseFont.createFont("C:/fonts/songti.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);

			com.itextpdf.text.Font fsongti9 = new com.itextpdf.text.Font(fontkai, 9, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fsongti11 = new com.itextpdf.text.Font(fontkai, 11, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fsongti14 = new com.itextpdf.text.Font(fontkai, 14, com.itextpdf.text.Font.NORMAL);

			com.itextpdf.text.Font fkai9 = new com.itextpdf.text.Font(fontkai, 9, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fkai11 = new com.itextpdf.text.Font(fontkai, 11, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fkai14 = new com.itextpdf.text.Font(fontkai, 14, com.itextpdf.text.Font.NORMAL);

			com.itextpdf.text.Font fhei9 = new com.itextpdf.text.Font(fonthei, 9, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fhei11 = new com.itextpdf.text.Font(fonthei, 11, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fhei14 = new com.itextpdf.text.Font(fonthei, 14, com.itextpdf.text.Font.NORMAL);

			for (int i = 0; i < list.size(); i++) {
				// 文档开始
				buildOneReport(document, list.get(i).getExpOrderId(), list.get(i).getSubjTitle(),
						list.get(i).getSubmitdate(), list.get(i).getUgname(), list.get(i).getExpTitle(),
						list.get(i).getErscore() + "", list.get(i).getUserAlias(), list.get(i).getUsername(),
						list.get(i).getTeacherAlias(), list.get(i).getExpPurposesRequirements(),
						list.get(i).getExpContent(), list.get(i).getErMainflow(), list.get(i).getErResult(), fsongti11,
						fhei14, fhei11, fsongti9);
				// 文档结束
				document.newPage();
			}

			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/***
	 * 添加一个实验报告
	 * 
	 * @param document
	 * @param expOrderId
	 * @param subjTitle
	 * @param submitdate
	 * @param ugname
	 * @param expTitle
	 * @param erscore
	 * @param userAlias
	 * @param username
	 * @param teacherAlias
	 * @param expPurposesRequirements
	 * @param expExpContent
	 * @param erMainflow
	 * @param erResult
	 * @param fsongti11
	 * @param fhei14
	 * @param fhei11
	 * @param fsongti9
	 * @throws DocumentException
	 * @throws IOException
	 */

	private void buildOneReport(Document document, Integer expOrderId, // 实验序号
			String subjTitle, // 科目名称
			Date submitdate, // 提交日期
			String ugname, // 班名
			String expTitle, // 实验名称
			String erscore, // 实验成绩
			String userAlias, // 学生名字
			String username, // 学号
			String teacherAlias, // 教师名字
			String expPurposesRequirements, // 实验目的及要求
			String expExpContent, // 实验内容
			String erMainflow, // 实验流程
			String erResult, // 实验结果

			Font fsongti11, Font fhei14, Font fhei11, Font fsongti9) throws DocumentException, IOException {
		SimpleDateFormat sdfday = new SimpleDateFormat("yyyy年MM月dd日");
		{
			Paragraph paragraph = new Paragraph(
					"实验编号：" + expOrderId + "   四川师范大学      " + subjTitle + "  实验报告    " + sdfday.format(submitdate),
					fsongti11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph("计算机科学学院：" + ugname + "   实验名称：    " + expTitle, fsongti11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{
			String score = "   实验成绩：" + erscore;
			// if (erscore == -99) { score = ""; }

			Paragraph paragraph = new Paragraph(
					"姓名:" + userAlias + "　　学号：" + username + "  指导老师：" + teacherAlias + score, fsongti11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph("\r\n实验" + expOrderId + ":" + expTitle, fhei14);
			paragraph.setAlignment(1);
			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph("\r\n一．实验目的及要求:", fhei11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{

			// 在xmlworker中的 XMLWorkerHelper中添加对应的字体参数支持
			//
			// public static ElementList parseToElementList(String html,
			// String css, Font font) throws IOException {
			// // HTML
			// MyFontsProvider fontProvider = new MyFontsProvider(font);
			// CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			// }
			// 自定义类
			// 2017年2月11日
			// public static class MyFontsProvider implements FontProvider {
			// private Font font;
			// public MyFontsProvider(Font font) {
			// this.font = font;
			// }
			// @Override
			// public boolean isRegistered(String fontname) {
			// return false;
			// }
			// @Override
			// public Font getFont(String fontname, String encoding, boolean
			// embedded,
			// float size, int style, BaseColor color) {
			// return font;
			// }
			// }

			Paragraph paragraph = new Paragraph();
			ElementList elementList = XMLWorkerHelper.parseToElementList(expPurposesRequirements, null);
			for (Element element : elementList) {
				paragraph.add(element);
			}
			document.add(paragraph);

		}
		{
			Paragraph paragraph = new Paragraph("\r\n二．	实验内容:", fhei11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{

			Paragraph paragraph = new Paragraph();
			ElementList elementList = XMLWorkerHelper.parseToElementList(expExpContent, null);
			for (Element element : elementList) {
				paragraph.add(element);
			}
			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph("\r\n三．	实验主要流程、基本操作或核心代码、算法片段:", fhei11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph();
			try {
				ElementList elementList = XMLWorkerHelper.parseToElementList(erMainflow, null);
				for (Element element : elementList) {
					paragraph.add(element);
				}
			} catch (Exception e) {
				// TODO: handle exception
				paragraph.add("源代码见附件");
			}

			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph("\r\n四．	实验结果的分析与评价:", fhei11);
			paragraph.setAlignment(0);
			document.add(paragraph);
		}
		{
			Paragraph paragraph = new Paragraph();
			ElementList elementList = XMLWorkerHelper.parseToElementList(erResult, null);
			for (Element element : elementList) {
				paragraph.add(element);
			}
			document.add(paragraph);
		}
	}

}
