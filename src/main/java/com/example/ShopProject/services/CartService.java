package com.example.ShopProject.services;

import com.example.ShopProject.entities.OrderProduct;
import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private ProductRepository productRepository;


    public OrderProduct findCartItemByProduct(List<OrderProduct> items, Optional<Product> product) {
        if (product.isPresent()) {
            for (OrderProduct item : items) {
                if (item.getProduct().getId().equals(product.get().getId())) {
                    return item;
                }
            }
        }
        return null;
    }

    public BigDecimal calculateTotalPrice(List<OrderProduct> orderProducts) {
        BigDecimal cartTotalPrice = BigDecimal.ZERO;

        for (OrderProduct item : orderProducts) {
            BigDecimal productPrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setTotalPrice(productPrice);
            cartTotalPrice = cartTotalPrice.add(productPrice);
        }

        return cartTotalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    /*public BigDecimal calculateTotalPrice(List<OrderProduct> orderProducts) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (orderProducts != null) {
            for (OrderProduct orderProduct : orderProducts) {
                BigDecimal productPrice = orderProduct.getProduct().getPrice();
                BigDecimal quantity = BigDecimal.valueOf(orderProduct.getQuantity());
                totalPrice = totalPrice.add(productPrice.multiply(quantity));
            }
        }
        return totalPrice;
    }*/


    public void addItemToShoppingCart(Long productId, HttpSession session) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
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
                item.setProduct(product.get());
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

