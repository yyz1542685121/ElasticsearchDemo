package com.yyz.elasticsearch;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
/**
 * Created by yaoyongzhen on 18/4/29
 */
public class Starter {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"server.xml"});
        context.start();
        System.in.read();
    }
}
