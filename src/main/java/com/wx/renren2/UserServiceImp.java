package com.wx.renren2;

import java.util.ArrayList;
import java.util.List;


public class UserServiceImp implements UserService {

	public void save(UserInfo user) {
		
		System.out.println("保存user"+user);
		DBHelper db=new DBHelper();
		String sql="insert into userinfozhihu(ranking,nickname,popularity) values(?,?,?)";
		List<String> valuelist=new ArrayList<String>();
		valuelist.add(user.getRanking());
		valuelist.add(user.getNickname());
		valuelist.add(user.getPopularity());
		
		int executeUpdate = db.executeUpdate(sql, valuelist);
		if(user==null)
		{
			db.close();
		}
		
	}

}
