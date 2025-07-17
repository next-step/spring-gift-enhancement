package gift.member.controller;

import gift.authorization.dto.TokenResponseDto;
import gift.member.dto.*;
import gift.member.exception.InvalidMemberException;
import gift.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.lang.reflect.Field;
import java.util.Objects;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //멤버를 추가하는 api
    @PostMapping
    public ResponseEntity<Void> addMember(
            @Valid @RequestBody MemberAddRequestDto requestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidMemberException(getDefaultMessage(bindingResult));
        }
        memberService.addMember(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //멤버를 추가하고 토큰을 반환하는 api
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> RegisterMember(
            @Valid @RequestBody MemberRegisterRequestDto requestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidMemberException(getDefaultMessage(bindingResult));
        }
        TokenResponseDto responseDto = memberService.registerMember(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> LoginMember(
            @Valid @RequestBody MemberLoginRequestDto requestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidMemberException(getDefaultMessage(bindingResult));
        }
        TokenResponseDto responseDto = memberService.loginMember(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(
            @PathVariable Long id
    ) {
        MemberResponseDto responseDto = memberService.findMemberById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMemberById(
            @PathVariable Long id,
            @Valid @RequestBody MemberUpdateRequestDto requestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidMemberException(getDefaultMessage(bindingResult));
        }
        memberService.updateMemberById(id, requestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(
            @PathVariable Long id
    ) {
        memberService.deleteMemberById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String getDefaultMessage(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError == null || fieldError.getDefaultMessage() == null) {
            throw new InvalidMemberException("잘못된 요청입니다.");
        }
        return fieldError.getDefaultMessage();
    }
}
