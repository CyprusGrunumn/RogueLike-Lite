package RogueLikeTut.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import RogueLikeTut.*;
import RogueLikeTut.spritePanel.Sprite;
import RogueLikeTut.spritePanel.SpriteLibrary;
import RogueLikeTut.spritePanel.SpritePanel;
import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {
    private World world;
    private Creature player;
    private List<String> messages;
    private FieldOfView fov;
    private Screen subscreen;

    //setup
    public PlayScreen() {
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
    }

    private void createCreatures(StuffFactory factory) {
        player = factory.newPlayer(messages, fov);

        for (int z = 0; z < world.depth(); z++) {
            for (int i = 0; i < 4; i++) {
                factory.newFungus(z);
            }
            for (int i = 0; i < 10; i++) {
                factory.newBat(z);
            }
            for (int i = 0; i < 3; i++){
                factory.newZombie(z, player);
            }
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
                factory.newDagger(z);
                factory.newSword(z);
                factory.newStaff(z);
                factory.newLongBow(z);
                factory.newCrossBow(z);
                factory.newLightArmor(z);
                factory.newMediumArmor(z);
                factory.newHeavyArmor(z);
                factory.randomArmor(z);
                factory.randomWeapon(z);
            }
        }
        factory.newVictoryItem(world.depth() - 1);
    }

    public int getScrollX(int screenWidth) {
        return Math.max(-5, Math.min(player.x - screenWidth / 2, world.width() + 5 - screenWidth));
    }

    public int getScrollY(int screenHeight) {
        return Math.max(-5, Math.min(player.y - screenHeight / 2, world.height() + 5 - screenHeight));
    }

    @Override
    public void displayOutput(SpritePanel terminal) {
        int left = getScrollX(terminal.getWidthInCharacters());
        int top = getScrollY(terminal.getHeightInCharacters() - 1);
        Graphics g = terminal.getOverlayGraphics();

        // Clear overlay
        g.clearRect(0, 0, terminal.getWidth(), terminal.getHeight());

        displayTiles(terminal, left, top);
        displayMessages(terminal, messages);

        String stats = String.format(" %3d/%3d hp %8s Attack %4s Defense %4s Level %2s", player.hp(), player.maxHp(), hunger(), player.attackValue(), player.defenseValue(), player.level());
        terminal.write(stats, 1, terminal.getHeightInCharacters() - 1);

        if (subscreen != null) {
            subscreen.displayOutput(terminal);
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
        int top = terminal.getHeightInCharacters() - messages.size() - 1;
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

    private void displayTiles(SpritePanel terminal, int left, int top) {
        fov.update(player.x, player.y, player.z, player.visionRadius());

        int visibleWidth = Math.min(terminal.getWidthInCharacters(), world.width() - left);
        int visibleHeight = Math.min(terminal.getHeightInCharacters() - 1, world.height() - top);

        int offsetX = Math.max(
                Math.abs(Math.min(left, 0)),
                Math.max(0, terminal.getWidthInCharacters() - world.width()) / 2);
        int offsetY = Math.max(
                Math.abs(Math.min(top, 0)),
                Math.max(0, terminal.getHeightInCharacters() - world.height()) / 2);

        for (int x = offsetX; x < visibleWidth; x++) {
            for (int y = offsetY; y < visibleHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy, player.z)) {
                    Tile tile = world.tile(wx, wy, player.z);
                    char glyph = world.glyph(wx, wy, player.z);
                    Color color = world.color(wx, wy, player.z);
                    Sprite sprite = terminal.getSpriteLibrary().getSprite(glyph, color, null);

                    if (sprite != null && terminal.spritesEnabled() && glyph != tile.glyph()) {
                        Graphics g = terminal.getOverlayGraphics();

                        int canvasX = x * terminal.getCharWidth();
                        int canvasY = y * terminal.getCharHeight();

                        int spriteX = sprite.getX() * terminal.getCharWidth();
                        int spriteY = sprite.getY() * terminal.getCharHeight();

                        glyph = tile.glyph();
                        color = tile.color();

                        terminal.write(glyph, x, y, color, terminal.spritesEnabled());

                        g.drawImage(sprite.getSheet(),
                                canvasX, canvasY,
                                canvasX + terminal.getCharWidth(), canvasY + terminal.getCharHeight(),
                                spriteX, spriteY,
                                spriteX + terminal.getCharWidth(), spriteY + terminal.getCharHeight(),
                                null);


                    } else {
                        terminal.write(glyph, x, y, color, terminal.spritesEnabled());
                    }
                }
                else{
                    terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray, terminal.spritesEnabled());
                }
            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int level = player.level();

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
                case KeyEvent.VK_W: subscreen = new EquipScreen(player); break;
                case KeyEvent.VK_X: subscreen = new ExamineScreen(player); break;
                case KeyEvent.VK_SEMICOLON: subscreen = new LookScreen(this,
                        player, "Looking",
                        player.x,
                        player.y); break;
                case KeyEvent.VK_T: subscreen = new ThrowScreen(this,
                        player,
                        player.x,
                        player.y); break;
                case KeyEvent.VK_F:
                    if (player.weapon() == null || player.weapon().rangedAttackValue() == 0)
                        player.notify("You don't have a ranged weapon equiped.");
                    else
                        subscreen = new FireWeaponScreen(this,
                                player,
                                player.x,
                                player.y); break;
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
                case '?': subscreen = new HelpScreen(); break;
            }
        }

        if (player.level() > level)
            subscreen = new LevelUpScreen(player, player.level() - level);

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

    private Screen userExits(){
        for (Item item : player.inventory().getItems()){
            if (item != null && item.name().equals("teddy bear"))
                return new WinScreen();
        }
        return new LoseScreen();
    }
}