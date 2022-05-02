package MyPackage;

import MyPackage.entities.*;

import MyPackage.exeption.InputMismatchException;
import MyPackage.exeption.ProductExeption;
import MyPackage.exeption.UserException;

import static MyPackage.entities.Role.CLIENT;
import static MyPackage.entities.Role.ADMIN;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static final String NEATPAZINTA_IVESTIS = "Neatpazinta ivestis";
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) throws IOException, UserException, ProductExeption {

        OrderManagement usersdata = new OrderManagement("src/MyPackage/userdata");

        try {
            while (true) {

                printMainMenu();
                String loginInput = SC.nextLine();

                switch (loginInput) {
                    case "1":
                        System.out.print("Iveskite prisijungimo varda: ");
                        String username = SC.nextLine();
                        System.out.print("Iveskite slaptazodi: ");
                        String password = SC.nextLine();
                        try {
                            User loggedInUser = usersdata.getUser(username, password);
                            userMenu(loggedInUser, usersdata);
                        } catch (UserException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "2":
                        registerNewUser(usersdata);
                        break;
                    case "3":
                        return;
                    default:
                        System.out.println(NEATPAZINTA_IVESTIS);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR nerasta duomenu failo");
        }
    }

    private static void userMenu(User user, OrderManagement usersdata) throws IOException, ProductExeption {

        if (user.getRole().equals(CLIENT)) {

            clientUserMenu(user);
        } else if (user.getRole().equals(ADMIN)) {

            adminUserMenu(user, usersdata);
        }
    }

    private static void adminUserMenu(User adminUser, OrderManagement usersdata) throws IOException, ProductExeption {
        OrderManagement productdata = new OrderManagement("src/MyPackage/productlist");
        OrderManagement ordersdata = new OrderManagement("src/MyPackage/orderdata");

        while (true) {
            printAdminMenu();
            String choice = SC.nextLine();

            switch (choice) {
                case "1":
                    ArrayList<Product> allProducts = productdata.getAllProducts();
                    printAllProducts(allProducts);
                    break;
                case "2":
                    ArrayList<User> allUsers = usersdata.getAllUsers();
                    printAllUsers(allUsers);
                    break;
                case "3":
                    try{
                        ArrayList<Order> allOrders = ordersdata.getAllOrders();
                        printAllOrders(allOrders);
                    } catch ( InputMismatchException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case "4":
                    addNewProduct(productdata);

                    break;
                case "5":
                    System.out.println("-------Prekės trynimas-------------");
                    System.out.print("Iveskite trinamos prekes modeli: ");
                    String modelToDelete = SC.nextLine();

                    try {
                        productdata.deleteProductByModel(modelToDelete);
                        System.out.printf("Prekes modelis \'%s\' sekmingai istrintas \n", modelToDelete);

                    } catch (ProductExeption e) {
                        System.out.println((e.getMessage()));
                    }

                    break;
                case "6":
                    return;

                default:
                    System.out.println(NEATPAZINTA_IVESTIS);
                    break;
            }
        }
    }

    private static void clientUserMenu(User clientUser) throws IOException {
        OrderManagement productdata = new OrderManagement("src/MyPackage/productlist");

        while (true) {
            printClientMenu();
            String choice = SC.nextLine();

            switch (choice) {
                case "1":
                    ArrayList<Product> allProducts = productdata.getAllProducts();
                    printAllProducts(allProducts);
                    break;
                case "2":
                    addToShoppingCart(clientUser, productdata);
                    break;
                case "3":
                    System.out.print("Iveskite trinamos prekes modeli: ");
                    String modelToDelete = SC.nextLine();

                    try {
                      productdata.deleteCartProductByModel(clientUser, modelToDelete);

                        System.out.println("----------------------------------------------------------");
                        System.out.printf("Prekes modelis \'%s\' sekmingai istrintas \n", modelToDelete);
                        System.out.println("-----------------------------------------------------------");



                    } catch (ProductExeption e) {
                        System.out.println((e.getMessage()));
                    }

                    break;
                case "4":
                    printShopingCart(clientUser.getShoppingCart().getCart());
                    System.out.println("*************************************");
                    System.out.println("Prekiu kiekis krepselyje: " + clientUser.getShoppingCart().getQuantity() + " Vnt");
                    System.out.println("Bendra prekiu kaina krepselyje: " + clientUser.getShoppingCart().getAmount() + " Eur");
                    System.out.println("**************************************");
                    System.out.println();
                    break;
                case "5":
                    addOrder(clientUser, productdata);

                    break;
                case "6":
                    System.out.println("********UZSAKYMAS**********");
                    System.out.println(clientUser.getName() + " " + clientUser.getSurname());
                    System.out.println("-----------------------------");
                    System.out.println("Prekiu kiekis krepselyje: " + clientUser.getShoppingCart().getQuantity() + " Vnt");
                    System.out.println("Bendra prekiu kaina krepselyje: " + clientUser.getShoppingCart().getAmount() + " Eur");
                    System.out.println("Apmokejimas: " + clientUser.getOrder().getPayment());
                    System.out.println("Pristatymas: " + clientUser.getOrder().getDelivery());
                    System.out.println("------------------------------");
                    printShopingCart(clientUser.getShoppingCart().getCart());
                    System.out.println();
                    System.out.println();
                    break;
                case "7":
                    return;
                default:
                    System.out.println(NEATPAZINTA_IVESTIS);
                    break;
            }
        }
    }

    private static void addOrder(User clientUser, OrderManagement orderdata) throws IOException {
        System.out.println("-------Prekiu uzsakymas:-------------");

        String userName = clientUser.getName();
        String userSurname = clientUser.getSurname();
        String cart = String.valueOf(clientUser.getShoppingCart().getCart());
        int quantity = clientUser.getShoppingCart().getQuantity();
        double amount = clientUser.getShoppingCart().getAmount();
        Payment payment = getPayment();
        Delivery delivery = getDelivery();

        orderdata.addOrder(new Order(userName, userSurname, cart, quantity, amount, payment, delivery));


    }

    private static Payment getPayment() {
        System.out.println("Pasirinkite atsiskaitymo buda:");
        Payment[] payments = Payment.values();
        while (true) {
            for (int i = 0; i < payments.length; i++) {
                System.out.printf("[%d] %s\n", i + 1, payments[i]);
            }
            int choicePayment = SC.nextInt();
            SC.nextLine();
            if (choicePayment >= 1 && choicePayment <= payments.length) {
                return payments [choicePayment - 1];
            }
        }
    }

    private static Delivery getDelivery() {
        System.out.println("Pasirinkite pristatymo buda:");
        Delivery[] deliveries = Delivery.values();
        while (true) {
            for (int i = 0; i < deliveries.length; i++) {
                System.out.printf("[%d] %s\n", i + 1, deliveries[i]);
            }
            int choicePayment = SC.nextInt();
            SC.nextLine();
            if (choicePayment >= 1 && choicePayment <= deliveries.length) {
                return deliveries [choicePayment - 1];
            }
        }
    }

    private static void addNewProduct(OrderManagement productdata) throws IOException {

        System.out.println("------------Naujos prekes ivedimas------------");
        System.out.print("Iveskite prekes pavadinima: ");
        String title = SC.nextLine();
        System.out.print("Iveskite prekes gamintoja: ");
        String producer = SC.nextLine();
        System.out.print("Iveskite prekes modeli: ");
        String model = SC.nextLine();
        System.out.print("Iveskite prekes kaina: ");
        double price = SC.nextDouble();
        SC.nextLine();

        productdata.addProduct(new Product(title, producer, model, price));
        System.out.println("--------------Preke sekmingai itraukta i asortimenta------------------");
        System.out.println();
    }

    private static void addToShoppingCart(User user, OrderManagement productdata) throws IOException {
        System.out.println("-------Prekiu krepselis-------------");
        String keepShopping = "T";

        do {
            System.out.print("Iveskite prekes pavadinima: ");
            String titleToAdd = SC.nextLine();
            System.out.print("Iveskite prekes gamintoją: ");
            String producerToAdd = SC.nextLine();
            System.out.print("Iveskite prekes modeli: ");
            String modelToAdd = SC.nextLine();
            double priceToAdd = 0;

            Product productToAdd = productdata.getProduct(titleToAdd, producerToAdd, modelToAdd);
            if (productToAdd != null) {
                user.getShoppingCart().addProduct(productToAdd);
                System.out.println("----------------------------------------");
                System.out.printf("Preke \'%s %s %s\' sekmingai ideta \n", titleToAdd, producerToAdd, modelToAdd);
                System.out.println("----------------------------------------");
            } else {
                System.out.println("Tokia preke neegzistuoja");
            }

            System.out.println("Testi apsipirkima(T/N)?");
            keepShopping = SC.nextLine();
        }
        while (keepShopping.equalsIgnoreCase("T"));
    }

    private static void registerNewUser(OrderManagement usersdata) throws IOException {

        System.out.println("------------Naujo vartotojo registracija:------------");
        System.out.println();
        String username = getValidUsername(usersdata);
        System.out.print("Iveskite slaptazodi: ");
        String password = SC.nextLine();
        System.out.print("Iveskite varda: ");
        String name = SC.nextLine();
        System.out.print("Iveskite pavarde: ");
        String surname = SC.nextLine();
        System.out.print("Iveskite adresa: ");
        String address = SC.nextLine();

        User user = new User(username, password, name, surname, address);
        usersdata.addUser(user);
        System.out.println("Vartotojas sekmingai uzregistruotas");
        System.out.println();

        clientUserMenu(user);
    }

    private static String getValidUsername(OrderManagement usersdata) throws FileNotFoundException {

        String username = "";
        while (true) {
            System.out.print("Iveskite prisijungimo varda: ");
            username = SC.nextLine();
            if (!usersdata.isUserExists(username)) {
                return username;
            }
            System.out.printf("Vartotojo vardas \'%s\' uzimtas\n", username);
            System.out.println();
        }
    }

    private static void printShopingCart(ArrayList<Product> cart) {
        System.out.println("-------PREKES:-----");
        System.out.println();
        for (Product product : cart) {
            System.out.println("Pavadinimas: " + product.getTitle());
            System.out.println("Gamintojas: " + product.getProducer());
            System.out.println("Modelis: " + product.getModel());
            System.out.println("Kaina: " + product.getPrice() + "Eur");
            System.out.println("");
        }
    }

    private static void printAllUsers(ArrayList<User> users) {

        for (User user : users) {
            printUserForAdmin(user);
        }
    }

    private static void printAllProducts(ArrayList<Product> products) {

        for (Product product : products) {
            System.out.println("--------------------------------------");
            System.out.println("Prekės pavadinimas: " + product.getTitle());
            System.out.println("Gamintojas: " + product.getProducer());
            System.out.println("Modelis: " + product.getModel());
            System.out.println("Kaina: " + product.getPrice() + " Eur");
            System.out.println();
        }
    }

    private static void printAllOrders(ArrayList<Order> orders) throws InputMismatchException {

        Order order = new Order();
        System.out.print("Vardas: " + order.getUser().getName());
        System.out.print("Pavarde: " + order.getUser().getSurname());
        for (Order ord : orders) {
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("Prekės pavadinimas: " + ord.getUser().getShoppingCart().getCart());
            System.out.println("Gamintojas: " + ord.getUser().getShoppingCart().getCart());
            System.out.println("Modelis: " + ord.getUser().getShoppingCart().getCart());
            System.out.println("Kaina: " + ord.getUser().getShoppingCart().getCart());
            System.out.println("Kiekis: " + ord.getQuantity());
            System.out.println("Suma apmokejimui: " + ord.getAmount());
            System.out.println("Apmokejimas: " + ord.getPayment());
            System.out.println("Pristatymas: " + ord.getDelivery());
            System.out.println("---------------------------------------");
        }
    }

    private static void printUserForAdmin(User adminUser) {
        System.out.println("--------------------------------------");
        System.out.println("Prisijungimo vardas: " + adminUser.getUsername());
        System.out.println("Vardas: " + adminUser.getName());
        System.out.println("Pavarde: " + adminUser.getSurname());
        System.out.println("Adresas: " + adminUser.getAddress());
        System.out.println("Role: " + adminUser.getRole());
        System.out.println("---------------------------------------");
    }

    private static void printAdminMenu() {
        System.out.println();
        System.out.println("--------------MENIU----------------");
        System.out.println("[1] Perziureti visa prekiu sarasa");
        System.out.println("[2] Perziureti registruotu vartotoju informacija");
        System.out.println("[3] Perziureti visus užsakymus");
        System.out.println("[4] Pridėti naują preke");
        System.out.println("[5] Istrinti egzistuojancia preke");
        System.out.println("[6] Atsijungti");
        System.out.println("------------------------------------");
    }

    private static void printClientMenu() {

        System.out.println();
        System.out.println("--------------MENIU----------------");
        System.out.println("[1] Perziureti visa prekiu sarasa");
        System.out.println("[2] Prideti prekę i pirkiniu krepseli");
        System.out.println("[3] Istrinti preke is pirkiniu krepselio");
        System.out.println("[4] Perziureti savo pirkiniu krepseli");
        System.out.println("[5] Pateikti nauja uzsakyma");
        System.out.println("[6] Perziureti savo pateiktus užsakymus");
        System.out.println("[7] Atsijungti");
        System.out.println();
    }

    private static void printMainMenu() {

        System.out.println();
        System.out.println("----PAGRINDINIS MENIU----");
        System.out.println();
        System.out.println("[1] Prisijungti");
        System.out.println("[2] Registruotis");
        System.out.println("[3] Atsijungti");
        System.out.println();
        System.out.println("--------------------------");
        System.out.println();
    }
}

