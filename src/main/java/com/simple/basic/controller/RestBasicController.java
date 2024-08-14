package com.simple.basic.controller;


import com.simple.basic.command.MemoVO;
import com.simple.basic.command.TestVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController //Controller + ResponseBody(컨트롤러에서 응답을 요청이 들어온곳으로 바꿈) 합성어
public class RestBasicController {

    @GetMapping("/hello")
    public String hello() {
        return "이게 무야??"; //요청을 보낸곳으로 응답하게 됩니다.
    }
    @GetMapping("/hello2")
    public String[] hello2() {
        return new String[] {"홍", "길", "동"}; //요청을 보낸곳으로 응답하게 됩니다.
    }

    ////////////////////////////////////////////////////////////////////////
    //get방식 요청받기 - 일반컨트롤러에서 받는형식과 똑같은 방법으로 가능함
    //http://localhost:8181/getData?num=1&name=홍길동

    //1st
//    @GetMapping("/getData")
//    public String getData(TestVO vo) {
//        System.out.println(vo.toString());
//        return "getData";
//    }

    @GetMapping("/getData")
    public String getData(@RequestParam("num") int num,
                          @RequestParam("name") String name) {
        System.out.println(num + ", " + name );
        return "getData";
    }

    //패스베리어블 방식
    //http://localhost:8181/getData2/1/홍길동
    @GetMapping("/getData2/{num}/{name}")
    public String getData2(@PathVariable("num") int num,
                           @PathVariable("name") String name) {
        System.out.println(num + "," + name);
        return "success";
    }

    //반환을 JSON형식으로 하려면 Map타입이나 VO를 쓰면 됩니다. (list, 배열도 됨)
    //Jackson-databind라이브러리가 필요함(스프링부트에 기본 포함됨)

    //1st
//    @GetMapping("/returnData")
//    public TestVO returnData() {
//        return new TestVO(1, "서버에서반환", 20, "서울시");
//    }
    //2nd
    @GetMapping("/returnData")
    public Map<String, Object> returnData() {
        Map<String, Object> map = new HashMap<>();
        map.put("num", 1);
        map.put("name", "홍길동");
        map.put("arr", Arrays.asList("a", "b", "c"));
        return map;
    }

    ///////////////////////////////////////////////////////////////////
    //post방식 - 소비자(사용자) 와 제공자(서버) 이 둘의 데이터를 주고 받는 규약이 정확하게 지켜져야 함

    //form형식으로 데이터 전송 - 소비자 데이터를 Form형식으로 반드시 만들어서 보내야 함
    //http://localhost:8181/getForm
    @PostMapping("/getForm")
    public String getForm( TestVO vo ) {
        System.out.println(vo.toString());
        return "success";
    }
    //json형식으로 데이터 전송
    //@RequestBody - JSON데이터를 -> 자바 오브젝트로 변형해서 맵핑
    //{ "name" : "홍길동", "age" : 20, "addr" : "서울시시" }

    //1st
//    @PostMapping("/getJSON")
//    public String getJSON( @RequestBody TestVO vo) {
//        System.out.println(vo.toString());
//        return "success";
//    }
    @PostMapping("/getJSON")
    public String getJSON( @RequestBody Map<String, Object> map) {
        System.out.println(map.toString());
        return "success";
    }
    //@PutMapping (수정), @DeleteMapping (삭제) - Post방식과 거의 유사함

    ///////////////////////////////////////////////////////////////////////////////
    //consumer - 반드시 이타입으로 보내라!! 안그럼 죽는다.
    //producer - 내가 이타입으로 줄게!!
    //기본값은 - "application/json" 입니다.
    @PostMapping(value = "/getResult", produces = "text/html", consumes = "text/plain")
    public String getResult( @RequestBody String str ) {
        System.out.println(str);
        return "<h3>문자열</h3>";
    }

    ///////////////////////////////////////////////////////////////////////////////
    //응답문서 명확하게 작성하기 ResponseEntity<데이터타입>
    //@CrossOrigin({"http://127.0.0.1:5500", "http://localhost:5500"})
    @CrossOrigin("*") //모든 서버에 대한 요청을 승안험 (위험할 수 있음)
    @PostMapping("/getEntity")
    public ResponseEntity<TestVO> getEntity( @RequestBody TestVO v) {

        System.out.println("받은 데이터:" + v.toString());

        TestVO vo = new TestVO(1, "홍길동", 20, "서울시");

        //1st
        //ResponseEntity entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        //ResponseEntity entity = new ResponseEntity(vo, HttpStatus.BAD_REQUEST);

        //2nd
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer JSON WEB TOKEN~" ); //키, 값
        header.add("Content-Type", "application/json"); //produce와 같은 표현
        //header.add("Access-Control-Allow-Origin", "http://example.com");

        ResponseEntity entity = new ResponseEntity(vo, header, HttpStatus.OK); //데이터, 헤더, 상태값
        return entity;
    }
    ///////////////////////////////////////////////////////////////
    /*
    요청주소 : /api/v1/getData
    메서드 : get
    요청 파라미터 : sno(숫자), name(문자)
    응답 파라미터 : MemoVO
    헤더에 담을 내용 HttpStatus.OK
    fetch API 사용해서 클라이언트에 요청 응답
    */

    @GetMapping("/api/v1/getData")
    public ResponseEntity<MemoVO> exampleData(@RequestParam("sno") int sno,
                                              @RequestParam("name") String name) {
        System.out.println(sno + ", " + name);

        return new ResponseEntity<MemoVO>(
                new MemoVO(1L, "홍길동", "1234", null, "Y")
                , HttpStatus.OK);
    }
    /*
    요청주소 : /api/v1/getInfo
    메서드 : post
    요청 파라미터 : MemoVO타입
    응답 파라미터 : List<MemoVO>타입
    헤더에 담을 내용 HttpStatus.OK
    fetch API 사용해서 클라이언트에 요청 응답
    */
    @PostMapping("/api/v1/getInfo")
    public ResponseEntity<List<MemoVO>> getInfo(@RequestBody @Valid MemoVO vo,
                                                BindingResult binding) {
        if(binding.hasErrors()) {
            System.out.println("유효성 검증에 실패함");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        //DB....
        List<MemoVO> list = new ArrayList<>();
        list.add(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }





}
