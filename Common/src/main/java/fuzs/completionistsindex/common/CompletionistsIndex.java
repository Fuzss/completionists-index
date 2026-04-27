package fuzs.completionistsindex.common;

import fuzs.completionistsindex.common.config.ClientConfig;
import fuzs.puzzleslib.common.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompletionistsIndex implements ModConstructor {
    public static final String MOD_ID = "completionistsindex";
    public static final String MOD_NAME = "Completionist's Index";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
