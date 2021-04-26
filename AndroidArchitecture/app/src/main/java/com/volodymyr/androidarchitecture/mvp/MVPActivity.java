package com.volodymyr.androidarchitecture.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.volodymyr.androidarchitecture.R;
import com.volodymyr.androidarchitecture.mvc.CountriesController;
import com.volodymyr.androidarchitecture.mvc.MVCActivity;

import java.util.ArrayList;
import java.util.List;

public class MVPActivity extends AppCompatActivity implements CountriesPresenter.View {

  private List<String> listValues = new ArrayList<>();
  private ArrayAdapter<String> adapter;
  private ListView list;
  private CountriesPresenter presenter;
  private Button retryButton;
  private ProgressBar progress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_m_v_p);
    setTitle("MVP Activity");

    list = findViewById(R.id.list);
    retryButton = findViewById(R.id.retry);
    progress = findViewById(R.id.progress);

    presenter = new CountriesPresenter(this);

    adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
    list.setAdapter(adapter);
    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MVPActivity.this, "You clicked "+listValues.get(position), Toast.LENGTH_SHORT).show();
      }
    });

  }

  public static Intent getIntent(Context context) {
    return new Intent(context, MVPActivity.class);
  }

  @Override
  public void setValues(List<String> listCountries) {
    listValues.clear();
    listValues.addAll(listCountries);
    retryButton.setVisibility(View.GONE);
    progress.setVisibility(View.GONE);
    list.setVisibility(View.VISIBLE);
    adapter.notifyDataSetChanged();
  }

  public void onRetry(View view) {
    presenter.onRefresh();
    list.setVisibility(View.GONE);
    retryButton.setVisibility(View.GONE);
    progress.setVisibility(View.VISIBLE);
  }

  @Override
  public void onError() {
    Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show();
    progress.setVisibility(View.GONE);
    list.setVisibility(View.GONE);
    retryButton.setVisibility(View.VISIBLE);
  }
}