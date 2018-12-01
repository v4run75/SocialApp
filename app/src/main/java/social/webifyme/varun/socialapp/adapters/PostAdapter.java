package social.webifyme.varun.socialapp.adapters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import social.webifyme.varun.socialapp.R;
import social.webifyme.varun.socialapp.models.Posts;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Posts> items;
    OnItemClickListener listener;

    public PostAdapter(Context context, ArrayList<Posts> users) {
        this.items = users;
        this.context = context;
    }
    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, int pos, View view);
    }
    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_new, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(context,items.get(i),i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView question;
        private RelativeLayout relativeLayout;
        LinearLayout linearLayout;
//        int lastPosition=-1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cover);
            question = itemView.findViewById(R.id.question);
//            relativeLayout =itemView.findViewById(R.id.relative);
//            linearLayout=itemView.findViewById(R.id.flipper);
         }

        public void bind(final Context context, final Posts item, final int pos) {
        imageView.setImageResource(item.getImages());
        question.setText(item.getQuestions());
//        relativeLayout.animate().rotationX(180).setDuration(1000).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                relativeLayout.animate().rotationX(0).setDuration(0).start();
//                Log.e("Inside End","Animate");
//            }
//        });
//        relativeLayout.setAnimation();

//            if (pos > lastPosition) {
//                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
//                imageView.startAnimation(animation);
//                question.startAnimation(animation);
//                lastPosition = pos;
//            }
        }
    }
}
