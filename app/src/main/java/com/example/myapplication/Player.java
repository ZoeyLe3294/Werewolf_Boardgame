package com.example.myapplication;

import java.io.Serializable;

public class Player extends Source implements Serializable {
    int roleCode, status, subStatus;

    public Player(int roleCode, int status, int subStatus) {
        this.roleCode=roleCode;
        this.status = status;
        this.subStatus = subStatus;
    }


    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int ID) {
        this.roleCode = ID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }
}
