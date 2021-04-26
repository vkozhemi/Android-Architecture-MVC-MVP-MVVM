package com.volodymyr.androidarchitecture.mvp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.volodymyr.androidarchitecture.model.CountriesService;
import com.volodymyr.androidarchitecture.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesPresenter {

  private View view;
  private CountriesService service;

  public CountriesPresenter(View view) {
    this.view = view;
    service = new CountriesService();

    fetchCountries();
  }

  @SuppressLint("CheckResult")
  private void fetchCountries() {
    service.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<List<Country>>() {

              @Override
              public void onSuccess(List<Country> value) {
                List<String> countryNames = new ArrayList<>();
                for (Country country : value) {
                  countryNames.add(country.countryName);
                }
                view.setValues(countryNames);
              }

              @Override
              public void onError(Throwable e) {
                Log.d("LOG_TAG", "onError Throwable = "+e);
                view.onError();
              }
            });
  }

  public void onRefresh() {
    fetchCountries();
  }

  public interface View {
    void setValues(List<String> list);
    void onError();
  }

}
