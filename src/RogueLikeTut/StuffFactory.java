package RogueLikeTut;

import java.awt.*;
import java.util.List;

import RogueLikeTut.Ai.BatAi;
import RogueLikeTut.Ai.FungusAi;
import RogueLikeTut.Ai.GoblinAi;
import RogueLikeTut.Ai.PlayerAi;
import asciiPanel.AsciiPanel;

public class StuffFactory {
    private World world;

    public enum Glyph {
        PLAYER('@', AsciiPanel.brightWhite),
        FUNGUS('f', AsciiPanel.green),
        BAT('b', AsciiPanel.yellow),
        //GOBLIN('G', AsciiPanel.green),
        ROCK(',', AsciiPanel.yellow),
        TEDDY_BEAR('*', AsciiPanel.brightWhite),
        BREAD('%', AsciiPanel.yellow),
        FRUIT('%', AsciiPanel.brightRed);

        private char glyph;
        public char glyph() { return glyph; }

        private Color color;
        public Color color() { return color; }

        @Override
        public String toString() { return super.toString().toLowerCase().replace('_', ' '); }

        Glyph(char glyph, Color color) {
            this.glyph = glyph;
            this.color = color;
        }
    }

    public StuffFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages, FieldOfView fov) {
        Creature player = new Creature(world,
                Glyph.PLAYER.glyph(), Glyph.PLAYER.color(), Glyph.PLAYER.toString(),
                100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;
    }

    public Creature newFungus(int depth) {
        Creature fungus = new Creature(world,
                Glyph.FUNGUS.glyph(), Glyph.FUNGUS.color(), Glyph.FUNGUS.toString(),
                10, 10, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);
        return fungus;
    }

    public Creature newBat(int depth) {
        Creature bat = new Creature(world,
                Glyph.BAT.glyph(), Glyph.BAT.color(), Glyph.BAT.toString(),
                15, 3, 0);
        world.addAtEmptyLocation(bat, depth);
        new BatAi(bat);
        return bat;
    }

  /*  public Creature newGoblin(int depth) {
        Creature goblin = new Creature (world,
                Glyph.GOBLIN.glyph(), Glyph.GOBLIN.color(), Glyph.GOBLIN.toString(),
                25, 15, 5);
        world.addAtEmptyLocation(goblin, depth);
        new GoblinAi(goblin);
        return goblin;
    }
*/
    public Item newRock(int depth) {
        Item rock = new Item(Glyph.ROCK.glyph(), Glyph.ROCK.color(), Glyph.ROCK.toString());
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }

    public Item newVictoryItem(int depth) {
        Item item = new Item(Glyph.TEDDY_BEAR.glyph(), Glyph.TEDDY_BEAR.color(), Glyph.TEDDY_BEAR.toString());
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newBread(int depth){
        Item item = new Item(Glyph.BREAD.glyph(), Glyph.BREAD.color(), Glyph.BREAD.toString());
        item.modifyFoodValue(200);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newFruit(int depth){
        Item item = new Item(Glyph.FRUIT.glyph(), Glyph.FRUIT.color(), Glyph.FRUIT.toString());
        item.modifyFoodValue(100);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

}
