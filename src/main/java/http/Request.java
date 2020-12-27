package http;

import java.io.*;

/**
 * @ClassName Request
 * @Description TODO
 * @Author hanjiale
 * @Date 2020/12/26 22:28
 * @Version 1.0
 */
public class Request {

    //获取url,
    private String url;
    //获取请求的方法，这里只写get和post
    private String method;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Request(InputStream inputStream){
        try{
            //获取inputStream
            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            //获取HTTP请求响应的第一行，GET /user HTTP/1.1,按空格隔开
            String data[] = read.readLine().split(" ");
            //取URL和method
            this.url = data[1];
            this.method  = data[0];
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //省略url和method的getter和sertter方法

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
