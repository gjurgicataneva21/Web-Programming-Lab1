
package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.exceptions.ChefNotFoundException;
import mk.ukim.finki.wp.lab.model.exceptions.DishNotFoundException;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.DishService;
import mk.ukim.finki.wp.lab.service.DishSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final ChefRepository chefRepository;

    public DishServiceImpl(DishRepository dishRepository, ChefRepository chefRepository) {
        this.dishRepository = dishRepository;
        this.chefRepository = chefRepository;
    }

    @Override
    public List<Dish> listDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Dish findByDishId(String dishId) {
        return dishRepository.findByDishId(dishId);
    }

    @Override
    public Dish findById(Long id) {
//        return dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(String dishId, String name, String cuisine, int preparationTime, Long chefId) {
        if (dishId == null || dishId.isEmpty() ||
                name == null || name.isEmpty() ||
                cuisine == null || cuisine.isEmpty() ||
                preparationTime < 0 || chefId == null) {
            throw new IllegalArgumentException();
        }

        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> new ChefNotFoundException(chefId));

        Dish dish = new Dish(dishId, name, cuisine, preparationTime, chef);
        return this.dishRepository.save(dish);
    }

    @Override
    public Dish update(Long id, String dishId, String name, String cuisine, int preparationTime, Long chefId) {
        if (dishId == null || dishId.isEmpty() ||
                name == null || name.isEmpty() ||
                cuisine == null || cuisine.isEmpty() ||
                preparationTime < 0 || chefId == null) {
            throw new IllegalArgumentException();
        }


        Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> new ChefNotFoundException(chefId));
        dish.setDishId(dishId);
        dish.setName(name);
        dish.setCusine(cuisine);
        dish.setPreparationTime(preparationTime);
        dish.setChef(chef);

        return dishRepository.save(dish);
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> findByChef_Id(Long chefId) {
        return dishRepository.findAllByChef_Id(chefId);
    }

    @Override
    public List<Dish> searchDishes(String searchBy, String searchValue) {
        Specification<Dish> spec = null;

        switch (searchBy) {
            case "name":
                spec = DishSpecification.filterContainsText("name", searchValue);
                break;
            case "dishId":
                spec = DishSpecification.filterContainsText("dishId", searchValue);
                break;
            case "cuisine":
                spec = DishSpecification.filterContainsText("cusine", searchValue);
                break;
            case "prepTime":
                try {
                    int maxTime = Integer.parseInt(searchValue);
                    spec = DishSpecification.filterLessThanEqual("preparationTime", maxTime);
                } catch (NumberFormatException e) {
                    return dishRepository.findAll();
                }
                break;
            case "chefName":
                spec = DishSpecification.filterChefName(searchValue);
                break;
            default:
                return dishRepository.findAll();
        }

        if (spec == null) {
            return dishRepository.findAll();
        }

        return dishRepository.findAll(spec);
    }

}
