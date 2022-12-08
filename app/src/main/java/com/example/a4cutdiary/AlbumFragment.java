package com.example.a4cutdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;


import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AlbumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private ArrayList<String> mItems;
    //private GridView gridView;
    //ThumbnailDownloader<ImageView> mThumbnailThread;
    private static final int PICK_IMAGE = 1000;
    private List<String> images = new ArrayList<>();
    ImageAdapter adapter;
    /*
    Integer[] posterID = {

            R.drawable.ic_album, R.drawable.ic_home, R.drawable.ic_map, R.drawable.ic_modechage1, R.drawable.ic_modechange2
    };*/




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup albumView = (ViewGroup) inflater.inflate(R.layout.fragment_album, container, false);

        Button button = albumView.findViewById(R.id.changebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(0);
            }
        });
        /*
        ImageButton addphoto = albumView.findViewById(R.id.addPhoto);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.AddNewPhoto();
            }
        });
        */

        //list = new ArrayList<ImageView>();
        GridView gridView = (GridView) albumView.findViewById(R.id.albumGrid);
        //adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        adapter = new ImageAdapter(getActivity(),images);
        gridView.setAdapter(adapter);


       // Intent intent = new Intent(getActivity(), MainActivity.class);
       // intent.putExtra("images", (Serializable) mainData.getData().getFeaturedProduct());
       // getActivity().startActivity(intent);

        Button photo_add = albumView.findViewById(R.id.photo_add);
        photo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();

                //MainActivity activity = (MainActivity) getActivity();
                //activity.pickImageFromGallery();
            }

            public void pickImageFromGallery() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"추억을 고르세요"),1000);

            }

/*
            public void onActivityResult(int requestCode, int resultCode, Intent data){
                AlbumFragment.super.onActivityResult(requestCode, resultCode,data);
                if(requestCode == 1000){
                    images.add(String.valueOf(data.getData()));
                    adapter.notifyDataSetChanged();
                }
            }

 */
        });


        /////


        //bindGrid();

        /*
        GridView gv = albumView.findViewById(R.id.albumGrid);
        PhGridArrayAdapter gAdapter = new PhGridArrayAdapter(getActivity(),itemList);
        gv.setAdapter(gAdapter);

         */


        /*
        GridView gv = albumView.findViewById(R.id.albumGrid);
        MyGridAdapter gAdapter = new MyGridAdapter(getActivity());
        gv.setAdapter(gAdapter);

         */

        return albumView;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 1000){
            images.add(String.valueOf(data.getData()));
            adapter.notifyDataSetChanged();
        }
    }


    /*private void bindGrid() {
        List<PhGridItem> itemList = new ArrayList<>();
        itemList
    }*/


    /*
    public  class MyGridAdapter extends BaseAdapter{
        Context context;
        public MyGridAdapter(Context c){
            context=c;
        }
        public int getCount(){

            return 0;
        }
        public Object getItem(int arg0){

            return null;
        }
        public long getItemId(int arg0){

            return 0;
        }
        public View getView(int arg0, View arg1, ViewGroup arg2){
            ImageView imageView = new ImageView(context);
            //imageView.setLayoutParams(new GridLayout.LayoutParams(100,300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5,5,5,5);

            imageView.setImageResource(posterID[arg0]);
            return imageView;
        }
    }

     */
    /*
    void setupAdapter(){
        if (getActivity() == null || gridView == null) return;
        if (mItems != null) {
            gridView.setAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_gallery_item, mItems));
        } else {
            gridView.setAdapter(null);
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ArrayList<String> quotes = DatabaseManager.get(getActivity()).getAllActiveQuotes();
            return quotes;
        }

        @Override
        protected void onPostExecute(ArrayList<String> items) {
            mItems = items;
            setupAdapter();
        }
    }*/

    /*
    public void pickImageFromGallery(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"추억을 고르세요"),PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == PICK_IMAGE){
            images.add(String.valueOf(data.getData()));
            adapter.notifyDataSetChanged();
        }
    }

     */



}