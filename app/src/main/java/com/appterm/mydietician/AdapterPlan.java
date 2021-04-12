package com.appterm.mydietician;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterPlan extends RecyclerView.Adapter<AdapterPlan.PlanViewHolder> {

    Context context;


    ArrayList<Food> breakfastRelatedPlan;
    ArrayList<Food> dinnerRelatedPlan;
    ArrayList<Food> lunchRelatedPlan;

    public AdapterPlan(Context context, ArrayList<Food> breakfastRelatedPlan, ArrayList<Food> dinnerRelatedPlan, ArrayList<Food> lunchRelatedPlan) {
        this.context = context;
        this.breakfastRelatedPlan = breakfastRelatedPlan;
        this.dinnerRelatedPlan = dinnerRelatedPlan;
        this.lunchRelatedPlan = lunchRelatedPlan;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan,parent,false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        SimpleDateFormat number = new SimpleDateFormat("dd");
//        String dayOfTheWeek = sdf.format(breakfastRelatedPlan.get(position).getDate());
//        String numberDayOfTheWeek = number.format(breakfastRelatedPlan.get(position).getDate());
      //


        String input_date= breakfastRelatedPlan.get(position).getDate();
        SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
        Date dt1= null;
        try {
            dt1 = format1.parse(input_date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE",new Locale("ar"));

            String dayOfTheWeek = (String)dateFormat.format(dt1); // Thursday
            String day          = (String) DateFormat.format("dd",  dt1); // 20
            holder.nameOfDay.setText(dayOfTheWeek);
            holder.numberOfDay.setText(day);
        } catch (Exception e) {
            e.printStackTrace();
        }



        holder.foods.setText("-"+breakfastRelatedPlan.get(position).getFoodName()+"\n-"+lunchRelatedPlan.get(position).getFoodName()+"\n-"+dinnerRelatedPlan.get(position).getFoodName());

    }

    @Override
    public int getItemCount() {
        return breakfastRelatedPlan.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfDay,numberOfDay,foods;
        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfDay = itemView.findViewById(R.id.nameOfDay);
            numberOfDay = itemView.findViewById(R.id.numberOfDay);
            foods = itemView.findViewById(R.id.foods);
        }
    }
}
