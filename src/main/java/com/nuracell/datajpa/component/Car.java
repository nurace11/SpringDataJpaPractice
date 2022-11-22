package com.nuracell.datajpa.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class Car {
    @Autowired
    Engine engine;
}
