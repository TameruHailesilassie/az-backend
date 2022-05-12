package com.softech.ehr.security;

import org.springframework.stereotype.Component;

@Component("R")
public class RoleContainer {
    public static final String ADMIN = "ADMIN";
    public static final String PROVIDER = "PROVIDER";
    public static final String NURSE = "NURSE";
    public static final String LAB_TECHNICIAN = "LAB_TECHNICIAN";
    public static final String RADIOGRAPHER = "RADIOGRAPHER";
    public static final String RECEPTIONIST = "RECEPTIONIST";
}
