package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.bean.OtherInfoBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.module.persondetail.PersonDetailAction;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.utils.image.ImageUtils;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener {
    private final static String OTHERID = "otherid";

    private ImageView mUserdetailimage;
    private TextView mUsernametext, mUsersigntext;
    private TextView mContact;
    private RelativeLayout mSeachmsglayout;
    private LinearLayout mOperationlayout;
    private ImageView mOperationimage;

    private long mOtherid;
    private UserBean mUserBean;
    private boolean mIsfriend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);
        setTitle(R.string.title_personal_info);
        mOtherid = getIntent().getLongExtra(OTHERID, 0);
        if (mOtherid <= 0) {
            UIUtils.showToast(R.string.noresult);
            finish();
            return;
        } else if (UserProfile.isLogin() && UserProfile.getUser().getUserId() == mOtherid) {
            PersonalDetailActivity.lanuch(this);
            finish();
            return;
        }
        initview();
        initdate();
    }

    public void initview() {
        mUserdetailimage = (ImageView) findViewById(R.id.user_detail_image);
        mUsernametext = (TextView) findViewById(R.id.user_name_text);
        mUsersigntext = (TextView) findViewById(R.id.user_sign_text);
        mContact = (TextView) findViewById(R.id.context_num);
        mSeachmsglayout = (RelativeLayout) findViewById(R.id.seachmsg_layout);
        mOperationlayout = (LinearLayout) findViewById(R.id.operation_layout);
        mOperationimage = (ImageView) findViewById(R.id.operation_image);
        mSeachmsglayout.setOnClickListener(this);
        mOperationlayout.setOnClickListener(this);
    }

    public void initdate() {
        PersonDetailAction.getOtherUserDetail(mOtherid, new PersonDetailAction.IGetUserDetailCB() {
            @Override
            public void onGetSuccess(OtherInfoBean info) {
                mUserBean = info.getOtherInfo();
                if (mUserBean.getUserName() != null && !mUserBean.getUserName().equals("")) {
                    mUsernametext.setText(mUserBean.getUserName());
                    setTitle(mUserBean.getUserName());
                }
                if (mUserBean.getSign() != null && !mUserBean.getSign().equals("")) {
                    mUsersigntext.setText(mUserBean.getSign());
                }
                if (mUserBean.getContact() != null && !mUserBean.getContact().equals("")) {
                    mContact.setText(mUserBean.getContact());
                }
                if (mUserBean.getPhotoName() != null && !mUserBean.getPhotoName().equals("")) {
                    ImageUtils.setUrl(mUserdetailimage, mUserBean.getPhotoName());
                }
                if (mUserBean.getIsFriend() == 0) {
                    mOperationimage.setImageDrawable(getResources().getDrawable(R.drawable.operation_add));
                    mIsfriend = false;
                } else {
                    mIsfriend = true;
                    mOperationimage.setImageDrawable(getResources().getDrawable(R.drawable.operation_confim));
                }
            }

            @Override
            public void onGetFail(int code, String message) {
                UIUtils.showToast(R.string.get_userdetail_fail);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seachmsg_layout:
                OtherInfoLostListActivity.launch(this, mOtherid);
                break;
            case R.id.operation_layout:
                friendOperation(!mIsfriend);
                break;
            default:

                break;
        }
    }

    public void friendOperation(final boolean isFollow) {
        PersonDetailAction.friendOperate(isFollow, mOtherid, new PersonDetailAction.IFollowCB() {
            @Override
            public void onSuccess() {
                UIUtils.showToast(isFollow ? R.string.add_success : R.string.delete_success);
                mOperationimage.setImageDrawable(getResources().getDrawable(mIsfriend ? R.drawable.operation_add : R.drawable.operation_confim));
                mIsfriend = !mIsfriend;
            }

            @Override
            public void onFail(int code, String message) {
                UIUtils.showToast(isFollow ? R.string.add_fail : R.string.delete_fail);
            }
        });
    }

    public static void launch(Context context, long otherid) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(OTHERID, otherid);
        context.startActivity(intent);
    }
}
