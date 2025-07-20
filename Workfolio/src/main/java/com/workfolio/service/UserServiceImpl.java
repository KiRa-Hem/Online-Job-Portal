package com.workfolio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.workfolio.dto.LoginDTO;
import com.workfolio.dto.ResponseDTO;
import com.workfolio.dto.UserDTO;
import com.workfolio.dto.ResumeDTO;
import com.workfolio.entity.OTP;
import com.workfolio.entity.User;
import com.workfolio.exception.JobPortalException;
import com.workfolio.repository.OTPRepository;
import com.workfolio.repository.UserRepository;
import com.workfolio.utility.Data;
import com.workfolio.utility.Utilities;

import jakarta.mail.internet.MimeMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private RestTemplate restTemplate; // For AI API calls

    // AI API endpoint (e.g., OpenAI or custom AI service)
    private static final String AI_API_URL = "https://api.openai.com/v1/completions"; // Placeholder, update with real URL
    private static final String API_KEY = "your-api-key-here"; // Replace with secure API key

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
        Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
        if (optional.isPresent())
            throw new JobPortalException("USER_FOUND");
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new JobPortalException("INVALID_CREDENTIALS");
        return user.toDTO();
    }

    @Override
    public boolean sendOtp(String email) throws Exception {
        userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true);
        message.setTo(email);
        message.setSubject("Your OTP code");
        String genOTP = Utilities.generateOTP();
        OTP otp = new OTP(email, genOTP, LocalDateTime.now());
        otpRepository.save(otp);
        message.setText(Data.getMessageBody(genOTP), true);
        mailSender.send(mm);
        return true;
    }

    @Override
    public boolean verifyOtp(String email, String otp) throws JobPortalException {
        OTP otpEntity = otpRepository.findById(email).orElseThrow(() -> new JobPortalException("OTP_NOT_FOUND"));
        if (!otpEntity.getOtpCode().equals(otp))
            throw new JobPortalException("OTP_INCORRECT");
        return true;
    }

    @Override
    public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);
        return new ResponseDTO("Password changed successfully");
    }

    @Override
    public ResumeDTO generateResume(UserDTO userDTO) throws JobPortalException {
        // Prepare prompt for AI (e.g., OpenAI)
        String prompt = String.format("Generate a professional resume for %s with email %s, skills: %s, education: %s",
                userDTO.getName(), userDTO.getEmail(), "Java, Spring Boot", "B.Tech"); // Extend with user data
        String aiResponse = callAIAPI(prompt);

        // Parse AI response into ResumeDTO (simplified example)
        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setName(userDTO.getName());
        resumeDTO.setEmail(userDTO.getEmail());
        resumeDTO.setContent(aiResponse); // AI-generated resume content
        return resumeDTO;
    }

    @Override
    public ResponseDTO autoApplyJobs(UserDTO userDTO, List<String> jobUrls, ResumeDTO resumeDTO) throws JobPortalException {
        for (String jobUrl : jobUrls) {
            try (WebDriver driver = getWebDriver()) {
                driver.get(jobUrl);
                // Simulate form filling (e.g., name, email, resume)
                // This is a placeholder; actual implementation depends on job site structure
                // Example: driver.findElement(By.id("name")).sendKeys(userDTO.getName());
                // Example: driver.findElement(By.id("resume")).sendKeys(resumeDTO.getContent());
                // Submit application (site-specific logic)
                System.out.println("Applied to " + jobUrl);
            } catch (Exception e) {
                throw new JobPortalException("Failed to apply to " + jobUrl + ": " + e.getMessage());
            }
        }
        return new ResponseDTO("Job applications submitted successfully");
    }

    @Override
    public ResponseDTO scheduleJobApplications(UserDTO userDTO, List<String> jobUrls) throws JobPortalException {
        // Store job URLs for scheduled execution (e.g., in a database or queue)
        // Here, we simulate scheduling by triggering autoApplyJobs after a delay
        new Thread(() -> {
            try {
                Thread.sleep(5000); // 5-second delay as an example
                autoApplyJobs(userDTO, jobUrls, generateResume(userDTO));
            } catch (Exception e) {
                throw new JobPortalException("Scheduled job application failed: " + e.getMessage());
            }
        }).start();
        return new ResponseDTO("Job applications scheduled successfully");
    }

    @Scheduled(fixedRate = 3000)
    public void removeExpiredOTPs() {
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
        List<OTP> expiredOTPs = otpRepository.findByCreationTimeBefore(expiry);
        if (!expiredOTPs.isEmpty()) {
            otpRepository.deleteAll(expiredOTPs);
            System.out.println("Removed " + expiredOTPs.size() + " expired OTps");
        }
    }

    // Helper method to call AI API
    private String callAIAPI(String prompt) {
        // Configure REST call to AI API (e.g., OpenAI)
        // This is a simplified example; use a proper SDK or secure key management
        return restTemplate.postForObject(AI_API_URL, 
            new org.springframework.http.HttpEntity<>(new org.springframework.util.LinkedMultiValueMap<>(), 
                org.springframework.http.HttpHeaders().set("Authorization", "Bearer " + API_KEY)
                    .set("Content-Type", "application/json")), 
            String.class, prompt);
    }

    // Helper method to get WebDriver instance
    private WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        return new ChromeDriver(options);
    }
}
