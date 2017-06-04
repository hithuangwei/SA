package controller;

import service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 15546 on 2017/5/31.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet
{
    private AuthService authService;

    @Override
    public void init() throws ServletException
    {
        super.init();
        authService = new AuthService();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        if (username != null && !username.trim().equals("") && password != null && !password.trim().equals("") && password.equals(confirm))
        {


            authService.registerUser(username, password);
            String path = req.getContextPath();
            String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
            resp.sendRedirect(basePath + "login");
        } else
        {
            req.setAttribute("reason", "注册失败");
            req.getRequestDispatcher("WEB-INF/view/register.jsp").forward(req, resp);
        }
    }


}
