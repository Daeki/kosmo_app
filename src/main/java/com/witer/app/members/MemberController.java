package com.witer.app.members;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/member/**")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("join")
    public MemberDTO join(MemberDTO memberDTO, MultipartFile profile) throws Exception {
        // PK값이 있으면 update
        // PK값이 없으면 insert
        System.out.println(memberDTO.getUsername());
        System.out.println(profile.getOriginalFilename());
        memberDTO = memberService.join(memberDTO, profile);

        return memberDTO;

    }

}
