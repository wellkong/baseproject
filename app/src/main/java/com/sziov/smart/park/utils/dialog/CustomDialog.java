package com.sziov.smart.park.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sziov.smart.park.R;
import com.sziov.smart.park.ui.SelectorFactory;
import com.sziov.smart.park.utils.DensityUtil;


/**
 * Created by willkong on 2017/4/12.
 */

public class CustomDialog extends Dialog {
    //   dialog高度
    private int height;
    //  dialog宽度
    private int width;
    //  点击外部是否可以取消
    private boolean cancelTouchOutside;
    //  弹窗布局View
    private View dialogView;
    //标题
    private String title;
    //内容
    private String content;


    private boolean cancelable;

    private static String dialog_cancel_normal = "#FFFFFF";
    private static String dialog_cancel_focused = "#EAEAEA";
    private static String dialog_confirm_normal = "#00CC99";
    private static String dialog_confirm_pressed = "#8800CC99";

    private CustomDialog(Builder builder) {
        super(builder.context);
        height = builder.height;
        width = builder.width;
        cancelTouchOutside = builder.cancelTouchOutside;
        dialogView = builder.mDialogView;
    }


    private CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        title = builder.title;
        content = builder.content;
        height = builder.height;
        width = builder.width;
        cancelable = builder.cancelable;
        cancelTouchOutside = builder.cancelTouchOutside;
        dialogView = builder.mDialogView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(dialogView);
        setCanceledOnTouchOutside(cancelTouchOutside);
        setCancelable(cancelable);
        Window win = getWindow();
        if (win != null) {
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.height = height;
            lp.width = width;
            win.setAttributes(lp);
        }
    }

    public static final class Builder {

        private Context context;
        private int height, width;
        private String title, content;
        private boolean cancelTouchOutside;
        private View mDialogView;
        private int resStyle = -1;
        private boolean cancelable = true;

        public Builder(Context context) {
            this.context = context;
            mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
            //  计算dialog宽高
            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mDialogView.measure(measureSpec, measureSpec);
            height = mDialogView.getMeasuredHeight();
            width = mDialogView.getMeasuredWidth();
            Button btn_cancel = mDialogView.findViewById(R.id.cancel);
            btn_cancel.setBackground(SelectorFactory.newShapeSelector()
                    .setDefaultBgColor(Color.parseColor(dialog_cancel_normal))
                    .setPressedBgColor(Color.parseColor(dialog_cancel_focused))
                    .setFocusedBgColor(Color.parseColor(dialog_cancel_focused))
                    .setCornerRadius(10)
                    .create());
            Button btn_confirm = mDialogView.findViewById(R.id.confirm);
            btn_confirm.setBackground(SelectorFactory.newShapeSelector()
                    .setDefaultBgColor(Color.parseColor(dialog_confirm_normal))
                    .setPressedBgColor(Color.parseColor(dialog_confirm_pressed))
                    .setCornerRadius(10)
                    .create());
        }

        /**
         * @param dialogView 关联dialog布局文件的View
         * @return
         */
        public Builder setDialogLayout(View dialogView) {
            this.mDialogView = dialogView;
            return this;
        }

        public Builder setHeightPx(int val) {
            height = val;
            return this;
        }

        public Builder setWidthPx(int val) {
            width = val;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setHeightDp(int val) {
            height = DensityUtil.dip2px(context, val);
            return this;
        }

        public Builder setWidthDp(int val) {
            width = DensityUtil.dip2px(context, val);
            return this;
        }

        /**
         * 设置主题
         *
         * @param resStyle
         * @return
         */
        public Builder setTheme(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        /**
         * 设置点击dialog外部是否取消dialog
         *
         * @param val
         * @return
         */
        public Builder cancelTouchOutside(boolean val) {
            cancelTouchOutside = val;
            return this;
        }

        /**
         * 给dialog中的view添加点击事件
         *
         * @param viewResId 被点击view的id
         * @param listener
         * @return
         */
        public Builder addViewOnclick(int viewResId, View.OnClickListener listener) {
            mDialogView.findViewById(viewResId).setOnClickListener(listener);
            return this;
        }

        /**
         * 确定键监听
         *
         * @param confirm
         * @param listener
         * @return
         */
        public Builder addConfirmClickListener(String confirm, View.OnClickListener listener) {
            Button btn_confirm = mDialogView.findViewById(R.id.confirm);
            btn_confirm.setText(confirm);
            btn_confirm.setOnClickListener(listener);
            return this;
        }

        /**
         * 取消键监听
         *
         * @param cancel
         * @param listener
         * @return
         */
        public Builder addCancelClickListener(String cancel, View.OnClickListener listener) {
            Button btn_cancel = mDialogView.findViewById(R.id.cancel);
            btn_cancel.setText(cancel);
            btn_cancel.setOnClickListener(listener);
            return this;
        }

        /**
         * 设置内容
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            TextView tvTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_content);
            tvTitle.setText(content);
            this.content = content;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            TextView tvTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_title);
            tvTitle.setText(title);
            this.title = title;
            return this;
        }

        /**
         * 设置标题
         *
         * @param color
         * @return
         */
        public Builder setTitleColor(int color) {
            TextView tvTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_title);
            tvTitle.setTextColor(color);
            return this;
        }

        /**
         * 设置取消键颜色
         *
         * @param color 颜色
         * @return
         */
        public Builder setCancelColor(int color) {
            Button btn_cancel = mDialogView.findViewById(R.id.cancel);
            btn_cancel.setTextColor(color);
            return this;
        }

        /**
         * 设置确定键颜色
         *
         * @param color 颜色
         * @return
         */
        public Builder setConfirmColor(int color) {
            Button btn_confirm = mDialogView.findViewById(R.id.confirm);
            btn_confirm.setTextColor(color);
            return this;
        }

        public CustomDialog build() {
            if (resStyle != -1) {
                return new CustomDialog(this, resStyle);
            } else {
                return new CustomDialog(this);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
