package com.example.coco.teademo.ui.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coco.teademo.R;
import com.example.coco.teademo.base.BaseFragment;
import com.example.coco.teademo.bean.UserInfoBean;
import com.example.coco.teademo.constant.Constant;
import com.example.coco.teademo.ui.activity.LoginActivity;
import com.example.coco.teademo.ui.activity.MainActivity;
import com.example.coco.teademo.ui.contrac.MineContarct;
import com.example.coco.teademo.ui.contrac.MinePresenterImpl;
import com.example.coco.teademo.widget.MyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.coco.teademo.R.id.iv_avatar;

/**
 * Created by coco on 2017/8/28.
 * 我的界面
 */

public class MineFragment extends BaseFragment implements MineContarct.MineView, View.OnClickListener {


    private MineContarct.MinePresenter presenter;
    private MainActivity activity;
    private FrameLayout fl_icon;
    private ImageView mImg_avatar;
    private TextView mTv_name;
    private MyView order, huiyuanka, kefu, ruzhu, shoucang, youhuiquan;
    private View view;

    @Override
    protected void initDate() {
        //在view创建完成后调用
        presenter = new MinePresenterImpl(this);
        //隐藏actionbar
        presenter.hide();
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        //注册eventbus
        EventBus.getDefault().register(this);

        //界面引入
        view = inflater.inflate(R.layout.mine_fragment, null, false);
        activity = (MainActivity) getActivity();
        fl_icon = (FrameLayout) view.findViewById(R.id.fl_icon);
        mImg_avatar = (ImageView) view.findViewById(iv_avatar);
        mTv_name = (TextView) view.findViewById(R.id.tv_name);

        order = (MyView) view.findViewById(R.id.item_dingdan);
        huiyuanka = (MyView) view.findViewById(R.id.item_huiyuanka);
        kefu = (MyView) view.findViewById(R.id.item_kefu);
        ruzhu = (MyView) view.findViewById(R.id.item_ruzhu);
        shoucang = (MyView) view.findViewById(R.id.item_shoucang);
        youhuiquan = (MyView) view.findViewById(R.id.item_youhuiquan);

        //头像的监听事件
        fl_icon.setOnClickListener(this);
        return view;
    }

    //当前界面隐藏actionbar
    @Override
    public void hideActionBar() {
        ActionBar actionBar = activity.getActionBar();
        actionBar.hide();


    }

    //登陆成功的数据展示
    @Override
    public void showLoginView(UserInfoBean bean) {
        String avatar = bean.getAvatar();//头像
        String nickname = bean.getNickname();//昵称
        String phone = bean.getPhone();//电话
        String sex = bean.getSex();//性别
        String xiadanshu1 = bean.getXiadanshu();//订单
        String shoucangshanghu1 = bean.getShoucangshanghu();//收藏
        String youhuiquan1 = bean.getYouhuiquan();//优惠券
        String huiyuanka1 = bean.getHuiyuanka();//会员卡

        if (!TextUtils.isEmpty(avatar)) {
            String url = Constant.IMAGE_AVATAR + avatar;

            Glide.with(this).load(url).into(mImg_avatar);
        }
        if (!TextUtils.isEmpty(nickname)) {
            mTv_name.setText(nickname);

        }
        if (!TextUtils.isEmpty(phone)) {
            if (TextUtils.isEmpty(nickname)) {
                mTv_name.setText(phone);
            }

        }
        if (!TextUtils.isEmpty(sex)) {

        }
        if (!TextUtils.isEmpty(xiadanshu1)) {
            order.setNum(xiadanshu1);

        }
        if (!TextUtils.isEmpty(shoucangshanghu1)) {
            shoucang.setNum(shoucangshanghu1);

        }
        if (!TextUtils.isEmpty(youhuiquan1)) {
            youhuiquan.setNum(youhuiquan1);

        }
        if (!TextUtils.isEmpty(huiyuanka1)) {
            huiyuanka.setNum(huiyuanka1);
        }
    }

    //登陆失败
    @Override
    public void showLoginError() {
        Toast.makeText(activity, "登陆失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_icon:
                if ("未登录".equals("未登录")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEvent(UserInfoBean bean) {
        showLoginView(bean);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //eventbus的反注册
        EventBus.getDefault().unregister(this);
    }
}
