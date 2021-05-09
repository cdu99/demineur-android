package fr.uge.demineur;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DemineurAdapter extends RecyclerView.Adapter<DemineurAdapter.ViewHolder> {
    private Cell[][] cells;
    private final OnCellClickListener cellClickListener;

    public DemineurAdapter(Cell[][] cells, OnCellClickListener cellClickListener) {
        this.cells = cells;
        this.cellClickListener = cellClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cell);
        }

        private void update(Cell cell) {
            textView.setBackgroundColor(Color.GRAY);
            textView.setOnClickListener(view -> cellClickListener.cellClick(cell));

            if (cell.isOpen()) {
                int cellValue = cell.getValue();
                if (cellValue == Cell.BOMB) {
                    textView.setBackgroundResource(R.drawable.ic_bombe_malefique);
                } else if (cellValue == Cell.EMPTY) {
                    textView.setBackgroundColor(Color.WHITE);
                } else {
                    textView.setText("" + cellValue);
                    switch (cellValue) {
                        case 1:
                            textView.setTextColor(Color.BLUE);
                            break;
                        case 2:
                            textView.setTextColor(Color.GREEN);
                            break;
                        case 3:
                            textView.setTextColor(Color.RED);
                            break;
                        case 4:
                            int fourColor = Color.parseColor("#6A4BF3");
                            textView.setTextColor(fourColor);
                            break;
                        default:
                            int legendaryColor = Color.parseColor("#FFFF00");
                            textView.setTextColor(legendaryColor);
                            break;
                    }
                }
            } else if (cell.isFlag()) {
                textView.setBackgroundResource(R.drawable.ic_flag_icon);
            }
        }
    }

    @NonNull
    @Override
    public DemineurAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemineurAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int row = position % cells.length;
        int col = position / cells.length;
        holder.update(cells[row][col]);
    }

    @Override
    public int getItemCount() {
        return cells.length * cells.length;
    }

    // Scrolling
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }
}
