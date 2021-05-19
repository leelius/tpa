package app.entity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import lombok.Data;
import tpa.entity.Evaluation_point;
import tpa.entity.Evaluation_result_record;
import tpa.entity.Vevaluation_point;

/***
 * 编程题目，结构不同
 * 
 * @author Denny
 *
 */
@Data
public class PevProgramPoint extends Evaluation_point {

	private String title;
	private String subjectName;
	private String evTypeName;
	private Boolean submited;
	private String reply;
	private Integer score;
	private Integer scoreWish;
	private Boolean reviewed;

	private String evpcontent;
	private String evpin;
	private String evpout;
	private String evpexamplein;
	private String evpexampleout;
	private String datalimit;

	private List<FileNameHash> filelist;

	public PevProgramPoint(Integer vevaluation_point_id, Integer subject_id, Boolean is_actived, Integer type,
			Integer difficulty, String content, String title, String evpin, String evpout, String evpexamplein,
			String evpexampleout, String datalimit) {

		super(vevaluation_point_id, subject_id, is_actived, type, difficulty, content);
		this.title = title;
		this.evpcontent = content;
		this.evpin = evpin;
		this.evpout = evpout;
		this.evpexamplein = evpexamplein;
		this.evpexampleout = evpexampleout;
		this.datalimit = datalimit;

	}

	@Override
	public String toString() {
		String str = "Evpoint:" + ",  id=" + getId() + ",  subjectId=" + getSubject_id() + ",  evType="
				+ getEvTypeName() + ",  evTypeName=" + evTypeName + ",reply=" + reply;

		if (filelist != null) {
			for (int i = 0; i < filelist.size(); i++) {
				str += "\r\n" + filelist.get(i).toString();
			}
		}
		return str;
	}

	public String toXMLString() {

		// 编写xml文件，包含本页面所有信息
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("root");
		document.setRootElement(root);

		Element el_title = DocumentHelper.createElement("para");
		el_title.addAttribute("name", "title");
		el_title.addAttribute("value", title);
		root.add(el_title);

		Element el_content = DocumentHelper.createElement("para");
		el_content.addAttribute("name", "evpcontent");
		el_content.addAttribute("value", super.getContent());
		root.add(el_content);

		Element el_evpin = DocumentHelper.createElement("para");
		el_evpin.addAttribute("name", "evpin");
		el_evpin.addAttribute("value", evpin);
		root.add(el_evpin);

		Element el_evpout = DocumentHelper.createElement("para");
		el_evpout.addAttribute("name", "evpout");
		el_evpout.addAttribute("value", evpout);
		root.add(el_evpout);

		Element el_evpexamplein = DocumentHelper.createElement("para");
		el_evpexamplein.addAttribute("name", "evpexamplein");
		el_evpexamplein.addAttribute("value", evpexamplein);
		root.add(el_evpexamplein);

		Element el_evpexampleout = DocumentHelper.createElement("para");
		el_evpexampleout.addAttribute("name", "evpexampleout");
		el_evpexampleout.addAttribute("value", evpexampleout);
		root.add(el_evpexampleout);

		Element el_datalimit = DocumentHelper.createElement("para");
		el_datalimit.addAttribute("name", "datalimit");
		el_datalimit.addAttribute("value", datalimit);
		root.add(el_datalimit);

		XMLWriter xmlWriter = null;
		StringWriter out = new StringWriter(1024);
		// OutputFormat format = OutputFormat.createPrettyPrint();
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("UTF-8");
		xmlWriter = new XMLWriter(out, format);
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}

	// 从数据库读取后，还原为最初的内容
	public PevProgramPoint(Vevaluation_point evp, Map<String, String> evtMap) {

		this.setId(evp.getId());
		this.setDifficulty(evp.getDifficulty());
		this.setSubject_id(evp.getSubject_id());
		this.setType(evp.getType());
		this.setContent(evp.getContent());
		this.setSubjectName(evp.getSubject_name());
		this.setIs_actived(evp.getIs_actived());

//		<?xml version="1.0" encoding="UTF-8"?>
//		 <root>
//		 <para name="title" value="三角形面积"/>
//		 <para name="content" value="输入三角形三边长a,b,c（保证能构成三角形），输出三角形面积。保存文件名：triangle.cpp"/>
//		 <para name="evpin" value="从文件triangle.in中读入数据，一行三个用一个空格隔开的实数a,b,c，表示三角形的三条边长。"/>
//		 <para name="evpout" value="输出到文件triangle.out中，输出三角形的面积，答案保留四位小数。"/>
//		 <para name="evpexamplein" value="3 4 5"/>
//		 <para name="evpexampleout" value="6.0000"/>
//		 <para name="datalimit" value="1&lt;=a,b,c&lt;=10000"/>
//		 </root>
		Document document = null;
		try {
			document = DocumentHelper.parseText(evp.getContent());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element element : elementList) {

			String name = element.attributeValue("name");
			String value = element.attributeValue("value");

//			this.title = title;
//			this.evpcontent = evpcontent;
//			this.evpin = evpin;
//			this.evpout = evpout;
//			this.evpexamplein = evpexamplein;
//			this.evpexampleout = evpexampleout;
//			this.datalimit = datalimit;

			if ("title".equals(name)) {
				this.setTitle(value);
			} else if ("evpcontent".equals(name)) {
				this.setEvpcontent(value);
			} else if ("evpin".equals(name)) {
				this.setEvpin(value);
			} else if ("evpout".equals(name)) {
				this.setEvpout(value);
			} else if ("evpexamplein".equals(name)) {
				this.setEvpexamplein(value);
			} else if ("evpexampleout".equals(name)) {
				this.setEvpexampleout(value);
			} else if ("datalimit".equals(name)) {
				this.setDatalimit(value);

			} else {

			}
		}

		this.setEvTypeName(evtMap.get(evp.getType() + "").toString());
		this.setFilelist(new ArrayList<>());

	}

	/***
	 * 学生端，需要显示从数据库中读取的题目
	 * 
	 * @param evp
	 * @param evtMap
	 */
	public PevProgramPoint(Evaluation_result_record evp, Map<Integer, String> evtMap) {

		// System.out.println("Pevpoint.Pevpoint()evp="+evp.toString());
		// System.out.println("Pevpoint.Pevpoint()evtMap.size="+evtMap.size());

		this.setId(evp.getId());
		this.setDifficulty(evp.getDifficulty());
		this.setSubject_id(evp.getSubject_id());
		this.setType(evp.getType());
		this.setContent(evp.getSubmit_record());

		this.setSubmited(evp.getIs_submited());

		this.setScore(evp.getScore());
		this.setReviewed(evp.getIs_reviewed());

		this.setScoreWish(evp.getScore_wish());

		// <?xml version="1.0" encoding="UTF-8"?>
		// <root>
		// <para name="title" value="aaa"/>
		// <para name="answer" value="bbb"/>
		// <para name="answer" value="d" check="on"/>
		// </root>
		Document document = null;
		try {
			document = DocumentHelper.parseText(evp.getSubmit_record());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element element : elementList) {

			String name = element.attributeValue("name");
			String value = element.attributeValue("value");

			if ("title".equals(name)) {
				this.setTitle(value);
			} else if ("evpcontent".equals(name)) {
				this.setEvpcontent(value);
			} else if ("evpin".equals(name)) {
				this.setEvpin(value);
			} else if ("evpout".equals(name)) {
				this.setEvpout(value);
			} else if ("evpexamplein".equals(name)) {
				this.setEvpexamplein(value);
			} else if ("evpexampleout".equals(name)) {
				this.setEvpexampleout(value);
			} else if ("datalimit".equals(name)) {
				this.setDatalimit(value);

			} else {

			}
		}

		this.setEvTypeName(evtMap.get(evp.getType()).toString());

	}

}
