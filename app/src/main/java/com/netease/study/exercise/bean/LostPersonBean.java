package com.netease.study.exercise.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * 走失人基本信息及相应关键信息
 * 该实体类对应后台：走失信息上传/修改 ； 走失信息列表查看；走失信息详情； 等三个接口
 */
public class LostPersonBean implements Serializable {
    private static final long serialVersionUID = -1478234807192645610L;

    private Integer mId; //走失信息编号（为空：进行上传    不为空：进行修改）
    private Integer mUserId;//用户编号
    private String mLostName;//失踪人姓名
    private String mLostTime;//失踪时间
    private String mLostPlace;//失踪地点
    private String mLostHeight;//失踪时身高
    private String mRemark;//备注
    private String mFaceBody;//面部及肢体描述
    private String mClothes;//衣着描述
    private String mOralState; //口音、精神状态描述
    private Integer mBonus;//悬赏金额
    private String mBirth; //出生年月
    private String mContact; //联系方式
    private Integer mLostSex; //走失人性别 0:男  1:女
    private double mLongitude; // 经度
    private double mLatitude; // 纬度
    private IdentityHashMap<String, File> mFiles = new IdentityHashMap<>(); //图片
    private String mAddBackupList; //上传文件名，以逗号分隔
    private String mDeleteFileList; //删除文件名，以逗号分隔
    private Integer mPublisherUserId;//发布走失信息的用户编号(在接口里对应到服务端的key值为userId）
    private String mPublisherUserName;//发布走失信息的用户名(在接口里对应到服务端的key值为userName）
    private String mCreateTime;//走失信息的发布时间
    private String mModifyTime;//该走失信息修改时间
    private Integer mCommentNum;//讨论条数
    private Integer mPhotoNum;//上传的照片数
    private Integer mStatus;//0：未找到  1：已找到
    private List<CommentBean> mCommentBeans;//该走失信息对应的评论列表
    private List<String> mBackup;

    public List<String> getBackup() {
        return mBackup;
    }

    public String getFaceBody() {
        return mFaceBody;
    }

    public void setFaceBody(String faceBody) {
        this.mFaceBody = faceBody;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        this.mUserId = userId;
    }

    public String getLostName() {
        return mLostName;
    }

    public void setLostName(String lostName) {
        this.mLostName = lostName;
    }


    public String getLostPlace() {
        return mLostPlace;
    }

    public void setLostPlace(String lostPlace) {
        this.mLostPlace = lostPlace;
    }

    public String getLostHeight() {
        return mLostHeight;
    }

    public void setLostHeight(String lostHeight) {
        this.mLostHeight = lostHeight;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        this.mRemark = remark;
    }


    public String getClothes() {
        return mClothes;
    }

    public void setClothes(String clothes) {
        this.mClothes = clothes;
    }

    public String getOralState() {
        return mOralState;
    }

    public void setOralState(String oralState) {
        this.mOralState = oralState;
    }

    public Integer getBonus() {
        return mBonus;
    }

    public void setBonus(Integer bonus) {
        this.mBonus = bonus;
    }


    public String getLostTime() {
        return mLostTime;
    }

    public void setLostTime(String lostTime) {
        this.mLostTime = lostTime;
    }

    public String getBirth() {
        return mBirth;
    }

    public void setBirth(String birth) {
        this.mBirth = birth;
    }

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        this.mCreateTime = createTime;
    }

    public String getModifyTime() {
        return mModifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.mModifyTime = modifyTime;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }

    public Integer getLostSex() {
        return mLostSex;
    }

    public void setLostSex(Integer lostSex) {
        this.mLostSex = lostSex;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public IdentityHashMap<String, File> getFile() {
        return mFiles;
    }

    public void addFile(String url) {
        //this.file = file;
        if (url.startsWith("http://")) {
            if (mBackup == null) mBackup = new ArrayList<>();
            int start = url.indexOf("?");
            if (start != -1) {
                url = url.substring(0, start);
            }
            mBackup.add(url);

        } else {
            mFiles.put(new String("file"), new File(url));
        }

    }

    public String getAddBackupList() {
        return mAddBackupList;
    }

    public void setAddBackupList(String addBackupList) {
        this.mAddBackupList = addBackupList;
    }

    public String getDeleteFileList() {
        return mDeleteFileList;
    }

    public void setDeleteFileList(String deleteFileList) {
        this.mDeleteFileList = deleteFileList;
    }

    public Integer getPublisher_userId() {
        return mPublisherUserId;
    }

    public void setPublisher_userId(Integer publisherUserId) {
        this.mPublisherUserId = publisherUserId;
    }

    public String getPublisher_userName() {
        return mPublisherUserName;
    }

    public void setPublisher_userName(String publisherUserName) {
        this.mPublisherUserName = publisherUserName;
    }

    public Integer getCommentNum() {
        return mCommentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.mCommentNum = commentNum;
    }

    public Integer getPhotoNum() {
        return mPhotoNum;
    }

    public void setPhotoNum(Integer photoNum) {
        this.mPhotoNum = photoNum;
    }

    public Integer getStatus() {
        return mStatus;
    }

    public void setStatus(Integer status) {
        this.mStatus = status;
    }

    public List<CommentBean> getCommentBeans() {
        return mCommentBeans;
    }

    public void setCommentBeans(List<CommentBean> commentBeans) {
        this.mCommentBeans = commentBeans;
    }

    public void clear() {
        if (mBackup != null) mBackup.clear();
        if (mFiles != null) mFiles.clear();
    }
}
