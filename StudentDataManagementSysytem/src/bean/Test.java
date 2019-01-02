package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	
	public static void main(String[] args) {
		try {
					InputStreamReader ir=new InputStreamReader(System.in);
					BufferedReader br=new BufferedReader(ir);
					int ch;
					String choice;
			
					do
						{
						do
						
					{
			
					
					System.out.println("Student Data Base Management  System :");
					System.out.println("---Menu---");
					System.out.println("1.Launch New Course.");
					System.out.println("2.Enroll Student.");
					System.out.println("3.View Course Details.");
					System.out.println("4.View Student Details.");
					System.out.println("5.Exit");
					System.out.println("Please Enter Your Choice:"); 
			
								//Course c=new Course();
								
								StudentCourseService newStudentService=new StudentCourseService();
								StudentManager newStudentManager=new StudentManager();
					// int ch;
					ch=Integer.parseInt(br.readLine());
					switch(ch)
					{
					case 1:
									newStudentService.launchCourse();
									break;
					case 2:
									newStudentManager.enrollStudent();
									break;
					case 3:
						int ch1;
						do
						{
									System.out.println("1.View All Course Details.");
											System.out.println("2.View Specific Course Details.");
											System.out.println("3.Menu");
											
											ch1=Integer.parseInt(br.readLine());
											switch(ch1)
											{
											case 1:
												Course[] allCourse;
												allCourse=newStudentService.fetchAllCourseDetails();
												System.out.println("Details Of  Course");
												int k=0;
												while(k<allCourse.length)
												{
													
													System.out.println("Course Id:"+allCourse[k].getCourseId());
												    System.out.println("Course Name :"+allCourse[k].getCourseName());
												    System.out.println("Course Duration :"+allCourse[k].getDuration());
												    System.out.println("Course Fees :"+allCourse[k].getFees());
												    k++;
												}
													break;
											case 2:
												
												Course oneCourse=new Course();
												System.out.println("Enter The Course Id :");
												int id=Integer.parseInt(br.readLine());
												oneCourse=newStudentService.fetchCourseDetails(id);
												if(oneCourse.getCourseId()>0)
												{
													System.out.println("Detail for Course Id :"+ id);
												
													
													System.out.println("Course Id:"+oneCourse.getCourseId());
													
												    System.out.println("Course Name :"+oneCourse.getCourseName());
												    System.out.println("Course Duration :"+oneCourse.getDuration());
												    System.out.println("Course Fees :"+oneCourse.getFees());
												}
												else
												{
												System.out.println("Course ID Is Not Valid.");	
												}
												
												break;
												
											}
												}while(ch1<3);
											
											break;
					case 4:
						int ch2;
										do{
											System.out.println("1.View Student Details By Using Reister Id.");
											System.out.println("2.View  Student Details By Using Course ID ");
											System.out.println("3.Menu");
											
											ch2=Integer.parseInt(br.readLine());
											
											
											switch(ch2)
											{
											case 1:
												
												System.out.println("Enter The Reg  Id :");
												int c1=Integer.parseInt(br.readLine());
												
												newStudentManager.viewStudentDetails1(c1);
													break;
											case 2:
												Student[] s;
												System.out.println("Enter The Course Id :");
												int courseId=Integer.parseInt(br.readLine());
												boolean status;
												CourseIdValidator cvalidator=new CourseIdValidator();
										status=cvalidator.validateCourseId(courseId);
										if(status)
										{
												s=newStudentManager.viewStudentDetails(courseId);
												for(int i=0;i<s.length;i++)
												{
													System.out.println("RegId :"+s[i].getRegId());
													System.out.println("Name :"+s[i].getContactNo());
													System.out.println("Address:"+s[i].getStudentAddress());
													System.out.println("Contact No :"+s[i].getContactNo());
													
													System.out.println("Feed Paid :"+s[i].getFeesPaid());
													System.out.println("Date Of Admition :"+s[i].getDateOfAdmission());
													System.out.println("Start Date :"+s[i].getStartDate());
													System.out.println("End Date :"+s[i].getEndDate());
													System.out.println("CourseId:"+s[i].getCourseId());
													
												}
												
										}
										else{
												System.out.println("Invalid CourseId:"+courseId);
										}

												break;
												
											}
											}while(ch2!=3);
										
						break;
								
					
					
					}
					}while(ch!=5);
						System.out.println("Confirm Exit!!!!!(yes/no)");
						choice=br.readLine();
						}while(choice.equals("no"));
					
						
					System.out.println("thanks.....");
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
		
	}

	}


