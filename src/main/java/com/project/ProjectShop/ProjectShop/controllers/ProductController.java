package com.project.ProjectShop.ProjectShop.controllers;

import com.project.ProjectShop.ProjectShop.entities.CartItem;
import com.project.ProjectShop.ProjectShop.repositories.ProductRepository;
import com.project.ProjectShop.ProjectShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String showShop(Model model, String keyword, CartItem cartItem) {
        model.addAttribute("products", productService.findAll(keyword));
        //model.addAttribute("type", Type.values());
        model.addAttribute("items", cartItem.getQuantity());
        System.out.println(cartItem.getQuantity());
        return "all_products";
    }


//    @GetMapping("/sh_card/{productId}")
//    public String editHotel(@RequestParam(name = "productId") Long productId,@RequestParam("quantity") int quantity, HttpSession session,
//                            Model model) {
//      productService.findById(productId);
//        model.addAttribute("products", productService.showCard());
//
//        return "index2";
//    }
//    @PostMapping("/add-to-cart")
//    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, HttpSession session) {
//        // Get the product with the specified ID from the database
//        Product product = productService.getProductById(productId);
//
//        // Create a new cart item with the product and quantity
//        Order order = new CartItem(product, quantity);
//
//        // Get the user's shopping cart from the session (or create a new one if it doesn't exist)
//        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new ShoppingCart();
//            session.setAttribute("cart", cart);
//        }
//
//        // Add the cart item to the shopping cart
//        cart.addItem(cartItem);
//
//        // Redirect the user to the shopping cart page
//        return "redirect:/cart";
//    }

    //    @GetMapping("/product/{id}")
//    public String getProductById(@PathVariable Long id, Model model) {
//        Optional<Product> product = productRepository.findById(id);
//        if (product.isPresent()) {
//            model.addAttribute("product", product.get());
//            return "index2";
//        } else {
//            return "error_page";
//        }
//    }
//    @GetMapping("/sh_card/{productId}")
//    public String addToBasket(@PathVariable(name = "productId") Long productId,@RequestParam("quantity") int quantity, Model model) {
//        if (productId == null) {
//            // Handle case where productId is missing
//        }
//        Optional<Product> prCart = productService.findById(productId);
//        if (!prCart.isPresent()) {
//            // Handle case where productId is invalid
//        }
//        productService.addToCart(prCart.get(),quantity);
//        model.addAttribute("products", productService.showCard());
//        model.addAttribute("totalPrice", productService.calculateTotalSum());
//        //return new ModelAndView("redirect:/sh_card");
//        return "sh_card";
//    }
//    @GetMapping("/sh_card/{productId}")
//    public ModelAndView addToBasket(@RequestParam(name = "productId", required = false) Long productId, Model model) {
//        if (productId == null) {
//            // Handle case where productId is missing
//        }
//        Optional<Product> prCart = productService.findById(productId);
//        if (!prCart.isPresent()) {
//            // Handle case where productId is invalid
//        }
//        productService.addToCart(prCart.get());
//        model.addAttribute("products", productService.showCard());
//        return new ModelAndView("redirect:/sh_card");
//    }
//    @GetMapping("/sh_card/{productId}")
//    public ModelAndView addToBasket(@PathVariable(name = "productId") Long productId, Model model) {
//
//        Optional<Product> prCart = productService.findById(productId);
//        productService.addToCart(prCart.get());
//        model.addAttribute("products", productService.showCard());
//        return new ModelAndView("redirect:/sh_card");
//    }
//     if(bindingResult.hasErrors()){
//        model.addAttribute("resorts", resortRepository.findAll());
//        return new ModelAndView ("edit_hotel");
//    }
//        hotelService.generateHotelRating(hotel);
//        hotelService.save(hotel);
//        redirectAttributes.addFlashAttribute("message", "The Hotel has been saved updated!!!");
//
//        return new ModelAndView("redirect:/all_hotels");

//    @GetMapping("/sh_card")
//    public String showCard(Model model) {
//        model.addAttribute("products", productService.showCard());
//
//        return "sh_card";
//    }

    @GetMapping("/shopping_card")
    public String showShoppingCard() {
        return "shopping_card";
    }


}
