package fr.ups.sim.superpianotiles;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {

    private Button boutonFacile;
    private Button boutonMoyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //listener sur les boutons
        boutonFacile = (Button) findViewById(R.id.niveau_facile);
        boutonFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TilesStartActivity.class);
                intent.putExtra("niveau", TilesStartActivity.NIVEAU_FACILE);
                startActivity(intent);
            }
        });
        boutonMoyen = (Button) findViewById(R.id.niveau_moyen);
        boutonMoyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,TilesStartActivity.class);
                intent.putExtra("niveau", TilesStartActivity.NIVEAU_MOYEN);
                startActivity(intent);
            }
        });
        //affichage du score s'il vient de perdre
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", -1);
        if(score != -1) {
            TextView affiche = (TextView) findViewById(R.id.affichageScore);
            affiche.setText("Votre score :" + score + "\nEssayez de l'ameliorer");
        }
    }

}
