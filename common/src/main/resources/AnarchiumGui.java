package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.client.gui.HistoryOverlay;
import com.zickzenni.anarchium.client.gui.ProgressBarOverlay;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public class AnarchiumGui
{
    private final HistoryOverlay historyOverlay;

    private final ProgressBarOverlay progressBarOverlay;

    public AnarchiumGui()
    {
        this.historyOverlay = new HistoryOverlay();
        this.progressBarOverlay = new ProgressBarOverlay();
    }

    public void render(final RenderGuiEvent event)
    {
        if (!(event instanceof RenderGuiEvent.Post))
        {
            return;
        }

        var graphics = event.getGuiGraphics();
        var deltaTime = event.getPartialTick().getRealtimeDeltaTicks();

        this.historyOverlay.render(graphics, deltaTime);
        this.progressBarOverlay.render(graphics, deltaTime);
    }
}
