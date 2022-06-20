package com.license.license;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class UserService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) throws MessagingException{
        String randomCodeGenerator = RandomString.make(64);
        user.setVerificationCode(randomCodeGenerator);
        user.setEnabledUser(false);

        return userRepository.save(user);
    }

    public void sendEmailToEnableAccount(User user, String urlSite) throws MessagingException, UnsupportedEncodingException{
        String subjectEmail = "Email Verification Code";
        String senderEmailName = "Mihai";
        String sendtoAnyone = user.getEmail();
        String sendFromMe = "mihaigraur2000@gmail.com";

        String urlLegit = urlSite + "/legit?code=" + user.getVerificationCode();

        String bodyMail = "Dear " + user.getFirstName() + " " + user.getLastName() + ", <br>";
        bodyMail = bodyMail + "Click the link to activate your account.<br>";
        bodyMail = bodyMail + "<h2><a href=\"" + urlLegit +  "\">ACTIVATE ACCOUNT</a></h3><br>";
        bodyMail = bodyMail + "Thank you for your register,<br>Mihai Graur";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(senderEmailName, sendFromMe);
        mimeMessageHelper.setTo(sendtoAnyone);
        mimeMessageHelper.setSubject(subjectEmail);
        mimeMessageHelper.setText(bodyMail, true);

        javaMailSender.send(mimeMessage);
    }

    public boolean legit(String verificationCode){
        User user = userRepository.findByCodeVerification(verificationCode);

        if(user.isEnabledUser() || user == null){
            return false;
        }else{
            userRepository.enableUserForAuth(user.getId());
            return true;
        }
    }

    // public void processDatabaseAfterOAuth2(String email, String firstName, AuthenticationProducer authenticationProducer){
    //     User user = new User();
    //     user.setEmail(email);
    //     user.setFirstName(firstName);
    //     user.setEnabledUser(true);
    //     user.setAuthenticationProducer(authenticationProducer);

    //     userRepository.save(user);
    // }
}