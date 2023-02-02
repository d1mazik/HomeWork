public class Shoe {

    protected int id;
    protected String size;
    protected String brand;
    protected String color;
    protected int price;


    public Shoe(int id, String size, String brand, String color, int price) {
        this.id = id;
        this.size = size;
        this.brand = brand;
        this.color = color;
        this.price = price;
    }

    @Override
    public String toString() {
        return brand + " " + size + " " +
                color + " " + price;
    }

    public Shoe(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
