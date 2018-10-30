package excel;

public class Common {                                                                                                              
    
    // connect the database                                                                                                     
    public static final String DRIVER = "com.mysql.jdbc.Driver";                                                                
    public static final String DB_NAME = "dirxu";                                                                                
    public static final String USERNAME = "root";                                                                               
    public static final String PASSWORD = "PaTr@1234";                                                                               
    public static final String IP = "localhost";                                                                            
    public static final String PORT = "3306";                                                                                   
    public static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB_NAME+"?useSSL=false&characterEncoding=utf8";                                         
                                                                                                                                
    // common                                                                                                                   
    public static final String EXCEL_PATH = "F://student.xls";                                                             
                                                                                                                                
    // sql                                                                                                                      
    public static final String INSERT_STUDENT_SQL = "insert into student_info(no, name, age, score) values(?, ?, ?, ?)";        
    
    public static final String INSERT_buglist_SQL = "insert into aaa_group (ip,port,protocol,service,bug_name,bug_num,bug_class,bug_classify,app_classify,sys_classify,threaten_classify,time_classify,CVE_year,dev_date,cve_num,CNNVD_num,CNCVE_num,CNVD_num,vdescribe,solve,return_des,sort) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    public static final String INSERT_port_SQL = "insert into aaa_group_port (path,port,protocol,service,id) values (?,?,?,?,?)";
    
    
    
    
    public static final String UPDATE_STUDENT_SQL = "update student_info set no = ?, name = ?, age= ?, score = ? where id = ? ";
    public static final String SELECT_STUDENT_ALL_SQL = "select id,no,name,age,score from student_info";                        
    public static final String SELECT_STUDENT_SQL = "select * from student_info where name like ";                              
}                                                                                                                               
