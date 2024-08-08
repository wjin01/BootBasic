package com.simple.basic.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidVO {

    /*
    @NotNull - null제외
    @NotEmpty - null제외, 공백 제외
    @NotBlank - null제외, 공백 제외, 화이트스페이스
    @Pattern - 정규표현식으로 유효성 검사
    @Email - 이메일타입이어야 함
    @Max - 해당 값 이상
    @Min - 최소값
    @Size - 크기
    등등등.......
     */
    @NotBlank //(message = "이름은 필수 입니다")
    private String name;
    @NotNull //(message = "급여는 필수 입니다")
    private Integer salary; //유효성 검사하는 필드는 wrapper클래스로 작성(null을 가질 수 있음)
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "전화번호는 000-0000-0000형식 입니다")
    private String phone;
    @NotBlank
    @Email //(message = "이메일 형식 이어야 합니다") //공백은 통과시킵니다.
    private String email;
}
