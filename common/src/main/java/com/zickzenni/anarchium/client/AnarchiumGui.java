package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.client.gui.HistoryOverlay;
import com.zickzenni.anarchium.client.gui.ProgressBarOverlay;
import com.zickzenni.anarchium.event.IRenderGuiEvent;

public class AnarchiumGui
{
    private final HistoryOverlay historyOverlay;

    private final ProgressBarOverlay progressBarOverlay;

    public AnarchiumGui()
    {
        this.historyOverlay = new HistoryOverlay();
        this.progressBarOverlay = new ProgressBarOverlay();
    }

    public void render(final IRenderGuiEvent event)
    {
        if (event.getStage() != IRenderGuiEvent.Stage.POST)
        {
            return;
        }

        var graphics = event.getGraphics();
        var deltaTime = event.getPartialTick().getRealtimeDeltaTicks();

        this.historyOverlay.render(graphics, deltaTime);
        this.progressBarOverlay.render(graphics, deltaTime);
    }
}
