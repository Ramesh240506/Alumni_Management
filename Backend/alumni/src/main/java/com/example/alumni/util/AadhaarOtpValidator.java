package com.example.alumni.util;

import com.example.alumni.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AadhaarOtpValidator {
    /**
     * Simulates sending an OTP. In a real application, this would integrate with an SMS gateway.
     * @param user The user to send the OTP for.
     * @param aadhaarNumber The Aadhaar number to verify against.
     */
    public void sendOtp(User user, String aadhaarNumber) {
        // TODO: Integrate with a real Aadhaar verification and SMS service
        // For now, we can log it for simulation purposes.
        System.out.println("Simulating sending Aadhaar OTP to user: " + user.getEmail() + " for Aadhaar: " + aadhaarNumber);
    }
    
    /**
     * Simulates OTP validation.
     * @param user The user being validated.
     * @param otp The OTP provided by the user.
     * @return True if the OTP is valid, otherwise false.
     */
    public boolean validate(User user, String otp) {
        // TODO: Implement real OTP validation logic (e.g., check against a stored value/cache)
        // For simulation, we can accept a "magic" OTP.
        return "123456".equals(otp);
    }
}