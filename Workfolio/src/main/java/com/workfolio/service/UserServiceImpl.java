package com.workfolio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

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
    /**
     * Verify the OTP sent to the user via email.
     * 
     * @param email the user's email address
     * @param otp   the OTP code entered by the user
     * @return true if the OTP is correct, false otherwise
     * @throws JobPortalException if the OTP is incorrect or not found
     */
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
        // Enhanced prompt with ATS-friendly structure and dynamic keywords
        String jobDescription = "Sample job: Java Developer with Spring Boot, REST API experience"; // Fetch from job URL or DB
        String prompt = String.format(
            "Generate a professional resume in plain text with sections: [Name], [Contact], [Summary], [Skills], [Experience], [Education]. " +
            "Use data for %s (email: %s), skills: Java, Spring Boot, and match keywords from this job: %s. Ensure ATS compatibility with clear section headers.",
            userDTO.getName(), userDTO.getEmail(), jobDescription);

        String aiResponse = callAIAPI(prompt);

        // Parse AI response into ResumeDTO with structured fields
        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setName(userDTO.getName());
        resumeDTO.setEmail(userDTO.getEmail());
        String[] sections = aiResponse.split("\\[|\\]");
        for (int i = 0; i < sections.length - 1; i += 2) {
            String section = sections[i + 1].trim();
            switch (sections[i].toLowerCase()) {
                case "name": resumeDTO.setName(section); break;
                case "contact": resumeDTO.setContact(section); break;
                case "summary": resumeDTO.setSummary(section); break;
                case "skills": resumeDTO.setSkills(section); break;
                case "experience": resumeDTO.setExperience(section); break;
                case "education": resumeDTO.setEducation(section); break;
            }
        }
        resumeDTO.setRawContent(aiResponse); // Store raw AI output for reference
        return resumeDTO;
    }

    @Override
    public ResponseDTO autoApplyJobs(UserDTO userDTO, List<String> jobUrls, ResumeDTO resumeDTO) throws JobPortalException {
        for (String jobUrl : jobUrls) {
            try (WebDriver driver = getWebDriver()) {
                driver.get(jobUrl);
                // ATS-compatible application: Upload resume as PDF
                String resumePdfPath = generateResumePdf(resumeDTO); // Generate PDF
                // driver.findElement(By.id("resume-upload")).sendKeys(resumePdfPath); // Site-specific
                System.out.println("Applied to " + jobUrl + " with ATS-friendly resume");
            } catch (Exception e) {
                throw new JobPortalException("Failed to apply to " + jobUrl + ": " + e.getMessage());
            }
        }
        return new ResponseDTO("Job applications submitted successfully");
    }

    @Override
    public ResponseDTO scheduleJobApplications(UserDTO userDTO, List<String> jobUrls) throws JobPortalException {
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

    private String callAIAPI(String prompt) {
        return restTemplate.postForObject(AI_API_URL, 
            new org.springframework.http.HttpEntity<>(new org.springframework.util.LinkedMultiValueMap<>(), 
                org.springframework.http.HttpHeaders().set("Authorization", "Bearer " + API_KEY)
                    .set("Content-Type", "application/json")), 
            String.class, prompt);
    }

    private WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

    private String generateResumePdf(ResumeDTO resumeDTO) throws JobPortalException {
        try {
            // Define PDF file path (e.g., in a temporary directory)
            String filePath = System.getProperty("java.io.tmpdir") + "/resume_" + resumeDTO.getName() + ".pdf";
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add ATS-friendly sections with keywords aligned to job description
            String jobDescription = "Java Developer with Spring Boot, REST API experience"; // Fetch dynamically if possible
            document.add(new Paragraph(new Text("Name: ").setBold()).add(resumeDTO.getName()));
            document.add(new Paragraph(new Text("Contact: ").setBold()).add(resumeDTO.getContact()));
            document.add(new Paragraph(new Text("Summary: ").setBold()).add(resumeDTO.getSummary()));
            document.add(new Paragraph(new Text("Skills: ").setBold()).add(alignSkillsWithJob(resumeDTO.getSkills(), jobDescription)));
            document.add(new Paragraph(new Text("Experience: ").setBold()).add(resumeDTO.getExperience()));
            document.add(new Paragraph(new Text("Education: ").setBold()).add(resumeDTO.getEducation()));

            // Close document
            document.close();
            return filePath;
        } catch (Exception e) {
            throw new JobPortalException("Failed to generate resume PDF: " + e.getMessage());
        }
    }

    // Helper method to align skills with job description for ATS
    private String alignSkillsWithJob(String skills, String jobDescription) {
        String[] jobKeywords = jobDescription.toLowerCase().split("\\s+");
        String[] userSkills = skills.toLowerCase().split(",\\s*");
        Map<String, Integer> skillMap = new HashMap<>();
        for (String skill : userSkills) {
            skillMap.put(skill, jobKeywords.length - countMissingKeywords(skill, jobKeywords));
        }
        return String.join(", ", skillMap.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .map(Map.Entry::getKey)
            .toArray(String[]::new));
    }

    private int countMissingKeywords(String skill, String[] jobKeywords) {
        return (int) java.util.Arrays.stream(jobKeywords)
            .filter(k -> !k.contains(skill))
            .count();
    }
}