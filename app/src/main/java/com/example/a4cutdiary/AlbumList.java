package com.example.a4cutdiary;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AlbumList extends AppCompatActivity {
    GridView gridView;
    ArrayList<Album> list;
    AlbumListAdapter adapter = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new AlbumListAdapter(this, R.layout.album_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = AlbumFragment.sqLiteHelper.getData("SELECT * FROM DIARY");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String diary = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Album(diary, image, id));
        }
        adapter.notifyDataSetChanged();
/////////////////////////////////////////////////////////////////////////////////////////////////////
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"추억 수정하기", "추억 지우기"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(AlbumList.this);

                dialog.setTitle("OPTION");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = AlbumFragment.sqLiteHelper.getData("SELECT id FROM DIARY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(AlbumList.this, arrID.get(position));

                        } else {

                            // delete
                            Cursor c = AlbumFragment.sqLiteHelper.getData("SELECT id FROM DIARY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));


                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView imageViewPhoto;
    private void showDialogUpdate(AlbumList activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_photo_activity);
        dialog.setTitle("추억 수정하기!");

        imageViewPhoto = (ImageView) dialog.findViewById(R.id.updatePhoto);
        final EditText edtText = (EditText) dialog.findViewById(R.id.updateText);
        Button btnUpdate = (Button) dialog.findViewById(R.id.updateButton);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.98);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        AlbumList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlbumFragment.sqLiteHelper.updateData(
                            edtText.getText().toString().trim(),
                            AlbumFragment.imageViewToByte(imageViewPhoto),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "수정 성공!!!새 추억~!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateAlbumList();
            }
        });
    }

    private void showDialogDelete(final int idPhoto){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(AlbumList.this);

        dialogDelete.setTitle("추억 지우기 ㅜ0ㅜ");
        dialogDelete.setMessage("다시 기억 못할지도 몰라요ㅜㅜ");
        dialogDelete.setPositiveButton("괜찮아요!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    AlbumFragment.sqLiteHelper.deleteData(idPhoto);
                    Toast.makeText(getApplicationContext(), "추억 지우기 성공!!!잘가~",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateAlbumList();
            }
        });

        dialogDelete.setNegativeButton("아...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void updateAlbumList(){
        // get all data from sqlite
        Cursor cursor = AlbumFragment.sqLiteHelper.getData("SELECT * FROM DIARY");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String diary = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Album(diary, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "앱의 권한 설정을 확인해주세요!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }


}


