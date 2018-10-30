package excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * @author Hongten
 * @created 2014-5-18
 */
public class DbUtil {

    /**
     * @param sql
     */
    public static void insert(String sql, Student student) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName(Common.DRIVER);
            conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setString(3, student.getAge());
            ps.setString(4, String.valueOf(student.getScore()));
            boolean flag = ps.execute();
            if(!flag){
                System.out.println("Save data : No. = " + student.getNo() + " , Name = " + student.getName() + ", Age = " + student.getAge() + " succeed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    public static void insert(String sql, buglist buglist) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName(Common.DRIVER);
            conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
            ps = conn.prepareStatement(sql);
        	ps.setString(1, buglist.getIp());            //ip               
             
            ps.setString(2,buglist.getPort());          // port             
        	ps.setString(3, buglist.getProtocol());    //  protocol         
        	ps.setString(4, buglist.getService());      // service          
        	ps.setString(5,buglist.getBug_name());      // bug_name         
        	ps.setString(6, buglist.getBug_num());      // bug_num          
        	ps.setString(7,buglist.getBug_class());     // bug_class        
        	ps.setString(8, buglist.getBug_classify()); // bug_classify     
        	ps.setString(9, buglist.getApp_classify()); // app_classify     
        	ps.setString(10, buglist.getSys_classify()); // sys_classify     
        	ps.setString(11, buglist.getThreaten_classify());// threaten_classify
        	ps.setString(12, buglist.getTime_classify());//time_classify    
        	ps.setString(13, buglist.getCVE_year());// CVE_year         
        	ps.setString(14, buglist.getDev_date());// dev_date         
        	ps.setString(15, buglist.getCve_num());// cve_num          
        	ps.setString(16, buglist.getCNNVD_num());// CNNVD_num        
        	ps.setString(17, buglist.getCNVD_num());// CNCVE_num        
        	ps.setString(18, buglist.getCNVD_num());// CNVD_num         
        	ps.setString(19, buglist.getDescribe());// describe         
        	ps.setString(20, buglist.getSolve());// solve            
        	ps.setString(21, buglist.getReturn_des());// return_des   
        	ps.setInt(22, buglist.getSort());// return_des   
            
            
            
            
            boolean flag = ps.execute();
            if(!flag){
                System.out.println("Save data : IP. = " + buglist.getIp() + " , Name = " + buglist.getBug_name() + " succeed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error " + buglist.getIp() );
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List selectOne(String sql, Student student) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            Class.forName(Common.DRIVER);
            conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("no").equals(student.getNo()) || rs.getString("name").equals(student.getName())|| rs.getString("age").equals(student.getAge())){
                    list.add(1);
                }else{
                    list.add(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public static List selectOne(String sql, buglist buglist) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            Class.forName(Common.DRIVER);
            conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("ip").equals(buglist.getIp()) || rs.getString("bug_name").equals(buglist.getBug_name())){
                    list.add(1);
                }else{
                    list.add(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public static ResultSet selectAll(String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.DRIVER);
            conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return rs;
    }


}
