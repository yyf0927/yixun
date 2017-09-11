package com.netease.study.exercise.fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.study.exercise.R;


public class DialogSimpleFragment extends DialogFragment implements View.OnClickListener {
    private int mStyle = DialogFragment.STYLE_NO_FRAME;

    private TextView mTitleTv;
    private TextView mSimpleTipTv;
    private Button mCancleBt;
    private Button mConfirmBt;

    private String mTitle;
    private String mSimpleTip;
    private String mConfirmText;
    private FDialogOnCancleClickListener mOnCancleClickListener;
    private FDialogOnConfirmClickListener mOnConfirmClickListener;
    private int mTheme = 0;
    private boolean mIsCancelable = true;

    public DialogSimpleFragment() {
    }

    @SuppressLint("ValidFragment")
    public DialogSimpleFragment(String simpleTip) {
        this(null, simpleTip);
    }

    @SuppressLint("ValidFragment")
    public DialogSimpleFragment(String title, String simpleTip) {
        this(title, simpleTip, null);
    }

    @SuppressLint("ValidFragment")
    public DialogSimpleFragment(String title, String simpleTip, String confirmText) {
        this.mTitle = title;
        this.mSimpleTip = simpleTip;
        this.mConfirmText = confirmText;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setCancelable(mIsCancelable);
        setStyle(mStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fdialogView = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_simple_tip, null);
        mTitleTv = (TextView) fdialogView.findViewById(R.id.pw_simple_title);
        mSimpleTipTv = (TextView) fdialogView.findViewById(R.id.pw_simple_tip);
        mCancleBt = (Button) fdialogView.findViewById(R.id.pw_simple_cancle);
        mConfirmBt = (Button) fdialogView.findViewById(R.id.pw_simple_confirm);
        ImageView closeIv = (ImageView) fdialogView.findViewById(R.id.pw_simple_close);
        mCancleBt.setOnClickListener(this);
        mConfirmBt.setOnClickListener(this);
        closeIv.setOnClickListener(this);
        mSimpleTipTv.setText(mSimpleTip);
        if (null != mTitle && !mTitle.isEmpty()) {
            mTitleTv.setText(mTitle);
            mTitleTv.setVisibility(View.VISIBLE);
        }
        if (null != mConfirmText && !mConfirmText.isEmpty()) {
            mConfirmBt.setText(mConfirmText);
        }
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return fdialogView;
    }


    public void setConfirmText(String confirm_bt_text) {
        mConfirmBt.setText(confirm_bt_text);
    }


    public void setCancleText(String cancle_bt_text) {
        mCancleBt.setText(cancle_bt_text);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
        mTitleTv.setText(title);
        mTitleTv.setVisibility(View.VISIBLE);
    }


    public void setOnCancleClickListener(FDialogOnCancleClickListener onCancleClickListener) {
        this.mOnCancleClickListener = onCancleClickListener;
    }


    public void setOnConfirmClickListener(FDialogOnConfirmClickListener onConfirmClickListener) {
        this.mOnConfirmClickListener = onConfirmClickListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pw_simple_cancle:
                if (null != mOnCancleClickListener) {
                    mOnCancleClickListener.onCancleClick(v);
                }
                dismiss();
                break;
            case R.id.pw_simple_confirm:
                if (null != mOnConfirmClickListener) {
                    mOnConfirmClickListener.onConfirmClick(v);
                }
                dismiss();
                break;
            case R.id.pw_simple_close:
                dismiss();
        }
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(int style) {
        this.mStyle = style;
    }

    @Override
    public int getTheme() {
        return mTheme;
    }

    public void setTheme(int theme) {
        this.mTheme = theme;
    }

    @Override
    public boolean isCancelable() {
        return mIsCancelable;
    }

    public void setIsCancelable(boolean isCancelable) {
        this.mIsCancelable = isCancelable;
    }

    public interface FDialogOnCancleClickListener {
        void onCancleClick(View view);
    }

    public interface FDialogOnConfirmClickListener {
        void onConfirmClick(View view);
    }

}
