package j3lcardmarket.atelier1.model;

public class Card  {
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private String smallImgUrl;
    private int id;
    private int energy;
    private int defence;
    private int attack;
    private int price;

    public Card(String name, String description, String family, String affinity, String imgUrl, String smallImgUrl, int id, int energy, int defence, int attack, int price) {
        this.name = name;
        this.description = description;
        this.family = family;
        this.affinity = affinity;
        this.imgUrl = imgUrl;
        this.smallImgUrl = smallImgUrl;
        this.id = id;
        this.energy = energy;
        this.defence = defence;
        this.attack = attack;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFamily() {
        return family;
    }

    public String getAffinity() {
        return affinity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public int getId() {
        return id;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getPrice() {
        return price;
    }
}
