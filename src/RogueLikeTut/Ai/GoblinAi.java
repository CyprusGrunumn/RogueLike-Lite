package RogueLikeTut.Ai;

import RogueLikeTut.*;

import java.util.List;

/**
 * Created by Will on 7/12/2017.
 */
public class GoblinAi extends CreatureAi {
    private Creature player;

    public GoblinAi(Creature creature, Creature player){
        super(creature);
        this.player = player;
    }

    public void onUpdate(){
        if(creature.canSee(player.x, player.y, player.z))
            hunt(player);
        else
            wander();
    }

    public void hunt(Creature target){
        List<Point> points  = new Path(creature, target.x, target.y).points();

        int mx = points.get(0).x - creature.x;
        int my = points.get(0).y - creature.y;

        creature.moveBy(mx, my, 0);
    }

}
