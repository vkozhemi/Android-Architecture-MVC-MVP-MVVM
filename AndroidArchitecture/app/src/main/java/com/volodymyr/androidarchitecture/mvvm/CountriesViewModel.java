package com.volodymyr.androidarchitecture.mvvm;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.volodymyr.androidarchitecture.model.CountriesService;
import com.volodymyr.androidarchitecture.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends ViewModel {

  private final MutableLiveData<List<String>> countries = new MutableLiveData<>();
  private final MutableLiveData<Boolean> countyError = new MutableLiveData<>();

  private CountriesService service;

  public CountriesViewModel() {
    service = new CountriesService();
    fetchCountries();
  }

  public LiveData<List<String>> getCountries() {
    return countries;
  }

  public LiveData<Boolean> getCountryError() {
    return countyError;
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
                countries.setValue(countryNames);
                countyError.setValue(false);
              }

              @Override
              public void onError(Throwable e) {
                countyError.setValue(true);
              }
            });
  }

  public void onRefresh() {
    fetchCountries();
  }
}
