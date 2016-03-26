package fr.ups.sim.superpianotiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import fr.ups.sim.superpianotiles.util.Tile;

public class TilesStartActivity extends Activity {
    private TilesView tilesView;
    public static final int NIVEAU_FACILE = 0;
    public static final int NIVEAU_MOYEN = 1;
    public static final int MAX_ERROR = 3;
    public static final int NIVEAU_DIFFICILE = 2;
    private static final int MAX_TILES = 20;
    private int niveau;
    private CountDownTimer timerApparitionTile;
    private long delai = 2000;
    private TextView affscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles_start);
        affscore = (TextView) findViewById(R.id.score);
        //recuperation du niveau
        Intent intent = getIntent();
        niveau = intent.getIntExtra("niveau",0);
        //ICI - Commentez le code
        tilesView = (TilesView) findViewById(R.id.view);
        //initialisation des positions
        tilesView.initPositions();
        // on rajoute trois tiles a debut du jeu
        for(int i=0;i<3;i++)
            tilesView.ajouterTileAleatoire();
        /*Activation du listener quand on touche l'ecran
          les actions MotionEvent.ACTION_DOWN et ACTION_POINTER_DOWN sont traites
         */
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
        //creattion du timer si niveau Moyen
        if(niveau == NIVEAU_MOYEN){
            createNStartTimer();
        }
    }

    /**
     * creation du compte a rebours
     */
    private void createNStartTimer() {
        timerApparitionTile = new CountDownTimer(delai,delai) {
            @Override
            public void onTick(long millisUntilFinished) {
                return;
            }
            @Override
            public void onFinish() {
                handlerApparition();
            }
        }.start();
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
        int result=tilesView.isPremier(evt.getX(),evt.getY());
        if(result == 0) {
            tilesView.modifierV1(niveau);
            affscore.setText(" "+String.valueOf(tilesView.getScore()));
        } else if(result == -1){
            gameOver();
        }
        return true;
    }

    /**
     * methode qui stope le jeu
     */
    public void gameOver(){
        if(timerApparitionTile != null)
            timerApparitionTile.cancel();
        Intent intent = new Intent(TilesStartActivity.this,ResultActivity.class);
        intent.putExtra("score",tilesView.getScore());
        startActivity(intent);
        finish();
    }

    /**
     * handler du timer d'apparition des tuiles
     */
    private void handlerApparition(){
        if(tilesView.nbTiles() < 2){
            tilesView.ajouterTileV2(3);
        } else {
            if(tilesView.nbTiles() <= 18)
                tilesView.ajouterTileV2(2);
            else
                tilesView.ajouterTileV2(1);
        }
        if(tilesView.nbTiles() >= MAX_TILES) {
            gameOver();
        }
        else {
            delai = Math.round(2031.045025 * Math.exp(-2.618556015*Math.pow(10,-2) * tilesView.getScore())) ;
            timerApparitionTile.cancel();
            createNStartTimer();
        }
    }
}
