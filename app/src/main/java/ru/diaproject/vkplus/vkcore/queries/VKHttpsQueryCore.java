package ru.diaproject.vkplus.vkcore.queries;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.vkcore.user.VKUserConfiguration;

public class VKHttpsQueryCore<T extends VKDataCore> extends VKQueryCore<T> {
    private static final String VK_CORE = "https://api.vk.com";

    protected VKHttpsQueryCore(VKUserConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected StringBuilder prepareQueryTemplate(StringBuilder builder) {
        return builder.append(VK_CORE);
    }
}
