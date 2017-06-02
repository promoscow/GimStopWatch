package ru.xpendence.development.gimstopwatch.util;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.foodstuffs.FoodStuffsData;
import ru.xpendence.development.gimstopwatch.foodstuffs.GoodInDayRation;

/**
 * Created by promoscow on 02.06.17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FoodViewHolder> {

    ArrayList<GoodInDayRation> list;

    public RVAdapter() {
        list = FoodStuffsData.dailyGoods;
    }
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.food_details_card_layout, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, int position) {
        holder.tableLayout.setVisibility(View.GONE);
        final boolean[] isVisible = {false};
        holder.header.setText(list.get(position).getName());
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible[0]) {
                    isVisible[0] = true;
                    holder.tableLayout.setVisibility(View.VISIBLE);
                } else {
                    isVisible[0] = false;
                    holder.tableLayout.setVisibility(View.GONE);
                }
            }
        });

        holder.amount.setText(String.valueOf(list.get(position).getAmount()));

        NumberFormat numberFormat = new DecimalFormat("0.00");
        numberFormat.setRoundingMode(RoundingMode.DOWN);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.US);
        String t = dateFormat.format(list.get(position).getDate());
        holder.time.setText(t);

        holder.calories.setText(String.valueOf(list.get(position).getCalories()));
        holder.proteins.setText(String.valueOf(numberFormat.format(list.get(position).getProteins())));
        holder.fats.setText(String.valueOf(numberFormat.format(list.get(position).getFats())));
        holder.carbohydrates.setText(String.valueOf(numberFormat.format(list.get(position).getCarbohydrates())));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TableLayout tableLayout;
        TextView header;
        TextView amount;
        TextView time;
        TextView calories;
        TextView proteins;
        TextView fats;
        TextView carbohydrates;

        public FoodViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tableLayout = (TableLayout) itemView.findViewById(R.id.food_card_table);
            header = (TextView) itemView.findViewById(R.id.food_card_header);
            header.setTypeface(CommonSettings.getRobotoCondLight());
            amount = (TextView) itemView.findViewById(R.id.food_card_amount);
            amount.setTypeface(CommonSettings.getRobotoCondLight());
            time = (TextView) itemView.findViewById(R.id.time);
            time.setTypeface(CommonSettings.getRobotoCondLight());
            calories = (TextView) itemView.findViewById(R.id.food_card_calories_value);
            calories.setTypeface(CommonSettings.getRobotoCondLight());
            proteins = (TextView) itemView.findViewById(R.id.food_card_proteins_value);
            proteins.setTypeface(CommonSettings.getRobotoCondLight());
            fats = (TextView) itemView.findViewById(R.id.food_card_fats_value);
            fats.setTypeface(CommonSettings.getRobotoCondLight());
            carbohydrates = (TextView) itemView.findViewById(R.id.food_card_carbohydrates_value);
            carbohydrates.setTypeface(CommonSettings.getRobotoCondLight());
        }
    }
}
