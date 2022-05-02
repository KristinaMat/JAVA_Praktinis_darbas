package MyPackage.entities;

import static MyPackage.entities.Role.CLIENT;

public class User {
    private String username;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String address;
    private ShoppingCart shoppingCart;
    private Order order;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(String username, String password, String name, String surname, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.role = CLIENT;
        this.shoppingCart=new ShoppingCart();
        this.order=new Order();

    }


    public User(String username, String password, Role role, String name, String surname, String address) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.shoppingCart=new ShoppingCart();
        this.order=new Order();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address=" + address +
                '}';
    }
}

