package socket;

import http.Request;
import http.Response;
import servlet.MyHttpServlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @ClassName RequestHandler
 * @Description TODO
 * @Author hanjiale
 * @Date 2020/12/26 22:47
 * @Version 1.0
 */
public class RequestHandler implements Runnable {
    public Socket socket;
    public RequestHandler(Socket socket)
    {
        this.socket=socket;
    }

    public void run() {
        try{
            //将inputstream封装成我们自己的request，用来获取uri，method等信息
            Request request=new Request(socket.getInputStream());
            //将outputstream封装成我们的response对象
            Response response=new Response(socket.getOutputStream());
            String uri=request.getUrl();
            System.out.println(uri);
            //根据uri得到servletname
            String servletname=MyTomcat.urlMapping.get(uri);
            //根据servletname得到Servlet对象，如果web.xml文件中有映射就不为空
            MyHttpServlet servlet= MyTomcat.servletMapping.get(servletname);
            if(servlet!=null){
                //不为空执行service方法，即跳转到doGet和doPost方法
                servlet.service(request,response);
            }else{
                String resp=Response.responseBody+"can not find servlet";
                OutputStream outputStream=socket.getOutputStream();
                System.out.println(resp);
                outputStream.write(resp.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
