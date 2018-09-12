package demo.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zacconding
 * @Date 2018-06-25
 * @GitHub : https://github.com/zacscoding
 */
public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    public static void main(String[] args) throws Exception {
        new JettyServer().startServer();
    }


    private void startServer() throws Exception {
        Server server = new Server(8700);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setResourceBase("src/main/webapp");

        // websocket 추가
        // webapp.addServlet(new ServletHolder(EventServlet.class), "/events/*");

        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
