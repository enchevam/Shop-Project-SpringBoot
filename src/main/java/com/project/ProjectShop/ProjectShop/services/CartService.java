package com.project.ProjectShop.ProjectShop.services;

import com.project.ProjectShop.ProjectShop.entities.Cart;
import com.project.ProjectShop.ProjectShop.entities.CartItem;
import com.project.ProjectShop.ProjectShop.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private ProductService productService;


    public CartItem findCartItemByProduct(List<CartItem> items, Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                return item;
            }
        }

        return null;
    }

    public double calculateTotalPrice(List<CartItem> items) {
        double cartTotalPrice = 0.0;

        for (CartItem item : items) {
            double itemTotalPrice = item.getProduct().getPrice() * item.getQuantity();
            item.setTotalPrice(itemTotalPrice);
            cartTotalPrice += itemTotalPrice;
        }

        return cartTotalPrice;
    }

    public void removeItem(Long productId, Cart cart) {
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));

        Double totalPrice = 0.0;
        for (CartItem item : cart.getCartItems()) {
            Double itemPrice = item.getProduct().getPrice() * item.getQuantity();
            item.setTotalPrice(itemPrice);
            totalPrice += itemPrice;
        }
        cart.setTotalPrice(totalPrice);
    }
    public void addItemToShoppingCart(Long productId, HttpSession session) {
        Product product = productService.findById(productId);
        if (product != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            List<CartItem> items = cart.getCartItems();
            if (items == null) {
                items = new ArrayList<>();
            }
            CartItem item = findCartItemByProduct(items, product);
            if (item != null) {
                item.setQuantity(item.getQuantity() + 1);
            } else {
                item = new CartItem();
                item.setProduct(product);
                item.setQuantity(1);
                items.add(item);
            }
            cart.setCartItems(items);
            cart.setTotalPrice(calculateTotalPrice(items));
        }
    }

//    public void addItemToShoppingCart(Long productId, HttpSession session) {
//        Product product = productService.findById(productId);
//        List<CartItem> items = new ArrayList<>();
//        if (product != null) {
//            Cart cart = (Cart) session.getAttribute("cart");
//            if (cart == null) {
//                cart = new Cart();
//                session.setAttribute("cart", cart);
//                CartItem cartItem = new CartItem();
//                cartItem.setProduct(product);
//                cartItem.setQuantity(1);
//                items.add(cartItem);
//                System.out.println("cart nulll");
//            } else {
//                items = cart.getCartItems();
//                CartItem item = findCartItemByProduct(items, product);
//                if (item != null) {
//                    item.setQuantity(item.getQuantity() + 1);
//                    System.out.println("item exist");
//
//                } else {
//                    item = new CartItem();
//                    item.setProduct(product);
//                    item.setQuantity(1);
//                    items.add(item);
//                    System.out.println("new item ");
//                }
//
//            }
//
//            cart.setCartItems(items);
//            System.out.println("cart set items" + items);
//            cart.setTotalPrice(calculateTotalPrice(items));
//            session.setAttribute("cart", cart);
//        }
//    }


}