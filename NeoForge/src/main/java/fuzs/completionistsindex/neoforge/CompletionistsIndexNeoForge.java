package fuzs.completionistsindex.neoforge;

import fuzs.completionistsindex.common.CompletionistsIndex;
import fuzs.completionistsindex.common.data.client.ModLanguageProvider;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import net.neoforged.fml.common.Mod;

@Mod(CompletionistsIndex.MOD_ID)
public class CompletionistsIndexNeoForge {

    public CompletionistsIndexNeoForge() {
        ModConstructor.construct(CompletionistsIndex.MOD_ID, CompletionistsIndex::new);
        DataProviderBuilder.of(CompletionistsIndex.MOD_ID).addProvider(ModLanguageProvider::new);
    }
}
