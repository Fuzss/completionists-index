package fuzs.completionistsindex.client.gui.screens.index;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import fuzs.completionistsindex.CompletionistsIndex;
import fuzs.completionistsindex.client.gui.components.index.IndexViewEntry;
import fuzs.completionistsindex.client.gui.components.index.IndexViewSingleEntry;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class ItemsIndexViewScreen extends IndexViewScreen<StatsSorting> {
    private static final WidgetSprites BACK_BUTTON_SPRITES = new WidgetSprites(CompletionistsIndex.id(
            "index/back_button"), CompletionistsIndex.id("index/back_button_highlighted"));
    private static final WidgetSprites ENABLE_EDITING_BUTTON_SPRITES = new WidgetSprites(CompletionistsIndex.id(
            "index/enable_editing_button"), CompletionistsIndex.id("index/enable_editing_button_highlighted"));
    private static final WidgetSprites DISABLE_EDITING_BUTTON_SPRITES = new WidgetSprites(CompletionistsIndex.id(
            "index/disable_editing_button"), CompletionistsIndex.id("index/disable_editing_button_highlighted"));

    private static StatsSorting statsSorting = StatsSorting.COLLECTED;
    private final List<ItemStack> items;
    @Nullable
    private final ServerPlayer serverPlayer;
    private boolean isEditingPermitted;
    private AbstractWidget enableEditingButton;
    private AbstractWidget disableEditingButton;

    public ItemsIndexViewScreen(Screen lastScreen, boolean fromInventory, List<ItemStack> items) {
        super(lastScreen, fromInventory);
        this.items = items;
        this.serverPlayer = this.getPlayerFromServer();
    }

    @Nullable
    private ServerPlayer getPlayerFromServer() {
        IntegratedServer integratedServer = this.minecraft.getSingleplayerServer();
        if (integratedServer != null) {
            ServerPlayer serverPlayer = integratedServer.getPlayerList()
                    .getPlayer(integratedServer.getSingleplayerProfile().id());
            if (serverPlayer != null && serverPlayer.canUseGameMasterBlocks()) {
                return serverPlayer;
            }
        }

        return null;
    }

    public @Nullable ServerPlayer getServerPlayer() {
        return this.isEditingPermitted ? this.serverPlayer : null;
    }

    @Override
    public void handleHoveringCursor(GuiGraphicsExtractor guiGraphics) {
        if (this.serverPlayer != null) {
            guiGraphics.requestCursor(this.isEditingPermitted ? CursorTypes.POINTING_HAND : CursorTypes.NOT_ALLOWED);
        }
    }

    @Override
    protected Stream<IndexViewEntry<?>> getPageEntries() {
        StatsCounter statsCounter = this.minecraft.player.getStats();
        return this.items.stream().map((ItemStack itemStack) -> {
            IndexViewSingleEntry indexViewEntry = new IndexViewSingleEntry(this, itemStack);
            indexViewEntry.initialize(statsCounter);
            return indexViewEntry;
        });
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new ImageButton(this.leftPos + 17,
                this.topPos + 11,
                16,
                13,
                BACK_BUTTON_SPRITES,
                (Button button) -> {
                    this.minecraft.setScreen(this.lastScreen);
                })).setTooltip(Tooltip.create(CommonComponents.GUI_BACK));
        if (this.serverPlayer != null) {
            this.enableEditingButton = this.addRenderableWidget(this.createEditingButton(ENABLE_EDITING_BUTTON_SPRITES,
                    true));
            this.disableEditingButton = this.addRenderableWidget(this.createEditingButton(DISABLE_EDITING_BUTTON_SPRITES,
                    false));
            this.setEditingPermitted(this.isEditingPermitted);
        }

        this.rebuildPages();
    }

    private AbstractWidget createEditingButton(WidgetSprites widgetSprites, boolean isEditingPermitted) {
        return new ImageButton(this.leftPos + 316 - 6 - 26 * 2 + 5 - 3,
                this.topPos - 23 + 5,
                16,
                16,
                widgetSprites,
                (Button button) -> {
                    this.setEditingPermitted(isEditingPermitted);
                });
    }

    private void setEditingPermitted(boolean isEditingPermitted) {
        this.isEditingPermitted = isEditingPermitted;
        this.enableEditingButton.active = !isEditingPermitted;
        this.disableEditingButton.active = isEditingPermitted;
    }

    @Override
    protected StatsSorting getSortProvider() {
        return statsSorting;
    }

    @Override
    protected void setSortProvider(StatsSorting sortProvider) {
        statsSorting = sortProvider;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        if (this.serverPlayer != null) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                    INDEX_LOCATION,
                    this.leftPos + 316 - 6 - 26 * 2 - 3,
                    this.topPos - 23,
                    this.isEditingPermitted ? 368 : 342,
                    45,
                    26,
                    23,
                    512,
                    256);
        }
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (super.keyPressed(keyEvent)) {
            return true;
        } else if (keyEvent.input() == InputConstants.KEY_BACKSPACE && this.shouldCloseOnEsc()) {
            this.minecraft.setScreen(this.lastScreen);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClose() {
        if (this.lastScreen != null) {
            this.lastScreen.onClose();
        }
    }
}
