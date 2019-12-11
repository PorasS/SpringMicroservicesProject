package com.poras.moviecatlogservice.controllers;

import com.poras.moviecatlogservice.models.CatlogItems;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catlog")
public class MovieCatlogServiceController {

    @RequestMapping("/{UserId}")
//  passing this {UserId} as an arugument to the method using @PathVariable
    public List<CatlogItems> getCatlog(@PathVariable("UserId") String UserId){
       return Collections.singletonList(
               new CatlogItems("Ghost in a shell", "sci-fi movie", 4));


    }

}
