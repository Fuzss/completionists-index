package fuzs.completionistsindex.fabric.client;

import fuzs.completionistsindex.common.CompletionistsIndex;
import fuzs.completionistsindex.common.client.CompletionistsIndexClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class CompletionistsIndexFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(CompletionistsIndex.MOD_ID, CompletionistsIndexClient::new);
    }
}
