package com.example.androidremark.ui.textview.span;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.XSpanUtils;

public class SpanActivity extends BaseActivity {


    int lineHeight;
    float textSize;

    float density;
    TextView tvAboutSpan;
    TextView tvAboutAnimRainbow;
    private XSpanUtils xSpanUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span);
        initView();
    }

    public void initView() {
        xSpanUtils = new XSpanUtils(this);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
//                ToastUtils.showShort("事件触发了");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        tvAboutSpan = (TextView) findViewById(R.id.tv_about_span);
        tvAboutAnimRainbow = (TextView) findViewById(R.id.tv_about_anim_span);

        // 响应点击事件的话必须设置以下属性
        tvAboutSpan.setMovementMethod(LinkMovementMethod.getInstance());
        // 去掉点击事件后的高亮
        tvAboutSpan.setHighlightColor(ContextCompat.getColor(this, android.R.color.transparent));
        lineHeight = tvAboutSpan.getLineHeight();
        textSize = tvAboutSpan.getTextSize();
        density = getResources().getDisplayMetrics().density;

        tvAboutSpan.setText(xSpanUtils.append("金额: ").append("600").setForegroundColor(Color.GRAY)
                .appendLine("元").setFontSize(25).setForegroundColor(Color.RED)
                .create());

//        tvAboutSpan.setText(new XSpanUtils(this)
//                .appendLine("XSpanUtils").setBackgroundColor(Color.LTGRAY).setBold().setForegroundColor(Color.YELLOW).setAlign(Layout.Alignment.ALIGN_CENTER)
//                .appendLine("前景色").setForegroundColor(Color.GREEN)
//                .appendLine("背景色").setBackgroundColor(Color.LTGRAY)
//                .appendLine("行高顶部对齐").setLineHeight(2 * lineHeight, XSpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
//                .appendLine("行高居中对齐").setLineHeight(2 * lineHeight, XSpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
//                .appendLine("行高底部对齐").setLineHeight(2 * lineHeight, XSpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
//                .appendLine("测试段落缩，首行缩进两字，其他行不缩进").setLeadingMargin((int) textSize * 2, 10).setBackgroundColor(Color.GREEN)
//                .appendLine("测试引用，后面的字是为了凑到两行的效果").setQuoteColor(Color.GREEN, 10, 10).setBackgroundColor(Color.LTGRAY)
//                .appendLine("测试列表项，后面的字是为了凑到两行的效果").setBullet(Color.GREEN, 20, 10).setBackgroundColor(Color.LTGRAY).setBackgroundColor(Color.GREEN)
//                .appendLine("32dp字体").setFontSize(32, true)
//                .appendLine("2倍字体").setFontProportion(2)
//                .appendLine("横向2倍字体").setFontXProportion(1.5f)
//                .appendLine("删除线").setStrikethrough()
//                .appendLine("下划线").setUnderline()
//                .append("测试").appendLine("上标").setSuperscript()
//                .append("测试").appendLine("下标").setSubscript()
//                .appendLine("粗体").setBold()
//                .appendLine("斜体").setItalic()
//                .appendLine("粗斜体").setBoldItalic()
//                .appendLine("monospace字体").setFontFamily("monospace")
//               // .appendLine("自定义字体").setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dnmbhs.ttf"))
//                .appendLine("相反对齐").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
//                .appendLine("居中对齐").setAlign(Layout.Alignment.ALIGN_CENTER)
//                .appendLine("正常对齐").setAlign(Layout.Alignment.ALIGN_NORMAL)
//                .append("测试").appendLine("点击事件").setClickSpan(clickableSpan)
//                .append("测试").appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
//                .append("测试").appendLine("模糊").setBlur(3, BlurMaskFilter.Blur.NORMAL)
//                .appendLine("颜色渐变").setShader(new LinearGradient(0, 0,
//                        64 * density * 4, 0,
//                        getResources().getIntArray(R.array.rainbow),
//                        null,
//                        Shader.TileMode.REPEAT)).setFontSize(64, true)
//                .appendLine("阴影效果").setFontSize(64, true).setBackgroundColor(Color.BLACK).setShadow(24, 8, 8, Color.WHITE)
//                .append("测试空格").appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN).appendSpace(100).appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN)
//                .create());
    }

    @Override
    protected void onDestroy() {
        xSpanUtils = null;
        super.onDestroy();
    }
}
