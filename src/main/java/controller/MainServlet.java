package controller;

import bean.WeiboItem;
import service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 15546 on 2017/5/31.
 */
@WebServlet("/index")
public class MainServlet extends HttpServlet
{
    private WeiboService weiboService;

    @Override
    public void init() throws ServletException
    {
        super.init();
        weiboService=new WeiboService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        List<WeiboItem> weiboList = weiboService.findAllWeiboItem();
        System.err.println("-->weibosize"+weiboList.size());
        req.setAttribute("weiboList",weiboList);
        req.getRequestDispatcher("WEB-INF/view/default.jsp").forward(req,resp);
    }
}
