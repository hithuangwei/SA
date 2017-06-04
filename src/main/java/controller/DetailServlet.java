package controller;

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
@WebServlet("/detail")
public class DetailServlet extends HttpServlet
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
        String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
        req.setAttribute("item",weiboService.findWeiboItemById(Long.valueOf(req.getParameter("id"))));
        req.getRequestDispatcher("WEB-INF/view/detail.jsp").forward(req,resp);
       
    }





}
