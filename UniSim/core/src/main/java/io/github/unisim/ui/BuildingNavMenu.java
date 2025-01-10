package io.github.unisim.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;

public class BuildingNavMenu {

    private Table mainTable = new Table();
    private Map<BuildingType, Table> buildingTypes = new HashMap<>();
    private Map<BuildingType, Queue<Label>> buildingCosts = new HashMap<>();
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private Stack content;
    private HorizontalGroup group;
    private final Button eatB = new TextButton("Eating", skin, "toggle");
    private final Button sleepB = new TextButton("Sleeping", skin, "toggle");
    private final Button recB = new TextButton("Recreation", skin, "toggle");
    private final Button learnB = new TextButton("Learning", skin, "toggle");

    public BuildingNavMenu(ArrayList<Building> buildings) {
        for (BuildingType type : BuildingType.values()) {
            buildingTypes.put(type, new Table());
            buildingCosts.put(type, new LinkedList<>());
        }
    }

    public void createTable() {
        for (BuildingType type : buildingTypes.keySet()) {
            buildingTypes.get(type).row();
            while (!buildingCosts.get(type).isEmpty()) {
                buildingTypes.get(type).add(buildingCosts.get(type).poll());
            }
        }

        group = new HorizontalGroup();
        eatB.pad(0, 5, 0, 5);
        sleepB.pad(0, 5, 0, 5);
        recB.pad(0, 5, 0, 5);
        learnB.pad(0, 5, 0, 5);
        group.addActor(eatB);
        group.addActor(sleepB);
        group.addActor(recB);
        group.addActor(learnB);

        content = new Stack();
        final Table eatTable = buildingTypes.get(BuildingType.EATING);
        final Table recTable = buildingTypes.get(BuildingType.RECREATION);
        final Table sleepTable = buildingTypes.get(BuildingType.SLEEPING);
        final Table learnTable = buildingTypes.get(BuildingType.LEARNING);
        content.addActor(eatTable);
        content.addActor(recTable);
        content.addActor(sleepTable);
        content.addActor(learnTable);

        mainTable.add(group);
        mainTable.row();
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

    public void addBuilding(BuildingType type, Image buildingImage, int cost) {
        Label l = new Label("$" + cost, skin);
        l.setAlignment(Align.center);
        l.setAlignment(Align.top);
        buildingCosts.get(type).add(l);
        buildingTypes.get(type).add(buildingImage);
    }

    public Table getTable() {
        return mainTable;
    }

    public void changeBuildingType(int i) {
        switch (i) {
            case 1:
                eatB.setChecked(true);
                break;
            case 2:
                sleepB.setChecked(true);
                break;
            case 3:
                recB.setChecked(true);
                break;
            case 4:
                learnB.setChecked(true);
                break;
            default:
                break;
        }
    }

    public void resize(int width, int height) {
        mainTable.setBounds(0, height * 0.02f, width, height * 0.108f);
        Actor[] tables = content.getChildren().toArray();
        for (Actor a1 : tables) {
            Table table = (Table) a1;
            for (Actor a2 : table.getChildren()) {
                Vector2 textureSize = new Vector2(a2.getWidth(), a2.getHeight());
                table.getCell(a2).width(
                    height * 0.1f * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1)
                ).height(
                    height * 0.1f * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1)
                );
            }
        }
    }
}
