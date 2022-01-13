package hoangdat.tdtu.deliverysystem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    ImageView imvLogin;
    Integer userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        //REQUEST PERMISSION
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        userID = prefs.getInt("userid", 0);
        if (userID == 0)
        {
        }
        else if (userID == 1)
        {
            Intent moveToAdmin = new Intent(this, MainActivity.class);
            startActivity(moveToAdmin);
        } else if (userID == 2)
        {
            Intent moveToCSKH = new Intent(this, MainActivityCSKH.class);
            startActivity(moveToCSKH);
        } else if (userID == 3)
        {
            Intent moveToKho = new Intent(this, MainActivityKho.class);
            startActivity(moveToKho);
        } else if (userID == 4)
        {
            Intent moveToShipper = new Intent(this, MainActivityShipper.class);
            startActivity(moveToShipper);
        }

        imvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(prefs);
            }
        });
    }

    public void mapping()
    {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        imvLogin = findViewById(R.id.btnLogin);
    }

    private void login(SharedPreferences prefs)
    {
        if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() )
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu !", Toast.LENGTH_LONG).show();
        } else if (etUsername.getText().toString().equals("KHACHHANG") && etPassword.getText().toString().equals("123456"))
        {
            prefs.edit().putInt("userid",1).commit();
            Intent moveToAdmin = new Intent(this, MainActivity.class);
            startActivity(moveToAdmin);
        }else if (etUsername.getText().toString().equals("NVCSKH") && etPassword.getText().toString().equals("123456"))
        {
            prefs.edit().putInt("userid",2).commit();
            Intent moveToCSKH = new Intent(this, MainActivityCSKH.class);
            startActivity(moveToCSKH);
        } else if (etUsername.getText().toString().equals("NVKHO") && etPassword.getText().toString().equals("123456"))
        {
            prefs.edit().putInt("userid",3).commit();
            Intent moveToKho = new Intent(this, MainActivityKho.class);
            startActivity(moveToKho);
        } else if (etUsername.getText().toString().equals("NVGIAOHANG") && etPassword.getText().toString().equals("123456"))
        {
            prefs.edit().putInt("userid",4).commit();
            Intent moveToShipper = new Intent(this, MainActivityShipper.class);
            startActivity(moveToShipper);
        }  else {
            Toast.makeText(this, "Đăng nhập thất bại! Vui lòng kiểm tra lại tài khoản và mật khẩu", Toast.LENGTH_LONG).show();
        }
    }


}