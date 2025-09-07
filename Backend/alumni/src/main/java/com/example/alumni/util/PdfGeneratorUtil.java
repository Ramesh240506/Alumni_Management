package com.example.alumni.util;

import com.example.alumni.entity.Donation;
import org.springframework.stereotype.Component;

/**
 * A utility component for generating PDF documents.
 * For the hackathon, this simulates PDF creation by printing to the console.
 */
@Component
public class PdfGeneratorUtil {

    /**
     * Simulates the generation of a PDF receipt for a donation.
     *
     * @param donation The donation to create a receipt for.
     * @return A byte array that would represent the PDF file.
     */
    public byte[] generateDonationReceipt(Donation donation) {
        System.out.println("\n--- SIMULATING PDF GENERATION ---");
        System.out.println("Generating receipt for Donation ID: " + donation.getDonationId());
        System.out.println("Amount: " + donation.getAmount());
        System.out.println("Donor: " + (donation.isAnonymous() ? "Anonymous" : donation.getDonor().getEmail()));
        System.out.println("--- END OF PDF SIMULATION ---\n");

        // In a real app, you would return the actual PDF bytes.
        // For the simulation, we return an empty byte array.
        return new byte[0];
    }
}