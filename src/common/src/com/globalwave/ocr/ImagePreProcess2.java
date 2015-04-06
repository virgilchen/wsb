package com.globalwave.ocr;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;


public class ImagePreProcess2 {
	 private static Map<BufferedImage, String> trainMap = null;  
	    private static int index = 0;  
	  
	    private static String BASE_PATH = "D:\\temp\\photo\\";
	    public static int isBlack(int colorInt) {  
	        Color color = new Color(colorInt);  
	        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {  
	            return 1;  
	        }  
	        return 0;  
	    }  
	    
	    public static boolean isBlackB(int colorInt) {
	    	return isBlack(colorInt) == 1;
	    }
	  
	    public static int isWhite(int colorInt) {  
	        Color color = new Color(colorInt);  
	        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {  
	            return 1;  
	        }  
	        return 0;  
	    }  
	  
	    public static BufferedImage removeBackgroud(String picFile)  
	            throws Exception {  
	        BufferedImage img = ImageIO.read(new File(picFile));  
	        return img;  
	    }  
	  
	    public static BufferedImage removeBlank(BufferedImage img) throws Exception {  
	        int width = img.getWidth();  
	        int height = img.getHeight();  
	        int start = 0;  
	        int end = 0;  
	        Label1: for (int y = 0; y < height; ++y) {  
	            int count = 0;  
	            for (int x = 0; x < width; ++x) {  
	                if (isWhite(img.getRGB(x, y)) == 1) {  
	                    count++;  
	                }  
	                if (count >= 1) {  
	                    start = y;  
	                    break Label1;  
	                }  
	            }  
	        }  
	        Label2: for (int y = height - 1; y >= 0; --y) {  
	            int count = 0;  
	            for (int x = 0; x < width; ++x) {  
	                if (isWhite(img.getRGB(x, y)) == 1) {  
	                    count++;  
	                }  
	                if (count >= 1) {  
	                    end = y;  
	                    break Label2;  
	                }  
	            }  
	        }  
	        return img.getSubimage(0, start, width, end - start + 1);  
	    }  
	  
	    public static List<BufferedImage> splitImage(BufferedImage img)  
	            throws Exception {  
	        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();  
	        int width = img.getWidth();  
	        int height = img.getHeight();  
	        List<Integer> weightlist = new ArrayList<Integer>();  
	        for (int x = 0; x < width; ++x) {  
	            int count = 0;  
	            for (int y = 0; y < height; ++y) {  
	                if (isWhite(img.getRGB(x, y)) == 1) {  
	                    count++;  
	                }  
	            }  
	            weightlist.add(count);  
	        }  
	        for (int i = 0; i < weightlist.size();) {  
	            int length = 0;  
	            while (weightlist.get(i++) > 1) {  
	                length++;  
	            }  
	            if (length > 12) {  
	                subImgs.add(removeBlank(img.getSubimage(i - length - 1, 0,  
	                        length / 2, height)));  
	                subImgs.add(removeBlank(img.getSubimage(i - length / 2 - 1, 0,  
	                        length / 2, height)));  
	            } else if (length > 3) {  
	                subImgs.add(removeBlank(img.getSubimage(i - length - 1, 0,  
	                        length, height)));  
	            }  
	        }  
	        return subImgs;  
	    }  
	  
	    public static Map<BufferedImage, String> loadTrainData() throws Exception {  
	        if (trainMap == null) {  
	            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();  
	            File dir = new File(BASE_PATH + "train2");  
	            File[] files = dir.listFiles();  
	            for (File file : files) {  
	                map.put(ImageIO.read(file), file.getName().charAt(0) + "");  
	            }  
	            trainMap = map;  
	        }  
	        return trainMap;  
	    }  
	  
	    public static String getSingleCharOcr(BufferedImage img,  
	            Map<BufferedImage, String> map) {  
	        String result = "";  
	        int width = img.getWidth();  
	        int height = img.getHeight();  
	        int min = width * height;  
	        for (BufferedImage bi : map.keySet()) {  
	            int count = 0;  
	            int widthmin = width < bi.getWidth() ? width : bi.getWidth();  
	            int heightmin = height < bi.getHeight() ? height : bi.getHeight();  
	            Label1: for (int x = 0; x < widthmin; ++x) {  
	                for (int y = 0; y < heightmin; ++y) {  
	                    if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {  
	                        count++;  
	                        if (count >= min)  
	                            break Label1;  
	                    }  
	                }  
	            }  
	            if (count < min) {  
	                min = count;  
	                result = map.get(bi);  
	            }  
	        }  
	        getSingleCharOcrMiddle(img, map);
	        getSingleCharOcrCenter(img, map);
	        
	        new ImageGuess(img,false).locateTop() ;
	        
	        return result;  
	    }  

	    public static String getSingleCharOcrMiddle(BufferedImage img,  
	            Map<BufferedImage, String> map) {  
	        int width = img.getWidth();  
	        int height = img.getHeight();  
	 
	        boolean currentIsBlack = false ;
	        int count = 0 ;
	        for (int y = 0; y < height; ++y) {  
	            if (isBlackB(img.getRGB((width/2) - 2, y)) 
	            		|| isBlackB(img.getRGB((width/2) - 1, y))
	            		|| isBlackB(img.getRGB((width/2) + 0, y))
	            		|| isBlackB(img.getRGB((width/2) + 1, y))
	            		|| isBlackB(img.getRGB((width/2) + 2, y))) { 
	            	if (!currentIsBlack) {
	            		currentIsBlack = true ;
	            		count ++ ;
	            	}
	            } else {
	            	currentIsBlack = false ;
	            }
	        }  
	        System.err.println("");
	        System.err.println("-->" + count);
	        
	        return null;  
	    }  
	  


	    public static String getSingleCharOcrCenter(BufferedImage img,  
	            Map<BufferedImage, String> map) {  
	        int width = img.getWidth();  
	        int height = img.getHeight();  
	 
	        boolean currentIsBlack = false ;
	        int count = 0 ;
	        for (int y = 0; y < width; ++y) {  
	            if (isBlackB(img.getRGB((height/2) - 2, y)) 
	            		|| isBlackB(img.getRGB(y, (height/2) - 1))
	            		|| isBlackB(img.getRGB(y, (height/2) + 0))
	            		|| isBlackB(img.getRGB(y, (height/2) + 1))
	            		|| isBlackB(img.getRGB(y, (height/2) + 2))) { 
	            	if (!currentIsBlack) {
	            		currentIsBlack = true ;
	            		count ++ ;
	            	}
	            } else {
	            	currentIsBlack = false ;
	            }
	        }  
	        
	        System.err.println("-->" + count);
	        
	        return null;  
	    }  
	    
	    public static String getAllOcr(String file) throws Exception {  
	        BufferedImage img = removeBackgroud(file);  
	        List<BufferedImage> listImg = splitImage(img);  
	        Map<BufferedImage, String> map = loadTrainData();  
	        String result = "";  
	        for (BufferedImage bi : listImg) {  
	            result += getSingleCharOcr(bi, map);  
	        }  
	        ImageIO.write(img, "JPG", new File(BASE_PATH + "result2//" + result + ".jpg"));  
	        return result;  
	    }  
	  
	    public static void downloadImage() {  
	        HttpClient httpClient = new HttpClient();  
	        GetMethod getMethod = null;  
	        for (int i = 0; i < 30; i++) {  
	            getMethod = new GetMethod("http://www.pkland.net/img.php?key="  
	                    + (2000 + i));  
	            try {  
	                // 执行getMethod  
	                int statusCode = httpClient.executeMethod(getMethod);  
	                if (statusCode != HttpStatus.SC_OK) {  
	                    System.err.println("Method failed: "  
	                            + getMethod.getStatusLine());  
	                }  
	                // 读取内容  
	                String picName = "img2//" + i + ".jpg";  
	                InputStream inputStream = getMethod.getResponseBodyAsStream();  
	                OutputStream outStream = new FileOutputStream(picName);  
	                IOUtils.copy(inputStream, outStream);  
	                outStream.close();  
	                System.out.println(i + "OK!");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            } finally {  
	                // 释放连接  
	                getMethod.releaseConnection();  
	            }  
	        }  
	    }  
	  
	    public static void trainData() throws Exception {  
	        File dir = new File(BASE_PATH + "temp");  
	        File[] files = dir.listFiles();  
	        for (File file : files) {  
	            BufferedImage img = removeBackgroud(BASE_PATH + "temp//" + file.getName());  
	            List<BufferedImage> listImg = splitImage(img);  
	            if (listImg.size() == 4) {  
	                for (int j = 0; j < listImg.size(); ++j) {  
	                    ImageIO.write(listImg.get(j), "JPG", new File(BASE_PATH + "train3//"  
	                            + file.getName().charAt(j) + "-" + (index++)  
	                            + ".jpg"));  
	                }  
	            }  
	        }  
	    }  
	  
	    /** 
	     * @param args 
	     * @throws Exception 
	     */  
	    public static void main(String[] args) throws Exception {  
	        // downloadImage();  
	    	//trainData();
	        for (int i = 0; i < 1; ++i) {  
	            String text = getAllOcr(BASE_PATH + "0_1281276767GVgH.gif");  
	            System.out.println(i + ".jpg = " + text);  
	        }  
	    }  
	    
	    
	    public static class ImageGuess {
	    	private BufferedImage img ;
	    	
	    	private boolean fontIsBlack ;
	    	
	    	private List<Point> tops;	    	
	    	private List<Point> rights;	    	
	    	private List<Point> bottoms;	    	
	    	private List<Point> lefts;
	    	
	    	public ImageGuess(BufferedImage img, boolean fontIsBlack) {
	    		this.img = img ;
	    		this.fontIsBlack = fontIsBlack ;
	    	}
	    	
	    	public void setFontIsBlack(boolean fontIsBlack) {
				this.fontIsBlack = fontIsBlack;
			}
	    	
	    	public BufferedImage getImg() {
				return img;
			}
	    	public void setImg(BufferedImage img) {
				this.img = img;
			}
	    	
	    	public void locateTop() {

	    		tops = new ArrayList<Point>();
	    			    		
		        int width = img.getWidth();  
		        int height = img.getHeight();  
		        
		        int x = 0 ; 
		        int y = 0 ;
		 
		        firstPoint:
		        for (x = 0 ; x < width ; x ++) {
			        for (y = 0; y < height; y ++) {  
			            if (isFound(x, y)) {
			            	break firstPoint;
			            }
			        }  
		        }
		        
		        boolean currentIsBlack = false ;
		        for (int xNext = 1; xNext < width - 1; xNext ++) {  
		            if (isContinuePoint(xNext, y)) { 
		            	if (!currentIsBlack) {//fist point
		            		tops.add(new Point(xNext, y)) ;
		            		currentIsBlack = true ;
		            	}
		            } else {
	            		tops.add(new Point(xNext, y - 1)) ;
		            	currentIsBlack = false ;
		            }
		        } 
		        
		        printPoint(tops);
	    	}
	    	
	    	private void printPoint(List<Point> points) {
	    		for (Point p : points) {
	    			System.err.print("(" + p.x + ", " + p.y + ") ");
	    		}
	    		System.err.println();
	    	}
	    	
	    	private boolean isFound(int x, int y) {
	    		try {
		    		if (this.fontIsBlack) {
		    			if (isBlackB(img.getRGB(x - 1, y)) 
			            		&& isBlackB(img.getRGB(x, y - 1))
			            		&& isBlackB(img.getRGB(x, y))
			            		&& isBlackB(img.getRGB(x + 1, y))
			            		&& isBlackB(img.getRGB(x, y + 1))) {
		    				return true ;
		    			}  else {
		    				return false ;
		    			}
		    		} else {
		    			if (!isBlackB(img.getRGB(x - 1, y)) 
			            		&& !isBlackB(img.getRGB(x, y - 1))
			            		&& !isBlackB(img.getRGB(x, y))
			            		&& !isBlackB(img.getRGB(x + 1, y))
			            		&& !isBlackB(img.getRGB(x, y + 1))) {
		    				return true ;
		    			}  else {
		    				return false ;
		    			}
		    		}
		    	} catch (Exception e) {
		    		e.printStackTrace() ;
		    		return false;
		    	}
		    }
	    	
	    	private boolean isContinuePoint(int x, int y) {

	    		try {
		    		if (this.fontIsBlack) {
		    			if (isBlackB(img.getRGB(x, y))
		    					|| isBlackB(img.getRGB(x, y + 1)) 
			            		|| isBlackB(img.getRGB(x, y - 1))) {
		    				return true ;
		    			}  else {
		    				return false ;
		    			}
		    		} else {
		    			if (!isBlackB(img.getRGB(x, y))
		    					|| !isBlackB(img.getRGB(x, y + 1)) 
			            		|| !isBlackB(img.getRGB(x, y - 1))) {
		    				return true ;
		    			}  else {
		    				return false ;
		    			}
		    		}
		    	} catch (Exception e) {
		    		e.printStackTrace() ;
		    		return false;
		    	}
	    	}
	    }
}
