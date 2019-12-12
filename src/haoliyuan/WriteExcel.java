package haoliyuan;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import TOOLS.DBAccess_new;
import TOOLS.getProperties;

public class WriteExcel {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    
	public static String driver = getProperties.getPropertie("mysqldriver").trim();
	public static String url = getProperties.getPropertie("haoliyuanUrl").trim();
	public static String user = getProperties.getPropertie("localuser").trim();
	public static String passwd = getProperties.getPropertie("localpassword").trim();
    
    public static String excelFile = getProperties.getPropertie("excelFile").trim();
    
    public static void main(String[] args) {
    	List<EntityTarget> list = 	getTrarget ();
    	
    	writeExcel(list,excelFile);
       
        
    }
    /*
     * country_rate,province_rate,province_evenness,country_evenness,city_rate,city_avg,province_lost,province_delay,province_fluctuates,country_delay,country_lost,chinaunicom_delay,chinaunicom_lost,chinatelecom_delay,chinatelecom_lost,internet_delay,internet_lost,webpage_delay,webpage_success,chinamobile_lost,chinamobile_delay ,chinaunicom_lost ,chinaunicom_delay,chinatelecom_lost  ,chinatelecom_delay                                                                                                                                                                                                                             
     * 
     * */
    public static void writeExcel(List<EntityTarget> dataList,String finalXlsxPath){
        OutputStream out = null;
        
        try {
        	
            // 获取总列数
        
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
           
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            
            /*
             * 设计单元格样式
             * */
    		CellStyle cellStyle =  workBook.createCellStyle();
    		
    		cellStyle.setAlignment(HorizontalAlignment.CENTER);
    		cellStyle.setBorderBottom(BorderStyle.THIN);
    		cellStyle.setBorderLeft(BorderStyle.THIN);
    		cellStyle.setBorderRight(BorderStyle.THIN);
    		cellStyle.setBorderTop(BorderStyle.THIN);
            
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 5; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 5);
                // 得到要插入的每一条记录
                EntityTarget dataTarget = dataList.get(j);

                // 数据日期	
                Cell cell=row.createCell(0);
                cell.setCellValue(dataTarget.getVdate());
                cell.setCellStyle(cellStyle);

                //CMNET省网-CMNET省际链路忙时带宽利用率;
                cell=row.createCell(1);
                cell.setCellValue(dataTarget.getB());
                cell.setCellStyle(cellStyle);

                //CMNET省网-CMNET省网、双跨城域网上联骨干网链路忙时带宽利用率;
                cell=row.createCell(2);
                cell.setCellValue(dataTarget.getC());
                cell.setCellStyle(cellStyle);

                //CMNET省内中继链路忙时带宽利用率;
                cell=row.createCell(3);
                cell.setCellValue(dataTarget.getD());
                cell.setCellStyle(cellStyle);

                //CMNET省网至骨干新平面的双通道路由发布一致率;
                cell=row.createCell(4);
                cell.setCellValue(dataTarget.getE());
                cell.setCellStyle(cellStyle);

                //CMNET省网至骨干新平面的双通道流量均衡度;
                cell=row.createCell(5);
                cell.setCellValue(dataTarget.getF());
                cell.setCellStyle(cellStyle);

                //CMNET双跨城域网至骨干新平面的双通道流量均衡度;
                cell=row.createCell(6);
                cell.setCellValue(dataTarget.getG());
                cell.setCellStyle(cellStyle);

                //CMNET城域网链路忙时带宽利用率;
                cell=row.createCell(7);
                cell.setCellValue(dataTarget.getH());
                cell.setCellStyle(cellStyle);

                //城域数据网带宽超限占比（忙时平均值）;
                cell=row.createCell(8);
                cell.setCellValue(dataTarget.getI());
                cell.setCellStyle(cellStyle);

                //BRAS地址池利用率预警占比;
                cell=row.createCell(9);
                cell.setCellValue(dataTarget.getJ());
                cell.setCellStyle(cellStyle);

                //CMNET省内链路拨测链路丢包率;
                cell=row.createCell(10);
                cell.setCellValue(dataTarget.getK());
                cell.setCellStyle(cellStyle);

                //CMNET省内链路拨测链路时延;
                cell=row.createCell(11);
                cell.setCellValue(dataTarget.getL());
                cell.setCellStyle(cellStyle);

                //CMNET省内链路拨测链路抖动;
                cell=row.createCell(12);
                cell.setCellValue(dataTarget.getM());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路时延（普天）;
                cell=row.createCell(13);
                cell.setCellValue(dataTarget.getN());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路丢包率（普天）;
                cell=row.createCell(14);
                cell.setCellValue(dataTarget.getO());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路时延（飞思达）;
                cell=row.createCell(15);
                cell.setCellValue(dataTarget.getP());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路丢包率（飞思达）;
                cell=row.createCell(16);
                cell.setCellValue(dataTarget.getQ());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路时延（普天）;
                cell=row.createCell(17);
                cell.setCellValue(dataTarget.getR());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路丢包率（普天）;
                cell=row.createCell(18);
                cell.setCellValue(dataTarget.getS());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路时延（飞思达）;
                cell=row.createCell(19);
                cell.setCellValue(dataTarget.getT());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路丢包率（飞思达）;
                cell=row.createCell(20);
                cell.setCellValue(dataTarget.getU());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路时延（普天）;
                cell=row.createCell(21);
                cell.setCellValue(dataTarget.getV());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路丢包率（普天）;
                cell=row.createCell(22);
                cell.setCellValue(dataTarget.getW());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路时延（飞思达）;
                cell=row.createCell(23);
                cell.setCellValue(dataTarget.getX());
                cell.setCellStyle(cellStyle);

                //CMNET省际链路丢包率（飞思达）;
                cell=row.createCell(24);
                cell.setCellValue(dataTarget.getY());
                cell.setCellStyle(cellStyle);

                //网间网络层时延（ms）;
                cell=row.createCell(25);
                cell.setCellValue(dataTarget.getZ());
                cell.setCellStyle(cellStyle);

                //网间网络层丢包率(%);
                cell=row.createCell(26);
                cell.setCellValue(dataTarget.getAA());
                cell.setCellStyle(cellStyle);

                //网间网页主要展现时延(s);
                cell=row.createCell(27);
                cell.setCellValue(dataTarget.getAB());
                cell.setCellStyle(cellStyle);

                //网间网页展现成功率(%);
                cell=row.createCell(28);
                cell.setCellValue(dataTarget.getAC());
                cell.setCellStyle(cellStyle);

                //家客单用户网间成本（元/月）;
                cell=row.createCell(29);
                cell.setCellValue(dataTarget.getAD());
                cell.setCellStyle(cellStyle);

                //骨干分摊流量超过基准流量占比（%）;
                cell=row.createCell(30);
                cell.setCellValue(dataTarget.getAE());
                cell.setCellStyle(cellStyle);

                //付费网间流量占比（%）;
                cell=row.createCell(31);
                cell.setCellValue(dataTarget.getAF());
                cell.setCellStyle(cellStyle);

                //省网互联出口中断时长;
                cell=row.createCell(32);
                cell.setCellValue(dataTarget.getAG());
                cell.setCellStyle(cellStyle);

                //省网出口忙时带宽利用率;
                cell=row.createCell(33);
                cell.setCellValue(dataTarget.getAH());
                cell.setCellStyle(cellStyle);

                //省网互联出口结算单价;
                cell=row.createCell(34);
                cell.setCellValue(dataTarget.getAI());
                cell.setCellStyle(cellStyle);

                //付费网间流量同比增幅;
                cell=row.createCell(35);
                cell.setCellValue(dataTarget.getAJ());
                cell.setCellStyle(cellStyle);

                //设备脱网时长;
                cell=row.createCell(36);
                cell.setCellValue(dataTarget.getAK());
                cell.setCellStyle(cellStyle);

                //省内链路中断次数;
                cell=row.createCell(37);
                cell.setCellValue(dataTarget.getAL());
                cell.setCellStyle(cellStyle);

                //省内链路中断次数比;
                cell=row.createCell(38);
                cell.setCellValue(dataTarget.getAM());
                cell.setCellStyle(cellStyle);

                //峰值带宽利用率超过50%AR-CE链路数;
                cell=row.createCell(39);
                cell.setCellValue(dataTarget.getAN());
                cell.setCellStyle(cellStyle);

                //IP地址信息准确性核查;
                cell=row.createCell(40);
                cell.setCellValue(dataTarget.getAO());
                cell.setCellStyle(cellStyle);

                //IP地址信息准确性核查;
                cell=row.createCell(41);
                cell.setCellValue(dataTarget.getAP());
                cell.setCellStyle(cellStyle);
                
            }
     		CellRangeAddress cra =new CellRangeAddress(4, 4+dataList.size(), 0, 41); // 起始行, 终止行, 起始列, 终止列
    		//sheet.addMergedRegion(cra);
    		
    		// 使用RegionUtil类为合并后的单元格添加边框
    		RegionUtil.setBorderBottom(1, cra, sheet); // 下边框
    		RegionUtil.setBorderLeft(1, cra, sheet); // 左边框
    		RegionUtil.setBorderRight(1, cra, sheet); // 有边框
    		RegionUtil.setBorderTop(1, cra, sheet); // 上边框
    		
            
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
    
    public static List<EntityTarget> getTrarget() {
    	List<EntityTarget> list = new ArrayList<EntityTarget>();
    	
    	  String DBInfo = driver + "|" + url + "|" + user + "|" + passwd;
			String[] dbinfo = DBInfo.split("\\|");
			DBAccess_new db = new DBAccess_new(dbinfo);
		     if (db.createConn())
		    {
		    	 //String sql ="SELECT 	t1.*, t2.*,t3.chinamobile_delay,t3.chinamobile_lost , t3.chinatelecom_lost AS chinatelecom_lost2, 	t3.chinatelecom_delay chinatelecom_delay2, 	t3.chinaunicom_delay chinaunicom_delay2, 	t3.chinaunicom_lost chinaunicom_lost2 FROM 	t_hlwzbjk_data t1, 	t_ptczz_data t2, 	t_vixtel_data t3 WHERE 	t1.vdate = t2.vdate AND t2.vdate = t3.vdate ORDER BY t1.vdate desc "; 
		    	  String sql = "SELECT  * FROM 	( SELECT vdate, max(maxifInbandwidth_rate) city_rate FROM t_cmnet_cyw_ms GROUP BY vdate 	) t1, 	( SELECT vdate, max(maxifOutbandwidth_rate) province_rate FROM t_cmnet_sn_zj GROUP BY vdate 	) t2, 	( SELECT vdate, max(maxifInbandwidth_rate) country_rate FROM t_cmnet_sw_sk GROUP BY vdate 	) t3, 	t_ptczz_data t4, 	(select vdate, chinamobile_delay vix_chinamobile_delay , chinamobile_lost vix_chinamobile_lost , chinatelecom_delay vix_chinatelecom_delay, chinatelecom_lost vix_chinatelecom_lost, chinaunicom_delay vix_chinaunicom_delay, chinaunicom_lost vix_chinaunicom_lost from t_vixtel_data )t5 WHERE 	t1.vdate = t4.vdate AND t1.vdate = t2.vdate AND t1.vdate = t3.vdate AND t4.vdate = t5.vdate ORDER BY 	t1.vdate DESC";
		    	 
		    	 //System.out.println(sql);
		    	 db.query(sql) ;
		    	 while (db.next()){
		    		 EntityTarget entityTarget = new EntityTarget();
		    		
		    		 entityTarget.setVdate(db.getValue("vdate"));// 日期
		    		 
		    		 entityTarget.setB(null);// CMNET省际链路忙时带宽利用率		    		
		    		 
		    		 entityTarget.setC(db.getValue("country_rate")+"%");// CMNET省网、双跨城域网上联骨干网链路忙时带宽利用率
		    		 
		    		 entityTarget.setD(db.getValue("province_rate")+"%");// CMNET省内中继链路忙时带宽利用率',
		    		 
		    		 entityTarget.setE(null);// CMNET省网至骨干新平面的双通道路由发布一致率
		    		 
		    		 entityTarget.setF(db.getValue("province_evenness")+"%");// CMNET省网至骨干新平面的双通道流量均衡度
		    		 
		    		 entityTarget.setG(db.getValue("country_evenness")+"%");//CMNET双跨城域网至骨干新平面的双通道流量均衡度
		    		 
		    		 entityTarget.setH(db.getValue("city_rate")+"%");//CMNET城域网链路忙时带宽利用率
		    		 
		    		 entityTarget.setI(db.getValue("city_avg")+"%");//城域数据网带宽超限占比（忙时平均值）
		    		 
		    		 entityTarget.setJ(null);//BRAS地址池利用率预警占比
		    		 
		    		 entityTarget.setK(db.getValue("province_lost")+"%");//CMNET省内链路拨测链路丢包率
		    		 
		    		 entityTarget.setL(db.getValue("province_delay"));//CMNET省内链路拨测链路时延
		    		 
		    		 entityTarget.setM(db.getValue("province_fluctuates"));//CMNET省内链路拨测链路抖动
		    		 
		    		 entityTarget.setN(db.getValue("country_delay"));//CMNET省际链路时延（普天）
		    		 
		    		 entityTarget.setO(db.getValue("country_lost")+"%");//CMNET省际链路丢包率（普天）
		    		 
		    		 
                     entityTarget.setP(db.getValue("vix_chinamobile_delay"));//CMNET省际链路时延（飞思达）
		    		 
		    		 entityTarget.setQ(db.getValue("vix_chinamobile_lost")+"%");//CMNET省际链路丢包率（飞思达）
		    		 
		    		 
		    		 entityTarget.setR(db.getValue("chinaunicom_delay"));//'CMNET省际链路时延（普天）- 联通
		    		 
		    		 entityTarget.setS(db.getValue("chinaunicom_lost")+"%");//'CMNET省际链路丢包（普天）- 联通
		    		 
                     entityTarget.setT(db.getValue("vix_chinaunicom_delay"));//'CMNET省际链路时延（普天）- 联通
		    		 
		    		 entityTarget.setU(db.getValue("vix_chinaunicom_lost")+"%");//'CMNET省际链路丢包（普天）- 联通
		    		 
		    		 
		    		 entityTarget.setV(db.getValue("chinatelecom_delay"));//'CMNET省际链路时延（普天）- 电信
		    		 
		    		 entityTarget.setW(db.getValue("chinatelecom_lost")+"%");//'CMNET省际链路丢包（普天）- 电信
		    		// System.out.println("chinatelecom_delay1:\t"+db.getValue("chinatelecom_delay1"));
                     entityTarget.setX(db.getValue("vix_chinatelecom_delay"));//'CMNET省际链路时延（普天）- 电信
                   //  System.out.println("chinatelecom_lost1:\t"+db.getValue("chinatelecom_lost1"));
		    		 entityTarget.setY(db.getValue("vix_chinatelecom_lost")+"%");//'CMNET省际链路丢包（普天）- 电信
		    		 
		    		 entityTarget.setZ(db.getValue("internet_delay"));//'网间网络层时延（ms）
		    		 
		    		 entityTarget.setAA(db.getValue("internet_lost")+"%");//网间网络层丢包率 (%)',
		    		 
		    		 entityTarget.setAB(db.getValue("webpage_delay"));//网间网页主要展现时延 (s)
		    		 
		    		 entityTarget.setAC(db.getValue("webpage_success")+"%");//网间网页展现成功率 (%)
		    		 /////////
		    		 entityTarget.setAD(null);//家客单用户网间成本（元/月）
		    		 entityTarget.setAE(null);//骨干分摊流量超过基准流量占比（%）
		    		 entityTarget.setAF(null);//付费网间流量占比（%）
		    		 entityTarget.setAG(null);//省网互联出口中断时长
		    		 entityTarget.setAH(null);//省网出口忙时带宽利用率
		    		 entityTarget.setAI(null);//省网互联出口结算单价
		    		 entityTarget.setAJ(null);//付费网间流量同比增幅
		    		 entityTarget.setAK(null);//设备脱网时长
		    		 entityTarget.setAL(null);//省内链路中断次数
		    		 entityTarget.setAM(null);//省内链路中断次数比
		    		 entityTarget.setAN(null);//峰值带宽利用率超过50%AR-CE链路数
		    		 entityTarget.setAO(null);//IP地址信息准确性核查
		    		 entityTarget.setAP(null);//IP地址信息准确性核查
		    		 
		    		 
		    		 list.add(entityTarget);
		    		 	    		 
		    	 }
		    	 db.closeRs();
		    	 db.closeStm();
		    }
		     db.closeConn();
    	
    	
    	return list;
    	
    	
    }
    
    
    
    
}