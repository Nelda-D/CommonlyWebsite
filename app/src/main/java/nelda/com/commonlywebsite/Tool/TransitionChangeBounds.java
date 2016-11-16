package nelda.com.commonlywebsite.Tool;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import nelda.com.commonlywebsite.R;

/**
 * Created by Administrator on 2016/11/15.
 */

public class TransitionChangeBounds extends ChangeBounds {
    public static final String PROPERTY_COLOR = "transition:color";
    public List<Animator> mList_Animator = new ArrayList<>();

    public void addAnimatorPlayTogether(Animator animators){
        mList_Animator.add(animators);
    }

    public TransitionChangeBounds(){
        super();
    }

//    @Override
//    public void captureStartValues(TransitionValues transitionValues) {
//        super.captureStartValues(transitionValues);
//        final View view = transitionValues.view;
//        if(view.getWidth() <= 0 || view.getHeight() <= 0) return;
//        transitionValues.values.put(PROPERTY_COLOR, R.color.shadow_background_alpha_50);
//    }
//
//    @Override
//    public void captureEndValues(TransitionValues transitionValues) {
//        super.captureEndValues(transitionValues);
//        final View view = transitionValues.view;
//        if(view.getWidth() <= 0 || view.getHeight() <= 0) return;
//        transitionValues.values.put(PROPERTY_COLOR,R.color.shadow_background_alpha_0);
//    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues == null || changeBounds == null) {
            return null;
        }

        AnimatorSet transition = new AnimatorSet();
        transition.setDuration(300);
        transition.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in));
        mList_Animator.add(changeBounds);
        transition.playTogether(mList_Animator);
        return changeBounds;

    }
}


