package RogueLikeTut.screens;

import RogueLikeTut.Creature;
import RogueLikeTut.Item;
import RogueLikeTut.Tile;

/**
 * Created by Cyprus on 11/29/2017.
 */
public class LookScreen extends TargetBasedScreen {

    public LookScreen(PlayScreen playScreen, Creature player, String caption, int sx, int sy) {
        super(playScreen, player, caption, sx, sy);
    }

    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
        Creature creature = player.creature(x, y, player.z);
        if (creature != null) {
            caption = creature.glyph() + " " + creature.name() + creature.details();
            return;
        }

        Item item = player.item(x, y, player.z);
        if (item != null) {
            caption = item.glyph() + " " + item.name() + item.details();
            return;
        }

        Tile tile = player.tile(x, y, player.z);
        caption = tile.glyph() + " " + tile.details();
    }
}
