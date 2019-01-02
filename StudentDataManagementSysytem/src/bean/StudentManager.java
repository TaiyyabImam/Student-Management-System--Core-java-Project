package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import sun.util.resources.LocaleData;

public class StudentManager {
@SuppressWarnings("deprecation")
void enrollStudent()
{
	/**
	 * Used To Enroll New Student.
	 * */
	Connection conn=null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");//load the driver
				String url="jdbc:oracle:thin:@localhost:1521:XE";
				String username="system";
				String passward="system";
				
				conn=DriverManager.getConnection(url,username,passward);
				String sql="insert into Student values(?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps=conn.prepareStatement(sql);

				Student stu=new Student();
				InputStreamReader ir=new InputStreamReader(System.in);
				BufferedReader br=new BufferedReader(ir);
				System.out.println("Enter Student Details As Follows :");
				System.out.println("--------------------------------");
						String getSID="select max(regId) from Student";
				int sid;				
				PreparedStatement ps1=conn.prepareStatement(getSID);
				ResultSet rs1=ps1.executeQuery();
			if(rs1.next())
				{
				/**
				 * If Course Is Present In DataBase Used TO set Student Id Automatically with Auto Increment.
				 * */
				sid=rs1.getInt(1);
				sid=sid+1;
				}
				else
				{
				 sid=1;
				}
							
				ps.setInt(1, sid);
				System.out.println("Enter The Name Of Student :");
				stu.setStudentName(br.readLine());
				ps.setString(2, stu.getStudentName());
				System.out.println("Enter The Address Of Student :");
				stu.setStudentAddress(br.readLine());
				ps.setString(3, stu.getStudentAddress());
				System.out.println("Enter The Contact Number Of Student :");
				stu.setContactNo((Long.parseLong(br.readLine())));
				ps.setLong(4, stu.getContactNo());
				System.out.println("Enter The Fees Paid By Student In Rupees:");
				stu.setFeesPaid(Integer.parseInt(br.readLine()));
				ps.setInt(5, stu.getFeesPaid());
				System.out.println("Enter The Date of Admission of  Student (DD/MM/YYYY):");
				  Date dateOfAdmittion = new Date();/**Used TO set Date of Admission of  Student To current Date*/
				
				System.out.println(dateOfAdmittion);
				ps.setString(6,dateOfAdmittion.toString());
				   
			    Date start = new Date();
			    String startDate="";
			    while(true)
			    {
				System.out.println("Enter The Start Date of Course of Student(DD/MM/YYYY) :");
				startDate=br.readLine();
				StringTokenizer st = new StringTokenizer(startDate,"/");
				int date = Integer.parseInt(st.nextToken());
				int month = Integer.parseInt(st.nextToken());
				int year = Integer.parseInt(st.nextToken());
			

				start.setDate(date);
				start.setMonth(month-1);
				start.setYear(year-1900);
				if(start.compareTo(dateOfAdmittion)>=0){/**Check The Start date Must Be Greather Than Or Equal To Date Of Admition*/
					stu.setStartDate(startDate);

					ps.setString(7, stu.getStartDate());
					break;
				}
				else{
					System.out.println("Start date Must Be Greather Than Or Equal To Date Of Admition :"+dateOfAdmittion.toString());
				}
			    }
			
				 Date end= new Date();
				    String endDate="";
				    while(true)
				    {
				System.out.println("Enter The End Date of Course of Student (DD/MM/YYYY):");
				endDate=br.readLine();
				StringTokenizer st1 = new StringTokenizer(endDate,"/");
				int date1= Integer.parseInt(st1.nextToken());
				int month1 = Integer.parseInt(st1.nextToken());
				int year1 = Integer.parseInt(st1.nextToken());
				
				
				
				end.setDate(date1);
				end.setMonth(month1-1);
				end.setYear(year1-1900);
				if(end.after(start)){/**Check End Date Must Be Greater than start date*/
					stu.setEndDate(endDate);
					ps.setString(8, stu.getEndDate());
					break;
				}
				else{
					System.out.println("End Date Must Be Greater than start date");
				}
				    }
				System.out.println("Enter The Course Id of Student :");
				stu.setCourseId(Integer.parseInt(br.readLine()));
				CourseIdValidator courseIdValidator=new CourseIdValidator();
				boolean validate=true;
				validate=courseIdValidator.validateCourseId(stu.getCourseId());/**Used TO check Course Id Is valid Or Not*/
				if(validate==true)
				{
				ps.setInt(9, stu.getCourseId());
				
									int rows=ps.executeUpdate();
										if(rows>0){
											System.out.println("Student Is Enroll Sucessfully with enrollment Id : "+sid);/**Shows Student 
																														*	Id After Successfully Enrollment Of New Student.*/
										}
				}
					else
			    {
					System.out.println("Please Enter The Valid Course Id.");
				}
				
			    
				
			}
				
			 
			catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
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

public Student[] viewStudentDetails(int courseId)
{
	Connection conn=null;
	Student[] s = null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String username="system";
		String passward="system";
		conn=DriverManager.getConnection(url,username,passward);
		String sql="select * from Student where courseId=?";
		PreparedStatement ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ps.setInt(1,courseId);
		ResultSet rs=ps.executeQuery();
		int i=0;
		int count=0;
		if(rs.next()){
			/**
			 * Used to find total Number Of Records In Student Table.
			 * */
			count++;
		}
		s= new Student[count];
		rs.beforeFirst();
while(rs.next())/**If Record Is Persent Then Insert Records Into Student array*/
{
	s[i] = new Student();
	s[i].setRegId(rs.getInt(1));
	s[i].setStudentName(rs.getString(2));
	s[i].setStudentAddress(rs.getString(3));
	s[i].setContactNo(rs.getLong(4));
	
	s[i].setFeesPaid(rs.getInt(5));
	s[i].setDateOfAdmission(rs.getString(6));
	s[i].setStartDate(rs.getString(7));
	s[i].setEndDate(rs.getString(8));
	s[i].setCourseId(rs.getInt(9));
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
return s;
}
         
public void viewStudentDetails1(int regId)  
{
	Connection conn=null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String username="system";
		String passward="system";
		
		 conn=DriverManager.getConnection(url,username,passward);
		String sql="select * from Student where regId=?";/**Used TO get  Student Records For Specific Register Id */
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1,regId);
		ResultSet rs=ps.executeQuery();
		System.out.println("--------------------");
		System.out.println("DETAILS OF STUDENT");
		System.out.println("---------------------");
		while(rs.next())/**If Record Is Present In DataBase The Display Student Information */
		{
		
		System.out.println("Register Id -:"+rs.getInt(1));
		System.out.println("Name -:" +rs.getString(2));
		System.out.println("Address -:" +rs.getString(3));
		System.out.println("Contact No -:"  +rs.getLong(4));
		
		System.out.println("Fees Paid -:" +rs.getInt(5));
		System.out.println("Date of Admission -:"  +rs.getString(6));
		System.out.println("Start Date -:" +rs.getString(7));
		System.out.println("End Date -:"  +rs.getString(8));
		System.out.println("Course Id -:" +rs.getInt(9) );
		System.out.println("________________________");
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

}
}

