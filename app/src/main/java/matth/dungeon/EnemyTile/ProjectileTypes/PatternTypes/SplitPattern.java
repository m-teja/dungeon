package matth.dungeon.EnemyTile.ProjectileTypes.PatternTypes;

import matth.dungeon.Utility.MainUtility;
import matth.dungeon.Utility.PlayerUtility;

public class SplitPattern extends Pattern {

    private MainUtility mainUtility;
    private PlayerUtility playerUtility;

    public SplitPattern(MainUtility mainUtility, PlayerUtility playerUtility) {
        super(mainUtility);
        this.mainUtility = mainUtility;
        this.playerUtility = playerUtility;
    }

    @Override
    public void init() {
        spawn.run();
    }

    @Override
    public void spawnPattern() {

    }

    @Override
    public void getSpawnDelay() {
        super.spawnDelay = 1000;
    }

    @Override
    public void delete() {

    }
}
