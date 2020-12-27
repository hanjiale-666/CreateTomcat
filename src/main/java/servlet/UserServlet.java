package servlet;

import http.Request;
import http.Response;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName UserServlet
 * @Description TODO
 * @Author hanjiale
 * @Date 2020/12/26 22:46
 * @Version 1.0
 */
public class UserServlet extends MyHttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        this.doPost(request,response);
    }

    @Override
    public void doPost(Request request, Response response) {
        //省略业务调用的代码，Tomcat会更加request对象里面的inputStream拿到对应参数
        //模拟业务层调用后的返回
        OutputStream outputStream = response.outputStream;
        String result = Response.responseBody + "user handle successful";
        try {
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {

    }

    public void destory() {

    }
}
