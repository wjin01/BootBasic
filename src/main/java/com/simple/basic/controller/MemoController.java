package com.simple.basic.controller;

import com.simple.basic.command.MemoVO;
import com.simple.basic.memo.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/memo")
public class MemoController {
    
    //insert기능 - 유효성검사
    //select기능 - 화면처리

    @Autowired
    @Qualifier("memoService")
    MemoService memoService;


    @GetMapping("/memoWrite")
    public String memoWrite(Model model) {
        model.addAttribute("vo", new MemoVO());
        return "memo/memoWrite";
    }
    
    //step.1 - 폼요청처리
    @PostMapping("/memoForm")
    public String memoForm(@Valid @ModelAttribute("vo") MemoVO vo, BindingResult binding) {

        if(binding.hasErrors()) {
            return "memo/memoWrite"; //유효성 검사 실패인 경우
        }

        //insert처리................
        memoService.insert(vo);

        return "redirect:/memo/memoList"; //목록화면으로
    }

    //step-2 목록화면
    @GetMapping("/memoList")
    public String memoList(Model model) {

        //select
        ArrayList<MemoVO> list = memoService.getList();
        model.addAttribute("list", list);

        return "memo/memoList";
    }

    




    


}
