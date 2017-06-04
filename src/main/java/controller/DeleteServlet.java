package controller;

import bean.WeiboItem;
import inter.Observer;
import observer.WeiboLogger;
import service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 15546 on 2017/5/31.
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet
{
    private WeiboService weiboService;

    private final String CREATE = "create";
    private final String DELETE = "delete";
    private final String UPDATE = "update";
    private final String QUERY = "query";

    @Override
    public void init() throws ServletException
    {
        super.init();
        weiboService = new WeiboService();
    }

    public void deleteItem(WeiboItem item, String username)
    {

        Observer logger = new WeiboLogger();
        item.attach(logger);
        item.notifyMessage(DELETE, username);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        String path = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
        String id = req.getParameter("id");
        if (id != null && id.trim().equals("") == false)
        {
            try
            {
                long weiboId = Long.valueOf(id);
                WeiboItem item = weiboService.findWeiboItemById(weiboId);
                weiboService.deleteWeiboItemById(weiboId);
                if (item != null)
                {
                    String username = null;
                    if (req.getSession() != null && req.getSession().getAttribute("username") != null)
                    {
                        username = req.getSession().getAttribute("username").toString();
                    } else
                    {
                        username = "Anonymous";
                    }
                    deleteItem(item, username);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            resp.sendRedirect(basePath+"index");
        }


    }


}
