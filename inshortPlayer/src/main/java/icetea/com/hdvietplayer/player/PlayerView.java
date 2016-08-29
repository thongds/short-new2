package icetea.com.hdvietplayer.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.CaptioningManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.metadata.TxxxMetadata;
import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.SubtitleView;

import java.util.Map;

import icetea.com.hdvietplayer.R;

/**
 * Created by icetea on 5/5/2015. Do not modify it.
 */

public class PlayerView extends FrameLayout implements TextureView.SurfaceTextureListener,
        DemoPlayer.Listener, DemoPlayer.TextListener, DemoPlayer.Id3MetadataListener, VideoControllerView.Callback {

    private String accessKey;
    private Surface surface;
    private VideoSurfaceView surfaceView;
    private boolean isSurfaceReady;

    public ImageView getIvCover() {
        return ivCover;
    }

    public View getCoverView() {
        return coverView;
    }

    public View getBtnPlay() {
        return btnPlay;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surface = new Surface(surface);
        if(player != null){
            player.setSurface(this.surface);
        }
        isSurfaceReady = true;
        if(callback != null){
            callback.onSurfaceReady();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (player != null) {
            player.blockingClearSurface();
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public boolean isSurfaceReady() {
        return isSurfaceReady;
    }

    @Override
    public void onButtonClick(VideoControllerView.ControlButton button) {
        callback.onButtonClick(button);
    }

    public interface Callback{
        void onStateChange(boolean playWhenReady,int state);
        void onButtonClick(VideoControllerView.ControlButton button);
        void onRequestLinkPlay(int episode, int movieId);
        void onSurfaceReady();
    }

    private static final String TAG = "PlayerActivity";
    private static final float CAPTION_LINE_HEIGHT_RATIO = 0.0533f;

    private View shutterView;
    private SubtitleView subtitleView;
    private EventLogger eventLogger;
    private ImageView ivCover;
    private View coverView;
    private View btnPlay;
    private VideoControllerView controllerView;
    private DemoPlayer player;
    private boolean playerNeedsPrepare;
    private int contentType;
    private String contentId;
    private long playerPosition;
    private LoadingView playerLoading;
    private Callback callback;

    private PlayerData playerData;

    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        callback = new Callback() {

            @Override
            public void onStateChange(boolean playWhenReady,int state) {

            }

            @Override
            public void onButtonClick(VideoControllerView.ControlButton button) {

            }

            @Override
            public void onRequestLinkPlay(int episode, int movieId) {

            }

            @Override
            public void onSurfaceReady() {

            }
        };
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_view, this, true);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        coverView = findViewById(R.id.fl_cover);
        btnPlay = findViewById(R.id.iv_play);
        shutterView = findViewById(R.id.shutter);
        surfaceView = (VideoSurfaceView) findViewById(R.id.surface_view);
        surfaceView.setSurfaceTextureListener(this);
        surfaceView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleControlsVisibility();
            }
        });
        subtitleView = (SubtitleView) findViewById(R.id.subtitles);
        controllerView = new VideoControllerView(getContext());
        contentId = "applemasterplaylist";
        contentType = 3;
        playerLoading = (LoadingView) findViewById(R.id.player_loading_view);
        playerLoading.setStyle(LoadingView.STYLE_WHITE);
        playerLoading.setCallback(new LoadingView.Callback() {
            @Override
            public void onReloadPress() {
                if (playerData != null)
                    callback.onRequestLinkPlay(controllerView.getCurrentEpi(), playerData.getMovieId());
                else {
                    callback.onRequestLinkPlay(1, -1);
                }
            }
        });
        controllerView.setAnchorView(this);
        controllerView.setCallback(this);
    }

    public void setData(PlayerData playerData){
        this.playerData = playerData;
        accessKey = getControllerView().getCurrentEpi() + "_" + playerData.getMovieId();
        controllerView.setData(playerData);
    }

    public PlayerData getData(){
        return this.playerData;
    }

    @Override
    protected void onAttachedToWindow() {
        Log.e("Player height", String.valueOf(getHeight()));
        super.onAttachedToWindow();
    }

    @Override
    public void onId3Metadata(Map<String, Object> metadata) {
        for (int i = 0; i < metadata.size(); i++) {
            if (metadata.containsKey(TxxxMetadata.TYPE)) {
                TxxxMetadata txxxMetadata = (TxxxMetadata) metadata.get(TxxxMetadata.TYPE);
                Log.i(TAG, String.format("ID3 TimedMetadata: description=%s, value=%s",
                        txxxMetadata.description, txxxMetadata.value));
            }
        }
    }

    public void savePlayerPosition(long position) {
        Log.e("savePlayerPosition", "getPlayer" + getPlayer());
        if(getPlayer() != null) {
            if(position == 0){
                position = player.getCurrentPosition();
            }
        }
        Preferences.writeLong(getContext(), accessKey, position);
    }

    public void resetPlayerPosition(){
        if(getPlayer() != null) {
            Preferences.writeLong(getContext(), accessKey, 0);
        }
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        callback.onStateChange(playWhenReady, playbackState);
        if (playbackState == ExoPlayer.STATE_ENDED) {
            showControls();
        }
        String text = "playWhenReady=" + playWhenReady + ", playbackState=";
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                text += "buffering";
                playerLoading.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_ENDED:
                text += "ended";
                playerLoading.setVisibility(View.GONE);
                if (playerData.getNumberEpisode() > 0 && controllerView.getCurrentEpi() < playerData.getNumberEpisode()) {
                    int epi = controllerView.getCurrentEpi() + 1;
                    callback.onRequestLinkPlay(epi, playerData.getMovieId());
                } else {
                    resetPlayerPosition();
                    releasePlayer();
                    showRefresh(getContext().getString(R.string.watch_it_again), -1);
                }
                break;
            case ExoPlayer.STATE_IDLE:
                playerLoading.setVisibility(View.GONE);
                text += "idle";
                savePlayerPosition(0);
                showRefresh(getContext().getResources().getString(R.string.reload_message), -1);
                break;
            case ExoPlayer.STATE_PREPARING:
                playerLoading.setVisibility(View.VISIBLE);
                text += "preparing";
                break;
            case ExoPlayer.STATE_READY:
                playerLoading.setVisibility(View.GONE);
                try {
                    controllerView.hide();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                text += "ready";
                break;
            default:
                text += "unknown";
                break;
        }
        Log.i("stateplay", text);
        //Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
        System.out.print(text);
    }

    @Override
    public void onErrorPlayer(Exception e) {
        playerNeedsPrepare = true;
        showControls();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthHeightRatio) {
        shutterView.setVisibility(View.GONE);
        surfaceView.setVideoWidthHeightRatio(
                height == 0 ? 1 : (width * pixelWidthHeightRatio) / height);
    }

    @Override
    public void onText(String text) {
        if (TextUtils.isEmpty(text)) {
            subtitleView.setVisibility(View.INVISIBLE);
        } else {
            subtitleView.setVisibility(View.VISIBLE);
            subtitleView.setText(Html.fromHtml(text));
        }
    }

    private void showControls() {
        controllerView.show(3000);
    }

    public void preparePlayer() {
        if (player == null) {
            if(playerData!=null){
                String keyAccess = String.valueOf(controllerView.getCurrentEpi()) + "_" + playerData.getMovieId();
                Log.e("keyAcess",keyAccess);
                playerPosition = Preferences.readLong(getContext(), keyAccess, 0);
                Log.e("playerPosition","position : "+playerPosition);
            }
            player = new DemoPlayer(getRendererBuilder());
            player.addListener(this);
            player.setTextListener(this);
            player.setMetadataListener(this);
            player.seekTo(playerPosition);
            playerNeedsPrepare = true;
            controllerView.setMediaPlayer(player.getPlayerControl());
            controllerView.setEnabled(true);
            eventLogger = new EventLogger();
            eventLogger.startSession();
            player.addListener(eventLogger);
            player.setInfoListener(eventLogger);
            player.setInternalErrorListener(eventLogger);
            if(surface != null){
                player.setSurface(surface);
            }
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
        }
        player.setPlayWhenReady(true);
    }

    public void releasePlayer() {
        if (player != null) {
            try {
                playerPosition = 0;
                player.release();
                player = null;
                eventLogger.endSession();
                eventLogger = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private DemoPlayer.RendererBuilder getRendererBuilder() {
        String userAgent = DemoUtil.getUserAgent(getContext());
        switch (contentType) {
            case DemoUtil.TYPE_SS:
                return new SmoothStreamingRendererBuilder(userAgent, playerData.getContentUri().toString(), contentId,
                        new SmoothStreamingTestMediaDrmCallback(), null);
            case DemoUtil.TYPE_DASH:
                return new DashRendererBuilder(userAgent, playerData.getContentUri().toString(), contentId,
                        new WidevineTestMediaDrmCallback(contentId), null);
            case DemoUtil.TYPE_HLS:
                return new HlsRendererBuilder(userAgent, playerData.getContentUri().toString(), contentId);
            default:
                return new DefaultRendererBuilder(getContext(), playerData.getContentUri(), null);
        }
    }

    public void showRefresh(String message, int icon) {
        controllerView.hide();
        playerLoading.setVisibility(View.VISIBLE);
        playerLoading.showProgressBar(false);
        playerLoading.setIcon(icon);
        playerLoading.setText(message);
    }

    public void toggleControlsVisibility() {
        if(player != null) {
            if (controllerView.isShowing()) {
                controllerView.hide();
            } else {
                showControls();
            }
        }
    }

    public LoadingView getPlayerLoading(){
        return playerLoading;
    }

    //TODO : config sub style
    public void configureSubtitleView(Context context,int colorCode,String fontPath,float size) {
        Log.e(TAG,"size sub "+size);
        AssetManager assetManager = getResources().getAssets();
        CaptionStyleCompat captionStyle = new CaptionStyleCompat(colorCode, Color.TRANSPARENT,
                Color.TRANSPARENT, CaptionStyleCompat.EDGE_TYPE_OUTLINE, Color.BLACK, Typeface.createFromAsset(assetManager,fontPath));
        float captionTextSize = getCaptionFontSize();
//        if (Util.SDK_INT >= 19) {
//            captionStyle = getUserCaptionStyleV19();
//            captionTextSize *= getUserCaptionFontScaleV19();
//        } else {
//            captionStyle = CaptionStyleCompat.DEFAULT;
//        }
        subtitleView.setStyle(captionStyle);
        subtitleView.setTextSize(size);
//        if(isTablet(context)) {
//            subtitleView.setTextSize(size);
//        }else{
//            subtitleView.setTextSize(size*3);
//        }
    }

    private float getCaptionFontSize() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        return Math.max(getResources().getDimension(R.dimen.subtitle_minimum_font_size),
                CAPTION_LINE_HEIGHT_RATIO * Math.min(displaySize.x, displaySize.y));
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getContext().getSystemService(Context.CAPTIONING_SERVICE);
        return captioningManager.getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getContext().getSystemService(Context.CAPTIONING_SERVICE);
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public VideoControllerView getControllerView(){
        return controllerView;
    }

    public DemoPlayer getPlayer(){
        return player;
    }

    public void showShutterView(boolean show){
        if(show){
            shutterView.setVisibility(View.VISIBLE);
        }
        else{
            shutterView.setVisibility(View.GONE);
        }
    }

    public void setEnableControllerView(boolean enable){
        controllerView.hide();
        controllerView.setEnabled(enable);
    }
    public  boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
