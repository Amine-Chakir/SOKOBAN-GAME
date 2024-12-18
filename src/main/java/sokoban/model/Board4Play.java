package sokoban.model;

import javafx.beans.binding.LongBinding;
import javafx.beans.property.*;

import java.util.TreeSet;

public class Board4Play extends Board{

    private final ObjectProperty<Grid4Play> grid;



    public Board4Play(int width, int height) {
        super(width, height);
        grid = new SimpleObjectProperty<>(new Grid4Play(width,height));
    }

    public Board4Play(Grid4Design design) {
        grid = new SimpleObjectProperty<>(new Grid4Play(design));
    }


    public void moveDown()
    {
        grid.get().moveDown();
    }
    public void moveUp()
    {
        grid.get().moveUp();
    }
    public void moveRight()
    {
        grid.get().moveRight();
    }
    public void moveLeft()
    {
        grid.get().moveLeft();
    }
    public ReadOnlyObjectProperty<TreeSet<Element>> valueProperty(int line, int col) {
        return grid.get().valueProperty(line, col);
    }

    public BooleanProperty isListChangedProperty(int line, int col)
    {
        return grid.get().isListChangedProperty(line, col);
    }
    public IntegerProperty getGridColumnsProperty()
    {
        return grid.get().getGridColumnsProperty();
    }


    public IntegerProperty getGridRowsProperty() {
        return grid.get().getGridRowsProperty();
    }

    public BooleanProperty GridChangedProperty() {
        return grid.get().GridChangedProperty();
    }
    public LongBinding ballCounterProperty() {
        return grid.get().ballCounterProperty();
    }

    public boolean isValidToMoveUp() {
       return grid.get().isValidToMoveUp();
    }

    public boolean isValidToMoveDown()
    {
        return grid.get().isValidToMoveDown();
    }
    public boolean isValidToMoveRight()
    {
        return grid.get().isValidToMoveRight();
    }
    public boolean isValidToMoveLeft()
    {
        return grid.get().isValidToMoveLeft();
    }



    public LongBinding totalPushedOnTargetProperty() {
        return grid.get().totalPushedOnTargetProperty();
    }
    public IntegerProperty movesCounterProperty() {
        return grid.get().movesCounterProperty();
    }
    public BooleanProperty getMoveProperty(){
        return grid.get().getMoveProperty();
    }

}
