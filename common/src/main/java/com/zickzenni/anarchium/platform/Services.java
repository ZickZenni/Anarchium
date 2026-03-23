package com.zickzenni.anarchium.platform;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.platform.service.IPacketDistributor;
import org.slf4j.Logger;

import java.util.ServiceLoader;

public class Services
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final IPacketDistributor PACKET_DISTRIBUTOR = load(IPacketDistributor.class);

    public static <T> T load(Class<T> clazz)
    {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));

        LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
