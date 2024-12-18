package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Grid4Play extends Grid{
    private final Map<Caisse,Integer> listCaisses = new HashMap<>();


     private final BooleanProperty moveProperty = new SimpleBooleanProperty(false) ;

    private final IntegerProperty totalPushedOnTargetProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty movesCounterProperty = new SimpleIntegerProperty(0);

    private final LongBinding numberOfBallsInGrid;
    private final LongBinding numberOfBoxOnCible;

    private int compteur=0;

    public Grid4Play() {
        super();
        numberOfBallsInGrid = Bindings.createLongBinding(() -> Arrays
                .stream(getMatrix())
                .flatMap(Arrays::stream)
                .filter(Cell::isCible)
                .count());
        numberOfBoxOnCible = Bindings.createLongBinding(() -> Arrays
                .stream(getMatrix())
                .flatMap(Arrays::stream)
                .filter(Cell::hasballAndBox)
                .count());
    }

    public Grid4Play(int width, int height) {
        super(width, height);
        configListners();

        numberOfBallsInGrid = Bindings.createLongBinding(() -> Arrays
                .stream(getMatrix())
                .flatMap(Arrays::stream)
                .filter(Cell::isCible)
                .count());
        numberOfBoxOnCible = Bindings.createLongBinding(() -> Arrays
                .stream(getMatrix())
                .flatMap(Arrays::stream)
                .filter(Cell::hasballAndBox)
                .count());

    }
    public Grid4Play(Grid4Design design) {
        super(design.getGridColumnsProperty().get(), design.getGridRowsProperty().get());
        configListners();

        Cell[][] designMatrix = design.getMatrix();
        Cell[][] playMatrix = new Cell4Play[design.getGridRowsProperty().get()][design.getGridColumnsProperty().get()];
        for (int i = 0; i < getGridRowsProperty().get(); i++) {
            for (int j = 0; j < getGridColumnsProperty().get(); j++) {
                playMatrix[i][j] = new Cell4Play();
                for (Element element : designMatrix[i][j].getValue()) {
                    if (element.getElement() == ElementType.caisse)
                        playMatrix[i][j].addElement(new Caisse(++compteur));
                    else
                        playMatrix[i][j].addElement(element);
                }
            }
        }
        numberOfBallsInGrid = Bindings.createLongBinding(() -> Arrays
                .stream(getMatrix())
                .flatMap(Arrays::stream)
                .filter(Cell::isCible)
                .count());

        numberOfBoxOnCible = Bindings.createLongBinding(() -> Arrays
                .stream(getMatrix())
                .flatMap(Arrays::stream)
                .filter(Cell::hasballAndBox)
                .count());



        setMatrix(playMatrix);
    }


    public LongBinding ballCounterProperty() {
        System.out.println(numberOfBallsInGrid.get());
        return numberOfBallsInGrid;
    }


    @Override
    void play(int line, int col, ElementType elementTypeSelected) {

    }



    private boolean moveCrate(int x, int y, int xChange, int yChange) {
        if (x + xChange < 0 || x + xChange >= getGridRowsProperty().get() || y + yChange < 0 || y + yChange >= getGridColumnsProperty().get()) {
            return false;
        }
        Cell currentCell = getMatrix()[x][y];
        Cell nextCell = getMatrix()[x + xChange][y + yChange];
        if ((!nextCell.isEmpty() &&
                !nextCell.isCible()
            &&
                !nextCell.isCible() && !nextCell.isBox()
                ||
                currentCell.isBox() && nextCell.isBox()) && !currentCell.isCible() && nextCell.getElement().getElement()!=ElementType.mur
                ||
                currentCell.hasballAndBox() && nextCell.hasballAndBox()
                ||
                currentCell.isBox() && nextCell.getElement().getElement()==ElementType.mur
                ||
                currentCell.hasballAndBox() && nextCell.isBox()
        ) {
            return false;
        }
        if (currentCell.getElement().getElement() == ElementType.caisse && !nextCell.isBox() || currentCell.getElement().getElement() == ElementType.cible && !isGameCompleted() && !nextCell.isBox()) {
           if (currentCell.getCaisse()!=null)
           {
               Caisse caisse = (Caisse) currentCell.getCaisse();
               System.out.println(caisse.getId() + "move Crate" );
               currentCell.removeElement(caisse);
               nextCell.addElement(caisse);
           }
            return true;

        }
        numberOfBoxOnCible.invalidate();
        // moveProperty.invalidate();
        return true;
    }

    public boolean isValidToMoveUp() {
        int x = getJoueur().getXPosition();
        int y = getJoueur().getyPosition();

        if (x <= 0) {
            return false;
        }
        if (getMatrix()[x - 1][y].getElement().getElement() == ElementType.caisse ||( getMatrix()[x - 1][y].getElement().getElement() == ElementType.cible && !isGameCompleted())){
            return moveCrate(x - 1, y, -1, 0);
        }
        return getMatrix()[x - 1][y].getElement().getElement() != ElementType.mur;
    }

    public boolean isValidToMoveDown() {
        int x = getJoueur().getXPosition();
        int y = getJoueur().getyPosition();
        if (x >= getGridRowsProperty().get() - 1) {
            return false;
        }
        if (getMatrix()[x + 1][y].getElement().getElement() == ElementType.caisse|| (getMatrix()[x +1][y].getElement().getElement() == ElementType.cible && !isGameCompleted())) {
            return moveCrate(x + 1, y, 1, 0);
        }
        return getMatrix()[x + 1][y].getElement().getElement() != ElementType.mur;
    }

    public boolean isValidToMoveLeft() {
            int x = getJoueur().getXPosition();
            int y = getJoueur().getyPosition();
        if (y <= 0) {
            return false;
        }
        if (getMatrix()[x][y - 1].getElement().getElement() == ElementType.caisse|| (getMatrix()[x][y - 1].getElement().getElement() == ElementType.cible && !isGameCompleted())) {
            return moveCrate(x, y - 1, 0, -1);
        }
        return getMatrix()[x][y - 1].getElement().getElement() != ElementType.mur;
    }

    public boolean isValidToMoveRight() {
        int x = getJoueur().getXPosition();
        int y = getJoueur().getyPosition();
        if (y >= getGridColumnsProperty().get() - 1) {
            return false;
        }

        if (getMatrix()[x][y + 1].getElement().getElement() == ElementType.caisse||(getMatrix()[x][y + 1].getElement().getElement() == ElementType.cible && !isGameCompleted())) {
            return moveCrate(x, y + 1, 0, 1);
        }
        return getMatrix()[x][y + 1].getElement().getElement() != ElementType.mur;
    }


//    void configListners()
//    {
//
//        getJoueur().xPositionProperty().addListener((obs,old,newVal)->
//                {
//                    getMatrix()[old.intValue()][getJoueur().getyPosition()].removeElement(getJoueur());
//                        getMatrix()[getJoueur().getXPosition()][getJoueur().getyPosition()].addElement(getJoueur());
//                }
//        );
//        getJoueur().yPositionProperty().addListener((obs,old,newVal)->
//                {
//                    getMatrix()[getJoueur().getXPosition()][old.intValue()].removeElement(getJoueur());
//                    getMatrix()[getJoueur().getXPosition()][getJoueur().getyPosition()].addElement(getJoueur());
//                }
//        );
//    }

    void configListners() {
    getJoueur().xPositionProperty().addListener((obs, old, newVal) -> {
        int oldX = old.intValue();
        int newY = getJoueur().getyPosition();
        if (isValidPosition(newVal.intValue(), newY) && isValidPosition(oldX, newY)) {
            getMatrix()[oldX][newY].removeElement(getJoueur());
            getMatrix()[newVal.intValue()][newY].addElement(getJoueur());
        }
    });

    getJoueur().yPositionProperty().addListener((obs, old, newVal) -> {
        int newX = getJoueur().getXPosition();
        int oldY = old.intValue();
        if (isValidPosition(newX, newVal.intValue()) && isValidPosition(newX, oldY)) {
            getMatrix()[newX][oldY].removeElement(getJoueur());
            getMatrix()[newX][newVal.intValue()].addElement(getJoueur());
        }
    });
    }

    boolean isValidPosition(int x, int y) {
        return x >= 0 && x < getGridRowsProperty().get() && y >= 0 && y < getGridColumnsProperty().get();
    }


    public void moveDown() {
        if (isValidToMoveDown() && !isGameCompleted()) {
            getJoueur().moveDown(getGridRowsProperty().get());
            numberOfBoxOnCible.invalidate();
            //checkTarget(getJoueur().getXPosition() + 1, getJoueur().getyPosition());
            if (!isGameCompleted()) {
                movesCounterProperty.set(movesCounterProperty.get() + 1);
            }
        }
      //  moveProperty.invalidate();

    }

    public void moveUp() {
        if (isValidToMoveUp() && !isGameCompleted()) {
            System.out.println("moveUP");
            getJoueur().moveUp();
            numberOfBoxOnCible.invalidate();
          //  checkTarget(getJoueur().getXPosition() - 1, getJoueur().getyPosition());
            if (!isGameCompleted()) {
                movesCounterProperty.set(movesCounterProperty.get() + 1);
            }
        }
      //  moveProperty.invalidate();

    }

    public void moveRight() {
        if (isValidToMoveRight() && !isGameCompleted()) {
            getJoueur().moveRight(getGridColumnsProperty().get());
            numberOfBoxOnCible.invalidate();
          //  checkTarget(getJoueur().getXPosition(), getJoueur().getyPosition() + 1);
            if (!isGameCompleted()) {
                movesCounterProperty.set(movesCounterProperty.get() + 1);
            }
        }

    }

    public void moveLeft() {
        if (isValidToMoveLeft() && !isGameCompleted()) {
            getJoueur().moveLeft();
            numberOfBoxOnCible.invalidate();
          //  checkTarget(getJoueur().getXPosition(), getJoueur().getyPosition() - 1);
            if (!isGameCompleted()) {
                movesCounterProperty.set(movesCounterProperty.get() + 1);
            }
        }
       // moveProperty.invalidate();

    }

    public IntegerProperty movesCounterProperty() {
        return movesCounterProperty;
    }
    public BooleanProperty getMoveProperty(){
        return moveProperty;
    }

    private boolean isGameCompleted() {
        boolean gameCompleted = numberOfBallsInGrid.getValue() == numberOfBoxOnCible.getValue();
        if (gameCompleted) {
            moveProperty.set(true);
        }

        return gameCompleted;
    }


    public LongBinding totalPushedOnTargetProperty() {
        return numberOfBoxOnCible;
    }


    private void checkTarget(int row, int col) {
        if (row >= 0 && row < getGridRowsProperty().get() && col >= 0 && col < getGridColumnsProperty().get() && getMatrix()[row][col] != null && getMatrix()[row][col].getElement() != null) {
            if (getMatrix()[row][col].getElement().getElement() == ElementType.cible) {
                for (Element element : getMatrix()[row][col].getValue()) {
                    if (element.getElement() == ElementType.caisse && !getMoveProperty().get()) {
                        Caisse caisse = (Caisse) element;
                        if (!listCaisses.containsValue(caisse.getId())) {
                            listCaisses.put(caisse, caisse.getId());
                            totalPushedOnTargetProperty.set(totalPushedOnTargetProperty.get() + 1);
                        }
                    }
                }
            }
        }
    }





}
