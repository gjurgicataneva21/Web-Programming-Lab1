package mk.ukim.finki.wp.lab.web.servlet.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;
    private final ChefRepository chefRepository;

    public DishController(DishService dishService, ChefRepository chefRepository) {
        this.dishService = dishService;
        this.chefRepository = chefRepository;
    }

    @GetMapping()
    public String getDishesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        List<Dish> dishes = dishService.listDishes();
        List<Chef> chefs = chefRepository.findAll();
        model.addAttribute("chefs", chefs);
        model.addAttribute("dishes", dishes);
        return "listDishes";
    }

    @GetMapping("/search")
    public String searchDishes(@RequestParam String searchBy,
                               @RequestParam String searchValue,
                               Model model) {
        List<Dish> dishes = dishService.searchDishes(searchBy, searchValue);
        List<Chef> chefs = chefRepository.findAll();

        model.addAttribute("chefs", chefs);
        model.addAttribute("dishes", dishes);
        return "listDishes";
    }

    @PostMapping()
    public String getDishesPageByChef(@RequestParam(required = false) String error,
                                      @RequestParam Long chefId,
                                      Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        if (chefId == -1)
            return "redirect:/dishes";

        List<Dish> dishes = dishService.findByChef_Id(chefId);
        model.addAttribute("dishes", dishes);

        List<Chef> chefs = chefRepository.findAll();
        model.addAttribute("chefs", chefs);
        model.addAttribute("chefId", chefId);

        return "listDishes";
    }

    @PostMapping("/add")
    public String saveDish(@RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam Long chefId) {
        dishService.create(dishId, name, cuisine, preparationTime, chefId);
        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id,
                           @RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam Long chefId) {
        dishService.update(id, dishId, name, cuisine, preparationTime, chefId);
        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return "redirect:/dishes";
    }

    @GetMapping("/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);
        if (dish == null) {
            return "redirect:/dishes?error=DishNotFound";
        }

        List<Chef> chefs = chefRepository.findAll();
        model.addAttribute("chefs", chefs);
        model.addAttribute("dish", dish);
        return "dish-form";
    }

    @GetMapping("/dish-form")
    public String getAddDishPage(Model model) {
        List<Chef> chefs = chefRepository.findAll();
        model.addAttribute("chefs", chefs);
        return "dish-form";
    }
}