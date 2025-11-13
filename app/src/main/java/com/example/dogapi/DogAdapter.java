package com.example.dogapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> {

    private List<Dog> dogs;
    private Context context;
    private DogDatabase db;

    public DogAdapter(Context context, List<Dog> dogs) {
        this.context = context;
        this.dogs = dogs;
        this.db = DogDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dog dog = dogs.get(position);
        holder.tvDogName.setText(dog.getName());

        Glide.with(context).load(dog.getImageUrl()).into(holder.ivDog);

        holder.btnEdit.setOnClickListener(v -> {
            AddEditDialog.show(context, "Editar perro", dog.getName(), name -> {
                dog.setName(name);
                db.dogDao().update(dog);
                notifyDataSetChanged();
            });
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.dogDao().delete(dog);
            dogs.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDogName;
        ImageView ivDog;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDogName = itemView.findViewById(R.id.tvDogName);
            ivDog = itemView.findViewById(R.id.ivDog);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
