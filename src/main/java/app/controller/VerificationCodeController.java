package app.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import app.utils.Common;

@Controller
public class VerificationCodeController {

	
	public VerificationCodeController() {
		System.out.println("VerificationCodeController--控制器加载");
	}
	
	
	/**
	 * 用于随机生成验证码的数据源
	 * 
	 * 去掉容易混淆的'O', '0','I', '1', 'L',
	 */
	private static final char[] source = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final int charNumber = 4;
	private static final int width = charNumber * 30;
	private static final int height = 36;

	/**
	 * 用于生成随机数的随机数生成器
	 */
	private static final Random rdm = new Random();

	@RequestMapping(value = { "getverificationcodeimage" })
	public void getVerificationCodeImage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		String text = generateText(charNumber);
		//System.out.println("VerificationCodeController.getAuthCode()text=" + text);
		BufferedImage image = createImage(text);

		// 设置response头信息
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 将字符保存到session中用于前端的验证
		session.setAttribute(Common.sessionVerificationCode, text);

		ImageIO.write(image, "JPEG", response.getOutputStream());
		response.getOutputStream().flush();
	}

	/**
	 * 生成长度为4的随机字符串
	 */
	private String generateText(Integer number) {
		char[] s = new char[number];
		for (int i = 0; i < s.length; i++) {
			s[i] = source[rdm.nextInt(source.length)];
		}
		return new String(s).toUpperCase();
	}

	/**
	 * 在内存中生成打印了随机字符串的图片
	 * 
	 * @return 在内存中创建的打印了字符串的图片
	 */
	private BufferedImage createImage(String text) {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		Font mFont = new Font("宋体", Font.BOLD, 28);
		g.setFont(mFont);
		g.setColor(getRandColor(180, 200));
		// 画随机的线条
		for (int i = 0; i < 50; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(6) + 1;
			int y1 = random.nextInt(12) + 1;
			BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
			g2d.setStroke(bs);
			g2d.draw(line);
		}
		// 输出随机的验证文字
		for (int i = 0; i < text.length(); i++) {

			String ctmp = text.substring(i, i + 1);
			Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110));
			g.setColor(color);
			/** **随机缩放文字并将文字旋转指定角度* */
			// 将文字旋转指定角度
			Graphics2D g2d_word = (Graphics2D) g;
			AffineTransform trans = new AffineTransform();
			trans.rotate(random.nextInt(45) * 3.14 / 180, 22 * i + 8, 7);
			// 缩放文字
			float scaleSize = random.nextFloat() + 0.8f;
			if (scaleSize > 1f)
				scaleSize = 1f;
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			/** ********************* */
			g.drawString(ctmp, 22 * i + 18, 22);

		}
		g.dispose();
		return image;
	}

	// 获取随机颜色
	public Color getRandColor(Integer s, Integer e) {
		Random random = new Random();
		if (s > 255)
			s = 255;
		if (e > 255)
			e = 255;
		int r = s + random.nextInt(e - s); // 随机生成RGB颜色中的r值
		int g = s + random.nextInt(e - s); // 随机生成RGB颜色中的g值
		int b = s + random.nextInt(e - s); // 随机生成RGB颜色中的b值
		return new Color(r, g, b);
	}

}
