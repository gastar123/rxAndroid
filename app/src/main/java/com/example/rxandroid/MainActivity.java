package com.example.rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

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
                .subscribe(new Observer<List<PictureUrl>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PictureUrl> pictureUrls) {
                        for (PictureUrl pictureUrl : pictureUrls) {
                            imageView = new ImageView(MainActivity.this);
                            Picasso.with(MainActivity.this).load(pictureUrl.getPicUrl()).into(imageView);
                            llMain.addView(imageView, lParams);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
