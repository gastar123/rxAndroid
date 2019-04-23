package com.example.rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout llMain;
    private int i;
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
                .flatMap(new Function<List<PictureUrl>, ObservableSource<PictureUrl>>() {
                    @Override
                    public ObservableSource<PictureUrl> apply(List<PictureUrl> pictureUrls) throws Exception {
                        return Observable.fromArray(pictureUrls.toArray(new PictureUrl[0]));
                    }
                })
                .subscribe(new Observer<PictureUrl>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PictureUrl pictureUrl) {
                            imageView = new ImageView(MainActivity.this);
                            Picasso.with(MainActivity.this).load(pictureUrl.getPicUrl()).into(imageView);
                            llMain.addView(imageView, lParams);
                            i ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "Загружено " + String.valueOf(i) + " изображений", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
