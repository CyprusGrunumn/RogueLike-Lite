package RogueLikeTut.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import RogueLikeTut.*;
import asciiPanel.AsciiPanel;

public class PlayScreen implements AsciiScreen {
    public static final int TILE_SIZE = 16;

    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private FieldOfView fov;
    private Screen subscreen;
    private SpriteLibrary sprites;

    //setup
    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 23;
        messages = new ArrayList<String>();
        createWorld();
        fov = new FieldOfView(world);

        StuffFactory factory = new StuffFactory(world);
        createCreatures(factory);
        createItems(factory);
    }

    private void createWorld() {
        world = new WorldBuilder(90, 32, 5)
                .makeCaves()
                .build();
        sprites = new SpriteLibrary();
    }

    private void createCreatures(StuffFactory factory) {
        player = factory.newPlayer(messages, fov);

        sprites.addSprite(StuffFactory.Glyph.FUNGUS,
                "DawnLike/Characters/Plant0.png",
                0, 112, TILE_SIZE, TILE_SIZE);

        for (int z = 0; z < world.depth(); z++) {
            for (int i = 0; i < 4; i++) {
                factory.newFungus(z);
            }
            for (int i = 0; i < 10; i++) {
                factory.newBat(z);
            }
           /* for (int i = 0; i < 20; i++){
                factory.newGoblin(z);
            } */
        }
    }

    private void createItems(StuffFactory factory) {
        for (int z = 0; z < world.depth(); z++) {
            for (int i = 0; i < world.width() * world.height() / 20; i++) {
                factory.newRock(z);
            }
            for (int i = 0; i < 3; i++){
                factory.newBread(z);
                factory.newFruit(z);
            }
        }
        factory.newVictoryItem(world.depth() - 1);
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }

    @Override
    public void displayOutput(Renderer renderer) {
        int left = getScrollX();
        int top = getScrollY();
        AsciiPanel terminal = renderer.terminal();
        Graphics g = renderer.canvas().graphics();

        // Clear canvas
        g.setColor(Color.black);
        g.fillRect(0, 0, renderer.canvas().getWidth(), renderer.canvas().getHeight());

        displayTiles(renderer, left, top);
        displayMessages(terminal, messages);

        String stats = String.format(" %3d/%3d hp %8s", player.hp(), player.maxHp(), hunger());
        renderer.terminal().write(stats, 1, 23);

        if (subscreen != null) {
            subscreen.displayOutput(renderer);
            terminal.setVisible(true);
        } else {
            terminal.setVisible(false);
        }
    }

    private String hunger(){
        if (player.food() < 100)
            return "Starving";
        else if (player.food() < 300)
            return "Hungry";
        else if (player.food() > player.maxFood() * 0.95)
            return "Stuffed";
        else
            return "Full";
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

    private void displayTiles(Renderer renderer, int left, int top) {
        AsciiPanel terminal = renderer.terminal();
        Graphics g = renderer.canvas().graphics();

        fov.update(player.x, player.y, player.z, player.visionRadius());

        SpriteLibrary.Sprite fungus = sprites.getSprite(StuffFactory.Glyph.FUNGUS);

        int destX = 120;
        int destY = 150;

        g.drawImage(fungus.sheet(),
                destX, destY,
                destX+TILE_SIZE, destY+TILE_SIZE,
                fungus.x(), fungus.h(),
                fungus.x()+TILE_SIZE, fungus.h()+TILE_SIZE,
                null);

        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy, player.z))
                    terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
                else
                    terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
            }
        }
    }

    @Override
    public AsciiScreen respondToUserInput(KeyEvent key) {
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            switch (key.getKeyCode()){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_H: player.moveBy(-1, 0, 0); break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_L: player.moveBy( 1, 0, 0); break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_K: player.moveBy( 0,-1, 0); break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_J: player.moveBy( 0, 1, 0); break;
                case KeyEvent.VK_Y: player.moveBy(-1,-1, 0); break;
                case KeyEvent.VK_U: player.moveBy( 1,-1, 0); break;
                case KeyEvent.VK_B: player.moveBy(-1, 1, 0); break;
                case KeyEvent.VK_N: player.moveBy( 1, 1, 0); break;
                case KeyEvent.VK_D: subscreen = new DropScreen(player); break;
                case KeyEvent.VK_E: subscreen = new EatScreen(player); break;
            }

            switch (key.getKeyChar()){
                case 'g':
                case ',': player.pickup(); break;
                case '<':
                    if (userIsTryingToExit())
                        return userExits();
                    else
                        player.moveBy( 0, 0, -1); break;
                case '>': player.moveBy( 0, 0, 1); break;
            }
        }

        if (subscreen == null){
            world.update();
        }

        if (player.hp() < 1)
            return new LoseScreen();

        return this;
    }

    private boolean userIsTryingToExit(){
        return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
    }

    private AsciiScreen userExits(){
        for (Item item : player.inventory().getItems()){
            if (item != null && item.name().equals("teddy bear"))
                return new WinScreen();
        }
        return new LoseScreen();
    }
}