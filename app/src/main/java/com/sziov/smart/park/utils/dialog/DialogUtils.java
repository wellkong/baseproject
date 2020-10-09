package com.sziov.smart.park.utils.dialog;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.sziov.smart.park.R;


/**
 * @Author: willkong
 * @CreateDate: 2020/5/15 16:34
 * @Description: dialog工具类
 */
public class DialogUtils {
    private CustomDialog dialog;
    private FragmentActivity activity;

    public DialogUtils(FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * @param content         内容
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    public void showDialog(String title, String content, String confirm, String cancel,
                           View.OnClickListener confirmListener,
                           View.OnClickListener cancelListener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (dialog == null) {
            dialog = new CustomDialog.Builder(activity)
                    .setTheme(R.style.dialog)
                    .setTitle(title)
                    .setContent(content)
                    .addConfirmClickListener(confirm, confirmListener)
                    .addCancelClickListener(cancel, cancelListener)
                    .build();
            dialog.show();
        } else {
            if (!dialog.getContent().equals(content) || !dialog.getTitle().equals(title)) {
                dialog = new CustomDialog.Builder(activity)
                        .setTheme(R.style.dialog)
                        .setTitle(title)
                        .setContent(content)
                        .addConfirmClickListener(confirm, confirmListener)
                        .addCancelClickListener(cancel, cancelListener)
                        .build();
            }
            dialog.show();
        }
    }

    /**
     * @param content         内容
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    public void showDialog(String content, String confirm, String cancel,
                           View.OnClickListener confirmListener,
                           View.OnClickListener cancelListener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (dialog == null) {
            dialog = new CustomDialog.Builder(activity)
                    .setTheme(R.style.dialog)
                    .setContent(content)
                    .addConfirmClickListener(confirm, confirmListener)
                    .addCancelClickListener(cancel, cancelListener)
                    .build();
            dialog.show();
        } else {
            if (!dialog.getContent().equals(content)) {
                dialog = new CustomDialog.Builder(activity)
                        .setTheme(R.style.dialog)
                        .setContent(content)
                        .addConfirmClickListener(confirm, confirmListener)
                        .addCancelClickListener(cancel, cancelListener)
                        .build();
            }
            dialog.show();
        }
    }

    /**
     * 隐藏dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
