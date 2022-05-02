package MyPackage;

import MyPackage.entities.*;
import MyPackage.exeption.ProductExeption;
import MyPackage.exeption.UserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class OrderManagement {
    private String path;


    public OrderManagement(String path) {
        this.path = path;
    }


    public User getUser(String username, String password) throws FileNotFoundException, UserException {

        for (User user : getAllUsers()) {

            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserException("Neteisingi prisijungimo duomenys");
    }

    public ArrayList<User> getAllUsers() throws FileNotFoundException {

        File file = new File(path);
        Scanner sc = new Scanner(file);
        ArrayList<User> users = new ArrayList<>();

        while (sc.hasNextLine()) {
            String username = sc.nextLine();
            String password = sc.nextLine();
            String role = sc.nextLine();
            String name = sc.nextLine();
            String surname = sc.nextLine();
            String address = sc.nextLine();
            sc.nextLine();

            users.add(new User(username, password, Role.valueOf(role), name, surname, address));
        }
        return users;
    }

    public Product getProduct(String title, String producer, String model) throws FileNotFoundException {
        ArrayList<Product> allProducts = getAllProducts();
        for (Product product : allProducts) {
            if (product.getTitle().equalsIgnoreCase(title) && product.getProducer().equalsIgnoreCase(producer) && product.getModel().equalsIgnoreCase(model)) {
                return product;
            }

        }
        return null;
    }


    public boolean isUserExists(String username) throws FileNotFoundException {

        ArrayList<User> users = getAllUsers();

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }


    public void addUser(User user) throws IOException {

        FileWriter fw = new FileWriter(path, true);
        PrintWriter writer = new PrintWriter(fw);

        writeUser(writer, user);

        writer.close();
    }


    public ArrayList<Product> getAllProducts() throws FileNotFoundException {

        File file = new File(path);
        Scanner sc = new Scanner(file);
        ArrayList<Product> products = new ArrayList<>();

        while (sc.hasNextLine()) {
            String title = sc.nextLine();
            String producer = sc.nextLine();
            String model = sc.nextLine();
            double price = sc.nextDouble();
            sc.nextLine();
            sc.nextLine();

            products.add(new Product(title, producer, model, price));
        }
        return products;
    }
        public ArrayList<Order> getAllOrders() throws FileNotFoundException{

            File file = new File(path);
            Scanner sc = new Scanner(file);

            ArrayList<Order> orders = new ArrayList<>();

            while (sc.hasNextLine()) {
                String name = sc.nextLine();
                String surname = sc.nextLine();
                String title = sc.nextLine();
                String producer = sc.nextLine();
                String model = sc.nextLine();
                double price = sc.nextDouble();
                sc.nextLine();
                int quantity = sc.nextInt();
                sc.nextLine();
                double amount = sc.nextDouble();
                sc.nextLine();
                String payment = sc.nextLine();
                String delivery = sc.nextLine();
                sc.nextLine();

                orders.add(new Order(name,surname,title,producer,model,price,quantity,amount, Payment.valueOf(payment), Delivery.valueOf(delivery)));
            }
            return orders;
        }


    public void addProduct(Product product) throws IOException {

        FileWriter fw = new FileWriter(path, true);
        PrintWriter writer = new PrintWriter(fw);

        writeProduct(writer, product);

        writer.close();
    }

    public void addOrder(Order order) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        PrintWriter writer = new PrintWriter(fw);

        writeOrder(writer, order);

        writer.close();

    }

    public void deleteProductByModel(String model) throws IOException, ProductExeption {

        ArrayList<Product> allProducts = getAllProducts();

        Product productToDelete = getProductByModel(model, allProducts);

        allProducts.remove(productToDelete);

        rewriteAllProduct(allProducts);
    }

    public void deleteCartProductByModel(User clientUser,String model) throws IOException, ProductExeption {


        ArrayList<Product> cart = clientUser.getShoppingCart().getCart();
        Product productToDelete = getProductByModel(model, cart);

        cart.remove(productToDelete);

    }


    private Product getProductByModel(String model, ArrayList<Product> allProducts) throws ProductExeption {

        Iterator<Product> iter = allProducts.iterator();

        while (iter.hasNext()) {

            Product productToDelete = iter.next();
            if (productToDelete.getModel().equalsIgnoreCase(model)) {
                return productToDelete;
            }
        }
        throw new ProductExeption(String.format("Prekes modelis \'%s\' neegzistuoja", model));
    }


    private void rewriteAllProduct(ArrayList<Product> products) throws IOException {

        FileWriter fw = new FileWriter(path);
        PrintWriter writer = new PrintWriter(fw);

        for (Product product : products) {
            writeProduct(writer, product);
        }

        writer.close();
    }

    private void writeUser(PrintWriter writer, User user) {
        writer.println(user.getUsername());
        writer.println(user.getPassword());
        writer.println(user.getRole());
        writer.println(user.getName());
        writer.println(user.getSurname());
        writer.println(user.getAddress());
        writer.println();
    }

    private void writeProduct(PrintWriter writer, Product product) {
        writer.println(product.getTitle());
        writer.println(product.getProducer());
        writer.println(product.getModel());
        writer.println(product.getPrice());
        writer.println();
    }

    private void writeOrder(PrintWriter writer, Order order) {
        writer.println(order.getUser().getName());
        writer.println(order.getUser().getSurname());
        writer.println(order.getUser().getShoppingCart().getCart());
        writer.println(order.getUser().getShoppingCart().getQuantity());
        writer.println(order.getUser().getShoppingCart().getAmount());
        writer.println(order.getPayment());
        writer.println(order.getDelivery());
        writer.println();
    }

}
