package fuzs.completionistsindex.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;
import fuzs.puzzleslib.common.api.config.v3.serialization.ConfigDataSet;
import fuzs.puzzleslib.common.api.config.v3.serialization.KeyedValueProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.util.Util;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ClientConfig implements ConfigCore {
    @Config(description = "Choose which screens to add the Completionist's Index button to.")
    public IndexButtonScreen indexButtonScreen = IndexButtonScreen.PAUSE_MENU;
    @Config(name = "indexed_items",
            description = "A list for overriding all items in the index, no other items are included.")
    List<String> indexedItemsRaw = KeyedValueProvider.<Item>tags().asStringList();
    @Config(name = "unobtainable_items", description = {
            "Add items to this list that should be excluded from the index, intended for creative-only items such as spawn eggs.",
            ConfigDataSet.CONFIG_DESCRIPTION
    })
    List<String> unobtainableItemsRaw = Util.make(new ArrayList<>(KeyedValueProvider.<Item>tags()
            .add(BlockItemIds.BEDROCK.item(),
                    BlockItemIds.BUDDING_AMETHYST.item(),
                    BlockItemIds.CHORUS_PLANT.item(),
                    BlockItemIds.DIRT_PATH.item(),
                    BlockItemIds.END_PORTAL_FRAME.item(),
                    BlockItemIds.FARMLAND.item(),
                    BlockItemIds.FROGSPAWN.item(),
                    BlockItemIds.INFESTED_STONE.item(),
                    BlockItemIds.INFESTED_COBBLESTONE.item(),
                    BlockItemIds.INFESTED_STONE_BRICKS.item(),
                    BlockItemIds.INFESTED_CHISELED_STONE_BRICKS.item(),
                    BlockItemIds.INFESTED_CRACKED_STONE_BRICKS.item(),
                    BlockItemIds.INFESTED_MOSSY_STONE_BRICKS.item(),
                    BlockItemIds.INFESTED_DEEPSLATE.item(),
                    BlockItemIds.REINFORCED_DEEPSLATE.item(),
                    BlockItemIds.SPAWNER.item(),
                    BlockItemIds.BARRIER.item(),
                    BlockItemIds.COMMAND_BLOCK.item(),
                    BlockItemIds.CHAIN_COMMAND_BLOCK.item(),
                    BlockItemIds.REPEATING_COMMAND_BLOCK.item(),
                    ItemIds.COMMAND_BLOCK_MINECART,
                    BlockItemIds.PETRIFIED_OAK_SLAB.item(),
                    BlockItemIds.PLAYER_HEAD.item(),
                    BlockItemIds.STRUCTURE_BLOCK.item(),
                    BlockItemIds.STRUCTURE_VOID.item(),
                    BlockItemIds.TRIAL_SPAWNER.item(),
                    BlockItemIds.VAULT.item())
            .asStringList()), (List<String> list) -> {
        list.add("*:*_spawn_egg");
    });
    @Config(name = "hidden_creative_tabs", description = {
            "Creative mode tabs containing items inaccessible in survival that should be excluded from the item groups, such as the operator items tab. ",
            ConfigDataSet.CONFIG_DESCRIPTION
    })
    List<String> hiddenCreativeTabsRaw = KeyedValueProvider.<CreativeModeTab>tags()
            .add(CreativeModeTabs.OP_BLOCKS)
            .asStringList();

    public ConfigDataSet<Item> indexedItems;
    public ConfigDataSet<Item> unobtainableItems;
    public ConfigDataSet<CreativeModeTab> hiddenCreativeTabs;

    @Override
    public void afterConfigReload() {
        this.indexedItems = ConfigDataSet.from(Registries.ITEM, this.indexedItemsRaw);
        this.unobtainableItems = ConfigDataSet.from(Registries.ITEM, this.unobtainableItemsRaw);
        this.hiddenCreativeTabs = ConfigDataSet.from(Registries.CREATIVE_MODE_TAB, this.hiddenCreativeTabsRaw);
    }

    public boolean filterItems(Item item) {
        if (this.indexedItems.isEmpty() || this.indexedItems.contains(item)) {
            return !this.unobtainableItems.contains(item);
        } else {
            return false;
        }
    }
}
