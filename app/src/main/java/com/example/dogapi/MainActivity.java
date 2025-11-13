package com.example.dogapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvDogs;
    private Button btnAdd, btnSync;
    private DogAdapter adapter;
    private List<Dog> dogs = new ArrayList<>();
    private DogDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvDogs = findViewById(R.id.rvDogs);
        btnAdd = findViewById(R.id.btnAdd);
        btnSync = findViewById(R.id.btnSync);

        rvDogs.setLayoutManager(new LinearLayoutManager(this));

        db = DogDatabase.getInstance(this);
        dogs = db.dogDao().getAllDogs();
        adapter = new DogAdapter(this, dogs);
        rvDogs.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> AddEditDialog.show(this, "Agregar perro", "", name -> {
            Dog dog = new Dog(name, "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg");
            db.dogDao().insert(dog);
            dogs.add(dog);
            adapter.notifyDataSetChanged();
        }));

        btnSync.setOnClickListener(v -> syncFromApi());
    }

    private void syncFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogService service = retrofit.create(DogService.class);
        service.getAllBreeds().enqueue(new Callback<DogResponse>() {
            @Override
            public void onResponse(Call<DogResponse> call, Response<DogResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> breeds = response.body().getMessage();
                    for (String breed : breeds.keySet()) {
                        Dog d = new Dog(breed, "https://images.dog.ceo/breeds/" + breed + "/n02088094_1003.jpg");
                        db.dogDao().insert(d);
                    }
                    dogs.clear();
                    dogs.addAll(db.dogDao().getAllDogs());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Sincronizado con la API 🐶", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DogResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
