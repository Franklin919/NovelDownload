package com.Franklin.java.NovelDownload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.nodes.Node;

import com.Franklin.java.utils.DownloadUtils;

public class DownloadTest {

	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(System.in);
			String bookSort = null;
			String filePath = null;
			String bookPath = null;
			String bookTitle = null;
			String bookUrl = null;
			String method = "GET";
			System.out.println("Franklin小说爬虫系列，Github : Franklin919");
			System.out.println("本爬虫不得用于商业用途,仅做学习交流");
			System.out.println("【1:玄幻奇侠；2:女频言情；3:现代都市；4:科幻灵异；5:美文同人；6:游戏竞技】");
			System.out.println("请选择要爬取的小说类型：（输入编号即可）");
			int sortNum = sc.nextInt();
			String sortNum2 = null;
			switch(sortNum) {
				case 1:
					bookSort = "玄幻奇侠";
					sortNum2 = "01";
				break;
				case 2:
					bookSort = "女频言情";
					sortNum2 = "03";
					break;
				case 3:
					bookSort = "现代都市";
					sortNum2 = "04";
					break;
				case 4:
					bookSort = "科幻灵异";
					sortNum2 = "07";
					break;
				case 5:
					bookSort = "美文同人";
					sortNum2 = "08";
					break;
				case 6:
					bookSort = "游戏竞技";
					sortNum2 = "06";
					break;
				default:
					break;
			}
			for(int page = 1; page < 50; page++) {
				ArrayList booklist = new ArrayList<Node>(DownloadUtils.getBooklist("https://www.qisuu.la/soft/sort"+ sortNum2+"/index_"+page+".html"));
				for (int i = 0; i < booklist.size(); i++) {
					FileOutputStream fileOut = null;  
				    HttpURLConnection conn = null;  
				    InputStream inputStream = null;
				    RandomAccessFile randomAccessFile = null;
					bookUrl = booklist.get(i).toString();	//拿到书本的主页面
					bookTitle = DownloadUtils.getBookTitle(booklist.get(i).toString());
					System.out.println("正在下载书本:"+bookTitle+"，书本主页地址："+booklist.get(i));
					filePath = "/Users/franklin919/Documents/小说/"+bookSort;
					bookPath = filePath+"/"+bookTitle+".txt";;
					File bookFolder = new File(filePath);
					File bookFile = new File(bookPath);
					if(!bookFolder.exists()) {
						bookFolder.mkdirs();
					};
					if(!bookFile.exists()){
							try {
								// 建立链接  
						         URL httpUrl=new URL(DownloadUtils.getDownloadUrl(bookUrl,bookTitle));  
						         conn=(HttpURLConnection) httpUrl.openConnection();  
						         //以Post方式提交表单，默认get方式  
						         conn.setRequestMethod(method);
						         conn.setDoInput(true);    
						         conn.setDoOutput(true);  
						         // post方式不能使用缓存   
						         conn.setUseCaches(false);  
						         //连接指定的资源   
						         conn.connect();  
						         //获取网络输入流  
						         inputStream=conn.getInputStream();  
						         BufferedInputStream bis = new BufferedInputStream(inputStream);  
						         //判断文件的保存路径后面是否以/结尾  
						         if (!filePath.endsWith("/")) {  
						             filePath += "/";  
						         }  
						         //写入到文件（注意文件保存路径的后面一定要加上文件的名称）  
						         fileOut = new FileOutputStream(filePath+bookTitle+".txt");  
						         BufferedOutputStream bos = new BufferedOutputStream(fileOut);  
						           
						         byte[] buf = new byte[4096];  
						         int length = bis.read(buf);
						         
						         int progres = 0; //用于保存当前进度（具体进度）
					             int maxProgres = conn.getContentLength();//获取文件
					             randomAccessFile = new RandomAccessFile(bookFile, "rwd");
					             randomAccessFile.setLength(maxProgres);//设置文件大小
					             int unit = maxProgres / 100;//将文件大小分成100分，每一分的大小为unit
					             int unitProgress = 0; //用于保存当前进度(1~100%)
					                
						         //保存文件  
						         while(length != -1)  
						         {  
						             bos.write(buf, 0, length);  
						             length = bis.read(buf);  
						             progres += length;//保存当前具体进度
					                 int temp = progres / unit; //计算当前百分比进度
					                 if (temp >= 1 && temp > unitProgress) {//如果下载过程出现百分比变化
					                      unitProgress = temp;//保存当前百分比
					                      System.out.println("正在下载中..." + unitProgress + "%");
					                 }
						         }
						         bos.close();  
						         bis.close();  
						         conn.disconnect();  
							} catch (Exception e) {
								System.out.println("下载异常！！《"+bookTitle+"》下载失败");
								e.printStackTrace();
								continue;
							}
							System.out.println("书本《"+bookTitle+"》下载完毕");
							System.out.println();
					}else {
						System.out.println("书本："+bookTitle+"已存在，如要下载请手动删除文件；");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
