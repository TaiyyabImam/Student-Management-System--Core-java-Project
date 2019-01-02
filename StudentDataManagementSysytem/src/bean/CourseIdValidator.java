package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseIdValidator {
	
	/**
	 * Use TO check the Entered Course Id Is Present In database
	 * or not 
	 * */
	public boolean validateCourseId(int courseId)
	{
	Connection conn=null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String username="system";
		String passward="system";
		
		conn=DriverManager.getConnection(url,username,passward);
		String sql="select courseId from Course where courseId=?";  /**Use to get avail. course Id From Data Base*/
			PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1,courseId);
		ResultSet rs=ps.executeQuery();
			
			if(rs.next())   /**If entry  Course is found in DATA BASE then return true otherwise wise return false*/
			{
				return true;
			}
			else
			{
				return false;
			}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally
	{
		if(conn!=null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	return false;
		
	}

}
