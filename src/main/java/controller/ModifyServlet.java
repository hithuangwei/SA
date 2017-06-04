package controller;

import bean.WeiboItem;
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
@WebServlet("/modify")
public class ModifyServlet extends HttpServlet
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String path = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
        WeiboItem item = weiboService.findWeiboItemById(Long.valueOf(req.getParameter("id").toString()));
        req.setAttribute("item", item);
        req.getRequestDispatcher("WEB-INF/view/modify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
//        String path = req.getContextPath();
//        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
        String content=req.getParameter("content");
        String weiboId=req.getParameter("id").toString();
        weiboService.updateWeiboItemById(Long.valueOf(weiboId),content);
        String path = req.getContextPath();
        String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
        resp.sendRedirect(basePath+"index");
    }


}
