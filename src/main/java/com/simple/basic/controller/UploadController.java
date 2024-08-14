package com.simple.basic.controller;

import com.simple.basic.command.UploadVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fileupload")
public class UploadController {

    @Value("${project.upload.path}") //application.properties 에 있는 키값 받아옴
    private String uploadPath; //업로드 경로

    //폴더 생성 함수
    public String makeFolder() {
        String filepath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        File file = new File(uploadPath + "/" + filepath);
        if(file.exists() == false) {
            file.mkdirs(); //폴더 생성
        }
        return filepath;
    }


    @GetMapping("/upload")
    public String uploadView() {
        return "fileupload/upload";
    }

    //단일 파일 업로드
    @PostMapping("/uploadOk")
    public String uploadOk(@RequestParam("file") MultipartFile file) {

        //1.브라우저별로 사용자의 풀경로가 제목에 포함되는 경우가 있음 (제외)
        //2.동일한 파일에 대한 처리, 동일한 이름이 올라오면 파일이 덥어써짐 (랜덤값 처리)
        //3.1개의 폴더에 저장 가능한 파일 수는 6만개? (월별 폴더를 생성해서 처리)

        String originName = file.getOriginalFilename(); //파일명
        String fileName = originName.substring( originName.lastIndexOf("\\") + 1);
        String filePath = makeFolder(); //폴더명
        String uuid = UUID.randomUUID().toString();
        String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName; //업로드 경로

        try {
            File path = new File(savePath); //파일명을 포함한 경로
            file.transferTo(path); //파일 업로드

            //fileName, filePath, uuid 이 값은 DB에 저장

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "fileupload/upload_ok";
    }


    //다중 파일 업로드 multiple
    @PostMapping("/uploadOk2")
    public String uploadOk2(MultipartHttpServletRequest files) {

        List<MultipartFile> list = files.getFiles("file"); //form 태그의 name 값

        for(MultipartFile file : list) { //파일 개수만큼 반복
            String originName = file.getOriginalFilename(); //파일명
            String fileName = originName.substring( originName.lastIndexOf("\\") + 1);
            String filePath = makeFolder(); //폴더명
            String uuid = UUID.randomUUID().toString();
            String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName; //업로드 경로

            try {
                File path = new File(savePath); //파일명을 포함한 경로
                file.transferTo(path); //파일 업로드

                //fileName, filePath, uuid 이 값은 DB에 저장

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "fileupload/upload_ok";
    }

    //복수 태그로 여러 파일 업로드 - 프로젝트 예제에서 사용할 업로드
    @PostMapping("/uploadOk3")
    public String uploadOk3(@RequestParam("file") List<MultipartFile> list) {

        //업로드 전에 빈 태그 값은 지우고 다시 처리
        list = list.stream().filter(a -> a.isEmpty() == false).collect(Collectors.toList());

        for(MultipartFile file : list) {

            String originName = file.getOriginalFilename(); //파일명
            String fileName = originName.substring( originName.lastIndexOf("\\") + 1);
            String filePath = makeFolder(); //폴더명
            String uuid = UUID.randomUUID().toString();
            String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName; //업로드 경로

            try {
                File path = new File(savePath); //파일명을 포함한 경로
                file.transferTo(path); //파일 업로드

                //fileName, filePath, uuid 이 값은 DB에 저장

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "fileupload/upload_ok";
    }

    //비동기 업로드
    //클라이언트에서는 form 형식 mulipart 타입으로 보내고 VO 타입으로 받으면 됨
    @PostMapping("/uploadOk4")
    @ResponseBody //일반 컨트롤러에서 Rest 처럼 쓰고싶다면 붙임
    public String uploadOk4(/*
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("writer") String writer
                            */
                            UploadVO vo
                            ) {
        MultipartFile file = vo.getFile();

        String originName = file.getOriginalFilename(); //파일명
        String fileName = originName.substring( originName.lastIndexOf("\\") + 1);
        String filePath = makeFolder(); //폴더명
        String uuid = UUID.randomUUID().toString();
        String savePath = uploadPath + "/" + filePath + "/" + uuid + "_" + fileName; //업로드 경로

        try {
            File path = new File(savePath); //파일명을 포함한 경로
            file.transferTo(path); //파일 업로드

            //fileName, filePath, uuid 이 값은 DB에 저장

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }


}
