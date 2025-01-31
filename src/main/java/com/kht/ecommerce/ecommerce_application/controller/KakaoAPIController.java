package com.kht.ecommerce.ecommerce_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/oauth/kakao")
public class KakaoAPIController {

    // ${변수이름} application.properties 나 config.properties 에 작성한 변수이름 가져오기
    // 변수이름에 해당하는 값을 불러오기
    @Value("${kakao.client-id}") // ${REST_API_KEY}
    private String kakaoClientId;

    // config.properties 에서 kakao.redirect-uri 직접적으로 가져올 수 있음
    // kakao.redirect-uri=http://localhost:8080/oauth/kakao/callback
    // java-spring 자체에서 보안을 가장 중요하게 생각하기 때문에
    // 아이디, 비밀번호와 같은 중요한 정보는 properties 파일로 나누어서
    // @Value 갑승로 호출해서 사용할 수 있도록 분리해 주는 것이 바람직함
    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.client-secret}")
    private String kakaoClientSecret;






    @GetMapping("/login")
    public ResponseEntity<?> getKakaoLoginUrl() { // ResponseEntity<?> 작성을 안해도 됨 현재 제대로 진행되고 있는지 상태 확인일 뿐
        // 카카오톡 개발 문서 에서 카카오로그인 > 예제 > 요청에 작성된 주소를 그대로 가져온 상태
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code" +
                "&client_id=" + kakaoClientId +
                "&redirect_uri=" + redirectUri;
        return ResponseEntity.ok(url);
    }

// kakao.redirect-uri=http://localhost:8080/oauth/kakao/callback

    @GetMapping("/callback")
    public String handleCallback(@RequestParam String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        if (kakaoClientSecret != null) {
            params.add("client_secret", kakaoClientSecret);
        }

        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        String accessToken = (String) response.getBody().get("access_token");

        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);
        ResponseEntity<Map> userResponse = restTemplate.postForEntity(userInfoUrl, userRequest, Map.class);

        Map userInfo = userResponse.getBody();
        Map<String, Object> properties = (Map<String, Object>) userInfo.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");

        // 추후 프로젝트에 맞게 카카오에서 가져올 값 설정
        String nickname = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");
        String email = (String) kakaoAccount.get("email");
        System.out.println(email);


        // 한글 깨짐 방지
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        // email 의 경우 영어 + 숫자 형식 -> 변환할 필요 없음
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);

        // 키-값 받아오기 위해 키-값 시작 = ? 기호
        // 키-값 여러 값 받아오고 전달할 경우 = & 기호로 키-값 다수 사용
        return "redirect:/signup?nickname=" + encodedNickname + "&email=" + email + ;
    }
}
