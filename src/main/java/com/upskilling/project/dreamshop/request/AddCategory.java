package com.upskilling.project.dreamshop.request;


import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AddCategory {
    private String name;
}
