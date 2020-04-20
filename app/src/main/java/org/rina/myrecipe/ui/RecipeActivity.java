package org.rina.myrecipe.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import org.rina.myrecipe.R;
import org.rina.myrecipe.api.helper.ServiceGenerator;
import org.rina.myrecipe.api.models.Envelope;
import org.rina.myrecipe.api.models.RecipeAdapter;
import org.rina.myrecipe.api.models.RecipeResponse;
import org.rina.myrecipe.api.services.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    public Context context;
    RecyclerView teamsView;
    private ProgressBar loading;
    int count = 0, page = 1;
    Button btn_next, btn_prev;
    private Envelope<List<RecipeResponse>> items;
    RecipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        teamsView = findViewById(R.id.rv_food);
        loading = findViewById(R.id.loading);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_load);
        getMeRecipe();
    }

    private void getMeRecipe() {
        loading.setVisibility(View.VISIBLE);
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<List<RecipeResponse>>> call = service.getRecipe();
        call.enqueue(new Callback<Envelope<List<RecipeResponse>>>() {
            @Override
            public void onResponse(Call<Envelope<List<RecipeResponse>>> call, Response<Envelope<List<RecipeResponse>>> response) {
                if (response.isSuccessful()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(RecipeActivity.this);
                    alert.setTitle("Notification")
                            .setMessage("Your data have been succesfully loaded")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alert.setCancelable(true);
                                }
                            })
                            .setCancelable(false);
                    alert.show();
//                    Toast.makeText(RecipeActivity.this, "Your data have been succesfully loaded", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    Envelope<List<RecipeResponse>> items = response.body();
                    RecipeAdapter.setItems(items);
                    for (int i = 0; i < response.body().getData().size(); i++){
                        RecipeAdapter adapter = new RecipeAdapter(RecipeActivity.this, items);
                        teamsView.setAdapter(adapter);
                        count = i;

                    }
                    setButton();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeActivity.this);
                    teamsView.setLayoutManager(layoutManager);
                } else {
                    AlertDialog.Builder batal = new AlertDialog.Builder(RecipeActivity.this);
                    batal.setTitle("Notification")
                            .setMessage("Your data cant be loaded")
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getMeRecipe();
                                }
                            })
                            .setCancelable(false);
                    batal.show();
                    loading.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Envelope<List<RecipeResponse>>> call, Throwable t) {
//                Toast.makeText(RecipeActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
//                llError.setVisibility(View.VISIBLE);
                AlertDialog.Builder fail = new AlertDialog.Builder(RecipeActivity.this);
                fail.setTitle("Notification")
                        .setMessage("Something Wrong")
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getMeRecipe();
                            }
                        })
                        .setCancelable(false);
                fail.show();
                loading.setVisibility(View.GONE);
            }
        });
    }
    private void getMoreRecipe(){
        loading.setVisibility(View.VISIBLE);
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<List<RecipeResponse>>> call = service.getMoreRecipe(page);
        call.enqueue(new Callback<Envelope<List<RecipeResponse>>>() {
            @Override
            public void onResponse(Call<Envelope<List<RecipeResponse>>> call, Response<Envelope<List<RecipeResponse>>> response) {
                if (response.isSuccessful()) {
                    loading.setVisibility(View.GONE);

                    Envelope<List<RecipeResponse>> items = response.body();
                    RecipeAdapter.setItems(items);
                    for (int i = 0; i < response.body().getData().size(); i++){
                        RecipeAdapter adapter = new RecipeAdapter(RecipeActivity.this, items);
                        teamsView.setAdapter(adapter);
                        count = i;
                    }
                    setButton();
                    RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(RecipeActivity.this);
                    teamsView.setLayoutManager(layoutManager);

                } else {
                    AlertDialog.Builder batal = new AlertDialog.Builder(RecipeActivity.this);
                    batal.setTitle("Notification")
                            .setMessage("Your data cant be loaded")
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getMeRecipe();
                                }
                            })
                            .setCancelable(false);
                    batal.show();
                    loading.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Envelope<List<RecipeResponse>>> call, Throwable t) {
//                Toast.makeText(RecipeActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
//                llError.setVisibility(View.VISIBLE);
                AlertDialog.Builder fail = new AlertDialog.Builder(RecipeActivity.this);
                fail.setTitle("Notification")
                        .setMessage("Something Wrong")
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getMeRecipe();
                            }
                        })
                        .setCancelable(false);
                fail.show();
                loading.setVisibility(View.GONE);
            }
        });
    }

    public void handleMoreRecipe(View view) {
        page++;
        count = 0;
        teamsView.setAdapter(null);
        RecipeAdapter.setItems(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMoreRecipe();
            }
        }, 500);
        System.out.println(page);
    }

    void setButton(){
        if (count < 9){
            btn_next.setEnabled(false);
        }else{
            btn_next.setEnabled(true);
        }

        if (page != 1){
            btn_prev.setEnabled(true);
        }else{
            btn_prev.setEnabled(false);
        }
    }

    public void handlePrev(View view) {
        page--;
        count = 0;
        teamsView.setAdapter(null);
        RecipeAdapter.setItems(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMoreRecipe();
            }
        }, 500);
        System.out.println(page);
    }
}
