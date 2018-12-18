package com.taotao.manager.controller;

import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.pojo.PicUploadResult;

@Controller
@RequestMapping("pic/upload")
public class PicUploadController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private static String[] TYPE = {".jpg", ".jpeg", ".bmp", ".png", ".gif"};
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public PicUploadResult upload(MultipartFile uploadFile) throws Exception {
		// 标志
		boolean flag = false;
		// 初始化返回值
		PicUploadResult picUploadResult = new PicUploadResult();
		picUploadResult.setError(1);
		
		// 校验后缀合法
		for (String t : TYPE) {
			String originalFilename = uploadFile.getOriginalFilename();
			if (StringUtils.endsWithIgnoreCase(originalFilename, t)) {
				flag = true;
				break;
			}
		}
		// 如果校验失败，返回结果
		if (!flag) return picUploadResult;
		// 重置标志
		flag = false;
		// 校验图片内容
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			if (image != null) {
				picUploadResult.setWidth(String.valueOf(image.getWidth()));
				picUploadResult.setHeight(String.valueOf(image.getHeight()));
				flag = true;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 校验成功, 上传图片
		if (flag) {
			// 加载tracker配置文件
			ClientGlobal.init(System.getProperty("user.dir") + "/src/main/resources/tracker.conf");
			// 创建trackerclient对象
			TrackerClient trackerClient = new TrackerClient();
			// 得到trackerServer对象
			TrackerServer trackerServer = trackerClient.getConnection();
			// 声明
			StorageServer storageServer = null;
			// 得到storageclient对象
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 上传图片
			String ext = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");
			String[] results = storageClient.upload_file(uploadFile.getBytes(), ext, null);
			// 拼接url
			String picUrl = this.IMAGE_SERVER_URL + results[0] + "/" + results[1];
			picUploadResult.setUrl(picUrl);
			picUploadResult.setError(0);
		}
		return picUploadResult;
		
	}
}
