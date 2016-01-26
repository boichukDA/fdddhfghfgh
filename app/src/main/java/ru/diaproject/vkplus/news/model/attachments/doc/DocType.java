package ru.diaproject.vkplus.news.model.attachments.doc;

public enum DocType {
    UNKNOWN(-1), GIF_DOC_TYPE(3);

    private int value;

    DocType(int type) {
        this.value = type;
    }

    public static DocType valueOf(int type) {
        DocType resType = UNKNOWN;

        for(DocType currentType:values())
            if (currentType.getValue() == type){
                resType = currentType;
                break;
            }
        return resType;
    }

    public int getValue() {
        return value;
    }
}
