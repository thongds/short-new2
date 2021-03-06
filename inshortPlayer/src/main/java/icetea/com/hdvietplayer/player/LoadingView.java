package icetea.com.hdvietplayer.player;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import icetea.com.hdvietplayer.R;

/**
 * Created by icetea on 4/17/2015.
 */
public class LoadingView extends RelativeLayout implements View.OnClickListener {

    public static final int STYLE_WHITE = 0;
    public static final int STYLE_DARK = 1;

    public interface Callback {
        void onReloadPress();
    }


    private TextView textView;
    private ProgressBar progressBar;
    private int style;
    private Callback callback = new Callback() {
        @Override
        public void onReloadPress() {

        }
    };

    public LoadingView(Context context) {
        super(context);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        textView = new TextView(getContext());
        textView.setVisibility(INVISIBLE);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_refresh);
        textView.setText(getContext().getResources().getString(R.string.reload_message));
        progressBar = new ProgressBar(getContext());
        textView.setOnClickListener(this);
        textView.setGravity(Gravity.CENTER);
        LayoutParams tvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams pbParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        pbParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(textView, tvParams);
        addView(progressBar, pbParams);
    }

    public void showProgressBar(boolean show){
        if(show){
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void setIcon(int icon){
        if(icon > 0)
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, icon);
        else if(style == STYLE_WHITE){
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_refresh_white);
        }
        else{
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_refresh);
        }
    }

    @Override
    public void onClick(View v) {
        showProgressBar(true);
        callback.onReloadPress();
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public void setText(String text){
        textView.setText(text);
    }

    public void setProgressBarStyle(int style){
        progressBar = new ProgressBar(getContext(), null, style);
    }

    public void setStyle(int style){
        this.style = style;
        if(style == STYLE_WHITE){
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_refresh_white);
        }
        else{
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_action_refresh);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textView.setEnabled(enabled);
    }
}
