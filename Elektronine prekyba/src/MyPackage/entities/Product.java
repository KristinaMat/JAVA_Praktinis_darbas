package MyPackage.entities;

public class Product {
    private String title;
    private String producer;
    private String model;
    private double price;



    public Product(String title, String producer, String model, double price) {
        this.title = title;
        this.producer = producer;
        this.model = model;
        this.price = price;

    }

    public String getTitle() {
        return title;
    }

    public String getProducer() {
        return producer;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
