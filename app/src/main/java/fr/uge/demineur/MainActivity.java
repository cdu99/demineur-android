package fr.uge.demineur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import static fr.uge.demineur.DemineurParams.GRID_SIZE;
import static fr.uge.demineur.DemineurParams.NUMBER_OF_BOMBS;

public class MainActivity extends AppCompatActivity implements OnCellClickListener {
    private DemineurAdapter adapter;
    private Demineur demineur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gérer la rotation
        if (savedInstanceState != null) {
            demineur = (Demineur) savedInstanceState.getSerializable("demineur");
        } else {
            demineur = new Demineur(GRID_SIZE, NUMBER_OF_BOMBS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton flagButton = findViewById(R.id.flag);

        flagButton.setOnClickListener(v -> {
            demineur.flagClick();
            if (demineur.flagIndicator()){
                flagButton.setBackgroundColor(Color.RED);
            } else {
                flagButton.setBackgroundColor(Color.GRAY);
            }
        });

        adapter = new DemineurAdapter(demineur.getGrid().getCells(), this);
        RecyclerView recyclerView = findViewById(R.id.grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, GRID_SIZE));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle b) {
        super.onSaveInstanceState(b);
        b.putSerializable("demineur", demineur);
    }

    @Override
    public void cellClick(Cell cell) {
        demineur.openCell(cell);
        if (demineur.isGameOver()) {
            // LOOSE
            demineur.getGrid().openAllBombs();
            Toast.makeText(this, "J'te déteste t'es trop nul ٩(╬ʘ益ʘ╬)۶", Toast.LENGTH_LONG).show();
        } else if (demineur.isGameWon()) {
            // WIN
            demineur.getGrid().openAllBombs();
            Toast.makeText(this, "Bien joué bg t'es trop fort je t'aime (´ ε ` )♡", Toast.LENGTH_LONG).show();
        }
        adapter.setCells(demineur.getGrid().getCells());
    }
}