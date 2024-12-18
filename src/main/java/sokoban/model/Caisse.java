package sokoban.model;

public class Caisse extends Element{

    private int id;

    public Caisse() {
        super(ElementType.caisse);
        this.setPriority(2);
    }
    public Caisse(int id) {
        super(ElementType.caisse);
        this.setPriority(2);
        this.id = id;
    }

    @Override
    ElementType getElement() {
        return super.elementTypeProperty().get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
