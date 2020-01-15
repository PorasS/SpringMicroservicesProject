package com.poras.moviecatlogservice.controllers;

import com.poras.moviecatlogservice.models.CatlogItems;
import com.poras.moviecatlogservice.models.Movie;
import com.poras.moviecatlogservice.models.Rating;
import org.apache.catalina.core.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catlog")
public class MovieCatlogServiceController {

//    Logger logger=LoggerF
    private static final Logger logger = LogManager.getLogger(MovieCatlogServiceController.class);


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{UserId}")
//  passing this {UserId} as an arugument to the method using @PathVariable
    public List<CatlogItems> getCatlog(@PathVariable("UserId") String UserId){
        logger.debug("debugging");

//        get all rated movie ids
        List<Rating> ratings= Arrays.asList(
                new Rating("Inception",4),new Rating("transformer",3),
                new Rating("ghost in the shell",4),new Rating("Arrival",5)
        );

        return ratings.stream().map(rating->{
//            Movie movie=restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
            Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8081/movies/"+ rating.getMovieId())
                            .retrieve()
                            .bodyToMono( Movie.class)
                            .block();

            return new CatlogItems(movie.getMovieId(), "sci-fi movie",rating.getRating());
        })
                .collect(Collectors.toList());

//            return ratings.stream().map(
//                    rating->
//                        new CatlogItems("Ghost in a shell", "sci-fi movie", 4))
//            ).collect(Collectors.toList());

//        get for each id call movie-info-service

//        put them all together
    /*   return Collections.singletonList(
               new CatlogItems("Ghost in a shell", "sci-fi movie", 4));*/

    }

}
