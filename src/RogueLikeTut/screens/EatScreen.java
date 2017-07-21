package RogueLikeTut.screens;

import RogueLikeTut.Creature;
import RogueLikeTut.Item;

/**
 * Created by Will on 7/12/2017.
 */
public class EatScreen extends InventoryBasedScreen {
    public EatScreen(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "eat";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.foodValue() != 0;
    }

    @Override
    protected AsciiScreen use(Item item) {
        player.eat(item);
        return null;
    }
}