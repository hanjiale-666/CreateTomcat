package servlet;

import http.Request;
import http.Response;

public interface MyServlet {
    void init() throws Exception;
    void service(Request request, Response response) throws Exception;
    void destory();
}
