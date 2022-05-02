package MyPackage.entities;

import java.util.ArrayList;


public class Order extends ShoppingCart {
    private User user;
    private Payment payment;
    private Delivery delivery;

    public Order() {
    }

    public Order(String name, String surname, ArrayList<Product> cart, int quantity, double amount, Payment payment, Delivery delivery) {
        super(cart, amount, quantity);
        this.user = user;
        this.payment = payment;
        this.delivery = delivery;
    }

    public Order(String name, String surname, String title, String producer, String model, double price, int quantity, double amount, Payment valueOf, Delivery valueOf1) {
    }

    public Order(String userName, String userSurname, String cart, int quantity, double amount, Payment payment, Delivery delivery) {
    }


    @Override
    public String getInfo(){
        return String.format("%s %s %s %s",user,super.getInfo(),payment,delivery );
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", payment=" + payment +
                ", delivery=" + delivery +
                '}';
    }
}