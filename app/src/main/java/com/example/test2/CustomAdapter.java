package com.example.test2;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList stock_id, produit_title, stock_unity, stock_quantity;

    CustomAdapter(Activity activity, Context context, ArrayList stock_id, ArrayList produit_title, ArrayList stock_unity, ArrayList stock_quantity){
        this.activity = activity;
        this.context = context;
        this.stock_id=stock_id;
        this.produit_title=produit_title;
        this.stock_unity=stock_unity;
        this.stock_quantity=stock_quantity;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.book_id_txt.setText(String.valueOf(stock_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(produit_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(stock_unity.get(position)));
        holder.book_pages_txt.setText(String.valueOf(stock_quantity.get(position)));

        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(stock_id.get(position)));
                intent.putExtra("produit", String.valueOf(produit_title.get(position)));
                intent.putExtra("unity", String.valueOf(stock_unity.get(position)));
                intent.putExtra("quantity", String.valueOf(stock_quantity.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return stock_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.stock_id_txt);
            book_title_txt = itemView.findViewById(R.id.stock_title_txt);
            book_author_txt = itemView.findViewById(R.id.stock_unity_txt);
            book_pages_txt = itemView.findViewById(R.id.stockquantity_txt);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}

