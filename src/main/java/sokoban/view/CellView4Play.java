package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import sokoban.model.Caisse;
import sokoban.model.Element;
import sokoban.model.ElementType;
import sokoban.viewmodel.CellViewModel4Play;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

 class CellView4Play extends CellView{
    protected final CellViewModel4Play viewModel;

    private final List<ImageView> imageViewList = new ArrayList<>();

    private final static Map<ElementType, Image> imageMap  = new TreeMap<>();

    static {

        imageMap.put(ElementType.joueur,new Image("player.png"));
        imageMap.put(ElementType.terrain,new Image("ground.png"));
        imageMap.put(ElementType.cible,new Image("goal.png"));
        imageMap.put(ElementType.caisse,new Image("box.png"));
        imageMap.put(ElementType.mur,new Image("wall.png"));
    }

    CellView4Play(CellViewModel4Play cellViewModel, DoubleBinding cellWidthProperty) {
        super(cellWidthProperty);
        viewModel = cellViewModel;
        setOnKeyPressed(new PlayerMoveHandler());
        setFocusTraversable(true);
        layoutControls();
        configListners();

    }

    private class PlayerMoveHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            KeyCode code = event.getCode();
            switch (code) {
                case UP:
                case Z:
                  viewModel.movePlayerUp();
                    break;
                case DOWN:
                case S:
                   viewModel.movePlayerDown();
                    break;
                case LEFT:
                case Q:
                   viewModel.movePlayerLeft();
                    break;
                case RIGHT:
                case D:
                   viewModel.movePlayerRight();
                    break;
                default:
                    break;
            }
            event.consume();
        }
    }
    private void layoutControls() {
        for (Element element : viewModel.valueProperty().get()) {
            ImageView imageView = new ImageView(getImageElement(element));
            imageView.fitWidthProperty().bind(widthPropertyProperty());
            imageView.setPreserveRatio(true);
            imageViewList.add(imageView);

            if (element.elementTypeProperty().get() == ElementType.caisse) {
                Caisse caisse = (Caisse) element;
                Label numLabel = new Label(String.valueOf(caisse.getId()));
                numLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                StackPane.setAlignment(numLabel, Pos.CENTER);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(imageView, numLabel);
                getChildren().add(stackPane);
            } else {
                getChildren().add(imageView);
            }
        }
    }

    private void configListners() {
        viewModel.isListChangedProperty().addListener((obs, old, newVal) -> {
            getChildren().clear();
            layoutControls();
        });

        hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (imageViewList.size() > 1) {
                imageViewList.get(1).setOpacity(newVal ? 0.5 : 1.0);
            } else {
                imageViewList.get(0).setOpacity(newVal ? 0.5 : 1.0);
            }
        });
    }
    private Image getImageElement(Element elementType)
    {
        return imageMap.get(elementType.elementTypeProperty().get());
    }



}
