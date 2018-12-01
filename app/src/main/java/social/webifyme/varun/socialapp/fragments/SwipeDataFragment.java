package social.webifyme.varun.socialapp.fragments;

import android.content.Context;
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
import me.kaelaela.verticalviewpager.VerticalViewPager;
import social.webifyme.varun.socialapp.R;
import social.webifyme.varun.socialapp.adapters.CardPagerAdapterS;
import social.webifyme.varun.socialapp.adapters.PostAdapter;
import social.webifyme.varun.socialapp.models.CardItemString;
import social.webifyme.varun.socialapp.models.Posts;
import social.webifyme.varun.socialapp.utils.HorizontalFlipTransformation;
import social.webifyme.varun.socialapp.utils.ShadowTransformer;

public class SwipeDataFragment extends android.support.v4.app.Fragment {
    View view;
    private VerticalViewPager verticalViewPager;

    private CardPagerAdapterS mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    String titlesText [] = {" Time Table 0", " Time Table 1", " Time Table 2"};
    String  detailsArray [] = {
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
    };
    int imageArray[]={R.drawable.gradient,R.drawable.gradient,R.drawable.gradient};
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_home_pager,container,false);
        context=getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        verticalViewPager= new VerticalViewPager(this);
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        VerticalViewPager mViewPager = (VerticalViewPager) view.findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapterS();


        for (int i=0; i<titlesText.length; i++){
            mCardAdapter.addCardItemS(new CardItemString(titlesText[i],detailsArray[i],imageArray[i]));
        }
//        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        HorizontalFlipTransformation horizontalFlipTransformation = new HorizontalFlipTransformation();
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, horizontalFlipTransformation);
        mViewPager.setOffscreenPageLimit(3);
//        verticalViewPager.setAdapter(mCardAdapter);
//        verticalViewPager.setPageTransformer(false, horizontalFlipTransformation);
//        verticalViewPager.setOffscreenPageLimit(3);

    }
}
