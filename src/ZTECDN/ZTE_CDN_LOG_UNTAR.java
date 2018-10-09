package ZTECDN;

import TOOLS.DBAcess;
import TOOLS.SetLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

public abstract class ZTE_CDN_LOG_UNTAR
{
  private static final String BASE_DIR = "";
  private static final String PATH = File.separator;
  private static final int BUFFER = 1024;
  private static final String EXT = ".tar";
  
  public static void archive(String srcPath, String destPath)
    throws Exception
  {
    File srcFile = new File(srcPath);
    
    archive(srcFile, destPath);
  }
  
  public static void archive(File srcFile, File destFile)
    throws Exception
  {
    TarArchiveOutputStream taos = new TarArchiveOutputStream(
      new FileOutputStream(destFile));
    
    archive(srcFile, taos, "");
    
    taos.flush();
    taos.close();
  }
  
  public static void archive(File srcFile)
    throws Exception
  {
    String name = srcFile.getName();
    String basePath = srcFile.getParent();
    String destPath = basePath + File.separator + name + ".tar";
    archive(srcFile, destPath);
  }
  
  public static void archive(File srcFile, String destPath)
    throws Exception
  {
    archive(srcFile, new File(destPath));
  }
  
  public static void archive(String srcPath)
    throws Exception
  {
    File srcFile = new File(srcPath);
    
    archive(srcFile);
  }
  
  private static void archive(File srcFile, TarArchiveOutputStream taos, String basePath)
    throws Exception
  {
    if (srcFile.isDirectory()) {
      archiveDir(srcFile, taos, basePath);
    } else {
      archiveFile(srcFile, taos, basePath);
    }
  }
  
  private static void archiveDir(File dir, TarArchiveOutputStream taos, String basePath)
    throws Exception
  {
    File[] files = dir.listFiles();
    if (files.length < 1)
    {
      TarArchiveEntry entry = new TarArchiveEntry(basePath + 
        dir.getName() + PATH);
      
      taos.putArchiveEntry(entry);
      taos.closeArchiveEntry();
    }
    File[] arrayOfFile1;
    int j = (arrayOfFile1 = files).length;
    for (int i = 0; i < j; i++)
    {
      File file = arrayOfFile1[i];
      
      archive(file, taos, basePath + dir.getName() + PATH);
    }
  }
  
  private static void archiveFile(File file, TarArchiveOutputStream taos, String dir)
    throws Exception
  {
    TarArchiveEntry entry = new TarArchiveEntry(dir + file.getName());
    
    entry.setSize(file.length());
    
    taos.putArchiveEntry(entry);
    
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
      file));
    
    byte[] data = new byte['?'];
    int count;
    while ((count = bis.read(data, 0, 1024)) != -1)
    {
      //int count;
      taos.write(data, 0, count);
    }
    bis.close();
    
    taos.closeArchiveEntry();
  }
  
  public static void dearchive(File srcFile)
    throws Exception
  {
    String basePath = srcFile.getParent();
    dearchive(srcFile, basePath);
  }
  
  public static void dearchive(File srcFile, File destFile)
    throws Exception
  {
    TarArchiveInputStream tais = new TarArchiveInputStream(
      new FileInputStream(srcFile));
    dearchive(destFile, tais);
    
    tais.close();
  }
  
  public static void dearchive(File srcFile, String destPath)
    throws Exception
  {
    dearchive(srcFile, new File(destPath));
  }
  
  private static void dearchive(File destFile, TarArchiveInputStream tais)
    throws Exception
  {
    TarArchiveEntry entry = null;
    while ((entry = tais.getNextTarEntry()) != null)
    {
      String dir = destFile.getPath() + File.separator + entry.getName();
      
      File dirFile = new File(dir);
      
      fileProber(dirFile);
      if (entry.isDirectory()) {
        dirFile.mkdirs();
      } else {
        dearchiveFile(dirFile, tais);
      }
    }
  }
  
  public static void dearchive(String srcPath)
    throws Exception
  {
    File srcFile = new File(srcPath);
    
    dearchive(srcFile);
  }
  
  public static boolean dearchive(String srcPath, String destPath)
  {
    boolean b = false;
    try
    {
      File srcFile = new File(srcPath);
      dearchive(srcFile, destPath);
      b = true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return b;
  }
  
  private static void dearchiveFile(File destFile, TarArchiveInputStream tais)
    throws Exception
  {
    BufferedOutputStream bos = new BufferedOutputStream(
      new FileOutputStream(destFile));
    
    byte[] data = new byte['?'];
    int count;
    while ((count = tais.read(data, 0, 1024)) != -1)
    {
     // int count;
      bos.write(data, 0, count);
    }
    bos.close();
  }
  
  private static void fileProber(File dirFile)
  {
    File parentFile = dirFile.getParentFile();
    if (!parentFile.exists())
    {
      fileProber(parentFile);
      
      parentFile.mkdir();
    }
  }
  
  public static void dearchive()
  {
    DBAcess db = new DBAcess();
    if (db.createConnLocal())
    {
      String sql = "select  fileName,path  from cdn_zte_log_unzip where ( status='' or status is null )  order by fileName desc   limit 3 ";
      db.query(sql);
      while (db.next())
      {
        String fileName = db.getValue("fileName");
        String path = db.getValue("path");
        try
        {
          System.out.println("UNTAR FILEL:\t" + path + File.separator + fileName.replace(".gz", ""));
          if (dearchive(path + File.separator + fileName.replace(".gz", ""), path.replace("\\", "\\\\")))
          {
            SetLog.setlog("cdn_zte_log_untar", fileName, path.replace("\\", "\\\\"));
            SetLog.updateStatus("cdn_zte_log_unzip", fileName, "YES");
          }
          else
          {
            SetLog.updateStatus("cdn_zte_log_unzip", fileName, "ERROR");
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      db.closeRs();
      db.closeStm();
    }
    db.closeConn();
  }
  
  public static void dearchivefile(String path, String fileName)
  {
    try
    {
      System.out.println("UNTAR FILEL:\t" + path + File.separator + fileName.replace(".gz", ""));
      if (dearchive(path + File.separator + fileName.replace(".gz", ""), path.replace("\\", "\\\\")))
      {
        SetLog.setlog("cdn_zte_log_untar", fileName, path.replace("\\", "\\\\"));
        SetLog.updateStatus("cdn_zte_log_unzip", fileName, "YES");
      }
      else
      {
        SetLog.updateStatus("cdn_zte_log_unzip", fileName, "ERROR");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args)
    throws Exception
  {
    for (;;)
    {
      dearchive();
      Thread.sleep(10000L);
    }
  }
}
