package org.example.cloud2fa.adapter.in.web.request;

public record CreateUserWebRequest(
            String username,
            String password,
            String masterPassword,
            String email) {
}
