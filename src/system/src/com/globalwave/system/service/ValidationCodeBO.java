package com.globalwave.system.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;



public class ValidationCodeBO {

	private static int WIDTH = 60;

	private static int HEIGHT = 20;

	public Object[] generate(OutputStream sos) throws Exception {

		// 创建内存图象并获得其图形上下文
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		// 产生随机的认证码
		char[] rands = generateCheckCode();

		// 产生图像
		drawBackground(g);

		drawRands(g, rands);

		// 结束图像 的绘制 过程， 完成图像
		g.dispose();

		// 将图像输出到客户端
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ImageIO.write(image, "JPEG", bos);

		byte[] buf = bos.toByteArray();

		// 下面的语句也可写成： bos.writeTo(sos);
		sos.write(buf);

		bos.close();

		sos.close();

		return new Object[]{buf.length, String.valueOf(rands)} ;

	}

	private char[] generateCheckCode()

	{

		// 定义验证码的字符表

		String chars = "0123456789abcdefghijklmnopqrstuvwxyz";

		char[] rands = new char[4];

		for (int i = 0; i < 4; i++)

		{

			int rand = (int) (Math.random() * 36);

			rands[i] = chars.charAt(rand);

		}

		return rands;

	}

	private void drawRands(Graphics g, char[] rands)

	{

		g.setColor(Color.BLACK);

		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));

		// 在不同的高度上输出验证码的每个字符

		g.drawString("" + rands[0], 1, 17);

		g.drawString("" + rands[1], 16, 15);

		g.drawString("" + rands[2], 31, 18);

		g.drawString("" + rands[3], 46, 16);

		System.out.println(rands);

	}

	private void drawBackground(Graphics g)

	{

		// 画背景

		g.setColor(new Color(0xDCDCDC));

		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 随机产生 120 个干扰点

		for (int i = 0; i < 120; i++)

		{

			int x = (int) (Math.random() * WIDTH);

			int y = (int) (Math.random() * HEIGHT);

			int red = (int) (Math.random() * 255);

			int green = (int) (Math.random() * 255);

			int blue = (int) (Math.random() * 255);

			g.setColor(new Color(red, green, blue));

			g.drawOval(x, y, 1, 0);

		}

	}

}
