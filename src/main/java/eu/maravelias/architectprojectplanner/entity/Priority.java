package eu.maravelias.architectprojectplanner.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Priority implements EnumClass<String> {

    HIGH("High"),
    NORMAL("Normal"),
    LOW("Low");

    private final String id;

    Priority(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Priority fromId(String id) {
        for (Priority at : Priority.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}