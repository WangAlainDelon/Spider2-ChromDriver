package com.wx.renren;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class RenRen {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		Spider spider=Spider.create(new UserPageProsessor());
		spider.setDownloader(httpClientDownloader);
		spider.addUrl("http://www.renren.com/").thread(5).run();	    
	}
    

}
