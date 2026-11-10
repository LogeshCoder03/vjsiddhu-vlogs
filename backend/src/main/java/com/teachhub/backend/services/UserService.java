package com.teachhub.backend.services;

import com.teachhub.backend.Utils.JwtUtil;
import com.teachhub.backend.Utils.ResponseData;
import com.teachhub.backend.dtos.Admin.AdminRequestDto.AdminRequestUserDto;
import com.teachhub.backend.dtos.Admin.AdminUserDto;
import com.teachhub.backend.dtos.UserDtos.LoginRequestDto;
import com.teachhub.backend.dtos.UserDtos.UserRegisterDto;
import com.teachhub.backend.entities.Role;
import com.teachhub.backend.entities.User;
import com.teachhub.backend.entities.UserToken;
import com.teachhub.backend.mappers.UserMapper;
import com.teachhub.backend.repositories.RolesRepository;
import com.teachhub.backend.repositories.TokenRepository;
import com.teachhub.backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Transactional
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private RolesRepository rolesRepository;
    private TokenRepository tokenRepository;
    private JwtUtil jwtUtil;
    private JavaMailSender mailSender;

    private void sendMail(String email,String subject,String body)throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body,true);
        mailSender.send(message);
    }

    public void register(UserRegisterDto userRegisterDto)throws MessagingException{
        User user=userMapper.registerDtoToEntity(userRegisterDto);
        if (userRepository.existsUserByEmail(user.getEmail())||
                userRepository.existsUserByEmail(user.getEmail())){
            throw new RuntimeException("Invalid credentials");
        }
        Role role=rolesRepository.findRoleByRole("USER").orElseThrow(
                ()->new RuntimeException("Role not found")
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(List.of(role));
        User savedUser=userRepository.save(user);
        String token= UUID.randomUUID().toString();
        UserToken verificationToken=new UserToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiresAt(LocalDateTime.now().plusHours(2));
        tokenRepository.save(verificationToken);
        String link="http://localhost:8080/auth/verify?token="+token;
        String mail="Hi,\n" +
                "\n" +
                "Thanks for signing up for our Game API platform.\n" +
                "\n" +
                "To activate your account, please verify your email address by clicking the link below:\n" +
                "\n" +
                "<a href=" +link+">verify</a>\n"+
                "\n" +
                "If you did not create this account, you can safely ignore this email.\n" +
                "\n" +
                "Best regards,\n" +
                "Game API Team\n";
        sendMail(savedUser.getEmail(),"Verify your email address",mail);
    }
    public String login(LoginRequestDto loginRequestDto){
        User user=userRepository.findUserByUsername(loginRequestDto.getUsername())
                .orElseThrow(()->new RuntimeException("Invalid email or password."));
        if (user.getIsActive().equals(false)){
            throw new RuntimeException("Please verify your email before logging in.");
        }
        Authentication authentication=new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );
        Authentication authManager=authenticationManager.authenticate(authentication);
        UserDetails userDetails=(UserDetails) authManager.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }
    public Page<AdminUserDto> getAllForAdmin(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,10);
        return userRepository.findAll(pageable)
                .map(user->{
                            AdminUserDto adminUserDto=userMapper.toDto(user);
                            List<String> roles=user
                                    .getRole()
                                    .stream()
                                    .map(Role::getRole)
                                    .toList();
                            adminUserDto.setRoles(roles);
                            return adminUserDto;
                        }
                        );
    }

    public void deleteForAdmin(Long id){
        userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("User not found")
        );
        userRepository.deleteById(id);
    }
    public void createForAdmin(AdminRequestUserDto adminRequestUserDto){
        if (userRepository.existsUserByEmail(adminRequestUserDto.getEmail())||
                userRepository.existsUserByUsername(adminRequestUserDto.getUsername())){
            throw new RuntimeException("Invalid credentials");
        }
        List<Role> roles=adminRequestUserDto.getRoles()
                .stream()
                .map(role->rolesRepository.findRoleByRole(role)
                        .orElseThrow(()->new RuntimeException("Role not found"))
                ).toList();
        User user=userMapper.toEntity(adminRequestUserDto);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void updateForAdmin(Long id,AdminRequestUserDto adminRequestUserDto){
        userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        if (userRepository.existsUserByEmail(adminRequestUserDto.getEmail())||
                userRepository.existsUserByEmail(adminRequestUserDto.getEmail())){
            throw new RuntimeException("Invalid credentials");
        }
        User user=userMapper.toEntity(adminRequestUserDto);
        List<Role> roles=adminRequestUserDto.getRoles()
                .stream()
                .map(role->rolesRepository.findRoleByRole(role)
                        .orElseThrow(()->new RuntimeException("Role not found"))
                ).toList();
        user.setId(id);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public ResponseData forgotPassword(String email)throws MessagingException{
        User user=userRepository.findUserByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        String token=UUID.randomUUID().toString();
        UserToken userToken=new UserToken();
        userToken.setToken(token);
        userToken.setUser(user);
        userToken.setExpiresAt(LocalDateTime.now().plusHours(1));
        tokenRepository.save(userToken);
        String link="http://localhost:5173/reset-password?token="+token;
        String mail="Hi,\n" +
                "\n" +
                "We received a request to reset your password.\n" +
                "\n" +
                "You can set a new password using the link below:\n" +
                "\n" +
                "<a href=" +link+">reset password</a>\n"+
                "\n" +
                "This link will expire in 30 minutes for security reasons.\n" +
                "\n" +
                "If you did not request a password reset, please ignore this email — your account is still safe.\n" +
                "\n" +
                "Best regards,\n" +
                "Game API Team";
        sendMail(user.getEmail(), "Reset Your Password",mail);
        return new ResponseData("\"Password reset link has been sent to your email.\"");
    }

    public ResponseData verifyUser(String token){
        UserToken verificationToken=tokenRepository.findByToken(token)
                .orElseThrow(()->new RuntimeException(""));
        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            return new ResponseData("This link has expired. Please request a new one.");
        }
        User user=verificationToken.getUser();
        user.setIsActive(true);
        userRepository.save(user);
        tokenRepository.deleteByToken(token);
        return new ResponseData("Your email has been verified successfully.");
    }

    public ResponseData resetPassword(String token,String password){
        UserToken userToken=tokenRepository.findByToken(token)
                .orElseThrow(()->new RuntimeException(""));
        if (userToken.getExpiresAt().isBefore(LocalDateTime.now())){
            return new ResponseData("This link has expired. Please request a new one.");
        }
        User user=userToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        tokenRepository.deleteByToken(token);
        return new ResponseData("Your password has been changed successfully.");
    }
}
