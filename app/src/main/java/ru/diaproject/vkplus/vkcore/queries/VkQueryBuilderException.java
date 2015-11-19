package ru.diaproject.vkplus.vkcore.queries;

public class VkQueryBuilderException extends Exception {
    public VkQueryBuilderException() {
    }

    public VkQueryBuilderException(String detailMessage) {
        super(detailMessage);
    }

    public VkQueryBuilderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public VkQueryBuilderException(Throwable throwable) {
        super(throwable);
    }
}
