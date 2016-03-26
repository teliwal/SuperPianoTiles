package fr.ups.sim.superpianotiles;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        TextView affiche = (TextView) findViewById(R.id.affichageScore);
        affiche.setText("Votre score :" + score);
        affiche.append("\nFranchement c'est pas terrible");

        Button rejouer = (Button) findViewById(R.id.bouton_rejouer);
        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ResultActivity.this,MenuActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

}
