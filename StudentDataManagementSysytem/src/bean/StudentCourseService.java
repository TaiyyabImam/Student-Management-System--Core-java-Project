package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseService {

	InputStreamReader ir=new InputStreamReader(System.in);
	BufferedReader br=new BufferedReader(ir);

	
	public void launchCourse(){
		/**
		 * Used to create New Course. 
		 * */
		
		Connection  conn=null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");//load the driver
				String url="jdbc:oracle:thin:@localhost:1521:XE";
				String username="system";
				String passward="system";
				
				conn=DriverManager.getConnection(url,username,passward);
				String sql="insert into Course values(?,?,?,?)";  /**  Insert Value In DataBase*/
				PreparedStatement ps=conn.prepareStatement(sql);
				

				Course c=new Course();
				System.out.println("");
				System.out.println("Enter course details:");
				System.out.println("----------------------");
				String getCID="select max(courseId) from Course";/**Maximum COurse Id From Course Table*/
				int cid;				
				PreparedStatement ps1=conn.prepareStatement(getCID);
				ResultSet rs1=ps1.executeQuery();
			if(rs1.next())   				
				{
				cid=rs1.getInt(1);/**If Course Id Is Available Then Increment By 1*/
				cid=cid+1;
				}
				else/**If Course Id Not Is Available Theny Set Id=1*/
				{
				 cid=1;
				}
				ps.setInt(1,cid);
				System.out.println("Enter Course Name :");
				System.out.println("Course Name Should be (C,Java,J2EE,Spring,Hibernate).");
				CourceName courseName=new CourceName();
				String[] s=courseName.getCourceName();
			 String cname=br.readLine();
			 int flag = 0;
			 for(int i=0;i<5;i++)
			 {
						  	if(s[i].equalsIgnoreCase(cname))/** Use To check The Entered Course Name is Valid Or Not*/
							{
								
								ps.setString(2,cname);
				
								System.out.println("Enter Course Duration in Months:");
								c.setDuration(Integer.parseInt(br.readLine()));
								ps.setInt(3,c.getDuration());
								System.out.println("Enter Course Fees In Rupees:");
								c.setFees(Integer.parseInt(br.readLine()));
								ps.setInt(4,c.getFees());
								int rows=ps.executeUpdate();
								if(rows>0) /**Used to check Record Is Inserted In DataBase Or Not*/
								{
								flag=1;
								}
								if(flag==1)
									{
									System.out.println(" New Course is Launched Successfully With Course ID  -:"+cid);/**Shows The Course Id When Course Is successfully Created */
									}
				else{
					System.out.println("You Have Enter Invalid Course Name!!!!!!!!!!!");
				}
				
				}			  
			  	
				
			 }
			 	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 catch (IOException e) {
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
	}
	
	
	public Course[] fetchAllCourseDetails()
	{
Connection conn=null;
Course[] c=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String username="system";
			String passward="system";
			
			conn=DriverManager.getConnection(url,username,passward);
			
			String sql="select * from Course";
			PreparedStatement ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=ps.executeQuery();
				int i=0;
				int count=0;
				while(rs.next()){   /**Find Total Numbers Of Records In DataBase*/
					count++;
					
				}
				c= new Course[count];/**Create Course reference Array of Size Count*/
				rs.beforeFirst();
				
		while(rs.next())	
		{
			/**
			 * used to set records from database to course Object Array.
			 * */
			c[i] = new Course();
			c[i].setCourseId(rs.getInt(1));
			c[i].setCourseName(rs.getString(2));
			c[i].setDuration(rs.getInt(3));
			c[i].setFees(rs.getInt(4));
				i++;
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
		return c;
	}
	
	
	
public Course fetchCourseDetails(int courseId)
{
	Course c=new Course();
	Connection conn=null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String username="system";
		String passward="system";
		
		conn=DriverManager.getConnection(url,username,passward);

		String sql="select * from Course where courseId=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1,courseId);
		ResultSet rs=ps.executeQuery();
		//if(rs.next())
	//	{
			while(rs.next())
			{
				/**
				 * used to set records from database to course Object.
				 * */
				c.setCourseId(rs.getInt(1));
				c.setCourseName(rs.getString(2));
				c.setDuration(rs.getInt(3));
				c.setFees(rs.getInt(4));
				
			}
		//}
		
		
	
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
	return c;
}




public Student[] fetchStudentsDetails(int courseId)
{
	
	
	/**Finds Student Enroll For  Specific Course */
	Student[] s=new Student[5];
	Connection conn=null;
	
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String username="system";
		String passward="system";
		
		conn=DriverManager.getConnection(url,username,passward);
		String sql="select * from Student where courseId=?";
		
		
		PreparedStatement ps=conn.prepareStatement(sql);
	
		ps.setInt(1,courseId);
		ResultSet rs=ps.executeQuery();
		
		while(rs.next())
		{
			
				if(rs.getInt(5)==courseId)
				{
					for(int i=0;i<5;i++)
					{
						s[i].setRegId(rs.getInt(1));
						s[i].setStudentName(rs.getString(2));
						s[i].setStudentAddress(rs.getString(3));
						s[i].setContactNo(rs.getLong(4));
						s[i].setCourseId(rs.getInt(5));
						s[i].setFeesPaid(rs.getInt(6));
						s[i].setDateOfAdmission(rs.getString(7));
						s[i].setStartDate(rs.getString(8));
						s[i].setEndDate(rs.getString(9));
					}
				}
				else
				{
					System.out.println("There is No Student For Course Id:"+courseId);
				}
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
	return s;
}
}
