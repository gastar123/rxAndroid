package com.example.rxandroid;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout llMain;
    private LinearLayout.LayoutParams lParams;
    private int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = findViewById(R.id.llMain);

        lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
        lParams.gravity = Gravity.LEFT;

        new RepositoryImpl().getPicturesUrl("https://ru.dotabuff.com/heroes")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(pictureUrls -> Observable.fromIterable(pictureUrls))
                .subscribe(pictureUrl -> {
                    imageView = new ImageView(MainActivity.this);
                    Picasso.with(MainActivity.this).load(pictureUrl.getPicUrl()).into(imageView);
                    llMain.addView(imageView, lParams);
                });
    }
}
