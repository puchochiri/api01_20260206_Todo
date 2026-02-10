package org.zerock.api01.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class SampleController {
        //http://localhost:9050/swagger-ui/index.html
        // authorize test
        // http://localhost:9050/files/apiLogin.html//generate token 클릭//f12 콘솔 상 token 복사
        // 아래와 같이 swagger authorize 로그인 후 테스트
        // bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtaWQiOiJhcGl1c2VyMTAiLCJpYXQiOjE3NjU0Mzg3OTAsImV4cCI6MTc2NTQzODg1MH0.J0U921Eu3GAbfRJswuAjM2Dus4Z0ILozrordYqUt5S42


  @Operation(
          summary = "Sample Get doA summary"
          ,description = "Sample Get doA description"
  )
  @GetMapping("/doA")
  @PreAuthorize("hasRole('ROLE_USER')")
  public List<String> doA() {
      return Arrays.asList("AAA","BBB","CCC");
    }


  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/doB")
  public List<String> doB() {
    return Arrays.asList("AdminAAA","AdminBBB","AdminCCC");
  }

}
