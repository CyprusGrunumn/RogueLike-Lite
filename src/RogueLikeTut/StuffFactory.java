package RogueLikeTut;

import java.util.List;

import RogueLikeTut.Ai.BatAi;
import RogueLikeTut.Ai.FungusAi;
import RogueLikeTut.Ai.GoblinAi;
import RogueLikeTut.Ai.PlayerAi;
import asciiPanel.AsciiPanel;

public class StuffFactory {
    private World world;

    public StuffFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages, FieldOfView fov) {
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, "player", 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;
    }

    public Creature newFungus(int depth) {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, "fungus", 10, 10, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);
        return fungus;
    }

    public Creature newBat(int depth) {
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 3, 0);
        world.addAtEmptyLocation(bat, depth);
        new BatAi(bat);
        return bat;
    }

  /*  public Creature newGoblin(int depth) {
        Creature goblin = new Creature (world, 'G', AsciiPanel.green, "Goblin", 25, 15, 5);
        world.addAtEmptyLocation(goblin, depth);
        new GoblinAi(goblin);
        return goblin;
    }
*/
    public Item newRock(int depth) {
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }

    public Item newVictoryItem(int depth) {
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newBread(int depth){
        Item item = new Item('%', AsciiPanel.yellow, "bread");
        item.modifyFoodValue(200);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newFruit(int depth){
        Item item = new Item('%', AsciiPanel.brightRed, "apple");
        item.modifyFoodValue(100);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

}
