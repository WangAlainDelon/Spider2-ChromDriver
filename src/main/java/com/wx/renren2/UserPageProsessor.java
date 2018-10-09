package com.wx.renren2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import net.sourceforge.htmlunit.cyberneko.HTMLEventInfo.SynthesizedItem;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class UserPageProsessor implements PageProcessor
{
    private final static Site site=Site
    		.me()
    		.setRetryTimes(3)
    		.setSleepTime(1000)
    		.addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
    private static ChromeDriverService service;
    public static UserService userService;
    static{
		 ApplicationContext context =new ClassPathXmlApplicationContext("beans.xml");
		 userService= (UserService)context.getBean("userService");	     
	}
    public static WebDriver getChromeDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver","D:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/tools/chromedriver_win32/chromedriver.exe")) .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }
    public void process(Page page) {
		// TODO Auto-generated method stub
    	try {
			WebDriver driver = UserPageProsessor.getChromeDriver();
			if(page.getUrl().regex("http://www\\.renren\\.com/").match())
			{
				driver.get("http://www.renren.com/");
				System.out.println("到达登陆的页面");
				//找到登陆页面的用户名密码输入框，填写用户名和密码提交表单即可				
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
			     System.out.println("please input valitadecode：");
			      try {
			    	  value=reader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    System.out.println(value);
				
				driver.findElement(By.id("icode")).sendKeys(value);
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
				//登陆成功以后就要开始抽取页面的信息
//				WebElement findElement = driver.findElement(By.xpath("/html"));
//				String str = findElement.getAttribute("outerHTML");
				//返回一个元素
				//WebElement name = driver.findElement(By.xpath("//div[@class='zb-rank']/div[1]//ul/li//div[@class='user-name ellip']"));
				//返回多个元素
				List<WebElement> userinfos = driver.findElements(By.xpath("//div[@class='zb-rank']/div[1]//ul/li"));
				UserInfo userinfo=new UserInfo();
				for (int i = 0; i < userinfos.size(); i++) {
					String userinfostr = userinfos.get(i).getText();
					userinfostr = userinfostr.replace("\n", " ");
					String[] split = userinfostr.split(" ");
					System.out.println(split[0]+":"+split[1]+":"+split[2]);
					userinfo.setRanking(split[0]);
					userinfo.setNickname(split[1]);
					userinfo.setPopularity(split[2]);
					userService.save(userinfo);
				}
				//driver.close();//关闭浏览器
				driver.quit(); //退出浏览器
				service.stop();//退出后台
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
	}

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
