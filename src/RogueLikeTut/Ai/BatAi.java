package RogueLikeTut.Ai;


import RogueLikeTut.Creature;
import RogueLikeTut.CreatureAi;

public class BatAi  extends CreatureAi {
    public BatAi(Creature creature){
        super(creature);
    }

    public void onUpdate(){
        wander();
        wander();
}
}
