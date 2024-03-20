package com.example.aquarionBackend.exceptions;

import com.example.aquarionBackend.configs.GlobalConfig;
import lombok.*;

public class WrongTokenExc extends GlobalAppException {
    public WrongTokenExc() {
        super(403, "Wrong jwt token");
    }
}
