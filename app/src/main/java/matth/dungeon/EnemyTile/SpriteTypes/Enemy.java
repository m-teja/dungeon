package matth.dungeon.EnemyTile.SpriteTypes;


import android.app.Activity;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import matth.dungeon.EnemyTile.EnemyEventActivity;
import matth.dungeon.R;
import matth.dungeon.Utility.EnemyUtility;
import matth.dungeon.Utility.MainUtility;

public abstract class Enemy implements EnemyBehaviour {

    private final int ANIMATION_DELAY = 15;
    private final int CHECK_DELAY = 20;
    private final int CHECK_PLAYER_DELAY = 15;

    MainUtility mainUtility;
    EnemyUtility enemyUtility;
    float health;
    float damage;
    String spriteName;
    boolean terminated;

    private ImageView sprite;
    private Handler check = new Handler();
    private Handler moveSprite = new Handler();
    private Handler updatePlayerPosition = new Handler();
    private Handler updateDestination = new Handler();

    private float playerX;
    private float playerY;
    float destinationX;
    private float destinationY;
    int velocity;
    float velocityX;
    float velocityY;
    int destinationUpdateDelay;

    private boolean boss;
    private ImageView healthBar;
    private ImageView maxHealthBar;


    public Enemy( MainUtility mainUtility, EnemyUtility enemyUtility) {
        this.mainUtility = mainUtility;
        this.enemyUtility = enemyUtility;
        this.terminated = false;
        initCheck();
        setUpdateDestinationDelay();
        setHealth();
        setSpriteName();
        setDamage();
        setVelocity();
    }

    private Runnable runCheck = new Runnable() {
        @Override
        public void run() {

            if (MainUtility.isActive()) {
                check.postDelayed(runCheck, CHECK_DELAY);
            }
            else {
                terminated = true;

                check.removeCallbacksAndMessages(null);
            }

        }
    };

    Runnable move = new Runnable() {
        @Override
        public void run() {

            movePattern();
            if (enemyUtility.checkPlayerOverlap(getSprite())) {
                effect();
            }

            if (!terminated) {
                moveSprite.postDelayed(move, ANIMATION_DELAY);
            }
        }
    };

    Runnable runUpdatePlayerPosition = new Runnable() {
        @Override
        public void run() {
            playerX = enemyUtility.getPlayerSprite().getX();
            playerY = enemyUtility.getPlayerSprite().getY();

            if (!terminated) {
                updatePlayerPosition.postDelayed(runUpdatePlayerPosition, CHECK_PLAYER_DELAY);
            }
        }
    };

    Runnable runUpdateDestination = new Runnable() {
        @Override
        public void run() {
            updateDestination();

            if (!terminated) {
                updateDestination.postDelayed(runUpdateDestination, destinationUpdateDelay);
            }
        }
    };

    void stopMoving() {
        moveSprite.removeCallbacksAndMessages(null);
    }

    void startMoving() {
        move.run();
    }

    final void deleteAll() {
        delete();
        terminated = true;
        moveSprite.removeCallbacksAndMessages(null);
        updatePlayerPosition.removeCallbacksAndMessages(null);
        updateDestination.removeCallbacksAndMessages(null);
        deleteImage();
        enemyUtility.checkDone();

    }

    public abstract void delete();

    private void deleteImage() {
        ConstraintLayout cl = ((Activity)mainUtility.getCon()).findViewById(R.id.enemyLay);
        cl.removeView(getSprite());

        if (boss) {
            cl.removeView(healthBar);
            cl.removeView(maxHealthBar);
        }
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void spawnSprite(float x, float y, Integer width, Integer height) {

        sprite = mainUtility.addImage(EnemyEventActivity.LAYOUT_NAME, spriteName, x, y);

        if (width != null) {
            sprite.setScaleType(ImageView.ScaleType.CENTER_CROP);
            sprite.getLayoutParams().width = width;
        }

        if (height != null) {
            sprite.setScaleType(ImageView.ScaleType.CENTER_CROP);
            sprite.getLayoutParams().height = height;
        }
    }

    public void initCheck() {
        Handler start = new Handler();
        start.postDelayed(new Runnable() {
            @Override
            public void run() {
                runCheck.run();
            }
        }, 500);
    }

    private void updateDestination() {
        destinationX = playerX;
        destinationY = playerY;

        calcVelocity();
    }

    void calcVelocity() {

        float differenceX = destinationX - getX();
        float differenceY = destinationY - getY();

        if (differenceX != 0 && differenceY != 0) {
            float differenceXY = (float)Math.sqrt(Math.pow(differenceX, 2) + Math.pow(differenceY, 2));

            float cosAngle = (float)Math.acos(differenceY/differenceXY);
            float sinAngle = (float)Math.asin(differenceX/differenceXY);

            velocityY = (float)(Math.cos(cosAngle) * velocity);
            velocityX = (float)(Math.sin(sinAngle) * velocity);
        }
        else if (differenceX == 0) {
            velocityX = 0;
            if (destinationY > getY()) {
                velocityY = velocity;
            }
            else {
                velocityY = -velocity;
            }
        }
        else {
            velocityY = 0;
            if (destinationX > getX()) {
                velocityX = velocity;
            }
            else {
                velocityX = -velocity;
            }
        }
    }

    void initBossHealth() {
        boss = true;
        maxHealthBar = mainUtility.addImage(EnemyEventActivity.LAYOUT_NAME, "max_health", 100, 100, mainUtility.getScreenWidth() - 200, 20);
        healthBar = mainUtility.addImage(EnemyEventActivity.LAYOUT_NAME, "health_remaining", 100, 100, mainUtility.getScreenWidth() - 200, 20);

    }

    private void changeBossHealth(float startingHealth) {

        healthBar.getLayoutParams().width = (int)(maxHealthBar.getLayoutParams().width * (health / startingHealth));
    }

    public abstract void init();
    public abstract void effect();
    public abstract void movePattern();
    public abstract void setHealth();
    public abstract void setSpriteName();
    public abstract void setVelocity();
    public abstract void setDamage();

    public boolean notTerminated() {
        return !terminated;
    }

    public float getX() {
        return sprite.getX();
    }

    public void setX(float x) {
        sprite.setX(x);
    }

    public float getY() {
        return sprite.getY();
    }

    public void setY(float y) {
        sprite.setY(y);
    }

    public void takeDamage(float x) {
        health -= x;

        if (health <= 0) {
            deleteAll();
        }
    }

    void takeDamage(float x, float startingHealth) {
        health -= x;

        if (health <= 0) {
            deleteAll();
        }

        if (boss) {
            changeBossHealth(startingHealth);
        }
    }


}
