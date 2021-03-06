package matth.dungeon.EnemyTile.SpriteTypes;

public interface EnemyBehaviour {

    void init();
    void delete();
    void initCheck();
    void effect();
    void movePattern();
    void setUpdateDestinationDelay();
    void setHealth();
    void setSpriteName();
    void setVelocity();
}
