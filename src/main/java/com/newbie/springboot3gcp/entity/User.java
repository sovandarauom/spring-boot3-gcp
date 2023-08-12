package com.newbie.springboot3gcp.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collectionName = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @DocumentId
    String id;
    String name;
    String email;
    String country;

}