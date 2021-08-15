package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jgoodies.common.collect.ArrayListModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rajendra.vacationtourapp.adapter.ChatMessageAdapter;
import com.rajendra.vacationtourapp.model.ChatMessage;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BotActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton btnSend;
    EditText edtTextMsg;
    ImageView imageView;
    private Bot bot;
    public static Chat chat;
    private ChatMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);
        listView = findViewById(R.id.listView);
        btnSend = findViewById(R.id.btnSend);
        edtTextMsg = findViewById(R.id.edtTextMsg);
        imageView = findViewById(R.id.imageView);
        adapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        listView.setAdapter(adapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTextMsg.getText().toString();
                String response = chat.multisentenceRespond(edtTextMsg.getText().toString());
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(BotActivity.this, "Please enter a query ", Toast.LENGTH_SHORT).show();
                    return;

                }
                sendMessage(message);
                botsReply(response);


                //clear edittext
                edtTextMsg.setText("");
                listView.setSelection(adapter.getCount() - 1);
            }
        });
        Dexter.withActivity(this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if(report.areAllPermissionsGranted()){
                        custom();
                        Toast.makeText(BotActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                    if(report.isAnyPermissionPermanentlyDenied()){

                        Toast.makeText(BotActivity.this, "Please Grant all permissions", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(BotActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();

    }

    private void custom() {
        boolean available = isSDCartAvailable();
        AssetManager assets = getResources().getAssets();
        File fileName = new File(Environment.getExternalStorageDirectory().toString() + "/TBC/bots/custombot");
        boolean makeFile = fileName.mkdirs();
        if (fileName.exists()) {
            //read the line
            try {
                for (String dir : assets.list("custombot")) {
                    File subDir = new File(fileName.getPath() + "/" + dir);
                    boolean subDir_Check = subDir.mkdirs();
                    for (String file : assets.list("custombot/" + dir)) {
                        File newFile = new File(fileName.getPath() + "/" + dir + "/" + file);
                        if (newFile.exists()) {
                            continue;
                        }
                        InputStream in;
                        OutputStream out;
                        String str;
                        in = assets.open("custombot/" + dir + "/" + file);
                        out = new FileOutputStream(fileName.getPath() + "/" + dir + "/" + file);

                        //copy files from assts to the mobile's SD CARD
                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        //get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString()+"/TBC";
        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        bot = new Bot("custombot",MagicStrings.root_path,"chat");
        chat =  new Chat(bot);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer,0,read);
        }

    }

    public static boolean isSDCartAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)?true:false;
    }

    private void botsReply(String response) {
        ChatMessage chatMessage = new ChatMessage(true,false,response);
        adapter.add(chatMessage);
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(false,true,message);
        adapter.add(chatMessage);
    }
}