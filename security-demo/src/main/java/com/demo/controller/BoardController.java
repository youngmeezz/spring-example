package com.demo.controller;

import com.demo.aop.MemberLoggings;
import com.demo.domain.Board;
import com.demo.domain.enums.MemberLogType;
import com.demo.mapper.BoardMapper;
import com.demo.security.SecurityUtil;
import com.demo.thread.ThreadLocalContext;
import com.demo.thread.ThreadLocalManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

/**
 * @author zacconding
 * @Date 2017-12-31
 * @GitHub : https://github.com/zacscoding
 */
@Controller
@RequestMapping("/boards/")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    @Autowired
    private BoardMapper boardMapper;

    @GetMapping("/list")
    public String getBoardAll(Model model) {
        model.addAttribute("boards", boardMapper.findAll());
        return "boards/list";
    }

    @MemberLoggings(logType = MemberLogType.READ, useComment = true)
    @GetMapping("/read/{id}")
    public String getBoard(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardMapper.findById(id));

        // insert member`s log
        SecurityUtil.setMemberLogComment("[READ BOARD] id : " + id);

        return "boards/read";
    }

    @GetMapping("/write")
    public String writeBoardForm() {
        return "boards/write";
    }

    @MemberLoggings(logType = MemberLogType.WRITE, useComment = true)
    @PostMapping("/write")
    public String writeBoard(Board board, RedirectAttributes rttr) {
        String message = null;

        boolean result = boardMapper.save(board) > 0;
        if(result) {
            message = "SUCCESS";
        }
        else{
            message = "FAIL";
        }

        // insert member`s log
        SecurityUtil.setMemberLogComment("[TRIED WRITE BOARD] result : " + message);


        rttr.addFlashAttribute("message", message);
        return "redirect:/boards/list";
    }

    @GetMapping("/modify/{id}")
    public String modifyBoardGET(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardMapper.findById(id));
        return "boards/modify";
    }

    @MemberLoggings(logType = MemberLogType.MODIFY, useComment = true)
    @PostMapping("/modify")
    public String modifyBoardPOST(Board board, RedirectAttributes rttr) {
        String message = null;
        board.setModDate(LocalDateTime.now());
        if(boardMapper.update(board) > 0) {
            message = "SUCCESS";
        }
        else {
            message = "FAIL";
        }

        // insert member`s log
        SecurityUtil.setMemberLogComment("[TRIED MODIFY BOARD] result : " + message);

        rttr.addFlashAttribute("message", message);

        return "redirect:/boards/list";
    }

    @MemberLoggings(logType = MemberLogType.REMOVE, useComment = true)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes rttr) {
        String message = null;
        if(boardMapper.delete(id) > 0) {
            message = "SUCCESS";
        }
        else {
            message = "FAIL";
        }

        // insert member`s log
        SecurityUtil.setMemberLogComment("[TRIED REMOVE BOARD] result : " + message);

        rttr.addFlashAttribute("message", "message");

        return "redirect:/boards/list";
    }
}
