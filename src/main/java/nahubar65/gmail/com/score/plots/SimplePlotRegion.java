package nahubar65.gmail.com.score.plots;

import nahubar65.gmail.com.score.regions.Region;

public class SimplePlotRegion implements PlotRegion {

    private Region region;

    private MemberManager memberManager;

    private PlotRequestSender plotRequestSender;

    public SimplePlotRegion(Region region, MemberManager memberManager) {
        this.region = region;
        this.memberManager = memberManager;
        this.plotRequestSender = new DefaultPlotRequestSender(this);
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public void setRegion(Region region){
        this.region = region;
    }

    @Override
    public MemberManager getMemberManager() {
        return memberManager;
    }

    @Override
    public PlotRequestSender getPlotRequestSender() {
        return plotRequestSender;
    }
}