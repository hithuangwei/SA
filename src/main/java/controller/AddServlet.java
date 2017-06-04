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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Created by 15546 on 2017/5/31.
 */
@WebServlet("/add")
public class AddServlet extends HttpServlet
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        WeiboItem item = new WeiboItem();
        String content =req.getParameter("content").toString();
        item.setContent(content);
        item.setCreateDate(new Date());
        item.setClickNum(0);
        item.setLikeNum(0);
        HttpSession session = req.getSession();
        if (session != null)
        {

            if (session.getAttribute("username") == null)
            {
                item.setUserName("Anonymous");
            } else
            {
                item.setUserName(session.getAttribute("username").toString());
            }
        } else
        {
            item.setUserName("Anonymous");
        }
        long id = weiboService.publishWeibo(item);
        item.setId(id);
        addItem(item, item.getUserName());
        String path = req.getContextPath();
        String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
        resp.sendRedirect(basePath+"index");

    }

    public void addItem(WeiboItem item, String username)
    {

        Observer logger = new WeiboLogger();
        item.attach(logger);
        item.notifyMessage(CREATE, username);
    }

}
