package MyPackage.entities;

import MyPackage.entities.Product;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Product> cart = new ArrayList<>();
    private double amount;
    private int quantity;

    public ShoppingCart() {

    }

    public ShoppingCart(ArrayList<Product> cart, double amount, int quantity) {
        this.cart = cart;
        this.amount = 0;
        this.quantity = 0;
    }

    public void addProduct(Product product) {

        cart.add(product);
        quantity += 1;
        amount += product.getPrice();
    }
    public void removeProduct(Product product) {

        cart.remove(product);
        quantity -= 1;
        amount -= product.getPrice();
    }
    public String getInfo() {
        return String.format("%s %s %d", cart, quantity, amount);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cart=" + cart +
                ", amount=" + amount +
                '}';
    }

}