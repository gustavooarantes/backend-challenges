package com.gustavooarantes.url_shortening.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gustavooarantes.url_shortening.entity.Url;

public interface UrlRepository extends MongoRepository<Url, String> {

}
