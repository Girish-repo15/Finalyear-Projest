package com.example.multitransulator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multitransulator.databinding.ActivityTransulationBinding;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.Objects;

public class Transulation extends AppCompatActivity implements View.OnClickListener {
  ActivityTransulationBinding bind;
  TextToSpeech ts;
  String ff="";
    ProgressDialog p;
    Dialog dialog;
    CardView tamil,kannada,telugu, japanese,hindi,french,spanish,urdu,russian,marati;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind=ActivityTransulationBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        ActionBar kk = Objects.requireNonNull(getSupportActionBar());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            kk.setTitle("Image to Multi-lingual Text");
            kk.setBackgroundDrawable(new ColorDrawable(getColor(R.color.myback)));
        }else{
            kk.setTitle("Image to Multi-lingual Text");
            kk.setBackgroundDrawable(new ColorDrawable(R.color.myback));

        }


        p=new ProgressDialog(this);
        p.setTitle("Loading....");
        p.setCancelable(false);

    dialog= new Dialog(this);
    dialog.setContentView(R.layout.popup);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bind.gallery.setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,101);
        });


        ts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
if(i==TextToSpeech.ERROR){
    Toast.makeText(Transulation.this, "Speaker Doesn't Work", Toast.LENGTH_SHORT).show();

}else{
    ts.setSpeechRate(0.9F);

}
            }
        });
  bind.capture.setOnClickListener(v->{


          if (ActivityCompat.checkSelfPermission(Transulation.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
              } else {
                  ActivityCompat.requestPermissions(Transulation.this, new String[]{Manifest.permission.CAMERA}, 200);
              }
          } else {
              startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 200
              );

          }

  });


  bind.speaker.setOnClickListener(v-> speak());



        tamil=findViewById(R.id.tamil);
        kannada=findViewById(R.id.kannada);
        telugu=findViewById(R.id.telugu);
        japanese =findViewById(R.id.japenese);
        hindi=findViewById(R.id.hindi);
        french=findViewById(R.id.french);
        spanish=findViewById(R.id.spanish);
        urdu=findViewById(R.id.urdu);
        russian=findViewById(R.id.russian);
        marati=findViewById(R.id.marati);
        marati.setOnClickListener(this);
        tamil.setOnClickListener(this);
        kannada.setOnClickListener(this);
        telugu.setOnClickListener(this);
        japanese.setOnClickListener(this);
        hindi.setOnClickListener(this);
        french.setOnClickListener(this);
        spanish.setOnClickListener(this);
        urdu.setOnClickListener(this);
        russian.setOnClickListener(this);
    }


    private void speak() {
  ts.speak(bind.editText.getText(),TextToSpeech.QUEUE_FLUSH,null,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(data!=null){
        ff="Help";
        if(requestCode!=101) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bind.imageview.setImageBitmap(bitmap);

            InputImage image = InputImage.fromBitmap(bitmap, 0);

            getenglish(image);
        }else {
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(data.getData().toString()));
                InputImage image=InputImage.fromBitmap(bitmap,0);
                getenglish(image);
bind.imageview.setImageURI(Uri.parse(data.getData().toString()));
            } catch (IOException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    }



    private void getenglish(InputImage bitmap) {
        p.show();

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
            recognizer.process(bitmap)
                    .addOnSuccessListener(text -> {
                        bind.editText.setText(text.getText());
                        p.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Transulation.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        p.dismiss();
                    });
                    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tamil:
if(check()){
    translating(TranslateLanguage.TAMIL);
}
                break;
            case R.id.kannada:
                if(check()){
                    translating(TranslateLanguage.KANNADA);
                }
                break;
            case R.id.marati:
                if(check()){
            translating(TranslateLanguage.MARATHI);
                   }
                break;
            case R.id.telugu:
                if(check()){
                    translating(TranslateLanguage.TELUGU);
                }
                break;
            case R.id.japenese:
                if(check()){
                    translating(TranslateLanguage.JAPANESE);
                }
                break;
            case R.id.hindi:
                if(check()){
                    translating(TranslateLanguage.HINDI);
                }
                break;
            case R.id.french:
                if(check()){
                    translating(TranslateLanguage.FRENCH);
                }
                break;
            case R.id.spanish:
                if(check()){
                    translating(TranslateLanguage.SPANISH);
                }
                break;
            case R.id.urdu:
                if(check()){
                    translating(TranslateLanguage.URDU);
                }
                break;
            case R.id.russian:
                if(check()){
                    translating(TranslateLanguage.SLOVAK);
                }
                break;
        }
    }

    private boolean check() {
if(bind.editText.getText().toString().trim().isEmpty()){
    Toast.makeText(this, "Please Capture a Image \n Select Image from gallery", Toast.LENGTH_SHORT).show();
    return false;
}else{
    return true;
}

    }


    private void translating(String target){
        p.show();
        TranslatorOptions option=new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(target)
                .build();
        Translator transulation = Translation.getClient(option);
        DownloadConditions condition= new DownloadConditions.Builder()
                .build();
        transulation.downloadModelIfNeeded(condition)
                .addOnSuccessListener(succes->{
                    transulation.translate(bind.editText.getText().toString())
                                    .addOnSuccessListener(o->{
                                        dialogbox(o,target);
                                        p.dismiss();
                                    }).addOnFailureListener(error->{
                                Toast.makeText(this, "2->"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                p.dismiss();
                            });

                }).addOnFailureListener(e->{
                    Toast.makeText(this, "1->"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    p.dismiss();
                });
    }

    private void dialogbox(String o, String transula) {

        EditText extracted=dialog.findViewById(R.id.extracted);
        EditText transulated=dialog.findViewById(R.id.transulated);
        transulated.setText(o);
        extracted.setText(bind.editText.getText().toString());
        TextView clipboard=dialog.findViewById(R.id.clipboard);
        ImageView translation=dialog.findViewById(R.id.translation);
translation.setOnClickListener(ow->
{

    ts.speak(o,TextToSpeech.QUEUE_FLUSH,null,null);

});

        TextView cancel=dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(v->{
                   ts.stop();
                    dialog.dismiss();
                });
        clipboard.setOnClickListener(wo->{
            ClipboardManager clip=(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
          ClipData  data= ClipData.newPlainText("Translated",o);
          clip.setPrimaryClip(data);
          ts.stop();
            Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    ts.shutdown();
    }
}