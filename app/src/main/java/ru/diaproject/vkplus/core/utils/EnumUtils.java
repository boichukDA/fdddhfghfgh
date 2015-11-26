package ru.diaproject.vkplus.core.utils;


import android.content.Intent;

public final class EnumUtils {
    public static class Serializer<T extends Enum<T>> extends Deserializer<T> {
        private T victim;

        public Serializer(T victim) {
            super(victim.getDeclaringClass());
            this.victim = victim;
        }
        public void to(Intent intent) {
            intent.putExtra(name, victim.ordinal());
        }
    }
    public static class Deserializer<T extends Enum<T>> {
        protected Class<T> victimType;
        protected String name;
        public Deserializer(Class<T> victimType) {
            this.victimType = victimType;
            this.name = victimType.getName();
        }
        public T from(Intent intent) {
            if (!intent.hasExtra(name)) throw new IllegalStateException();
            return victimType.getEnumConstants()[intent.getIntExtra(name, -1)];
        }

        public T from(Intent intent, T defaultValue) {
            if (!intent.hasExtra(name)) return defaultValue;
            return victimType.getEnumConstants()[intent.getIntExtra(name, -1)];
        }
    }
    public static <T extends Enum<T>> Deserializer<T> deserialize(Class<T> victim) {
        return new Deserializer<>(victim);
    }
    public static <T extends Enum<T>> Serializer<T> serialize(T victim) {
        return new Serializer<>(victim);
    }
}
