package com.netease.study.exercise.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netease.study.exercise.R;
import com.netease.study.exercise.activity.base.BaseActivity;
import com.netease.study.exercise.adapter.DetailCommentAdapter;
import com.netease.study.exercise.bean.Comment;
import com.netease.study.exercise.bean.InfoDetailBean;
import com.netease.study.exercise.bean.LostDetailBean;
import com.netease.study.exercise.bean.LostListItemBean;
import com.netease.study.exercise.bean.MoreCommentBean;
import com.netease.study.exercise.bean.UserBean;
import com.netease.study.exercise.module.login.UserProfile;
import com.netease.study.exercise.module.lostdetail.LostDetailAction;
import com.netease.study.exercise.net.BaseRequest;
import com.netease.study.exercise.net.HttpCallback;
import com.netease.study.exercise.share.ShareManager;
import com.netease.study.exercise.utils.UIUtils;
import com.netease.study.exercise.widget.FlashView;
import com.netease.study.exercise.widget.FlashViewListener;
import com.netease.study.exercise.widget.HrListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.netease.study.exercise.R.string.comment;

public class LostDetailActivity extends BaseActivity {
    private static final String TAG = LostDetailActivity.class.getName();
    private static final String EXTRA_ID = "id";
    private static final int SHARE = 1;
    private static final int COLLECTION = 2;

    //comment
    protected EditText mCommentEditText;
    private Button mSendCommentButton;
    //listview
    public HrListView mCommentList;
    //headerview
    public View mHeaderView;
    private FlashView mHeadFlashView;
    private TextView mHeadName;
    private ImageView mHeadImage;
    private TextView mHeadTime;
    private TextView mHeadLostName;
    private TextView mHeadLostAttr;
    private TextView mHeadLostDesc;
    private TextView mHeadLostPlace;
    private TextView mHeadLostContact;
    private TextView mHeadCommentNumber;
    private TextView mHeadNoComment;

    private int mId;
    private DetailCommentAdapter mCommentsAdapter;
    private List<Comment> mComments = new ArrayList<>();
    private int mCommentCount = 0;
    private int mPageNumber = 2;
    private InfoDetailBean mInfoDetailBean;
    private Comment mReplyComment;

    public static void launch(Context context, int id) {
        Intent intent = new Intent(context, LostDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItemCompat.setShowAsAction(menu.add(1, SHARE, 1, R.string.action_share), MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItemCompat.setShowAsAction(menu.add(1, COLLECTION, 1,R.string.action_collection),MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == SHARE) {
            if (mInfoDetailBean == null) {
                UIUtils.showToast(R.string.loading);
            } else {
                LostListItemBean bean = new LostListItemBean();
                if (mHeadFlashView.getImageUris().size() > 0) {
                    bean.setFirstPhoto(mHeadFlashView.getImageUris().get(0));
                }
                bean.setLostName(mInfoDetailBean.getLostName());
                bean.setLostSex(mInfoDetailBean.getLostSex());
                bean.setDescribes(mInfoDetailBean.getDescribes());
                bean.setLostPlace(mInfoDetailBean.getLostPlace());
                bean.setLostTime(mInfoDetailBean.getLostTime());
                ShareManager.share(this, bean);
            }
            return true;
        } else if(item.getItemId() == COLLECTION){
            LostDetailAction.addCollection(mId, new HttpCallback() {
                @Override
                public void onResponse(BaseRequest request, Object data) {
                    Toast.makeText(LostDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(BaseRequest request, Exception e, int code, String message) {
                    Toast.makeText(LostDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_info_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        initCommentListView();
        initHeaderView();

        mId = getIntent().getIntExtra(EXTRA_ID, 0);
        getLostDetail();
    }

    private void initView() {
        mCommentList = (HrListView) findViewById(R.id.lost_detail_list);

        mSendCommentButton = (Button) findViewById(R.id.report_list_detail_sendbtn);
        mCommentEditText = (EditText) findViewById(R.id.report_list_detail_message);

        mSendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendCommentButtonClick();
            }
        });
    }

    private void initCommentListView() {
        //mHeaderView
        mHeaderView = getLayoutInflater().inflate(R.layout.lost_detail_header, mCommentList, false);
        mCommentList.addHeaderView(mHeaderView);

        //mCommentsAdapter
        mCommentsAdapter = new DetailCommentAdapter(this, mComments);
        mCommentList.setAdapter(mCommentsAdapter);

        mCommentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mCommentList.getHeaderViewsCount()) {
                    return;
                }
                mReplyComment = (Comment) parent.getAdapter().getItem(position);

                String replyPrefix = getResources().getString(R.string.replyto, mReplyComment.getUserName());
                mCommentEditText.setHint(replyPrefix);
                mCommentEditText.requestFocus();

                InputMethodManager imm = (InputMethodManager) mCommentEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });

        //MoreCallBack
        mCommentList.setMoreCallBack(new HrListView.MoreCallBack() {
            @Override
            public void moreCallback() {
                LostDetailAction.getComment(mId, mPageNumber, new HttpCallback() {
                    @Override
                    public void onResponse(BaseRequest request, Object data) {
                        mCommentList.loadComplete();

                        MoreCommentBean moreCommentBean = (MoreCommentBean) data;
                        if (moreCommentBean.getCommentList() != null && moreCommentBean.getCommentList().size() > 0) {
                            mComments.addAll(moreCommentBean.getCommentList());
                            mCommentsAdapter.notifyDataSetChanged();
                            mPageNumber++;
                        }
                    }

                    @Override
                    public void onFailure(BaseRequest request, Exception e, int code, String message) {
                        mCommentList.loadComplete();
//                        Toast.makeText(LostDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initHeaderView() {
        mHeadFlashView = (FlashView) mHeaderView.findViewById(R.id.flash_view);
        mHeadImage = (ImageView) mHeaderView.findViewById(R.id.head_img);
        mHeadName = (TextView) mHeaderView.findViewById(R.id.head_user_name);
        mHeadTime = (TextView) mHeaderView.findViewById(R.id.head_time);
        mHeadLostName = (TextView) mHeaderView.findViewById(R.id.lost_detail_list_lostname);
        mHeadLostAttr = (TextView) mHeaderView.findViewById(R.id.lost_detail_list_lostattrs);
        mHeadLostDesc = (TextView) mHeaderView.findViewById(R.id.lost_detail_list_desc);
        mHeadLostPlace = (TextView) mHeaderView.findViewById(R.id.lost_detail_list_addr);
        mHeadLostContact = (TextView) mHeaderView.findViewById(R.id.lost_detail_list_phone);
        mHeadCommentNumber = (TextView) mHeaderView.findViewById(R.id.lost_comment_cnt);
        mHeadNoComment = (TextView) mHeaderView.findViewById(R.id.cb_empty);
    }

    private void getLostDetail() {
        LostDetailAction.getLostDetail(mId, new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                InfoDetailBean s = ((LostDetailBean) data).getSearchInfoDetail();
                mInfoDetailBean = s;

                if (!TextUtils.isEmpty(s.getAvatar())) {
                    Glide.with(LostDetailActivity.this)
                            .load(Uri.parse(s.getAvatar()))
                            .into(mHeadImage);
                }

                mHeadName.setText(s.getUserName());
                mHeadLostName.setText(s.getLostName());
                mHeadTime.setText(s.getCreateTime());
                mHeadLostDesc.setText(s.getDescribes());
                mHeadLostPlace.setText(s.getLostPlace());
                mHeadLostContact.setText(s.getContact());
                mHeadLostAttr.setText(getResources().getString(R.string.lost_detail_attr, s.getLostSex(), s.getYear()));
                mHeadCommentNumber.setText(s.getCommentNum() == 0
                        ? getString(comment)
                        : getString(R.string.comment_num, s.getCommentNum()));

                if (s.getCommentNum() == 0) {
                    mHeadNoComment.setVisibility(View.VISIBLE);
                } else {
                    mHeadNoComment.setVisibility(View.GONE);
                }


                mCommentCount = s.getCommentNum();

                mComments.addAll(s.getCommentsList());
                mCommentsAdapter = new DetailCommentAdapter(LostDetailActivity.this, mComments);
                mCommentList.setAdapter(mCommentsAdapter);

                if (TextUtils.isEmpty(s.getPhotoName())) {
                    mHeadFlashView.setVisibility(View.GONE);
                } else {
                    mHeadFlashView.setImageUris(splitImageUrls(s.getPhotoName()));
                    mHeadFlashView.setOnPageClickListener(new FlashViewListener() {
                        @Override
                        public void onClick(int position) {
                            //TODO 查看大图
                        }
                    });
                }
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                Toast.makeText(LostDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onSendCommentButtonClick() {
        String content = mCommentEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            return;
        }

        final int replyCommentId;
        final String replyContent;
        if (mReplyComment != null) {
            replyCommentId = mReplyComment.getCommentId();
            replyContent = getResources().getString(R.string.replyto, mReplyComment.getUserName()) + content;
        } else {
            replyCommentId = 0;
            replyContent = content;
        }

        LostDetailAction.addComment(mId, replyContent, replyCommentId, new HttpCallback() {
            @Override
            public void onResponse(BaseRequest request, Object data) {
                //构造Comment
                Comment comment = new Comment();

                UserBean self = UserProfile.getUser();
                if (self != null) {
                    comment.setAvatar(self.getPhotoName());
                    comment.setUserName(self.getUserName());
                } else {
                    comment.setUserName("匿名用户");
                }

                comment.setCommentId(replyCommentId);
                comment.setContent(replyContent);
                comment.setCreateTime(new Date().toString());
                comment.setUserId(self.getUserId());

                //刷新列表，并跳转至最新评论
                mComments.add(0, comment);
                mCommentsAdapter.notifyDataSetChanged();
                mCommentList.setSelection(1);

                //修改评论数
                mCommentCount += 1;
                mHeadCommentNumber.setText(getString(R.string.comment_num, mCommentCount));

                //重置评论数据
                mReplyComment = null;

                //重置评论相关UI
                mCommentEditText.setText("");
                mCommentEditText.setHint("");
                mHeadNoComment.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(BaseRequest request, Exception e, int code, String message) {
                Toast.makeText(LostDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static List<String> splitImageUrls(String photoName) {
        List<String> imageUrlList = new ArrayList<>();
        String[] imageUrlArray = photoName.split(",");

        for (String imageUrl : imageUrlArray) {
            imageUrlList.add(imageUrl);
        }

        return imageUrlList;
    }
}
