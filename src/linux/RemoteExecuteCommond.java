package linux;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
// import com.sun.deploy.util.StringUtils;
//import com.sun.org.apache.regexp.internal.RE;

import java.io.*;

public class RemoteExecuteCommond {

    //字符编码
    private static String DEFAULTCHART ="utf-8";
    private static Connection conn;
    private String ip;
    private String userName;
    private String userPwd;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public RemoteExecuteCommond(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public RemoteExecuteCommond() {
    }

    /**
     * 远程登录Linux主机
     * @return
     * */
    public boolean login()
    {
        boolean flag =false;
       
       // Session.setConfig("StrictHostKeyChecking", "no");
        conn = new Connection(ip,22);
       
        try {
        	
            conn.connect();
            flag=conn.authenticateWithPassword(userName,userPwd);
            if(flag)
            {
                System.out.println("认证成功");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 远程执行shell脚本或命令
     * */
    public String execute(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session  = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("") || result==null){
                    result = processStdout(session.getStderr(),DEFAULTCHART);
                }
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public   String executeSuccess(String cmd)
    {
        String result="";
        try{
            if(login())
            {  
                Session session = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 解析脚本执行返回的结果集
     * */
    public static String processStdout(InputStream is,String charset)
    {
        InputStream inputStream = new StreamGobbler(is);
        StringBuffer sb = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,charset));
            String line =null;
            while ((line = br.readLine()) !=null){
                sb.append(line +"\n");
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void main(String[]args) throws Exception, Exception
    {
     
  /*      String jiamihou = "";
        String pass = DesUtil.decrypt(jiamihou);
        System.out.println(pass);
        
        String data = "Sox$1234";
        String key ="qwerrewq";
        //defaultKey = "netwxactive";
        System.out.println("加密前===>"+data);
        try {
            System.err.println(DesUtil.encrypt(data, key));
            System.err.println(DesUtil.decrypt(DesUtil.encrypt(data, key), key));
              jiamihou = DesUtil.encrypt(data);
            System.out.println("加密后===>"+jiamihou);
            System.out.println("解密后===>"+DesUtil.decrypt(jiamihou));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/    
        
        
      
        //RemoteExecuteCommond rec = new RemoteExecuteCommond("39.137.1.187","root", pass);
        RemoteExecuteCommond rec = new RemoteExecuteCommond("39.137.1.187","root", "PaTr@1234"); 
        try {
            if (rec.login()) {
                System.out.println("-------- 启动连接--------");
                
                Session session = conn.openSession();
                //session.execCommand("pwd");
                session.execCommand("cat /proc/cpuinfo | grep 'model name' |uniq ");
                String result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("")|| result==null){
                    System.out.println("--------出错啦--------");
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }
                System.out.println(result);
                session.close();

            }
           
        }catch (Exception e){
            e.printStackTrace();
        }finally{
        	 conn.close();
             
        }
    }
    
   // public static String ExecuteCommond()
}