package io.github.unisim.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;

public class BuildingNavMenu {

    private Table mainTable = new Table();
    private Map<BuildingType, Table> buildingTypes = new HashMap<>();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private Stack content;

    public BuildingNavMenu(ArrayList<Building> buildings) {
        HorizontalGroup group = new HorizontalGroup();
        final Button eatB = new TextButton("Eating", skin, "toggle");
        final Button sleepB = new TextButton("Sleeping", skin, "toggle");
        final Button recB = new TextButton("Recreation", skin, "toggle");
        final Button learnB = new TextButton("Learning", skin, "toggle");
        group.addActor(eatB);
        group.addActor(sleepB);
        group.addActor(recB);
        group.addActor(learnB);
        mainTable.addActor(group);
        mainTable.row();

        for (BuildingType type : BuildingType.values()) {
            buildingTypes.put(type, new Table());
        }

        content = new Stack();
        Table eatTable = buildingTypes.get(BuildingType.EATING);
        Table recTable = buildingTypes.get(BuildingType.RECREATION);
        Table sleepTable = buildingTypes.get(BuildingType.SLEEPING);
        Table learnTable = buildingTypes.get(BuildingType.LEARNING);
        content.addActor(eatTable);
        content.addActor(recTable);
        content.addActor(sleepTable);
        content.addActor(learnTable);

        mainTable.add(content).expand().fill();

        ChangeListener listener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            eatTable.setVisible(eatB.isChecked());
            recTable.setVisible(recB.isChecked());
            sleepTable.setVisible(sleepB.isChecked());
            learnTable.setVisible(learnB.isChecked());
        }
        };

        eatB.addListener(listener);
        recB.addListener(listener);
        sleepB.addListener(listener);
        learnB.addListener(listener);

        ButtonGroup<Button> tabs = new ButtonGroup<>();
        tabs.setMinCheckCount(1);
        tabs.setMaxCheckCount(1);
        tabs.add(eatB);
        tabs.add(recB);
        tabs.add(sleepB);
        tabs.add(learnB);
    }

    public void addBuilding(BuildingType type, Image buildingImage) {
        buildingTypes.get(type).addActor(buildingImage);
    }

    public Table getTable() {
        return mainTable;
    }

    @SuppressWarnings("unchecked")
    public void resize(int width, int height) {
        mainTable.setBounds(width/2f, height * 0.025f, width, height * 0.1f);
        Table[] tables = (Table[]) content.getChildren().toArray();
        for (Table table : tables) {
            for (Cell<Actor> cell2 : table.getCells()) {
                System.out.println(cell2);
                Image buildingImage = (Image) cell2.getActor();
                Vector2 textureSize = new Vector2(buildingImage.getWidth(), buildingImage.getHeight());
                cell2.width(
                    height * 0.1f * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1)
                ).height(
                    height * 0.1f * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1)
                );
            }
        }
    }
}
