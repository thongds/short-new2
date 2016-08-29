package icetea.com.hdvietplayer.player.text;

import com.google.android.exoplayer.text.Subtitle;
import com.google.android.exoplayer.text.SubtitleParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by icetea on 5/2/2015.
 */
public class SrtParser implements SubtitleParser {
    @Override
    public boolean canParse(String mimeType) {
        return mimeType.equals("/srt");
    }

    @Override
    public Subtitle parse(InputStream inputStream, String inputEncoding, long startTimeUs) throws IOException {
        return null;
    }
}
