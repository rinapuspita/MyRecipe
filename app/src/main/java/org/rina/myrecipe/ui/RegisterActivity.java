package org.rina.myrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.rina.myrecipe.R;
import org.rina.myrecipe.api.helper.ServiceGenerator;
import org.rina.myrecipe.api.models.ApiError;
import org.rina.myrecipe.api.models.ErrorUtils;
import org.rina.myrecipe.api.models.RegisterRequest;
import org.rina.myrecipe.api.models.RegisterResponse;
import org.rina.myrecipe.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private RegisterRequest registerRequest;
    EditText namaInput, emailInput, passwordInput, conPassInput;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        namaInput = findViewById(R.id.eName);
        emailInput = findViewById(R.id.eEmail);
        passwordInput=findViewById(R.id.ePassword);
        conPassInput = findViewById(R.id.eConfirm);
        btnRegister = findViewById(R.id.btnSubmit);
    }

    private void register() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.doRegister(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegisterActivity.this, response.body().getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    if(namaInput.getText().toString().isEmpty()){
                        namaInput.setError(error.getError().getName().get(0));
                    } else if(emailInput.getText().toString().isEmpty()){
                        emailInput.setError(error.getError().getEmail().get(0));
                    } else if(!emailInput.getText().toString().matches(emailPattern)) {
                        emailInput.setError(error.getError().getEmail().get(0));
                    }
                    else if(passwordInput.getText().toString().isEmpty()){
                        passwordInput.setError(error.getError().getPassword().get(0));
                    } else if(passwordInput.getText().toString().length()<8){
                        passwordInput.setError(error.getError().getPassword().get(0));
                    } else if(!conPassInput.getText().toString().equals(passwordInput)){
                        conPassInput.setError(error.getError().getPassword().get(0));
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, error.getError().getEmail().get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleRegisterProses(View view) {
        String name = namaInput.getText().toString();
        String email= emailInput.getText().toString();
        String pass = passwordInput.getText().toString();
        String pass_con = conPassInput.getText().toString();
        registerRequest = new RegisterRequest(name, email, pass, pass_con);
        register();
    }
}
