package social.webifyme.varun.socialapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import social.webifyme.varun.socialapp.R;
import social.webifyme.varun.socialapp.adapters.PostAdapter;
import social.webifyme.varun.socialapp.models.Posts;

public class HomeFragment extends android.support.v4.app.Fragment {
    View view;
    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Posts> postsList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.items);
//        FlipInTopXAnimator animator = new FlipInTopXAnimator(new OvershootInterpolator(1f));
        SnapHelper snapHelper=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        Posts post= new Posts("Test Question 1","Test Answer 1 Test Answer 1Test Answer 1Test Answer 1Test Answer 1Test Answer 1Test Answer 1Test Answer 1Test Answer 1Test Answer 1",1,R.drawable.gradient);
        Posts post1= new Posts("Test Question 2","Test Answer 2",2,R.drawable.gradient);
        postsList.add(post);
        postsList.add(post1);
        adapter=new PostAdapter(getContext(),postsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                recyclerView.animate().rotationX(180).setDuration(1000).start();
//            }
//        });

    }
}
