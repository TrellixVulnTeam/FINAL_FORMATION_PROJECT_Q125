package com.schairamaniega.pizzaproject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.schairamaniega.pizzaproject.entities.Comment;
import com.schairamaniega.pizzaproject.entities.Ingredient;
import com.schairamaniega.pizzaproject.entities.Pizza;
import com.schairamaniega.pizzaproject.entities.User;
import com.schairamaniega.pizzaproject.services.CommentService;
import com.schairamaniega.pizzaproject.services.IngredientService;
import com.schairamaniega.pizzaproject.services.PizzaService;
import com.schairamaniega.pizzaproject.services.UserService;

@SpringBootApplication
public class PizzaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(PizzaService pizzaService, IngredientService ingredientService,
		UserService userService, CommentService commentService){

		return args -> {

			// TYPES: [0- GENERAL INGREDIENT, 1- DOUGH, 2- CHEESE, 3- SAUCE]

			ingredientService.save(Ingredient.builder().name("MASA NORMAL").price(1.0).type(1).build());
			ingredientService.save(Ingredient.builder().name("MASA FINA").price(1.25).type(1).build());
			ingredientService.save(Ingredient.builder().name("QUESO").type(3).price(1.0D).build());
			ingredientService.save(Ingredient.builder().name("QUESO AZUL").type(3).price(1.6).build());
			ingredientService.save(Ingredient.builder().name("QUESO MOZZARELLA").type(3).price(1.8).build());
			ingredientService.save(Ingredient.builder().name("QUESO PARMESANO").type(3).price(1.75).build());
			ingredientService.save(Ingredient.builder().name("JAMON").type(4).price(2.1).build());
			ingredientService.save(Ingredient.builder().name("JAMON DE YORK").type(4).price(1.8).build());
			ingredientService.save(Ingredient.builder().name("PEPPERONNI").type(4).price(2.0).build());
			ingredientService.save(Ingredient.builder().name("POLLO").type(4).price(1.8).build());
			ingredientService.save(Ingredient.builder().name("CARNE PICADA").type(4).price(1.9).build());
			ingredientService.save(Ingredient.builder().name("SALSA DE TOMATE").type(2).price(0.7).build());
			ingredientService.save(Ingredient.builder().name("SALSA DE TOMATE ESPECIAL").price(1.0).type(2).build());
			ingredientService.save(Ingredient.builder().name("SALSA BARBACOA").type(2).price(1.8).build());
			ingredientService.save(Ingredient.builder().name("SALSA MOSTAZA").type(2).price(1.8).build());
			ingredientService.save(Ingredient.builder().name("OREGANO").price(0.5).build());
			ingredientService.save(Ingredient.builder().name("ACEITUNAS").price(1.0).build());
			ingredientService.save(Ingredient.builder().name("CHAMPIÑONES").price(1.35).build());
			ingredientService.save(Ingredient.builder().name("PIÑA").price(0.9).build());
			ingredientService.save(Ingredient.builder().name("BACON").price(1.2).build());



			// USERS

			userService.save(User.builder().name("User").email("example@domain.com").password("1234").surname("Name").build());
			userService.save(User.builder().name("Peter").email("pizzalover27@gmail.com").password("567890").surname("Parker").build());
			userService.save(User.builder().name("Pete").email("petezetas@gmail.com").password("abcdefg").surname("Zass").build());

			// List<Ingredient> ingredients = new ArrayList<Ingredient>();

			// PIZZA MARGARITA
			this.createPizza("Margarita", "margherita.jpg", new Long[]{1L, 3L, 12L, 16L, 8L}, ingredientService, pizzaService);

			// PIZZA PROSCIUTTO
			this.createPizza("Prosciutto", "prosciutto.jpg", new Long[]{1L, 3L, 12L, 16L, 7L}, ingredientService, pizzaService);

			// PIZZA YORKESO
			this.createPizza("Yorkeso", "york.jpg", new Long[]{1L, 5L, 12L, 16L, 8L}, ingredientService, pizzaService);

			// PIZZA CUATRO-QUESOS
			this.createPizza("Cuatro Quesos", "4q.jpg", new Long[]{1L, 3L, 4L, 5L, 6L, 12L, 16L}, ingredientService, pizzaService);

			// PIZZA HAWAIANA
			this.createPizza("Hawaiana", "hawaian.jpg", new Long[]{1L, 5L, 12L, 14L, 16L, 19L, 20L}, ingredientService, pizzaService);

			// PIZZA CARBONARA
			this.createPizza("Carbonara", "carbonara.jpg", new Long[]{1L, 5L, 6L, 8L, 13L, 15L, 16L}, ingredientService, pizzaService);

			// PIZZA PEPPERONNISIMA
			this.createPizza("Pepperonisima", "pepperoni.jpg", new Long[]{2L, 3L, 9L, 12L, 16L}, ingredientService, pizzaService);

			// PIZZA BBQ-MIX
			this.createPizza("BBQ-Mix", "bbq.jpg", new Long[]{1L, 3L, 10L, 11L, 12L, 16L, 14L}, ingredientService, pizzaService);

			for (Pizza pizza : pizzaService.findAll()) {
			

				commentService.save(Comment.builder().body("Este es un comentario de ejemplo.").user(userService.findNameById((long)Math.floor(Math.random() * 3 + 1))).score(Math.random() * 10).pizza(pizza).build());

			}
		};
	}

	private double getPrice(List<Ingredient> list){
		double total = list.stream().mapToDouble(d -> d.getPrice().doubleValue()).sum();
		total += total * 0.2F;
		return total;
	}

	private void createPizza(String name, String img, Long[] ingredientsArray, IngredientService ingredientService, PizzaService pizzaService){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		double price;

		ingredients.clear();

		for (Long ingredient : ingredientsArray) {
			ingredients.add(ingredientService.findById(ingredient));
		}

		price = getPrice(ingredients);
		
		pizzaService.save(Pizza.builder().name(name).img(img).price(price).ingredients(ingredients).build());
	}
	private void createPizza(String name, Long[] ingredientsArray, IngredientService ingredientService, PizzaService pizzaService){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		double price;

		ingredients.clear();

		for (Long ingredient : ingredientsArray) {
			ingredients.add(ingredientService.findById(ingredient));
		}

		price = getPrice(ingredients);
		
		pizzaService.save(Pizza.builder().name(name).img("pizza.png").price(price).ingredients(ingredients).build());
	}

}
