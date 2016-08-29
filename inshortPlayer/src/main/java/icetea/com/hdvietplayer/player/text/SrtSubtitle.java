package icetea.com.hdvietplayer.player.text;

import com.google.android.exoplayer.text.Subtitle;
import com.google.android.exoplayer.util.Util;

import java.util.List;

/**
 * Created by icetea on 5/2/2015.
 */
public class SrtSubtitle implements Subtitle {

    List<SrtNode> srtNodes;
    private long startTimeUs;
    private long[] eventTimesUs;

    public SrtSubtitle(List<SrtNode> srtNodes){
        this.srtNodes = srtNodes;
        startTimeUs = srtNodes.get(0).getStartTime();
        int size = srtNodes.size();
        eventTimesUs = new long[size * 2];
        for(int i = 0; i < size; i++){
            eventTimesUs[i * 2] = srtNodes.get(i).getStartTime();
            eventTimesUs[i * 2 + 1] = srtNodes.get(i).getEndTime();
        }
    }

    @Override
    public long getStartTime() {
        return srtNodes.get(0).getStartTime();
    }

    @Override
    public int getNextEventTimeIndex(long timeUs) {
        int index = Util.binarySearchCeil(eventTimesUs, timeUs - startTimeUs, false, false);
        return index < eventTimesUs.length ? index : -1;
    }

    @Override
    public int getEventTimeCount() {
        return eventTimesUs.length;
    }

    @Override
    public long getEventTime(int index) {
        return eventTimesUs[index] + startTimeUs;
    }

    @Override
    public long getLastEventTime() {
        return (eventTimesUs.length == 0 ? -1 : eventTimesUs[eventTimesUs.length - 1]) + startTimeUs;
    }

    @Override
    public String getText(long timeUs) {
        return null;
    }
}
