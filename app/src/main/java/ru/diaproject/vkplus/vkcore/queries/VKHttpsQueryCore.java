package ru.diaproject.vkplus.vkcore.queries;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.IDataObject;

public class VKHttpsQueryCore<T extends IDataObject> extends VKQueryCore<T> {
    private static final String VK_CORE = "https://api.vk.com";

    protected VKHttpsQueryCore(User user) {
        super(user);
    }

    @Override
    protected StringBuilder prepareQueryTemplate(StringBuilder builder) {
        return builder.append(VK_CORE);
    }
}
