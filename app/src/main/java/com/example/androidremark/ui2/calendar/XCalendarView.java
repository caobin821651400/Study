package com.example.androidremark.ui2.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidremark.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by caobin on 2017/9/19.
 */

public class XCalendarView extends LinearLayout {

    private ImageButton preButton;
    private ImageButton nextButton;
    private TextView tvCurDate;
    private GridView mGridView;
    private CalendarAdapter mAdapter;
    ArrayList<calendarBean> cells;
    private List<Date> markList = new ArrayList<>();
    ;
    String curMonthFormat = "yyyy-MM";

    private OnDateSelectedListener onDateSelectedListener;
    private OnMonthChangeListener onMonthChangeListener;

    private Calendar curDate = Calendar.getInstance();

    public XCalendarView(Context context) {
        super(context);
    }

    public XCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 设置需要标记的list集合
     *
     * @param list
     */
    public void setMarkList(List<Date> list) {
        markList.addAll(list);
        initCalendarCells();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XCalendarView);
        curMonthFormat = ta.getString(R.styleable.XCalendarView_curMonth);
        ta.recycle();
        findView(context);
       // initCalendarCells();
    }

    /**
     * 控件绑定
     *
     * @param context
     */
    private void findView(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendar_layout, this);

        preButton = (ImageButton) view.findViewById(R.id.ib_pre_month);
        nextButton = (ImageButton) view.findViewById(R.id.ib_next_month);
        tvCurDate = (TextView) findViewById(R.id.tv_current_date);
        mGridView = (GridView) findViewById(R.id.calendar_grid_view);

        mAdapter = new CalendarAdapter(context);
        mGridView.setAdapter(mAdapter);


        //上个月
        preButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, -1);
                initCalendarCells();
                mAdapter.setList(cells);
                if (onMonthChangeListener != null) {
                    onMonthChangeListener.onPreMonthChange(curDate.getTime());
                }
            }
        });
        //下个月
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, 1);
                initCalendarCells();
                mAdapter.setList(cells);
                if (onMonthChangeListener != null) {
                    onMonthChangeListener.onNextMonthChange(curDate.getTime());
                }

            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calendarBean bean = mAdapter.getItem(position);
                if (onDateSelectedListener != null) {
                    onDateSelectedListener.onDateSelected(bean.getDate());
                }
            }
        });
    }

    /**
     * 加载日历的每个cell
     */
    private void initCalendarCells() {
        //显示顶部日期
        SimpleDateFormat sdf = new SimpleDateFormat(curMonthFormat);
        tvCurDate.setText(sdf.format(curDate.getTime()));

        cells = new ArrayList<>();
        //克隆系统日历
        Calendar calendar = (Calendar) curDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //Calendar.DAY_OF_WEEK获取这个月的第一天是星期几，减一显示上个月的后几天
        //例如这个月的第一天是星期2，减一后应该只显示上个月的最后一天
        int preDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        //当前日历向前移动preDays个位置
        calendar.add(Calendar.DAY_OF_MONTH, -preDays);

        //添加cell
        int allCellsCount = 6 * 7;
        while (cells.size() < allCellsCount) {
            calendarBean bean = new calendarBean();
            bean.setDate(calendar.getTime());
            for (Date date : markList) {
                if (date.getYear() == calendar.getTime().getYear() &&
                        date.getMonth() == calendar.getTime().getMonth() && date.getDate() == calendar.getTime().getDate()) {
                    bean.setMark(true);
                }
            }
            cells.add(bean);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        mAdapter.setList(cells);
    }

    private static boolean sameDate(Calendar cal, Calendar selectedDate) {
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    /**
     * 日历点击回调
     *
     * @param listener
     */
    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.onDateSelectedListener = listener;
    }

    /**
     * 上下个月点击回调
     *
     * @param listener
     */
    public void setOnMonthChangeListener(OnMonthChangeListener listener) {
        this.onMonthChangeListener = listener;
    }

    /**
     * 日历点击接口
     */
    interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }

    /**
     * 月份点击接口
     */
    interface OnMonthChangeListener {
        void onPreMonthChange(Date date);

        void onNextMonthChange(Date date);
    }
}
