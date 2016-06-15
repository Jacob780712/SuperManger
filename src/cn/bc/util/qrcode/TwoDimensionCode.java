package cn.bc.util.qrcode;

import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
import java.io.InputStream;  
  



import javax.imageio.ImageIO;  
  



import jp.sourceforge.qrcode.QRCodeDecoder;  
import jp.sourceforge.qrcode.exception.DecodingFailedException;  
  

public class TwoDimensionCode {  
 
      
    /** 
     * 解析二维码（QRCode） 
     * @param imgPath 图片路径 
     * @return 
     */  
    public String decoderQRCode(File imageFile) {  
        // QRCode 二维码图片的文件  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(imageFile);  
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return content;  
    }  
      
    /** 
     * 解析二维码（QRCode） 
     * @param input 输入流 
     * @return 
     */  
    public String decoderQRCode(InputStream input) {  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(input);  
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return content;  
    }  
  
    /**
     * 
     * @param imgPath 二维码地址
     * @param fileName 文件名
     * @param filePath 暂存路径
     * @return
     * @throws Exception
     */
    public static String encodeQrCode(String imgPath,String fileName,String filePath) throws Exception {
    	TwoDimensionCode handler = new TwoDimensionCode();
    	try {
			DownLoadImg.download(imgPath, fileName, filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "error";
		}
    	File fileDir = new File(filePath);  
    	if(!fileDir.exists()){  
    		fileDir.mkdirs();  
    	}  
    	File file = new File(filePath+"/"+fileName);
    	String content = handler.decoderQRCode(file);  
    	file.delete();
        return content;
    }
    
    public static void main(String[] args) {  
    	
//        String imgPath = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGQ8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1hUc1ZMd2psUVI4NFJFWXFOaGR3AAIEw/7XVgMEAAAAAA==";  
    	String imgPath = "F:1.jpg";
    	File file = new File(imgPath);
    	TwoDimensionCode handler = new TwoDimensionCode();  
        String decoderContent = handler.decoderQRCode(file);  
        System.out.println("解析结果如下：");  
        System.out.println(decoderContent);  
        System.out.println("========decoder success!!!");  
    }  
}  