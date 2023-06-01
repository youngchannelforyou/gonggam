package App.Gonggam.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // mailSender의 설정을 구성합니다 (SMTP 호스트, 포트, 계정 정보 등)
        return mailSender;
    }

    // 필요에 따라 다른 빈 및 설정을 추가할 수 있습니다.
}
