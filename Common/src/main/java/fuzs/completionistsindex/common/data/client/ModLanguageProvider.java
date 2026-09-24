package fuzs.completionistsindex.common.data.client;

import fuzs.completionistsindex.common.CompletionistsIndex;
import fuzs.completionistsindex.common.client.CompletionistsIndexClient;
import fuzs.completionistsindex.common.client.gui.screens.index.IndexGroup;
import fuzs.completionistsindex.common.client.gui.screens.index.StatsSorting;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        addKeyCategory(CompletionistsIndex.MOD_ID, CompletionistsIndex.MOD_NAME);
        add(CompletionistsIndexClient.OPEN_INDEX_KEY_MAPPING, "Open Index");
        add(StatsSorting.CREATIVE.getComponent(), "Creative");
        add(StatsSorting.ALPHABETICALLY.getComponent(), "Alphabetically");
        add(StatsSorting.COLLECTED.getComponent(), "Collected");
        add(IndexGroup.CREATIVE.getComponent(), "Creative");
        add(IndexGroup.MODS.getComponent(), "Mods");
    }
}
