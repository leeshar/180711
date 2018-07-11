package com.icia.vo;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Entity : DB 작업
// DTO(Data Transfer Object) 
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Size(min=8, max=10, message="아이디는 8~10자입니다")
	private String id;
	@Pattern(regexp="^[0-9a-zA-Z]{8,10}$", message="비밀번호는 8~10자입니다")
	private String pwd;
	@Pattern(regexp="^[가-힣]{2,5}$", message="이름은 2~5자입니다")
	private String irum;
	@Pattern(regexp="^[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.[0-9a-zA-Z]+$",message="올바른 이메일 형식이 아닙니다")
	private String email;
	private String photo;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reg_date;
	
}
