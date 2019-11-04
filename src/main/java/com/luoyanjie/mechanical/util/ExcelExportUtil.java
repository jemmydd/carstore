package com.luoyanjie.mechanical.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;

/**
 * @author luoyanjie
 * @date 2019-08-26 20:48
 * Coding happily every day!
 */
public class ExcelExportUtil {
    public static void noDataReturn(HttpServletResponse httpServletResponse) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(byteArrayOutputStream);

        byte[] bytes = "没有数据，无需导出".getBytes(StandardCharsets.UTF_8);
        gzip.write(bytes);
        gzip.close();

        bytes = byteArrayOutputStream.toByteArray();

        OutputStream os = httpServletResponse.getOutputStream();
        //清空输出流
        httpServletResponse.reset();
        httpServletResponse.setHeader("Content-Encoding", "gzip");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentLength(bytes.length);

        //写入文件刷新，关闭输出流
        os.write(bytes);
        os.flush();
        os.close();
    }

    public static void handleResponse(HttpServletResponse httpServletResponse, HSSFWorkbook hSSFWorkbook, String fileName) throws IOException {
        //取得输出流
        OutputStream os = httpServletResponse.getOutputStream();

        //清空输出流
        httpServletResponse.reset();

        //设定输出文件头
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls" + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
        //设定输出格式
        httpServletResponse.setContentType("application/vnd.ms-excel;");

        //写入文件刷新，关闭输出流
        hSSFWorkbook.write(os);
        os.flush();
        os.close();
    }

    public static String getFileName(String title) {
        return title + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
    }
}
