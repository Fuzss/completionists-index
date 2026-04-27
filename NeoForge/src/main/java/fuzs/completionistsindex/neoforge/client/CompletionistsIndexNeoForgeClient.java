package fuzs.completionistsindex.neoforge.client;

import fuzs.completionistsindex.common.CompletionistsIndex;
import fuzs.completionistsindex.common.client.CompletionistsIndexClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = CompletionistsIndex.MOD_ID, dist = Dist.CLIENT)
public class CompletionistsIndexNeoForgeClient {

    public CompletionistsIndexNeoForgeClient() {
        ClientModConstructor.construct(CompletionistsIndex.MOD_ID, CompletionistsIndexClient::new);
    }
}
