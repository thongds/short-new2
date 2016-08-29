package icetea.com.hdvietplayer.player.text;

/**
 * Created by icetea on 5/2/2015.
 */
public class SrtNode {
    private String text;
    private long startTime;
    private long endTime;

    public SrtNode(String text, long startTime, long endTime) {
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getText() {
        return text;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
