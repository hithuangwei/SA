package controller;

import bean.WeiboItem;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15546 on 2017/6/4.
 */
@WebServlet("/top")
public class FetchTopServlet extends HttpServlet
{
    public static List<WeiboItem> topWeibos;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");

        if (topWeibos != null)
        {
            List<String> tags = new ArrayList<String>();
            for (int i = 0; i < topWeibos.size(); i++)
            {
                WeiboItem item = topWeibos.get(i);
                long id = item.getId();
                String content = item.getContent();
                String path = req.getContextPath();
                String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
                String weiboUrl = basePath + "detail?id=" + id;
//                <li><a target=_blank href="http://www.xwcms.net" style="color: white">网易女性美容幻灯片动画</a></li>
                String href = "<li><a target=_blank href='" + weiboUrl + "' style='color: white'>" + content + "</a></li>";
                tags.add(href);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 200);
            jsonObject.put("data", tags);
            String jsonStr = JSON.toJSONString(jsonObject);
            PrintWriter out = null;
            try
            {
                out = resp.getWriter();
                out.write(jsonStr);
            } catch (IOException e)
            {
                e.printStackTrace();
            } finally
            {
                if (out != null)
                {
                    out.close();
                }
            }
        } else

        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 400);
            String jsonStr = JSON.toJSONString(jsonObject);
            PrintWriter out = null;
            try
            {
                out = resp.getWriter();
                out.write(jsonStr);
            } catch (IOException e)
            {
                e.printStackTrace();
            } finally
            {
                if (out != null)
                {
                    out.close();
                }
            }
        }
    }

}
