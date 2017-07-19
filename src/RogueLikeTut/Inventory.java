package RogueLikeTut;

/**
 * Created by William Rock Hendry on 5/7/2017.
 */

//In order to make items able to place in the same tile like a stack, a list is required, methods would need updating.
// and searching through the pile would need a whole new screen.
public class Inventory {
    private Item[] items;
    public Item[] getItems() { return items; }
    public Item get(int i) { return items[i]; }

    public Inventory(int max){
        items = new Item[max];
    }

    public void add(Item item){
        for( int i = 0; i < items.length; i++){
            if(items[i] == null){
                items[i] = item;
                break;
            }
        }
    }

    public void remove(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == item){
                items[i] = null;
                return;
            }
        }
    }

    public boolean isFull(){
        int size = 0;
        for(int i = 0; i < items.length; i++){
            if(items[i] != null)
                size++;
        }
        return size ==items.length;
    }
}
