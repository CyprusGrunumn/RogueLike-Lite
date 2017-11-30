/*
I guess you could also make a Location interface.
Then Point, Creature, and Item could implement it.
That way an item's location could be a point in the world, a creature that's carrying it (or it's point in the world),
or a container it's in.
That would also be useful because an item would have a reference to wherever it is and whoever is carrying it.

I guess you could have the owner update their location and add another flag indicating if the item is on the floor, in a
container, or being carried.
 */
package RogueLikeTut;

import java.awt.Color;

public class Item {

    private char glyph;

    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }

    private int foodValue;
    public int foodValue() { return foodValue; }
    public void modifyFoodValue(int amount) { foodValue += amount; }

    private int attackValue;
    public int attackValue() { return  attackValue; }
    public void modifyAttackValue(int amount) { attackValue += amount; }

    private int thrownAttackValue = 1;
    public int thrownAttackValue() { return thrownAttackValue; }
    public void modifyThrownAttackValue(int amount) { thrownAttackValue += amount; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }
    public void modifyDefenseValue(int amount) { defenseValue += amount; }

    public Item(char glyph, Color color, String name){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }

    public String details() {
        String details = "";

        if (attackValue != 0)
            details += "    attack:" + attackValue;

        if(defenseValue != 0)
            details += "    defense:" + defenseValue;

        if (foodValue != 0)
            details += "    food:" + foodValue;

        if (thrownAttackValue != 0)
            details += "    Thrown damage:" + thrownAttackValue;

        return details;
    }
}