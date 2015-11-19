package ru.diaproject.vkplus.news.model.groups;

public enum CloseType {
    OPEN(0), CLOSE(1), PRIVATE(2);

    private Integer value;
    CloseType(Integer  val){
        value = val;
    }

    public static  CloseType valueOf(Integer id){
        for (CloseType value:values())
            if (value.getValue().equals(id))
                return value;
        return OPEN;
    }

    public Integer getValue() {
        return value;
    }
}
