package com.zuiai.activitytest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.zuiai.activitytest.aidl.Book;
import com.zuiai.activitytest.aidl.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);

            try {

                Book newBook = new Book(3,"History");
                bookManager.addBook(newBook);
                List<Book> list = bookManager.getBookList();
                Log.i("MainActivity", "onServiceConnected: "+ JSON.toJSONString(list));




            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

        Button button = findViewById(R.id.btn_remote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,RemoteActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
