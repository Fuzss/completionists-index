package fuzs.completionistsindex.common.client.gui.screens.index;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.stats.StatsCounter;
import org.jspecify.annotations.Nullable;

/**
 * An implementation similar to vanilla's old {@code StatsUpdateListener} interface, whose functionality is now
 * restricted to instances of {@link StatsScreen}.
 */
public abstract class StatsUpdateListener extends StatsScreen {

    public StatsUpdateListener(@Nullable Screen lastScreen) {
        super(lastScreen, new StatsCounter());
    }

    /**
     * @see Screen#init()
     */
    @Override
    protected void init() {
        // NO-OP
    }

    /**
     * @see Screen#extractRenderState(GuiGraphicsExtractor, int, int, float)
     */
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Renderable renderable : this.renderables) {
            renderable.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    /**
     * @see Screen#extractMenuBackground(GuiGraphicsExtractor)
     */
    @Override
    protected void extractMenuBackground(GuiGraphicsExtractor guiGraphics) {
        this.extractMenuBackground(guiGraphics, 0, 0, this.width, this.height);
    }

    /**
     * @see Screen#repositionElements()
     */
    @Override
    protected void repositionElements() {
        this.rebuildWidgets();
    }

    @Override
    public void onStatsUpdated() {
        // NO-OP
    }
}
