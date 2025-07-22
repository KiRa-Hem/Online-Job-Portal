# Online Job Portal

## Overview
The Online Job Portal is a full-stack web application designed to connect job seekers with employers. The backend, built with Spring Boot and MongoDB, manages user authentication, job applications, and OTP-based security. The frontend, developed with React and TypeScript using Mantine UI and Tailwind CSS, provides a responsive, dark-themed interface with various pages for job browsing and application submission.

## Features
- **User Authentication**: Register, login, reset passwords, and OTP verification via email.
- **Job Applications**: Submit detailed applications with personal and educational data.
- **Job Browsing**: Search and filter job listings with a dedicated job description page.
- **Responsive Design**: Custom dark theme with Mantine UI and Tailwind CSS styling.
- **Email Notifications**: OTPs sent using Gmail SMTP.

## Prerequisites
- **Backend (Workfolio)**:
  - Java 17
  - Maven
  - MongoDB (local or cloud instance)
  - Git
- **Frontend (job-portal)**:
  - Node.js (version 14.x or higher)
  - npm
  - A modern web browser (e.g., Chrome, Firefox)

## Installation

### Backend (Workfolio)
1. Navigate to `D:\Projects\projectinfo-main\Workfolio`.
2. Install dependencies:
   ```bash
   mvn install
   ```
3. Configure `application.properties` with your MongoDB URI and Gmail credentials.
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will be available at `http://localhost:8080`.

### Frontend (job-portal)
1. Navigate to `D:\Projects\projectinfo-main\job-portal`.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the application:
   ```bash
   npm start
   ```
   The frontend will be available at `http://localhost:3000`.

## Usage
- **Job Seekers**: Register, log in, apply for jobs, and manage profiles via the frontend.
- **Employers**: (Future feature) Post and manage job listings.
- Access backend APIs (e.g., `/users/register`, `/applicantDetails/applyjob`) for integration.

## Project Structure
```
D:\Projects\projectinfo-main\
├── job-portal/                        # 🖥️ Frontend: React TypeScript Project
│   ├── public/                        # Static files served by React
│   │   ├── Category/                  # Job categories (icons/images)
│   │   ├── Companies/                 # Company logos
│   │   ├── Icons/                     # Icon set (brands like Apple, Google, etc.)
│   │   ├── teamImg/                   # Team member images (JPG, PNG)
│   │   ├── favicon.ico                # Website favicon
│   │   ├── index.html                 # HTML template for React app
│   │   ├── manifest.json              # Web app manifest for PWA
│   │   ├── robots.txt                 # SEO bots crawling instructions
│   │   └── logo192.png/logo512.png   # PWA icons
│   ├── src/                           # Source code for React
│   │   ├── ApplyJob/                  # Apply Job section
│   │   │   └── ApplyJobComp.tsx
│   │   ├── Components/FindJobs/       # Components for job listings
│   │   │   ├── JobCard.tsx
│   │   │   ├── Jobs.tsx
│   │   │   ├── MultiInput.tsx
│   │   │   ├── SearchBar.tsx
│   │   │   └── Sort.tsx
│   │   ├── Footer/                    # Website footer
│   │   │   └── Footer.tsx
│   │   ├── Header/                    # Website header
│   │   │   ├── About.tsx
│   │   │   ├── Contact.tsx
│   │   │   ├── Header.tsx
│   │   │   ├── NavLinks.tsx
│   │   │   └── ProfileMenu.tsx
│   │   ├── LandingPage/               # Home page section components
│   │   │   ├── Companies.tsx
│   │   │   ├── Dreamjob.tsx
│   │   │   ├── JobCategory.tsx
│   │   │   └── Subscribe.tsx
│   │   ├── SignUpLogin/               # Auth components
│   │   │   ├── Login.tsx
│   │   │   ├── ResetPassword.tsx
│   │   │   └── SignUp.tsx
│   │   ├── Data/                      # Centralized data and mock DB
│   │   │   ├── Company.tsx
│   │   │   ├── Data.tsx
│   │   │   ├── JobDescData.tsx
│   │   │   ├── JobsData.tsx
│   │   │   ├── PostedJob.tsx
│   │   │   ├── PostJob.tsx
│   │   │   ├── Profile.tsx
│   │   │   └── TalentData.tsx
│   │   ├── JobDesc/                   # Job description component
│   │   │   └── JobDesc.tsx
│   │   ├── Pages/                     # Full page views
│   │   │   ├── ApplyJobPage.tsx
│   │   │   ├── FindJobs.tsx
│   │   │   ├── Homepage.tsx
│   │   │   ├── JobDescPage.tsx
│   │   │   ├── MockTest.tsx
│   │   │   └── SignUpPage.tsx
│   │   ├── Services/                  # API service hooks or utils (if present)
│   │   ├── App.tsx                    # Entry component for React
│   │   ├── App.css                    # Global styles
│   │   ├── index.tsx                  # React DOM render root
│   │   ├── index.css                  # Index styles
│   │   └── reportWebVitals.ts         # Performance measurement
│   ├── tailwind.config.js             # Tailwind CSS configuration
│   ├── tsconfig.json                  # TypeScript config
│   ├── package.json                   # Project dependencies
│   ├── package-lock.json              # Dependency lock file
│   └── README.md                      # Project description
├── Workfolio/                         # ☕ Backend: Java Spring Boot App
│   ├── src/                           # Source code
│   │   └── main/
│   │       └── java/com/workfolio/
│   │           ├── api/               # REST Controllers
│   │           │   ├── JobAPI.java
│   │           │   └── UserAPI.java
│   │           ├── dto/               # DTO classes
│   │           │   ├── ApplicantDTO.java
│   │           │   ├── LoginDTO.java
│   │           │   ├── ResponseDTO.java
│   │           │   └── UserDTO.java
│   │           ├── entity/            # JPA Entities
│   │           │   ├── Applicant.java
│   │           │   ├── OTP.java
│   │           │   ├── Sequence.java
│   │           │   └── User.java
│   │           ├── exception/         # Custom exceptions
│   │           │   └── JobPortalException.java
│   │           ├── repository/        # JPA Repositories
│   │           │   ├── ApplicantRepository.java
│   │           │   ├── OTPRepository.java
│   │           │   └── UserRepository.java
│   │           ├── service/           # Business Logic Layer
│   │           │   ├── JobService.java
│   │           │   ├── JobServiceImpl.java
│   │           │   ├── UserService.java
│   │           │   └── UserServiceImpl.java
│   │           ├── utility/           # Utility and Configs
│   │           │   ├── Data.java
│   │           │   ├── ErrorInfo.java
│   │           │   ├── ExceptionControllerAdvice.java
│   │           │   ├── SecurityConfig.java
│   │           │   ├── Utilities.java
│   │           │   └── WorkfolioApplication.java  # Spring Boot entry point
│   │   └── resources/
│   │       └── application.properties  # Spring Boot config file
│   ├── test/                          # Test cases
│   │   └── java/com/workfolio/
│   │       └── WorkfolioApplicationTests.java
│   ├── pom.xml                         # Maven build file
│   └── mvnw / mvnw.cmd                 # Maven wrapper scripts
├── .gitignore                          # Git ignored files list
├── .gitattributes                      # Git settings
└── README.md                           # Optional readme for the root project
```

## Technologies Used
- **Backend**: Spring Boot, MongoDB, Maven, Lombok, Spring Security, Spring Mail
- **Frontend**: React, TypeScript, Mantine UI, Tailwind CSS, React Router
- **Other**: Gmail SMTP for email, PWA support via manifest.json

## Contributing
1. Fork the repository.
2. Create a branch (`git checkout -b feature-name`).
3. Commit changes (`git commit -m "Add feature"`).
4. Push and create a pull request.

## License
[MIT License](LICENSE)

## Contact
For questions, 
Contact Vasa Hemesh 
at hemeshvasa@gmail.com.
