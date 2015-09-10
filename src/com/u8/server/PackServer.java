package com.u8.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 控制台方式直接启动Spring容器
 * Created by ant on 2014/12/9.
 */
public class PackServer {


    public static void main(String[] args){

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        try{

            //Do something not in web container.

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
