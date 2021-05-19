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

@Data
public class Pevpoint extends Evaluation_point {

	// private Integer id;
	// private Integer subjectId;//科目
	// private String evType;//题型
	// private String evpContent;//内容

	private String title;
	private String subjectName;
	private String evTypeName;
	private Boolean submited;
	private String reply;
	private Integer score;
	private Integer scoreWish;
	private Boolean reviewed;

	private List<PevOneAnswer> answers;
	private List<FileNameHash> filelist;

	public Pevpoint(Integer vevaluation_point_id, Integer subject_id, Boolean is_actived, Integer type,
			Integer difficulty, String content, String title, List<PevOneAnswer> answers) {

		super(vevaluation_point_id, subject_id, is_actived, type, difficulty, content);
		this.title = title;
		this.answers = answers;
	}

	public Pevpoint(Integer subject_id, Boolean is_actived, Integer type, Integer difficulty, String content,
			String title, List<PevOneAnswer> answers) {

		super(subject_id, is_actived, type, difficulty, content);
		this.title = title;
		this.answers = answers;
	}

	@Override
	public String toString() {
		String str = "Evpoint:" + ",  id=" + getId() + ",  subjectId=" + getSubject_id() + ",  evType="
				+ getEvTypeName() + ",  evTypeName=" + evTypeName + ",reply=" + reply;

		if (answers != null) {
			for (int i = 0; i < answers.size(); i++) {
				str += "\r\n" + answers.get(i).toString();
			}
		}
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

		Element el_reply = DocumentHelper.createElement("para");
		el_reply.addAttribute("name", "reply");
		el_reply.addAttribute("value", reply);
		root.add(el_reply);

		for (int i = 0; i < answers.size(); i++) {

			Element para = DocumentHelper.createElement("para");
			para.addAttribute("key", answers.get(i).getKey());
			para.addAttribute("name", "answer");
			para.addAttribute("value", answers.get(i).getAnswer());

			if (answers.get(i).getCorrect()) {
				para.addAttribute("check", "on");
			}

			if (answers.get(i).getReply() != null && answers.get(i).getReply()) {
				para.addAttribute("reply", "on");
			}

			root.add(para);
		}

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

	public Pevpoint() {
		// TODO Auto-generated constructor stub
	}

	public Pevpoint(Vevaluation_point evp, Map<String, String> evtMap) {

		this.setId(evp.getId());
		this.setDifficulty(evp.getDifficulty());
		this.setSubject_id(evp.getSubject_id());
		this.setType(evp.getType());
		this.setContent(evp.getContent());
		this.setSubjectName(evp.getSubject_name());
		this.setIs_actived(evp.getIs_actived());

		this.setAnswers(new ArrayList<PevOneAnswer>());

		// <?xml version="1.0" encoding="UTF-8"?>
		// <root>
		// <para name="title" value="aaa"/>
		// <para name="answer" value="bbb"/>
		// <para name="answer" value="d" check="on"/>
		// </root>
		Document document = null;
		try {
			document = DocumentHelper.parseText(evp.getContent());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element element : elementList) {

			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");
			String check = element.attributeValue("check");
			// System.out.println("ManageEvAdmin.execute()"+"name="+name+",value="+value+",check="+check);

			if ("title".equals(name)) {
				this.setTitle(value);
			} else if ("reply".equals(name)) {
				this.setReply(value);
			} else {
				PevOneAnswer answer = new PevOneAnswer();

				answer.setKey(key);
				answer.setAnswer(value);
				if ("on".equals(check)) {
					answer.setCorrect(true);
				} else {
					answer.setCorrect(false);
				}
				this.getAnswers().add(answer);
			}
		}

		this.setEvTypeName(evtMap.get(evp.getType() + "").toString());
		this.setFilelist(new ArrayList<>());

	}

	public Pevpoint(Evaluation_result_record evp, Map<String, String> evtMap, List<FileNameHash> filelist) {

		this.setFilelist(filelist);
		this.setId(evp.getId());
		this.setDifficulty(evp.getDifficulty());
		this.setSubject_id(evp.getSubject_id());
		this.setType(evp.getType());
		this.setContent(evp.getSubmit_record());

		this.setAnswers(new ArrayList<PevOneAnswer>());

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

			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");
			String check = element.attributeValue("check");
			// System.out.println("ManageEvAdmin.execute()"+"name="+name+",value="+value+",check="+check);

			if ("title".equals(name)) {
				this.setTitle(value);
			} else if ("reply".equals(name)) {
				this.setReply(value);
			} else {
				PevOneAnswer answer = new PevOneAnswer();

				answer.setKey(key);
				answer.setAnswer(value);
				if ("on".equals(check)) {
					answer.setCorrect(true);
				} else {
					answer.setCorrect(false);
				}
				this.getAnswers().add(answer);
			}
		}
		// System.out.println("Pevpoint.Pevpoint()evp.getType()=" + evp.getType());
		this.setEvTypeName(evtMap.get(evp.getType() + "").toString());

		// 去掉多余的<p>和</p>
		// this.title = this.title.replace("<p>", "").replace("</p>", "");

	}

	/***
	 * 学生端，需要显示从数据库中读取的题目
	 * 
	 * @param evp
	 * @param evtMap
	 */
	public Pevpoint(Evaluation_result_record evp, Map<Integer, String> evtMap) {

		// System.out.println("Pevpoint.Pevpoint()evp="+evp.toString());
		// System.out.println("Pevpoint.Pevpoint()evtMap.size="+evtMap.size());

		this.setId(evp.getId());
		this.setDifficulty(evp.getDifficulty());
		this.setSubject_id(evp.getSubject_id());
		this.setType(evp.getType());
		this.setContent(evp.getSubmit_record());

		this.setAnswers(new ArrayList<PevOneAnswer>());

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

			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");
			String check = element.attributeValue("check");
			String reply = element.attributeValue("reply");
			// System.out.println("ManageEvAdmin.execute()" + "name=" + name + ",value=" +
			// value + ",check=" + check
			// + ",reply=" + reply);

			if ("title".equals(name)) {

				this.setTitle(value);

			} else if ("reply".equals(name)) {

				this.setReply(value);

			} else {
				PevOneAnswer answer = new PevOneAnswer();

				answer.setKey(key);
				answer.setAnswer(value);
				if ("on".equals(check)) {
					answer.setCorrect(true);
				} else {
					answer.setCorrect(false);
				}
				if ("on".equals(reply)) {
					answer.setReply(true);
				} else {
					answer.setReply(false);
				}

				this.getAnswers().add(answer);
			}
		}
		// System.out.println("Pevpoint.Pevpoint()evp.getType()=" + evp.getType());
		this.setEvTypeName(evtMap.get(evp.getType()).toString());

		// 去掉多余的<p>和</p>
		// this.title = this.title.replace("<p>", "").replace("</p>", "");

	}

}
