package RogueLikeTut;

import java.awt.*;
import java.util.List;

import RogueLikeTut.Ai.*;
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

    public Creature newZombie(int depth, Creature player){
        Creature zombie = new Creature(world, 'z', AsciiPanel.white, "zombie", 50, 10, 10);
        world.addAtEmptyLocation(zombie, depth);
        new ZombieAi(zombie, player);
        return zombie;
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
        rock.modifyThrownAttackValue(5);
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

    public Item newDagger(int depth){
        Item item = new Item('|', AsciiPanel.white, "dagger");
        item.modifyAttackValue(5);
        item.modifyThrownAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
    }
    public Item newSword(int depth){
        Item item = new Item('|', AsciiPanel.brightWhite, "sword");
        item.modifyAttackValue(10);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newStaff(int depth){
        Item item = new Item('|', AsciiPanel.yellow, "staff");
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newLongBow(int depth){
        Item item = new Item(')', AsciiPanel.yellow, "bow");
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(8);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newCrossBow(int depth){
        Item item = new Item(')', AsciiPanel.green, "Crossbow");
        item.modifyAttackValue(3);
        item.modifyRangedAttackValue(10);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newLightArmor(int depth){
        Item item = new Item('[', AsciiPanel.green, "Tunic");
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newMediumArmor(int depth){
        Item item = new Item('[', AsciiPanel.white, "Chainmail");
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newHeavyArmor(int depth){
        Item item = new Item('[', AsciiPanel.brightWhite, "Platemail");
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item randomWeapon(int depth){
        switch ((int)(Math.random() * 4)){
            case 0: return newDagger(depth);
            case 1: return newSword(depth);
            case 2: return newLongBow(depth);
            default: return newStaff(depth);
        }
    }

    public Item randomArmor(int depth){
        switch ((int)(Math.random() * 3)){
            case 0: return newLightArmor(depth);
            case 1: return newMediumArmor(depth);
            default: return newHeavyArmor(depth);
        }
    }
    /*One advantage of having all our items be the same class but have different values is that an item can be more than one thing,
    e.g. you could make an edible weapon and the player would be able to eat or wield it with no extra code or you could have
    have a weapon that increases attack and defense.
    public Item newEdibleWeapon(int depth){
        Item item = new Item(')', AsciiPanel.yellow, "baguette");
        item.modifyAttackValue(3);
        item.modifyFoodValue(50);
        world.addAtEmptyLocation(item, depth);
        return item;
    }
    */

}
