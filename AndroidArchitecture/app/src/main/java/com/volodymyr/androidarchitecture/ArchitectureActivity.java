package com.volodymyr.androidarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.volodymyr.androidarchitecture.mvc.MVCActivity;
import com.volodymyr.androidarchitecture.mvp.MVPActivity;
import com.volodymyr.androidarchitecture.mvvm.MVVMActivity;

public class ArchitectureActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_architectures);
  }


  public void onMVC(View view) {
    startActivity(MVCActivity.getIntent(this));
  }

  public void onMVP(View view) {
    startActivity(MVPActivity.getIntent(this));
  }

  public void onMVVM(View view) {
    startActivity(MVVMActivity.getIntent(this));
  }


}