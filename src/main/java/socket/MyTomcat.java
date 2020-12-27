package socket;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import servlet.MyHttpServlet;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName MyTomcat
 * @Description TODO
 * @Author hanjiale
 * @Date 2020/12/26 22:46
 * @Version 1.0
 */
public class MyTomcat {
    //自定义端口号为1324；
    public static final int port = 1314;
    //定义web.xml中的两个映射
    public static final HashMap<String, MyHttpServlet> servletMapping = new HashMap<String, MyHttpServlet>();
    public static final HashMap<String,String> urlMapping = new HashMap<String, String>();

    public static void main(String[] args) {
        MyTomcat myTomcat = new MyTomcat();
        myTomcat.init();
        myTomcat.run();

    }
    //初始化，加载web.xml里面的配置的servlet信息
    private void init(){
        //获取web.xml目录地址
        String path = MyTomcat.class.getResource("/").getPath();
        //实例化SAXReader对象
        SAXReader reader=new SAXReader();
        //读取webi.xml文件
        try {
            Document document = reader.read(new File(path+"web.xml"));
            //获取根标签（servlet和，servelt-mapping),放在一个list中
            Element rootelement = document.getRootElement();
            List<Element> elements = rootelement.elements();
            //循环将映射写进MAP映射里
            for(Element element : elements){
                if("servlet".equalsIgnoreCase(element.getName())){
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    System.out.println(servletName.getText()+"-->"+servletClass.getText());
                    //需要注意的是servletMapping映射的第二个参数，要通过反射的方式镜像实例化
                    servletMapping.put(servletName.getText(),(MyHttpServlet) Class.forName(servletClass.getText().trim()).newInstance());
                } else if("servlet-mapping".equalsIgnoreCase(element.getName())){
                    Element servletName = element.element("servlet-name");
                    Element urlPattern = element.element("url-pattern");
                    System.out.println(servletName.getText()+"==>"+urlPattern.getText());
                    urlMapping.put(urlPattern.getText(),servletName.getText());

                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //负责启动容器
    private void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("====服务启动====");
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
