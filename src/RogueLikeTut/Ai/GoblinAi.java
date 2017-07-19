package RogueLikeTut.Ai;

import RogueLikeTut.Creature;
import RogueLikeTut.CreatureAi;
import RogueLikeTut.Tile;

/**
 * Created by Will on 7/12/2017.
 */
public class GoblinAi extends CreatureAi {
    public GoblinAi(Creature creature){
        super(creature);
    }

    public void onUpdate(){
        wander();
    }

}
