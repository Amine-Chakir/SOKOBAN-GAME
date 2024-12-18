package sokoban.model;

class Mur extends Element {

    public Mur() {
        super(ElementType.mur);
        this.setPriority(2);
    }

    @Override
    ElementType getElement() {
        return super.elementTypeProperty().get();
    }
}
