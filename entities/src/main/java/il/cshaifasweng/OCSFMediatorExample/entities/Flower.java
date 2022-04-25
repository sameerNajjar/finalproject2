package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.*;


@Entity
@Table(name = "Flowers")
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int price;
    @Column(name="Color")
    private String color;

    public Flower() {
    }

    public Flower(int id, int price, String color) {
        super();
        this.id = id;
        this.price = price;
        this.color = color;
    }

    public Flower(int price, String color) {
        this.price = price;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", price=" + price +
                ", color='" + color + '\'' +
                '}';
    }
}
