package com.example.administrator.boshide2.Modular.View.Time;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressLint("SimpleDateFormat")
public class TimeDialog {

	private Context context;
	private WheelMain wheelMain;
	String time;
	String riqi = "";
	QueRenXiugai queRenXiugai;

	public void setQueRenXiugai(QueRenXiugai queRenXiugai) {
		this.queRenXiugai = queRenXiugai;
	}

	public TimeDialog(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 获得选中的时间
	 * 
	 * @param strYear
	 *            间隔符号
	 * @param strMon
	 * @param strDay
	 * @param strHour
	 * @param strMins
	 * @param strSecond
	 * @return
	 */
	public String getTxtTime(String strYear, String strMon, String strDay,
			String strHour, String strMins, String strSecond) {
		return wheelMain.getTime(strYear, strMon, strDay, strHour, strMins,
				strSecond);
	}

	public View timePickerView() {
		View timepickerview = View.inflate(context, R.layout.timepicker, null);
		wheelMain = new WheelMain(timepickerview);
		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		wheelMain.setEND_YEAR(2050);// 设置最大年份
		wheelMain.initDateTimePicker(year, month, day, hour, min, second);

		return timepickerview;
	}

	/**
	 * 时间选择控件
	 * 
	 * @param dateStr
	 *            需显示的日期
	 * @return
	 */
	public View timePickerView(String dateStr) {
		View timepickerview = View.inflate(context, R.layout.timepicker, null);
		wheelMain = new WheelMain(timepickerview);
		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		wheelMain.setEND_YEAR(2050);
		// 若为空显示当前时间
		if (dateStr.equals("2"))
		{
			wheelMain.initDateTimePicker(year, month, day,  hour,-1,-1);// 传-1表示不显示
		}else  if (dateStr != null && !dateStr.equals("")) {
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// try {
			// // Date date = format.parse(dateStr);
			// // calendar.setTime(date);
			// // year = calendar.get(Calendar.YEAR);
			// // month = calendar.get(Calendar.MONTH);
			// // day = calendar.get(Calendar.DAY_OF_MONTH);
			wheelMain.initDateTimePicker(year, month, day, -1, -1, -1);// 传-1表示不显示
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
		} else {
			wheelMain.initDateTimePicker(year, month, day, hour, min, second);
		}
		return timepickerview;
	}

	/**
	 * alertDialog时间选择
	 * 
	 * @param textView
	 */
	public void timePickerAlertDialog(final TextView textView) {
		final IOSAlertDialog dialog = new IOSAlertDialog(context);
		// dialog.daxiao(0.9);
		dialog.builder();
		dialog.setTitle("选择日期");
		dialog.setView(timePickerView("2"));
		dialog.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		dialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
//				queRenXiugai.onYesClick();
				textView.getText();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");

						textView.setText(getTxtTime("-", "-", " ", ":", "", ""));
						dialog.guanbi();


			}
		});

		dialog.show();
	}

	/**
	 * alertDialog时间选择
	 * 
	 */
	public String timePickerAlertDialogkj(String name) {

		final IOSAlertDialog dialog = new IOSAlertDialog(context);
		dialog.builder();
		dialog.setTitle("开始日期");
		time = "开始日期";
		dialog.setView(timePickerView(""));
		dialog.xianshiname(name);
		dialog.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		dialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (time.equals("开始日期")) {

					time = "结束日期";
					dialog.setTitle("结束日期");
					riqi = getTxtTime("-", "-", " ", "-", "-", "") + ",";
				} else {

					riqi = riqi + getTxtTime("-", "-", " ", "-", "-", "");

					dialog.guanbi();
				}

			}
		});
		dialog.show();
		return riqi;

	}


	public interface QueRenXiugai {
		public void onYesClick();
	}
}
