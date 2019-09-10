package cn.luge.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * cookie案例
 */
@WebServlet("/cookieTest")
public class CookieTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应消息的编码方式
        response.setContentType("text/html;charset=utf-8");
        // 获取所有的cookie
        Cookie[] cookies = request.getCookies();
        // 设置有无lastTime的cookie的标志
        boolean flag = false;
        // 不是第一次访问
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastTime")) {
                    flag = true;
                    // 获取上次访问的时间
                    String lastTime = cookie.getValue();
                    // URL解码
                    lastTime = URLDecoder.decode(lastTime, "utf-8");
                    response.getWriter().write("<h1>您上次的访问时间为" + lastTime + "</h1>");
                    // 修改名为lastTime的cookie的value为当前时间
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    String str_date = sdf.format(date);
                    // URL编码
                    str_date = URLEncoder.encode(str_date, "utf-8");
                    cookie.setValue(str_date);
                    // 设置cookie的存活时间
                    cookie.setMaxAge(3600 * 24 * 30);
                    // 发送
                    response.addCookie(cookie);

                    break;
                }
            }
        }
        // 第一次访问
        if (cookies == null || cookies.length == 0 || flag == false) {
            // 建立名为lastTime的cookie
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String str_date = sdf.format(date);
            // URL编码
            str_date = URLEncoder.encode(str_date, "utf-8");
            Cookie cookie = new Cookie("lastTime", str_date);
            // 设置cookie的存活时间
            cookie.setMaxAge(3600 * 24 * 30);
            // 发送
            response.addCookie(cookie);
            // 显示响应消息
            response.getWriter().write("<h1>欢迎您首次访问我们的网站！</h1>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
