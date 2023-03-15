package com.project.ProjectShop.ProjectShop.services;

import com.project.ProjectShop.ProjectShop.utils.Cart;
import com.project.ProjectShop.ProjectShop.entities.OrderProduct;
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


    public OrderProduct findCartItemByProduct(List<OrderProduct> items, Product product) {
        for (OrderProduct item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                return item;
            }
        }

        return null;
    }

    public double calculateTotalPrice(List<OrderProduct> items) {
        double cartTotalPrice = 0.0;

        for (OrderProduct item : items) {
            double itemTotalPrice = Math.round((item.getProduct().getPrice() * item.getQuantity())* 100) / 100.0d;
            item.setTotalPrice(itemTotalPrice);
            cartTotalPrice += itemTotalPrice;
        }

        return Math.round(cartTotalPrice * 100) / 100.0d;
    }

    public void addItemToShoppingCart(Long productId, HttpSession session) {
        Product product = productService.findById(productId);
        if (product != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            List<OrderProduct> items = cart.getOrderProducts();
            if (items == null) {
                items = new ArrayList<>();
            }
            OrderProduct item = findCartItemByProduct(items, product);
            if (item != null) {
                item.setQuantity(item.getQuantity() + 1);
            } else {
                item = new OrderProduct();
                item.setProduct(product);
                item.setQuantity(1);
                items.add(item);
            }
            cart.setOrderProducts(items);
            cart.setTotalPrice(calculateTotalPrice(items));
        }
    }

    public void updateItemQuantityInShoppingCart(Long productId, Integer quantity, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");

        for (OrderProduct orderProduct : cart.getOrderProducts()) {
            if (orderProduct.getProduct().getId().equals(productId)) {
                orderProduct.setQuantity(quantity);
                break;
            }
        }
        cart.setTotalPrice(calculateTotalPrice(cart.getOrderProducts()));
    }

    public void removeItemFromShoppingCart(Long productId, Cart cart) {
        List<OrderProduct> itemsDelete = new ArrayList<>();
        for (OrderProduct item : cart.getOrderProducts()) {
            if (item.getProduct().getId().equals(productId)) {
                itemsDelete.add(item);
            }
        }
        cart.getOrderProducts().removeAll(itemsDelete);
        cart.setTotalPrice(calculateTotalPrice(cart.getOrderProducts()));
    }
}