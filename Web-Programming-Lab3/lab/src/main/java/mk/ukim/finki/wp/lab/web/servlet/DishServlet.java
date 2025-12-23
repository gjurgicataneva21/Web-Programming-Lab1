package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "dishesListServlet", urlPatterns = "/dish")
public class DishServlet extends HttpServlet {
   private final SpringTemplateEngine springTemplateEngine;
   private final DishService dishService;
   private final ChefService chefService;

    public DishServlet(SpringTemplateEngine springTemplateEngine, DishService dishService, ChefService chefService) {
        this.springTemplateEngine = springTemplateEngine;
        this.dishService = dishService;
        this.chefService = chefService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        String chefId = req.getParameter("chefId");
        Long chefIdLong = Long.parseLong(chefId);

        Chef chef = chefService.findById(chefIdLong);
        List<Dish> dishes=dishService.listDishes();

        WebContext context=new WebContext(webExchange);

        context.setVariable("dishes", dishes);
        context.setVariable("chef", chef.getFirstName()+" "+chef.getLastName());
        context.setVariable("chefId", chefIdLong);

        springTemplateEngine.process("dishesList.html", context, resp.getWriter());

    }
}