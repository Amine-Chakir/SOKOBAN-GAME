package sokoban.model;

 class Cible extends Element{



    public Cible() {
        super(ElementType.cible);
        this.setPriority(3);
    }


    @Override
    ElementType getElement() {
        return super.elementTypeProperty().get();
    }
}
