package com.sb.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.sb.tictactoe.databinding.ActivityMain2Binding;
import com.sb.tictactoe.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        binding = DataBindingUtil.setContentView(MainActivity2.this, R.layout.activity_main2) ;
        binding.buttonStart.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
            intent.putExtra("checkedButton",binding.radioGroup.getCheckedRadioButtonId());
            intent.putExtra("name", binding.editTextTextPersonName.getText().toString().trim());
            startActivity(intent);
        });
    }
}