package fr.ups.sim.superpianotiles;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import fr.ups.sim.superpianotiles.util.Tile;

public class TilesStartActivity extends Activity {
    TilesView tilesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles_start);
        //remplissage des positions possibles
        List<Tile> pos = new ArrayList<>();
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++)
                pos.add(new Tile(0,j,i));
        }
        Queue<Tile> vi = new ArrayDeque<>();
        vi.offer(new Tile(1, 4, 0));
        vi.offer(new Tile(2, 2, 3));
        vi.offer(new Tile(3, 0, 3));
        pos.removeAll(vi);
        //ICI - Commentez le code
        tilesView = (TilesView) findViewById(R.id.view);
        tilesView.setTilesVisibles(vi);
        tilesView.setTilesInvisibles(pos);
        //ICI - Commentez le code
        tilesView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        return onTouchEventHandler(event);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tiles_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // ICI - A compléter pour déclencher l'ouverture de l'écran de paramétrage
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * ICI - Commentez le code
     */
    private boolean onTouchEventHandler (MotionEvent evt){
        //Log.i("TilesView", "Touch event handled");
        boolean result=tilesView.isPremier(evt.getX(),evt.getY());
        if(result) {
            tilesView.modifierV1();
        }
        return true;
    }
}
