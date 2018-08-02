package kz.amanzhol;

import kz.amanzhol.domain.Order;
import kz.amanzhol.domain.Product;
import kz.amanzhol.ejb.OrderManagerBean;
import kz.amanzhol.ejb.ProductManagerBean;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped
public class OrderBean implements Serializable {
    @EJB
    private OrderManagerBean orderManagerBean;
    @EJB
    private ProductManagerBean productManagerBean;

    private Order order;
    private String name;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void createOrder() {
        if (order == null) {
            order = orderManagerBean.createOrder();
        }
    }

    public void createProduct() {
        productManagerBean.createProduct(name, quantity);
    }

    public List<Product> getProducts() {
        return productManagerBean.getProducts();
    }

    public void addProduct(Product product) {
        if (order == null) {
            return;
        }
        orderManagerBean.addToOrder(product.getId(), order.getId(), 1);
    }

    public List<Product> getProductsInOrder() {
        if (order == null) {
            return Collections.emptyList();
        }

        return orderManagerBean.getProductsInOrder(order.getId());
    }
}
