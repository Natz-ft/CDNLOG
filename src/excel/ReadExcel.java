package excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




/**
 * @author Hongten
 * @created 2014-5-18
 */
public class ReadExcel {
	public String Excel_path ;
	public ReadExcel (String path){
		Excel_path =path ;
	}
	

    public List<buglist> readXls() throws IOException {
        InputStream is = new FileInputStream(Excel_path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
       // Student student = null;
        buglist buglist =null ;
        List<buglist> list = new ArrayList<buglist>();
        // 循环工作表Sheet
        int i = hssfWorkbook.getNumberOfSheets();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
           
            
            if (!(hssfSheet.getSheetName().equals("漏洞信息")) ) {
                continue;
            }
            //System.out.println(hssfSheet.getSheetName());
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	
                //	HSSFCell no = hssfRow.getCell(0);
                	              
                    /*student = new Student();
                    HSSFCell no = hssfRow.getCell(0);
                    HSSFCell name = hssfRow.getCell(1);
                    HSSFCell age = hssfRow.getCell(2);
                    HSSFCell score = hssfRow.getCell(3);
                    
                    
                    student.setNo(getValue(no));
                    student.setName(getValue(name));
                    student.setAge(getValue(age));
                    student.setScore(Float.valueOf(getValue(score)));
                    list.add(student);
                    */
                	
                	buglist = new buglist();
                	
                	HSSFCell port = hssfRow.getCell(0);
                	String str = getValue(port);
                	HSSFCell protocol = hssfRow.getCell(1);
                	HSSFCell service= hssfRow.getCell(2);
                	HSSFCell bug_name = hssfRow.getCell(3);
                	HSSFCell bug_num = hssfRow.getCell(4);
                	HSSFCell bug_class = hssfRow.getCell(5);
                	HSSFCell bug_classify = hssfRow.getCell(6);
                	HSSFCell app_classify= hssfRow.getCell(7);
                	HSSFCell sys_classify = hssfRow.getCell(8);
                	HSSFCell threaten_classify= hssfRow.getCell(9);
                	HSSFCell time_classify= hssfRow.getCell(10);
                	HSSFCell CVE_year= hssfRow.getCell(11);
                	HSSFCell dev_date= hssfRow.getCell(12);
                	HSSFCell cve_num= hssfRow.getCell(13);
                	HSSFCell CNNVD_num= hssfRow.getCell(14);
                	HSSFCell CNCVE_num= hssfRow.getCell(15);
                	HSSFCell CNVD_num= hssfRow.getCell(16);
                	HSSFCell describe= hssfRow.getCell(17);
                	HSSFCell solve= hssfRow.getCell(18);
                	HSSFCell return_des= hssfRow.getCell(19);
                	
                	//Excel_path
                	//buglist.
                	 buglist.setIp(Excel_path);
                	
                	 buglist.setPort(getValue(port));
                	 
                	 buglist.setProtocol(getValue(protocol));
                	 buglist.setService(getValue(service));
                	 buglist.setBug_name(getValue(bug_name).replace("'", ""));
                	 buglist.setBug_num(getValue(bug_num));
                	 buglist.setBug_class(getValue(bug_class));
                	 buglist.setBug_classify(getValue(bug_classify));
                	 buglist.setApp_classify(getValue(app_classify));
                	 buglist.setSys_classify(getValue(sys_classify));
                	 buglist.setThreaten_classify(getValue(threaten_classify));
                	 buglist.setTime_classify(getValue(time_classify));
                	 buglist.setCVE_year(getValue(CVE_year));
                	 buglist.setDev_date(getValue(dev_date));
                	 buglist.setCve_num(getValue(cve_num));
                	 buglist.setCNNVD_num(getValue(CNNVD_num));
                	 buglist.setCNCVE_num(getValue(CNCVE_num));
                	 buglist.setCNVD_num(getValue(CNVD_num));
                	 buglist.setDescribe(getValue(describe));
                	 buglist.setSolve(getValue(solve));
                	 buglist.setReturn_des(getValue(return_des));
                	 buglist.setSort(rowNum);
                	 list.add(buglist);
                	 
                }
            }
        }
        return list;
    }
    
    public List<portList> readXls_port() throws IOException {
        InputStream is = new FileInputStream(Excel_path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
       // Student student = null;
        portList portList =null ;
        List<portList> list = new ArrayList<portList>();
        // 循环工作表Sheet
        int i = hssfWorkbook.getNumberOfSheets();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
           
          //  System.out.println(hssfSheet.getSheetName());
            
            if (!(hssfSheet.getSheetName().trim().equals("其它信息")) ) {
                continue;
            }
            //
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            
           // System.out.println("hssfSheet.getLastRowNum()"+"\t"+hssfSheet.getLastRowNum());
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	
                 
                	
                	portList = new portList();
                	HSSFCell name =  hssfRow.getCell(0);
                	 
                	String col_name ="";
                	if (name == null){
                		
                		col_name = "";
                	}
                	else 
                	{
                		  col_name =  getValue(hssfRow.getCell(0)).trim() ;
                		  
                		  
                	}
                	
                    
                	
                	 
                	
                	
                	 //System.out.println("col_name"+"\t"+col_name);
                	if (col_name.equals("端口信息")) {
                		continue;
                	}
                	
                	if (!col_name.equals("端口信息") && !col_name.equals("") ) {
                		break;
                	}
                	
           
              
                	
                	HSSFCell port = hssfRow.getCell(1); 
                	
                	if (getValue(port).equals("端口")){
                		continue;
                	}
                	
                	
                	HSSFCell protocol = hssfRow.getCell(2);
                	HSSFCell service= hssfRow.getCell(3);
                	
                	
                	portList.setId(rowNum);
                	portList.setPath(Excel_path);
                	portList.setPort(getValue(port));
                	portList.setProtocol(getValue(protocol));
                	portList.setService(getValue(service));
                	
          
                	 list.add(portList);
                	 
                }
            }
        }
        return list;
    }
    
     @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
    	 
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
                // 返回布尔类型的值
            	 
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                // 返回数值类型的值
            	 
                return String.valueOf(hssfCell.getNumericCellValue());
            } else {
                // 返回字符串类型的值
            	 
                return String.valueOf(hssfCell.getStringCellValue());
            
        }
    	 
   
}
     }
