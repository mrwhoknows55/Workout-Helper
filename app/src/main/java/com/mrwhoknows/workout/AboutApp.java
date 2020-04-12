package com.mrwhoknows.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutApp extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        intent = new Intent(Intent.ACTION_VIEW);
    }

    public void challege(View view) {
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=VFrKjhcTAzE"));
        startActivity(intent);
    }

    public void git(View view) {
        intent.setData(Uri.parse("https://github.com/mrwhoknows55"));
        startActivity(intent);
    }

    public void ln(View view) {
        intent.setData(Uri.parse("https://www.linkedin.com/in/mrwhoknows/"));
        startActivity(intent);
    }

    public void ig(View view) {
        intent.setData(Uri.parse("https://instagram.com/mr_whoknows/"));
        startActivity(intent);
    }
}
