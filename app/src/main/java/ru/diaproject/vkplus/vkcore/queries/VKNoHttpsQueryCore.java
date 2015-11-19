package ru.diaproject.vkplus.vkcore.queries;

import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.vkcore.user.VKUserConfiguration;

public class VKNoHttpsQueryCore extends VKQueryCore{
    private static final String VK_CORE = "http://api.vk.com";
    private static final String VK_NOHTTPS_SIGN = "&sig=%s";

    protected VKNoHttpsQueryCore(VKUserConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected StringBuilder sertificate(StringBuilder query) {
        query = super.sertificate(query);
        query = noHttpsSertificate(query);
        query.insert(0, VK_CORE);
        return query;
    }

    private StringBuilder noHttpsSertificate(StringBuilder builder){
        String forMD5 = builder.toString()+ configuration.getSecret() ;
        forMD5 = String.format(VK_NOHTTPS_SIGN, Utils.md5(forMD5));
                builder.append(forMD5);
        return builder;
    }
}
