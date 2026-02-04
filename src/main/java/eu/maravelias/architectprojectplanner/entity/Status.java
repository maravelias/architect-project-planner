package eu.maravelias.architectprojectplanner.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Status implements EnumClass<String> {

    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    BLOCKED("BLOCKED"),
    UNDER_REVIEW("UNDER_REVIEW"),
    COMPLETE("COMPLETE");

    private final String id;

    Status(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Status fromId(String id) {
        for (Status at : Status.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}