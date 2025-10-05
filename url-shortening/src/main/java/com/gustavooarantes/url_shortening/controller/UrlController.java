package com.gustavooarantes.url_shortening.controller;

import java.net.URI;
import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gustavooarantes.url_shortening.controller.dto.ShortenUrlRequest;
import com.gustavooarantes.url_shortening.controller.dto.ShortenUrlResponse;
import com.gustavooarantes.url_shortening.entity.Url;
import com.gustavooarantes.url_shortening.repository.UrlRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UrlController {

  private final UrlRepository repository;

  public UrlController(UrlRepository repository) {
    this.repository = repository;
  }

  @PostMapping(value = "/shorten-url")
  public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest req,
      HttpServletRequest servletRequest) {

    String id;
    do {
      id = RandomStringUtils.randomAlphanumeric(5, 10);
    } while (repository.existsById(id));

    repository.save(new Url(id, req.url(), LocalDateTime.now().plusMinutes(1)));

    var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", id);

    return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Void> redirect(@PathVariable("id") String id) {

    var url = repository.findById(id);

    if (url.isEmpty())
      return ResponseEntity.notFound().build();

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(url.get().getFullUrl()));

    return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
  }
}
