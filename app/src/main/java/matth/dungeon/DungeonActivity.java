package matth.dungeon;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.logging.Level;

public class DungeonActivity extends AppCompatActivity {

    ArrayList<ArrayList<LevelTile>> levelMap;
    Utility utility;

    int size = 5; //temp variable, will change for each level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon);

        initLevel();
        utility = new Utility(this);
    }

    public void toggleMap (View view) {

        if (findViewById(R.id.mapDisp).getVisibility() == View.GONE) {
            View map = findViewById(R.id.mapDisp);
            map.setVisibility(View.VISIBLE);

            map.getLayoutParams().height = (int)(utility.getScreenHeight() * 0.6);
            map.getLayoutParams().width = (int)(utility.getScreenWidth() * 0.8);
            map.setX((utility.getScreenWidth()/2) - map.getLayoutParams().width/2 );
            map.setY((utility.getScreenHeight()/2) - map.getLayoutParams().height/2);
        }
        else {
            findViewById(R.id.mapDisp).setVisibility(View.GONE);
        }



    }

    private void initLevel() {
        levelMap = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            levelMap.add(i, new ArrayList<LevelTile>());
            for (int j = 0; j < size; j++) {
                levelMap.get(i).add(j, genTile());
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Log.d("test", ((LevelTile)((ArrayList)levelMap.get(i)).get(j)).test());
            }
        }
    }

    private LevelTile genTile() {

        double rand = Math.random() * 100;
        LevelTile current = new LevelTile(-1);

        if (rand > 50) {
            //wall
            current.setType(1);
        }
        else {
            //open tile
            current.setType(0);
        }

        return current;
    }

}
