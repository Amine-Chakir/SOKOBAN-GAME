package sokoban.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Board4Play;
import sokoban.model.Element;

import java.util.TreeSet;

public class CellViewModel4Play {
    private final int line, col;
    private final Board4Play board4Play;

    CellViewModel4Play(int line, int col, Board4Play board4Play) {
        this.line = line;
        this.col = col;
        this.board4Play = board4Play;
    }


    public void movePlayerUp() {
        if (board4Play.isValidToMoveUp())
            board4Play.moveUp();
    }

    public void movePlayerDown() {
        if (board4Play.isValidToMoveDown())
            board4Play.moveDown();
    }
    public void movePlayerRight() {
        if (board4Play.isValidToMoveRight())
            board4Play.moveRight();
    }
    public void movePlayerLeft() {
        if (board4Play.isValidToMoveLeft())
            board4Play.moveLeft();
    }

    public ReadOnlyObjectProperty<TreeSet<Element>> valueProperty() {
        return board4Play.valueProperty(line, col);
    }

    public BooleanProperty isListChangedProperty()
    {
        return board4Play.isListChangedProperty(line, col);
    }



}
