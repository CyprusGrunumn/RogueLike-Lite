package RogueLikeTut.Ai;

import RogueLikeTut.Creature;
import RogueLikeTut.CreatureAi;
import RogueLikeTut.Path;
import RogueLikeTut.Point;
import java.util.List;

public class ZombieAi extends CreatureAi{
    private Creature player;

    public ZombieAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

    public void onUpdate(){
        if (Math.random() < 0.2)
            return;

        if (creature.canSee(player.x, player.y, player.z))
            hunt(player);
        else
            wander();
    }

}
