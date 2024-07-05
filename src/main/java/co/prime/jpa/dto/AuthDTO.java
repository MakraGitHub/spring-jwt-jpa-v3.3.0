package co.prime.jpa.dto;

public record AuthDTO(
        String accessToken,
        String refreshToken) {
}
