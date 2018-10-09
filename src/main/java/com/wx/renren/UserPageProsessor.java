package com.wx.renren;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class UserPageProsessor implements PageProcessor {

	private final static Site site=Site
			.me()
			.setRetryTimes(3)
			.setSleepTime(1000)
			.addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");	
	public static PhantomJSDriver getPhantomJSDriver(){
		//设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"D:/tools/phantomjs/phantomjs-2.1.1-windows/bin/phantomjs.exe");        
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return  driver;
	}
	public void process(Page page) {
		// TODO Auto-generated method stub		 
		 WebDriver driver=getPhantomJSDriver();
		//判断请求的链接
		if(page.getUrl().regex("http://www\\.renren\\.com/").match())
		{
			long start=System.currentTimeMillis();
			System.out.println("到达登陆的页面");
			//找到登陆页面的用户名密码输入框，填写用户名和密码提交表单即可
			driver.get("http://www.renren.com/");			
			try {
				//防止页面未能及时加载出来而设置一段时间延迟
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//设置用户名，设置密码
			driver.findElement(By.id("email")).sendKeys("13330914910");
			driver.findElement(By.id("password")).sendKeys("133309");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				//截图保存
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File("D:\\temp\\baidu_selenium.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//输入验证吗？
			 BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		      String value=null;
		      System.out.println("请输入验证码：");
		      try {
		    	  value=reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(value);
			
			driver.findElement(By.id("codeTip")).sendKeys(value);
			//找到登陆按钮点击
			driver.findElement(By.id("login")).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//截图保存
				File scrFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile2, new File("D:\\temp\\baidu_selenium2.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//等5秒
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WebElement findElement = driver.findElement(By.xpath("/html"));
			
			System.out.println(findElement.getAttribute("outerHTML"));
			driver.close();//关闭浏览器
			driver.quit(); //退出浏览器
			//System.out.println("耗时 "+ (System.currentTimeMillis()-start)+" 毫秒");
			//将要继续爬取的页面添加入队列
			
			//https://www.cnblogs.com/null-qige/p/7844381.html
			
		}
		if(page.getUrl().regex("http://zhibo\\.renren\\.com/top").match())
		{
			System.out.println("到达登陆成功的页面");
		}
		
	}

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
 
}
