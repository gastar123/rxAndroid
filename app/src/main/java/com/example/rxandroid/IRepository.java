package com.example.rxandroid;

import java.util.List;

import io.reactivex.Observable;

public interface IRepository {
    Observable<List<PictureUrl>> getPicturesUrl(String url);
}
