package com.sb.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.sb.tictactoe.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home) ;
        binding.buttonStart.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this,BoardActivity.class);
            intent.putExtra("checkedButton",binding.radioGroup.getCheckedRadioButtonId());
            intent.putExtra("name", binding.editTextTextPersonName.getText().toString().trim());
            startActivity(intent);
        });
    }
}