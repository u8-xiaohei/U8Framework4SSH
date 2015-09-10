package com.u8.server.log;

import org.apache.log4j.Logger;

/**
 * 日志接口
 */
public class Log {

    private static Logger logger = Logger.getLogger(Log.class.getName());

    public static void i(String msg){
        logger.info(msg);
    }

    public static void d(String msg){ logger.info(msg); }

    public static void e(String msg){
        logger.error(msg);
    }

    public static void e(String msg, Throwable throwable){
        logger.error(msg, throwable);
    }
}
