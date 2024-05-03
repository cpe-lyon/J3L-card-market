package j3lcardmarket.atelier1.model;

public class Card  {
    private String color;
    private String superPower;
    private String name;
    private String imgUrl;

    public Card() {
        this.color = "";
        this.superPower = "";
        this.name = "";
        this.imgUrl="";
    }
    public Card(String name,String color,String superPower, String imgUrl) {
        this.color = color;
        this.superPower = superPower;
        this.name = name;
        this.imgUrl=imgUrl;
    }

    // GETTER AND SETTER


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
