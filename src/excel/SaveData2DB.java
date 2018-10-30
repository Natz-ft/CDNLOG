package excel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import TOOLS.DBAcess;



/**
 * @author Hongten
 * @created 2014-5-18
 */
public class SaveData2DB {
	 private String exelPath;
	public SaveData2DB(String Path){
		exelPath =Path;
	}

    @SuppressWarnings({ "rawtypes" })
    public void save() throws IOException, SQLException {
    	
    	
        ReadExcel xlsMain = new ReadExcel(exelPath);
        Student student = null;
        buglist buglist =null;
        
       // List<Student> list1 = xlsMain.readXls();
        List<buglist> list =  xlsMain.readXls();
        DBAcess db = new DBAcess();
        if (db.createConnLocal())
        {
        for (int i = 0; i < list.size(); i++) {
        	buglist = list.get(i);
            List l = DbUtil.selectOne(Common.SELECT_STUDENT_SQL + "'%" + buglist.getBug_name() + "%'", buglist);
            if (!l.contains(1)) {
              
                   
                	db.insert(Common.INSERT_buglist_SQL, buglist);
                
            	
            	
            	
                //DbUtil.insert(Common.INSERT_buglist_SQL, buglist);
            } else {
                System.out.println("The Record was Exist : No. = " + student.getNo() + " , Name = " + student.getName() + ", Age = " + student.getAge() + ", and has been throw away!");
            }
        }
        }
        db.closeRs();
        db.closeStm();
        
        db.closeConn();
    }
    
    
    
    public void save_port() throws IOException, SQLException {
    	
    	
        ReadExcel xlsMain = new ReadExcel(exelPath);
       
        portList portList =null;
        
       // List<Student> list1 = xlsMain.readXls();
        List<portList> list =  xlsMain.readXls_port();
        DBAcess db = new DBAcess();
        if (db.createConnLocal())
        {
        for (int i = 0; i < list.size(); i++) {
        	portList = list.get(i);
           // List l = DbUtil.selectOne(Common.SELECT_STUDENT_SQL + "'%" + buglist.getBug_name() + "%'", portList);
           
              
                   
                	db.insert(Common.INSERT_port_SQL, portList);
                
            	
            	
            	
                //DbUtil.insert(Common.INSERT_buglist_SQL, buglist);
            }
        
        }
        db.closeRs();
        db.closeStm();
        
        db.closeConn();
    }
    
    
    
    
    
    
}