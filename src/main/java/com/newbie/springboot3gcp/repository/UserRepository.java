package com.newbie.springboot3gcp.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.newbie.springboot3gcp.entity.User;

import reactor.core.publisher.Flux;

public interface UserRepository extends FirestoreReactiveRepository<User> {

    Flux<User> findByCountry(String country);

}
