package com.example.a4cutdiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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
    Button photo_add;
    Button gobtn;
    GridView gridView;
    ListView listView;
    LinearLayout albumlayout;

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

        listView = (ListView) albumView.findViewById(R.id.diaryList);
        albumlayout = (LinearLayout) albumView.findViewById(R.id.albumlayout);
        gobtn = albumView.findViewById(R.id.changebtn);

        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gobtn.getText().toString() == "Diary") {
                    albumlayout.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    gobtn.setText("Album");
                }
                else{
                    albumlayout.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    gobtn.setText("Diary");
                }
            }
        });

        gridView = (GridView) albumView.findViewById(R.id.albumGrid);
        adapter = new ImageAdapter(getActivity(),images);
        gridView.setAdapter(adapter);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {

                float currentXPosition = me.getX();
                float currentYPosition = me.getY();
                int position = gridView.pointToPosition((int) currentXPosition, (int) currentYPosition);
                if(position < 0) return false;

                // Access text in the cell, or the object itself
                String imagePath = (String) gridView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity().getApplicationContext(), DiaryActivity.class);
                intent.putExtra("imagePath", imagePath);

                startActivity(intent);

                return false;
            }
        });

        photo_add = albumView.findViewById(R.id.photo_add);
        photo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }

            public void pickImageFromGallery() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"추억을 고르세요"),1000);

            }

        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("clicked", "has clicked");
            }
        });
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

}