package RogueLikeTut.screens;

import RogueLikeTut.Creature;
import RogueLikeTut.Item;

/**
 * Created by Cyprus on 11/30/2017.
 */
public class ThrowScreen extends InventoryBasedScreen {
    protected PlayScreen playScreen;
    private int sx;
    private int sy;

    public ThrowScreen(PlayScreen playScreen, Creature player, int sx, int sy){
        super(player);
        this.playScreen = playScreen;
        this.sx = sx;
        this.sy = sy;
    }

    protected String getVerb() {
        return "throw";
    }

    protected boolean isAcceptable(Item item){
        return true;
    }

    protected Screen use(Item item){
        return new ThrowAtScreen(playScreen, player, sx, sy, item);
    }
}
