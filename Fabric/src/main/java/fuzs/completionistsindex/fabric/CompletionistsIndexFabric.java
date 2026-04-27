package fuzs.completionistsindex.fabric;

import fuzs.completionistsindex.CompletionistsIndex;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class CompletionistsIndexFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(CompletionistsIndex.MOD_ID, CompletionistsIndex::new);
    }
}
