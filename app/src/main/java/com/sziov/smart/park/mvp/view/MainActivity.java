package com.sziov.smart.park.mvp.view;

import android.os.Bundle;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sziov.smart.park.R;
import com.sziov.smart.park.bean.BigSwitchScreenInfoResp;
import com.sziov.smart.park.bean.CameraDtoList;
import com.sziov.smart.park.mvp.base.BaseMvpActivity;
import com.sziov.smart.park.mvp.base.BaseResponse;
import com.sziov.smart.park.mvp.contract.MainContract;
import com.sziov.smart.park.mvp.presenter.MainPresenter;
import com.sziov.smart.park.utils.FastJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainContract.View, MainContract.Presenter> implements MainContract.View {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.result)
    TextView result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.getBigSwitchScreenList(MainActivity.this);
//        String s = "{\"code\":0,\"message\":\"调用成功\",\"data\":[{\"type\":0,\"parkId\":1,\"cameraDtoList\":[{\"id\":35,\"status\":1}]},{\"type\":0,\"parkId\":2,\"cameraDtoList\":[{\"id\":33,\"status\":1},{\"id\":34,\"status\":1}]},{\"type\":1,\"parkId\":null,\"cameraDtoList\":[]}]}";
        String s = "{\"code\":0,\"message\":\"调用成功\",\"data\":{\"type\":0,\"parkId\":1,\"cameraDtoList\":[{\"id\":35,\"status\":1}]}}";
//        String s = "{\"code\":0,\"message\":\"调用成功\",\"data\":null}";
        text.setText(s);
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showBigSwitchScreenList(List<BigSwitchScreenInfoResp> pageInfo) {
        Logger.i(String.valueOf(pageInfo.get(0).getParkId()));
    }

    @OnClick(R.id.parse)
    public void onViewClicked() {
        BigSwitchScreenInfoResp bigSwitchScreenInfoResp = new BigSwitchScreenInfoResp();
        bigSwitchScreenInfoResp.setParkId(1);
        bigSwitchScreenInfoResp.setType(2);
        List<CameraDtoList> cameraDtoList = new ArrayList<>();
        CameraDtoList cameraDtoList1 = new CameraDtoList();
        cameraDtoList1.setId(36);
        cameraDtoList1.setStatus(1);
        bigSwitchScreenInfoResp.setCameraDtoList(cameraDtoList);
        text.setText(FastJsonUtil.toJSONString(bigSwitchScreenInfoResp));
        String s = FastJsonUtil.toJSONString(bigSwitchScreenInfoResp);
        BigSwitchScreenInfoResp b = FastJsonUtil.parseResult(s,BigSwitchScreenInfoResp.class);
        result.setText(String.valueOf(b.getParkId()));
    }
}
