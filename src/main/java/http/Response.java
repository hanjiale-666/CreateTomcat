package http;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ClassName Response
 * @Description TODO
 * @Author hanjiale
 * @Date 2020/12/26 22:28
 * @Version 1.0
 */
public class Response {

    public OutputStream outputStream;

    public static final String responseBody = "HTTP/1.1 200+\r\n" + "Content-Type: text/html +\r\n"
    + "\r\n";
    public Response(OutputStream outputStream){
        this.outputStream = outputStream;
    }
}
