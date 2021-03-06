package matth.dungeon.Utility;

import android.graphics.Rect;
import android.widget.ImageView;

import java.util.ArrayList;

import matth.dungeon.EnemyTile.EnemyEventActivity;
import matth.dungeon.EnemyTile.SpriteTypes.Enemy;
import matth.dungeon.EnemyTile.SpriteTypes.PlayerSprite;

public class EnemyUtility {

    private PlayerSprite playerSprite;
    private EnemyEventActivity enemyEventActivity;
    private ArrayList<Enemy> enemies;

    public EnemyUtility(PlayerSprite playerSprite, EnemyEventActivity enemyEventActivity) {
        this.playerSprite = playerSprite;
        this.enemyEventActivity = enemyEventActivity;
    }

    public static void moveImage(ImageView image, float x, float y) {
        image.setX(x);
        image.setY(y);
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

    public boolean checkPlayerOverlap(ImageView image) {
        Rect playerRect = new Rect();
        Rect imageRect = new Rect();

        playerSprite.getPlayerImage().getHitRect(playerRect);
        image.getHitRect(imageRect);

        return playerRect.intersect(imageRect);
    }

    public void checkDone() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).notTerminated()) {
                return;
            }
        }

        enemyEventActivity.exitWin();
    }

    public void addEnemy(Enemy enemy, float x, float y, Integer width, Integer height) {
        enemies.add(enemy);
        spawnEnemy(enemy, x, y, width, height);
    }

    private void spawnEnemy(Enemy enemy, float x, float y, Integer width, Integer height) {
        enemy.spawnSprite(x, y, width, height);
        enemy.init();
    }

    public void spawnAllEnemies(int screenWidth) {
        int distance = (int)((float) screenWidth/(enemies.size() + 1));
        for (int i = 0; i < enemies.size(); i++) {

            spawnEnemy(enemies.get(i), (i+1) * distance, 200, null, null);

        }
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}
