package RogueLikeTut.screens;

import RogueLikeTut.Creature;
import RogueLikeTut.Item;

/**
 * Created by Will on 6/29/2017.
 */
public class DropScreen extends InventoryBasedScreen {

    public DropScreen(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "drop";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
}
