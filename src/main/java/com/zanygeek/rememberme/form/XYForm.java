package com.zanygeek.rememberme.form;

import com.zanygeek.rememberme.entity.AddressForm;
import lombok.Data;

@Data
public class XYForm {
    private String status;
    private String errorMessage;
    private Addresses[] addresses;

    @Data
    public static class Addresses {
        private String x;    //x좌표
        private String y;  // y좌표
    }

    public String getX() {
        if (addresses[0] != null)
            return addresses[0].getX();
        else return null;
    }

    public String getY() {
        if (addresses[0] != null)
        return addresses[0].getY();
        else return null;
    }
}
