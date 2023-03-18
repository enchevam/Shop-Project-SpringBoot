package com.example.ShopProject.controllers;

import com.example.ShopProject.services.CartService;
import com.example.ShopProject.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().isEmpty()) {
            cart = new Cart();
            System.out.println("Cart - Cart empty 1 " + cart);
            redirectAttributes.addFlashAttribute("message", "ShoppingCart Empty!!!");
            return  "shop/shCart";
        }

        model.addAttribute("cart", cart.getOrderProducts());
        model.addAttribute("total", cart.getTotalPrice());

        return "shop/shCart";
    }

    @PostMapping("/cart/add/{productId}")
    public ModelAndView addToCart(@RequestParam(name = "productId") Long productId, HttpSession session) {

        cartService.addItemToShoppingCart( productId, session);

        return new ModelAndView("redirect:/cart");
    }

    @PostMapping("/cart/updateQuantity")
    public ModelAndView updateQuantity(@RequestParam(name = "productId") Long productId, @RequestParam(name = "quantity") Integer quantity,
                                       HttpSession session) {
        cartService.updateItemQuantityInShoppingCart(productId, quantity, session);
        return new ModelAndView("redirect:/cart");
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@RequestParam(name = "productId") Long productId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cartService.removeItemFromShoppingCart(productId, cart);
        }
        return "redirect:/cart";
    }

}
