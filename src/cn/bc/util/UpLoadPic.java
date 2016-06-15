package cn.bc.util;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpLoadPic {

	private static String accessKeyId = "xxxx";
	private static String accessKeySecret = "xxx";
	private static String endPoint = "xxx";
	private static String path = "xxx";
	//测试
	private static String bucketName = "yocardtest";
	//生产
//	private static String bucketName = "xxx";

    public static void putObject(String key, File file) throws FileNotFoundException {

        // 初始化一个OSSClient
        OSSClient client = new OSSClient(endPoint,accessKeyId, accessKeySecret);

        // 获取指定文件的输入流
        InputStream content = new FileInputStream(file);

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();

        // 必须设置ContentLength
        meta.setContentLength(file.length());

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, path+key, content, meta);

        // 打印ETag
        System.out.println(result.getETag());
    }

    public static void main(String[] args){
    	File file = new File("C:\\Users\\sks\\Desktop\\img\\1.png");
        try {
            putObject("test.png",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
