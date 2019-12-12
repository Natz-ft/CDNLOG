package TOOLS;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileWrite {

	public static void main(String[] args) throws Exception {
		List<String> list=new ArrayList<String>();
		list.add("sss");
		list.add("nnn");
		
		
		//WriteFileByBuffered("f:/ftp/ftp.log",list,"1");

/*        FileOutputStream out = null;
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        FileWriter fw = null;

        int count = 1000000;//写文件行数

        try {
            //经过测试：FileOutputStream执行耗时:17，6，10 毫秒
            out = new FileOutputStream(new File("f:/ftp/ftp.log"));
            long begin = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                out.write("测试java 文件操作\r\n".getBytes());
            }
            out.close();
            long end = System.currentTimeMillis();
            System.out.println("FileOutputStream执行耗时:" + (end - begin) + " 毫秒");

            //经过测试：ufferedOutputStream执行耗时:1,1，1 毫秒
            outSTr = new FileOutputStream(new File("f:/ftp/ftp.log"));
            Buff = new BufferedOutputStream(outSTr);
            long begin0 = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                Buff.write("测试java 文件操作\r\n".getBytes());
            }
            Buff.flush();
            Buff.close();
            long end0 = System.currentTimeMillis();
            System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 毫秒");

            //经过测试：FileWriter执行耗时:3,9，5 毫秒
            fw = new FileWriter("f:/ftp/ftp.log");
            long begin3 = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                fw.write("测试java 文件操作\r\n");
            }
            fw.close();
            long end3 = System.currentTimeMillis();
            System.out.println("FileWriter执行耗时:" + (end3 - begin3) + " 毫秒");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
                Buff.close();
                outSTr.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

    }
	  
	public static void WriteFileByBuffered(String fileName,String line ,String mode) throws IOException{ // mode 是写文件模式  1，追加，0，重写
		 
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        if (mode.equals("1")){
        outSTr = new FileOutputStream(new File(fileName),true);//追加模式
        }
        else {
         outSTr = new FileOutputStream(new File(fileName)); // 重写模式
        }
        Buff = new BufferedOutputStream(outSTr);
        
        //bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
      
       // long begin0 = System.currentTimeMillis();
       // for (String line :list) {
        	line = line +"\r\n";
            Buff.write(line.getBytes());
       // }
        
        Buff.flush();
        Buff.close();
        
        
       // long end0 = System.currentTimeMillis();
        //System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 毫秒");
	}

}
