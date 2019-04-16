package com.example.rxandroid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RepositoryImpl implements IRepository {
    @Override
    public Observable<List<PictureUrl>> getPicturesUrl(String url) {
        return Observable.create(new ObservableOnSubscribe<List<PictureUrl>>() {
            @Override
            public void subscribe(ObservableEmitter<List<PictureUrl>> emitter) throws Exception {
                List<PictureUrl> urlsList = new ArrayList<>();
                Document doc;
                try {
                    doc = Jsoup.connect(url).get();

                    Elements heroes = doc.select("div.hero-grid");
                    String dotabfURL = "https://ru.dotabuff.com";

                    for (Element hero : heroes.select("div.hero")) {
                        String heroURL = hero.attr("style");

                        PictureUrl pictureUrl = new PictureUrl();
                        pictureUrl.setPicUrl(dotabfURL + heroURL.substring(16, heroURL.length() - 1));
                        urlsList.add(pictureUrl);
                    }
                    emitter.onNext(urlsList);

                } catch (IOException e) {
                    emitter.onError(e);
                } finally {
                    emitter.onComplete();
                }
            }
        });
    }
}
