# 🔗 URL Shortening Service

A simple yet fully functional **URL Shortener API** built with **Spring Boot**.  
It demonstrates key backend concepts such as request handling, persistence, HTTP redirects, and REST design using the Spring MVC framework.

---

## ⚙️ How It Works

### 1. Shortening a URL — `POST /shorten-url`

When a user sends a POST request with a long URL, the application:

1. **Generates a unique short ID**

```java
id = RandomStringUtils.randomAlphanumeric(5, 10);
```

2. Creates a new Url record

```java
new Url(id, req.url(), LocalDateTime.now().plusMinutes(1));
```

Each entry contains:

- The random id
- The original long URL
- An expiration time (1 minute in this example)

3. Builds the shortened link

```java
var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", id);
```

Uses HttpServletRequest to extract the base URL (domain + port) dynamically, ensuring the shortened link works in any environment (localhost, staging, or production).

4. Returns the shortened URL

```json
{
  "shortenedUrl": "http://localhost:8080/Abc123"
}
```

---

### 2. Redirecting to the Original URL — `GET /{id}`

When a user accesses a shortened URL:

1. The system looks up the id in the database.

2. If not found → returns 404 Not Found.

3. If found → returns a 302 Found response with a Location header pointing to the original URL:

```java
HttpHeaders headers = new HttpHeaders();
headers.setLocation(URI.create(url.get().getFullUrl()));
return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
```

Example HTTP response:

```pgsql
HTTP/1.1 302 Found
Location: https://original-long-url.com/page
```

The browser then automatically redirects to the target address.

---

### 🧠 Key concepts

1. URI vs URL

- URI (Uniform Resource Identifier) → Identifies any resource (e.g., mailto:someone@example.com).
- URL (Uniform Resource Locator) → A URI that includes access information (protocol + location).
- In this project, java.net.URI is used for redirect destinations to stay fully compliant with HTTP standards.

2. Servlets & `HttpServletRequest`

- A Servlet is a Java component that runs inside a web container (Tomcat, Jetty, etc.) and handles HTTP requests/responses.
- Spring MVC sits on top of this layer, using the DispatcherServlet to route requests to controller methods.
- HttpServletRequest represents the incoming HTTP request. It exposes: headers, query parameters, path info and request URL
- In this app, it’s used to dynamically build the base URL for shortened links.

3. `HttpHeaders` and Redirects:

- HttpHeaders is a Spring abstraction for working with HTTP response headers.
- The Location header tells the client where to redirect.
- Example:

```java
headers.setLocation(URI.create(url.get().getFullUrl()));
```

Combined with a `302 FOUND` status, this triggers an automatic browser redirect.
