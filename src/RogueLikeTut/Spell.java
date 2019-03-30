package RogueLikeTut;


public class Spell {

    private String name;
    public String name() { return name; }

    private int manaCost;
    public int manaCost() { return manaCost; }

    private Effect effect;
    public Effect effect() { return new Effect(effect); }

    public boolean requiresTarget;
    public boolean requiresTarget() { return true; }

    public Spell(String name, int manaCost, Effect effect, boolean requiresTarget){
        this.name = name;
        this.manaCost = manaCost;
        this.effect = effect;
        this.requiresTarget = requiresTarget;
    }

}

/*
Notes From The Tutorial:

(days later....) I just realized a different, and almost certainly better,
way of handling effects for each spell. Instead of each spell having a reference
 to an Effect and using the Effect's copy constructor, the spell class should act
  as an Effect factory and have an abstract newEffect method. Each individual spell
   would subclass Spell and implement newEffect. I'll have to do that with my next rogue like.
 */