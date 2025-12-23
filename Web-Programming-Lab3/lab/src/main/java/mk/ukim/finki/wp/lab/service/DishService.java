package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Dish;

import java.util.List;

public interface DishService {
    List<Dish> listDishes();
    Dish findByDishId(String dishId);
    //void deleteDish(String dishId);
    Dish findById(Long id);
    Dish create(String dishId, String name, String cuisine, int preparationTime, Long chefId);
    Dish update(Long id, String dishId, String name, String cuisine, int preparationTime, Long chefId);
    void delete(Long id);
    List<Dish> findByChef_Id(Long chefId);
    List<Dish> searchDishes(String searchBy, String searchValue);
    //List<Dish> search(Long id);
   // List<Dish> findAll();

}
