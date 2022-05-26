package com.astudio.routinepartner;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class SetCategoryAdapter extends RecyclerView.Adapter<SetCategoryAdapter.ViewHolder> {

    Context context;

    SetCategoryAdapter(Context c){
        this.context = c;
    }

    static ArrayList<CategoryInfo> CategoryItem = new ArrayList<CategoryInfo>(
            Arrays.asList(
                    //new CategoryInfo(SavedSettings.CategoryList.get(0), SavedSettings.ColorList.get(0), SavedSettings.CategoryStatList.get(0), SavedSettings.GoalType.get(0), SavedSettings.GoalList.get(0)),
                    //00new CategoryInfo(SavedSettings.CategoryList.get(1), SavedSettings.ColorList.get(1), SavedSettings.CategoryStatList.get(1), SavedSettings.GoalType.get(1), SavedSettings.GoalList.get(1))));
            ));

    Button CategoryButton;

    int DeleteNum = 0;
    int EditNum = 0;


    private OnItemClickListener mListener = null;
    private OnItemLongClickListener lListener = null;

    interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    interface OnItemLongClickListener{
        void onItemLongClick(View v, int pos);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener llistener){
        this.lListener = llistener;
    }



    public void addItem(CategoryInfo C){
        CategoryItem.add(C);
        notifyItemInserted(CategoryItem.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.set_catetory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetCategoryAdapter.ViewHolder holder, int position) {

        CategoryInfo cate = CategoryItem.get(position);
        Log.v("cate", ""+cate.getName()+"   "+position);
        holder.setItem(cate);
    }

    @Override
    public int getItemCount() {
        return CategoryItem.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemview){
            super(itemview);
            CategoryButton = itemview.findViewById(R.id.CategoryInfoBtn);


            CategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onItemClick(view,position);
                        }
                    }
                }
            });

            CategoryButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(lListener!=null){
                            lListener.onItemLongClick(view,position);
                        }
                    }
                    return true;
                }
            });
        }

        public void setItem(CategoryInfo Item){
            String styledText;
            if(Item.getGoalType() == 1){
                styledText = "<big>"
                        + Item.getName() + "</big>" + "<br />"
                        + "<small>" + SavedSettings.StatList.get(Item.getStat()-1) + " / 목표: " + Item.getGoal()+ "번" + "</small>";
            }else{
                styledText = "<big>"
                        + Item.getName() + "</big>" + "<br />"
                        + "<small>" + SavedSettings.StatList.get(Item.getStat()-1) + " / 목표: " + Item.getGoal()+"시간" + "</small>";
            }

            CategoryButton.setText(Html.fromHtml(styledText));
//            CategoryButton.setText(Item.getName()+" "+Item.getStat()+" "+Item.getColor()+" "+Item.getGoalType()+" "+Item.getGoal());
            Log.v("t", ""+Item.getName());
            CategoryButton.setBackgroundTintList(ColorStateList.valueOf(Item.getColor().intValue()));
//            CategoryButton.setBackgroundColor(Item.getColor().intValue());
        }
    }
    public void delItem(int position) {
        CategoryItem.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, CategoryItem.size());
    }

    public void editItem(int position, CategoryInfo C){
        CategoryItem.set(position, C);
        notifyItemChanged(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
