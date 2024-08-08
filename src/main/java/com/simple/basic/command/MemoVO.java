package com.simple.basic.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemoVO {

    private Long mno;
    @Size(min = 5, message = "최소 5글자 이상 입니다")
    private String memo;
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "000-0000-0000형식 입니다")
    private String phone;
    @Pattern(regexp = "[0-9]{4}", message = "숫자 4자리 입니다")
    private String pw;
    private String secret = "n";

}
