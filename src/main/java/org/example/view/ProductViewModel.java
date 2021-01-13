package org.example.view;

import org.example.model.Product;

public class ProductViewModel {
    private String name, price, quantity, total;

    private ProductViewModel() {
    }

    public static ProductViewModel of(Product product){
        final ProductViewModel mv = new ProductViewModel();
        mv.name = product.getName();
        mv.price = String.valueOf(product.getPrice());
        mv.quantity = String.valueOf(product.getQuantity());

        double total = product.getPrice() * product.getQuantity();
        mv.total = String.valueOf(total);

        return mv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
