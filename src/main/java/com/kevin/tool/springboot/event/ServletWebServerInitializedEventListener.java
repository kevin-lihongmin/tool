package com.kevin.tool.springboot.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardThreadExecutor;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

/**
 *  服务监听 {@link AbstractApplicationContext#finishRefresh()}
 * @author lihongmin
 * @date 2020/7/2 9:10
 * @since 1.0.0
 */
@Component
@Slf4j
public class ServletWebServerInitializedEventListener {

    @EventListener
    public void printWebServerInfo(ServletWebServerInitializedEvent event) {
        WebServer webServer = event.getWebServer();
        if (webServer instanceof TomcatWebServer) {
            Tomcat tomcat = ((TomcatWebServer) webServer).getTomcat();
            Service service = tomcat.getService();
            Executor[] executors = service.findExecutors();
            log.info("executors size is " + executors.length);

            /*StandardThreadExecutor executor = (StandardThreadExecutor)executors[0];
            executor.getCorePoolSize();
            executor.getLargestPoolSize();
            executor.getMaxQueueSize();*/
        }
    }
}
