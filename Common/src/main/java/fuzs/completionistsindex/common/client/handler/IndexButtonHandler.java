package fuzs.completionistsindex.common.client.handler;

import fuzs.completionistsindex.common.CompletionistsIndex;
import fuzs.completionistsindex.common.client.gui.screens.index.ModsIndexViewScreen;
import fuzs.completionistsindex.common.config.ClientConfig;
import fuzs.puzzleslib.common.api.client.gui.v2.components.ScreenElementPositioner;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class IndexButtonHandler {
    private static final WidgetSprites INVENTORY_BUTTON_SPRITES = new WidgetSprites(CompletionistsIndex.id(
            "index/inventory_button"), CompletionistsIndex.id("index/inventory_button_highlighted"));
    private static final WidgetSprites MENU_BUTTON_SPRITES = new WidgetSprites(CompletionistsIndex.id(
            "index/menu_button"), CompletionistsIndex.id("index/menu_button_highlighted"));
    private static final String[] VANILLA_BUTTON_TRANSLATION_KEYS = {
            "gui.stats", "menu.returnToGame", "menu.reportBugs", "menu.shareToLan"
    };

    @Nullable
    private static AbstractWidget recipeBookButton;
    @Nullable
    private static AbstractWidget collectorsLogButton;

    public static void onAfterInventoryScreenInit(InventoryScreen screen, int screenWidth, int screenHeight, List<AbstractWidget> widgets, UnaryOperator<AbstractWidget> addWidget, Consumer<AbstractWidget> removeWidget) {
        if (CompletionistsIndex.CONFIG.get(ClientConfig.class).indexButtonScreen
                == ClientConfig.IndexButtonScreen.PAUSE_MENU) {
            return;
        }

        recipeBookButton = findRecipeBookButton(widgets);
        if (recipeBookButton == null) {
            return;
        }

        collectorsLogButton = new ImageButton(recipeBookButton.getX() + recipeBookButton.getWidth() + 8,
                recipeBookButton.getY(),
                20,
                18,
                INVENTORY_BUTTON_SPRITES,
                (Button button) -> {
                    screen.minecraft.setScreen(new ModsIndexViewScreen(screen, true));
                });
        addWidget.apply(collectorsLogButton);
    }

    @Nullable
    private static AbstractWidget findRecipeBookButton(List<AbstractWidget> widgets) {
        for (AbstractWidget widget : widgets) {
            if (widget instanceof ImageButton imageButton) {
                return imageButton;
            }
        }

        return null;
    }

    public static void onAfterMouseClick(Screen screen, MouseButtonEvent mouseButtonEvent) {
        if (collectorsLogButton != null && recipeBookButton != null) {
            collectorsLogButton.setX(recipeBookButton.getX() + recipeBookButton.getWidth() + 8);
            collectorsLogButton.setY(recipeBookButton.getY());
        }
    }

    public static void onAfterPauseScreenInit(PauseScreen screen, int screenWidth, int screenHeight, List<AbstractWidget> widgets, UnaryOperator<AbstractWidget> addWidget, Consumer<AbstractWidget> removeWidget) {
        if (CompletionistsIndex.CONFIG.get(ClientConfig.class).indexButtonScreen
                == ClientConfig.IndexButtonScreen.INVENTORY_MENU) {
            return;
        }

        AbstractWidget abstractWidget = new ImageButton(20, 20, MENU_BUTTON_SPRITES, (Button button) -> {
            screen.minecraft.setScreen(new ModsIndexViewScreen(screen, false));
        }, CommonComponents.EMPTY);
        if (ScreenElementPositioner.tryPositionElement(abstractWidget, widgets, VANILLA_BUTTON_TRANSLATION_KEYS)) {
            addWidget.apply(abstractWidget);
        }
    }
}
