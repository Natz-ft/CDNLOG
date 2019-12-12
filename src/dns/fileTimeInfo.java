package dns;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
 
 
 
public class fileTimeInfo {
	
	private static String file_name="F:/ftp/unzip/20190416/240_221180135008_20190416020832_1";
	private static Date lastmodfiyTimeDate;
	private static Date CreateTimeDate;
	
	public static void set_fileInfo(String filename){
		
		Path path = Paths.get(file_name);
		BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attr;
		try {
			attr = basicview.readAttributes();
			//attr.lastModifiedTime();
			
			lastmodfiyTimeDate=new Date(attr.lastModifiedTime().toMillis());
			CreateTimeDate= new Date(attr.creationTime().toMillis());
			Date vdate = new Date();
			System.out.println(" vdate	"+calLastedTime(lastmodfiyTimeDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static  int calLastedTime(Date startDate) {
		long a = new Date().getTime();
		long b = startDate.getTime();
		int c = (int)((a - b) / 1000);
		return c;
		}
	
	
	  public static int differentDays(Date date1,Date date2)
	    {
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	       int day1= cal1.get(Calendar.DAY_OF_YEAR);
	        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
	        
	        int year1 = cal1.get(Calendar.YEAR);
	        int year2 = cal2.get(Calendar.YEAR);
	        if(year1 != year2)   //同一年
	        {
	            int timeDistance = 0 ;
	            for(int i = year1 ; i < year2 ; i ++)
	            {
	                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
	                {
	                    timeDistance += 366;
	                }
	                else    //不是闰年
	                {
	                    timeDistance += 365;
	                }
	            }
	            
	            return timeDistance + (day2-day1) ;
	        }
	        else    //不同年
	        {
	            System.out.println("判断day2 - day1 : " + (day2-day1));
	            return day2-day1;
	        }
	    }
	
	//test main
	public static void main(String[] args) {
		set_fileInfo(file_name);
		System.out.println("lastModfiedTime	"+lastmodfiyTimeDate);
		System.out.println("creationTime	"+CreateTimeDate);
		
		
		
	}
}
 
