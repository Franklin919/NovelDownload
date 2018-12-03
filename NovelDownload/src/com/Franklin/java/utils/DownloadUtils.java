package com.Franklin.java.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class DownloadUtils {
		
	public static ArrayList getBooklist(String url) throws IOException{
		String bookUrl = null;	//存放a标签里面的href属性值
		String str = null;	//存放书编号
		Document doc = Jsoup.connect(url).timeout(60000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get();
		Elements contentOrigin = doc.getElementsByClass("listBox").get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
		ArrayList booklist = new ArrayList<Node>();
		for(int i = 0; i < contentOrigin.size(); i++) {
			bookUrl = "https://www.qisuu.la" + contentOrigin.get(i).getElementsByTag("a").get(0).attr("href");
			booklist.add(bookUrl);
		}
		return booklist;
	}
	
	public static String getBookTitle(String url) throws IOException{
		String bookTitle = null;
		Document doc = Jsoup.connect(url).timeout(60000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get();
		Element contentOrigin = doc.getElementsByClass("detail_right").get(0).getElementsByTag("h1").get(0);
		bookTitle = subString(contentOrigin.toString(),"《","》");
		System.out.println("东西："+bookTitle);
		return bookTitle;
	}
	
	public static String getDownloadUrl(String url,String bookTitle) throws IOException{
		String DownloadUrl = null;  //存放书本下载地址
		String temp = null;
		Document doc = Jsoup.connect(url).timeout(60000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get();
		Elements contentOrigin = doc.getElementsByClass("showDown").get(0).getElementsByTag("ul").get(0).getElementsByTag("li").get(2).getElementsByTag("script");
		temp = subString(contentOrigin.toString(),"https://dzs.qisuu.la",bookTitle+".txt");
		DownloadUrl = "https://dzs.qisuu.la"+temp+bookTitle+".txt";
		System.out.println("下载地址："+DownloadUrl);
		return DownloadUrl;
	}
	
	public static String subString(String str, String strStart, String strEnd) {
        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "空";
        }
        if (strEndIndex < 0) {
            return "空";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
}
