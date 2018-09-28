package com.example.administrator.boshide2.Modular.View.KeyBoard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.View;

import android.widget.EditText;

import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;

public class KeyboardUtil {
    private Context ctx;
    private KeyboardView keyboardView;
    private Keyboard k1;// 省份简称键盘
    private Keyboard k2;// 数字字母键盘
    private String provinceShort[];
    private String letterAndDigit[];
    private EditText edits[];
    private int currentEditText = 0;//默认当前光标在第一个EditText
    private int shanchu = 1;  // 1,表示省份界面，0，表示数字和字母
    private int tianjia = 1;
    private int biao = 1;
    private OnClickYes onclikc;
    View view;
    public KeyboardUtil(Context ctx, EditText edits[], View view,  int currentEditText) {
        this.ctx = ctx;
        this.view = view;
        this.edits = edits;
        this.currentEditText=currentEditText;
        k1 = new Keyboard(ctx, R.xml.province_short_keyboard);
        k2 = new Keyboard(ctx, R.xml.lettersanddigit_keyboard);
        keyboardView = (KeyboardView) view.findViewById(R.id.bsd_keyboard_view1);
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
//        keyboardView.setBackgroundResource(R.drawable.keyboard_bg_selector);
        //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
        keyboardView.setPreviewEnabled(true);
        //设置键盘按键监听器
        keyboardView.setOnKeyboardActionListener(listener);
        provinceShort = new String[]{"京", "津", "冀", "鲁", "晋", "蒙", "辽", "吉", "黑"
                , "沪", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘"
                , "粤", "桂", "渝", "川", "贵", "云", "藏", "陕", "甘"
                , "青", "琼", "新", "港", "澳", "台", "宁"};

        letterAndDigit = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"
                , "A", "S", "D", "F", "G", "H", "J", "K", "L"
                , "Z", "X", "C", "V", "B", "N", "M"};
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode == 112) { // 清空
                for (int i = 0; i < 9; i++) {
                    edits[i].setText("");
                }
                currentEditText = 0;
                shanchu=1;
                keyboardView.setKeyboard(k1);
            } else if (primaryCode == 111){  // 单个删除
                if (currentEditText > 0) {
                    edits[currentEditText - 1].setText("");
                    currentEditText--;
                } else  {
                    currentEditText = 0;
                }
            } else if (primaryCode == 66) { // 回车
                String license = "";
                for (int i = 0; i < 9; i++) {
                    license += edits[i].getText().toString();
                }
                if (edits[6].getText().toString().trim().equals("")) {
                    Show.showTime((MainActivity) ctx, "请输入完整车牌");
                } else {
//                    Show.showTime((MainActivity) ctx, "车牌号为" + license);
                    if (onclikc != null) {
                        onclikc.onYesClick(license);
                    }
                }
            } else if (primaryCode == 55) {  // 返回键
                keyboardView.setKeyboard(k1);
                shanchu = 1;
            } else if (primaryCode == 123) {  //车牌切换数字键
                keyboardView.setKeyboard(k2);
                shanchu = 0;
            } else {                         //其它字符按键
                if (currentEditText < 9) {
                    if (shanchu == 1) {
                        edits[currentEditText].setText(provinceShort[primaryCode]);
                        currentEditText++;
                    } else if (shanchu == 0) {
                        edits[currentEditText].setText(letterAndDigit[primaryCode]);
                        currentEditText++;
                    }
                }
            }
        }
    };

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    public void setOnClickYes(OnClickYes onclikc) {
        this.onclikc = onclikc;
    }

    public interface OnClickYes {
        void onYesClick(String license);
    }
}
