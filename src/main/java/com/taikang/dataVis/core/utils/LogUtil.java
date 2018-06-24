package com.taikang.dataVis.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
	public final static Logger log = LoggerFactory.getLogger(LogUtil.class);

	//在日志文件中，打印异常堆栈 
	public static String PrintStack(Throwable e) {  
        StringWriter errorsWriter = new StringWriter();  
        e.printStackTrace(new PrintWriter(errorsWriter));  
        return errorsWriter.toString();  
    }  
}
