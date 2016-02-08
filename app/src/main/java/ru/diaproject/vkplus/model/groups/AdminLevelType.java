
package ru.diaproject.vkplus.model.groups;

public enum AdminLevelType {
    NOT_ADMIN(0), ADMINISTRATOR(3), MODERATOR(1), EDITOR(2);

    private Integer value;
    AdminLevelType(Integer val){
        value = val;
    }
    public static  AdminLevelType valueOf(Integer id){
        for (AdminLevelType value:values())
            if (value.getValue().equals(id))
                return value;
        return NOT_ADMIN;
    }

    public Integer getValue() {
        return value;
    }
}
