package com.wx.renren2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBHelper {

	private static final String Driver_Class="com.mysql.jdbc.Driver";
	private static final String Driver_Url="jdbc:mysql://192.168.42.129:3306/wxmysql?characterEncoding=UTF-8";
	private static final String UserNmme="root";
	private static final String Password="***";
	
	private static Connection conn=null;
	private PreparedStatement predtatement=null;
	private ResultSet rst=null;
	
	public DBHelper()
	{
		conn=getConnection();
	}
	public Connection getConnection()
	{
		if(conn==null)
		{
			try {
				Class.forName(Driver_Class);
				conn=DriverManager.getConnection(Driver_Url, UserNmme, Password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	public void close() {
        try {
            if (predtatement != null) {
                this.predtatement.close();
            }
            if (rst != null) {
                this.rst.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("Close connection success.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	 public int executeUpdate(String sql, List<String> sqlValues) {
	        int result = -1;
	        try {
	        	predtatement = conn.prepareStatement(sql);
	            if (sqlValues != null && sqlValues.size() > 0) {
	                setSqlValues(predtatement, sqlValues);
	            }
	            result = predtatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return result;
	 }
	 private void setSqlValues(PreparedStatement pst, List<String> sqlValues) {
	        for (int i = 0; i < sqlValues.size(); i++) {
	            try {
	                pst.setObject(i + 1, sqlValues.get(i));
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
}
