package RogueLikeTut;

import java.util.List;

import RogueLikeTut.Ai.BatAi;
import RogueLikeTut.Ai.FungusAi;
import RogueLikeTut.Ai.GoblinAi;
import RogueLikeTut.Ai.PlayerAi;
import asciiPanel.AsciiPanel;

public class StuffFactory {
    private World world;

    public enum Glyph {
        PLAYER('@'),
        FUNGUS('f'),
        BAT('b'),
        //GOBLIN('G'),
        ROCK(','),
        TEDDY_BEAR('*'),
        FOOD('%');

        private char glyph;
        public char glyph() { return glyph; }

        @Override
        public String toString() { return this.toString().toLowerCase().replace('_', ' '); }

        Glyph(char glyph) {
            this.glyph = glyph;
        }
    }

    public StuffFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages, FieldOfView fov) {
        Creature player = new Creature(world, Glyph.PLAYER.glyph(), AsciiPanel.brightWhite, "player", 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;
    }

    public Creature newFungus(int depth) {
        Creature fungus = new Creature(world, Glyph.FUNGUS.glyph(), AsciiPanel.green, "fungus", 10, 10, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);
        return fungus;
    }

    public Creature newBat(int depth) {
        Creature bat = new Creature(world, Glyph.BAT.glyph(), AsciiPanel.yellow, "bat", 15, 3, 0);
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
        Item rock = new Item(Glyph.ROCK.glyph(), AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }

    public Item newVictoryItem(int depth) {
        Item item = new Item(Glyph.TEDDY_BEAR.glyph(), AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newBread(int depth){
        Item item = new Item(Glyph.FOOD.glyph(), AsciiPanel.yellow, "bread");
        item.modifyFoodValue(200);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newFruit(int depth){
        Item item = new Item(Glyph.FOOD.glyph(), AsciiPanel.brightRed, "apple");
        item.modifyFoodValue(100);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

}
