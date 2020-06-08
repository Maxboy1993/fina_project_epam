package entity;

import entity.exception.EntityException;

public enum  StatusType {
    ACTIVE("active"),
    INACTIVE("inactive");

    private String statusType;

    StatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getStatusType(){
        return statusType;
    }

    public StatusType getStatusTypeByValue(String value) throws EntityException {
        StatusType[] statusTypes = StatusType.values();
        for (StatusType status : statusTypes) {
            if (status.getStatusType().equals(value)){
                return status;
            }
        }
        throw new EntityException("Illegal argument");
    }
}
